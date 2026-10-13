package it.vle.entellord.presentation;

import it.vle.entellocore.model.ExtractedPost;
import it.vle.entellocore.model.ExtractedPostYoutube;
import net.dv8tion.jda.api.EmbedBuilder;

public class YoutubeDiscordPostRenderer implements DiscordPostRenderer {

    @Override
    public boolean supports(ExtractedPost post) {
        return post instanceof ExtractedPostYoutube;
    }

    @Override
    public void render(ExtractedPost post, EmbedBuilder embed) {
        ExtractedPostYoutube youtubePost = (ExtractedPostYoutube) post;
        if (youtubePost.getChannel() != null && !youtubePost.getChannel().isBlank()) {
            embed.addField("Channel", DiscordEmbedText.truncate(youtubePost.getChannel(), 1024), false);
        }
    }
}