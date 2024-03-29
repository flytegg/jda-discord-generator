package gg.flyte.discordgenerator.jda

import gg.flyte.discordgenerator.Component
import gg.flyte.discordgenerator.DiscordGenerator
import net.dv8tion.jda.api.entities.*
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel
import net.dv8tion.jda.api.entities.emoji.CustomEmoji
import java.nio.file.Files
import java.nio.file.Path

data class GeneratedExport(
    val html: String
) {
    fun saveToFile(path: Path) {
        Files.writeString(path, html)
    }
}

object JDAGenerator {
    fun exportTextChannel(
        channel: TextChannel,
        title: String = "Export of #${channel.name}",
        date: String? = null,
        reversed: Boolean = false
    ): GeneratedExport {
        DiscordGenerator {
            this.title = title
            this.reversed = reversed
            if (date != null) this.date = date
            addMessages(
                channel.retrieveCompleteMessageHistory().asGeneratorComponent()
            )
        }.generate().apply {
            return GeneratedExport(this)
        }
    }
}

fun TextChannel.retrieveCompleteMessageHistory(
    messageId: String? = null,
    previousMessages: MutableSet<Message> = mutableSetOf()
): MutableSet<Message> =
    with((if (messageId == null) getHistoryFromBeginning(100) else getHistoryAfter(messageId, 100)).complete().retrievedHistory.toMutableSet()) {
        previousMessages.addAll(this)
        if (size == 100) retrieveCompleteMessageHistory(last().id, previousMessages.reversed().toMutableSet()) else previousMessages
    }

fun Message.asGeneratorComponent() = Component.Message(
    author = author.asGeneratorComponent(),
    timestamp = timeCreated.toInstant().toEpochMilli(),
    content = contentRaw,
    embeds = embeds.asGeneratorComponent(),
    images = attachments.filter { it.isImage }.asGeneratorComponent(),
    reactions = reactions.asGeneratorComponent()
)

@JvmName("messagesAsGeneratorComponent")
fun Set<Message>.asGeneratorComponent() = map { it.asGeneratorComponent() }.toList()

fun User.asGeneratorComponent() = Component.Author(
    imageUrl = effectiveAvatarUrl,
    name = effectiveName,
    isBot = isBot
)

fun MessageEmbed.asGeneratorComponent() = Component.Embed(
    title = title,
    description = description
)

@JvmName("embedsAsGeneratorComponent")
fun List<MessageEmbed>.asGeneratorComponent() = map { it.asGeneratorComponent() }.toList()

fun Message.Attachment.asGeneratorComponent() = Component.Image(imageUrl = proxyUrl)

@JvmName("imagesAsGeneratorComponent")
fun List<Message.Attachment>.asGeneratorComponent() = map { it.asGeneratorComponent() }.toList()

fun MessageReaction.asGeneratorComponent() = Component.Reaction(
    imageUrl = if (emoji is CustomEmoji) emoji.asCustom().imageUrl else "",
    amount = count
)

@JvmName("reactionsAsGeneratorComponent")
fun List<MessageReaction>.asGeneratorComponent() = map { it.asGeneratorComponent() }.toList()
