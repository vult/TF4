package com.vult.tf.customview;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.vult.TF4.R;
import com.vult.tf.listener.MenuSlidingClickListener;

/**
 * @author Hung Hoang This class init Sliding Menu
 */
public class SlidingMenuCustom {
	private SlidingMenu menu = null;
	private Button mBtTrailer;
	private Button mBtFilm;
	private Button mBtShare;
	private Button mBtAbout;

	/**
	 * @param context
	 * @param listenner
	 */
	public SlidingMenuCustom(Context context,
			final MenuSlidingClickListener listenner) {
		menu = new SlidingMenu(context);
		menu.setMode(SlidingMenu.LEFT);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		menu.setShadowWidthRes(R.dimen.shadow_width);
		menu.setShadowDrawable(R.anim.shadow);
		menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		menu.setFadeDegree(0.35f);
		menu.attachToActivity((Activity) context, SlidingMenu.SLIDING_CONTENT);
		menu.setMenu(R.layout.activity_sliding);
		menu.setSlidingEnabled(true);
		View view = menu.getRootView();
		mBtAbout = (Button) view.findViewById(R.id.activity_sliding_bt_about);
		mBtFilm = (Button) view
				.findViewById(R.id.activity_sliding_bt_film);
		mBtTrailer = (Button) view
				.findViewById(R.id.activity_sliding_bt_trailer);
		mBtShare = (Button) view
				.findViewById(R.id.activity_sliding_bt_share);

		mBtAbout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				menu.toggle();
				listenner.onAboutClickListener();
			}
		});

		mBtFilm.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				menu.toggle();
				listenner.onFilmClickListener();
			}
		});

		mBtTrailer.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				menu.toggle();
				listenner.onTrailerClickListener();
			}
		});

		mBtShare.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				menu.toggle();
				listenner.onShareClickListener();
			}
		});
	}

	public void toggle() {
		menu.toggle();
	}

	public void setTouchModeAboveMargin() {
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
	}

	public void setTouchModeAboveCustom() {
	}

	public void setBehindOffsetRes(int resID) {
		menu.setBehindOffsetRes(resID);
	}
	public void setBehindOff(int i) {
		menu.setBehindOffset(i);
	}
}
