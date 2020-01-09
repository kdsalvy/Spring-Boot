package mini.project.spring.domain.viewobject;

import java.time.ZonedDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduledTaskInfo {

    private ZonedDateTime previousTriggerTime;
    private ZonedDateTime nextTriggerTime;
}
