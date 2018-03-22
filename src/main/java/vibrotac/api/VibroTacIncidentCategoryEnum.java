/*///////////////////////////////////////////////////////////////////////////
//                                      ###################################
//                                     #########                    ########
//                                      ##################          ########
//                                                #########         ########
//                                     ###################   ##############
//                                                www.sensodrive.de
//
//  File:           VibroTacIncidentCategoryEnum.java
//  Project:        VibroTac Android API
//  Originator:     jtymoszu
//  Copyright:      SENSODRIVE GmbH, Wessling
//
//      $LastChangedDate: 2017-09-08 08:36:19 +0200 (Fr, 08. Sep 2017) $
//        $LastChangedBy: geist $
//  $LastChangedRevision: 609 $
//
/////////////////////////////////////////////////////////////////////////////
//  $Id: VibroTacIncidentCategoryEnum.java 609 2017-09-08 06:36:19Z geist $
///////////////////////////////////////////////////////////////////////////*/

package com.sensodrive.vibrotac.api;

/**
 * Enum representing incident category
 *
 * @author jtymoszu
 */
public enum VibroTacIncidentCategoryEnum {

    /**
     * All clear
     */
    SUCCESS(0),

    /**
     * Warning
     */
    WARNING(1),

    /**
     * Error
     */
    ERROR(2);

    /**
     * the name of the enqueue mode
     */
    private int value;

    /**
     * Private constructor.
     *
     * @param value The value of incident category, must be unique
     */
    private VibroTacIncidentCategoryEnum(final int value) {

        this.value = value;
    }

    /**
     * Mapper from a VibrotacIncidentCategoryEnum ID to a
     * VibrotacIncidentCategoryEnum.
     *
     * @param value the value of incident category, must be known
     * @return the mapping for VibrotacIncidentCategoryEnum or
     * IllegalStateException when not found
     */
    public static VibroTacIncidentCategoryEnum fromValue(final int value) {
        for (final VibroTacIncidentCategoryEnum incidentCategory : values()) {
            if (incidentCategory.getValue() == value) {
                return incidentCategory;
            }
        }
        throw new IllegalStateException("Value " + value + " is not known");
    }

    /**
     * Return the name of the incident category value (unique value).
     *
     * @return the incident category value, never null
     */
    public int getValue() {
        return value;
    }

}
