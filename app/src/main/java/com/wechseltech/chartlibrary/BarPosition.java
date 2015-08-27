package com.wechseltech.chartlibrary;

public class BarPosition {

	
	double leftedge,rightedge,value,maxvalue,minvalue,xvalue, zvalue,yvalue,topedge,bottomedge;
	Boolean side;
	int series;
	String seriesname,label;
	
	
	
	
	public BarPosition()
	{
		
	}
	
	public void setLeftEdge(double leftedge)
	{
		this.leftedge=leftedge;
	}
	
	public void setSide(Boolean side)
	{
		this.side=side;
	}
	
	public boolean getSide()
	{
		return side;
	}
	
	public double getLeftEdge()
	{
		return leftedge;
	}
	public void setRightEdge(double rightedge)
	{
		this.rightedge=rightedge;
	}
	
	public double getRightEdge()
	{
		return rightedge;
	}
	
	public void setTopEdge(double topedge)
	{
		this.topedge=topedge;
	}
	
	public double getTopEdge()
	{
		return topedge;
	}
	
	public void setValue(double value)
	{
		this.value=value;
	}
	
	public double getValue()
	{
		return value;
	}
	
	public void setXValue(double value)
	{
		this.xvalue=value;
	}
	
	public double getXValue()
	{
		return xvalue;
	}
	
	public void setZValue(double value)
	{
		this.zvalue=value;
	}
	
	public double getZValue()
	{
		return zvalue;
	}
	
	public void setYValue(double value)
	{
		this.yvalue=value;
	}
	
	public double getYValue()
	{
		return yvalue;
	}
	
	public void setMaxValue(double value)
	{
		this.maxvalue=value;
	}
	
	public double getMaxValue()
	{
		return maxvalue;
	}
	
	public void setMinValue(double value)
	{
		this.minvalue=value;
	}
	
	public double getMinValue()
	{
		return minvalue;
	}
	
	public void setBottomEdge(double bottomedge)
	{
		this.bottomedge=bottomedge;
	}
	
	public double getBottomEdge()
	{
		return bottomedge;
	}
	
	public void setSeries(int series)
	{
		this.series=series;
	}
	
	public int getSeries()
	{
		return series;
	}
	
	public void setSeriesName(String seriesname)
	{
		this.seriesname=seriesname;
	}
	
	public String getSeriesName()
	{
		return seriesname;
	}
	
	public void setLabel(String label)
	{
		this.label=label;
	}
	
	public String getLabel()
	{
		return label;
	}
}
