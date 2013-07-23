package com.momentum.food.finder;

import android.widget.RatingBar;
import android.app.SearchManager;
import android.content.Intent;
import android.content.DialogInterface;
import android.content.Context;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.AdapterView;
import android.widget.Toast;
import android.app.AlertDialog;
import android.widget.TextView;
import android.view.View;
import android.os.Bundle;
import android.widget.SimpleAdapter;
import android.app.ListActivity;

// -------------------------------------------------------------------------
/**
 * The Result screen class contains the code to create an output view in an
 * android screen. The "two_line" reference is actually a three line text view,
 * with an id for each text view The simple adapter takes the map as a parameter
 * and then translates the items in the map from the key, to a text view with
 * the given id. The adapter continues to generate new vies until the map is
 * empty
 *
 * @author spn2460
 * @version Nov 28, 2012
 */
public class ResultScreen
    extends ListActivity
{

    //private SimpleAdapter ad;
    private String the_place;
   // private Intent es;


    /**
     * creates  publicly accessible food item
     */
    public static Food searched_food;

    // ----------------------------------------------------------

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
        setContentView(R.layout.two_line);
        makeAdapter();

        ListView lv = getListView();
        Context context = getWindow().getContext();
        lv.setOnItemClickListener(new ItemListener(context));

    }

    // ----------------------------------------------------------
    /**
     * Creates a list view adapter to populate the custom view.
     */
    public void makeAdapter()
    {
        if (ListPopulator.getList().size() == 0)
        {
            Toast.makeText(this, "There are no matches to your search",
                Toast.LENGTH_LONG).show();
        }

        SimpleAdapter adapter =
            new SimpleAdapter(
                this,
                ListPopulator.getList(),
                R.layout.custom_view,
                new String[] { "restaurant", "meal", "cost", "rating", "type",
                    "comment" },
                new int[] { R.id.place_line, R.id.meal_line, R.id.cost_line,
                    R.id.rating_line, R.id.type_line, R.id.comment_line });

        adapter.setViewBinder(new RatingBinder());
        setListAdapter(adapter);
    }

    private class ItemListener
        implements OnItemClickListener
    {
        private static final int PLACE_IDX = 0;
        // added to for edit from search //
        private static final int MEAL_IDX    = 1;
        private static final int TYPE_IDX    = 2;
        private static final int COST_IDX    = 3;
        private static final int COMMENT_IDX = 4;
        private static final int RATING_IDX  = 6;
        // end of add //
        private Context          context;


        public ItemListener(Context context)
        {
            this.context = context;
        }


        public void onItemClick(
            AdapterView<?> parent,
            View view,
            int pos,
            long id)
        {
            searched_food = new Food();

            RelativeLayout rl = (RelativeLayout)view;
            TextView tv = (TextView)rl.getChildAt(PLACE_IDX);
            // added for edit from search //
            TextView place = (TextView)rl.getChildAt(PLACE_IDX);
            TextView meal = (TextView)rl.getChildAt(MEAL_IDX);
            TextView type = (TextView)rl.getChildAt(TYPE_IDX);
            TextView cost = (TextView)rl.getChildAt(COST_IDX);
            TextView comment = (TextView)rl.getChildAt(COMMENT_IDX);
            RatingBar rating_bar = (RatingBar)rl.getChildAt(RATING_IDX);

            searched_food.setPlace(place.getText().toString());
            searched_food.setMeal(meal.getText().toString());
            searched_food.setType(type.getText().toString());
            searched_food.setComment(comment.getText().toString());
            searched_food.setCost(cost.getText().toString()
                .substring(1));

            searched_food.setRating(rating_bar.getRating());
            // end of add /
            the_place = tv.getText().toString();

            AlertDialog.Builder pop_up_box = new AlertDialog.Builder(context);
            pop_up_box.setTitle("Search the Web, or Make Changes");
            final String[] options = {"Search the Web", "Edit", "Delete", "Cancel"};
            pop_up_box.setItems(options, new DialogInterface.OnClickListener()
            {

                public void onClick(DialogInterface dialog, int which)
                {
                    if (options[which].equals(options[0]))
                    {

                        Intent search = new Intent(Intent.ACTION_WEB_SEARCH);
                        search.putExtra(SearchManager.QUERY, the_place
                            + " restaurant near me");

                        startActivity(search);
                    }

                    else if (options[which].equals(options[1]))
                    {
                        // add edit from search //

                        Intent es = new Intent(ResultScreen.this, EditScreen.class);
                        startActivity(es);
                        finish();


                    }

                    else if (options[which].equals(options[2]))
                    {
                        AlertDialog.Builder yes_no = new AlertDialog.Builder(context);
                        yes_no.setTitle("Are You Sure?");
                        final String[] options1 = {"yes", "no"};
                        yes_no.setItems(options1, new DialogInterface.OnClickListener()
                        {

                            public void onClick(DialogInterface dialog1, int choice)
                            {
                                if (options1[choice].equals(options1[0]))
                                {
                                    deleteDataBaseEntry();

                                }

                                else
                                {
                                    dialog1.cancel();

                                }
                            }

                        });
                        AlertDialog dialog1 = yes_no.create();
                        dialog1.show();



                    }
                    else
                    {
                        dialog.cancel();
                    }

                }
            });
            AlertDialog dialog = pop_up_box.create();
            dialog.show();

        }

    }


 // ----------------------------------------------------------
    /**
     * deletes from the database.
     */
    public void deleteDataBaseEntry()
    {


        DbHelper helper = new DbHelper();
        helper.deleteFoodItem(searched_food);
        ListPopulator.populate(SearchScreen.food);
        makeAdapter();

    }


}
