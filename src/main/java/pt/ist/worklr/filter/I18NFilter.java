package pt.ist.worklr.filter;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import pt.ist.worklr.utils.I18N;

public class I18NFilter implements Filter {
    
    @Override
    public void init(final FilterConfig config) throws ServletException {
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        try {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            String requestLocale = request.getParameter("locale");
            if (requestLocale != null) {
                I18N.setLocale(httpRequest.getSession(true), Locale.forLanguageTag(requestLocale));
            } else {
                I18N.updateFromSession(httpRequest.getSession(false));
            }
            chain.doFilter(request, response);
        } finally {
            I18N.setLocale(null);
        }
    }

}