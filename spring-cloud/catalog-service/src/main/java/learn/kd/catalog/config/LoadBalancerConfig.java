package learn.kd.catalog.config;

import org.springframework.context.annotation.Bean;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AvailabilityFilteringRule;
import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RetryRule;
import com.netflix.niws.loadbalancer.NIWSDiscoveryPing;

public class LoadBalancerConfig {

    @Bean
    public IPing ribbonPing(IClientConfig config) {
        return new NIWSDiscoveryPing();
    }

    @Bean
    public IRule ribbonRule(IClientConfig config) {
        AvailabilityFilteringRule availabilityFilteringRule = new AvailabilityFilteringRule();
        availabilityFilteringRule.initWithNiwsConfig(config);
        RetryRule rule = new RetryRule(availabilityFilteringRule);
        return rule;
    }

}
