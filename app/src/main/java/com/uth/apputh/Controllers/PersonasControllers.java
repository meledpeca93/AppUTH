package com.uth.apputh.Controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.uth.apputh.Database.DatabaseHelper;
import com.uth.apputh.Models.Personas;

public class PersonasControllers {

        private final DatabaseHelper databaseHelper;

        public PersonasControllers(Context context) {
            databaseHelper =  new databaseHelper(context);
        }

}
