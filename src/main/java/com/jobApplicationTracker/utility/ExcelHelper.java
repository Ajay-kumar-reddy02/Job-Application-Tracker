package com.jobApplicationTracker.utility;

import com.jobApplicationTracker.entity.ApplicationTrackerEntity;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelHelper {

    public static ByteArrayInputStream getJobDetailsInExcel(List<ApplicationTrackerEntity> applicationTrackerEntity) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        String[] columns = {"Id", "Company Applied To", "Job Designation", "Link", "Applied Through",
        "Resume Name", "Applied Date", "Update From Company", "First Follow Up", "Second Follow Up",
        "Final Update", "Notes"};
        Sheet jobDetails = workbook.createSheet("Job Details");
        Row row = jobDetails.createRow(0);
        for(int i=0; i < columns.length; i++){
            row.createCell(i).setCellValue(columns[i]);
        }
        int rowNumber = 1;
        for(ApplicationTrackerEntity a: applicationTrackerEntity){
            Row row1 = jobDetails.createRow(rowNumber++);
            row1.createCell(0).setCellValue(a.getId());
            row1.createCell(1).setCellValue(a.getCompanyAppliedTo());
            row1.createCell(2).setCellValue(a.getJobDesignation());
            row1.createCell(3).setCellValue(a.getLink());
            row1.createCell(4).setCellValue(a.getAppliedThrough());
            row1.createCell(5).setCellValue(a.getResumeName());
            row1.createCell(6).setCellValue(a.getAppliedDate());
            row1.createCell(7).setCellValue(a.getUpdateFromCompany().name());
            row1.createCell(8).setCellValue(a.getFirstFollowUp());
            row1.createCell(9).setCellValue(a.getSecondFollowUp());
            row1.createCell(10).setCellValue(a.getFinalUpdate());
            row1.createCell(11).setCellValue(a.getNotes());

        }

        workbook.write(byteArrayOutputStream);
        workbook.close();
        return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
    }
}
