package it.vle.entellord.presentation;

import it.vle.entellocore.model.ExtractedPost;
import it.vle.entellocore.model.ExtractedPostTwitter;
import net.dv8tion.jda.api.EmbedBuilder;

public class TwitterDiscordPostRenderer implements DiscordPostRenderer {

    @Override
    public boolean supports(ExtractedPost post) {
        return post instanceof ExtractedPostTwitter;
    }

    @Override
    public void render(ExtractedPost post, EmbedBuilder embed) {
        ExtractedPostTwitter twitter = (ExtractedPostTwitter) post;
        embed.setAuthor(twitter.getAuthor(), twitter.getUrlProfile(), twitter.getProfileImageUrl());
        embed.addField("Reply", twitter.getReplyCount(), true);
        embed.addField("Retweet", twitter.getRetweetCount(), true);
        embed.addField("Quote", twitter.getQuoteCount(), true);
        embed.addField("Views", twitter.getViewCount(), true);
    }
}
