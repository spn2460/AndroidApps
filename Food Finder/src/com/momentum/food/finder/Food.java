package com.momentum.food.finder;

import sofia.util.Observable;

// -------------------------------------------------------------------------
/**
 * This class creates a food object. The food object holds the fields used for
 * searching and sorting a place, a meal, cost and rating for assistance in
 * making meal-time decisions.
 *
 * @author Shawn P Neuman spn2460
 * @version Oct 11, 2012
 */
public class Food
    extends Observable
{

    private int    id;
    private String place;
    private String meal;
    private String type;
    private String comment;
    private int  cost;
    private float  rating;


    // ----------------------------------------------------------
    /**
     * Create a new Food object.
     */
    public Food()
    {
        place = null;
        meal = null;
        cost = 0;
        rating = 0;
        id = -1;

    }


    // ----------------------------------------------------------
    /**
     * Gets the meal id.
     * @return id the id of the meal
     */
    public int getId()
    {
        return id;
    }


    // ----------------------------------------------------------
    /**
     * sets the meal id.
     * @param id the meal id being set
     */
    public void setId(int id)
    {
        this.id = id;
    }


    // ----------------------------------------------------------
    /**
     * Sets the name of the dining establishment.
     *
     * @param newPlace
     *            the name of the dining establishment
     */
    public void setPlace(String newPlace)
    {
        place = Capitalizer.format(newPlace);
    }



    // ----------------------------------------------------------
    /**
     * returns the name of the restaurant.
     *
     * @return place the name of the dining establishment
     */
    public String getPlace()
    {
        return place;

    }


    // ----------------------------------------------------------
    /**
     * sets the name of the meal you are eating.
     *
     * @param newItem
     *            the name of the meal
     */
    public void setMeal(String newItem)
    {
        meal = Capitalizer.format(newItem);
    }


    // ----------------------------------------------------------
    /**
     * Get the name of the meal.
     *
     * @return the name of the meal
     */
    public String getMeal()
    {
        return meal;
    }


    // ----------------------------------------------------------
    /**
     * set the type of the meal, i.e. mexican, sushi, burgers, pizza and so on .
     *
     * @param newType
     *            the type of the food
     */
    public void setType(String newType)
    {
        type = Capitalizer.format(newType);
    }


    // ----------------------------------------------------------
    /**
     * return the type of the food.
     *
     * @return the type of the food
     */
    public String getType()
    {
        return type;
    }


    // ----------------------------------------------------------
    /**
     * Sets the cost of a meal.
     *
     * @param newCost
     *            the cost of the meal
     */
    public void setCost(float newCost)
    {
        cost = (int)(newCost * 100);
    }

    // ----------------------------------------------------------
    /**
     * sets the cost of the meal in the correct format by calling capitalizer
     * @param newCost the string cost from the gui.
     */
    public void setCost(String newCost)
    {
        cost = Integer.parseInt(Capitalizer.numberFormat(newCost));
    }


    // ----------------------------------------------------------
    /**
     * returns the cost of the meal.
     *
     * @return the cost of the meal
     */
    public int getCost()
    {
        return cost;
    }


    // ----------------------------------------------------------
    /**
     * Sets the rating of a given meal.
     *
     * @param newRating
     *            the star rating of a meal from 1 to 5
     */
    public void setRating(float newRating)
    {
        rating = newRating;
    }


    // ----------------------------------------------------------
    /**
     * gets the rating.
     *
     * @return the integer rating of a meal
     */
    public float getRating()
    {
        return rating;
    }


    // ----------------------------------------------------------
    /**
     * add a comment to your food item.
     *
     * @param str
     *            the comment you wish to add
     */
    public void setComment(String str)
    {
        if (str != "" && str != null)
        {
            comment = str.toLowerCase();
        }
        else
        {
            comment = str;
        }

    }


    // ----------------------------------------------------------
    /**
     * get the comment previously set
     *
     * @return the comment set for that food item
     */
    public String getComment()
    {
        return comment;
    }


    /**
     * returns the string value of the food item
     *
     * @return string value
     */
    public String toString()
    {
        return place + " " + meal + " " + cost + " " + rating + '\n';
    }

}
