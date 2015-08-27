package com.wechseltech.chartlibrary;


public class LineItem {

	double xval,yval;
	int series;
	String seriesname,syval,sxval;
	int order=-1;
	int color;
	Boolean bothcategory;
	String label;
	
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
	
	
	
	public String getCatXLabel()
	{
		
		return sxval;
		
	}
	
	
	public String getCatYLabel()
	{
		return syval;
	}
	
	public int getSeries()
	{
		return series;
	}
	
	public int getOrder()
	{
		return order;
	}
	
	public String getSeriesName()
	{
		return seriesname;
	}
	
	
	/**
	 * Constructor for Line Chart and Area Chart
	 * @param xval  value on x-axis
	 * @param yval  value on y-axis
	 * @param series  integer indicating the series the item belongs to 
	 * @param seriesname  name of the data series that the item belongs to
	 */
	
	public LineItem (double xval,double yval,int series ,String seriesname)
	{
		this.xval=xval;
		this.yval=yval;
		this.series=series;
		this.seriesname=seriesname;
		
	}
	
	/**
	 * Constructor for Line Chart and Area Chart
	 * @param xval  value on x-axis
	 * @param yval  value on y-axis
	 * @param series  integer indicating the series the item belongs to 
	 * @param seriesname  name of the data series that the item belongs to
	 * @param seriescolor   color assigned to the series
	 */
	public LineItem (double xval,double yval,int series ,String seriesname,int seriescolor)
	{
		this.xval=xval;
		this.yval=yval;
		this.series=series;
		this.color=seriescolor;
		this.seriesname=seriesname;
		
	}
	
	/**
	 * Constructor for Line Chart and Area Chart
	 * @param xval  value on x-axis
	 * @param yval  value on y-axis
	 * @param label label assigned to the point
	 * @param series  integer indicating the series the item belongs to 
	 * @param seriesname  name of the data series that the item belongs to
	 */
	public LineItem (double xval,double yval, 
			String label,int series, String seriesname)
	{
		this.xval=xval;
		this.yval=yval;
		
		this.label=label;
		this.series=series;
		this.seriesname=seriesname;
	}
	
	/**
	 * Constructor for Line Chart and Area Chart
	 * @param xval  value on x-axis
	 * @param yval  value on y-axis
	 * @param label label assigned to the point
	 * @param series  integer indicating the series the item belongs to
	 * @param seriesname  name of the data series that the item belongs to
	 * @param seriescolor  color assigned to the series
	 */
	public LineItem (double xval,double yval, 
			String label,int series, String seriesname,int seriescolor)
	{
		this.xval=xval;
		this.yval=yval;
		this.color=seriescolor;
		this.label=label;
		this.series=series;
		this.seriesname=seriesname;
	}
	
	

	
	
	/**
	 * Constructor for Line Chart and Area Chart
	 * @param xval value on x-axis
	 * @param category  categories on the y-axis
	 * @param order position of the point in the line
	 * @param series  integer indicating the series the item belongs to
	 * @param seriesname  name of the data series that the item belongs to
	 */
	
	public LineItem (double xval,String category,int order,
			int series,String seriesname)
	{
		this.xval=xval;
		this.syval=category;
		this.order=order;
		this.series=series;
		this.seriesname=seriesname;
		
	}
	
	/**
	 * Constructor for Line Chart and Area Chart
	 * @param xval value on x-axis
	 * @param category  categories on the y-axis
	 * @param order position of the point in the line
	 * @param series  integer indicating the series the item belongs to
	 * @param seriesname  name of the data series that the item belongs to
	 * @param seriescolor  color assigned to the series
	 */
	
	public LineItem (double xval,String category,int order,int series,
			String seriesname,int seriescolor)
	{
		this.xval=xval;
		this.syval=category;
		this.order=order;
		this.series=series;
		this.seriesname=seriesname;
		this.color=seriescolor;
		
	}
	
	/**
	 * Constructor for Line Chart and Area Chart
	 * @param xval value on x-axis
	 * @param category  categories on the y-axis
	 * @param order position of the point in the line
	 * @param label label assigned to the point
	 * @param series  integer indicating the series the item belongs to
	 * @param seriesname  name of the data series that the item belongs to
	 */
	public LineItem (double xval,String category,
			int order,String label,int series,String seriesname)
	{
		this.xval=xval;
		this.syval=category;
		this.order=order;
		this.label=label;
		this.series=series;
		this.seriesname=seriesname;
		
	}
	

	/**
	 * Constructor for Line Chart and Area Chart
	 * @param xval value on x-axis
	 * @param category  categories on the y-axis
	 * @param order position of the point in the line
	 * @param label label assigned to the point
	 * @param series  integer indicating the series the item belongs to
	 * @param seriesname  name of the data series that the item belongs to
	 * @param seriescolor  color assigned to the series
	 */
	public LineItem (double xval,String category,
			int order,String label,int series,String seriesname,int seriescolor)
	{
		this.xval=xval;
		this.syval=category;
		this.order=order;
		this.label=label;
		this.series=series;
		this.seriesname=seriesname;
		this.color=seriescolor;
		
	}
	
	

	
	/**
	 * Constructor for Line Chart and Area Chart
	 * @param xcategory  categories on the x-axis
	 * @param ycategory  categories on the y-axis
	 * @param order  position of the point in the line
	 * @param series  integer indicating the series the item belongs to
	 * @param seriesname  name of the data series that the item belongs to
	 */
	
	public LineItem (String xcategory,String ycategory,int order,int series,String seriesname)
	{
		this.sxval=xcategory;
		this.syval=ycategory;
		
		this.order=order;
		this.series=series;
		this.seriesname=seriesname;
		
	}
	
	/**
	 * Constructor for Line Chart and Area Chart
	 * @param xcategory  categories on the x-axis
	 * @param ycategory  categories on the y-axis
	 * @param order  position of the point in the line
	 * @param series  integer indicating the series the item belongs to
	 * @param seriesname  name of the data series that the item belongs to
	 * @param seriescolor  color assigned to the series
	 */
	public LineItem (String xcategory,String ycategory,int order,int series,String seriesname,int seriescolor)
	{
		this.sxval=xcategory;
		this.syval=ycategory;
		this.color=seriescolor;
		this.order=order;
		this.series=series;
		this.seriesname=seriesname;
		
	}
	

	
	/**
	 * Constructor for Line Chart and Area Chart
	 * @param xcategory  categories on the x-axis
	 * @param ycategory  categories on the y-axis
	 * @param order  position of the point in the line
	 * @param label label assigned to the point
	 * @param series  integer indicating the series the item belongs to
	 * @param seriesname  name of the data series that the item belongs to
	 */

	public LineItem (String xcategory,String ycategory,int order,String label,int series,String seriesname)
	{
		this.sxval=xcategory;
		this.syval=ycategory;
		this.label=label;
		this.order=order;
		this.series=series;
		this.seriesname=seriesname;
		
	}
	
	/**
	 * Constructor for Line Chart and Area Chart
	 * @param xcategory  categories on the x-axis
	 * @param ycategory  categories on the y-axis
	 * @param order  position of the point in the line
	 * @param label label assigned to the point
	 * @param series  integer indicating the series the item belongs to
	 * @param seriesname  name of the data series that the item belongs to
	 * @param seriescolor color assigned to the series
	 */
	
	public LineItem (String xcategory,String ycategory,int order,String label,int series,
			String seriesname,int seriescolor)
	{
		this.sxval=xcategory;
		this.syval=ycategory;
		this.label=label;
		this.order=order;
		this.series=series;
		this.seriesname=seriesname;
		this.color=seriescolor;
		
	}
	
	
	
	
	
	/**
	 * Constructor for Line Chart and Area Chart
	 * @param xcategory categories on x-axis
	 * @param yval value on y-axis
	 * @param order  position of the point in the line
	 * @param series  integer indicating the series the item belongs to
	 * @param seriesname  name of the data series that the item belongs to
	 * @param seriescolor color assigned to the series
	 
	 */
	public LineItem (String xcategory,double yval,int order,int series, String seriesname,
			int seriescolor)
	{
		this.sxval=xcategory;
		this.yval=yval;
		this.order=order;
		this.series=series;
		this.seriesname=seriesname;
		this.color=seriescolor;
		
		
	}
	
	/**
	 * Constructor for Line Chart and Area Chart
	 * @param xcategory categories on x-axis
	 * @param yval value on y-axis
	 * @param order  position of the point in the line
	 * @param series  integer indicating the series the item belongs to
	 * @param seriesname  name of the data series that the item belongs to
	 */
	public LineItem (String xcategory,double yval,int order,int series, String seriesname)
	{
		this.sxval=xcategory;
		this.yval=yval;
		this.order=order;
		this.series=series;
		this.seriesname=seriesname;
		
		
	}
	
	
	
	/**
	 * Constructor for Line Chart and Area Chart
	 * @param xcategory  categories on x-axis
	 * @param yval value on y-axis
	 * @param order  position of the point in the line
	 * @param label label assigned to the point
	 * @param series  integer indicating the series the item belongs to
	 * @param seriesname  name of the data series that the item belongs to
	 */
	public LineItem (String xcategory,double yval,int order,
			String label,int series, String seriesname)
	{
		this.sxval=xcategory;
		this.yval=yval;
		this.label=label;
		this.order=order;
		this.series=series;
		this.seriesname=seriesname;
		
		
	}
	
	/**
	 * Constructor for Line Chart and Area Chart
	 * @param xcategory  categories on x-axis
	 * @param yval value on y-axis
	 * @param order  position of the point in the line
	 * @param label label assigned to the point
	 * @param series  integer indicating the series the item belongs to
	 * @param seriesname  name of the data series that the item belongs to
	 * @param seriescolor color assigned to the series
	 */
	public LineItem (String xcategory,double yval,int order,String label,int series,
			String seriesname, int seriescolor)
	{
		this.sxval=xcategory;
		this.yval=yval;
		this.label=label;
		this.order=order;
		this.series=series;
		this.color=seriescolor;
		this.seriesname=seriesname;
		
		
	}
	
	/******************************************/
	public String getStringXVal()
	{
		return sxval;
	}
	
	public String getStringYVal()
	{
		return syval;
	}
	
	public int getColor()
	{
		return color;
	}
	
	
	
}
