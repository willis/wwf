package com.mpaike.client.zoie.service;

import proj.zoie.impl.indexing.ZoieSystem;

public class IndexEngineBuild{
	
	private static ZoieSystem indexingSystem;
	//private static ZoieVersionFactory versionFactory = new DefaultZoieVersionFactory();

	public static ZoieSystem getIndexingSystem() {
		return indexingSystem;
	}

	public static void setIndexingSystem(ZoieSystem indexingSystem) {
		IndexEngineBuild.indexingSystem = indexingSystem;
	}

}

