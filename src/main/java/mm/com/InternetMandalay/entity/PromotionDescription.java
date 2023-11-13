package mm.com.InternetMandalay.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "PROMOTION_DESCRIPTION")
public class PromotionDescription {
    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "DESCRIPTION", columnDefinition = "TEXT")
    private String description;
}
