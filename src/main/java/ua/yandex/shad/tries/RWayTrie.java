package ua.yandex.shad.tries;

import ua.yandex.shad.collections.MyArrayList;

/**
 * @author Romam Usik
 */
public class RWayTrie implements Trie {
    
    private static final int R = 26;
    
    private final Node[] startNodes;
    
    private int size;
    
    public static class Node {
        
        private boolean val;
        private char c;
        private Node left;
        private Node middle;
        private Node right;
        
        public Node() {
            val = false;
            left = null;
            middle = null;
            right = null; 
        }

        /**
         * @return the val
         */
        public boolean isVal() {
            return val;
        }

        /**
         * @param valPar the val to set
         */
        public void setVal(boolean valPar) {
            this.val = valPar;
        }

        /**
         * @return the c
         */
        public char getC() {
            return c;
        }

        /**
         * @param cPar the c to set
         */
        public void setC(char cPar) {
            this.c = cPar;
        }

        /**
         * @return the left
         */
        public Node getLeft() {
            return left;
        }

        /**
         * @param leftPar the left to set
         */
        public void setLeft(Node leftPar) {
            this.left = leftPar;
        }

        /**
         * @return the middle
         */
        public Node getMiddle() {
            return middle;
        }

        /**
         * @param middlePar the middle to set
         */
        public void setMiddle(Node middlePar) {
            this.middle = middlePar;
        }

        /**
         * @return the right
         */
        public Node getRight() {
            return right;
        }

        /**
         * @param rightPar the right to set
         */
        public void setRight(Node rightPar) {
            this.right = rightPar;
        }
        
    }
    
    public RWayTrie() {
        this.startNodes = new Node[R * R];
        size = 0;
    }
    
    private Node add(Node x, String s, int k) {
        Node current = x;
        if (x == null) {
            current = new Node();
            current.setC(s.charAt(k));
        }
        if (s.charAt(k) < current.getC()) {
            current.setLeft(add(current.getLeft(), s, k));
        }
        else if (s.charAt(k) > current.getC()) {
            current.setRight(add(current.getRight(), s, k));
        }
        else if (k < s.length() - 1) {
            current.setMiddle(add(current.getMiddle(), s, k + 1));
        }
        else {
            if (!current.isVal()) {
                size++;
                current.setVal(true);
            }  
        }
        return current;
    }
    
    private int findIndex(char i, char j) {
        return R * (i - 'a') + j - 'a';
    }

    @Override
    public void add(Tuple t) {
        int index = findIndex(t.getTerm().charAt(0), t.getTerm().charAt(1));
        Node x = startNodes[index];
        this.startNodes[index] = add(x, t.getTerm(), 2);
    }
    
    private boolean contains(Node x, String s, int k) {
        if (x == null) {
            return false;
        }
        if (s.charAt(k) > x.getC()) {
            return contains(x.getRight(), s, k);
        }
        else if (s.charAt(k) < x.getC()) {
            return contains(x.getLeft(), s, k);
        }
        else if (s.length() - 1 > k) {
            return contains(x.getMiddle(), s, k + 1);
        }
        else {
            return x.isVal();
        }
    }

    @Override
    public boolean contains(String word) {
        Node x = startNodes[findIndex(word.charAt(0), word.charAt(1))];
        return contains(x, word, 2);
        
    }
    
    private Node delete(Node x, String s, int k) {
        Node current = x;
        if (current == null) {
            return current;
        }
        if (s.charAt(k) > current.getC()) {
            current.setRight(delete(current.getRight(), s, k));
        }
        else if (s.charAt(k) < current.getC()) {
            current.setLeft(delete(current.getLeft(), s, k));
        }
        else if (s.length() - 1 > k) {
            current.setMiddle(delete(current.getMiddle(), s, k + 1));
        }
        else {
            if (current.isVal()) {
                current.setVal(false);
                size--;
            }
//            boolean a = current.getMiddle() != null;
//            boolean b = current.getRight() != null;
//            boolean c = current.getLeft() == null;
//            System.out.println(c + " " + a + " " + b);
            if (current.getMiddle() == null
                    && current.getRight() == null
                    && current.getLeft() == null) {
                current = null;
            }
        }
        return current;   
    }
    
    @Override
    public boolean delete(String word) { 
        int sizeWas = size;
        int index = findIndex(word.charAt(0), word.charAt(1));
        Node x = startNodes[index];
        startNodes[index] = delete(x, word, 2);
        int sizeNow = size;
        return sizeWas - sizeNow == 1;  
    }
    
    private void findWords(Node x, String s, MyArrayList<String> arr) { 
        if (x == null) {
            return;
        }
        findWords(x.getLeft(), s, arr);
        if (x.isVal()) {
            arr.add(s + x.getC());
        }
        findWords(x.getMiddle(), s + x.getC(), arr);
        findWords(x.getRight(), s, arr);  
    }

    @Override
    public Iterable<String> words() {  
        MyArrayList<String> words;
        words = new MyArrayList();
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < R; j++) {
                Node x = startNodes[R * i + j];
                findWords(x, "" + (char) ('a' + i) + (char) ('a' + j), words);
            }
        }
        return words;    
    }
    
    private void findWordsWithPrefix(Node x, String s, String prefix, 
            int k, MyArrayList<String> arr) {   
        if (x == null) {
            return;
        }
        if (k < prefix.length()) {
            if (prefix.charAt(k) < x.getC()) {
                findWordsWithPrefix(x.getLeft(), s, prefix, k, arr);
            }
            else if (prefix.charAt(k) > x.getC()) {
                findWordsWithPrefix(x.getRight(), s, prefix, k, arr);
            }
            else {
                if (k == prefix.length() - 1 && x.isVal()) {
                    arr.add(prefix);
                }
                findWordsWithPrefix(x.getMiddle(), s + x.getC(),
                        prefix, k + 1, arr);
            }      
        }
        else {
            findWordsWithPrefix(x.getLeft(), s, prefix, k, arr);
            if (x.isVal()) {
                arr.add(s + x.getC());
            }
            findWordsWithPrefix(x.getMiddle(), s + x.getC(), prefix,
                    k + 1, arr);
            findWordsWithPrefix(x.getRight(), s, prefix, k, arr);
        } 
    }

    @Override
    public Iterable<String> wordsWithPrefix(String s) {
        if (s.length() == 0) {
            return new MyArrayList();
        }
        MyArrayList<String> words = new MyArrayList();
        Node x;
        if (s.length() == 1) {
            for (int i = 0; i < R; i++) {
                x = startNodes[R * (s.charAt(0) - 'a') 
                    + i];
                String prefix = "" + s.charAt(0) + (char) ('a' + i);
                findWordsWithPrefix(x, prefix, s, 2, words);
            }
        }
        else {
            x = startNodes[findIndex(s.charAt(0), s.charAt(1))];
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
