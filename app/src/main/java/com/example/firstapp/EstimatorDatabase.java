package com.example.firstapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class EstimatorDatabase extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="Estimator.db";
    public static final String TABLE_NAME="covid19";
    public static final String COL_1="ID";
    public static final String COL_2="region_name";
    public static final String COL_3="avg_age";
    public static final String COL_4="avg_dailyincome";
    public static final String COL_5="avg_dailypopulation";
    public static final String COL_6="period_type";
    public static final String COL_7="timeto_elapse";
    public static final String COL_8="reportedcases";
    public static final String COL_9="hospitalbeds";
    public static final String COL_10="impact_CI";
    public static final String COL_11="impact_Infections";
    public static final String COL_12="impact_CBRT";
    public static final String COL_13="impact_HBeds";
    public static final String COL_14="impact_ICU";
    public static final String COL_15="impact_Ventilators";
    public static final String COL_16="impact_dollars";
    public static final String COL_17="severe_CI";
    public static final String COL_18="severe_Infections";
    public static final String COL_19="severe_CBRT";
    public static final String COL_20="severe_HBeds";
    public static final String COL_21="severe_ICU";
    public static final String COL_22="severe_Ventilators";
    public static final String COL_23="severe_dollars";

    public EstimatorDatabase(Context context) {
         super(context,DATABASE_NAME,null,1);}
      public boolean insertNewData(String regname, String age, Integer income, Integer population, String period, Integer elapsedTime,
                                   Integer reportcases, Integer beds, Integer imp_CI, Integer imp_infection, Integer imp_hosBeds, Integer imp_CBRT,
                                   Integer imp_ICU, Integer imp_vent, Integer imp_dollars, Integer severe_CI, Integer severe_infection, Integer severe_hosBeds,
                                   Integer severe_ICU, Integer severe_vent, Integer severe_CBRT, Integer severe_dollars ) {
         SQLiteDatabase db= this.getWritableDatabase();
          ContentValues cv=new ContentValues();
          cv.put(COL_2,regname);
          cv.put(COL_3,age);
          cv.put(COL_4,income);
          cv.put(COL_5,population);
          cv.put(COL_6,period);
          cv.put(COL_7,elapsedTime);
          cv.put(COL_8,reportcases);
          cv.put(COL_9,beds);
          cv.put(COL_10,imp_CI);
          cv.put(COL_11,imp_infection);
          cv.put(COL_12,imp_CBRT);
          cv.put(COL_13,imp_hosBeds);
          cv.put(COL_14,imp_ICU);
          cv.put(COL_15,imp_vent);
          cv.put(COL_16,imp_dollars);
          cv.put(COL_17,severe_CI);
          cv.put(COL_18,severe_infection);
          cv.put(COL_19,severe_CBRT);
          cv.put(COL_20,severe_hosBeds);
          cv.put(COL_21,severe_ICU);
          cv.put(COL_22,severe_vent);
          cv.put(COL_23,severe_dollars);
          long result=db.insert(TABLE_NAME,null,cv);
          if(result==-1)
              return  false;
          else
              return  true;

      }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TABLE_NAME+"(ID INTEGER PRIMARY KEY AUTOINCREMENT, REGION_NAME TEXT,AVEREAGE_AGE TEXT, DAILYINCOME INTEGER, POPULATION INTEGER, PERIOD TEXT,ELAPSED_TIME INTEGER,REPORTED_CASES INTEGER,HOSPITAL_BEDS INTEGER, IMPACT_CI INTEGER, IMPACT_INFECTION INTEGER, IMPACT_CBRT INTEGER, IMPACT_ICU INTEGER, IMPACT_VENTILATORS INTEGER, IMPACT_DOLLARS INTEGER,SEVERE_CI INTEGER, SEVERE_INFECTION INTGER, SEVERE_CBRT INTEGER, SEVERE_ICU INTEGER, SEVERE_VENTILATORS INTEGER, SEVERE_DOLLARS INTEGER ) ");
        onCreate(db);
    }
    public Cursor getAllRecords() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor records = db.rawQuery("select * from " + TABLE_NAME, null);
        return records;
    }
    public int deleteRecord(String id) {
        SQLiteDatabase db=this.getWritableDatabase();
        return db.delete(TABLE_NAME,"ID=?",new String[]{id});
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
