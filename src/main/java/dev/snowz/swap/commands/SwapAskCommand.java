package dev.snowz.swap.commands;

import dev.snowz.swap.Swap;
import dev.snowz.swap.SwapRequest;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class SwapAskCommand implements CommandExecutor, TabCompleter {
    public final static HashMap<UUID, SwapRequest> swapRequests = new HashMap<>();

    int expiresAfterSeconds = 120;

    public static HashMap<UUID, SwapRequest> getSwapRequests() {
        return swapRequests;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§c§l(!) §cOnly players can run this command!");
            return true;
        }

        Player player = (Player) sender;

        if (!(args.length == 1)) {
            player.sendMessage("§c§l(!) §cYou must specify a player to swap positions with!");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            player.sendMessage("§c§l(!) §cThe specified player cannot be found!");
            return true;
        }

        if (target == player) {
            player.sendMessage("§c§l(!) §cYou cannot swap positions with yourself!");
            return true;
        }

        SwapRequest request = new SwapRequest(player, target);
        swapRequests.put(target.getUniqueId(), request);
        player.sendMessage("§6Swap request sent to §c" + target.getName());
        player.sendMessage("§6To cancel this request, type §c/swapcancel");

        target.sendMessage("§c" + player.getName() + " §6has requested to swap positions with you");
        target.sendMessage("§6To swap, type §c/swapaccept");
        target.sendMessage("§6To deny this request, type §c/swapdeny");
        target.sendMessage("§6This request will expire after §c" + expiresAfterSeconds + " seconds§6.");


        Bukkit.getScheduler().runTaskLater(Swap.getInstance(), () -> {
            if (swapRequests.containsKey(target.getUniqueId())) {
                swapRequests.remove(target.getUniqueId());
                player.sendMessage("§6Your request to swap positions with §c" + target.getName() + " §6has expired.");
                target.sendMessage("§c" + player.getName() + "'s §6request to swap positions has expired.");
            }
        }, 20L * expiresAfterSeconds);

        return true;
    }


    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            List<String> onlinePlayers = new ArrayList<>();
            for (Player player : Bukkit.getOnlinePlayers()) {
                onlinePlayers.add(player.getName());
            }
            return onlinePlayers;
        }
        return null;
    }
}
