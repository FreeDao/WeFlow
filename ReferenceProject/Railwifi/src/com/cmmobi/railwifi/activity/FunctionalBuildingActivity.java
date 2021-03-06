package com.cmmobi.railwifi.activity;

import com.cmmobi.railwifi.R;
import com.cmmobi.railwifi.utils.DisplayUtil;
import com.cmmobi.railwifi.utils.ViewUtils;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FunctionalBuildingActivity extends TitleRootActivity {

	@Override
	public boolean handleMessage(Message msg) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int subContentViewId() {
		// TODO Auto-generated method stub
		return R.layout.fragment_city_life;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setTitleText("功能建设中");
		hideRightButton();
		
		initViews();
	}
	
	private void initViews() {
		Button btnGame = (Button) findViewById(R.id.btn_game);
		Button btnReport = (Button) findViewById(R.id.btn_report);
		TextView tvTips = (TextView) findViewById(R.id.tv_empty_tips);
		TextView tvBusniss = (TextView) findViewById(R.id.tv_business);
		
		btnGame.setOnClickListener(this);
		btnReport.setOnClickListener(this);
		
		btnGame.setTextSize(DisplayUtil.textGetSizeSp(this, 33));
		btnReport.setTextSize(DisplayUtil.textGetSizeSp(this, 33));
		tvTips.setTextSize(DisplayUtil.textGetSizeSp(this, 48));
		tvBusniss.setTextSize(DisplayUtil.textGetSizeSp(this, 30));
		
		ViewUtils.setHeight(findViewById(R.id.rl_bottom), 176);
		ViewUtils.setHeight(findViewById(R.id.rl_business), 76);
		
		ViewUtils.setMarginTop(tvTips, 24);
		
		ViewUtils.setSize(btnGame, 321, 125);
		ViewUtils.setSize(btnReport, 321, 125);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_title_left:
			finish();
			break;
		case R.id.btn_game:
			startActivity(new Intent(this,GameActivity.class));
			break;
		case R.id.btn_report:
			startActivity(new Intent(this,FeedBackActivity.class));
			break;
		}
		super.onClick(v);
	}

}
