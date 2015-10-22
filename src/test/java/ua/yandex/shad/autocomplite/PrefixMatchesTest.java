/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.yandex.shad.autocomplite;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import ua.yandex.shad.autocomplete.PrefixMatches;

/**
 *
 * @author romm
 */
public class PrefixMatchesTest {
    
    private static PrefixMatches pm;
    
    @Before
    public void init(){
        pm = new PrefixMatches();
    }
    
    @Test
    public void testLoad(){
        Scanner fsc = null;
        try{
            fsc = new Scanner(new File("src/main/resources/words-333333.txt"));
        }catch(FileNotFoundException e){
            System.out.println("File not found");
            return;
        }
        int expected = 0;
        fsc.nextLine();
        String s;
        while(fsc.hasNextLine()){
            s = fsc.nextLine();
            String[] temp = s.split("\t");
            expected += pm.load(temp[temp.length - 1]);
        }
        Assert.assertEquals(pm.size(), expected);
    }
    
    @Test
    public void testContains(){
        pm.load("word");
        Assert.assertTrue(pm.contains("word"));
        Assert.assertFalse(pm.contains("wor"));
    }
    
    @Test
    public void testDelete(){
        pm.load("word");
        Assert.assertTrue(pm.contains("word"));
        pm.delete("words");
        Assert.assertTrue(pm.contains("word"));
        pm.delete("word");
        Assert.assertFalse(pm.contains("word"));
    }
    
    @Test
    public void testWordsWithPrefix(){
        pm.load("abc");
        pm.load("abcd");
        pm.load("abcde");
        pm.load("abcdef");
        pm.load("abcdefg");
        pm.load("abcdefgh");
        pm.load("abcgf");
        String[] expected = {"abc", "abcd", "abcde", "abcgf"};
        Iterable<String> actual = pm.wordsWithPrefix("abc");
        int i = 0;
        for(String s : actual){
            if(i > expected.length - 1){
                Assert.fail("Out of bounds");
            }
            Assert.assertTrue(s.equals(expected[i]));
            i++;
        }
    }
    
    @Test
    public void testWordsWithPrefixWithK(){
        pm.load("abc");
        pm.load("abcd");
        pm.load("abcde");
        pm.load("abcdef");
        pm.load("abcdefg");
        pm.load("abcdefgh");
        pm.load("abcgf");
        String[][] expected = {
            {"abc"},
            {"abc", "abcd"},
            {"abc", "abcd", "abcde", "abcgf"},
            {"abc", "abcd", "abcde", "abcdef", "abcgf"},
            {"abc", "abcd", "abcde", "abcdef", "abcdefg", "abcgf"}
        };
        for(int j = 0; j < expected.length; j++){
            Iterable<String> actual = pm.wordsWithPrefix("abc", j + 1);
            int i = 0;
            for(String s : actual){
                if(i > expected[j].length - 1){
                    Assert.fail("Out of bounds");
                }
                Assert.assertTrue(s.equals(expected[j][i]));
                i++;
            }
        }
        Assert.assertNull(pm.wordsWithPrefix("abc", 0));
        Assert.assertNull(pm.wordsWithPrefix("a", 10));
        Assert.assertNull(pm.wordsWithPrefix("a", 0));
    }
    
    @Test
    public void testSize(){
        pm.load("abc");
        pm.load("abcd");
        pm.load("abcde");
        pm.load("abcdef");
        pm.load("abcdefg");
        pm.load("abcdefgh");
        pm.load("abcgf");
        pm.load("abc");
        int expected = 7;
        Assert.assertEquals(expected, pm.size());
    }
    
}
