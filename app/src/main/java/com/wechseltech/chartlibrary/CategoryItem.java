package com.wechseltech.chartlibrary;

public class CategoryItem {
	
	String seriesname,category;
	double val,polarangle;
	int seriescolor;
	int series;
	int order;
	double numberOf;
	float startangle, endangle;
	
	
	/**
	 * Constructor for Polar Chart and Spider Web
	 * @param seriesname name assigned to series
	 * @param seriescolor color assigned to series
	 * @param category  
	 * @param val value of category
	 * @param series  integer indicating the series which the item belong to
	 */
	public CategoryItem(String seriesname,int seriescolor, String category,
			double val,int series)
	{
		this.val=val;
		this.seriesname=seriesname;
		this.seriescolor=seriescolor;
		this.category=category;
		this.series=series;
		
	}
	
	/**
	 * Constructor for Polar Chart and Spider Web 
	 * @param seriesname name assigned to series  
	 * @param category
	 * @param val value of category
	 * @param series integer indicating the series which the item belong to
	 */
	public CategoryItem(String seriesname, String category, double val,int series)
	{
		this.val=val;
		this.seriesname=seriesname;
		
		this.category=category;
		this.series=series;
		
	}
	
	
	
	
	/**
	 * Constructor for Polar Chart
	 * @param seriesname  name assigned to the series
	 * @param seriescolor  color assigned to series
	 * @param polarangle  angle of the item between 0 and 360  
	 * @param val value at the given polar angle
	 * @param series integer indicating the series to which the item belongs to
	 */
	public CategoryItem(String seriesname,int seriescolor,double polarangle,
			double val,int series)
	{
		this.val=val;
		this.seriesname=seriesname;
		this.seriescolor=seriescolor;
		this.polarangle=polarangle;
		this.series=series;
		
		
	}
	
	/**
	 * Constructor for Polar Chart
	 * @param seriesname  name assigned to the series
	 * @param polarangle  angle of the item between 0 and 360 
	 * @param val value at the given polar angle
	 * @param series integer indicating the series to which the item belongs to
	 */
	public CategoryItem(String seriesname,double polarangle,
			double val,int series)
	{
		this.val=val;
		this.seriesname=seriesname;
		this.polarangle=polarangle;
		this.series=series;
		
	}
	
	
	/**
	 * Constructor for Wind Rose
	 * @param seriesname name assigned to series
	 * @param category category
	 * @param numberOf Number of items in that category and series
	 */
	public CategoryItem(String seriesname, String category,double numberOf)
	{
		
		this.seriesname=seriesname;
		
		this.category=category;
		
		this.numberOf=numberOf;
		
		
	}
	
	/**
	 * Constructor for Wind Rose
	 * @param seriesname  name assigned to series
	 * @param category
	 * @param numberOf Number of items in that category and series
	 * @param seriescolor color assigned to series
	 */
	public CategoryItem(String seriesname, String category,int seriescolor,double numberOf)
	{
		
		this.seriesname=seriesname;
		
		this.category=category;
		
		this.numberOf=numberOf;
		this.seriescolor = seriescolor;
		
		
	}
	
	/*	//FOR WIND ROSE
	public CategoryItem(String seriesname, int seriescolor,String category)
	{
			
			this.seriesname=seriesname;
			this.seriescolor=seriescolor;
			this.category=category;
			this.series=series;
			
	}*/
	
	
	//POLAR AND OTHERS


	public double getValue()
	{
		return val;
	}
	
	public int getSeriesColor()
	{
		return seriescolor;
	}
	
	public void setSeriesColor(int seriescolor)
	{
		this.seriescolor=seriescolor;
	}
	
	public int getSeries()
	{
		return series;
	}
	
	public double getPolarAngle()
	{
		return polarangle;
	}
	
	public void setPolarAngle(double polarangle)
	{
		this.polarangle=polarangle;
	}
	
	
	public String getSeriesName()
	{
		return seriesname;
	}
	
	
	
	public String getCategory()
	{
		return category;
	}
	
	//For SpiderWeb
	public int getOrder()
	{
		return order;
	}
	public void setOrder(int order)
	{
		this.order=order;
	}
	public double getNumberOf()
	{
		return numberOf;
		
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
	public float getEndAngle()
	{
		return endangle;
	}
}
