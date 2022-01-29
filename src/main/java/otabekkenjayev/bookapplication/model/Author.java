package otabekkenjayev.bookapplication.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import otabekkenjayev.bookapplication.model.template.AbsEntity;

import javax.persistence.Entity;

@Data
@EqualsAndHashCode(callSuper = true )
@AllArgsConstructor
@Entity
public class Author extends AbsEntity {

}
