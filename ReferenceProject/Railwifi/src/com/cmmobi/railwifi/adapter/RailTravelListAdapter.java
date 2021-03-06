package com.cmmobi.railwifi.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cmmobi.railwifi.R;
import com.cmmobi.railwifi.network.GsonResponseObject.LineInfo;
import com.cmmobi.railwifi.utils.DisplayUtil;
import com.nostra13.universalimageloader.api.BlurBitmapDisplayer;
import com.nostra13.universalimageloader.api.MyImageLoader;
import com.nostra13.universalimageloader.api.BlurBitmapDisplayer.BlurType;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
public class RailTravelListAdapter extends BaseAdapter {

	private Activity context;
	private LayoutInflater inflater;
	private List<LineInfo> listLine = new ArrayList<LineInfo>();
	MyImageLoader imageLoader = null;
	DisplayImageOptions imageLoaderOptions = null;
	
	public RailTravelListAdapter(final Activity context) {
		this.context = context;
		DisplayMetrics dm = context.getApplicationContext().getResources().getDisplayMetrics();
		inflater = LayoutInflater.from(context);
		
		imageLoader = MyImageLoader.getInstance();

		imageLoaderOptions = new DisplayImageOptions.Builder()
				.cacheInMemory(true)
				.cacheOnDisc()
				.imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
				.bitmapConfig(Bitmap.Config.RGB_565)
				.showImageOnFail(R.drawable.tourism_pic_bg_default)
				.showImageForEmptyUri(R.drawable.tourism_pic_bg_default)
				.showImageOnLoading(R.drawable.tourism_pic_bg_default)
				.displayer(new RoundedBitmapDisplayer(12))// 圆角图片
//				.displayer(new BlurBitmapDisplayer(BlurType.Both, 50))
				.build();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listLine.size();
	}

	@Override
	public LineInfo getItem(int position) {
		// TODO Auto-generated method stub
		return listLine.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = inflater.inflate(
					R.layout.activity_railtravel_item, null);
			holder = new ViewHolder();			
			holder.iv_travelImg = (ImageView) convertView.findViewById(R.id.iv_travel_img);
			RelativeLayout.LayoutParams pm = (RelativeLayout.LayoutParams) holder.iv_travelImg.getLayoutParams();
			pm.height = DisplayUtil.getSize(context, 260);
			pm.leftMargin = DisplayUtil.getSize(context, 12);
			pm.rightMargin = DisplayUtil.getSize(context, 12);
			pm.topMargin = DisplayUtil.getSize(context, 12);
			holder.iv_travelImg.setLayoutParams(pm);
			holder.tv_tag = (TextView) convertView.findViewById(R.id.tv_tag);
			holder.tv_tag.setTypeface(Typeface.MONOSPACE,Typeface.ITALIC);
			pm = (RelativeLayout.LayoutParams) holder.tv_tag.getLayoutParams();
			pm.topMargin= DisplayUtil.getSize(context, 12);
			pm.bottomMargin = DisplayUtil.getSize(context, 75);
			pm.width = DisplayUtil.getSize(context, 434);
			pm.height = DisplayUtil.getSize(context, 51);
			holder.tv_tag.setLayoutParams(pm);
			holder.tv_tag.setTextSize(DisplayUtil.textGetSizeSp(context, 27));
			holder.iv_title = (ImageView) convertView.findViewById(R.id.iv_title);
			pm = (RelativeLayout.LayoutParams) holder.iv_title.getLayoutParams();
			pm.leftMargin= DisplayUtil.getSize(context, 21);
			pm.height = DisplayUtil.getSize(context, 90);
			holder.iv_title.setLayoutParams(pm);
			holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
			holder.tv_name.setTextSize(DisplayUtil.textGetSizeSp(context, 51));
			holder.tv_descrp = (TextView) convertView.findViewById(R.id.tv_descrp);
			holder.tv_descrp.setTextSize(DisplayUtil.textGetSizeSp(context, 24));
			holder.tv_descrp.setPadding(0, 0, DisplayUtil.getSize(context, 24), 0);
			holder.tv_descrp.setTypeface(Typeface.MONOSPACE,Typeface.ITALIC);
			holder.rl_travel = (RelativeLayout) convertView.findViewById(R.id.rl_travel);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		final ViewHolder holderimage = holder;
		holderimage.iv_travelImg.setScaleType(ScaleType.FIT_XY);
		imageLoader.displayImage(listLine.get(position).img_path, holder.iv_travelImg,imageLoaderOptions, new ImageLoadingListener() {
			
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
				holderimage.iv_travelImg.setScaleType(ScaleType.CENTER_CROP);
			}
			
			@Override
			public void onLoadingCancelled(String arg0, View arg1) {
				// TODO Auto-generated method stub
				
			}
		});
		
		String color = listLine.get(position).color;
		if("1".equals(color)){
			holder.tv_tag.setBackgroundResource(R.drawable.tourism_pic_title_a);
			holder.iv_title.setImageResource(R.drawable.tourism_title_a);
		}else if("2".equals(color)){
			holder.tv_tag.setBackgroundResource(R.drawable.tourism_pic_title_b);
			holder.iv_title.setImageResource(R.drawable.tourism_title_b);
		}else if("3".equals(color)){
			holder.tv_tag.setBackgroundResource(R.drawable.tourism_pic_title_c);
			holder.iv_title.setImageResource(R.drawable.tourism_title_c);
		}else if("4".equals(color)){
			holder.tv_tag.setBackgroundResource(R.drawable.tourism_pic_title_d);
			holder.iv_title.setImageResource(R.drawable.tourism_title_d);
		}else if("5".equals(color)){
			holder.tv_tag.setBackgroundResource(R.drawable.tourism_pic_title_e);
			holder.iv_title.setImageResource(R.drawable.tourism_title_e);
		}
		
		if(!TextUtils.isEmpty(listLine.get(position).tag)){
			holder.tv_tag.setText(listLine.get(position).tag + " ");		
		}else{
			holder.tv_tag.setVisibility(View.GONE);/*setBackground(null)*/;
		}
		
		holder.tv_name.setText(listLine.get(position).name);
		holder.tv_descrp.setText(listLine.get(position).introduction + " ");
		
		
		return convertView;
	}
	
	public void setData(LineInfo[] linelist){
			this.listLine.clear();
			for(int i=0; i< linelist.length; i++){
				listLine.add(linelist[i]);
			}
			notifyDataSetChanged();
	}
	
	public void setData(List<LineInfo> linelist){
		this.listLine.clear();
		this.listLine.addAll(linelist);
		notifyDataSetChanged();
	}
		
	public class ViewHolder {
		ImageView iv_travelImg;
		TextView tv_tag;
		RelativeLayout rl_travel;
		ImageView iv_title;
		TextView tv_name;
		TextView tv_descrp;
	}

	
	
}