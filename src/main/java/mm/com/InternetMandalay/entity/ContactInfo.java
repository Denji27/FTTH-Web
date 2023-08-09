package mm.com.InternetMandalay.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "CONTACT_INFO")
public class ContactInfo implements Serializable {
    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "HOTLINE")
    private String hotline;

    @Column(name = "OTHER_INFORMATION")
    private String otherInfos;
}
