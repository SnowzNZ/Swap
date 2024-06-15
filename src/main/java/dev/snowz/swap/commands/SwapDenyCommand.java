package dev.snowz.swap.commands;

import dev.snowz.swap.SwapRequest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class SwapDenyCommand implements CommandExecutor {
    private final HashMap<UUID, SwapRequest> swapRequests = SwapAskCommand.getSwapRequests();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§c§l(!) §cOnly players can run this command!");
            return true;
        }

        Player player = (Player) sender;
        SwapRequest request = swapRequests.get(player.getUniqueId());

        if (request != null) {
            Player requester = request.getRequester();
            swapRequests.remove(player.getUniqueId());
            swapRequests.remove(requester.getUniqueId());

            player.sendMessage("§6Denied swap request from §c" + requester.getName() + "§6.");
            requester.sendMessage("§c" + player.getName() + "§6 denied your swap request.");
        } else {
            player.sendMessage("§cYou don't have any pending swap requests.");
        }
        return true;
    }
}
