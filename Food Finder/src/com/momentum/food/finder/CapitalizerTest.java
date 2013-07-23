package com.momentum.food.finder;

import junit.framework.TestCase;

// -------------------------------------------------------------------------
/**
 *  Tests the Capitalizer class
 *
 *  @author spn2460
 *  @version Dec 9, 2012
 */
public class CapitalizerTest
    extends TestCase
{

    private String unClean = "Burger King's whoPper";


    // ----------------------------------------------------------
    /**
     * set up the test fixture.
     */
    public void setup()
    {

        // empty by design

    }

    // ----------------------------------------------------------
    /**
     * test the format method
     */
    public void testFormat()
    {
        String clean = Capitalizer.format(unClean);
        assertEquals("Burger Kings Whopper", clean);

    }

    // ----------------------------------------------------------
    /**
     * test number formatting from decimal to int string.
     */
    public void testNumberFormat()
    {
        String number = "4.99";
        String clean = Capitalizer.numberFormat(number);
        assertEquals("499", clean);

        String number1 = ".99";
        String clean1 = Capitalizer.numberFormat(number1);
        assertEquals("99", clean1);

        String number2 = "4.";
        String clean2 = Capitalizer.numberFormat(number2);
        assertEquals("400", clean2);

        String number3 = "4";
        String clean3 = Capitalizer.numberFormat(number3);
        assertEquals("400", clean3);

        String number4 = ".01";
        String clean4 = Capitalizer.numberFormat(number4);
        assertEquals("01", clean4);


    }


}
