package com.diesgut.medical.app.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
//@EntityScan(basePackageClasses=City.class)
@EntityScan(basePackages = { "com.diesgut.medical.model" })
public class PersistenceConfig {

}
