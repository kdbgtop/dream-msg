package pl.kdbg.msg.command;

import cc.dreamcode.command.CommandBase;
import cc.dreamcode.command.annotation.Async;
import cc.dreamcode.command.annotation.Command;
import cc.dreamcode.command.annotation.Executor;
import cc.dreamcode.command.annotation.Permission;
import cc.dreamcode.notice.bukkit.BukkitNotice;
import eu.okaeri.injector.annotation.Inject;
import lombok.RequiredArgsConstructor;
import pl.kdbg.msg.config.MessageConfig;
import pl.kdbg.msg.config.PluginConfig;

@RequiredArgsConstructor(onConstructor_ = @Inject)
@Command(name = "msgreload")
public class MSGReloadCommand implements CommandBase {

    private final PluginConfig pluginConfig;
    private final MessageConfig messageConfig;

    @Async
    @Permission("dream-msg.reload")
    @Executor(description = "Reload konfiguracji pluginu")
    public BukkitNotice reload() {
        long startTime = System.currentTimeMillis();

        try {
            this.messageConfig.load();
            this.pluginConfig.load();

            long timeTaken = System.currentTimeMillis() - startTime;
            return this.messageConfig.reloaded.with("time", String.valueOf(timeTaken));
        } catch (Exception e) {
            e.printStackTrace();
            return this.messageConfig.reloadError.with("error", e.getMessage());
        }
    }
}