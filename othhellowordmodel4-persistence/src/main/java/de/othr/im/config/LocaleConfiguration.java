package de.othr.im.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

/*
This Class is used to create the locale resolver and attach it to the application
Written by Tobias Mooshofer
 */
@Configuration
public class LocaleConfiguration implements WebMvcConfigurer {

    /*
    Creates a new locale resolver object
    Default values: Locale.Germany (de_DE)
    Written by Tobias Mooshofer
     */
    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver resolver = new SessionLocaleResolver();
        resolver.setDefaultLocale(Locale.GERMANY);
        resolver.setLocaleAttributeName("session.current.locale");
        resolver.setTimeZoneAttributeName("session.current.timezone");
        return resolver;
    }

    /*
    Creates a new locale change interceptor object
    Listenes to the http parameter "lang"
    currently accepted: de_DE, en_US
    Written by Tobias Mooshofer
     */
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        interceptor.setParamName("lang");
        return interceptor;
    }

    /*
    Creates a new message source object
    This can be used to draw server-side generated text from the messages.properties bundle
    Written by Tobias Mooshofer
    */
    @Bean("messageSource")
    public MessageSource messageSource() {
        ResourceBundleMessageSource source = new ResourceBundleMessageSource();
        source.setBasename("messages");
        source.setDefaultEncoding("UTF-8");
        source.setDefaultLocale(Locale.GERMANY);
        return source;
    }

    /*
    Attaches the locale interceptor to the environment
    Written by Tobias Mooshofer
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }
}
