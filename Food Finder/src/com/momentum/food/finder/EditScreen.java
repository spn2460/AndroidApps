package com.momentum.food.finder;

import sofia.app.Screen;
import java.text.DecimalFormat;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.RatingBar;
import android.os.Bundle;

// -------------------------------------------------------------------------
/**
 * presents a result screen with the entire database listed. a selected item
 * will bring up a dialog box for editing or outright deletion.
 *
 * @author spn2460
 * @version Dec 4, 2012
 */
public class EditScreen
    extends Screen
{
    /**
     * static food object for retrieving data from the on click functions
     */
    public static Food    edit_food;
    private EditText  edit_place;
    private EditText  edit_type;
    private EditText  edit_item;
    private EditText  edit_cost;
    private RatingBar ratingBar1;
    private EditText  edit_comment;
    //private boolean   edit;

    //private Button    update;

    /**
     * This method creates a view from a simple adapter The view is re -
     * populated until the map is empty more lines can be added if needed
     *
     * @param savedInstanceState
     *            the saved instance state of the screen
     */
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.two_line);
        setContentView(R.layout.editscreen);
        edit_place.setText(ResultScreen.searched_food.getPlace());
        edit_item.setText(ResultScreen.searched_food.getMeal());
        DecimalFormat decForm = new DecimalFormat("#.00");
        String str_cost = decForm.format(ResultScreen.searched_food.getCost() / 100.0);
        edit_cost.setText(str_cost);
        edit_type.setText(ResultScreen.searched_food.getType());
        edit_comment.setText(ResultScreen.searched_food.getComment());
        ratingBar1.setRating(ResultScreen.searched_food.getRating());


    }




    // ----------------------------------------------------------
    /**
     * after changing the info from the edit screen, this method updates the
     * database, and the re-populates the list with the updated info
     */
    public void edit_screen_updateClicked()
    {
        edit_food = new Food();
        String tempPlace = Capitalizer.format(edit_place.getText().toString());
        String tempItem = Capitalizer.format(edit_item.getText().toString());
        String tempCost = edit_cost.getText().toString();

        if (tempPlace.equals("") || tempItem.equals("") || tempCost.equals(""))
        {
            Toast.makeText(
                this,
                "Place, Item, and Cost are a REQUIRED fields",
                Toast.LENGTH_LONG).show();
        }
        else
        {


            edit_food.setPlace(edit_place.getText().toString());
            edit_food.setType(edit_type.getText().toString());
            edit_food.setMeal(tempItem);
            edit_food.setCost(tempCost);
           // new_food.setCost(Float.parseFloat(tempCost));
            edit_food.setRating(ratingBar1.getRating());
            edit_food.setComment(edit_comment.getText().toString());
        }
        boolean edited = DbHelper.updateDb(ResultScreen.searched_food, edit_food);

        if (edited)
        {
            Toast.makeText(
                this,
                ResultScreen.searched_food.getPlace() + " "
                + ResultScreen.searched_food.getMeal()
                    + " has been successfuly updated",
                Toast.LENGTH_LONG).show();
        }

        finish();
        ListPopulator.populate(SearchScreen.food);
        presentScreen(ResultScreen.class);
    }
}
