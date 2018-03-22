/*///////////////////////////////////////////////////////////////////////////
//                                      ###################################
//                                     #########                    ########
//                                      ##################          ########
//                                                #########         ########
//                                     ###################   ##############
//                                                www.sensodrive.de
//
//  File:           VibroTacLogListener.java
//  Project:        VibroTac Android API
//  Originator:     jtymoszuk
//  Copyright:      SENSODRIVE GmbH, Wessling
//
//      $LastChangedDate: 2017-09-08 08:36:19 +0200 (Fr, 08. Sep 2017) $
//        $LastChangedBy: geist $
//  $LastChangedRevision: 609 $
//
/////////////////////////////////////////////////////////////////////////////
//  $Id: VibroTacLogListener.java 609 2017-09-08 06:36:19Z geist $
///////////////////////////////////////////////////////////////////////////*/

package com.sensodrive.vibrotac.api;

/**
 * Listener as Logger for Vibrotac. Message contains information about sent or received bytes.
 *
 * @author jtymoszuk
 */
public interface VibroTacLogListener {

    /**
     * Adds new entry to log data
     *
     * @param message       message to be logged. Message contains always sent or received bytes. Example: "2b 4c 00 b4".
     * @param isMessageSent true if message was successfully sent to VibroTac, otherwise message was received successfully.
     */
    public void addLogEntry(String message, boolean isMessageSent);

}
