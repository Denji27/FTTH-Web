package mm.com.InternetMandalay.service.impl;

import mm.com.InternetMandalay.entity.Customer;
import mm.com.InternetMandalay.exception.NotFoundException;
import mm.com.InternetMandalay.repository.CustomerRepo;
import mm.com.InternetMandalay.request.SearchRequest;
import mm.com.InternetMandalay.service.CustomerService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepo customerRepo;

    @Override
    @CacheEvict(value = "Customer")
    public void uploadData(InputStream excelFile) throws IOException{
        Workbook workbook = new XSSFWorkbook(excelFile);
        Sheet sheet = workbook.getSheetAt(0);
        for (Row row : sheet){
            if (row.getRowNum() == 0){
                continue;
            }
            Customer customer = Customer.builder()
                    .customerId(row.getCell(0).getStringCellValue())
                    .name(row.getCell(1).getStringCellValue())
                    .account(row.getCell(2).getStringCellValue())
                    .phoneNumber(row.getCell(3).getStringCellValue())
                    .serviceName(row.getCell(4).getStringCellValue())
                    .numberOfPaidMonths((int) row.getCell(5).getNumericCellValue())
                    .extensionDate((Date) row.getCell(6).getDateCellValue())
                    .internetBlockingDate((Date) row.getCell(7).getDateCellValue())
                    .build();
            customerRepo.save(customer);
        }
        workbook.close();
    }

    @Override
    @Cacheable(value = "Customer")
    public Customer search(SearchRequest searchRequest) {
        if(searchRequest.getAccount().isBlank() & searchRequest.getPhoneNumber().isBlank()){
            throw new NotFoundException("You haven't entered your account or phone number!");
        }
        if(searchRequest.getAccount().isBlank() & !searchRequest.getPhoneNumber().isBlank()){
            Customer customer = customerRepo.findCustomerByPhoneNumber(searchRequest.getPhoneNumber());
            if (customer == null){
                throw new NotFoundException("Your account is not exist");
            }
            return customer;
        }
        if (!searchRequest.getAccount().isBlank() & searchRequest.getPhoneNumber().isBlank()){
            Customer customer = customerRepo.findCustomerByAccount(searchRequest.getAccount());
            if (customer == null){
                throw new NotFoundException("Your account is not exist");
            }
            return customer;
        }
        Customer customer = customerRepo.findCustomerByAccountAndPhoneNumber(searchRequest.getAccount(), searchRequest.getPhoneNumber());
        if (customer == null){
            throw new NotFoundException("Your account is not exist");
        }
        return customer;
    }

    @Override
    @CacheEvict(value = "Customer")
    public void resetCustomerData() {
        customerRepo.deleteAll();
    }

}
