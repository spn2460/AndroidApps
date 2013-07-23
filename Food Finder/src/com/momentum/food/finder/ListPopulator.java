package com.momentum.food.finder;

import java.text.DecimalFormat;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;

// -------------------------------------------------------------------------
/**
 * populates an array list of Hash Maps that can be used to populate the search
 * screen or edit screen. This class is a helper class.
 *
 * @author spn2460
 * @version Dec 4, 2012
 */
public class ListPopulator
{
    private static ArrayList<HashMap<String, Object>> arr;
    private static final String                       restaurant = "restaurant";
    private static final String                       meal       = "meal";
    private static final String                       type       = "type";
    private static final String                       cost       = "cost";
    private static final String                       rating     = "rating";
    private static final String                       comment    = "comment";


    // ----------------------------------------------------------
    /**
     * populates an array list for use with the simple adapter.
     *
     * @param food
     *            the food object being passed in
     */
    public static void populate(Food food)
    {
        // instantiates a new array list for passing to a simple adapter
        // while loop moves through cursor, stores values to map
        // map is placed in array list
        arr = new ArrayList<HashMap<String, Object>>();
        List<Food> foodList = DbHelper.retrieveFoodItemList(food);
        for (Food foodItem : foodList)
        {
            HashMap<String, Object> hm = new HashMap<String, Object>();

            hm.put(restaurant, foodItem.getPlace());
            hm.put(meal, foodItem.getMeal());
            hm.put(type, foodItem.getType());
            DecimalFormat decForm = new DecimalFormat("#.00");
            String str_cost = "$" + decForm.format(foodItem.getCost() / 100.0);
            hm.put(cost, str_cost);

            Float str_rating = foodItem.getRating();
            hm.put(rating, str_rating);

            hm.put(comment, foodItem.getComment());

            arr.add(hm);

        }

    }


    // ----------------------------------------------------------
    /**
     * returns the list of maps based on the search.
     *
     * @return the populated list of maps
     */
    public static ArrayList<HashMap<String, Object>> getList()
    {
        return arr;
    }

}
