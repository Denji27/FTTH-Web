package mm.com.InternetMandalay.service.impl;

import mm.com.InternetMandalay.entity.Customer;
import mm.com.InternetMandalay.exception.BadRequestException;
import mm.com.InternetMandalay.exception.NotFoundException;
import mm.com.InternetMandalay.repository.CustomerRepo;
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
import java.util.ArrayList;
import java.util.List;

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
    public List<CustomerDTO> search(String contactPhone, String ftthAccount) {
        List<CustomerDTO> customerDTOList = new ArrayList<>();
        if(ftthAccount.isBlank() & contactPhone.isBlank()){
            throw new NotFoundException("You haven't entered your account or phone number!");
        }
        if(ftthAccount.isBlank() & !contactPhone.isBlank()){
            List<Customer> customers = customerRepo.findCustomerByContactPhone(contactPhone);
            if (customers.size() == 0){
                throw new NotFoundException("Your account is not exist");
            }
            for (Customer customer : customers){
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
                customerDTOList.add(customerDto);
            }

            return customerDTOList;
        }
        if (!ftthAccount.isBlank() & contactPhone.isBlank()){
            List<Customer> customers = customerRepo.findCustomerByFtthAccount(ftthAccount);
            if (customers.size() == 0){
                throw new NotFoundException("Your account is not exist");
            }
            for (Customer customer : customers){
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
                customerDTOList.add(customerDto);
            }
            return customerDTOList;
        }
        List<Customer> customers = customerRepo.findCustomerByFtthAccountAndContactPhone(ftthAccount, contactPhone);
        if (customers.size() == 0){
            throw new NotFoundException("Your account is not exist");
        }
        for (Customer customer : customers){
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
            customerDTOList.add(customerDto);
        }
        return customerDTOList;
    }

    @Override
    public void resetCustomerData() {
        customerRepo.deleteAll();
    }

}
