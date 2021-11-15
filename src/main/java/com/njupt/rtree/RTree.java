package com.njupt.rtree;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import com.AVL_Tree.Position;
import com.encrypty.Tools;
import com.njupt.constants.Constants;

/**
 * @ClassName RTree
 * @Description
 */
public class RTree {

	public RTNode root;

	public Enc_RTNode Enc_root;
	

	private int nodeCapacity = -1;


	private float fillFactor = -1;

	public RTree(int capacity, float fillFactor) {
		this.fillFactor = fillFactor;
		nodeCapacity = capacity;
		root = new RTDataNode(this, Constants.RTNode_NULL);
	}
	
	public RTree(){
		Enc_root=new Enc_RTDataNode(this, Constants.Enc_RTNode_NULL);
	}

	public void setRoot(RTNode root) {
		this.root = root;
	}

	public float getFillFactor() {
		return fillFactor;
	}


	public int getNodeCapacity() {
		return nodeCapacity;
	}
	

	public List<Rectangle> search(RTNode root,double x1,double y1,double x2,double y2) {
		if (root == null)
			throw new IllegalArgumentException("Node cannot be null.");
		Point p1 = new Point(new double[] { min(x1,x2), min(y1,y2) });
		Point p2 = new Point(new double[] { max(x1,x2), max(y1,y2) });
		Rectangle rectangle = new Rectangle(p1, p2);
		List<Rectangle> list = root.search(rectangle);
		
		return list;
	}


	public boolean insert(Rectangle rectangle) {
		if (rectangle == null)
			throw new IllegalArgumentException("Rectangle cannot be null.");

		RTDataNode leaf = root.chooseLeaf(rectangle);

		return leaf.insert(rectangle);
	}


	public static List<RTNode> traversePostOrder(RTNode root) {
		if (root == null)
			throw new IllegalArgumentException("Node cannot be null.");

		List<RTNode> list = new ArrayList<RTNode>();
		list.add(root);

		if (!root.isLeaf()) {
			for (int i = 0; i < root.usedSpace; i++) {
				List<RTNode> a = traversePostOrder(((RTDirNode) root).getChild(i));
				for (int j = 0; j < a.size(); j++) {
					list.add(a.get(j));
				}
			}
		}
		return list;
	}
	

	public List<Enc_RTNode> enc_TraversePostOrder(Enc_RTNode root){
		if (root == null)
			throw new IllegalArgumentException("Node cannot be null.");

		List<Enc_RTNode> list = new ArrayList<Enc_RTNode>();
		list.add(root);

		if (!root.isLeaf()) {
			for (int i = 0; i < root.usedSpace; i++) {
				List<Enc_RTNode> a = enc_TraversePostOrder(((Enc_RTDirNode) root).getChild(i));
				for (int j = 0; j < a.size(); j++) {
					list.add(a.get(j));
				}
			}
		}
		return list;
	}


	private Enc_Rectangle[] enc_Rectangles(Rectangle[] datas,boolean isLeaf){
		Node_Encrypty encrypty=new Node_Encrypty();
		Enc_Rectangle[] enc_datas=new Enc_Rectangle[datas.length];
		for(int i=0;i<datas.length;i++){
			enc_datas[i]=encrypty.rectangle_Enc_Index(datas[i],isLeaf);
		}
		return enc_datas;
	}
	

	public Enc_RTNode rtree_Encrypty(RTNode root,Enc_RTNode parent){
		Enc_RTNode enc_node;
		if(!root.isLeaf()){
			enc_node=new Enc_RTDirNode(this, parent, root.getLevel());
			Rectangle[] datas=root.getDatas();
			Enc_Rectangle[] enc_datas=enc_Rectangles(root.getDatas(),false);
			enc_node.setEncData(enc_datas);//�趨��������
			enc_node.setDatas(datas);//�趨��������
			for(int i=0;i<root.usedSpace;i++){//��Ӻ��ӽڵ�
				((Enc_RTDirNode)enc_node).addChild(
						rtree_Encrypty(((RTDirNode) root).getChild(i),enc_node));
			}
		}else{
			enc_node=new Enc_RTDataNode(this, parent);
			Rectangle[] datas=root.getDatas();
			Enc_Rectangle[] enc_datas=enc_Rectangles(root.getDatas(),true);
			enc_node.setEncData(enc_datas);//�趨��������
			enc_node.setDatas(datas);//�趨��������
		}
		
		return enc_node;
	}


	public Enc_RTNode produce_EncRtree(RTNode root){
		Enc_RTNode enc_root=rtree_Encrypty(root,null);
		return enc_root;
	}
	

	public static RTree produce_Rtree(String file_path, String data_file_name) {
		RTree tree = new RTree(3, 0.4f);
		// ���ļ��л�ȡ����
		List<Position> vals = Tools.getDataInFile(file_path + data_file_name);
		for(int i=0;i<vals.size();i++){
			Point p=new Point(new double[]{vals.get(i).getX(),vals.get(i).getY()});
			final Rectangle rectangle = new Rectangle(p, p);
			tree.insert(rectangle);
		}
		return tree;
	}
	
	private double max(double a,double b){
		return a>b?a:b;
	}
	
	private double min(double a,double b){
		return a<b?a:b;
	}
	

	public List<Enc_Rectangle> advance_enc_search(Enc_RTNode enc_root,double x1,double y1,double x2,double y2){
		if (enc_root == null)
			throw new IllegalArgumentException("Node cannot be null.");
		Node_Encrypty encrypty=new Node_Encrypty();

		Point p1 = new Point(new double[] { min(x1,x2), min(y1,y2) });

		Point p2 = new Point(new double[] { max(x1,x2), max(y1,y2) });

		Rectangle rectangle = new Rectangle(p1, p2);
		long b = System.nanoTime();

		Enc_Rectangle enc_Rectangle=encrypty.rectangle_Enc_Query(rectangle,false);

		Constants.ave_advance_enctime=(System.nanoTime() - b) / 1000.0f;
		b = System.currentTimeMillis();

		List<Enc_Rectangle> list = enc_root.search(enc_Rectangle);

		Constants.ave_advance_sertime=(System.currentTimeMillis() - b) / 1f;

		return list;
	}

	public List<Enc_Rectangle> basic_enc_search(Enc_RTNode enc_root,double x1,double y1,double x2,double y2){
		if (enc_root == null)
			throw new IllegalArgumentException("Node cannot be null.");
		Node_Encrypty encrypty=new Node_Encrypty();

		Point p1 = new Point(new double[] { min(x1,x2), min(y1,y2) });

		Point p2 = new Point(new double[] { max(x1,x2), max(y1,y2) });

		Rectangle rectangle = new Rectangle(p1, p2);		
		long b = System.currentTimeMillis();

		Enc_Rectangle enc_Rectangle=encrypty.rectangle_Enc_Query(rectangle,true);

		Constants.ave_basic_enctime=(System.currentTimeMillis() - b) / 1f;
		
		b = System.currentTimeMillis();

		List<Enc_Rectangle> list = enc_root.search(enc_Rectangle);

		Constants.ave_basic_sertime=(System.currentTimeMillis() - b) / 1f;

		return list;
	}

	public static void main(String[] args) throws Exception {
		RTree tree = new RTree(3, 0.4f);

		double[] f = {

				1, 0, 9, 8, 7, 6, 2, 3, 1, 3, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 0, 0, 6, 6 };

		// ������
		for (int i = 0; i < f.length;) {
			Point p1 = new Point(new double[] { f[i++], f[i++] });
			final Rectangle rectangle = new Rectangle(p1, p1);
			tree.insert(rectangle);

			Rectangle[] rectangles = tree.root.getDatas();
			System.out.println(tree.root.getLevel());
			for (int j = 0; j < rectangles.length; j++)
				System.out.println(rectangles[j]);
		}
		System.out.println("---------------------------------");
		System.out.println("Insert finished.");

		List<RTNode> rtrees = traversePostOrder(tree.root);
		for (int i = 0; i < rtrees.size(); i++) {
			RTNode node = rtrees.get(i);
			Rectangle[] rectangles = node.getDatas();
			System.out.println("level:" + node.getLevel());

			for (int j = 0; j < rectangles.length; j++)
				System.out.println(rectangles[j]);
		}

		System.out.println("---------------------------------");
		

	}

}