package mm.com.InternetMandalay.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "PAYMENT_REQUEST")
public class PaymentRequest implements Serializable {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "FTTH_ACCOUNT")
    private String ftthAccount;

    @Column(name = "CONTACT_PHONE")
    private String contactPhone;

    @CreationTimestamp
    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;
}
