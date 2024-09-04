package bsn.backend;

import bsn.backend.REPOSITORIES.RoleRepository;
import bsn.backend.ROLES.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableAsync
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}
	@Bean
	public CommandLineRunner commandLineRunner(RoleRepository roleRepository){
		System.out.println(" ******************************");
		System.out.println(" *    Default Role Seeding    *");
		System.out.println(" ******************************");

		return args ->	{
			if(roleRepository.findRoleByName("USER").isEmpty()){
				Role role = Role.builder().name("USER").build();
			roleRepository.save(role);
		}

	};
	}

}
