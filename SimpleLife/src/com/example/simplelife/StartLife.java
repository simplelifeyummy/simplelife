package com.example.simplelife;

import android.os.Bundle;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;


public class StartLife extends Activity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);//ע��˳��
        setContentView(R.layout.activity1);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_start);
        setContentView(R.layout.activity_start_life);
        
        AppManager.getAppManager().addActivity(this);//����Activityʵ����ӵ�AppManager�Ķ�ջ
        
        Button bn=(Button) findViewById(R.id.button1);
        bn.setOnClickListener(new OnClickListener()
        {

			@Override
			public void onClick(View source) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(StartLife.this,active1.class);
        		startActivity(intent);
			}
			
        });
 
        
    /*--------�˳�����--------------*/
        Button exit=(Button) findViewById(R.id.exit);
        exit.setOnClickListener(new OnClickListener()
        {		
			@Override
        	public void onClick(View source)
        	{
				AppManager.getAppManager().AppExit(StartLife.this);
        	}
        });
    }
    
}
