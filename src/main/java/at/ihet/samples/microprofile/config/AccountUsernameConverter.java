package at.ihet.samples.microprofile.config;

import org.eclipse.microprofile.config.spi.Converter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This is a sample converter which is capable of converting properties for the format
 * 'account1=user1,user2;account2=user1,user2' to a custom model.
 */
public class AccountUsernameConverter implements Converter<AccountUsernames> {

    private static final String SEPARATOR_ACCOUNT = ";";
    private static final String SEPARATOR_ACCOUNT_USERNAME = "=";
    private static final String SEPARATOR_USERNAME = ",";

    @Override
    public AccountUsernames convert(String value) {
        if (value == null || value.isEmpty()) {
            return AccountUsernames.empty();
        }
        final Map<String, List<String>> result;

        try {
            // Split the accounts
            result = new HashMap<>();
            final String[] accounts = value.split(SEPARATOR_ACCOUNT);
            for (final String account : accounts) {
                // Split the account and its usernames
                final String[] accountEntry = account.split(SEPARATOR_ACCOUNT_USERNAME);
                final String accountName = accountEntry[0];
                final String usernameEntry = accountEntry[1];
                result.computeIfAbsent(accountName, (key) -> new ArrayList<>());
                // Split the usernames
                final String[] usernames = usernameEntry.split(SEPARATOR_USERNAME);
                for (final String username : usernames) {
                    result.get(accountName).add(username);
                }
            }

            return AccountUsernames.of(result);
        } catch (Exception e) {
            throw new IllegalArgumentException(String.format("Property cannot be converted because it is of an invalid format. value=%s", value));
        }
    }
}
