package com.zd.lbsx.utils;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;



public class FileUtils {
	
	public static final String[] FILE_EXTENSIONS= {"txt","doc","docx","pdf"};
	
	public static HashSet<String> fileSet;
	static{
		fileSet=new HashSet<String>(Arrays.asList(FILE_EXTENSIONS));
	}
	
	
	public static boolean isStudyFile(File file){
		String ext=getFileExtension(file);
		return fileSet.contains(ext);
		
	}
	
    public static String getFileExtension(File f) {
        if (f != null) {
            String filename = f.getName();
            int i = filename.lastIndexOf('.');
            if (i > 0 && i < filename.length() - 1) {
                return filename.substring(i + 1).toLowerCase();
            }
        }
        return null;
    }
}
