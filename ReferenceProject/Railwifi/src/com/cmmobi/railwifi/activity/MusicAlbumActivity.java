package com.cmmobi.railwifi.activity;

import java.util.ArrayList;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cmmobi.railwifi.R;
import com.cmmobi.railwifi.adapter.MusicAlbumListAdapter;
import com.cmmobi.railwifi.music.MusicService;
import com.cmmobi.railwifi.network.GsonResponseObject;
import com.cmmobi.railwifi.network.GsonResponseObject.MusicAlumb;
import com.cmmobi.railwifi.network.GsonResponseObject.MusicElem;
import com.cmmobi.railwifi.utils.DisplayUtil;
import com.cmmobi.railwifi.view.MusicControllerView;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.nostra13.universalimageloader.api.MyImageLoader;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

/**
 * @author youtian
 * @email youtian@cmmobi.com
 * @date  2015-01-19
 */
public class MusicAlbumActivity extends TitleRootActivity implements OnRefreshListener2<ListView>{

	private String TAG = "MusicMainPageAcitivity";
	
	private TextView tvSongName;
	private TextView tvSongNum;
	private ImageView ivAlumImage;
	private PullToRefreshListView xlvMusicList;
	private ListView lvMusicList;
	private MusicAlbumListAdapter listAdapter;
	
	private MusicControllerView musicControllerView;
	
	private MusicAlumb mAlbum;
	private ArrayList<MusicElem> musicElems = new ArrayList<GsonResponseObject.MusicElem>(); //专辑歌曲列表
	
	private MusicService musicService;
	
	private PlayStateReceiver playStateReceiver;
	
	
	public class PlayStateReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			if(musicControllerView.BROADCAST_MUSIC_PLAY.equals(intent.getAction())){
				Log.e(TAG, "music playing ");
				tvSongName.setText(musicService.curSongName());
				musicControllerView.setButtonPause();
				listAdapter.notifyDataSetChanged();
			}else if(musicControllerView.BROADCAST_MUSIC_PAUSE.equals(intent.getAction())){
				Log.e(TAG, "music stoped ");
				tvSongName.setText(musicService.curSongName());
				musicControllerView.setButtonPlay();
			}
		}
		
	}
	
	@Override
	public int subContentViewId() {
		return R.layout.activity_music_album;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		hideRightButton();

		if(getIntent().getExtras()!=null){
			String album = getIntent().getExtras().getString(MusicMainPageActivity.ALBUM_LIST);
			if(!TextUtils.isEmpty(album)){
				mAlbum = new Gson().fromJson(album, GsonResponseObject.MusicAlumb.class);
			}else{
				this.finish();
			}
		}
		setTitleText(mAlbum.title);
		musicService = MusicService.getInstance();
		
		tvSongName = (TextView) findViewById(R.id.tv_song_name);
		tvSongName.setTextSize(DisplayUtil.textGetSizeSp(this, 30));
		RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) tvSongName.getLayoutParams();
		lp.leftMargin = DisplayUtil.getSize(this, 12);
		lp.width = DisplayUtil.getSize(this, 330);
		lp.topMargin = DisplayUtil.getSize(this, 288);
		tvSongName.setLayoutParams(lp);
		tvSongName.setText(musicService.curSongName());
				
		tvSongNum = (TextView) findViewById(R.id.tv_song_num);
		tvSongNum.setTextSize(DisplayUtil.textGetSizeSp(this, 30));
		tvSongNum.setText(Html.fromHtml("共"+ "<font color=\"#f65c00\">"+ mAlbum.alumblist.length + "</font>" + "首"));
		lp = (RelativeLayout.LayoutParams) tvSongNum.getLayoutParams();
		lp.height = DisplayUtil.getSize(this, 66);
		tvSongNum.setLayoutParams(lp);
		tvSongNum.setPadding(DisplayUtil.getSize(this, 27), 0, 0, 0);
		
		ivAlumImage = (ImageView) findViewById(R.id.iv_album_img);
		MyImageLoader imageLoader = null;
		DisplayImageOptions imageLoaderOptions = null;
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
		
		imageLoader.displayImage(mAlbum.img_path, ivAlumImage, imageLoaderOptions);
		lp = (RelativeLayout.LayoutParams) ivAlumImage.getLayoutParams();
		lp.height = DisplayUtil.getSize(this, 354); 
		ivAlumImage.setLayoutParams(lp);
		
		
		musicControllerView = (MusicControllerView)findViewById(R.id.mcv_music_album);
		int width = musicControllerView.setDrawableAndGetWidth(false);
		lp = (RelativeLayout.LayoutParams)musicControllerView.getLayoutParams();
		lp.width = width;
		lp.topMargin = DisplayUtil.getSize(this, 243);
		lp.rightMargin = DisplayUtil.getSize(this, 27);
		musicControllerView.setLayoutParams(lp);
		musicControllerView.setIsList(true);
		
		if(MusicService.getInstance().isPlaying()){
			musicControllerView.setButtonPause();
		}else{
			musicControllerView.setButtonPlay();
		}
	
		xlvMusicList = (PullToRefreshListView) findViewById(R.id.xlv_music_list);
		xlvMusicList.setShowIndicator(false);
		lvMusicList = xlvMusicList.getRefreshableView();
		xlvMusicList.setPullToRefreshEnabled(false);
		xlvMusicList.setOnRefreshListener(this);

		RelativeLayout.LayoutParams pm = (RelativeLayout.LayoutParams) xlvMusicList.getLayoutParams();
		pm.bottomMargin = DisplayUtil.getSize(this, 12);
		xlvMusicList.setLayoutParams(pm);
		listAdapter = new MusicAlbumListAdapter(this);
		listAdapter.setData(mAlbum.alumblist);
		
		xlvMusicList.setAdapter(listAdapter);
		lvMusicList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				tvSongName.setText(mAlbum.alumblist[arg2-1].name);
				musicService.stopPlay();
				musicService.setCurMusicId(mAlbum.alumblist[arg2-1].media_id);
				musicService.playSong();
				listAdapter.notifyDataSetChanged();
			}
		});
		
		
		playStateReceiver = new PlayStateReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(MusicControllerView.BROADCAST_MUSIC_PLAY);    //只有持有相同的action的接受者才能接收此广播
        filter.addAction(MusicControllerView.BROADCAST_MUSIC_PAUSE);
        registerReceiver(playStateReceiver, filter);
	}
	
		
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();	
	}
	
	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	
	
	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		// TODO Auto-generated method stub		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch(v.getId()){
		case R.id.btn_title_right:
			//标签页

			break;
			default:{
				
			}	
		}
	}	
	
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		unregisterReceiver(playStateReceiver);
		super.onDestroy();
	}

	@Override
	public boolean handleMessage(Message msg) {
		// TODO Auto-generated method stub
				
		return false;
	}

	
	
}
