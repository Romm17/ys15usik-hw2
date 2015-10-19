/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.yandex.shad.tries;

import java.util.Iterator;
import org.junit.Test;
import org.junit.Assert;
import org.junit.Ignore;

/**
 *
 * @author romm
 */
public class RWayTrieTest {
    
    @Test
    public void testAdd(){
        RWayTrie trie = new RWayTrie();
        trie.add(new Tuple("lololo", 6));
        trie.add(new Tuple("lololk", 6));
        trie.add(new Tuple("lolok", 5));
        trie.add(new Tuple("loloa", 5));
        Assert.assertTrue(trie.contains("lololo"));
        Assert.assertTrue(trie.contains("lololk"));
        Assert.assertTrue(trie.contains("lolok"));
        Assert.assertTrue(trie.contains("loloa"));
//        Assert.assertTrue(true);
    }
    
    @Test
    public void testDelete(){
        RWayTrie trie = new RWayTrie();
        trie.add(new Tuple("lololo", 6));
        trie.add(new Tuple("lololk", 6));
        trie.add(new Tuple("lolok", 5));
        trie.add(new Tuple("loloa", 5));
        Assert.assertTrue(trie.contains("lolok"));
        trie.delete("lolok");
        Assert.assertEquals(trie.size(), 3);
        Assert.assertFalse(trie.contains("lolok"));
    }
    
    @Test
    public void testWords(){
        RWayTrie trie = new RWayTrie();
        trie.add(new Tuple("lololo", 6));
        trie.add(new Tuple("lololk", 6));
        trie.add(new Tuple("lolok", 5));
        trie.add(new Tuple("loloa", 5));
        Iterable<String> words = trie.words();
//        for(String s : words){
//            System.out.println(s);
//        }
    }
    @Test
    public void testWordsWithPrefix(){
        RWayTrie trie = new RWayTrie();
        trie.add(new Tuple("lololo", 6));
        trie.add(new Tuple("lololk", 6));
        trie.add(new Tuple("laloa", 5));
        trie.add(new Tuple("lolok", 5));
        trie.add(new Tuple("loloa", 5));
        Iterable<String> words = trie.wordsWithPrefix("");
        for(String s : words){
            System.out.println(s);
        }
    }
    
    
}
