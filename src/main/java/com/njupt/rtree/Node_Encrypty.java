package com.njupt.rtree;

import com.encrypty.Encrypty;
import com.encrypty.IndexEncrypty;
import com.encrypty.QueryEncrypty;
import com.encrypty.splitedMatrix;
import com.njupt.constants.Constants;

public class Node_Encrypty {

	/**
	 */
	public Enc_Rectangle rectangle_Enc_Query(Rectangle rectangle, boolean isBasic) {
		if (rectangle == null) {
			return null;
		}
		Enc_Rectangle enc_Rectangle;

		Enc_Point enc_low = point_Enc(rectangle.getLow(), false,isBasic);

		Enc_Point enc_high = point_Enc(rectangle.getHigh(), false,isBasic);

		enc_Rectangle = new Enc_Rectangle(enc_low, enc_high);
		return enc_Rectangle;
	}

	/**

	 * 
	 * @param rectangle
	 * @return
	 */
	public Enc_Rectangle rectangle_Enc_Index(Rectangle rectangle, boolean isLeaf) {
		if (rectangle == null) {
			return null;
		}
		Enc_Rectangle enc_Rectangle;
		if (isLeaf) {//
			Enc_Point enc_low = point_Enc(rectangle.getLow(), true,false);
			enc_Rectangle = new Enc_Rectangle(enc_low);
		} else {//
			Enc_Point enc_low = point_Enc(rectangle.getLow(), true,false);
			Enc_Point enc_high = point_Enc(rectangle.getHigh(), true,false);
			enc_Rectangle = new Enc_Rectangle(enc_low, enc_high);
		}
		return enc_Rectangle;
	}

	/**

	 */
	private Enc_Point point_Enc(Point point, boolean isIndex, boolean isBasic) {
		Enc_Point enc_Point;
		Encrypty encrypty = new Encrypty();
		splitedMatrix[] enc_data = new splitedMatrix[point.getDimension()];

		for (int i = 0; i < point.getDimension(); i++) {
			double d = point.getDoubleCoordinate(i);
			if (isIndex == true) {
				enc_data[i] = encrypty.indexDataEncrypty(d);
			} else {
				if (isBasic) {
					enc_data[i] = encrypty.basic_QueryDataEncrypty(d);
				} else {
					enc_data[i] = encrypty.advance_QueryDataEncrypty(d);
				}
			}
		}
		enc_Point = new Enc_Point(enc_data);
		return enc_Point;
	}

}
