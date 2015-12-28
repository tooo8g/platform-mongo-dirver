package com.platform.io;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.platform.io.bean.Certification;
import com.platform.io.bean.Standardization;

public class MSIO {

	public static List<Certification> readCertification(String fileName) {
		List<Certification> certification = new ArrayList<Certification>();
		try {
			FileInputStream fis = new FileInputStream(fileName);
			Workbook workbook = null;
			if (fileName.toLowerCase().endsWith("xlsx")) {
				workbook = new XSSFWorkbook(fis);
			} else if (fileName.toLowerCase().endsWith("xls")) {
				workbook = new HSSFWorkbook(fis);
			} else {
				fis.close();
				return certification;
			}

			int numberOfSheets = workbook.getNumberOfSheets();

			for (int i = 0; i < numberOfSheets; i++) {
				Sheet sheet = workbook.getSheetAt(i);
				Iterator<Row> rowIterator = sheet.iterator();
				while (rowIterator.hasNext()) {
					Row row = rowIterator.next();
					Certification cert = new Certification();
					System.out.println(row.getCell(23).getRowIndex());
				}
			}
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return certification;
	}

	public static List<Standardization> readStandardizations(String fileName) {
		List<Standardization> standardizations = new ArrayList<Standardization>();
		try {
			FileInputStream fis = new FileInputStream(fileName);
			Workbook workbook = null;
			if (fileName.toLowerCase().endsWith("xlsx")) {
				workbook = new XSSFWorkbook(fis);
			} else if (fileName.toLowerCase().endsWith("xls")) {
				workbook = new HSSFWorkbook(fis);
			} else {
				fis.close();
				return standardizations;
			}

			int numberOfSheets = workbook.getNumberOfSheets();

			for (int i = 0; i < numberOfSheets; i++) {
				Sheet sheet = workbook.getSheetAt(i);
				Iterator<Row> rowIterator = sheet.iterator();
				while (rowIterator.hasNext()) {
					Row row = rowIterator.next();
					Standardization standard = new Standardization();
					standard.setStandard_group(row.getCell(0)
							.getStringCellValue().trim());
					standard.setStandard_id(row.getCell(1).getStringCellValue()
							.trim());
					standard.setStandard_name(row.getCell(2)
							.getStringCellValue().trim());
					standard.setReplace_id(row.getCell(3).getStringCellValue()
							.trim());
					standard.setPublish_date(row.getCell(4)
							.getStringCellValue().trim());
					standard.setExecute_date(row.getCell(5)
							.getStringCellValue().trim());
					standard.setStandard_status(row.getCell(6)
							.getStringCellValue().trim());
					standard.setSpecial_subject(row.getCell(7)
							.getStringCellValue().trim());
					standardizations.add(standard);
				}
			}
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return standardizations;
	}

	public static void main(String[] args) {
		MSIO.readCertification("d://test//certification.xls");
		// List<Standardization> stands = MSIO
		// .readStandardizations("d://test//standards.xls");
		// for (Standardization stand : stands) {
		// System.out.println(stand);
		// }
	}

}
