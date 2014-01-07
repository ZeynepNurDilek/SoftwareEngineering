package com.example.realmancala;

import core.MancalaDB;
import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import android.widget.ToggleButton;

public class GameOptionsActivity extends Activity {
	String diff;
	String stone;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Remove title bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// Remove notification bar
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_mancala_gameoptions);
		getDefaultSettings();

		final ToggleButton tgEasy = (ToggleButton) findViewById(R.id.toggleEasyOnOff);
		final ToggleButton tgMedium = (ToggleButton) findViewById(R.id.toggleMediumOnOff);
		final ToggleButton tgHard = (ToggleButton) findViewById(R.id.toggleHardOnOff);

		final ToggleButton tg4 = (ToggleButton) findViewById(R.id.toggleStoneFour);
		final ToggleButton tg5 = (ToggleButton) findViewById(R.id.toggleStoneFive);
		final ToggleButton tg6 = (ToggleButton) findViewById(R.id.toggleStoneSix);

		switch (Integer.parseInt(diff)) {
		case 1:
			tgEasy.setChecked(true);
			break;
		case 2:
			tgMedium.setChecked(true);
			break;
		case 3:
			tgHard.setChecked(true);
			break;
		default:
			break;
		}

		switch (Integer.parseInt(stone)) {
		case 4:
			tg4.setChecked(true);
			break;
		case 5:
			tg5.setChecked(true);
			break;
		case 6:
			tg6.setChecked(true);
			break;
		default:
			break;
		}

		tgEasy.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean on = ((ToggleButton) v).isChecked();
				if (on) {
					diff = "1";
					tgMedium.setChecked(false);
					tgHard.setChecked(false);
				}
			}
		});
		tgMedium.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean on = ((ToggleButton) v).isChecked();
				if (on) {
					diff = "2";
					tgEasy.setChecked(false);
					tgHard.setChecked(false);
				}
			}
		});
		tgHard.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean on = ((ToggleButton) v).isChecked();
				if (on) {
					diff = "3";
					tgEasy.setChecked(false);
					tgMedium.setChecked(false);
				}
			}
		});

		tg4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean on = ((ToggleButton) v).isChecked();
				if (on) {
					stone = "4";
					tg5.setChecked(false);
					tg6.setChecked(false);
				}
			}
		});
		tg5.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean on = ((ToggleButton) v).isChecked();
				if (on) {
					stone = "5";
					tg4.setChecked(false);
					tg6.setChecked(false);
				}
			}
		});
		tg6.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean on = ((ToggleButton) v).isChecked();
				if (on) {
					stone = "6";
					tg5.setChecked(false);
					tg4.setChecked(false);
				}
			}
		});
	}

	public void getDefaultSettings() {
		try {
			String s = "1";
			long l = Long.parseLong(s);
			MancalaDB con = new MancalaDB(GameOptionsActivity.this);
			con.open();
			diff = con.getDiffLevel(l);
			stone = con.getStone(l);
			con.close();

		} catch (Exception e) {

		}
	}

	public void updateSettings() {

		try {
			String s = "1";
			long l = Long.parseLong(s);

			MancalaDB ex = new MancalaDB(GameOptionsActivity.this);
			ex.open();
			ex.updateSettings(l, diff, stone);
			ex.close();

		} catch (Exception e) {
			Toast.makeText(GameOptionsActivity.this, "güncelleme basarýsýz",
					Toast.LENGTH_SHORT).show();
		}
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

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK)// donanýmsal olarak geri butonuna
		// basýlýrsa
		{
			return true;
		}
		return false;
	}

	public void startActivityFirstScreen(View v) {
		updateSettings();
		ai.CBoard.StartingStones = Integer.parseInt(stone);
		ai.CBoard.TotalStones = 12 * Integer.parseInt(stone);
		GameOptionsActivity.this.finish();
	}
}
