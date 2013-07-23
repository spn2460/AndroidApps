package com.momentum.food.finder;

import java.text.DecimalFormat;
import android.widget.Button;
import android.widget.Toast;
import android.widget.RatingBar;
import android.widget.EditText;
import sofia.app.Screen;

// -------------------------------------------------------------------------
/**
 * this is the enter screen where users will enter the data that creates a new
 * food object, and then by clicking the upload button, the data will be stored
 * to the database.
 *
 * @author spn2460
 * @version Nov 13, 2012
 */
public class EnterScreen
    extends Screen
{

    private EditText  enter_place;
    private EditText  enter_type;
    private EditText  enter_item;
    private EditText  enter_cost;
    private RatingBar ratingBar1;
    private EditText  enter_comment;
    private boolean   edit;
    private Food      food;
    private Button    add_to_database;


    // ----------------------------------------------------------
    /**
     * initializes the enter screen
     *
     * @param bool
     *            a boolean variable used to check if the edit screen is calling
     *            the method or the enter screen is calling if it is enter
     *            screen, then we execute the add to db if it is the edit
     *            screen, then we use the update db command
     * @param new_food
     *            a food object being passed in. If the food is null, the fields
     *            can be added. it the food is not null, the boolean will be
     *            true and then the enter screen text views can be set to the
     *            values contained in the food item.
     */
    public void initialize(boolean bool, Food new_food)
    {
        edit = bool;
        food = new_food;
        if (edit)
        {
            add_to_database.setText("Update Database");
            enter_place.setText(food.getPlace());
            enter_item.setText(food.getMeal());
            DecimalFormat decForm = new DecimalFormat("#.00");
            String str_cost = decForm.format(food.getCost() / 100.0);
            enter_cost.setText(str_cost);
            enter_type.setText(food.getType());
            enter_comment.setText(food.getComment());
            ratingBar1.setRating(food.getRating());
        }

    }


    /**
     * initializes the enter screen
     */
    public void initialize()
    {
        // empty by design
    }


    // ----------------------------------------------------------
    /**
     * adds the food object to the database
     */
    public void add_to_databaseClicked()
    {

        // fills all the fields for a new food item //

        String tempPlace = Capitalizer.format(enter_place.getText().toString());
        String tempItem = Capitalizer.format(enter_item.getText().toString());
        String tempCost = enter_cost.getText().toString();

        if (tempPlace.equals("") || tempItem.equals("") || tempCost.equals(""))
        {
            Toast.makeText(
                this,
                "Place, Item, and Cost are a REQUIRED fields",
                Toast.LENGTH_LONG).show();
        }
        else
        {
            Food new_food = new Food();

            new_food.setPlace(tempPlace);
            new_food.setType(enter_type.getText().toString());
            new_food.setMeal(tempItem);
            new_food.setCost(tempCost);
           // new_food.setCost(Float.parseFloat(tempCost));
            new_food.setRating(ratingBar1.getRating());
            new_food.setComment(enter_comment.getText().toString());


            if (!edit)
            {
                boolean success = DbHelper.addToDb(new_food);

                if (success)
                {
                    String result = DbHelper.makeToast(new_food);
                    Toast.makeText(this, "" + result, Toast.LENGTH_LONG).show();
                }

                else
                {
                    Toast.makeText(
                        this,
                        "this meal at this restaurant already "
                            + "exists in the database",
                        Toast.LENGTH_LONG).show();

                }
                finish();

            }

            else
            {

                // the food item from the list before being updated //

                boolean edited = DbHelper.updateDb(food, new_food);

                if (edited)
                {
                    Toast.makeText(
                        this,
                        food.getPlace() + " " + food.getMeal()
                            + " has been successfuly updated",
                        Toast.LENGTH_LONG).show();
                }

                finish();
                ListPopulator.populate(null);
                presentScreen(EditScreen.class);
            }


        }

    }

}
