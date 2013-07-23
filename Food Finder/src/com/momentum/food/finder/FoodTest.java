package com.momentum.food.finder;

import junit.framework.TestCase;

// -------------------------------------------------------------------------
/**
 *  tests Food object
 *
 *  @author spn2460
 *  @version Dec 9, 2012
 */
public class FoodTest
extends TestCase
{
    private Food food;

    /**
     * sets up test fixture
     */
    public void setUp()
    {
        food = new Food();
    }


    // ----------------------------------------------------------
    /**
     * tests setters and getters
     */
    public void testGetAndSetFood()
    {
        food.setPlace("olive");
        food.setType("ital");
        food.setMeal("lasgna");
        food.setCost(10);
        food.setRating(3);
        food.setComment("no");
        food.setId(1);


        assertEquals("olive", food.getPlace());
        assertEquals("ital", food.getType());
        assertEquals("lasgna", food.getMeal());
        assertEquals(1000, food.getCost());
        assertEquals(3.0, food.getRating(), .01);
        assertEquals("no", food.getComment());
        assertEquals(1, food.getId());
    }


    // ----------------------------------------------------------



}
