package it.vle.entellord.service;

import it.vle.entellocore.service.ContentExtractionEngine;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;

import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DiscordPostController {

    private static final Logger LOGGER = Logger.getLogger(DiscordPostController.class.getName());
    private final ContentExtractionEngine extractionEngine;
    private final DiscordService discordService;
    private final Executor processingExecutor;

    public DiscordPostController(
            ContentExtractionEngine extractionEngine,
            DiscordService discordService,
            Executor processingExecutor
    ) {
        this.extractionEngine = extractionEngine;
        this.discordService = discordService;
        this.processingExecutor = processingExecutor;
    }

    public void submit(String rawMessage, MessageChannel channel) {
        try {
            processingExecutor.execute(() -> process(rawMessage, channel));
        } catch (RejectedExecutionException exception) {
            LOGGER.log(Level.WARNING, "Discord processing queue is full.");
        }
    }

    private void process(String rawMessage, MessageChannel channel) {
        try {
            extractionEngine.process(rawMessage)
                    .ifPresent(post -> discordService.sendPost(channel, post));
        } catch (Exception exception) {
            LOGGER.log(Level.SEVERE, "Error processing Discord message", exception);
        }
    }
}
