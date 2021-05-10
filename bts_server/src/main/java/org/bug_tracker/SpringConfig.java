package org.bug_tracker;

import org.bug_tracker.service.IService;
import org.bug_tracker.service.Service;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiServiceExporter;

@Configuration
@EntityScan("domain")
@ComponentScan
@EnableAutoConfiguration
public class SpringConfig {

    @Bean
    RmiServiceExporter exporter(Service service) {
        RmiServiceExporter exporter = new RmiServiceExporter();
        exporter.setServiceName("service");
        exporter.setService(service);
        exporter.setServiceInterface(IService.class);
        exporter.setServicePort(1099);
        return exporter;
    }
}


