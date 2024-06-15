package dev.snowz.swap;

import dev.snowz.swap.commands.SwapAcceptCommand;
import dev.snowz.swap.commands.SwapAskCommand;
import dev.snowz.swap.commands.SwapCancelCommand;
import dev.snowz.swap.commands.SwapDenyCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class Swap extends JavaPlugin {

    private static Swap instance;

    public static Swap getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        getCommand("swapaccept").setExecutor(new SwapAcceptCommand());
        getCommand("swapask").setExecutor(new SwapAskCommand());
        getCommand("swapcancel").setExecutor(new SwapCancelCommand());
        getCommand("swapdeny").setExecutor(new SwapDenyCommand());
    }


}
