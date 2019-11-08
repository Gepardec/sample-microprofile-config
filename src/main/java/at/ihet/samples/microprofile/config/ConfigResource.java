package at.ihet.samples.microprofile.config;

import org.eclipse.microprofile.config.Config;
import org.jboss.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple rest resource to get the current configuration and to set configuration system properties.
 */
@RequestScoped
@Path("/")
public class ConfigResource {

    private Logger log = Logger.getLogger(ConfigResource.class);

    @Inject
    Configuration configuration;

    @Inject
    Config config;

    @Path("/config")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> get() {
        log.infof("Provided whole configuration");
        return configuration.getAsMap();
    }

    @Path("/config/dynamic/{property}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getDynamic(@PathParam("property") final String property) {
        // Programmatic lookup of properties
        final String result = config.getOptionalValue(property, String.class).orElse("Property not found");
        log.infof("Provided configuration parameter '%s=%s'", property, result);
        return result;
    }

    @Path("/modify/{property}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, String> modify(@PathParam("property") final String property,
                                      @QueryParam("value") final String value) {
        final String oldValue = System.getProperty(property);
        System.setProperty(property, value);
        final String newValue = System.getProperty(property);
        log.infof("Modified property='%s' oldValue='%s', neValue='%s'", property, oldValue, newValue);
        return new HashMap<>() {{
            put("oldValue", oldValue);
            put("newValue", newValue);
        }};
    }
}
