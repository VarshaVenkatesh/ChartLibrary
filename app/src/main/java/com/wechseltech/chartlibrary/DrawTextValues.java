package com.wechseltech.chartlibrary;


import java.text.DecimalFormat;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

public class DrawTextValues {

	
	Paint tPaint,aPaint,vPaint,fPaint;
	
	int mAxisLabelHeight,mAxisLabelWidth,
	subLabelHeight, nLabelWidth,nhLabelWidth,nvLabelWidth;
	RectF coordbounds;
	String HLabelName;
	float mLabelSeparation;
	
	
	
	public DrawTextValues(Paint tPaint,Paint aPaint,
			int mAxisLabelHeight, int mAxisLabelWidth,
			RectF coordbounds,int subLabelHeight,int nLabelWidth,float mLabelSeparation,
			Paint vPaint,Paint fPaint,int nhLabelWidth, int nvLabelWidth)
	{
		this.coordbounds=coordbounds;
		this.tPaint=tPaint;
		this.vPaint=vPaint;
		this.fPaint=fPaint;
		this.aPaint=aPaint;
		this.mAxisLabelHeight=mAxisLabelHeight;
		this.mAxisLabelWidth=mAxisLabelWidth;
		this.subLabelHeight=subLabelHeight;
		this.nLabelWidth = nLabelWidth;
		this.nhLabelWidth = nhLabelWidth;
		this.nvLabelWidth = nvLabelWidth;
		
		this.mLabelSeparation=mLabelSeparation;
	}
	
	private String[] formatfloat(float floatvalue, double interval, 
			Paint tPaint,int nLabelWidth) {
		
		String numstring ;
		int numsig=0;
		if(interval<1)
			
			numsig =(int)Math.ceil(-Math.log10(interval));
		
		 String append = "#.";
		 
		 String nodec = "#";
		 
		 for(int i=0; i<numsig;i++)
			 
			 append.concat("#");
		 
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
					trimsig =(int)Math.ceil(-Math.log10(sigint));
				
				String pattern = "#.";
				 for(int i=0; i<trimsig;i++)
					 pattern.concat("#");
				numstring = new DecimalFormat(pattern).format(value);
				
				
			}
		
		String [] strings = new String[2];
		strings[0]=numstring;
		System.out.println("EXP" + exp);
		if(exp!=0)
			strings [1] = Integer.toString(exp);
		return strings;
		
		
	}
	
	public void DrawTextNumVerticalBar(Canvas canvas,String axisLabel,float startposV,int pos,AxisStops 
			stops,float startposH,float dim,double interval,Boolean horizontal,Boolean flipped ,int min)
	{
		
	
		 
		 float minLength = tPaint.measureText("0");
		 
		  //String[] outstring = formatfloat(stops.axisstops[pos],interval,tPaint,nLabelWidth);
		 	
		 	//System.out.println(outstring[0]);
		 
		  String outstring = formatfloatValue(stops.axisstops[pos]/Math.pow(10,min),interval,tPaint,nLabelWidth);
			
			// TODO ADD Exponent to the sublabel
			
		 	float axislabelLength= axisLabel.length() * minLength;
//			
		 	int numaxischar =0;
//			
			if(mAxisLabelWidth - axislabelLength<0)
			 {
				numaxischar =(int) Math.ceil((mAxisLabelWidth - axislabelLength)/minLength);
			 }
			
		
				 float xx = startposH+ 2 * mAxisLabelHeight + aPaint.ascent() +  nLabelWidth/2;
			
				float x = startposH+ 2 * mAxisLabelHeight - aPaint.ascent() - nLabelWidth;
			
				 if(stops.posstops[pos]<=coordbounds.bottom)
				 {
					 
					 		if(flipped)
					 		{
					 			canvas.save();
					 			canvas.rotate((float)(90));
					 			if(pos<stops.posstops.length-1)
					 			canvas.drawText(outstring,0,outstring.length(), 
									stops.posstops[pos],xx, tPaint);
					 			canvas.restore();
					 		}
					 		else
					 		{
					 			System.out.println("STAY UNFLIPPED");
					 			if(pos<stops.posstops.length-1)
					 			canvas.drawText(outstring,0,outstring.length(), 
										x,stops.posstops[pos], vPaint);
					 			
					 			
					 			
					 		}
					 
					 
				 }
				 
		
				 
//				 if(outstring[1]!=null)
//				 {
//					 
//					
//					 	if(Integer.parseInt(outstring[1])!=0)
//					 	{
//					 			String string = "x10";
//					 				canvas.drawText(string,0,string.length(),
//							
//					 						startposH+ (3 *mAxisLabelHeight/2), startposV + dim/2,aPaint);
//					
//					 				canvas.drawText(outstring[1],0,outstring[1].length(),
//							
//					 							startposH+ (3 *mAxisLabelHeight/2)+
//					 							
//					 				aPaint.ascent(), startposV+ dim/2+aPaint.measureText(string)/2+2,aPaint);
//					 	}
//					 
//				 }
 
		 
	}
	
	

	
	
	public void DrawTextNum(Canvas canvas,float startposV,int pos,AxisStops 
			stops,float startposH,float dim,double interval,Boolean horizontal,Boolean flipped )
	{
		
	
		 
		 float minLength = tPaint.measureText("0");
		 
		  String[] outstring = formatfloat((float)stops.axisstops[pos],interval,tPaint,nLabelWidth);
		 	
		 	System.out.println(outstring[0]);
			
			// TODO ADD Exponent to the sublabel
			
		 	float axislabelLength = 0;
		 	
		 	
		 	/*if(axisLabel!=null)
		 	axislabelLength= axisLabel.length() * minLength;
//			
		 	int numaxischar =0;
//			
			if(mAxisLabelWidth - axislabelLength<0)
			 {
				numaxischar =(int) Math.ceil((mAxisLabelWidth - axislabelLength)/minLength);
			 }*/
			
			 if(horizontal)
			 {
			
				 float y = startposV +(2 * mAxisLabelHeight + aPaint.ascent() + subLabelHeight/2);
				 
				// float y = startposV -(2 * mAxisLabelHeight + aPaint.ascent() + subLabelHeight/2);
				 
				 
				
				 
				 
				 if(stops.posstops[pos]>=coordbounds.left)
				 {
					 
					 if(flipped)
				 		{
						   
						 	float yval = startposV -(2 * mAxisLabelHeight + aPaint.ascent() + subLabelHeight/2);
						 	System.out.println("FLIPPED");
				 			canvas.save();
				 			canvas.rotate((float)(90));
				 			canvas.drawText(outstring[0],0,outstring[0].length(), 
									 stops.posstops[pos],yval, tPaint);
				 			canvas.restore();
				 		}
				 		else
				 		{
				 			
				 			canvas.drawText(outstring[0],0,outstring[0].length(), 
									 stops.posstops[pos],y, tPaint);
				 		}
					 
				 
				 
				 }
				 
				 
			
//				 canvas.drawText(axisLabel,0,axisLabel.length(),
//					startposH + dim/2,
//					startposV+(mAxisLabelHeight/2), aPaint);
				 
				 if(outstring[1]!=null)
				 {
					
						 if(Integer.parseInt(outstring[1])!=0)
							{
							String string = "x10";
							canvas.drawText(string,0,string.length(),
									startposH + dim/2,
									startposV+ (3 *mAxisLabelHeight/2)+aPaint.ascent(), aPaint);
							
							canvas.drawText(outstring[1],0,outstring[1].length(),
									startposH + dim/2+aPaint.measureText(string)/2+2,
									startposV+ (3 *mAxisLabelHeight/2), aPaint);
							}
				 
				 }
			
			 }
			 else
			 {
				 
				 
				 System.out.println("N LABEL WIDTH" + nLabelWidth/2);
				 
				 float x = startposH+ 2 * mAxisLabelHeight + aPaint.ascent() +  nLabelWidth/2;
				 if(stops.posstops[pos]<=coordbounds.bottom)
				 {
					 if(pos<stops.posstops.length-1)
						 canvas.drawText(outstring[0],0,outstring[0].length(), 
								 x,stops.posstops[pos], tPaint);
				 }
				 
				 System.out.println("SecondPart"+ outstring[1]);
			
//				 canvas.drawText(axisLabel,0,axisLabel.length()-numaxischar,
//						 startposH+(mAxisLabelHeight/2),startposV + dim/2,
//					 aPaint);
//				 
				 
				 /*if(outstring[1]!=null)
				 {
					 
					
					 	if(Integer.parseInt(outstring[1])!=0)
					 	{
					 		
					 		
					 		
					 		
					 		
					 				canvas.save();
					 				String string = "x10";
					 				
					 				canvas.rotate(90);
					 				
					 				
					 				canvas.drawText(string,0,string.length(),
							
					 						startposV - dim/2,startposH-mAxisLabelHeight- nLabelWidth, 
					 						 aPaint);
					
					 				canvas.drawText(outstring[1],0,outstring[1].length(),
					 						
					 						startposV - dim/2 - aPaint.ascent(),
							
					 							startposH - mAxisLabelHeight- nLabelWidth-aPaint.measureText(string)/2+2,aPaint);
					 	
					 				
					 				canvas.restore();
					 				
					 	}
					 
				 }*/
			 }
//			
			
		 
		 
	}
	
	
	public void DrawTextNumExponent(Canvas canvas,String axisLabel,float startposV,int pos,AxisStops 
			stops,float startposH,float dim,double interval,Boolean horizontal,Boolean flipped,int min)
	{
		
	
		 
		 float minLength = tPaint.measureText("0");
		 
		  String outstring = formatfloatValue(stops.axisstops[pos]/Math.pow(10,min),interval,tPaint,nLabelWidth);
		 	
		 	
			
			// TODO ADD Exponent to the sublabel
			
		 	float axislabelLength = 0;
		 	
		 	
		 	if(axisLabel!=null)
		 	axislabelLength= axisLabel.length() * minLength;
//			
		 	int numaxischar =0;
//			
			if(mAxisLabelWidth - axislabelLength<0)
			 {
				numaxischar =(int) Math.ceil((mAxisLabelWidth - axislabelLength)/minLength);
			 }
			
			 if(horizontal)
			 {
			
				 //float y = startposV +(2 * mAxisLabelHeight + aPaint.ascent() + subLabelHeight/2);
				 
				// float y = startposV -(2 * mAxisLabelHeight + aPaint.ascent() + subLabelHeight/2);
				 
				 System.out.println("SUBLABELHT" + subLabelHeight);
				 
				 float y = startposV+subLabelHeight/2+mLabelSeparation;
				 
				 if(stops.posstops[pos]>=coordbounds.left)
				 {
					 
					 if(flipped)
				 		{
						   
						 
						 
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
				 			
				 			
				 			System.out.println("UNFLIPPPED");
				 			 //if(pos<stops.posstops.length-1)
				 			canvas.drawText(outstring,0,outstring.length(), 
									 stops.posstops[pos],y, fPaint);
				 		}
					 
				 
				 
				 }
				 
				 
			
			 }
			 else
			 {
				 
				 
				
				 
				 //float x = startposH+ 2 * mAxisLabelHeight +aPaint.ascent() +  nLabelWidth/2;
				 
				 //float x = startposH+ 2 * mAxisLabelHeight - aPaint.ascent() +  nLabelWidth-mLabelSeparation;
				 
				 
				 float x = startposH+ 2 * mAxisLabelHeight- aPaint.ascent() +  nLabelWidth/2;
				 
				 
				 
				 if(stops.posstops[pos]<=coordbounds.bottom)
				 {
					 int mLabelHeight =(int) Math.abs(tPaint.getFontMetrics().top);
					 //if(pos<stops.posstops.length-1)
						 canvas.drawText(outstring,0,outstring.length(), 
								 x,stops.posstops[pos]+tPaint.descent()/2, tPaint);
				 }
				 
				 
			

			 }
//			
			
		 
		 
	}
	
	
	
	public void DrawTextNumHExponent(Canvas canvas,String axisLabel,float startposV,int pos,AxisStops 
			stops,float startposH,float dim,double interval,Boolean horizontal,Boolean flipped,int min)
	{
		
	
		 
		 
		 
		  
		 	
			 if(horizontal)
			 {
					 
				 System.out.println("SUBLABELHT" + subLabelHeight);
				 
				 //float y = startposV+subLabelHeight/2+mLabelSeparation;
				 
				 float y = startposV+nvLabelWidth/2+mLabelSeparation;
				 
				 if(stops.posstops[pos]>=coordbounds.left)
				 {
					 
					 if(flipped)
				 		{
						   
						 
						 String outstring = formatfloatValue(stops.axisstops[pos]/Math.pow(10,min),interval,tPaint,subLabelHeight);
						 
						 
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
				 			
				 			String outstring = formatfloatValue(stops.axisstops[pos]/Math.pow(10,min),interval,tPaint,nhLabelWidth);
				 			System.out.println("UNFLIPPPED");
				 			 //if(pos<stops.posstops.length-1)
				 			canvas.drawText(outstring,0,outstring.length(), 
									 stops.posstops[pos],y, fPaint);
				 		}
					 
				 
				 
				 }
				 
				 
			
			 }
			
//			
			
		 
		 
	}
	
	
	public void DrawTextNumVExponent(Canvas canvas,String axisLabel,float startposV,int pos,AxisStops 
			stops,float startposH,float dim,double interval,Boolean horizontal,Boolean flipped,int min)
	{
		 
		  String outstring = formatfloatValue(stops.axisstops[pos]/Math.pow(10,min),interval,tPaint,nvLabelWidth);
	 
		  float x = startposH+ 2 * mAxisLabelHeight- aPaint.ascent() +  nvLabelWidth/2;
				 
				 
				 
		 if(stops.posstops[pos]<=coordbounds.bottom)
		 {
					 
					 //if(pos<stops.posstops.length-1)
						 canvas.drawText(outstring,0,outstring.length(), 
								 x,stops.posstops[pos]+tPaint.descent()/2, tPaint);
		}
				 
				 
	}
	
	
	
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
	
	public void DrawTextCategoryVerticalBar(String label,Canvas canvas,String axisLabel,float 
			startposV,int pos,AxisStops 
			stops,float startposH,float dim,Boolean horizontal,Boolean flipped)
	{
	
		float minLength = tPaint.measureText("0");
		 
		 float actLength= label.length() * minLength;
		 //float axislabelLength= HLabelName.length() * minLength;
		 int numchar=0;
		 int numaxischar =0;
		 
		 if(nLabelWidth - actLength<0)
		 {
			numchar =(int) Math.ceil((nLabelWidth-actLength)/minLength);
		 }
		 
//		 if(mAxisLabelWidth - axislabelLength<0)
//		 {
//			numaxischar =(int) Math.ceil((mAxisLabelWidth - axislabelLength)/minLength);
//		 }
		 					
		 
			 
			 
			 
			 	//float x = startposH+ 2 * mAxisLabelHeight + aPaint.ascent() + nLabelWidth/2;
		 
		 		float x = startposH-2*mAxisLabelHeight-(-aPaint.ascent());
		 		
		 		System.out.println("mAXIS"+ mAxisLabelHeight);
			 
			 	if(stops.posstops[pos]<=coordbounds.bottom)
			 	{
			 		if(flipped)
			 		{
//			 		canvas.drawText(label,0,label.length(),
//						x,stops.posstops[pos], tPaint);
			 			canvas.save();
			 			canvas.rotate((float)(90));
			 			System.out.println("X"+ x + "STOPS" + stops.posstops[pos]);
			 			canvas.drawText(label,0,label.length(),
								stops.posstops[pos],x, tPaint);
			 			canvas.restore();
			 			
			 		
			 		}
			 		else
			 		{
			 			
			 			canvas.drawText(label,0,label.length(),x,stops.posstops[pos], tPaint);
			 			
			 			
			 		}
				
			 	}	
				
				
				System.out.println("DrawTextVCategory"+stops.posstops[pos]);
			
				
				
				System.out.println("DrawTextVAxisCategory"+stops.posstops[pos]);
			 
		 
		
		
	}
	public void DrawTextCategory(String label,Canvas canvas,float 
			startposV,int pos,AxisStops 
			stops,float startposH,float dim,Boolean horizontal,Boolean flipped)
	{
		 
		 float minLength = tPaint.measureText("0");
		 
		 float actLength= label.length() * minLength;
		 //float axislabelLength= HLabelName.length() * minLength;
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
		 	
		 	if(stops.posstops[pos]>=coordbounds.left & stops.posstops[pos]<=coordbounds.right)
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
		 			
//		 			canvas.drawText(label,0,label.length()
//					
//		 			,coordbounds.bottom,coordbounds.left,tPaint);
//		 				 			canvas.drawText(label,0,label.length()
//							 			,(coordbounds.bottom - coordbounds.height()/2),
//							 			(coordbounds.left+  coordbounds.width()/2),tPaint);
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
}
