package com.wechseltech.chartlibrary;

import java.util.Comparator;

public class LineOrderComparator implements Comparator<LineItem>{

	@Override
	public int compare(LineItem lhs, LineItem rhs) {
		
//		double alzval = Math.abs(lhs.order);
//		double arzval = Math.abs(rhs.order);
		
		double alzval = lhs.order;
		double arzval = rhs.order;
		return (alzval<arzval? -1 : (alzval== arzval? 0 : 1));
	}
}
