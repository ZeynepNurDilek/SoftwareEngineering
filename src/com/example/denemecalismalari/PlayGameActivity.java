package com.example.realmancala;

import core.MancalaDB;
import ai.*;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.TranslateAnimation;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class PlayGameActivity extends Activity {
	static String diff;
	static String stone;
	String win;
	String draw;
	String lose;

	CBoard MainBoard; // Oyun durumunun saklanacaðý sýnýf
	P1AI player1; // Yapay zeka fonksiyonlarýnýn bulunduðu ve hangi hamlenin
					// yapýlacaðýnýn hesaplandýðý sýnýf
	CBoard copyBoard;
	boolean[] h;
	int counter;
	int boardIndex;

	final TextView[] item = new TextView[14];
	final Button[] btnItems = new Button[14];

	public void getDefaultSettings() {
		try {
			String s = "1";
			long l = Long.parseLong(s);
			MancalaDB con = new MancalaDB(PlayGameActivity.this);
			con.open();
			diff = con.getDiffLevel(l);
			stone = con.getStone(l);
			win = con.getWin(l);
			lose = con.getLose(l);
			draw = con.getDraw(l);

			con.close();

//			Toast.makeText(PlayGameActivity.this,
//					stone + " " + win + " " + draw + " " + lose,
//					Toast.LENGTH_SHORT).show();
		} catch (Exception e) {

		}
	}

	public void updateWins() {
		int count = Integer.parseInt(win);
		count += 1;
		try {
			String s = "1";
			long l = Long.parseLong(s);

			MancalaDB ex = new MancalaDB(PlayGameActivity.this);
			ex.open();
			ex.updateWin(l, Integer.toString(count));
			ex.close();

		} catch (Exception e) {
			Toast.makeText(PlayGameActivity.this, "güncelleme basarýsýz",
					Toast.LENGTH_SHORT).show();
		}
	}

	public void updateLoses() {
		int count = Integer.parseInt(lose);
		count += 1;
		try {
			String s = "1";
			long l = Long.parseLong(s);

			MancalaDB ex = new MancalaDB(PlayGameActivity.this);
			ex.open();
			ex.updateLose(l, Integer.toString(count));
			ex.close();

		} catch (Exception e) {
			Toast.makeText(PlayGameActivity.this, "güncelleme basarýsýz",
					Toast.LENGTH_SHORT).show();
		}
	}
	
	public void updateDraws() {
		int count = Integer.parseInt(draw);
		count += 1;
		try {
			String s = "1";
			long l = Long.parseLong(s);

			MancalaDB ex = new MancalaDB(PlayGameActivity.this);
			ex.open();
			ex.updateDraw(l, Integer.toString(count));
			ex.close();

		} catch (Exception e) {
			Toast.makeText(PlayGameActivity.this, "güncelleme basarýsýz",
					Toast.LENGTH_SHORT).show();
		}
	}
	/* animasyon oluþturma */
	int deltaX = -96;
	int startingValue;
	int stepNumber = 0;
	int binIndex;
	int numberOfStone = 0;
	ImageView imgHand;

	public void setRightToLeftAnimation(int _stepNumber, int _startingIndex) {
		int from = _startingIndex * -96;
		int to = from - 96;

		TranslateAnimation moveRightToLeft = new TranslateAnimation(from, to,
				0, 0);
		moveRightToLeft.setDuration(1000);
		moveRightToLeft.setFillAfter(true);

		imgHand.setAnimation(moveRightToLeft);
		if (_startingIndex >= 5) {
			_startingIndex = 0;
		}

	}

	boolean gameMode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Remove title bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// Remove notification bar
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_mancala_playgame_screen);
		getDefaultSettings();

		imgHand = (ImageView) findViewById(R.id.imgHandP1);
		/* oyun modunu al */
		gameMode = getIntent().getExtras().getBoolean("gameMode");

		if (gameMode) {
			// Toast.makeText(PlayGameActivity.this, "CH", Toast.LENGTH_SHORT)
			// .show();
		} else {
			// Toast.makeText(PlayGameActivity.this, "HH", Toast.LENGTH_SHORT)
			// .show();
		}

		/* When activity loaded */
		h = new boolean[6]; // Heuristic seçimleri
		MainBoard = new CBoard();
		copyBoard = new CBoard();
		player1 = new P1AI();
		ai.CBoard.StartingStones = Integer.parseInt(stone);
		ai.CBoard.TotalStones = 12 * Integer.parseInt(stone);

		h[0] = false; //
		h[1] = false; //
		h[2] = false; // heuristic seçimleri resetlenir.
		h[3] = false; //
		h[4] = false; //
		h[5] = false; //

		/* ____________________________________________________________________________________ */
		/* Oyuklar ve kaleleri tanýmlama */
		item[0] = (TextView) findViewById(R.id.txt0);
		item[1] = (TextView) findViewById(R.id.txt1);
		item[2] = (TextView) findViewById(R.id.txt2);
		item[3] = (TextView) findViewById(R.id.txt3);
		item[4] = (TextView) findViewById(R.id.txt4);
		item[5] = (TextView) findViewById(R.id.txt5);
		item[6] = (TextView) findViewById(R.id.txt6);// P1 Kalesi
		item[7] = (TextView) findViewById(R.id.txt7);
		item[8] = (TextView) findViewById(R.id.txt8);
		item[9] = (TextView) findViewById(R.id.txt9);
		item[10] = (TextView) findViewById(R.id.txt10);
		item[11] = (TextView) findViewById(R.id.txt11);
		item[12] = (TextView) findViewById(R.id.txt12);
		item[13] = (TextView) findViewById(R.id.txt13);// P2 Kalesi

		/* Oyuklar ve kaleleri tanýmlama */
		btnItems[0] = (Button) findViewById(R.id.btn0);
		btnItems[1] = (Button) findViewById(R.id.btn1);
		btnItems[2] = (Button) findViewById(R.id.btn2);
		btnItems[3] = (Button) findViewById(R.id.btn3);
		btnItems[4] = (Button) findViewById(R.id.btn4);
		btnItems[5] = (Button) findViewById(R.id.btn5);
		btnItems[6] = (Button) findViewById(R.id.btn6);// P1 Kalesi
		btnItems[7] = (Button) findViewById(R.id.btn7);
		btnItems[8] = (Button) findViewById(R.id.btn8);
		btnItems[9] = (Button) findViewById(R.id.btn9);
		btnItems[10] = (Button) findViewById(R.id.btn10);
		btnItems[11] = (Button) findViewById(R.id.btn11);
		btnItems[12] = (Button) findViewById(R.id.btn12);
		btnItems[13] = (Button) findViewById(R.id.btn13);// P2 Kalesi

		switch (Integer.parseInt(diff)) {
		case 1:
			// easy oyun ayarlandý
			h[4] = true;// h5 ve h6 heuristic ve 1 derinlikte çalýþacak yapay
						// zeka ayarlandý.
			h[5] = true;
			player1.reset(h, 1);

			for (int i1 = 0; i1 < 6; i1++)
				h[i1] = false;
			h[4] = true;// h5 ve h6 heuristic ve 1 derinlikte çalýþacak yapay
						// zeka ayarlandý.
			h[5] = true;
			player1.reset(h, 1);
			// Toast.makeText(PlayGameActivity.this, "easy", Toast.LENGTH_SHORT)
			// .show();
			break;
		case 2:
			// medium oyun ayarlandý
			h[0] = true;// h1 heuristic ve 3 derinlikte çalýþacak yapay zeka
						// ayarlandý.
			player1.reset(h, 3);

			for (int i2 = 0; i2 < 6; i2++)
				h[i2] = false;
			h[0] = true;// h1 heuristic ve 3 derinlikte çalýþacak yapay zeka
						// ayarlandý.
			player1.reset(h, 3);
			// Toast.makeText(PlayGameActivity.this, "medium",
			// Toast.LENGTH_SHORT)
			// .show();
			break;
		case 3:
			// hard oyun ayarlandý
			h[0] = true;// h1 ve h3 heuristic ve 8 derinlikte çalýþacak yapay
						// zeka ayarlandý.
			h[2] = true;
			player1.reset(h, 8);

			for (int i3 = 0; i3 < 6; i3++)
				h[i3] = false;
			h[0] = true;// h1 ve h3 heuristic ve 8 derinlikte çalýþacak yapay
						// zeka ayarlandý.
			h[2] = true;
			player1.reset(h, 8);
			// Toast.makeText(PlayGameActivity.this, "hard", Toast.LENGTH_SHORT)
			// .show();
			break;
		default:
			break;
		}

		for (int k = 0; k < item.length; k++) {
			item[k].setEnabled(true);
			if (k == 6 || k == 13) {
				item[k].setText("0");
			} else {
				item[k].setText(Integer.toString(ai.CBoard.StartingStones));
			}
		}

		btnItems[0].setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// Toast.makeText(PlayGameActivity.this, item[0].getText(),
				// Toast.LENGTH_SHORT).show();

				binIndex = 0;
				numberOfStone = Integer.parseInt(item[0].getText().toString());

				if (!gameMode) {
					OynaHH(1);
				}
			}
		});
		btnItems[1].setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// Toast.makeText(PlayGameActivity.this, item[1].getText(),
				// Toast.LENGTH_SHORT).show();

				binIndex = 1;
				numberOfStone = Integer.parseInt(item[1].getText().toString());

				if (!gameMode) {
					OynaHH(2);
				}
			}
		});
		btnItems[2].setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// Toast.makeText(PlayGameActivity.this, item[2].getText(),
				// Toast.LENGTH_SHORT).show();

				binIndex = 2;
				numberOfStone = Integer.parseInt(item[2].getText().toString());

				if (!gameMode) {
					OynaHH(3);
				}
			}
		});
		btnItems[3].setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// Toast.makeText(PlayGameActivity.this, item[3].getText(),
				// Toast.LENGTH_SHORT).show();

				binIndex = 3;
				numberOfStone = Integer.parseInt(item[3].getText().toString());

				if (!gameMode) {
					OynaHH(4);
				}
			}
		});
		btnItems[4].setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// Toast.makeText(PlayGameActivity.this, item[4].getText(),
				// Toast.LENGTH_SHORT).show();

				binIndex = 4;
				numberOfStone = Integer.parseInt(item[4].getText().toString());

				if (!gameMode) {
					OynaHH(5);
				}
			}
		});
		btnItems[5].setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// Toast.makeText(PlayGameActivity.this, item[5].getText(),
				// Toast.LENGTH_SHORT).show();

				binIndex = 5;
				numberOfStone = Integer.parseInt(item[5].getText().toString());

				if (!gameMode) {
					OynaHH(6);
				}
			}
		});
		btnItems[7].setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// Toast.makeText(PlayGameActivity.this, item[7].getText(),
				// Toast.LENGTH_SHORT).show();
				if (gameMode) {
					OynaCH(1);
				} else {
					OynaHH(1);
				}
			}
		});
		btnItems[8].setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// Toast.makeText(PlayGameActivity.this, item[8].getText(),
				// Toast.LENGTH_SHORT).show();
				if (gameMode) {
					OynaCH(2);
				} else {
					OynaHH(2);
				}
			}
		});
		btnItems[9].setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// Toast.makeText(PlayGameActivity.this, item[9].getText(),
				// Toast.LENGTH_SHORT).show();
				if (gameMode) {
					OynaCH(3);
				} else {
					OynaHH(3);
				}
			}
		});
		btnItems[10].setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// Toast.makeText(PlayGameActivity.this, item[10].getText(),
				// Toast.LENGTH_SHORT).show();
				if (gameMode) {
					OynaCH(4);
				} else {
					OynaHH(4);
				}
			}
		});
		btnItems[11].setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// Toast.makeText(PlayGameActivity.this, item[11].getText(),
				// Toast.LENGTH_SHORT).show();
				if (gameMode) {
					OynaCH(5);
				} else {
					OynaHH(5);
				}
			}
		});

		btnItems[12].setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// Toast.makeText(PlayGameActivity.this, item[12].getText(),
				// Toast.LENGTH_SHORT).show();
				if (gameMode) {
					OynaCH(6);
				} else {
					OynaHH(6);
				}
			}
		});

		final Button btnGoHome = (Button) findViewById(R.id.btnGoBackHome);
		btnGoHome.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				PlayGameActivity.this.finish();
			}
		});
	}/* end onCreate */

	/* my functions */
	// -----------------------------------------------------------------------------------------
	// Method: Oyna
	// Deðiþkenleri: int bin (player2 nin hangi çukuru oynamak istediðini
	// tutar.)
	// Dönüþ Deðeri: None
	// Amaç: player2 ye oynama hakký verir, o oynadýktan sonra player1 oynar.
	int player1Move;

	public void OynaHH(int bin) {
		if (MainBoard.Status == 1) // sýra bizdeyse oyna, bizden sonra rakibe
		// geçerse o oynar.
		{

			MainBoard.MakeCopyIn(copyBoard);
			MainBoard.MakeMove(bin);
			PlayOnBoard(bin);

			if (MainBoard.Status > 2)
				GameResults();
		} else {
			MainBoard.MakeCopyIn(copyBoard);
			MainBoard.MakeMove(bin);
			PlayOnBoard(bin);
			if (MainBoard.Status > 2)
				GameResults();
		}
	}

	public void OynaCH(int bin) {
		player1Move = 0;

		if (MainBoard.Status == 2) // sýra bizdeyse oyna, bizden sonra rakibe
									// geçerse o oynar.
		{
			MainBoard.MakeCopyIn(copyBoard);
			MainBoard.MakeMove(bin);
			PlayOnBoard(bin);
			if (MainBoard.Status > 2)
				GameResults();
		}

		while (MainBoard.Status == 1)// sýra rakipteyse status deðiþene(sýra
		// geçene) kadar yapay zeka oynar.
		{//

			player1Move = player1.getMove(MainBoard);
			MainBoard.MakeCopyIn(copyBoard);
			MainBoard.MakeMove(player1Move);
			PlayOnBoard(player1Move);
			if (MainBoard.Status > 2)
				GameResults();
		}

	}/* end Oyna() */

	public void GameResults() {
		if (gameMode) {
			if (MainBoard.Status == 3) {
				Toast.makeText(PlayGameActivity.this, "Bilgisayar kazandý!",
						Toast.LENGTH_SHORT).show();
				updateLoses();
				Toast.makeText(PlayGameActivity.this,
						"Oyun kaydedildi!",
						Toast.LENGTH_SHORT).show();
			}

			if (MainBoard.Status == 4) {
				Toast.makeText(PlayGameActivity.this, "Siz kazandýnýz!",
						Toast.LENGTH_SHORT).show();
				updateWins();
				Toast.makeText(PlayGameActivity.this,
						"Oyun kaydedildi!",
						Toast.LENGTH_SHORT).show();
			}

			if (MainBoard.Status == 5) {
				Toast.makeText(PlayGameActivity.this, "Berabere!",
						Toast.LENGTH_SHORT).show();
				updateDraws();
				Toast.makeText(PlayGameActivity.this,
						"Oyun kaydedildi!",
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	public void PlayOnBoard(int move) {
		// deðerleri atama
		int numStonesInBin;
		int tempCounter = 0;

		int arrayIndexOfP1Move = move - 1;
		int indexOfP1FirstDropStone = arrayIndexOfP1Move + 1;

		int arrayIndexOfP2Move = (move - 1) + (CBoard.holes + 1);
		int indexOfP2FirstDropStone = arrayIndexOfP2Move + 1;

		int moduloIndex = (CBoard.holes + 1) * 2;

		int indexOfP1FirstBin = 0;
		int indexOfP1LastBin = CBoard.indexP1Home - 1;

		int indexOfP2FirstBin = CBoard.holes + 1;
		int indexOfP2LastBin = CBoard.indexP2Home - 1;

		int x;// counter
		boolean GoAgain = false;
		int sum1 = 0;
		int sum2 = 0;
		boolean GameOver = false;

		// hamle uygun ise oyuncu bu hamleyi yapabilir
		if (copyBoard.ValidMove(move)) {
			switch (copyBoard.Status)// oyuncunun hamlesi ise, manipule et
			{
			case 1: {
				// P1 tarafýndaki taþlarýn sayýsý
				numStonesInBin = copyBoard.gameBoard[arrayIndexOfP1Move];

				// Boþ oyuklar kaldýrýlýr
				copyBoard.gameBoard[arrayIndexOfP1Move] = 0;
				item[arrayIndexOfP1Move].setText("0");
				// Taþlarý saatin tersi yönünde daðýt
				// Rakibin kalesini atla
				for (x = 0; x < (numStonesInBin + tempCounter); x++)// burada
																	// taþlar
																	// daðýtýlacak
				{

					// karþýnýn kalesi deðilse taþ koy
					if (!(((indexOfP1FirstDropStone + x) % moduloIndex) == CBoard.indexP2Home)) {
						copyBoard.gameBoard[(indexOfP1FirstDropStone + x)
								% moduloIndex]++;

						/* ?? */item[(indexOfP1FirstDropStone + x)
								% moduloIndex]
								.setText(Integer
										.toString(copyBoard.gameBoard[(indexOfP1FirstDropStone + x)
												% moduloIndex]));
						setRightToLeftAnimation(x + 1, binIndex++);

					} else
						tempCounter++;
				}
				// capture u kontrol et
				int indexOfBinOfLastStone = (indexOfP1FirstDropStone + x - 1)
						% (moduloIndex);

				// bizim tarafta isek
				if ((indexOfBinOfLastStone >= indexOfP1FirstBin)
						&& (indexOfBinOfLastStone <= indexOfP1LastBin)) {
					// goal ise
					int currBin = indexOfBinOfLastStone + 1;// currBin 1-6 arasý
															// bin no su.

					// son taþýn atýldýðý yer
					int oppositeBin = CBoard.holes + 1 - currBin;// currBin in
																	// karþýsýndaki
																	// bin no
																	// su.

					int indexOfOppositeBin = oppositeBin + CBoard.holes;

					if ((copyBoard.gameBoard[indexOfBinOfLastStone] == 1) && // son
																				// taþýn
																				// koyuldugu
																				// binde
																				// 1
																				// tas
																				// olmalý
							(copyBoard.gameBoard[indexOfOppositeBin] > 0) && // son
																				// taþýn
																				// koyulduðu
																				// oyugun
																				// karsýsý
																				// boþ
																				// olmamalý
							(indexOfBinOfLastStone != CBoard.indexP1Home))// son
																			// taþýn
																			// koyuldugu
																			// home
																			// olmamalý
					{

						copyBoard.gameBoard[CBoard.indexP1Home] += copyBoard.gameBoard[indexOfBinOfLastStone]
								+ copyBoard.gameBoard[indexOfOppositeBin];
						item[CBoard.indexP1Home]
								.setText(Integer
										.toString(copyBoard.gameBoard[CBoard.indexP1Home]));

						copyBoard.gameBoard[indexOfBinOfLastStone] = 0;
						copyBoard.gameBoard[indexOfOppositeBin] = 0;
						item[indexOfBinOfLastStone].setText("0");
						item[indexOfOppositeBin].setText("0");
					}
				}

				if (indexOfBinOfLastStone == CBoard.indexP1Home)// Taþ
																// daðýtýldýktan
																// sonra status
																// ayarý için
					GoAgain = true;
				break;
			}

			// oyuncunun hamlesi ise, manipule et
			case 2: {
				// p2 tarafýndaki taþlarýn sayýsý
				numStonesInBin = copyBoard.gameBoard[arrayIndexOfP2Move];

				// Boþ oyuklar kaldýrýlýr
				copyBoard.gameBoard[arrayIndexOfP2Move] = 0;// burada label i
															// deðiþtir
				item[arrayIndexOfP2Move].setText("0");
				// Taþlarý saatin tersi yönünde daðýt
				// Rakibin kalesini atla
				for (x = 0; x < (numStonesInBin + tempCounter); x++) {
					// kale deðilse taþ koy
					if (!(((indexOfP2FirstDropStone + x) % moduloIndex) == CBoard.indexP1Home)) {
						copyBoard.gameBoard[(indexOfP2FirstDropStone + x)
								% moduloIndex]++;
						item[(indexOfP2FirstDropStone + x) % moduloIndex]
								.setText(Integer
										.toString(copyBoard.gameBoard[(indexOfP2FirstDropStone + x)
												% moduloIndex]));

					} else
						tempCounter++;
				}
				// capture u kontrol et
				int indexOfBinOfLastStone = (indexOfP2FirstDropStone + x - 1)
						% moduloIndex;

				// karþý tarafta isek
				if ((indexOfBinOfLastStone >= indexOfP2FirstBin)
						&& (indexOfBinOfLastStone <= indexOfP2LastBin)) {
					// son taþýn býrakýldýðý oyuk
					int currBin = indexOfBinOfLastStone - CBoard.holes;

					int oppositeBin = CBoard.holes + 1 - currBin;

					int indexOfOppositeBin = oppositeBin - 1;

					if ((copyBoard.gameBoard[indexOfBinOfLastStone] == 1)
							&& (copyBoard.gameBoard[indexOfOppositeBin] > 0)
							&& (indexOfBinOfLastStone != CBoard.indexP2Home)) {
						copyBoard.gameBoard[CBoard.indexP2Home] += copyBoard.gameBoard[indexOfBinOfLastStone]
								+ copyBoard.gameBoard[indexOfOppositeBin];
						item[CBoard.indexP2Home]
								.setText(Integer
										.toString(copyBoard.gameBoard[CBoard.indexP2Home]));
						copyBoard.gameBoard[indexOfBinOfLastStone] = 0;
						copyBoard.gameBoard[indexOfOppositeBin] = 0;
						item[indexOfBinOfLastStone].setText("0");
						item[indexOfOppositeBin].setText("0");
					}
				}

				if (indexOfBinOfLastStone == CBoard.indexP2Home)
					GoAgain = true;
				break;
			}
			default:
				break;
			}

			// kimin oynayacaðýný belirle
			if (!GoAgain) {
				// P1 oynadý P2ye geç
				if (copyBoard.Status == 1) {
					copyBoard.Status = 2;
					for (int i = 7; i < 13; i++) {
						btnItems[i].setEnabled(true);
					}
					for (int i = 0; i < 6; i++) {
						btnItems[i].setEnabled(false);
					}
				}// P2 oynadý P1e geç
				else if (copyBoard.Status == 2) {
					copyBoard.Status = 1;
					for (int i = 0; i < 6; i++) {
						btnItems[i].setEnabled(true);
					}
					for (int i = 7; i < 13; i++) {
						btnItems[i].setEnabled(false);
					}
				}
			}

			sum1 = sum2 = 0;

			for (x = 0; x < CBoard.holes; x++) {
				sum1 += copyBoard.gameBoard[x];
				sum2 += copyBoard.gameBoard[x + (CBoard.holes + 1)];
			}

			// P1 tarafýnda taþ kalmadýysa kalan taþlarý aktar oyunu bitir
			if (sum1 == 0) {
				// P2 tarafýnda kalan taþlarý aktar
				copyBoard.gameBoard[CBoard.indexP2Home] += sum2;
				item[CBoard.indexP2Home].setText(Integer
						.toString(copyBoard.gameBoard[CBoard.indexP2Home]));

				// oyukarý sýfýrla
				for (x = 0; x < CBoard.holes; x++) {
					copyBoard.gameBoard[x + (CBoard.holes + 1)] = 0;
					item[x + (CBoard.holes + 1)].setText("0");
				}

				// oyun bittiðini belirt
				GameOver = true;
			}

			// P2 tarafýnda taþ kalmadýysa kalan taþlarý aktar oyunu bitir
			else if (sum2 == 0) {
				// P1 tarafýnda kalan taþlarý aktar
				copyBoard.gameBoard[CBoard.indexP1Home] += sum1;
				item[CBoard.indexP1Home].setText(Integer
						.toString(copyBoard.gameBoard[CBoard.indexP1Home]));// /////////////////////

				// oyukarý sýfýrla
				for (x = 0; x < CBoard.holes; x++) {
					copyBoard.gameBoard[x] = 0;
					item[x].setText("0");
				}

				// oyun bittiðini belirt
				GameOver = true;
			}

			// Kim kazandý?
			if (GameOver) {
				// P1
				if (copyBoard.gameBoard[CBoard.indexP1Home] > copyBoard.gameBoard[CBoard.indexP2Home])
					copyBoard.Status = 3;
				// P2
				else if (copyBoard.gameBoard[CBoard.indexP2Home] > copyBoard.gameBoard[CBoard.indexP1Home])
					copyBoard.Status = 4;
				// berabere
				else
					copyBoard.Status = 5;
			}
		} else // eðer doðru bir hamle yapmýyorsa uyarý ver.
		{
			Toast.makeText(PlayGameActivity.this, "Geçerli bir hamle deðil!",
					Toast.LENGTH_SHORT).show();
		}
	}/* end PlayOnBoard */

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK)// donanýmsal olarak geri butonuna
												// basýlýrsa
		{
			// are you sure to end game?
			// finish();
			return true;
		}
		return false;
	}
}
