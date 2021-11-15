package com.njupt.rtree;

import java.util.List;

import com.encrypty.splitedMatrix;
import com.njupt.constants.Constants;

public abstract class Enc_RTNode {

	/** 
     *
     */  
    protected RTree rtree;        
      
    /** 
     *
     */  
    private int level; 
    
    /** 
     *
     */  
    protected Enc_RTNode parent;
	
	/**
	 *
	 * */
	private Enc_Rectangle enc_datas[];
	/** 
     *
     */  
    protected int usedSpace; 
	
	public int getUsedSpace() {
		return usedSpace;
	}

	/** 
     *
     */  
    private Rectangle[] datas;  
	
	public Rectangle[] getDatas() {
		return datas;
	}

	protected void addEncData(Enc_Rectangle data){
		getEnc_Datas()[usedSpace++]=data;
	}

	public void setDatas(Rectangle[] datas) {
		this.datas = datas;
	}


	public Enc_RTNode(RTree rtree, Enc_RTNode parent, int level){
		this.rtree=rtree;
		this.parent=parent;
		this.level=level;
		setEnc_Datas(new Enc_Rectangle[rtree.getNodeCapacity()]);
		usedSpace = 0;  
	}
	
	
	public Enc_RTNode getParent() {
		return parent;
	}

	private void setEnc_Datas(Enc_Rectangle[] datas) {
		this.enc_datas = datas;
	}

	/** 
     * @return
     */  
    public boolean isRoot()  
    {  
        return (parent == Constants.Enc_RTNode_NULL);  
    }  
    
    /** 
     * @return
     */  
    public boolean isIndex()  
    {  
        return (getLevel() != 0);  
    }  
      
	/** 
     * @return
     */  
    public boolean isLeaf()  
    {  
        return (getLevel() == 0);  
    }  
    
    public int getLevel() {
  		// TODO Auto-generated method stub
  		return level;
  	}
    
    public Enc_Rectangle[] getEnc_Datas() {
		return enc_datas;
	}
    
    /**
     * @return
     */
    protected abstract List<Enc_Rectangle> search(Enc_Rectangle rectangle);

    protected abstract void setEncData(Enc_Rectangle[] datas);
}
