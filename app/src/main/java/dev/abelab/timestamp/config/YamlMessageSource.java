package dev.abelab.timestamp.config;

import net.rakugakibox.util.YamlResourceBundle;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

class YamlMessageSource extends ResourceBundleMessageSource {

    @Override
    protected ResourceBundle doGetBundle(String basename, Locale locale) throws MissingResourceException {
        return ResourceBundle.getBundle(basename, locale, YamlResourceBundle.Control.INSTANCE);
    }

}
