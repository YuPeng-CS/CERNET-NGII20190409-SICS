package com.encrypty;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Jama.Matrix;

public class Gen {
	
	
	public static double[] GenArr(double x,double y,double setp){
		double[] arr=new double[4];
		arr[0]=x-(setp/2.0);
		arr[1]=y-(setp/2.0);
		arr[2]=x+(setp/2.0);
		arr[3]=y+(setp/2.0);
		
		
		return arr;
	}


	public static double[][] GenXY(double min_x,double min_y,double max_x,double max_y){
		int len=50;
		double[][] XY=new double[len][2];
		
		for(int i=0;i<len;i++){
			Random random=new Random();
			double x=random.nextDouble()*(max_x-min_x)+min_x;
			double y=random.nextDouble()*(max_y-min_y)+min_y;
			XY[i][0]=Tools.Rounding(x, 4);
			XY[i][1]=Tools.Rounding(y, 4);
		}
		
		return XY;
	}
	

	public static SecretKey GenKey(int d){
		SecretKey SK=new SecretKey();

		int S=2*d;//
		List<Integer> L=new ArrayList<>();//
		Random random=new Random();
		int one_num=S/4;//
		int zero_num=S/4;
		while(true){//
			int x=random.nextInt(2);//
			L.add(x);
			if(x==0){
				zero_num--;
			}else{
				one_num--;
			}
			if(zero_num==0){
				break;
			}
			if(one_num==0){
				break;
			}

		}
		if(zero_num!=0){
			while(zero_num!=0){
				L.add(0);
				zero_num--;
			}
		}
		if(one_num!=0){
			while(one_num!=0){
				L.add(1);
				one_num--;
			}
		}


		List<Integer> R=new ArrayList<>();
		//Random random=new Random();
		int one=S/2;//
		int zero=S/2;
		while(true){//
			int x=random.nextInt(2);//
			R.add(x);
			if(x==0){
				zero--;
			}else{
				one--;
			}
			if(zero==0){
				break;
			}
			if(one==0){
				break;
			}

		}

		if(zero!=0){
			while(zero!=0){
				R.add(0);
				zero--;
			}
		}
		if(one!=0){
			while(one!=0){
				R.add(1);
				one--;
			}
		}		


		Matrix M1;		
		do{	
			double array[][]=new double[S][S];
			for(int i=0;i<S;i++){
				for(int j=0;j<S;j++){
					array[i][j]=random.nextInt(S*10)+1;//
				}
			}
			M1=new Matrix(array);
		}while(M1.det()==0);
		Matrix M2;		
		do{	
			double array[][]=new double[S][S];
			for(int i=0;i<S;i++){
				for(int j=0;j<S;j++){
					array[i][j]=random.nextInt(S*10)+1;//
				}
			}
			M2=new Matrix(array);
		}while(M2.det()==0);

		SK.setS(S);
		SK.setL(L);
		SK.setR(R);
		SK.setM1(M1);
		SK.setM2(M2);
		
		SK.setM1_Inverse(M1.inverse());
		SK.setM2_Inverse(M2.inverse());
		//System.out.println("R:"+R);
		return SK;
	}
	

	public static Matrix GenInverMatrix(Matrix matrix){
		Matrix inverMatrix=matrix.inverse();
		return inverMatrix;
	}


	public static Matrix genTransMatrix(Matrix matrix){
		Matrix transMatrix=matrix.transpose();
		return transMatrix;
	}


	public static splitedMatrix splitVector(double[] array,SecretKey sk,int spliteIndex){
		int S=sk.getS();
		double[] array1=new double[S];
		double[] array2=new double[S];
		splitedMatrix splitedVecI = new splitedMatrix();
		List<Integer> R = sk.getR();
		int R_size=R.size();
		for(int i=0;i<R_size;i++){
			if(R.get(i)==spliteIndex){
				array1[i]=array[i];					
				array2[i]=array[i];	
			}
			else {
				Random random=new Random();
				double c=(random.nextDouble()*(array[i]+array[i]+1))-array[i]-1;//
				BigDecimal b1=new BigDecimal(c);  
				c=b1.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

				array1[i]=array[i]-c;
				array2[i]=array[i]-array1[i];

			}
		}		

		Matrix I_a=new Matrix(array1, 1);//
		Matrix I_b=new Matrix(array2, 1);//

		splitedVecI.setVec_a(I_a);
		splitedVecI.setVec_b(I_b);

		return splitedVecI;
	}
	

	public static double splitedVector_times(splitedMatrix vector1,splitedMatrix vector2){

		Matrix M1=vector1.getVec_a().times(vector2.getVec_a());//
		Matrix M2=vector1.getVec_b().times(vector2.getVec_b());
		double r1=0;
		double r2=0;
		for (int i = 0; i < M1.getRowDimension(); i++) {
			for (int j = 0; j < M1.getColumnDimension(); j++) {
				r1+=M1.get(i, j);
			}			
		}
		for (int i = 0; i < M2.getRowDimension(); i++) {
			for (int j = 0; j < M2.getColumnDimension(); j++) {
				r2+=M2.get(i, j);
			}			
		}
		return Tools.Rounding(r1+r2, 6);
		//return r1+r2;
	}

}
