/*///////////////////////////////////////////////////////////////////////////
//                                      ###################################
//                                     #########                    ########
//                                      ##################          ########
//                                                #########         ########
//                                     ###################   ##############
//                                                www.sensodrive.de
//
//  File:           IVibroTacDevice.java
//  Project:        VibroTac Android API
//  Originator:     jtymoszuk
//  Copyright:      SENSODRIVE GmbH, Wessling
//
//      $LastChangedDate: 2017-09-08 08:36:19 +0200 (Fr, 08. Sep 2017) $
//        $LastChangedBy: geist $
//  $LastChangedRevision: 609 $
//
/////////////////////////////////////////////////////////////////////////////
//  $Id: IVibroTacDevice.java 609 2017-09-08 06:36:19Z geist $
///////////////////////////////////////////////////////////////////////////*/

package com.sensodrive.vibrotac.api;

import com.sensodrive.vibrotac.api.exceptions.VibroTacCommandNotAcknowledgedException;
import com.sensodrive.vibrotac.api.exceptions.VibroTacIOException;
import com.sensodrive.vibrotac.api.exceptions.VibroTacInvalidParameterException;
import com.sensodrive.vibrotac.api.exceptions.VibroTacMalformedResponseException;
import com.sensodrive.vibrotac.api.exceptions.VibroTacNoConnectionEstablishedException;
import com.sensodrive.vibrotac.api.exceptions.VibroTacNoConnectionException;
import com.sensodrive.vibrotac.api.exceptions.VibroTacWrongLrcResponseException;
import com.sensodrive.vibrotac.api.exceptions.VibrotacTimeoutResponseException;

import java.io.IOException;
import java.util.Map;

/**
 * Representing the Vibrotac device with all its functions. <br>
 * Notice for bluetooth: VibroTac must be already paired in Android system.
 * <br><br>
 * Example of use: <br>
 * <pre><code>
 *  try {
 *      IVibroTacDevice vibroTac = new VibroTacDevice();
 *      vibroTac.connectBluetooth("33:11:aa:bb:cc:00");
 *      vibroTac.sendBasicModuleControl(1,100);  // First module will be vibrating
 *      Thread.sleep(50);
 *      vibroTac.stopAll();
 *      vibroTac.disconnect();
 *  }
 *  catch(VibroTacException e){
 *      // log
 *  }
 *  catch(InterruptedException e){
 *      // log
 *  }
 * </code></pre>
 *
 * @author jtymoszuk
 */
public interface IVibroTacDevice {
    /**
     * Establish connection with VibroTac via bluetooth. <br>
     * These 2 permissions must be added into manifest file if you are calling this method: <br>
     * &#60;uses-permission android:name="android.permission.BLUETOOTH" /&#62; <br>
     * &#60;uses-permission android:name="android.permission.BLUETOOTH_ADMIN" /&#62; <br>
     *
     * @param macAddress MAC address of bluetooth device.
     * @throws VibroTacIOException                      in case of IO problems with VibroTac.
     * @throws VibroTacNoConnectionEstablishedException in case no connection with VibroTas has been established.
     * @throws VibroTacInvalidParameterException        in case MAC address is invalid.
     */
    void connectBluetooth(final String macAddress) throws VibroTacIOException, VibroTacNoConnectionEstablishedException, VibroTacInvalidParameterException;


    /**
     * Closes connection with VibroTac, if exists.
     *
     * @throws VibroTacIOException in case of IO problems with VibroTac.
     */
    void disconnect() throws VibroTacIOException;

    /**
     * This is the basic way to control vibration of VibroTac. By specifying
     * address and intensity of a single motor module, the module will keep this
     * intensity until a new value is set. If the specified address is 0xFF, the
     * following intensity will apply to all modules of VibroTac.
     *
     * @param moduleAddress Module address (0x01 - 0xFE, 0xFF=all modules)
     * @param intensity     Intensity 0-100
     * @throws InterruptedException                    in case of execution thread was interrupted
     * @throws VibroTacIOException                     in case of IO problems with VibroTac.
     * @throws VibroTacNoConnectionException           in case of no connection with VibroTac
     * @throws VibroTacWrongLrcResponseException       in case of wrong received LRC
     * @throws VibrotacTimeoutResponseException        in case of timeout while waiting for the whole message.
     * @throws VibroTacMalformedResponseException      in case the received message has unexpected message ID or message length.
     * @throws VibroTacCommandNotAcknowledgedException in case the sent command was not acknowledged.
     */
    void sendBasicModuleControl(int moduleAddress, int intensity)
            throws VibroTacIOException, InterruptedException, VibroTacNoConnectionException, VibroTacWrongLrcResponseException, VibrotacTimeoutResponseException, VibroTacMalformedResponseException, VibroTacCommandNotAcknowledgedException;


    /**
     * This command is used to set intensities and durations for up to 10 motor
     * modules with a single frame. The first data byte in the frame indicates
     * how many modules are controlled with this frame. After that, a group of
     * four bytes for every motor module specifies the module address (1 byte),
     * intensity (1 byte) and pulse duration (2 bytes). Modules controlled with
     * this frame will start vibrating immediately after receiving this frame,
     * and will stop vibrating after the specified pulse duration has expired.
     * The unit of pulse duration is specified in milliseconds. However VibroTac
     * updates vibrations of its modules only every 10 milliseconds.
     *
     * @param motorCommandMap Map of commands for individual modules.
     * @throws InterruptedException                    in case of execution thread was interrupted
     * @throws VibroTacIOException                     in case of IO problems with VibroTac.
     * @throws VibroTacNoConnectionException           in case of no connection with VibroTac
     * @throws VibroTacWrongLrcResponseException       in case of wrong received LRC
     * @throws VibrotacTimeoutResponseException        in case of timeout while waiting for the whole message.
     * @throws VibroTacMalformedResponseException      in case the received message has unexpected message ID or message length.
     * @throws VibroTacCommandNotAcknowledgedException in case the sent command was not acknowledged.
     */
    void sendMultiModuleControl(
            Map<Integer, VibroTacCmdParamDO> motorCommandMap) throws VibroTacIOException,
            InterruptedException, VibroTacNoConnectionException, VibroTacWrongLrcResponseException, VibrotacTimeoutResponseException, VibroTacMalformedResponseException, VibroTacCommandNotAcknowledgedException;


    /**
     * Stops all motors.
     *
     * @throws InterruptedException                    in case of execution thread was interrupted
     * @throws VibroTacIOException                     in case of IO problems with VibroTac.
     * @throws VibroTacNoConnectionException           in case of no connection with VibroTac
     * @throws VibroTacWrongLrcResponseException       in case of wrong received LRC
     * @throws VibrotacTimeoutResponseException        in case of timeout while waiting for the whole message.
     * @throws VibroTacMalformedResponseException      in case the received message has unexpected message ID or message length.
     * @throws VibroTacCommandNotAcknowledgedException in case the sent command was not acknowledged.
     */
    void stopAll() throws VibroTacIOException, InterruptedException, VibroTacNoConnectionException, VibroTacWrongLrcResponseException, VibrotacTimeoutResponseException, VibroTacMalformedResponseException, VibroTacCommandNotAcknowledgedException;


    /**
     * This command requests status, warning and error information.
     *
     * @return received VibrotacStatusInformation.
     * @throws InterruptedException               in case of execution thread was interrupted
     * @throws VibroTacIOException                in case of IO problems with VibroTac.
     * @throws VibroTacNoConnectionException      in case of no connection with VibroTac
     * @throws VibroTacWrongLrcResponseException  in case of wrong received LRC
     * @throws VibrotacTimeoutResponseException   in case of timeout while waiting for the whole message.
     * @throws VibroTacMalformedResponseException in case the received message has unexpected message ID or message length.
     */
    IVibroTacStatusInformation requestDeviceStatusInfo() throws VibroTacIOException,
            InterruptedException, VibroTacNoConnectionException, VibroTacWrongLrcResponseException, VibroTacMalformedResponseException, VibrotacTimeoutResponseException;


    /**
     * This command requests a clear on status, warning and error information.
     *
     * @throws InterruptedException               in case of execution thread was interrupted
     * @throws VibroTacIOException                in case of IO problems with VibroTac.
     * @throws VibroTacNoConnectionException      in case of no connection with VibroTac
     * @throws VibroTacWrongLrcResponseException  in case of wrong received LRC
     * @throws VibrotacTimeoutResponseException   in case of timeout while waiting for the whole message.
     * @throws VibroTacMalformedResponseException in case the received message has unexpected message ID or message length.
     */
    void requestClearDeviceStatusInfo() throws VibroTacIOException,
            InterruptedException, VibroTacNoConnectionException, VibroTacWrongLrcResponseException, VibrotacTimeoutResponseException, VibroTacMalformedResponseException;


    /**
     * Requests firmware revision of the device module control.
     *
     * @return firmware revision
     * @throws VibroTacNoConnectionException in case of no connection with VibroTac
     */
    IVibroTacFirmwareVersion getControlModuleFirmwareRevision() throws VibroTacNoConnectionException;

    /**
     * This commands requests battery status.
     *
     * @return received battery status.
     * @throws InterruptedException               in case of execution thread was interrupted
     * @throws VibroTacIOException                in case of IO problems with VibroTac.
     * @throws VibroTacNoConnectionException      in case of no connection with VibroTac
     * @throws VibroTacWrongLrcResponseException  in case of wrong received LRC
     * @throws VibrotacTimeoutResponseException   in case of timeout while waiting for the whole message.
     * @throws VibroTacMalformedResponseException in case the received message has unexpected message ID or message length.
     */
    IVibroTacBatteryStatus requestBatteryStatus() throws VibroTacIOException, InterruptedException, VibroTacNoConnectionException, VibroTacWrongLrcResponseException, VibroTacMalformedResponseException, VibrotacTimeoutResponseException;


    /**
     * Adds listener as Logger.
     *
     * @param listener Listener to be added. If Listener is null, it will not be added.
     */
    void addLogListener(VibroTacLogListener listener);


    /**
     * Removes listener as Logger.
     *
     * @param listener Listener to be removed
     */
    void removeLogListener(VibroTacLogListener listener);
}
