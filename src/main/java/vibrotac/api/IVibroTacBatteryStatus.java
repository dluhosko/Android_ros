/*///////////////////////////////////////////////////////////////////////////
//                                      ###################################
//                                     #########                    ########
//                                      ##################          ########
//                                                #########         ########
//                                     ###################   ##############
//                                                www.sensodrive.de
//
//  File:           IVibroTacBatteryStatus.java
//  Project:        VibroTac Android API
//  Originator:     Petr Maslak
//  Copyright:      SENSODRIVE GmbH, Wessling
//
//      $LastChangedDate: 2017-09-08 08:36:19 +0200 (Fr, 08. Sep 2017) $
//        $LastChangedBy: geist $
//  $LastChangedRevision: 609 $
//
/////////////////////////////////////////////////////////////////////////////
//  $Id: IVibroTacBatteryStatus.java 609 2017-09-08 06:36:19Z geist $
///////////////////////////////////////////////////////////////////////////*/

package com.sensodrive.vibrotac.api;

/**
 * Contains battery status from VibroTac.
 */
public interface IVibroTacBatteryStatus {
    /**
     * Returns charge (0-100)
     *
     * @return charge (0-100)
     */
    int getCharge();

    /**
     * Returns battery charging state.
     *
     * @return battery charging state.
     */
    BatteryChargingStateEnum getState();
}
