package cn.com.icore.util.dao;
/**
 * 主键接口
 * 
 * 为IBeanManger接口提供主键操作
 * 
 * @author niko
 * 
 */
public interface IBeanPrimaryKey {
	/**
	 * 得到主键
	 * 
	 * @return
	 */
	Long getId();

	/**
	 * 设置主键
	 * 
	 * @param l
	 */
	void setId(Long l);
}
