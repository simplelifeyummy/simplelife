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
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);//注意顺序
        setContentView(R.layout.activity2);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title2);//设置自定义标题栏
        AppManager.getAppManager().addActivity(active2.this);//将该Activity实例添加到AppManager的堆栈
        
        //创建数据库以及数据表
		db=SQLiteDatabase.openOrCreateDatabase(this.getFilesDir()+"/pocket_book.db3", null);
		//String sql="create table pocket_detail(year primary key,month primary key,day primary key,breakfast,launch,dinner,wash,other,total)";
		//db.execSQL(sql);表在要插入时创建，到时候要通过异常来判断表是否已经被创建
		
		//AppManager.getAppManager().addActivity(this);//将该Activity实例添加到AppManager的堆栈
		
		//final Calendar c=Calendar.getInstance();
		//final int y=c.get(Calendar.YEAR);
		//final int m=c.get(Calendar.MONTH)+1;
		//final int d=c.get(Calendar.DAY_OF_MONTH);//今天的日期
		
	/*----------achieve the date to be edited---------------*/
		Intent intent=getIntent();
		Bundle bundle=intent.getExtras();
		final int y=bundle.getInt("year");
		final int m=bundle.getInt("month");
		final int d=bundle.getInt("day");
		
        TextView show=(TextView)findViewById(R.id.config_date);
        show.setText(y+"\n"+m+"."+d);//设置显示当前日期
        
        //保存部分编辑的值
        EditText e1=(EditText)findViewById(R.id.breakfast);
		EditText e2=(EditText)findViewById(R.id.launch);
		EditText e3=(EditText)findViewById(R.id.dinner);
		EditText e4=(EditText)findViewById(R.id.wash);
		EditText e5=(EditText)findViewById(R.id.other);
		TextView e6=(TextView)findViewById(R.id.total);
        if(tableExist("pocket_detail"))
		{   Cursor cursor=db.rawQuery("select * from pocket_detail where year=? and month=? and day=?",
					new String[]{Integer.toString(y),Integer.toString(m),Integer.toString(d)}); //查询今天的记录，用于显示在EditText中
			if(cursor.getCount()==0)
			    {e1.setText("0");e2.setText("0");e3.setText("0");e4.setText("0");e5.setText("0");e6.setText("0");}
			else{
				//若今天已有部分数据，则将已经存在的部分数据显示在EditText中
				cursor.moveToFirst();//这句别落下，即使只有一行结果
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
        
        Button bn=(Button)findViewById(R.id.return_button2);//设置返回,并将数据库中内容显示在前一个Activity的列表项中
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
		e5.setFocusableInTouchMode(false);//初始状态不能编辑
        
        Button bn1=(Button)findViewById(R.id.confirm);//编辑完后的确定按钮，要实现总计和存数据库的功能
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
				e5.setFocusableInTouchMode(false);//输入完成后按确定键，设置不能编辑
				
				float sum=0;
				sum=Float.parseFloat(e1.getText().toString())+Float.parseFloat(e2.getText().toString())+Float.parseFloat(e3.getText().toString())+
						Float.parseFloat(e4.getText().toString())+Float.parseFloat(e5.getText().toString());
				TextView show=(TextView)findViewById(R.id.total);//输入完成后计算总体花销
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
				
				
				
				//向数据库插入数据,先判断表是否存在，若存在就直接插入数据，若不存在就创建表然后插入数据
				if(tableExist("pocket_detail"))
				{	//String sql="select breakfast from pocket_detail Where year=c.get(Calendar.YEAR) and"
							//+"month=c.get(Calendar.MONTH)+1 and day=c.get(Calendar.DAY_OF_MONTH)";查询语句中没有table,只有table名
					Cursor cursor=db.rawQuery("select * from pocket_detail where year=? and month=? and day=?",
							new String[]{Integer.toString(y),Integer.toString(m),Integer.toString(d)}); //查询该条记录是否已经存在
					if(cursor.getCount()==0)
					    {db.insert("pocket_detail", null, values);}
					else{
						//若数据已经存在则更新数据
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
		
       
		
        Button bn2=(Button)findViewById(R.id.modify);//控制编辑框是否可编辑
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
    
	
	//判断表是否存在
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
		//退出程序时关闭SQLiteDatabase
		if(db!=null&&db.isOpen()){
			db.close();
		}
	}

}
