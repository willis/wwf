package com.mpaike.client.zoie.service;

import org.apache.lucene.index.FilterIndexReader;
import org.apache.lucene.index.IndexReader;

public class MyDoNothingFilterIndexReader extends FilterIndexReader {
	public MyDoNothingFilterIndexReader(IndexReader reader) {
		super(reader);
	}

	public void updateInnerReader(IndexReader inner) {
		in = inner;
	}
}