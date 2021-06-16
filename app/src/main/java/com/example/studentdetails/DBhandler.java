package com.example.studentdetails;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class DBhandler extends SQLiteOpenHelper {

    public DBhandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "Student.db", factory, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {         // CREATE
        String table = "CREATE TABLE Student (id INTEGER PRIMARY KEY , name TEXT)";
        db.execSQL(table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP Table IF EXISTS Student");
        onCreate(db);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public String loadHandler() {          // to print table data ----
        String result = "";
        String query = "Select*FROM Student";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        while(cursor.moveToNext()){
            int result_0 = cursor.getInt(0);
            String result_1 = cursor.getString(1);
            result += String.valueOf(result_0)+""+ result_1 + System.lineSeparator();
        }
        cursor.close();
        db.close();
        return result;
    }


    public void addHandler(Student student) {       //to add data into table  - ADD
        ContentValues values = new ContentValues();
        values.put("id", student.getId());
        values.put("name",student.getName());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert("Student",null,values);
        db.close();
    }

    public Student findHandler(String studentname) {    //find data by condition
        String query = "Select *FROM Student WHERE name = '"+studentname+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        Student student = new Student();
        if(cursor.moveToFirst()){
            cursor.moveToFirst();
            student.setId(Integer.parseInt(cursor.getString(0)));
            student.setName(cursor.getString(1));
            cursor.close();
        }else{
            student=null;
        }
        db.close();
        return student;
    }

    public boolean deleteHandler(int ID) {   //find and delete data -- DELETE
        boolean result = false;
        String query="SELECT * FROM Student WHERE id = "+ID+"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        Student student = new Student();
        if(cursor.moveToFirst()){
            student.setId(Integer.parseInt(cursor.getString(0)));
            db.delete("Student","id=?",new String[]{String.valueOf(student.getId())});
            cursor.close();
            result=true;
        }
        db.close();
        return  result;
    }


    public boolean updateHandler(int ID, String name) {  // find and update -- UPDATE
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id",ID);
        values.put("name",name);
        return db.update("Student",values,"id="+ID+"",null) > 0;
    }
}

