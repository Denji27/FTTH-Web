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
@Table(name = "PAYMENT_INSTRUCTION")
public class PaymentInstruction implements Serializable {
    @Id
    private String id;

    @Column(name = "TITLE", unique = true, nullable = false)
    private String title;

    @Lob
    @Column(name = "IMAGE", columnDefinition = "MEDIUMBLOB")
    private String image;

    @Column(name = "DESCRIPTION", columnDefinition = "TEXT")
    private String description;
}
