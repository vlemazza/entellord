package it.vle.entellord.presentation;

import it.vle.entellocore.model.ExtractedPost;
import it.vle.entellocore.model.ExtractedPostReddit;
import net.dv8tion.jda.api.EmbedBuilder;

public class RedditDiscordPostRenderer implements DiscordPostRenderer {

    @Override
    public boolean supports(ExtractedPost post) {
        return post instanceof ExtractedPostReddit;
    }

    @Override
    public void render(ExtractedPost post, EmbedBuilder embed) {
        ExtractedPostReddit reddit = (ExtractedPostReddit) post;
        embed.addField("Subreddit", DiscordEmbedText.truncate(reddit.getSubreddit(), 1024), false);
        embed.setTitle(DiscordEmbedText.truncate(reddit.getTitle(), 256), reddit.getUrlExt());
        embed.addField("Upvotes", reddit.getUpvote(), true);
        embed.addField("Comments", reddit.getNumComments(), true);
    }
}
