package com.mpaike.core.util;

import java.util.Set;

/**
 * 下拉列表框树
 */
public interface SelectTree {
	/**
	 * 获得树ID
	 * 
	 * @return
	 */
	public Long getId();

	/**
	 * 获得父节点
	 * 
	 * @return
	 */
	public SelectTree getTreeParent();

	/**
	 * 获得原节点名称
	 * 
	 * @return
	 */
	public String getTreeName();

	/**
	 * 获得下拉列表树名称
	 * 
	 * @return
	 */
	public String getSelectTree();

	/**
	 * 设置下拉列表树名称
	 * 
	 * @param selectTree
	 */
	public void setSelectTree(String selectTree);

	/**
	 * 获得树的子节点。可以被处理，比如为null时，可以直接调用getChild()
	 * 
	 * @return
	 */
	public Set<? extends SelectTree> getTreeChild();

	/**
	 * 未处理的子节点。如果没有调用setTreeChild，则该方法返回null。
	 * 
	 * @return
	 */
	public Set<? extends SelectTree> getTreeChildRaw();

	/**
	 * 设置树的子节点
	 * 
	 * @param treeChild
	 */
	@SuppressWarnings("unchecked")
	public void setTreeChild(Set treeChild);
}
