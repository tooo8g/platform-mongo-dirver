package com.platform.io;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FILEIO {
	  
	    /** 
	     * 将文件转成byte[] 
	     *  
	     * @param filename 
	     * @return 
	     * @throws IOException 
	     */  
	    public static byte[] toByteArray(String filename) throws IOException {  
	  
	        File f = new File(filename);  
	        if (!f.exists()) {  
	            throw new FileNotFoundException(filename);  
	        }  
	  
	        ByteArrayOutputStream bos = new ByteArrayOutputStream((int) f.length());  
	        BufferedInputStream in = null;  
	        try {  
	            in = new BufferedInputStream(new FileInputStream(f));  
	            int buf_size = 1024;  
	            byte[] buffer = new byte[buf_size];  
	            int len = 0;  
	            while (-1 != (len = in.read(buffer, 0, buf_size))) {  
	                bos.write(buffer, 0, len);  
	            }  
	            return bos.toByteArray();  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	            throw e;  
	        } finally {  
	            try {  
	                in.close();  
	            } catch (IOException e) {  
	                e.printStackTrace();  
	            }  
	            bos.close();  
	        }  
	    }  
}