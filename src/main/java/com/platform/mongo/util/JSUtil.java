package com.platform.mongo.util;

import java.util.ArrayList;
import java.util.List;

import com.platform.io.bean.Word;

public class JSUtil {

	/**
	 * 
	 * @auth zhanglei
	 * @param words
	 * @param field
	 * @return
	 */
	public static String wordMatchJS(List<Word> words, String field) {
		StringBuilder js = new StringBuilder();
		js.append("function(){");
		js.append("var w = 0;");
		for (Word word : words) {
			js.append("if(this.").append(field).append(".match(\"^.*")
					.append(word.getStr()).append(".*$\")) w+=")
					.append(word.getW()).append(";");
		}
		js.append("if(w>1.0) return 1;else return 0;");
		js.append("}");
		return js.toString();
	}

	/**
	 * @auth zhanglei
	 * @param args
	 */
	public static void main(String[] args) {
		List<Word> words = new ArrayList<Word>();
		words.add(new Word("大"));
		words.add(new Word("联合1"));
		words.add(new Word("及北"));
		System.out.println(JSUtil.wordMatchJS(words, "text"));
	}

}
