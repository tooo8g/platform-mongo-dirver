package query;

import java.io.FileOutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import com.platform.mongo.s2.MongoDirver;

public class Test {
	/**
	 * 词频
	 * @author niyn
	 * @param fileName
	 * @param outfilename
	 * @return
	 */
	public static List<String> readS(String fileName,String outfilename) {
		CsvReader r = null;
		CsvWriter w = null;
		List<String> sp = new ArrayList<String>();
		MongoDirver md = new MongoDirver();
		String count = "";//出现次数
		try {
			r = new CsvReader(fileName, ',', Charset.forName("UTF-8"));
			w = new CsvWriter(new FileOutputStream(outfilename), ',',Charset.forName("UTF-8"));
//			r.readHeaders();//是否读取表头
			while (r.readRecord()) {
				String csvs[] = r.getValues();//参数
				for (int i = 1; i < csvs.length; i++) {
					if(csvs[i].length()>1){
						sp.add(csvs[i]);
						count = md.queryC(csvs[i]);
						csvs[i] = csvs[i]+"#"+count;
					}
				}
				w.writeRecord(csvs);
				w.flush();
				for (int i = 0; i < csvs.length; i++) {
					System.out.println(csvs[i]);
				}
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
//		词频
		String outfilename = "E:/test/rr.csv";
		readS("E:/test/test.csv",outfilename);
	}
}
