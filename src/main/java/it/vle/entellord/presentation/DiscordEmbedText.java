package it.vle.entellord.presentation;

final class DiscordEmbedText {

    private DiscordEmbedText() {
    }

    static String truncate(String value, int limit) {
        if (value == null || value.length() <= limit) {
            return value;
        }
        return value.substring(0, limit - 3) + "...";
    }
}
