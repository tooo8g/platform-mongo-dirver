package com.platform.io.bean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.platform.mongo.util.Constant;

public class Words {

	/**
	 * w/x * n = 此条记录的总权重 (w=权重值 x=匹配词数 n=检索词数)
	 */
	public static List<Word> make(List<Word> wordList) {
		if(wordList.size()==1){
			wordList.get(0).setW(new BigDecimal(1.1));
			return wordList;
		}
		// 得到总权重
		BigDecimal w_total = new BigDecimal(Constant.w).divide(
				new BigDecimal(Constant.x), 4, BigDecimal.ROUND_HALF_EVEN)
				.multiply(new BigDecimal(wordList.size()));

		// 得到词频比例
		BigDecimal c_total = new BigDecimal(0.0);
		BigDecimal hz_total = new BigDecimal(0.0);
		for (Word word : wordList) {
			c_total = c_total.add(word.getCount());
			// c_total += word.getCount();
		}

		for (Word word : wordList) {
			BigDecimal whz = word.getCount().divide(c_total, 4,
					BigDecimal.ROUND_HALF_EVEN);
			BigDecimal hz = new BigDecimal(1.0).subtract(whz);
			hz_total = hz_total.add(hz);
			word.setPer(whz);
			word.setHz_per(hz);
		}

		for (Word word : wordList) {
			BigDecimal w = w_total.multiply(word.getHz_per().divide(hz_total,
					4, BigDecimal.ROUND_HALF_EVEN));
			word.setW(w.multiply(w).pow(3).divide(new BigDecimal(0.18),4,BigDecimal.ROUND_HALF_EVEN));
		}

		/*
		 * // 生成一个升序一个降序的集合 List<Word> wordList_copy = new
		 * ArrayList<Word>(wordList.size()); wordList_copy.addAll(wordList);
		 * Collections.sort(wordList, new Comparator<Word>() {
		 * 
		 * @Override public int compare(Word o1, Word o2) { return (o1.getPer()
		 * > o2.getPer() ? 1 : -1); } }); Collections.sort(wordList_copy, new
		 * Comparator<Word>() {
		 * 
		 * @Override public int compare(Word o1, Word o2) { return (o1.getPer()
		 * < o2.getPer() ? 1 : -1); } }); // 按照倒置词频比例(80% 15% 3% 2% --> 2% 3%
		 * 15% 80%)的方式给权重赋值 for (int i = 0; i < wordList.size(); i++) { Word
		 * word = wordList.get(i); word.setW(wordList_copy.get(i).getPer() *
		 * w_total); }
		 */

		return wordList;
	}

	public static void main(String[] args) {
		List<Word> wordList = new ArrayList<Word>();
		wordList.add(new Word("好", "15"));
		wordList.add(new Word("你", "80"));
		wordList.add(new Word("京", "2"));
		wordList.add(new Word("北", "3"));

		wordList = Words.make(wordList);
		for (Word word : wordList)
			System.out.println(word);
	}

}
