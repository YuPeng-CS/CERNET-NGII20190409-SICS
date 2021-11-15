package com.njupt.rtree;

import java.util.ArrayList;
import java.util.List;

import com.njupt.constants.Constants;

/**
 * @ClassName RTDataNode
 * @Description
 */
public class RTDataNode extends RTNode {

	public RTDataNode(RTree rTree, RTNode parent) {
		super(rTree, parent, 0);
	}

	/**
	 * 
	 * @param rectangle
	 * @return
	 */
	public boolean insert(Rectangle rectangle) {
		if (usedSpace < rtree.getNodeCapacity()) {
			getDatas()[usedSpace++] = rectangle;
			RTDirNode parent = (RTDirNode) getParent();

			if (parent != null)
				parent.adjustTree(this, null);
			return true;

		} else {//
			RTDataNode[] splitNodes = splitLeaf(rectangle);
			RTDataNode l = splitNodes[0];
			RTDataNode ll = splitNodes[1];

			if (isRoot()) {
				RTDirNode rDirNode = new RTDirNode(rtree, Constants.RTNode_NULL, getLevel() + 1);
				rtree.setRoot(rDirNode);
				rDirNode.addData(l.getNodeRectangle());
				rDirNode.addData(ll.getNodeRectangle());

				ll.parent = rDirNode;
				l.parent = rDirNode;

				rDirNode.children.add(l);
				rDirNode.children.add(ll);

			} else {
				RTDirNode parentNode = (RTDirNode) getParent();
				parentNode.adjustTree(l, ll);
			}

		}
		return true;
	}

	/**
	 * 
	 * @param rectangle
	 * @return
	 */
	public RTDataNode[] splitLeaf(Rectangle rectangle) {
		int[][] group = null;
		group = quadraticSplit(rectangle);
		

		RTDataNode l = new RTDataNode(rtree, parent);
		RTDataNode ll = new RTDataNode(rtree, parent);

		int[] group1 = group[0];
		int[] group2 = group[1];

		for (int i = 0; i < group1.length; i++) {
			l.addData(getDatas()[group1[i]]);
		}

		for (int i = 0; i < group2.length; i++) {
			ll.addData(getDatas()[group2[i]]);
		}
		return new RTDataNode[] { l, ll };
	}

	@Override
	public RTDataNode chooseLeaf(Rectangle rectangle) {
		insertIndex = usedSpace;
		return this;
	}
	
	@Override
	protected List<Rectangle> search(Rectangle rectangle) {
		// TODO Auto-generated method stub
		List<Rectangle> list=new ArrayList<Rectangle>();
		for(int i=0;i<usedSpace;i++){
			if(rectangle.enclosure(getDatas()[i])){
				list.add(getDatas()[i]);
			}
		}
		return list;
	}

}