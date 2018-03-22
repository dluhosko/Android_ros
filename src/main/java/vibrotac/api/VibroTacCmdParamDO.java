/*///////////////////////////////////////////////////////////////////////////
//                                      ###################################
//                                     #########                    ########
//                                      ##################          ########
//                                                #########         ########
//                                     ###################   ##############
//                                                www.sensodrive.de
//
//  File:           VibroTacCmdParamDO.java
//  Project:        VibroTac Android API
//  Originator:     jtymoszuk
//  Copyright:      SENSODRIVE GmbH, Wessling
//
//      $LastChangedDate: 2017-09-08 08:36:19 +0200 (Fr, 08. Sep 2017) $
//        $LastChangedBy: geist $
//  $LastChangedRevision: 609 $
//
/////////////////////////////////////////////////////////////////////////////
//  $Id: VibroTacCmdParamDO.java 609 2017-09-08 06:36:19Z geist $
///////////////////////////////////////////////////////////////////////////*/

package com.sensodrive.vibrotac.api;

/**
 * Data Object representing command parameters
 *
 * @author jtymoszuk
 */
public class VibroTacCmdParamDO {

    /**
     * Motor intensity.
     */
    private final byte intensity;

    /**
     * Pulse duration (2 bytes).
     */
    private final short duration;

    public VibroTacCmdParamDO(final byte intensity, final short duration) {
        this.intensity = intensity;
        this.duration = duration;
    }


    /**
     * Gets intensity of this command.
     *
     * @return intensity of this command.
     */
    public byte getIntensity() {
        return intensity;
    }


    /**
     * Gets duration of this command in [ms]
     *
     * @return duration of this command in [ms]
     */
    public short getDuration() {
        return duration;
    }

}
