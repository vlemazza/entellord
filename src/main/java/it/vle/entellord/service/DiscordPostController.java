package it.vle.entellord.service;

import it.vle.entellocore.model.TextExtractionOutcome;
import it.vle.entellocore.service.ContentExtractionEngine;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;

import java.util.List;
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

    public void submitMessage(String message, MessageChannel channel) {
        try {
            processingExecutor.execute(() -> process(message, channel));
        } catch (RejectedExecutionException exception) {

            LOGGER.log(Level.WARNING, "Discord processing queue is full.");
        }
    }

    private void process(String rawMessage, MessageChannel channel) {
        List<TextExtractionOutcome> outcomes;

        try {
            outcomes = extractionEngine.extractFromText(rawMessage);
        } catch (RuntimeException exception) {

            LOGGER.log(Level.SEVERE, "Unexpected error processing Discord message.", exception);
            return;
        }

        for (TextExtractionOutcome outcome : outcomes) {
            switch (outcome) {
                case TextExtractionOutcome.Success success -> {
                    try {

                        discordService.sendPost(channel, success.post());
                    } catch (RuntimeException exception) {

                        LOGGER.log(Level.SEVERE, "Failed to send extracted post for URL: " + success.url(), exception);
                    }
                }

                case TextExtractionOutcome.Failure failure ->
                        LOGGER.log(Level.WARNING, "Content extraction failed for URL: " + failure.url(), failure.exception());
            }
        }
    }
}
