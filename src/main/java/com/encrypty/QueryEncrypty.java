package com.encrypty;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.njupt.constants.Constants;

import Jama.Matrix;


public class QueryEncrypty {

	/**

	 * @return
	 */
	public double[] BuildQueryVector(double q,SecretKey sk){
		int S=sk.getS();
		double[] array=new double[S];
		
		List<Integer> L=sk.getL();
		int count=L.size();
		Random random=new Random();
		int r=random.nextInt(S/4)+1;
		List<Integer> index=new ArrayList<Integer>();
		while(true){
			int i=random.nextInt(S/4)+1;
			index.add(i);
			if(index.size()==r){
				break;
			}
		}
		Collections.sort(index);
		
		Map<Integer,Double> C=new HashMap<Integer,Double>();
		for(int i=0;i<r;i++){
			double ci=random.nextDouble()*S;
			BigDecimal b=new BigDecimal(ci);  
			ci=b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			C.put(index.get(i), ci);
		}
		
		int one=0;
		int zero=0;
		for(int i=0;i<count;i++){
			if(L.get(i)==0){
				array[count+i]=q;
				zero++;
				if(index.contains(zero)){
					array[i]=q*C.get(zero);
//					BigDecimal b=new BigDecimal(array[i]); 
//					array[i]=b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				}else{
					array[i]=0;
				}
			}else{
				array[count+i]=-1;
				one++;
				if(index.contains(one)){
					array[i]=-1*C.get(one);
//					BigDecimal b=new BigDecimal(array[i]); 
//					array[i]=b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				}else{
					array[i]=0;
				}
			}
			
		}
		
		//Matrix Q=new Matrix(array,1);
		return array;
	}
	

	public double[][] BuildQueryMatrix(double[] vector_Q,SecretKey sk){
		int S=sk.getS();
		int col=Constants.MATRIX_Q_COL;
		double[][] matrix_Q=new double[S][col];
		Random random=new Random();
		
		double r=random.nextDouble()*5;
		r=Tools.Rounding(r, 2);

		for(int i=0;i<S;i++){
			vector_Q[i]=vector_Q[i]*r;			
		}

		double a=random.nextDouble()*5;///
		for(int i=0;i<S;i++){
			matrix_Q[i][0]=vector_Q[i];//
			double sum=0;
			for(int j=1;j<col-1;j++){
				double num=(random.nextDouble()*(a*matrix_Q[i][0]+1))-1;
				num=Tools.Rounding(num, 2);
					
				int sysbol=random.nextInt(2);//
				if(sysbol==0){
					num=-1*num;
				}
				matrix_Q[i][j]=num;
				sum+=num;
			}
			matrix_Q[i][col-1]=(matrix_Q[i][0]*a)-sum;//
		}
		
		return matrix_Q;
	}
	

	public splitedMatrix EncQueryVector(splitedMatrix splited_Q,SecretKey sk){
		splitedMatrix EncQ=new splitedMatrix();
		EncQ.setVec_a(sk.getM1_Inverse().times(splited_Q.getVec_a().transpose()));
		EncQ.setVec_b(sk.getM2_Inverse().times(splited_Q.getVec_b().transpose()));
//		EncQ.setVec_a(sk.getM1().inverse().times(splited_Q.getVec_a().transpose()));
//		EncQ.setVec_b(sk.getM2().inverse().times(splited_Q.getVec_b().transpose()));
		return EncQ;
	}
	
	public splitedMatrix EncQueryMatrix(splitedMatrix splited_Q,SecretKey sk){
		splitedMatrix EncQ=new splitedMatrix();
		EncQ.setVec_a(sk.getM1_Inverse().times(splited_Q.getVec_a()));
		EncQ.setVec_b(sk.getM2_Inverse().times(splited_Q.getVec_b()));
		return EncQ;
	}
	

	public splitedMatrix splitedQueryMatrix(double[][] array,SecretKey sk){
		int S=sk.getS();
		int col=Constants.MATRIX_Q_COL;
		double[][] array1=new double[S][col];
		double[][] array2=new double[S][col];
		splitedMatrix splitedMatQ = new splitedMatrix();
		List<Integer> R = sk.getR();
		int R_size=R.size();

		for(int i=0;i<S;i++){
			for(int j=0;j<col;j++){				
				if(R.get(i)==0){//R[i]
					array1[i][j]=array[i][j];					
					array2[i][j]=array[i][j];	
				}else{
					Random random=new Random();
					double c=(random.nextDouble()*(array[i][j]+array[i][j]+1))-array[i][j]-1;//-array[i]~array
					BigDecimal b1=new BigDecimal(c);  
					c=b1.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();//
					
					array1[i][j]=array[i][j]-c;
					array2[i][j]=array[i][j]-array1[i][j];

				}
			}			
		}
		Matrix I_a=new Matrix(array1);//
		Matrix I_b=new Matrix(array2);//

		splitedMatQ.setVec_a(I_a);
		splitedMatQ.setVec_b(I_b);
		
		return splitedMatQ;
	}
	
	/**
	 */
	public splitedMatrix splitQueryVector(double[] array,SecretKey sk){
		return Gen.splitVector(array, sk, 0);
	}
	
}
