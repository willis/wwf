package com.mpaike.client.zoie.service;

import com.mpaike.image.model.Picture;

import proj.zoie.api.indexing.ZoieIndexable;
import proj.zoie.api.indexing.ZoieIndexableInterpreter;


public class DataIndexableInterpreter implements ZoieIndexableInterpreter<Picture> {
	@Override
	public ZoieIndexable convertAndInterpret(Picture temp) {
		return new DataIndexable(temp);
	}
}