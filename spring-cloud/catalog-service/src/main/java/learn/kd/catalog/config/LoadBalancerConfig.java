package learn.kd.catalog.config;

import org.springframework.context.annotation.Bean;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RetryRule;
import com.netflix.loadbalancer.WeightedResponseTimeRule;
import com.netflix.niws.loadbalancer.NIWSDiscoveryPing;

public class LoadBalancerConfig {

    @Bean
    public IPing ribbonPing(IClientConfig config) {
        NIWSDiscoveryPing niwsDiscoveryPing = new NIWSDiscoveryPing();
        niwsDiscoveryPing.initWithNiwsConfig(config);
        return niwsDiscoveryPing;
    }

    @Bean
    public IRule ribbonRule(IClientConfig config) {
        WeightedResponseTimeRule weightedResponseTimeRule = new WeightedResponseTimeRule();
        weightedResponseTimeRule.initWithNiwsConfig(config);
        RetryRule rule = new RetryRule(weightedResponseTimeRule);
        return rule;
    }

}
