package com.cmmobi.railwifi.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cmmobi.railwifi.R;
import com.cmmobi.railwifi.dialog.PromptDialog;
import com.cmmobi.railwifi.network.GsonResponseObject;
import com.cmmobi.railwifi.network.Requester;
import com.cmmobi.railwifi.utils.MyTextWatcher;
import com.cmmobi.railwifi.utils.StringUtils;
import com.cmmobi.railwifi.utils.ValidTextWatcher;
import com.cmmobi.railwifi.utils.ViewUtils;
import com.cmmobi.railwifi.view.ListDialog;

public class ComplaintSuggestActivity extends TitleRootActivity implements TextWatcher {

	private EditText etName;
	private EditText etCellphone;
	private EditText etContent;
	private TextView tvType;
	
	private ListDialog listDialog;
	private ImageView ivPhoneError;
	private ImageView ivNameError;
	
	@Override
	public boolean handleMessage(Message msg) {
		// TODO Auto-generated method stub
		switch(msg.what) {
		case Requester.RESPONSE_TYPE_COMPLAINT:
			GsonResponseObject.commonContent resp = (GsonResponseObject.commonContent) msg.obj;
			if(resp != null && "0".equals(resp.status)){
				Toast.makeText(this, "发送成功", Toast.LENGTH_LONG).show();
				finish();
			}else{
				Toast.makeText(this, "发送失败", Toast.LENGTH_LONG).show();
			}
			break;
		}
		return false;
	}

	@Override
	public int subContentViewId() {
		// TODO Auto-generated method stub
		return R.layout.activity_complaint_suggest;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setTitleText("投诉建议");
		setRightButtonText("发送>>");
		
		initView();
	}
	
	private void initView() {
		// TODO Auto-generated method stub
		etName = (EditText) findViewById(R.id.et_contact_name);
		etCellphone = (EditText) findViewById(R.id.et_contact_celphome);
		tvType = (TextView) findViewById(R.id.tv_type_name);
		etContent = (EditText) findViewById(R.id.et_complaints);
		ivPhoneError = (ImageView) findViewById(R.id.iv_phone_error);
		ivNameError = (ImageView) findViewById(R.id.iv_name_error);
		etCellphone.addTextChangedListener(new ValidTextWatcher(etCellphone,ivPhoneError));
		etName.addTextChangedListener(new ValidTextWatcher(etName,ivNameError));
		
		etContent.addTextChangedListener(this);
		
		listDialog = new ListDialog(this,tvType);
		listDialog.setTitle("内容类型");
		List<String> listStr = new ArrayList<String>();
		listStr.add("建议");
		listStr.add("投诉");
		listDialog.setDate(listStr);
		
		findViewById(R.id.rl_type).setOnClickListener(this);
		
		ViewUtils.setMarginRight(ivPhoneError, 12);
		ViewUtils.setMarginRight(ivNameError, 12);
		
		ViewUtils.setMarginTop(findViewById(R.id.rl_user_info), 12);
		ViewUtils.setMarginLeft(findViewById(R.id.rl_user_info), 12);
		ViewUtils.setMarginRight(findViewById(R.id.rl_user_info), 12);
		
		ViewUtils.setMarginTop(findViewById(R.id.tv_contact_name), 24);
		ViewUtils.setMarginLeft(findViewById(R.id.tv_contact_name), 12);
		
		ViewUtils.setSize(findViewById(R.id.tv_contact_name), 150, 99);
		ViewUtils.setSize(findViewById(R.id.tv_contact_celphome), 150, 99);
		ViewUtils.setMarginRight(findViewById(R.id.iv_icon_arrow), 24);
		ViewUtils.setSize(findViewById(R.id.tv_type), 150, 99);
		ViewUtils.setSize(findViewById(R.id.rl_type), 522, 99);
		ViewUtils.setSize(etName, 522, 99);
		ViewUtils.setSize(etCellphone, 522, 99);
		
		ViewUtils.setHeight(etContent, 228);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		InputMethodManager imm = (InputMethodManager) this
				  .getSystemService(Context.INPUT_METHOD_SERVICE);
		switch (v.getId()) {
		case R.id.btn_title_right:
			if (TextUtils.isEmpty(etName.getText())) {
				etName.requestFocus();
				etName.setHintTextColor(0xffc60606);
				imm.showSoftInput(etName, 0);
				return;
			}else if(!PromptDialog.checkName(etName.getText().toString().trim())){
				etName.requestFocus();
				ivNameError.setVisibility(View.VISIBLE);
				imm.showSoftInput(etName, 0);
				return;
			}
			if (TextUtils.isEmpty(etCellphone.getText())) {
				etCellphone.requestFocus();
				etCellphone.setHintTextColor(0xffc60606);
				imm.showSoftInput(etCellphone, 0);
				return;
			} else if (!PromptDialog.checkPhoneNum(etCellphone.getText().toString())) {
				etCellphone.requestFocus();
				ivPhoneError.setVisibility(View.VISIBLE);
				imm.showSoftInput(etCellphone, 0);
				return;
			}
			
			if (StringUtils.isEmpty(etContent.getText().toString().trim())) {
				PromptDialog.Dialog(this, "温馨提示", "请填写一些建议或意见吧！", "确定");
				return;
			}
			
			String name = etName.getText().toString();
			String cellPhone = etCellphone.getText().toString();
			int type = listDialog.getCurSelector();
			String content = etContent.getText().toString();
			
			String trainNum = "";
			if (MainActivity.train_num != null) {
				trainNum = MainActivity.train_num;
			} else {
				trainNum = "NULL";
			}
			Requester.requestComplaint(handler, name, cellPhone, "" + (type + 1), content, trainNum);
			break;
		case R.id.rl_type:
			listDialog.show();
			break;
		default:
			break;
		}
		super.onClick(v);
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub
		
	}

	private int cursorPos;

	@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub
		etContent.removeTextChangedListener(this);
		String editNumOri = etContent.getText().toString();
		cursorPos = etContent.getSelectionStart();
		String editNum = MyTextWatcher.removeExpression(editNumOri);
		if (!editNum.equals(editNumOri)) {
			
			if (cursorPos > editNum.length()) {
				cursorPos = editNum.length();
			}
			etContent.setText(editNum);
			etContent.setSelection(cursorPos);
		}
		etContent.addTextChangedListener(this);
	}
	
}
