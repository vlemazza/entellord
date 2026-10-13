package it.vle.entellord.presentation;

import it.vle.entellocore.model.ExtractedPost;
import net.dv8tion.jda.api.EmbedBuilder;

public interface DiscordPostRenderer {

    boolean supports(ExtractedPost post);

    void render(ExtractedPost post, EmbedBuilder embed);
}
