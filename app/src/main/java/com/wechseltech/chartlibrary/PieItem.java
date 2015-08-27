package com.wechseltech.chartlibrary;

public class PieItem {
	
	String category;
	double value;
	float percent,angle;
	int color;
	float startangle,endangle;
	float xtextoffset,ytextoffset;
	
	
	/**
	 * Constructor for Pie Chart
	 * @param category
	 * @param value
	 */
	public PieItem (String category,double value)
	{
		this.category=category;
		this.value=value;
		
		
	}
	
	/**
	 * Constructor for Pie Chart
	 * @param category
	 * @param value
	 * @param color
	 */
	
	public PieItem (String category,double value,int color)
	{
		this.category=category;
		this.value=value;
		this.color=color;
		
		
	}
	
	public String getXVal()
	{
		return category;
	}
	
	public double getYVal()
	{
		return value;
	}
	
	public void setPercentage(float percent)
	{
		this.percent=percent;
		
	}
	
	public float getPercentage()
	{
		return percent;
	}
	
	public void setColor(int color)
	{
		this.color=color;
		
	}
	
	public int getColor()
	{
		return color;
	}
	public void setAngle(float angle)
	{
		this.angle=angle;
		
	}
	
	public void setStartAngle(float startangle)
	{
		this.startangle = startangle;
	}
	
	public float getStartAngle()
	{
		return startangle;
	}
	
	public void setEndAngle(float endangle)
	{
		this.endangle = endangle;
	}
	
	public void setXTextOffset(float textoffset)
	{
		this.xtextoffset = textoffset;
	}
	
	public float getXTextOffset()
	{
		return xtextoffset;
	}
	
	public void setYTextOffset(float textoffset)
	{
		this.ytextoffset = textoffset;
	}
	
	public float getYTextOffset()
	{
		return ytextoffset;
	}
	
	public float getEndAngle()
	{
		return endangle;
	}
	
	public float getAngle()
	{
		return angle;
	}
	
	
}
