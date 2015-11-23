package com.linwright.scrollviewembedlistview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {
	
	private static final String TAG = "MainActivity";
	private Button btn_one, btn_two;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		initView();
	}

	private void initView() {
		btn_one = (Button) findViewById(R.id.btn_one);
		btn_two = (Button) findViewById(R.id.btn_two);
		
		btn_one.setOnClickListener(this);
		btn_two.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_one:
			Intent externalIntent = new Intent(this, ExternalInterceptActivity.class);
			startActivity(externalIntent);
			break;

		case R.id.btn_two:
			Intent innerIntent = new Intent(this, InnerlInterceptActivity.class);
			startActivity(innerIntent);
			break;
		}
	}

}
