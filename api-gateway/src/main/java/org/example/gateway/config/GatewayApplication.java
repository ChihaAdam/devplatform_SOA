package org.example.gateway.config;

import org.example.gateway.service.ProjectService;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

/** Configuration Jersey : ressources REST, CORS et injection du service metier. */
public final class GatewayApplication extends ResourceConfig {

    public GatewayApplication(ProjectService projectService) {
        packages("org.example.gateway.rest");
        register(ApiCorsFilter.class);
        register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(projectService).to(ProjectService.class);
            }
        });
    }
}
