
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

public class MultipleSeriesBarChart extends BarView {

	
	//***********************************//
	
	
	private ScaleGestureDetector mScaleGestureDetector=null;
	private GestureDetectorCompat mGestureDetector=null;
	private Point ScrollableSurface = new Point();
		
	//************************************//
	Paint labelPaint;
	private EdgeEffectCompat mEdgeEffectTop;
	private EdgeEffectCompat mEdgeEffectBottom;
	private EdgeEffectCompat mEdgeEffectLeft;
	private EdgeEffectCompat mEdgeEffectRight;
	
	Paint fPaint;
	private boolean mEdgeEffectTopActive;
	private boolean mEdgeEffectBottomActive;
	private boolean mEdgeEffectLeftActive;
	private boolean mEdgeEffectRightActive;
	float xpoint,ypoint;
	//************************************//
	
	RectF boxbounds,coordbounds,legendBox;
	private RectF Viewport;
	int min=0;
	//************************************//
	DrawGridLines lines;
	OptimizeTicks tasks;
	List<Integer> exponent;
	BarPosition[] barposition;
	
	
	
	//************************************//
	
	AxisStops hstops,vstops;
	
	
	Paint tPaint,aPaint,rPaint,gPaint,bPaint,lPaint,
	vPaint,
	legendPaint;
	
	//************************************//
	
	
	int primarycolor;
	
	float axisHGridLines,axisVGridLines;
	
	
	int minLabelWidth,minLabelHeight,mLabelWidth,
	mLabelHeight,nLabelWidth,subLabelHeight,nLabelHeight;
	int mAxisLabelHeight,mAxisLabelWidth,nhLabelWidth,nvLabelWidth;
	Context context;
	float axisLabelSpace;
	
	float mVLabelWidth;

	Boolean hflipped=false,vflipped=false,showLegend=true;
	
	
	Boolean invalidate=false;
	
	RectF colorBox;
	int drawBoxSize;
	
	int [] setcolors;
	Integer [] cols ;
	Map<String,Integer>seriescolor
	= new HashMap<String, Integer> ();
	//************************************//
	
	List<MultipleSeriesItem> series;
	private float AXIS_H_MIN;
	private float AXIS_H_MAX;
	private float AXIS_V_MIN;
	private float AXIS_V_MAX;
	int numseries=1;
	String [] uniqueX,uniqueY,seriesname;
	
	//************************************//
	ReturnIntervalsTicks ticks;
	int nVticks;int nHticks;
	double hInterval, vInterval;
	
	//************************************//
	
	float [] AxisHGridLines,AxisVGridLines;
	String xLabelName,yLabelName;
	
	
	
	
	//************************************************//
	
	
	public void setSeries(List<MultipleSeriesItem> series,
			int numseries)
	{
		this.series=series;
		this.numseries=numseries;
		
		
		
	}
	
	
	private void assignColors()
	{
		getSeriesNames();
		if(assignSeriesColors)
		{
			calculateColors();
			
		}
		else 
		{
			calculateColorsFromSeries();
			
		}
	}
	


	private void redrawLegendBox(float coww,int drawBoxSize,int mLabelWidth) {
		

		
		
		float coll = (float) boxbounds.height()- subLabelHeight-
				(2*mAxisLabelHeight+3*mLabelSeparation)-(-aPaint.ascent())
				-(legendSize+drawBoxSize);

		
		coordbounds = new RectF(0.0f,0.0f,
				coww,coll);
		
		float xoffset = nvLabelWidth+(2*mAxisLabelHeight)-(-aPaint.ascent())+3*mLabelSeparation;
		
         coordbounds.offsetTo(getPaddingLeft()+ xoffset, getPaddingTop()+mLabelSeparation);
		
		legendBox = new RectF(0.0f,0.0f,coww,legendSize + drawBoxSize);
		
		legendSize  = legendSize+ drawBoxSize;
		
		legendBox.offset(getPaddingLeft()+xoffset,coordbounds.bottom+subLabelHeight+(mAxisLabelHeight-aPaint.ascent()));
		
		invalidate =true;
		
		invalidate();
	}
	
		

	private void getSeriesNames() {
		
		
		List<String> names = new ArrayList<String>();
		for(int i=0;i<series.size();i++)
		{
			names.add(series.get(i).getSeriesname());
		}
		
			
		 seriesname= new HashSet<String>(names).toArray(new String[numseries]);
		
	}

	private void calculateColorsFromSeries() {
		
		
		List<Integer> colors = new ArrayList<Integer>();
		for(int i=0;i<series.size();i++)
		{
			
			colors.add(series.get(i).getColor());
		}
		
		 cols= new HashSet<Integer>(colors).toArray(new Integer[numseries]);
		
		 
		 
		
		 for(int i=0;i<series.size();i++)
		 {
			
			 System.out.println("SERIES"+ series.get(i).getSeriesName()+
					 
					 series.get(i).getColor());
			 seriescolor.put(series.get(i).getSeriesName(),series.get(i).getColor());
			
			 
		 }
		 
		 String [] keys = seriescolor.keySet().toArray(new String[seriescolor.size()]);
		 for(int m=0;m<keys.length;m++)
			 System.out.println("COLS"+seriescolor.get(keys[m]));
		
	}
	
	
	public void drawLegend(Canvas canvas) {



		int row=1;
		int a=0;
		float lastleft=0;

		int [] labelwidths = new int[numseries];


		for (int i=0; i<labelwidths.length; i++)

		{
			labelwidths[i]= (int) Math.abs(legendPaint.measureText(seriesname[i]+"0"));

		}


		
		for (int i=0; i<numseries; i++)
			
		{

            float left=0; float right=0;

            if(i==0) {
                left = legendBox.left + drawSpace;
                lastleft = left;
            }
            else {

                if(lastleft!=0)
                    left = lastleft + labelwidths[i-1]+drawSpace+drawBoxSize;
                else
                    left=legendBox.left+drawSpace;
                lastleft=left;

            }

            right = left+drawBoxSize;

            lPaint.setColor(seriescolor.get(seriesname[i]));
            legendPaint.setColor(seriescolor.get(seriesname[i]));
		
			
	
			if(((right + labelwidths[i])<legendBox.right))
			{
				
				

			}
			else
			{
				row =row+1;
                lastleft=0;
                left = legendBox.left +drawSpace;
                lastleft=left;
                right = left+drawBoxSize;

			}


            float top = legendBox.top+((row)*drawSpace)+(row-1)*drawBoxSize;
            float bottom = top+drawBoxSize;

            if(bottom>legendBox.bottom)
            {
                redrawLegendBox(legendBox.width(),drawBoxSize,mLabelWidth);
            }

            canvas.drawRect(left,top,right,
                    bottom, lPaint);

            System.out.println(seriesname[i]);
            canvas.drawText(seriesname[i],right+drawSpace,
                    bottom, legendPaint);


        }
		
		
		
	}


	private void calculateColors() {
		
		ColorCode colors = new ColorCode();
		 setcolors =new int[numseries];
		 
		 
		if(useSeriesPrimary)
			setcolors = colors.setPrimaryColor(numseries,60,0.8f,0.9f);
		else
			setcolors = colors.setHSVAccent(primarycolor,numseries,0.5f,1.0f, 20);
		
		
		
		for(int i=1; i<=numseries;i++)
		{
			for(int j=0; j<series.size();j++)
			{
				if(series.get(j).getSeries()==i)
				{
					series.get(j).setColor(setcolors[i-1]);
					seriescolor.put(series.get(j).getSeriesname(),setcolors[i-1]);
				}
					
			}
			
			
		}
		
	}
	
	public List<MultipleSeriesItem> getSeries()
	{
		return series;
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
	
	//******************************************//
	
	
	
	
	
	
	
	public MultipleSeriesBarChart(Context context) {
		super(context);
		
		series = new ArrayList<MultipleSeriesItem>();
		this.context=context;
	
		hstops = new AxisStops();
		vstops = new AxisStops();
		
		mEdgeEffectTop = new EdgeEffectCompat(context);
		mEdgeEffectLeft = new EdgeEffectCompat(context);
		mEdgeEffectRight = new EdgeEffectCompat(context);
		mEdgeEffectBottom = new EdgeEffectCompat(context);
		
		mScaleGestureDetector = new ScaleGestureDetector(context,mScaleGestureListener);
		mGestureDetector = new GestureDetectorCompat(context,mGestureListener);
	}

	
	public MultipleSeriesBarChart(Context context,AttributeSet attrs) {
		super(context,attrs);
		
		
		series = new ArrayList<MultipleSeriesItem>();
		this.context=context;
		
		
		
		hstops = new AxisStops();
		vstops = new AxisStops();
		
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
			drawLabels = a.getBoolean(R.styleable.Charts_drawLabels, false);
			showgrid = a.getBoolean(R.styleable.Charts_showGrid, true);
			assignSeriesColors = a.getBoolean(R.styleable.Charts_assignSeriesColors, true);
			useSeriesPrimary = a.getBoolean(R.styleable.Charts_usePrimary, true);
			primarycolor = a.getColor(R.styleable.Charts_accentprimaryColor, Color.RED);
			rectcolor =a.getColor(R.styleable.Charts_boundingboxColor, Color.BLACK);
			legendSize = a.getDimension(R.styleable.Charts_legendBoxSize,30);
			mAxisLabelSize = a.getDimension(R.styleable.Charts_axisLabelSize, 12);
			mLabelSeparation = a.getDimension(R.styleable.Charts_labelSeparation, 8);
			mTextSize = a.getDimension(R.styleable.Charts_textSize, 12);
			textcolor = a.getColor(R.styleable.Charts_textColor, Color.BLACK);
			gridcolor = a.getColor(R.styleable.Charts_gridColor, Color.BLACK);
		
		}
		
		finally
		{
			
			a.recycle();
		}
		
		
	
	}
	
	
	
	public MultipleSeriesBarChart(Context context,AttributeSet attrs,int defStyle) {
		
		
		super(context,attrs,defStyle);
		
		this.context=context;
		series = new ArrayList<MultipleSeriesItem>();
		hstops = new AxisStops();
		vstops = new AxisStops();
		
		mEdgeEffectTop = new EdgeEffectCompat(context);
		mEdgeEffectLeft = new EdgeEffectCompat(context);
		mEdgeEffectRight = new EdgeEffectCompat(context);
		mEdgeEffectBottom = new EdgeEffectCompat(context);
		
		mScaleGestureDetector = new ScaleGestureDetector(context,mScaleGestureListener);
		mGestureDetector = new GestureDetectorCompat(context,mGestureListener);
		
		TypedArray a = context.getTheme().
				obtainStyledAttributes(attrs,R.styleable.Charts,defStyle,defStyle);
		
		try
		{
			
			
			isHorizontal =a.getBoolean(R.styleable.Charts_isHorizontal, false);
			XisNum = a.getBoolean(R.styleable.Charts_XisNum, false);
			YisNum = a.getBoolean(R.styleable.Charts_YisNum, false);
			YisCategory = a.getBoolean(R.styleable.Charts_YisCategory, false);
			XisCategory = a.getBoolean(R.styleable.Charts_XisCategory, false);
			assignSeriesColors = a.getBoolean(R.styleable.Charts_assignColors, true);
			showLegend = a.getBoolean(R.styleable.Charts_showLegend, false);
			useSeriesPrimary = a.getBoolean(R.styleable.Charts_usePrimary, true);
			drawLabels = a.getBoolean(R.styleable.Charts_drawLabels, false);
			rectcolor =a.getColor(R.styleable.Charts_boundingboxColor, Color.BLACK);
			primarycolor = a.getColor(R.styleable.Charts_accentprimaryColor, Color.BLACK);
			legendSize = a.getDimension(R.styleable.Charts_legendBoxSize,30);
			mAxisLabelSize = a.getDimension(R.styleable.Charts_axisLabelSize, 12);
			mLabelSeparation = a.getDimension(R.styleable.Charts_labelSeparation, 8);
			mTextSize = a.getDimension(R.styleable.Charts_textSize, 12);
			textcolor = a.getColor(R.styleable.Charts_textColor, Color.BLACK);
			showgrid = a.getBoolean(R.styleable.Charts_showGrid, true);
			gridcolor = a.getColor(R.styleable.Charts_gridColor, Color.BLACK);
		
			
			
		}
		
		finally
		{
			
			a.recycle();
		}
		
		
		
	}
	private void init()
	{
		
		lines = new DrawGridLines();
		
		
		
		 tPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	     tPaint.setColor(textcolor);
	     tPaint.setTextSize(mTextSize);
	     tPaint.setTextAlign(Paint.Align.CENTER);
		
	     aPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	     aPaint.setColor(textcolor);
	     aPaint.setTextSize(mAxisLabelSize);
	     aPaint.setTextAlign(Paint.Align.CENTER);
	     
	     rPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	     rPaint.setColor(rectcolor);
	       
	     rPaint.setStyle(Paint.Style.STROKE);
	     
	     gPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	     gPaint.setColor(gridcolor);
	     
	     bPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	     bPaint.setStyle(Paint.Style.FILL_AND_STROKE);
	     
	     lPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	     lPaint.setStyle(Paint.Style.FILL);
	     
	     	labelPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	        labelPaint.setColor(textcolor);
	        labelPaint.setTextSize(mLabelSize);
	        labelPaint.setTextAlign(Paint.Align.CENTER);
	        
	     legendPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	     legendPaint.setColor(textcolor);
	     legendPaint.setTextSize(mTextSize);
	     legendPaint.setTextAlign(Paint.Align.LEFT);
	     
	     
	     	vPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	        vPaint.setColor(textcolor);
	        vPaint.setTextSize(mTextSize);
	        vPaint.setTextAlign(Paint.Align.RIGHT);
	        
	    	fPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	        fPaint.setColor(textcolor);
	        fPaint.setTextSize(mTextSize);
	        fPaint.setTextAlign(Paint.Align.LEFT);
	        
       
        
	    //drawBoxSize = mLabelHeight;
	     
        mLabelHeight =(int) Math.abs(tPaint.getFontMetrics().top);
        
        drawBoxSize = mLabelHeight;
        
        
        minLabelHeight = (int) Math.abs(tPaint.getFontMetrics().bottom);
    
        minLabelWidth = (int) Math.abs(tPaint.measureText("000"));
        mLabelWidth = (int) Math.abs(tPaint.measureText("000000"));
        mVLabelWidth = (int) Math.abs(tPaint.measureText("00000"));
        
        mAxisLabelHeight = (int) Math.abs(aPaint.getFontMetrics().top);
        
       
        
        
	}
	
	
	
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh)
	{
		
		float xpad = (float)(getPaddingLeft()+ getPaddingRight());
		float ypad = (float)(getPaddingTop() +  getPaddingBottom());
		
		float coww =0.0f,cohh=0.0f,coll =0.0f;
		
		init();
		
		System.out.println("APAINT"+aPaint.ascent());
		
		coww = (float) w-xpad
				-mLabelWidth-(2*mAxisLabelHeight+aPaint.ascent())-4*mLabelSeparation;
//		cohh = (float) h-ypad-mLabelHeight-(2*mAxisLabelHeight+aPaint.ascent()+2*mLabelSeparation)
//				-(legendSize+mLabelSeparation);
		
		cohh = (float) h-ypad-mLabelHeight-(2*mAxisLabelHeight+aPaint.ascent()) - 3 * mLabelSeparation
				-(legendSize);

		float bxww = (float) w-xpad;
		float bxhh = (float) h-ypad;
		
		mAxisLabelWidth = (int) (coww/2);
		
		boxbounds = new RectF(getPaddingLeft(),getPaddingTop(),
				getPaddingLeft()+bxww,getPaddingTop()+bxhh);
		
		
		//coll = bxhh-legendSize-mLabelSeparation;
		
		
		
		coordbounds = new RectF(0.0f,0.0f,
				coww,cohh);
		
		float xoffset = mLabelWidth+ (2*mAxisLabelHeight+aPaint.ascent())+3*mLabelSeparation;
		
		
		
		axisLabelSpace = (2*mAxisLabelHeight-aPaint.ascent())+3*mLabelSeparation;
		coordbounds.offset(getPaddingLeft()+ xoffset,getPaddingTop()+mLabelSeparation);
		
		
		legendBox = new RectF(0.0f,0.0f,coww,legendSize);
		
		//legendBox.offset(getPaddingLeft()+xoffset,coordbounds.bottom+2*mLabelSeparation+mLabelHeight);
		legendBox.offset(getPaddingLeft()+xoffset,coordbounds.bottom+mLabelSeparation+mLabelHeight);
		
		
			
		tasks = new OptimizeTicks(mLabelWidth,mLabelHeight,coordbounds.width(),
				coordbounds.height(),minLabelWidth,
				minLabelHeight,boxbounds.height(),boxbounds.width(),
				axisLabelSpace,legendSize);
		
		assignColors();
		
		getXYTicks(boxbounds.width(),boxbounds.height(),coordbounds.width(),axisLabelSpace);
		
		
	}
	
	
	private double[] getYArray()
	{
		double [] maxvalY = new double[series.size()];
		 
		for(int i=0;i<series.size();i++)
		{
				maxvalY[i]=series.get(i).getYVal();
				
		}
		 Arrays.sort(maxvalY);
		 
		 
		return maxvalY;
	}
	
	private double[] getXArray()
	{
		double [] maxvalX = new double[series.size()];
		 
		for(int i=0;i<series.size();i++)
		{
				maxvalX[i]=series.get(i).getXVal();
				
		}
		 Arrays.sort(maxvalX);
		return maxvalX;
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
	
	
	
		
		private void getXYTicks(float bxw,float bxh,float coww,float axisLabelSpace)
		{
			ComputeStops cmp = new ComputeStops();
			
			System.out.println("LGSIZE" + legendSize);
			
			
			HandleXYTicks handleticks = new HandleXYTicks(coordbounds,getPaddingLeft(), 
					
					getPaddingTop(), mAxisLabelHeight,mLabelSeparation, aPaint,mBarWidth,
					mBarSpacing,iBarSpacing,legendSize,mVLabelWidth);
		
			if  (XisCategory & YisNum)
			{
				
				if(numseries>1)
				barspace = (numseries*mBarWidth)+((numseries-1)*iBarSpacing)+(2*mBarSpacing);
				
			
				double [] maxvalY = getYArray();
			
				getXCategory();
				 
				
				ticks =handleticks.
						getXCategoryYNumericBar(isHorizontal,maxvalY[maxvalY.length-1],
								maxvalY[0], maxvalY, hstops, vstops, uniqueX,
								bxh, bxw,coww, axisLabelSpace,tasks,numseries);
				
				nVticks = ticks.nVticks;
				nHticks = ticks.nHticks;
				nLabelWidth = ticks.nLabelWidth;
				nhLabelWidth = ticks.nhLabelWidth;
				nvLabelWidth = ticks.nvLabelWidth;
				subLabelHeight = ticks.subLabelHeight;
				nLabelHeight=ticks.nLabelHeight;
				if(!isHorizontal)
					vInterval = ticks.vInterval;
				else
					hInterval = ticks.hInterval;
				
				Viewport = ticks.Viewport;
				coordbounds = ticks.coordbounds;
				legendBox = ticks.legendBox;
				
				System.out.println("LGVAL" + ticks.legendBox);
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
			}
				
			
			if  (XisNum & YisCategory)
			{
				
				if(numseries>1)
				barspace = (numseries*mBarWidth)+((numseries-1)*iBarSpacing)+(2*mBarSpacing);
				
				
				double [] maxvalX = getXArray();
				getYCategory();
			
				 
				
				
				ticks =handleticks.
						getYCategoryXNumericBar(isHorizontal,maxvalX[maxvalX.length-1],
								maxvalX[0], maxvalX, hstops, vstops, uniqueY, bxh, bxw, coww,
								axisLabelSpace,tasks,numseries);
				
				nLabelHeight=ticks.nLabelHeight;
				
				System.out.println("NLABELHT"+ nLabelHeight);
				nVticks = ticks.nVticks;
				nHticks = ticks.nHticks;
				nLabelWidth = ticks.nLabelWidth;
				nhLabelWidth = ticks.nhLabelWidth;
				nvLabelWidth = ticks.nvLabelWidth;
				subLabelHeight = ticks.subLabelHeight;
				
				
				
				System.out.println("SUBLABEL"+ subLabelHeight);
				
				System.out.println("NLABEL"+ nLabelWidth);
				
				
				if(!isHorizontal)
					hInterval = ticks.hInterval;
				else
					vInterval = ticks.vInterval;
				
				Viewport = ticks.Viewport;
				coordbounds = ticks.coordbounds;
				legendBox = ticks.legendBox;
				
				System.out.println("LGVAL" + ticks.legendBox);
				
				
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
				
				
				
				
				
				
				
			}
			
		}

		
		
		
		@Override 
		 public void onDraw(Canvas canvas)
		 
		 {
			super.onDraw(canvas);
			
			System.out.println("Calling onDraw");
			
			
			
			
			int clipRestoreCount = canvas.save();
			canvas.clipRect(coordbounds);
			
			drawAxes(canvas);
			
			drawData(canvas);
			
			
			
			
			drawEdgeEffectsUnclipped(canvas);
			
			canvas.restoreToCount(clipRestoreCount);
			
			drawText(canvas);
			
			if(showLegend)
			{
				/*if(!invalidate)
				drawLegend(canvas);
				else
				drawInvalidateLegend(canvas);	*/

                drawLegend(canvas);
				
			}
			
			//canvas.drawRect(legendBox, rPaint);
			
			canvas.drawRect(coordbounds,rPaint);
			
			
			
			 
			 
		 }
		
		private float getCentreVCategory(double value,int n ,float space)
		{
			float pos = 0;float bpos =0;
			
//			pos = coordbounds.bottom - coordbounds.height()*
//					(float)(value-Viewport.top)/Viewport.height();
			
			pos = coordbounds.bottom - barspace*
					(float)(value-Viewport.top);
			
			if(n==1)
//				pos = coordbounds.bottom - coordbounds.height()*
//					(float)((value-(space/2)+ mBarSpacing +
//							(n-1)*mBarWidth + (n-1)*iBarSpacing)-Viewport.top)/Viewport.height() ;
				
				bpos = (pos-space/2)+ mBarSpacing + 
				(n-1)*mBarWidth + (n-1)*iBarSpacing;
			
			
			else if(n==numseries)
			{
				/*pos = coordbounds.bottom - coordbounds.height()*
				(float)((value-(space/2)+ mBarSpacing 
						+ (n-1)*mBarWidth + (n-1)*iBarSpacing)-Viewport.top)/Viewport.height() ;*/
				
				
				bpos = (pos-space/2) + mBarSpacing + 
						(n-1)*mBarWidth + (n)*iBarSpacing;
			}
			else
//				pos = coordbounds.bottom - coordbounds.height()*
//				(float)((value-(space/2) +
//						(n-1)*mBarWidth + (n-1)*iBarSpacing)-Viewport.top)/Viewport.height() ;
				
				bpos = (pos-space/2)+(n-1)*mBarWidth + (n-1)*iBarSpacing+mBarSpacing;
			
			return bpos;
		}
		
		
		
		private float getCentreHCategory(double value,int n,float space)
		{
			
			System.out.println("VALUE" + value);
			System.out.println("SPACE" + space);
			
			
			
			
			float pos = 0;
			float bpos=0;
			
			float indspace = space/numseries;
			
			pos = coordbounds.left + barspace*
					(float)(value-Viewport.left);
			/*if(n==1)
				pos = coordbounds.left + coordbounds.width()*
					(float)((value-(space/2)+ mBarSpacing + 
							(n-1)*mBarWidth + (n-1)*iBarSpacing)-Viewport.left)/Viewport.width() ;
			else if(n==numseries)
				pos = coordbounds.left + coordbounds.width()*
				 (float)((value-(space/2)+ 
						 mBarSpacing + (n-1)*mBarWidth + (n-1)*iBarSpacing)-Viewport.left)/
				 Viewport.width() ;
			else
				pos = coordbounds.left + coordbounds.width()*
				 (float)((value-(space/2)+
						 (n-1)*mBarWidth + (n-1)*iBarSpacing)-Viewport.left)/
				 Viewport.width() ;*/
			
		if(n==1)
		{
			
			
			
			/*bpos = (pos + indspace+ mBarSpacing + 
			(n-1)*mBarWidth + (n-1)*iBarSpacing);*/
			
			bpos = (pos-space/2)+ mBarSpacing + 
					(n-1)*mBarWidth + (n-1)*iBarSpacing;
			
			
		}
		else if(n==numseries)
		{
			
			bpos = (pos-space/2) + mBarSpacing + 
					(n-1)*mBarWidth + (n-1)*iBarSpacing;
			
				
			/*bpos = (pos-space/2) +(indspace+ mBarSpacing + 
					(n-1)*mBarWidth + (n-1)*iBarSpacing);*/
			
			
			
		}
		else
		{
			
			/*bpos = (pos-space/2)+ (indspace+ (n-1)*mBarWidth + (n-1)*iBarSpacing);*/
			
			bpos = (pos-space/2)+(n-1)*mBarWidth + (n-1)*iBarSpacing+mBarSpacing;
				
		}
				
			return bpos;
		}
		
		private float getCentreH(double value)
		{
			float pos = coordbounds.left + coordbounds.width()*
			(float)(value-Viewport.left)/Viewport.width();
			
			return pos;
		}
		
		private float getCentreV(double value)
		{
			float pos = coordbounds.bottom - coordbounds.height()*
			(float)(value-Viewport.top)/Viewport.height();
			
			return pos;
		}
		
		
		
		private  void drawData(Canvas canvas)
		{
			//float space = (numseries*mBarWidth)+(numseries*iBarSpacing)+(2*mBarSpacing);
			
			float space = (numseries*mBarWidth)+(numseries*iBarSpacing);
			
			barposition = new BarPosition[series.size()];
			if(!isHorizontal)
			{
				
	
					for(int m=0; m<series.size();m++)
					{
						
						barposition[m]= new BarPosition();
						
						float centreh=0.0f,centrev=0.0f;
						
						
						
						if(XisCategory)
							
						{
							int serie = series.get(m).getSeries();
							for(int n=0; n<uniqueX.length;n++)
								
							{
								if(series.get(m).getXLabel().equals(uniqueX[n]))
								 centreh = getCentreHCategory(n+1,serie,space);
							}
							
							barposition[m].setLeftEdge(centreh);
							barposition[m].setRightEdge(centreh+mBarWidth);
							
							
						}
						
						else
						{
							centreh = getCentreH(series.get(m).getXVal());
							barposition[m].setLeftEdge(coordbounds.left);
							barposition[m].setRightEdge(centreh);
							barposition[m].setValue(series.get(m).getXVal());
						
						}
						
						
						if(YisCategory)
							
						{
							int serie = series.get(m).getSeries();
							for(int r=0; r<uniqueY.length;r++)
								
							{
								if(series.get(m).getYLabel().equals(uniqueY[r]))
								 centrev = getCentreVCategory(r+1,serie,space);
							}
							
							barposition[m].setTopEdge(centrev);
							barposition[m].setBottomEdge(centrev+mBarWidth);
							
							
						}
						
						else
						{
							centrev = getCentreV(series.get(m).getYVal());
							
							barposition[m].setTopEdge(centrev);
							barposition[m].setBottomEdge(coordbounds.bottom);
							barposition[m].setValue(series.get(m).getYVal());
						}
							
							//TODO GET FROM COLOR CODE LIKE PIE CHART
							drawBar(canvas,centreh,centrev,series.get(m).getColor());
							
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
							 centrev = getCentreVCategory(n+1,series.get(m).getSeries(),space);
						}
						
						barposition[m].setTopEdge(centrev);
						barposition[m].setBottomEdge(centrev+mBarWidth);
						
						
					}
					
					else
					{
						centrev = getCentreV(series.get(m).getXVal());
						
						System.out.println(barposition[m]);
						barposition[m].setTopEdge(centrev);
						barposition[m].setBottomEdge(coordbounds.bottom);
						barposition[m].setValue(series.get(m).getXVal());
					}
					
					if(YisCategory)
						
					{
						for(int r=0; r<uniqueY.length;r++)
							
						{
							if(series.get(m).getYLabel().equals(uniqueY[r]))
							 centreh = getCentreHCategory(r+1,series.get(m).getSeries(),space);
						}
						
						
						barposition[m].setLeftEdge(centreh);
						barposition[m].setRightEdge(centreh+mBarWidth);
						
					}
					
					else
					{
						centreh = getCentreH(series.get(m).getYVal());
						
						barposition[m].setLeftEdge(coordbounds.left);
						barposition[m].setRightEdge(centreh);
						barposition[m].setValue(series.get(m).getYVal());
						
					}
						//TODO GET FROM COLOR CODE LIKE PIE CHART
						drawBar(canvas,centreh,centrev,series.get(m).getColor());
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
		
		
		private void drawBar(Canvas canvas, float centreh,float centrev,int color)
		{
			
			
			
			bPaint.setColor(color);
			
			if(!isHorizontal)
				
				
				
			{
				
				 if(YisNum)
				 
					 canvas.drawRect(centreh,centrev,centreh+mBarWidth,coordbounds.bottom,bPaint);
				 
				 if(XisNum)
					 
					 
					 canvas.drawRect(coordbounds.left,centrev,centreh,centrev+mBarWidth,bPaint);
			
			}
			else
			{
				System.out.println("CENTREV" + centrev + "CENTREV BAR"+(centrev+mBarWidth));
						
				if(YisNum)
					canvas.drawRect(coordbounds.left,centrev,centreh,centrev+mBarWidth,bPaint);
				
				if(XisNum)
					
					 canvas.drawRect(centreh,centrev,centreh+mBarWidth,coordbounds.bottom,bPaint);
			}
			
			//canvas.drawRect(centreh,coordbounds.top,centreh+mBarWidth,coordbounds.bottom,bPaint);
			
		}
		private void drawAxes(Canvas canvas)
		{
			
			float [] hposstops = new float[hstops.axisstops.length];
			float [] vposstops= new float[vstops.axisstops.length];
			
			
			System.out.println("COW" + coordbounds.width()+"COL" + coordbounds.left
					+"COR" + coordbounds.right);
		
			
			if(!isHorizontal)
			{
				
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
		

		private void drawEdgeEffectsUnclipped(Canvas canvas)
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
		
	  private final GestureDetector.SimpleOnGestureListener mGestureListener =
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
	  
	  
	  
	  
	  private void drawText(Canvas canvas) {
			
			 
			 
			 
			 
			 exponent = new ArrayList<Integer>();
			 
			 
				if(!isHorizontal)
					
					
				{
					
					DrawTextValues values = new DrawTextValues(tPaint,aPaint,
							mAxisLabelHeight, mAxisLabelWidth,coordbounds,subLabelHeight,nLabelWidth,mLabelSeparation,vPaint
							,fPaint,nhLabelWidth,nvLabelWidth);
					
					
					System.out.println(hstops.axisstops.length);
					
					System.out.println("NLBLWD"+ nLabelWidth);
					
					
					for(int j=0;j<hstops.axisstops.length;j++)
					{
						if(XisNum)
							
						{		
								// CalculateExp(hstops.axisstops,j,tPaint,nLabelWidth,hInterval);
							
							CalculateExp(hstops.axisstops,j,tPaint,ticks.nvLabelWidth,hInterval);
							
							
						}
						
					}
					
					for(int j=0;j<vstops.axisstops.length;j++)
					{
						if(YisNum)
							
						{
							CalculateExp(vstops.axisstops,j,tPaint,nvLabelWidth,vInterval);
							
							
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
							
						
							values.DrawTextCategory(uniqueX[i-1],canvas,
									coordbounds.bottom,i,hstops,
							coordbounds.left, coordbounds.width(),true,hflipped); 
							
							
						}
				
					
						else if(XisNum)
					
						{
							
							
//							DrawTextNum(canvas,getHLabelName(),boxbounds.bottom,i,hstops 
//									
//								,coordbounds.left,coordbounds.width(),hInterval,true);
							
							if(i%2==0)
							values.DrawTextNumHExponent(canvas,getXLabelName(),coordbounds.bottom,i,hstops 
						
									,coordbounds.left,coordbounds.width(),hInterval,true,hflipped,min);
							
						}
						
					}
					
					
					canvas.drawText(getXLabelName(),0,getXLabelName().length(),
							coordbounds.left+coordbounds.width()/2,
							boxbounds.bottom-(mAxisLabelHeight/2), aPaint);
					
					
					for(int j=1;j<vstops.axisstops.length;j++)
					{
						
						if(YisCategory)
							
						{
							System.out.println("Pos" + j);
							System.out.println(uniqueY[j-1]);
							System.out.println("Vflipped VAL" + vflipped);
							
							values.DrawTextCategoryVerticalBar(uniqueY[j-1],canvas,getYLabelName(),coordbounds.bottom,j,
									vstops,boxbounds.left, coordbounds.height(),false,vflipped);
							
						}
				
						else if(YisNum)
							
						{
//							values.DrawTextNumVerticalBar(canvas,getYLabelName(),coordbounds.bottom,j,vstops 
//								,boxbounds.left,coordbounds.height(),vInterval,false,vflipped,min);
							if(j%2==0)
							values.DrawTextNumVExponent(canvas,getYLabelName(),coordbounds.bottom,j,vstops 
								,boxbounds.left,coordbounds.height(),vInterval,false,vflipped,min);
						}
						
					}
					
					
					
					canvas.save();
					canvas.rotate((float)(90));
					System.out.println("CANVAS ");
					canvas.drawText(getYLabelName(),0,getYLabelName().length(),
						coordbounds.bottom-coordbounds.height()/2,boxbounds.left-mLabelSeparation/2, aPaint);
					canvas.restore();
					
					
					
					
					if(YisNum)
					{
						
						if(min!=0)
							values.drawExponentVertical(canvas,coordbounds.bottom,min,boxbounds.left,coordbounds.height());
					}
					
					if(XisNum)
					{
						
						if(min!=0)
							values.drawExponentHorizontal(canvas,coordbounds.bottom,min,coordbounds.left,coordbounds.width()); 
					}
					
			
				}
				else
				{
					
					DrawTextValues values = new DrawTextValues(tPaint,aPaint,
							mAxisLabelHeight, mAxisLabelWidth,coordbounds,subLabelHeight,nvLabelWidth,mLabelSeparation,vPaint
							,fPaint,nhLabelWidth,nvLabelWidth);
					
					System.out.println("nVLabelWidth"+nvLabelWidth);
					
					for(int j=0;j<vstops.axisstops.length;j++)
					{
						if(XisNum)
							
						{
							
							
							CalculateExp(vstops.axisstops,j,tPaint,nvLabelWidth,vInterval);
							
							
						}
						
					}
					
					for(int j=0;j<hstops.axisstops.length;j++)
					{
						if(YisNum)
							
						{		
								 CalculateExp(hstops.axisstops,j,tPaint,ticks.nvLabelWidth,hInterval);
							
							
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
							values.DrawTextCategoryVerticalBar(uniqueX[i-1],canvas,getXLabelName(),coordbounds.bottom,
									i,vstops,
									boxbounds.left, coordbounds.height(),false,vflipped); 
						}
						
						else if(XisNum)
						{
/*//							values.DrawTextNumVerticalBar(canvas,getXLabelName(),coordbounds.bottom,i,vstops 
//									,boxbounds.left,coordbounds.height(),vInterval,false,vflipped,min);
*/						
							
							if(i%2==0)
							
							values.DrawTextNumVExponent(canvas,getXLabelName(),coordbounds.bottom,i,vstops 
									,boxbounds.left,coordbounds.height(),vInterval,false,vflipped,min);
							
							
						
						}
						
						
					}
					
					canvas.save();
					canvas.rotate((float)(90));
					System.out.println("CANVAS ");
					canvas.drawText(getXLabelName(),0,getXLabelName().length(),
						coordbounds.bottom-coordbounds.height()/2,boxbounds.left-mLabelSeparation/2, aPaint);
					canvas.restore();
					
					for(int i=1; i< hstops.axisstops.length;i++)
					{
					
						if(YisCategory)
					
							
						
							
							values.DrawTextCategory(uniqueY[i-1],canvas,coordbounds.bottom,i,hstops,
							coordbounds.left, coordbounds.width(),true,hflipped); 
				
					
						else if(YisNum)
					
						{
							if(i%2==0)
							values.DrawTextNumHExponent(canvas,getYLabelName(),coordbounds.bottom,i,hstops 
								,coordbounds.left,coordbounds.width(),hInterval,true,hflipped,min);
							
						}
						
					}
					
					
					if(XisNum)
					{
						
						if(min!=0)
							values.drawExponentVertical(canvas,coordbounds.bottom,min,boxbounds.left,coordbounds.height());
					}
					
					if(YisNum)
					{
						
						if(min!=0)
							values.drawExponentHorizontal(canvas,coordbounds.bottom,min,coordbounds.left,coordbounds.width()); 
					}
					
					canvas.drawText(getYLabelName(),0,getYLabelName().length(),
							coordbounds.left+coordbounds.width()/2,
							boxbounds.bottom-(mAxisLabelHeight/2), aPaint);
				}
						
			
		}

	protected boolean hitTest(float x, float y, PointF viewportFocus) {
		
		  if(!coordbounds.contains((int)x,(int)y))
		  {
			  return false;
		  }
		  
		
		  
		  viewportFocus.set(Viewport.left+(Viewport.width() *
				  (x - coordbounds.left)/coordbounds.width()),
				 Viewport.top + (Viewport.height() * (y - coordbounds.bottom)/-coordbounds.height()));
		 
		  return true;
		  
		
	}

	private final ScaleGestureDetector.OnScaleGestureListener mScaleGestureListener
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
		  ViewCompat.postInvalidateOnAnimation(MultipleSeriesBarChart.this);
		 
		 lastSpanX = spanX;
		 lastSpanY = spanY;
			return true;
		 }
	 };
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
	
private void CalculateExp(double [] stops , int i,Paint tPaint,int nLabelWidth,double interval) {
		
		
		
		
		
		String[] outstring = formatfloat((float)stops[i],
				interval,tPaint,nLabelWidth);
		
		if(outstring[1]!=null)
	{
		
		exponent.add(Integer.parseInt(outstring[1]));
		
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
				 pattern.concat("#");
			numstring = new DecimalFormat(pattern).format(value);
			
			
		}
//	
	String [] strings = new String[2];
	strings[0]=numstring;
	System.out.println("EXP" + exp);
	if(exp!=0)
		strings [1] = Integer.toString(exp);
	return strings;
	
	
	
	
	
}



	private void setViewportBottomLeft(float x,float y) {
			
		  
		  float curWidth = Viewport.width();
		 
		  float curHeight = Viewport.height();
		  x= Math.max(AXIS_H_MIN,Math.min(x, AXIS_H_MAX-curWidth));
	      y = Math.max(AXIS_V_MIN +curHeight,Math.min(y, AXIS_V_MAX));
	      
	      Viewport.set(x,y-curHeight,x + curWidth,y);
	      ViewCompat.postInvalidateOnAnimation(this);
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
