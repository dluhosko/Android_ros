/*///////////////////////////////////////////////////////////////////////////
//                                      ###################################
//                                     #########                    ########
//                                      ##################          ########
//                                                #########         ########
//                                     ###################   ##############
//                                                www.sensodrive.de
//
//  File:           VibroTacDevice.java
//  Project:        VibroTac Android API
//  Originator:     jtymoszuk
//  Copyright:      SENSODRIVE GmbH, Wessling
//
//      $LastChangedDate: 2017-09-08 08:36:19 +0200 (Fr, 08. Sep 2017) $
//        $LastChangedBy: geist $
//  $LastChangedRevision: 609 $
//
/////////////////////////////////////////////////////////////////////////////
//  $Id: VibroTacDevice.java 609 2017-09-08 06:36:19Z geist $
///////////////////////////////////////////////////////////////////////////*/

package com.sensodrive.vibrotac.api.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import com.sensodrive.vibrotac.api.exceptions.VibroTacCommandNotAcknowledgedException;
import com.sensodrive.vibrotac.api.exceptions.VibroTacException;
import com.sensodrive.vibrotac.api.exceptions.VibroTacIOException;
import com.sensodrive.vibrotac.api.exceptions.VibroTacInvalidParameterException;
import com.sensodrive.vibrotac.api.exceptions.VibroTacMalformedResponseException;
import com.sensodrive.vibrotac.api.exceptions.VibroTacNoConnectionEstablishedException;
import com.sensodrive.vibrotac.api.exceptions.VibroTacNoConnectionException;
import com.sensodrive.vibrotac.api.exceptions.VibroTacWrongLrcResponseException;
import com.sensodrive.vibrotac.api.exceptions.VibrotacTimeoutResponseException;
import com.sensodrive.vibrotac.api.IVibroTacBatteryStatus;
import com.sensodrive.vibrotac.api.IVibroTacDevice;
import com.sensodrive.vibrotac.api.IVibroTacFirmwareVersion;
import com.sensodrive.vibrotac.api.IVibroTacStatusInformation;
import com.sensodrive.vibrotac.api.VibroTacCmdParamDO;
import com.sensodrive.vibrotac.api.VibroTacLogListener;


/**
 * Implementation of {@link IVibroTacDevice}.
 *
 * @author jtymoszuk
 * @see IVibroTacDevice
 */
public class VibroTacDevice implements IVibroTacDevice {

    /**
     * Tag for logging.
     */
    private static final String TAG = VibroTacDevice.class.getSimpleName();


    /**
     * Number of retries whene waiting for data in input buffer.
     */
    private static final int DEFAULT_RETRY_COUNT_WHEN_WAITING_FOR_ANSWER = 200;

    /**
     * Timeout after no data were found in input buffer.
     */
    private static final int DEVICE_ANSWER_TIMEOUT_MILLIS = 10;

    /**
     * Number of retries when sending a command to device.
     */
    private static final int DEFAULT_RETRY_COUNT_FOR_SENDING_COMMAND = 5;
    /**
     * All received bytes are firstly XOR with this byte. For transmit are all bytes XOR with this byte too.
     */
    private static byte XOR_BYTE = 0x2b;
    /**
     * Start byte of the sent/received message.
     */
    private static final byte SOF = 0;
    /**
     * Last byte of the sent/received message.
     */
    private static final byte EOF = 0;

    private static final byte COMMAND_CONTROL_BASIC = 0x41;
    private static final byte COMMAND_CONTROL_MULTI = 0x42;

    private static final byte COMMAND_READ_DEVICE_STATUS = 0x4C;
    private static final byte COMMAND_CLEAR_DEVICE_STATUS = 0x0F;
    private static final int COMMAND_READ_DEVICE_STATUS_RESPONSE_LENGTH = 11;

    private static final int COMMAND_ACKNOWLEDGE_RESPONSE_LENGTH = 8;

    // TODO Phase 3 Methods
    // private static final byte COMMAND_READ_HARDWARE_INFO = 0x4A;
    private static final byte COMMAND_READ_BATERRY_STATE = 0x4B;
    private static final int COMMAND_READ_BATERRY_STATE_RESPONSE_LENGTH = 7;

    private static final byte COMMAND_READ_CONTROL_MODULE_FIRMWARE_REVISION = 0x4D;
    private static final int COMMAND_READ_CONTROL_MODULE_FIRMWARE_REVISION_RESPONSE_LENGTH = 15;
    /**
     * Number of bytes to be added in order to created send message.
     */
    private static final int SEND_MESSAGE_ADDITIONAL_BYTES = 4;

    private static String macAddress = "";

    private List<VibroTacLogListener> logListeners = new ArrayList<VibroTacLogListener>();

    IVibroTacFirmwareVersion firmwareVersion = null;

    /**
     * Socket
     */
    private volatile BluetoothSocket mSocket = null;

    /**
     * Output stream
     */
    private OutputStream bluetoothOut;

    /**
     * Input stream
     */
    private InputStream bluetoothIn;

    private void checkConnection() throws VibroTacNoConnectionException {
        if ((this.bluetoothIn == null) || (this.bluetoothOut == null)) {
            throw new VibroTacNoConnectionException();
        }
    }


    /**
     * Find and return bluetooth device with defined MAC address.
     *
     * @param bluetoothAdapter Bluetooth adapter
     * @param remoteDeviceMac  MAC address of VibroTac
     * @return null if no VibroTac was found.
     */
    private BluetoothDevice findDevice(final BluetoothAdapter bluetoothAdapter,
                                       final String remoteDeviceMac) {
        BluetoothDevice theDevice = null;

        if ((remoteDeviceMac != null) && !remoteDeviceMac.isEmpty()) {
            final Set<BluetoothDevice> pairedDevices = bluetoothAdapter
                    .getBondedDevices();

            if (pairedDevices.size() > 0) {
                // Loop through paired devices
                for (final BluetoothDevice device : pairedDevices) {
                    Log.d(TAG, "Paired device: " + device.getName() + " - "
                            + device.getAddress());
                    if (remoteDeviceMac.equalsIgnoreCase(device.getAddress())) {
                        Log.i(TAG, "Found device: " + device.getName() + " - "
                                + device.getAddress());
                        theDevice = device;
                        break;
                    }
                }
            }
        }
        return theDevice;
    }

    @Override
    public synchronized void connectBluetooth(final String macAddress) throws VibroTacIOException, VibroTacNoConnectionEstablishedException, VibroTacInvalidParameterException {
        this.macAddress = macAddress;
        if (!isMacAddressValid(this.macAddress)) {
            throw new VibroTacInvalidParameterException("Invalid MAC address of Vibrotac device(" + macAddress + ")!");
        }

        // prepare thread bluetooth communication
        disconnect();
        final BluetoothAdapter bluetoothAdapter = BluetoothAdapter
                .getDefaultAdapter();
        final BluetoothDevice theDevice = findDevice(bluetoothAdapter,
                macAddress);
        bluetoothAdapter.cancelDiscovery();

        if (theDevice == null) {
            throw new VibroTacNoConnectionEstablishedException();
        }

        try {
            mSocket = (BluetoothSocket) theDevice.getClass().getMethod("createInsecureRfcommSocket", new Class[]{int.class}).invoke(theDevice, 1);
        } catch (IllegalAccessException e) {
            Log.e(TAG, e.getMessage());
        } catch (InvocationTargetException e) {
            Log.e(TAG, e.getMessage());
        } catch (NoSuchMethodException e) {
            Log.e(TAG, e.getMessage());
        }

        if (mSocket == null) {
            throw new VibroTacNoConnectionEstablishedException();
        }
        try {
            connectToDevice(mSocket);
            initStreams(mSocket);

            try {
                this.firmwareVersion = requestControlModuleFirmwareRevision();
            } catch (InterruptedException | VibroTacException e) {
                throw new VibroTacNoConnectionEstablishedException();
            }
        } catch (IOException e) {
            throw new VibroTacIOException(e);
        }
    }


    /**
     * Initializes streams from socket.
     *
     * @throws VibroTacIOException in case of IO problems
     * @throws VibroTacException   in case of VibroTacException
     */
    private void initStreams(BluetoothSocket socket) throws IOException, VibroTacNoConnectionEstablishedException {
        this.bluetoothOut = socket.getOutputStream();
        this.bluetoothIn = socket.getInputStream();
        if ((this.bluetoothOut == null) || (this.bluetoothIn == null)) {
            throw new VibroTacNoConnectionEstablishedException();
        }
    }

    @Override
    public synchronized void disconnect() throws VibroTacIOException {
        this.firmwareVersion = null;
        try {
            if (bluetoothIn != null) {
                bluetoothIn.close();
                bluetoothIn = null;
            }
            if (bluetoothOut != null) {
                bluetoothOut.close();
                bluetoothOut = null;
            }
        } catch (IOException e) {
            throw new VibroTacIOException(e);
        }

        if (mSocket != null) {
            Log.i(TAG,
                    "Socket connection is there - trying to close the connection...");
            try {
                mSocket.close();

            } catch (final IOException e) {
                Log.e(TAG, "IO Exception caught during socket closure", e);
                mSocket = null;
                throw new VibroTacIOException(e);
            }
            mSocket = null;
        }
    }

    /**
     * Connects to blutooth socket
     *
     * @param bluetoothSocket Bluetooth socket.
     * @throws IOException in case of IO problems
     */
    private synchronized void connectToDevice(BluetoothSocket bluetoothSocket) throws IOException {
        try {
            bluetoothSocket.connect();
        } catch (final IOException e) {
            Log.e(TAG, "IOException:oo " + e);
        }
    }


    /**
     * Returns whether input parameter mac has valid format.
     *
     * @param mac MAC address of Vibrotac to be checked.
     * @return true if mac address is valid, otherwise false.
     */
    private boolean isMacAddressValid(String mac) {
        boolean result = false;
        if (mac != null) {
            if (mac.length() == 17) {
                if ((mac.charAt(2) == ':') && (mac.charAt(5) == ':') && (mac.charAt(8) == ':') && (mac.charAt(11) == ':') && (mac.charAt(14) == ':')) {
                    result = true;
                    String tmp = mac.replace(":", "");
                    for (char c : tmp.toCharArray()) {
                        if (!(((c >= '0') && (c <= '9')) || ((c >= 'a') && (c <= 'f') || ((c >= 'A') && (c <= 'F'))))) {
                            return false;
                        }
                    }
                }
            }
        }
        return result;
    }


    @Override
    public synchronized void requestClearDeviceStatusInfo()
            throws VibroTacIOException, InterruptedException, VibroTacNoConnectionException, VibroTacWrongLrcResponseException, VibrotacTimeoutResponseException, VibroTacMalformedResponseException {
        final byte[] message = new byte[2];
        message[0] = COMMAND_READ_DEVICE_STATUS;
        message[1] = COMMAND_CLEAR_DEVICE_STATUS;

        try {
            sendMessageAndReadAnswer(message, COMMAND_READ_DEVICE_STATUS_RESPONSE_LENGTH);
        } catch (VibroTacIOException e) {
            Log.e(TAG,
                    "Couldn't acquire device status information"
                            + e.getMessage(), e);
            throw e;
        } catch (InterruptedException e) {
            Log.e(TAG,
                    "Couldn't acquire device status information"
                            + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public IVibroTacFirmwareVersion getControlModuleFirmwareRevision() throws VibroTacNoConnectionException {
        if (this.firmwareVersion == null) {
            throw new VibroTacNoConnectionException();
        }
        return this.firmwareVersion;
    }

    /**
     * Request firmware version from the VibroTac.
     *
     * @return VibroTac firmware version.
     * @throws VibroTacIOException                in case of IO problems.
     * @throws InterruptedException               in case of execution thread was interrupted
     * @throws VibroTacNoConnectionException      in case of no connection with VibroTac
     * @throws VibroTacWrongLrcResponseException  in case of wrong received LRC
     * @throws VibrotacTimeoutResponseException   in case of timeout while waiting for the whole message.
     * @throws VibroTacMalformedResponseException in case of received unexpected response like message ID or message length.
     */
    private synchronized IVibroTacFirmwareVersion requestControlModuleFirmwareRevision() throws InterruptedException, VibrotacTimeoutResponseException, VibroTacIOException, VibroTacMalformedResponseException, VibroTacNoConnectionException, VibroTacWrongLrcResponseException {
        if (firmwareVersion != null) {
            return this.firmwareVersion;
        }

        final byte[] message = new byte[1];
        message[0] = COMMAND_READ_CONTROL_MODULE_FIRMWARE_REVISION;

        final byte[] answer;
        try {
            answer = sendMessageAndReadAnswer(message, COMMAND_READ_CONTROL_MODULE_FIRMWARE_REVISION_RESPONSE_LENGTH);
            return new VibroTacVersion(answer);
        } catch (InterruptedException | VibroTacException e) {
            Log.e(TAG,
                    "Couldn't acquire module firmware information"
                            + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public synchronized IVibroTacBatteryStatus requestBatteryStatus() throws VibroTacIOException, InterruptedException, VibroTacNoConnectionException, VibroTacWrongLrcResponseException, VibroTacMalformedResponseException, VibrotacTimeoutResponseException {
        final byte[] message = new byte[1];
        message[0] = COMMAND_READ_BATERRY_STATE;
        final byte[] answer = sendMessageAndReadAnswer(message, COMMAND_READ_BATERRY_STATE_RESPONSE_LENGTH);
        VibroTacBatteryStatus batteryStatus = new VibroTacBatteryStatus(answer);
        Log.d(TAG, "requestBatteryStatus: charge=" + batteryStatus.getCharge() + "; charging status=" + batteryStatus.getState());
        return batteryStatus;
    }

    @Override
    public synchronized IVibroTacStatusInformation requestDeviceStatusInfo()
            throws VibroTacIOException, InterruptedException, VibroTacNoConnectionException, VibroTacWrongLrcResponseException, VibroTacMalformedResponseException, VibrotacTimeoutResponseException {

        final VibroTacStatusInformation result;

        final byte[] message = new byte[2];
        message[0] = COMMAND_READ_DEVICE_STATUS;
        // Request status/warnings/error
        message[1] = 0;

        try {
            final byte[] answer = sendMessageAndReadAnswer(message, COMMAND_READ_DEVICE_STATUS_RESPONSE_LENGTH);
            result = new VibroTacStatusInformation(answer);

            Log.d(TAG, "requestDeviceStatusInfo -  parsing answer: "
                    + byteArrayToHex(answer));

        } catch (VibroTacException | InterruptedException e) {
            Log.e(TAG,
                    "Couldn't acquire device status information: "
                            + e.getMessage(), e);
            throw e;
        }
        return result;
    }

    @Override
    public synchronized void sendBasicModuleControl(final int moduleAddress, final int intensity)
            throws VibroTacIOException, InterruptedException, VibroTacNoConnectionException, VibroTacWrongLrcResponseException, VibrotacTimeoutResponseException, VibroTacMalformedResponseException, VibroTacCommandNotAcknowledgedException {

        final byte[] message = new byte[3];
        message[0] = COMMAND_CONTROL_BASIC;
        message[1] = (byte) moduleAddress;
        message[2] = (byte) intensity;

        if (!sendMessageAndCheckForStatus(message)) {
            throw new VibroTacCommandNotAcknowledgedException("Sent command wasn´t acknowledged.");
        }
    }

    @Override
    public synchronized void sendMultiModuleControl(
            final Map<Integer, VibroTacCmdParamDO> motorCommandMap)
            throws VibroTacIOException, InterruptedException, VibroTacNoConnectionException, VibroTacWrongLrcResponseException, VibrotacTimeoutResponseException, VibroTacMalformedResponseException, VibroTacCommandNotAcknowledgedException {

        final byte[] message = new byte[2 + (motorCommandMap.size() * 4)];

        message[0] = COMMAND_CONTROL_MULTI;
        message[1] = (byte) motorCommandMap.size();

        int i = 0;
        for (final Map.Entry<Integer, VibroTacCmdParamDO> entry : motorCommandMap
                .entrySet()) {
            message[2 + i] = entry.getKey().byteValue();
            message[3 + i] = entry.getValue().getIntensity();

            short duration = entry.getValue().getDuration();
            message[4 + i] = (byte) (duration & 0xff);
            message[5 + i] = (byte) ((duration >> 8) & 0xff);
            i += 4;
        }

        if (Thread.currentThread().isInterrupted()) {
            throw new InterruptedException();
        }
        if (!sendMessageAndCheckForStatus(message)) {
            throw new VibroTacCommandNotAcknowledgedException("Sent command wasn´t acknowledged.");
        }
    }

    @Override
    public synchronized void stopAll() throws VibroTacIOException,
            InterruptedException, VibroTacNoConnectionException, VibroTacWrongLrcResponseException, VibrotacTimeoutResponseException, VibroTacMalformedResponseException, VibroTacCommandNotAcknowledgedException {
        sendBasicModuleControl(0xFF, 0);
    }

    /**
     * Writes message to output and checks for answer
     *
     * @param message The message
     * @return true if successful, otherwise false.
     * @throws VibroTacIOException  in case of IO problems
     * @throws InterruptedException in case of execution thread was interrupted
     */
    private boolean sendMessageAndCheckForStatus(final byte[] message)
            throws VibroTacIOException, InterruptedException, VibroTacNoConnectionException, VibroTacWrongLrcResponseException, VibrotacTimeoutResponseException, VibroTacMalformedResponseException {
        Log.d(TAG, "Pure message: " + message);
        final byte[] command = encodeMessage(message);
        Log.d(TAG, "Message in frame (incl, stuffing): " + command);
        checkConnection();
        emptyInputBuffer();
        try {
            bluetoothOut.write(command);
            fireAddLogByAllListeners(byteArrayToHex(command), true);
            waitForDataAndThrowExceptionIfNotAvailable(COMMAND_ACKNOWLEDGE_RESPONSE_LENGTH);
            return checkForStatus();
        } catch (IOException e) {
            throw new VibroTacIOException(e);
        }
    }

    /**
     * Writes message to output and reads information from device
     *
     * @throws InterruptedException in case of thread was interrupted.
     */
    private byte[] sendMessageAndReadAnswer(byte[] message, int expectedRcvLength)
            throws VibroTacIOException, InterruptedException, VibroTacNoConnectionException, VibroTacWrongLrcResponseException, VibroTacMalformedResponseException, VibrotacTimeoutResponseException {
        checkConnection();
        final byte[] command = encodeMessage(message);
        emptyInputBuffer();

        try {
            bluetoothOut.write(command);
            fireAddLogByAllListeners(byteArrayToHex(command), true);
            byte[] rcvBytes = readFromInpuStream(expectedRcvLength);

            // check ID.
            if (command[2] != rcvBytes[2]) {
                throw new VibroTacMalformedResponseException("Received message has wrong ID.");
            }

            if (rcvBytes.length != expectedRcvLength) {
                throw new VibroTacMalformedResponseException("Received message has wrong length.");
            }
            return decodeMessage(rcvBytes);
        } catch (IOException e) {
            throw new VibroTacIOException(e);
        }
    }


    /**
     * Checks for answer from device
     *
     * @throws IOException          in case IO problems
     * @throws InterruptedException in case of thread was interrupted.
     */
    private boolean checkForStatus() throws IOException, InterruptedException, VibroTacNoConnectionException, VibroTacWrongLrcResponseException, VibrotacTimeoutResponseException, VibroTacMalformedResponseException {
        byte[] inputBuffer = null;

        inputBuffer = readFromInpuStream(8);

        final byte[] decodedBytes = decodeMessage(inputBuffer);

        if (('A' == (char) decodedBytes[1]) && ('C' == (char) decodedBytes[2])
                && ('K' == (char) decodedBytes[3])) {
            Log.d(TAG, "Answer from VibroTac is OK, "
                    + byteArrayToHex(decodedBytes) + " - returning true");
            return true;
        } else {
            Log.w(TAG, "Answer from VibroTac is NOT OK, "
                    + byteArrayToHex(decodedBytes)
                    + " - adding message and returning false");
            return false;
        }
    }

    /**
     * Checks SOF and EOF by the received message.
     *
     * @param message
     * @throws VibroTacMalformedResponseException in case First bit is not SOF or last bit is not EOF.
     */
    private void checkSofAndEofByRcvMessage(byte[] message) throws VibroTacMalformedResponseException {
        if (message[0] != (SOF ^ XOR_BYTE)) {
            throw new VibroTacMalformedResponseException("Wrong start bit received in the message. ");
        }
        if (message[message.length - 1] != (EOF ^ XOR_BYTE)) {
            throw new VibroTacMalformedResponseException("Wrong end bit received in the message. ");
        }
    }

    /**
     * Reads from input stream.
     *
     * @return read data
     * @throws IOException          in case of IO problems
     * @throws InterruptedException in case of of thread was interrupted.
     */
    private byte[] readFromInpuStream(int messageLength)
            throws IOException, InterruptedException, VibroTacNoConnectionException, VibrotacTimeoutResponseException, VibroTacMalformedResponseException {

        final byte[] buffer = new byte[125];

        checkConnection();

        readByte((byte) (SOF ^ XOR_BYTE));
        buffer[0] = (byte) (SOF ^ XOR_BYTE);

        int numberOfLastReadByte = 0;

        final int available = waitForDataAndThrowExceptionIfNotAvailable(messageLength - 1);
        if ((available) > buffer.length) {
            throw new IOException(
                    "Input receive buffer overflown.");
        }
        numberOfLastReadByte = bluetoothIn.read(buffer, 1, messageLength - 1);

        if (numberOfLastReadByte == -1) {
            throw new IOException(
                    "Although data was available, we've reached the end of the stream");
        }

        byte[] result = new byte[messageLength];
        System.arraycopy(buffer, 0, result, 0, result.length);
        fireAddLogByAllListeners(byteArrayToHex(result), false);
        checkSofAndEofByRcvMessage(result);
        return result;
    }

    /**
     * Empty the input buffer.
     *
     * @throws VibroTacNoConnectionException in case of no connection with VibroTac
     */
    private void emptyInputBuffer() throws VibroTacNoConnectionException {
        checkConnection();

        try {
            final byte[] buffer = new byte[125];
            int available = bluetoothIn.available();
            while (available > 0) {
                if (available > 125) {
                    bluetoothIn.read(buffer, 0, 125);
                } else {
                    bluetoothIn.read(buffer, 0, available);
                }
                available = bluetoothIn.available();
            }
        } catch (IOException e) {
            //ignore - is not important for this purpose
        }
    }

    /**
     * Reads data from bluetooth stream until the required byte is received.
     *
     * @param wantedByte Expected byte.
     * @throws IOException
     * @throws InterruptedException
     * @throws VibroTacException
     */
    private void readByte(final byte wantedByte)
            throws IOException, InterruptedException, VibroTacNoConnectionException, VibrotacTimeoutResponseException {

        checkConnection();

        while (waitForDataAndThrowExceptionIfNotAvailable(1) > 0) {
            final int readByte = bluetoothIn.read();
            Log.v(TAG, "readByte=" + readByte);
            if (readByte == -1) {
                throw new IOException(
                        "Although data was available, we've reached the end of the stream");
            }

            if (wantedByte == (byte) readByte) {
                break;
            }
        }
    }

    /**
     * Wait for specified number of bytes in input streams.
     *
     * @param count Number of bytes to wait for.
     * @return number of bytes in input stream.
     * @throws IOException
     * @throws InterruptedException
     * @throws VibroTacNoConnectionException
     * @throws VibrotacTimeoutResponseException
     */
    private int waitForDataAndThrowExceptionIfNotAvailable(int count)
            throws IOException, InterruptedException, VibroTacNoConnectionException, VibrotacTimeoutResponseException {

        checkConnection();
        for (int i = 0; i < 50; i++) {
            final int available = bluetoothIn.available();
            if (available >= count) {
                Log.d(TAG,
                        "waitForDataAndThrowExceptionIfNotAvailable.available="
                                + available + ", iteration=" + i);
                return available;
            }
            Thread.sleep(DEVICE_ANSWER_TIMEOUT_MILLIS);
        }
        throw new VibrotacTimeoutResponseException();
    }

    private String byteArrayToHex(final byte[] byteArray) {
        return byteArrayToHex(byteArray, false);
    }

    /**
     * Converts byte array into hex. <br>
     * Example: <br>
     * Input 0x55,0x45,0x33<br>
     * Output "0x55","0x45","0x33"
     *
     * @param byteArray
     * @param ignoreFirstByte
     * @return
     */
    private String byteArrayToHex(final byte[] byteArray,
                                  final boolean ignoreFirstByte) {

        final StringBuilder sb = new StringBuilder();

        final int startIndex = ignoreFirstByte ? 2 : 1;

        for (int i = startIndex; i <= byteArray.length; i++) {
            sb.append(String.format("%02x", byteArray[i - 1]));
            if (i < byteArray.length) {
                sb.append(" ");
            }
        }

        return sb.toString();
    }


    /**
     * Sends new log record to all registered Log listeners.
     *
     * @param message message to be added as record.
     */
    private void fireAddLogByAllListeners(final String message, final boolean isMessageSent) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (VibroTacLogListener logListener : logListeners) {
                    logListener.addLogEntry(message, isMessageSent);
                }
            }
        });
        thread.start();
    }

    /**
     * Encodes message to be sent. <br>  Firstly is LRC computed and added to message at the end.
     * Then it is encoded into COBS. SOF and EOF is added and the end all bytes are with XOR computed.
     *
     * @param message Message to be encoded.
     * @return encoded message.
     */
    private byte[] encodeMessage(byte[] message) {
        int totalLength = message.length + SEND_MESSAGE_ADDITIONAL_BYTES;
        byte[] result = new byte[totalLength];
        // For cobs is message and LRC simultaneously
        byte[] bytesForCobs = new byte[message.length + 1];
        System.arraycopy(message, 0, bytesForCobs, 0, message.length);
        bytesForCobs[bytesForCobs.length - 1] = (byte) calcLRC(message);
        byte[] encodedBytes = Cobs.encodeCobs(bytesForCobs);
        System.arraycopy(encodedBytes, 0, result, 1, encodedBytes.length);
        result[0] = SOF;
        result[result.length - 1] = EOF;
        return xorAllBytes(result, XOR_BYTE);
    }

    /**
     * Decodes received message.<br>
     * All bytes are with XOR computed. Then First and last byte is removed.
     * After that is message with cobs decoded and LRC is removed.
     *
     * @param message Received message
     * @return decoded received message.
     */
    private byte[] decodeMessage(byte[] message) throws VibroTacWrongLrcResponseException {
        int totalLength = message.length - SEND_MESSAGE_ADDITIONAL_BYTES;
        byte[] result = new byte[totalLength];
        byte[] tmp = xorAllBytes(message, XOR_BYTE);
        tmp = removeFirstAndLastByte(tmp);
        tmp = Cobs.decodeCobs(tmp);
        byte[] dataForLrc = new byte[tmp.length - 1];
        System.arraycopy(tmp, 0, dataForLrc, 0, dataForLrc.length);
        int computedLrc = calcLRC(dataForLrc);
        if (computedLrc != tmp[tmp.length - 1]) {
            throw new VibroTacWrongLrcResponseException();
        }
        System.arraycopy(tmp, 0, result, 0, result.length);
        return result;
    }

    /**
     * Removed first and last byte from the byte array.
     *
     * @param bytes Byte array where the first and last byte will be removed.
     * @return Adjusted byte array.
     */
    private byte[] removeFirstAndLastByte(byte[] bytes) {
        byte[] result = new byte[bytes.length - 2];
        System.arraycopy(bytes, 1, result, 0, result.length);
        return result;
    }

    /**
     * Does XOR operation by all bytes.
     *
     * @param bytes   Bytes to be adjusted with XOR operation
     * @param xorByte byte for XOR operation.
     * @return Adjusted bytes according to XOR operation.
     */
    private byte[] xorAllBytes(byte[] bytes, byte xorByte) {
        byte[] result = new byte[bytes.length];
        System.arraycopy(bytes, 0, result, 0, bytes.length);
        for (int i = 0; i < result.length; i++) {
            result[i] ^= xorByte;
        }
        return result;
    }

    @Override
    public synchronized void addLogListener(VibroTacLogListener listener) {
        if (listener != null) {
            this.logListeners.add(listener);
        }
    }

    @Override
    public synchronized void removeLogListener(VibroTacLogListener listener) {
        this.logListeners.remove(listener);
    }

    /**
     * Computes LRC from the specified bytes.
     *
     * @param data Data from which the LRC will be calculated.
     * @return calculated LRC.
     */
    private static byte calcLRC(final byte[] data) {
        int checksum = 0;
        for (int i = 0; i <= (data.length - 1); i++) {
            checksum = (checksum + data[i]) & 0xFF;
        }
        checksum = ((checksum ^ 0xFF) + 1) & 0xFF;
        byte result = (byte) checksum;
        return result;
    }
}
