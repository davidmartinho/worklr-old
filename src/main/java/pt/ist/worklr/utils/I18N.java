package pt.ist.worklr.utils;

import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class I18N {
    private static final Logger logger = LoggerFactory.getLogger(I18N.class);

    private static final String LOCALE_KEY = I18N.class.getName() + "_LOCAL_KEY";

    private static final InheritableThreadLocal<Locale> locale = new InheritableThreadLocal<>();

    public static Locale getLocale() {
        return locale.get() != null ? locale.get() : Locale.getDefault();
    }

    /**
     * Sets the {@link Locale} for current thread, and for current session (if a session is passed)
     * 
     * @param session Option session instance,
     * @param locale Locale to set
     */
    public static void setLocale(HttpSession session, Locale locale) {
        if (session != null) {
            session.setAttribute(LOCALE_KEY, locale);
        }
        setLocale(locale);
    }

    public static void setLocale(Locale locale) {
        I18N.locale.set(locale);
        logger.debug("Set locale to: {}", locale);
    }

    public static void updateFromSession(HttpSession session) {
        if (session != null && session.getAttribute(LOCALE_KEY) != null) {
            locale.set((Locale) session.getAttribute(LOCALE_KEY));
            logger.debug("Set thread's locale to: {}", locale.get().toString());
        } else {
            locale.set(null);
        }
    }
    
}