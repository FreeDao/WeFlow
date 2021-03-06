package com.cmmobi.railwifi.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.cmmobi.railwifi.Config;
import com.cmmobi.railwifi.R;
import com.cmmobi.railwifi.activity.CmmobiVideoPlayer;
import com.cmmobi.railwifi.activity.VideoPlayerActivity;
import com.cmmobi.railwifi.network.GsonResponseObject;
import com.cmmobi.railwifi.utils.CmmobiClickAgentWrapper;
import com.cmmobi.railwifi.utils.DisplayUtil;
import com.cmmobi.railwifi.utils.ViewUtils;
import com.nostra13.universalimageloader.api.AnimateFirstDisplayListener;
import com.nostra13.universalimageloader.api.MyImageLoader;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class CityIntroAdapter extends BaseAdapter {

	private Context context;
	private LayoutInflater inflater;
	private List<GsonResponseObject.cityScopeElem> list;
	private int size = 0;
	
	//使用开源的webimageloader
	public static DisplayImageOptions options;
	protected MyImageLoader imageLoader;
	private ImageLoadingListener animateFirstListener;
	
	public CityIntroAdapter(final Context context) {
		this.context = context;
		DisplayMetrics dm = context.getApplicationContext().getResources().getDisplayMetrics();
		this.size = dm.widthPixels/5;
		inflater = LayoutInflater.from(context);
		
		animateFirstListener = new AnimateFirstDisplayListener();
		imageLoader = MyImageLoader.getInstance();
		
		options = new DisplayImageOptions.Builder()
		.cacheOnDisk(true)
		.showImageOnLoading(R.drawable.btn_list_yyyl_normal)
		.showImageForEmptyUri(R.drawable.btn_list_yyyl_normal)
		.showImageOnFail(R.drawable.btn_list_yyyl_normal)
		.cacheInMemory(true)
		.displayer(new SimpleBitmapDisplayer())
//		.displayer(new CircularBitmapDisplayer()) //圆形图片
		.build();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public GsonResponseObject.cityScopeElem getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
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
					R.layout.list_city_intro_item, null);
			holder = new ViewHolder();
			
			holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
			holder.im_city = (ImageView) convertView.findViewById(R.id.im_city);
			holder.btnPlay = (ImageButton) convertView.findViewById(R.id.btn_play);
			ViewUtils.setSize(convertView.findViewById(R.id.rl_city_img), 318, 222);
			holder.tv_title.setTextSize(DisplayUtil.textGetSizeSp(context, 42));
			ViewUtils.setMarginTop(holder.tv_title, 12);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.btnPlay.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CmmobiClickAgentWrapper.onEvent(context, "city_mien", list.get(position).object_id);
				if(Config.IS_USE_COMMOBI_VIDEOVIEW){
					Intent movieIntent = new Intent(context, CmmobiVideoPlayer.class);
					movieIntent.putExtra(VideoPlayerActivity.KEY_MEDIA_ID, list.get(position).object_id);
					movieIntent.putExtra(VideoPlayerActivity.KEY_NAME, list.get(position).name);
					movieIntent.putExtra(VideoPlayerActivity.KEY_PATH, list.get(position).src_path);
					movieIntent.putExtra(VideoPlayerActivity.KEY_MOVIE_TYPE, "2");
					context.startActivity(movieIntent);
				}else{
					Intent movieIntent = new Intent(context, VideoPlayerActivity.class);
					movieIntent.putExtra(VideoPlayerActivity.KEY_MEDIA_ID, list.get(position).object_id);
					movieIntent.putExtra(VideoPlayerActivity.KEY_NAME, list.get(position).name);
					movieIntent.putExtra(VideoPlayerActivity.KEY_PATH, list.get(position).src_path);
					movieIntent.putExtra(VideoPlayerActivity.KEY_MOVIE_TYPE, "2");
					context.startActivity(movieIntent);
				}
			}
		});
		
		holder.tv_title.setText(list.get(position).name);
		imageLoader.displayImage(list.get(position).img_path, holder.im_city, options, animateFirstListener);
		return convertView;
	}
	
	public void setData(List<GsonResponseObject.cityScopeElem> data){
		this.list = data;
	}
	
	public boolean isEmpty()
	{
		return list.isEmpty();
	}
	
	
	
	public class ViewHolder {
		TextView tv_title;
		ImageView im_city;
		ImageButton btnPlay;
	}

	
}