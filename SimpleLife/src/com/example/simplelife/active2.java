package com.example.simplelife;
import java.util.Calendar;

import android.os.Bundle;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class active2 extends Activity{
	SQLiteDatabase db;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);//ע��˳��
        setContentView(R.layout.activity2);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title2);//�����Զ��������
        AppManager.getAppManager().addActivity(active2.this);//����Activityʵ����ӵ�AppManager�Ķ�ջ
        
        //�������ݿ��Լ����ݱ�
		db=SQLiteDatabase.openOrCreateDatabase(this.getFilesDir()+"/pocket_book.db3", null);
		//String sql="create table pocket_detail(year primary key,month primary key,day primary key,breakfast,launch,dinner,wash,other,total)";
		//db.execSQL(sql);����Ҫ����ʱ��������ʱ��Ҫͨ���쳣���жϱ��Ƿ��Ѿ�������
		
		//AppManager.getAppManager().addActivity(this);//����Activityʵ����ӵ�AppManager�Ķ�ջ
		
		//final Calendar c=Calendar.getInstance();
		//final int y=c.get(Calendar.YEAR);
		//final int m=c.get(Calendar.MONTH)+1;
		//final int d=c.get(Calendar.DAY_OF_MONTH);//���������
		
	/*----------achieve the date to be edited---------------*/
		Intent intent=getIntent();
		Bundle bundle=intent.getExtras();
		final int y=bundle.getInt("year");
		final int m=bundle.getInt("month");
		final int d=bundle.getInt("day");
		
        TextView show=(TextView)findViewById(R.id.config_date);
        show.setText(y+"\n"+m+"."+d);//������ʾ��ǰ����
        
        //���沿�ֱ༭��ֵ
        EditText e1=(EditText)findViewById(R.id.breakfast);
		EditText e2=(EditText)findViewById(R.id.launch);
		EditText e3=(EditText)findViewById(R.id.dinner);
		EditText e4=(EditText)findViewById(R.id.wash);
		EditText e5=(EditText)findViewById(R.id.other);
		TextView e6=(TextView)findViewById(R.id.total);
        if(tableExist("pocket_detail"))
		{   Cursor cursor=db.rawQuery("select * from pocket_detail where year=? and month=? and day=?",
					new String[]{Integer.toString(y),Integer.toString(m),Integer.toString(d)}); //��ѯ����ļ�¼��������ʾ��EditText��
			if(cursor.getCount()==0)
			    {e1.setText("0");e2.setText("0");e3.setText("0");e4.setText("0");e5.setText("0");e6.setText("0");}
			else{
				//���������в������ݣ����Ѿ����ڵĲ���������ʾ��EditText��
				cursor.moveToFirst();//�������£���ʹֻ��һ�н��
				int breakfast=cursor.getColumnIndex("breakfast");
			    int launch=cursor.getColumnIndex("launch");
			    int dinner=cursor.getColumnIndex("dinner");
			    int wash=cursor.getColumnIndex("wash");
			    int other=cursor.getColumnIndex("other");
			    int total=cursor.getColumnIndex("total");
				e1.setText(Float.toString(cursor.getFloat(breakfast)));
				e2.setText(Float.toString(cursor.getFloat(launch)));
				e3.setText(Float.toString(cursor.getFloat(dinner)));
				e4.setText(Float.toString(cursor.getFloat(wash)));
				e5.setText(Float.toString(cursor.getFloat(other)));
				e6.setText(Float.toString(cursor.getFloat(total)));
			    }
		 }
		else
		{
			e1.setText("0");e2.setText("0");e3.setText("0");e4.setText("0");e5.setText("0");e6.setText("0");
		}
        
        Button bn=(Button)findViewById(R.id.return_button2);//���÷���,�������ݿ���������ʾ��ǰһ��Activity���б�����
        bn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(active2.this,active1.class);
				finish();
				startActivity(intent);
				
			}
        	
           });
        
		e1.setFocusable(false);
		e1.setFocusableInTouchMode(false);
		
		e2.setFocusable(false);
		e2.setFocusableInTouchMode(false);
		
		e3.setFocusable(false);	
		e3.setFocusableInTouchMode(false);
		
		e4.setFocusable(false);
		e4.setFocusableInTouchMode(false);
		
		e5.setFocusable(false);	
		e5.setFocusableInTouchMode(false);//��ʼ״̬���ܱ༭
        
        Button bn1=(Button)findViewById(R.id.confirm);//�༭����ȷ����ť��Ҫʵ���ܼƺʹ����ݿ�Ĺ���
        bn1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				EditText e1=(EditText)findViewById(R.id.breakfast);
				e1.setFocusable(false);
				e1.setFocusableInTouchMode(false);
				EditText e2=(EditText)findViewById(R.id.launch);
				e2.setFocusable(false);
				e2.setFocusableInTouchMode(false);
				EditText e3=(EditText)findViewById(R.id.dinner);
				e3.setFocusable(false);	
				e3.setFocusableInTouchMode(false);
				EditText e4=(EditText)findViewById(R.id.wash);
				e4.setFocusable(false);
				e4.setFocusableInTouchMode(false);
				EditText e5=(EditText)findViewById(R.id.other);
				e5.setFocusable(false);	
				e5.setFocusableInTouchMode(false);//������ɺ�ȷ���������ò��ܱ༭
				
				float sum=0;
				sum=Float.parseFloat(e1.getText().toString())+Float.parseFloat(e2.getText().toString())+Float.parseFloat(e3.getText().toString())+
						Float.parseFloat(e4.getText().toString())+Float.parseFloat(e5.getText().toString());
				TextView show=(TextView)findViewById(R.id.total);//������ɺ�������廨��
				CharSequence cs=String.valueOf(sum);
				show.setText(cs);
				Calendar c=Calendar.getInstance();
				
				ContentValues values=new ContentValues();
				values.put("year",y);
				values.put("month",m);
				values.put("day",d);
				values.put("breakfast",Float.parseFloat(e1.getText().toString()));
				values.put("launch",Float.parseFloat(e2.getText().toString()));
				values.put("dinner",Float.parseFloat(e3.getText().toString()));
				values.put("wash",Float.parseFloat(e4.getText().toString()));
				values.put("other",Float.parseFloat(e5.getText().toString()));
				values.put("total",sum);
				
				
				
				//�����ݿ��������,���жϱ��Ƿ���ڣ������ھ�ֱ�Ӳ������ݣ��������ھʹ�����Ȼ���������
				if(tableExist("pocket_detail"))
				{	//String sql="select breakfast from pocket_detail Where year=c.get(Calendar.YEAR) and"
							//+"month=c.get(Calendar.MONTH)+1 and day=c.get(Calendar.DAY_OF_MONTH)";��ѯ�����û��table,ֻ��table��
					Cursor cursor=db.rawQuery("select * from pocket_detail where year=? and month=? and day=?",
							new String[]{Integer.toString(y),Integer.toString(m),Integer.toString(d)}); //��ѯ������¼�Ƿ��Ѿ�����
					if(cursor.getCount()==0)
					    {db.insert("pocket_detail", null, values);}
					else{
						//�������Ѿ��������������
				       db.update("pocket_detail", values, "year=? and month=? and day=?",new 
				    		   String[]{Integer.toString(y),Integer.toString(m),Integer.toString(d)});
					    }
				 }
				else
				{
					db.execSQL("create table pocket_detail(year integer,month integer,day integer,"
							+"breakfast,launch,dinner,wash,other,total,primary key(year,month,day))");
					db.insert("pocket_detail", null, values);
				}
			}
        });
		
       
		
        Button bn2=(Button)findViewById(R.id.modify);//���Ʊ༭���Ƿ�ɱ༭
        bn2.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				EditText e1=(EditText)findViewById(R.id.breakfast);
				e1.setFocusable(true);
				e1.setFocusableInTouchMode(true);
				e1.requestFocus();
				EditText e2=(EditText)findViewById(R.id.launch);
				e2.setFocusable(true);
				e2.setFocusableInTouchMode(true);
				EditText e3=(EditText)findViewById(R.id.dinner);
				e3.setFocusable(true);	
				e3.setFocusableInTouchMode(true);
				EditText e4=(EditText)findViewById(R.id.wash);
				e4.setFocusable(true);
				e4.setFocusableInTouchMode(true);
				EditText e5=(EditText)findViewById(R.id.other);
				e5.setFocusable(true);	
				e5.setFocusableInTouchMode(true);	
			}        	
        });
	}
    
	
	//�жϱ��Ƿ����
	public boolean tableExist(String tablename)
	{
		boolean result=false;
		if(tablename==null){return false;}
		Cursor cursor=null;
		try{
			String sql="select count(*)as c from sqlite_master where type='table' and name='"+tablename.trim()+"'";
			cursor=db.rawQuery(sql,null);
			if(cursor.moveToNext()){
				int count=cursor.getInt(0);
				if(count>0){result=true;}
			}
		}catch(Exception e){}
		return result;
		
	}
    
	
	public void onStop()
	{
		super.onStop();
		this.finish();
	}
	@Override
	public void onDestroy()
	{
		super.onDestroy();
		//�˳�����ʱ�ر�SQLiteDatabase
		if(db!=null&&db.isOpen()){
			db.close();
		}
	}

}
