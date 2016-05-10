package com.platform.mongo.s2.work;

import java.io.FileOutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import com.platform.io.bean.Word;
import com.platform.io.bean.Words;
import com.platform.mongo.s2.MongoDirver;
import com.platform.mongo.s2.dao.MongoDao;

public class CompareWords {
	/**
	 * 比对关键字
	 * 
	 * @author niyn
	 * @param fileName
	 * @param outfilename
	 * @return
	 */
	public static List<String> compare(String fileName, String outfilepath) {
		CsvReader r = null;
		CsvWriter w = null;
		List<String> sp = new ArrayList<String>();
		MongoDao md = new MongoDao();
		try {
			r = new CsvReader(fileName, ',', Charset.forName("UTF-8"));
			w = new CsvWriter(new FileOutputStream(outfilepath), ',',
					Charset.forName("UTF-8"));
			// r.readHeaders();//是否读取表头
			while (r.readRecord()) {
				String csvs[] = r.getValues();// 参数
				List<Word> words = new ArrayList<Word>();
				for (int i = 1; i < csvs.length; i++) {
					if (csvs[i].contains("#")) {
						System.out.println("===" + csvs[i]);
						String[] params = csvs[i].split("#");
						if (!params[1].equals("0"))
							words.add(new Word(params[0], params[1]));
					}
				}
				List<Word> wordList = Words.make(words);
				long b = System.currentTimeMillis();
				List<Document> resultList = md.matchWord("test",
						"distinctmaterial", wordList, "names", null, 0, 10000)
						.into(new ArrayList<Document>());
				long e = System.currentTimeMillis();
				System.out.println("用时" + ((e - b) / 1000) + "秒,取出"
						+ resultList.size() + "条");
				String[] resultcsv = new String[2];
				if (resultList.size() > 0) {
					resultcsv[0] = csvs[0];
					resultcsv[1] = resultList.get(0).getString("names");
					// resultcsv[2] =
					// resultList.get(0).getString("specification");
					w.writeRecord(resultcsv);
					resultcsv[0] = "";
					for (int j = 1; j < resultList.size(); j++) {
						resultcsv[1] = resultList.get(j).getString("names");
						// resultcsv[2] =
						// resultList.get(j).getString("specification");
						w.writeRecord(resultcsv);
					}
				} else {
					resultcsv[0] = csvs[0];
					resultcsv[1] = "";
					// resultcsv[2] = "";
					w.writeRecord(resultcsv);
				}
				w.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (r != null)
				r.close();
			if (w != null)
				w.close();
		}
		return sp;
	}

	/**
	 * 生成词频
	 * 
	 * @author niyn
	 * @param fileName
	 * @param outfilename
	 * @return
	 */
	public static List<String> createS(String fileName, String outfilename) {
		CsvReader r = null;
		CsvWriter w = null;
		List<String> sp = new ArrayList<String>();
		MongoDirver md = new MongoDirver();
		int count = 0;// 出现次数
		try {
			r = new CsvReader(fileName, ',', Charset.forName("UTF-8"));
			w = new CsvWriter(new FileOutputStream(outfilename), ',',
					Charset.forName("UTF-8"));
			// r.readHeaders();//是否读取表头
			while (r.readRecord()) {
				String csvs[] = r.getValues();// 参数
				for (int i = 1; i < csvs.length; i++) {
					if (csvs[i].length() > 1) {
						sp.add(csvs[i]);
						count = md.queryMaterialCount(csvs[i]);
						csvs[i] = csvs[i] + "#" + count;
					}
				}
				w.writeRecord(csvs);
				w.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (r != null)
				r.close();
			if (w != null)
				w.close();
		}
		return sp;
	}

	public static void main(String[] args) {
		// 词频
		compare("D://test//word//vulgo//vulgo_counts_1.csv",
				"D://test//word//vulgo//vulgo_result_1.csv");
		// createS("D://test//word//vulgo//vulgo_split_1.csv",
		// "D://test//word//vulgo//vulgo_counts_1.csv");
	}

}
