/*///////////////////////////////////////////////////////////////////////////
//                                      ###################################
//                                     #########                    ########
//                                      ##################          ########
//                                                #########         ########
//                                     ###################   ##############
//                                                www.sensodrive.de
//
//  File:           VibroTacBatteryStatus.java
//  Project:        VibroTac Android API
//  Originator:     Petr Maslak
//  Copyright:      SENSODRIVE GmbH, Wessling
//
//      $LastChangedDate: 2017-09-08 08:36:19 +0200 (Fr, 08. Sep 2017) $
//        $LastChangedBy: geist $
//  $LastChangedRevision: 609 $
//
/////////////////////////////////////////////////////////////////////////////
//  $Id: VibroTacBatteryStatus.java 609 2017-09-08 06:36:19Z geist $
///////////////////////////////////////////////////////////////////////////*/

package com.sensodrive.vibrotac.api.impl;

import com.sensodrive.vibrotac.api.BatteryChargingStateEnum;
import com.sensodrive.vibrotac.api.IVibroTacBatteryStatus;

/**
 * Implementation of {@link IVibroTacBatteryStatus}.
 */

public class VibroTacBatteryStatus implements IVibroTacBatteryStatus {
    /**
     * Battery charge (0-100)
     */
    private final int charge;
    /**
     * Battery charging state.
     */
    private BatteryChargingStateEnum state;

    /**
     * Constructor.
     *
     * @param receivedBytes Received bytes from VibroTac.
     */
    VibroTacBatteryStatus(byte[] receivedBytes) {
        this.charge = receivedBytes[1];
        for (BatteryChargingStateEnum state : BatteryChargingStateEnum.class.getEnumConstants()) {
            if (receivedBytes[2] == state.getStatus()) {
                this.state = state;
                return;
            }
        }
        state = BatteryChargingStateEnum.CHARGING_UNKNOWN;
    }

    /**
     * Cosntructor.
     *
     * @param charge Battery charge.
     * @param state  Battery charging state.
     */
    public VibroTacBatteryStatus(int charge, BatteryChargingStateEnum state) {
        this.charge = charge;
        this.state = state;
    }

    @Override
    public int getCharge() {
        return charge;
    }

    @Override
    public BatteryChargingStateEnum getState() {
        return state;
    }
}
