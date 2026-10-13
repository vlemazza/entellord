package it.vle.entellord.presentation;

import it.vle.entellocore.model.ExtractedPost;
import it.vle.entellocore.model.ExtractedPostBluesky;
import net.dv8tion.jda.api.EmbedBuilder;

public class BlueskyDiscordPostRenderer implements DiscordPostRenderer {
    @Override
    public boolean supports(ExtractedPost post) { return post instanceof ExtractedPostBluesky; }

    @Override
    public void render(ExtractedPost post, EmbedBuilder embed) {
        ExtractedPostBluesky bluesky = (ExtractedPostBluesky) post;
        embed.setAuthor(bluesky.getAuthor(), null, bluesky.getAvatar());
        if (bluesky.hasEmbeddedPost()) {
            ExtractedPost quoted = bluesky.getEmbeddedPost();
            String value = quote(quoted.getAuthor(), quoted.getText());
            if (!value.isBlank()) embed.addField("", value, false);
        }
        if (bluesky.getEmbedExternal() != null) {
            ExtractedPostBluesky.EmbedExternal external = bluesky.getEmbedExternal();
            String value = quote(external.title(), external.description(), external.uri());
            if (!value.isBlank()) embed.addField("", value, false);
        }
        embed.addField("Reply", bluesky.getReplyCount(), true);
        embed.addField("Repost", bluesky.getRepostCount(), true);
        embed.addField("Like", bluesky.getLikeCount(), true);
    }

    private String quote(String title, String... lines) {
        StringBuilder value = new StringBuilder();
        if (title != null && !title.isBlank()) value.append("> **").append(title).append("**\n");
        for (String line : lines) {
            if (line != null && !line.isBlank()) value.append("> ").append(line).append("\n");
        }
        return DiscordEmbedText.truncate(value.toString().trim(), 1024);
    }
}
