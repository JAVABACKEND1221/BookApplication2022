package otabekkenjayev.bookapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {
    private String name;
    private Integer parentCategoryId;
    private boolean active=true;
}
