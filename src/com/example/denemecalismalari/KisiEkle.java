package com.example.denemecalismalari;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class KisiEkle  extends Activity {
Database database;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.kisi_ekle);
		
		final EditText txt_adi =(EditText)findViewById(R.id.kisi_ekle_uye_adi);
	    final EditText txt_soyadi =(EditText)findViewById(R.id.kisi_ekle_uye_soyadi);
	    final EditText txt_telno =(EditText)findViewById(R.id.kisi_ekle_tel_no);
	    final EditText txt_email=(EditText)findViewById(R.id.kisi_ekle_email);
		
		Button btn_kaydet=(Button)findViewById(R.id.kisi_ekle_btn_kaydet);
		Button btn_anamenu=(Button)findViewById(R.id.kisi_ekle_anamenu);
		database=new Database(this);
		
		final SQLiteDatabase db = database.getWritableDatabase();
	    final ContentValues cv = new ContentValues();
		
		btn_kaydet.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				try{
					cv.put("adi",txt_adi.getText().toString());
					cv.put("soyadi",txt_soyadi.getText().toString());
					cv.put("telno",txt_telno.getText().toString());
					cv.put("email",txt_email.getText().toString());
					db.insertOrThrow("Kisi_Ekle", null, cv);
					Toast.makeText(getApplication(), "Veriler Başarı ile Kayıt Edildi.", Toast.LENGTH_SHORT).show();
					txt_adi.setText("");
					txt_soyadi.setText("");
					txt_telno.setText("");
					txt_email.setText("");
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
				startActivity(new Intent(KisiEkle.this,MainActivity.class));
			}
		});
		
	}
}
