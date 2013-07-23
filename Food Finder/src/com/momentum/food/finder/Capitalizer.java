package com.momentum.food.finder;

// -------------------------------------------------------------------------
/**
 * Capitalizes the first letter of each word. Used to ensure consistency in data
 * input both for visual esthetics and for search consistency
 *
 * @author Souly the Amazing
 * @author Shawn P Neuman (if it says Shawn P, It's gotta be me) spn2460
 * @version Dec 3, 2012
 */

public class Capitalizer
{
    // ----------------------------------------------------------
    /**
     * capitalizes the first letter in each word.
     *
     * @param str
     *            the string being passed in
     * @return the capitalized word(s)
     */
    public static String format(String str)
    {
        if (str == null ||str.equals(""))
        {
            return "";
        }
        String cleanWord = "";
        String[] split = str.split(" ");
        for (String word : split)
        {
            if (word.equals(""))
            {
                continue;
            }
            String first = word.substring(0, 1).toUpperCase();
            String rest = word.substring(1).toLowerCase();
            cleanWord += first + rest + " ";
        }
        return cleanWord.replaceAll("[^a-zA-Z0-9 ]", "").trim();
    }

    // ----------------------------------------------------------
    /**
     * takes a decimal string and turns it to an integer string.
     * @param str the string number being passed in
     * @return cleanNumber a new string representation of an integer
     */
    public static String numberFormat(String str)
    {
        if (str == null || str.equals(""))
        {
            return "";
        }
        String cleanNumber = "";
        String[] split = str.split("[.]");
        if (str.substring(0, 1).equals("[.]"))
        {
            if (split[0].length()>1)
            {
                cleanNumber = split[0];
            }
            else
            {
                cleanNumber = split[0] + "0";
            }
        }
        else if (split.length > 1)
        {
            String first = split[0];
            String rest = split[1];
            if (split[1].length() > 1)
            {
                cleanNumber = first + rest;
            }
            else
            {
                cleanNumber = first +  rest + "0";
            }

        }
        else
        {
            cleanNumber = split[0] + "00";
        }

        return cleanNumber;
    }
}
