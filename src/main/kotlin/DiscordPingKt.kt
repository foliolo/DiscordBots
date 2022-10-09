import discord4j.core.DiscordClient
import discord4j.core.GatewayDiscordClient
import discord4j.core.event.domain.lifecycle.ReadyEvent
import discord4j.core.event.domain.message.MessageCreateEvent
import discord4j.core.`object`.entity.channel.MessageChannel
import java.lang.StringBuilder

object DiscordPingKt {

    private val messageManager = MessageManager.getInstance()

    @JvmStatic
    fun main(args: Array<String>) {
        val token = args[0]

        val client = DiscordClient.create(token)
        val gateway = client.login().block()

        if (gateway != null) {
            login(gateway)
            messagesReceiver(gateway)
            gateway.onDisconnect().block()
        }
    }

    private fun login(gateway: GatewayDiscordClient) {
        gateway.on(ReadyEvent::class.java).subscribe { event: ReadyEvent ->
            val self = event.self
            System.out.printf("Logged in as %s#%s%n", self.username, self.discriminator)
        }
    }

    private fun messagesReceiver(gateway: GatewayDiscordClient) {
        gateway.on(MessageCreateEvent::class.java).subscribe { event: MessageCreateEvent ->
            val message = event.message
            val channel: MessageChannel? = message.channel.block()

            if ("!ping" == message.content) {
                channel?.createMessage("Pong!")?.block()
            } else {
                messageManager.messageFromUser(message.userData.username())
                println(messageManager.toString(message))

                messageManager.getMessageCounterFromUser(message.userData.username()).let { counter ->
                    if (counter % 10 == 0) {
                        channel?.createMessage(

                            StringBuilder()
                                .append("Novato nivel ")
                                .append(counter / 10).toString()
                        )?.block()
                    }
                }
            }
        }
    }
}