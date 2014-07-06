package com.vult.tf.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.google.ads.AdRequest;
import com.google.ads.AdView;
import com.vult.TF4.R;
import com.vult.tf.utils.Utils;

public class SplashActivity extends Activity {
	private final int STOPSPLASH = 0;
	// time in milliseconds
	private final long SPLASHTIME = 2000;
	AdView adView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		adView = (AdView) this.findViewById(R.id.adView);
		adView.loadAd(new AdRequest());
		Message msg = new Message();
		if(!Utils.isNetworkConnected(this)) {
			Toast.makeText(SplashActivity.this,
					"Can not connect to network, please check again.",
					Toast.LENGTH_SHORT).show();
		}
		msg.what = STOPSPLASH;
		try {
			splashHandler.sendMessageDelayed(msg, SPLASHTIME);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Handler for splash screen
	 */
	private Handler splashHandler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			try {

				startActivity(new Intent(SplashActivity.this,
						TrailerActivity.class));
				finish();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};

	@Override
	public void onDestroy() {
		if (adView != null) {
			adView.destroy();
		}
		super.onDestroy();
	}
}
