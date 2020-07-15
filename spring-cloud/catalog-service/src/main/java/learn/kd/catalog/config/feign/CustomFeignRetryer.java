package learn.kd.catalog.config.feign;

import org.springframework.stereotype.Component;

import feign.RetryableException;
import feign.Retryer;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@NoArgsConstructor
public class CustomFeignRetryer implements Retryer {

    private int retryMaxAttempt;

    private long retryInterval;

    private int attempt = 1;
    
    private int retryIntervalMultiplier;

    public CustomFeignRetryer(int retryMaxAttempt, Long retryInterval, int retryIntervalMultiplier) {
        this.retryMaxAttempt = retryMaxAttempt;
        this.retryInterval = retryInterval;
        this.retryIntervalMultiplier = retryIntervalMultiplier;
    }

    @Override
    public void continueOrPropagate(RetryableException e) {
        log.info("Feign retry attempt {} due to {} ", attempt, e.getMessage());

        if (attempt++ == retryMaxAttempt) {
            throw e;
        }
        try {
            retryInterval *= retryIntervalMultiplier;
            Thread.sleep(retryInterval);
        } catch (InterruptedException ignored) {
            Thread.currentThread()
                .interrupt();
        }

    }

    @Override
    public Retryer clone() {
        return new CustomFeignRetryer(6, 2000L, 2);
    }

}
