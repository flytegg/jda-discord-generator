# JDA Discord Generator

## How to use 

Gradle (Kotlin DSL)
```kotlin
maven("https://repo.flyte.gg/releases")

implementation("gg.flyte:discordgenerator-jda:1.0.1")
```

Gradle (Groovy DSL)
```groovy
maven {
    url "https://repo.flyte.gg/releases"
}

implementation "gg.flyte:discordgenerator-jda:1.0.1"
```

Maven
```xml
<repository>
    <id>flyte-repository-releases</id>
    <name>Flyte Repository</name>
    <url>https://repo.flyte.gg/releases</url>
</repository>

<dependency>
  <groupId>gg.flyte</groupId>
  <artifactId>discordgenerator-jda</artifactId>
  <version>1.0.1</version>
</dependency>
```

## Example usage

Kotlin
```kotlin
const val TOKEN = "your token"
const val TEXT_CHANNEL_ID = "your text channel id"

fun main() {
    val jda = JDABuilder.createDefault(TOKEN)
        .enableIntents(GatewayIntent.MESSAGE_CONTENT)
        .build()
        .awaitReady()

    val channel = jda.getTextChannelById(TEXT_CHANNEL_ID)!!
    val generatedOutput = JDAGenerator.exportTextChannel(channel, title = "Export of #${channel.name}", date = "AAAAAA", reversed = true)
    generatedOutput.saveToFile(Path.of("output.html"))
}
```

Java
```java
private static final String TOKEN = "your token";
private static final String TEXT_CHANNEL_ID = "your text channel id";

public static void main(String[] args) {
    JDA jda = JDABuilder.createDefault(TOKEN)
        .enableIntents(GatewayIntent.MESSAGE_CONTENT)
        .build()
        .awaitReady();

    TextChannel channel = jda.getTextChannelById(TEXT_CHANNEL_ID);
    GeneratedExport generatedOutput = JDAGenerator.exportTextChannel(channel, "Export of #" + channel.getName(), "AAAAAA", true);
    generatedOutput.saveToFile(Path.of("output.html"));
}
```
