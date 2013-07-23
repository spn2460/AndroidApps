package com.momentum.food.finder;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

// -------------------------------------------------------------------------
/**
 * Creates a data base for holding ratings for food at local eating
 * establishments.
 *
 * @author Shawn P Neuman
 * @author Eric Geunther
 * @author Ryan McCloskey
 * @author Souly Ayoub
 * @version Oct 30, 2012
 */

public class FinderDb
{
    /**
     * protected database
     */
    // Database
    protected static final String DATABASE_NAME    = "Finder_DB";

    //
    /**
     * Database Table for place
     */
    public static final String  PLACE_TABLE      = "Place";
    /**
     * Database Table for food objects
     */
    public static final String  FOOD_TABLE       = "Food";

    /**
     * Table columns for place id
     */
    public static final String  PK_PLACE         = "_place_id";
    /**
     * Table columns for place name
     */
    public static final String  PLACE            = "placeName";

    // Columns in food
    /**
     * Table columns for food id
     */
    public static final String  PK_FOOD          = "_food_id";
    /**
     * Table columns for food foreign key to place table
     */
    public static final String  PLACE_IN_FOOD    = "placeId";
    /**
     * Table columns for food type of food
     */
    public static final String  TYPE             = "typeName";
    /**
     * Table columns for food name of meal
     */
    public static final String  MEAL             = "mealName";
    /**
     * Table columns for food cost of item
     */
    public static final String  COST             = "cost";
    /**
     * Table columns for food rating of food
     */
    public static final String  RATING           = "rating";
    /**
     * Table columns for food additional comments
     */
    public static final String  COMMENT          = "comment";

    private static final int    DATABASE_VERSION = 5;

    private DbHelper            ourHelper;
    private final Context       ourContext;


    // private SQLiteDatabase ourDatabase;

    private static class DbHelper
        extends SQLiteOpenHelper
    {

        public DbHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }


        @Override
        public void onCreate(SQLiteDatabase db)
        {
            db.execSQL("CREATE TABLE " + PLACE_TABLE + " (" + PK_PLACE
                + " INTEGER PRIMARY KEY AUTOINCREMENT, " + PLACE
                + " TEXT NOT NULL UNIQUE);");

            db.execSQL("CREATE TABLE " + FOOD_TABLE + " ( " + PK_FOOD
                + " INTEGER PRIMARY KEY ASC AUTOINCREMENT, " + PLACE_IN_FOOD
                + " INTEGER REFERENCES " + PLACE_TABLE + "(" + PK_PLACE + "),"
                + TYPE + " TEXT, " + MEAL + " TEXT NOT NULL, " + COST
                + " REAL NOT NULL, " + RATING + " REAL, " + COMMENT
                + " TEXT );");

        }


        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {

            db.execSQL("DROP TABLE IF EXISTS " + PLACE_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + FOOD_TABLE);
            onCreate(db);
        }

    }


    // ----------------------------------------------------------
    /**
     * Create a new FinderDb object.
     *
     * @param c
     *            the context being passed in
     */
    public FinderDb(Context c)
    {
        ourContext = c;
    }


    // ----------------------------------------------------------
    /**
     * opens our database, and sets our context and helper from private to
     * public.
     *
     * @return a new Finder database object with columns as assigned in the
     *         DbHelper constructor
     */
    public SQLiteDatabase open()
    {
        ourHelper = new DbHelper(ourContext);
        return ourHelper.getWritableDatabase();

    }


    // ----------------------------------------------------------
    /**
     * closes the open database.
     */
    public void close()
    {
        ourHelper.close();
    }

}
