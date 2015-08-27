package com.wechseltech.chartlibrary;

import android.graphics.RectF;


public class CategoryMultipleSeries {

	String xval;
	double yval;
	int series;
	int color;
	String seriesname;
	float percent,angle;
	float xtextoffset, ytextoffset;
	float startangle,endangle;
	float outerradii,innerradii;
	
	
	/**
	 * Constructor for doughnut chart 
	 * @param category  item representing a slice in one of the rings in the doughnut chart
	 * @param value   numerical value of the item
	 * @param series  integer designating the  data series that the item belongs to
	 * @param seriesname  name of the data series that the item belongs to
	 */
	public CategoryMultipleSeries (String category,double value,int series,
			String seriesname)
	{
		this.xval=category;
		this.yval=value;
		this.series=series;
		this.seriesname=seriesname;
		
		
		
		
	}
	
	/**
	 * Constructor for doughnut chart 
	 * @param category  item representing a slice in one of the rings in the doughnut chart
	 * @param value   numerical value of the item
	 * @param series  integer designating the  data series that the item belongs to
	 * @param seriesname  name of the data series that the item belongs to
	 * @param categorycolor color of the item
	 */
	
	public CategoryMultipleSeries (String category,double value,
			int series,String seriesname,int categorycolor)
	{
		this.xval=category;
		this.yval=value;
		this.color =categorycolor;
		this.series=series;
		this.seriesname=seriesname;
		
		
	}
	
	
	
	public int getSeries()
	{
		return series;
		
	}
	
	public String getSeriesname()
	{
		return seriesname;
		
	}
	
	
	public String getXVal()
	{
		return xval;
	}
	
	public double getYVal()
	{
		return yval;
	}
	
	public void setPercentage(float percent)
	{
		this.percent=percent;
		
	}
	
	public float getPercentage()
	{
		return percent;
	}
	
	
	public void setAngle(float angle)
	{
		this.angle=angle;
		
	}
	
	public float getAngle()
	{
		return angle;
	}
	
	public void setStartAngle(float startangle)
	{
		this.startangle = startangle;
	}
	
	public float getStartAngle()
	{
		return startangle;
	}
	
	public void setOuterRadii(float radii)
	{
		this.outerradii = radii;
	}
	
	public float getOuterRadii()
	{
		return outerradii;
	}
	
	public void setInnerRadii(float radii)
	{
		this.innerradii = radii;
	}
	
	public float getInnerRadii()
	{
		return innerradii;
	}
	
	
	
	
	public void setEndAngle(float endangle)
	{
		this.endangle = endangle;
	}
	
	public float getEndAngle()
	{
		return endangle;
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
	public void setColor(int color)
	{
		this.color=color;
		
	}
	
	public int getColor()
	{
		return color;
	}
}
