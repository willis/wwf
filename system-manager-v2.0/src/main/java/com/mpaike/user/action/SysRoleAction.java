package com.mpaike.user.action;

import com.mpaike.user.model.SysMenu;
import com.mpaike.user.model.SysPopedom;
import com.mpaike.user.model.SysRole;
import com.mpaike.user.service.SysMenuControl;
import com.mpaike.util.Message;
import com.mpaike.util.MessageType;
import com.mpaike.util.MyBeanUtils;
import com.mpaike.util.app.BaseAction;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class SysRoleAction extends BaseAction {

    private static final long serialVersionUID = 1L;
    private SysRole sysRole;
    private long id;
    private List<SysMenu> tree = new ArrayList<SysMenu>();
    private SysMenu rootObj;
    private Long[] ids;
    private String[] cs;
    private String[] c;
    private Long roleId;
    private List<SysRole> roleList;
    List<SysPopedom> all;
    List<SysPopedom> checked;

    public String list() {
        sysRole = sysRole == null ? new SysRole() : sysRole;
        roleList = this.getSysRoleService().listToGrid(sysRole, getPageInfo(), this.getOrderby());
        return "list";
    }

    public void saveOrUpdate() {
        Message message = new Message();
        if (sysRole.getId() != null) {
            SysRole target = this.getSysRoleService().getSysRole(sysRole.getId());
            MyBeanUtils.fillForNotNullObject(target, sysRole);
            sysRole = target;
        }
        if (getSysRoleService().getSysRoleList(sysRole.getName()).size() > 0) {
            message.setStatusCode(MessageType.ERROR.value);
            message.setMessage("角色名已重复");
        } else {
            getSysRoleService().saveSysRole(sysRole);
            message.setStatusCode(MessageType.SUCCESS.value);
            message.setMessage("操作成功");
        }
        printMessage(message);
    }

    public String edit() {
        sysRole = this.getSysRoleService().getSysRole(id);
        return "edit";
    }

    public void del() {
        for (Long id : ids) {
            this.getSysRoleService().removeSysRole(id);
        }
        Message message = new Message();
        message.setStatusCode(MessageType.SUCCESS.value);
        message.setMessage("操作成功");
        printMessage(message);
    }

    public String sysrolePopedom() throws Exception {
        sysRole = new SysRole();
        sysRole.setId(id);
        all = this.getSysPopedomService().listToGrid(new SysPopedom(), getPageInfo(), getOrderby());
        checked = this.getSysRoleService().listCheckPopedomsToGrid(sysRole);
        for (SysPopedom sysPopedom : all) {
            for (SysPopedom sysPopedom1 : checked) {
                if (sysPopedom.getId().equals(sysPopedom1.getId())) {
                    sysPopedom.setChecked(1);
                    break;
                }
            }
        }
        //   notChecked = this.getSysRoleService().listNotCheckPopedomsToGrid(sysRole,getPageInfo());
        return "sysrolePopedom";
    }


    /*public void getCheckPopedoms() throws Exception {


         sysRole = new SysRole();
         sysRole.setId(id);
         List<SysPopedom> datas = this.getSysRoleService().listCheckPopedomsToGrid(sysRole,this.pageInfo);
         this.printPageList(datas);

     }*/

    public void getNotCheckPopedoms() throws Exception {

        sysRole = new SysRole();
        sysRole.setId(id);
        List<SysPopedom> datas = this.getSysRoleService().listNotCheckPopedomsToGrid(sysRole, this.pageInfo);
        this.printPageList(datas);

    }

    public void addRolePopedoms() throws Exception {
        Message message = new Message();
        if (id == -1L) {
            message.setMessage("请选择权限！");
        }
        if ((cs == null) || (cs.length == 0))
            message.setMessage("请选择权限！");
        if (cs.length == 1) {
            cs = cs[0].split(",");
        }
        this.getSysRoleService().addSysPopedom(id, cs);
        SysMenuControl.getInstance().putRootTree();
        message.setStatusCode(MessageType.SUCCESS.value);
        message.setMessage("操作成功");
        printMessage(message);

    }

    public void delRolePopedoms() throws Exception {
        Message message = new Message();

        if (id == -1L) {
            message.setMessage("请选择权限！");
        }
        if ((cs == null) || (cs.length == 0))
            message.setMessage("请选择权限！");
        if (cs.length == 1) {
            cs = cs[0].split(",");
        }
        getSysRoleService().removeSysPopedom(id, cs);
        message.setStatusCode(MessageType.SUCCESS.value);
        message.setMessage("操作成功");
        printMessage(message);

    }

    public String listSysMenu() {
        long rootId = Long.parseLong(super.getAppProps().get("sysMenuRootId")
                .toString());
        List<SysMenu> list = this.getSysMenuService().getTree(rootId);
        tree = new ArrayList<SysMenu>();
        for(SysMenu menu : list){
            if(menu.getLevel()==1){
                tree.add(menu);
            }
        }

        List<SysMenu> beans = this.getSysRoleService().getSysMenusByRoleId(id);
        for (SysMenu menu : tree) {
            menu.setChecked(isTreeChecked(beans, menu.getId()));
            Set<SysMenu> ch1 = menu.getChilds();
            if(ch1!=null){
                for(SysMenu chm1 : ch1){
                    chm1.setChecked(isTreeChecked(beans,chm1.getId()));
                    Set<SysMenu> ch2 = chm1.getChilds();
                    if(ch2!=null){
                        for (SysMenu chm2  :ch2) {
                            chm2.setChecked(isTreeChecked(beans,chm2.getId()));
                        }
                    }
                }
            }
        }
        return "menulist";
    }

    private String isTreeChecked(List<SysMenu> menus,Long curid){
        String result = "false";
        for(SysMenu menu : menus)
            if (menu.getId().equals(curid))
                result = "checked";

        return result;
    }

    public void addSysMenus() {
        Message message = new Message();

        if (roleId == -1L)
            super.printSuccessJson("角色为空不能设置菜单！");
        String[] cs = c;

        if (cs == null) {
            this.getSysRoleService().addSysMenu(roleId, new String[0]);
            message.setStatusCode(MessageType.SUCCESS.value);
            message.setMessage("操作成功");
            printMessage(message);
            return;
        }

        this.getSysRoleService().addSysMenu(roleId, cs);
        SysMenuControl.getInstance().putRootTree();
        message.setStatusCode(MessageType.SUCCESS.value);
        message.setMessage("操作成功");
        printMessage(message);
    }


    public SysRole getSysRole() {
        return sysRole;
    }

    public void setSysRole(SysRole sysRole) {
        this.sysRole = sysRole;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<SysMenu> getTree() {
        return tree;
    }

    public void setTree(List<SysMenu> tree) {
        this.tree = tree;
    }

    public SysMenu getRootObj() {
        return rootObj;
    }

    public void setRootObj(SysMenu rootObj) {
        this.rootObj = rootObj;
    }

    public Long[] getIds() {
        return ids;
    }

    public void setIds(Long[] ids) {
        this.ids = ids;
    }

    public String[] getCs() {
        return cs;
    }

    public void setCs(String[] cs) {
        this.cs = cs;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String[] getC() {
        return c;
    }

    public void setC(String[] c) {
        this.c = c;
    }

    public List<SysRole> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<SysRole> roleList) {
        this.roleList = roleList;
    }

    public List<SysPopedom> getAll() {
        return all;
    }

    public void setAll(List<SysPopedom> all) {
        this.all = all;
    }

    public List<SysPopedom> getChecked() {
        return checked;
    }

    public void setChecked(List<SysPopedom> checked) {
        this.checked = checked;
    }


}
