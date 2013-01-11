package pt.ist.worklr.filter;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.SecurityContext;

import pt.ist.worklr.domain.AuthToken;
import pt.ist.worklr.domain.User;

import com.sun.jersey.core.util.Base64;

public class AuthenticationFilter implements Filter {

    private class AuthenticatedRequestWrapper extends HttpServletRequestWrapper {

	private final String username;

	public AuthenticatedRequestWrapper(HttpServletRequest request, String username) {
	    super(request);
	    this.username = username;
	}

	@Override
	public Principal getUserPrincipal() {
	    return new Principal() {

		@Override
		public String getName() {
		    return username;

		}
	    };
	}
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
	    ServletException {
	HttpServletRequest httpServletRequest = (HttpServletRequest) request;
	String authHeader = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
	String ipAddress = httpServletRequest.getRemoteAddr();
	if (authHeader == null) {
	    chain.doFilter(request, response);
	} else {
	    String auth = authHeader.substring(SecurityContext.BASIC_AUTH.length());
	    String[] values = Base64.base64Decode(auth).split(":");

	    String userExternalId = values[0];
	    String token = values[1];

	    User user = User.fromExternalId(userExternalId);

	    AuthToken authToken = AuthToken.validateAuthToken(user, token, ipAddress);

	    if (authToken != null) {
		chain.doFilter(new AuthenticatedRequestWrapper((HttpServletRequest) request, user.getExternalId()), response);
	    } else {
		chain.doFilter(request, response);
	    }
	}
    }

    @Override
    public void destroy() {
    }

}