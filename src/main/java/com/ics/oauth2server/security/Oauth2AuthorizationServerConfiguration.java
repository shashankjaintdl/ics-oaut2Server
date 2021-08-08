package com.ics.oauth2server.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpointAuthenticationFilter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import javax.sql.DataSource;

@Configuration
@EnableAuthorizationServer
public class Oauth2AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter{

    public static final Logger LOGGER = LoggerFactory.getLogger(Oauth2AuthorizationServerConfiguration.class);

    @Value("true")
    private boolean checkUserScope;

    // JPA are the dependency should be downloaded.
    private final DataSource dataSource;

    private final PasswordEncoder passwordEncoder;

    private final ClientDetailsService clientDetailsService;

    private final UserDetailsService userDetailsService;

    private final AuthenticationManager authenticationManager;


    public Oauth2AuthorizationServerConfiguration(PasswordEncoder passwordEncoder, DataSource dataSource, ClientDetailsService clientDetailsService, UserDetailsService userDetailsService, @Qualifier("authenticationManagerBean") AuthenticationManager authenticationManager) {
        this.passwordEncoder = passwordEncoder;
        this.dataSource = dataSource;
        this.clientDetailsService = clientDetailsService;
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
    }

    @Bean
    public OAuth2RequestFactory requestFactory() {
        CustomOauth2RequestFactory requestFactory = new CustomOauth2RequestFactory(clientDetailsService);
        requestFactory.setCheckUserScopes(true);
        return requestFactory;
    }

    @Bean
    public TokenStore tokenStore() {
        LOGGER.info("Token Store Executed "+jwtAccessTokenConverter());
        //        return new JdbcTokenStore(dataSource);
        //        return new CustomJwtTokenStore(jwtAccessTokenConverter());
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        LOGGER.info("JKS file executed");
        JwtAccessTokenConverter converter = new CustomTokenEnhancer();
        converter.setKeyPair(new KeyStoreKeyFactory(new ClassPathResource("jwt.jks"), "password".toCharArray()).getKeyPair("jwt"));
        return converter;
    }


    @Bean
    public TokenEndpointAuthenticationFilter tokenEndpointAuthenticationFilter(){
        return new TokenEndpointAuthenticationFilter(authenticationManager,requestFactory());
    }
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        LOGGER.info("AuthorizationServerSecurityConfigurer Method execution started....");
        security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        LOGGER.info("Client Detail Service Configure Method execution started....");
//        clients.inMemory()
//                .withClient("first-client").secret(passwordEncoder.encode("noonewilleverguess"))
//                .authorizedGrantTypes("password","authorization_code","refresh_token")
//                .authorities("READ_ONLY_CLIENT")
//                .scopes("read_profile_info")
//                .resourceIds("oauth2-resource")
//                .redirectUris("http://localhost:8080/login")
//                .accessTokenValiditySeconds(120)
//                .refreshTokenValiditySeconds(2000)
//                .autoApprove(true);
        clients.jdbc(dataSource).passwordEncoder(passwordEncoder);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        LOGGER.info("AuthorizationServerEndpointsConfigurer  Method execution started....");
        endpoints.tokenStore(tokenStore()).tokenEnhancer(jwtAccessTokenConverter())
                .authenticationManager(authenticationManager).userDetailsService(userDetailsService);
        if (checkUserScope)
            endpoints.requestFactory(requestFactory());
    }


}
