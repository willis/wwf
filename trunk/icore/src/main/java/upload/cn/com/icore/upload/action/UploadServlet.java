package cn.com.icore.upload.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.FileCleanerCleanup;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileCleaningTracker;
import org.apache.commons.lang.StringUtils;

import cn.com.icore.upload.model.Annex;
import cn.com.icore.upload.service.AnnexService;
import cn.com.icore.user.service.LoginControl;
import cn.com.icore.util.ParamHelper;
import cn.com.icore.util.app.ApplictionContext;



/**
 * 
 * @author Chen.H
 *
 */
public class UploadServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AnnexService getAnnexService() {
		return (AnnexService) ApplictionContext.getInstance()
		.getBean(AnnexService.ID_Name);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doPost(req, resp);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		
		boolean isMultipart = ServletFileUpload.isMultipartContent(req);
		if (!isMultipart){
			System.out.println(">> This wasn't a file upload request!");
			return;
		}
		
		PrintWriter out = resp.getWriter();
		// create factory and file cleanup tracker
		FileCleaningTracker tracker = FileCleanerCleanup.getFileCleaningTracker(getServletContext());
		File tmpDir = new File(getBaseDir() + "/upload/temp");
		String object_id = ParamHelper.getStr(req, "object_id", "");
		String type = ParamHelper.getStr(req, "type", "");

		DiskFileItemFactory factory = new DiskFileItemFactory(
				DiskFileItemFactory.DEFAULT_SIZE_THRESHOLD,
				tmpDir);
		factory.setFileCleaningTracker(tracker);
		String objId = UUID.randomUUID().toString();
		// save upload file to disk
		ServletFileUpload upload = new ServletFileUpload(factory);
		try {
			List<FileItem> items = upload.parseRequest(req);
			String fileName = null;
			File savefile = null;
			for (FileItem item : items){
				if (!item.isFormField()){
					// 确定是文件而不是一个普通的表单字段
					fileName = item.getName();
					Annex annex = new Annex();
					annex.setDate(new Date(System.currentTimeMillis()));
					annex.setUser(LoginControl.getSysUser(req));
					annex.setFileNames(fileName);
					String extendName = fileName
					.substring(fileName.lastIndexOf(".") + 1);
					annex.setFilePath("/upload/" + objId + "."
							+ extendName);
					
					annex.setFileSize(item.getSize() + "");
					annex.setType(type);
					if (StringUtils.isNotBlank(object_id)) {
						annex.setObject_id(object_id);
					} else {
						object_id = "00-";
						object_id += UUID.randomUUID().toString();
						annex.setObject_id(object_id);
					}
				 
					savefile = new File(getBaseDir() + "/upload/" + objId + "."
							+ extendName);
					item.write(savefile);
					
					getAnnexService().save(annex);
					out.print(object_id);
					req.setAttribute("object_id", object_id);
					out.flush();
				}
			}
		} catch (Exception e) {
			throw new IOException(e.getMessage());
		}
	}
	/**
	 * 初始系统
	 * 
	 * @param config
	 *            ServletConfig
	 * @throws ServletException
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		File uploadDir = new File(getBaseDir() + "/upload");
		try{
		if (!uploadDir.exists()) { 
			 System.out.println("系统未包含upload目录，创建upload成功"); 
			 uploadDir.mkdir(); 
		} 
		}catch (Exception e) { 
		      System.out.println("新建文件夹操作出错"); 
		      e.printStackTrace(); 
		} 
		File tempDir = new File(getBaseDir() + "/upload/temp");
		try{
		if (!tempDir.exists()) { 
			 System.out.println("系统未包含upload/temp目录，创建upload/temp成功"); 
			 tempDir.mkdir(); 
		} 
		}catch (Exception e) { 
		      System.out.println("新建文件夹操作出错"); 
		      e.printStackTrace(); 
		} 
		
	}
	/**
	 * Return the webapp directory.
	 * @return
	 */
	private String getBaseDir(){
		return this.getServletContext().getRealPath("");
	}
}
