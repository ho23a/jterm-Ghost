/* Copyright 2016 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.ghost;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class TrieNode {
    // A map from the next character in the alphabet to the trie node containing those words
    private HashMap<Character, TrieNode> children;
    // If true, this node represents a complete word.
    private boolean isWord;

    public TrieNode() {
        children = new HashMap<>();
        isWord = false;
    }

    /**
     * Add the string as a child of this trie node.
     *
     * @param s String representing partial suffix of a word.
     */
    public void add(String s) {
        if (s.length() == 0) {
            isWord = true;
            return;
        }

        char character = s.charAt(0);
        String rest = s.substring(1);
        if (!children.containsKey(character)) {
            children.put(character, new TrieNode());
        }
        children.get(character).add(rest);
    }

    /**
     * Determine whether this node is part of a complete word for the string.
     *
     * @param s String representing partial suffix of a word.
     * @return
     */
    public boolean isWord(String s) {
        if (s.length() == 0) {
            return isWord;
        }

        char character = s.charAt(0);
        String rest = s.substring(1);
        if (!children.containsKey(character)) {
            return false;
        }
        return children.get(character).isWord(rest);
    }

    /**
     * Find any complete word with this partial segment.
     *
     * @param s String representing partial suffix of a word.
     * @return
     */
    public String getAnyWordStartingWith(String s) {
        Character character;
        String rest;
        if (s.length() == 0) {
            if (isWord) {
                return "";
            }
            Set<Character> keySet = children.keySet();
            if (keySet.isEmpty()) {
                return null;
            }

            character = keySet.iterator().next();
            rest = "";
//            return getRemainingOfWord(s);
        } else {
            character = s.charAt(0);
            rest = s.substring(1);
        }
        if (children.containsKey(character)) {
            String result = children.get(character).getAnyWordStartingWith(rest);
            if (result != null) {
                return character + result;
            }
        }
        return null;
    }

    private String getRemainingOfWord(String result) {
        if (isWord) {
            return result;
        }
        for (char character = 'a'; character <= 'z'; character++) {
            if (children.containsKey(character)) {
                TrieNode node = children.get(character);
                return node.getRemainingOfWord(result + character);
            }
        }

        return result;
    }

    /**
     * Find a good complete word with this partial segment.
     *
     * Definition of "good" left to implementor.
     * good = even number of remaining letters
     * @param s String representing partial suffix of a word.
     * @return
     */
    // TODO return the word if no word found i.e see test
    public String getGoodWordStartingWith(String s) {
        System.out.println(s);
        if (s.length() == 0) {
            System.out.println("here");
            String remainingResult = getRemainingOfGoodWord(s, 0);
            if (remainingResult != null) {
                return remainingResult;
            }
            // at this point, result is any word
            return s;
        }

        char character = s.charAt(0);
        String rest = s.substring(1);
        if (children.containsKey(character)) {
            String result = children.get(character).getGoodWordStartingWith(rest);
            if (result != null) {
                return character + result;
            }
        }
        return null;
    }

    private String getRemainingOfGoodWord(String result, int count) {
        System.out.println(result);
        System.out.println(count);

        if (isWord && count % 2 == 0) {
            return result;
        } else if (isWord && count % 2 != 0) {
            return null;
        }
        for (char character = 'a'; character <= 'z'; character++) {
            if (children.containsKey(character)) {
                System.out.println(character);
                TrieNode node = children.get(character);
                String temp = node.getRemainingOfGoodWord(result + character, count+1);
                if (temp != null) {
                    return temp;
                }
            }
        }
        /**
         * n
         * o
         * r 1
         * t 2
         * h 3
         */
        return result;
    }
}