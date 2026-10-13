package it.vle.entellord.presentation;

import it.vle.entellocore.model.ExtractedPost;
import it.vle.entellocore.model.ExtractedPostPixiv;
import net.dv8tion.jda.api.EmbedBuilder;

public class PixivDiscordPostRenderer implements DiscordPostRenderer {

    @Override
    public boolean supports(ExtractedPost post) {
        return post instanceof ExtractedPostPixiv;
    }

    @Override
    public void render(ExtractedPost post, EmbedBuilder embed) {
        embed.setTitle(DiscordEmbedText.truncate(((ExtractedPostPixiv) post).getTitle(), 256));
    }
}
