package com.encrypty;

import com.njupt.constants.Constants;
/**
 *
 */
public class Encrypty {

	/**
	 */
	public splitedMatrix indexDataEncrypty(double d){
		if(Constants.SK==null){
			throw new IllegalArgumentException("SK cannot be null.");
		}
		//
		IndexEncrypty indexEnc = new IndexEncrypty();
		double[] I = indexEnc.BuildIndexVector(d, Constants.SK);

		//
		splitedMatrix splited_I = indexEnc.splitIndexVector(I, Constants.SK);
		//
		splitedMatrix encI = indexEnc.EncIndexVector(splited_I, Constants.SK);
		
		return encI;
	}
	
	
	/**

	 */
	public splitedMatrix advance_QueryDataEncrypty(double d){
		if(Constants.SK==null){
			throw new IllegalArgumentException("SK cannot be null.");
		}
		///
		QueryEncrypty queryEnc = new QueryEncrypty();
		double[] Q = queryEnc.BuildQueryVector(d, Constants.SK);
		//
		double[][] M_Q=queryEnc.BuildQueryMatrix(Q, Constants.SK);
		//
		splitedMatrix splited_Q = queryEnc.splitedQueryMatrix(M_Q, Constants.SK);
		//
		splitedMatrix encQ = queryEnc.EncQueryMatrix(splited_Q, Constants.SK);
		
		//System.out.println(encQ);
		return encQ;
	}
	
	/**

	 */
	public splitedMatrix basic_QueryDataEncrypty(double d){
		if(Constants.SK==null){
			throw new IllegalArgumentException("SK cannot be null.");
		}
		///
		QueryEncrypty queryEnc = new QueryEncrypty();
		double[] Q = queryEnc.BuildQueryVector(d, Constants.SK);
		//
		splitedMatrix splited_Q = queryEnc.splitQueryVector(Q, Constants.SK);
		splitedMatrix encQ = queryEnc.EncQueryVector(splited_Q, Constants.SK);
		
		return encQ;
	}
	
}
