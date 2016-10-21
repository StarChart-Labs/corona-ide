/*******************************************************************************
 * Copyright (c) Oct 16, 2016 Corona IDE.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    romeara - initial API and implementation and/or initial documentation
 *******************************************************************************/
package com.coronaide.core;

import java.util.Objects;

import javax.annotation.Nullable;

/**
 * Representation of the state of an item relative its state at earlier or later points in time
 *
 * <p>
 * This representation uses definitions of versions based on <a href="http://semver.org/">semantic versioning</a>
 * </p>
 *
 * @author romeara
 * @since 0.1
 */
public class Version {

    private final int major;

    private final int minor;

    private final int micro;

    /**
     * @param major
     *            The major version of the item. Generally changes when incompatible API differences are present
     * @param minor
     *            The minor version of the item. Generally changes when functionality was added in a compatible manner
     * @param micro
     *            The micro version of the item. Generally changes when bug fixes are made in a compatible manner
     * @since 0.1
     */
    public Version(int major, int minor, int micro) {
        this.major = major;
        this.minor = minor;
        this.micro = micro;
    }

    /**
     * @return The major version of the item. Generally changes when incompatible API differences are present
     * @since 0.1
     */
    public int getMajor() {
        return major;
    }

    /**
     * @return The minor version of the item. Generally changes when functionality was added in a compatible manner
     * @since 0.1
     */
    public int getMinor() {
        return minor;
    }

    /**
     * @return The micro version of the item. Generally changes when bug fixes are made in a compatible manner
     * @since 0.1
     */
    public int getMicro() {
        return micro;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMajor(), getMinor(), getMicro());
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        boolean result = false;

        if (obj instanceof Version) {
            Version compare = (Version) obj;

            result = Objects.equals(compare.getMajor(), getMajor())
                    && Objects.equals(compare.getMinor(), getMinor())
                    && Objects.equals(compare.getMicro(), getMicro());
        }

        return result;
    }

    @Override
    public String toString() {
        return getMajor() + "." + getMinor() + "." + getMicro();
    }
}
