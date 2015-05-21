package com.example.simplelife;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class modify extends Activity {
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);//ע��˳��
        setContentView(R.layout.expense_modify);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_modify);//�����Զ��������
        
		AppManager.getAppManager().addActivity(this);//����Activityʵ����ӵ�AppManager�Ķ�ջ
		
/*-----------��ʾ�޸�ǰ����-----------*/
		Intent intent=getIntent();
		final detail_pday pday=(detail_pday) intent.getSerializableExtra("line");
		final EditText e1=(EditText)findViewById(R.id.breakfast);
		final EditText e2=(EditText)findViewById(R.id.launch);
		final EditText e3=(EditText)findViewById(R.id.dinner);
		final EditText e4=(EditText)findViewById(R.id.wash);
		final EditText e5=(EditText)findViewById(R.id.other);
		TextView e6=(TextView)findViewById(R.id.total);
		e1.setText(Float.toString(pday.getbreakfast()));
		e2.setText(Float.toString(pday.getlaunch()));
		e3.setText(Float.toString(pday.getdinner()));
		e4.setText(Float.toString(pday.getwash()));
		e5.setText(Float.toString(pday.getother()));
		e6.setText(Float.toString(pday.gettotal()));
		
		
/*------------�޸�����-------------*/
		
		Button bn=(Button) findViewById(R.id.confirm);
		final SQLiteDatabase db=SQLiteDatabase.openOrCreateDatabase(this.getFilesDir()+"/pocket_book.db3", null);
		bn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				float sum=0;
				sum=Float.parseFloat(e1.getText().toString())+Float.parseFloat(e2.getText().toString())+
						Float.parseFloat(e3.getText().toString())+
						Float.parseFloat(e4.getText().toString())+Float.parseFloat(e5.getText().toString());//����һ���ܻ���
				ContentValues values=new ContentValues();
				values.put("year",pday.getyear());
				values.put("month",pday.getmonth());
				values.put("day",pday.getday());
				values.put("breakfast",Float.parseFloat(e1.getText().toString()));
				values.put("launch",Float.parseFloat(e2.getText().toString()));
				values.put("dinner",Float.parseFloat(e3.getText().toString()));
				values.put("wash",Float.parseFloat(e4.getText().toString()));
				values.put("other",Float.parseFloat(e5.getText().toString()));
				values.put("total",sum);
				db.update("pocket_detail", values, "year=? and month=? and day=?",new 
			    		   String[]{Integer.toString(pday.getyear()),Integer.toString(pday.getmonth()),
						            Integer.toString(pday.getday())});
				Intent intent=new Intent(modify.this,active1.class);
				startActivity(intent);
			}
		});
		
		
		
		
		
	}

}
