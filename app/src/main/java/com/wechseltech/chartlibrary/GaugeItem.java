package com.wechseltech.chartlibrary;


public class GaugeItem {

	double val;
	double minimum,
	maximum,highlightStart,highlightStop;
	int numTicks;
	
	
	public GaugeItem(double val,double minimum,double maximum, int numTicks,
			double highlightStart,double highlightStop)
	{
		
		this.val=val;
		this.minimum = minimum;
		this.maximum=maximum;
		this.numTicks=numTicks;
		this.highlightStart=highlightStart;
		this.highlightStop=highlightStop;
	}
	
	public double getHighlightStart()
	{
		return highlightStart;
		
		
	}
	
	public double getHighlightStop()
	{
		return highlightStop;
		
		
	}
	
	public double getValue()
	{
		return val;
	}
	
	public double getMaximum()
	{
		return maximum;
	}
	
	public double getMinimum()
	{
		return minimum;
	}
	
	public double getNumTicks()
	{
		return numTicks;
	}
	
	
}
