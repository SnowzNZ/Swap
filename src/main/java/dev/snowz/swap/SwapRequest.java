package dev.snowz.swap;

import org.bukkit.entity.Player;

public class SwapRequest {
    private final Player requester;
    private final Player target;

    public SwapRequest(Player requester, Player target) {
        this.requester = requester;
        this.target = target;
    }

    public Player getRequester() {
        return requester;
    }

    public Player getTarget() {
        return target;
    }
}