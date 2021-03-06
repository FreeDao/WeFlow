package com.cmmobi.railwifi.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.cmmobi.railwifi.fragment.PlayBillFragment;
import com.cmmobi.railwifi.fragment.RailServiceBannerFragment;
import com.cmmobi.railwifi.fragment.RailServiceFragment;
import com.cmmobi.railwifi.fragment.RailTravelBannerFragment;
import com.cmmobi.railwifi.network.GsonResponseObject.LineC;
import com.cmmobi.railwifi.network.GsonResponseObject.serviceBannerphotoElem;
import com.imbryk.viewPager.LoopViewPager;

public class RailTravelBannerAdapter extends FragmentPagerAdapter {

	public RailTravelBannerAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}

	List<LineC> travelLines = new ArrayList<LineC>();
	Activity act;
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return travelLines.size();
	}
	
	public void setList(List<LineC> list) {
		travelLines = list;
	}
	

	@Override
	public Fragment getItem(int position) {
		// TODO Auto-generated method stub
		position = LoopViewPager.toRealPosition(position, getCount());
		return RailTravelBannerFragment.newInstance(travelLines.get(position));
	}

}
