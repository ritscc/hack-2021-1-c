package dev.abelab.timestamp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	PasswordEncoder passwordEncoder() {
		return new Pbkdf2PasswordEncoder();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		// 静的リソースへのアクセスを許可
		web.ignoring().antMatchers("/v2/api-docs", "/swagger-resources/**", "/swagger-ui/**", "/webjars/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// CORSを有効化し，CSRFを無効化
		http = http.cors().and().csrf().disable();

		// ステートレスなセッション管理
		http = http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and();

		// アクセス許可
		http.authorizeRequests() //
			.antMatchers("/api/batch/**").hasIpAddress("::1") //
			.antMatchers("/api/**").permitAll() //
			.anyRequest().authenticated();
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry //
					.addMapping("/**") //
					.allowedOrigins("*") //
					.allowedMethods("*") //
					.allowedHeaders("*") //
					.exposedHeaders("*") //
					.allowedOriginPatterns("*");
			}
		};
	}

}
