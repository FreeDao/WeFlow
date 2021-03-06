package com.cmmobi.railwifi.activity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.cmmobi.draganddrop.CoolDragAndDropGridView;
import com.cmmobi.draganddrop.CoolDragAndDropGridView.DragAndDropListener;
import com.cmmobi.draganddrop.SimpleScrollingStrategy;
import com.cmmobi.draganddrop.SpanVariableGridView;
import com.cmmobi.railwifi.R;
import com.cmmobi.railwifi.event.NetworkEvent;
import com.cmmobi.railwifi.utils.DisplayUtil;
import com.cmmobi.railwifi.utils.ModuleUtils;
import com.cmmobi.railwifi.utils.ModuleUtils.TagWrapper;
import com.cmmobi.railwifi.utils.StringUtils;
import com.cmmobi.railwifi.utils.ViewUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ModuleDragActivity extends TitleRootActivity implements DragAndDropListener, OnItemLongClickListener {

	TagAdapter mItemAdapter;
	CoolDragAndDropGridView mCoolDragAndDropGridView;
	List<TagAddWrapper> mItems = null;
	
	@Override
	public boolean handleMessage(Message msg) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int subContentViewId() {
		// TODO Auto-generated method stub
		return R.layout.activity_module_drag;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		initViews();
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
	public void onEventMainThread(NetworkEvent event) {
		SharedPreferences mySharedPreferences= getSharedPreferences("selected_modules", 
				Activity.MODE_PRIVATE); 
		String selectedModulesStr = mySharedPreferences.getString("modules", null);
		
		ArrayList<Integer> idLists = null;
		
		if (selectedModulesStr != null) {
			idLists = new Gson().fromJson(selectedModulesStr, new TypeToken<ArrayList<Integer>>(){}.getType());
		}
		
		switch(event) {
		case NET_RAILWIFI:
			Log.d("=AAA=","ModuleDragActivity NET_RAILWIFI ");
			for (TagWrapper wrapper:ModuleUtils.tagLists) {
				TagAddWrapper addWrapper = new TagAddWrapper();
				addWrapper.wrapper = wrapper;
				if (wrapper.isRailService && !mItems.contains(addWrapper)) {
					if (idLists != null && idLists.contains(wrapper.tagId)) {
						addWrapper.isAdded = true;
					} else {
						addWrapper.isAdded = false;
					}
					
					swapList(addWrapper);
				}
			}
			mItemAdapter.notifyDataSetChanged();
			
			break;
		case NET_OTHERS:
			Log.d("=AAA=","ModuleDragActivity NET_OTHERS ");
			for (TagWrapper wrapper:ModuleUtils.tagLists) {
				TagAddWrapper addWrapper = new TagAddWrapper();
				addWrapper.wrapper = wrapper;
				if (wrapper.isRailService && mItems.contains(addWrapper)) {
					mItems.remove(addWrapper);
				}
			}
			mItemAdapter.notifyDataSetChanged();
			break;
		}
	}
	
	private void initViews() {
		// TODO Auto-generated method stub
		setRightButtonText("完成>>");
		ScrollView scrollView = (ScrollView) findViewById(R.id.scrollView);
		mCoolDragAndDropGridView = (CoolDragAndDropGridView) findViewById(R.id.coolDragAndDropGridView);
		
		ArrayList<Integer> selectedList = getIntent().getIntegerArrayListExtra("modules");
		mItems = new LinkedList<TagAddWrapper>();
		if (selectedList != null) {
			for (Integer id:selectedList) {
				TagAddWrapper addWrapper = new TagAddWrapper();
				addWrapper.isAdded = true;
				addWrapper.wrapper = ModuleUtils.findModules(id);
				mItems.add(addWrapper);
			}
		}
		
		for (TagWrapper wrapper:ModuleUtils.tagLists) {
			if (!selectedList.contains(wrapper.tagId)) {
				TagAddWrapper addWrapper = new TagAddWrapper();
				addWrapper.wrapper = wrapper;
				addWrapper.isAdded = false;
				mItems.add(addWrapper);
			}
		}
		
		if (StringUtils.isEmpty(MainActivity.train_num)) {
			Iterator iter = mItems.iterator();
			while (iter.hasNext()) {
				TagAddWrapper wrapper = (TagAddWrapper) iter.next();
				if (wrapper.wrapper.isRailService) {
					iter.remove();
				}
			}
		}
		
		mItemAdapter = new TagAdapter(this, mItems);
		mCoolDragAndDropGridView.setAdapter(mItemAdapter);
		mCoolDragAndDropGridView.setScrollingStrategy(new SimpleScrollingStrategy(scrollView));
		mCoolDragAndDropGridView.setDragAndDropListener(this);
		mCoolDragAndDropGridView.setOnItemLongClickListener(this);
	}
	
	class TagAddWrapper {
		public TagWrapper wrapper;
		public boolean isAdded;
		
		@Override
		public boolean equals(Object o) {
			// TODO Auto-generated method stub
			return wrapper.tagId == ((TagAddWrapper) o).wrapper.tagId;
		}
	}
	
	class TagHolder {
		public ImageView ivTagImg;
		public TextView tvTagText;
		public RelativeLayout rlRoot;
		public ImageView ivSelected;
	}
	
	class TagAdapter extends BaseAdapter implements SpanVariableGridView.CalculateChildrenPosition{
		
		
		
		private LayoutInflater inflater;
		private Context context;
		private List<TagAddWrapper> tagLists = new LinkedList<TagAddWrapper>();
		
		public TagAdapter(Context context,List<TagAddWrapper> list) {
			this.context = context;
			inflater = LayoutInflater.from(context);
			
			if (list != null) {
				tagLists = list;
			}
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return tagLists.size();
		}

		@Override
		public TagWrapper getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			TagHolder holder = null;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.item_homepage_tag_drag,parent, false);
				holder = new TagHolder();
				holder.ivTagImg = (ImageView) convertView.findViewById(R.id.iv_tag_img);
				holder.tvTagText = (TextView) convertView.findViewById(R.id.tv_tag_text);
				holder.rlRoot = (RelativeLayout) convertView.findViewById(R.id.rl_tag_root);
				holder.ivSelected = (ImageView) convertView.findViewById(R.id.iv_tag_selected);
				convertView.setTag(holder);
				
				ViewUtils.setHeight(holder.ivTagImg,116);
				
				holder.tvTagText.setTextSize(DisplayUtil.textGetSizeSp(context, 30));
				ViewUtils.setMarginBottom(holder.tvTagText, 8);
				
				ViewGroup.LayoutParams params = convertView.getLayoutParams();
				if (params != null) {
					params.height = DisplayUtil.getSize(context, 162);
				}
				if (params != null) {
					SpanVariableGridView.LayoutParams lp = new SpanVariableGridView.LayoutParams(params);
					convertView.setLayoutParams(lp);
				}
			} else {
				holder = (TagHolder) convertView.getTag();
			}
			
			
			TagAddWrapper tagWrapper = tagLists.get(position);
			
			holder.rlRoot.setTag(tagWrapper);
			holder.rlRoot.setOnClickListener(ModuleDragActivity.this);
			holder.tvTagText.setText(tagWrapper.wrapper.tagDesc);
			holder.ivTagImg.setImageResource(tagWrapper.wrapper.drawableRes);
			
			if (tagWrapper.isAdded) {
				holder.rlRoot.setBackgroundResource(R.drawable.bg_module_add);
				holder.ivSelected.setVisibility(View.VISIBLE);
			} else {
				holder.rlRoot.setBackgroundResource(R.drawable.bg_item_tag);
				holder.ivSelected.setVisibility(View.GONE);
			}
			
			return convertView;
		}
		@Override
		public void onCalculatePosition(View view, int position, int row, int column) {
			// TODO Auto-generated method stub
			
		}
	}
	
	private ArrayList<Integer> getSelectedModule() {
		ArrayList<Integer> moduleList = new ArrayList<Integer>();
		for (TagAddWrapper addWrapper:mItems) {
			if (addWrapper.isAdded) {
				moduleList.add(addWrapper.wrapper.tagId);
			}
		}
		return moduleList;
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_title_right:
			Intent mIntent = new Intent();
			ArrayList<Integer> selectedModules = getSelectedModule();
			mIntent.putIntegerArrayListExtra("selectedmodule", selectedModules);
			SharedPreferences mySharedPreferences= getSharedPreferences("selected_modules", 
					Activity.MODE_PRIVATE); 
			SharedPreferences.Editor editor = mySharedPreferences.edit();
			String contentStr = new Gson().toJson(selectedModules);
			editor.putString("modules", contentStr);
			editor.commit();
			setResult(RESULT_OK,mIntent);
			finish();
			break;
		case R.id.rl_tag_root:
			RelativeLayout rlRoot = (RelativeLayout) v.findViewById(R.id.rl_tag_root);
			ImageView ivSelected = (ImageView) v.findViewById(R.id.iv_tag_selected);
			
			TagAddWrapper tagWrapper = (TagAddWrapper) v.getTag();
			tagWrapper.isAdded = !tagWrapper.isAdded;
			
//			swapList(tagWrapper);
			if (tagWrapper.isAdded) {
				rlRoot.setBackgroundResource(R.drawable.bg_module_add);
				ivSelected.setVisibility(View.VISIBLE);
			} else {
				rlRoot.setBackgroundResource(R.drawable.bg_item_tag);
				ivSelected.setVisibility(View.GONE);
			}
			break;
		}
		super.onClick(v);
	}
	
	private void swapList(TagAddWrapper tagWrapper) {
		mItems.remove(tagWrapper);
		int size = mItems.size();
		int i;
		for (i = 0;i < size;i++) {
			TagAddWrapper addWrapper = mItems.get(i);
			if (addWrapper.isAdded == false) {
				mItems.add(i,tagWrapper);
				return;
			}
		}
		mItems.add(i,tagWrapper);
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		mCoolDragAndDropGridView.startDragAndDrop();
		return false;
	}

	@Override
	public void onDragItem(int from) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDraggingItem(int from, int to) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDropItem(int from, int to) {
		// TODO Auto-generated method stub
		if (from != to) {
			mItems.add(to, mItems.remove(from));
			mItemAdapter.notifyDataSetChanged();
		}
	}

	@Override
	public boolean isDragAndDropEnabled(int position) {
		// TODO Auto-generated method stub
		return true;
	}

}
