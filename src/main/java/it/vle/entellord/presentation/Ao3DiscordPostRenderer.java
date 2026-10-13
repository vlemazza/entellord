package it.vle.entellord.presentation;

import it.vle.entellocore.model.ExtractedPost;
import it.vle.entellocore.model.ExtractedPostAO3;
import net.dv8tion.jda.api.EmbedBuilder;

public class Ao3DiscordPostRenderer implements DiscordPostRenderer {
    @Override
    public boolean supports(ExtractedPost post) { return post instanceof ExtractedPostAO3; }

    @Override
    public void render(ExtractedPost post, EmbedBuilder embed) {
        ExtractedPostAO3 ao3 = (ExtractedPostAO3) post;
        embed.setTitle(DiscordEmbedText.truncate(ao3.getTitle(), 256));
        embed.addField("Rating",DiscordEmbedText.truncate(String.join(", ", ao3.getRating()), 1024), true);
        embed.addField("Category", DiscordEmbedText.truncate(String.join(", ", ao3.getCategories()), 1024), true);
        embed.addField("Fandom", DiscordEmbedText.truncate(String.join(", ", ao3.getFandoms()), 1024), true);
        embed.addField("Relationship", DiscordEmbedText.truncate(String.join(", ", ao3.getRelationships()), 1024), true);
        embed.addField("Characters", DiscordEmbedText.truncate(String.join(", ", ao3.getCharacters()), 1024), true);
        String stats = "Words: " + ao3.getWords() + "\nChapters: " + ao3.getChapters()
                + "\nKudos: " + ao3.getKudos() + "\nHits: " + ao3.getHits();
        embed.addField("Stats", stats, true);
    }
}
