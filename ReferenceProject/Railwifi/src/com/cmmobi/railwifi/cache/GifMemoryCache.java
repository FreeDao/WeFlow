package com.cmmobi.railwifi.cache;

import com.googlecode.concurrentlinkedhashmap.ConcurrentLinkedHashMap;
import com.googlecode.concurrentlinkedhashmap.EntryWeigher;
import com.googlecode.concurrentlinkedhashmap.EvictionListener;

import android.util.Log;

public class GifMemoryCache {
	
	protected static final String TAG = "MemoryCache";

	private ConcurrentLinkedHashMap<String, byte[]> cache;
	
	
	public GifMemoryCache(){
		cache = new ConcurrentLinkedHashMap.Builder<String, byte[]>()
				.maximumWeightedCapacity(10) // 10 gif pic
			    .weigher(memoryUsageWeigher)
			    .initialCapacity(5)
			    .listener(listener)
			    .build();
	}
	
	private EntryWeigher<String, byte[]> memoryUsageWeigher = new EntryWeigher<String, byte[]>() {
		  //final MemoryMeter meter = new MemoryMeter();

		  @Override 
		  public int weightOf(String key, byte[] value) {
		    return 1;
		  }
	};
	
	private EvictionListener<String, byte[]> listener = new EvictionListener<String, byte[]>() {
		  @Override 
		  public void onEviction(String key, byte[] value) {
			    //rm the key(file name)
			    Log.e(TAG, "Evicted(delete) key=" + key);
		  }
	};
	

	public byte[] get(String url) {
		// TODO Auto-generated method stub
		return cache.get(url);
	}
	
	public void put(String url, byte[] m) {
		// TODO Auto-generated method stub
		cache.put(url, m);
	}

	public void clear() {
		// TODO Auto-generated method stub
		cache.clear();
	}

}
