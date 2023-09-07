package mm.com.InternetMandalay.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "CUSTOMER")
public class Customer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "FTTH_ACCOUNT")
    private String ftthAccount;

    @Column(name = "NAME")
    private String customerName;

    @Column(name = "CUSTOMER_ADDRESS")
    private String customerAddress;

    @Column(name = "CONTACT_PHONE")
    private String contactPhone;

    @Column(name = "PRODUCT_CODE")
    private String productCode;

    @Column(name = "MONTH_ADV")
    private String monthAdv;

    @Column(name = "TOTAL_MONEY")
    private String totalMoney;

    @Column(name = "D2D_NAME")
    private String d2dName;

    @Column(name = "D2d_PHONE_NUMBER")
    private String d2dPhoneNumber;

    @Column(name = "BILL_BLOCK")
    private String billBlock;
}