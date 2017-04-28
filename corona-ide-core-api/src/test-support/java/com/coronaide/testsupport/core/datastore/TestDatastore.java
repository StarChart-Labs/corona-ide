/*******************************************************************************
 * Copyright (c) Oct 20, 2016 Corona IDE.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    romeara - initial API and implementation and/or initial documentation
 *******************************************************************************/
package com.coronaide.testsupport.core.datastore;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.coronaide.core.datastore.Datastore;

/**
 * Simple datastore implementation which allows testing for data storage APIs
 *
 * @author romeara
 */
public class TestDatastore implements Datastore<String> {

    @Override
    public String getKey() {
        return "test-data-store";
    }

    @Override
    public void store(String data, BufferedWriter destination) throws IOException {
        destination.write(data);
    }

    @Override
    public String load(BufferedReader source) throws IOException {
        List<String> lines = new ArrayList<>();
        String line = source.readLine();

        while (line != null) {
            lines.add(line);
            line = source.readLine();
        }

        return lines.stream().collect(Collectors.joining("\n"));
    }

}
