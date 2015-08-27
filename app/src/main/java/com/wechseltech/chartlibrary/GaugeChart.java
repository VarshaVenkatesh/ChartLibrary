package com.wechseltech.chartlibrary;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.PointF;
import android.graphics.RectF;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

public class GaugeChart extends ChartView{

	float legendSize
	;
	int min;
	int mLabelWidth,
	mLabelHeight,
	thickness;
	MinMax Data;
	
	Paint gPaint,axisPaint,
	circlePaint,arrowPaint,
	boundaryPaint,
	rpaint;
	RectF boxbounds,
	coordbounds,axisbounds,
	textbounds,legendBox
	,highlightbounds;
	
	float length;
	int dialcolor;
	float diameter ;
	float tdiameter;
	
	double hInterval;
	
	float [] angles;
	
	int hRTicks;
	AxisStopValues hstops;
	
	RectF Viewport;
	
	float AXIS_H_MAX,AXIS_H_MIN;
	
	float angleInterval;

	GaugeItem series;
	Paint tPaint,highlightPaint,aPaint;
	
	
	
	
	private float mScaleFactor = 1.f;
	
	float mPosX,mPosY,diffX=0,diffY=0;
	
	 public ScaleGestureDetector mScaleGestureDetector=null;
	 public GestureDetectorCompat mGestureDetector=null;
	
	 String hLabelName;
	 
	public GaugeChart(Context context) {
		super(context);
		
		System.out.println("INIT C");
		
		mScaleGestureDetector = new ScaleGestureDetector(context,mScaleGestureListener);
		
		
		
	}
	
	
	
	public void setSeriesName(String hLabelName)
	{
		this.hLabelName=hLabelName;
	}
	
	
	public String getSeriesName()
	{
		return hLabelName;
	}
	
	
	public void setGaugeItem(GaugeItem series)
	{
		this.series=series;
	}
	
	
	public GaugeChart(Context context,AttributeSet attrs) {
		super(context,attrs);
		System.out.println("INIT At");
		
		
		
		
		
		
		mScaleGestureDetector = new ScaleGestureDetector(context,mScaleGestureListener);
		
		TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.Charts,0,0);
		
		try
		{
			
			
			mLabelSeparation = a.getDimension(R.styleable.Charts_labelSeparation, 8);
			mTextSize = a.getDimension(R.styleable.Charts_textSize, 12);
			textcolor = a.getColor(R.styleable.Charts_textColor, Color.BLACK);
			highlightcolor = a.getColor(R.styleable.Charts_highlightColor ,Color.RED);
			gaugecolor = a.getColor(R.styleable.Charts_gaugeColor ,Color.BLACK);
			arrowcolor = a.getColor(R.styleable.Charts_arrowColor ,Color.RED);
			boundarycolor=a.getColor(R.styleable.Charts_boundingboxColor ,Color.RED);
			
			
			
			
			
		}
		
		finally
		{
			
			a.recycle();
		}
		
		
	}
	
	
	
	
	public GaugeChart(Context context,AttributeSet attrs,int defStyle) {
		
		
		super(context,attrs,defStyle);
		System.out.println("INIT DF");
		
		
		mScaleGestureDetector = new ScaleGestureDetector(context,mScaleGestureListener);
		
		
		TypedArray a = context.getTheme().
				obtainStyledAttributes(attrs,R.styleable.Charts,defStyle,defStyle);
		
		try
		{
			
			mLabelSeparation = a.getDimension(R.styleable.Charts_labelSeparation, 8);
			mTextSize = a.getDimension(R.styleable.Charts_textSize, 12);
			textcolor = a.getColor(R.styleable.Charts_textColor, Color.BLACK);
			highlightcolor = a.getColor(R.styleable.Charts_highlightColor ,Color.RED);
			gaugecolor = a.getColor(R.styleable.Charts_gaugeColor ,Color.BLACK);
			arrowcolor = a.getColor(R.styleable.Charts_arrowColor ,Color.RED);
			boundarycolor=a.getColor(R.styleable.Charts_boundingboxColor ,Color.RED);
			
			
			
		}
		
		finally
		{
			
			a.recycle();
		}
		
		
		
	}
	
	
	
	
	
	private void init()
	
	
	{
		
		hstops = new AxisStopValues();
		
		
		gPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        gPaint.setColor(gaugecolor);
        gPaint.setStrokeWidth(4);
        gPaint.setStyle(Paint.Style.STROKE);
        
        tPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        tPaint.setTextSize(mTextSize);
        tPaint.setTextAlign(Align.CENTER);
        tPaint.setColor(textcolor);
        
        
         aPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	     aPaint.setColor(textcolor);
	     aPaint.setTextSize(mTextSize);
	     aPaint.setTextAlign(Align.CENTER);
        
       
        
        arrowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        arrowPaint.setStrokeWidth(5);
        arrowPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        arrowPaint.setColor(arrowcolor);
        
        
        boundaryPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        boundaryPaint.setStrokeWidth(5);
        boundaryPaint.setStyle(Paint.Style.STROKE);
        boundaryPaint.setColor(boundarycolor);
        
        
		
        highlightPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        
        highlightPaint.setColor(highlightcolor);
        highlightPaint.setStyle(Paint.Style.STROKE);
        
        
        mLabelHeight =(int) Math.abs(tPaint.getFontMetrics().top);
        
        
        mLabelWidth = (int) Math.abs(tPaint.measureText("000000"));
       
	}
	
	
	
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh)
	{
		
		float xpad = (float)(getPaddingLeft()+ getPaddingRight());
		float ypad = (float)(getPaddingTop() +  getPaddingBottom());
		
		float coww =0.0f,cohh=0.0f,coll =0.0f,tww=0.0f,thh=0.0f;
		float axww=0.0f,axhh=0.0f;
		
		float bxww = (float) w-xpad;
		float bxhh = (float) h-ypad;
		
		
		init();
	
		boxbounds = new RectF(getPaddingLeft(),getPaddingTop(),
				getPaddingLeft()+bxww,getPaddingTop()+bxhh);
		
		
		coww = (float) w-xpad-2*mLabelWidth;
		coll = (float) h-ypad-2*mLabelHeight-
				(mLabelSeparation);

		
		
		float cxoffset = mLabelWidth+mLabelSeparation;
		
	
	
		diameter = Math.min(coww, coll);
	    coordbounds = new RectF(
	                0.0f,
	                0.0f,
	                diameter,
	                diameter);
	    
	    //coordbounds.offsetTo(getPaddingLeft()+ cxoffset, getPaddingTop());
	    
	    coordbounds.offsetTo(boxbounds.centerX()-(diameter/2), boxbounds.centerY()-(diameter/2));
	    
		
		
	   
		
		 
		 getXYTicks();
	}
	
	
	
	private void getXYTicks() {
		
		
		 
		
		 getNumericalStops();
		
		
	}

	
	public IntervalStats determineReqTicks(
			double GraphMax, double GraphMin)
	{

		IntervalStats stats = determineAvgSeparation();
		double avgsepH=0;
		
			avgsepH= niceNumeral(stats.xavgdiff);
			double min = (Math.floor(GraphMin/avgsepH)*avgsepH);
			double max = (Math.ceil(GraphMax/(avgsepH))*avgsepH);
			
			
			System.out.println("MIN" +min+"MAX"+max+"");
			
			
			double hrange = max -min;
			int hRTicks = (int)(hrange/niceNumeral(avgsepH));
			stats.hRticks = hRTicks;
			
			stats.hinterval=niceNumeral(hrange/(stats.hRticks));
			
			System.out.println("H INterval" + stats.hinterval+"HRTICKS"+(stats.hRticks)+"");
			
		
		
			return stats;
		
		
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
	
	public IntervalStats determineAvgSeparation()
	{
		

		
			
		double avgdiff =(series.getMaximum()-series.getMinimum())/series.numTicks;
		System.out.println("XAVG" + avgdiff);
		IntervalStats stats = new IntervalStats();
		stats.xavgdiff = avgdiff;
		
			
		return stats;
	}
	
	private void getNumericalStops() {
		
		
		
		IntervalStats stats = determineReqTicks(series.getMaximum(),series.getMinimum());
		
		
		hInterval = stats.hinterval;
		hRTicks = stats.hRticks;
		
		
		
		Data =computeMaxMinRange();
		
		System.out.println("GMIN" + Data.graphMin);
		System.out.println("GMAX" + Data.graphMax);
		System.out.println("HINT" + hInterval + "hRticks"+hRTicks);
		computeStopsNum(stats.hRticks,hstops,Data.graphMin,Data.graphMax,hInterval);
	
		
	
		/*Viewport = new RectF((float)-(Data.graphMax+hInterval),
				(float)-(Data.graphMax+hInterval),
				(float)(Data.graphMax+hInterval),(float)());*/
		
		AXIS_H_MIN = (float)(hstops.axisstops[0]);
		AXIS_H_MAX = (float)(hstops.axisstops[hstops.axisstops.length-1]+hInterval);
		
		
		
	}
	
	public void computeStopsNum(int length,AxisStopValues
			stops,double graphMin,double graphMax,double interval)
	{
		
		System.out.println("AXISLENGTH"+length);
		//float[] outstops = new float[(length+1)];
		double[] outstops = new double[length+1];
		
		 //outstops[0]=(float) -(graphMax+interval);
		 int a=0;
		 
		 
		 System.out.println("AXIS STOPS GMAX" + graphMax);
		 for(double x =graphMin; x<=(graphMax+0.5*interval);x=x+interval)
		 {
			
			outstops[a] = x;
			System.out.println("AXIS STOPS VAL" + outstops[a] + "A" + a);
			a++;
		 }
		 
		 
		 
		 stops.axisstops=outstops;
		 
		 for(int i=0;i<outstops.length;i++)
		 {
			 System.out.println("AXIS STOPS" + outstops[i]);
		 }
		//Includes the leftmost stop - i.e Viewport.left
		
		 
		 
		 
		
	}
	@Override 
	 public boolean onTouchEvent(MotionEvent event)
	 {
		 
		// mGestureDetector.onTouchEvent(event);
		mScaleGestureDetector.onTouchEvent(event);
		 return true;
	 }
		
	
	 @Override 
	 public void onDraw(Canvas canvas)
	 
	 {
		super.onDraw(canvas);
		
		canvas.save();
		
		canvas.translate(mPosX, mPosY);
	    canvas.scale(mScaleFactor, mScaleFactor);
	    System.out.println("DRAW");
	    
	    drawGrid(canvas);
	    drawData(canvas);
	    
	   
	    
	    
	    canvas.restore();
	 
	 }
	
	
	



	private void drawGrid(Canvas canvas) {
		
		
		angleInterval = (float)(270/series.numTicks);
		
		System.out.println("NT" + series.numTicks);
		
		angles = new float[series.numTicks+1];
		
		length = (float) ((diameter/2)*0.15);
		
		System.out.println("L" + length +"A"+angleInterval);
		
		
		float highlightStartAngle = getCentreV(series.getHighlightStart(),true);
		
		float highlightStopAngle = getCentreV(series.getHighlightStop(),true);
		
		
		System.out.println("HSTRTANG" + highlightStartAngle);
		System.out.println("HSTOPANG" + highlightStopAngle);
		
		float th1 = (float) (coordbounds.centerY() + ((diameter/2) * Math.sin((highlightStartAngle*Math.PI/180))));
		
		float th2 = (float) (coordbounds.centerY() + ((diameter/2) * Math.sin((highlightStopAngle * Math.PI/180))));
		
		System.out.println("TH1" +th1 +"TH2"+th2 +"CY" + coordbounds.centerY()+"CT"+coordbounds.top);
		
		highlightbounds = new RectF();
		//highlightbounds.set(coordbounds.centerX()-diameter/2,coordbounds.centerY()-diameter/2, coordbounds.centerX()+(diameter/2),coordbounds.centerY()+diameter/2);
		/*if(th1>=th2)
		//highlightbounds = new RectF(coordbounds.centerX()+(diameter/2-length/2),th2,coordbounds.centerX()+diameter/2,th1);
		else if(th1<th2)
			//highlightbounds = new RectF(coordbounds.centerX()+(diameter/2-length/2),th1,coordbounds.centerX()+diameter/2,th2);
*/			
			
		
		highlightPaint.setStrokeWidth((diameter/2-(diameter/2-length/2)));
		
		highlightbounds.set(coordbounds.centerX()-(diameter/2-length/2),coordbounds.centerY()-(diameter/2-length/2), 
				coordbounds.centerX()+(diameter/2-length/2),coordbounds.centerY()+(diameter/2-length/2));
		
		System.out.println(highlightbounds);
		
		//canvas.drawArc(highlightbounds,highlightStartAngle,Math.abs(highlightStopAngle-highlightStartAngle),false, highlightPaint);
		
		canvas.drawArc(highlightbounds,highlightStartAngle,
				(highlightStopAngle-highlightStartAngle),false, highlightPaint);
		
		List<Integer> exponent = new ArrayList<Integer>();
		
		min=0;
		for(int i=0;i<(series.numTicks+1);i++)
		{
			
			String[] outstring = formatfloat((float)hstops.axisstops[i],
					hInterval,tPaint,mLabelWidth);
			System.out.println("OUTSTRING" + outstring[0]+"HINTERVAL" + hInterval
					+"SERIES MIN" + series.getMinimum());
			
			int a=0;
			if(outstring[1]!=null)
			{
				exponent.add(Integer.parseInt(outstring[1]));
				System.out.println("EXP ADD" + exponent.get(a));
				a++;
			}
			
			
			
		}
		
		
		Integer [] exps = exponent.toArray(new Integer[exponent.size()]);
		Arrays.sort(exps);
		if(exps.length>0)
		 min = exps[0];
		
		
		
		for(int i=0;i<(series.numTicks+1);i++)
		{
			angles[i]= (float)((135+(i*angleInterval))*Math.PI/180);
			
			System.out.println("ANGLES" + angles[i] + "DEGREE"+ (angles[i]*180/Math.PI ));
			
			System.out.println("ANGLES" + ((270*Math.PI)/180 ) + "DEGREE"+ "270");
			
			float tx1 = (float) (coordbounds.centerX() + ((diameter/2-length/2) * Math.cos(angles[i])));
			float ty1 = (float) (coordbounds.centerY() + ((diameter/2-length/2) * Math.sin(angles[i])));
			
			
			float tx2 = (float) (coordbounds.centerX() + ((diameter/2) * Math.cos(angles[i])));
			float ty2 = (float) (coordbounds.centerY() + ((diameter/2) * Math.sin(angles[i])));
			
			
			//System.out.println("TX1" + tx1 + "TY1" + ty1+ "TX2" + tx2+ "TY2" + ty2);
			
			
			canvas.drawLine(tx1,ty1,tx2,ty2, gPaint);
			
			float tx3=0,ty3=0;
			if(angles[i]>=(90*(Math.PI/180))& angles[i]<(270*(Math.PI/180)))
					{
						tx3 = (float) (coordbounds.centerX() + 
								((diameter/2-length/2) * Math.cos(angles[i])))+mLabelSeparation;
						/*ty3 = (float) (coordbounds.centerY() + 
								((diameter/2-length/2) * Math.sin(angles[i])))+mLabelSeparation;*/
						
						ty3 = (float) (coordbounds.centerY() + 
								((diameter/2-length/2) * Math.sin(angles[i])));
					}
			
			
			
			if(angles[i]>=(240*(Math.PI/180)))
			{
					tx3 = (float) (coordbounds.centerX() + 
						((diameter/2-length/2) * Math.cos(angles[i])));
					ty3 = (float) (coordbounds.centerY() + 
						((diameter/2-length/2) * Math.sin(angles[i])))+mLabelSeparation;
			}
			
			
			if(angles[i]>(300*(Math.PI/180)))
			{
					tx3 = (float) (coordbounds.centerX() + 
						((diameter/2-length/2) * Math.cos(angles[i])))-mLabelSeparation;
					/*ty3 = (float) (coordbounds.centerY() + 
						((diameter/2-length/2) * Math.sin(angles[i])))+mLabelSeparation;*/
					
					ty3 = (float) (coordbounds.centerY() + 
							((diameter/2-length/2) * Math.sin(angles[i])));
			}
			
			System.out.println("HSTOP"+ hstops.axisstops[i]);
			
			double value = hstops.axisstops[i]/Math.pow(10,min);
			
			String outstring = formatfloatValue((float)value,
					hInterval,tPaint,mLabelWidth);
			canvas.drawText(outstring, tx3, ty3, 
					tPaint);
			
			
			
			/*if(i==0)
			{
				float tx3 = (float) (coordbounds.centerX() + 
						((diameter/2-length/2-2) * Math.cos(135)));
				float ty3 = (float) (coordbounds.centerY() + 
						((diameter/2-length/2-2) * Math.sin(135)));
				
				String[] outstring = formatfloat((float)series.getMinimum(),
						hInterval,tPaint,mLabelWidth);
				System.out.println("OUTSTRING" + outstring[0]+"HINTERVAL" + hInterval
						+"SERIES MIN" + series.getMinimum());
				
				canvas.drawText(outstring[0], tx3, ty3, 
						tPaint);
			}
			
			if(i==series.numTicks-1)
			{
				float tx4 = (float) (coordbounds.centerX() + 
						((diameter/2-length/2-2) * Math.cos(angles[series.numTicks-1])));
				float ty4 = (float) (coordbounds.centerY() + 
						((diameter/2-length/2-2) * Math.sin(angles[series.numTicks-1])));
				
				String[] outstring = formatfloat((float)series.getMaximum(),hInterval,tPaint,mLabelWidth);
				canvas.drawText(outstring[0], tx4, ty4, 
						tPaint);
			}*/
			
			
		
			
			
		
			
			
			
		}
		
		
		
		/*canvas.drawText(getHLabelName(),coordbounds.left+diameter/2,
				coordbounds.bottom , tPaint);*/
		
		canvas.drawText(getSeriesName(),coordbounds.left+diameter/2,
				coordbounds.bottom, tPaint);
		
		
		
		
		if(min!=0)
		{
			canvas.drawText("x10", coordbounds.centerX(),
					coordbounds.centerY()+3*mLabelHeight, tPaint);
			canvas.drawText(Integer.toString(min), coordbounds.centerX()+tPaint.measureText("x10"),
				coordbounds.centerY()+3*mLabelHeight+aPaint.ascent(), tPaint);
		}
		
		
		
	}



	private String formatfloatValue(float value, double interval,
			Paint tPaint, int nLabelWidth) {
		
		String numstring ;
		int numsig=0;
		
		if(interval<1)
		{
			if(interval!=0)
			numsig =(int)Math.ceil(-Math.log10(interval));
			
		}
		
		if(interval>1)
		{
			if(interval!=0)
			numsig =(int)Math.ceil(Math.log10(interval));
			
		}
//		if(interval<1)
//			
//			numsig =(int)Math.ceil(-Math.log10(interval));
		
		 String append = "#.";
		 
		 String nodec = "#";
		 
		 String negdec = "##.";
		 
		 for(int i=0;i<numsig;i++)
			 
		 {
			 System.out.println("Calculating Append"+ append);
			 
			 append+="#";
			 
		 }
		 
		 if(value<0)
		 {
			 for(int i=0;i<numsig;i++)
				 
			 {
				 System.out.println("Calculating Append"+ append);
				 
				 negdec+="#";
				 
			 }
		 }
		 System.out.println("APPEND"+ append);
		 
		 System.out.println("FLOATVAL" + value);
		 //TODO Find an alternative toDecimalFormat
		 if(value%1>0)
			 numstring = new DecimalFormat(append).format(value);
		 else if(value%1<0)
		 {
			 numstring = new DecimalFormat(negdec).format(value);
			 
		 }
		 else
			 numstring = new DecimalFormat(nodec).format(value);
		
		return numstring;
	}



	private float getCentreV(double value,Boolean degree)
	{
		float pos =0;
		/*float pos = (float) (angles[0]+(270*(Math.PI/180))*
				(float)(value-Viewport.left)/(Viewport.width()));*/
		if(degree)
		{
		pos= (float) (135+(270 *(float)(value-Data.graphMin)/(Data.graphMax-Data.graphMin)));
		}
		else
		pos= (float) (angles[0]+(270*(Math.PI/180))*
				(float)(value-Data.graphMin)/(Data.graphMax-Data.graphMin));
			
			return pos;
	}
	
	private String[] formatfloat(float floatvalue, double interval, 
			Paint tPaint,int nLabelWidth) {
		
		String numstring ;
		int numsig=0;
//		if(interval<1)
//			
//			numsig =(int)Math.ceil(-Math.log10(interval));
		
		if(interval<1)
		{
			if(interval!=0)
			numsig =(int)Math.ceil(-Math.log10(interval));
			
		}
		
		if(interval>1)
		{
			if(interval!=0)
			numsig =(int)Math.ceil(Math.log10(interval));
			
		}
		
		 String append = "#.";
		 
		 String nodec = "#";
		 
		 for(int i=0;i<numsig;i++)
			 
		 {
			 System.out.println("Calculating Append"+ append);
			 
			 append+="#";
			 
		 }
		 
		 System.out.println("APPEND"+ append);
		 
		 System.out.println("FLOATVAL" + floatvalue);
		 //TODO Find an alternative toDecimalFormat
		 if(floatvalue%1>0)
			 numstring = new DecimalFormat(append).format(floatvalue);
		 else
			 numstring = new DecimalFormat(nodec).format(floatvalue);
		 
		 
		 System.out.println("NUMSTR" + numstring);
		  float minLength = tPaint.measureText("0");
		 
		    int numchars = (int) Math.floor(nLabelWidth/minLength);
		    
		    int trimsig =0;
		    int exp=0;
		    
		    System.out.println("LABELWIDTH" + nLabelWidth);
		    System.out.println("NUMCHARS" + numchars);
			if(numstring.length()>=numchars)
			{
				   if(floatvalue!=0)
					exp =(int) Math.floor(Math.log10
							(floatvalue));
					else
					exp=0;
				//exp =(int) Math.floor(Math.log10(floatvalue));
				float value  = (float)(floatvalue
						/Math.pow(10,exp));
				
				System.out.println("VAL"+value+"FL"+floatvalue +"EXP" + exp);
				float sigint = (float) (interval/Math.pow(10,exp));
				if(sigint<1)
				{
					if(sigint!=0)
					trimsig =(int)Math.ceil(-Math.log10(sigint));
					
					
				}
				
				if(sigint>1)
				{
					if(sigint!=0)
					trimsig =(int)Math.ceil(Math.log10(sigint));
					
					
				}
				
				String pattern = "#.";
				 for(int i=0; i<trimsig;i++)
					 pattern+="#";
				numstring = new DecimalFormat(pattern).format(value);
				
				
			}
		
		String [] strings = new String[2];
		strings[0]=numstring;
		System.out.println("EXP" + exp);
		if(exp!=0)
		{
			strings [1] = Integer.toString(exp);
			System.out.println("EXP NUM" + exp);
		}
		return strings;
		
		
	}
	
	
	

	private void drawData(Canvas canvas) {
		
		
		float angle = getCentreV(series.getValue(),false);
		
		
		float tx1 = (float) (coordbounds.centerX() + ((diameter/2-2) * Math.cos(angle)));
		float ty1 = (float) (coordbounds.centerY() + ((diameter/2-2) * Math.sin(angle)));
		
		
		canvas.drawLine(coordbounds.centerX(),
				coordbounds.centerY(),tx1,ty1, arrowPaint);
		
		
		RectF boundarybox = new RectF();
		
		boundarybox.set(coordbounds.centerX()-(diameter/2),
				coordbounds.centerY()-(diameter/2), coordbounds.centerX()+(diameter/2),
				coordbounds.centerY()+(diameter/2));
		
		canvas.drawArc(boundarybox,135,270,false, boundaryPaint);
		
		
		/*float tx4 = (float) (coordbounds.centerX() + 
				((diameter/2-length/2-2) * Math.cos(90)));
		float ty4 = (float) (coordbounds.centerY() + 
				((diameter/2-length/2-2) * Math.sin(90)));
		
		String [] outstring =formatfloat((float)series.getMinimum(),hInterval,tPaint,mLabelWidth);
		canvas.drawText(outstring[0],tx4,ty4, tPaint);*/
		
		canvas.drawText(Double.toString(series.getValue()/Math.pow(10,min)), coordbounds.centerX(),coordbounds.centerY()+mLabelHeight, tPaint);
		
		
	}

	
	
	public MinMax computeMaxMinRange()
	{
		
		
		/*MinMax  values = new MinMax();
		double graphMin = (float)niceNumeral(series.getMinimum());
		double graphMax = (float)niceNumeral(series.getMaximum());*/
		
		
		MinMax  values = new MinMax();
		double graphMin = Math.floor(series.getMinimum()/hInterval)*hInterval;
		double graphMax = Math.ceil(series.getMaximum()/hInterval)*hInterval;
		
		
		
		values.graphMax=graphMax;
		values.graphMin=graphMin;
		
		
		
		return values;
	}

	 private final ScaleGestureDetector.OnScaleGestureListener mScaleGestureListener
	  = new ScaleGestureDetector.SimpleOnScaleGestureListener()
	 {
		 
		 
		 @Override 
		 public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector)
		 {
			 
			return true;
			 
		 }
		 
		 @Override
		 public boolean onScale(ScaleGestureDetector scaleGestureDetector)
		 {
			 
			    mScaleFactor *=  mScaleGestureDetector.getScaleFactor();

		        // Don't let the object get too small or too large.
			    
			    mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 5.0f));
			    
			    if (mScaleFactor < 5f) {
			    	   // 1 Grabbing
			    	   final float centerX = mScaleGestureDetector.getFocusX();
			    	   final float centerY = mScaleGestureDetector.getFocusY();
			    	   // 2 Calculating difference
			    	   diffX = centerX - mPosX;
			    	   diffY = centerY - mPosY;
			    	   // 3 Scaling difference
			    	   diffX = diffX * mScaleGestureDetector.getScaleFactor() - diffX;
			    	   diffY = diffY * mScaleGestureDetector.getScaleFactor() - diffY;
			    	   // 4 Updating image origin
			    	   mPosX -= diffX;
			    	   mPosY -= diffY;
			    	}

		       invalidate();
		        
		        return true;
		 }
	 };

	

}
