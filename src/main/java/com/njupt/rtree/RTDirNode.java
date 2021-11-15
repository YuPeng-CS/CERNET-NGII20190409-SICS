package com.njupt.rtree;  
  
import java.util.ArrayList;  
import java.util.List;  
  
import com.njupt.constants.Constants;  
  
/** 
 * @ClassName RTDirNode  
 * @Description
 */  
public class RTDirNode extends RTNode  
{  
    /**
     */  
    protected List<RTNode> children;  
      
    public RTDirNode(RTree rtree, RTNode parent, int level)   
    {  
        super(rtree, parent, level);  
        children = new ArrayList<RTNode>();  
    }  
      
    /** 
     * @param index 
     * @return
     */  
    public RTNode getChild(int index)  
    {  
        return children.get(index);  
    }  
  
    @Override  
    public RTDataNode chooseLeaf(Rectangle rectangle)   
    {  
        int index;  
        index = findLeastEnlargement(rectangle);    
          
        insertIndex = index;//��¼����·��������  
        return getChild(index).chooseLeaf(rectangle);  
    }  
  
    /** 
     * @param rectangle 
     * @return
     */  
    private int findLeastEnlargement(Rectangle rectangle)   
    {  
        double area = Double.POSITIVE_INFINITY;  
        int sel = -1;  
          
        for(int i = 0; i < usedSpace; i ++)  
        {  
            double enlargement = getDatas()[i].getUnionRectangle(rectangle).getArea() - getDatas()[i].getArea();  
            if(enlargement < area)  
            {  
                area = enlargement;  
                sel = i;  
            }else if(enlargement == area)  
            {  
                sel = (getDatas()[sel].getArea() < getDatas()[i].getArea()) ? sel : i;  
            }  
        }  
          
        return sel;  
    }  
  
    /** 

     */  
    public void adjustTree(RTNode node1, RTNode node2)   
    {  

        getDatas()[insertIndex] = node1.getNodeRectangle();//
        children.set(insertIndex, node1);//
          
        if(node2 != null)  
        {  
            insert(node2);//
              
        }else if(! isRoot())//
        {  
            RTDirNode parent = (RTDirNode) getParent();  
            parent.adjustTree(this, null);//
        }  
    }  
  
    /** 
     * @param node 
     * @return
     */  
    protected boolean insert(RTNode node)   
    {  
        if(usedSpace < rtree.getNodeCapacity())  
        {  
            getDatas()[usedSpace ++] = node.getNodeRectangle();  
            children.add(node);//
            node.parent = this;//
            RTDirNode parent = (RTDirNode) getParent();  
            if(parent != null)  
            {  
                parent.adjustTree(this, null);  
            }  
            return false;  
        }else{//
            RTDirNode[] a = splitIndex(node);  
            RTDirNode n = a[0];  
            RTDirNode nn = a[1];  
              
            if(isRoot())  
            {  
                //
                RTDirNode newRoot = new RTDirNode(rtree, Constants.RTNode_NULL, getLevel() + 1);  
                  
                //
//              for(int i = 0; i < n.usedSpace; i ++)  
//              {  
//                  n.children.add(this.children.get(index));  
//              }  
//                
//              for(int i = 0; i < nn.usedSpace; i ++)  
//              {  
//                  nn.children.add(this.children.get(index));  
//              }  
                  
                //
                newRoot.addData(n.getNodeRectangle());  
                newRoot.addData(nn.getNodeRectangle());  
                  
                newRoot.children.add(n);  
                newRoot.children.add(nn);  
                  
                //
                n.parent = newRoot;  
                nn.parent = newRoot;  
                  
                //
                rtree.setRoot(newRoot);//
            }else {  
                RTDirNode p = (RTDirNode) getParent();  
                p.adjustTree(n, nn);  
            }  
        }  
        return true;  
    }  
  
    /**
     *  
     * @param node 
     * @return 
     */  
    private RTDirNode[] splitIndex(RTNode node)   
    {  
        int[][] group = null;  
        group = quadraticSplit(node.getNodeRectangle());  
        children.add(node);//
        node.parent = this;//

          
        RTDirNode index1 = new RTDirNode(rtree, parent, getLevel());  
        RTDirNode index2 = new RTDirNode(rtree, parent, getLevel());  
          
        int[] group1 = group[0];  
        int[] group2 = group[1];  
          
        for(int i = 0; i < group1.length; i ++)  
        {  
            //Ϊindex
            index1.addData(getDatas()[group1[i]]);  
            index1.children.add(this.children.get(group1[i]));//
            //
            this.children.get(group1[i]).parent = index1;//
        }  
        for(int i = 0; i < group2.length; i ++)  
        {  
            index2.addData(getDatas()[group2[i]]);  
            index2.children.add(this.children.get(group2[i]));//
            this.children.get(group2[i]).parent = index2;//
        }  
          
        return new RTDirNode[]{index1,index2};  
    }  
    
    @Override
    protected List<Rectangle> search(Rectangle rectangle) {
    	// TODO Auto-generated method stub
    	List<Rectangle> list=new ArrayList<Rectangle>();
    	for(int i = 0; i < usedSpace; i ++)  
        {  
            if(rectangle.isIntersection(getDatas()[i]))  
            {  
               // deleteIndex = i;//
            	RTNode node=children.get(i);
            	List<Rectangle> a=node.search(rectangle);
            	for(int j = 0; j < a.size(); j ++)  
                {  
                     list.add(a.get(j));  
                }  
            }  
        }  
    	return list;
    }
  
}