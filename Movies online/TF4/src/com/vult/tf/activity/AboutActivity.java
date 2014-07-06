package com.vult.tf.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.widget.Toast;

import com.google.ads.AdRequest;
import com.google.ads.AdView;
import com.vult.TF4.R;
import com.vult.tf.customview.SlidingMenuCustom;
import com.vult.tf.listener.MenuSlidingClickListener;
import com.vult.tf.utils.Utils;

public class AboutActivity extends BaseActivity implements
		MenuSlidingClickListener {

	private WebView mWebView;
	private SlidingMenuCustom mMenu;
	private long lastPressedTime;
	private static final int PERIOD = 2000;
	String mContent = "";
	AdView adView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		adView = (AdView) this.findViewById(R.id.adView);
		adView.loadAd(new AdRequest());
		try {
			mWebView = (WebView) findViewById(R.id.activity_about_webview);
			mWebView.getSettings().setJavaScriptEnabled(true);
			setTransparentBackground();
			mMenu = new SlidingMenuCustom(this, this);
			DisplayMetrics displaymetrics = new DisplayMetrics();
			getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
			int width = displaymetrics.widthPixels;
			int display_mode = getResources().getConfiguration().orientation;
			if (display_mode == 1) {
				mMenu.setBehindOff(width / 2 + width / 5);
			} else {
				mMenu.setBehindOff(width / 2 + width / 4);
			}
			new loadTask().execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void onClick_Menu(View view) {
		try {
			if (mMenu == null) {
				mMenu = new SlidingMenuCustom(this, AboutActivity.this);
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
		startActivity(new Intent(this, FilmActivity.class));
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
	public void onAboutClickListener() {
	}

	public class loadTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			showProgress();
		}

		@Override
		protected Void doInBackground(Void... params) {
			try {
				mContent = Utils.getHtmlFromAsset(AboutActivity.this,
						R.string.content_html);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			try {
				mWebView.loadDataWithBaseURL("file:///android_asset", mContent,
						"text/html", "UTF-8", null);
				dismissProgress();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public void setTransparentBackground() {
		try {
			mWebView.setBackgroundColor(R.color.bg_color);
			mWebView.getSettings().setLayoutAlgorithm(
					LayoutAlgorithm.SINGLE_COLUMN);
			mWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void onClickRateApp(View v) {
		Uri uri = Uri.parse("market://details?id=com.vult.TF4");
		Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
		try {
			if (Utils.isNetworkConnected(this)) {
				startActivity(goToMarket);
			} else {
				Toast.makeText(AboutActivity.this,
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
				Toast.makeText(AboutActivity.this,
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
