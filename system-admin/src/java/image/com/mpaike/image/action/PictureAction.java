/*
 * Copyright (C) 2009-2011 WWF Software Limited.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.

 * As a special exception to the terms and conditions of version 2.0 of 
 * the GPL, you may redistribute this Program in connection with Free/Libre 
 * and Open Source Software ("FLOSS") applications as described in WWF's 
 * FLOSS exception.  You should have recieved a copy of the text describing 
 * the FLOSS exception, and it is also available here: 
 */
package com.mpaike.image.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import proj.zoie.api.ZoieException;
import proj.zoie.api.DataConsumer.DataEvent;

import cn.vivame.v2.gene.model.GeneGrade;

import com.mpaike.client.zoie.service.IndexEngineBuild;
import com.mpaike.core.database.hibernate.OrderBy;
import com.mpaike.image.model.Picture;
import com.mpaike.user.model.SysUser;
import com.mpaike.util.ArrayUtil;
import com.mpaike.util.MyBeanUtils;
import com.mpaike.util.app.BaseAction;

/**
 * @author Chen.H @Date 2011-9-22
 * com.mpaike.image.action system-admin
 */
@SuppressWarnings("serial")
public class PictureAction extends BaseAction {
	
	
	
	private Picture picture;
	private String ids;
	private List<Picture> datas;
	private Long id;
	private String type;
	/**
	 * 查询列表
	 */
	public void list(){
		picture = new Picture();
		
		datas = this.getPictureService().find(picture, this.pageInfo, OrderBy.desc("id"));
		this.printPageList(datas);
	}
	
	public String imageList(){
		picture = new Picture();
		pageInfo.setPageSize(12);
		datas = this.getPictureService().find(picture, this.pageInfo, 	OrderBy.desc("id"));
		return "imageList";
	}
	
	public String imageProcess(){
		picture = this.getPictureService().find(id);
		return "imageProcess";
	}
	
	public String imagePrev(){
		picture = this.getPictureService().findPrev(id);
		return "imageProcess";
	}
	
	public String imageNext(){
		picture = this.getPictureService().findNext(id);
		return "imageProcess";
	}
	public void remove(){
		Long[] longValue =  ArrayUtil.toLongArray(ids,",");
		this.getPictureService().remove(longValue);
		super.printSuccessJson("删除成功！");
	}
	public void issue(){
		Long[] longValue =  ArrayUtil.toLongArray(ids,",");
		List list = new ArrayList();
		for(Long idds : longValue){
			list.add(new  DataEvent(this.getPictureService().find(idds), String.valueOf(version)));
		}
		try {
			IndexEngineBuild.getIndexingSystem().consume(list);
		} catch (ZoieException e) {
			
			e.printStackTrace();
		}
		super.printSuccessJson("发布成功！");
	}
	public void process(){
		
		Picture target = getPictureService().find(picture.getId());
		
	    MyBeanUtils.fillForNotNullObject(target, picture);
		//计算标签并保存
		StringBuilder geneContent = new StringBuilder();
		StringBuilder geneDefaultValue = new StringBuilder();
	
		if (StringUtils.isNotBlank(target.getTags())) {
			String[] tagsName = target.getTags().split(";");
				if (tagsName.length != 0) {
					List<GeneGrade> spiltTagsName = getGeneService().grade(tagsName);
					getGeneService().saveGradeRedis(target.getId(), spiltTagsName);
					if(!spiltTagsName.isEmpty()){
						for(GeneGrade gg : spiltTagsName){
							geneContent.append(gg.getTagName());
							geneContent.append(";");
							geneDefaultValue.append(gg.toString());
							geneDefaultValue.append(";");
						}
						target.setGeneContent(geneContent.substring(0, geneContent.length()-1).toString());
						target.setGeneDefaultValue(geneDefaultValue.substring(0, geneDefaultValue.length()-1).toString());
				
					}

				}
			
			if(geneContent.length()>0)
			{	
				picture = target;
				picture.setStatus(Picture.STATUS_ISSUE);
				this.getPictureService().save(picture);
				try {
					List list = new ArrayList();
					list.add(new  DataEvent(picture, String.valueOf(version)));
					IndexEngineBuild.getIndexingSystem().consume(list);
				} catch (ZoieException e) {
					
					e.printStackTrace();
				}
				
			}
			super.printSuccessJson("发布成功！");
		}else{
			super.printSuccessJson("发布失败！");
		}
		
	}
	public Picture getPicture() {
		return picture;
	}

	public void setPicture(Picture picture) {
		this.picture = picture;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public List<Picture> getDatas() {
		return datas;
	}

	public void setDatas(List<Picture> datas) {
		this.datas = datas;
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
	
}
