package com.mpaike.client.zoie.service;

import java.util.Comparator;

public class ComparatorHelper  implements Comparator<String>{

	@Override
	public int compare(String a, String b) {

		return a.compareTo(b);
	}

}
