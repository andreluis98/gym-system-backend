package br.com.gym.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API - Gym System")
                        .description("API para gerenciamento de treinos e alunos")
                        .version("v1")
                        .contact(new Contact().name("Equipe Gym").email("contato@gym.com"))
                        .license(new License().name("Licença Livre").url("https://opensource.org/licenses/MIT"))
                );
    }
}
