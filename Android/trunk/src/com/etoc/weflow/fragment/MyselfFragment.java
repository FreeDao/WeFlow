package com.etoc.weflow.fragment;

import com.etoc.weflow.R;
import com.etoc.weflow.activity.CaptureActivity;
import com.etoc.weflow.activity.FeedBackActivity;
import com.etoc.weflow.activity.SettingsActivity;
import com.etoc.weflow.activity.SignInActivity;
import com.etoc.weflow.activity.ExpenseFlowActivity;
import com.etoc.weflow.activity.MakeFlowActivity;
import com.etoc.weflow.activity.MyBillListActivity;
import com.etoc.weflow.utils.ViewUtils;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MyselfFragment extends XFragment<Object>/*TitleRootFragment*/implements OnClickListener {

	private final String TAG = "MyselfFragment";
	
	private RelativeLayout rlMyBill;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreateView(inflater, container, savedInstanceState);
		View v = inflater.inflate(R.layout.fragment_myself, null);
		initView(v);
		return v;
	}
	
	private void initView(View view) {
		rlMyBill = (RelativeLayout) view.findViewById(R.id.rl_me_bill);
		rlMyBill.setOnClickListener(this);
		
		
		view.findViewById(R.id.rl_me_sign).setOnClickListener(this);
		view.findViewById(R.id.rl_me_feedback).setOnClickListener(this);
		view.findViewById(R.id.rl_me_settings).setOnClickListener(this);
		
		ViewUtils.setHeight(view.findViewById(R.id.rl_me_top), 222);
		
		ViewUtils.setHeight(view.findViewById(R.id.rl_me_msg), 112);
		ViewUtils.setHeight(view.findViewById(R.id.rl_me_bill), 112);
		ViewUtils.setHeight(view.findViewById(R.id.rl_me_sign), 112);
		ViewUtils.setHeight(view.findViewById(R.id.rl_me_feedback), 112);
		ViewUtils.setHeight(view.findViewById(R.id.rl_me_settings), 112);
		
		
		ViewUtils.setTextSize((TextView) view.findViewById(R.id.tv_flow_hint), 35);
		ViewUtils.setTextSize((TextView) view.findViewById(R.id.tv_flow_value), 35);
		ViewUtils.setTextSize((TextView) view.findViewById(R.id.tv_flow_paper_hint), 35);
		ViewUtils.setTextSize((TextView) view.findViewById(R.id.tv_flow_paper), 35);
		
		ViewUtils.setTextSize((TextView) view.findViewById(R.id.tv_msg), 35);
		ViewUtils.setTextSize((TextView) view.findViewById(R.id.tv_bill), 35);
		ViewUtils.setTextSize((TextView) view.findViewById(R.id.tv_sign), 35);
		ViewUtils.setTextSize((TextView) view.findViewById(R.id.tv_feedback), 35);
		ViewUtils.setTextSize((TextView) view.findViewById(R.id.tv_settings), 35);
		
		ViewUtils.setMarginLeft((TextView) view.findViewById(R.id.tv_flow_hint), 52);
		ViewUtils.setMarginLeft((TextView) view.findViewById(R.id.tv_flow_paper_hint), 52);
		
		ViewUtils.setMarginRight((TextView) view.findViewById(R.id.tv_flow_value), 25);
		ViewUtils.setMarginRight((TextView) view.findViewById(R.id.tv_flow_paper), 25);
		
		ViewUtils.setMarginLeft(view.findViewById(R.id.tv_msg), 48);
		ViewUtils.setMarginLeft(view.findViewById(R.id.tv_bill), 48);
		ViewUtils.setMarginLeft(view.findViewById(R.id.tv_download), 48);
		ViewUtils.setMarginLeft(view.findViewById(R.id.tv_sign), 48);
		ViewUtils.setMarginLeft(view.findViewById(R.id.tv_feedback), 48);
		ViewUtils.setMarginLeft(view.findViewById(R.id.tv_settings), 48);
		ViewUtils.setMarginLeft(view.findViewById(R.id.view_divide_h_center), 32);
		ViewUtils.setMarginRight(view.findViewById(R.id.view_divide_h_center), 32);
		ViewUtils.setPaddingLeft(view.findViewById(R.id.rl_me_msg), 32);
		ViewUtils.setPaddingLeft(view.findViewById(R.id.rl_me_bill), 32);
		ViewUtils.setPaddingLeft(view.findViewById(R.id.rl_me_download), 32);
		ViewUtils.setPaddingLeft(view.findViewById(R.id.rl_me_sign), 32);
		ViewUtils.setPaddingLeft(view.findViewById(R.id.rl_me_feedback), 32);
		ViewUtils.setPaddingLeft(view.findViewById(R.id.rl_me_settings), 32);
		ViewUtils.setMarginLeft(view.findViewById(R.id.view_divide_h_centerb2), 32);
		ViewUtils.setMarginRight(view.findViewById(R.id.view_divide_h_centerb2), 32);
		ViewUtils.setMarginTop(view.findViewById(R.id.rl_me_center_a), 20);
		ViewUtils.setMarginTop(view.findViewById(R.id.rl_me_center_b), 20);
		ViewUtils.setMarginTop(view.findViewById(R.id.rl_me_bottom), 20);
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.d(TAG, "onResume");
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_title_left:
			
			break;
		case R.id.rl_me_bill:
			startActivity(new Intent(getActivity(), MyBillListActivity.class));
			break;
		case R.id.rl_me_sign:
//			startActivity(new Intent(getActivity(), SignInActivity.class));
			break;
		case R.id.rl_me_feedback:
			startActivity(new Intent(getActivity(), FeedBackActivity.class));
			break;
		case R.id.rl_me_settings:
			startActivity(new Intent(getActivity(), SettingsActivity.class));
			break;
		}
	}
	
	@Override
	public int getIndex() {
		return INDEX_ME;
	}

	@Override
	public void onShow() {
		Log.d(TAG, "onShow IN!");
	}
	
}
