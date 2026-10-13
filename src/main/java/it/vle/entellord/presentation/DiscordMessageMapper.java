package it.vle.entellord.presentation;

import it.vle.entellocore.model.ExtractedPost;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.utils.FileUpload;

import java.awt.Color;
import java.nio.file.Path;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class DiscordMessageMapper {

    private final List<DiscordPostRenderer> renderers;

    public DiscordMessageMapper() {
        this(List.of(
                new TwitterDiscordPostRenderer(),
                new RedditDiscordPostRenderer(),
                new PixivDiscordPostRenderer(),
                new Ao3DiscordPostRenderer(),
                new TumblrDiscordPostRenderer(),
                new HackerNewsDiscordPostRenderer(),
                new BlueskyDiscordPostRenderer(),
                new YoutubeDiscordPostRenderer()
        ));
    }

    public DiscordMessageMapper(List<DiscordPostRenderer> renderers) {
        this.renderers = List.copyOf(renderers);
    }

    public EmbedBuilder createEmbed(ExtractedPost post) {
        EmbedBuilder embed = new EmbedBuilder().setColor(Color.BLUE);

        if (post.getTimestamp() != null) {
            embed.setTimestamp(Instant.ofEpochSecond(post.getTimestamp()));
        }
        if (post.getAuthor() != null && !post.getAuthor().isBlank()) {
            embed.setAuthor(DiscordEmbedText.truncate(post.getAuthor(), 256));
        }
        if (post.getText() != null && !post.getText().isBlank()) {
            embed.setDescription(DiscordEmbedText.truncate(post.getText(), 4096));
        }
        DiscordFooter footer = DiscordFooter.findFor(post);

        if (footer != null) {
            embed.setFooter(footer.text(), footer.iconUrl());
        }

        renderers.stream()
                .filter(renderer -> renderer.supports(post))
                .forEach(renderer -> renderer.render(post, embed));

        return embed;
    }

    public List<FileUpload> buildUploads(List<Path> media) {
        List<FileUpload> uploads = new ArrayList<>();
        for (Path path : media) {
            uploads.add(FileUpload.fromData(path.toFile(), path.getFileName().toString()));
        }
        return uploads;
    }
}
