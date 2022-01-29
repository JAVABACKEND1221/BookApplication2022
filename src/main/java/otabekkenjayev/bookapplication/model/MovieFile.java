package otabekkenjayev.bookapplication.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import otabekkenjayev.bookapplication.model.template.AbsEntity;

import javax.persistence.Entity;

@Data
@EqualsAndHashCode(callSuper = true )
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class MovieFile extends AbsEntity {

    private String url;
}
