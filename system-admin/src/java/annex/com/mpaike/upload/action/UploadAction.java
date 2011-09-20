package com.mpaike.upload.action;

import java.util.List;

import com.mpaike.upload.model.Annex;
import com.mpaike.upload.service.AnnexService;
import com.mpaike.util.ParamHelper;
import com.mpaike.util.app.ApplictionContext;
import com.mpaike.util.app.BaseAction;

@SuppressWarnings("serial")
public class UploadAction extends BaseAction {

	public AnnexService getAnnexService() {
		return (AnnexService) ApplictionContext.getInstance().getBean(
				AnnexService.ID_NAME);
	}

	private Long id;

	public void annexList() {
		String type = ParamHelper.getStr(request, "type", "");
		String object_id = ParamHelper.getStr(request, "object_id", "");
		List<Annex> datas = getAnnexService().find(object_id, type,this.pageInfo,this.getOrderby());
		this.printPageList(datas);
	}

	public void getAnnexByObjectId(){
		String type = ParamHelper.getStr(request, "type", "");
		String object_id = ParamHelper.getStr(request, "object_id", "");
		List<Annex> datas = getAnnexService().findByObject_id(object_id, type);
	    Annex annex = datas.get(0);
	    String msg ="{status:1,id:" + ParamHelper.toJsString(annex.getId().toString())+",fileName:'"+ParamHelper.toJsString(annex.getFileNames())+"',fileSize:'"+annex.getFileSize()+"'}";
		this.printJson(msg);
		
	}
	public void remove() {
		String result = "删除成功!";

		getAnnexService().remove(id);

		this.printSuccessJson(result);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
