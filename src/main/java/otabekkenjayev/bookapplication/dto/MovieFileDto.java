package otabekkenjayev.bookapplication.dto;

import lombok.Data;

@Data
public class MovieFileDto {

    private String name;
    private String url;
    private boolean active = true;
}
