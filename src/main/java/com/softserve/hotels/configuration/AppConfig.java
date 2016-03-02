/**
 * @author Makhov Sergiy
 */
package com.softserve.hotels.configuration;

import java.util.Properties;

import org.apache.commons.lang3.SystemUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;

import com.paypal.api.payments.Payment;
import com.paypal.base.ConfigManager;
import com.softserve.hotels.service.PgDumper;
import com.softserve.hotels.service.PgWindowsDumper;
import com.softserve.hotels.service.PgunixDumper;

@Configuration
@EnableWebMvc
@EnableScheduling
@ComponentScan(basePackages = "com.softserve.hotels")
@PropertySource(value = { "classpath:mail.properties", 
						"classpath:sdk_config.properties", 
						"classpath:hibernate.properties" })
@EnableAspectJAutoProxy
public class AppConfig {

    public static final int MAX_UPLOAD_SIZE = 100000000;

    
    @Autowired
    private Environment environment;

    @Bean
    public TilesViewResolver viewResolver() {
        return new TilesViewResolver();
    }

    @Bean
    public TilesConfigurer tilesConfigurer() {
        TilesConfigurer tilesConfigurer = new TilesConfigurer();
        tilesConfigurer.setDefinitions("/WEB-INF/template/definitions.xml");
        return tilesConfigurer;
    }

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(MAX_UPLOAD_SIZE);
        return multipartResolver;
    }

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setDefaultEncoding("UTF-8");
        mailSender.setHost(environment.getProperty("mail.host"));
        mailSender.setPort(Integer.valueOf(environment.getProperty("mail.port")));
        mailSender.setUsername(environment.getProperty("mail.username"));
        mailSender.setPassword(environment.getProperty("mail.password"));
        
        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.transport.protocol", environment.getProperty("mail.transport.protocol"));
        javaMailProperties.put("mail.smtp.auth", environment.getProperty("mail.smtp.auth"));
        javaMailProperties.put("mail.smtp.starttls.enable", environment.getProperty("mail.smtp.starttls.enable"));
        javaMailProperties.put("mail.smtp.quitwait", environment.getProperty("mail.smtp.quitwait"));
        mailSender.setJavaMailProperties(javaMailProperties);
        return mailSender;
    }

    @Bean
    public TaskScheduler taskScheduler() {
        return new ThreadPoolTaskScheduler();
    }
    
    @Bean 
    public ConfigManager configManager() {
        Properties paypalOptions = new Properties();
        paypalOptions.setProperty("http.ConnectionTimeOut", environment.getProperty("http.ConnectionTimeOut"));
        paypalOptions.setProperty("http.Retry", environment.getProperty("http.Retry"));
        paypalOptions.setProperty("http.ReadTimeOut", environment.getProperty("http.ReadTimeOut"));
        paypalOptions.setProperty("http.MaxConnection", environment.getProperty("http.MaxConnection"));
        paypalOptions.setProperty("http.GoogleAppEngine", environment.getProperty("http.GoogleAppEngine"));
        paypalOptions.setProperty("service.EndPoint", environment.getProperty("service.EndPoint"));
        paypalOptions.setProperty("clientId", environment.getProperty("clientId"));
        paypalOptions.setProperty("clientSecret", environment.getProperty("clientSecret"));
        Payment.initConfig(paypalOptions);
        return ConfigManager.getInstance();
    }
    
    @Bean(name = "pgDumper")
    public PgDumper pgDumper() {
        if (SystemUtils.IS_OS_WINDOWS) {
        	PgWindowsDumper dumper = new PgWindowsDumper();
        	dumper.setUrl(environment.getProperty("jdbc.url"));
        	dumper.setUserName(environment.getProperty("jdbc.username"));
            dumper.setPassword(environment.getProperty("jdbc.password"));
            dumper.setPostgresBinPath(environment.getProperty("postgres.bin_path"));
            return dumper;
        }
        return new PgunixDumper();
    }
}
