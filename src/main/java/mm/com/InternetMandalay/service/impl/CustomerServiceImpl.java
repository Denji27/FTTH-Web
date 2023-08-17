package mm.com.InternetMandalay.service.impl;

import mm.com.InternetMandalay.entity.Customer;
import mm.com.InternetMandalay.exception.BadRequestException;
import mm.com.InternetMandalay.exception.NotFoundException;
import mm.com.InternetMandalay.repository.CustomerRepo;
import mm.com.InternetMandalay.request.SearchRequest;
import mm.com.InternetMandalay.response.CustomerDTO;
import mm.com.InternetMandalay.service.CustomerService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepo customerRepo;

    @Override
    public void uploadData(InputStream excelFile) throws IOException{
        if (customerRepo.findAll().size() > 0){
            throw new BadRequestException("Please clear the database before import a new data set");
        }

        Workbook workbook = new XSSFWorkbook(excelFile);
        Sheet sheet = workbook.getSheetAt(0);
        for (Row row : sheet){
            if (row.getRowNum() == 0){
                continue;
            }
            Customer customer = Customer.builder()
                    .ftthAccount(row.getCell(0).getStringCellValue())
                    .customerName(row.getCell(1).getStringCellValue())
                    .customerAddress(row.getCell(2).getStringCellValue())
                    .contactPhone(row.getCell(3).getStringCellValue())
                    .productCode(row.getCell(4).getStringCellValue())
                    .monthAdv(row.getCell(5).getStringCellValue())
                    .mgt(row.getCell(6).getStringCellValue())
                    .d2dName(row.getCell(7).getStringCellValue())
                    .d2dPhoneNumber(row.getCell(8).getStringCellValue())
                    .billBlock(row.getCell(9).getStringCellValue())
                    .build();
            customerRepo.save(customer);
        }
        workbook.close();
    }

    @Override
    public CustomerDTO search(SearchRequest searchRequest) {
        if(searchRequest.getFtthAccount().isBlank() & searchRequest.getContactPhone().isBlank()){
            throw new NotFoundException("You haven't entered your account or phone number!");
        }
        if(searchRequest.getFtthAccount().isBlank() & !searchRequest.getContactPhone().isBlank()){
            Customer customer = customerRepo.findCustomerByContactPhone(searchRequest.getContactPhone());
            if (customer == null){
                throw new NotFoundException("Your account is not exist");
            }
            CustomerDTO customerDto = CustomerDTO.builder()
                    .ftthAccount(customer.getFtthAccount())
                    .customerName(customer.getCustomerName())
                    .customerAddress(customer.getCustomerAddress())
                    .contactPhone(customer.getContactPhone())
                    .productCode(customer.getProductCode())
                    .monthAdv(customer.getMonthAdv())
                    .mgt(customer.getMgt())
                    .d2dName(customer.getD2dName())
                    .d2dPhoneNumber(customer.getD2dPhoneNumber())
                    .billBlock(customer.getBillBlock())
                    .build();
            return customerDto;
        }
        if (!searchRequest.getFtthAccount().isBlank() & searchRequest.getContactPhone().isBlank()){
            Customer customer = customerRepo.findCustomerByFtthAccount(searchRequest.getFtthAccount());
            if (customer == null){
                throw new NotFoundException("Your account is not exist");
            }
            CustomerDTO customerDto = CustomerDTO.builder()
                    .ftthAccount(customer.getFtthAccount())
                    .customerName(customer.getCustomerName())
                    .customerAddress(customer.getCustomerAddress())
                    .contactPhone(customer.getContactPhone())
                    .productCode(customer.getProductCode())
                    .monthAdv(customer.getMonthAdv())
                    .mgt(customer.getMgt())
                    .d2dName(customer.getD2dName())
                    .d2dPhoneNumber(customer.getD2dPhoneNumber())
                    .billBlock(customer.getBillBlock())
                    .build();
            return customerDto;
        }
        Customer customer = customerRepo.findCustomerByFtthAccountAndContactPhone(searchRequest.getFtthAccount(), searchRequest.getContactPhone());
        if (customer == null){
            throw new NotFoundException("Your account is not exist");
        }
        CustomerDTO customerDto = CustomerDTO.builder()
                .ftthAccount(customer.getFtthAccount())
                .customerName(customer.getCustomerName())
                .customerAddress(customer.getCustomerAddress())
                .contactPhone(customer.getContactPhone())
                .productCode(customer.getProductCode())
                .monthAdv(customer.getMonthAdv())
                .mgt(customer.getMgt())
                .d2dName(customer.getD2dName())
                .d2dPhoneNumber(customer.getD2dPhoneNumber())
                .billBlock(customer.getBillBlock())
                .build();
        return customerDto;
    }

    @Override
    public void resetCustomerData() {
        customerRepo.deleteAll();
    }

}
