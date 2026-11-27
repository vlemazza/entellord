# Entellord

[![Java](https://img.shields.io/badge/Java-25-orange?logo=openjdk)](https://openjdk.org/)
[![Maven](https://img.shields.io/badge/Maven-Build-C71A36?logo=apachemaven)](https://maven.apache.org/)
[![Discord](https://img.shields.io/badge/Discord-Bot-5865F2?logo=discord&logoColor=white)](https://discord.com/)
[![JDA](https://img.shields.io/badge/JDA-6.5.0-5865F2?logo=discord&logoColor=white)](https://github.com/discord-jda/JDA)
[![Docker](https://img.shields.io/badge/Docker-2496ED?logo=docker&logoColor=fff)](https://www.docker.com/)
[![Entelloverse](https://img.shields.io/badge/Entelloverse-10)]()


Entellord is a Discord bot built in Java that uses [EntelloCore](https://github.com/vlemazza/entellocore) to retrieve posts and media from supported social media platforms.

Send a supported social media URL in Discord, and Entellord extracts the post and its media, making the content available directly in the conversation.

## Features

- Extract posts and media from supported social media URLs.
- Integrate with EntelloCore for content extraction.
- Process requests asynchronously.
- Handle multiple requests through a dedicated thread pool.
- Use `gallery-dl` and `yt-dlp` for media extraction.

## Requirements

- Git
- Docker
- A Discord bot token
- Access to the social media platforms you want to use

## Installation

### 1. Clone the repositories

Clone both repositories into the same parent directory:

```bash
git clone https://github.com/vlemazza/entellord.git
git clone https://github.com/vlemazza/entellocore.git
```

The directory structure should be similar to the following:

```text
.
├── config/
│   ├── gallery-dl.conf
│   └── session/
├── entellocore/
│   ├── pom.xml
│   └── src/
└── entellord/
    ├── pom.xml
    ├── Dockerfile
    └── docker-compose.yaml
```

### 2. Configure session cookies

Some platforms require authentication cookies to retrieve posts and media. Entellord uses `gallery-dl` configuration to specify the cookie files and other platform-specific settings.

Create `config/session/` in the shared parent directory and export the cookies from your browser in Netscape format there.

The [Export Cookies](https://github.com/rotemdan/ExportCookies) browser extension can be used to export cookies in a compatible format.

```text
config/session/
├── facebook_cookies.txt
├── instagram_cookies.txt
└── tiktok_cookies.txt
```

The actual filenames must match the paths specified in the `gallery-dl.conf` configuration.

### 3. Configure gallery-dl

If `config/gallery-dl.conf` does not already exist, create it from EntelloCore's shared template:

```bash
cp entellocore/config/gallery-dl.conf.example config/gallery-dl.conf
```

Keep the runtime config and session cookies in the shared parent `config/` directory. Entellogram's meme and random-reply files remain in its own `config/`; the extraction process reads Gallery-dl configuration and cookies only from the shared parent directory.

The Docker configuration uses `/app/config/session/` paths, matching the `config/` directory mounted by `docker-compose.yaml`.

The template already uses the container paths expected by both applications. Update its credential placeholders and cookie filenames in the shared runtime config.


### 4. Configure the Discord bot token

Create a Discord application in the [Discord Developer Portal](https://discord.com/developers/applications), and obtain its token.

Rename `.env.example` in `.env`.

Set the `DISCORD_TOKEN` in the `.env` file:

```dotenv
DISCORD_TOKEN=<<TOKEN>>
```

### 5. Build and start Entellord

From the `entellord` directory, run:

```bash
docker-compose up --build -d
```

## Acknowledgements

Thanks to the maintainers and contributors of these projects:

- [JDA](https://github.com/discord-jda/JDA) - Java Discord API.
- [gallery-dl](https://github.com/mikf/gallery-dl)
- [yt-dlp](https://github.com/yt-dlp/yt-dlp)
- [Dashboard Icons](https://github.com/homarr-labs/dashboard-icons) - icons used in the footer of Discord embed messages.

