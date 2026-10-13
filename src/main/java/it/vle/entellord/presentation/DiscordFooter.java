package it.vle.entellord.presentation;

import it.vle.entellocore.model.*;

import java.util.Map;

record DiscordFooter(String text, String iconUrl) {

    private static final Map<Class<? extends ExtractedPost>, DiscordFooter> FOOTERS =
            Map.ofEntries(
                    Map.entry(ExtractedPostTwitter.class,
                            new DiscordFooter("Twitter", "https://raw.githubusercontent.com/homarr-labs/dashboard-icons/refs/heads/main/png/twitter.png")),
                    Map.entry(ExtractedPostBluesky.class,
                            new DiscordFooter("Bluesky", "https://raw.githubusercontent.com/homarr-labs/dashboard-icons/refs/heads/main/png/bluesky.png")),
                    Map.entry(ExtractedPostTiktok.class,
                            new DiscordFooter("TikTok", "https://raw.githubusercontent.com/homarr-labs/dashboard-icons/refs/heads/main/png/tiktok.png")),
                    Map.entry(ExtractedPostInstagram.class,
                            new DiscordFooter("Instagram", "https://raw.githubusercontent.com/homarr-labs/dashboard-icons/refs/heads/main/png/instagram.png")),
                    Map.entry(ExtractedPostReddit.class,
                            new DiscordFooter("Reddit", "https://raw.githubusercontent.com/homarr-labs/dashboard-icons/refs/heads/main/png/reddit.png")),
                    Map.entry(ExtractedPostBilibili.class,
                            new DiscordFooter("Bilibili", "https://raw.githubusercontent.com/homarr-labs/dashboard-icons/refs/heads/main/png/bilibili.png")),
                    Map.entry(ExtractedPostPixiv.class,
                            new DiscordFooter("Pixiv", "https://upload.wikimedia.org/wikipedia/commons/e/ed/Pixiv.png")),
                    Map.entry(ExtractedPostAO3.class,
                            new DiscordFooter("Archive of Our Own", "https://archiveofourown.org/images/ao3_logos/logo_42.png")),
                    Map.entry(ExtractedPostPoipiku.class,
                            new DiscordFooter("Poipiku", "https://cdn.poipiku.com/assets/img/pc_top_title-03.png")),
                    Map.entry(ExtractedPostTumblr.class,
                            new DiscordFooter("Tumblr", "https://raw.githubusercontent.com/homarr-labs/dashboard-icons/refs/heads/main/png/tumblr.png")),
                    Map.entry(ExtractedPostPinterest.class,
                            new DiscordFooter("Pinterest", "https://raw.githubusercontent.com/homarr-labs/dashboard-icons/refs/heads/main/png/pinterest.png")),
                    Map.entry(ExtractedPostHackerNews.class,
                            new DiscordFooter("Hacker News", "https://raw.githubusercontent.com/homarr-labs/dashboard-icons/refs/heads/main/png/hacker-news.png")),
                    Map.entry(ExtractedPostYoutube.class,
                            new DiscordFooter("YouTube", "https://raw.githubusercontent.com/homarr-labs/dashboard-icons/refs/heads/main/png/youtube.png"))
            );

    static DiscordFooter findFor(ExtractedPost post) {
        return FOOTERS.get(post.getClass());
    }
}