package mini.project.spring.domain.viewobject;

import java.util.Map;

import org.springframework.boot.actuate.scheduling.ScheduledTasksEndpoint.ScheduledTasksReport;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduledTasksReportWrapper {
    
    private ScheduledTasksReport scheduledTasksReport;
    private Map<String, ScheduledTaskInfo> scheduledTasksRuntimeInfo;
}
