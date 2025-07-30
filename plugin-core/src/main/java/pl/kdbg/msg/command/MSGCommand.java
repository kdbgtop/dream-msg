package pl.kdbg.msg.command;

import cc.dreamcode.command.CommandBase;
import cc.dreamcode.command.DreamSender;
import cc.dreamcode.command.annotation.Arg;
import cc.dreamcode.command.annotation.Command;
import cc.dreamcode.command.annotation.Completion;
import cc.dreamcode.command.annotation.Executor;
import cc.dreamcode.command.annotation.Permission;
import cc.dreamcode.command.annotation.Sender;
import cc.dreamcode.notice.bukkit.BukkitNotice;
import eu.okaeri.injector.annotation.Inject;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import pl.kdbg.msg.config.MessageConfig;

@Command(name = "msg", aliases = {"message", "w"})
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class MSGCommand implements CommandBase {

    private final MessageConfig messageConfig;

    @Permission("dream-msg.msg")
    @Executor
    @Sender(DreamSender.Type.CLIENT)
    @Completion(arg = "target", value = "@all-players")
    @Completion(arg = "message", value = "")
    public BukkitNotice msg(Player sender, @Arg("target") Player target, @Arg("message") String message) {
        if (target == null) {
            return messageConfig.msgPlayerNotFound.with("target", "nieznany gracz");
        }

        if (sender.equals(target)) {
            return messageConfig.cannotDoAtMySelf;
        }

        if (!target.isOnline()) {
            return messageConfig.msgPlayerNotFound.with("target", target.getName());
        }

        messageConfig.senderFormat
                .with("sender", sender.getName())
                .with("receiver", target.getName())
                .with("message", message)
                .send(sender);

        messageConfig.receiverFormat
                .with("sender", sender.getName())
                .with("receiver", target.getName())
                .with("message", message)
                .send(target);

        return null;
    }
}