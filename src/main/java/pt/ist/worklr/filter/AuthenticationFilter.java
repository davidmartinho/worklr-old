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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.ist.worklr.domain.AuthToken;
import pt.ist.worklr.domain.User;

import com.sun.jersey.core.util.Base64;

public class AuthenticationFilter implements Filter {

    private static final Logger LOG = LoggerFactory.getLogger(AuthenticationFilter.class);
    
	private static final String BASIC_AUTH = "Basic";

	private class BasicAuthenticationRequestWrapper extends HttpServletRequestWrapper {

		private final String username;

		public BasicAuthenticationRequestWrapper(HttpServletRequest request, String username) {
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
		    LOG.debug("No Authorization Header was found...");
			chain.doFilter(request, response);
		} else {
		    LOG.debug("Authorization Header found...");
			if (authHeader.contains(BASIC_AUTH)) {
			    LOG.debug("Basic Auth Header found...");
				String auth = authHeader.substring(BASIC_AUTH.length() + 1);
				String[] values = Base64.base64Decode(auth).split(":");

				String userExternalId = values[0];
				String token = values[1];

				try {
					User user = User.fromExternalId(userExternalId);
					LOG.debug("User authenticated is " + user.getName());
					AuthToken authToken = AuthToken.validateAuthToken(user, token, ipAddress);
					
					if (authToken != null) {
						System.out.println("AuthToken is not null and logout timestamp is:" + authToken.getLogoutTimestamp());
						System.out.println("User external ID: " + user.getExternalId());
						chain.doFilter(new BasicAuthenticationRequestWrapper((HttpServletRequest) request, user.getExternalId()),
								response);
					} else {
						System.out.println("AuthToken is null...");

						chain.doFilter(request, response);
					}
				} catch (NumberFormatException nfe) {
					System.out.println("Error while authenticating credentials");
					chain.doFilter(request, response);
				}
			}
		}
	}

	@Override
	public void destroy() {
	}

}