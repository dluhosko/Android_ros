/*///////////////////////////////////////////////////////////////////////////
//                                      ###################################
//                                     #########                    ########
//                                      ##################          ########
//                                                #########         ########
//                                     ###################   ##############
//                                                www.sensodrive.de
//
//  File:           VibroTacVersion.java
//  Project:        VibroTac Android API
//  Originator:     Petr Maslak
//  Copyright:      SENSODRIVE GmbH, Wessling
//
//      $LastChangedDate: 2017-09-08 08:36:19 +0200 (Fr, 08. Sep 2017) $
//        $LastChangedBy: geist $
//  $LastChangedRevision: 609 $
//
/////////////////////////////////////////////////////////////////////////////
//  $Id: VibroTacVersion.java 609 2017-09-08 06:36:19Z geist $
///////////////////////////////////////////////////////////////////////////*/

package com.sensodrive.vibrotac.api.impl;

import com.sensodrive.vibrotac.api.IVibroTacFirmwareVersion;

/**
 * Implementation of {@link IVibroTacFirmwareVersion}.
 */

public class VibroTacVersion implements IVibroTacFirmwareVersion {

    private Long major;

    private Long minor;

    private Long point;

    private Long revision;

    VibroTacVersion(final byte[] rcvBytes) {
        major = Long.parseLong("" + rcvBytes[2] + rcvBytes[1]);
        minor = Long.parseLong("" + rcvBytes[4] + rcvBytes[3]);
        point = Long.parseLong("" + rcvBytes[6] + rcvBytes[5]);
        revision = Long.parseLong("" + rcvBytes[10] + rcvBytes[9] + rcvBytes[8] + rcvBytes[7]);
    }

    @Override
    public int getMajor() {
        return 0;
    }

    @Override
    public int getMinor() {
        return 0;
    }

    @Override
    public int getPoint() {
        return 0;
    }

    @Override
    public int getRevision() {
        return 0;
    }

    @Override
    public String getAsString() {
        return major + "."
                + minor + "."
                + point + "."
                + revision;
    }
}
