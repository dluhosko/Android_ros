/*///////////////////////////////////////////////////////////////////////////
//                                      ###################################
//                                     #########                    ########
//                                      ##################          ########
//                                                #########         ########
//                                     ###################   ##############
//                                                www.sensodrive.de
//
//  File:           Cobs.java
//  Project:        VibroTac Android API
//  Originator:     Petr Maslak
//  Copyright:      SENSODRIVE GmbH, Wessling
//
//      $LastChangedDate: 2017-09-08 08:36:19 +0200 (Fr, 08. Sep 2017) $
//        $LastChangedBy: geist $
//  $LastChangedRevision: 609 $
//
/////////////////////////////////////////////////////////////////////////////
//  $Id: Cobs.java 609 2017-09-08 06:36:19Z geist $
///////////////////////////////////////////////////////////////////////////*/

package com.sensodrive.vibrotac.api.impl;

/**
 * Provides static operations for encoding and decoding the COBS protocol.
 * COBS is the abbreviation of Consistent Overhead Byte Stuffing
 */
public class Cobs {
    /**
     * Encodess message to COBS.
     *
     * @param bytes Message to be encoded.
     * @return encoded message.
     */
    public static byte[] encodeCobs(final byte[] bytes) {
        final byte[] result = new byte[bytes.length + 2];
        result[result.length - 1] = 0x00;
        byte distance = 0x01;
        for (int i = bytes.length - 1; i >= 0; i--) {
            if (bytes[i] == 0x00) {
                result[i + 1] = distance;
                distance = 0x01;
            } else {
                result[i + 1] = bytes[i];
                distance++;
            }
        }
        result[0] = distance;

        return result;
    }

    /**
     * Decodes COBS message. <br>
     * Example 1:  <br>
     * input= 0x02,0x03,0x01,0x00 <br>
     * output=0x03,0x00,0x00 <br>
     * <br>
     * Example 2:  <br>
     * input= 0x02,0x03,0x01 <br>
     * output=0x03,0x00,0x00 <br>
     *
     * @param bytes COBS message. Last byte can be 0x00.
     * @return decoded message.
     */
    public static byte[] decodeCobs(final byte[] bytes) {
        final byte[] result;
        if (bytes[bytes.length - 1] == 0x00) {
            result = new byte[bytes.length - 2];
            System.arraycopy(bytes, 1, result, 0, bytes.length - 2);
        } else {
            result = new byte[bytes.length - 1];
            System.arraycopy(bytes, 1, result, 0, bytes.length - 1);
        }

        for (int i = 1; i < bytes.length - 1; i++) {
            result[i - 1] = bytes[i];
        }

        int actZero = bytes[0] - 1;
        while (actZero < result.length - 1) {
            final int tmp = result[actZero];
            result[actZero] = 0;
            actZero += tmp;
        }
        return result;
    }
}
