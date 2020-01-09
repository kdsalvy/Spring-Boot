package mini.project.spring.domain.viewobject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchCriteria {

    private String key;
    private String value;
    private String operation;
    private int page;
    private int limit;
    
}
