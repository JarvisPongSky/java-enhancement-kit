package com.pongsky.kit.core.autoconfigure;

import com.pongsky.kit.core.config.JacksonConfig;
import com.pongsky.kit.core.config.SpringMvcConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Core 自动装配
 *
 * @author pengsenhao
 **/
@Configuration(proxyBeanMethods = false)
@Import({SpringMvcConfig.class, JacksonConfig.class})
public class CoreAutoConfiguration {


}
