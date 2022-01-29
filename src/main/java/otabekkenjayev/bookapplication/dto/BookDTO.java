package otabekkenjayev.bookapplication.dto;

import lombok.Data;

@Data
public class BookDTO {
    private String name;
    private Integer catId;
    private String imageUrl;
    private String fileUrl;
    private String audioUrl;
    private String movieUrl;
    private Integer authorId;

}
