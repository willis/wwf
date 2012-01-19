package com.mpaike.image.service.impl;

import java.io.File;
import java.util.List;

import com.mpaike.core.database.hibernate.OrderBy;
import com.mpaike.core.util.page.Pagination;
import com.mpaike.image.model.Picture;
import com.mpaike.image.service.IPictureService;
import com.mpaike.sys.service.impl.BaseService;
import com.mpaike.util.FileUtil;

public class PictureService extends BaseService implements IPictureService {
	private String path;
	
	


	@Override
	public List<Picture> find(Picture pic, Pagination p, OrderBy ob) {
	
		return this.getPictureDao().find(pic, p, ob);
	}

	@Override
	public void remove(Long[] longValue) {
		for(Long id : longValue){
			Picture pic = this.getPictureDao().get(id);
		
			File file = new File(this.path+pic.getPath());
			// 如果文件路径所对应的文件存在，并且是一个文件，则直接删除

			FileUtil.deleteDirectory(file);
			if (file.exists() && file.isFile()) {
				file.delete();
			}
			this.getPictureDao().deleteById(id);
		}
		
	}

	@Override
	public Picture find(Long id) {
		return this.getPictureDao().get(id);
	}

	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public Picture findPrev(Long id) {
		
		return this.getPictureDao().findPrev(id);
	}

	@Override
	public Picture findNext(Long id) {
		
		return this.getPictureDao().findNext(id);
	}

}
