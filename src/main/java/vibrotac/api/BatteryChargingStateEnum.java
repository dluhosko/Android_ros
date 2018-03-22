/*///////////////////////////////////////////////////////////////////////////
//                                      ###################################
//                                     #########                    ########
//                                      ##################          ########
//                                                #########         ########
//                                     ###################   ##############
//                                                www.sensodrive.de
//
//  File:           BatteryChargingStateEnum.java
//  Project:        VibroTac Android API
//  Originator:     Petr Maslak
//  Copyright:      SENSODRIVE GmbH, Wessling
//
//      $LastChangedDate: 2017-09-08 08:36:19 +0200 (Fr, 08. Sep 2017) $
//        $LastChangedBy: geist $
//  $LastChangedRevision: 609 $
//
/////////////////////////////////////////////////////////////////////////////
//  $Id: BatteryChargingStateEnum.java 609 2017-09-08 06:36:19Z geist $
///////////////////////////////////////////////////////////////////////////*/

package com.sensodrive.vibrotac.api;

/**
 * Battery charging states.
 */

public enum BatteryChargingStateEnum {
    /**
     * Battery is being charged.
     */
    CHARGING(0x05),
    /**
     * Fault during charging the battery.
     */
    CHARGING_FAULT(0x07),
    /**
     * Battery output voltage is too low.
     */
    LOW_BATTERY_OUTPUT(0x04),
    /**
     * No battery is present.
     */
    NO_BATTERY_PRESENT(0x01),
    /**
     * No input power is present.
     */
    NO_INPUT_POWER_PRESENT(0x00),
    /**
     * Charging completed.
     */
    CHARGING_COMPLETE(0x03),
    /**
     * Charging status is unknown.
     */
    CHARGING_UNKNOWN(0x00);
    private final int statusByte;

    /**
     * Constructor.
     *
     * @param statusByte Status byte.
     */
    BatteryChargingStateEnum(final int statusByte) {
        this.statusByte = statusByte;
    }

    /**
     * Returns status byte.
     *
     * @return status byte.
     */
    public byte getStatus() {
        return (byte) this.statusByte;
    }
}
