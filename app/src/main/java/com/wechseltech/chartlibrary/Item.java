package com.wechseltech.chartlibrary;

import java.util.Comparator;

public class Item implements Comparator<Item> {

	private double xval;
	private double yval;
	private double zval;
	private String xcategory,ycategory;
	private String label,seriesname;
	int series,color;
	
	
	
	public String getXLabel()
	{
		
		return String.valueOf(xval);
		
	}
	
	public String getCatXLabel()
	{
		
		return xcategory;
		
	}
	
	
	public void setSeriesColor(int color)
	{
		this.color=color;
	}
	
	public int getSeriesColor()
	{
		return color;
	}
	
	
	
	public String getCatYLabel()
	{
		return ycategory;
	}
	
	
	
	/**
	 * 
	 * @param xval value on xaxis
	 * @param yval value on yaxis
	 * @param series integer indicating the series the item belongs to
	 * @param seriesname name assigned to series
	 */

	public Item (double xval,double yval,int series ,String seriesname)
	{
		this.xval=xval;
		this.yval=yval;
		
		this.series=series;
		this.seriesname=seriesname;
		
	}
	
	
	/**
	 * 
	 * @param xval value on xaxis
	 * @param yval value on yaxis
	 * @param series integer indicating the series the item belongs to 
	 * @param seriesname name assigned to series
	 * @param seriescolor color assigned to series
	 */
	public Item (double xval,double yval,int series ,String seriesname,int seriescolor)
	{
		this.xval=xval;
		this.yval=yval;
		this.color=seriescolor;
		this.series=series;
		this.seriesname=seriesname;
		
	}
	

	/**
	 * 
	 * @param xval value on xaxis
	 * @param yval value on yaxis
	 * @param label label assigned to point
	 * @param series  integer indicating the series the item belongs to 
	 * @param seriesname  name assigned to series
	 */
	public Item (double xval,double yval,String label,int series, String seriesname)
	{
		this.xval=xval;
		this.yval=yval;
		
		this.label=label;
		this.series=series;
		this.seriesname=seriesname;
	}
	
	
	/**
	 * 
	 * @param xval value on xaxis
	 * @param yval value on yaxis
	 * @param label label assigned to point
	 * @param series integer indicating the series the item belongs to
	 * @param seriesname  name assigned to series
	 * @param seriescolor color assigned to series
	 */
	public Item (double xval,double yval,String label,int series, String seriesname,int seriescolor)
	{
		this.xval=xval;
		this.yval=yval;
		this.color=seriescolor;
		this.label=label;
		this.series=series;
		this.seriesname=seriesname;
	}
	

	
	/**
	 * 
	 * @param xval value on xaxis
	 * @param ycategory categories on yaxis
	 * @param series integer indicating the series the item belongs to
	 * @param seriesname  name assigned to series
	 */
	public Item (double xval,String ycategory,int series,String seriesname)
	{
		this.xval=xval;
		this.ycategory=ycategory;
		this.series=series;
		this.seriesname=seriesname;
		
	}
	
	
	/**
	 * 
	 * @param xval value on xaxis
	 * @param ycategory categories on y axis
	 * @param series integer indicating the series the item belongs to
	 * @param seriesname  name assigned to series
	 * @param seriescolor color assigned to series
	 */
	public Item (double xval,String ycategory,int series,String seriesname,int seriescolor)
	{
		this.xval=xval;
		this.ycategory=ycategory;
		this.series=series;
		this.seriesname=seriesname;
		this.color=seriescolor;
		
	}
	
	
	/**
	 * 
	 * @param xval value on xaxis
	 * @param ycategory categories on y axis
	 * @param label label assigned to point
	 * @param series integer indicating the series the item belongs to
	 * @param seriesname  name assigned to series
	 * @param seriescolor color assigned to series
	 */
	public Item (double xval,String ycategory,String label,
			int series, String seriesname,int seriescolor)
	{
		this.xval=xval;
		this.ycategory=ycategory;
		this.color=seriescolor;
		this.label=label;
		this.series=series;
		this.seriesname=seriesname;
		
	}
	
	/**
	 * 
	 * @param xval value on xaxis
	 * @param ycategory categories on y axis
	 * @param label label assigned to point
	 * @param series integer indicating the series the item belongs to
	 * @param seriesname  name assigned to series
	 */

	public Item (double xval,String ycategory,String label,int series, String seriesname)
	{
		this.xval=xval;
		this.ycategory=ycategory;
		
		this.label=label;
		this.series=series;
		this.seriesname=seriesname;
		
	}
	

	/**
	 * 
	 * @param xcategory categories on x axis
	 * @param ycategory categories on y axis
	 * @param series integer indicating the series the item belongs to
	 * @param seriesname  name assigned to series
	 */
	public Item (String xcategory,String ycategory,int series, String seriesname)
	{
		this.xcategory=xcategory;
		this.ycategory=ycategory;
		
		
		this.series=series;
		this.seriesname=seriesname;
		
	}
	
	/**
	 * 
	 * @param xcategory categories on x axis
	 * @param ycategory categories on y axis
	 * @param label label assigned to point
	 * @param series integer indicating the series the item belongs to
	 * @param seriesname  name assigned to series
	 */
	public Item (String xcategory,String ycategory,String label,int series, String seriesname)
	{
		this.xcategory=xcategory;
		this.ycategory=ycategory;
		
		this.label=label;
		this.series=series;
		this.seriesname=seriesname;
		
	}

	

	/**
	 * 
	 * @param xcategory categories on x axis
	 * @param ycategory categories on y axis
	 * @param series integer indicating the series the item belongs to
	 * @param seriesname  name assigned to series
	 * @param seriescolor color assigned to series
	 */
	public Item (String xcategory,String ycategory,int series,String seriesname,int seriescolor)
	{
		this.xcategory=xcategory;
		this.ycategory=ycategory;
		this.series=series;
		this.seriesname=seriesname;
		this.color=seriescolor;
		
		
	}
	
	/**
	 * 
	 * @param xcategory categories on x axis
	 * @param ycategory categories on y axis
	 * @param label label assigned to point
	 * @param series integer indicating the series the item belongs to
	 * @param seriesname  name assigned to series
	 * @param seriescolor color assigned to series
	 */
	public Item (String xcategory,String ycategory,String label,
			int series, String seriesname,int seriescolor)
	{
		this.xcategory=xcategory;
		this.ycategory=ycategory;
		this.color=seriescolor;
		this.label=label;
		this.series=series;
		this.seriesname=seriesname;
		
	}
	
	/**
	 * 
	 * @param xcategory categories on x axis
	 * @param yval value on y axis
	 * @param series integer indicating the series the item belongs to
	 * @param seriesname  name assigned to series
	 */
	public Item (String xcategory,double yval,int series, String seriesname)
	{
		this.xcategory=xcategory;
		this.yval=yval;
		
		this.series=series;
		this.seriesname=seriesname;
		
		
	}
	
	
	/**
	 * 
	 * @param xcategory categories on x axis
	 * @param yval value on y axis
	 * @param series integer indicating the series the item belongs to
	 * @param seriesname  name assigned to series
	 * @param seriescolor color assigned to series
	 */
	public Item (String xcategory,double yval,int series, String seriesname,int seriescolor)
	{
		this.xcategory=xcategory;
		this.yval=yval;
		this.color=seriescolor;
		this.series=series;
		this.seriesname=seriesname;
		
		
	}
	

	/**
	 * 
	 * @param xcategory categories on x axis
	 * @param yval value on y axis
	 * @param label label assigned to point
	 * @param series integer indicating the series the item belongs to
	 * @param seriesname name assigned to series
	 */
	public Item (String xcategory,double yval,String label,int series, String seriesname)
	{
		this.xcategory=xcategory;
		this.yval=yval;
		
		this.label=label;
		this.series=series;
		this.seriesname=seriesname;
		
	}
	
	
	/**
	 * 
	 * @param xcategory categories on x axis
	 * @param yval value on y axis
	 * @param label label assigned to point
	 * @param series integer indicating the series the item belongs to
	 * @param seriesname name assigned to series
	 * @param seriescolor  color assigned to series
	 */
	public Item (String xcategory,double yval,String label,int series, String seriesname,int seriescolor)
	{
		this.xcategory=xcategory;
		this.yval=yval;
		this.color=seriescolor;
		this.label=label;
		this.series=series;
		this.seriesname=seriesname;
		
	}
	
	
	public String getLabel()
	{
		return label;
	}
	
	
	
	public double getXVal()
	{
		return xval;
	}
	
	public double getYVal()
	{
		return yval;
	}
	
	public double getZVal()
	{
		return zval;
	}
	
	public String getStringXVal()
	{
		return xcategory;
	}
	
	public String getStringYVal()
	{
		return ycategory;
	}
	
	public int getSeries()
	{
		return series;
	}
	
	public String getSeriesName()
	{
		return seriesname;
	}

	//TODO ONLY FOR BUBBLE - EXCLUDE FOR TYPE SCATTER PLOT
	@Override
	public int compare(Item lhs, Item rhs) {
		
		double alzval = Math.abs(lhs.getZVal());
		double arzval = Math.abs(rhs.getZVal());
		return (alzval >arzval? -1 : (alzval== arzval? 0 : 1));
		
	}
	
	public static Comparator<Item> Comparator 
    = new Comparator<Item>() {

		public int compare(Item item1, Item item2) {


			return compare(item1,item2);


		}
		};
	
}
