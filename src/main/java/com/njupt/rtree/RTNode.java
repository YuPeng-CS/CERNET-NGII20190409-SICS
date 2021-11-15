package com.njupt.rtree;  
  
import java.util.List;  
  
import com.njupt.constants.Constants;  

  
/** 
 * @ClassName RTNode  
 * @Description  
 */  
public abstract class RTNode  
{  
    /** 

     */  
    protected RTree rtree;        
      
    /** 

     */  
    private int level;          
      
    /** 

     */  
    private Rectangle[] datas;  
      
//  /**  

//   */  
//  protected int capacity;  
      
    /** 

     */  
    protected RTNode parent;  
      
    /** 

     */  
    protected int usedSpace;  
      
    /** 

     */  
    protected int insertIndex;  
      
    public RTNode(RTree rtree, RTNode parent, int level)   
    {  
        this.rtree = rtree;  
        this.parent = parent;  
        this.setLevel(level);  
//      this.capacity = capacity;  
        setDatas(new Rectangle[rtree.getNodeCapacity() + 1]);
        usedSpace = 0;  
    }  
      
    /** 

     */  
    public RTNode getParent()  
    {  
        return parent;  
    }  
      
//  /**
//   */  
//  public int getNodeCapacity()  
//  {  
//      return capacity;  
//  }  
      
    /**
     * @param rectangle 
     */  
    protected void addData(Rectangle rectangle)   
    {  
        if(usedSpace == rtree.getNodeCapacity())  
        {  
            throw new IllegalArgumentException("Node is full.");  
        }  
        getDatas()[usedSpace ++] = rectangle;  
    }    
      
    /** 

     */  
    protected int[][] quadraticSplit(Rectangle rectangle)   
    {  
        if(rectangle == null)  
        {  
            throw new IllegalArgumentException("Rectangle cannot be null.");  
        }  
          
        getDatas()[usedSpace] = rectangle;
//      if(this instanceof RTDirNode)  
//      {  
//          (RTDirNode)(this).children.add()  
//      }  
        int total = usedSpace + 1;
          

        int[] mask = new int[total];  
        for(int i = 0; i < total; i ++)  
        {  
            mask[i] = 1;  
        }  
          

        int c = total/2 + 1;  

        int minNodeSize = Math.round(rtree.getNodeCapacity() * rtree.getFillFactor()); 

        if(minNodeSize < 2)  
            minNodeSize = 2;  
          

        int rem = total;  
          
        int[] group1 = new int[c];//
        int[] group2 = new int[c];//

        int i1 = 0, i2 = 0;  
          
        int[] seed = pickSeeds();  
        group1[i1 ++] = seed[0];  
        group2[i2 ++] = seed[1];  
        rem -=2;  
        mask[group1[0]] = -1;  
        mask[group2[0]] = -1;  
          
        while(rem > 0)  
        {  

            if(minNodeSize - i1 == rem)
            {  
                for(int i = 0; i < total; i ++)
                {  
                    if(mask[i] != -1)
                    {  
                        group1[i1 ++] = i;  
                        mask[i] = -1;  
                        rem --;  
                    }  
                }  

            }else if(minNodeSize - i2 == rem)  
            {  
                for(int i = 0; i < total; i ++)
                {  
                    if(mask[i] != -1)//
                    {  
                        group2[i2 ++] = i;  
                        mask[i] = -1;  
                        rem --;  
                    }  
                }  
            }else  
            {  

                Rectangle mbr1 = (Rectangle) getDatas()[group1[0]].clone();  
                for(int i = 1; i < i1; i ++)  
                {  
                    mbr1 = mbr1.getUnionRectangle(getDatas()[group1[i]]);  
                }  

                Rectangle mbr2 = (Rectangle) getDatas()[group2[0]].clone();  
                for(int i = 1; i < i2; i ++)  
                {  
                    mbr2 = mbr2.getUnionRectangle(getDatas()[group2[i]]);  
                }  
                  

                double dif = Double.NEGATIVE_INFINITY;  
                double areaDiff1 = 0, areaDiff2 = 0;  
                double diff1=0,diff2=0;
                int sel = -1;                 
                for(int i = 0; i < total; i ++)  
                {  
                    if(mask[i] != -1)
                    {  

                        Rectangle a = mbr1.getUnionRectangle(getDatas()[i]);  
                        areaDiff1 = a.getArea() - mbr1.getArea();  
                          
                        Rectangle b = mbr2.getUnionRectangle(getDatas()[i]);  
                        areaDiff2 = b.getArea() - mbr2.getArea();  
                          
                        if(Math.abs(areaDiff1 - areaDiff2) > dif)  
                        {  
                            dif = Math.abs(areaDiff1 - areaDiff2);  
                            sel = i;  
                            diff1=areaDiff1;
                            diff2=areaDiff2;
                        }  
                    }  
                }  
                  
                if(diff1 < diff2)//
                {  
                    group1[i1 ++] = sel;  
                }else if(diff1 > diff2)  
                {  
                    group2[i2 ++] = sel;  
                }else if(mbr1.getArea() < mbr2.getArea())//
                {  
                    group1[i1 ++] = sel;  
                }else if(mbr1.getArea() > mbr2.getArea())  
                {  
                    group2[i2 ++] = sel;  
                }else if(i1 < i2)//
                {  
                    group1[i1 ++] = sel;  
                }else if(i1 > i2)  
                {  
                    group2[i2 ++] = sel;  
                }else {  
                    group1[i1 ++] = sel;  
                }  
                mask[sel] = -1;  
                rem --;  
                  
            }  
        }//end while  
          
          
        int[][] ret = new int[2][];  
        ret[0] = new int[i1];  
        ret[1] = new int[i2];  
          
        for(int i = 0; i < i1; i ++)  
        {  
            ret[0][i] = group1[i];  
        }  
        for(int i = 0; i < i2; i ++)  
        {  
            ret[1][i] = group2[i];  
        }  
        return ret;  
    }  
      
      
    /** 

     */  
    protected int[] pickSeeds()   
    {  
        double inefficiency = Double.NEGATIVE_INFINITY;  
        int i1 = 0, i2 = 0;  
          
        //  
        for(int i = 0; i < usedSpace; i ++)  
        {  
            for(int j = i + 1; j <= usedSpace; j ++)//
            {  
                Rectangle rectangle = getDatas()[i].getUnionRectangle(getDatas()[j]);  
                double d = rectangle.getArea() - getDatas()[i].getArea() - getDatas()[j].getArea();  
                  
                if(d > inefficiency)  
                {  
                    inefficiency = d;  
                    i1 = i;  
                    i2 = j;  
                }  
            }  
        }  
        return new int[]{i1, i2};  
    }  
  
    /** 

     */  
    public Rectangle getNodeRectangle()  
    {  
        if(usedSpace > 0)  
        {  
            Rectangle[] rectangles = new Rectangle[usedSpace];  
            System.arraycopy(getDatas(), 0, rectangles, 0, usedSpace);  
            return Rectangle.getUnionRectangle(rectangles);  
        }else {  
            return new Rectangle(new Point(new double[]{0,0}), new Point(new double[]{0,0}));  
        }  
    }  
      
    /** 

     */  
    public boolean isRoot()  
    {  
        return (parent == Constants.RTNode_NULL);  
    }  
      
    /** 

     */  
    public boolean isIndex()  
    {  
        return (getLevel() != 0);  
    }  
      
    /** 
     * @return �Ƿ�Ҷ�ӽ�� 
     */  
    public boolean isLeaf()  
    {  
        return (getLevel() == 0);  
    }  
  

    protected abstract RTDataNode chooseLeaf(Rectangle rectangle);  
      

    

    protected abstract List<Rectangle> search(Rectangle rectangle);

	public Rectangle[] getDatas() {
		return datas;
	}

	public void setDatas(Rectangle[] datas) {
		this.datas = datas;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
      
}  