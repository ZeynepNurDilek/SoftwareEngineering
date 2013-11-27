package com.example.denemecalismalari;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EtkinlikOlusturArayuz extends Activity{
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.etkinlikolustur);
		
		
		final EditText ead= (EditText)findViewById(R.id.widget36);
		
		final EditText aciklama=(EditText)findViewById(R.id.widget41);
		
		Button btnKaydet =(Button)findViewById(R.id.kaydet);
		
		btnKaydet.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "Etkinlik Adi :"+ead.getText().toString()+"\n"+"aciklama :"+aciklama.getText().toString(), Toast.LENGTH_SHORT).show();
			}
		});
	}

}
