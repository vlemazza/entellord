package it.vle.entellord.service;

import it.vle.entellocore.model.ExtractedPost;
import it.vle.entellocore.service.media.MediaProcessor;
import it.vle.entellord.presentation.DiscordMessageMapper;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.utils.FileUpload;

import java.util.List;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DiscordService {

    private static final Logger LOGGER = Logger.getLogger(DiscordService.class.getName());
    private static final int MAX_ATTACHMENTS = 10;
    private static final long MAX_FILE_SIZE_BYTES = 20L * 1024 * 1024;
    private final DiscordMessageMapper messageMapper;
    private final MediaProcessor mediaProcessor;
    private final long maxFileSizeBytes;

    public DiscordService(DiscordMessageMapper messageMapper) {
        this(messageMapper, new MediaProcessor(), MAX_FILE_SIZE_BYTES);
    }

    public DiscordService(DiscordMessageMapper messageMapper, MediaProcessor mediaProcessor, long maxFileSizeBytes) {
        this.messageMapper = messageMapper;
        this.mediaProcessor = mediaProcessor;
        this.maxFileSizeBytes = maxFileSizeBytes;
    }

    public void sendPost(MessageChannel channel, ExtractedPost post) {

        if (post.hasNoMedia()) {
            try {
                channel.sendMessageEmbeds(messageMapper.createEmbed(post).build())
                        .queue(_ -> closePost(post), exception -> handleUploadFailure(post, exception));
            } catch (RuntimeException exception) {
                closePost(post);
                throw exception;
            }
            return;
        }

        try {
            mediaProcessor.process(post, maxFileSizeBytes);
            EmbedBuilder embed = messageMapper.createEmbed(post);
            List<FileUpload> uploads = messageMapper.buildUploads(post.getMedia());
            sendUploadBatch(channel, embed, uploads, 0, post);
        } catch (RuntimeException exception) {
            closePost(post);
            throw exception;
        }
    }

    private void sendUploadBatch(
            MessageChannel channel,
            EmbedBuilder embed,
            List<FileUpload> uploads,
            int start,
            ExtractedPost post
    ) {

        int end = Math.min(start + MAX_ATTACHMENTS, uploads.size());

        List<FileUpload> batch = uploads.subList(start, end);

        if (start == 0) {

            channel.sendFiles(batch).setEmbeds(embed.build()).queue(
                    _ -> processNextBatch(channel, embed, uploads, end, post),
                    exception -> handleUploadFailure(post, exception)
            );

        } else {

            channel.sendFiles(batch)
                    .queue(
                            _ -> processNextBatch(channel, embed, uploads, end, post),
                            exception -> handleUploadFailure(post, exception)
                    );
        }
    }

    private void processNextBatch(
            MessageChannel channel,
            EmbedBuilder embed,
            List<FileUpload> uploads,
            int end,
            ExtractedPost post
    ) {

        try {
            if (end < uploads.size()) {
                sendUploadBatch(channel, embed, uploads, end, post);
            } else {
                closePost(post);
            }
        } catch (RuntimeException exception) {
            closePost(post);
            throw exception;
        }
    }

    private void closePost(ExtractedPost post) {
        try {
            post.close();
        } catch (IOException exception) {
            LOGGER.log(Level.WARNING, "Unable to remove temporary media files", exception);
        }
    }

    private void handleUploadFailure(ExtractedPost post, Throwable exception) {
        LOGGER.log(Level.WARNING, "Discord send failed; temporary media files will be removed", exception);
        closePost(post);
    }
}
