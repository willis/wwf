package cn.com.icore.upload.action;

import java.io.File;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.FileCleanerCleanup;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileCleaningTracker;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.fins.gt.server.GridServerHandler;
import cn.com.icore.upload.model.Annex;
import cn.com.icore.upload.service.AnnexService;
import cn.com.icore.user.service.LoginControl;
import cn.com.icore.util.ParamHelper;
import cn.com.icore.util.app.ApplictionContext;
import cn.com.icore.util.app.BaseAction;

@SuppressWarnings("serial")
public class UploadAction extends BaseAction {

	public AnnexService getAnnexService() {
		return (AnnexService) ApplictionContext.getInstance().getBean(
				AnnexService.ID_Name);
	}

	private Long id;

	public void annexList() {
		String type = ParamHelper.getStr(request, "type", "");
		String object_id = ParamHelper.getStr(request, "object_id", "");
		GridServerHandler handler = super.getGridServerHandler(request,
				response);
		List<Annex> datas = getAnnexService().find(object_id, type,
				handler.getPageInfo());
		handler.setData(datas, Annex.class);
		handler.printLoadResponseText();
	}

	public void remove() {
		String result = "删除成功!";

		getAnnexService().remove(id);

		super.printSuccessJson(response, result);
	}

	public void upload() {
		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			
			

			boolean isMultipart = ServletFileUpload.isMultipartContent(request);
			if (!isMultipart) {
				System.out.println(">> This wasn't a file upload request!");
				return;
			}

			PrintWriter out = response.getWriter();
			// create factory and file cleanup tracker
			FileCleaningTracker tracker = FileCleanerCleanup
					.getFileCleaningTracker(ServletActionContext
							.getServletContext());
			File tmpDir = new File(getBaseDir() + "/upload/temp");
			String object_id = ParamHelper.getStr(request, "object_id", "");
			String type = ParamHelper.getStr(request, "type", "");
			System.out.println("---------->object_id:"+object_id);
			DiskFileItemFactory factory = new DiskFileItemFactory(
					DiskFileItemFactory.DEFAULT_SIZE_THRESHOLD, tmpDir);
			factory.setFileCleaningTracker(tracker);
			String objId = UUID.randomUUID().toString();
			// save upload file to disk
			ServletFileUpload upload = new ServletFileUpload(factory);

			List<FileItem> items = upload.parseRequest(ServletActionContext.getRequest());
			String fileName = null;
			File savefile = null;
			System.out.println(items.size());
			for (FileItem item : items) {
				if (!item.isFormField()) {
					// 确定是文件而不是一个普通的表单字段
					fileName = item.getName();
					Annex annex = new Annex();
					annex.setDate(new Date(System.currentTimeMillis()));

					annex.setUser(LoginControl.getSysUser(request));
					annex.setFileNames(fileName);
					String extendName = fileName.substring(fileName
							.lastIndexOf(".") + 1);
					annex.setFilePath("/upload/" + objId + "." + extendName);

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
					request.setAttribute("object_id", object_id);
					out.flush();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Return the webapp directory.
	 * 
	 * @return
	 */
	private String getBaseDir() {
		return ServletActionContext.getServletContext().getRealPath("");
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
