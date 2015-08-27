package com.wechseltech.chartlibrary;

import java.util.Arrays;

import android.graphics.Color;

public class ColorCode {

	
	int r,g,b,a;
	int length;
	int [] colors;
	public ColorCode()
	{
		
	}
	
	public int[] setHSVAccent(int primcolor,int length,float dcr,float sat,float incr)
	{
		colors = new int[length];
		int n=1,s=0;
		
		
		int g = Color.green(primcolor);
		int r = Color.red(primcolor);
		int b = Color.blue(primcolor);
		
		float [] hsv = new float [3];
		
		Color.RGBToHSV(r, g, b, hsv);
				
		System.out.println("RGB"+ r + g +b);
		System.out.println("HSV"+ hsv[0]+ hsv[1] +hsv[2]);
		
		for(int m=0; m<length;m++)
			
		{   
			
			float[] vals = null;
			if(m*dcr<0.63f)
			{
				 System.out.println("CHOOSING ACCENTS" + primcolor);
				 vals = new float[]{hsv[0],hsv[1]-(m*dcr),hsv[2]};
				 System.out.println("H" + vals[0] + vals[1] + vals[2]);
			}
			else
			{
				
				if(s*dcr<0.63f)
				{
					System.out.println("FINDING ACCENTS");
					if(hsv[0]+(n*incr)<=360)
							vals = new float[]{hsv[0]+(n*incr),sat-(s*dcr),1.0f};
					else
							vals = new float[]{0+(n*incr),sat-(s*dcr),1.0f};
					 s++;
					 float pc =hsv[0]+(n*incr);
					 
					 System.out.println("VAL" +pc);
					 System.out.println("H" + vals[0] + vals[1] + vals[2] + "N" + n);
				}
				
				else
				{
					
					n++;
					s=0;
					
					if(s*dcr<0.63f)
					{
						System.out.println("FINDING ACCENTS");
						if(hsv[0]+(n*incr)<=360)
								vals = new float[]{hsv[0]+(n*incr),sat-(s*dcr),1.0f};
						else
								vals = new float[]{0+(n*incr),sat-(s*dcr),1.0f};
						 s++;
						 float pc =hsv[0]+(n*incr);
						 
						 System.out.println("VAL" +pc);
						 System.out.println("H" + vals[0] + vals[1] + vals[2] + "N" + n);
					}
					System.out.println("INCREMENTING");
				}
			}
			
			System.out.println("VAL" + vals);
			if(vals!=null)
			colors[m]= Color.HSVToColor(vals);
			else
			colors[m] =primcolor;
		}
		
		return colors;	
	}
	
	
	
	public int[] setAccentColor(int r,int g,int b,int length,int incr)
	{
		int lasti=0;
		int[] colorconcat = null;
		colors = new int[length];
		//USE RGB
			if((length-1)*incr<=255)
			{
				
			
				for (int i=0;i<length;i++)
				{
					
					colors[i]= Color.argb(0+(i*incr), r, g, b);
						
				}
			
				colorconcat=colors;
			}
			
			else
				
			{
				
				for (int i=0;i<length;i++)
				{
					//TODO SIMPLIFY LOGIC
					if(i*incr<255)
					{
					colors[i]= Color.argb(0+(i*incr), r, g, b);
					lasti=i+1;
					}
					else 
					{	colorconcat = colors;
						int ntimes = (int) Math.floor(((double)length)/(lasti-1));
						int diff = length-(ntimes*(lasti-1));
						
						for(int a=1;a<=ntimes;a++)
						{
							/*for(int c=0;c<lasti-1;c++)
								colors[a*lasti]= Color.argb(0+(c*incr), r, g, b);*/
							
							colorconcat[a] = lasti*a;
								
								
						}
						
						if(diff!=0)
						{
							
							colorconcat = Arrays.copyOf(colorconcat, colorconcat.length+diff);
							for(int m=0; m<diff; m++)
								colorconcat[m] = ntimes*lasti;
						}
							
						
						
						
					}
					
					
						
				}
			}
		
			return colorconcat;
		
	}
	
	public int[] setPrimaryColor(int length,int incr,float sat,float val)
	{
		
		int lasti=0;
		int[] colorconcat = null;
		colors = new int[length];
		//USE HSV - HUE (0..360), Sat -0-1,Val-0-1
			if((length-1)*incr<=360)
			{
				
				
				for (int i=0;i<length;i++)
				{
					float[] vals = new float[]{0+(i*incr),sat,val};
					colors[i]= Color.HSVToColor(vals);
						
				}
				
				colorconcat=colors;
			
			}
			
			else
			{
				for (int i=0;i<length;i++)
				{
					//TODO SIMPLIFY LOGIC
					if(i*incr<255)
					{
						float[] vals = new float[]{0+(i*incr),sat,val};
						colors[i]= Color.HSVToColor(vals);
						lasti=i+1;
					}
					else 
					{	colorconcat = colors;
						int ntimes = (int) Math.floor(((double)length)/(lasti-1));
						int diff = length-(ntimes*(lasti-1));
						
						for(int a=1;a<=ntimes;a++)
						{
							/*for(int c=0;c<lasti-1;c++)
								colors[a*lasti]= Color.argb(0+(c*incr), r, g, b);*/
							
							colorconcat[a]=lasti*a;
								
								
						}
						
						if(diff!=0)
						{
							colorconcat = Arrays.copyOf(colorconcat, colorconcat.length+diff);
							for(int m=0; m<diff; m++)
								colorconcat[m] = ntimes*lasti;
							
						}
							
						
						
						
						
					}
					
					
						
				}
				
			}
			return colorconcat;
		
		
	}
	
}
