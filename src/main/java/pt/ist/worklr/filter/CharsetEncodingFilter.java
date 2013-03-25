package pt.ist.worklr.filter;

import java.io.IOException;
import java.nio.charset.Charset;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CharsetEncodingFilter implements Filter {

    Logger logger = LoggerFactory.getLogger(CharsetEncodingFilter.class);

    private static String defaultCharset = Charset.defaultCharset().name();

    @Override
    public void init(final FilterConfig config) throws ServletException {
        final String defaultCharset = config.getInitParameter("defaultCharset");
        if (defaultCharset != null && !defaultCharset.isEmpty() && Charset.forName(defaultCharset) != null) {
            CharsetEncodingFilter.defaultCharset = defaultCharset;
        }
        logger.info("Charset : {}", defaultCharset);
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        if (request.getCharacterEncoding() == null) {
            request.setCharacterEncoding(defaultCharset);
        }
        chain.doFilter(request, response);
        if (response.getCharacterEncoding() == null) {
            response.setCharacterEncoding(defaultCharset);
        }
    }

}