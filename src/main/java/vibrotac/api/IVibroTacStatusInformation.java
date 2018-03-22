/*///////////////////////////////////////////////////////////////////////////
//                                      ###################################
//                                     #########                    ########
//                                      ##################          ########
//                                                #########         ########
//                                     ###################   ##############
//                                                www.sensodrive.de
//
//  File:           IVibroTacStatusInformation.java
//  Project:        VibroTac Android API
//  Originator:     Petr Maslak
//  Copyright:      SENSODRIVE GmbH, Wessling
//
//      $LastChangedDate: 2017-09-08 08:36:19 +0200 (Fr, 08. Sep 2017) $
//        $LastChangedBy: geist $
//  $LastChangedRevision: 609 $
//
/////////////////////////////////////////////////////////////////////////////
//  $Id: IVibroTacStatusInformation.java 609 2017-09-08 06:36:19Z geist $
///////////////////////////////////////////////////////////////////////////*/

package com.sensodrive.vibrotac.api;

import java.util.List;

/**
 * Provides status information of the VibroTac. Returns warnings, errors, and status bits.
 */
public interface IVibroTacStatusInformation {
    /**
     * Returns whether battery is low.
     *
     * @return true if battery from Vibrotac is low, otherwise false
     */
    boolean isBatteryLow();

    /**
     * Returns whether Vibrotac received unknown command byte.
     *
     * @return true if Vibrotac received unknown command byte, otherwise false
     */
    boolean isUnknownCommandByte();

    /**
     * Return whether checksum of malformed message was all right.
     *
     * @return true if checksum of malformed message was all right, otherwise false
     */
    boolean isInvalidChecksumOfMalformedMessage();

    /**
     * Returns whether Vibrotac has received invalid value of intensity as part of command.
     *
     * @return true if Vibrotac has received invalid value of intensity as command, otherwise false
     */
    boolean isInvalidVibrationIntensity();

    /**
     * Returns whether Vibrotac has received invalid module address as part of command.
     *
     * @return true if Vibrotac has received invalid module address as part of command, otherwise false
     */
    boolean isInvalidModuleAddress();

    /**
     * Returns whether motor module initialization failed.
     *
     * @return true if motor module initialization failed, otherwise false
     */
    boolean isFailedMotorModuleInitialization();

    /**
     * Returns List of all warnings received from Vibrotac.
     *
     * @return List of all warnings received from Vibrotac.
     */
    List<VibroTacErrorsAndWarningsEnum> getAllWarnings();

    /**
     * Returns List of all errors received from Vibrotac.
     *
     * @return List of all errors received from Vibrotac.
     */
    List<VibroTacErrorsAndWarningsEnum> getAllErrors();

    /**
     * Returns List of all warnings and errors received from Vibrotac.
     *
     * @return List of all warnings and errors received from Vibrotac.
     */
    List<VibroTacErrorsAndWarningsEnum> getAllErrorsAndWarnings();

    /**
     * Returns count of errors received from Vibrotac.
     *
     * @return count of errors received from Vibrotac.
     */
    int getCountOfErrors();

    /**
     * Returns count of warning received from Vibrotac.
     *
     * @return count of warning received from Vibrotac.
     */
    int getCountOfWarnings();

    /**
     * Returns count of all errors and warnings received from Vibrotac.
     *
     * @return count of all errors and warnings received from Vibrotac.
     */
    int getCountOfErrorsAndWarnings();
}
