package mm.com.InternetMandalay.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "ABNORMAL_CASE")
public class AbnormalCase {
    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "TITLE", unique = true, nullable = false)
    private String title;

    @Column(name = "IMAGE")
    private String image;

    @Column(name = "DESCRIPTION")
    private String description;
}
