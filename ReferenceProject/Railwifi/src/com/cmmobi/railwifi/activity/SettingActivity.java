package com.cmmobi.railwifi.activity;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.trinea.android.common.util.NetWorkUtils;

import com.cmmobi.common.tools.Info;
import com.cmmobi.common.tools.SpHelper;
import com.cmmobi.railwifi.R;
import com.cmmobi.railwifi.dialog.DialogUtils;
import com.cmmobi.railwifi.dialog.PromptDialog;
import com.cmmobi.railwifi.download.DownloadEvent;
import com.cmmobi.railwifi.event.ParallelEvent;
import com.cmmobi.railwifi.network.CRMRequester;
import com.cmmobi.railwifi.network.CRM_Object;
import com.cmmobi.railwifi.network.CRM_Object.versionCheckResponse;
import com.cmmobi.railwifi.parallel.DiscScanTask;
import com.cmmobi.railwifi.parallel.DiskCleanTask;
import com.cmmobi.railwifi.parallel.ParallelManager;
import com.cmmobi.railwifi.sql.HistoryManager;
import com.cmmobi.railwifi.utils.CmmobiClickAgentWrapper;
import com.cmmobi.railwifi.utils.DateUtils;
import com.cmmobi.railwifi.utils.ViewUtils;
import com.nostra13.universalimageloader.api.MyImageLoader;

public class SettingActivity extends TitleRootActivity{
	private static final String TAG = "SettingActivity";
	private static final long SETTING_CACHE_SCAN_DU = 0x12468321;
	private static final long SETTING_CACHE_CLEAR = 0x12468322;
	public static final String PROMT_DATE = "promt_date";
	RelativeLayout btn_check_update;
	RelativeLayout btn_clear_cache;
	RelativeLayout btn_clear_history;
	RelativeLayout btn_download_manage;
	SharedPreferences mySharedPreferences= null;
	ImageView ivPrompt = null;
	TextView tv_disk_usage;
	File cacheFold;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setTitleText("设置");
		Log.e(TAG,"showContent in - devID:" + Info.getDevId(this));
		rightButton.setVisibility(View.GONE);
		
		btn_check_update = (RelativeLayout)findViewById(R.id.btn_check_update);
		btn_clear_cache = (RelativeLayout)findViewById(R.id.btn_clear_cache);
		btn_clear_history = (RelativeLayout)findViewById(R.id.btn_clear_history);
		tv_disk_usage = (TextView)findViewById(R.id.tv_disk_usage);
		btn_download_manage = (RelativeLayout) findViewById(R.id.btn_download_manage);
		
		btn_check_update.setOnClickListener(this);
		btn_clear_cache.setOnClickListener(this);
		btn_clear_history.setOnClickListener(this);
		btn_download_manage.setOnClickListener(this);
		
		ivPrompt = (ImageView) findViewById(R.id.iv_tips);
		ViewUtils.setSize(ivPrompt, 16, 16);
		ViewUtils.setMarginLeft(ivPrompt, 16);
		
		mySharedPreferences = getSharedPreferences("has_new_download", 
				Activity.MODE_PRIVATE); 

		cacheFold = MyImageLoader.getInstance().getDiskCache().getDirectory();
		PromptDialog.showProgressDialog(this);
		ParallelManager.getInstance().submitTask(new DiscScanTask(SETTING_CACHE_SCAN_DU, cacheFold));
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		if (mySharedPreferences.getBoolean("has_new", false)) {
			ivPrompt.setVisibility(View.VISIBLE);
        } else {
        	ivPrompt.setVisibility(View.GONE);
        }
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.btn_clear_cache:
			//Toast.makeText(this, R.string.startCleanCache, Toast.LENGTH_LONG).show();
			PromptDialog.showProgressDialog(this);
			CmmobiClickAgentWrapper.onEvent(this, "set", "2");
			ParallelManager.getInstance().submitTask(new DiskCleanTask(SETTING_CACHE_CLEAR, cacheFold));
			break;
		case R.id.btn_check_update:
			CmmobiClickAgentWrapper.onEvent(this, "set", "1");
			CRMRequester.checkVersion(handler);
			break;
		case R.id.btn_clear_history:
			CmmobiClickAgentWrapper.onEvent(this, "set", "3");
			HistoryManager.getInstance().removeAll();
			Toast.makeText(this, "清理成功", Toast.LENGTH_LONG).show();
			break;
		case R.id.btn_download_manage:
			startActivity(new Intent(this, DownloadManageActivity1.class));
			break;
		default:
			super.onClick(v);	
			break;
		}
	}
	
	
	@Override
	public boolean handleMessage(Message msg) {
		// TODO Auto-generated method stub
		switch(msg.what){
		case CRMRequester.RESPONSE_TYPE_VERSION_CHECK:
			CRM_Object.versionCheckResponse versionResp = (versionCheckResponse) msg.obj;
			doingCheckVersion(this, versionResp, true);
			break;
		}
		return false;
	}

	@Override
	public int subContentViewId() {
		// TODO Auto-generated method stub
		return R.layout.activity_setting;
	}
	
	public void onEventMainThread(Object _event) {
		if(_event instanceof ParallelEvent){
			ParallelEvent event = (ParallelEvent)_event;
			switch(event) {
			case CACHE_SCAN_DU:
				PromptDialog.dimissProgressDialog();
				tv_disk_usage.setText(event.getValue());
				break;
			case CACHE_CLEAR:
				PromptDialog.dimissProgressDialog();
				Toast.makeText(this, R.string.endCleanCache, Toast.LENGTH_LONG).show();
				ParallelManager.getInstance().submitTask(new DiscScanTask(SETTING_CACHE_SCAN_DU, cacheFold));
				break;
			default:
				break;
			}
		} else if (_event instanceof DownloadEvent) {
			if (_event == DownloadEvent.STATUS_CHANGED && ((DownloadEvent) _event).getType() == -1) {
				if (mySharedPreferences.getBoolean("has_new", false)) {
					ivPrompt.setVisibility(View.VISIBLE);
		        }
			}
		}

		super.onEventMainThread(_event);
	}
	
	public static void doingCheckVersion(final Context context, final versionCheckResponse resp, boolean zhudong){
		//升级类型 0—无需升级 1—强制升级 2—普通升级
		if(resp==null){
			return;
		}
		
		
		if("1".equals(resp.type)){
			if(NetWorkUtils.NETWORK_TYPE_WIFI.equals(NetWorkUtils.getNetWorkType(context))){
				DialogUtils.SendUpdateDialogForceAlways(context, "发现新版本", resp.description, resp.path);
			}else{
				DialogUtils.SendUpdateDialogForceDismiss(context, "发现新版本", resp.description, resp.path);
			}

		}else if("2".equals(resp.type)){
			String promtDate = SpHelper.getSharedPreferences(context, PROMT_DATE, "");
			String curDate = DateUtils.getDayString();
			if(zhudong || !promtDate.equals(curDate)){
				DialogUtils.SendUpdateDialogNormal(context, "发现新版本", resp.description, resp.path);
			}


		}else if("0".equals(resp.type)){
			if(zhudong){
				Toast.makeText(context, "当前已是最新版本", Toast.LENGTH_LONG).show();
			}
			
		}
	}
	
	public static void getApkUrl(Context context, String url){
		launchWebBrower(context, url);
	}
	
	public static void launchWebBrower(Context context, String url){

		if(url!=null){
			Uri uri = Uri.parse(url.replace("\"", ""));
			Log.e(TAG, "launchWebBrower url:" + url + ", uri:" + uri.toString());
			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
			intent.setData(uri); 
			try{
				context.startActivity(intent);
			}catch(android.content.ActivityNotFoundException e){
				e.printStackTrace();
			}

		}

	}
}
