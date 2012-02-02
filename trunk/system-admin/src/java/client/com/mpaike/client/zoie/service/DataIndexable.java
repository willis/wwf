package com.mpaike.client.zoie.service;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;

import proj.zoie.api.indexing.ZoieIndexable;

import com.mpaike.image.model.Picture;

public class DataIndexable implements ZoieIndexable {
	
	public static final String DELETE_STATUS = "_MARKED_FOR_DELETE";
	public static final String SKIP_STATUS = "_MARKED_FOR_SKIP";
	
	private Doc _data;
	private String deleted;
	
	public DataIndexable(Picture temp) {
		_data = createDoc(temp);
	}
	public IndexingReq[] buildIndexingReqs() {
		
		// documents
		Document doc = new Document();
		doc.add(new Field(QueryFactory.PICTURE_ID, _data.getPictureId(), Store.YES, Index.NOT_ANALYZED));
		doc.add(new Field(QueryFactory.PICTURE_EXIFVERSION, _data.getExifVersion(), Store.YES, Index.NO));
		doc.add(new Field(QueryFactory.PICTURE_FILENAME, _data.getFilename(), Store.YES, Index.NO));
		doc.add(new Field(QueryFactory.PICTURE_FILESIZE, _data.getFileSize(), Store.YES, Index.NO));
		doc.add(new Field(QueryFactory.PICTURE_FILETYPE, _data.getFileType(), Store.YES, Index.NO));
		doc.add(new Field(QueryFactory.PICTURE_SRCHEIGHT, _data.getSrcHeight(), Store.YES, Index.NO));
		doc.add(new Field(QueryFactory.PICTURE_SRCWIDTH, _data.getSrcWidth(), Store.YES, Index.NO));
		doc.add(new Field(QueryFactory.PICTURE_URL, _data.getUrl(), Store.YES, Index.NO));
		return new IndexingReq[] { new IndexingReq(doc) };
	}

	
	public boolean isDeleted() {
		return DELETE_STATUS.equals(deleted);
	}

	public boolean isSkip() {
		return SKIP_STATUS.equals(deleted);
	}



	@Override
	public long getUID() {
		return Long.valueOf(_data.getPictureId());
	}
	
	public static Doc createDoc(Picture doc){
		Doc dc = new Doc();
		dc.setExifVersion(doc.getExifVersion());
		dc.setFilename(doc.getFilename());
		dc.setFileSize(doc.getFileSize());
		dc.setFileType(doc.getFileType());
		dc.setUrl(doc.getUrl());
		dc.setSrcHeight(String.valueOf(doc.getSrcHeight()));
		dc.setSrcWidth(String.valueOf(doc.getSrcWidth()));
		dc.setPictureId(String.valueOf(doc.getId()));
		
		return dc;
	}


}
