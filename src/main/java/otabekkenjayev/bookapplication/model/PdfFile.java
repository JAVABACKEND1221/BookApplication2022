package otabekkenjayev.bookapplication.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import otabekkenjayev.bookapplication.model.template.AbsEntity;
import otabekkenjayev.bookapplication.model.template.FileStatus;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PdfFile extends AbsEntity implements Serializable {

    private String extension;

    private Long fileSize;

    private String hashId;

    private String contentType;

    private String uploadPath;

    @Enumerated(EnumType.STRING)
    private FileStatus fileStatus;

}
