package it.vle.entellord;

import it.vle.entellocore.service.ContentExtractionEngine;
import it.vle.entellocore.service.EntelloContentExtractionEngine;
import it.vle.entellord.presentation.DiscordMessageMapper;
import it.vle.entellord.listener.MessageReceiveListener;
import it.vle.entellord.service.DiscordService;
import it.vle.entellord.service.DiscordPostController;
import it.vle.entellord.utils.ConfigValidator;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.util.EnumSet;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Main {

    private static final int PROCESSING_WORKERS = 2;
    private static final int PROCESSING_QUEUE_CAPACITY = 100;

    static void main() {

        ConfigValidator.validate();

        String token = System.getenv("DISCORD_TOKEN");

        ContentExtractionEngine extractionEngine = new EntelloContentExtractionEngine();

        DiscordMessageMapper messageMapper = new DiscordMessageMapper();

        DiscordService discord = new DiscordService(messageMapper);

        ExecutorService processingExecutor = new ThreadPoolExecutor(
                PROCESSING_WORKERS,
                PROCESSING_WORKERS,
                0L,
                TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(PROCESSING_QUEUE_CAPACITY)
        );

        DiscordPostController postRequestService = new DiscordPostController(
                extractionEngine,
                discord,
                processingExecutor
        );

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            processingExecutor.shutdown();
        }));

        JDABuilder.createLight(
                        token,
                        EnumSet.of(
                                GatewayIntent.GUILD_MESSAGES,
                                GatewayIntent.MESSAGE_CONTENT,
                                GatewayIntent.DIRECT_MESSAGES
                        ))
                .addEventListeners(
                        new MessageReceiveListener(postRequestService)
                )
                .build();
    }
}