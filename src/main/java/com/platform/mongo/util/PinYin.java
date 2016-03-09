package com.platform.mongo.util;

import net.sourceforge.pinyin4j.PinyinHelper;

public class PinYin {
	public static String getPinYinHeadChar(String str) {  
        String convert = "";  
        for (int j = 0; j < str.length(); j++) {  
            char word = str.charAt(j);  
            // 提取汉字的首字母  
            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);  
            if (pinyinArray != null) {  
                convert += pinyinArray[0].charAt(0);  
            } else {  
                convert += word;  
            }  
        }  
        return convert;  
    }
	
	public static void main(String[] args){
		System.out.println(PinYin.getPinYinHeadChar("周末在家").charAt(0));
	}
}
