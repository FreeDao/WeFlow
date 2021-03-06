package com.cmmobi.railwifi.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.ViewGroup;

import com.cmmobi.railwifi.fragment.RailServiceBannerFragment;
import com.cmmobi.railwifi.network.GsonResponseObject.serviceBannerphotoElem;
import com.imbryk.viewPager.LoopViewPager;

public class RailServiceBannerAdapter extends FragmentPagerAdapter {

	private int mDrawable;
	public RailServiceBannerAdapter(FragmentManager fm,int drawable,List<serviceBannerphotoElem> list) {
		super(fm);
		// TODO Auto-generated constructor stub
		mDrawable = drawable;
		playBillImageSites = list;
	}

	List<serviceBannerphotoElem> playBillImageSites = new ArrayList<serviceBannerphotoElem>();
	Activity act;
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return playBillImageSites.size();
	}
	
	@Override
	public int getItemPosition(Object object) {
		// TODO Auto-generated method stub
		return PagerAdapter.POSITION_NONE;
	}
	
	@Override
	public Fragment getItem(int position) {
		// TODO Auto-generated method stub
		Log.d("RailServiceFragment","getItem in");
		position = LoopViewPager.toRealPosition(position, getCount());
//        return TestFragment.newInstance(CONTENT[position % CONTENT.length]);
		return RailServiceBannerFragment.newInstance(playBillImageSites.get(position),mDrawable);
	}
	
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// TODO Auto-generated method stub
		Log.d("RailServiceFragment","instantiateItem in");
		return super.instantiateItem(container, position);
	}
	
}
