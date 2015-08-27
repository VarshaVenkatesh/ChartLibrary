package com.wechseltech.chartlibrary;

import java.text.DecimalFormat;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

public class ComputeStops {

	public ComputeStops()
	{
		
	}
	
	public void computeStopsCategory(int length,AxisStops stops)
	{
		
		double[] outstops = new double[length+1];
		
		outstops[0]=0;

		int a=1;
		 for(double x =1; x<=length;x++)
		 {
			outstops[a] = (float)(x);
			a++;
		 }
		 
		 stops.axisstops=outstops;
		 //Includes the leftmost stop - i.e Viewport.left
		 stops.numstops=a-1;
		
		
	}
	
//	private void computeStopsNum(int length,AxisStops stops,double graphMin,double graphMax,double interval)
//	{
//		
//		System.out.println("length " + length);
//		
//		System.out.println("Gph.Min "+graphMin);
//		
//		System.out.println("Gph.Maxs "+graphMax);
//		
//		System.out.println("Interval "+ interval);
//		
//		
//		float[] outstops = new float[length+1];
//		
//		
//		 outstops[0]=(float) (graphMin-interval);
//		 int a=1;
//		 
//		 for(double x =graphMin; x<=(graphMax);x=x+interval)
//		 {
//			outstops[a] = (float)x;
//			System.out.println("AXIS STOPS " + outstops[a]);
//			a++;
//		 }
//		 
//		 stops.axisstops=outstops;
//		//Includes the leftmost stop - i.e Viewport.left
//		 stops.numstops=a-1;
//		 
//		 
//		 
//		
//	}
	
	public void computeStopsNum(int length,AxisStops stops,double graphMin,double graphMax,double interval)
	{
		
		
		
		
		double[] outstops = new double[length+1];
		
		
		 outstops[0]=(float) (graphMin-interval);
		 int a=1;
		 
		 for(double x =graphMin; x<=(graphMax + (0.5*interval));x=x+interval)
		 {
			System.out.println("AXIS STOPS X" + x);
			outstops[a] = (float)x;
			System.out.println("AXIS STOPS " + outstops[a]);
			a++;
		 }
		 
		 stops.axisstops=outstops;
		//Includes the leftmost stop - i.e Viewport.left
		 stops.numstops=a-1;
		 
		 
		 
		
	}
	
	public void computeStopsNumRounded(int length,AxisStops stops,double graphMin,double graphMax,double interval)
	{
		
		
		
		System.out.println("Length" + length);
		
		
		double[] outstops = new double[length+3];
		
		
		 outstops[0]=(float) (graphMin-interval);
		 int a=1;
		 
		 for(double x =graphMin; x<=(graphMax + interval);x=x+interval)
		 {
			System.out.println("AXIS STOPS X" + x);
			outstops[a] = (float)x;
			System.out.println("AXIS STOPS " + outstops[a]);
			a++;
		 }
		 
		 stops.axisstops=outstops;
		//Includes the leftmost stop - i.e Viewport.left
		 stops.numstops=a-1;
		 
		 
		 
		
	}
	
	
	public void computeStopsNumRoundedBar(int length,AxisStops stops,double graphMin,double graphMax,double interval)
	{
		
		
		
		System.out.println("Length" + length);
		
		
		double[] outstops = new double[length+1];
		
		
		
		 int a=0;
		 
		 for(double x =graphMin; x<=(graphMax+0.5*interval);x=x+interval)
		 {
			System.out.println("AXIS STOPS X" + x);
			outstops[a] = (float)x;
			System.out.println("AXIS STOPS " + outstops[a]);
			a++;
		 }
		 
		 stops.axisstops=outstops;
		//Includes the leftmost stop - i.e Viewport.left
		 stops.numstops=a-1;
		 
		 
		 
		
	}
	
	public void computeStopsNumNegative
	(int Llength,AxisStops stops,
			double LgraphMin,double LgraphMax,double Linterval,
			int Rlength,
			double RgraphMin,double RgraphMax,double Rinterval)
	
	
	
	
	{
		
		
		double[] outstops = new double[Llength+Rlength+1];
		double[] pointstops = new double[Llength+Rlength+1];
		
		/*float[] outstops = new float[Llength+Rlength+2];
		float[] pointstops = new float[Llength+Rlength+2];*/
		
		 
		
		 int a=0,c=0,e=0;
		 
		 
		 
		 /*for(double x =LgraphMin; x<=(LgraphMax + (0.5*Linterval));x=x+Linterval)
		 {
			System.out.println("AXIS STOPS L" + (LgraphMax + (0.5*Linterval)));
			
			//outstops[a] = (float)x;
			leftstops[e]=(float) x;
			pointstops[c]=(float)a+1;
			System.out.println("AXIS STOPS " + leftstops[e]);
			a++;
			c++;
			e++;
		 }*/
		 
			
			/*for(double x =(LgraphMax);x>=(LgraphMin-0.5*Linterval);x=x-Linterval)
			 {
				
				
				outstops[a] = (float)x;
				//leftstops[e]=(float) x;
				pointstops[c]=(float)a+1;
				System.out.println("AXIS STOPS " + outstops[a]);
				a++;
				c++;
				e++;
			 }*/
		 
		 for(double x =(LgraphMax);x>(LgraphMin);x=x-Linterval)
		 {
			
			
			outstops[a] = (float)x;
			//leftstops[e]=(float) x;
			//pointstops[c]=(float)a+1;
			pointstops[c]=(float)a;
			System.out.println("AXIS STOPS L" + outstops[a]);
			a++;
			c++;
			e++;
		 }
		 
		 //outstops[a]=(float) (LgraphMin-Linterval);
		 //pointstops[c]=(float)a+1;
		 
		// pointstops[c]=(float)a;
		 
		 
		 //float midpoint = a+1;
		 
		 float midpoint = a;
		 
		 //pointstops[a]=a+1;
		 
		 pointstops[a]=a;
		 
		 outstops[a]=(float) LgraphMin;
		 
		 
		 
		 //int b=a+2;
		 //int b=a;
		 
		 int b=a+1;
		 System.out.println("LENGTH" + Rlength);
		 float [] rightstops = new float[Rlength+1];
		 
		 int d=0;
		/* for(double x = RgraphMin; x<=
				 (RgraphMax + (0.5*Rinterval));x=
				 x+Rinterval)
		 {
			
			//System.out.println("AXIS STOPS X" + x);
			outstops[b] = (float)x;
		   // rightstops[d]= (float)x;
			pointstops[b]=(float)b+1;
			System.out.println("AXIS STOPS " + outstops[b]);
			b++;
			d++;
		 }*/
		 
		 
		/* for(double x = RgraphMin+Rinterval; x<=
				 (RgraphMax + (0.5*Rinterval));x=
				 x+Rinterval)*/
		 
		 for(double x = RgraphMin+Rinterval; x<=
				 (RgraphMax);x=
				 x+Rinterval)
			 
		 {
			
			//System.out.println("AXIS STOPS X" + x);
			outstops[b] = (float)x;
		   // rightstops[d]= (float)x;
			//pointstops[b]=(float)b+1;
			pointstops[b]=(float)b;
			System.out.println("AXIS STOPS " + outstops[b]);
			b++;
			d++;
		 }
		
		 
		 
		 stops.pointstops=outstops;
		 stops.axisstops = pointstops;
		 stops.midpoint=midpoint;
		//Includes the leftmost stop - i.e Viewport.left
		 stops.numstops=b-1;
		 
		 for(int y=0; y<outstops.length;y++)
		 {
			 System.out.println("POINTSTOP" + outstops[y]);
			 System.out.println("AXISSTOP" + pointstops[y]);
		 }
		 
		 System.out.println("MIDPOINT" + midpoint);
		 
		 
		
	}
	
	private double niceNumeral(double calrange)
	 
	 {
		 double nf;
		 int exp = (int)Math.floor(Math.log10(calrange));
		 double frac = calrange/Math.pow(10,exp);
		 
		 if(frac<1.5)
			 nf=1;
		 else if(frac<3)
			 nf=2;
		 else if(frac<7)
			 nf=5;
		 else 
			 nf=10;
		 
		 System.out.println("EXP" + nf*Math.pow(10,exp));
		 return nf*Math.pow(10,exp);
		 
	 }
	public MinMax computeMaxMinRangeSpider(double[] array,double interval,int visTicks)
	{
		
		
		MinMax  values = new MinMax();
		double graphMin =  (Math.floor(array[0]/interval)*interval);
		double graphMax = (Math.ceil(array[array.length-1]/interval)*interval);
		
		
		System.out.println("GRAPH Interval" + interval);
		System.out.println("GRAPHMINC" + graphMin);
		System.out.println("GRAPHMAXC" + graphMax);
		float graphRange = (float)((visTicks-1)*interval+graphMin);
		
		values.graphMax=graphMax;
		values.graphMin=graphMin;
		values.graphRange=graphRange;
		
		
		return values;
	}
	
	public MinMax computeMaxMinRangeDouble(double[] array,double interval,int visTicks)
	{
		
		
		MinMax  values = new MinMax();
		double graphMin = Math.floor(array[0]/interval)*interval;
		double graphMax = Math.ceil(array[array.length-1]/interval)*interval;
		
		
		System.out.println("GRAPH Interval" + interval);
		System.out.println("GRAPHMINC" + graphMin);
		System.out.println("GRAPHMAXC" + graphMax);
		float graphRange = (float)((visTicks-1)*interval+graphMin);
		
		values.graphMax=graphMax;
		values.graphMin=graphMin;
		values.graphRange=graphRange;
		
		
		return values;
	}
	
	public MinMax computeMaxMinRange(double[] array,double interval,int visTicks)
	{
		
		
		MinMax  values = new MinMax();
		double graphMin = (float)niceNumeral(array[0]);
		double graphMax = (float)niceNumeral(array[array.length-1]);
		
		
		
		float graphRange = (float)((visTicks-1)*interval+graphMin);
		
		values.graphMax=graphMax;
		values.graphMin=graphMin;
		values.graphRange=graphRange;
		
		
		return values;
	}
	
	
	

	
}
