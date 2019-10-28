package com.diesgut.medical.app.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

@Configuration
public class ThymeleafConfig {

	@Autowired
	ThymeleafViewResolver viewResolver;

	@EventListener(ApplicationReadyEvent.class)
	public void loadStaticVariables() {
		Map<String, String> adicionales = new HashMap<>();
//        adicionales.put("TAWKTO", despliegueConfig.getTawkto().toString());
//        adicionales.put("ambiente", despliegueConfig.getAmbiente());

		viewResolver.setCharacterEncoding("ISO-8859-1");
		viewResolver.setForceContentType(true);
		// viewResolver.setStaticVariables(adicionales);
	}

}
