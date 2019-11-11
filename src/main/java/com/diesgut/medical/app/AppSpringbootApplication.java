package com.diesgut.medical.app;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.diesgut.medical.app.security.SecurityUserServiceImp;
import com.diesgut.medical.model.security.User;
import com.diesgut.medical.model.security.UserRole;

@SpringBootApplication
//@ComponentScan(basePackages = { "com.diesgut.medical" })
public class AppSpringbootApplication implements CommandLineRunner {

	@Autowired
	SecurityUserServiceImp securityUserServiceImp;

	public static void main(String[] args) {
		SpringApplication.run(AppSpringbootApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();
		String password = bCrypt.encode("1234");

		UserDetails userDB = securityUserServiceImp.loadUserByUsername("admin");
		if (userDB == null) {
			User user = new User();
			user.setEnabled(true);
			user.setUsername("admin");
			user.setPassword(password);

			List<UserRole> userRole = new ArrayList<>();
			userRole.add(new UserRole(user, "ADMIN"));
			user.setUserRoles(userRole);
			securityUserServiceImp.saveUser(user);

			user = new User();
			user.setEnabled(true);
			user.setUsername("doctor");
			user.setPassword(password);

			userRole = new ArrayList<>();
			userRole.add(new UserRole(user, "DOCTOR"));
			user.setUserRoles(userRole);
			securityUserServiceImp.saveUser(user);
		}
	}

}
