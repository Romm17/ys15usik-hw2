package ua.yandex.shad.collections;

import java.util.NoSuchElementException;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

public class MyArrayListTest {

    @Test
    public void testConstructor() { 
        MyArrayList<String> list = new MyArrayList();
        int expected = 0;
        int actual = list.size();
        assertEquals(actual, expected); 
    }

    @Test
    public void testAdd() {  
        MyArrayList<String> list = new MyArrayList();
        for(int i = 0; i < 11; i++){
            list.add("word" + i);
        }
        int i = 0;
        for(String s : list){
            Assert.assertEquals(s, "word" + i++);
        } 
    }
    
    @Test(expected = NoSuchElementException.class)
    public void testIteratorOnEmptyList() {
        MyArrayList<String> list = new MyArrayList();
        list.iterator().next();
        Assert.fail();
    }

}
