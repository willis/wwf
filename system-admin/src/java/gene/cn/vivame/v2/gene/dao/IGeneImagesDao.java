package cn.vivame.v2.gene.dao;

import java.io.File;

public interface IGeneImagesDao {
	
	public String saveIcon(String tagName, File file);
	
	public String saveCover(String tagName, File file);
	
	public boolean remove(String fileName);

}
