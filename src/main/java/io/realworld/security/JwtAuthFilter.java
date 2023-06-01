package io.realworld.security;

import io.dropwizard.auth.AuthFilter;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.HttpHeaders;
import java.security.Principal;

@Priority(Priorities.AUTHENTICATION)
public class JwtAuthFilter<P extends Principal> extends AuthFilter<JwtToken, P> {

    public static final String TOKEN_PREFIX = "Token ";

    @Override
    public void filter(final ContainerRequestContext requestContext) {
        final JwtToken credentials = getCredentials(requestContext.getHeaders().getFirst(HttpHeaders.AUTHORIZATION));
        if (!authenticate(requestContext, credentials, "JWT")) {
            throw new WebApplicationException(unauthorizedHandler.buildResponse(prefix, realm));
        }
    }

    private JwtToken getCredentials(final String authLine) {
        if (authLine != null && authLine.startsWith(TOKEN_PREFIX)) {
            return new JwtToken(authLine.substring(TOKEN_PREFIX.length()));
        }
        return null;
    }

    public static class Builder<P extends Principal> extends AuthFilterBuilder<JwtToken, P, JwtAuthFilter<P>> {
        @Override
        protected JwtAuthFilter<P> newInstance() {
            return new JwtAuthFilter<>();
        }
    }
}
