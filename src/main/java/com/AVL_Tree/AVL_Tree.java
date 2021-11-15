package com.AVL_Tree;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AVL_Tree {
	
	private TreeNode root;
	private Map<Double, List<Integer>> encodes=new HashMap<Double,List<Integer>>();
	
	
	
	public TreeNode getRoot() {
		return root;
	}

	private int getHeight(TreeNode node){
		if(node!=null){
			return node.getHeight();
		}
		return -1;
	}
	
	public int max(int a,int b){
		return a>b?a:b;
	}
	
	
	
	
	/**
	 *
	 **/
	public TreeNode singRotateLeft(TreeNode n1){
		TreeNode n2=n1.getLson();
		n1.setLson(n2.getRson());
		n2.setRson(n1);
		
		n1.setHeight(max(getHeight(n1.getLson()),getHeight( n1.getRson()))+1);
		n2.setHeight(max(getHeight(n2.getLson()), getHeight(n2.getRson()))+1);
		
		return n2;
	}


	/**

	 **/
	public TreeNode singRotateRight(TreeNode n1){
		TreeNode n2=n1.getRson();
		n1.setRson(n2.getLson());
		n2.setLson(n1);
		
		n1.setHeight(max(getHeight(n1.getLson()), getHeight(n1.getRson()))+1);
		n2.setHeight(max(getHeight(n2.getLson()), getHeight(n2.getRson()))+1);
		
		return n2;
		
	}
	
	/**

	 * */
	public TreeNode doubleRotateLR(TreeNode n1){
		n1.setLson(singRotateRight(n1.getLson()));;//
		n1=singRotateLeft(n1);
		return n1;
	}
	

	public TreeNode doubleRotateRL(TreeNode n1){
		n1.setRson(singRotateLeft(n1.getRson()));;
		n1=singRotateRight(n1);
		return n1;
	}
	

	public TreeNode insertNode(TreeNode node,double val){
		if (node==null) {
			node=new TreeNode(val, 0);
			return node;
		}

		if(node.getVal()>val){
			node.setLson(insertNode(node.getLson(), val));

			if((getHeight(node.getLson())-getHeight(node.getRson()))==2){
				if(val<node.getLson().getVal()){
					node=singRotateLeft(node);
				}else{
					node=doubleRotateLR(node);		
				}
			}
		}else if(node.getVal()<val){
			node.setRson(insertNode(node.getRson(), val));
			if((getHeight(node.getRson())-getHeight(node.getLson()))==2){
				if(val<node.getRson().getVal()){
					node=doubleRotateRL(node);
				}else{
					node=singRotateRight(node);
				}
			}
		}else{

			return node;
		}
		

		node.setHeight(max(getHeight(node.getLson()), getHeight(node.getRson()))+1);
		return node;
	}
	
	public void insert(double val){
		root=insertNode(root, val);		
	}
	

	private TreeNode deleteNode(TreeNode node,double val){
		if(node==null){
			return null;
		}
		if(node.getVal()>val){
			node.setLson(deleteNode(node.getLson(), val));
			if((getHeight(node.getRson())-getHeight(node.getLson()))==2){
				if((node.getRson().getLson()!=null)&&
						(getHeight(node.getRson().getLson())>getHeight(node.getRson().getRson()))){
					node=doubleRotateRL(node);
				}else{
					node=singRotateRight(node);
				}
			}
		}else if(node.getVal()<val){
			node.setRson(deleteNode(node.getRson(), val));
			if((getHeight(node.getLson())-getHeight(node.getRson()))==2){
				if((node.getLson().getRson()!=null)&&
						(getHeight(node.getLson().getRson())>getHeight(node.getLson().getLson()))){
					node=doubleRotateLR(node);
				}else{
					node=singRotateLeft(node);
				}
			}
		}else{

			if(node.getLson()!=null&&node.getRson()!=null){
				TreeNode temp=node.getRson();
				while(temp.getLson()!=null){
					temp=temp.getLson();
				}
				node.setVal(temp.getVal());
				System.out.println(temp.getVal());
				node.setRson(deleteNode(node.getRson(), temp.getVal()));;
				if((getHeight(node.getLson())-getHeight(node.getRson()))==2){
					if((node.getLson().getRson()!=null)&&
							(getHeight(node.getLson().getRson())>getHeight(node.getLson().getLson()))){
						node=doubleRotateLR(node);
					}else{
						node=singRotateLeft(node);
					}
				}
			}else{
				if(node.getLson()!=null){
					node=node.getLson();
				}else if(node.getRson()!=null){
					node=node.getRson();
				}else{
					node=null;
				}				
			}
		}
		if(node!=null){

			node.setHeight(max(getHeight(node.getLson()), getHeight(node.getRson()))+1);
		}
		return node;
	}
	
	public void delete(double val) {
		root=deleteNode(root, val);
	}
	

	public void preOrderTraversal(TreeNode node){
		if(node!=null){
			System.out.print(node.getVal()+"("+node.getHeight()+")"+"  ");
			preOrderTraversal(node.getLson());
			preOrderTraversal(node.getRson());
		}
	}
	

	public void inOrderTraversal(TreeNode node){
		if(node!=null){			
			inOrderTraversal(node.getLson());
			System.out.print(node.getVal()+"("+node.getHeight()+")"+"  ");
			inOrderTraversal(node.getRson());
		}
	}
	

	public void postOrderTraversal(TreeNode node){
		if(node!=null){			
			postOrderTraversal(node.getLson());
			postOrderTraversal(node.getRson());
			System.out.print(node.getVal()+"("+node.getHeight()+")"+"  ");
		}
	}	
	

	public void orderTraversal(){
		System.out.println("�������:");
		preOrderTraversal(root);
		System.out.println();
		System.out.println("�������:");
		inOrderTraversal(root);
		System.out.println();
		System.out.println("�������:");
		postOrderTraversal(root);
		System.out.println();
	}
	

	private void encoding(TreeNode node,double val,List<Integer> encode,int size){
		if(val==node.getVal()){
			if(encode.size()<size)
				encode.add(1);
			while(encode.size()<size){
				encode.add(0);
			}
		}else if (val<node.getVal()) {
			encode.add(0);
			encoding(node.getLson(),val,encode,size);
		}else if(val>node.getVal()){
			encode.add(1);
			encoding(node.getRson(), val, encode, size);
		}		
	}


	private void getNodeVal(TreeNode node,List<Double> vals){
		if(node!=null){
			vals.add(node.getVal());
			getNodeVal(node.getLson(), vals);
			getNodeVal(node.getRson(), vals);
		}
	}
	
	public Map<Double, List<Integer>> getEncoding(){
		if(root==null){
			return encodes;
		}
		int encode_size=root.getHeight()+1;//
		List<Double> vals=new ArrayList<>();//
		getNodeVal(root, vals);
		//System.out.prdoubleln(vals.size());
		for(int i=0;i<vals.size();i++){
			List<Integer> encode=new ArrayList<>();
			encoding(root, vals.get(i), encode, encode_size);
			encodes.put(vals.get(i), encode);
		}
		return encodes;
	}
}
