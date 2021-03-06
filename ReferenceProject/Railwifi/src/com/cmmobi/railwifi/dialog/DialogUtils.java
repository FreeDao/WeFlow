package com.cmmobi.railwifi.dialog;

import android.content.Context;
import android.content.Intent;

import com.cmmobi.railwifi.event.DialogEvent;
import com.cmmobi.railwifi.service.PromtService;

import de.greenrobot.event.EventBus;

public class DialogUtils {
	private static boolean useService = false;
	
	public static void SendLoadingDialogStart(Context context) {
		if(useService){
			Intent intent = new Intent(context, PromtService.class);
			intent.putExtra(PromtService.KEY_TYPE, PromtService.KEY_TYPE_LOADING_START);
			context.startService(intent);
		}else{
			EventBus.getDefault().post(DialogEvent.LOADING_START);
		}
	}
	
	public static void SendLoadingDialogEnd(Context context) {
		if(useService){
			Intent intent = new Intent(context, PromtService.class);
			intent.putExtra(PromtService.KEY_TYPE, PromtService.KEY_TYPE_LOADING_END);
			context.startService(intent);
		}else{
			EventBus.getDefault().post(DialogEvent.LOADING_END);
		}
	}
	
	public static void SendCallHelpDialog(Context context, String title, String content, String msgId, int notifyId) {
		if(useService){
			Intent intent = new Intent(context, PromtService.class);
			intent.putExtra(PromtService.KEY_TYPE, PromtService.KEY_TYPE_CALLHELP_DIALOG);
			intent.putExtra(PromtService.KEY_TITLE, title);
			intent.putExtra(PromtService.KEY_CONTENT, content);
			intent.putExtra(PromtService.KEY_MSGID, msgId);
			intent.putExtra(PromtService.KEY_NOTIFYID, notifyId);
			context.startService(intent);
		}else{
			DialogEvent e = DialogEvent.CALL_HELP_DIALOG;
			e.setTitle(title);
			e.setContent(content);
			e.setNotifyID(notifyId);
			EventBus.getDefault().post(e);
		}
	}
	
	public static void SendJumpDialog(Context context, String title, String content, String msgId, int notifyId, String type, String object_id) {
		if(useService){
			Intent intent = new Intent(context, PromtService.class);
			intent.putExtra(PromtService.KEY_TYPE, PromtService.KEY_TYPE_JUMP);
			intent.putExtra(PromtService.KEY_TITLE, title);
			intent.putExtra(PromtService.KEY_CONTENT, content);
			intent.putExtra(PromtService.KEY_MSGID, msgId);
			intent.putExtra(PromtService.KEY_NOTIFYID, notifyId);
			context.startService(intent);
		}else{
			DialogEvent e = DialogEvent.JUMP_TO;
			e.setTitle(title);
			e.setContent(content);
			e.setNotifyID(notifyId);
			e.setType(type);
			e.setObjectId(object_id);
			EventBus.getDefault().post(e);
		}
	}
	
	public static void SendDownloadDialog(Context context, String title, String content, String url, String packageName) {
		if(useService){
			Intent intent = new Intent(context, PromtService.class);
			intent.putExtra(PromtService.KEY_TYPE, PromtService.KEY_TYPE_PROMPT_DOWNLOAD);
			intent.putExtra(PromtService.KEY_TITLE, title);
			intent.putExtra(PromtService.KEY_CONTENT, content);
			intent.putExtra(PromtService.KEY_URL, url);
			intent.putExtra(PromtService.KEY_PACKAGE_NAME, packageName);
			context.startService(intent);
		}else{
			DialogEvent e = DialogEvent.DOWNLOAD;
			e.setTitle(title);
			e.setContent(content);
			e.setUrl(url);
			e.setData(packageName);
			EventBus.getDefault().post(e);
		}
	}
	
	public static void SendUpdateDialogForceAlways(Context context, String title, String content, String url) {
		if(useService){
			Intent intent = new Intent(context, PromtService.class);
			intent.putExtra(PromtService.KEY_TYPE, PromtService.KEY_TYPE_UPDATE_FORCE_DIALOG_ALWAYS);
			intent.putExtra(PromtService.KEY_TITLE, title);
			intent.putExtra(PromtService.KEY_CONTENT, content);
			intent.putExtra(PromtService.KEY_URL, url);
			context.startService(intent);
		}else{
			DialogEvent e = DialogEvent.UPDATE_FORCE_DIALOG_ALWAYS;
			e.setTitle(title);
			e.setContent(content);
			e.setUrl(url);
			EventBus.getDefault().post(e);
		}
	}
	
	public static void SendUpdateDialogForceDismiss(Context context, String title, String content, String url) {
		if(useService){
			Intent intent = new Intent(context, PromtService.class);
			intent.putExtra(PromtService.KEY_TYPE, PromtService.KEY_TYPE_UPDATE_FORCE_DIALOG_DISMISS);
			intent.putExtra(PromtService.KEY_TITLE, title);
			intent.putExtra(PromtService.KEY_CONTENT, content);
			intent.putExtra(PromtService.KEY_URL, url);
			context.startService(intent);
		}else{
			DialogEvent e = DialogEvent.UPDATE_FORCE_DIALOG_DISMISS;
			e.setTitle(title);
			e.setContent(content);
			e.setUrl(url);
			EventBus.getDefault().post(e);
		}
	}
	
	public static void SendUpdateDialogNormal(Context context, String title, String content, String url) {
		if(useService){
			Intent intent = new Intent(context, PromtService.class);
			intent.putExtra(PromtService.KEY_TYPE, PromtService.KEY_TYPE_UPDATE_NORMAL_DIALOG);
			intent.putExtra(PromtService.KEY_TITLE, title);
			intent.putExtra(PromtService.KEY_CONTENT, content);
			intent.putExtra(PromtService.KEY_URL, url);
			context.startService(intent);
		}else{
			DialogEvent e = DialogEvent.UPDATE_NORMAL_DIALOG;
			e.setTitle(title);
			e.setContent(content);
			e.setUrl(url);
			EventBus.getDefault().post(e);
		}
	}

}
