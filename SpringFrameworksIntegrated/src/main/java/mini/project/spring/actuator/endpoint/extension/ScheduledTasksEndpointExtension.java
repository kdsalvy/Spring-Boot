package mini.project.spring.actuator.endpoint.extension;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.web.WebEndpointResponse;
import org.springframework.boot.actuate.endpoint.web.annotation.EndpointWebExtension;
import org.springframework.boot.actuate.scheduling.ScheduledTasksEndpoint;
import org.springframework.stereotype.Component;

import mini.project.spring.aop.ScheduledJobAspect;
import mini.project.spring.domain.viewobject.ScheduledTasksReportWrapper;

@Component
@EndpointWebExtension(endpoint = ScheduledTasksEndpoint.class)
public class ScheduledTasksEndpointExtension {

    private static final Logger logger = LoggerFactory.getLogger(ScheduledTasksEndpointExtension.class);

    @Autowired
    private ScheduledTasksEndpoint delegate;

    @Autowired
    private ScheduledJobAspect scheduledJobAspect;

    @ReadOperation
    public WebEndpointResponse<ScheduledTasksReportWrapper> updateScheduledInfoMap() {
        logger.info("updating scheduled info map");
        ScheduledTasksReportWrapper scheduledTasksReportWrapper 
            = new ScheduledTasksReportWrapper(this.delegate.scheduledTasks()
                , scheduledJobAspect.getScheduledTaskRuntimeInfo());
        return new WebEndpointResponse<>(scheduledTasksReportWrapper);
    }
}
