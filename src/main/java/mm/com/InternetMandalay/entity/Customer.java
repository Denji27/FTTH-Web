package mm.com.InternetMandalay.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "CUSTOMER")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "CUSTOMER_ID")
    private String customerId;

    @Column(name = "NAME")
    private String name;

    @Column(name = "ACCOUNT", unique = true)
    private String account;

    @Column(name = "PHONE_NUMBER", unique = true)
    private String phoneNumber;

    @Column(name = "SERVICE_NAME")
    private String serviceName;

    @Column(name = "NUMBER_OF_PAID_MONTHS")
    private Integer numberOfPaidMonths;

    @Column(name = "EXTENSION_DATE")
    @Temporal(TemporalType.DATE)
    private Date extensionDate;

    @Column(name = "INTERNET_BLOCKING_DATE")
    @Temporal(TemporalType.DATE)
    private Date internetBlockingDate;
}