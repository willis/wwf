package com.mpaike.client.zoie.service;

import java.io.IOException;

import org.apache.lucene.search.DocIdSet;

import proj.zoie.api.ZoieIndexReader;
import proj.zoie.api.indexing.IndexReaderDecorator;

public class MyDoNothingIndexReaderDecorator implements IndexReaderDecorator<MyDoNothingFilterIndexReader> {

	@Override
	public MyDoNothingFilterIndexReader decorate(
			ZoieIndexReader<MyDoNothingFilterIndexReader> indexReader)
			throws IOException {
		// TODO Auto-generated method stub
		return new MyDoNothingFilterIndexReader(indexReader);
	}

	@Override
	public MyDoNothingFilterIndexReader redecorate(
			MyDoNothingFilterIndexReader decorated,
			ZoieIndexReader<MyDoNothingFilterIndexReader> copy,
			boolean withDeletes) throws IOException {
		// TODO Auto-generated method stub
		decorated.updateInnerReader(copy);
	    return decorated;
	}

	@Override
	public void setDeleteSet(MyDoNothingFilterIndexReader reader,
			DocIdSet docIds) {
		// TODO Auto-generated method stub
		
	}


}