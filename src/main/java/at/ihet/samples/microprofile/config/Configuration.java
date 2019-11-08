package at.ihet.samples.microprofile.config;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This is a scoped instance and is created for each request,
 * which will reload the injected properties.
 * Be aware that it depends on the MicroProfile-Config implementation if configurations are reloaded or not.
 */
@RequestScoped
public class Configuration {

    @Inject
    @ConfigProperty(name = "modifiableProp", defaultValue = "No 'prop' provided")
    String modifiableProp;

    @Inject
    @ConfigProperty(name = "count")
    Integer count;

    @Inject
    @ConfigProperty(name = "pets")
    List<String> pets;

    @Inject
    @ConfigProperty(name = "accounts")
    AccountUsernames accountWithUsernames;

    public String getModifiableProp() {
        return modifiableProp;
    }

    public Integer getCount() {
        return count;
    }

    public List<String> getPets() {
        return pets;
    }

    public AccountUsernames getAccountWithUsernames() {
        return accountWithUsernames;
    }

    /**
     * @return Jsonb annotations not found on CDI proxy class, therefore return a map which can be serialized by Jsonb.
     */
    public Map<String, Object> getAsMap() {
        return new HashMap<>() {{
            put("modifiableProp", modifiableProp);
            put("count", count);
            put("pets", pets);
            put("accounts", accountWithUsernames.getAccountWithUsernames());
        }};
    }
}
