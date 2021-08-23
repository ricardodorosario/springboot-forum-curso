package com.pokolegas.forum.configuration;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.pokolegas.forum.model.Usuario;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfiguration {

	@Bean
	public Docket forumAPI() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.pokolegas.forum"))
				.paths(PathSelectors.ant("/**"))
				.build()
				.ignoredParameterTypes(Usuario.class)
				.securitySchemes(Arrays.asList(apiKey()))
		        .securityContexts(Arrays.asList(securityContext()));	}

	private SecurityScheme apiKey() {
		return new ApiKey("apiKey", "Authorization", "header");
	}

	@SuppressWarnings("deprecation")
	private SecurityContext securityContext() {
	    return SecurityContext.builder().securityReferences(defaultAuth())
	        .forPaths(PathSelectors.any()).build();
	    }


	private List<SecurityReference> defaultAuth() {
	    AuthorizationScope authorizationScope = new AuthorizationScope(
	        "global", "accessEverything");
	    AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
	    authorizationScopes[0] = authorizationScope;
	    return Arrays.asList(new SecurityReference("apiKey",
	        authorizationScopes));
	    }
}
