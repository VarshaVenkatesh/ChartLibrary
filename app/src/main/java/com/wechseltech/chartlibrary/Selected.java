package com.wechseltech.chartlibrary;

public class Selected {

	String series,selected;
	double value;
	float x , y;
	
	public Selected()
	{
		
	}
	
	public void setValue(double value)
	{
		this.value=value;
	}
	
	public void setSelected(String selected)
	{
		this.selected=selected;
	}
	
	public double getValue()
	{
		return value;
	}
	
	public String getSelected()
	{
		return selected;
	}
	
	
	public  void setSeries(String series)
	{
		this.series=series;
	}
	
	public String getSeries()
	{
		return series;
	}
	
	public  void setAnchorX(float x)
	{
		this.x=x;
	}
	
	public float getAnchorX()
	{
		return x;
	}
	
	public float getAnchorY()
	{
		return y;
	}
	public  void setAnchorY(float y)
	{
		this.y=y;
	}
	
	
}
