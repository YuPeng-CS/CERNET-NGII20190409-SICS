package com.njupt.constants;

import com.encrypty.SecretKey;
import com.njupt.rtree.Enc_RTNode;
import com.njupt.rtree.RTNode;

public class Constants {

	public static final int MAX_NUMBER_OF_ENTRIES_IN_NODE = 20;
    public static final int MIN_NUMBER_OF_ENTRIES_IN_NODE = 8;
      

    public static final int NIL = -1;  
    public static final RTNode RTNode_NULL = null;  
    public static final Enc_RTNode Enc_RTNode_NULL = null;  
    
    public static double ave_basic_enctime;
    public static double ave_basic_sertime;
    /**
     *
     */
    public static double ave_advance_enctime;
    /**
     * 
     */
    public static double ave_advance_sertime;
    
    /**
     *
     */
    public static int MATRIX_Q_COL;
    
    //
    public static String file_path = "F:\\_yanyi\\_shiyan\\";
  	//
    public static String data_file_name = "data1_want.txt";
  	//
    public static String str_file_name="str.txt";
    
    public static SecretKey SK;
}
