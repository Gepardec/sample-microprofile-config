package at.ihet.samples.microprofile.config;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Thanks to Type Erasure, we need a custom type which is injectable by MicroProfile-Config.
 */
public class AccountUsernames {

    private final Map<String, List<String>> accountWithUsernames;

    public AccountUsernames(Map<String, List<String>> accountWithUsernames) {
        this.accountWithUsernames = accountWithUsernames;
    }

    public Map<String, List<String>> getAccountWithUsernames() {
        return accountWithUsernames;
    }

    public static AccountUsernames empty() {
        return new AccountUsernames(Collections.emptyMap());
    }

    public static AccountUsernames of(Map<String, List<String>> accountWithUsernames) {
        return new AccountUsernames(accountWithUsernames);
    }
}
