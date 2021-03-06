package org.pac4j.jax.rs.grizzly.features;

import javax.inject.Inject;
import javax.inject.Provider;

import org.glassfish.grizzly.http.server.Request;
import org.pac4j.core.config.Config;
import org.pac4j.jax.rs.features.JaxRsContextFactoryProvider;
import org.pac4j.jax.rs.grizzly.pac4j.GrizzlyJaxRsContext;
import org.pac4j.jax.rs.pac4j.JaxRsSessionStore;

/**
 * 
 * Extends {@link JaxRsContextFactoryProvider} to support the Grizzly container (without the need for servlet support)
 * and its session manager (i.e., pac4j indirect clients will work, contrary than with
 * {@link JaxRsContextFactoryProvider}).
 * 
 * @see JaxRsContextFactoryProvider
 * @author Victor Noel - Linagora
 * @since 1.0.0
 *
 */
public class GrizzlyJaxRsContextFactoryProvider extends JaxRsContextFactoryProvider {

    @Inject
    protected Provider<Request> requestProvider;

    public GrizzlyJaxRsContextFactoryProvider(Config config) {
        super(config);
    }

    @Override
    public JaxRsContextFactory getContext(Class<?> type) {
        Request request = requestProvider.get();
        assert request != null;
        return context -> new GrizzlyJaxRsContext(providers, context, (JaxRsSessionStore) config.getSessionStore(),
                request);
    }
}
