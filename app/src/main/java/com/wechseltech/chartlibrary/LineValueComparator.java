package com.wechseltech.chartlibrary;

import java.util.Comparator;

public class LineValueComparator  implements Comparator<LineItem>{

	@Override
	public int compare(LineItem lhs, LineItem rhs) {
		
//		double alzval = Math.abs(lhs.xval);
//		double arzval = Math.abs(rhs.xval);
		
		double alzval = lhs.xval;
		double arzval = rhs.xval;
		return (alzval<arzval? -1 : (alzval== arzval? 0 : 1));
	}

}
