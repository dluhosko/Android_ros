/*///////////////////////////////////////////////////////////////////////////
//                                      ###################################
//                                     #########                    ########
//                                      ##################          ########
//                                                #########         ########
//                                     ###################   ##############
//                                                www.sensodrive.de
//
//  File:           VibroTacErrorsAndWarningsEnum.java
//  Project:        VibroTac Android API
//  Originator:     Petr Maslak
//  Copyright:      SENSODRIVE GmbH, Wessling
//
//      $LastChangedDate: 2017-09-08 08:36:19 +0200 (Fr, 08. Sep 2017) $
//        $LastChangedBy: geist $
//  $LastChangedRevision: 609 $
//
/////////////////////////////////////////////////////////////////////////////
//  $Id: VibroTacErrorsAndWarningsEnum.java 609 2017-09-08 06:36:19Z geist $
///////////////////////////////////////////////////////////////////////////*/

package com.sensodrive.vibrotac.api;

/**
 * Enum representing errors and warnings that can be reported by remote device
 *
 * @author maslak
 */
public enum VibroTacErrorsAndWarningsEnum {
    /**
     * Status ok.
     */
    STATUS_OK(VibroTacIncidentCategoryEnum.SUCCESS, "VibroTac: Status OK"),
    /**
     * Battery low
     */
    WARNING_BATTERY_LOW(VibroTacIncidentCategoryEnum.WARNING, "VibroTac: Unknown command byte"),
    /**
     * Unknown command byte
     */
    WARNING_UNKNOWN_COMMAND_BYTE(VibroTacIncidentCategoryEnum.WARNING, "VibroTac: Unknown command byte"),
    /**
     * Invalid checksum or malformed message
     */
    WARNING_INVALID_CHECKSUM_OR_MALFORMED_MESSAGE(VibroTacIncidentCategoryEnum.WARNING, "VibroTac: Invalid checksum or malformed message"),
    /**
     * Invalid vibration intensity
     */
    WARNING_INVALID_VIBRATION_INTENSITY(VibroTacIncidentCategoryEnum.WARNING, "VibroTac: Invalid vibration intensity"),
    /**
     * Invalid module address
     */
    WARNING_INVALID_MODULE_ADDRESS(VibroTacIncidentCategoryEnum.WARNING, "VibroTac: Invalid module address"),
    /**
     * Initialization of motor module failed
     */
    ERROR_MOTOR_MODULE_INIT_FAILED(VibroTacIncidentCategoryEnum.ERROR, "VibroTac: Motor Module initialization failed");

    private VibroTacIncidentCategoryEnum type;
    private String messageEN;

    private VibroTacErrorsAndWarningsEnum(VibroTacIncidentCategoryEnum type, String messageEN) {
        this.type = type;
        this.messageEN = messageEN;
    }


    /**
     * Returns incident category.
     *
     * @return incident category.
     */
    public VibroTacIncidentCategoryEnum getIncidentCategory() {
        return this.type;
    }

    /**
     * Returns as message in english.
     *
     * @param language reserved.
     * @return as message in english.
     */
    public String getMessage(int language) {
        String result = this.messageEN;
        return result;
    }
}
