/*******************************************************************************
 * Copyright (c) Oct 17, 2016 Corona IDE.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    romeara - initial API and implementation and/or initial documentation
 *******************************************************************************/
package com.coronaide.core.exception;

/**
 * Represents an error storing or loading to/from a data store. Implemented as a runtime exception, as I/O read/write
 * operations are not expected to fail - failures indicate that base assumptions for running the program are not met
 *
 * @author romeara
 * @since 0.1
 */
public class DataStorageException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new data storage exception with the specified detail message and cause.
     *
     * <p>
     * Note that the detail message associated with {@code cause} is <i>not</i> automatically incorporated in this
     * runtime exception's detail message.
     * </p>
     *
     * @param message
     *            the detail message (which is saved for later retrieval by the {@link #getMessage()} method).
     * @param cause
     *            the cause (which is saved for later retrieval by the {@link #getCause()} method). (A <tt>null</tt>
     *            value is permitted, and indicates that the cause is nonexistent or unknown.)
     * @since 0.1
     */
    public DataStorageException(String message, Throwable cause) {
        super(message, cause);
    }

}
