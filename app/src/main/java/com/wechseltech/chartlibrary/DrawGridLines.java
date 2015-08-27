package com.wechseltech.chartlibrary;

import android.graphics.RectF;

public class DrawGridLines {

	float [] AxisHGridLines,AxisVGridLines;
	public DrawGridLines()
	{
		
		
	}
	
	public float[] drawHorizontalGrid(AxisStops hstops,RectF coordbounds,Boolean gridshown)
	{
		AxisHGridLines = new float[(hstops.posstops.length-1)*4];
//		
		
		if(gridshown)
		{
		
		for(int i=1;i<hstops.posstops.length;i++)
		{
			
			
//			canvas.drawLine(hstops.posstops[i],coordbounds.bottom-8,
//					hstops.posstops[i] ,coordbounds.bottom,gPaint);
			
			if(i%2==0)
			{
				System.out.println(Math.floor(hstops.posstops[i]));
				
				AxisHGridLines[(i-1)*4+0]= (float)Math.floor(hstops.posstops[i]);
				
				
				System.out.println(AxisHGridLines[(i-1)*4+0]+ "GRID-H");
				AxisHGridLines[(i-1)*4+1]= coordbounds.top;
				
				System.out.println(AxisHGridLines[(i-1)*4+1]);
				
				AxisHGridLines[(i-1)*4+2]= (float)Math.floor(hstops.posstops[i]);
			
				System.out.println(AxisHGridLines[(i-1)*4+2]);
				
				
				AxisHGridLines[(i-1)*4+3]=coordbounds.bottom;
				
				System.out.println(AxisHGridLines[(i-1)*4+3]);
			}
				
			
			
			

			
			
			
		}
		
		
		
		}
		
		else
			
			
		{
			
			
			for(int i=1;i<hstops.posstops.length;i++)
			{
				
				
//				canvas.drawLine(hstops.posstops[i],coordbounds.bottom-8,
//						hstops.posstops[i] ,coordbounds.bottom,gPaint);
				
				if(i%2==0)
				{
					System.out.println(Math.floor(hstops.posstops[i]));
					
					AxisHGridLines[(i-1)*4+0]= (float)Math.floor(hstops.posstops[i]);
					
					
					System.out.println(AxisHGridLines[(i-1)*4+0]+ "GRID-H");
					AxisHGridLines[(i-1)*4+1]= coordbounds.bottom-8;
					
					System.out.println(AxisHGridLines[(i-1)*4+1]);
					
					AxisHGridLines[(i-1)*4+2]= (float)Math.floor(hstops.posstops[i]);
				
					System.out.println(AxisHGridLines[(i-1)*4+2]);
					
					
					AxisHGridLines[(i-1)*4+3]=coordbounds.bottom;
					
					System.out.println(AxisHGridLines[(i-1)*4+3]);
				}
					
				
				
				

				
				
				
			}
			
			
			
			
			
		}
		
		System.out.println("AXL"+AxisHGridLines.length+"HP"+hstops.posstops.length);
		
		return AxisHGridLines;
		
		
	}
	
	
	public float[] drawHorizontalGridCategory(AxisStops hstops,RectF coordbounds)
	{
		AxisHGridLines = new float[(hstops.posstops.length-1)*4];
//		
		
		for(int i=1;i<hstops.posstops.length;i++)
		{
			
			
//			canvas.drawLine(hstops.posstops[i],coordbounds.bottom-8,
//					hstops.posstops[i] ,coordbounds.bottom,gPaint);
			
			
				System.out.println(Math.floor(hstops.posstops[i]));
				
				AxisHGridLines[(i-1)*4+0]= (float)Math.floor(hstops.posstops[i]);
				
				
				System.out.println(AxisHGridLines[(i-1)*4+0]+ "GRID-H");
				AxisHGridLines[(i-1)*4+1]= coordbounds.bottom-8;
				
				System.out.println(AxisHGridLines[(i-1)*4+1]);
				
				AxisHGridLines[(i-1)*4+2]= (float)Math.floor(hstops.posstops[i]);
			
				System.out.println(AxisHGridLines[(i-1)*4+2]);
				
				
				AxisHGridLines[(i-1)*4+3]=coordbounds.bottom;
				
				System.out.println(AxisHGridLines[(i-1)*4+3]);
				
			
			
			
			
			
			
		}
		
		System.out.println("AXL"+AxisHGridLines.length+"HP"+hstops.posstops.length);
		
		return AxisHGridLines;
		
		
	}
	
	public float[] drawVerticalGrid(AxisStops vstops,RectF coordbounds,Boolean GridShown)
	{
		
		AxisVGridLines = new float[(vstops.posstops.length-1)*4];
		
		
		System.out.println("DrawVLines");
		
			if(GridShown)
				
			{
				for(int i=1;i<vstops.posstops.length;i++)
				{
					
						if(i%2==0)
						{
							AxisVGridLines[(i-1)*4+0]= coordbounds.left;
							AxisVGridLines[(i-1)*4+1]= (float)Math.floor(vstops.posstops[i]);
							AxisVGridLines[(i-1)*4+2]= coordbounds.right;
							AxisVGridLines[(i-1)*4+3]=(float)Math.floor(vstops.posstops[i]);
						}
						
					
	
				}

				
				System.out.println("DrawVLines"+ AxisVGridLines.length+"VP"+vstops.posstops.length);
			
			}
		
			else
			
			{
				for(int i=1;i<vstops.posstops.length;i++)
				{
					
					if(i%2==0)
					{
					
						AxisVGridLines[(i-1)*4+0]= coordbounds.left;
						System.out.println(AxisVGridLines[(i-1)*4+0]+"GRIDV");
	
						AxisVGridLines[(i-1)*4+1]= (float)Math.floor(vstops.posstops[i]);
	
						System.out.println(AxisVGridLines[(i-1)*4+1]);
						
			
						AxisVGridLines[(i-1)*4+2]= coordbounds.left+8;
				
						System.out.println(AxisVGridLines[(i-1)*4+2]);
		
						AxisVGridLines[(i-1)*4+3]=(float)Math.floor(vstops.posstops[i]);
		
						System.out.println(AxisVGridLines[(i-1)*4+3]);
						
					}
					
		
				}
			}
			
			return AxisVGridLines;
			
			
	}
	
	public float[] drawVerticalGridCategory(AxisStops vstops,RectF coordbounds,Boolean GridShown)
	{
		
		AxisVGridLines = new float[(vstops.posstops.length-1)*4];
		
		
		System.out.println("DrawVLines");
		
			if(GridShown)
				
			{
				for(int i=1;i<vstops.posstops.length;i++)
				{
					
						
							AxisVGridLines[(i-1)*4+0]= coordbounds.left;
							AxisVGridLines[(i-1)*4+1]= (float)Math.floor(vstops.posstops[i]);
							AxisVGridLines[(i-1)*4+2]= coordbounds.right;
							AxisVGridLines[(i-1)*4+3]=(float)Math.floor(vstops.posstops[i]);
						
						
					
	
				}

				
				System.out.println("DrawVLines"+ AxisVGridLines.length+"VP"+vstops.posstops.length);
			
			}
		
			else
			
			{
				for(int i=1;i<vstops.posstops.length;i++)
				{
					
					
					
						AxisVGridLines[(i-1)*4+0]= coordbounds.left;
						System.out.println(AxisVGridLines[(i-1)*4+0]+"GRIDV");
	
						AxisVGridLines[(i-1)*4+1]= (float)Math.floor(vstops.posstops[i]);
	
						System.out.println(AxisVGridLines[(i-1)*4+1]);
						
			
						AxisVGridLines[(i-1)*4+2]= coordbounds.left+8;
				
						System.out.println(AxisVGridLines[(i-1)*4+2]);
		
						AxisVGridLines[(i-1)*4+3]=(float)Math.floor(vstops.posstops[i]);
		
						System.out.println(AxisVGridLines[(i-1)*4+3]);
						
					
					
		
				}
			}
			
			return AxisVGridLines;
			
			
	}
	
	public float[] drawVerticalGridStack(AxisStops vstops,RectF coordbounds,Boolean GridShown)
	{
		
		AxisVGridLines = new float[(vstops.posstops.length)*4];
		
		
		
		
			if(GridShown)
				
			{
				
				
				
				for(int i=1;i<=vstops.posstops.length;i++)
				{
					
					
					AxisVGridLines[(i-1)*4+0]= coordbounds.left;
					AxisVGridLines[(i-1)*4+1]= (float)Math.floor(vstops.posstops[i-1]);
					AxisVGridLines[(i-1)*4+2]= coordbounds.right;
					AxisVGridLines[(i-1)*4+3]=(float)Math.floor(vstops.posstops[i-1]);
					
					
	
				}

				System.out.println("DrawVLines"+ AxisVGridLines.length+"VP"+vstops.posstops.length);
				
			
			}
		
			else
			
			{
				for(int i=1;i<vstops.posstops.length;i++)
				{
					AxisVGridLines[(i-1)*4+0]= coordbounds.left;
					System.out.println(AxisVGridLines[(i-1)*4+0]+"GRIDV");

					AxisVGridLines[(i-1)*4+1]= (float)Math.floor(vstops.posstops[i]);

					System.out.println(AxisVGridLines[(i-1)*4+1]);
					
		
					AxisVGridLines[(i-1)*4+2]= coordbounds.left+8;
			
					System.out.println(AxisVGridLines[(i-1)*4+2]);
	
					AxisVGridLines[(i-1)*4+3]=(float)Math.floor(vstops.posstops[i]);
	
					System.out.println(AxisVGridLines[(i-1)*4+3]);
		
				}
				
				System.out.println("DrawVLines"+ AxisVGridLines.length+"VP"+vstops.posstops.length);
			}
			
			return AxisVGridLines;
			
			
	}
}
