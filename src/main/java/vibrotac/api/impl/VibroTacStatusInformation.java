/*///////////////////////////////////////////////////////////////////////////
//                                      ###################################
//                                     #########                    ########
//                                      ##################          ########
//                                                #########         ########
//                                     ###################   ##############
//                                                www.sensodrive.de
//
//  File:           VibroTacStatusInformation.java
//  Project:        VibroTac Android API
//  Originator:     Petr Maslak
//  Copyright:      SENSODRIVE GmbH, Wessling
//
//      $LastChangedDate: 2017-09-08 08:36:19 +0200 (Fr, 08. Sep 2017) $
//        $LastChangedBy: geist $
//  $LastChangedRevision: 609 $
//
/////////////////////////////////////////////////////////////////////////////
//  $Id: VibroTacStatusInformation.java 609 2017-09-08 06:36:19Z geist $
///////////////////////////////////////////////////////////////////////////*/

package com.sensodrive.vibrotac.api.impl;

import com.sensodrive.vibrotac.api.IVibroTacStatusInformation;
import com.sensodrive.vibrotac.api.VibroTacErrorsAndWarningsEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of {@link IVibroTacStatusInformation}.
 */
public class VibroTacStatusInformation implements IVibroTacStatusInformation {

    byte[] status = new byte[2];
    byte[] warnings = new byte[2];
    byte[] errors = new byte[2];


    /**
     * Default constructor.
     *
     * @param receivedVibrotacStatus Received bytes from Vibrotac
     */
    VibroTacStatusInformation(byte[] receivedVibrotacStatus) {
        if (receivedVibrotacStatus.length > 5) {
            status[0] = receivedVibrotacStatus[0];
            status[1] = receivedVibrotacStatus[1];
            warnings[0] = receivedVibrotacStatus[2];
            warnings[1] = receivedVibrotacStatus[3];
            errors[0] = receivedVibrotacStatus[4];
            errors[1] = receivedVibrotacStatus[5];
        }
    }


    public boolean isBatteryLow() {
        return ((warnings[0] & (0x01)) > 0);
    }


    public boolean isUnknownCommandByte() {
        return ((warnings[0] & (0x02)) > 0);
    }


    public boolean isInvalidChecksumOfMalformedMessage() {
        return ((warnings[0] & (0x04)) > 0);
    }


    public boolean isInvalidVibrationIntensity() {
        return ((warnings[0] & (0x08)) > 0);
    }


    public boolean isInvalidModuleAddress() {
        return ((warnings[0] & (0x10)) > 0);
    }


    public boolean isFailedMotorModuleInitialization() {
        return (((errors[1] & (0x01)) > 0) && (errors[0] == 0));
    }


    public List<VibroTacErrorsAndWarningsEnum> getAllWarnings() {
        List<VibroTacErrorsAndWarningsEnum> result = new ArrayList<VibroTacErrorsAndWarningsEnum>();
        if (isBatteryLow()) {
            result.add(VibroTacErrorsAndWarningsEnum.WARNING_BATTERY_LOW);
        }
        if (isUnknownCommandByte()) {
            result.add(VibroTacErrorsAndWarningsEnum.WARNING_UNKNOWN_COMMAND_BYTE);
        }
        if (isInvalidChecksumOfMalformedMessage()) {
            result.add(VibroTacErrorsAndWarningsEnum.WARNING_INVALID_CHECKSUM_OR_MALFORMED_MESSAGE);
        }
        if (isInvalidVibrationIntensity()) {
            result.add(VibroTacErrorsAndWarningsEnum.WARNING_INVALID_VIBRATION_INTENSITY);
        }
        if (isInvalidModuleAddress()) {
            result.add(VibroTacErrorsAndWarningsEnum.WARNING_INVALID_MODULE_ADDRESS);
        }
        return result;
    }


    public List<VibroTacErrorsAndWarningsEnum> getAllErrors() {
        List<VibroTacErrorsAndWarningsEnum> result = new ArrayList<VibroTacErrorsAndWarningsEnum>();
        if (isFailedMotorModuleInitialization()) {
            result.add(VibroTacErrorsAndWarningsEnum.ERROR_MOTOR_MODULE_INIT_FAILED);
        }
        return result;
    }


    public List<VibroTacErrorsAndWarningsEnum> getAllErrorsAndWarnings() {
        List<VibroTacErrorsAndWarningsEnum> result = new ArrayList<VibroTacErrorsAndWarningsEnum>();
        result.addAll(getAllErrors());
        result.addAll(getAllWarnings());
        return result;
    }

    public int getCountOfErrors() {
        return getAllErrors().size();
    }

    public int getCountOfWarnings() {
        return getAllWarnings().size();
    }


    public int getCountOfErrorsAndWarnings() {
        return getAllErrors().size();
    }
}
