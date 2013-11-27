package com.example.denemecalismalari;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		Button  btnEtkinlikler = (Button)findViewById(R.id.btnEtkinlikler);
		Button btnGorevVer=(Button)findViewById(R.id.btnGorevVer);
		Button btnToplantilar=(Button)findViewById(R.id.btnToplantilar);
		Button btnKisiEkle=(Button)findViewById(R.id.btnKisiEkle);
		Button btnAjanda=(Button)findViewById(R.id.btnAjanda);
		
	
	    
	 
	    
		btnEtkinlikler.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
		
				
				startActivity(new Intent(MainActivity.this,EtkinlikOlusturArayuz.class));
			}
		});
		
		btnGorevVer.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				
				startActivity(new Intent(MainActivity.this,GorevVer.class));
			}
		});
		
		
		btnToplantilar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(MainActivity.this,Toplantilar.class));
			}
		});
		
		btnKisiEkle.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(MainActivity.this,KisiEkle.class));
			}
		});
		
		btnAjanda.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(MainActivity.this,Ajanda.class));
			}
		});
	}

	

}
