package com.platform.mongo.s2.impdata;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.platform.io.bean.Company;
import com.platform.io.bean.Material;
import com.platform.mongo.s2.MongoDirver;  
import com.platform.mongo.util.FileUtils;
public class impCompany {
	
	    /**
	     * 导入供应商
	     * @author niyn
	     * @param path
	     * @param dex
	     * @throws Exception 
	     */
		public static void impCompany(String path,String dex) throws Exception{
			String[] files = FileUtils.getFilesFormDirectory(path, dex);
			MongoDirver md = new MongoDirver();
			if (files != null) {
				for (int i = 0; i < files.length; i++) {
					List<Company>   companys  = 	readCompany(path+files[i]);
					int j=164;
					for (Company com : companys) {
						com.setOrg_code(j+"");
						com.setCon_person("中铁");
						com.setCom_addr("中铁");
						com.setCon_way("北京");
						md.addCompany(com);
						j--;
					}
				}
			}
		}
		
		
		public static void main(String[] args) throws Exception {
			impCompany("E:/test/data/xls/","xlsx");
		}
		/**
		 * 读取物资
		 * @author niyn
		 * @param fileName
		 * @return
		 */
		public static List<Company> readCompany(String fileName) {
			List<Company> companys = new ArrayList<Company>();
			try {
				FileInputStream fis = new FileInputStream(fileName);
				Workbook workbook = null;
				if (fileName.toLowerCase().endsWith("xlsx")) {
					workbook = new XSSFWorkbook(fis);
				} else if (fileName.toLowerCase().endsWith("xls")) {
					workbook = new XSSFWorkbook(fis) ;
				} else {
					fis.close();
					return companys;
				}

				int numberOfSheets = workbook.getNumberOfSheets();
				long start = System.currentTimeMillis();
				for (int i = 0; i < numberOfSheets; i++) {
					Sheet sheet = workbook.getSheetAt(i);
					Iterator<Row> rowIterator = sheet.iterator();
					if (rowIterator.hasNext())
						rowIterator.next();
					while (rowIterator.hasNext()) {
						Row row = rowIterator.next();
						Company company = new Company();
						company.setCom_name(row.getCell(0).getStringCellValue().trim());
						companys.add(company);
					}
				}
				long end = System.currentTimeMillis();
				System.out.println("读取Excel循环时间："+(end-start)/1000+"秒");
				fis.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return companys;
		}
}


