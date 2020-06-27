package learn.kd.catalog.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CatalogItem {

    private String movieName;
    private String description;
    private Integer rating;
}
