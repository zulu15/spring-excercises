package com.jis.springboot.web.app;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@PropertySources({
	@PropertySource("classpath:indexcontroller.properties"
			+ "")
})
public class AdditionalPropertiesConfiguration {

}
