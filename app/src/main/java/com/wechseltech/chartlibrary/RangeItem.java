package com.wechseltech.chartlibrary;

public class RangeItem {

	double minval,
	maxval,mean;
	String label;
	
	/**
	 * Constructor for Range Chart
	 * @param minval Minimum value of the category 
	 * @param maxval Maximum value of the category 
	 * @param category 
	 */
	
	public RangeItem(double minval,double maxval,String category)
	{
		this.maxval=maxval;
		this.minval=minval;
		this.label=category;
	}
	
	
	/**
	 * Constructor for Range Chart
	 * @param minval Minimum value of the category 
	 * @param mean   Mean of the category
	 * @param maxval  Maximum value of the category
	 * @param category 
	 */
	
	public RangeItem(double minval,double mean,double maxval,String category)
	{
		this.maxval=maxval;
		this.minval=minval;
		this.mean=mean;
		this.label=category;
	}
	
	
	
	
	
	public double getMinValue()
	{
		return minval;
		
	}
	
	public double getMaxValue()
	{
		return maxval;
		
	}
	
	public double getMeanValue()
	{
		return mean;
		
	}
	
	public String getLabel()
	{
		return label;
		
	}
}
