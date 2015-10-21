package ua.yandex.shad.tries;

import ua.yandex.shad.collections.MyArrayList;

public class RWayTrie implements Trie {
    
    public final static int R = 26;
    
    private Node[] startNodes;
    
    private int size;
    
    public static class Node {
        
        private boolean val;
        private char c;
        private Node left;
        private Node middle;
        private Node right;
        
        public Node(){
            
            val = false;
            left = middle = right = null;
            
        }

        /**
         * @return the val
         */
        public boolean isVal() {
            return val;
        }

        /**
         * @param val the val to set
         */
        public void setVal(boolean val) {
            this.val = val;
        }

        /**
         * @return the c
         */
        public char getC() {
            return c;
        }

        /**
         * @param c the c to set
         */
        public void setC(char c) {
            this.c = c;
        }

        /**
         * @return the left
         */
        public Node getLeft() {
            return left;
        }

        /**
         * @param left the left to set
         */
        public void setLeft(Node left) {
            this.left = left;
        }

        /**
         * @return the middle
         */
        public Node getMiddle() {
            return middle;
        }

        /**
         * @param middle the middle to set
         */
        public void setMiddle(Node middle) {
            this.middle = middle;
        }

        /**
         * @return the right
         */
        public Node getRight() {
            return right;
        }

        /**
         * @param right the right to set
         */
        public void setRight(Node right) {
            this.right = right;
        }
        
    }
    
    public RWayTrie(){
        
        this.startNodes = new Node[R * R];
        size = 0;
     
    }
    
    private Node add(Node x, String s, int k){
        
        if(x == null){
            x = new Node();
            x.setC(s.charAt(k));
        }
        if(s.charAt(k) < x.getC()){
            x.setLeft(add(x.getLeft(), s, k));
        }
        else if(s.charAt(k) > x.getC()){
            x.setRight(add(x.getRight(), s, k));
        }
        else if(k < s.length() - 1){
            x.setMiddle(add(x.getMiddle(), s, k + 1));
        }
        else{
            if(!x.isVal()){
                size++;
                x.setVal(true);
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
        if(s.charAt(k) > x.getC()){
            return contains(x.getRight(), s, k);
        }
        else if(s.charAt(k) < x.getC()){
            return contains(x.getLeft(), s, k);
        }
        else if(s.length() - 1 > k){
            return contains(x.getMiddle(), s, k + 1);
        }
        else{
            return x.isVal();
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
        if(s.charAt(k) > x.getC()){
            x.setRight(delete(x.getRight(), s, k));
        }
        else if(s.charAt(k) < x.getC()){
            x.setLeft(delete(x.getLeft(), s, k));
        }
        else if(s.length() - 1 > k){
            x.setMiddle(delete(x.getMiddle(), s, k + 1));
        }
        else{
            if(x.isVal()) {
                x.setVal(false);
                size--;
            }
            if(x.getLeft() == null
                    && x.getRight() == null
                    && x.getMiddle() == null){
                x = null;
            }
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
        findWords(x.getLeft(), s, arr);
        if(x.isVal()){
            arr.add(s + x.getC());
        }
        findWords(x.getMiddle(), s + x.getC(), arr);
        findWords(x.getRight(), s, arr);
        
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
            if(prefix.charAt(k) < x.getC()){
                this.findWordsWithPrefix(x.getLeft(), s, prefix, k, arr);
            }
            else if(prefix.charAt(k) > x.getC()){
                this.findWordsWithPrefix(x.getRight(), s, prefix, k, arr);
            }
            else {
                if(k == prefix.length() - 1 && x.isVal()){
                    arr.add(prefix);
                }
                this.findWordsWithPrefix(x.getMiddle(), s + x.getC(), prefix, k + 1, arr);
            }      
        }
        else{
            findWordsWithPrefix(x.getLeft(), s, prefix, k, arr);
            if(x.isVal()){
                arr.add(s + x.getC());
            }
            findWordsWithPrefix(x.getMiddle(), s + x.getC(), prefix, k + 1, arr);
            findWordsWithPrefix(x.getRight(), s, prefix, k, arr);
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
