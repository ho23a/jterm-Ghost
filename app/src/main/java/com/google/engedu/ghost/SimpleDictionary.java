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

import android.support.annotation.VisibleForTesting;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Set;

public class SimpleDictionary implements GhostDictionary {
    private static final String TAG = "SampleDictionary";
    private ArrayList<String> words;
    private Random mRandom;

    public SimpleDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        words = new ArrayList<>();
        String line = null;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            if (word.length() >= MIN_WORD_LENGTH)
              words.add(line.trim());
        }
        mRandom = new Random();
    }

    @VisibleForTesting
    public SimpleDictionary(ArrayList<String> words, long randomSeed) {
        this.words = words;
        mRandom = new Random(randomSeed);
    }

    /**
     * isWord determines whether the argument `word` is present in the dictionary.
     * @param word
     * @return true if the word is in the dictionary, false otherwise.
     */
    @Override
    public boolean isWord(String word) {
        int lo = 0;
        int hi = words.size() - 1;

        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            String temp = words.get(mid);
            if (word.compareTo(temp) < 0) {
                hi = mid - 1;
            } else if (word.compareTo(temp) > 0) {
                lo = mid + 1;
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * Given a prefix, find any word in the dictionary which begins with that prefix, or return null
     * if the dictionary contains no words with that prefix.
     *
     * @param prefix
     * @return
     */
    @Override
    public String getAnyWordStartingWith(String prefix) {
        int lo = 0;
        int hi = words.size() - 1;

        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            String word = words.get(mid);
            if (word.startsWith(prefix)) {
                return word;
            } else if (prefix.compareTo(word) < 0) {
                hi = mid - 1;
            } else if (prefix.compareTo(word) > 0) {
                lo = mid + 1;
            }
        }
        return null;
    }

    /**
     * Given a prefix, find a word in the dictionary that makes a "good" ghost word starting with
     * that prefix, or null if no word in the dictionary starts with that prefix.
     *
     * What defines a "good" starter word is left to the implementer.
     * good = even number of remaining letters
     * @param prefix
     * @return
     */
    @Override
    public String getGoodWordStartingWith(String prefix) {
        String selected = null;
        int lo = 0;
        int hi = words.size() - 1;
        int mid = 0;

        // search for any word
        while (lo <= hi) {
            mid = lo + (hi - lo) / 2;
            selected = words.get(mid);
            if (selected.startsWith(prefix)) {
                break;
            } else if (prefix.compareTo(selected) < 0) {
                hi = mid - 1;
            } else if (prefix.compareTo(selected) > 0) {
                lo = mid + 1;
            }
        }

        if (lo > hi) {
            return null;
        }

        return isGoodWord(prefix, mid);
    }

    private String isGoodWord(String prefix, int index) {
        int left = index;
        int right = index;
        int wordsSize = words.size();
        String selected = null;

        while (left >= 0) {
            selected = words.get(left);
            if (selected.startsWith(prefix) && (selected.length() - prefix.length()) % 2 == 0) {
                return selected;
            }
            left--;
        }

        while (right < wordsSize) {
            selected = words.get(right);
            if (selected.startsWith(prefix) && ((selected.length() - prefix.length()) % 2 == 0)) {
                return selected;
            }
            right ++;
        }
        
        return selected;
    }

    /**
     * Returns the index of the specified key in the specified array.
     *
     * @param  a the array of integers, must be sorted in ascending order
     * @param  key the search key
     * @return index of key in array {@code a} if present; {@code -1} otherwise
     */
    public static int indexOf(int[] a, int key) {
        int lo = 0;
        int hi = a.length - 1;
        while (lo <= hi) {
            // Key is in a[lo..hi] or not present.
            int mid = (lo + hi) / 2;
            if (key == a[mid]) {
                return mid;
            } else if (key < a[mid]) {
                hi = mid - 1;
            } else if (key > a[mid]) {
                lo = mid + 1;
            }
        }
        return -1;
    }

    /**
     * Returns the index of the specified key in the specified array.
     *
     * @param  a the array of integers, must be sorted in ascending order
     * @param  key the search key
     * @return index of key in array {@code a} if present; {@code -1} otherwise
     */
    public static int betterIndexOf(int[] a, int key) {
        int lo = 0;
        int hi = a.length - 1;
        while (lo <= hi) {
            // Key is in a[lo..hi] or not present.
            int mid = lo + (hi - lo) / 2;
            if (key < a[mid]) {
                hi = mid - 1;
            } else if (key > a[mid]) {
                lo = mid + 1;
            } else {
                return mid;
            }
        }
        return -1;
    }
}
