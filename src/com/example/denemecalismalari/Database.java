package com.example.denemecalismalari;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {
	final static String name= "DBName4";

     
	public Database(Context context) {
	
		super(context, name, null, 1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		
		db.execSQL("create Table Kisi_Ekle(id integer primary key ,adi Text,soyadi Text,telno Text,email Text);");
		db.execSQL("create Table Gorevler(id integer primary key ,adi Text,aciklama Text,tarih Text);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

}
