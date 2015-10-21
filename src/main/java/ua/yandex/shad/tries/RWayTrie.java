package ua.yandex.shad.tries;

import ua.yandex.shad.collections.MyArrayList;

public class RWayTrie implements Trie {
    
    public static int R = 26;
    
    private Node[] startNodes;
    
    private int size;
    
    public static class Node {
        
        public boolean val;
        public char c;
        public Node left, middle, right;
        
        public Node(){
            
            val = false;
            left = middle = right = null;
            
        }
        
        public Node(char c){
            
            this.c = c;
            val = false;
            left = middle = right = null;
            
        }
        
    }
    
    public RWayTrie(){
        
        this.startNodes = new Node[R * R];
        size = 0;
     
    }
    
    private Node add(Node x, String s, int k){
        
        if(x == null){
            x = new Node(s.charAt(k));
        }
        if(s.charAt(k) < x.c){
            x.left = add(x.left, s, k);
        }
        else if(s.charAt(k) > x.c){
            x.right = add(x.right, s, k);
        }
        else if(k < s.length() - 1){
            x.middle = add(x.middle, s, k + 1);
        }
        else{
            if(!x.val){
                size++;
                x.val = true;
            }
            
        }
        return x;
        
    }

    @Override
    public void add(Tuple t) {
        
        Node x = this.startNodes[R * (t.term.charAt(0) - 'a') 
                + (t.term.charAt(1) - 'a')];
        this.startNodes[R * (t.term.charAt(0) - 'a') 
                + (t.term.charAt(1) - 'a')] = add(x, t.term, 2);
        
    }
    
    private boolean contains(Node x, String s, int k){
        
        if(x == null)
            return false;
        if(s.charAt(k) > x.c){
            return contains(x.right, s, k);
        }
        else if(s.charAt(k) < x.c){
            return contains(x.left, s, k);
        }
        else if(s.length() - 1 > k){
            return contains(x.middle, s, k + 1);
        }
        else{
            return x.val;
        }
        
    }

    @Override
    public boolean contains(String word) {
        
        Node x = this.startNodes[R * (word.charAt(0) - 'a') 
                + (word.charAt(1) - 'a')];
        return contains(x, word, 2);
        
    }
    
    private Node delete(Node x, String s, int k){
        
        if(x == null)
            return x;
        if(s.charAt(k) > x.c){
            x.right = delete(x.right, s, k);
        }
        else if(s.charAt(k) < x.c){
            x.left = delete(x.left, s, k);
        }
        else if(s.length() - 1 > k){
            x.middle = delete(x.middle, s, k + 1);
        }
        else{
            if(!x.val)
                return x;
            if(x.left == null
                    && x.right == null
                    && x.middle == null){
                x = null;
            }
            else{
                x.val = false;
            }
            size--;
        }
        return x;
        
    }

    @Override
    public boolean delete(String word) {
        
        int sizeWas = size;
        Node x = this.startNodes[R * (word.charAt(0) - 'a') 
                + (word.charAt(1) - 'a')];
        this.startNodes[R * (word.charAt(0) - 'a') 
                + (word.charAt(1) - 'a')] = delete(x, word, 2);
        int sizeNow = size;
        return sizeWas - sizeNow == 1;
        
    }
    
    private void findWords(Node x, String s, MyArrayList<String> arr){
        
        if(x == null)
            return;
        findWords(x.left, s, arr);
        if(x.val){
            arr.add(s + x.c);
        }
        findWords(x.middle, s + x.c, arr);
        findWords(x.right, s, arr);
        
    }

    @Override
    public Iterable<String> words() {
        
        MyArrayList<String> words;
        words = new MyArrayList();
        for(int i = 0; i < R; i++){
            for(int j = 0; j < R; j++){
                Node x = this.startNodes[R * i + j];
                findWords(x, "" + (char)('a' + i) + (char)('a' + j), words);
            }
        }
        return words;
        
    }
    
    private void findWordsWithPrefix(Node x, String s, String prefix, 
            int k, MyArrayList<String> arr
            ) {
        
        if(x == null)
            return;
        if(k < prefix.length()){
            if(prefix.charAt(k) < x.c){
                this.findWordsWithPrefix(x.left, s, prefix, k, arr);
            }
            else if(prefix.charAt(k) > x.c){
                this.findWordsWithPrefix(x.right, s, prefix, k, arr);
            }
            else {
                if(k == prefix.length() - 1 && x.val){
                    arr.add(prefix);
                }
                this.findWordsWithPrefix(x.middle, s + x.c, prefix, k + 1, arr);
            }      
        }
        else{
            findWordsWithPrefix(x.left, s, prefix, k, arr);
            if(x.val){
                arr.add(s + x.c);
            }
            findWordsWithPrefix(x.middle, s + x.c, prefix, k + 1, arr);
            findWordsWithPrefix(x.right, s, prefix, k, arr);
        }
        
    }

    @Override
    public Iterable<String> wordsWithPrefix(String s) {
        
        if(s.length() == 0){
            return new MyArrayList();
        }
        MyArrayList<String> words = new MyArrayList();
        Node x;
        if(s.length() == 1){
            for(int i = 0; i < R; i++){
                x = this.startNodes[R * (s.charAt(0) - 'a') 
                    + i];
                String prefix = "" + s.charAt(0) + (char)('a' + i);
                findWordsWithPrefix(x, prefix, s, 2, words);
            }
        }
        else {
            x = this.startNodes[R * (s.charAt(0) - 'a') 
                    + (s.charAt(1) - 'a')];
            String prefix = "" + s.charAt(0) + s.charAt(1);
            findWordsWithPrefix(x, prefix, s, 2, words);
        }
        return words;
        
    }

    @Override
    public int size() {
        
        return size; 
        
    }

}
