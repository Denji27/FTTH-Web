package mm.com.InternetMandalay.service.impl;

import mm.com.InternetMandalay.entity.Customer;
import mm.com.InternetMandalay.exception.BadRequestException;
import mm.com.InternetMandalay.exception.NotFoundException;
import mm.com.InternetMandalay.repository.CustomerRepo;
import mm.com.InternetMandalay.response.CustomerDTO;
import mm.com.InternetMandalay.service.CustomerService;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
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
            Customer customer = new Customer();
            customer.setFtthAccount(row.getCell(0).getStringCellValue());
            if (row.getCell(1) == null){
                customer.setCustomerName("");
            }else {
                customer.setCustomerName(row.getCell(1).getStringCellValue());
            }
            if (row.getCell(2) == null){
                customer.setCustomerAddress("");
            }else {
                customer.setCustomerAddress(row.getCell(2).getStringCellValue());
            }
            customer.setContactPhone(row.getCell(3).getStringCellValue());
            if (row.getCell(4) == null){
                customer.setProductCode("");
            } else {
                customer.setProductCode(row.getCell(4).getStringCellValue());
            }
            if (row.getCell(5) == null){
                customer.setMonthAdv("");
            } else {
                customer.setMonthAdv(row.getCell(5).getStringCellValue());
            }
            if (row.getCell(6) == null){
                customer.setTotalMoney("");
            } else {
                CellType totalMoneyType = row.getCell(6).getCellType();
                if (totalMoneyType == CellType.STRING){
                    customer.setTotalMoney(row.getCell(6).getStringCellValue());
                }
                if (totalMoneyType == CellType.NUMERIC){
                    Integer value = (int) row.getCell(6).getNumericCellValue();
                    customer.setTotalMoney(value.toString());
                }
            }
            if (row.getCell(7) == null){
                customer.setD2dName("");
            }else {
                customer.setD2dName(row.getCell(7).getStringCellValue());
            }
            if (row.getCell(8) == null){
                customer.setD2dPhoneNumber("");
            } else{
                customer.setD2dPhoneNumber(row.getCell(8).getStringCellValue());
            }
            if (row.getCell(9) == null){
                customer.setBillBlock("");
            } else {
                customer.setBillBlock(row.getCell(9).getStringCellValue());
            }
            customerRepo.save(customer);
        }
        workbook.close();
    }

    @Cacheable(value = "Customer", key = "#contactPhone + ':' + #ftthAccount")
    @Override
    public List<CustomerDTO> search(String contactPhone, String ftthAccount, String otp) {
        if (!otp.equals("12345") || otp.isBlank()){
            throw new BadRequestException("The OTP is invalid");
        }
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
                        .totalMoney(customer.getTotalMoney())
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
                        .totalMoney(customer.getTotalMoney())
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
                    .totalMoney(customer.getTotalMoney())
                    .d2dName(customer.getD2dName())
                    .d2dPhoneNumber(customer.getD2dPhoneNumber())
                    .billBlock(customer.getBillBlock())
                    .build();
            customerDTOList.add(customerDto);
        }
        return customerDTOList;
    }

    @Override
    public String getOtp(String contactPhone) {
        if (contactPhone.isBlank()){
            throw new NotFoundException("You haven't entered phone number yet!");
        }
        List<Customer> customers = customerRepo.findCustomerByContactPhone(contactPhone);
        if (customers.size() == 0){
            throw new NotFoundException("Phone number is not included in our system!");
        }
        return "The otp has been sent to your phone number, please check it and fill it to the blank for OTP";
    }

    @Override
    public void resetCustomerData() {
        customerRepo.deleteAll();
    }

}
