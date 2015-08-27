package com.wechseltech.chartlibrary;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.widget.EdgeEffectCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.Toast;

public class SpiderWeb extends ChartView{

	//TODO On Scale Not working grid doesn't move...
	//Leftmost label inside the grid...
	
	float axisLabelSpace;
	
	int mLabelWidth,mLabelHeight;
	float diameter;
	Context context;
	Map<String,PointValues[]> seriesvals;
	Path []vertexPath;
	List<CategoryItem> series;
	int numseries;
	String[] unique;
	float [] AxisGridLines;
	//double[] angles;
	int hRTicks;
	
	int ticks;
	double hInterval;
	RectF Viewport,coordbounds
	,boxbounds,legendBox;
	boolean invalidate = false;
	Paint gPaint,lPaint,
	seriesPaint,pointPaint,tPaint,ltPaint,aPaint;
	
	int min =0;
	private float mScaleFactor = 1.f;
	
	float mPosX,mPosY,diffX=0,diffY=0;
	
	public Point ScrollableSurface = new Point();
	AxisStops hstops,vstops;
	
	float AXIS_H_MAX,
	AXIS_H_MIN,
	AXIS_V_MAX,AXIS_V_MIN;
	
	float xpoint,ypoint;
	
	int drawBoxSize;
	
	
	
	Map<String,Integer> numinseries;
	Map<String,Integer>seriescolor;
	
	Map<String,Double> angles;
	
	String [] seriesname;
	
	 Path gridPath,seriesPath;
	 List<Integer> exponent;
	
	 public boolean mEdgeEffectTopActive;
	 public boolean mEdgeEffectBottomActive;
	 public boolean mEdgeEffectLeftActive;
	 public boolean mEdgeEffectRightActive;
	 
	 
	 public EdgeEffectCompat mEdgeEffectTop;
	 public EdgeEffectCompat mEdgeEffectBottom;
	 public EdgeEffectCompat mEdgeEffectLeft;
	 public EdgeEffectCompat mEdgeEffectRight;
	
	 
	 public ScaleGestureDetector mScaleGestureDetector=null;
	 public GestureDetectorCompat mGestureDetector=null;
	
	 PointValues[] values;
	
	 
	 public static boolean ASC = true;
	 public static boolean DESC = false;
	 
	 
	 
	 
	 
	 
	 
	 
		public SpiderWeb(Context context) {
			super(context);
			
			System.out.println("INIT C");
			series = new ArrayList<CategoryItem>();
			this.context=context;
			mScaleGestureDetector = new ScaleGestureDetector(context,mScaleGestureListener);
			//mGestureDetector = new GestureDetectorCompat(context,mGestureListener);
			
			mEdgeEffectTop = new EdgeEffectCompat(context);
			mEdgeEffectLeft = new EdgeEffectCompat(context);
			mEdgeEffectRight = new EdgeEffectCompat(context);
			mEdgeEffectBottom = new EdgeEffectCompat(context);
			
			
			
		
		}
		
		
		
		
		public SpiderWeb(Context context,AttributeSet attrs) {
			super(context,attrs);
			System.out.println("INIT At");
			this.context=context;
			series = new ArrayList<CategoryItem>();
			
			mScaleGestureDetector = new ScaleGestureDetector(context,mScaleGestureListener);
			//mGestureDetector = new GestureDetectorCompat(context,mGestureListener);
			
			mEdgeEffectTop = new EdgeEffectCompat(context);
			mEdgeEffectLeft = new EdgeEffectCompat(context);
			mEdgeEffectRight = new EdgeEffectCompat(context);
			mEdgeEffectBottom = new EdgeEffectCompat(context);
			
			TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.Charts,0,0);
			
			try
			{
				
				
				mLabelSeparation = a.getDimension(R.styleable.Charts_labelSeparation, 8);
				mTextSize = a.getDimension(R.styleable.Charts_textSize, 14);
				axisTextSize = a.getDimension(R.styleable.Charts_axisTextSize, 12);
				textcolor = a.getColor(R.styleable.Charts_textColor, Color.BLACK);
				legendSize= a.getDimension(R.styleable.Charts_legendBoxSize,30);
				drawSpace = a.getDimension(R.styleable.Charts_drawSpace, 5);
				gridcolor = a.getColor(R.styleable.Charts_gridColor, Color.BLACK);
				
				assignSeriesColors= a.getBoolean(R.styleable.Charts_assignSeriesColors, true);
				useSeriesPrimary= a.getBoolean(R.styleable.Charts_useSeriesPrimary, true);
				
				accentprimarycolor =a.getColor(R.styleable.Charts_accentprimaryColor, Color.RED);
				
				
				
				
			}
			
			finally
			{
				
				a.recycle();
			}
			
			
		}
		
		
		
		
		public SpiderWeb(Context context,AttributeSet attrs,int defStyle) {
			
			
			super(context,attrs,defStyle);
			System.out.println("INIT DF");
			this.context=context;
			series = new ArrayList<CategoryItem>();
			
			mScaleGestureDetector = new ScaleGestureDetector(context,mScaleGestureListener);
			//mGestureDetector = new GestureDetectorCompat(context,mGestureListener);
			
			mEdgeEffectTop = new EdgeEffectCompat(context);
			mEdgeEffectLeft = new EdgeEffectCompat(context);
			mEdgeEffectRight = new EdgeEffectCompat(context);
			mEdgeEffectBottom = new EdgeEffectCompat(context);
			
			
			TypedArray a = context.getTheme().
					obtainStyledAttributes(attrs,R.styleable.Charts,defStyle,defStyle);
			
			try
			{
				
				mLabelSeparation = a.getDimension(R.styleable.Charts_labelSeparation, 8);
				mTextSize = a.getDimension(R.styleable.Charts_textSize, 14);
				textcolor = a.getColor(R.styleable.Charts_textColor, Color.BLACK);
				axisTextSize = a.getDimension(R.styleable.Charts_axisTextSize, 12);
				legendSize= a.getDimension(R.styleable.Charts_legendBoxSize, 30);
				drawSpace = a.getDimension(R.styleable.Charts_drawSpace, 5);
				gridcolor = a.getColor(R.styleable.Charts_gridColor, Color.GRAY);
			
				assignSeriesColors= a.getBoolean(R.styleable.Charts_assignSeriesColors, true);
				useSeriesPrimary= a.getBoolean(R.styleable.Charts_useSeriesPrimary, true);
				
				accentprimarycolor =a.getColor(R.styleable.Charts_accentprimaryColor, Color.RED);
				
				
				
			}
			
			finally
			{
				
				a.recycle();
			}
			
			
			
	}
	
	 
	 
	
	
	
	
	
	private void init()
	{
		 hstops  = new AxisStops();
		 
		 gridPath = new Path();
		 
		 seriesPath = new Path();
		
		 tPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	     tPaint.setColor(textcolor);
	     tPaint.setTextSize(mTextSize);
	     //tPaint.setTextAlign(Paint.Align.CENTER);
	     
	     aPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	     aPaint.setColor(textcolor);
	     aPaint.setTextSize(axisTextSize);
	     aPaint.setTextAlign(Paint.Align.LEFT);
	     
	     
	     ltPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	     ltPaint.setColor(textcolor);
	     ltPaint.setTextSize(mTextSize);
	     ltPaint.setTextAlign(Paint.Align.LEFT);
	     
	    
	     
	     
	     gPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	     gPaint.setStyle(Paint.Style.STROKE);
	     gPaint.setColor(Color.BLACK);
	     
	     
	     
	     lPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	     lPaint.setStyle(Paint.Style.FILL_AND_STROKE);
	     
	     
	     seriesPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	     seriesPaint.setStyle(Paint.Style.STROKE);
	    		 
	     pointPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	     pointPaint.setStyle(Paint.Style.STROKE);
	     pointPaint.setStrokeWidth(10);
	     pointPaint.setStrokeCap(Cap.ROUND);
	     
	     
	     mLabelHeight =(int) Math.abs(tPaint.getFontMetrics().top);
	        
	     drawBoxSize = mLabelHeight;
	        
	   
	     mLabelWidth = (int) Math.abs(tPaint.measureText("000000"));
	}
	
	
	
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh)
	{
		
		float xpad = (float)(getPaddingLeft()+ getPaddingRight());
		float ypad = (float)(getPaddingTop() +  getPaddingBottom());
		
		float coww =0.0f,cohh=0.0f,coll =0.0f;
		
		init();
		
		coww = (float) w-xpad-2*mLabelWidth-2*mLabelSeparation;
		cohh = (float) h-ypad-3*mLabelHeight-(legendSize+2*mLabelSeparation)-
				mLabelHeight-2*mLabelSeparation;
				

		float bxww = (float) w-xpad;
		float bxhh = (float) h-ypad;
		
		
		boxbounds = new RectF(getPaddingLeft(),getPaddingTop(),
				getPaddingLeft()+bxww,getPaddingTop()+bxhh);
		
		//coordbounds = new RectF(0.0f,0.0f,
				//coww,cohh);
		
		//coll = bxhh-legendSize-mLabelSeparation;
		
		 diameter = Math.min(coww, cohh);
	     
		 coordbounds = new RectF(
	                0.0f,
	                0.0f,
	                diameter,
	                diameter);
	     float xoffset = mLabelWidth+mLabelSeparation;
	     
	     float toffset = mLabelHeight+mLabelSeparation;
	    
	     
	     
	     //coordbounds.offsetTo(getPaddingLeft()+ xoffset, getPaddingTop()+toffset);
	    // coordbounds.offsetTo(getPaddingLeft(), getPaddingTop());
	     
	      coordbounds.offsetTo(boxbounds.centerX()-(diameter/2), boxbounds.centerY()-(diameter/2));   
	     
	     
	      legendBox = new RectF(0.0f,0.0f,coww,legendSize );
	      legendBox.offset(getPaddingLeft()+xoffset,coordbounds.bottom+toffset);
	   	 
	      assignColors();
	   	 	getXYTicks();
	   	 
	   	 
		
		
	}
	
	 private void getXYTicks() {
		
		
		 getCategory();
		 float verticesoffset = 360/unique.length;
		 
		 System.out.println("VErteX" + verticesoffset);
		 
		 for(int i=0;i<unique.length;i++)
		 {
			 System.out.println("ANGLE DEG" +(i*verticesoffset));
			// angles[i]= ((i*verticesoffset)* Math.PI/180);
			 
			 angles.put(unique[i],((i*verticesoffset)* Math.PI/180));
			 
			 //System.out.println("ANGLES" +angles[i]);
		 }
		 
		 
		this.angles=sortByComparator(angles,ASC);
		
		
		
		 String [] keys = angles.keySet().toArray(new String[angles.size()]);
		 
		 
		 int a=0;
		 
		 for(int i=0;i<angles.size();i++)
		 {
			 System.out.println("MAP" + keys[i] + angles.get(keys[i]));
			 
			 
			 for(int j=0;j<series.size();j++)
			 {
				 if(series.get(j).getCategory().equals(keys[i]))
					 
				 {
					 series.get(j).setOrder(a);
					 
					 System.out.println("SOMAP" + keys[i] + series.get(j).getOrder());
					 
					 
				 }
				 
				 
			 }
			 
			 a++;
			 
		 }
		 
		 
		 
		 double [] val = new double[series.size()];
		 for(int i=0;i<series.size();i++)
		 {
			 val[i]= series.get(i).val;
			 
			 
		 }
		 
		
		 getNumericalStops(val);
	}


	private void getNumericalStops(double[]val) {
		
		Arrays.sort(val);
		ComputeStops cmp = new ComputeStops();
		IntervalStats stats = determineReqTicks
				( val[val.length-1],val[0],val);
		
		
		System.out.println("VAL MAX" + val[val.length-1]);
		System.out.println("VAL MIN" + val[0]);
		hInterval = stats.hinterval;
		hRTicks = stats.hRticks;
		
		
		MinMax Data =cmp.computeMaxMinRangeSpider(val,hInterval,stats.hRticks);
		
		computeStopsNum(stats.hRticks,hstops,Data.graphMin,Data.graphMax,hInterval);
	
		
	
		Viewport = new RectF((float)-(Data.graphMax),
				(float)-(Data.graphMax),
				(float)(Data.graphMax),(float)(Data.graphMax));
		
		AXIS_H_MIN = (float)-(hstops.axisstops[hstops.axisstops.length-1]+hInterval);
		AXIS_H_MAX = (float)(hstops.axisstops[hstops.axisstops.length-1]+hInterval);
		
		AXIS_V_MIN = (float)-(hstops.axisstops[hstops.axisstops.length-1]+hInterval);
		AXIS_V_MAX = (float)(hstops.axisstops[hstops.axisstops.length-1]+hInterval);
		
	}

	public void computeStopsNum(int length,AxisStops stops,double graphMin,double graphMax,double interval)
	{
		
		
		
		System.out.println("Length" + length);
		double[] outstops = new double[2*(length+1)];
		
		
		 //outstops[0]=(float) -(graphMax+interval);
		 int a=0;
		 
		 System.out.println("STRT" + (graphMax+(0.5*interval)));
		 System.out.println("STOP" + (graphMin));
		 System.out.println("INT" + (interval));
		 for(double x = (graphMin); x<=(graphMax+(0.5*interval));x=x+interval)
		 {
			
			outstops[a] = (float)(-x);
			//System.out.println("AXIS STOPS LESS"+ outstops[a]);
			
			a++;
		 }
		 
		 
		 
		 //outstops[a]=(float)(graphMin-interval);
		 
		 
		 Arrays.sort(outstops);
		 
		 
		 for(double x =graphMin; x<=(graphMax + (0.5*interval));x=x+interval)
		 {
			
			outstops[a] = (float)x;
			//System.out.println("AXIS STOPS PLUS"+ outstops[a]);
			a++;
		 }
		 
		 stops.axisstops=outstops;
		//Includes the leftmost stop - i.e Viewport.left
		
		 
		 for(int x =0; x<outstops.length;x++)
		 {
			 System.out.println("AXIS STOPS"+ outstops[x]);
		 
		 }
		 
		
	}

	@Override 
	 public void onDraw(Canvas canvas)
	 
	 {
		super.onDraw(canvas);
		
		
		canvas.save();
		
		//canvas.clipRect(coordbounds);
		
		canvas.translate(mPosX, mPosY);
	    canvas.scale(mScaleFactor, mScaleFactor);
	   
	    drawGrid(canvas);
	    drawData(canvas);
	    
	    
	    drawText(canvas);
		
	    if(!invalidate)
			drawLegend(canvas);
			else
			drawInvalidateLegend(canvas);
		
	   // drawLegend(canvas,seriesname,seriescolor,mLabelWidth);
	    
	    canvas.restore();
	    
	    
	   
	 }
	 
	
	
	 
	 
	 
	 private void drawText(Canvas canvas) {
		
		 
		 tPaint.setTextAlign(Paint.Align.CENTER);
		 canvas.drawText(textdesc,coordbounds.centerX(),boxbounds.top+2*mLabelSeparation, tPaint);
		 
		 //Draw Category
		 String[] labels = angles.keySet().toArray(new String[angles.size()]);
		 for(int i=0; i<angles.size();i++)
		 {
			 //drawAngle(canvas,angles[i],unique[i]);
			drawAngle(canvas,angles.get(labels[i]),labels[i]);
		 }
		 
		 drawNumbers(canvas,hstops.axisstops.length/2);
		 
		 
		 //Draw Numbers
		 
		 
		
	}

	 
	 
	private void drawNumbers(Canvas canvas,int nstops) {
		
		double radii = (diameter/2)/(nstops-1);
		
		exponent = new ArrayList<Integer>();
		
		
		
		for(int i=1; i< nstops-1;i++ )
		{	
			 
			 
			 
			 CalculateExp(nstops+i,radii);

			  
		 }
		
		Integer [] exps = exponent.toArray(new Integer[exponent.size()]);
		Arrays.sort(exps);
		if(exps.length>0)
		 min = exps[0];
		
		String [] keys = angles.keySet().toArray(new String[angles.size()]);
		
		for(int i=1; i<nstops-1;i++ )
		{	
			 float x = (float) (coordbounds.centerX()+ (radii*i)*Math.cos(angles.get(keys[0])));
			 float y = (float) (coordbounds.centerY()+ (radii*i)*Math.sin(angles.get(keys[0])));
			 
			 
			 DrawTextNum(canvas,x,y,nstops+i,radii);

			  
		 }
		
		if(min!=0)
		{
			aPaint.setTextAlign(Paint.Align.CENTER);
			canvas.drawText("x10", coordbounds.centerX(),
					coordbounds.centerY(), aPaint);
			canvas.drawText(Integer.toString(min), coordbounds.centerX()+(aPaint.measureText("x10")/2),
				coordbounds.centerY()+aPaint.ascent(), aPaint);
		}
		
	
	}

	
	
	public void DrawTextNum(Canvas canvas,float x,float y,int pos,double radii)
	{
		
	
		 	
		 	
		   /* tPaint.setTextAlign(Paint.Align.LEFT);
		 	String[] outstring = formatfloat((float)hstops.axisstops[pos],hInterval,aPaint,mLabelWidth);
		 	

		 		if(x>=coordbounds.left & y<=coordbounds.bottom)
		 		{
 
					 canvas.drawText(outstring[0],0,outstring[0].length(), 
							 x,y, aPaint);

				 }
				 */
				 
		float value = (float) (hstops.axisstops[pos]/Math.pow(10,min));
	 	
		System.out.println("VAL" + value +"MIN" + min);
		String outstring = formatfloatValue(value,
				hInterval,aPaint,radii);
		System.out.println("OUTSTRING" + outstring);
		
		if(x>=coordbounds.left & y<=coordbounds.bottom)
		{
			System.out.println(hstops.axisstops[pos]);
			 aPaint.setTextAlign(Paint.Align.LEFT);
			canvas.drawText(outstring,0,outstring.length(), 
							 x,y, aPaint);

		}	 
				 

				
			
		 
		 
	}

	private void CalculateExp(int i, double radii) {
		
		String[] outstring = formatfloat((float)hstops.axisstops[i],
				hInterval,tPaint,radii);
		
		
		if(outstring[1]!=null)
		{
			exponent.add(Integer.parseInt(outstring[1]));
			
		}
		
	}

	

	private void drawAngle(Canvas canvas,double d,String label) {
		
		
		
       
        float x =0.0f;
		
        System.out.println("D" +d);
        
         double angle =  (d*180)/Math.PI;
        
		 if(angle==0)
         {
			
			tPaint.setTextAlign(Paint.Align.LEFT);
         	x = (float) (coordbounds.centerX()+(coordbounds.width()/2 * Math.cos(d)))+
         			mLabelSeparation;
         	float y = (float) (coordbounds.centerY()+(coordbounds.height()/2)* Math.sin(d));
         	System.out.println("x"+x+"y" +y+label);
         	System.out.println("is0");
         	canvas.drawText(label,x,y, tPaint);
         	
         }
		 
		 else if(angle!=0 && angle<90)
         {
			tPaint.setTextAlign(Paint.Align.LEFT);
         	x = (float) (coordbounds.centerX()+(coordbounds.width()/2 * Math.cos(d)))+
         			mLabelSeparation;
         	float y = (float) (coordbounds.centerY()+(coordbounds.height()/2)* Math.sin(d));
         	System.out.println("x"+x+"y" +y+label);
         	System.out.println("is<90");
         	canvas.drawText(label,x,y, tPaint);
         	
         }
		 
         else if(angle>90 && angle<180)
         {
        	 
        	tPaint.setTextAlign(Paint.Align.RIGHT);
         	
        	x = (float) (coordbounds.centerX()+(coordbounds.width()/2 * Math.cos(d)))-mLabelSeparation;
         	
         	float y = (float) (coordbounds.centerY()+(coordbounds.height()/2)* Math.sin(d))+mLabelSeparation;
         	System.out.println("x"+x+"y" +y+label);
         	System.out.println("is>90" + coordbounds.left);
         	canvas.drawText(label,x,y, tPaint);
         }
		 
         else if(angle==90)
         {
        	 
        	tPaint.setTextAlign(Paint.Align.CENTER);
        	
         	
        	x = (float) (coordbounds.centerX()+(coordbounds.width()/2 * Math.cos(d)));
         	
         	float y = (float) (coordbounds.centerY()+(coordbounds.height()/2)* Math.sin(d))+
         			mLabelSeparation;
         	System.out.println("x"+x+"y" +y+label);
         	System.out.println("is>90" + coordbounds.left);
         	canvas.drawText(label,x,y, tPaint);
         }
         
         else if(angle>=180 && angle<270)
         {
        	tPaint.setTextAlign(Paint.Align.RIGHT);
         	x = (float) (coordbounds.centerX()+(coordbounds.width()/2 * Math.cos(d)));
         	float y = (float) (coordbounds.centerY()+(coordbounds.height()/2)* Math.sin(d))-
         			mLabelSeparation;
         	System.out.println("CLx"+x+"y" +y+label);
         	System.out.println("CL" + coordbounds.left);
         	System.out.println("is>180" + coordbounds.left);
         	
         	canvas.drawText(label,x,y, tPaint);
         }
		 
         else if(angle==180)
         {
        	tPaint.setTextAlign(Paint.Align.RIGHT);
         	x = (float) (coordbounds.centerX()+(coordbounds.width()/2 * Math.cos(d)))-mLabelSeparation;
         	float y = (float) (coordbounds.centerY()+(coordbounds.height()/2)* Math.sin(d))
         			;
         	System.out.println("CLx"+x+"y" +y+label);
         	System.out.println("CL" + coordbounds.left);
         	System.out.println("is>180" + coordbounds.left);
         	
         	canvas.drawText(label,x,y, tPaint);
         }
		 
         else if(angle==270)
         {
        	tPaint.setTextAlign(Paint.Align.CENTER);
         	x = (float) (coordbounds.centerX()+(coordbounds.width()/2 * Math.cos(d)));
         	float y = (float) (coordbounds.centerY()+(coordbounds.height()/2)* Math.sin(d))-
         			mLabelSeparation;
         	System.out.println("CLx"+x+"y" +y+label);
         	System.out.println("CL" + coordbounds.left);
         	System.out.println("is>180" + coordbounds.left);
         	
         	canvas.drawText(label,x,y, tPaint);
         }
		 
         
         
         else if(angle>270 && angle<=360)
         {
        	tPaint.setTextAlign(Paint.Align.LEFT);
         	x = (float) (coordbounds.centerX()+(coordbounds.width()/2 * Math.cos(d)))+
         			mLabelSeparation;
         	float y = (float) (coordbounds.centerY()+
         			(coordbounds.height()/2)* Math.sin(d))-
         			mLabelSeparation;
         	System.out.println("x"+x+"y" +y+label);
         	System.out.println("is>270" + coordbounds.left);
         	
         	canvas.drawText(label,x,y, tPaint);
         }
		
	}




	private void drawGrid(Canvas canvas) {
		 
		  int nstops = hstops.axisstops.length/2;
		  
		  System.out.println("NSTOPS" + nstops);
		  
		  
		  double radii = (diameter/2)/(nstops-1);
		  

		  String [] keys = angles.keySet().toArray(new String[angles.size()]);
		  
		  
		  
		  for(int i=0; i<nstops;i++ )
		  {
		  float startX=0,startY=0;
		  System.out.println("RADII" + radii);
		  float [] vertices = new float[angles.size()*2];
		  
		  
			  for(int j=0;j<angles.size();j++)
		  {
				  float x = (float) (coordbounds.centerX()+ (radii*i)*Math.cos(angles.get(keys[j])));
				  float y = (float) (coordbounds.centerY()+  (radii*i)*Math.sin(angles.get(keys[j])));
		  
				  	vertices[j]=x;
				  	vertices[j+1]=y;
////				    
////				    System.out.println("X" + x + "Y" +y);
////				  
				  		if(j!=0)
				  		{
				  			gridPath.lineTo(x, y);
				  			//gridPath.moveTo(x, y);
				  		}
				  		if(j==0)
				  		{
				  			gridPath.moveTo(x, y);
				  			startX=x;
				  			startY=y;
				  		}
////				    
					  	if(j==angles.size()-1)
			  		    {
					    	gridPath.lineTo(x, y);
					    	//gridPath.moveTo(x, y);
					    	gridPath.lineTo(startX, startY);
						}
////				  
////				  
				}
////			 
		  		gridPath.close();
		  		gPaint.setStrokeWidth(3);
//		  		canvas.drawPath(gridPath, gPaint);
		  		canvas.drawPath(gridPath,gPaint);
		  		gridPath.reset();
		  
////			  //canvas.drawVertices(VertexMode.TRIANGLES,
////					 // vertices.length, vertices,0,null,0,null,0,null,0,0, gPaint);
		  }
		 
	}

	
		
	private float getCentreV(double value)
	{
			//float startradii = (diameter/2)/(hstops.axisstops.length/2);
			
			//System.out.println("ST" + startradii);
			float pos =((coordbounds.width()/2))*
			(float)(value-Viewport.centerX())/(Viewport.width()/2);
			
			System.out.println("CW"+coordbounds.width()+"VW" + Viewport.width()+ "CT"+ coordbounds.centerX()
					+"CY" + coordbounds.centerY()+"CH"+coordbounds.height());
			
			return pos;
	}

	private void drawData(Canvas canvas)
	 {
		float startradii = (diameter/2)/(hstops.axisstops.length/2);
		
		
		seriesvals = new HashMap<String, PointValues[]>();
		 
		String [] keys = angles.keySet().toArray(new String[angles.size()]);
		for(int n=1; n<=numseries;n++)
		{
			int a=0;
		 
			int num = numinseries.get(seriesname[n-1]);
			
			float StartX=0.0f,StartY=0.0f;
			
			values = new PointValues[num];
			
			System.out.println("NUM"+num);
			
			float [] vertices = new float[num];
			
			for(int m=0; m<series.size();m++)
		 
		 
			{
				
				if(series.get(m).getSeriesName().equals(seriesname[n-1]))
				{
					double angle=0.0f;
					float radii=0.0f;
					int pos=0;
					String unq = null;
					for(int i=0;i<angles.size();i++)
					{
						
						 if(series.get(m).getCategory().equals(keys[i]))
						 {
							angle =  angles.get(keys[i]); 
							unq=keys[i];
							pos = series.get(m).getOrder();
							 
						 }
						 
						 
						
					 
					 
					}
				 
				
			     System.out.println("ANGLE"+ unq+ angles.get(unq));
				 radii = getCentreV(series.get(m).getValue());
			
				 System.out.println("RADII" + radii+"ANGLE" + angle);
				 
				 System.out.println("VP Center" + Viewport.centerX());
				 
				 
				 int serie = series.get(m).getSeries();
				 
				 float x = (float) (coordbounds.centerX()+ (radii* Math.cos(angle)));
				 float y = (float) (coordbounds.centerY()+ (radii* Math.sin(angle)));
				 
				 
				 System.out.println("UNIQUE" + unq);
				 
				 
				 System.out.println("Diameter" + diameter+"radii"+radii+series.get(m).getValue());
				
				 values[pos] = new PointValues();
				 values[pos].x=x;
				 values[pos].y=y;
				 values[pos].value=series.get(m).getValue();
				 
				 System.out.println("VAL" +values[pos].x + values[pos].y + "POS" + pos);
				
				 	
				 
				 pointPaint.setColor(series.get(m).getSeriesColor());
				 canvas.drawPoint(x, y, pointPaint);
					
				 a++;
				}
				
				
				
				
				
				 
		 }
			
			for(int s=0;s<values.length;s++)
			{
				
				System.out.println("VL" + values.length);
				
				 if(s==0)
				 {
					 seriesPath.moveTo(values[s].x, values[s].y);
					 StartX=values[s].x;
					 StartY=values[s].y;
				 }
				 else if(s==values.length-1)
				 {
					 seriesPath.lineTo(values[s].x, values[s].y);
					 seriesPath.lineTo(StartX, StartY);
				 }
				 else
				 {
					 seriesPath.lineTo(values[s].x, values[s].y);
					 
				 }
				 
			}
			
			 	seriesPaint.setColor(seriescolor.get(seriesname[n-1]));
			 
			 	seriesPath.close();
		  		seriesPaint.setStrokeWidth(3);
//		  		canvas.drawPath(gridPath, gPaint);
		  		canvas.drawPath(seriesPath,seriesPaint);
		  		seriesPath.reset();
	
		 
		  seriesvals.put(seriesname[n-1], values);
		 
		}
		 
	 }
	 
	 	public IntervalStats determineReqTicks(
				double GraphMax, double GraphMin,double[] val)
		{

			IntervalStats stats = determineAvgSeparation(val);
			double avgsepH=0;
			
				
				
				
				//double hrange = niceNumeral(GraphMax -GraphMin);
				avgsepH= niceNumeral(stats.xavgdiff);
				
				System.out.println(avgsepH);
				
				double graphMin = (float) (Math.floor(GraphMin/avgsepH)*avgsepH);
				double graphMax = (float)(Math.ceil(GraphMax/avgsepH)*avgsepH);
				
				
				System.out.println("GMX" + graphMin+"GMY" + graphMax);
				
				double hrange = graphMax -graphMin;
				
				
				System.out.println("AVGSEPH" + avgsepH + hrange);
				
				//int hRTicks = (int)(hrange/niceNumeral(avgsepH))+1;
				int hRTicks = (int)(hrange/avgsepH);
				
				System.out.println("Range" + hrange);
				
				//int hRticks = hRTicks;
				System.out.println("TICKS" + (hRTicks));
				stats.hRticks=hRTicks;
				//stats.hinterval=(int)Math.ceil(hrange/(stats.hRticks-1));
				stats.hinterval=niceNumeral(hrange/(stats.hRticks));
				System.out.println("H INterval" + stats.hinterval);
				
			
				
				
			
				return stats;
			
			
		}
	 	
	 	
	 	private double niceNumeral(double calrange)
		 
		 {
			 double nf;
			 int exp = (int)Math.floor(Math.log10(calrange));
			 double frac = (int)calrange/Math.pow(10,exp);
			 
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
	 
	 	private String[] getCategory()
		
	 	{
				 List<String> uni= new ArrayList<String>();
				 for(int i=0; i<series.size();i++)
				 {
						
					uni.add(series.get(i).getCategory());
				 }		
				Set<String> uyVal= new HashSet<String>(uni);
				unique = uyVal.toArray(new String[uyVal.size()]);
				
				
				System.out.println(unique.length);
				//angles = new double[unique.length];
				angles = new HashMap<String,Double>();
				
				return unique;
		}
	 
	 	
	 	public IntervalStats determineAvgSeparation(double [] val)
		{
			

			Arrays.sort(val);
			
			double sumdiff =0;
				
			for(int i=0; i< val.length-1;i++)
			{
				sumdiff = (sumdiff + Math.abs(val[i+1]-val[i]));
				
			}
				
			double avgdiff =sumdiff/(val.length-1);
			
			IntervalStats stats = new IntervalStats();
			stats.xavgdiff = avgdiff;
			
				
			return stats;
		}
	 	
	 	
	 	private void assignColors()
	 	{
	 		seriescolor = new HashMap<String,Integer>();
			if(numseries>=1)
			{
				getSeriesNames();
				if(assignSeriesColors)
					calculateSeriesColors(numseries,
						useSeriesPrimary,accentprimarycolor,seriescolor);
				else
					getSeriesColors();
			}
			
			
			numinseries = new HashMap<String,Integer>();
			
			for(int i=1; i<=numseries;i++)
			{
				int n=0;
				for(int m=0;m<series.size();m++)
				{
					if(series.get(m).getSeriesName()==seriesname[i-1])
					{
						n++;
						
					}
				}
				
				
				numinseries.put(seriesname[i-1],n);
				
			}
			
			int [] max = new int[numseries];
			for(int i=1; i<=numinseries.size();i++)
			{
				max[i-1]= numinseries.get(seriesname[i-1]);
			}
			
			Arrays.sort(max);
			
			values = new PointValues[max[max.length-1]];
	 	}
	 	
		
		
		public void setSeries(List<CategoryItem> series,int numseries)
		{
			
			this.series=series;
			this.numseries=numseries;
			
			
			
			
			
			
			
			
			
			
		}
		
		
		
		
		private void getSeriesColors() {
			
			
			for(int i=1;i<=numseries;i++)
			{
				for(int j=0;j<series.size();j++)
				{
					if(series.get(j).getSeriesName()==seriesname[i-1])
					seriescolor.put(seriesname[i-1], series.get(j).getSeriesColor());
				}
			}
			
		}




		private void getSeriesNames() {
			
			
			List<String> names = new ArrayList<String>();
			for(int i=0;i<series.size();i++)
			{
				if(series.get(i).getSeriesName()!=null)
					names.add(series.get(i).getSeriesName());
			}
			
			
			 Set<String> sernames =new HashSet<String>(names);
			 if(sernames.size()==numseries)
			 {
				 seriesname = new String[numseries];
				 seriesname= sernames.
					 toArray(new String[numseries]);
			 }
			 else
			 {
				 seriesname = new String[numseries];
				 for(int i=0;i<numseries;i++)
					 seriesname[i]="";
			 }
		}
		
		public void drawLegend(Canvas canvas) {
			
			
			 
		 	System.out.println("LEGEND" + legendBox);
		 
			//int lLabelWidth = (int) Math.abs(ltPaint.measureText("00000000"));
			int row=1;
			int a=0;
			
			
			for (int i=0; i<seriesname.length; i++)
				
			{
				
				int lLabelWidth = 0;
				
				if(i!=0)
				lLabelWidth = (int) Math.abs(ltPaint.measureText(seriesname[i-1]+"0"));
				float left = legendBox.left+ ((i+1)*drawSpace)+ (i*lLabelWidth) +(i*drawBoxSize);
				float right = left+drawBoxSize;
				
				
			
				//lPaint.setColor(seriescolor.get(i+1));
				

				
					lPaint.setColor(seriescolor.get(seriesname[i]));
					
					
					ltPaint.setColor(seriescolor.get(seriesname[i]));
			
		
				if(((right + lLabelWidth)<legendBox.right))
				{
					
					
					System.out.println("A-1" + left);
					canvas.drawRect(left,legendBox.top+drawSpace,right,
							legendBox.top+drawSpace+drawBoxSize, lPaint);
				
					canvas.drawText(seriesname[i],right+drawSpace,legendBox.top+drawSpace+drawBoxSize, ltPaint);
				}
				else
				{
					row =row+1;
					float top = legendBox.top+((row)*drawSpace)+(row-1)*drawBoxSize;
					float bottom = top+drawBoxSize+drawSpace;
					
					
					
					
					
					
					if(bottom>legendBox.bottom)
					{
						
						float rleft = legendBox.left+ (drawSpace);
						float rright = rleft+(drawBoxSize);
						
						System.out.println("A" + a + rleft );
						
						System.out.println("LEGEND > BOTTOM" + legendBox.left);
						redrawLegendBox(legendBox.width(),drawBoxSize,mLabelWidth);
						
						canvas.drawRect(rleft,top,rright,
								bottom, lPaint);
					
						System.out.println(seriesname[i]);
						canvas.drawText(seriesname[i],rright+drawSpace,
								bottom, ltPaint);
					}
					else
					{
						
						float rleft = legendBox.left+ ((a+1)*drawSpace)+ (a*lLabelWidth) +(a*drawBoxSize);
						float rright = rleft+(drawBoxSize);
						
						System.out.println("A" + a);
						canvas.drawRect(rleft,top,rright,
								bottom, lPaint);
					
						System.out.println(seriesname[i]);
						canvas.drawText(seriesname[i],rright+drawSpace,
								bottom, ltPaint);
						
						a++;
						
					}
					
					
				}
				
			}
			
			
			
		}
		
		
		private void drawInvalidateLegend(Canvas canvas) {
			System.out.println("LEGEND" + legendBox);
			 
			//int lLabelWidth = (int) Math.abs(ltPaint.measureText("000000000"));
			int row=1;
			int a=0;
			
			
			
			
			for (int i=0; i<seriesname.length; i++)
				
			{
				int lLabelWidth = 0;
				
				if(i!=0)
				lLabelWidth = (int) Math.abs(ltPaint.measureText(seriesname[i-1]+"0"));
				
				float checkleft = legendBox.left+ ((a+1)*drawSpace)+ (a*lLabelWidth) +(a*drawBoxSize);
				float checkright = checkleft+drawBoxSize;
				
				
			
				//lPaint.setColor(seriescolor.get(i+1));
				

				
				lPaint.setColor(seriescolor.get(seriesname[i]));
				ltPaint.setColor(seriescolor.get(seriesname[i]));
			
			
		
				if(((checkright + lLabelWidth)<legendBox.right))
				{
					
					System.out.println(seriesname[i]+"<RIGHT");
					
					float left = legendBox.left+ ((a+1)*drawSpace)+ (a*lLabelWidth) +(a*drawBoxSize);
					float right = left+drawBoxSize;
					float top = legendBox.top+((row)*drawSpace)+(row-1)*drawBoxSize;
					float bottom = top+drawBoxSize;
					
					canvas.drawRect(left,top,right,
							bottom, lPaint);
					System.out.println(seriesname[i]);
					canvas.drawText(seriesname[i],right+drawSpace,
								bottom, ltPaint);
					
					a++;
					
				}
				else
				{
					row =row+1;
					System.out.println(seriesname[i]+">RIGHT");
		     		
					a=0;	
					float left = legendBox.left+ ((a+1)*drawSpace)+ (a*lLabelWidth) +(a*drawBoxSize);
					float right = left+drawBoxSize;
					float top = legendBox.top+((row)*drawSpace)+(row-1)*drawBoxSize;
					float bottom = top+drawBoxSize;
						
					System.out.println("A" + a + "ROW"+row);
					
					canvas.drawRect(left,top,right,
							bottom, lPaint);
					System.out.println(seriesname[i]);
					canvas.drawText(seriesname[i],right+drawSpace,
								bottom, ltPaint);
					
					
					a++;
				}
				
			}
			
			
		}
		 private void redrawLegendBox(float coww,int drawBoxSize,int mLabelWidth) {
				
			 
			 
				
				//float coll = (float) boxbounds.height()-mLabelSeparation-(legendSize+(drawBoxSize));
				
				float cohh = (float) boxbounds.height()-3*mLabelHeight-
						(legendSize+2*mLabelSeparation+drawBoxSize)-mLabelHeight-2*mLabelSeparation;
				
				
				
				
				
				diameter = Math.min(coww, cohh);
				
				System.out.println("COHH" +cohh+"DIA" + diameter);
				
				coordbounds = new RectF(0.0f,0.0f,
						diameter,diameter);
				
				float xoffset = mLabelWidth+mLabelSeparation;
		       // coordbounds.offsetTo(getPaddingLeft()+ xoffset, getPaddingTop());
				 coordbounds.offsetTo(boxbounds.centerX()-(diameter/2), boxbounds.centerY()-(diameter/2));
				
				legendBox = new RectF(0.0f,0.0f,coww,legendSize + drawBoxSize);
				
				legendSize  = legendSize+ drawBoxSize;
				
				float toffset = mLabelHeight+mLabelSeparation;
				
				legendBox.offset(getPaddingLeft()+xoffset,coordbounds.bottom+ toffset);
				
				System.out.println("LG" + legendBox);
				
				invalidate =true;
				
				invalidate();
			}
			
			
			private String formatfloatValue(float value, double interval,
					Paint tPaint, double nLabelWidth) {
				
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
				 System.out.println("FLVAL" + numstring);
				return numstring;
			}
			
			private String[] formatfloat(float floatvalue, double interval, 
					Paint tPaint,double radii) {
				
				String numstring ;
				int numsig=0;
				if(interval<1)
					
				{
					if(interval!=0)
					numsig =(int)Math.ceil(-Math.log10(interval));
					
				}
				
				 String append = "#.";
				 
				 String nodec = "#";
				 
				 for(int i=0; i<numsig;i++)
					 
					 append +="#";
				 
				 System.out.println("FLOATVAL" + floatvalue);
				 
				 System.out.println(append);
				 //TODO Find an alternative toDecimalFormat
				 if(floatvalue%1>0)
					 numstring = new DecimalFormat(append).format(floatvalue);
				 else
					 numstring = new DecimalFormat(nodec).format(floatvalue);
				 
				 
				 System.out.println("NUMSTR" + numstring);
				  float minLength = tPaint.measureText("0");
				 
				    int numchars = (int) Math.floor(radii/minLength);
				    
				    int trimsig =0;
				    int exp=0;
					if(numstring.length()>numchars)
					{
						
						 if(floatvalue!=0)
								exp =(int) Math.floor(Math.log10
										(floatvalue));
								else
								exp=0;
						 
						 
						float value  = (float)(floatvalue
								/Math.pow(10,exp));
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
					strings [1] = Integer.toString(exp);
				return strings;
				
				
			}
			
			
			public void calculateSeriesColors(int numseries,Boolean useSeriesPrimary,
					int accentprimarycolor,Map<String, Integer> seriescolor) {
				
				
				int[] colors = new int[numseries];
				ColorCode sercolor = new ColorCode();
				if(useSeriesPrimary)
					colors = sercolor.setPrimaryColor(numseries,20,0.8f,0.9f);
				else
					colors = sercolor.setHSVAccent(accentprimarycolor,numseries,0.5f,1.0f, 20);
				for(int i=1; i<=numseries;i++)
				{
					System.out.println("SCOLOR"+ seriescolor);
					
					System.out.println("SNAME"+ seriesname[i-1]);
					
					System.out.println("COLOR"+ colors[i-1]);
					
					seriescolor.put(seriesname[i-1], colors[i-1]);
					
				}
				
				for(int j=0; j<series.size();j++)
				{
					
					series.get(j).
					setSeriesColor(seriescolor.get(series.get(j).getSeriesName()));
				}
				
			}
			
			
			
			
		
			private static Map<String, Double> sortByComparator(Map<String, Double> unsortMap, final boolean order)
		    {

		        List<Entry<String, Double>> list = new LinkedList<Entry<String, Double>>(unsortMap.
		        		entrySet());

		        // Sorting the list based on values
		        Collections.sort(list, new Comparator<Entry<String, Double>>()
		        {
		            public int compare(Entry<String, Double> o1,
		                    Entry<String, Double> o2)
		            {
		                if (order)
		                {
		                    return o1.getValue().compareTo(o2.getValue());
		                }
		                else
		                {
		                    return o2.getValue().compareTo(o1.getValue());

		                }
		            }
		        });

		        // Maintaining insertion order with the help of LinkedList
		        Map<String, Double> sortedMap = new LinkedHashMap<String, Double>();
		        for (Entry<String, Double> entry : list)
		        {
		            sortedMap.put(entry.getKey(), entry.getValue());
		        }
		        
		        String [] keys = sortedMap.keySet().toArray(new String[sortedMap.size()]);
				 for(int i=0;i<sortedMap.size();i++)
				 {
					 System.out.println("SORTEDMAP" + keys[i] + sortedMap.get(keys[i]));
				 }
		        

		        return sortedMap;
		    }
			
			
			
			@Override 
			 public boolean onTouchEvent(MotionEvent event)
			 {
				 
				if(event.getAction()==MotionEvent.ACTION_UP)
				{
					
					xpoint = event.getX();
					ypoint = event.getY();
					
					System.out.println("XPOINT" + xpoint);
					System.out.println("YPOINT" + ypoint);
					
					getChosenPoint();
					
					
				    
					
				}
				 //mGestureDetector.onTouchEvent(event);
				mScaleGestureDetector.onTouchEvent(event);

				 return true;
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
			 
			 public void getChosenPoint()
			    {
			 		
			 		
			    	
			 		for(int i=0; i< seriesvals.size();i++)
			 		{
			 			PointValues[] vals = seriesvals.get(seriesname[i]);
			 			for(PointValues val : vals)
			 			{
			 				
			 				System.out.println("X"+ val.x +"Y" +val.y);
			 				
			 				if(((xpoint<=val.x+20)& (xpoint>=val.x-20))&((ypoint<=val.y+20)& (ypoint>=val.y-20)))
			 				{	
			 					Selected  selected = new Selected();
			 			    	
			 			    	selected.setValue(val.value);
			 			    	selected.setAnchorX((float)xpoint);
			 			    	selected.setAnchorY((float)ypoint);
			 				
			 					Toast toast = Toast.makeText(context, 
			 					Double.toString(val.value), Toast.LENGTH_SHORT);
			 					toast.setGravity(Gravity.TOP|Gravity.LEFT,(int)(boxbounds.left+
			 							val.x),
			 							(int)(boxbounds.top+val.y));
			 					toast.show();
			 				
			 				}
			 			}
			 		}
			    	

//			    	;
			  
			    }
			
			
}

