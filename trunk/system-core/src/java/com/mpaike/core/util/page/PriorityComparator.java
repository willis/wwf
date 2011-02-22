package com.mpaike.core.util.page;

import java.util.Comparator;

public class PriorityComparator implements Comparator<PriorityInterface> {
	public static final PriorityComparator INSTANCE = new PriorityComparator();

	public int compare(PriorityInterface o1, PriorityInterface o2) {
		Integer v1 = o1.getPriority();
		Integer v2 = o2.getPriority();
		Long id1 = o1.getId();
		Long id2 = o2.getId();
		if (id1 != null && id2 != null && id1.equals(id2)) {
			return 0;
		} else if (v1 == null) {
			return 1;
		} else if (v2 == null) {
			return -1;
		} else if (v1 > v2) {
			return 1;
		} else if (v1 < v2) {
			return -1;
		} else if (id1 == null) {
			return 1;
		} else if (id2 == null) {
			return -1;
		} else if (id1 > id2) {
			return 1;
		} else if (id1 < id2) {
			return -1;
		} else {
			return 0;
		}
	}
}
