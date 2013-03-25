package pt.ist.worklr.utils;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BundleUtil {
    private static final Logger LOG = LoggerFactory.getLogger(BundleUtil.class);

    public static String getString(final String bundle, final String key, String... args) {
        return getString(bundle, I18N.getLocale(), key, args);
    }

    public static String getString(final String bundle, Locale locale, final String key, String... args) {
        try {
            String message = ResourceBundle.getBundle(bundle, locale).getString(key);
            for (int i = 0; i < args.length; i++) {
                message = message.replaceAll("\\{" + i + "\\}", args[i] == null ? "" : args[i]);
            }
            return message;
        } catch (MissingResourceException e) {
            LOG.warn(e.getMessage());
            return '!' + key + '!';
        }
    }
}