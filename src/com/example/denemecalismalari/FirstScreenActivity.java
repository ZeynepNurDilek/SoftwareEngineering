package com.example.realmancala;

import core.MancalaDB;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

public class FirstScreenActivity extends Activity {
	String diff;
	String stone;
	String win;
	String draw;
	String lose;
	String isWork = "Undetermined";

	private void doFirstRun() {
		SharedPreferences settings = getSharedPreferences("permissions",
				MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		if (settings.getBoolean("isFirstRun", true)) {

			editor.putBoolean("isFirstRun", false);
			editor.commit();
			AddFirstRun();
			Toast.makeText(FirstScreenActivity.this, isWork, Toast.LENGTH_SHORT)
					.show();
		} else {
			
		}
	}

	public void AddFirstRun() {
		diff = "3";
		stone = "4";
		win = "0";
		draw = "0";
		lose = "0";

		boolean didItWork = true;
		try {
			MancalaDB entry = new MancalaDB(FirstScreenActivity.this);
			entry.open();
			entry.createEntry(diff, stone, win, draw, lose);
			entry.close();
		} catch (Exception e) {
			didItWork = false;
			isWork = "Veri tabaný oluþturulamadý!";

		} finally {
			if (didItWork) {
				isWork = "Ýþlem baþarýlý";
			}
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Remove title bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		// Remove notification bar
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		doFirstRun();//Uygulamayý ilk çalýþtýrma 
		setContentView(R.layout.activity_mancala_first_screen);

		// components initializing
		final Button btnCH = (Button) findViewById(R.id.btnCH);
		final Button btnHH = (Button) findViewById(R.id.btnHH);
		final Button btnSettingsInFirstScreen = (Button) findViewById(R.id.btnSettings);
		final Button btnStatsInFirstScreen = (Button) findViewById(R.id.btnStats);
		final Button btnHowToPlay = (Button) findViewById(R.id.btnHowTo);

	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	public void startActivityPlayGame(View v) {
		Intent playGame = new Intent(FirstScreenActivity.this,
				PlayGameActivity.class);
		switch (v.getId()) {
		case R.id.btnCH:
			playGame.putExtra("gameMode", true);
			startActivity(playGame);
			break;
		case R.id.btnHH:
			playGame.putExtra("gameMode", false);
			startActivity(playGame);
			break;
		default:
			break;
		}
	}

	public void startActivityGameOptions(View v) {
		Intent gameOptions = new Intent(FirstScreenActivity.this,
				GameOptionsActivity.class);
		startActivity(gameOptions);
		// FirstScreenActivity.this.finish();
	}

	public void startActivityStats(View v) {
		Intent stats = new Intent(FirstScreenActivity.this, StatsActivity.class);
		startActivity(stats);
	}

	public void startActivityHowTo(View v) {
		Intent how = new Intent(FirstScreenActivity.this, HowActivity.class);
		startActivity(how);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK)// donanýmsal olarak geri butonuna
		// basýlýrsa
		{

			FirstScreenActivity.this.finish();
			return true;
		}
		return false;
	}
}
