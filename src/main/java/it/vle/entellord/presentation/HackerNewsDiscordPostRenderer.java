package it.vle.entellord.presentation;

import it.vle.entellocore.model.ExtractedPost;
import it.vle.entellocore.model.ExtractedPostHackerNews;
import net.dv8tion.jda.api.EmbedBuilder;

public class HackerNewsDiscordPostRenderer implements DiscordPostRenderer {

    @Override
    public boolean supports(ExtractedPost post) {
        return post instanceof ExtractedPostHackerNews;
    }

    @Override
    public void render(ExtractedPost post, EmbedBuilder embed) {
        ExtractedPostHackerNews hackerNews = (ExtractedPostHackerNews) post;
        embed.setTitle(DiscordEmbedText.truncate(hackerNews.getTitle(), 256), hackerNews.getUrl());
        embed.addField("Type", hackerNews.getType(), true);
        embed.addField("Score", hackerNews.getScore(), true);
    }
}
