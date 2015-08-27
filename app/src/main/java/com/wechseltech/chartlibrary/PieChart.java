
package com.wechseltech.chartlibrary;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class PieChart extends ChartView{

	//TODO Accents don't work properly
	//Chart seems a little off center
	//Fix on measure
	//Add a pointer to select an item
	AxisStops hstops,vstops;
	List<PieItem> series;
	
	Paint tPaint,aPaint,gPaint,rPaint,vPaint,pPaint
	,perPaint;
	Context context;
	//*******************************************
	
	PieItem chosenitem;
	
	double xpoint,ypoint;
	private ScaleGestureDetector mScaleGestureDetector=null;
	private GestureDetectorCompat mGestureDetector=null;
	private float mScaleFactor = 1.f;
	
	float mPosX,mPosY,diffX=0,diffY=0;
	//***********************************************
	
	RectF boxbounds,coordbounds;
	float centerX,diameter ;
	
	
	
	
	
	//********************************//
	
	
	double mSum=0.0;
	
	//*************************************//
	
	//************************************//
		
		
		
		
		int maxChar=0;
		
		Boolean usePrimary;
		//**********************************//
		
		
		
		//************************************//
		int mLabelWidth,
		mLabelHeight;
		
		//***********************************//
	
		public PieChart(Context context) {
		super(context);
		
		System.out.println("INIT C");
		series = new ArrayList<PieItem>();
		this.context=context;
		mScaleGestureDetector = new ScaleGestureDetector(context,mScaleGestureListener);
		
		
		
	}
	
	
	
	
	public PieChart(Context context,AttributeSet attrs) {
		super(context,attrs);
		System.out.println("INIT At");
		
		this.context=context;
		series = new ArrayList<PieItem>();
		
		
		
		
		mScaleGestureDetector = new ScaleGestureDetector(context,mScaleGestureListener);
		
		TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.Charts,0,0);
		
		try
		{
			
			
			mLabelSeparation = a.getDimension(R.styleable.Charts_labelSeparation, 8);
			mTextSize = a.getDimension(R.styleable.Charts_textSize, 12);
			textcolor = a.getColor(R.styleable.Charts_textColor, Color.BLACK);
			accentprimarycolor = a.getColor(R.styleable.Charts_accentprimaryColor, Color.RED);
			assignSeriesColors= a.getBoolean(R.styleable.Charts_assignSeriesColors, true);
			
			useSeriesPrimary =a.getBoolean(R.styleable.Charts_useSeriesPrimary, false);
			
			
			
			
		}
		
		finally
		{
			
			a.recycle();
		}
		
		
	}
	
	
	
	
	public PieChart(Context context,AttributeSet attrs,int defStyle) {
		
		
		super(context,attrs,defStyle);
		this.context=context;
		
		
		
		
		System.out.println("INIT DF");
		series = new ArrayList<PieItem>();
		
		mScaleGestureDetector = new ScaleGestureDetector(context,mScaleGestureListener);
		
		
		TypedArray a = context.getTheme().
				obtainStyledAttributes(attrs,R.styleable.Charts,defStyle,defStyle);
		
		try
		{
			
			mLabelSeparation = a.getDimension(R.styleable.Charts_labelSeparation, 8);
			mTextSize = a.getDimension(R.styleable.Charts_textSize, 12);
			textcolor = a.getColor(R.styleable.Charts_textColor, Color.BLACK);
			accentprimarycolor = a.getColor(R.styleable.Charts_accentprimaryColor, Color.RED);
			usePrimary =a.getBoolean(R.styleable.Charts_usePrimary, true);
			assignSeriesColors= a.getBoolean(R.styleable.Charts_assignSeriesColors, true);
			
			
			
		}
		
		finally
		{
			
			a.recycle();
		}
		
		
		
	}
	

	
	 @Override
	    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
	        super.onSizeChanged(w, h, oldw, oldh);

	        
	        System.out.println("CALL VIEWS"+ w +h );
	        
	        float xpad = (float) (getPaddingLeft() + getPaddingRight());
	        float ypad = (float) (getPaddingTop() + getPaddingBottom());

	        
	        init();
			
			float coww =0.0f,cohh=0.0f;
			
			coww = (float) w-xpad-2*mLabelWidth-mLabelSeparation;
			cohh = (float) h-ypad-2*(mLabelHeight+mLabelSeparation)-mLabelHeight-2*mLabelSeparation;
			
			
			float bxww = (float) w-xpad;
			float bxhh = (float) h-ypad;
			
			
			boxbounds = new RectF(getPaddingLeft(),getPaddingTop(),
					getPaddingLeft()+bxww,getPaddingTop()+bxhh);
			
			
			
	        // Figure out how big we can make the pie.
	        diameter = Math.min(coww, cohh);
	        
	        coordbounds = new RectF(
	                0.0f,
	                0.0f,
	                diameter,
	                diameter);
	        float loffset = mLabelWidth+mLabelSeparation;
	        float toffset = mLabelHeight+mLabelSeparation;
	        
	        
	        coordbounds.offsetTo(boxbounds.centerX()-(diameter/2), boxbounds.centerY()-(diameter/2));
	        
	        
	        assignColors();
	        
	        System.out.println(coordbounds);

	    }
	
	
	
	 
	private void init()
	{
		
		//setLayerToSW(this);
		
		//setWillNotDraw(false);
		//invalidate();
		
		// Set up the paint for the label text
        tPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        tPaint.setColor(textcolor);
        tPaint.setTextSize(mTextSize);
        tPaint.setTextAlign(Paint.Align.LEFT);
        
        
        perPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        perPaint.setColor(textcolor);
        perPaint.setTextAlign(Paint.Align.CENTER);
        
        
        
        
        
        pPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        pPaint.setStyle(Paint.Style.FILL_AND_STROKE);
       // pPaint.setColor(Color.RED);
        
     
       
        
        mLabelHeight =(int) Math.abs(tPaint.getFontMetrics().top);
        
        
        mLabelWidth = (int) Math.abs(tPaint.measureText("0000000"));
        
        maxChar = 10;
       
        
        
        
       
        
        
	}
	
	@Override 
	 public void onDraw(Canvas canvas)
	 
	 {
		super.onDraw(canvas);
		
		System.out.println("DRAW");
		
		
		
		canvas.save();
		
		
		canvas.translate(mPosX, mPosY);
	    canvas.scale(mScaleFactor, mScaleFactor);
	   
	    drawData(canvas);
	    
	    drawText(canvas);
	    
	    canvas.restore();
		
	 }
	
	
	
	
	


	private void drawText(Canvas canvas) {
		
		tPaint.setTextAlign(Paint.Align.CENTER);
		canvas.drawText(textdesc,coordbounds.centerX(),boxbounds.top+2*mLabelSeparation, tPaint);
		
		tPaint.setTextAlign(Paint.Align.LEFT);
	}




	private void drawData(Canvas canvas) {
		
		
		
		
		
		
		float StartAngle = 0.0f;
		float y =0.0f,x=0.0f;
		
		for (PieItem it : series) {
            
			System.out.println("GET ANGLE" + it.getAngle());
			pPaint.setColor(it.getColor());
            canvas.drawArc(coordbounds,
                    StartAngle,
                    it.getAngle(),
                    true, pPaint);
            
            it.setStartAngle(StartAngle);
           
            
            System.out.println("INCR" + StartAngle);
            
            double d = ((StartAngle + (it.getAngle()/2) )* Math.PI/180);
            
            
            double arcd = it.getAngle()*Math.PI/180;
            double medianangle = (StartAngle + (it.getAngle()/2) );
            
            
            
            //y = (float) (coordbounds.centerY()+(coordbounds.height()/2 * Math.sin(d)))+ 
            		//mLabelSeparation;
            
            
           // x = (float)(coordbounds.centerX()+(coordbounds.width()/2 * Math.cos(d)))
            		//+mLabelSeparation;
            
            
            System.out.println("PERCENT VALUE" + it.getPercentage());
            DecimalFormat format = new DecimalFormat("#.##");
			String per = format.format(it.getPercentage()) +"%";
			
			
			 System.out.println("PERCENT STRING" + per);
////			
			//float radii = coordbounds.height()/4;
			float radii = diameter/3;
			int arclength = (int) (radii*arcd);
			float posy = (float) (coordbounds.centerY()+(radii * Math.sin(d)));
			float posx = (float) (coordbounds.centerX()+(radii * Math.cos(d)));
			
			
			it.setXTextOffset(posx);
			
			it.setYTextOffset(posy);
			
			 float minLength = tPaint.measureText("0");
			 
			 float actLength= it.getXVal().length() * minLength;
			 

			
			if(medianangle>=0 & medianangle<=90)
			{
				System.out.println("median <90");
				 y = (float) (coordbounds.centerY()+(coordbounds.height()/2 * Math.sin(d)))
		            		+mLabelSeparation;
				 
				 //y = (float) (coordbounds.centerY()+(coordbounds.height()/2 * Math.sin(d)))
		            	//	+2*mLabelSeparation;
		            
		            
		          x = (float)(coordbounds.centerX()+(coordbounds.width()/2 * Math.cos(d)))
		        		  + mLabelSeparation;
		          
		         // x = (float)(coordbounds.centerX()+(coordbounds.width()/2 * Math.cos(d)))
		        		//  + 2*mLabelSeparation;
		          
		         
				
			}
			
			if(medianangle>90 & medianangle<=180)
			{
				
			
				System.out.println("median >90");
				  y = (float) (coordbounds.centerY()+(coordbounds.height()/2 * Math.sin(d)))
		            		+mLabelSeparation;
		            
		          if(actLength<mLabelWidth)
		          x = (float)(coordbounds.centerX()+(coordbounds.width()/2 * Math.cos(d)))
		        		   - (mLabelSeparation+actLength);
		          else if(actLength>mLabelWidth)
		           x = (float)(coordbounds.centerX()+(coordbounds.width()/2 * Math.cos(d)))
	        		   - (mLabelSeparation+mLabelWidth);
		          
		          
			}
			
			if(medianangle>180 & medianangle<=270)
			{
				System.out.println("median >180");
				y = (float) (coordbounds.centerY()+(coordbounds.height()/2 * Math.sin(d)))-
	            		mLabelSeparation;
	            
				if(actLength<mLabelWidth)
			          x = (float)(coordbounds.centerX()+(coordbounds.width()/2 * Math.cos(d)))
			        		   - (mLabelSeparation+actLength);
			          else if(actLength>mLabelWidth)
			           x = (float)(coordbounds.centerX()+(coordbounds.width()/2 * Math.cos(d)))
		        		   - (mLabelSeparation+mLabelWidth);
				//x = (float)(coordbounds.centerX()+(coordbounds.width()/2 * Math.cos(d)))
	            	//	- (mLabelSeparation+mLabelWidth);
				
			}
			
			if(medianangle>=270 & medianangle<=360)
			{
				System.out.println("median >180");
				y = (float) (coordbounds.centerY()+(coordbounds.height()/2 * Math.sin(d)))-
	            		mLabelSeparation;
	            
	            
				x = (float)(coordbounds.centerX()+(coordbounds.width()/2 * Math.cos(d)))
	            		-mLabelSeparation;
				
				
			}
			
			if(actLength<=mLabelWidth)
				canvas.drawText(it.getXVal(),x, y, tPaint);
			else
				canvas.drawText(it.getXVal().substring(0,maxChar), x, y, tPaint);
			
			System.out.println("ARC LENGTH" +  arclength);
			
			if(arclength>=tPaint.measureText(per))
			{
				System.out.println("AXIS GREATER"+tPaint.measureText(per));
				perPaint.setTextSize(mTextSize);
				canvas.drawText(per,posx,posy, perPaint);
			}
			
			if(arclength<tPaint.measureText(per))
			{
				System.out.println("AXIS SMALLER"+tPaint.measureText(per)+ "ARC"+((float)0.8*arclength));
				
				float tx = (float) ((tPaint.getTextSize()*(arclength))/tPaint.measureText(per));
				System.out.println("TX"+tx);
						
						
				perPaint.setTextSize(tx);
				
				canvas.drawText(per,posx,posy, perPaint);
			}
			
			it.setEndAngle(StartAngle + it.getAngle());
		     
           StartAngle = StartAngle + it.getAngle();
           
           
            
        }
		
		
	}




	//***********************************************//
	
	private void assignColors()
	{
		if(assignSeriesColors)
			calculateAnglesAndColors();
			else
			calculateAngles();
	}
	public void setSeries(List<PieItem> series)
	{
		this.series=series;
		
		
	}
	
	
	private void calculateAngles() {
		
		for(int i=0;i<series.size();i++)
		{
			mSum+=series.get(i).getYVal();
			System.out.println(series.get(i).getYVal());
		}
		
		System.out.println("SUM" + mSum);
		
		int j=0;
		for (PieItem it: series)
		{
			float percent = (float)(Math.abs(it.getYVal())/mSum)*100;
			
			
			
			
			float angle = (float)(Math.abs(it.getYVal())/mSum *360f);
			
			
			
			it.setPercentage(percent);
			
			it.setAngle(angle);
			
			
			j++;
		}
		
		
	}




	private void calculateAnglesAndColors() {
		
		ColorCode colors = new ColorCode();
		int [] setcolors =new int[series.size()];
		if(useSeriesPrimary)
		
			
		{
			setcolors = colors.setPrimaryColor(series.size(),40,0.8f,0.9f);
			System.out.println("COL" + setcolors.length+"SER" + series.size());
		
		}
		else
		
			setcolors = colors.setHSVAccent(accentprimarycolor,series.size(),0.4f,1.0f, 20);
		
		
		
		for(int i=0;i<series.size();i++)
		{
			mSum+=series.get(i).getYVal();
			System.out.println(series.get(i).getYVal());
		}
		
		System.out.println("SUM" + mSum);
		
		int j=0;
		for (PieItem it: series)
		{
			float percent = (float)(Math.abs(it.getYVal())/mSum)*100;
			
			System.out.println("Percent" +j + percent);
			
			
			float angle = (float)(Math.abs(it.getYVal())/mSum *360f);
			
			System.out.println("Angle degrees" +j + angle);
			
			it.setPercentage(percent);
			
			it.setAngle(angle);
			it.setColor(setcolors[j]);
			
			j++;
		}
		
	}




	public List<PieItem> getSeries()
	{
		return series;
	}
	
	
	//*************************************************************//
	
	
	
	
	
	@Override 
	 public boolean onTouchEvent(MotionEvent event)
	 {
	
		
		 //mGestureDetector.onTouchEvent(event);
		
		
		if(event.getAction()==MotionEvent.ACTION_UP)
		{
			
			xpoint = event.getX();
			ypoint = event.getY();
			
			System.out.println("XPOINT" + xpoint);
			System.out.println("YPOINT" + ypoint);
			
			getChosenPoint();
			
			
		    
			
		}
		
		mScaleGestureDetector.onTouchEvent(event);
		

		 return true;
	 }
		
	 private PieItem whereIsInPie(float x, float y) {
		
		 PieItem chosen =null;
		 float hyp = (float) (Math.pow((x-coordbounds.centerX()),2) +  Math.pow((y-coordbounds.centerY()),2)) ;
		
		 if(Math.sqrt(hyp)<=diameter/2)
		 {
			 float diffx = x- coordbounds.centerX();
			 float diffy = -(y- coordbounds.centerY());
			 double rad = Math.atan2(diffy, diffx);
			 if(rad<0)
			 {
				 rad = Math.abs(rad);
			 }
			 else
			 {
				 rad = 2* Math.PI-rad;
			 }
			 
			 double degree =Math.toDegrees(rad);
			
			 for(PieItem item : series)
			 {
				 if(degree>= item.getStartAngle()& degree <= item.getEndAngle())
				     chosen = item;
				 
			 }
		 }
		 
		 
		return chosen;
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
	
	  
		
	 
	 @TargetApi(Build.VERSION_CODES.HONEYCOMB) private void setLayerToSW(View v) {
	        if (!v.isInEditMode() && Build.VERSION.SDK_INT >= 11) {
	            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
	        }
	    }

	    @TargetApi(Build.VERSION_CODES.HONEYCOMB) private void setLayerToHW(View v) {
	        if (!v.isInEditMode() && Build.VERSION.SDK_INT >= 11) {
	            setLayerType(View.LAYER_TYPE_HARDWARE, null);
	        }
	    }

	


	    public Selected getChosenPoint()
	    {
	    	chosenitem = whereIsInPie((float)xpoint,(float)ypoint);
	    	Selected  selected = new Selected();
	    	selected.setSelected(chosenitem.getXVal());
	    	selected.setValue(chosenitem.getYVal());
	    	selected.setAnchorX((float)xpoint);
	    	selected.setAnchorY((float)ypoint);
	    	
	    	System.out.println("CHOSENITEM" + chosenitem.getXVal());
	    	
	    	Toast toast = Toast.makeText(context, chosenitem.getXVal()+"-"+Double.toString(chosenitem.getYVal()), Toast.LENGTH_SHORT);
	    	toast.setGravity(Gravity.TOP|Gravity.LEFT,(int)(boxbounds.left+chosenitem.getXTextOffset()),
	    			(int)(boxbounds.top+chosenitem.getYTextOffset()));
	    	toast.show();
	    	;
	    	
	    	
	    	return selected;
	    }




		
	  
		
}
