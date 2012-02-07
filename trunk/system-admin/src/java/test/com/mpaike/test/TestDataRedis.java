package com.mpaike.test;

import java.text.ParseException;

import org.springframework.context.support.AbstractApplicationContext;

import cn.vivame.v2.gene.model.Tag;
import cn.vivame.v2.gene.model.TagRelation;
import cn.vivame.v2.gene.service.IGeneService;

import com.mpaike.core.exception.ParameterException;
import com.mpaike.core.util.ApplicationContextUtil;

public class TestDataRedis {

	/**
	 * @param args
	 * @throws ParameterException 
	 * @throws ParseException 
	 */
	public static void main(String[] args) throws ParameterException, ParseException {
		restData();
		//restData();
		//System.out.print(DateTimeUtil.getTimeStamp("2011-12-30 23:00:00", 1));
	}
	
	public static void testRemoveTagChild() throws ParameterException{
		AbstractApplicationContext ac = ApplicationContextUtil.getInstance().creatContext();
		IGeneService db = (IGeneService)ac.getBean("geneService");
		Tag tag = db.getTag("足球运动员容器");
		db.removeTagChild(tag);
	}
	
	public static void restData() throws ParameterException {
		AbstractApplicationContext ac = ApplicationContextUtil.getInstance().creatContext();
		
//		KeyValueDao db1 = (KeyValueDao)ac.getBean("keyValueDao");
//		db1.remove("TAG");
		
		IGeneService db = (IGeneService)ac.getBean("geneService");
		db.removeAll();
		
//		db.remove(new TagRelation("test","test",TagRelation.RELATION_GATHER));
//		System.out.println(db.getTag("test"));
		
		Tag tag1 = new Tag("娱乐",Tag.TYPE_INSTANCE,Tag.PROPERTY_WHERE);
			Tag tag10 = new Tag("明星",Tag.TYPE_INSTANCE,Tag.PROPERTY_WHERE);
				Tag tag101 = new Tag("周迅",Tag.TYPE_INSTANCE,Tag.PROPERTY_WHERE);
				Tag tag102 = new Tag("李亚鹏",Tag.TYPE_INSTANCE,Tag.PROPERTY_WHERE);
				Tag tag103 = new Tag("王海涛",Tag.TYPE_INSTANCE,Tag.PROPERTY_WHERE);
				Tag tag104 = new Tag("王太鹏",Tag.TYPE_INSTANCE,Tag.PROPERTY_WHERE);
				Tag tag105 = new Tag("小王",Tag.TYPE_INSTANCE,Tag.PROPERTY_WHERE);
				Tag tag106 = new Tag("王菲",Tag.TYPE_INSTANCE,Tag.PROPERTY_WHERE);
				tag106.getAlias().add("王靖雯");
			Tag tag11 = new Tag("电影",Tag.TYPE_INSTANCE,Tag.PROPERTY_WHERE);
			Tag tag12 = new Tag("八卦",Tag.TYPE_INSTANCE,Tag.PROPERTY_WHERE);
			Tag tag13 = new Tag("音乐",Tag.TYPE_INSTANCE,Tag.PROPERTY_WHERE);
		
		Tag tag2 = new Tag("时尚",Tag.TYPE_INSTANCE,Tag.PROPERTY_WHERE);
			Tag tag21 = new Tag("服饰",Tag.TYPE_INSTANCE,Tag.PROPERTY_WHERE);
			Tag tag22 = new Tag("化妆",Tag.TYPE_INSTANCE,Tag.PROPERTY_WHERE);
			Tag tag23 = new Tag("另类",Tag.TYPE_INSTANCE,Tag.PROPERTY_WHERE);
			
		Tag tag3 = new Tag("科技",Tag.TYPE_INSTANCE,Tag.PROPERTY_WHERE);
			Tag tag30 = new Tag("IT硬件",Tag.TYPE_INSTANCE,Tag.PROPERTY_WHERE);
			Tag tag31 = new Tag("IT软件",Tag.TYPE_INSTANCE,Tag.PROPERTY_WHERE);
			Tag tag32 = new Tag("生物学",Tag.TYPE_INSTANCE,Tag.PROPERTY_WHERE);
			Tag tag33 = new Tag("物理学",Tag.TYPE_INSTANCE,Tag.PROPERTY_WHERE);
			
		Tag tag4 = new Tag("新闻",Tag.TYPE_INSTANCE,Tag.PROPERTY_WHERE);
		
		Tag tag5 = new Tag("汽车",Tag.TYPE_INSTANCE,Tag.PROPERTY_WHERE);
		Tag tag6 = new Tag("房产",Tag.TYPE_INSTANCE,Tag.PROPERTY_WHERE);
		
		//添加标签
		db.saveTag(tag1,false,true);
		db.saveTag(tag10,false,true);
			db.saveTag(tag101,false,true);
			db.saveTag(tag102,false,true);
			db.saveTag(tag103,false,true);
			db.saveTag(tag104,false,true);
			db.saveTag(tag105,false,true);
			db.saveTag(tag106,false,true);
		db.saveTag(tag11,false,true);
		db.saveTag(tag12,false,true);
		db.saveTag(tag13,false,true);
		
		db.saveTag(tag2,false,true);
		db.saveTag(tag21,false,true);
		db.saveTag(tag22,false,true);
		db.saveTag(tag23,false,true);
		
		db.saveTag(tag3,false,true);
		db.saveTag(tag30,false,true);
		db.saveTag(tag31,false,true);
		db.saveTag(tag32,false,true);
		db.saveTag(tag33,false,true);

		db.saveTag(tag4,false,true);
		db.saveTag(tag5,false,true);
		db.saveTag(tag6,false,true);
		
		//添加关系
		db.addTagRelation(new TagRelation("明星","娱乐",TagRelation.RELATION_GATHER));
			db.addTagRelation(new TagRelation("王海涛","明星",TagRelation.RELATION_GATHER));
			db.addTagRelation(new TagRelation("王太鹏","明星",TagRelation.RELATION_GATHER));
			db.addTagRelation(new TagRelation("小王","明星",TagRelation.RELATION_GATHER));
			db.addTagRelation(new TagRelation("李亚鹏","明星",TagRelation.RELATION_GATHER));
			db.addTagRelation(new TagRelation("王菲","明星",TagRelation.RELATION_GATHER));
			db.addTagRelation(new TagRelation("周迅","明星",TagRelation.RELATION_GATHER));
		db.addTagRelation(new TagRelation("电影","娱乐",TagRelation.RELATION_GATHER));
		db.addTagRelation(new TagRelation("八卦","娱乐",TagRelation.RELATION_GATHER));
			db.addTagRelation(new TagRelation("李亚鹏","八卦",TagRelation.RELATION_GATHER));
			db.addTagRelation(new TagRelation("王菲","八卦",TagRelation.RELATION_GATHER));
			db.addTagRelation(new TagRelation("周迅","八卦",TagRelation.RELATION_GATHER));
		db.addTagRelation(new TagRelation("音乐","娱乐",TagRelation.RELATION_GATHER));
			db.addTagRelation(new TagRelation("王菲","音乐",TagRelation.RELATION_GATHER));
			db.addTagRelation(new TagRelation("周迅","音乐",TagRelation.RELATION_GATHER));
		
		db.addTagRelation(new TagRelation("明星","时尚",TagRelation.RELATION_GATHER));
		db.addTagRelation(new TagRelation("服饰","时尚",TagRelation.RELATION_GATHER));
		db.addTagRelation(new TagRelation("化妆","时尚",TagRelation.RELATION_GATHER));
			db.addTagRelation(new TagRelation("王菲","化妆",TagRelation.RELATION_GATHER));
			db.addTagRelation(new TagRelation("周迅","化妆",TagRelation.RELATION_GATHER));
		db.addTagRelation(new TagRelation("另类","时尚",TagRelation.RELATION_GATHER));
			db.addTagRelation(new TagRelation("王菲","另类",TagRelation.RELATION_GATHER));
		
		db.addTagRelation(new TagRelation("IT硬件","科技",TagRelation.RELATION_GATHER));
		db.addTagRelation(new TagRelation("IT软件","科技",TagRelation.RELATION_GATHER));
		db.addTagRelation(new TagRelation("生物学","科技",TagRelation.RELATION_GATHER));
		db.addTagRelation(new TagRelation("物理学","科技",TagRelation.RELATION_GATHER));

		
		Tag root = new Tag("基因树",Tag.TYPE_INSTANCE_HIDE,Tag.PROPERTY_WHERE);
		db.saveTag(root,false,true);
		db.addTagRelation(new TagRelation("娱乐","基因树",TagRelation.RELATION_GATHER));
		db.addTagRelation(new TagRelation("时尚","基因树",TagRelation.RELATION_GATHER));
		db.addTagRelation(new TagRelation("科技","基因树",TagRelation.RELATION_GATHER));
		db.addTagRelation(new TagRelation("新闻","基因树",TagRelation.RELATION_GATHER));
		db.addTagRelation(new TagRelation("汽车","基因树",TagRelation.RELATION_GATHER));
		db.addTagRelation(new TagRelation("房产","基因树",TagRelation.RELATION_GATHER));
		 
		/*
		//单节点下2000个子节点
		int n=2000;
		Tag t = null;
		for(int i=0;i<n;i++){
			t = new Tag("IT硬件"+i,Tag.TYPE_INSTANCE,Tag.PROPERTY_WHERE);
			db.saveTagAndRelation(t, new TagRelation(t.getName(),"aaaa",TagRelation.RELATION_GATHER));
		}
		*/
		System.out.println("========");
		System.out.println(db.getTag("基因树"));
		System.out.println(db.getTag("王靖雯"));
		//System.out.println(db.getTag("aaaa"));

	}
}
