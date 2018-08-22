package com.elib.demo;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ELibraryTest {

    private GoldenMaster goldenMaster;

    @Before
    public void setup() throws Exception {
        goldenMaster = new GoldenMaster();
    }


    @Ignore
//    @Test
    public void should_generate_golden_master() throws Exception {
        goldenMaster.generateGoldenMaster();
    }

    @Test
    public void should_match_goldenMaster() throws Exception {
        String expected = goldenMaster.readCurrentGoldenMaster() + System.lineSeparator();
        String actual = new String(goldenMaster.runResultBytes());

        assertEquals(expected, actual);
    }
}
