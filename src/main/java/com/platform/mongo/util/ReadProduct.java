package com.platform.mongo.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.platform.io.bean.Product;

public class ReadProduct {
		
	public static void readProduct(String fileName){
        List<String> dataList=new ArrayList<String>();
        List<Product> products  = readProducts(fileName);
        dataList.add("标准名称,唯一性");
        for (Product p : products) {
        	dataList.add(p.getProduct_name()+","+p.getSpecification());
		}
        boolean isSuccess=CSVUtils.exportCsv(new File("E:/test.csv"), dataList);
        System.out.println(isSuccess);
	}
	
	public static void main(String[] args) {
		readProduct("E:\\test\\qq.xls");
	}
    
	/**
	 * 读取产品表示代码产品表
	 * @author niyn
	 * @param fileName
	 * @return
	 */
	public static List<Product> readProducts(String fileName) {
		List<Product> products = new ArrayList<Product>();
		try {
			FileInputStream fis = new FileInputStream(fileName);
			Workbook workbook = null;
			if (fileName.toLowerCase().endsWith("xlsx")) {
				workbook = new XSSFWorkbook(fis);
			} else if (fileName.toLowerCase().endsWith("xls")) {
				workbook = new HSSFWorkbook(fis);
			} else {
				fis.close();
				return products;
			}

			int numberOfSheets = workbook.getNumberOfSheets();

			for (int i = 0; i < numberOfSheets; i++) {
				Sheet sheet = workbook.getSheetAt(i);
				Iterator<Row> rowIterator = sheet.iterator();
				if (rowIterator.hasNext())
					rowIterator.next();
				while (rowIterator.hasNext()) {
					Row row = rowIterator.next();
					Product product = new Product();
					product.setProduct_name(row.getCell(3).getStringCellValue().trim());
					product.setSpecification(row.getCell(5).getStringCellValue().trim());
					products.add(product);
				}
			}
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return products;
	}
}
