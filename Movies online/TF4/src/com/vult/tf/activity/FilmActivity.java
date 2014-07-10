package com.vult.tf.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.RelativeLayout.LayoutParams;

import com.google.ads.AdRequest;
import com.google.ads.AdView;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerView;
import com.smaato.soma.AdDimension;
import com.smaato.soma.AdDownloaderInterface;
import com.smaato.soma.AdListenerInterface;
import com.smaato.soma.AdType;
import com.smaato.soma.BannerStateListener;
import com.smaato.soma.BannerView;
import com.smaato.soma.BaseView;
import com.smaato.soma.ReceivedBannerInterface;
import com.smaato.soma.bannerutilities.constant.BannerStatus;
import com.smaato.soma.exception.ClosingLandingPageFailed;
import com.vult.TF4.R;
import com.vult.tf.customview.SlidingMenuCustom;
import com.vult.tf.listener.MenuSlidingClickListener;
import com.vult.tf.utils.Utils;

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
	private BannerView mBanner;
	private RelativeLayout myRelativeLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_film);
		try {
			adView = (AdView) this.findViewById(R.id.adView);
			adView.loadAd(new AdRequest());

			if (getIntent().getExtras() != null) {
				VIDEO_ID = getIntent().getExtras().getString("YOUTUBE_ID");
			}
			YouTubePlayerView youTubePlayerView = (YouTubePlayerView) findViewById(R.id.activity_youtube_playerview);
			youTubePlayerView.initialize(API_KEY, this);

			if (!Utils.isNetworkConnected(this)) {
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
		} catch (Exception e) {
			// TODO: handle exception
		}
		// =========Add smaato Ads============
		mBanner = new BannerView(this);
		myRelativeLayout = (RelativeLayout) findViewById(R.id.banner);
		myRelativeLayout.addView(mBanner, new LayoutParams(
				LayoutParams.MATCH_PARENT, 50));
		mBanner.getAdSettings().setPublisherId(923880529);
		mBanner.getAdSettings().setAdspaceId(65837995);
		mBanner.asyncLoadNewBanner();
		mBanner.setLocationUpdateEnabled(true);
		mBanner.setBackgroundResource(R.color.bg);

		mBanner.addAdListener(new AdListenerInterface() {
			@Override
			public void onReceiveAd(AdDownloaderInterface arg0,
					ReceivedBannerInterface banner) {
				if (banner.getStatus() == BannerStatus.ERROR) {
					Log.w("ERR: " + banner.getErrorCode(),
							"" + banner.getErrorMessage());
					myRelativeLayout.setVisibility(View.GONE);
				} else {
					Log.d("Success", "Banner download succeeded");
					myRelativeLayout.setVisibility(View.VISIBLE);
					// Banner download succeeded
				}
			}
		});

		mBanner.setBannerStateListener(new BannerStateListener() {

			@Override
			public void onWillCloseLandingPage(BaseView arg0)
					throws ClosingLandingPageFailed {
				Log.d("onWillCloseLandingPage", "onWillCloseLandingPage");

			}

			@Override
			public void onWillOpenLandingPage(BaseView arg0) {
				Log.d("onWillOpenLandingPage", "onWillOpenLandingPage");

			}
		});

		mBanner.getAdSettings().setAdType(AdType.RICHMEDIA);
		mBanner.getAdSettings().setAdDimension(AdDimension.DEFAULT);
		mBanner.setAutoReloadEnabled(true);
	}

	@Override
	protected void onResume() {
		super.onResume();
		mBanner.setBackgroundResource(R.color.bg);
		mBanner.asyncLoadNewBanner();
		mBanner.setLocationUpdateEnabled(true);
		mBanner.setAutoReloadFrequency(1);
		mBanner.getAdSettings().setAdType(AdType.RICHMEDIA);
		mBanner.getAdSettings().setAdDimension(AdDimension.DEFAULT);
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
		try {
			DisplayMetrics displaymetrics = new DisplayMetrics();
			getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
			int width = displaymetrics.widthPixels;
			// Checks the orientation of the screen
			if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
				mMenu.setBehindOff(width / 2 + width / 4);
			} else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
				mMenu.setBehindOff(width / 2 + width / 5);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	public void onInitializationFailure(Provider arg0,
			YouTubeInitializationResult result) {
		try {
			if (result.isUserRecoverableError()) {
				result.getErrorDialog(this, 100)// "CODE_REQUEST_UPDATE_YOUTUBE"
						.show();
			} else {
				Toast.makeText(this,
						"YouTubePlayer failed: " + result.toString(),
						Toast.LENGTH_LONG).show();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	public void onInitializationSuccess(Provider provider,
			YouTubePlayer player, boolean wasRestored) {
		try {
			if (!wasRestored) {
				if (!Utils.isNetworkConnected(this)) {
					Toast.makeText(FilmActivity.this,
							"Can not connect to network, please check again.",
							Toast.LENGTH_SHORT).show();
				}
				player.cueVideo(VIDEO_ID);
				player.setFullscreen(false);
			}
		} catch (Exception e) {
			// TODO: handle exception
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
		try {
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
		} catch (Exception e) {
			// TODO: handle exception
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

	public void onClickNew(View v) {
		Uri uri = Uri.parse("market://details?id=com.vult.rio2");
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
					Uri.parse("http://play.google.com/store/apps/details?id=com.vult.rio2")));
		}
	}

}
