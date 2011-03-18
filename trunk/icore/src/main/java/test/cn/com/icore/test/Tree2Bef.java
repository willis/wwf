package cn.com.icore.test;

public class Tree2Bef { 
	private StringBuffer bef=new StringBuffer(); 

	//传入中序遍历和后序遍历,返回前序遍历字串 
	public String getBef(String mid, String beh) { 
	//若节点存在则向bef中添加该节点,继续查询该节点的左子树和右子树 
	if (root(mid, beh) != -1) { 
	int rootindex=root(mid, beh); 
	char root=mid.charAt(rootindex); 
	bef.append(root); 
	System.out.println(bef.toString()); 
	String mleft, mright; 
	mleft = mid.substring(0,rootindex); 
	mright = mid.substring(rootindex+1); 
	getBef(mleft,beh); 
	getBef(mright,beh); 
	} 
	//所有节点查询完毕,返回前序遍历值 
	return bef.toString(); 

	} 

	//从中序遍历中根据后序遍历查找节点索引值index 
	private int root(String mid, String beh) { 
	char[] midc = mid.toCharArray(); 
	char[] behc = beh.toCharArray(); 
	for (int i = behc.length-1; i > -1; i--) { 
	for (int j = 0; j < midc.length; j++) { 
	if (behc[i] == midc[j]) 
	return j; 
	} 
	} 
	return -1; 
	} 

	public static void main(String[] args) { 
	Tree2Bef tree=new Tree2Bef(); 
	String mid="84925163A7B"; 
	String bef="894526AB731"; 
	System.out.println(tree.getBef(mid,bef)); 
	} 
	} 

