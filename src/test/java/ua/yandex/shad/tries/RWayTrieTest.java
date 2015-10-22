/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.yandex.shad.tries;

import java.util.Iterator;
import org.junit.Test;
import org.junit.Assert;
import ua.yandex.shad.collections.MyArrayList;

/**
 *
 * @author romm
 */
public class RWayTrieTest {

    @Test
    public void testAdd() {
        RWayTrie trie = new RWayTrie();
        trie.add(new Tuple("abcde", 5));
        trie.add(new Tuple("abcdef", 6));
        trie.add(new Tuple("abca", 4));
        trie.add(new Tuple("abcz", 4));
        Assert.assertTrue(trie.contains("abcde"));
        Assert.assertTrue(trie.contains("abcdef"));
        Assert.assertTrue(trie.contains("abca"));
        Assert.assertTrue(trie.contains("abcz"));
    }

    @Test
    public void testDelete() {
        RWayTrie trie = new RWayTrie();
        trie.add(new Tuple("abcde", 5));
        trie.add(new Tuple("abcdef", 6));
        trie.add(new Tuple("abca", 4));
        trie.add(new Tuple("abcz", 4));
        trie.delete("abc");
        trie.delete("abca");
        trie.delete("abcz");
        trie.delete("abcde");
        Assert.assertEquals(trie.size(), 1);
        Assert.assertFalse(trie.contains("abca"));
        Assert.assertFalse(trie.contains("abcz"));
        Assert.assertFalse(trie.contains("abcde"));
    }
    
    @Test
    public void testDeleteRemovingNode() {
        RWayTrie trie = new RWayTrie();
        trie.add(new Tuple("abc", 3));
        trie.add(new Tuple("abcd", 4));
        trie.add(new Tuple("abcde", 5));
        trie.add(new Tuple("abcdef", 6));
        trie.add(new Tuple("abcdea", 6));
        trie.add(new Tuple("abcdez", 6));
        trie.add(new Tuple("abca", 4));
        trie.add(new Tuple("abcz", 4));
        trie.delete("abcde");
        trie.delete("abcdef");
        trie.delete("abcdea");
        trie.delete("abcdez");
        trie.delete("abc");
        trie.delete("abcd");
        trie.delete("abcz");
        trie.delete("abca");
        Assert.assertEquals(0, trie.size());
    }

    @Test
    public void testWords() {
        RWayTrie trie = new RWayTrie();
        MyArrayList<String> expected = new MyArrayList();
        expected.add("abca");
        expected.add("abcde");
        expected.add("abcdef");
        expected.add("abcz");
        trie.add(new Tuple("abcde", 5));
        trie.add(new Tuple("abcdef", 6));
        trie.add(new Tuple("abca", 4));
        trie.add(new Tuple("abcz", 4));
        Iterable<String> words = trie.words();
        Iterator<String> expect = expected.iterator();
        Iterator<String> actual = words.iterator();
        while (expect.hasNext() && actual.hasNext()) {
            Assert.assertTrue(expect.next().equals(actual.next()));
        }
        Assert.assertFalse(actual.hasNext());
        Assert.assertFalse(expect.hasNext());
    }

    @Test
    public void testWordsWithPrefix() {
        RWayTrie trie = new RWayTrie();
        String[][] expected = {
            {"abca", "abcab", "abcd", "abcf", "abcfb"},
            {"abca", "abcab"},
            {"abcf", "abcfb"}
        };
        trie.add(new Tuple("abcd", 4));
        trie.add(new Tuple("abca", 4));
        trie.add(new Tuple("abcf", 4));
        trie.add(new Tuple("abcab", 5));
        trie.add(new Tuple("abcfb", 5));
        Iterable<String> words = trie.wordsWithPrefix("");
        Assert.assertFalse(words.iterator().hasNext());
        words = trie.wordsWithPrefix("abc");
        int i = 0;
        for (String s : words) {
            if (i > expected[0].length - 1) {
                Assert.fail();
            }
            Assert.assertEquals(s, expected[0][i]);
            i++;
        }
        i = 0;
        words = trie.wordsWithPrefix("a");
        for (String s : words) {
            if (i > expected[0].length - 1) {
                Assert.fail();
            }
            Assert.assertEquals(s, expected[0][i]);
            i++;
        }
        i = 0;
        words = trie.wordsWithPrefix("abca");
        for (String s : words) {
            if (i > expected[1].length - 1) {
                Assert.fail();
            }
            Assert.assertEquals(s, expected[1][i]);
            i++;
        }
        i = 0;
        words = trie.wordsWithPrefix("abcf");
        for (String s : words) {
            if (i > expected[2].length - 1) {
                Assert.fail();
            }
            Assert.assertEquals(s, expected[2][i]);
            i++;
        }

    }

}
