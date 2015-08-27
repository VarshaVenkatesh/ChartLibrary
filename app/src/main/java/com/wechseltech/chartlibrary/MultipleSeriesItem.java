package com.wechseltech.chartlibrary;

public class MultipleSeriesItem {

	
	int series;
	String xcategory,ycategory;
	double y,x,scaleval,zval;
	int color;
	String seriesname;
	boolean negative;
	String label;
	
	/****************************************************/
	/**
	 * Constructor for MultipleSeriesBarChart
	 * and Stacked Bar Chart
	 * @param series  integer indicating the series the item belongs to 
	 * @param xcategory  categories on the x-axis
	 * @param y  value of a particular category on the y axis
	 * @param seriesname  name of the data series that the item belongs to
	 */
	public MultipleSeriesItem(int series,String xcategory, 
			double y,String seriesname)
	{
		this.y =y;
		this.xcategory=xcategory;
		this.series=series;
		this.seriesname=seriesname;
		
		
	}
	
	/**
	 * Constructor for MultipleSeriesBarChart
	 * and Stacked Bar Chart
	 * @param series  integer indicating the series the item belongs to 
	 * @param xcategory  categories on the x-axis
	 * @param y  value of a particular category on the y axis
	 * @param seriesname  name of the data series that the item belongs to
	 * @param label label assigned to point
	 */
	public MultipleSeriesItem(int series,String xcategory, 
			double y,String seriesname,String label)
	{
		this.y =y;
		this.xcategory=xcategory;
		this.series=series;
		this.seriesname=seriesname;
		this.label=label;
		
		
	}
	
	
	/**
	 *Constructor for MultipleSeriesBarChart
	 * and Stacked Bar Chart
	 * @param series  integer indicating the series the item belongs to
	 * @param xcategory  categories on the x-axis
	 * @param y  value of a particular category on the y axis
	 * @param seriesname  name of the data series that the item belongs to
	 * @param seriescolor color assigned to the series
	 */
	
	public MultipleSeriesItem(int series,String xcategory, double y,String seriesname,int 
			seriescolor)
	{
		this.y =y;
		this.xcategory=xcategory;
		this.series=series;
		this.color=seriescolor;
		this.seriesname=seriesname;
		
	}
	
	/**
	 *Constructor for MultipleSeriesBarChart
	 * and Stacked Bar Chart
	 * @param series  integer indicating the series the item belongs to
	 * @param xcategory  categories on the x-axis
	 * @param y  value of a particular category on the y axis
	 * @param seriesname  name of the data series that the item belongs to
	 * @param seriescolor color assigned to the series
	 * @param label assigned to point
	 */
	
	public MultipleSeriesItem(int series,String xcategory, double y,String seriesname,int 
			seriescolor,String label)
	{
		this.y =y;
		this.xcategory=xcategory;
		this.series=series;
		this.color=seriescolor;
		this.seriesname=seriesname;
		this.label=label;
		
	}
	
	/**
	 * Constructor for Bubble Chart
	 * @param series  integer indicating the series the item belongs to
	 * @param xcategory  categories on the x-axis
	 * @param y  value of a particular category on the y-axis
	 * @param zval	z value that determines the size of the bubble
	 * @param seriesname   name of the data series that the item belongs to
	 * @param seriescolor  color assigned to the series
	 */
	
	public MultipleSeriesItem(int series,String xcategory, double y,double zval,
			String seriesname,int seriescolor)
	{
		this.y =y;
		this.xcategory=xcategory;
		this.series=series;
		this.zval=zval;
		this.color=seriescolor;
		this.seriesname=seriesname;
		
	}

	/**
	 * Constructor for Bubble Chart
	 * @param series  integer indicating the series the item belongs to
	 * @param xcategory  categories on the x-axis
	 * @param y  value of a particular category on the y-axis
	 * @param zval	z value that determines the size of the bubble
	 * @param seriesname  name of the data series that the item belongs to
	 */
	public MultipleSeriesItem(int series,String xcategory, 
			double y, double zval,String seriesname)
	{
		this.y =y;
		this.xcategory=xcategory;
		this.series=series;
		this.seriesname=seriesname;
		this.zval=zval;
		
		
	}
	
	
	/**
	 * Constructor for Bubble Chart
	 * @param series  integer indicating the series the item belongs to
	 * @param xcategory  categories on the x-axis
	 * @param y  value of a particular category on the y-axis
	 * @param zval	z value that determines the size of the bubble
	 * @param label label for a bubble 
	 * @param seriesname  name of the data series that the item belongs to
	 */
	
	public MultipleSeriesItem(int series,String xcategory, double y, 
			double zval,String label,String seriesname)
	{
		this.y =y;
		this.xcategory=xcategory;
		this.label=label;
		this.series=series;
		this.seriesname=seriesname;
		this.zval=zval;
		
		
	}
	
	/**
	 * Constructor for Bubble Chart
	 * @param series  integer indicating the series the item belongs to
	 * @param xcategory  categories on the x-axis
	 * @param y  value of a particular category on the y-axis
	 * @param zval	z value that determines the size of the bubble
	 * @param label label for a bubble
	 * @param seriesname  name of the data series that the item belongs to
	 * @param seriescolor  color assigned to the series
	 */
	public MultipleSeriesItem(int series,String xcategory, double y,double zval,
			String label,String seriesname,int seriescolor)
	{
		this.y =y;
		this.xcategory=xcategory;
		this.label=label;
		this.series=series;
		this.zval=zval;
		this.color=seriescolor;
		this.seriesname=seriesname;
		
	}
	
	
	/**
	 * Constructor for Bubble Chart
	 * @param series  integer indicating the series the item belongs to
	 * @param x  x-axis value
	 * @param y  y-axis value
	 * @param zval	z value that determines the size of the bubble
	 * @param seriesname  name of the data series that the item belongs to
	 */
	
	public MultipleSeriesItem(int series,double x, double y, double zval,String seriesname)
	{
		this.y =y;
		this.x=x;
		this.series=series;
		this.seriesname=seriesname;
		this.zval=zval;
		
		
	}
	/**
	 * Constructor for Bubble Chart
	 * @param series  integer indicating the series the item belongs to
	 * @param x  x-axis value
	 * @param y  y-axis value
	 * @param zval	z value that determines the size of the bubble
	 * @param label label for a bubble
	 * @param seriesname  name of the data series that the item belongs to
	 */
	public MultipleSeriesItem(int series,double x, double y, double zval,String label,
			String seriesname)
	{
		this.y =y;
		this.x=x;
		this.label=label;
		this.series=series;
		this.seriesname=seriesname;
		this.zval=zval;
		
		
	}
	
	/**
	 * Constructor for Bubble Chart
	 * @param series  integer indicating the series the item belongs to
	 * @param x  x-axis value
	 * @param y  y-axis value
	 * @param zval	z value that determines the size of the bubble
	 * @param seriesname  name of the data series that the item belongs to
	 * @param seriescolor color assigned to a series
	 */
	
	public MultipleSeriesItem(int series,double x, double y,
			double zval,String seriesname,int seriescolor)
	{
		this.y =y;
		this.x=x;
		this.series=series;
		this.zval=zval;
		this.color=seriescolor;
		this.seriesname=seriesname;
		
	}
	/**
	 * Constructor for Bubble Chart
	 * @param series  integer indicating the series the item belongs to
	 * @param x  x-axis value
	 * @param y  y-axis value
	 * @param zval	z value that determines the size of the bubble
	 * @param label  label for a bubble
	 * @param seriesname  name of the data series that the item belongs to
	 * @param seriescolor color assigned to a series
	 */
	
	public MultipleSeriesItem(int series,double x, double y,double zval
			,String label,String seriesname,int seriescolor)
	{
		this.y =y;
		this.x=x;
		this.label=label;
		this.series=series;
		this.zval=zval;
		this.color=seriescolor;
		this.seriesname=seriesname;
		
	}
	
	
	
	/**
	 * Constructor for Negative Bar Chart
	 * @param xcategory  categories on the x-axis
	 * @param y  y-axis value
	 * @param seriesname  name of the data series that the item belongs to
	 * @param negative if true will be draw on the negative side
	 * @param seriescolor color assigned to a series
	 */
	public MultipleSeriesItem(String xcategory, double y,String 
			seriesname,boolean negative,int seriescolor)
	{
		this.y =y;
		this.xcategory=xcategory;
		this.color=seriescolor;
		this.seriesname=seriesname;
		this.negative=negative;
		
	}
	
	/**
	 * Constructor for Negative Bar Chart
	 * @param xcategory  categories on the x-axis
	 * @param y  y-axis value
	 * @param seriesname  name of the data series that the item belongs to
	 * @param negative if true will be draw on the negative side
	 * @param seriescolor color assigned to a series
	 * @param label label assigned to point
	 */
	public MultipleSeriesItem(String xcategory, double y,String 
			seriesname,boolean negative,int seriescolor,String label)
	{
		this.y =y;
		this.xcategory=xcategory;
		this.color=seriescolor;
		this.seriesname=seriesname;
		this.negative=negative;
		this.label=label;
		
	}
	

		/**
		 * Constructor for Negative Bar Chart
		 * @param xcategory  categories on the x-axis
		 * @param y  y-axis value
		 * @param seriesname  name of the data series that the item belongs to
		 * @param negative  if true will be draw on the negative side
		 */
		public MultipleSeriesItem(String xcategory, double y,
					String seriesname,boolean negative)
		{
				this.y =y;
				this.xcategory=xcategory;
				this.negative=negative;
				this.seriesname=seriesname;
				
		}
		
		/**
		 * Constructor for Negative Bar Chart
		 * @param xcategory  categories on the x-axis
		 * @param y  y-axis value
		 * @param seriesname  name of the data series that the item belongs to
		 * @param negative  if true will be draw on the negative side
		 * @param label label assigned to point
		 */
		public MultipleSeriesItem(String xcategory, double y,
					String seriesname,boolean negative,String label)
		{
				this.y =y;
				this.xcategory=xcategory;
				this.negative=negative;
				this.label=label;
				this.seriesname=seriesname;
				
		}
	
		/**
		 * Constructor for Negative Bar Chart
		 * @param x  x-axis value
		 * @param ycategory  categories on the y-axis
		 * @param seriesname  name of the data series that the item belongs to
		 * @param negative  if true will be draw on the negative side
		 * @param seriescolor  color assigned to a series
		 */
		public MultipleSeriesItem(double x, String ycategory
				,String seriesname,boolean negative,int seriescolor)
		{
			this.x =x;
			this.ycategory=ycategory;
			this.color=seriescolor;
			this.seriesname=seriesname;
			this.negative=negative;
			
		}
		
		/**
		 * Constructor for Negative Bar Chart
		 * @param x  x-axis value
		 * @param ycategory  categories on the y-axis
		 * @param seriesname  name of the data series that the item belongs to
		 * @param negative  if true will be draw on the negative side
		 * @param seriescolor  color assigned to a series
		 * @param label label assigned to point
		 */
		public MultipleSeriesItem(double x, String ycategory
				,String seriesname,boolean negative,int seriescolor,String label)
		{
			this.x =x;
			this.ycategory=ycategory;
			this.color=seriescolor;
			this.seriesname=seriesname;
			this.negative=negative;
			this.label=label;
			
		}
	
	
		/**
		 * Constructor for Negative Bar Chart
		 * @param x  x-axis value
		 * @param ycategory  categories on the y-axis
		 * @param seriesname  name of the data series that the item belongs to
		 * @param negative  if true will be draw on the negative side
		 */
		public MultipleSeriesItem(double x, String ycategory,
				String seriesname,boolean negative)
		{
			this.x =x;
			this.ycategory=ycategory;
			this.negative=negative;
			this.seriesname=seriesname;
			
		}
		
		/**
		 * Constructor for Negative Bar Chart
		 * @param x  x-axis value
		 * @param ycategory  categories on the y-axis
		 * @param seriesname  name of the data series that the item belongs to
		 * @param negative  if true will be draw on the negative side
		 * @param label label assigned to point
		 */
		public MultipleSeriesItem(double x, String ycategory,
				String seriesname,boolean negative,String label)
		{
			this.x =x;
			this.ycategory=ycategory;
			this.negative=negative;
			this.seriesname=seriesname;
			this.label=label;
			
		}
	
	
	
		/**
		 * Constructor for Multiple Series Bar Chart and Stacked Bar Chart
		 * @param series  integer indicating the series the item belongs to
		 * @param x  x-axis value
		 * @param ycategory  categories on the y-axis
		 * @param seriesname  name of the data series that the item belongs to
		 * @param seriescolor  color assigned to a series
		 */
	
		public MultipleSeriesItem(int series,double x,String ycategory,
				String seriesname,int seriescolor)
		{
			this.x =x;
			this.ycategory=ycategory;
			this.series=series;
			this.color=seriescolor;
			this.seriesname=seriesname;
			
			
		}
		
		/**
		 * Constructor for Multiple Series Bar Chart and Stacked Bar Chart
		 * @param series  integer indicating the series the item belongs to
		 * @param x  x-axis value
		 * @param ycategory  categories on the y-axis
		 * @param seriesname  name of the data series that the item belongs to
		 * @param seriescolor  color assigned to a series
		 * @param label label assigned to point
		 */
	
		public MultipleSeriesItem(int series,double x,String ycategory,
				String seriesname,int seriescolor,String label)
		{
			this.x =x;
			this.ycategory=ycategory;
			this.series=series;
			this.color=seriescolor;
			this.seriesname=seriesname;
			this.label=label;
			
			
		}
		
		
		/**
		 * Constructor for Multiple Series Bar Chart and Stacked Bar Chart
		 * @param series  integer indicating the series the item belongs to
		 * @param x  x-axis value
		 * @param ycategory  categories on the y-axis
		 * @param seriesname  name of the data series that the item belongs to
		 */
		public MultipleSeriesItem(int series,double x,String ycategory,String seriesname)
		{
			this.x =x;
			this.ycategory=ycategory;
			this.series=series;
			this.seriesname=seriesname;
			
		}
		
		/**
		 * Constructor for Multiple Series Bar Chart and Stacked Bar Chart
		 * @param series  integer indicating the series the item belongs to
		 * @param x  x-axis value
		 * @param ycategory  categories on the y-axis
		 * @param seriesname  name of the data series that the item belongs to
		 * @param label label assigned to point
		 */
		public MultipleSeriesItem(int series,double x,String ycategory
				,String seriesname,String label)
		{
			this.x =x;
			this.ycategory=ycategory;
			this.series=series;
			this.seriesname=seriesname;
			this.label=label;
			
		}
		
		/**
		 * Constructor for Bubble Chart
		 * @param series  integer indicating the series the item belongs to
		 * @param x  x-axis value
		 * @param ycategory  categories on the y-axis
		 * @param zval	z value that determines the size of the bubble
		 * @param seriesname  name of the data series that the item belongs to
		 * @param seriescolor  color assigned to a series
		 */
		
		public MultipleSeriesItem(int series,double x,String ycategory,double zval
				,String seriesname,int seriescolor)
		{
			this.x =x;
			this.ycategory=ycategory;
			this.zval=zval;
			this.series=series;
			this.color=seriescolor;
			this.seriesname=seriesname;
			
		}
		
		/**
		 * Constructor for Bubble Chart
		 * @param series  integer indicating the series the item belongs to
		 * @param x  x-axis value
		 * @param ycategory  categories on the y-axis
		 * @param zval	z value that determines the size of the bubble
		 * @param label  label for a bubble
		 * @param seriesname name of the data series that the item belongs to
		 * @param seriescolor  color assigned to a series
		 */
		
		public MultipleSeriesItem(int series,double x,String ycategory,double zval
				,String label,String seriesname,int seriescolor)
		{
			this.x =x;
			this.ycategory=ycategory;
			this.zval=zval;
			this.label=label;
			this.series=series;
			this.color=seriescolor;
			this.seriesname=seriesname;
			
		}
		
	
		/**
		 * Constructor for Bubble Chart
		 * @param series  integer indicating the series the item belongs to
		 * @param x  x-axis value
		 * @param ycategory  categories on the y-axis
		 * @param zval	z value that determines the size of the bubble
		 * @param seriesname name of the data series that the item belongs to
		 */
	
		public MultipleSeriesItem(int series,double x,
				String ycategory,double zval,String seriesname)
		{
			
			this.x =x;
			this.ycategory=ycategory;
			this.zval=zval;
			
			this.series=series;
			this.seriesname=seriesname;
			
		}
	
		/**
		 * Constructor for Bubble Chart
		 * @param series  integer indicating the series the item belongs to
		 * @param x  x-axis value
		 * @param ycategory  categories on the y-axis
		 * @param zval	z value that determines the size of the bubble
		 * @param label  label for a bubble
		 * @param seriesname name of the data series that the item belongs to
		 */
		public MultipleSeriesItem(int series,double x,String ycategory,
				double zval,String label,String seriesname)
		{
			this.x =x;
			this.ycategory=ycategory;
			this.label=label;
			this.zval=zval;
			this.series=series;
			this.seriesname=seriesname;
			
		}
	
	
	
		
	
	/**
	 * Constructor for Bubble Chart
	 * @param series  integer indicating the series the item belongs to
	 * @param xcategory  categories on the x-axis
	 * @param ycategory  categories on the y-axis
	 * @param zval	z value that determines the size of the bubble
	 * @param seriesname name of the data series that the item belongs to
	 */
	public MultipleSeriesItem(int series,String xcategory,String ycategory,
			double zval,String seriesname)
	{
		this.xcategory =xcategory;
		this.ycategory=ycategory;
		this.zval=zval;
		this.series=series;
		this.seriesname=seriesname;
		
	}
	
	/**
	 * Constructor for Bubble Chart
	 * @param series integer indicating the series the item belongs to
	 * @param xcategory categories on the x-axis
	 * @param ycategory  categories on the y-axis
	 * @param zval	z value that determines the size of the bubble
	 * @param label  label for a bubble
	 * @param seriesname name of the data series that the item belongs to
	 */
	public MultipleSeriesItem(int series,String xcategory,String ycategory,
			double zval,String label,String seriesname)
	{
		this.xcategory =xcategory;
		this.ycategory=ycategory;
		this.label=label;
		this.zval=zval;
		this.series=series;
		this.seriesname=seriesname;
		
	}
	
	/**
	 * Constructor for Bubble Chart
	 * @param series  integer indicating the series the item belongs to
	 * @param xcategory  categories on the x-axis
	 * @param ycategory  categories on the y-axis
	 * @param zval	z value that determines the size of the bubble
	 * @param seriesname  name of the data series that the item belongs to
	 * @param seriescolor  color assigned to a series
	 */
	
	public MultipleSeriesItem(int series,String xcategory,String ycategory,
			double zval,String seriesname,int seriescolor)
	{
		this.xcategory =xcategory;
		this.ycategory=ycategory;
		this.series=series;
		this.zval=zval;
		this.color=seriescolor;
		this.seriesname=seriesname;
		
	}
	
	
	/**
	 * Constructor for Bubble Chart
	 * @param series  integer indicating the series the item belongs to
	 * @param xcategory  categories on the x-axis
	 * @param ycategory  categories on the y-axis
	 * @param zval	z value that determines the size of the bubble
	 * @param label  label for a bubble
	 * @param seriesname  name of the data series that the item belongs to
	 * @param seriescolor  color assigned to a series
	 */
	public MultipleSeriesItem(int series,String xcategory,String ycategory,
			double zval,String label,String seriesname,int seriescolor)
	{
		this.xcategory =xcategory;
		this.ycategory=ycategory;
		this.series=series;
		this.label=label;
		this.color=seriescolor;
		this.seriesname=seriesname;
		this.zval=zval;
		
	}
	
	
	
	
	
	/****************************************************/
	
	public String getSeriesname()
	{
		return seriesname;
	}
	
	public double getYVal()
	{
		return y;
	}
	
	public String getXLabel()
	{
		return xcategory;
	}
	
	public int getSeries()
	{
		return series;
	}
	
	public boolean getSide()
	{
		return negative;
	}
	
	
	public void setColor(int color)
	{
		System.out.println("ENTCOL" + color);
		
		this.color=color;
		System.out.println("SETCOL" + color);
		
	}
	
	public int getColor()
	{
		return color;
	}
	
	
	public void setScaledValue(double scaleval)
	{
		this.scaleval = scaleval;
		
	}
	
	public double getScaledValue()
	{
		return scaleval;
	}
	
	
	
	public String getSeriesName()
	{
		return seriesname;
	}
	
	public double getXVal()
	{
		return x;
	}
	
	public String getYLabel()
	{
		return ycategory;
	}
	
	public double getZVal()
	{
		return zval;
	}
	
	public String setLabel()
	{
		return label;
	}
	
	public String getLabel()
	{
		return label;
	}
	
	
	
}
