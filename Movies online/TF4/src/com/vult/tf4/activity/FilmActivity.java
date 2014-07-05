package com.vult.TF4.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.ads.AdRequest;
import com.google.ads.AdView;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerView;
import com.vult.TF4.R;
import com.vult.TF4.customview.SlidingMenuCustom;
import com.vult.TF4.listener.MenuSlidingClickListener;
import com.vult.TF4.utils.Utils;

/**
 * @author vu le
 * 
 */
public class FilmActivity extends YouTubeBaseActivity implements
		YouTubePlayer.OnInitializedListener, MenuSlidingClickListener,
		OnClickListener {

	private SlidingMenuCustom mMenu;
	WebView mWvContent;
	String mContent = "";
	private long lastPressedTime;
	private static final int PERIOD = 2000;
	public static final String API_KEY = "AIzaSyAZQIG9IW7-jL86LtIeBtol-1dYc7K-JPI";
	public static String VIDEO_ID = "rtu62Gklgso";
	AdView adView;
	private Button mBtSession1;
	private Button mBtSession2;
	private Button mBtSession3;
	private Button mBtSession4;
	private ImageView mIvClose;
	private LinearLayout mLlAlertView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_film);
		adView = (AdView) this.findViewById(R.id.adView);
		adView.loadAd(new AdRequest());

		if (getIntent().getExtras() != null) {
			VIDEO_ID = getIntent().getExtras().getString("YOUTUBE_ID");
		}
		YouTubePlayerView youTubePlayerView = (YouTubePlayerView) findViewById(R.id.activity_youtube_playerview);
		youTubePlayerView.initialize(API_KEY, this);
		
		if(!Utils.isNetworkConnected(this)) {
			Toast.makeText(FilmActivity.this,
					"Can not connect to network, please check again.",
					Toast.LENGTH_SHORT).show();
		}

		// Identify address
		mBtSession1 = (Button) findViewById(R.id.btn_s1);
		mBtSession2 = (Button) findViewById(R.id.btn_s2);
		mBtSession3 = (Button) findViewById(R.id.btn_s3);
		mBtSession4 = (Button) findViewById(R.id.btn_s4);
		mIvClose = (ImageView) findViewById(R.id.bt_close);
		mLlAlertView = (LinearLayout) findViewById(R.id.ll_alert);
		mBtSession1.setOnClickListener(this);
		mBtSession2.setOnClickListener(this);
		mBtSession3.setOnClickListener(this);
		mBtSession4.setOnClickListener(this);
		mIvClose.setOnClickListener(this);
	}

	public void onClick_Menu(View view) {
		try {
			if (mMenu == null) {
				mMenu = new SlidingMenuCustom(this, FilmActivity.this);
			}
			mMenu.toggle();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		try {
			if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
				switch (event.getAction()) {
				case KeyEvent.ACTION_DOWN:
					if (event.getDownTime() - lastPressedTime < PERIOD) {
						finish();
					} else {
						Toast.makeText(getApplicationContext(),
								getString(R.string.press_exit),
								Toast.LENGTH_SHORT).show();
						lastPressedTime = event.getEventTime();
					}
					return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		int width = displaymetrics.widthPixels;
		// Checks the orientation of the screen
		if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
			mMenu.setBehindOff(width / 2 + width / 4);
		} else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
			mMenu.setBehindOff(width / 2 + width / 5);
		}
	}

	@Override
	public void onInitializationFailure(Provider arg0,
			YouTubeInitializationResult result) {
		if (result.isUserRecoverableError()) {
			result.getErrorDialog(this, 100)// "CODE_REQUEST_UPDATE_YOUTUBE"
					.show();
		} else {
			Toast.makeText(this, "YouTubePlayer failed: " + result.toString(),
					Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public void onInitializationSuccess(Provider provider,
			YouTubePlayer player, boolean wasRestored) {
		if (!wasRestored) {
			if(!Utils.isNetworkConnected(this)) {
				Toast.makeText(FilmActivity.this,
						"Can not connect to network, please check again.",
						Toast.LENGTH_SHORT).show();
			}
			player.cueVideo(VIDEO_ID);
			player.setFullscreen(false);
		}
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
	}

	@Override
	public void onTrailerClickListener() {
		startActivity(new Intent(this, TrailerActivity.class));
		finish();
	}

	@Override
	public void onFilmClickListener() {

	}

	@Override
	public void onAboutClickListener() {
		startActivity(new Intent(this, AboutActivity.class));
		finish();
	}

	@Override
	public void onShareClickListener() {
		try {
			Intent sendIntent = new Intent();
			sendIntent.setAction(Intent.ACTION_SEND);
			sendIntent
					.putExtra(
							Intent.EXTRA_TEXT,
							"This is a good app to watch full Transformers 4 free !."
									+ " Enjoy it now and get the interested."
									+ "https://play.google.com/store/apps/details?id=com.vult.TF4");
			sendIntent.setType("text/plain");
			startActivity(Intent.createChooser(sendIntent, "Share via"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onDestroy() {
		if (adView != null) {
			adView.destroy();
		}
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent(FilmActivity.this, FilmActivity.class);
		if (v == mBtSession1) {
			intent.putExtra("YOUTUBE_ID", "rtu62Gklgso");
			startActivity(intent);
			finish();
		} else if (v == mBtSession2) {
			intent.putExtra("YOUTUBE_ID", "DgQHgy7Nmkk");
			startActivity(intent);
			finish();
		} else if (v == mBtSession3) {
			intent.putExtra("YOUTUBE_ID", "WJs_peEATiU");
			startActivity(intent);
			finish();
		} else if (v == mBtSession4) {
			intent.putExtra("YOUTUBE_ID", "PZ8h2L63D0Y");
			startActivity(intent);
			finish();
		} else if (v == mIvClose) {
			mLlAlertView.setVisibility(View.GONE);
		}
	}

	public void onClickRateApp(View v) {
		Uri uri = Uri.parse("market://details?id=com.vult.TF4");
		Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
		try {
			if (Utils.isNetworkConnected(this)) {
				startActivity(goToMarket);
			} else {
				Toast.makeText(FilmActivity.this,
						"Can not connect to network, please check again.",
						Toast.LENGTH_SHORT).show();
			}
		} catch (Exception e) {
			startActivity(new Intent(
					Intent.ACTION_VIEW,
					Uri.parse("http://play.google.com/store/apps/details?id=com.vult.TF4")));
		}
	}

}
