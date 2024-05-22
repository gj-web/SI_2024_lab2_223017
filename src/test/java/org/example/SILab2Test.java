package org.example;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SILab2Test {
    private List<Item> items(Item... items) {
        return new ArrayList<>(Arrays.asList(items));
    }

    @Test
    void checkEveryBranch() {
        RuntimeException exception;
        //1 allItems=null,payment=any
        exception = assertThrows(RuntimeException.class, () -> SILab2.checkCart(null, 100));
        assertTrue(exception.getMessage().contains("allItems list can't be null!"));

        //2 allItems=[],payment=0
        assertTrue(SILab2.checkCart(new ArrayList<Item>(), 0));

        //3 allItems=[],payment=-1
        assertFalse(SILab2.checkCart(new ArrayList<Item>(), -1));

        //4 SAME
        exception = assertThrows(RuntimeException.class, () -> SILab2.checkCart(items(new Item("", null, 100, 0.1f)), 1));
        assertTrue(exception.getMessage().contains("No barcode!"));

        //5 allItems=[{"",012345,350,0.1}],payment=100 => TRUE
        assertFalse(SILab2.checkCart(items(new Item("", "01234", 350, 0.4f)), 100));

        //6 SAME
        exception = assertThrows(RuntimeException.class, () -> SILab2.checkCart(items(new Item("Item1", "12345a", 100, 0.1f)), 100));
        assertTrue(exception.getMessage().contains("Invalid character in item barcode!"));

        //7 allItems=[{"Item1",12345,100,-1}],payment=100  => TRUE
        assertFalse(SILab2.checkCart(items(new Item("Item1", "12345", 100, -1)), 2));
    }
    @Test
    void checkMultipleConditions(){
        assertFalse(SILab2.checkCart(items(new Item("", "12345", 350, 0.2f)),2));
        assertFalse(SILab2.checkCart(items(new Item("", "12345", 350, 0)),2));
        assertTrue(SILab2.checkCart(items(new Item("", "01234", 350, 0.2f)),300));
        assertFalse(SILab2.checkCart(items(new Item("", "01234", 100, 0.2f)),2));
    }
}