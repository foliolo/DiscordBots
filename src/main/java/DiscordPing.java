import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.User;
import discord4j.core.object.entity.channel.MessageChannel;

public class DiscordPing {

    public static void main(String[] args) {

        String token = args[0];

        final DiscordClient client = DiscordClient.create(token);
        final GatewayDiscordClient gateway = client.login().block();

        assert gateway != null;

        login(gateway);
        messagesReceiver(gateway);

        gateway.onDisconnect().block();

    }

    private static void login(GatewayDiscordClient gateway) {

        gateway.on(ReadyEvent.class).subscribe(event -> {
            final User self = event.getSelf();
            System.out.printf("Logged in as %s#%s%n", self.getUsername(), self.getDiscriminator());
        });
    }

    private static void messagesReceiver(GatewayDiscordClient gateway) {

        gateway.on(MessageCreateEvent.class).subscribe(event -> {
            final Message message = event.getMessage();
            if ("!ping".equals(message.getContent())) {
                final MessageChannel channel = message.getChannel().block();
                assert channel != null;
                channel.createMessage("Pong!").block();
            }
        });
    }
}
