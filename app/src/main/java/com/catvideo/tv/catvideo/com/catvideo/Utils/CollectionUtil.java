package com.catvideo.tv.catvideo.com.catvideo.Utils;

import java.util.Collection;

public class CollectionUtil {

	public static <E> boolean isGetValid(Collection<E> collection, int position) {
		if (collection == null) {
			return false;
		}
		if (collection.isEmpty()) {
			return false;
		}
		if (collection.size() <= position) {
			return false;
		}
		return true;
	}
}
