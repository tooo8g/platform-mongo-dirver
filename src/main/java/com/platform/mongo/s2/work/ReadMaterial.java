package com.platform.mongo.s2.work;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.platform.io.bean.Material;
import com.platform.mongo.s2.MongoDirver;  
import com.platform.mongo.util.FileUtils;
public class ReadMaterial {
	
	    /**
	     * 读取物资
	     * @author niyn
	     * @param path
	     * @param dex
	     * @throws Exception 
	     */
		public static void readMaterial(String path,String dex) throws Exception{
			String[] files = FileUtils.getFilesFormDirectory(path, dex);
			List<String> dataList=new ArrayList<String>();
			MongoDirver md = new MongoDirver();
			if (files != null) {
				for (int i = 0; i < files.length; i++) {
					List<Material>   materials  = 	readProducts(path+files[i]);
					System.out.println("读取了"+(i+1)+"个文件");
					md.addMaterial(materials);
					System.out.println(i+1+"成功入库！");
				}
			}
		}
		
		
		public static void main(String[] args) throws Exception {
			readMaterial("D:/test/data/xls/","xls");
		}
		/**
		 * 读取物资
		 * @author niyn
		 * @param fileName
		 * @return
		 */
		public static List<Material> readProducts(String fileName) {
			List<Material> materials = new ArrayList<Material>();
			try {
				FileInputStream fis = new FileInputStream(fileName);
				Workbook workbook = null;
				if (fileName.toLowerCase().endsWith("xlsx")) {
					workbook = new XSSFWorkbook(fis);
				} else if (fileName.toLowerCase().endsWith("xls")) {
					workbook = new XSSFWorkbook(fis) ;
				} else {
					fis.close();
					return materials;
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
						Material material = new Material();
						material.setMaterial_name(row.getCell(6).getStringCellValue().trim());
						material.setSpecification(row.getCell(8).getStringCellValue().trim());
						materials.add(material);
					}
				}
				long end = System.currentTimeMillis();
				System.out.println("读取Excel循环时间："+(end-start)/1000+"秒");
				fis.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return materials;
		}
}


