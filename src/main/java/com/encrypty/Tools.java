package com.encrypty;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.AVL_Tree.Position;
import com.AVL_Tree.Position_Encode;


public class Tools {

	/**
	 * @param num
	 * @param rounding
	 * @return
	 */
	public static double Rounding(double num,int rounding){
		double result;
		BigDecimal b=new BigDecimal(num); 
		result=b.setScale(rounding, BigDecimal.ROUND_HALF_UP).doubleValue();
		return result;
	}
	
	/*

	 */
	public static int binary_to_decimal(List<Integer> encode) {
		int result = 0;
		int size = encode.size();
		for (int i = size; i > 0; i--) {
			if (encode.get(i - 1) == 1) {
				result += Math.pow(2, size - i);
			}
		}
		return result;
	}

	/*
	 */
	public static List<Position> getDataInFile(String file_name) {
		List<Position> values = new ArrayList<Position>();

		File file = new File(file_name);
		BufferedReader reader = null;

		try {
			reader = new BufferedReader(new FileReader(file));
			String line = null;

			while ((line = reader.readLine()) != null) {
				String[] temp = line.split("\t");//
				if (temp.length == 2) {
					Position pos = new Position();
					pos.setX(Double.parseDouble(temp[0]));
					pos.setY(Double.parseDouble(temp[1]));

					values.add(pos);
				}
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return values;
	}

	/**
	 * @param file_name
	 * @param enc_Encodes
	 */
	public static void saveEncDataToFile(String file_name, List<Enc_Encode> enc_Encodes) {

		File file = new File(file_name);

		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		FileOutputStream out = null;
		BufferedOutputStream buff = null;

		try {
			out = new FileOutputStream(file);
			buff = new BufferedOutputStream(out);
			int count=enc_Encodes.size();
			for(int i=0;i<count;i++){
				Enc_Encode enc_Encode=enc_Encodes.get(i);
				buff.write((enc_Encode.enc_X_toString()+":"+enc_Encode.encode_X_toString()+"\t").getBytes());
				buff.write((enc_Encode.enc_Y_toString()+":"+enc_Encode.encode_Y_toStrig()+"\r\n").getBytes());
			}
			buff.flush();
			buff.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				buff.close();
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	/*

	 */
	public static void saveDataToFile(String file_name, List<Position_Encode> position_Encodes) {

		File file = new File(file_name);

		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		FileOutputStream out = null;
		BufferedOutputStream buff = null;

		try {
			out = new FileOutputStream(file);
			buff = new BufferedOutputStream(out);

			int count = position_Encodes.size();
			for (int i = 0; i < count; i++) {
				Position_Encode pos = position_Encodes.get(i);
				buff.write((pos.getPos().getX() + ":" + pos.encode_X_toString() + "\t").getBytes());
				buff.write((pos.getPos().getY() + ":" + pos.encode_Y_toStrig() + "\r\n").getBytes());
				// buff.write((pos.getPos().getScore()+"\r\n").getBytes());
			}

			buff.flush();
			buff.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				buff.close();
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
	
	/*
	 * 

	 */
	public static void saveStrToFile(String file_name, StringBuffer str) {

		File file = new File(file_name);

		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		FileOutputStream out = null;
		BufferedOutputStream buff = null;

		try {
			out = new FileOutputStream(file);
			buff = new BufferedOutputStream(out);

		
			buff.write(str.toString().getBytes());
//			int count = position_Encodes.size();
//			for (int i = 0; i < count; i++) {
//				Position_Encode pos = position_Encodes.get(i);
//				buff.write((pos.getPos().getX() + ":" + pos.encode_X_toString() + "\t").getBytes());
//				buff.write((pos.getPos().getY() + ":" + pos.encode_Y_toStrig() + "\r\n").getBytes());
//				// buff.write((pos.getPos().getScore()+"\r\n").getBytes());
//			}

			buff.flush();
			buff.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				buff.close();
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

}
