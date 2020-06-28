package com.techelevator.vendingmachine;

import static org.junit.Assert.*;

import com.techelevator.items.Item;
import org.junit.Before;
import org.junit.Test;
import org.junit.*;

import java.time.LocalTime;
import java.util.HashMap;

public class LoaderTest {

    private Loader loader;

    @Before
    public void setup() {
        loader = new Loader();
    }

    @Test
    public void testParseInputFileProducesArrayOfExpectedSize() {

        String[] resultArray = loader.parseInputFile();
        int expectedLength = 16;
        int actualLength = resultArray.length;

        assertEquals(expectedLength, actualLength);

    }

    @Test
    public void testPassMapMethodProducesHashMapOfExpectedSize() {
        int expectedSize = 16;
        HashMap<String, Item> testMap = loader.passMapForVendingMachine();
        int actualSize = testMap.size();

        assertEquals(expectedSize, actualSize);

    }
}
