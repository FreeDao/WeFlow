package com.cmmobi.railwifi.download;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.cmmobi.railwifi.MainApplication;
import com.cmmobi.railwifi.activity.MovieDetailActivity;
import com.cmmobi.railwifi.dao.DaoMaster;
import com.cmmobi.railwifi.dao.DaoMaster.DevOpenHelper;
import com.cmmobi.railwifi.dao.DaoSession;
import com.cmmobi.railwifi.dao.DownloadHistory;
import com.cmmobi.railwifi.dao.DownloadHistoryDao;
import com.cmmobi.railwifi.dao.DownloadHistoryDao.Properties;
import com.cmmobi.railwifi.utils.ConStant;

import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;
import de.greenrobot.event.EventBus;

public class DownloadManager {
	public static Object globeLock = new Object();
	public static final long UPDATE_INTERVAL_MS = 1000;
	private static final String TAG = "DownloadManager";
	
	private static DownloadManager ins = null;
	private Context context;
	private DaoMaster daoMaster;
	private DaoSession daoSession;
	private DownloadHistoryDao downloadHistoryDao;
	private SQLiteDatabase db;
	
	//WAIT=1,PERPARE=2,RUN=3,PAUSE=4,DONE=5,FAIL=6
	List<DownloadItem> runningList; //WAIT=1,PERPARE=2,RUN=3,PAUSE=4  运行列表
	List<DownloadItem> doneList; //DONE=5,FAIL=6                     完成列表
	
	private Map<DownloadType, DownloadItem> runningSets;  //正在下载的各种类别集合（PERPARE=2,RUN=3,PAUSE=4）
	
//	int lastSeqNo; //已有的最大seq序列
	
	private ExecutorService executor;
	
	private long lastReportTs;
	
	private DownloadManager(){
		//init data
		runningSets = new HashMap<DownloadType, DownloadItem>();
//		lastSeqNo = 0;
		lastReportTs = System.currentTimeMillis();
		runningList = new ArrayList<DownloadItem>();
		doneList = new ArrayList<DownloadItem>();
		executor = Executors.newFixedThreadPool(8);
		
		//init from sql
		context = MainApplication.getAppInstance();
		DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "railwifidb", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        downloadHistoryDao = daoSession.getDownloadHistoryDao();
        List<DownloadHistory> allList = downloadHistoryDao.loadAll();
        
        if(allList!=null && allList.size()>0){
        	for(DownloadHistory item : allList){
    			DownloadItem ditem = new DownloadItem(item);
//    			if(lastSeqNo<ditem.seqNo){
//    				lastSeqNo = ditem.seqNo;
//    			}
    			
        		if(item.getDownloadStatus()<=4){
        			runningList.add(ditem);
        		}else if(item.getDownloadStatus()==5){
        			if(ditem.path!=null){
        				File f = new File(ditem.path);
        				if(f.exists() && f.isFile()){
        					int len = (int) f.length();
        					if(len==ditem.wholeSize){
        	        			doneList.add(ditem);
        	        			continue;
        					}
        				}
        			}
        			downloadHistoryDao.deleteByKey(item.getUrl());
        		}else{
        			//fail 被忽略掉
//        			downloadHistoryDao.deleteByKey(item.getUrl());
        		}
        		
        	}
        	
        	//恢复下载
        	if(runningList.size()>0){
    			for(DownloadItem _item : runningList){
    				if(!runningSets.containsKey(_item.downloadType)){
    					runningSets.put(_item.downloadType, _item);
    					executor.submit(_item);
    				}
    			}
        	}
        }
		
	}
	
	public synchronized static DownloadManager getInstance(){
		if(ins==null){
			ins = new DownloadManager();
		}
		
		return ins;
	}
	
	public synchronized List<DownloadItem> getRunningList(){
		return runningList;
	}
	
	public synchronized List<DownloadItem> getDoneList(){
		return doneList;
	}
	
	public synchronized void notifyLockItems(){
		try{
			synchronized (globeLock) {
				globeLock.notifyAll();
			}
		}catch(Exception e){
			//e.printStackTrace();
		}

	}
	
	public synchronized String getDownloadTask(String url, DownloadType type){
		for(DownloadItem item : doneList){
			if(item.url.equals(url) && item.downloadType == type){
				return item.path;
			}
		}
		
		return null;
	}
	
	/**
	 * 增加一个任务，如果runningList 或 doneList 里有，则不做任何操作
	 * 
	 * 最后，直接返回对应对象
	 * */
	public synchronized DownloadItem addDownloadTask(String url, String media_id, String title, String picUrl, String descrp, DownloadType type, String source, String data) {
		Log.v(TAG, "addDownloadTask - DownloadType:" + type + ", title:" + title + ", media_id:" + media_id + ", url:" + url + ", source:" + source + ", data:" + data);
		for(DownloadItem item : runningList){
			if(item.url.equals(url)){
				Toast.makeText(context, "已在下载队列中", Toast.LENGTH_LONG).show();
				return item;
			}
		}
		
		for(DownloadItem item : doneList){
			if(item.url.equals(url)){
				//check file
				File f = new File(item.path);
				if(f.exists() && f.isFile()){
					int len = (int) f.length();
					if(len==item.wholeSize){
						//install app
						if(item.downloadType == DownloadType.APP){
							if(data!=null && data.length()>0){
								try {
									PackageManager pm = context.getPackageManager();
									pm.getPackageInfo(data.trim(), PackageManager.GET_ACTIVITIES);
									
								}catch(NameNotFoundException e){
									installFromPath(context,item.path);
								}
							}else{
								installFromPath(context,item.path);
							}

						}else if(item.downloadType == DownloadType.MOVIE){
							if(ConStant.SOHU_SOURCE_NAME.equals(item.source)){
								MovieDetailActivity.startSohuClient(context, item);
							}
						}
						return item;
					}
				}
				
				//重新下载
				doneList.remove(item);
				downloadHistoryDao.deleteByKey(item.url);
				
			}
		}
		
		//如果点击的是Fail条目，先清除
		List<DownloadHistory> list = queryByUrl(url);
		if(list!=null && list.size()>0){
			DownloadHistory one = list.get(0);
			if(one!=null && one.getDownloadStatus()==DownloadStatus.FAIL.getIndex()){
				downloadHistoryDao.deleteByKey(url);
			}
		}else{
			Toast.makeText(context, "已加入下载队列", Toast.LENGTH_LONG).show();
		}

		DownloadItem item = new DownloadItem(media_id, title, url, picUrl, descrp, type, source, data);//path, downloadSize, wholeSize, DownloadStatus
		addDownloadTask(item);
		

		
		return item;
	}
	
	public static void installFromPath(Context context,String path) {
		// TODO Auto-generated method stub
		if(path!=null){
			String fileName = path; 
			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.setDataAndType(Uri.fromFile(new File(fileName)), "application/vnd.android.package-archive"); 
			context.startActivity(intent);
		}

	}

	synchronized public void delDownloadingTask(String url) {
		// TODO Auto-generated method stub
		for(DownloadItem item : runningList){
			if(item.url.equals(url)){
				delDownloadingTask(item);
				break;
			}
		}
	}
	
//	public synchronized void delItemByKey(String url, final String path){
//		if(url!=null){
//			downloadHistoryDao.deleteByKey(url);
//		}
//
//		new Thread(){
//			@Override
//			public void run(){
//				if(path!=null){
//					File f = new File(path);
//					if(f.isFile() && f.exists()){
//						f.delete();
//					}
//				}
//			}
//		}.start();
//		
//	}
	
	/**
	 * <br> 删除正在下载的条目
	 * <br> 调用完毕后上层可以认为实际已经被删除了
	 * */
	public synchronized void delDownloadingTask(DownloadItem item){
		if(item==null){
			return;
		}
		
		item.cancel = true;
		runningList.remove(item);
		if(runningSets.containsValue(item)){
			runningSets.remove(item.downloadType);
		}
		downloadHistoryDao.deleteByKey(item.url);
		notifyLockItems();
	}
	
	
	
	public synchronized void delDownloadedTask(final DownloadItem item){
		if(item==null){
			return;
		}
		
		doneList.remove(item);
		downloadHistoryDao.deleteByKey(item.url);
		
		new Thread(){
			@Override
			public void run(){
				if(item.path!=null){
					File f = new File(item.path);
					if(f.isFile() && f.exists()){
						f.delete();
					}
				}
			}
		}.start();

	}
	
	public synchronized void cleanupDownloadedTask(){
		final List<DownloadItem> copy = new ArrayList<DownloadItem>();
		copy.addAll(doneList);
		doneList.clear();
		
		
		new Thread(){
			@Override
			public void run(){
				if(copy.size()>0){
					for(DownloadItem item : copy){
						if(item!=null && item.path!=null){
							File f = new File(item.path);
							if(f.isFile() && f.exists()){
								f.delete();
							}
						}

						if(item!=null && item.url!=null){
							downloadHistoryDao.deleteByKey(item.url);
						}
						
					}
					
				}
				
				List<DownloadHistory> donelist = queryByStatus(DownloadStatus.DONE);
				if(donelist!=null && donelist.size()>0){
					for(DownloadHistory item : donelist){
						if(item!=null && item.getPath()!=null){
							File f = new File(item.getPath());
							if(f.isFile() && f.exists()){
								f.delete();
							}
						}

						if(item!=null && item.getUrl()!=null){
							downloadHistoryDao.deleteByKey(item.getUrl());
						}

					}
				}
				
				List<DownloadHistory> faillist = queryByStatus(DownloadStatus.FAIL);
				if(faillist!=null && faillist.size()>0){
					for(DownloadHistory item : faillist){
						if(item!=null && item.getPath()!=null){
							File f = new File(item.getPath());
							if(f.isFile() && f.exists()){
								f.delete();
							}
						}

						if(item!=null && item.getUrl()!=null){
							downloadHistoryDao.deleteByKey(item.getUrl());
						}

					}
				}
				
				EventBus.getDefault().post(DownloadEvent.DONE_CLEAN_ALL);
			}
		}.start();
	}

	private synchronized void addDownloadTask(DownloadItem item){
		//1 去重
		if(runningList.size()>0){
			for(DownloadItem _item : runningList){
				if(_item.url.equals(item.url)){
					return;
				}
			}
		}
		
		
//		item.seqNo = ++lastSeqNo;
		item.downloadStatus = DownloadStatus.WAIT;
		item.wholeSize = -1;
		item.downloadSize = 0;
		
		runningList.add(item);
		
		//2. 同步到数据库
		DownloadHistory entity = new DownloadHistory();
		entity.setUrl(item.url);
		entity.setPath(item.path);
		entity.setDownloadSize(item.downloadSize);
		entity.setWholeSize(item.wholeSize);
		entity.setDownloadStatus(DownloadStatus.WAIT.getIndex());
		entity.setDownloadType(item.downloadType.getIndex());
		entity.setPicUrl(item.picUrl);
		entity.setTitle(item.title);
		entity.setDetail(item.detail);
		entity.setMediaId(item.mediaId);
		entity.setData(item.data);
		entity.setSource(item.source);
		downloadHistoryDao.insertOrReplace(entity);
		
		//3. 启动第一个相同类型的
		if(!runningSets.containsKey(item.downloadType)){
			for(DownloadItem _item : runningList){
				if(_item.downloadType == item.downloadType){
					executor.submit(_item);
					runningSets.put(item.downloadType, item);
					break;
				}
			}
		}
		
	}
	
	public synchronized void notifyProgressChanged(DownloadItem item){
//		long now = System.currentTimeMillis();
//		if(now-lastReportTs>UPDATE_INTERVAL_MS){
//			//需要通过eventBus告诉外部
//			
//			lastReportTs = now;
//		}
		
		DownloadEvent e = DownloadEvent.PROGRESS_CHANGED;
		EventBus.getDefault().post(e);
		
		notifyLockItems();
	}
	
	//需要通过eventBus告诉外部
	public synchronized void notifyStatusChanged(DownloadItem item, DownloadStatus status){
		item.downloadStatus = status;
		
		//通知外部
		DownloadEvent e = DownloadEvent.STATUS_CHANGED;
		if(status==DownloadStatus.FAIL || status==DownloadStatus.DONE){
			e.setType(DownloadEvent.RUNNING_LIST_DEL);
			runningList.remove(item);
			runningSets.remove(item.downloadType);
			
			if(status==DownloadStatus.DONE){
				doneList.add(item);
				e.setType(DownloadEvent.RUNNING_LIST_DEL|DownloadEvent.DONE_LIST_ADD);
				
				SharedPreferences mySharedPreferences= context.getSharedPreferences("has_new_download", 
						Activity.MODE_PRIVATE); 
				SharedPreferences.Editor editor = mySharedPreferences.edit();
				editor.putBoolean("has_new", true);
				editor.apply();
				
				//install app
				if(item.downloadType == DownloadType.APP){
					installFromPath(context,item.path);
				}
			}
			
			//下一个相同类型的下载任务
			if(runningList.size()>0){
				for(DownloadItem _item : runningList){
					if(_item.downloadType == item.downloadType){
						executor.submit(_item);
						runningSets.put(item.downloadType, item);
						break;
					}
				}
			}
		}

		EventBus.getDefault().post(e);
		
		//同步数据库
		DownloadHistory entity = new DownloadHistory();
//		entity.setSeqNo(item.seqNo);
		entity.setUrl(item.url);
		entity.setPath(item.path);
		entity.setDownloadSize(item.downloadSize);
		entity.setWholeSize(item.wholeSize);
		entity.setDownloadStatus(status.getIndex());
		entity.setDownloadType(item.downloadType.getIndex());
		entity.setPicUrl(item.picUrl);
		entity.setTitle(item.title);
		entity.setDetail(item.detail);
		entity.setMediaId(item.mediaId);
		entity.setData(item.data);
		entity.setSource(item.source);
		downloadHistoryDao.insertOrReplace(entity);

		notifyLockItems();
		
		

	}
	
	public List<DownloadHistory> queryByUrl(String url) {
		// TODO Auto-generated method stub
		if(url==null){
			return new ArrayList<DownloadHistory>();
		}
		
		QueryBuilder<DownloadHistory> qb = downloadHistoryDao.queryBuilder();
		qb.where(Properties.Url.isNotNull(), Properties.Url.eq(url));//, Properties.DownloadType.eq(type.getIndex())).build();
		List<DownloadHistory> ret =  qb.list();
//		List<DownloadHistory> ret = downloadHistoryDao.queryRaw("WHERE media_id=?", new String[]{media_id});
		return ret;
	}

	public List<DownloadHistory> queryByMediaId(String media_id) {
		// TODO Auto-generated method stub
		if(media_id==null){
			return new ArrayList<DownloadHistory>();
		}
		QueryBuilder<DownloadHistory> qb = downloadHistoryDao.queryBuilder();
		qb.where(Properties.MediaId.isNotNull(), Properties.MediaId.eq(media_id));//, Properties.DownloadType.eq(type.getIndex())).build();
		List<DownloadHistory> ret =  qb.list();
//		List<DownloadHistory> ret = downloadHistoryDao.queryRaw("WHERE media_id=?", new String[]{media_id});
		return ret;
	}

	public List<DownloadHistory> queryByStatus(DownloadStatus status) {
		// TODO Auto-generated method stub
		QueryBuilder<DownloadHistory> qb = downloadHistoryDao.queryBuilder();
		qb.where(Properties.DownloadStatus.isNotNull(), Properties.DownloadStatus.eq(status.getIndex()));//, Properties.DownloadType.eq(type.getIndex())).build();
		List<DownloadHistory> ret =  qb.list();
		return ret;
	}

}
