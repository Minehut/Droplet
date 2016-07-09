package com.minehut.droplet.bukkit;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.ArrayList;
import java.util.logging.Level;

/**
 * Created by luke on 7/5/16.
 */
public class JoinManager implements Listener {
    /**
     * The Join Manager ensures that players are only
     * joining from Minehut's Proxy. Without this,
     * players are able to setup false Bungeecord proxies
     * and spoof names/uuids in order to grief servers.
     *
     * This is effectively the same process as
     * setting up iptables on a vps or box,
     * but not all servers have access to these tools.
     */

    public ArrayList<String> allowedHosts = new ArrayList<>();

    public JoinManager() {
        allowedHosts.add("158.69.123.129");
//        Bukkit.getPluginManager().registerEvents(this, DropletBukkit.getInstance());
    }

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
        if (!allowedHosts.contains(event.getAddress().getHostAddress().toString())) {
            Bukkit.getLogger().log(Level.SEVERE, "Denied login for player using host-address " + event.getAddress().getHostAddress().toString());
            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, ChatColor.DARK_RED + "ERROR: " + ChatColor.RED + "Unable to contact Minehut login servers.");
        }
    }
}
