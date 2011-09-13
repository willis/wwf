package com.mpaike.affix.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.mpaike.affix.model.Affix;
import com.mpaike.affix.service.AffixConfig;
import com.mpaike.affix.service.AffixService;
import com.mpaike.util.ParamHelper;
import com.mpaike.util.app.ApplictionContext;
import com.mpaike.util.app.BaseAction;
import com.mpaike.util.file.FileUtil;

/**
 * 通用附件上传
 * 
 * @author zhangchong
 * @email zhangchong@maxtech.com.cn
 * @createDate Sep 9, 2011 2:55:21 PM
 * @description
 */
public class AffixAction extends BaseAction {

	private String name;
	/**
	 * 文件内容
	 */
	private File file;// 
	/**
	 * 对像id，例如人员id
	 */
	private String objectId;// 
	/**
	 * 对像类别，例如 1：人员照片 2：人员档案 3：部门档案
	 */
	private Integer objectType;// 

	AffixConfig affixConfig = (AffixConfig) ApplictionContext.getInstance()
			.getBean(AffixConfig.ID_NAME);
	AffixService service = (AffixService) ApplictionContext.getInstance()
			.getBean(AffixService.ID_NAME);

	/**
	 * 通用附件上传，附件有objectType和objectId两个字段，objectType用于区别附件类别，
	 * 一般情况下开发时可以将所有需要上传附件的地方做一个分类
	 * 例如：人员照片、个人文档、部门文档，可以定义三个objectType分别是1、2、3，objectId则用于存放该人员的id或是该部门的id
	 * 如果需要存储某个人员的照片，可以使用条件 objectType=1，objectId=人员id存储，如需存储某人员的文档，可以使用使用条件
	 * objectType=2，objectId=人员id存储
	 */
	public String addFile() throws Exception {
		try {

			Calendar c = Calendar.getInstance();
			String uploadPath;
			int userWater = 0;

			uploadPath = "/" + c.get(Calendar.YEAR) + "/"
					+ c.get(Calendar.DAY_OF_MONTH);

			if (affixConfig.getDirs() != null
					&& affixConfig.getDirs().get(getObjectType()) != null) {
				uploadPath = "/" + affixConfig.getDirs().get(getObjectType())
						+ uploadPath;
			}

			String filePath = affixConfig.getBaseDir() + uploadPath;

			File file2 = new File(filePath);
			if (!file2.exists())
				file2.mkdirs();
			// 创建一个随机的文件ID
			String objId = UUID.randomUUID().toString();
			String fileName = file.getName();
			String extendName = fileName
					.substring(fileName.lastIndexOf(".") + 1);

			BufferedOutputStream bout = new BufferedOutputStream(
					new FileOutputStream(filePath + "/" + objId + "."
							+ extendName));

			if (userWater == 0) {
				BufferedInputStream bin = new BufferedInputStream(
						new FileInputStream(file));
				// System.out.println(fileForm.getFile().getFileData().length);
				FileUtil.createFile(bin, bout);
			}

			Affix affix = new Affix();
			affix.setName(getName());
			affix.setObjectId(getObjectId());
			affix.setObjectType(getObjectType());

			affix.setSource(filePath + "/" + objId + "." + extendName);

			String fileSizeStr = FileUtil.getFileSizeStr(new FileInputStream(
					file).getChannel().size());

			affix.setSize(fileSizeStr);
			affix.setCreateDate(new Date());

			affix.setExtname(extendName.toLowerCase());

			affix.setName(fileName);

			service.addAffix(affix);
			request.setAttribute("affix", affix);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return "success";
	}

	/**
	 * 通用附件下载类 通过附件id直接下载该附件
	 */
	public void download() throws Exception {
		// response.setCharacterEncoding("UTF-8");
		// response.setContentType("application/x-msdownload");//
		// 不同类型的文件对应不同的MIME类型
		long id = ParamHelper.getLongParamter(request, "id", -1);

		if (id == -1)
			return;
		Affix affix = service.getAffix(id);

		response.reset();
		response.addHeader("Content-Disposition", "attachment; filename="
				+ new String(affix.getName().getBytes(), "iso-8859-1"));
		// response.setContentType(affix.getAtype());
		response.setContentType("application/octet-stream");
		// 取得文件上传地址
		String filePath = affix.getSource();
		OutputStream out = response.getOutputStream();
		BufferedInputStream bin = new BufferedInputStream(new FileInputStream(
				filePath));

		FileUtil.createFile(bin, out);

	}

	/**
	 * 图片查看
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public void showImage() throws Exception {
		// 不同类型的文件对应不同的MIME类型
		long id = ParamHelper.getLongParamter(request, "id", -1);
		String filePath = "";
		if (id == -1) {
			filePath = request.getSession().getServletContext().getRealPath(
					"/images/no-photo.png");
		} else {
			Affix affix = service.getAffix(id);
			response.reset();
			response.addHeader("Content-Disposition", "attachment; filename="
					+ new String(affix.getName().getBytes(), "iso-8859-1"));
			// response.setContentType(affix.getAtype());
			response.setContentType("application/octet-stream");
			// 取得文件上传地址
			filePath = affix.getSource();
		}
		try {
			OutputStream out = response.getOutputStream();
			BufferedInputStream bin = new BufferedInputStream(
					new FileInputStream(filePath));
			FileUtil.createFile(bin, out);
		} catch (Exception ex) {
			// ex.printStackTrace();
		}
		return;
	}

	/**
	 * 附件删除，通过id删除该附件
	 */
	public void delete() throws Exception {
		long id = ParamHelper.getLongParamter(request, "id", -1);
		service.delAffix(id);
		super.printSuccessJson("删除成功！");
		return;
		// return mapping.findForward(this.SUCCESS);
	}

	/**
	 * 查询附件列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String list() throws Exception {
		long objectType = ParamHelper
				.getLongParamter(request, "objectType", -1);
		String objectId = ParamHelper.getStr(request, "objectId", "");
		List<Affix> beans = service.getAffixs(objectType, objectId);
		request.setAttribute("beans", beans);
		request.setAttribute("affixSize", beans.size());
		return "success";
	}


	public void showFile() throws Exception {
		// response.setCharacterEncoding("UTF-8");
		// response.setContentType("application/x-msdownload");//
		// 不同类型的文件对应不同的MIME类型
		long id = ParamHelper.getLongParamter(request, "id", -1);

		if (id == -1)
			return;
		Affix affix = service.getAffix(id);

		response.reset();
		// response.addHeader("Content-Disposition", "attachment; filename=" +
		// new String(affix.getName().getBytes(), "iso-8859-1"));
		response.setContentType(affix.getType());

		// 取得文件上传地址
		String filePath = affix.getSource();
		OutputStream out = response.getOutputStream();
		BufferedInputStream bin = new BufferedInputStream(new FileInputStream(
				filePath));

		FileUtil.createFile(bin, out);

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public Integer getObjectType() {
		return objectType;
	}

	public void setObjectType(Integer objectType) {
		this.objectType = objectType;
	}

}
