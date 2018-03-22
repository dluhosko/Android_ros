/*///////////////////////////////////////////////////////////////////////////
//                                      ###################################
//                                     #########                    ########
//                                      ##################          ########
//                                                #########         ########
//                                     ###################   ##############
//                                                www.sensodrive.de
//
//  File:           IVibroTacFirmwareVersion.java
//  Project:        VibroTac Android API
//  Originator:     Petr Maslak
//  Copyright:      SENSODRIVE GmbH, Wessling
//
//      $LastChangedDate: 2017-09-08 08:36:19 +0200 (Fr, 08. Sep 2017) $
//        $LastChangedBy: geist $
//  $LastChangedRevision: 609 $
//
/////////////////////////////////////////////////////////////////////////////
//  $Id: IVibroTacFirmwareVersion.java 609 2017-09-08 06:36:19Z geist $
///////////////////////////////////////////////////////////////////////////*/

package com.sensodrive.vibrotac.api;

/**
 * Provides firmware version information of the VibroTac.
 */

public interface IVibroTacFirmwareVersion {
    /**
     * Returns major version of the firmware.
     *
     * @return major version of the firmware.
     */
    int getMajor();

    /**
     * Returns minor version of the firmware.
     *
     * @return minor version of the firmware.
     */
    int getMinor();

    /**
     * Return  point version of the firmware.
     *
     * @return point version of the firmware.
     */
    int getPoint();

    /**
     * Returns revision of the firmware.
     *
     * @return revision of the firmware.
     */
    int getRevision();

    /**
     * Returns whole firmware version: <br>
     * Format: major.minor.point.revision.
     * Example: 1.0.1.1056
     *
     * @return whole firmware version
     */
    String getAsString();
}
