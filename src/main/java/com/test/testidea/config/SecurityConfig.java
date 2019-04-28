package com.test.testidea.config;

import com.test.testidea.constant.Static;
import com.test.testidea.secruity.AccountPasswordAuthenticationProvider;
import com.test.testidea.secruity.CustomAccessDeniedHandler;
import com.test.testidea.secruity.JwtAuthenticationEntryPoint;
import com.test.testidea.secruity.JwtAuthenticationTokenFilter;
import com.test.testidea.secruity.MobileCodeAuthenticationProvider;
import com.test.testidea.secruity.SecurityFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 权限配置
 *
 * @author fangzhimin
 * @version 0.1
 * @date 2018/9/4 10:54
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    JwtAuthenticationEntryPoint unauthorizedHandler;
    @Autowired
    CustomAccessDeniedHandler customAccessDeniedHandler;
    @Autowired
    JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
    @Autowired
    SecurityFilter securityFilter;
    @Autowired
    AccountPasswordAuthenticationProvider accountPasswordAuthenticationProvider;
    @Autowired
    MobileCodeAuthenticationProvider mobileCodeAuthenticationProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .headers().cacheControl().disable().and()
                .sessionManagement().disable()
                .securityContext().disable()
                .exceptionHandling().accessDeniedHandler(customAccessDeniedHandler).authenticationEntryPoint(unauthorizedHandler).and()
                .authorizeRequests()
                .antMatchers("/" + Static.PATH_PREFIX + "/**").permitAll()
                .antMatchers("/ws/**").permitAll()
                .antMatchers("/auth/login/account").permitAll()
                .antMatchers("/auth/login/mobile").permitAll()
                .antMatchers("/auth/getMobileCode").permitAll()
                .antMatchers("/rtc/getAppId").permitAll()
                .anyRequest().authenticated().and()
//                 .anyRequest().permitAll().and()
                .addFilterBefore(securityFilter, FilterSecurityInterceptor.class)
                .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .httpBasic();
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(
            "/favicon.ico",
            "/*.html",
            "/**/*.html",
            "/**/*.css",
            "/**/*.js",
            "/v2/api-docs",
            "/swagger-resources/**",
            "/swagger-ui.html**",
            "/webjars/**"
        );
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(accountPasswordAuthenticationProvider);
        auth.authenticationProvider(mobileCodeAuthenticationProvider);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public WebMvcConfigurer configurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*").allowedMethods("*");
            }

            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
                registry.addResourceHandler("/" + Static.PATH_PREFIX + "/**")
                        .addResourceLocations("file:" + Static.LOCAL_PATH);

                registry.addResourceHandler("swagger-ui.html")
                        .addResourceLocations("classpath:/META-INF/resources/");

                registry.addResourceHandler("/webjars/**")
                        .addResourceLocations("classpath:/META-INF/resources/webjars/");
            }
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
