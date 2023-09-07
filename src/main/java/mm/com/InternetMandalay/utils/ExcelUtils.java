package mm.com.InternetMandalay.utils;

import mm.com.InternetMandalay.entity.NewCustomer;
import mm.com.InternetMandalay.entity.PaymentRequest;
import mm.com.InternetMandalay.entity.RepairRequest;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Component
public class ExcelUtils {
    public ByteArrayInputStream newCustomersToExcelFile(List<NewCustomer> newCustomers){
        Workbook workbook = new XSSFWorkbook();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            Sheet sheet = workbook.createSheet("New Customer");
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("NAME");
            headerRow.createCell(1).setCellValue("PHONE_NUMBER");
            headerRow.createCell(2).setCellValue("SERVICE_NAME");
            headerRow.createCell(3).setCellValue("ADDRESS");
            headerRow.createCell(4).setCellValue("COLLABORATOR_CODE");
            headerRow.createCell(5).setCellValue("CREATED_AT");
            int rowNum = 1;
            for (NewCustomer newCustomer : newCustomers){
                Row row = sheet.createRow(rowNum);
                row.createCell(0).setCellValue(newCustomer.getName());
                row.createCell(1).setCellValue(newCustomer.getPhoneNumber());
                row.createCell(2).setCellValue(newCustomer.getServiceName());
                row.createCell(3).setCellValue(newCustomer.getAddress());
                row.createCell(4).setCellValue(newCustomer.getCollaboratorCode());
                row.createCell(5).setCellValue(newCustomer.getCreatedAt().toString());
                rowNum++;
            }
            workbook.write(outputStream);
            return new ByteArrayInputStream(outputStream.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                workbook.close();
                outputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public ByteArrayInputStream paymentRequestListToExcelFile(List<PaymentRequest> paymentRequests){
        Workbook workbook = new XSSFWorkbook();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            Sheet sheet = workbook.createSheet("Payment Request");
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("FTTH_ACCOUNT");
            headerRow.createCell(1).setCellValue("CONTACT_phone");
            headerRow.createCell(2).setCellValue("CREATED_AT");
            int rowNum = 1;
            for (PaymentRequest paymentRequest : paymentRequests){
                Row row = sheet.createRow(rowNum);
                row.createCell(0).setCellValue(paymentRequest.getFtthAccount());
                row.createCell(1).setCellValue(paymentRequest.getContactPhone());
                row.createCell(2).setCellValue(paymentRequest.getCreatedAt().toString());
                rowNum++;
            }
            workbook.write(outputStream);
            return new ByteArrayInputStream(outputStream.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                workbook.close();
                outputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public ByteArrayInputStream repairRequestListToExcelFile(List<RepairRequest> repairRequests){
        Workbook workbook = new XSSFWorkbook();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            Sheet sheet = workbook.createSheet("Repair Request");
            Row headRow = sheet.createRow(0);
            headRow.createCell(0).setCellValue("FTTH_ACCOUNT");
            headRow.createCell(1).setCellValue("CONTACT_phone");
            headRow.createCell(2).setCellValue("CREATED_AT");
            int rowNum = 1;
            for (RepairRequest repairRequest : repairRequests){
                Row row = sheet.createRow(rowNum);
                row.createCell(0).setCellValue(repairRequest.getFtthAccount());
                row.createCell(1).setCellValue(repairRequest.getContactPhone());
                row.createCell(2).setCellValue(repairRequest.getCreatedAt().toString());
                rowNum++;
            }
            workbook.write(outputStream);
            return new ByteArrayInputStream(outputStream.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                workbook.close();
                outputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
