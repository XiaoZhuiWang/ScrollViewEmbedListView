/**
 * @FileName: ExternalInterceptActivity.java 
 * @Package: com.example.scrollviewandlistview
 * @Copyright Information: 青年微视科技（深圳）有限公司版权所有
 * @Description: TODO
 * @author: Lin Wright
 * @date: 2015年11月12日
 * @version: V1.0
 *
 * @modify Description: 
 * @modify author: 
 * @modify date: 
 */
/**
 *@Description:
 *
 */
package com.linwright.scrollviewembedlistview;

import java.util.ArrayList;


import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.linwright.scrollviewembedlistview.view.EmbedListViewScrollView;

public class ExternalInterceptActivity extends Activity {

	private static final String TAG = "MainActivity";

	private EmbedListViewScrollView sv_my;
	private ListView ll_my;
	private ArrayList<String> list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_externalintercept);
		
		initData();
		initView();
	}

	private void initView() {
		sv_my = (EmbedListViewScrollView) this.findViewById(R.id.sv_my);
		ll_my = (ListView) this.findViewById(R.id.ll_my);
		
		sv_my.setInnerListView(ll_my);
		ll_my.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_expandable_list_item_1, list));
		sv_my.smoothScrollTo(0, 0);//将ScrollView滑动到顶部
	}

	private void initData() {
		list = new ArrayList<String>();
		for (int i = 0; i < 40; i++) {
			list.add("条目：" + i);
		}
	}
}
