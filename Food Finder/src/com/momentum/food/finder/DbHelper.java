package com.momentum.food.finder;

import android.content.ContentValues;
import sofia.app.Screen;
import java.util.List;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;

/**
 * Search helper that retrieves info from the search screen class and constructs
 * a query string to search the database which is then passed back to the search
 * screen class to search the database
 *
 * @author spn2460
 * @version Nov 17, 2012
 */

public class DbHelper
    extends Screen
{
 // ----------------------------------------------------------
    /**
     * opens the database, then adds the items retrieved from the enter screen.
     * @param food the food object passed in
     *
     * @return true if the item has been added otherwise false
     */
    public static boolean addToDb(Food food)
    {
        ContentValues placeValues = new ContentValues();
        ContentValues foodValues = new ContentValues();
        SQLiteDatabase db = HomeScreen.ourDataBase.open();

        placeValues.put(FinderDb.PLACE, food.getPlace()); // inserting a string
        long id = 0;
        try
        {
            id = db.insertOrThrow(FinderDb.PLACE_TABLE, null, placeValues);

        }
        catch (Exception e)
        {

            String queryString =
                " SELECT * FROM " + FinderDb.PLACE_TABLE + " WHERE "
                    + FinderDb.PLACE + " LIKE \'" + food.getPlace() + "\'";

            Cursor cursor = db.rawQuery(queryString, null);

            while (cursor.moveToNext())
            {


                int pkPlace = cursor.getColumnIndex(FinderDb.PK_PLACE);
                id = cursor.getLong(pkPlace);
            }
        }

        String duplicate_query =
            "SELECT * FROM " + FinderDb.FOOD_TABLE + " WHERE "
                + FinderDb.PLACE_IN_FOOD + " = " + id + " AND " + FinderDb.MEAL
                + " LIKE \'" + food.getMeal() + "\'";
        Cursor cursor = db.rawQuery(duplicate_query, null);
        int counter = cursor.getCount();


        if (counter == 0)
        {
            foodValues.put(FinderDb.PLACE_IN_FOOD, id);
            foodValues.put(FinderDb.TYPE, food.getType());
            foodValues.put(FinderDb.MEAL, food.getMeal());
            foodValues.put(FinderDb.COST, food.getCost());
            foodValues.put(FinderDb.RATING, food.getRating());
            foodValues.put(FinderDb.COMMENT, food.getComment());

            db.insert(FinderDb.FOOD_TABLE, null, foodValues);
            cursor.close();
            db.close();

            return true;

        }
        cursor.close();
        db.close();

        return false;

    }


    // ----------------------------------------------------------
    /**
     * creates a string for a screen toast that confirms the information has
     * been added to the database.
     * @param food the food item being passed in
     *
     * @return the resultant string to toast
     */
    public static String makeToast(Food food)
    {
        SQLiteDatabase db = HomeScreen.ourDataBase.open();

        String result = "";
        String queryString2 =
            " SELECT * FROM " + FinderDb.FOOD_TABLE + " WHERE " + FinderDb.MEAL
                + " LIKE \'" + food.getMeal() + "\'";

        Cursor cursor2 = db.rawQuery(queryString2, null);

        cursor2.getColumnIndex(FinderDb.PLACE_IN_FOOD);
        int typeIndex = cursor2.getColumnIndex(FinderDb.TYPE);
        int mealIndex = cursor2.getColumnIndex(FinderDb.MEAL);
        int costIndex = cursor2.getColumnIndex(FinderDb.COST);
        int ratingIndex = cursor2.getColumnIndex(FinderDb.RATING);
        int commentIndex = cursor2.getColumnIndex(FinderDb.COMMENT);

        while (cursor2.moveToNext())
        {
            result = food.getPlace();

            result += ", " + cursor2.getString(typeIndex);
            result += ", " + cursor2.getString(mealIndex);
            result += ", " + (float)(cursor2.getInt(costIndex) / 100.0);
            result += ", " + cursor2.getFloat(ratingIndex);
            result += ", " + cursor2.getString(commentIndex);
            result += " " + "has been added to your data base";
        }

        return result;

    }



    // ----------------------------------------------------------
    /**
     * uses the food item being passed in to the helper to update the existing
     * columns in the tables.
     *
     * @param oldFood
     *            the original food in the db
     * @param newFood
     *            the updated food with new values
     * @return true when the database was updated
     */
    public static boolean updateDb(Food oldFood, Food newFood)
    {
        SQLiteDatabase db = HomeScreen.ourDataBase.open();
        long id = -1;

        String queryString =
            " SELECT * FROM " + FinderDb.PLACE_TABLE + " WHERE "
                + FinderDb.PLACE + " LIKE \'" + oldFood.getPlace() + "\'";

        Cursor cursor = db.rawQuery(queryString, null);

        while (cursor.moveToNext())
        {


            int pkPlace = cursor.getColumnIndex(FinderDb.PK_PLACE);
            id = cursor.getLong(pkPlace);
        }

        String query =
            "SELECT * FROM " + FinderDb.FOOD_TABLE + " WHERE "
                + FinderDb.PLACE_IN_FOOD + " = " + id + " AND " + FinderDb.MEAL
                + " LIKE \'" + oldFood.getMeal() + "\'";
        Cursor cursor1 = db.rawQuery(query, null);
        int counter = cursor1.getCount();


        if (counter != 0)
        {
            ContentValues foodValues = new ContentValues();
            // foodValues.put(FinderDb.PLACE_IN_FOOD, id);
            foodValues.put(FinderDb.TYPE, newFood.getType());
            foodValues.put(FinderDb.MEAL, newFood.getMeal());
            foodValues.put(FinderDb.COST, newFood.getCost());
            foodValues.put(FinderDb.RATING, newFood.getRating());
            foodValues.put(FinderDb.COMMENT, newFood.getComment());
            String where =
                FinderDb.PLACE_IN_FOOD + " = " + id + " AND " + FinderDb.MEAL
                    + " LIKE \'" + oldFood.getMeal() + "\'";
            db.update(FinderDb.FOOD_TABLE, foodValues, where, null);
            db.close();
            return true;
        }
        db.close();
        return false;

    }


    // ----------------------------------------------------------
    /**
     * Creates a string used to query the database
     *
     * @param food
     *            the food to search
     * @return the query string used to search
     */
    private static String createQuery(Food food)
    {

        String query =
            " SELECT * FROM " + FinderDb.PLACE_TABLE + ", "
                + FinderDb.FOOD_TABLE + " WHERE " + FinderDb.PLACE_TABLE + "."
                + FinderDb.PK_PLACE + " = " + FinderDb.FOOD_TABLE + "."
                + FinderDb.PLACE_IN_FOOD;
        if (food != null)
        {
            if (!food.getPlace().equals(""))
            {
                query +=
                    " AND " + FinderDb.PLACE + " LIKE \'%" + food.getPlace()
                        + "%\'";
            }

            if (!food.getType().equals(""))
            {
                query +=
                    " AND " + FinderDb.TYPE + " LIKE \'%" + food.getType()
                        + "%\'";
            }

            if (!food.getMeal().equals(""))
            {
                query +=
                    " AND " + FinderDb.MEAL + " LIKE \'%" + food.getMeal()
                        + "%\'";
            }

            if (food.getCost() != 0)
            {
                query += " AND " + FinderDb.COST + " <= " + food.getCost();
            }
        }
        query += " ORDER BY " + FinderDb.RATING + " DESC";

        return query;

    }


    // ----------------------------------------------------------
    /**
     * Gets a food item from a string db query.
     *
     * @param query
     *            the string being used to search for an item
     * @return food the food item found
     */
    private static Food retrieveFoodItem(String query)
    {
        // instantiates a cursor, gets the columns indexes for the
        // query values
        SQLiteDatabase db = HomeScreen.ourDataBase.open();
        Cursor c = db.rawQuery(query, null);

        Food food = null;
        //System.out.println("Cursor contents: " + c.getCount());
        while (c.moveToNext())
        {

            food = DbHelper.createFoodFromCursor(c);
            break;
        }
        db.close();

        return food;
    }


    // ----------------------------------------------------------
    /**
     * Retrieves an array list of food items.
     *
     * @param food
     *            the food being searched for or null for database dump
     * @return list the array list of food items
     */
    public static List<Food> retrieveFoodItemList(Food food)
    {

        String query = DbHelper.createQuery(food);
        // instantiates a cursor, gets the columns indexes for the
        // query values
        SQLiteDatabase db = HomeScreen.ourDataBase.open();
        Cursor c = db.rawQuery(query, null);

        ArrayList<Food> foodList = new ArrayList<Food>();
        while (c.moveToNext())
        {
            Food foodItem = DbHelper.createFoodFromCursor(c);
            foodList.add(foodItem);
        }
        db.close();

        return foodList;
    }


    // ----------------------------------------------------------
    /**
     * Deletes the food item from the database.
     *
     * @param food
     *            the item to be deleted
     */
    public void deleteFoodItem(Food food)
    {
        if (food != null)
        {

            Food foundFood = food;
            if (food.getId() == -1)
            {
                String query = DbHelper.createQuery(food);
                foundFood = DbHelper.retrieveFoodItem(query);

            }

            if (foundFood != null && foundFood.getId() != -1)
            {

                SQLiteDatabase db = HomeScreen.ourDataBase.open();
                db.execSQL(" DELETE FROM " + FinderDb.FOOD_TABLE + " WHERE "
                    + FinderDb.PK_FOOD + " = " + foundFood.getId());

                db.close();
            }

        }

    }

    /**
     * takes the info from the search results and puts it back into
     * a food object
     */

    private static Food createFoodFromCursor(Cursor c)
    {
        int place_idx = c.getColumnIndex(FinderDb.PLACE);
        int type_idx = c.getColumnIndex(FinderDb.TYPE);
        int meal_idx = c.getColumnIndex(FinderDb.MEAL);
        int cost_idx = c.getColumnIndex(FinderDb.COST);
        int rating_idx = c.getColumnIndex(FinderDb.RATING);
        int comment_idx = c.getColumnIndex(FinderDb.COMMENT);
        int meal_id_idx = c.getColumnIndex(FinderDb.PK_FOOD);

        Integer id = c.getInt(meal_id_idx);
        String meal = c.getString(meal_idx);
        String placeName = c.getString(place_idx);
        String type = c.getString(type_idx);
        Double cost = c.getInt(cost_idx)/ 100.00;
        String strCost = "" + cost;
        Float rating = c.getFloat(rating_idx);
        String comment = c.getString(comment_idx);

        Food food = new Food();
        food.setId(id);
        food.setMeal(meal);
        food.setPlace(placeName);
        food.setType(type);
        food.setCost(strCost);
        food.setRating(rating);
        food.setComment(comment);

        return food;
    }

}
