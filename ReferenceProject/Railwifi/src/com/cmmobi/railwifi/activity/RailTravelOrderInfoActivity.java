package com.cmmobi.railwifi.activity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.cmmobi.common.promt.Prompt;
import com.cmmobi.railwifi.R;
import com.cmmobi.railwifi.dialog.PromptDialog;
import com.cmmobi.railwifi.network.GsonResponseObject;
import com.cmmobi.railwifi.utils.ConStant;
import com.cmmobi.railwifi.utils.DateUtils;
import com.cmmobi.railwifi.utils.DisplayUtil;
import com.cmmobi.railwifi.utils.MyTextWatcher;
import com.cmmobi.railwifi.utils.RailTravelInfo;
import com.cmmobi.railwifi.utils.StringUtils;
import com.cmmobi.railwifi.utils.ViewUtils;
import com.google.gson.Gson;

public class RailTravelOrderInfoActivity extends TitleRootActivity {

	private EditText etAdultNum = null;
	private EditText etChildNum = null;
	private EditText etContactName = null;
	private EditText etContactTel = null;
	private EditText etContactEmail = null;
	private EditText etContactIdNum = null;
	private ImageView ivAdultPlus = null;
	private ImageView ivAdultMinus = null;
	private ImageView ivChildPlus = null;
	private ImageView ivChildMinus = null;
	private ImageView ivPhoneError = null;
	private ImageView ivEmailError = null;
	private ImageView ivNameError = null;
	private ImageView ivIdCardError = null;
	private ToggleButton toggleButtom = null;
	private TextView tvDeparture;
	private TextView tvProtocol = null;
	private String departureTimeStr = null;
	public static String INTENT_DATE_PICKER_RESULT = "intent_date_picker_result";
	public static String INTENT_DATE_ADULT_PRICE = "intent_date_adult_price";
	public static String INTENT_DATE_KID_PRICE = "intent_date_kid_price";
	public static int REQUEST_DATE_PICKER = 0xaabb;
	private String lineId = "";
	private String adultPrice;
	private String kidPrice;
	GsonResponseObject.travelLineInfoResp lineInfoResp = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setTitleText("订单信息");
		setRightButtonText("提交订单>>");
		lineId = getIntent().getStringExtra(ConStant.INTENT_LINE_ID);
		String lineInfoStr = getIntent().getStringExtra("lineinfo");
		if (lineInfoStr != null) {
			lineInfoResp = new Gson().fromJson(lineInfoStr, GsonResponseObject.travelLineInfoResp.class);
		}
		initViews();
		findViewById(R.id.tv_not_use).requestFocus();
	}
	
	private void initViews() {
		etAdultNum = (EditText) findViewById(R.id.et_adult_ticket_num);
		etChildNum = (EditText) findViewById(R.id.et_child_ticket_num);
		etContactName = (EditText) findViewById(R.id.et_contact_name);
		etContactTel = (EditText) findViewById(R.id.et_contact_celphome);
		etContactEmail = (EditText) findViewById(R.id.et_contact_email);
		etContactIdNum = (EditText) findViewById(R.id.et_contact_id_num);
		ivPhoneError = (ImageView) findViewById(R.id.iv_phone_error);
		ivEmailError = (ImageView) findViewById(R.id.iv_email_error);
		ivIdCardError = (ImageView) findViewById(R.id.iv_idnum_error);
		ivNameError = (ImageView) findViewById(R.id.iv_name_error);
		etAdultNum.setOnFocusChangeListener(MyTextWatcher.onFocusAutoClearHintListener);
		etChildNum.setOnFocusChangeListener(MyTextWatcher.onFocusAutoClearHintListener);
		
		tvProtocol = (TextView) findViewById(R.id.tv_read_protocol_text);
		tvProtocol.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//下划线
		
		etAdultNum.addTextChangedListener(new MyTextWatcher(etAdultNum));
		etContactName.addTextChangedListener(new NameTextWatcher(etContactName));
		etContactTel.addTextChangedListener(new PhoneTextWatcher(etContactTel));
		etContactEmail.addTextChangedListener(new EmailTextWatcher(etContactEmail));
		etContactIdNum.addTextChangedListener(new IdTextWatcher(etContactIdNum));
		
		ivAdultPlus = (ImageView) findViewById(R.id.iv_adult_ticket_plus);
		ivAdultMinus = (ImageView) findViewById(R.id.iv_adult_ticket_minus);
		ivChildPlus = (ImageView) findViewById(R.id.iv_child_ticket_plus);
		ivChildMinus = (ImageView) findViewById(R.id.iv_child_ticket_minus);
		tvDeparture = (TextView) findViewById(R.id.tv_departure_date);
		
		if (lineInfoResp != null) {
			if (StringUtils.isEmpty(lineInfoResp.kid_price) || "-".equals(lineInfoResp.kid_price)) {
				ivChildPlus.setEnabled(false);
				ivChildMinus.setEnabled(false);
				
				etChildNum.setEnabled(false);
			}
		}
		
		toggleButtom = (ToggleButton) findViewById(R.id.tb_read_protocol);
		toggleButtom.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					Resources resource=(Resources)RailTravelOrderInfoActivity.this.getResources();   
					ColorStateList csl=(ColorStateList)resource.getColorStateList(R.color.btn_protocal_text); 
					tvProtocol.setTextColor(csl);
				}
			}
		});
		
		ivAdultPlus.setOnClickListener(this);
		ivAdultMinus.setOnClickListener(this);
		ivChildPlus.setOnClickListener(this);
		ivChildMinus.setOnClickListener(this);
		tvDeparture.setOnClickListener(this);
		findViewById(R.id.rl_read_protocol_text).setOnClickListener(this);
		
		ViewUtils.setMarginRight(ivPhoneError, 12);
		ViewUtils.setMarginRight(ivEmailError, 12);
		ViewUtils.setMarginRight(ivIdCardError, 12);
		ViewUtils.setMarginRight(ivNameError, 12);
		
		ViewUtils.setMarginTop(findViewById(R.id.sv_scrollview), 12);
		ViewUtils.setMarginBottom(findViewById(R.id.sv_scrollview), 12);
		ViewUtils.setMarginLeft(findViewById(R.id.rl_ticket_info), 12);
		ViewUtils.setMarginRight(findViewById(R.id.rl_ticket_info), 12);
		ViewUtils.setMarginLeft(findViewById(R.id.rl_adult_ticket_info), 18);
		ViewUtils.setMarginRight(findViewById(R.id.rl_adult_ticket_info), 20);
		ViewUtils.setMarginTop(findViewById(R.id.rl_adult_ticket_info), 45);
		((TextView) findViewById(R.id.tv_adult_picket_label)).setTextSize(DisplayUtil.textGetSizeSp(this, 33));
		ViewUtils.setMarginLeft(findViewById(R.id.tv_adult_picket_intro), 12);
		((TextView) findViewById(R.id.tv_adult_picket_intro)).setTextSize(DisplayUtil.textGetSizeSp(this, 30));
		((TextView) findViewById(R.id.tv_adult_ticket_num_lable)).setTextSize(DisplayUtil.textGetSizeSp(this, 33));
		ViewUtils.setMarginRight(etAdultNum, 68);
		ViewUtils.setMarginRight(findViewById(R.id.tv_adult_ticket_num_lable), 32);
		((TextView) findViewById(R.id.tv_child_picket_label)).setTextSize(DisplayUtil.textGetSizeSp(this, 33));
		((TextView) findViewById(R.id.tv_child_picket_intro)).setTextSize(DisplayUtil.textGetSizeSp(this, 30));
		((TextView) findViewById(R.id.tv_child_ticket_num_lable)).setTextSize(DisplayUtil.textGetSizeSp(this, 33));
		
		ViewUtils.setMarginTop(findViewById(R.id.rl_child_ticket_info), 45);
		ViewUtils.setMarginLeft(findViewById(R.id.tv_child_picket_intro), 12);
		ViewUtils.setMarginRight(etChildNum, 68);
		
		ViewUtils.setMarginTop(findViewById(R.id.rl_departure_date), 56);
		((TextView) findViewById(R.id.tv_departure_date_label)).setTextSize(DisplayUtil.textGetSizeSp(this, 33));
		((TextView) findViewById(R.id.tv_departure_date)).setTextSize(DisplayUtil.textGetSizeSp(this, 30));
		
		ViewUtils.setMarginTop(findViewById(R.id.rl_read_protocol), 40);
		ViewUtils.setMarginLeft(findViewById(R.id.rl_read_protocol_text), 22);
		((TextView) findViewById(R.id.tv_read_protocol_text)).setTextSize(DisplayUtil.textGetSizeSp(this, 27));
		
		ViewUtils.setMarginTop(findViewById(R.id.tv_contact_info), 22);
		ViewUtils.setMarginLeft(findViewById(R.id.tv_contact_info), 30);
		
		ViewUtils.setMarginTop(findViewById(R.id.rl_contact_info), 16);
		
		/*ViewUtils.setMarginTop(findViewById(R.id.tv_contact_name), 22);
		ViewUtils.setMarginLeft(findViewById(R.id.tv_contact_name), 21);
		((TextView) findViewById(R.id.tv_contact_name)).setTextSize(DisplayUtil.textGetSizeSp(this, 30));
		
		ViewUtils.setMarginTop(findViewById(R.id.tv_contact_celphome), 28);
		((TextView) findViewById(R.id.tv_contact_celphome)).setTextSize(DisplayUtil.textGetSizeSp(this, 30));
		
		ViewUtils.setMarginTop(findViewById(R.id.et_contact_email), 28);
		((TextView) findViewById(R.id.et_contact_email)).setTextSize(DisplayUtil.textGetSizeSp(this, 30));

		ViewUtils.setMarginTop(findViewById(R.id.tv_contact_id_num), 28);
		((TextView) findViewById(R.id.tv_contact_id_num)).setTextSize(DisplayUtil.textGetSizeSp(this, 30));
		
		ViewUtils.setSize(etContactName, 489, 66);
		ViewUtils.setSize(etContactTel, 489, 66);
		ViewUtils.setSize(etContactEmail, 489, 66);
		ViewUtils.setSize(etContactIdNum, 489, 66);
		ViewUtils.setMarginTop(etContactName, 12);
		ViewUtils.setMarginTop(etContactTel, 12);
		ViewUtils.setMarginTop(etContactEmail, 12);
		ViewUtils.setMarginTop(etContactIdNum, 12);*/
		
		((TextView) findViewById(R.id.tv_contact_name)).setTextSize(DisplayUtil.textGetSizeSp(this, 36));
		((TextView) findViewById(R.id.tv_contact_celphome)).setTextSize(DisplayUtil.textGetSizeSp(this, 36));
		((TextView) findViewById(R.id.et_contact_email)).setTextSize(DisplayUtil.textGetSizeSp(this, 36));
		((TextView) findViewById(R.id.tv_contact_id_num)).setTextSize(DisplayUtil.textGetSizeSp(this, 36));
		ViewUtils.setMarginTop(findViewById(R.id.tv_contact_name), 24);
		ViewUtils.setMarginLeft(findViewById(R.id.tv_contact_name), 12);
		
		ViewUtils.setSize(findViewById(R.id.tv_contact_name), 150, 99);
		ViewUtils.setSize(findViewById(R.id.tv_contact_celphome), 150, 99);
		ViewUtils.setSize(findViewById(R.id.tv_contact_email), 150, 99);
		ViewUtils.setSize(findViewById(R.id.tv_contact_id_num), 150, 99);
		ViewUtils.setSize(etContactName, 522, 99);
		ViewUtils.setSize(etContactTel, 522, 99);
		ViewUtils.setSize(etContactEmail, 522, 99);
		ViewUtils.setSize(etContactIdNum, 522, 99);
		
	}
	
	class NameTextWatcher extends MyTextWatcher {

		public NameTextWatcher(EditText et) {
			super(et);
			// TODO Auto-generated constructor stub
		}
		
		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub
			ivNameError.setVisibility(View.GONE);
			super.onTextChanged(s, start, before, count);
		}
	}
	
	class PhoneTextWatcher extends MyTextWatcher {

		public PhoneTextWatcher(EditText et) {
			super(et);
			// TODO Auto-generated constructor stub
		}
		
		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub
			ivPhoneError.setVisibility(View.GONE);
			super.onTextChanged(s, start, before, count);
		}
	}
	
	class IdTextWatcher extends MyTextWatcher {

		public IdTextWatcher(EditText et) {
			super(et);
			// TODO Auto-generated constructor stub
		}
		
		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub
			String editable = et.getText().toString();   
	        String str = stringIDFilter(editable.toString()); 
	        if(!editable.equals(str)){ 
	        	et.setText(str); 
	            //设置新的光标所在位置   
	        	et.setSelection(str.length()); 
	        } 
	        ivIdCardError.setVisibility(View.GONE);
			super.onTextChanged(s, start, before, count);
		}
	}
	
	class EmailTextWatcher extends MyTextWatcher {

		public EmailTextWatcher(EditText et) {
			super(et);
			// TODO Auto-generated constructor stub
		}
		
		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub
			
			String editable = et.getText().toString();   
	        String str = stringEmailFilter(editable.toString()); 
	        if(!editable.equals(str)){ 
	        	et.setText(str); 
	            //设置新的光标所在位置 www.2cto.com    
	        	et.setSelection(str.length()); 
	        }
	        ivEmailError.setVisibility(View.GONE);
			super.onTextChanged(s, start, before, count);
		}
		
	}
	
	public static String stringEmailFilter(String str) throws PatternSyntaxException {
		String   regEx  =  "[^a-zA-Z0-9_@\\-\\.]";                      
	    Pattern   p   =   Pattern.compile(regEx);      
	    Matcher   m   =   p.matcher(str);      
	    return   m.replaceAll("").trim();
	}
	
	public static String stringIDFilter(String str)throws PatternSyntaxException{      
	    // 只允许字母和数字        
	    String   regEx  =  "[^a-zA-Z0-9]";                      
	    Pattern   p   =   Pattern.compile(regEx);      
	    Matcher   m   =   p.matcher(str);      
	    return   m.replaceAll("").trim();      
	}   
	
	
	@Override
	public boolean handleMessage(Message msg) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int subContentViewId() {
		// TODO Auto-generated method stub
		return R.layout.activity_railtravel_order_info;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		final InputMethodManager imm = (InputMethodManager) this
				  .getSystemService(Context.INPUT_METHOD_SERVICE);
		switch (v.getId()) {
		case R.id.btn_title_right:
			if (getAdultTicketNum() == 0) {
				PromptDialog.Dialog(this, false, "温馨提示", "请至少添加一张成人票", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						handler.postDelayed(new Runnable() {
							
							@Override
							public void run() {
								// TODO Auto-generated method stub
								etAdultNum.requestFocus();
								etAdultNum.setHintTextColor(0xffc60606);
								imm.showSoftInput(etAdultNum, 0);
							}
						}, 100);
					}
				});
				return;
			}
			
			if (TextUtils.isEmpty(etContactName.getText())) {
				etContactName.requestFocus();
				imm.showSoftInput(etContactName, 0);
				etContactName.setHintTextColor(0xffc60606);
				return;
			} else if(!PromptDialog.checkName(etContactName.getText().toString().trim())){
				etContactName.requestFocus();
				ivNameError.setVisibility(View.VISIBLE);
				imm.showSoftInput(etContactName, 0);
				return;
			}
			
			if (TextUtils.isEmpty(etContactTel.getText())) {
				etContactTel.requestFocus();
				etContactTel.setHintTextColor(0xffc60606);
				imm.showSoftInput(etContactTel, 0);
				return;
			} else if (!PromptDialog.checkPhoneNum(etContactTel.getText().toString())) {
				etContactTel.requestFocus();
				ivPhoneError.setVisibility(View.VISIBLE);
				imm.showSoftInput(etContactTel, 0);
				return;
			}
			
			if (TextUtils.isEmpty(etContactEmail.getText())) {
				etContactEmail.requestFocus();
				etContactEmail.setHintTextColor(0xffc60606);
				imm.showSoftInput(etContactTel, 0);
				return;
			} else if(!PromptDialog.checkEmail(etContactEmail.getText().toString())) {
				etContactEmail.requestFocus();
				ivEmailError.setVisibility(View.VISIBLE);
				imm.showSoftInput(etContactEmail, 0);
				return;
			}
			
			if (TextUtils.isEmpty(etContactIdNum.getText())) {
				etContactIdNum.requestFocus();
				etContactIdNum.setHintTextColor(0xffc60606);
				imm.showSoftInput(etContactIdNum, 0);
				return;
			} else if (!PromptDialog.checkIdCard(etContactIdNum.getText().toString())) {
				etContactIdNum.requestFocus();
				ivIdCardError.setVisibility(View.VISIBLE);
				imm.showSoftInput(etContactIdNum, 0);
				return;
			}
			
			if (departureTimeStr == null) {
				PromptDialog.Dialog(this, true, "温馨提示", "请选择出发日期!", new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						Intent intentDate = new Intent(RailTravelOrderInfoActivity.this, DatePickerActivity.class);
						intentDate.putExtra(ConStant.INTENT_LINE_ID, lineId);
						startActivityForResult(intentDate, REQUEST_DATE_PICKER);
						
					}
				}, new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
					}
				});
				return;
			}
			
			if (!toggleButtom.isChecked()) {
				tvProtocol.setTextColor(0xffc60606);
				return;
			}
			
			RailTravelInfo railTravelInfo = new RailTravelInfo();
			railTravelInfo.adultTicket = "" + getAdultTicketNum();
			railTravelInfo.childTicket = "" + getChildTicketNum();
			railTravelInfo.name = etContactName.getText().toString();
			railTravelInfo.cellPhone = etContactTel.getText().toString();
			railTravelInfo.emailAddr = etContactEmail.getText().toString();
			railTravelInfo.IdCardfNum = etContactIdNum.getText().toString();
			railTravelInfo.time = departureTimeStr;
			railTravelInfo.adultPrice = adultPrice;
			railTravelInfo.kidPrice = kidPrice;
			String railTravelInfoStr = new Gson().toJson(railTravelInfo);
			
			Intent intent = new Intent(this, RailTravelOrderDetailActivity.class);
			String lineinfo =  getIntent().getStringExtra("lineinfo");
			if(!TextUtils.isEmpty(lineinfo)){
				intent.putExtra("lineinfo", lineinfo);
			}
			if(!TextUtils.isEmpty(lineId)){
				intent.putExtra("lineid", lineId);
			}
			intent.putExtra("railtravelinfo", railTravelInfoStr);
			startActivityForResult(intent, RailTravelDetailAcitivity.REQUEST_CODE_SUCCESS);
			break;
		case R.id.iv_adult_ticket_plus:
			addTicket(etAdultNum);
			break;
		case R.id.iv_adult_ticket_minus:
			minusTicket(etAdultNum);
			break;
		case R.id.iv_child_ticket_plus:
			addTicket(etChildNum);
			break;
		case R.id.iv_child_ticket_minus:
			minusTicket(etChildNum);
			break;
		case R.id.tv_departure_date:
			Intent intentDate = new Intent(this, DatePickerActivity.class);
			intentDate.putExtra(ConStant.INTENT_LINE_ID, lineId);
			startActivityForResult(intentDate, REQUEST_DATE_PICKER);
			break;
		case R.id.rl_read_protocol_text:
			Intent intentProtocol = new Intent(this, RailTravelProtocolActivity.class);
			startActivity(intentProtocol);
			break;

		default:
			break;
		}
	}
	
	private void addTicket(EditText edit) {
		if (TextUtils.isEmpty(edit.getText().toString())) {
			edit.setText("1");
		} else {
			int num = Integer.parseInt(edit.getText().toString());
			edit.setText("" + (num + 1));
		}
		edit.clearFocus();
	}
	
	private void minusTicket(EditText edit) {
		if (TextUtils.isEmpty(edit.getText().toString())) {
			return;
		} else {
			int num = Integer.parseInt(edit.getText().toString());
			if (num > 1) {
				edit.setText("" + (num - 1));
			} else {
				edit.setText("");
			}
		}
		edit.clearFocus();
	}
	
	private int getAdultTicketNum() {
		int num = 0;
		if (!TextUtils.isEmpty(etAdultNum.getText().toString())) {
			num = Integer.parseInt(etAdultNum.getText().toString());
		}
		return num;
	}
	
	private int getChildTicketNum() {
		int num = 0;
		if (!TextUtils.isEmpty(etChildNum.getText().toString())) {
			num = Integer.parseInt(etChildNum.getText().toString());
		}
		return num;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (resultCode == RESULT_OK) {
			if (requestCode == REQUEST_DATE_PICKER) {
				if (data != null) {
					
					departureTimeStr = data.getStringExtra(INTENT_DATE_PICKER_RESULT);
					adultPrice = data.getStringExtra(INTENT_DATE_ADULT_PRICE);
					kidPrice = data.getStringExtra(INTENT_DATE_KID_PRICE);
					tvDeparture.setText(departureTimeStr);
				}
			}
			if(requestCode == RailTravelDetailAcitivity.REQUEST_CODE_SUCCESS){
				this.setResult(RESULT_OK);
				this.finish();
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}
