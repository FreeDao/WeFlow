package com.cmmobi.railwifi.fragment;

import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;

import com.cmmobi.railwifi.R;
import com.cmmobi.railwifi.network.GsonResponseObject.newsListResp;
import com.cmmobi.railwifi.utils.DisplayUtil;
import com.nostra13.universalimageloader.api.MyImageLoader;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class RailTravelCheckOneFragment extends Fragment implements OnClickListener {

	private View contentView;
	private MyImageLoader imageLoader = null;
	private DisplayImageOptions imageLoaderOptions = null;
	
	private TextView tvServiceDetail;
	private TextView tvLinePointDetail;
	private ImageButton ibtnOpenOne;
	private ImageButton ibtnOpenTwo;
	private TextView tvService;
	private TextView tvLinePoint;
	private ImageView ivImage;
    private LinearLayout llCheckOne;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		contentView = inflater.inflate(
				R.layout.activity_railtravel_detail_check_one, null);
		imageLoader = MyImageLoader.getInstance();
		imageLoaderOptions = new DisplayImageOptions.Builder()
				.cacheInMemory(true)
				.cacheOnDisc()
				.imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
				.bitmapConfig(Bitmap.Config.RGB_565)
				.showImageOnFail(R.drawable.content_pic_default_9)
				.showImageForEmptyUri(R.drawable.content_pic_default_9)
				.showImageOnLoading(R.drawable.content_pic_default_9)
				.build();
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
		
		llCheckOne = (LinearLayout) contentView.findViewById(R.id.ll_check_one);
		llCheckOne.setPadding(DisplayUtil.getSize(getActivity(), 12), DisplayUtil.getSize(getActivity(), 21), DisplayUtil.getSize(getActivity(), 12), DisplayUtil.getSize(getActivity(), 12));
		
		ivImage = (ImageView) contentView.findViewById(R.id.iv_image);
		LinearLayout.LayoutParams lParams = (LinearLayout.LayoutParams) ivImage.getLayoutParams();
		lParams.bottomMargin = DisplayUtil.getSize(getActivity(), 21);
		lParams.width = DisplayUtil.getScreenWidth(getActivity()) - DisplayUtil.getSize(getActivity(), 12)*2;
		lParams.height = DisplayUtil.getSize(getActivity(), 335);
		ivImage.setLayoutParams(lParams);
		tvService = (TextView) contentView.findViewById(R.id.tv_services);
		tvService.setTextSize(DisplayUtil.textGetSizeSp(getActivity(), 33));
		tvService.setTypeface(Typeface.DEFAULT_BOLD);
		lParams = (LinearLayout.LayoutParams) tvService.getLayoutParams();
		lParams.height = DisplayUtil.getSize(getActivity(), 42);
		tvService.setLayoutParams(lParams);
		
		tvServiceDetail = (TextView) contentView.findViewById(R.id.tv_services_detail);
		tvServiceDetail.setTextSize(DisplayUtil.textGetSizeSp(getActivity(), 27));
		tvServiceDetail.setLineSpacing(0, (float)1.5);
		tvServiceDetail.setPadding(DisplayUtil.getSize(getActivity(), 12), 0, DisplayUtil.getSize(getActivity(), 12), 0);
		
		tvLinePoint = (TextView) contentView.findViewById(R.id.tv_linepoint);
		tvLinePoint.setTextSize(DisplayUtil.textGetSizeSp(getActivity(), 33));
		tvLinePoint.setTypeface(Typeface.DEFAULT_BOLD);
		lParams = (LinearLayout.LayoutParams) tvLinePoint.getLayoutParams();
		lParams.height = DisplayUtil.getSize(getActivity(), 42);
		lParams.topMargin =  DisplayUtil.getSize(getActivity(), 12);
		tvLinePoint.setLayoutParams(lParams);
		
		tvLinePointDetail = (TextView) contentView.findViewById(R.id.tv_linepoint_detail);
		tvLinePointDetail.setTextSize(DisplayUtil.textGetSizeSp(getActivity(), 27));
		tvLinePointDetail.setLineSpacing(0, (float)1.5);
		tvLinePointDetail.setPadding(DisplayUtil.getSize(getActivity(), 12), 0, DisplayUtil.getSize(getActivity(), 12), 0);
		
		Bundle bundle = getArguments();
		if(bundle != null){
			String services = bundle.getString("services");
			String linepoint = bundle.getString("linepoint");
			String uri = bundle.getString("img");
			tvLinePointDetail.setText(linepoint);
			tvServiceDetail.setText(services);
			imageLoader.displayImage(uri, ivImage, imageLoaderOptions, new ImageLoadingListener() {
				
				@Override
				public void onLoadingStarted(String arg0, View arg1) {
					// TODO Auto-generated method stub
				}
				
				@Override
				public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
					// TODO Auto-generated method stub
				}
				
				@Override
				public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
					// TODO Auto-generated method stub
					ivImage.setScaleType(ScaleType.CENTER_CROP);
				}
				
				@Override
				public void onLoadingCancelled(String arg0, View arg1) {
					// TODO Auto-generated method stub
				}
			});
		}
		
		/*ibtnOpenOne = (ImageButton) contentView.findViewById(R.id.btn_open_one);
		ibtnOpenOne.setPadding(0, DisplayUtil.getSize(getActivity(), 12), DisplayUtil.getSize(getActivity(), 48), DisplayUtil.getSize(getActivity(), 21));
		ibtnOpenOne.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(tvServiceDetail.getMaxLines() == 4)
				{
					tvServiceDetail.setMaxLines(100);
					ibtnOpenOne.setImageResource(R.drawable.btn_close);
				}
				else
				{
					tvServiceDetail.setMaxLines(4);
					ibtnOpenOne.setImageResource(R.drawable.btn_open);
				}
			}
		});
		tvServiceDetail.post(new Runnable() {

		    @Override
		    public void run() {
		        int lineCount    = tvServiceDetail.getLineCount();
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
				if(tvLinePointDetail.getMaxLines() == 4)
				{
					tvLinePointDetail.setMaxLines(100);
					ibtnOpenTwo.setImageResource(R.drawable.btn_close);
				}
				else
				{
					tvLinePointDetail.setMaxLines(4);
					ibtnOpenTwo.setImageResource(R.drawable.btn_open);
				}
			}
		});
		tvLinePointDetail.post(new Runnable() {

		    @Override
		    public void run() {
		        int lineCount    = tvLinePointDetail.getLineCount();
		        if(lineCount>4){
		        	ibtnOpenTwo.setVisibility(View.VISIBLE);
		        } else{
		        	ibtnOpenTwo.setVisibility(View.GONE);
		        }
		    }
		});*/
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
	
		default:
			break;
		}
	}


}
