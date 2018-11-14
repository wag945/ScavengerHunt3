package com.example.bill.scavengerhunt3.entity.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.bill.scavengerhunt3.entity.entity.UserProfile;

import java.util.ArrayList;

public class UserProfilePersistence implements IPersistence{

    public DatabaseAccess databaseAccess;

    public UserProfilePersistence(Context context){this.databaseAccess = new DatabaseAccess(context);}

    @Override
    public void insert(Object o){

        //Cast the generic object to have access to the user info
        UserProfile userProfile = (UserProfile) o;

        SQLiteDatabase sqLiteDatabase = databaseAccess.getWritableDatabase();


        //The ContentValues object create a map of valuse, where the columns are the keys
        ContentValues contentValues = new ContentValues();

        contentValues.put(UserProfileTable.COLUMN_NAME_FIRSTNAME, userProfile.getFirstName());
        contentValues.put(UserProfileTable.COLUMN_NAME_LASTNAME, userProfile.getLastName());
        contentValues.put(UserProfileTable.COLUMN_NAME_USERNAME, userProfile.getUserName());
        contentValues.put(UserProfileTable.COLUMN_NAME_BIRTHDAY, userProfile.getBirthday());
        contentValues.put(UserProfileTable.COLUMN_NAME_PHONENUMBER, userProfile.getPhoneNumber());
        contentValues.put(UserProfileTable.COLUMN_NAME_EMAIL, userProfile.getEmail());
        contentValues.put(UserProfileTable.COLUMN_NAME_PASSWORD, userProfile.getPassword());

        Log.d("content values", "content values " + contentValues.toString());

        //Insert the ContentValues into the UserProfile table
        sqLiteDatabase.insert(UserProfileTable.TABLE_NAME, null, contentValues);
        Log.d("database after insert", "database values: " + sqLiteDatabase.toString());

        sqLiteDatabase.close();
    }

    @Override
    public void delete(Object o){
        UserProfile userProfile = (UserProfile) o;

        //Define which column will be the parameter for deleting the record
        String selection = UserProfileTable.COLUMN_NAME_USERNAME + "LIKE ? ";

        //Arguments must be identified in the placeholder order
        String[] selectionArgs = {userProfile.getUserName().trim()};

        //Get database instance
        SQLiteDatabase sqLiteDatabase = databaseAccess.getWritableDatabase();
        sqLiteDatabase.delete(UserProfileTable.TABLE_NAME, selection, selectionArgs);
    }

    @Override
    public void edit(Object o){

    }

    @Override
    public ArrayList getDataFromDB(){
        //create ArraList of userprofiles
        ArrayList<UserProfile> userProfiles = null;

        //Instantiate the database
        SQLiteDatabase sqLiteDatabase = databaseAccess.getWritableDatabase();

        //Gather all the records found for the UserProfile table
        Cursor cursor = sqLiteDatabase.rawQuery(UserProfileTable.select(), null);
        Log.d("cursor", cursor.toString());


        //it will iterate since the first record gathered from the database
        cursor.moveToFirst();

        //Check if there exists other records in the cursor
        userProfiles = new ArrayList<>();

        if(cursor !=null && cursor.moveToFirst()){
            do{
                String firstName = cursor.getString(cursor.getColumnIndex(UserProfileTable.COLUMN_NAME_FIRSTNAME));
                String lastName = cursor.getString(cursor.getColumnIndex(UserProfileTable.COLUMN_NAME_LASTNAME));
                String userName = cursor.getString(cursor.getColumnIndex(UserProfileTable.COLUMN_NAME_USERNAME));
                String birthday = cursor.getString(cursor.getColumnIndex(UserProfileTable.COLUMN_NAME_BIRTHDAY));
                String phoneNumber = cursor.getString(cursor.getColumnIndex(UserProfileTable.COLUMN_NAME_PHONENUMBER));
                String email = cursor.getString(cursor.getColumnIndex(UserProfileTable.COLUMN_NAME_EMAIL));
                String password = cursor.getString(cursor.getColumnIndex(UserProfileTable.COLUMN_NAME_PASSWORD));

                UserProfile userProfile = new UserProfile(firstName, lastName, userName, birthday,
                        phoneNumber, email,password);

                userProfiles.add(userProfile);
            } while(cursor.moveToNext());
        }
        return userProfiles;

    }

}
