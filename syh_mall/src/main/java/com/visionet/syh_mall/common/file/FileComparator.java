package com.visionet.syh_mall.common.file;

import java.io.File;

import java.util.Comparator;

/**
 * @author visionet
 */
public class FileComparator implements Comparator<File> {

	public int compare(File file1, File file2) {
		return file1.getName().compareTo(file2.getName());
	}

}