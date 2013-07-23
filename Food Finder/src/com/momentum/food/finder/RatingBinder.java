package com.momentum.food.finder;

import android.widget.RatingBar;
import android.view.View;
import android.widget.SimpleAdapter.ViewBinder;

// -------------------------------------------------------------------------
/**
 *  This class allows us to represent a rating bar in a list view format.
 *
 *  @author spn2460
 *  @version Dec 4, 2012
 */
public class RatingBinder
    implements ViewBinder
{

    /**
     * this method creates the rating bar view so the simple adapter can
     * recognize the rating bar value
     */
    public boolean setViewValue(View view, Object data, String text)
    {
        if (view instanceof RatingBar)
        {
            // since data object has different kinds of data //
            // it must be converted to a string //
            String rat_str = String.valueOf(data);

            float rating = Float.parseFloat(rat_str);

            RatingBar rbar = (RatingBar)view;
            rbar.setRating(rating);
            return true;
        }

        return false;
    }


}
