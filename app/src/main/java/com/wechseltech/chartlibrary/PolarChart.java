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
import java.util.Set;
import java.util.Map.Entry;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.Canvas.VertexMode;
import android.graphics.Paint.Cap;
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


public class PolarChart extends ChartView {

	int mLabelWidth
	,mLabelHeight;
	float xpoint,ypoint;
	List<Integer> exponent;
	Context context;
	float diameter,
	angleInterval=30;
	PointValues[] values;
	Map<Integer,PointValues[]> seriesvals
	;
	public Point ScrollableSurface = new Point();
	RectF coordbounds,
	boxbounds,legendBox,Viewport;
	String[] categoryLabels
	=new String[]{""};
	Boolean modifyLabels=false;
	List<CategoryItem>series;
	double [] angles;
	Map<Integer,Map<String,Double>> valangles =
			new HashMap<Integer,Map<String,Double>>();
	 public static boolean ASC = true;
	 public static boolean DESC = false;
	double hInterval;
	int hRTicks;
	AxisStops hstops;
	float AXIS_H_MAX;
	
	float 
	AXIS_H_MIN,
	AXIS_V_MAX,AXIS_V_MIN;
	Paint tPaint,gPaint,lPaint,
	pointPaint,seriesPaint,areaPaint,legendPaint,aPaint;
	Path seriesPath;
	Path[] vertexPath;
	float innerradii,outerradii,radii;
	
	public boolean mEdgeEffectTopActive;
	 public boolean mEdgeEffectBottomActive;
	 public boolean mEdgeEffectLeftActive;
	 public boolean mEdgeEffectRightActive;
	 
	 int min=0;
	 boolean invalidate=false;
	 
	 public EdgeEffectCompat mEdgeEffectTop;
	 public EdgeEffectCompat mEdgeEffectBottom;
	 public EdgeEffectCompat mEdgeEffectLeft;
	 public EdgeEffectCompat mEdgeEffectRight;
	
	Map<String,ChartType>charts;
	int numseries;
	Map<String,Float> catangles;
	private float mScaleFactor = 1.f;
	
	float mPosX,mPosY,diffX=0,diffY=0;
	
	Map<Integer,Integer> numinseries;
	Map<String,Integer>seriescolor;
	
	String [] seriesname;
	
	
	

	 public ScaleGestureDetector mScaleGestureDetector=null;
	 public GestureDetectorCompat mGestureDetector=null;
	
	
	 
	 int drawBoxSize;
	 
	

	public PolarChart(Context context) {
		super(context);
		
		System.out.println("INIT C");
		this.context=context;
		series = new ArrayList<CategoryItem>();
		hstops=new AxisStops();
		mScaleGestureDetector = new ScaleGestureDetector(context,mScaleGestureListener);
		//mGestureDetector = new GestureDetectorCompat(context,mGestureListener);
		
		mEdgeEffectTop = new EdgeEffectCompat(context);
		mEdgeEffectLeft = new EdgeEffectCompat(context);
		mEdgeEffectRight = new EdgeEffectCompat(context);
		mEdgeEffectBottom = new EdgeEffectCompat(context);
		
		
	
	}
	
	
	
	
	public PolarChart(Context context,AttributeSet attrs) {
		super(context,attrs);
		System.out.println("INIT At");
		
		this.context=context;
		series = new ArrayList<CategoryItem>();
		hstops=new AxisStops();
		
		mScaleGestureDetector = new ScaleGestureDetector(context,mScaleGestureListener);
		//mGestureDetector = new GestureDetectorCompat(context,mGestureListener);
		
		TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.Charts,0,0);
		
		try
		{
			
			
			mLabelSeparation = a.getDimension(R.styleable.Charts_labelSeparation, 8);
			mTextSize = a.getDimension(R.styleable.Charts_textSize, 12);
			textcolor = a.getColor(R.styleable.Charts_textColor, Color.BLACK);
			axisTextSize = a.getDimension(R.styleable.Charts_axisTextSize, 12);
			legendSize= a.getDimension(R.styleable.Charts_legendBoxSize, 30);
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
		
		mEdgeEffectTop = new EdgeEffectCompat(context);
		mEdgeEffectLeft = new EdgeEffectCompat(context);
		mEdgeEffectRight = new EdgeEffectCompat(context);
		mEdgeEffectBottom = new EdgeEffectCompat(context);
		
		
	}
	
	
	
	
	public PolarChart(Context context,AttributeSet attrs,int defStyle) {
		
		
		super(context, attrs, defStyle);
		System.out.println("INIT DF");
		series = new ArrayList<CategoryItem>();
		this.context=context;
		
		mScaleGestureDetector = new ScaleGestureDetector(context,mScaleGestureListener);
		//mGestureDetector = new GestureDetectorCompat(context,mGestureListener);
		
		
		TypedArray a = context.getTheme().
				obtainStyledAttributes(attrs, R.styleable.Charts, defStyle, defStyle);
		
		try
		{
			
			mLabelSeparation = a.getDimension(R.styleable.Charts_labelSeparation,8);
			mTextSize = a.getDimension(R.styleable.Charts_textSize, 12);
			textcolor = a.getColor(R.styleable.Charts_textColor, Color.BLACK);
			legendSize= a.getDimension(R.styleable.Charts_legendBoxSize, 30);
			axisTextSize = a.getDimension(R.styleable.Charts_axisTextSize, 12);
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
		
		
		mEdgeEffectTop = new EdgeEffectCompat(context);
		mEdgeEffectLeft = new EdgeEffectCompat(context);
		mEdgeEffectRight = new EdgeEffectCompat(context);
		mEdgeEffectBottom = new EdgeEffectCompat(context);
		
		
		
		
}

 
 





	private void init()
{
	 hstops  = new AxisStops();
	
	 tPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
     tPaint.setColor(textcolor);
     tPaint.setTextSize(mTextSize);
     tPaint.setTextAlign(Paint.Align.CENTER);
     
     seriesPath = new Path();
     
     gPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
     gPaint.setStyle(Paint.Style.STROKE);
     
     lPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
     lPaint.setStyle(Paint.Style.FILL_AND_STROKE);
     
     legendPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
     legendPaint.setColor(textcolor);
     legendPaint.setTextSize(mTextSize);
     legendPaint.setTextAlign(Paint.Align.LEFT);
     
     aPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
     aPaint.setColor(textcolor);
     aPaint.setTextSize(axisTextSize);
    
     
     seriesPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
     seriesPaint.setStyle(Paint.Style.STROKE);
     
     areaPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
     areaPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    		 
    /* pointPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
     pointPaint.setStyle(Paint.Style.FILL_AND_STROKE);
     pointPaint.setStrokeCap(Cap.ROUND);*/
     
     
     pointPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
     pointPaint.setStyle(Paint.Style.STROKE);
     pointPaint.setStrokeWidth(10);
     pointPaint.setStrokeCap(Cap.ROUND);
     
     
     
     
     
     
     mLabelHeight =(int) Math.abs(tPaint.getFontMetrics().top);
        
     drawBoxSize = mLabelHeight;
        
   
     mLabelWidth = (int) Math.abs(tPaint.measureText("000000"));
}
	
	
	
	
	@Override 
	 public void onDraw(Canvas canvas)
	 
	 {
		super.onDraw(canvas);
		

		
		canvas.save();
		
		
		canvas.translate(mPosX, mPosY);
	    canvas.scale(mScaleFactor, mScaleFactor);
	   
	    drawGrid(canvas);
	    drawData(canvas);
	    
	    drawText(canvas);
		

		drawLegend(canvas);

	    
	    canvas.restore();
		
		
		
		
		
		

	 
	 }



	public void drawLegend(Canvas canvas) {

		System.out.println("DRAW LEGEND");

		int [] labelwidths = new int[seriesname.length];
		int row=1;
		int a=0;
		float lastleft=0,top=0,bottom=0;


		for (int i=0; i<labelwidths.length; i++)

		{
			labelwidths[i]= (int) Math.abs(legendPaint.measureText(seriesname[i]+"0"));

		}




		for (int i=0; i<seriesname.length; i++)

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




			legendPaint.setColor(seriescolor.get(seriesname[i]));

			if(((right + labelwidths[i])<=legendBox.right))
			{
				System.out.println("LABEL"+labelwidths[i] + "RIGHT LABEL"
						+(right+labelwidths[i]));
			}
			else
			{
				row =row+1;
				lastleft=0;
				left = legendBox.left +drawSpace;
				lastleft=left;
				right = left+drawBoxSize;



			}


			top = legendBox.top+((row)*drawSpace)+(row-1)*drawBoxSize;
			bottom = top+drawBoxSize;

			if(bottom>legendBox.bottom)
			{
				redrawLegendBox(legendBox.width(), drawBoxSize, mLabelWidth);
			}





			canvas.drawRect(left,top,right,
					bottom, legendPaint);

			System.out.println(seriesname[i]);
			canvas.drawText(seriesname[i],right+drawSpace,
					bottom, legendPaint);



		}




	}


	private void redrawLegendBox(float coww,int drawBoxSize,int mLabelWidth) {
		
		 
		 
		
		//float coll = (float) boxbounds.height()-mLabelSeparation-(legendSize+(drawBoxSize));
		
		float cohh = (float) boxbounds.height()-3*mLabelHeight-
				(legendSize+3*mLabelSeparation+drawBoxSize)-
				mLabelHeight-2*mLabelSeparation;
		
		
		
		
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
	    
		
		legendBox.offset(getPaddingLeft() + xoffset, coordbounds.bottom + toffset);
		
		System.out.println("LG" + legendBox);
		
		invalidate =true;
		
		invalidate();
	}
	
	
	
	
	
	private float getCentreV(double value)
	
	{
		
		
			float pos = (outerradii-innerradii)*
				(float)(value-Viewport.left)/(Viewport.width());
			
			System.out.println("VPCENT" + Viewport.left);
			System.out.println("VAL" + value);
			System.out.println("VPWD" + Viewport.width());
			System.out.println("CWD" + (outerradii-innerradii));
			System.out.println("INNERRADII" + innerradii);
			
			return pos;
	}
	
	
	public void setSeriesType(Map<String,ChartType> charts)
	{
		this.charts=charts;
		
	}
	
	
	
	
	
	private void drawData(Canvas canvas)
	 {
		 
		 
		 int nstops = hstops.axisstops.length/2;
		  
		  double radii = (diameter/2)/nstops;
		  
		 // innerradii = (float) radii;
		  
		  innerradii = (float) 0.0;
		  
		  outerradii = (float) (nstops*radii);
		  
		  
		  seriesvals = new HashMap<Integer, PointValues[]>();
		 
		  
		  
		  
		for(int n=1; n<=numseries;n++)
		{
			int a=0;
			
			int num = numinseries.get(n);
			
			System.out.println("NUM"+ num + "SER" + n);
			
			values = new PointValues[num];
			
			float StartX=0.0f,StartY=0.0f;
			
			float [] vertices = new float[num];
	
			
			
			
			Map<String,Double> angs = valangles.get(n);
			System.out.println("ANGS" + angs);
			String[] db = angs.keySet().toArray(new String[angs.size()]);
			for(int m=0; m<series.size();m++)
		 
		 
			{
				
				
				if(series.get(m).getSeriesName()==seriesname[n-1])
				{
					double angle=0.0f;
					float pos=0.0f;
					
					
					
					
					
					
					pos = getCentreV(series.get(m).getValue());
					
					 Double ang= angs.get(db[a])* (Math.PI/180);
					
					 float x = (float)(coordbounds.centerX()+
							 (float)((innerradii+pos)*Math.cos(ang)));
					 float y = (float)(coordbounds.centerY()+ 
							 (float)((innerradii+pos)*Math.sin(ang)));
					 
					 System.out.println("ANGLEX"+ ang+ angs.get(db[a]));
					
					 
					 
					/* vertices[a] = x;
					 vertices[a+1]=y;*/
					 
					 values[a] = new PointValues();
					 values[a].x=x;
					 values[a].y=y;
					 values[a].value=series.get(m).getValue();
					 
					 System.out.println("VALUES"+ values[a].x+ "VALUES"+values[a].y);
					 
					 pointPaint.setColor(series.get(m).getSeriesColor());
					 
					 //pointPaint.setColor(series.get));
					 canvas.drawPoint(x, y, pointPaint);
						
					 a++;
				 
				
					
				 
				}
				
				
				 
		 }
			
			
			
			if(charts.get(seriesname[n-1])==ChartType.LINE)
			{
				
				/*canvas.drawVertices(VertexMode.TRIANGLES,vertices.length, vertices, 0,
						 null,0,null,0,null,0,0, seriesPaint);*/
				
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
//			  		canvas.drawPath(gridPath, gPaint);
			  		canvas.drawPath(seriesPath,seriesPaint);
			  		seriesPath.reset();
			  		
				
				
			}
			
			else if(charts.get(seriesname[n-1])==ChartType.AREA)
			{
				
				
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
				
				 	areaPaint.setColor(seriescolor.get(seriesname[n-1]));
				 	
				 
				 	seriesPath.close();
			  		areaPaint.setStrokeWidth(3);
			  		areaPaint.setAlpha(140);
//			  		canvas.drawPath(gridPath, gPaint);
			  		canvas.drawPath(seriesPath,areaPaint);
			  		seriesPath.reset();
				
				
			}
			else if(charts.get(seriesname[n-1])== ChartType.CURVE)
			{
				
				
				seriesPath.moveTo(values[0].x, values[0].y);
				
				
				for(int s=0;s<values.length;s++)
				{
					
					PointValues cp1 = new PointValues();
					PointValues cp2 = new PointValues();
					PointValues cp3 = new PointValues();
					
					
					 
					
					 StartX=values[0].x;
					 StartY=values[0].y;
					
					 
					 
					 
					
						
						if(s<values.length-1)
						{
							
							
							
							CalculateControlPoints(values[s].x,values[s].y,values[s+1].x,
									values[s+1].y,cp1);
							System.out.println("S"+s + "X" + values[s].x +"Y"+ values[s].y);
							seriesPath.quadTo(values[s].x,values[s+1].y,values[s+1].x, values[s+1].y);
							seriesPath.moveTo(values[s+1].x, values[s+1].y);
						}
						
						if(s==values.length-1)
						{
							seriesPath.quadTo(values[s].x,values[0].y,values[0].x, values[0].y);
							seriesPath.moveTo(values[0].x, values[0].y);
						}
					
					
	
				}
				
				 	seriesPaint.setColor(seriescolor.get(seriesname[n-1]));
				 
				 	seriesPath.close();
			  		seriesPaint.setStrokeWidth(3);
//			  		canvas.drawPath(gridPath, gPaint);
			  		canvas.drawPath(seriesPath,seriesPaint);
			  		seriesPath.reset();
			
			}
			
			
			 
			seriesvals.put(n, values);
			 
			
		 
		 
		}
		
		
		 
	 }
	
	
	
	private void CalculateControlPoints(float x1, float y1, float x2, float y2,
			PointValues cp1) {
		
		
		
		cp1.x = (float) (0.5*x1 + (0.5*x2));
		cp1.y = (float) (0.5*y1 + (0.5*y2));
		
		
		
		
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
	
	
	
	private void drawGrid(Canvas canvas) {
		 
		  int nstops = hstops.axisstops.length/2;
		 // radii = (diameter/2)/nstops;
		  radii = (diameter/2)/(nstops-1);
		 
//		  for(int i=1; i<=nstops;i++ )
//		  {
		  
		  for(int i=0; i<nstops;i++ )
		  {
			  
			  canvas.drawCircle(coordbounds.centerX(),coordbounds.centerY(),
					  (float) (i*radii), gPaint);	
			  
			  
			  
		  }
		  
		  
		 
	}
	
	
	
	public void drawText(Canvas canvas)
	{
		
		canvas.drawText(textdesc,coordbounds.centerX(),boxbounds.top+2*mLabelSeparation, tPaint);
		
		
		if(modifyLabels)
		{
			String [] keys = catangles.keySet().toArray(new String [catangles.size()]);
			
			
			for(int i=0; i<catangles.size();i++)
			{
				
				double angle = (catangles.get(keys[i])*Math.PI)/180;
				System.out.println("CATANG" + keys[i]+ angle);
				drawAngle(canvas,angle,keys[i]);
			}
		}
		
		else
		{
			float num = 360/angleInterval;
			for(int i=0; i<num;i++)
			{
				String[] outstring= formatfloat(i*angleInterval,angleInterval,tPaint,mLabelWidth);
				drawAngle(canvas,angles[i],outstring[0]);
			}
		}
		
		
		
		drawNumbers(canvas,hstops.axisstops.length/2);
	}
	
	
	private void drawNumbers(Canvas canvas,int nstops) {
		
		//double radii = (diameter/2)/nstops;
		
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
	
		
		
		for(int i=1; i< nstops-1;i++ )
		{	
			 float x = (float) (coordbounds.centerX()+ (radii*i)*Math.cos(angles[0]));
			 float y = (float) (coordbounds.centerY()+ (radii*i)*Math.sin(angles[0]));
			 
			 
			 DrawTextNum(canvas,x,y,nstops+i,radii);

			  
		 }
		
		
		
		
		if(min!=0)
		{
			aPaint.setTextAlign(Paint.Align.CENTER);
			canvas.drawText("x10", coordbounds.centerX(),
					coordbounds.centerY(), aPaint);
			canvas.drawText(Integer.toString(min), coordbounds.centerX() + (aPaint.measureText("x10") / 2),
					coordbounds.centerY() + aPaint.ascent(), aPaint);
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
		
		
		double angle = (d * 180)/Math.PI;
		
		
		if(angle>=0 && angle<90)
		{
			System.out.println(">=0");
			tPaint.setTextAlign(Paint.Align.LEFT);
			float x = (float) (coordbounds.centerX()+(coordbounds.width()/2) * Math.cos(d))
					+mLabelSeparation;
			float y = (float) (coordbounds.centerY()+(coordbounds.height()/2)* Math.sin(d));
			canvas.drawText(label,x,y, tPaint);
		}
		
		else if(angle==90)
		{
			System.out.println("==90");
			tPaint.setTextAlign(Paint.Align.CENTER);
			float x = (float) (coordbounds.centerX()+(coordbounds.width()/2) * Math.cos(d));
			float y = (float) (coordbounds.centerY()+(coordbounds.height()/2)* Math.sin(d))
				+2*mLabelSeparation;
			canvas.drawText(label,x,y, tPaint);
		}
		
		else if(angle>90 && angle<=180)
		{
			System.out.println(">=90");
			tPaint.setTextAlign(Paint.Align.RIGHT);
			float x = (float) (coordbounds.centerX()+(coordbounds.width()/2) * Math.cos(d))
					-mLabelSeparation;
			float y = (float) (coordbounds.centerY()+(coordbounds.height()/2)* Math.sin(d));
			canvas.drawText(label,x,y, tPaint);
		}
		
		else if(angle>180 && angle<270)
		{
			System.out.println(">=180");
			tPaint.setTextAlign(Paint.Align.RIGHT);
			float x = (float) (coordbounds.centerX()+(coordbounds.width()/2) * Math.cos(d))
					-mLabelSeparation;
			float y = (float) (coordbounds.centerY()+(coordbounds.height()/2)* Math.sin(d));
			canvas.drawText(label,x,y, tPaint);
		}
		
		else if(angle==270)
		{
			System.out.println(">=180");
			tPaint.setTextAlign(Paint.Align.CENTER);
			float x = (float) (coordbounds.centerX()+(coordbounds.width()/2) * Math.cos(d));
				
			float y = (float) (coordbounds.centerY()+(coordbounds.height()/2)* Math.sin(d));
			canvas.drawText(label,x,y, tPaint);
		}
		
		 else if(angle>270 && angle<=360)
		 {
			
			 	tPaint.setTextAlign(Paint.Align.LEFT);
				float x = (float) (coordbounds.centerX()+(coordbounds.width()/2) * Math.cos(d))
						+mLabelSeparation;
				float y = (float) (coordbounds.centerY()+(coordbounds.height()/2)* Math.sin(d));
				canvas.drawText(label,x,y, tPaint);
		 }
		
     
		
	}
	
	
	public void setTickAngle(float angleInterval)
	{
		this.angleInterval=angleInterval;
	}
	

		public void setCategoryAsTrue(Boolean categorytrue)
		{
			this.modifyLabels= categorytrue;
		}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh)
	{
		
		float xpad = (float)(getPaddingLeft()+ getPaddingRight());
		float ypad = (float)(getPaddingTop() +  getPaddingBottom());
		
		float coww =0.0f,cohh=0.0f,coll =0.0f;
		
		
		init();
		
		coww = (float) w-xpad-2*mLabelWidth-2*mLabelSeparation;
		cohh = (float) h-ypad-(2*mLabelHeight+3*mLabelSeparation)-
				(legendSize+mLabelSeparation)-mLabelHeight-2*mLabelSeparation;
		
		
				

		float bxww = (float) w-xpad;
		float bxhh = (float) h-ypad;
		
		
		boxbounds = new RectF(getPaddingLeft(),getPaddingTop(),
				getPaddingLeft()+bxww,getPaddingTop()+bxhh);
		
		
		
		coll = bxhh-legendSize-mLabelSeparation;
		
		 diameter = Math.min(coww, cohh);
	     
		 coordbounds = new RectF(
	                0.0f,
	                0.0f,
	                diameter,
	                diameter);
		 
		 
		 float xoffset = mLabelWidth+mLabelSeparation;
	     
	     
	      coordbounds.offsetTo(boxbounds.centerX()-(diameter/2), boxbounds.centerY()-(diameter/2));
	     
	     legendBox = new RectF(0.0f,0.0f,coww,legendSize );
	   	 legendBox.offset(getPaddingLeft()+xoffset,coordbounds.bottom+mLabelSeparation);
	   	 
	   	 assignColors();
	   	 
	   	 getXYTicks();
	   	 
	   	 
		
		
	}
	
	private void getXYTicks() {
		
		 if(modifyLabels)
		 getXCategory();
		
		 
		 int num = (int)Math.ceil(360/angleInterval);
		 
		 angles = new double[num];
		 for(int i=0;i<num;i++)
		 {
			 
			 angles[i]= ((i*angleInterval)* Math.PI/180);
			 System.out.println("ANGLES" + angles[i]);
		 }
		 
		 
		 double [] val = new double[series.size()];
		 for(int i=0;i<series.size();i++)
		 {
			 val[i]= series.get(i).getValue();
		 }
		 
		 	for(int i=1; i<=numseries;i++)
			{
			
				Map<String,Double> angs = new HashMap<String,Double>();
				for(int j=0; j<series.size();j++)
				{
					 
				      angs.put(Double.toString(series.get(j).getPolarAngle()),
				    		  series.get(j).getPolarAngle());
				}
			
				angs = sortByComparator(angs,ASC);
				valangles.put(i, angs);
			}
		 	
		 	
		
		 getNumericalStops(val);
		
		
	}
	
	private void getXCategory() {
		
		 List<String> uniX= new ArrayList<String>();
		 for(int i=0; i<series.size();i++)
		 {
					
				uniX.add(series.get(i).getCategory());
		 }		
		Set<String> uxVal= new HashSet<String>(uniX);
		categoryLabels = uxVal.toArray(new String[uxVal.size()]);
		
		angleInterval = 360/categoryLabels.length;
		
		catangles = new HashMap<String,Float>();
		
		
		for(int i=0;i<categoryLabels.length;i++)
		{
			catangles.put(categoryLabels[i],i*angleInterval);
		}
		
		for(int i=0;i<categoryLabels.length;i++)
		{
			for(int j=0;j<series.size();j++)
			{
				Float angle = catangles.get(categoryLabels[i]);
				
				if(series.get(j).getCategory().equals(categoryLabels[i]))
				series.get(j).setPolarAngle(angle);
			}
		
		}
		
		for(int j=0;j<series.size();j++)
		{
			System.out.println("ANG" + series.get(j).getCategory()+series.get(j).getPolarAngle());
			
		}
		
		
		
		
		
	}




	public MinMax computeMaxMinRange(double[] array,double interval,int visTicks)
	{
		

		System.out.println("ARRAY LEN MAX"+ array[array.length-1]);
		System.out.println("ARRAY_LEN_MIN"+ array[0]);
		

		MinMax  values = new MinMax();
		double graphMin = (Math.floor(array[0]/hInterval)*hInterval);
		double graphMax = (Math.ceil(array[array.length-1]/hInterval)*hInterval);
		
		values.graphMax=graphMax;
		values.graphMin=graphMin;
		
		
		return values;
	}
	
	public IntervalStats determineReqTicks(
			double GraphMax, double GraphMin,double[] val)
	{
		
		
		   double avgsepH=0;
	
		   for(int i=0; i< val.length;i++)
		   {
			   System.out.println(val[i]);
		   
		   }
		   
		   IntervalStats stats = determineAvgSeparation(val);
		
		
			
			avgsepH= niceNumeral(stats.xavgdiff);
			
			
			System.out.println("AVGH"+ avgsepH);
			
			
			
			double min = Math.floor(GraphMin/avgsepH)*avgsepH;
			double max = Math.ceil(GraphMax/avgsepH)*avgsepH;
			
			double hrange = max -min;
			int hRTicks = (int)Math.ceil(hrange / avgsepH);

			stats.hRticks = hRTicks;
			
			stats.hinterval=niceNumeral(hrange/(stats.hRticks));
			
			hInterval = stats.hinterval;
			
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
	
	public IntervalStats determineAvgSeparation(double[] val)
	{
		

		
		
		double sumdiff =0;
			
		for(int i=0; i< val.length-1;i++)
		{
			sumdiff = (sumdiff + Math.abs(val[i+1]-val[i]));
			
		}
			
		double avgdiff =sumdiff/(val.length-1);
		
		IntervalStats stats = new IntervalStats();
		stats.xavgdiff = avgdiff;
		
		System.out.println(stats.xavgdiff);
		System.out.println(sumdiff);
			
		return stats;
	}

	
	private void getNumericalStops(double[]val) {
		
		Arrays.sort(val);
		
		
		ComputeStops cmp = new ComputeStops();
		IntervalStats stats = determineReqTicks
				( val[val.length-1],val[0],val);
		
		
		hInterval = stats.hinterval;
		hRTicks = stats.hRticks;
		
		
		MinMax Data =computeMaxMinRange(val,hInterval,stats.hRticks);
		
		System.out.println("GRAPHMAX" + Data.graphMax);
		System.out.println("GRAPHMIN" + Data.graphMin);
		System.out.println("HMIN" + hInterval);
		
		computeStopsNumPolar(stats.hRticks,hstops,Data.graphMin,Data.graphMax,hInterval);
	
		
	
		Viewport = new RectF((float)Data.graphMin,
				(float)Data.graphMin,
				(float)(Data.graphMax),(float)Data.graphMax);
		
		/*AXIS_H_MIN = (float)(hstops.axisstops[0]-hInterval);
		AXIS_H_MAX = (float)(hstops.axisstops[hstops.axisstops.length-1]+hInterval);
		
		AXIS_V_MIN = (float)(hstops.axisstops[0]+hInterval);
		AXIS_V_MAX = (float)(hstops.axisstops[hstops.axisstops.length-1]+hInterval);*/
		
	}
	
	public void computeStopsNum(int length,AxisStops
			stops,double graphMin,double graphMax,double interval)
	{
		
		double[] outstops = new double[2*(length+1)];
		
		
		 //outstops[0]=(float) -(graphMax+interval);
		 int a=0;
		 
		 
		 for(double x =graphMax+(0.5*interval); x<=(graphMin-interval);x=x-interval)
		 {
			
			outstops[a] = (float)-x;
			
			a++;
		 }
		 
		 outstops[a]=(float)(graphMin-interval);
		 
		 int b=1;
		 for(double x =graphMin; x<=(graphMax + (0.5*interval));x=x+interval)
		 {
			
			outstops[b] = (float)x;
			
			b++;
		 }
		 
		 stops.axisstops=outstops;
		//Includes the leftmost stop - i.e Viewport.left
		
		 
		 
		 
		
	}
	
	
	public void computeStopsNumPolar(int length,AxisStops stops,
			double graphMin,double graphMax,
			double interval)
	
	{
		
		double[] outstops = new double[2*length+2];
		int a=0;
		 
		 
		
		 
		 for(double x =(graphMax);x>=(graphMin-0.5*interval);x=x-interval)
		 {
			System.out.println("AXIS STOPS X" + x);
			 outstops[a] = (float)x;	
			a++;
			
		 }
		 
		 
	
		
		 int b=a;
		
		 
		 for(double x = graphMin; x<=
				 graphMax+0.5*interval ;x=
				 x+interval)
		 {
			
			System.out.println("AXIS STOPS X" + x);
			outstops[b] = (float)x;
		 	b++;
			
		 }
		
		 
		 
		 stops.axisstops=outstops;
		 
	}
	
	public void DrawTextNum(Canvas canvas,float x,float y,int pos,double radii)
	{

		float value = (float) (hstops.axisstops[pos]/Math.pow(10,min));
		 	
		System.out.println("VAL" + value +"MIN" + min);
		String outstring = formatfloatValue(value,
				hInterval,tPaint,radii);
		System.out.println("OUTSTRING" + outstring);
		
		
			System.out.println(hstops.axisstops[pos]);
			tPaint.setTextAlign(Paint.Align.LEFT);
			canvas.drawText(outstring, 0, outstring.length(),
					x, y, tPaint);

		 
		 
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
	
	private void assignColors()
	{
		if(numseries>=1)
		{
			getSeriesNames();
			if(assignSeriesColors)
			{
				calculateSeriesColors(numseries,
					useSeriesPrimary,accentprimarycolor,seriescolor);
				System.out.println("SERIESPRIMARY" + useSeriesPrimary);
				
			}
			else
				getSeriesColors();
		}
	}
	
	public void setSeries(List<CategoryItem> series,int numseries)
	{
		
		this.series=series;
		this.numseries=numseries;
		numinseries = new HashMap<Integer,Integer>();
		seriescolor = new HashMap<String,Integer>();
		for(int i=1; i<=numseries;i++)
		{
			int n=0;
			for(int m=0;m<series.size();m++)
			{
				if(series.get(m).getSeries()==i)
				{
					n++;
					
				}
			}
			
			numinseries.put(i,n);
		}
		
		int [] max = new int[numseries];
		for(int i=1; i<=numinseries.size();i++)
		{
			max[i-1]= numinseries.get(i);
		}
		
		Arrays.sort(max);
		
		values = new PointValues[max[max.length-1]];
		
		
		
		
		
		
		
	}
	
	public void calculateSeriesColors(int numseries,Boolean useSeriesPrimary,
			int accentprimarycolor,Map<String,Integer> seriescolor) {
		
		
		int[] colors = new int[numseries];
		ColorCode sercolor = new ColorCode();
		if(useSeriesPrimary)
			colors = sercolor.setPrimaryColor(numseries,60,0.8f,0.9f);
		else
			colors = sercolor.setHSVAccent(accentprimarycolor,numseries,0.6f,1.0f, 20);
		for(int i=0; i<numseries;i++)
		{
			seriescolor.put(seriesname[i], colors[i]);
			for(int j=0; j<series.size();j++)
			{
				
				if(seriesname[i].equals(series.get(j).getSeriesName()))
				{
					System.out.println("SERIES NAME" + seriesname[i]);
					series.get(j).setSeriesColor(seriescolor.get(seriesname[i]));
				}
			}
			
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
	
	 
		 
		 
		  
	
	private void getSeriesColors() {
		
		
		for(int i=0;i<seriesname.length;i++)
		{
			for(int j=0;j<series.size();j++)
			{
				if(series.get(j).getSeriesName()==seriesname[i])
				seriescolor.put(seriesname[i], series.get(j).getSeriesColor());
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
	 			PointValues[] vals = seriesvals.get(i+1);
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
	    	

//	    	;
	  
	    }
}
