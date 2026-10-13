package it.vle.entellord.listener;

import it.vle.entellord.service.DiscordPostController;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageReceiveListener extends ListenerAdapter {
    private final DiscordPostController postRequestService;
    public MessageReceiveListener(DiscordPostController postRequestService) {
        this.postRequestService = postRequestService;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        if (event.getAuthor().isBot()) {
            return;
        }

        String message = event.getMessage().getContentRaw();

        postRequestService.submit(message, event.getChannel());
    }
}
