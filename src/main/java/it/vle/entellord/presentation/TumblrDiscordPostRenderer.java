package it.vle.entellord.presentation;

import it.vle.entellocore.model.ExtractedPost;
import it.vle.entellocore.model.ExtractedPostTumblr;
import net.dv8tion.jda.api.EmbedBuilder;

public class TumblrDiscordPostRenderer implements DiscordPostRenderer {

    @Override
    public boolean supports(ExtractedPost post) {
        return post instanceof ExtractedPostTumblr;
    }

    @Override
    public void render(ExtractedPost post, EmbedBuilder embed) {
        ExtractedPostTumblr tumblr = (ExtractedPostTumblr) post;
        embed.addField("Tags", DiscordEmbedText.truncate(String.join(", ", tumblr.getTags()), 1024), false);
    }
}
