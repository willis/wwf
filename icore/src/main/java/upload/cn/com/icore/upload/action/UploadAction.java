package cn.com.icore.upload.action;

import java.util.List;

import com.fins.gt.server.GridServerHandler;
import cn.com.icore.upload.model.Annex;
import cn.com.icore.upload.service.AnnexService;
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
		String result ="删除成功!";
	
		getAnnexService().remove(id);
		
		super.printSuccessJson(response, result);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
