package com.mpaike.user.action;

import com.mpaike.user.model.SysPopedom;
import com.mpaike.util.Message;
import com.mpaike.util.MessageType;
import com.mpaike.util.MyBeanUtils;
import com.mpaike.util.app.BaseAction;

import java.util.ArrayList;
import java.util.List;


public class SysPopedomAction extends BaseAction {
    private SysPopedom sysPopedom;
    private Long id;
    private String[] c;
    private Long[] ids;
    private List<SysPopedom> authList = new ArrayList<SysPopedom>();
    /**
     *
     */
    private static final long serialVersionUID = 1L;


    public String list() {
        sysPopedom = sysPopedom == null ? new SysPopedom() : sysPopedom;
        authList = this.getSysPopedomService().listToGrid(sysPopedom, this.getPageInfo(), this.getOrderby());
        return "list";
    }

    public void del(){
        logger.info(ids);
        for(Long id : ids) {
            getSysPopedomService().removeSysPopedom(id);
        }
        Message message = new Message();
        message.setStatusCode(MessageType.SUCCESS.value);
        message.setMessage("操作成功");
        printMessage(message);
    }

    public String edit() {
        sysPopedom = this.getSysPopedomService().getSysPopedom(id);
        return "edit";
    }


    public void saveOrUpdate(){
        if (sysPopedom.getId() != null) {
            SysPopedom target = this.getSysPopedomService().getSysPopedom(sysPopedom
                    .getId());
            MyBeanUtils.fillForNotNullObject(target, sysPopedom);
            sysPopedom = target;
        }
        getSysPopedomService().saveSysPopedom(sysPopedom);
        Message message = new Message();
        message.setStatusCode(MessageType.SUCCESS.value);
        message.setMessage("操作成功");
        message.setCallbackType("closeCurrent");
        printMessage(message);
    }


    public SysPopedom getSysPopedom() {
        return sysPopedom;
    }

    public void setSysPopedom(SysPopedom sysPopedom) {
        this.sysPopedom = sysPopedom;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long[] getIds() {
        return ids;
    }

    public void setIds(Long[] ids) {
        this.ids = ids;
    }

    public List<SysPopedom> getAuthList() {
        return authList;
    }

    public void setAuthList(List<SysPopedom> authList) {
        this.authList = authList;
    }

}
