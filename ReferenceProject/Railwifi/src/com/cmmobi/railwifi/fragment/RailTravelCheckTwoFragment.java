package com.cmmobi.railwifi.fragment;

import java.util.ArrayList;
import java.util.Collections;

import org.apache.mina.filter.reqres.Response;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.cmmobi.railwifi.R;
import com.cmmobi.railwifi.adapter.RailTravelDetailListAdapter;
import com.cmmobi.railwifi.network.GsonResponseObject;
import com.cmmobi.railwifi.utils.DisplayUtil;
import com.google.gson.Gson;

public class RailTravelCheckTwoFragment extends Fragment implements OnClickListener {

	private View contentView;
	private ListView lvTravelList;
	private RailTravelDetailListAdapter mAdapter;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		contentView = inflater.inflate(
				R.layout.activity_railtravel_detail_check_two, null);	
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
		lvTravelList = (ListView)contentView.findViewById(R.id.lv_travellist);
		lvTravelList.setPadding(0, 0, 0, DisplayUtil.getSize(getActivity(), 12));
		Bundle bundle = getArguments();
		if(bundle != null){
			String info = bundle.getString("info");
			GsonResponseObject.travelLineInfoResp resp= new Gson().fromJson(info, GsonResponseObject.travelLineInfoResp.class);
			mAdapter = new RailTravelDetailListAdapter(getActivity(),resp.travellist);
			lvTravelList.setAdapter(mAdapter);
		}
		
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
	
		default:
			break;
		}
	}


}
