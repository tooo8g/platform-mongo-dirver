package com.platform.io;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.platform.io.bean.Certification;
import com.platform.io.bean.Certification_Detail;
import com.platform.io.bean.Product;
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
				Certification cert = null;
				if (rowIterator.hasNext())
					rowIterator.next();
				while (rowIterator.hasNext()) {
					Row row = rowIterator.next();
					if (row.getCell(4) != null
							&& !row.getCell(4).getStringCellValue().equals("")) {
						cert = new Certification();
						cert.setProduct_range(row.getCell(0)
								.getStringCellValue());
						cert.setCompany_name(row.getCell(1)
								.getStringCellValue());
						cert.setCert_unit(row.getCell(2).getStringCellValue());
						cert.setCert_name(row.getCell(3).getStringCellValue());
						cert.setCert_num(row.getCell(4).getStringCellValue());
						cert.setIssue_organization(row.getCell(5)
								.getStringCellValue());
						cert.setCert_standards(row.getCell(6)
								.getStringCellValue());
						cert.setPublish_date(row.getCell(7)
								.getStringCellValue());
						cert.setValid_date(row.getCell(8).getStringCellValue());
						cert.setCert_status(row.getCell(9).getStringCellValue());
						cert.setCert_status_id(row.getCell(10)
								.getStringCellValue());
						cert.setMount_code(row.getCell(11).getStringCellValue());
						cert.setProject_code(row.getCell(12)
								.getStringCellValue());
						cert.setOrganization_code(row.getCell(13)
								.getStringCellValue());
						cert.setReg_addr(row.getCell(14).getStringCellValue());
						cert.setPost_code(row.getCell(15).getStringCellValue());
						cert.setProduct_kind(row.getCell(16)
								.getStringCellValue());
						cert.setProduct_addr(row.getCell(17)
								.getStringCellValue());
						cert.setNotification_number(row.getCell(18)
								.getStringCellValue());
						cert.setCert_condition(row.getCell(19)
								.getStringCellValue());
						cert.setCert_expand(row.getCell(20)
								.getStringCellValue());
						cert.setRemark(row.getCell(21).getStringCellValue());
						List<Certification_Detail> details = new ArrayList<Certification_Detail>();
						Certification_Detail detail = new Certification_Detail();
						detail.setProduct_code(row.getCell(22)
								.getStringCellValue());
						detail.setSpecification(row.getCell(23)
								.getStringCellValue());
						detail.setSpecification_status(row.getCell(24)
								.getStringCellValue());
						detail.setProduct_property_name(row.getCell(25)
								.getStringCellValue());
						detail.setExpand(row.getCell(26).getStringCellValue());
						details.add(detail);
						cert.setCert_detail(details);
						certification.add(cert);
					} else {
						Certification_Detail detail = new Certification_Detail();
						detail.setProduct_code(row.getCell(22)
								.getStringCellValue());
						detail.setSpecification(row.getCell(23)
								.getStringCellValue());
						detail.setSpecification_status(row.getCell(24)
								.getStringCellValue());
						detail.setProduct_property_name(row.getCell(25)
								.getStringCellValue());
						detail.setExpand(row.getCell(26).getStringCellValue());
						cert.addCert_detail(detail);
					}
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
				if (rowIterator.hasNext())
					rowIterator.next();
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

	public static List<Certification> readCompany_name(String fileName) {
		List<Certification> list = new ArrayList<Certification>();
		try {
			FileInputStream fis = new FileInputStream(fileName);
			Workbook workbook = null;
			if (fileName.toLowerCase().endsWith("xlsx")) {
				workbook = new XSSFWorkbook(fis);
			} else if (fileName.toLowerCase().endsWith("xls")) {
				workbook = new HSSFWorkbook(fis);
			} else {
				fis.close();
				return list;
			}

			// int numberOfSheets = workbook.getNumberOfSheets();

			// for (int i = 0; i < numberOfSheets; i++) {
			Sheet sheet = workbook.getSheetAt(1);
			Iterator<Row> rowIterator = sheet.iterator();
			if (rowIterator.hasNext())
				rowIterator.next();
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				Certification certification = new Certification();
				String name = row.getCell(0).getStringCellValue().trim();
				certification.setCompany_name(name);
				list.add(certification);
			}
			// }
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public static List<Product> readProduct(String fileName) {
		List<Product> list = new ArrayList<Product>();
		try {
			FileInputStream fis = new FileInputStream(fileName);
			Workbook workbook = null;
			if (fileName.toLowerCase().endsWith("xlsx")) {
				workbook = new XSSFWorkbook(fis);
			} else if (fileName.toLowerCase().endsWith("xls")) {
				workbook = new HSSFWorkbook(fis);
			} else {
				fis.close();
				return list;
			}

			Sheet sheet = workbook.getSheetAt(1);
			Iterator<Row> rowIterator = sheet.iterator();
			if (rowIterator.hasNext())
				rowIterator.next();
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				Product product = new Product();
//				Cell cell = row.getCell(0);
//				// if(cell!=null){
				String company = row.getCell(0).getStringCellValue().trim();
				product.setCompany(company);// 供应商
				String cert_num = row.getCell(1).getStringCellValue().trim();
				product.setCert_num(cert_num);// 证书编号
				String product_name = row.getCell(9).getStringCellValue()
						.trim();
				product.setProduct_name(product_name);// 产品名称
				String specification = row.getCell(10).getStringCellValue()
						.trim();
				product.setSpecification(specification);// 规格型号
				String cert_standards = row.getCell(11).getStringCellValue()
						.trim();
				product.setCert_standards(cert_standards);// 执行标准
				// }

				list.add(product);
			}
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public static void main(String[] args) {
		// List<Certification> certs = MSIO
		// .readCertification("d://test//certification.xls");
		// Certification cert = certs.get(certs.size()-1);
		// System.out.println(cert);
		// for (Certification_Detail d : cert.getCert_detail()) {
		// System.out.println(d);
		// }
		List<Certification> stands = MSIO
				.readCompany_name("d://test//供应商综合信息.xlsx");

	}

}
