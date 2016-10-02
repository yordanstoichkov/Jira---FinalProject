package configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan(basePackages={"controller.service.emailService"})
@Configuration
public class SpringConfig {
}
