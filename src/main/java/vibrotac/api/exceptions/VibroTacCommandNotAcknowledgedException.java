/*///////////////////////////////////////////////////////////////////////////
//                                      ###################################
//                                     #########                    ########
//                                      ##################          ########
//                                                #########         ########
//                                     ###################   ##############
//                                                www.sensodrive.de
//
//  File:           VibroTacCommandNotAcknowledgedException.java
//  Project:        VibroTac Android API
//  Originator:     Petr Maslak
//  Copyright:      SENSODRIVE GmbH, Wessling
//
//      $LastChangedDate: 2017-09-08 08:36:19 +0200 (Fr, 08. Sep 2017) $
//        $LastChangedBy: geist $
//  $LastChangedRevision: 609 $
//
/////////////////////////////////////////////////////////////////////////////
//  $Id: VibroTacCommandNotAcknowledgedException.java 609 2017-09-08 06:36:19Z geist $
///////////////////////////////////////////////////////////////////////////*/

package com.sensodrive.vibrotac.api.exceptions;

/**
 * Thrown in case the sent command was not acknowledged with positive response from VibroTac.
 */

public class VibroTacCommandNotAcknowledgedException extends VibroTacException {
    /**
     * Constructor.
     *
     * @param message Exception message.
     */
    public VibroTacCommandNotAcknowledgedException(String message) {
        this.message = message;
    }
}
