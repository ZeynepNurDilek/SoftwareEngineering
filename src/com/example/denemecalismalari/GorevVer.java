package com.example.denemecalismalari;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

public class GorevVer   extends Activity {
	Database database;
	 List<String> list=new ArrayList<String>();
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gorevver);
		
		final EditText txt_gorev_tanimi = (EditText)findViewById(R.id.gorevTanimi);
		final TimePicker time = (TimePicker)findViewById(R.id.timePicker1);
		Button btn_kaydet=(Button)findViewById(R.id.btnKaydet);
		Button btn_anamenu=(Button)findViewById(R.id.anamenudon);
		database=new Database(this);
		
		String selectQuery = "SELECT  * FROM Kisi_Ekle";
	    SQLiteDatabase db = database.getReadableDatabase();
	    Cursor cursor = db.rawQuery(selectQuery, null);
	    
	    //looping through all rows and adding to list
	    
	    while (cursor.moveToNext()) {
	    	Toast.makeText(getApplicationContext(), cursor.getString(cursor.getColumnIndex("adi")), Toast.LENGTH_SHORT).show();
	    	 
	    	list.add( cursor.getString(cursor.getColumnIndex("adi")).toString());
	    }  
	
	    final Spinner spinner = (Spinner)findViewById(R.id.spinner);
	    
	    ArrayAdapter<String> adp= new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,list);
		adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adp);



		final SQLiteDatabase db_gorevler = database.getWritableDatabase();
		final ContentValues cv = new ContentValues();


btn_kaydet.setOnClickListener(new View.OnClickListener() {
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		try{
			cv.put("adi",spinner.getSelectedItem().toString());
			cv.put("aciklama",txt_gorev_tanimi.getText().toString());
			cv.put("tarih",String.valueOf(time.getCurrentHour()));
			
			db_gorevler.insertOrThrow("Gorevler", null, cv);
			//Toast.makeText(getApplication(), spinner.getSelectedItem().toString()+"'ye "+txt_gorev_tanimi.getText().toString()+" görevi verilmiştir.", Toast.LENGTH_SHORT).show();
			txt_gorev_tanimi.setText("");
			
		}catch(Exception e)
		{
			Toast.makeText(getApplication(), e.getMessage(), Toast.LENGTH_SHORT).show();
		}
	}
});

btn_anamenu.setOnClickListener(new View.OnClickListener() {
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		startActivity(new Intent(GorevVer.this,MainActivity.class));
	}
});
	
	}
}
