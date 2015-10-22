/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.yandex.shad.tries;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author romm
 */
public class TestTuple {
    
    @Test
    public void TestGetters() {
        Tuple t = new Tuple("String", 6);
        Assert.assertEquals("String", t.getTerm());
        Assert.assertEquals(6, t.getWeight());
    }
    
}
