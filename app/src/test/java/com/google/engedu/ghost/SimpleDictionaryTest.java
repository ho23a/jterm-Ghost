/*
 *  Copyright 2016 Google Inc. All Rights Reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.google.engedu.ghost;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class SimpleDictionaryTest {
    SimpleDictionary dictionary;

    @Before
    public void setUp() {
        ArrayList<String> list = new ArrayList<>();
        list.add("dance");
        list.add("done");
        list.add("math");
        list.add("mouth");
        list.add("north");

        dictionary = new SimpleDictionary(list, 0);
    }

    @Test
    public void testIsWord() {
        // TODO(you): Add some tests!
        assertTrue(dictionary.isWord("dance"));
        assertFalse(dictionary.isWord("don"));
        assertFalse(dictionary.isWord("mee"));
    }

    @Test
    public void testGetAnyWordStartingWith() {
        assertEquals(dictionary.getAnyWordStartingWith("don"), "done");
        assertEquals(dictionary.getAnyWordStartingWith("mee"), null);
        assertEquals(dictionary.getAnyWordStartingWith("no"), "north");
        assertEquals(dictionary.getAnyWordStartingWith("m"), "math");
        assertEquals(dictionary.getAnyWordStartingWith("th"), null);
    }
}
