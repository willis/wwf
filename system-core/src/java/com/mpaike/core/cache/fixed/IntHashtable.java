package com.mpaike.core.cache.fixed;

/**
 * Simplified hashtable implementation that uses int keys instead of Objects.
 * All methods are unsynchronized, so not natively thread safe.<p>
 *
 * The implementation is inspired by the Hashtable class, but we tried not to
 * rip it off.
 */
public class IntHashtable {

    Entry [] entries;

    public IntHashtable(int initialSize) {
    }

    /**
     * Hashtable collision list.
     */
    private static class Entry {
        int hash;
	int key;
	Object value;
	Entry next;
    }
}
