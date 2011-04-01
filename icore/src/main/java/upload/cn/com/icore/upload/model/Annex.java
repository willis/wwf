package cn.com.icore.upload.model;

import java.util.Date;

import cn.com.icore.user.model.SysUser;
import cn.com.icore.util.dao.IBeanPrimaryKey;


@SuppressWarnings("serial")
public class Annex implements IBeanPrimaryKey, java.io.Serializable{
	
	private Long id;

	private String fileNames;

	private String fileSize;

	private String filePath;

	private String type;
    
	private Date date;

	private SysUser user;
	
	private String object_id;

	public Annex() {
		super();
	}

	public Annex(Long id, String fileNames, String fileSize, String filePath,
			String type, Date date, String object_id) {
		super();
		this.id = id;
		this.fileNames = fileNames;
		this.fileSize = fileSize;
		this.filePath = filePath;
		this.type = type;
		this.date = date;
		this.object_id = object_id;
	}

	public String getFileNames() {
		return fileNames;
	}

	public void setFileNames(String fileNames) {
		this.fileNames = fileNames;
		
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public SysUser getUser() {
		return user;
	}

	public void setUser(SysUser user) {
		this.user = user;
	}

	public String getObject_id() {
		return object_id;
	}

	public void setObject_id(String object_id) {
		this.object_id = object_id;
	}

	
}
