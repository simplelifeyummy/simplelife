package com.example.simplelife;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;


public class active1 extends Activity {
	/*public String getTime(){
		Calendar c=Calendar.getInstance();
	}*/

	SQLiteDatabase db;
	final Calendar c=Calendar.getInstance();
	final int y=c.get(Calendar.YEAR);
	final int m=c.get(Calendar.MONTH)+1;
	final int d=c.get(Calendar.DAY_OF_MONTH);//今天的日期
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    /*---------标题栏布局--------------*/
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);//注意顺序
        setContentView(R.layout.activity1);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title);   
        
        
        AppManager.getAppManager().addActivity(this);//将该Activity实例添加到AppManager的堆栈
        
        
    /*-----------设置初始显示日期-----------*/
        final TextView date= (TextView)findViewById(R.id.date);
		date.setText(c.get(Calendar.YEAR)+"\n"+(c.get(Calendar.MONTH)+1)+"."+c.get(Calendar.DAY_OF_MONTH));
		
		
	/*------------返回原界面--------------*/
		Button return_start=(Button)findViewById(R.id.return_button);
		return_start.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				Intent intent=new Intent(active1.this,StartLife.class);// TODO Auto-generated method stub
				startActivity(intent);
			}
			
		});
		
	/*-----------设置日期选择器-------------*/	
        date.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				Calendar c=Calendar.getInstance();
				new DatePickerDialog(active1.this,new DatePickerDialog.OnDateSetListener() {
					
					@Override
					public void onDateSet(DatePicker dp, int year, int month, int day) {
						// TODO Auto-generated method stub
						date.setText(year+"\n"+(month+1)+"."+day);
						Bundle bundle=new Bundle();//transmit the selected date to edit page(acive2)
						bundle.putInt("year",year);
						bundle.putInt("month",month+1);
						bundle.putInt("day",day);
						Intent intent=new Intent(active1.this,active2.class);
						intent.putExtras(bundle);
						startActivity(intent);
					}
				}
				,c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH)).show();
			}
        	
        });
        
   /*------------进入记账页面----------*/
        Button add_expense=(Button)findViewById(R.id.add);
        add_expense.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				Bundle bundle=new Bundle();//transmit the selected date to edit page(acive2)
				bundle.putInt("year",y);
				bundle.putInt("month",m);
				bundle.putInt("day",d);
				Intent intent=new Intent(active1.this,active2.class);
				intent.putExtras(bundle);
				startActivityForResult(intent,0);      // TODO Auto-generated method stub
				
			}
        	
        });
		
	}

        
	@Override
	public void onResume()
	{
		super.onResume();
		/*-------------在listview中显示本月账单---------*/
        db=SQLiteDatabase.openOrCreateDatabase(this.getFilesDir()+"/pocket_book.db3", null);
        if(tableExist("pocket_detail"))
		{   
			Cursor cursor=db.rawQuery("select * from pocket_detail where year=? and month=? and day>=?",
					new String[]{Integer.toString(y),Integer.toString(m),Integer.toString(1)});
			if(cursor.getCount()!=0)
			{
				cursor.moveToLast();
				int year=cursor.getColumnIndex("year");
				int month=cursor.getColumnIndex("month");
				int day=cursor.getColumnIndex("day");
				int breakfast=cursor.getColumnIndex("breakfast");
			    int launch=cursor.getColumnIndex("launch");
			    int dinner=cursor.getColumnIndex("dinner");
			    int wash=cursor.getColumnIndex("wash");
			    int other=cursor.getColumnIndex("other");
			    int total=cursor.getColumnIndex("total");
			    List<Map<String,Object>> listItems=new ArrayList<Map<String,Object>>();
			    float total_monthly=0;
			    
			    do
			    {
			    	String list_date=Integer.toString(cursor.getInt(month))+"月"+
				                     Integer.toString(cursor.getInt(day))+"日";
				    detail_pday pday=new detail_pday(list_date,cursor.getInt(year),cursor.getInt(month),
				    		                     cursor.getInt(day),
				    		                     cursor.getFloat(breakfast),cursor.getFloat(launch),
						                         cursor.getFloat(dinner),cursor.getFloat(wash),
						                         cursor.getFloat(other),cursor.getFloat(total));
				    Map<String,Object> listItem=new HashMap<String,Object>();//存储列表项以及detail_pday对象
				    listItem.put("line",pday.getline());
				    listItem.put("object",pday);
				    listItems.add(listItem);
				    total_monthly=total_monthly+pday.gettotal();
				    
			    }while(cursor.moveToPrevious());
			    SimpleAdapter simpleAdapter=new SimpleAdapter(this,listItems,R.layout.simple_item,
			    	new String[] {"line"},new int[] {R.id.simple_item_content});
			    
			    TextView expense=(TextView) findViewById(R.id.expense);
			    expense.setText(Float.toString(total_monthly));
			    
			    ListView list=(ListView) findViewById(R.id.simple_list);
			    list.setAdapter(simpleAdapter);
			    list.setOnItemLongClickListener(new OnItemLongClickListener(){
			    	@Override
			    	public boolean onItemLongClick(AdapterView<?> parent,View view,int position,long id)
			    	{
			    		//parent is the clicked listview
			    		ListView listview=(ListView)parent;
			    		HashMap<String,Object> item=(HashMap<String,Object>)listview.getItemAtPosition(position);
			    		detail_pday pday=(detail_pday) item.get("object");
			    		Bundle data=new Bundle();
			    		data.putSerializable("line", pday);
			    		Intent intent=new Intent(active1.this,modify.class);
			    		intent.putExtras(data);
			    		startActivity(intent);
			    		return true;
			    	}
			    	
			    });
				
			}
			else
			{
				Toast toast=Toast.makeText(active1.this,"本月还没有记录！", Toast.LENGTH_SHORT);//there is no record in this month
				toast.show();
			}
		}
		else
		{
			Toast toast=Toast.makeText(active1.this,"现在还没有记录，可以开始记录！", Toast.LENGTH_SHORT);
			toast.show();//table is not exist
			
		}
	}
	
	
	//响应指定activity的返回结果:active2的返回结果，并显示在列表项中
	/*public void onActivityResult(int requestCode,int resultCode,Intent intent)
	{
		if(requestCode==0&&resultCode==0)
		{
			Bundle  bundle=intent.getExtras();
			Set<String> keySet = bundle.keySet();
			 for(String key : keySet) {
			    Object value = bundle.get(key);}
		}
	}*/
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
    
}
