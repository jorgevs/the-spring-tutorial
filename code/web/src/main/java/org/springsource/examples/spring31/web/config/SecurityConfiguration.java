package org.springsource.examples.spring31.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;


/**
 * class for all the security aware parts of the system like form-based login and OAuth
 *
 * @author Josh long
 */
@Configuration
@ImportResource({"classpath:/security.xml"})
public class SecurityConfiguration {


    /*
    @Bean
    public UnanimousBased accessDecisionManager() {
        List<AccessDecisionVoter> decisionVoters = new ArrayList<AccessDecisionVoter>();
        decisionVoters.add(new ScopeVoter());
        decisionVoters.add(new RoleVoter());
        decisionVoters.add(new AuthenticatedVoter());
        return new UnanimousBased(decisionVoters);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
  @Bean
    public TextEncryptor textEncryptor() {
        return Encryptors.noOpText();
    }
    @Bean
    public InMemoryTokenStore tokenStore() {
        return new InMemoryTokenStore();
    }
    @Bean
    public DefaultTokenServices tokenServices(InMemoryTokenStore tokenStore, ClientDetailsService jpaUserCredentialsService) {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore);
        defaultTokenServices.setSupportRefreshToken(true);
        defaultTokenServices.setClientDetailsService(jpaUserCredentialsService);
        return defaultTokenServices;
    }

    @Bean
    public OAuth2AuthenticationEntryPoint oauthAuthenticationEntryPoint() {
        OAuth2AuthenticationEntryPoint oAuth2AuthenticationEntryPoint = new OAuth2AuthenticationEntryPoint();
        oAuth2AuthenticationEntryPoint.setRealmName("crm");
        return oAuth2AuthenticationEntryPoint;
    }

    @Bean
    public OAuth2AccessDeniedHandler oauthAccessDeniedHandler() {
        return new OAuth2AccessDeniedHandler();
    }

    @Bean
    @Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
    public ClientCredentialsTokenEndpointFilter clientCredentialsTokenEndpointFilter(AuthenticationManager authenticationManager) {
        ClientCredentialsTokenEndpointFilter endpointFilter = new ClientCredentialsTokenEndpointFilter() {
        }; // todo fix this so that the class isnt loaded by subclassing. this is gross.
        endpointFilter.setAuthenticationManager(authenticationManager);
        return endpointFilter;

    }

    @Bean
    public RoleAwareOAuthTokenServicesUserApprovalHandler oauthTokenServicesUserApprovalHandler(AuthorizationServerTokenServices tokenServices) {
        RoleAwareOAuthTokenServicesUserApprovalHandler approvalHandler = new RoleAwareOAuthTokenServicesUserApprovalHandler();
        approvalHandler.setUseTokenServices(true);
        approvalHandler.setTokenServices(tokenServices);
        return approvalHandler;
    }*/

}



