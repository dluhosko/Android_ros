/*///////////////////////////////////////////////////////////////////////////
//                                      ###################################
//                                     #########                    ########
//                                      ##################          ########
//                                                #########         ########
//                                     ###################   ##############
//                                                www.sensodrive.de
//
//  File:           VibroTacException.java
//  Project:        VibroTac Android API
//  Originator:     Petr Maslak
//  Copyright:      SENSODRIVE GmbH, Wessling
//
//      $LastChangedDate: 2017-09-08 08:36:19 +0200 (Fr, 08. Sep 2017) $
//        $LastChangedBy: geist $
//  $LastChangedRevision: 609 $
//
/////////////////////////////////////////////////////////////////////////////
//  $Id: VibroTacException.java 609 2017-09-08 06:36:19Z geist $
///////////////////////////////////////////////////////////////////////////*/

package com.sensodrive.vibrotac.api.exceptions;

/**
 * Thrown in case of any exception caused by VibroTac.
 * All VibroTac exceptions inherit from this class.
 */
public class VibroTacException extends Exception {

    String message = null;

    /**
     * Default constructor.
     */
    VibroTacException() {

    }

    VibroTacException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        if (message == null) {
            return "";
        }
        return message;
    }
}
