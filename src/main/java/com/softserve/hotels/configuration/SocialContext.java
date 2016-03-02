package com.softserve.hotels.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurer;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.google.connect.GoogleConnectionFactory;
import org.springframework.social.security.AuthenticationNameUserIdSource;

import com.softserve.hotels.service.UserService;
import com.softserve.hotels.social.AccountConnectionSignUp;

@Configuration
@EnableSocial
@PropertySource(value = { "classpath:socials.properties" })
public class SocialContext implements SocialConfigurer {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private UserService userService;

    @Override
    public void addConnectionFactories(ConnectionFactoryConfigurer cfConfig, Environment env) {
        FacebookConnectionFactory facebook = new FacebookConnectionFactory(
                env.getProperty("app.config.oauth.facebook.apikey"),
                env.getProperty("app.config.oauth.facebook.apisecret"));

        facebook.setScope(env.getProperty("app.config.oauth.facebook.scope"));
        cfConfig.addConnectionFactory(facebook);
        GoogleConnectionFactory google = new GoogleConnectionFactory(env.getProperty("google.key"),
                env.getProperty("google.secret"));
        google.setScope(env.getProperty("google.scope"));
        cfConfig.addConnectionFactory(google);

    }

    @Override
    public UserIdSource getUserIdSource() {
        return new AuthenticationNameUserIdSource();
    }

    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        JdbcUsersConnectionRepository repository = new JdbcUsersConnectionRepository(dataSource,
                connectionFactoryLocator, Encryptors.noOpText());
        repository.setConnectionSignUp(new AccountConnectionSignUp(userService));
        return repository;
    }

    @Bean
    public ConnectController connectController(ConnectionFactoryLocator connectionFactoryLocator,
            ConnectionRepository connectionRepository) {
        return new ConnectController(connectionFactoryLocator, connectionRepository);
    }
}
