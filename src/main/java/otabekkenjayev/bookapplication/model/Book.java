package otabekkenjayev.bookapplication.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import otabekkenjayev.bookapplication.model.template.AbsEntity;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.ManyToOne;

@Data
@EqualsAndHashCode(callSuper = true )
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(value = AuditingEntityListener.class)
public class Book extends AbsEntity {

    private String imageUrl;
    private String audioUrl;
    private String fileUrl;
    private String movieUrl;

    @ManyToOne
    private Category category;
    @ManyToOne
    private Author author;



}
