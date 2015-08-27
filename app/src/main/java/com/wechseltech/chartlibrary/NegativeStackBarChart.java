
package com.wechseltech.chartlibrary;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.EdgeEffectCompat;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Toast;

public class NegativeStackBarChart extends BarView {

	//TODO SCALING IS NOT WORKING PROPERLY 
	//TODO DRAW TEXT IS NOT WORKING PROPERLY
	Map<String,Integer> seriescolor 
	= new HashMap<String,Integer>();
	
	Paint labelPaint;
	//************************************//
	
		private EdgeEffectCompat mEdgeEffectTop;
		private EdgeEffectCompat mEdgeEffectBottom;
		private EdgeEffectCompat mEdgeEffectLeft;
		private EdgeEffectCompat mEdgeEffectRight;
		
		private boolean mEdgeEffectTopActive;
		private boolean mEdgeEffectBottomActive;
		private boolean mEdgeEffectLeftActive;
		private boolean mEdgeEffectRightActive;
		
		
		RectF enclosingBox = new RectF();
		Context context;
		private ScaleGestureDetector mScaleGestureDetector=null;
		private GestureDetectorCompat mGestureDetector=null;
		private Point ScrollableSurface = new Point();
		
		//************************************//
	
		float xpoint,ypoint;
	
		
	int mLabelWidth, mLabelHeight
	,mAxisLabelWidth,mAxisLabelHeight,
	nLabelHeight,subLabelHeight;
	
	
	float axisLabelSpace;
	
	
	int min=0;
	
	float xoffset;
	int [] setcolors;
	
	Paint aPaint,rPaint,lPaint,fPaint;
	OptimizeTicks tasks;
	
	String[] uniqueY,uniqueX,seriesname;
	
	int nHticks,nVticks;
	RectF coordbounds,
	boxbounds,legendBox,Viewport;
	List<Integer> exponent;
	String xLabelName,yLabelName;
	
	AxisStops hstops,vstops;
	
	 public float AXIS_H_MIN;
		
	 public float AXIS_H_MAX;
	 public float AXIS_V_MIN;
	 
	 
	 BarPosition[] barposition;
	 public  float AXIS_V_MAX;
	
	 double hLinterval,vLinterval,
	 hRinterval,vRinterval;
	 
	 float [] AxisVGridLines,AxisHGridLines;
	 DrawGridLines lines;
	 Boolean hflipped=false,vflipped=false,showLegend;
	 float midpointpos;
		
	 int nLabelWidth;
	 
	 List<MultipleSeriesItem> series;
	 double [] LmaxvalX,LmaxvalY,RmaxvalX
	 ,RmaxvalY;
	 
	 Integer [] cols ;
	 
	 float fixedbottom,fixedtop,fixedleft,fixedright;
	 int drawBoxSize;
	 
	 
	 Paint tPaint,gPaint,bPaint,dPaint;
	 int  minLabelHeight,minLabelWidth;
	
	 int dividercolor;
	 
	 
	 
	 Boolean invalidate = false;
	 
	 ReturnIntervalsTicks ticks;
	 
	 Paint ltPaint;
	 
	 
	 int mVLabelWidth;
	 
	public NegativeStackBarChart(Context context) {
		super(context);
		
		this.context=context;
		hstops = new AxisStops();
		vstops = new AxisStops();
		lines = new DrawGridLines();
		
		mEdgeEffectTop = new EdgeEffectCompat(context);
		mEdgeEffectLeft = new EdgeEffectCompat(context);
		mEdgeEffectRight = new EdgeEffectCompat(context);
		mEdgeEffectBottom = new EdgeEffectCompat(context);
		
		mScaleGestureDetector = new ScaleGestureDetector(context,mScaleGestureListener);
		mGestureDetector = new GestureDetectorCompat(context,mGestureListener);
		
		
		
		
	}
	
	
	
	public NegativeStackBarChart(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		hstops = new AxisStops();
		vstops = new AxisStops();
		lines = new DrawGridLines();
		
		this.context=context;
		
		mEdgeEffectTop = new EdgeEffectCompat(context);
		mEdgeEffectLeft = new EdgeEffectCompat(context);
		mEdgeEffectRight = new EdgeEffectCompat(context);
		mEdgeEffectBottom = new EdgeEffectCompat(context);
		
		mScaleGestureDetector = new ScaleGestureDetector(context,mScaleGestureListener);
		mGestureDetector = new GestureDetectorCompat(context,mGestureListener);
		
		TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.Charts,0,0);
		
		try
		{
			
			isHorizontal =a.getBoolean(R.styleable.Charts_isHorizontal, false);
			XisNum = a.getBoolean(R.styleable.Charts_XisNum, false);
			YisNum = a.getBoolean(R.styleable.Charts_YisNum, false);
			YisCategory = a.getBoolean(R.styleable.Charts_YisCategory, false);
			XisCategory = a.getBoolean(R.styleable.Charts_XisCategory, false);
			showLegend = a.getBoolean(R.styleable.Charts_showLegend, true);
			assignSeriesColors = a.getBoolean(R.styleable.Charts_assignSeriesColors, true);
			useSeriesPrimary = a.getBoolean(R.styleable.Charts_usePrimary, true);
			accentprimarycolor = a.getColor(R.styleable.Charts_accentprimaryColor, Color.RED);
			legendSize = a.getDimension(R.styleable.Charts_legendBoxSize,30);
			mAxisLabelSize = a.getDimension(R.styleable.Charts_axisLabelSize, 12);
			mLabelSeparation = a.getDimension(R.styleable.Charts_labelSeparation, 8);
			drawLabels = a.getBoolean(R.styleable.Charts_drawLabels, false);
			mTextSize = a.getDimension(R.styleable.Charts_textSize, 12);
			textcolor = a.getColor(R.styleable.Charts_textColor, Color.BLACK);
			gridcolor = a.getColor(R.styleable.Charts_gridColor, Color.BLACK);
			rectcolor = a.getColor(R.styleable.Charts_boundingboxColor, Color.BLACK);
			dividercolor = a.getColor(R.styleable.Charts_dividerColor, Color.BLACK);
			showgrid = a.getBoolean(R.styleable.Charts_showGrid, true);
			drawSpace = a.getDimension(R.styleable.Charts_drawSpace, 3);
		
		}
		
		finally
		{
			
			a.recycle();
		}
		

		
	}
	
	
	public NegativeStackBarChart(Context context, AttributeSet attrs,int defStyle) {
		super(context, attrs,defStyle);
		
		hstops = new AxisStops();
		vstops = new AxisStops();
		lines = new DrawGridLines();
		
		this.context=context;
		mEdgeEffectTop = new EdgeEffectCompat(context);
		mEdgeEffectLeft = new EdgeEffectCompat(context);
		mEdgeEffectRight = new EdgeEffectCompat(context);
		mEdgeEffectBottom = new EdgeEffectCompat(context);
		
		mScaleGestureDetector = new ScaleGestureDetector(context,mScaleGestureListener);
		mGestureDetector = new GestureDetectorCompat(context,mGestureListener);
		
		TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.Charts,0,0);
		
		try
		{
			
			isHorizontal =a.getBoolean(R.styleable.Charts_isHorizontal, false);
			XisNum = a.getBoolean(R.styleable.Charts_XisNum, false);
			YisNum = a.getBoolean(R.styleable.Charts_YisNum, false);
			YisCategory = a.getBoolean(R.styleable.Charts_YisCategory, false);
			XisCategory = a.getBoolean(R.styleable.Charts_XisCategory, false);
			showLegend = a.getBoolean(R.styleable.Charts_showLegend, true);
			assignSeriesColors = a.getBoolean(R.styleable.Charts_assignSeriesColors, true);
			rectcolor = a.getColor(R.styleable.Charts_boundingboxColor, Color.BLACK);
			useSeriesPrimary = a.getBoolean(R.styleable.Charts_usePrimary, true);
			drawLabels = a.getBoolean(R.styleable.Charts_drawLabels, false);
			accentprimarycolor = a.getColor(R.styleable.Charts_accentprimaryColor, Color.RED);
			legendSize = a.getDimension(R.styleable.Charts_legendBoxSize,30);
			mAxisLabelSize = a.getDimension(R.styleable.Charts_axisLabelSize, 12);
			mLabelSeparation = a.getDimension(R.styleable.Charts_labelSeparation, 8);
			mTextSize = a.getDimension(R.styleable.Charts_textSize, 12);
			textcolor = a.getColor(R.styleable.Charts_textColor, Color.BLACK);
			gridcolor = a.getColor(R.styleable.Charts_gridColor, Color.BLACK);
			dividercolor = a.getColor(R.styleable.Charts_dividerColor, Color.BLACK);
			showgrid = a.getBoolean(R.styleable.Charts_showGrid, true);
			drawSpace = a.getDimension(R.styleable.Charts_drawSpace, 3);
	
		
		}
		
		finally
		{
			
			a.recycle();
		}
		
		
		
	}

	private void init() {
		
		
		
			 tPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		     tPaint.setColor(textcolor);
		     tPaint.setTextSize(mTextSize);
		     tPaint.setTextAlign(Paint.Align.CENTER);
		     
		     
		     fPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		        fPaint.setColor(textcolor);
		        fPaint.setTextSize(mTextSize);
		        fPaint.setTextAlign(Paint.Align.LEFT);
		        
		        labelPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		        labelPaint.setColor(textcolor);
		        labelPaint.setTextSize(mLabelSize);
		        labelPaint.setTextAlign(Paint.Align.CENTER);
		        
		     aPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		     aPaint.setColor(textcolor);
		     aPaint.setTextSize(mAxisLabelSize);
		     aPaint.setTextAlign(Paint.Align.CENTER);
		     
		     rPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		     rPaint.setColor(Color.BLACK);
		       
		     rPaint.setStyle(Paint.Style.STROKE);
		     
		     
		     ltPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		     
		     ltPaint.setTextSize(mTextSize);
		     ltPaint.setTextAlign(Paint.Align.LEFT);
		     
		     
		     dPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		     dPaint.setColor(dividercolor);
		       
		     dPaint.setStyle(Paint.Style.STROKE);
		     
		     gPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		     gPaint.setColor(gridcolor);
		     
		     bPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		     bPaint.setStyle(Paint.Style.FILL);
		     
		     lPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		     lPaint.setStyle(Paint.Style.FILL);
		     
		     
		    
			mLabelHeight =(int) Math.abs(tPaint.getFontMetrics().top);
	        
			drawBoxSize = mLabelHeight;
			
			mVLabelWidth = (int) Math.abs(tPaint.measureText("00000"));
			
	        minLabelHeight = (int) Math.abs(tPaint.getFontMetrics().bottom);
	    
	        minLabelWidth = (int) Math.abs(tPaint.measureText("000"));
	        mLabelWidth = (int) Math.abs(tPaint.measureText("000000"));
	        
	        mAxisLabelHeight = (int) Math.abs(aPaint.getFontMetrics().top);
		
	}



	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh)
	{
		
		float coww =0.0f,cohh=0.0f,coll =0.0f;
		
		float xpad = (float)(getPaddingLeft()+ getPaddingRight());
		float ypad = (float)(getPaddingTop() +  getPaddingBottom());
		
		
		init();
		
	
		
		coordbounds = new RectF(0.0f,0.0f,
				coww,cohh);
		
		coww = (float) w-xpad
				-mLabelWidth-2*mAxisLabelHeight-4*mLabelSeparation-(-aPaint.ascent());;
		cohh = (float) h-ypad-mLabelHeight-2*mAxisLabelHeight-(-aPaint.ascent())-(legendSize) - 3 * mLabelSeparation;

		float bxww = (float) w-xpad;
		float bxhh = (float) h-ypad;
		
		mAxisLabelWidth = (int) (coww/2);
		
		boxbounds = new RectF(getPaddingLeft(),getPaddingTop(),
				getPaddingLeft()+bxww,getPaddingTop()+bxhh);
		
		
		//coll = bxhh-legendSize-mLabelSeparation;
		
		
		
		float xoffset = mLabelWidth+ (2*mAxisLabelHeight)+3*mLabelSeparation-(-aPaint.ascent());
		//axisLabelSpace = (2*mAxisLabelHeight+aPaint.ascent())+2*mLabelSeparation;
		
		axisLabelSpace = (2*mAxisLabelHeight-aPaint.ascent())+3*mLabelSeparation;
		
		
		coordbounds.offset(getPaddingLeft()+ xoffset,getPaddingTop()+mLabelSeparation);
		
		
		legendBox = new RectF(0.0f,0.0f,coww,legendSize);
		
		legendBox.offset(getPaddingLeft()+xoffset,coordbounds.bottom+mLabelSeparation+mLabelHeight);
		
			
		tasks = new OptimizeTicks(mLabelWidth,mLabelHeight,coordbounds.width(),
				coordbounds.height(),minLabelWidth,
				minLabelHeight,boxbounds.height(),boxbounds.width(),
				axisLabelSpace,legendSize);
		
		assignColors();
		getXYTicks(boxbounds.width(),boxbounds.height(),coordbounds.width(),axisLabelSpace);
		
	}
	
	
	
	
	
	private void getXYTicks(float bxw,float bxh,float coww,float axisLabelSpace)
	{
		
		
		ComputeStops cmp = new ComputeStops();
		
		HandleXYTicks handleticks = new HandleXYTicks(coordbounds,getPaddingLeft(), 
				
				getPaddingTop(), mAxisLabelHeight,mLabelSeparation, aPaint,mBarWidth,
				mBarSpacing,0,legendSize,mVLabelWidth);
	
		 //barspace = (mBarWidth)+(2*mBarSpacing);	
		
		
		
		
		if  (XisNum & YisCategory)
		{
			
			getXArray();
			
			getYCategory();
			
			ReturnIntervalsTicks ticks =handleticks.getYCategoryXNumericNegativeBar(isHorizontal,
					RmaxvalX[RmaxvalX.length-1],RmaxvalX[0],RmaxvalX,
					LmaxvalX[LmaxvalX.length-1],LmaxvalX[0],LmaxvalX,
					hstops,vstops,
					uniqueY,bxh,bxw,coww,
					axisLabelSpace,xoffset,tasks,1);
			
			nLabelHeight=ticks.nLabelHeight;
			nVticks = ticks.nVticks;
			nHticks = ticks.nHticks;
			nLabelWidth = ticks.nLabelWidth;
			System.out.println("LABELWIDTH" + nLabelWidth + mLabelWidth);
			subLabelHeight = ticks.subLabelHeight;
			if(!isHorizontal)
			{
				hLinterval = ticks.hLinterval;
				hRinterval = ticks.hRinterval;
				System.out.println("INTERVAL" + hLinterval + hRinterval);
				
			}
			else
			{
				vLinterval = ticks.vLinterval;
				vRinterval = ticks.vRinterval;
				
			}
			
			Viewport = ticks.Viewport;
			fixedtop = Viewport.top;
			fixedbottom = Viewport.bottom;
			fixedleft = Viewport.left;
			fixedright = Viewport.right;
			coordbounds = ticks.coordbounds;
			legendBox = ticks.legendBox;
			
			System.out.println("COORDLEFT"+ coordbounds.left + "COORDRT"+ coordbounds.right);
			hstops = ticks.hstops;
			vstops = ticks.vstops;
			if(ticks.isHflipped!=null)
				hflipped = ticks.isHflipped;
			if(ticks.isVflipped!=null)
				vflipped = ticks.isVflipped;
			
			this.ticks=ticks;
			AXIS_H_MIN = ticks.AXIS_H_MIN;
			AXIS_H_MAX = ticks.AXIS_H_MAX;
			AXIS_V_MIN = ticks.AXIS_V_MIN;
			AXIS_V_MAX =ticks.AXIS_V_MAX;
			
			
			if(isHorizontal)
			{
			int visTicks =(int)Math.floor(coordbounds.width()/barspace);
			
			System.out.println("VIS" + visTicks);
			if(hstops.axisstops.length>visTicks)
			Viewport.right=visTicks;
			}
			
			else
			{
				int visTicks =(int)Math.floor(coordbounds.height()/barspace);
				
				if(vstops.axisstops.length>visTicks)
					Viewport.bottom=visTicks;
			}
			
			
			 if(!isHorizontal)
			 {
				 if(YisNum)
				 {
					 
					 
					 midpointpos = vstops.midpoint;
					 
				 }
				 if(XisNum)
				 {
					 
					 
					 midpointpos = hstops.midpoint ;
					 
					
					 
				 }
			 }
			 
			 else
			 {
				 if(YisNum)
				 {
					 
					 
					 midpointpos = hstops.midpoint;
					 
				 }
				 if(XisNum)
				 {
					/* vstops.midpointpos=coordbounds.bottom - coordbounds.height()*
						(vstops.midpoint-Viewport.top)/Viewport.height();*/
					 
					 midpointpos = vstops.midpoint;
					 
				 }
			 }
			
			
			if(!isHorizontal)
			{
				if(XisNum)
				{
			
					for(int i=0; i<series.size();i++)
					{
						if(series.get(i).getSide())
						{
							
							System.out.println("STOPS MIDPOINT" +
							hstops.midpoint+"RANGE" +(ticks.LGraphMax-ticks.LGraphMin));
							
							
							float val = (float) (hstops.midpoint-((series.get(i).getXVal()
									-ticks.LGraphMin)*Math.abs(hstops.midpoint-Viewport.left))
									/(ticks.LGraphMax-ticks.LGraphMin));
							series.get(i).setScaledValue(val);
							
							System.out.println("NSER" + val);
						}
						
						else
							
						{
							float val = (float) (hstops.midpoint+((series.get(i).getXVal()-ticks.RGraphMin)*
									Math.abs(Viewport.right-hstops.midpoint))/(ticks.RGraphMax-ticks.RGraphMin));
							series.get(i).setScaledValue(val);
							
							System.out.println("SER" + val);
						}
						
						
						
					}
				
				}
			
			}
			
			else
				
			{
				if(XisNum)
				{
			
					for(int i=0; i<series.size();i++)
					{
						if(series.get(i).getSide())
						{
							float val = (float) (vstops.midpoint-((series.get(i).getXVal()-ticks.LGraphMin)*Math.abs(vstops.midpoint-Viewport.top))
									/(ticks.LGraphMax-ticks.LGraphMin));
							series.get(i).setScaledValue(val);
							
						
						}
						
						else
							
						{
							float val = (float) (vstops.midpoint+((series.get(i).getXVal()-ticks.RGraphMin)*
									Math.abs(Viewport.bottom-vstops.midpoint))/(ticks.RGraphMax-ticks.RGraphMin));
							series.get(i).setScaledValue(val);
						}
						
						
						
					}
				
				}
			}
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
		}
		
		
		if  (XisCategory & YisNum)
		{
			
			getYArray();
			
			
			getXCategory();
			 
			
			ReturnIntervalsTicks ticks =handleticks.getXCategoryYNumericNegativeBar(isHorizontal, 
				RmaxvalY[RmaxvalY.length-1], 
					RmaxvalY[0], RmaxvalY,LmaxvalY[LmaxvalY.length-1],LmaxvalY[0],
					LmaxvalY, 
					hstops, vstops, uniqueX, bxh, bxw, coww, 
					axisLabelSpace,xoffset, tasks, 1);
					
			
			nVticks = ticks.nVticks;
			nHticks = ticks.nHticks;
			nLabelWidth = ticks.nLabelWidth;
			subLabelHeight = ticks.subLabelHeight;
			nLabelHeight=ticks.nLabelHeight;
			if(!isHorizontal)
			{
				vLinterval = ticks.vLinterval;
				vRinterval = ticks.vRinterval;
			}
			else
			{
				hLinterval = ticks.hLinterval;
				hRinterval = ticks.hRinterval;
				
			}
			this.ticks=ticks;
			Viewport = ticks.Viewport;
			fixedtop = Viewport.top;
			fixedbottom = Viewport.bottom;
			fixedleft = Viewport.left;
			fixedright = Viewport.right;
			coordbounds = ticks.coordbounds;
			legendBox = ticks.legendBox;
			
			
			
			hstops = ticks.hstops;
			vstops = ticks.vstops;
			if(ticks.isHflipped!=null)
				hflipped = ticks.isHflipped;
			if(ticks.isVflipped!=null)
				vflipped = ticks.isVflipped;
			
			AXIS_H_MIN = ticks.AXIS_H_MIN;
			AXIS_H_MAX = ticks.AXIS_H_MAX;
			AXIS_V_MIN = ticks.AXIS_V_MIN;
			AXIS_V_MAX =ticks.AXIS_V_MAX;
			
			
			System.out.println("AXIS_H_MAX" +ticks.AXIS_H_MAX );
			System.out.println("AXIS_H_MIN" +ticks.AXIS_H_MIN );
			System.out.println("AXIS_V_MIN" +ticks.AXIS_V_MIN );
			System.out.println("AXIS_V_MAX" +ticks.AXIS_V_MAX );
			
			
			if(!isHorizontal)
			{
			int visTicks =(int)Math.floor(coordbounds.width()/barspace);
			
			System.out.println("VIS" + visTicks);
			if(hstops.axisstops.length>visTicks)
			Viewport.right=visTicks;
			}
			
			else
			{
				int visTicks =(int)Math.floor(coordbounds.height()/barspace);
				
				if(vstops.axisstops.length>visTicks)
					Viewport.bottom=visTicks;
			}
//			
			
			 if(!isHorizontal)
			 {
				 if(YisNum)
				 {
					 
					 
					 midpointpos = vstops.midpoint;
					 
				 }
				 if(XisNum)
				 {
					 
					 
					 midpointpos = hstops.midpoint ;
					 
					
					 
				 }
			 }
			 
			 else
			 {
				 if(YisNum)
				 {
					 
					 
					 midpointpos = hstops.midpoint;
					 
				 }
				 if(XisNum)
				 {
					/* vstops.midpointpos=coordbounds.bottom - coordbounds.height()*
						(vstops.midpoint-Viewport.top)/Viewport.height();*/
					 
					 midpointpos = vstops.midpoint;
					 
				 }
			 }
			
			
			if(!isHorizontal)
			{
				if(YisNum)
				{
			
					for(int i=0; i<series.size();i++)
					{
						if(series.get(i).getSide())
						{
							System.out.println("VAL" + vstops.midpoint);
							float val = (float) (vstops.midpoint-((series.get(i).getYVal()-ticks.LGraphMin)*Math.abs(vstops.midpoint-Viewport.top))
									/(ticks.LGraphMax-ticks.LGraphMin));
							series.get(i).setScaledValue(val);
							
							System.out.println("NSER" + val);
						}
						
						else
							
						{
							float val = (float) (vstops.midpoint+((series.get(i).getYVal()-ticks.RGraphMin)*
									Math.abs(Viewport.bottom-vstops.midpoint))/(ticks.RGraphMax-ticks.RGraphMin));
							series.get(i).setScaledValue(val);
							
							System.out.println("SER" + val);
						}
						
						
						
					}
				
				}
			
			}
			
			else
				
			{
				if(YisNum)
				{
			
					for(int i=0; i<series.size();i++)
					{
						if(series.get(i).getSide())
						{
							float val = (float) (hstops.midpoint-
									((series.get(i).getYVal()-ticks.LGraphMin)*Math.abs(hstops.midpoint-Viewport.left))
									/(ticks.LGraphMax-ticks.LGraphMin));
							series.get(i).setScaledValue(val);
							
						
						}
						
						else
							
						{
							float val = (float) (hstops.midpoint+((series.get(i).getYVal()-ticks.RGraphMin)*
									Math.abs(Viewport.right-hstops.midpoint))/(ticks.RGraphMax-ticks.RGraphMin));
							series.get(i).setScaledValue(val);
						}
						
						
						
					}
				
				}
			}
			
		}
		
		
		
		
		
	}
	
	
	@Override 
	 public void onDraw(Canvas canvas)
	 
	 {
		super.onDraw(canvas);
	
		int clipRestoreCount = canvas.save();
		canvas.clipRect(coordbounds);
		
		
		
		drawAxes(canvas);
		
		drawData(canvas);
		
		
		drawEdgeEffectsUnclipped(canvas);
		
		
		System.out.println("COLBEFORE"+ coordbounds.left+"CORBEORE"+ coordbounds.right);
		
		//canvas.drawRect(legendBox, rPaint);
		
		canvas.restoreToCount(clipRestoreCount);
		
		

		drawText(canvas);
		
		if(showLegend)
		{
			if(!invalidate)
			drawLegend(canvas);
			else
			drawInvalidateLegend(canvas);
		
		}
		
		
		
		System.out.println("COLAFTER"+ coordbounds.left+"CORAFTER"+ coordbounds.right+mAxisLabelHeight);
		
		
		canvas.drawRect(coordbounds,rPaint);
		
		
		
		
		
		 
	 }
	

	
	public void drawLegend(Canvas canvas) {
		
		
		//int lLabelWidth = (int) Math.abs(ltPaint.measureText("00000000"));
		int row=1;
		
		int a=0;
		
		for (int i=0; i<2; i++)
			
		{
			
			int lLabelWidth = 0;
			
			if(i!=0)
			lLabelWidth = (int) Math.abs(ltPaint.measureText(seriesname[i-1]+"0"));
			
			float left = legendBox.left+ ((i+1)*drawSpace)+ (i*lLabelWidth) +(i*drawBoxSize);
			float right = left+drawBoxSize;
			
			
		
			//lPaint.setColor(seriescolor.get(i+1));
			

			if(assignSeriesColors)
			{
				lPaint.setColor(seriescolor.get(seriesname[i]));
				ltPaint.setColor(seriescolor.get(seriesname[i]));
				
			}
		
			else
			{
				lPaint.setColor(seriescolor.get(seriesname[i]));
				ltPaint.setColor(seriescolor.get(seriesname[i]));
				
			}
		
	
			if(((right + lLabelWidth)<legendBox.right))
			{
				
				
				canvas.drawRect(left,legendBox.top+drawSpace,right,
				legendBox.top+drawSpace+drawBoxSize, lPaint);
			
				canvas.drawText(seriesname[i],right+drawSpace,legendBox.top+drawSpace+drawBoxSize, ltPaint);
			}
			else
			{
				row =row+1;
				float top = legendBox.top+((row)*drawSpace)+(row-1)*drawBoxSize;
				float bottom = top+drawBoxSize;
				
				
				float rleft = legendBox.left+ ((a+1)*drawSpace)+ (a*lLabelWidth) +(a*drawBoxSize);
				float rright = rleft+(drawBoxSize);
				
				System.out.println("A" + a);
				
				if(bottom>legendBox.bottom)
				{
					redrawLegendBox(legendBox.width(),drawBoxSize,mLabelWidth);
				}
				else
				{
					
					canvas.drawRect(rleft,top,rright,
							bottom, lPaint);
				
					System.out.println(seriesname[i]);
					canvas.drawText(seriesname[i],rright+drawSpace,
							bottom, ltPaint);
					
				}
				
				a++;
			}
			
		}
		
		
		
	}
	
	
		private void redrawLegendBox(float coww,int drawBoxSize,int mLabelWidth) {
		

			
			
			float coll = (float) boxbounds.height()- subLabelHeight-
					(2*mAxisLabelHeight+3*mLabelSeparation)-(-aPaint.ascent())
					-(legendSize+drawBoxSize);
			
			/*float coll = (float) boxbounds.height()- subLabelHeight-
					(2*mAxisLabelHeight+3*mLabelSeparation)-(aPaint.ascent())
					-(legendSize+drawBoxSize);*/

			
			coordbounds = new RectF(0.0f,0.0f,
					coww,coll);
			
			float xoffset = ticks.nvLabelWidth+(2*mAxisLabelHeight)-(-aPaint.ascent())+3*mLabelSeparation;
			
	         coordbounds.offsetTo(getPaddingLeft()+ xoffset, getPaddingTop()+mLabelSeparation);
			
			legendBox = new RectF(0.0f,0.0f,coww,legendSize + drawBoxSize);
			
			legendSize  = legendSize+ drawBoxSize;
			
			legendBox.offset(getPaddingLeft()+xoffset,coordbounds.bottom+subLabelHeight+(mAxisLabelHeight-aPaint.ascent()));
			
			invalidate =true;
			
			invalidate();
			
			
		
	}
	
		
		private void drawInvalidateLegend(Canvas canvas) {
			System.out.println("LEGEND" + legendBox);
			 
			//int lLabelWidth = (int) Math.abs(ltPaint.measureText("00000000"));
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
				

				
				if(assignSeriesColors)
				{
					lPaint.setColor(seriescolor.get(seriesname[i]));
					ltPaint.setColor(seriescolor.get(seriesname[i]));
					
				}
			
				else
				{
					lPaint.setColor(seriescolor.get(seriesname[i]));
					ltPaint.setColor(seriescolor.get(seriesname[i]));
					
				}
			
			
		
				if(((checkright + lLabelWidth)<legendBox.right))
				{
					
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
	
	private float getCentreHCategory(double value,float space)
	{
		
		
		/*float pos = coordbounds.left + coordbounds.width()*
				(float)(value-(space/2)+ mBarSpacing -Viewport.left)/Viewport.width() ;
		
			
		return pos;*/
		
		
		
		float pos = 0;
		float bpos=0;
		
		//float indspace = space/numseries;
		
//		pos = coordbounds.left + coordbounds.width()*
//				(float)(value-Viewport.left)/Viewport.width();
		
		
		pos = coordbounds.left + barspace*
				(float)(value-Viewport.left);
			
				
				bpos = (pos-space/2)+ mBarSpacing;
				
	    System.out.println("BPOS" +pos);	
			
			
			
			
		return bpos;
	}
	
	private float getCentreVCategory(double value,float space)
	{
		float pos = 0,bpos=0;
		
		
			/*pos = coordbounds.bottom - coordbounds.height()*
				(float)(value - Viewport.top)/Viewport.height() ;*/
		
		pos = coordbounds.bottom - barspace*
				(float)(value-Viewport.top);
		
			
			bpos = (pos-space/2)+mBarSpacing;
		return bpos;

	}
	
	

	/*
	private float getCentreV(double value,float midpoint,boolean negative)
	{
		double val =0.0f;
		
		if(negative)
		{
		
			val = midpoint-((value-ticks.LGraphMin)*Math.abs(midpoint-Viewport.top))
					/(ticks.LGraphMax-ticks.LGraphMin);
		
			System.out.println("VAL LEFT" + val+"MP" + midpoint);
		}
		else
		{
		
			System.out.println("VAL RIGHT" + val + "MP" + midpoint);
		
			
			val = midpoint+((value-ticks.RGraphMin)*
					Math.abs(Viewport.bottom-midpoint))/(ticks.RGraphMax-ticks.RGraphMin);
			
			
		
		}
		
		
			
		
		
		float pos=0 ;
		if(negative)
		{
		pos=  vstops.midpointpos +
				Math.abs(coordbounds.bottom-vstops.midpointpos)*
				(float)(midpoint-val)/Math.abs(Viewport.top-midpoint);
			
			pos=   coordbounds.bottom -
					Math.abs(coordbounds.bottom-vstops.midpointpos)*
					(float)(midpoint-val)/Math.abs(Viewport.top-midpoint);
			
		}
		else
		{
		 pos = vstops.midpointpos - Math.abs(vstops.midpointpos-coordbounds.top)*
					(float)(val-midpoint)/Math.abs(Viewport.bottom-midpoint);
			
			pos = coordbounds.bottom - Math.abs(coordbounds.width())*
					(float)((val+midpoint)-  )/Math.abs(Viewport.bottom-midpoint);
		}
		
		System.out.println("MP" +midpoint +"VMP"+vstops.midpointpos);
		System.out.println("POS" +pos);
		return pos;
	}*/
	
	
	private float getCentreV(double value,float midpoint,boolean negative)
	{
		double val =0.0f,midpointval =0.0f;
		
		if(negative)
		{
			/*val =  midpoint - (value - (LmaxvalY[0]-vLinterval))*
				((LmaxvalY[0]-vLinterval)-(LmaxvalY[LmaxvalY.length-1]+vLinterval))/Viewport.height()/2;*/
		
			/*val = midpoint-((value-ticks.LGraphMin)*Math.abs(midpoint-Viewport.top))
					/(ticks.LGraphMax-ticks.LGraphMin);*/
			
			
			
			
			
			//midpointval = midpoint * (Viewport.bottom-Viewport.top)/(fixedbottom-fixedtop);
			
			
			/*val = midpoint-((value-ticks.LGraphMin)*Math.abs(midpoint-Viewport.top))
					/(ticks.LGraphMax-ticks.LGraphMin);
			*/
			/*val = midpointval-((value-ticks.LGraphMin)*Math.abs(midpointval-Viewport.top))
					/(ticks.LGraphMax-ticks.LGraphMin);*/
			
			
			System.out.println("TL" + ticks.LGraphMax+ticks.LGraphMin);
			
			
			
			/*val = midpointval-((value-vstops.pointstops[0])*Math.abs(midpointval-Viewport.top))
					/(vstops.pointstops[vstops.pointstops.length-1]-vstops.pointstops[0]);*/
		
			System.out.println("VALUE LEFT" + val+"VB"+Viewport.bottom + "VT" +Viewport.top
					+ "MP" + midpoint + "MPVAL" + midpointval + "VTHT" + Viewport.height());
		}
		else
		{
		
			System.out.println("VAL" + val + "MP" + midpoint);
			/*val =  midpoint + (value - (RmaxvalY[0]-vRinterval))*
			 ((RmaxvalY[0]-vRinterval)-(RmaxvalY[RmaxvalY.length-1]+vRinterval))/Viewport.height()/2;*/
			
			/*val = midpoint+((value-ticks.RGraphMin)*
					Math.abs(Viewport.bottom-midpoint))/(ticks.RGraphMax-ticks.RGraphMin);*/
			
			/*midpointval = midpoint * (Viewport.bottom-Viewport.top)/(fixedbottom-fixedtop);
			*/
			
			/*val = midpoint+((value-ticks.RGraphMin)*
					Math.abs(Viewport.bottom-midpoint))/(ticks.RGraphMax-ticks.RGraphMin);*/
			
		/*	val = midpointval+((value-ticks.RGraphMin)*
					Math.abs(Viewport.bottom-midpointval))/(ticks.RGraphMax-ticks.RGraphMin);*/
		
			
			System.out.println("VALUE RIGHT" + val + "VT" + Viewport.top);
		}
		
		
			
		
		/*float pos = coordbounds.bottom - coordbounds.height()*
		(float)(val-Viewport.top)/Viewport.height();*/
		
		
		
		float pos=0 ;
		/*if(negative)
		{
		pos=  vstops.midpointpos +
				Math.abs(coordbounds.bottom-vstops.midpointpos)*
				(float)(midpoint-val)/Math.abs(Viewport.top-midpoint);
			
			pos=  vstops.midpointpos +
					Math.abs(coordbounds.bottom-vstops.midpointpos)*
					(float)(midpoint-val)/Math.abs(Viewport.top-midpoint);
		}
		else
		{*/
		/* pos = vstops.midpointpos - Math.abs(vstops.midpointpos-coordbounds.top)*
					(float)(val-midpoint)/Math.abs(Viewport.bottom-midpoint);*/
			
			 pos = coordbounds.bottom - coordbounds.height()*
						(float)(value-Viewport.top)/(Viewport.height());
		//}
		
		System.out.println("MP" +midpoint +"VMP"+vstops.midpointpos);
		System.out.println("POS" +pos);
		return pos;
	}
	
	
	private float getCentreH(double value,float midpoint, boolean negative)
	
	
	
	{
		
		
		
		float pos = coordbounds.left + coordbounds.width()*
		(float)(value-Viewport.left)/Viewport.width();
		
		System.out.println("POS H "+ pos + value);
		
		return pos;
	}
	
	
	
	private void drawData(Canvas canvas)
	{
		float space = (1*mBarWidth)+(2*mBarSpacing);
		
		barposition = new BarPosition[series.size()];
		
		
		
		if(!isHorizontal)
		{
			

				for(int m=0; m<series.size();m++)
				{
					
					
					float centreh=0.0f,centrev=0.0f;
					
					
					barposition[m]= new BarPosition();
					
					if(XisCategory)
						
					{
						int serie = series.get(m).getSeries();
						for(int n=0; n<uniqueX.length;n++)
							
						{
							if(series.get(m).getXLabel().equals(uniqueX[n]))
							 centreh = getCentreHCategory(n+1,space);
						}
						
						
						barposition[m].setLeftEdge(centreh);
						barposition[m].setRightEdge(centreh+mBarWidth);
						
						
					}
					
					else
					{
						centreh = getCentreH(series.get(m).getScaledValue(),
								hstops.midpoint,series.get(m).getSide());
						
						
						
						
						if(series.get(m).getSide())
						{
							barposition[m].setLeftEdge(centreh);
							barposition[m].setRightEdge(hstops.midpointpos);
							barposition[m].setValue(series.get(m).getXVal());
						}
						else
						{
							barposition[m].setLeftEdge(hstops.midpointpos);
							barposition[m].setRightEdge(centreh);
							barposition[m].setValue(series.get(m).getXVal());
						}
					}
					
					
					
					if(YisCategory)
						
					{
						int serie = series.get(m).getSeries();
						for(int r=0; r<uniqueY.length;r++)
							
						{
							if(series.get(m).getYLabel().equals(uniqueY[r]))
							 centrev = getCentreVCategory(r+1,space);
						}
						
						
						barposition[m].setTopEdge(centrev);
						barposition[m].setBottomEdge(centrev+mBarWidth);
						
						
						
						
					}
					
					else
					{
						centrev = getCentreV(series.get(m).getScaledValue(),
								vstops.midpoint,series.get(m).getSide());
						if(series.get(m).getSide())
						{
							barposition[m].setTopEdge(vstops.midpointpos);
							barposition[m].setBottomEdge(centrev);
							barposition[m].setValue(series.get(m).getYVal());
						}
						else
						{
							barposition[m].setTopEdge(centrev);
							barposition[m].setBottomEdge(vstops.midpointpos);
							barposition[m].setValue(series.get(m).getYVal());
						}
					}
						
					
					   System.out.println("CENTREV" + centrev);
						//TODO GET FROM COLOR CODE LIKE PIE CHART
					   drawBar(canvas,centreh,centrev,series.get(m).getColor(),
							series.get(m).getSide());
					   
					   if(drawLabels)
					   {
							if(XisCategory)
							   canvas.drawText(series.get(m).getLabel(),0,
										series.get(m).getLabel().length(),
										centreh,centrev, labelPaint);
						   if(YisCategory)
						   {
							   System.out.println("DRAW LABEL");
							   canvas.save();
							   canvas.rotate((float)(90));
							   canvas.drawText(series.get(m).getLabel(),0,
										series.get(m).getLabel().length(),
										centrev+mBarWidth/2,-(centreh), labelPaint);
							   canvas.restore();
						   }
					   }
					   //bPaint.setColor(series.get(m).getColor());
					   
					   //canvas.drawCircle(centreh,centrev,5,bPaint);
					   
					
				}
		}
		
		else
		{
			for(int m=0; m<series.size();m++)
			{
				
				barposition[m]= new BarPosition();
				
				float centreh=0.0f,centrev=0.0f;
				
				
				if(XisCategory)
					
				{
					for(int n=0; n<uniqueX.length;n++)
						
					{
						if(series.get(m).getXLabel().equals(uniqueX[n]))
						 centrev = getCentreVCategory(n+1,space);
						
					}
					
					barposition[m].setTopEdge(centrev);
					barposition[m].setBottomEdge(centrev+mBarWidth);
					
					
				}
				
				else
				{
					centrev = getCentreV(series.get(m).getScaledValue(),
							vstops.midpoint,series.get(m).getSide());
					
					if(series.get(m).getSide())
					{
						System.out.println(barposition[m]);
						barposition[m].setTopEdge(vstops.midpointpos);
						barposition[m].setBottomEdge(centrev);
						barposition[m].setValue(series.get(m).getXVal());
					}
					else
					{
						barposition[m].setTopEdge(centrev);
						barposition[m].setBottomEdge(vstops.midpointpos);
						barposition[m].setValue(series.get(m).getXVal());
					}
					
					System.out.println("CentreV" + centrev);
					System.out.println("SCALE" + series.get(m).getScaledValue());
					
				}
				
				if(YisCategory)
					
				{
					for(int r=0; r<uniqueY.length;r++)
						
					{
						if(series.get(m).getYLabel().equals(uniqueY[r]))
						 centreh = getCentreHCategory(r+1,space);
						System.out.println("CentreH" + centreh);
					}
					
					barposition[m].setLeftEdge(centreh);
					barposition[m].setRightEdge(centreh+mBarWidth);
					
					
				}
				
				else
				{
					centreh = getCentreH(series.get(m).getScaledValue(),
							hstops.midpoint,series.get(m).getSide());
					
					System.out.println("CentreH" + centreh);
					System.out.println("CentreV" + centrev);
					System.out.println("SCALE" + series.get(m).getScaledValue());
					
					if(series.get(m).getSide())
					{
						barposition[m].setLeftEdge(centreh);
						barposition[m].setRightEdge(hstops.midpointpos);
						barposition[m].setValue(series.get(m).getYVal());
					}
					else
					{
						barposition[m].setLeftEdge(hstops.midpointpos);
						barposition[m].setRightEdge(centreh);
						barposition[m].setValue(series.get(m).getYVal());
					}
					
				}
					//TODO GET FROM COLOR CODE LIKE PIE CHART
					drawBar(canvas,centreh,centrev,series.get(m).getColor(),series.get(m).getSide());
				
					if(drawLabels)
						 
					{
						
							if(YisCategory)
							   canvas.drawText(series.get(m).getLabel(),0,
										series.get(m).getLabel().length(),
										centreh,centrev, labelPaint);
						   if(XisCategory)
						   {
							   System.out.println("DRAW LABEL");
							   canvas.save();
							   canvas.rotate((float)(90));
							   canvas.drawText(series.get(m).getLabel(),0,
										series.get(m).getLabel().length(),
										centrev+mBarWidth/2,-(centreh), labelPaint);
							   canvas.restore();
						   }
						   
					}
			}
		}
		
		
		
		
	}

	
	private void drawBar(Canvas canvas, float centreh,float centrev,int color,boolean negative)
	{
		bPaint.setColor(color);
		
		System.out.println("COLOR" + color);
		
		if(!isHorizontal)
		 {
			 if(YisNum)
			 {
				
				 
				 if(negative)
				 {
					canvas.drawRect(centreh,vstops.midpointpos,centreh+mBarWidth,centrev,bPaint);
					 
					 //canvas.drawRect(centreh,200,centreh+mBarWidth,300,bPaint);
						//canvas.drawRect(centreh,vstops.midpointpos,centreh+mBarWidth,centrev,bPaint);
				 }
				else
					{
					
					  canvas.drawRect(centreh,centrev,centreh+mBarWidth,vstops.midpointpos,bPaint);
						//canvas.drawRect(centreh,centrev,centreh+mBarWidth,vstops.midpointpos,bPaint);
					}
				 
			 }
			 if(XisNum)
			 {
				 if(negative)
						canvas.drawRect(centreh,centrev,hstops.midpointpos,centrev+mBarWidth,bPaint);
					else
						canvas.drawRect(hstops.midpointpos,centrev,centreh,centrev+mBarWidth,bPaint);
				 
			 }
		 }
		 
		 else
		 {
			 if(XisNum)
			 {
				 if(negative)
						canvas.drawRect(centreh,vstops.midpointpos,centreh+mBarWidth,centrev,bPaint);
					else
						canvas.drawRect(centreh,centrev,centreh+mBarWidth,vstops.midpointpos,bPaint);
				 
			 }
			 if(YisNum)
			 {
				 if(negative)
					 
						canvas.drawRect(centreh,centrev,hstops.midpointpos,centrev+mBarWidth,bPaint);
					else
						canvas.drawRect(hstops.midpointpos,centrev,centreh,centrev+mBarWidth,bPaint);
				 
			 }
		 }
		
		
	}

	
	
	private void drawText(Canvas canvas)
	{
		
		
		exponent = new ArrayList<Integer>();
		
		if(!isHorizontal)
		{
			

			
			
			for(int j=0;j<hstops.axisstops.length;j++)
			{
				if(XisNum)
					
				{		if((hstops.axisstops[j]>=hstops.midpoint))
								CalculateExp(hstops.pointstops,j,tPaint,ticks.nvLabelWidth,hRinterval);
						if((hstops.axisstops[j]<hstops.midpoint))
								CalculateExp(hstops.pointstops,j,tPaint,ticks.nvLabelWidth,hLinterval);
					
				}
				
			}
			
			for(int j=0;j<vstops.axisstops.length;j++)
			{
				if(YisNum)
					
				{
					if((vstops.axisstops[j]>=vstops.midpoint))
					{
				
						
						CalculateExp(vstops.pointstops,j,tPaint,ticks.nvLabelWidth,hRinterval);
					}
					
					if((vstops.axisstops[j]<vstops.midpoint))
					{
						
						CalculateExp(vstops.pointstops,j,tPaint,ticks.nvLabelWidth,hLinterval);
					}
					
					
					
				}
				
			}
			
			Integer [] exps = exponent.toArray(new Integer[exponent.size()]);
			Arrays.sort(exps);
			if(exps.length>0)
			 min = exps[0];
			
			System.out.println("MIN"+min);
			
			
			for(int i=1; i<hstops.axisstops.length;i++)
			{
			
				if(XisCategory)
				{
					
					System.out.println("HVALFLIPPED"+ hflipped);
					
					DrawTextCategory(uniqueX[i-1],canvas,getXLabelName(),coordbounds.bottom,i,hstops,
							coordbounds.left, coordbounds.width(),true,hflipped); 
					
					
					
//					DrawTextCategory(uniqueX[i-1],canvas,getHLabelName(),coordbounds.bottom,i,hstops,
//					coordbounds.left, coordbounds.width(),true,hflipped); 
					
					
				}
		
			
				else if(XisNum)
			
				{
					
					
					if((hstops.axisstops[i-1]>=hstops.midpoint))
					{
					
						System.out.println("TEXT VAL GT" + hstops.axisstops[i-1] );
					
					if((i-1)%2==0)
					DrawTextNumHExponent(canvas,getXLabelName(),coordbounds.bottom,i-1,hstops 
				
							,coordbounds.left,coordbounds.width(),hRinterval,true,hflipped,min);
					}
					
					if((hstops.axisstops[i-1]<hstops.midpoint))
					{
						
						if((i-1)%2==0)
						
					
						DrawTextNumHExponent(canvas,getXLabelName(),coordbounds.bottom,i-1,hstops 
								
								,coordbounds.left,coordbounds.width(),hLinterval,true,hflipped,min);
						
					}
					
					
				}
				
			}
			
			
			canvas.drawText(getXLabelName(),0,getXLabelName().length(),
					coordbounds.left+coordbounds.width()/2,boxbounds.bottom-(mAxisLabelHeight/2), aPaint);
			
			
			for(int j=1;j<vstops.axisstops.length;j++)
			{
				
				if(YisCategory)
					
				{
					System.out.println("Pos" + j);
					System.out.println(uniqueY[j-1]);
					
					DrawTextCategory(uniqueY[j-1],canvas,getYLabelName(),coordbounds.bottom,j,
							vstops,boxbounds.left, coordbounds.height(),false,vflipped);
					
				}
		
				else if(YisNum)
					
				{
					if((vstops.axisstops[j-1]>=vstops.midpoint))
					{
				
						if((j-1)%2==0)
						DrawTextNumVExponent(canvas,getYLabelName(),coordbounds.bottom,j-1,vstops 
						,boxbounds.left,coordbounds.height(),vRinterval,false,vflipped,min);
					}
					
					if((vstops.axisstops[j-1]<vstops.midpoint))
					{
						if((j-1)%2==0)
						DrawTextNumVExponent(canvas,getYLabelName(),coordbounds.bottom,j-1,vstops 
								,boxbounds.left,coordbounds.height(),vLinterval,false,vflipped,min);
					}
					
					
				 
				}
					
				
			}
			
			canvas.save();
			canvas.rotate((float)(90));
			System.out.println("CANVAS ");
			canvas.drawText(getYLabelName(),0,getYLabelName().length(),
				coordbounds.bottom-coordbounds.height()/2,boxbounds.left, aPaint);
			canvas.restore();
			

			if(YisNum)
			{
				
				if(min!=0)
					drawExponentVertical(canvas,coordbounds.bottom,min,boxbounds.left,coordbounds.height());
			}
			
			if(XisNum)
			{
				
				if(min!=0)
					drawExponentHorizontal(canvas,coordbounds.bottom,min,coordbounds.left,coordbounds.width()); 
			}
			
			
		}
		else
		{
			

			for(int j=0;j<vstops.axisstops.length;j++)
			{
				if(XisNum)
					
				{
					if((vstops.axisstops[j]>=vstops.midpoint))
					CalculateExp(vstops.pointstops,j,tPaint,ticks.nvLabelWidth,vRinterval);
					if((vstops.axisstops[j]<vstops.midpoint))
						CalculateExp(vstops.pointstops,j,tPaint,ticks.nvLabelWidth,vLinterval);
					
				}
				
			}
			
			for(int j=0;j<hstops.axisstops.length;j++)
			{
				if(YisNum)
					
				{		if((hstops.axisstops[j]>=hstops.midpoint))
						 CalculateExp(hstops.pointstops,j,tPaint,ticks.nvLabelWidth,hRinterval);
						if((hstops.axisstops[j]<hstops.midpoint))
							CalculateExp(hstops.pointstops,j,tPaint,ticks.nvLabelWidth,hLinterval);
					
				}
				
			}
			
			Integer [] exps = exponent.toArray(new Integer[exponent.size()]);
			Arrays.sort(exps);
			if(exps.length>0)
			 min = exps[0];
			
			System.out.println("MIN"+min);
			
			
			
			for(int i=1; i< vstops.axisstops.length;i++)
			{
			
				if(XisCategory)
				{
					DrawTextCategory(uniqueX[i-1],canvas,getXLabelName(),coordbounds.bottom,
							i,vstops,
							boxbounds.left, coordbounds.height(),false,vflipped); 
				}
				
				 else if(XisNum)
				{
					if((vstops.axisstops[i-1]>=vstops.midpoint))
					{
						
						System.out.println("TEXT VAL GT" + vstops.pointstops[i-1] );
						
					if((i-1)%2==0)	
					DrawTextNumVExponent(canvas,getXLabelName(),coordbounds.bottom,i-1,vstops 
							,boxbounds.left,coordbounds.height(),vRinterval,false,vflipped,min);
					}
					
					if((vstops.axisstops[i-1]<vstops.midpoint))
						
					{
						System.out.println("TEXT VAL LS" + vstops.pointstops[i-1] );
						if((i-1)%2==0)
						DrawTextNumVExponent(canvas,getXLabelName(),coordbounds.bottom,i-1,vstops 
							,boxbounds.left,coordbounds.height(),vLinterval,false,vflipped,min);
					}
					
					
				}
				
			}
			
			canvas.save();
			canvas.rotate((float)(90));
			System.out.println("CANVAS ");
			canvas.drawText(getXLabelName(),0,getXLabelName().length(),
				coordbounds.bottom-coordbounds.height()/2,boxbounds.left, aPaint);
			canvas.restore();
			
			
			for(int i=1; i< hstops.axisstops.length;i++)
			{
			
				if(YisCategory)
			
				{
				
					DrawTextCategory(uniqueY[i-1],canvas,getYLabelName(),coordbounds.bottom,i,hstops,
					coordbounds.left, coordbounds.width(),true,hflipped); 
					
					System.out.println("VVALFLIPPED"+ hflipped);
				}
			
				else if(YisNum)
			
				{
					if((hstops.axisstops[i-1]>=hstops.midpoint))
					{
						if((i-1)%2==0)
						DrawTextNumHExponent(canvas,getYLabelName(),coordbounds.bottom,i-1,hstops 
						,coordbounds.left,coordbounds.width(),hRinterval,true,hflipped,min);
					}
				
					if((hstops.axisstops[i-1]<hstops.midpoint))
					{
						if((i-1)%2==0)
						DrawTextNumHExponent(canvas,getYLabelName(),coordbounds.bottom,i-1,hstops 
					,coordbounds.left,coordbounds.width(),hLinterval,true,hflipped,min);
					}
					
				}
				
			}
			
			if(XisNum)
			{
				
				if(min!=0)
					drawExponentVertical(canvas,coordbounds.bottom,min,boxbounds.left,coordbounds.height());
			}
			
			if(YisNum)
			{
				
				if(min!=0)
					drawExponentHorizontal(canvas,coordbounds.bottom,min,coordbounds.left,coordbounds.width()); 
			}
			
			canvas.drawText(getYLabelName(),0,getYLabelName().length(),
					coordbounds.left+coordbounds.width()/2,
					boxbounds.bottom-(mAxisLabelHeight/2), aPaint);
		}
		
		
	}
	
	
	
	private void drawAxes(Canvas canvas) {
		
		float [] hposstops = new float[hstops.axisstops.length];
		float [] vposstops= new float[vstops.axisstops.length];
		
	
		System.out.println("VPL" + Viewport.left+"VPR" + Viewport.right+ "A_HMIN"+ AXIS_H_MIN+
				"A_HMAX"+ AXIS_H_MAX+"VPT"+Viewport.top+"VPB"+ Viewport.bottom);
		
		System.out.println("COL" + coordbounds.left+"COR" + coordbounds.right);
		
		
		/*for(int i=0; i< hstops.axisstops.length;i++)
		{
			
			hposstops [i] = (float) (coordbounds.left + coordbounds.width()*
					(hstops.axisstops[i]-Viewport.left)/Viewport.width());
			
			System.out.println("HAXIS" + hstops.axisstops[i]);
			System.out.println("HPOS" + hposstops[i]);
			
		}
		
		System.out.println("CB"+ coordbounds.bottom+vstops.axisstops.length);
		
		
		for(int j=0;j<vstops.axisstops.length;j++)
		{
			
			System.out.println("VAXIS" + vstops.axisstops[j]);
			vposstops [j] = (float) (coordbounds.bottom - coordbounds.height()*
					(vstops.axisstops[j]-(Viewport.top))/Viewport.height());
			
			System.out.println("HPOS" + vposstops[j]);
			
			
			
		}*/
		
		
		if(!isHorizontal)
		{
			
			System.out.println("BAR SPACE"+barspace);
			if(XisCategory & YisNum)
			{
				for(int i=0; i< hstops.axisstops.length;i++)
				{
					hposstops [i] = (float) (coordbounds.left + barspace*
							(hstops.axisstops[i]-Viewport.left));
					System.out.println(hposstops[i]+"HposStops");
				}
				
				for(int j=0;j<vstops.axisstops.length;j++)
				{
					vposstops [j] = (float) (coordbounds.bottom - coordbounds.height()*
							(vstops.axisstops[j]-Viewport.top)/Viewport.height());
					
					
					System.out.println(vposstops[j]+"VposStops");
				}
			}
			
			
			if(YisCategory & XisNum)
			{
				for(int i=0; i< hstops.axisstops.length;i++)
				{
					hposstops [i] = (float) (coordbounds.left + coordbounds.width()*
							(hstops.axisstops[i]-Viewport.left)/Viewport.width());
					System.out.println(hposstops[i]+"HposStops");
				}
				
				for(int j=0;j<vstops.axisstops.length;j++)
				{
					vposstops [j] = (float) (coordbounds.bottom - barspace*
							(vstops.axisstops[j]-Viewport.top));
					
					
					System.out.println(vposstops[j]+"VposStops");
				}
			}
			
			
				
		
		}
		
		else
			
		{
			if(YisCategory & XisNum)
			{
				for(int i=0; i< hstops.axisstops.length;i++)
				{
					hposstops [i] = (float) (coordbounds.left + barspace*
							(hstops.axisstops[i]-Viewport.left));
					System.out.println(hposstops[i]+"HposStops");
				}
				
				for(int j=0;j<vstops.axisstops.length;j++)
				{
					vposstops [j] = (float) (coordbounds.bottom - coordbounds.height()*
							(vstops.axisstops[j]-Viewport.top)/Viewport.height());
					
					
					System.out.println(vposstops[j]+"VposStops");
				}
				
			}
			
			if(XisCategory & YisNum)
			{
				for(int i=0; i< hstops.axisstops.length;i++)
				{
					hposstops [i] = (float) (coordbounds.left + coordbounds.width()*
							(hstops.axisstops[i]-Viewport.left)/Viewport.width());
					System.out.println(hposstops[i]+"HposStops");
				}
				
				for(int j=0;j<vstops.axisstops.length;j++)
				{
					vposstops [j] = (float) (coordbounds.bottom - barspace*
							(vstops.axisstops[j]-Viewport.top));
					
					
					System.out.println(vposstops[j]+"VposStops");
				}
				
			}
			
		}
//		
		 hstops.posstops=hposstops;
		 vstops.posstops=vposstops;
		 //float midpointpos=0.0f;
		 
		 if(!isHorizontal)
		 {
			 if(YisNum)
			 {
				 
				 
				 //midpointpos = vstops.midpoint * (Viewport.bottom-Viewport.top)/(fixedbottom-fixedtop);
				 
				 
				/* vstops.midpointpos=coordbounds.bottom - coordbounds.height()*
							(vstops.midpoint-Viewport.top)/Viewport.height();*/
				 
				 vstops.midpointpos=coordbounds.bottom - coordbounds.height()*
							(midpointpos-Viewport.top)/Viewport.height();
				 
				 
				 canvas.drawLine(coordbounds.left, vstops.midpointpos, 
						 coordbounds.right, vstops.midpointpos+4, dPaint);
				 
			 }
			 if(XisNum)
			 {
				 
				 
				 //midpointpos = hstops.midpoint * (Viewport.right-Viewport.left)/(fixedright
						// -fixedleft);
				 
				/* hstops.midpointpos=coordbounds.left + coordbounds.width()*
					(hstops.midpoint-Viewport.left)/Viewport.width();*/
				 
				 hstops.midpointpos=coordbounds.left + coordbounds.width()*
							(midpointpos-Viewport.left)/Viewport.width();
				 
				 
				 canvas.drawLine(hstops.midpointpos, coordbounds.top,
						 hstops.midpointpos+4,coordbounds.bottom, dPaint);
				 
			 }
		 }
		 
		 else
		 {
			 if(YisNum)
			 {
				 
				 
//				 midpointpos = hstops.midpoint * (Viewport.right-Viewport.left)/
//						 (fixedright-fixedleft);
				/* hstops.midpointpos= coordbounds.left + coordbounds.width()*
					(hstops.midpoint-Viewport.left)/Viewport.width();*/
				 
				 hstops.midpointpos= coordbounds.left + coordbounds.width()*
							(midpointpos-Viewport.left)/Viewport.width();
				 
				 canvas.drawLine(hstops.midpointpos, coordbounds.top
						 ,hstops.midpointpos+4,coordbounds.bottom, dPaint);
				 
			 }
			 if(XisNum)
			 {
				/* vstops.midpointpos=coordbounds.bottom - coordbounds.height()*
					(vstops.midpoint-Viewport.top)/Viewport.height();*/
				 
//				 midpointpos = vstops.midpoint * (Viewport.bottom-Viewport.top)/
//						 (fixedbottom-fixedtop);
//				 
				 vstops.midpointpos=coordbounds.bottom - coordbounds.height()*
							(midpointpos-Viewport.top)/Viewport.height();
				 
				 canvas.drawLine(coordbounds.left, vstops.midpointpos, 
						 coordbounds.right, vstops.midpointpos+4, dPaint);
				 
			 }
		 }
		 
		 
		 if(!isHorizontal)
			{
				if(XisCategory)
				{
					AxisHGridLines = lines.drawHorizontalGridCategory(hstops,coordbounds);
					
				}
				
				else if (XisNum)
				{
					if(isGridShown())
					AxisHGridLines = lines.drawHorizontalGrid(hstops,coordbounds,true);
					else
					AxisHGridLines = lines.drawHorizontalGrid(hstops,coordbounds,false);
				}
				
			}
			
			else
			{
				if(YisCategory)
				{
					AxisHGridLines = lines.drawHorizontalGridCategory(hstops,coordbounds);
					
				}
				
				else if (YisNum)
				{
					if(isGridShown())
					AxisHGridLines = lines.drawHorizontalGrid(hstops,coordbounds,true);
					else
					AxisHGridLines = lines.drawHorizontalGrid(hstops,coordbounds,false);
				}
				
			}
			
		 
		 
			
			canvas.drawLines(AxisHGridLines, 0,AxisHGridLines.length,gPaint);
			
			
			if(isGridShown())
				
			{
					

	
					//canvas.drawLines(AxisVGridLines, 0,(vstops.posstops.length-1)*4,gPaint);
					
				if(!isHorizontal)
				{
					if(YisCategory)
					{
						AxisVGridLines = lines.drawVerticalGridCategory(vstops, coordbounds, false);
						
					}
					
					else if (YisNum)
					{
						AxisVGridLines = lines.drawVerticalGrid(vstops, coordbounds, true);
					}
					
				}
				
				else
				{
					if(XisCategory)
					{
						AxisVGridLines = lines.drawVerticalGridCategory(vstops, coordbounds, false);
						
					}
					
					else if (XisNum)
					{
						AxisVGridLines = lines.drawVerticalGrid(vstops, coordbounds, true);
					}
					
				}
					
					
					canvas.drawLines(AxisVGridLines, 0,AxisVGridLines.length,gPaint);
			}
			
			else
				
			{
				
				if(!isHorizontal)
				{
					if(YisCategory)
					{
						AxisVGridLines = lines.drawVerticalGridCategory(vstops, coordbounds, false);
						
					}
					
					else if (YisNum)
					{
						AxisVGridLines = lines.drawVerticalGrid(vstops, coordbounds, false);
					}
					
				}
				
				else
				{
					if(XisCategory)
					{
						AxisVGridLines = lines.drawVerticalGridCategory(vstops, coordbounds, false);
						
					}
					
					else if (XisNum)
					{
						AxisVGridLines = lines.drawVerticalGrid(vstops, coordbounds, false);
					}
					
				}
					

					//canvas.drawLines(AxisVGridLines, 0,(vstops.posstops.length-1)*4,gPaint);
					canvas.drawLines(AxisVGridLines, 0,AxisVGridLines.length,gPaint);
			}	
			
		 
		
			
			
		
	}



	private void getXCategory()
	{
		 List<String> uniX= new ArrayList<String>();
		 for(int i=0; i<series.size();i++)
		 {
				
			uniX.add(series.get(i).getXLabel());
		 }		
		Set<String> uxVal= new HashSet<String>(uniX);
		uniqueX = uxVal.toArray(new String[uxVal.size()]);
		
		
		
		
	}
	
	private String[] getYCategory()
	{
		 List<String> uniY= new ArrayList<String>();
		 for(int i=0; i<series.size();i++)
		 {
				
			uniY.add(series.get(i).getYLabel());
		 }		
		Set<String> uyVal= new HashSet<String>(uniY);
		uniqueY = uyVal.toArray(new String[uyVal.size()]);
		
		return uniqueY;
	}
	

	private void getXArray()
	{
		
		int a=0,b=0;
		for(int i=0;i<series.size();i++)
		{
			if(series.get(i).getSide())
				b++;
			else
				a++;
			
			
		}
		LmaxvalX =  new double[b];
		RmaxvalX =  new double[a];
		 
		int c=0,d=0;
		for(int i=0;i<series.size();i++)
		{
				if(series.get(i).getSide())
				{
				LmaxvalX[c]=series.get(i).getXVal();
				c++;
				}
				else
				{
				RmaxvalX[d]=series.get(i).getXVal();	
				d++;
				}
				
		}
		
		Arrays.sort(LmaxvalX);
		Arrays.sort(RmaxvalX);
		
	}
	
	private void getYArray()
	{
		
		
		
		
		int a=0,b=0;
		for(int i=0;i<series.size();i++)
		{
			if(series.get(i).getSide())
				b++;
			else
				a++;
			
			
		}
		LmaxvalY =  new double[b];
		RmaxvalY =  new double[a];
		 
		int c=0,d=0;
		for(int i=0;i<series.size();i++)
		{
				if(series.get(i).getSide())
				{
				LmaxvalY[c]=series.get(i).getYVal();
				c++;
				}
				else
				{
				RmaxvalY[d]=series.get(i).getYVal();	
				d++;
				}
				
		}
		
		Arrays.sort(LmaxvalY);
		Arrays.sort(RmaxvalY);
		
	}
	
	public final ScaleGestureDetector.OnScaleGestureListener mScaleGestureListener
	  = new ScaleGestureDetector.SimpleOnScaleGestureListener()
	 {
		 
		 private PointF viewportFocus = new PointF();
		 private float lastSpanX;
		 private float lastSpanY;
		 
		 @Override 
		 public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector)
		 {
			 lastSpanX = ScaleGestureDetectorCompat.getCurrentSpanX(scaleGestureDetector);
			 lastSpanY = ScaleGestureDetectorCompat.getCurrentSpanY(scaleGestureDetector);
			 
			
			return true;
		 }
		 
		 @Override
		 public boolean onScale(ScaleGestureDetector scaleGestureDetector)
		 {
			 float spanX =ScaleGestureDetectorCompat.getCurrentSpanX(scaleGestureDetector);
			 float spanY =ScaleGestureDetectorCompat.getCurrentSpanY(scaleGestureDetector);
		
			 float newWidth = (lastSpanX/spanX) * Viewport.width();
			 float newHeight = (lastSpanY/spanY) * Viewport.height();
	 
			 float focusX = scaleGestureDetector.getFocusX();
			 float focusY = scaleGestureDetector.getFocusY();
			 

			 hitTest(focusX,focusY,viewportFocus);

//			 
			 Viewport.set(viewportFocus.x - newWidth *
				 (focusX-coordbounds.left)/coordbounds.width(),viewportFocus.y- 
				 newHeight * (coordbounds.bottom-focusY)/coordbounds.height(),0,0);
		 	Viewport.right = Viewport.left+newWidth;
		 	Viewport.bottom = Viewport.top + newHeight;
		
		   constrainViewport();
		   ViewCompat.postInvalidateOnAnimation(NegativeStackBarChart.this);
		 
		   lastSpanX = spanX;
		   lastSpanY = spanY;
			return true;
		 }
	 };
	 
	 
	 
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
		 
		 boolean retVal = mScaleGestureDetector.onTouchEvent(event);
		 retVal = mGestureDetector.onTouchEvent(event)||retVal;
		 return retVal || super.onTouchEvent(event);
	 }
	
	  public final GestureDetector.SimpleOnGestureListener mGestureListener =
			  new GestureDetector.SimpleOnGestureListener()
	  {
		  
		  @Override
		  
		  public boolean onDown(MotionEvent e)
		  {
			 releaseEdgeEffects();
			 //ViewCompat.postInvalidateOnAnimation(BubblePlot.this);
			
			return true;
		
		
		  }
		  @Override
		  
		  public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,float distanceY)
		  {
			 
			  
			  float viewportOffsetH = distanceX*(Viewport.width()/coordbounds.width());
			 
			  
			  float viewportOffsetV= -distanceY*Viewport.height()/coordbounds.height();
			  
			 
			  
			  computeScrollSurfaceSize(ScrollableSurface);
			  
			  int ScrolledH =(int) (ScrollableSurface.x * (Viewport.left + 
					  (viewportOffsetH-AXIS_H_MIN))/(AXIS_H_MAX-AXIS_H_MIN));
			  
			  int ScrolledV =(int) ((ScrollableSurface.y * (AXIS_V_MAX - Viewport.bottom -viewportOffsetV))/
					  (AXIS_V_MAX-AXIS_V_MIN));
			  
			  boolean canScrollH = Viewport.left>AXIS_H_MIN || Viewport.right<AXIS_H_MAX;
			  boolean canScrollV= Viewport.top > AXIS_V_MIN || Viewport.bottom<AXIS_V_MAX;
			  
			  setViewportBottomLeft(Viewport.left+viewportOffsetH,Viewport.bottom + viewportOffsetV);
			  
			  if(canScrollH && ScrolledH<0)
			  {
				  mEdgeEffectLeft.onPull(ScrolledH/(float)coordbounds.width());
				  mEdgeEffectLeftActive=true;
			  }
					  
			  
			  if(canScrollV && ScrolledV<0)
			  {
				  mEdgeEffectTop.onPull(ScrolledV/(float)coordbounds.height());
				  mEdgeEffectTopActive=true;
			  }
			  
			  if(canScrollH && ScrolledH > ScrollableSurface.x - coordbounds.width())
			  {
				  mEdgeEffectRight.onPull((ScrolledH-ScrollableSurface.x+ coordbounds.width())
						  /(float)coordbounds.width());
				  mEdgeEffectRightActive=true;
			  }
			  
			  if(canScrollV && ScrolledV > ScrollableSurface.y - coordbounds.height())
			  {
				  mEdgeEffectBottom.onPull((ScrolledV-ScrollableSurface.y+ coordbounds.height())
						  /(float)coordbounds.height());
				  mEdgeEffectBottomActive=true;
			  }
			return true;
		  }

		
	  };
		
		
	  private void computeScrollSurfaceSize(Point out) {
		  out.set(
				  
				  (int)(coordbounds.width()*((AXIS_H_MAX-AXIS_H_MIN))/Viewport.width()),
				  
				  (int)(coordbounds.height()*((AXIS_V_MAX-AXIS_V_MIN)/Viewport.height()))
				  
				  );
		 		
		}
	
	
	
	  protected void constrainViewport() {
			
		  Viewport.left = Math.max(AXIS_H_MIN,Viewport.left);
		  Viewport.top = Math.max(AXIS_V_MIN,Viewport.top);
		  Viewport.bottom = Math.max(Math.nextUp(Viewport.top),Math.min(AXIS_V_MAX,Viewport.bottom));
		  Viewport.right = Math.max(Math.nextUp(Viewport.left),Math.min(AXIS_H_MAX,Viewport.right));
	}

	  
	  
	protected boolean hitTest(float x, float y, PointF viewportFocus) {
		
		  if(!coordbounds.contains((int)x,(int)y))
		  {
			  return false;
		  }
		  
		  else
		  { 
		
		  
		  viewportFocus.set(Viewport.left+(Viewport.width() *
				  (x - coordbounds.left)/coordbounds.width()),
				 Viewport.top + (Viewport.height() * (y - coordbounds.bottom)/-coordbounds.height()));
		 
		  return true;
		  }
		  
		
	}

	protected void releaseEdgeEffects() {
		
		  mEdgeEffectLeftActive = 
				  mEdgeEffectRightActive=
				  mEdgeEffectTopActive=
				  mEdgeEffectBottomActive=
				  false;
		  
		  mEdgeEffectLeft.onRelease();
		  mEdgeEffectRight.onRelease();
		  mEdgeEffectTop.onRelease();
		  mEdgeEffectBottom.onRelease();
				  
		
	}


	private void setViewportBottomLeft(float x,float y) {
			
		  
		  float curWidth = Viewport.width();
		 
		  float curHeight = Viewport.height();
		  x= Math.max(AXIS_H_MIN,Math.min(x, AXIS_H_MAX-curWidth));
	      y = Math.max(AXIS_V_MIN +curHeight,Math.min(y, AXIS_V_MAX));
	      
	      Viewport.set(x,y-curHeight,x + curWidth,y);
	      ViewCompat.postInvalidateOnAnimation(this);
		}
	
	
	
	public void drawEdgeEffectsUnclipped(Canvas canvas)
	{
		boolean needsInvalidate = false;
		
		if(!mEdgeEffectTop.isFinished())
		{
			final int restoreCount = canvas.save();
			canvas.translate(coordbounds.left, coordbounds.top);
			mEdgeEffectTop.setSize((int)coordbounds.width(),(int)coordbounds.height());
			if(mEdgeEffectTop.draw(canvas))
			{
				needsInvalidate = true;
			}
			
			canvas.restoreToCount(restoreCount);
			
		}
		
		
		
		if(!mEdgeEffectBottom.isFinished())
		{
			final int restoreCount = canvas.save();
			canvas.translate(2*coordbounds.left - coordbounds.right, coordbounds.bottom);
			canvas.rotate(180, coordbounds.width(), 0);
			mEdgeEffectBottom.setSize((int)coordbounds.width(),(int)coordbounds.height());
			if(mEdgeEffectBottom.draw(canvas))
			{
				needsInvalidate = true;
			}
			
			canvas.restoreToCount(restoreCount);
			
		}
		
		if(!mEdgeEffectLeft.isFinished())
		{
			final int restoreCount = canvas.save();
			canvas.translate(coordbounds.left, coordbounds.bottom);
			canvas.rotate(-90,0,0);
			mEdgeEffectLeft.setSize((int)coordbounds.height(),(int)coordbounds.width());
			if(mEdgeEffectLeft.draw(canvas))
			{
				needsInvalidate = true;
			}
			
			canvas.restoreToCount(restoreCount);
			
		}
		
		if(!mEdgeEffectRight.isFinished())
		{
			final int restoreCount = canvas.save();
			canvas.translate(coordbounds.right, coordbounds.top);
			canvas.rotate(90,0,0);
			mEdgeEffectRight.setSize((int)coordbounds.height(),(int)coordbounds.width());
			if(mEdgeEffectRight.draw(canvas))
			{
				needsInvalidate = true;
			}
			
			canvas.restoreToCount(restoreCount);
			
		}
		
		if(needsInvalidate)
		{
			ViewCompat.postInvalidateOnAnimation(this);
		}
	}
	
	
	
	private String[] formatfloat(float floatvalue, double interval, 
			Paint tPaint,int nLabelWidth) {
		
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
		
		System.out.println("NUMSIG"+ numsig);
		
		 String append = "#.";
		 
		 String nodec = "#";
		 
		 for(int i=0; i<numsig;i++)
			 
			 append+="#";
		 
		 System.out.println("FLOATVAL" + floatvalue);
		 //TODO Find an alternative toDecimalFormat
		 if(floatvalue%1>0)
			 numstring = new DecimalFormat(append).format(floatvalue);
		 else
			 numstring = new DecimalFormat(nodec).format(floatvalue);
		 
		 
		 System.out.println("NUMSTR" + numstring);
		  float minLength = tPaint.measureText("0");
		 
		  
		  System.out.println("MINLEN" + minLength);
		  
		  
		  
		    int numchars = (int) Math.floor(nLabelWidth/minLength);
//		    
		    int trimsig =0;
		    int exp=0;
			if(numstring.length()>numchars)
			{
				if(floatvalue!=0)
				exp =(int) Math.floor(Math.log10
						(floatvalue));
				else
				exp=0;
				
				System.out.println("EXP" + exp);
				
				float value  = (float)(floatvalue
						/Math.pow(10,exp));
				
				System.out.println("FLOAT V" + value);
				
				float sigint = (float) (interval/Math.pow(10,exp));
				if(sigint<1)
				{
					if(sigint!=0)
					trimsig =(int)Math.ceil(-Math.log10(sigint));
					
				}
//				
				if(sigint>1)
				{
					if(sigint!=0)
					trimsig =(int)Math.ceil(Math.log10(sigint));
					
					
				}
				
				String pattern = "#.";
				 for(int i=0; i<trimsig;i++)
					 pattern+="#";
				numstring = new DecimalFormat(pattern).format(value);
				
				System.out.println("FLOAT NUMSTR" + numstring);
			}
	//	
		String [] strings = new String[2];
		strings[0]=numstring;
		System.out.println("EXP" + exp);
		if(exp!=0)
			strings [1] = Integer.toString(exp);
		return strings;
		
		
		
		
		
	}
	

	
	public void DrawTextCategory(String label,Canvas canvas,String axisLabel,float 
			startposV,int pos,AxisStops 
			stops,float startposH,float dim,Boolean horizontal,Boolean flipped)
	{
		 
		 float minLength = tPaint.measureText("0");
		 
		 float actLength= label.length() * minLength;
		 
		 int numchar=0;
		 int numaxischar =0;
		 
		 if(nLabelWidth - actLength<0)
		 {
			numchar =(int) Math.ceil((nLabelWidth-actLength)/minLength);
		 }
		 
		 if(horizontal)
		 {
		 /*	float y = startposV- (2 * mAxisLabelHeight +aPaint.ascent()+subLabelHeight/2);*/
			 
			float y = startposV+  (2* mAxisLabelHeight +aPaint.ascent()+ subLabelHeight/2);
			 
			
		 	System.out.println(stops.posstops[pos]);
		 	
		 	if(stops.posstops[pos]>=coordbounds.left)
		 	{
		 		if(flipped)
		 		{
		 			System.out.println(" H  FLIPPED" + subLabelHeight/2);
		 			
		 			float yy = startposV + subLabelHeight/2;
		 			
		 			
		 			canvas.save();
		 			canvas.rotate((float)(90));
		 			
		 			System.out.println("H" +"START" + stops.posstops[pos]);
		 			
		 			System.out.println("H" +"LEFT" + (coordbounds.left  - coordbounds.width()/2));
		 			
		 			
		 			canvas.drawText(label,0,label.length(),
		 					yy,-stops.posstops[pos], tPaint);
		 			

		 			canvas.restore();
		 			
		 			
					
		 		}
		 		else
		 		{
		 			
		 			canvas.drawText(label,0,label.length(),
							
			 				stops.posstops[pos],y, tPaint);
		 		}
			
		 	}
		 	
			
			
			
			
		 }
		 
		 					
		 
		 else
		 {
			 
			 if(flipped)
			 {
			 
					
				 		
				 		float x = startposH-2*mAxisLabelHeight-(-aPaint.ascent());
				 			if(stops.posstops[pos]<=coordbounds.bottom)
						 	{
				 			canvas.save();
				 			canvas.rotate((float)(90));
				 			System.out.println("X"+ x + "STOPS" + stops.posstops[pos]);
				 			canvas.drawText(label,0,label.length(),
									stops.posstops[pos],x, tPaint);
				 			canvas.restore();
				 			
						 	}
				 			
				 		
			 	
			 }
			 
			 else
			 {
				 float x = startposH+ 2 * mAxisLabelHeight + aPaint.ascent() + nLabelWidth/2;
				 
				 	if(stops.posstops[pos]<=coordbounds.bottom)
					canvas.drawText(label,0,label.length(),
							x,stops.posstops[pos], tPaint);
			 }
			 
			 
			 	
				
			
			 
		 }
			
			
	}
	
	
	
	private void assignColors()
	{
		getSeriesNames();
		if(assignSeriesColors)
		{
			System.out.println("ASSIGN SERIES");
			calculateColors();
			
		}
		else 
			calculateColorsFromSeries();
	}
	

	public void setSeries(List<MultipleSeriesItem> series)
	{
		this.series=series;
		
		
		
		
		
		
	}
	
	
	
	private void calculateColors() {
		
		ColorCode colorcode = new ColorCode();
		setcolors =new int[2];
		 
		 
		if(useSeriesPrimary)
			setcolors = colorcode.setPrimaryColor(2,100,0.8f,0.9f);
		else
			
			setcolors = colorcode.setHSVAccent(accentprimarycolor,2,0.5f,1.0f, 20);
		
		System.out.println("SET1" + setcolors[0]+"SET2"+ setcolors[1]);
		
		
		
		
		for(int i=1; i<=2;i++)
		{
			seriescolor.put(seriesname[i-1], setcolors[i-1]);
			System.out.println("SERIES" + i  + seriescolor.get(seriesname[i-1]));
			
		}
		
		for(int i=0; i<2;i++)
		{
			for(int j=0; j<series.size();j++)
			{
				System.out.println("SERSET" + series.get(j).getSeries());
				if(series.get(j).getSeriesname()==seriesname[i])
				{
					System.out.println("SER" + i+1);
					series.get(j).setColor(seriescolor.get(series.get(j).getSeriesname()));
					
					
				}
					
			}
		}
		
	}
	
	private void getSeriesNames() {
		
		
		List<String> names = new ArrayList<String>();
		for(int i=0;i<series.size();i++)
		{
			names.add(series.get(i).getSeriesname());
		}
		
		Set<String> sernames = new HashSet<String>(names);
		 seriesname= sernames.toArray(new String[sernames.size()]);
		
	}

	private void calculateColorsFromSeries() {
		
		List<Integer> colors = new ArrayList<Integer>();
		for(int i=0;i<series.size();i++)
		{
			colors.add(series.get(i).getColor());
		}
		
		 cols= new HashSet<Integer>(colors).toArray(new Integer[2]);
		
		 
		 for(int i=0;i<series.size();i++)
		 {
			
			 System.out.println("SERIES"+ series.get(i).getSeries()+
					 
					 series.get(i).getColor());
			 
			 
			 seriescolor.put(series.get(i).getSeriesName(),series.get(i).getColor());
			
			 
		 }
		
		
		
	}
	
	
	/**
	 * 
	 * @param xLabelName set label for x-axis
	 */
	public void setXLabelName(String xLabelName)
	{
		this.xLabelName =xLabelName;
	}
	
	public String getXLabelName()
	{
		return xLabelName;
	}
	
	/**
	 * 
	 * @param yLabelName set label for y-axis
	 */
	public void setYLabelName(String yLabelName)
	{
		this.yLabelName =yLabelName;
	}
	
	public String getYLabelName()
	{
		return yLabelName;
	}
	
	
	/**
	 * 
	 * @param dividercolor set color of divider between positive and negative sides
	 */
	public void setDividerColor(int dividercolor)
	{
		this.dividercolor =dividercolor;
	}
	
	public int getDividerColor()
	{
		return dividercolor;
	}
	
	
	//******************************//
	
	
	
	

	private String formatfloatValue(double value, double interval,
			Paint tPaint, int nLabelWidth) {
		
   String numstring ;
	int numsig=0;
		
    System.out.println("Calculating Interval"+ interval);
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
	
	private void CalculateExp(double [] stops , int i,Paint tPaint,int nLabelWidth,double interval) {
		
		
		
		System.out.println("NLABELWIDTH" + nLabelWidth);
		System.out.println("INTERVAL" + interval);
		System.out.println("STOPS" + stops[i]);
		
		String[] outstring = formatfloat((float)stops[i],
				interval,tPaint,nLabelWidth);
		
		if(outstring[1]!=null)
	{
		System.out.println("OUTSTRING" + outstring[1]);
		exponent.add(Integer.parseInt(outstring[1]));
		
	}
		
	}
	
	
	
	/*public void drawExponentHorizontal(Canvas canvas,float startposV,int exponent,float startposH,float dim)
	{
		 
			
			String string = "x10";
			 				
			
			 				
			 				
			 canvas.drawText(string,0,string.length(),
					
			 		startposH + dim/2,startposV+mAxisLabelHeight/2+subLabelHeight, 
			 		aPaint);
			
			 canvas.drawText(Integer.toString(exponent),0,Integer.toString(exponent).length(),
			 						
			 	startposH + dim/2 +aPaint.measureText(string)/2+8,
				
			 	startposV + mAxisLabelHeight/2+ subLabelHeight +aPaint.ascent()+mLabelSeparation , aPaint);
			 	
			 System.out.println("AXIS LABEL"+ (startposV + mAxisLabelHeight+ subLabelHeight +aPaint.ascent()));

		
	}*/
	
	public void drawExponentHorizontal(Canvas canvas,float startposV,int exponent,float startposH,float dim)
	{
		 
			
			String string = "x10";
			 				
			
			System.out.println("SUB EXP"+subLabelHeight);
			 				
			 				
			 /*canvas.drawText(string,0,string.length(),
					
			 		startposH + dim/2,startposV+mAxisLabelHeight/2+subLabelHeight, 
			 		aPaint);*/
			
			aPaint.setTextAlign(Paint.Align.CENTER);
			canvas.drawText(string,0,string.length(),
					
			 		startposH + dim/2,startposV+subLabelHeight+mAxisLabelHeight+mLabelSeparation, 
			 		aPaint);
			
			canvas.drawText(Integer.toString(exponent),0,Integer.toString(exponent).length(),
						
				 	startposH + dim/2 +aPaint.measureText(string)/2+8,
					
				 	startposV + mAxisLabelHeight+ subLabelHeight+aPaint.ascent()/2+mLabelSeparation , aPaint);
			
			 /*canvas.drawText(Integer.toString(exponent),0,Integer.toString(exponent).length(),
			 						
			 	startposH + dim/2 +aPaint.measureText(string)/2+8,
				
			 	startposV + mAxisLabelHeight/2+ subLabelHeight+aPaint.ascent()+mLabelSeparation , aPaint);*/
			 	
			 System.out.println("AXIS LABEL"+ (startposV + mAxisLabelHeight+ subLabelHeight +aPaint.ascent()));
			 				
			
			 				
		
		
	}
	
	
	
	
	
	public void DrawTextNumVExponent(Canvas canvas,String axisLabel,float startposV,int pos,AxisStops 
			stops,float startposH,float dim,double interval,Boolean horizontal,Boolean flipped,int min)
	{
		 
		  String outstring = formatfloatValue(stops.pointstops[pos]/Math.pow(10,min),interval,tPaint,ticks.nvLabelWidth);
	 
		  float x = startposH+ 2 * mAxisLabelHeight- aPaint.ascent() +  ticks.nvLabelWidth/2;
				 
				 
				 
		 if(stops.posstops[pos]<=coordbounds.bottom)
		 {
					 
					 //if(pos<stops.posstops.length-1)
						 canvas.drawText(outstring,0,outstring.length(), 
								 x,stops.posstops[pos]+tPaint.descent()/2, tPaint);
		}
				 
				 
	}
	
	
	public void DrawTextNumHExponent(Canvas canvas,String axisLabel,float startposV,int pos,AxisStops 
			stops,float startposH,float dim,double interval,Boolean horizontal,Boolean flipped,int min)
	{
		
	
		 
		 
		 
		  
		 	
			 if(horizontal)
			 {
					 
				 System.out.println("SUBLABELHT" + subLabelHeight);
				 
				 float y = startposV+subLabelHeight/2+mLabelSeparation;
				 
				 if(stops.posstops[pos]>=coordbounds.left)
				 {
					 
					 if(flipped)
				 		{
						   
						 
						 String outstring = formatfloatValue(stops.pointstops[pos]/Math.pow(10,min),interval,tPaint,subLabelHeight);
						 
						 
						 //float yy = startposV + subLabelHeight/2+mLabelSeparation;
				 			
				 		
						  float yy = startposV+mLabelSeparation;
				 			
						 	//float yval = startposV -(2 * mAxisLabelHeight + aPaint.ascent() + subLabelHeight/2);
						 	System.out.println("V FLIPPED");
				 			canvas.save();
				 			canvas.rotate((float)(90));
				 			/*canvas.drawText(outstring,0,outstring.length(), 
									 stops.posstops[pos],yval, tPaint);*/
				 			
				 			 //if(pos<stops.posstops.length-1)
				 			canvas.drawText(outstring,0,outstring.length(), yy,
									 -stops.posstops[pos]+tPaint.descent()/2, fPaint);
				 			
				 			canvas.restore();
				 		}
				 		else
				 		{
				 			
				 			String outstring = formatfloatValue(stops.pointstops[pos]/Math.pow(10,min),interval,tPaint,ticks.nhLabelWidth);
				 			System.out.println("UNFLIPPPED");
				 			 //if(pos<stops.posstops.length-1)
				 			canvas.drawText(outstring,0,outstring.length(), 
									 stops.posstops[pos],y, fPaint);
				 		}
					 
				 
				 
				 }
				 
				 
			
			 }
			
//			
			
		 
		 
	}
	
	public void drawExponentVertical(Canvas canvas,float startposV,int exponent,float startposH,float dim)
	{
		 
			canvas.save();
			String string = "x10";
			 				
			 canvas.rotate(90);
			 				
			 				
			
			 
			 
			 
			 canvas.drawText(string,0,string.length(),
						
				 		startposV - dim/2,startposH-mAxisLabelHeight-mAxisLabelHeight/2, 
				 		aPaint);
			 
			 canvas.drawText(Integer.toString(exponent),0,Integer.toString(exponent).length(),
						
					 	startposV - dim/2 - aPaint.ascent(),
							
					 	startposH-mAxisLabelHeight-mAxisLabelHeight/2-aPaint.measureText(string)/2+2,aPaint);
			 
			 
			 
			
			 				
			canvas.restore();
			 				
		
		
	}
	
	
	


	public void getChosenPoint()
    {
    	
		for(int i = 0; i< barposition.length;i++)
		{
			System.out.println("LEFT" + barposition[i].getLeftEdge()+ 
					"RIGHT"+barposition[i].getRightEdge());
			
			System.out.println("TOP" + barposition[i].getTopEdge()+ 
					"BOTTOM"+barposition[i].getBottomEdge());
			
			
			if(xpoint>=barposition[i].getLeftEdge()& 
					xpoint<=barposition[i].getRightEdge()& ypoint >=barposition[i].getTopEdge()& ypoint <= barposition[i].getBottomEdge())
			{
				
				System.out.println("TRUE");
				Toast toast = Toast.makeText(context,Double.toString(barposition[i].getValue()) , Toast.LENGTH_SHORT);
		    	toast.setGravity(Gravity.TOP|Gravity.LEFT,(int)
		    			(boxbounds.left+ xpoint),
		    			(int)(boxbounds.top+ ypoint));
		    	toast.show();
		    	;
		    	
			}
		}
    	
    	
    	
    	
    }

	
}
