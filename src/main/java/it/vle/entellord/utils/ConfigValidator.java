package it.vle.entellord.utils;

import it.vle.entellocore.utils.CoreConfigValidator;

public class ConfigValidator {

    public static void validate() {
        String token = System.getenv("DISCORD_TOKEN");

        if (token == null || token.isBlank()) {
            throw new RuntimeException("Missing DISCORD_TOKEN");
        }

        CoreConfigValidator.validate();

        System.out.println("Discord configuration OK");
    }
}
