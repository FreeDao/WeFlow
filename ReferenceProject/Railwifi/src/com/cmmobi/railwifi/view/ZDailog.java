package com.cmmobi.railwifi.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cmmobi.railwifi.R;
import com.cmmobi.railwifi.utils.DisplayUtil;
import com.cmmobi.railwifi.utils.StringUtils;
import com.cmmobi.railwifi.utils.ViewUtils;

/**
 * @date  2014-11-24
 */

public class ZDailog implements android.view.View.OnClickListener,AnimationListener {


	public ZDailog(Context context) {
//		super(context,R.style.CmmobiDialog);
		init(context);
	}

	AlertDialog alertDialog;
	private LayoutInflater inflater;
	private Button btnOK;
	private Button btnCancel;
	private TextView tvTitle;
	private TextView tvContent;
	private View view;
	private ImageView ivIcon;
	private TextView tvInfo;
	private LinearLayout llBtns;
//	private Animation startAnim;
//	private Animation endAnim;
//	private Animation loopAnim;
	
	private AlertDialog.Builder builder;
	
	private void init(Context context){
//		alertDialog = new AlertDialog.Builder(context).create();
		builder = new AlertDialog.Builder(context); 
		inflater=LayoutInflater.from(context);
		view=inflater.inflate(R.layout.xdialog, null);
//		alertDialog.setContentView(view);
//		alertDialog.setView(view);
		(view.findViewById(R.id.ll_content)).setMinimumHeight(DisplayUtil.getSize(context, 372));
		
		ViewUtils.setWidth((view.findViewById(R.id.ll_content)), 564);
		
		tvInfo = (TextView) view.findViewById(R.id.tv_info);
		tvInfo.setTextSize(DisplayUtil.textGetSizeSp(context, 24));
		ivIcon = (ImageView) view.findViewById(R.id.iv_icon);
		ivIcon.setVisibility(View.GONE);
		
		tvInfo.setVisibility(View.GONE);
		
		btnOK=(Button) view.findViewById(R.id.btn_ok);
		btnCancel=(Button) view.findViewById(R.id.btn_cancel);
		int size12 = DisplayUtil.getSize(context, 12);
		int size30 = DisplayUtil.getSize(context, 30);
		int size20 = DisplayUtil.getSize(context, 20);
		
	
		tvInfo.setPadding(0, 0, 0, DisplayUtil.getSize(context, 25));
		llBtns = (LinearLayout) view.findViewById(R.id.ll_btns);
		llBtns.setPadding(0, size30, 0, size30);
		
		btnOK.setVisibility(View.GONE);
		btnOK.setTextSize(DisplayUtil.textGetSizeSp(context, 33));
		btnCancel.setVisibility(View.GONE);
		btnCancel.setTextSize(DisplayUtil.textGetSizeSp(context, 33));
		btnCancel.setOnClickListener(this);
		btnOK.setOnClickListener(this);
		tvTitle=(TextView) view.findViewById(R.id.tv_title);
		tvTitle.setPadding(size12, size20, size12, size20);
		tvTitle.setVisibility(View.GONE);
		tvTitle.setTextSize(DisplayUtil.textGetSizeSp(context, 42));
		tvContent=(TextView) view.findViewById(R.id.tv_content);
		tvContent.setPadding(size30, size12, size30, size12*2);
		tvContent.setTextSize(DisplayUtil.textGetSizeSp(context, 36));
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_cancel:
			alertDialog.dismiss();
        	if(cancelClickListener!=null)cancelClickListener.onClick(alertDialog/*Xdialog.this*/, 0);
			break;
		case R.id.btn_ok:
			alertDialog.dismiss();
			if(okClickListener!=null)okClickListener.onClick(alertDialog/*Xdialog.this*/, 0);
			break;
		default:
			break;
		}
	}
	
	private DialogInterface.OnClickListener okClickListener;
	private DialogInterface.OnClickListener cancelClickListener;
	
    public ZDailog setCancelable(boolean flag) {
    	builder.setCancelable(flag);
        return this;
    }
	
	public ZDailog setTitle(String title){
		tvTitle.setVisibility(View.VISIBLE);
		tvTitle.setText(title);
		return this;
	}
	
	public ZDailog setTitle(Spanned title){
		tvTitle.setVisibility(View.VISIBLE);
		tvTitle.setText(title);
		return this;
	}
	
	public ZDailog setMessage(String msg){
		tvContent.setText(msg);
		return this;
	}
	
	public ZDailog setMessage(Spanned msg){
		tvContent.setText(msg);
		return this;
	}
	
	public ZDailog setMessage(SpannableStringBuilder style){
		tvContent.setText(style);
		return this;
	}
	
	public ZDailog setMessage(int res){
		tvContent.setText(res);
		return this;
	}
	
	public ZDailog setPositiveButton(String s,DialogInterface.OnClickListener listener){
		btnOK.setVisibility(View.VISIBLE);
		btnOK.setText(s);
		okClickListener=listener;
		return this;
	}
	
	public ZDailog setNegativeButton(String s, DialogInterface.OnClickListener listener){
		btnCancel.setVisibility(View.VISIBLE);
		btnCancel.setText(s);
		cancelClickListener=listener;
		return this;
	}
	
	
	public ZDailog setInfo(String msg){
		tvInfo.setVisibility(View.VISIBLE);
		tvInfo.setText(msg);
		return this;
	}
	
	public ZDailog showIcon(boolean isShow){
		if(isShow){
			ivIcon.setVisibility(View.VISIBLE);
		}else{
			ivIcon.setVisibility(View.GONE);
		}
		return this;
	}
	
	public ZDailog createX(){
		if(btnCancel.getVisibility()==View.GONE){
			ViewUtils.setMarginRight(btnOK, 0);
			if (StringUtils.isEmpty(btnOK.getText())) {
				btnOK.setText("确定");
			}
		}else{
			ViewUtils.setMarginRight(btnOK, 24);
		}
		alertDialog = builder.create();
		return this;
	}

	
	@Override
	public void onAnimationEnd(Animation animation) {
		Handler handler = new Handler();
	    handler.postDelayed(new Runnable() {
	        @Override
	        public void run() {
	        	alertDialog.dismiss();
	        	if("ok".equals(view.getTag())){
	        		if(okClickListener!=null)okClickListener.onClick(alertDialog/*Xdialog.this*/, 0);
	        	}else{
	        		if(cancelClickListener!=null)cancelClickListener.onClick(alertDialog/*Xdialog.this*/, 0);
	        	}
	        }
	    }, 10);
	}
	@Override
	public void onAnimationRepeat(Animation animation) {}
	@Override
	public void onAnimationStart(Animation animation) {}
	public void dismiss() {
		// TODO Auto-generated method stub
		if(alertDialog!=null){
			try{
				alertDialog.dismiss();
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}

	}
	public void show() {
		// TODO Auto-generated method stub
		alertDialog.show();
		
		alertDialog.setContentView(view);
	}
	public Window getWindow() {
		// TODO Auto-generated method stub
		return alertDialog.getWindow();
	}

	public void setCanceledOnTouchOutside(boolean isCancelable) {
		// TODO Auto-generated method stub
		alertDialog.setCanceledOnTouchOutside(isCancelable);
	}
}

