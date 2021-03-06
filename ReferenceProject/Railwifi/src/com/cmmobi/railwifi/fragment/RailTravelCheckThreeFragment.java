package com.cmmobi.railwifi.fragment;

import java.util.ArrayList;
import java.util.Collections;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.cmmobi.railwifi.R;
import com.cmmobi.railwifi.utils.DisplayUtil;

public class RailTravelCheckThreeFragment extends Fragment implements OnClickListener {

	private View contentView;
	private TextView tvNoticeDetail;
	private TextView tvRemindDetail;
	private TextView tvAttentionDetail;
	private ImageButton ibtnOpenOne;
	private ImageButton ibtnOpenTwo;
	private ImageButton ibtnOpenThree;
	private TextView tvNotice;
	private TextView tvRemind;
	private TextView tvAttention;
	private LinearLayout llCheckThree;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		contentView = inflater.inflate(
				R.layout.activity_railtravel_detail_check_three, null);
		return contentView;
	}
	


	
	@Override
	public void onResume() {
		super.onResume();
		
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		llCheckThree = (LinearLayout) contentView.findViewById(R.id.ll_check_three);
		llCheckThree.setPadding(DisplayUtil.getSize(getActivity(), 12), DisplayUtil.getSize(getActivity(), 21), DisplayUtil.getSize(getActivity(), 12), DisplayUtil.getSize(getActivity(), 12));
		
		tvNotice = (TextView) contentView.findViewById(R.id.tv_notice);
		tvNotice.setTextSize(DisplayUtil.textGetSizeSp(getActivity(), 33));
		tvNotice.setTypeface(Typeface.DEFAULT_BOLD);
		LinearLayout.LayoutParams lParams = (LinearLayout.LayoutParams) tvNotice.getLayoutParams();
		lParams.height = DisplayUtil.getSize(getActivity(), 42);
		tvNotice.setLayoutParams(lParams);
		
		tvNoticeDetail = (TextView) contentView.findViewById(R.id.tv_notice_detail);
		tvNoticeDetail.setTextSize(DisplayUtil.textGetSizeSp(getActivity(), 27));
		tvNoticeDetail.setLineSpacing(0, (float)1.5);
		tvNoticeDetail.setPadding(DisplayUtil.getSize(getActivity(), 12), 0, DisplayUtil.getSize(getActivity(), 12), 0);
		
		tvRemind = (TextView) contentView.findViewById(R.id.tv_remind);
		tvRemind.setTextSize(DisplayUtil.textGetSizeSp(getActivity(), 33));
		tvRemind.setTypeface(Typeface.DEFAULT_BOLD);
		lParams = (LinearLayout.LayoutParams) tvRemind.getLayoutParams();
		lParams.height = DisplayUtil.getSize(getActivity(), 42);
		lParams.topMargin = DisplayUtil.getSize(getActivity(), 12);
		tvRemind.setLayoutParams(lParams);
		
		tvRemindDetail = (TextView) contentView.findViewById(R.id.tv_remind_detail);
		tvRemindDetail.setTextSize(DisplayUtil.textGetSizeSp(getActivity(), 27));
		tvRemindDetail.setLineSpacing(0, (float)1.5);
		tvRemindDetail.setPadding(DisplayUtil.getSize(getActivity(), 12), 0, DisplayUtil.getSize(getActivity(), 12), 0);
		
		tvAttention = (TextView) contentView.findViewById(R.id.tv_attention);
		tvAttention.setTextSize(DisplayUtil.textGetSizeSp(getActivity(), 33));
		tvAttention.setTypeface(Typeface.DEFAULT_BOLD);
		lParams = (LinearLayout.LayoutParams) tvAttention.getLayoutParams();
		lParams.height = DisplayUtil.getSize(getActivity(), 42);
		lParams.topMargin = DisplayUtil.getSize(getActivity(), 12);
		tvAttention.setLayoutParams(lParams);
		
		tvAttentionDetail = (TextView) contentView.findViewById(R.id.tv_attention_detail);
		tvAttentionDetail.setTextSize(DisplayUtil.textGetSizeSp(getActivity(), 27));
		tvAttentionDetail.setLineSpacing(0, (float)1.5);
		tvAttentionDetail.setPadding(DisplayUtil.getSize(getActivity(), 12), 0, DisplayUtil.getSize(getActivity(), 12), 0);
		
		Bundle bundle = getArguments();
		if(bundle != null){
			String notice = bundle.getString("notice");
			String remind = bundle.getString("remind");
			String attention = bundle.getString("attention");
			tvNoticeDetail.setText(notice);
			tvRemindDetail.setText(remind);
			tvAttentionDetail.setText(attention);
		}
		
		/*ibtnOpenOne = (ImageButton) contentView.findViewById(R.id.btn_open_one);
		ibtnOpenOne.setPadding(0, DisplayUtil.getSize(getActivity(), 12), DisplayUtil.getSize(getActivity(), 48), DisplayUtil.getSize(getActivity(), 21));
		ibtnOpenOne.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(tvNoticeDetail.getMaxLines() == 4)
				{
					tvNoticeDetail.setMaxLines(100);
					ibtnOpenOne.setImageResource(R.drawable.btn_close);
				}
				else
				{
					tvNoticeDetail.setMaxLines(4);
					ibtnOpenOne.setImageResource(R.drawable.btn_open);
				}
			}
		});
		tvNoticeDetail.post(new Runnable() {

		    @Override
		    public void run() {
		        int lineCount    = tvNoticeDetail.getLineCount();
		        if(lineCount>4){
		        	ibtnOpenOne.setVisibility(View.VISIBLE);
		        } else{
		        	ibtnOpenOne.setVisibility(View.GONE);
		        }
		    }
		});
		
		ibtnOpenTwo = (ImageButton) contentView.findViewById(R.id.btn_open_two);
		ibtnOpenTwo.setPadding(0, DisplayUtil.getSize(getActivity(), 12), DisplayUtil.getSize(getActivity(), 48), DisplayUtil.getSize(getActivity(), 21));
		ibtnOpenTwo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(tvRemindDetail.getMaxLines() == 4)
				{
					tvRemindDetail.setMaxLines(100);
					ibtnOpenTwo.setImageResource(R.drawable.btn_close);
				}
				else
				{
					tvRemindDetail.setMaxLines(4);
					ibtnOpenTwo.setImageResource(R.drawable.btn_open);
				}
			}
		});
		tvRemindDetail.post(new Runnable() {

		    @Override
		    public void run() {
		        int lineCount    = tvRemindDetail.getLineCount();
		        if(lineCount>4){
		        	ibtnOpenTwo.setVisibility(View.VISIBLE);
		        } else{
		        	ibtnOpenTwo.setVisibility(View.GONE);
		        }
		    }
		});	
		
		ibtnOpenThree = (ImageButton) contentView.findViewById(R.id.btn_open_three);
		ibtnOpenThree.setPadding(0, DisplayUtil.getSize(getActivity(), 12), DisplayUtil.getSize(getActivity(), 48), DisplayUtil.getSize(getActivity(), 21));
		ibtnOpenThree.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(tvAttentionDetail.getMaxLines() == 4)
				{
					tvAttentionDetail.setMaxLines(100);
					ibtnOpenThree.setImageResource(R.drawable.btn_close);
				}
				else
				{
					tvAttentionDetail.setMaxLines(4);
					ibtnOpenThree.setImageResource(R.drawable.btn_open);
				}
			}
		});
		tvAttentionDetail.post(new Runnable() {

		    @Override
		    public void run() {
		        int lineCount    = tvAttentionDetail.getLineCount();
		        if(lineCount>4){
		        	ibtnOpenThree.setVisibility(View.VISIBLE);
		        } else{
		        	ibtnOpenThree.setVisibility(View.GONE);
		        }
		    }
		});	*/
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
	
		default:
			break;
		}
	}


}
