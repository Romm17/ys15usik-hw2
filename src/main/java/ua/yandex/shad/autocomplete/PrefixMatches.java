/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.yandex.shad.autocomplete;

import ua.yandex.shad.collections.MyArrayList;
import ua.yandex.shad.tries.RWayTrie;
import ua.yandex.shad.tries.Tuple;
import ua.yandex.shad.tries.Trie;

/**
 *
 * @author andrii
 */
public class PrefixMatches {

    private final Trie trie;
    
    {
        trie = new RWayTrie();
    }

    public int load(String... strings) {
        int previousSize = trie.size();
        for(String s : strings){
            String[] words = s.split(" ");
            for(String word : words){
                if(word.length() > 2){
                    trie.add(new Tuple(word, word.length()));
                }
            }
        }
        return trie.size() - previousSize;
    }

    public boolean contains(String word) {
        return trie.contains(word);
    }

    public boolean delete(String word) {
        return trie.delete(word);
    }

    public Iterable<String> wordsWithPrefix(String pref) {
        return this.wordsWithPrefix(pref, 3);
    }

    public Iterable<String> wordsWithPrefix(String pref, int k) {
        Iterable<String> words;
        if(pref.length() >= 2 && k >= 1){
            words = trie.wordsWithPrefix(pref);
        }
        else{
            return null;
        }
        MyArrayList<String> wordsLessThanK = new MyArrayList();
        for(String s : words){
            if(s.length() <= k + 2){
                wordsLessThanK.add(s);
            }
        }
        return wordsLessThanK;
    }

    public int size() {
        return trie.size();
    }
}
