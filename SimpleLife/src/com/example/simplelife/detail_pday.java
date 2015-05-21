package com.example.simplelife;

import java.io.Serializable;

public class detail_pday implements Serializable{
	private static final long serialVersionUID = -7060210544600464481L;//????
	private String date;//日期
	private int y;
	private int m;
	private int day;
	private float b;   //breakfast
	private float l;   //launch
	private float d;   //dinner
	private float w;   //wash
	private float o;   //other
	private float t;   //total
	public detail_pday(String date,int y,int m,int day,float b,float l,float d,float w,float o,float t)
	{
		this.date=date;
		this.y=y;
		this.m=m;
		this.day=day;
		this.b=b;
		this.l=l;
		this.d=d;
		this.w=w;
		this.o=o;
		this.t=t;
		
	}
	public String getline()
	{
		String str;
		str=date+"\n"+"早饭："+Float.toString(b)+"   "+"中饭："+Float.toString(l)+"   "+"晚饭："+Float.toString(d)
				+"\n"+"洗澡："+Float.toString(w)+"   "+"其它："+Float.toString(o)+"   "+"总计："+Float.toString(t);
		return str;
	}
	
	public float gettotal()
	{
		return t;
	}
	
    public float getbreakfast()
    {
    	return b;
    }
    
    public float getlaunch()
    {
    	return l;
    }
    
    public float getdinner()
    {
    	return d;
    }
    
    public float getother()
    {
    	return o;
    }
    
    public String getdate()
    {
    	return date;
    }
    
    public float getwash()
    {
    	return w;
    }
    
    public int getyear()
    {
    	return y;
    }
    
    public int getmonth()
    {
    	return m;
    }
    
    public int getday()
    {
    	return day;
    }
    
    
}
