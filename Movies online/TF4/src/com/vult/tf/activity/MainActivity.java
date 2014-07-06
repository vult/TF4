package com.vult.tf.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.vult.TF4.R;

public class MainActivity extends Activity {
	SlidingMenu menu = null;
	Button mBtTrailer;
	Button mBtFilm;
	Button mBtAbout;
	Button mBtShare;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initMenu();
	}

	public void onClick_Menu(View view) {
		try {
			if (menu == null) {
				initMenu();
			}
			menu.setMode(SlidingMenu.LEFT);
			menu.toggle();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initMenu() {
		try {
			menu = new SlidingMenu(this);
			menu.setMode(SlidingMenu.LEFT);
			menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
			menu.setShadowWidthRes(R.dimen.shadow_width);
			menu.setShadowDrawable(R.anim.shadow);
			menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
			menu.setFadeDegree(0.35f);
			menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
			menu.setMenu(R.layout.activity_sliding);
			menu.setSlidingEnabled(true);
			View view = menu.getRootView();
			mBtTrailer = (Button) view
					.findViewById(R.id.activity_sliding_bt_trailer);
			mBtFilm = (Button) view.findViewById(R.id.activity_sliding_bt_film);
			mBtAbout = (Button) view
					.findViewById(R.id.activity_sliding_bt_about);
			mBtShare = (Button) view
					.findViewById(R.id.activity_sliding_bt_share);

			mBtShare.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					menu.toggle();
					Toast.makeText(MainActivity.this, "Share1",
							Toast.LENGTH_SHORT).show();
				}
			});

			mBtFilm.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					startActivity(new Intent(MainActivity.this,
							FilmActivity.class));
					finish();
					menu.toggle();
					// Toast.makeText(MainActivity.this, "Film1",
					// Toast.LENGTH_SHORT).show();
				}
			});

			mBtTrailer.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					menu.toggle();
					// Toast.makeText(MainActivity.this, "Trailer1",
					// Toast.LENGTH_SHORT).show();
				}
			});

			mBtAbout.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					startActivity(new Intent(MainActivity.this,
							AboutActivity.class));
					finish();
					menu.toggle();

				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
