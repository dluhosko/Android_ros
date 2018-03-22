/*///////////////////////////////////////////////////////////////////////////
//                                      ###################################
//                                     #########                    ########
//                                      ##################          ########
//                                                #########         ########
//                                     ###################   ##############
//                                                www.sensodrive.de
//
//  File:           VibroTacWrongLrcResponseException.java
//  Project:        VibroTac Android API
//  Originator:     Petr Maslak
//  Copyright:      SENSODRIVE GmbH, Wessling
//
//      $LastChangedDate: 2017-09-08 08:36:19 +0200 (Fr, 08. Sep 2017) $
//        $LastChangedBy: geist $
//  $LastChangedRevision: 609 $
//
/////////////////////////////////////////////////////////////////////////////
//  $Id: VibroTacWrongLrcResponseException.java 609 2017-09-08 06:36:19Z geist $
///////////////////////////////////////////////////////////////////////////*/

package com.sensodrive.vibrotac.api.exceptions;

/**
 * Thrown in case the received message from VibroTac has wrong LRC check sum.
 */

public class VibroTacWrongLrcResponseException extends VibroTacException {

    public VibroTacWrongLrcResponseException() {
        this.message = "Received message from VibroTac has wrong LRC.";
    }
}
