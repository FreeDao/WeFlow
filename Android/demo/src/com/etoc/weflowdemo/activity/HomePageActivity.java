package com.etoc.weflowdemo.activity;

import java.text.NumberFormat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.etoc.weflowdemo.MainApplication;
import com.etoc.weflowdemo.R;
import com.etoc.weflowdemo.view.ExpandableLayout;
import com.etoc.weflowdemo.view.ExpandableLayout.ExpandListener;
import com.etoc.weflowdemo.view.MagicTextView;

public class HomePageActivity extends TitleRootActivity {

	private RelativeLayout useFlowLayout = null;
	private RelativeLayout discoverLayout = null;
	//UI Component
	private ExpandableLayout rlExpand;
	private MagicTextView mtvFlow;
	private RelativeLayout rlMakeFlow;
	private TextView tvPhoneNum = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initViews();
	}
	
	private void initViews() {
		setTitleText("微流量");
		hideLeftButton();
//		setLeftButtonText("宝典");
		hideRightButton();
//		setRightButtonText("消息");
		
		useFlowLayout = (RelativeLayout) findViewById(R.id.rl_use_flow);
		discoverLayout = (RelativeLayout) findViewById(R.id.rl_discover);
		rlExpand = (ExpandableLayout) findViewById(R.id.exv_user_info);
		rlExpand.setOnExpandListener(new ExpandListener() {
			@Override
			public void onExpandCompleted() {
				// TODO Auto-generated method stub
				if(mtvFlow != null) {
					mtvFlow.showNumberWithAnimation(MainApplication.totalFlow, 1000);
				}
			}
		});
		
		useFlowLayout.setOnClickListener(this);
		discoverLayout.setOnClickListener(this);
		mtvFlow = (MagicTextView) findViewById(R.id.tv_flow);
//		mtvFlow.showNumberWithAnimation(98.5f, 1000);
		TextView tvYesterDayRate = (TextView) findViewById(R.id.tv_yestory_precent);
		NumberFormat nf   =   NumberFormat.getPercentInstance(); 
		nf.setMinimumFractionDigits(2); 
		if (MainApplication.userInfo != null && MainApplication.userInfo.rate != null) {
			tvYesterDayRate.setText("昨日年化率：" + nf.format(Float.parseFloat(MainApplication.userInfo.rate)));
		}
		
		rlMakeFlow = (RelativeLayout) findViewById(R.id.rl_make_flow);
		rlMakeFlow.setOnClickListener(this);
		
		tvPhoneNum = (TextView) findViewById(R.id.tv_phone_num);
		String phoneNum = getIntent().getStringExtra("phone");
		if (phoneNum != null) {
			tvPhoneNum.setText(phoneNum);
		}
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(mtvFlow != null) {
			mtvFlow.showNumberWithAnimation(MainApplication.totalFlow, 1000);
		}
	}
	
	private long firstTime = 0;

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			long secondTime = System.currentTimeMillis();
			if (secondTime - firstTime > 2000) { // 如果两次按键时间间隔大于2秒，则不退出
				Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
				firstTime = secondTime;// 更新firstTime
				return true;
			} else { // 两次按键小于2秒时，退出应用
				System.exit(0);
			}
			break;
		}
		return super.onKeyUp(keyCode, event);
	}
	
	@Override
	public boolean handleMessage(Message arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int subContentViewId() {
		// TODO Auto-generated method stub
		return R.layout.activity_homepage;
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.rl_discover:
			Intent lotteryIntent = new Intent(this,ScratchCardActivity.class);
			lotteryIntent.putExtra("phone", tvPhoneNum.getText().toString());
			startActivity(lotteryIntent);
			break;
		case R.id.rl_make_flow:
			startActivity(new Intent(this, AdvertActivity.class));
			break;
		case R.id.rl_use_flow:
			Intent payIntent = new Intent(this,PayPhoneBillActivity.class);
			payIntent.putExtra("phone", tvPhoneNum.getText().toString());
			startActivity(payIntent);
			break;

		default:
			break;
		}
		super.onClick(v);
	}

}
