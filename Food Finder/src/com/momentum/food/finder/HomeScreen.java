package com.momentum.food.finder;

import android.widget.Toast;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import sofia.app.Screen;

// -------------------------------------------------------------------------
/**
 * the homescreen is the first screen initialized.  All other screens can be
 * accessed from here.
 *
 * @author spn2460
 * @version Nov 9, 2012
 */
public class HomeScreen
    extends Screen
{
    private static final String URL_LINK =
        "http://dl.dropbox.com/u/127748546/food_finder_populator.txt";

    /**
     * declaring a bridge between search and finder screen
     */
    public static FinderDb      ourDataBase;


    // "https://dl.dropbox.com/u/127748546/food_finder_populator.txt";

    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     */
    public void initialize()
    {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ourDataBase = new FinderDb(this);

        SharedPreferences pref = getSharedPreferences("pref28", 0);
        boolean first_run = pref.getBoolean("firstTime", true);
        SharedPreferences.Editor editor = pref.edit();
        // sets the boolean for first run populator //
        if (first_run)
        {
            editor.putBoolean("firstTime", false);
            editor.commit();
            Toast.makeText(
                this,
                "Please wait a moment while we populate your "
                    + "database for you",
                Toast.LENGTH_LONG).show();
            FirstRunPop pop = new FirstRunPop();
            pop.execute(URL_LINK, URL_LINK);

        }
        else
        {
            Toast.makeText(
                this,
                "Welcome back to food finder !!",
                Toast.LENGTH_LONG).show();
        }
    }


    // ----------------------------------------------------------
    /**
     * when the enter button is clicked on the welcome screen a new screen
     * appears (the enter screen) that contains the fields for creating a new
     * entry into the database.
     */
    public void enter_buttonClicked()
    {

        presentScreen(EnterScreen.class, false, new Food());

    }


    // ----------------------------------------------------------
    /**
     * allows the user to click both the enter button and enter label.
     */
    public void enter_labelClicked()
    {
        enter_buttonClicked();
    }


    // ----------------------------------------------------------
    /**
     * enter the search screen
     */
    public void search_buttonClicked()
    {
        presentScreen(SearchScreen.class);

    }


    // ----------------------------------------------------------
    /**
     * Allows the user to click either the search button or search text
     */
    public void search_labelClicked()
    {
        search_buttonClicked();
    }


    // ----------------------------------------------------------

    // ----------------------------------------------------------
    /**
     * enters the help screen.
     */
    public void help_buttonClicked()
    {
        presentScreen(HelpScreen.class);
    }


    // ----------------------------------------------------------
    /**
     * Allows the user to click either the help button or help label
     */
    public void help_labelClicked()
    {
        help_buttonClicked();
    }


}
