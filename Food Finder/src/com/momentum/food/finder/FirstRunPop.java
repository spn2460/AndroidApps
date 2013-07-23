package com.momentum.food.finder;

import java.util.ArrayList;
import java.net.MalformedURLException;
import android.os.AsyncTask;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

// -------------------------------------------------------------------------
/**
 * Based on a boolean value from the HomeScreen class This class connects to the
 * Internet and uploads a file full of restaurant menu items. It then reads from
 * that file and populates the database.
 *
 * @author spn2460
 * @version Dec 11, 2012
 */
public class FirstRunPop
    extends AsyncTask<String, Integer, Void>
{


    // ----------------------------------------------------------
    /**
     * gets data from an online text file to populate the data base on on first
     * run only.
     */
    @Override
    protected Void doInBackground(String... urls)
    {
        // If you want multiple urls passed:

        for (String url : urls)
        {
            ArrayList<String> lines = new ArrayList<String>();

            try
            {
                URL popUrl = new URL(url);

                // Read all the text returned by the server
                BufferedReader in =
                    new BufferedReader(new InputStreamReader(
                        popUrl.openStream()));
                String str;
                while ((str = in.readLine()) != null)
                {
                    // str is one line of text; readLine() strips the newline
                    // character(s)
                    lines.add(str);
                }
                in.close();

            }
            catch (MalformedURLException e)
            {
                System.out.println("MalformedURL!");
                System.out.println(e.getMessage());
            }
            catch (IOException e)
            {
                System.out.println("IOException!!");
                System.out.println(e.getMessage());
            }

            //System.out.println("Lines: " + lines);

            for (String line : lines)
            {
                this.parseFoodFromString(line);
            }
        }
        return null;
    }

/**
 * gets the necessary string values from the file to insert into the database
 */
    private void parseFoodFromString(String line)
    {
        String[] split = line.split("[|]");

        if (split.length >= 3)
        {
            Food food = new Food();
            food.setPlace(split[0]);
            food.setMeal(split[1]);
            food.setCost(split[2]);

            DbHelper.addToDb(food);
        }
    }
}
