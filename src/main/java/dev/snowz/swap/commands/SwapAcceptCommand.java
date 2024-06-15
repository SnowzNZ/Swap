package dev.snowz.swap.commands;

import dev.snowz.swap.SwapRequest;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class SwapAcceptCommand implements CommandExecutor {
    private final HashMap<UUID, SwapRequest> swapRequests = SwapAskCommand.getSwapRequests();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§c§l(!) §cOnly players can run this command!");
            return true;
        }

        Player player = (Player) sender;
        SwapRequest request = swapRequests.get(player.getUniqueId());

        if (request == null) {
            player.sendMessage("§c§l(!) §cYou have no pending requests to accept!");
            return true;
        }

        Player requester = request.getRequester();
        Location playerLocation = player.getLocation();
        Location requesterLocation = requester.getLocation();

        player.teleport(requesterLocation);
        requester.teleport(playerLocation);

        player.sendMessage("§aYou have swapped positions with " + requester.getName() + ".");
        requester.sendMessage("§aYou have swapped positions with " + player.getName() + ".");

        swapRequests.remove(player.getUniqueId());
        return true;
    }
}
