/*///////////////////////////////////////////////////////////////////////////
//                                      ###################################
//                                     #########                    ########
//                                      ##################          ########
//                                                #########         ########
//                                     ###################   ##############
//                                                www.sensodrive.de
//
//  File:           VibrotacTimeoutResponseException.java
//  Project:        VibroTac Android API
//  Originator:     Petr Maslak
//  Copyright:      SENSODRIVE GmbH, Wessling
//
//      $LastChangedDate: 2017-09-08 08:36:19 +0200 (Fr, 08. Sep 2017) $
//        $LastChangedBy: geist $
//  $LastChangedRevision: 609 $
//
/////////////////////////////////////////////////////////////////////////////
//  $Id: VibrotacTimeoutResponseException.java 609 2017-09-08 06:36:19Z geist $
///////////////////////////////////////////////////////////////////////////*/

package com.sensodrive.vibrotac.api.exceptions;

/**
 * Thrown  in case of timeout while waiting for the whole message to be received.
 */

public class VibrotacTimeoutResponseException extends VibroTacException {

    /**
     * Default constructor.
     */
    public VibrotacTimeoutResponseException() {

    }

    @Override
    public String getMessage() {
        return "No data available after 500ms";
    }

}
