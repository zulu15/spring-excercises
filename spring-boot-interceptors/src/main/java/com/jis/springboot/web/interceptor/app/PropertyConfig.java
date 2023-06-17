package com.jis.springboot.web.interceptor.app;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;


@Configuration
@PropertySources({@PropertySource("classpath:values.properties")})


public class PropertyConfig {

}
