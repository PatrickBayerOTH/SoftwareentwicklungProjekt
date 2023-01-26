package de.othr.im.config;

import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/*
This Class is used to prepare the parameters needed in the paypal integration
Written by Tobias Mooshofer
 */
@Configuration
public class PaypalConfiguration {

    @Value("${paypal.clientId}")
    private String clientId;
    @Value("${paypal.secret}")
    private String clientSecret;
    @Value("${paypal.mode}")
    private String mode;

    /*
    Generates a mapping of the specified paypal configuration parameters
     */
    @Bean
    public Map<String, String> paypalSdkConfig() {
        Map<String, String> configMap = new HashMap<>();
        configMap.put("mode", mode);
        return configMap;
    }

    /*
    Generates an OAuthTokenCredential from a configuration and the applications paypal id / secret
     */
    @Bean
    public OAuthTokenCredential oAuthTokenCredential() {
        return new OAuthTokenCredential(clientId, clientSecret, paypalSdkConfig());
    }

    /*
    Generates an APIContext object from a configuration and an OAuthToken
    This token is appended to paypal api requests for identification
     */
    @Bean
    public APIContext apiContext() throws PayPalRESTException {
        APIContext context = new APIContext(oAuthTokenCredential().getAccessToken());
        context.setConfigurationMap(paypalSdkConfig());
        return context;
    }

}