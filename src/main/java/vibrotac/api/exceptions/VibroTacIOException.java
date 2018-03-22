package com.sensodrive.vibrotac.api.exceptions;

import java.io.IOException;

/**
 * Thrown in case of any IO exception with VibroTac.
 */

public class VibroTacIOException extends VibroTacException {
    /**
     * Wrapper constructor for IO exception.
     *
     * @param e IOException
     */
    public VibroTacIOException(IOException e) {
        this.message = e.getMessage();
    }
}
