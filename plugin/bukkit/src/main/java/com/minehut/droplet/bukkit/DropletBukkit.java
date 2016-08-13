package com.minehut.droplet.bukkit;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * Created by luke on 7/3/16.
 */
public class DropletBukkit extends JavaPlugin {
    private static DropletBukkit instance;

    public String key = null;
    public boolean doUpdate = true;

    private JoinManager joinManager;

    @Override
    public void onEnable() {
        instance = this;

        FileConfiguration config = getConfig();
        config.options().copyDefaults(true);
        saveConfig();

        if (!config.contains("secret-key")) {
            Bukkit.getLogger().log(Level.SEVERE, "Key was not found in config.yml, disabling...");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        this.key = config.getString("secret-key");

        this.joinManager = new JoinManager();

        Bukkit.getScheduler().scheduleAsyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                if (doUpdate) {
                    sendStatusUpdate();
                }
            }
        }, 0L, 100L); //update every 5 seconds
    }

    /**
     * Responsible for sending status
     * post requests to the droplet
     * web server.
     */
    public void sendStatusUpdate() {
        try {
            HttpClient httpclient = HttpClients.createDefault();
            HttpPost httppost = new HttpPost("http://mchost.co:8003/status/bukkit");

            //Request parameters and other properties.
            List<NameValuePair> params = new ArrayList<NameValuePair>(2);
            params.add(new BasicNameValuePair("key", key));
            params.add(new BasicNameValuePair("player_count", Integer.toString(Bukkit.getOnlinePlayers().size())));
            params.add(new BasicNameValuePair("max_players", Integer.toString(Bukkit.getMaxPlayers())));
            params.add(new BasicNameValuePair("ip", Bukkit.getIp()));
            params.add(new BasicNameValuePair("port", Integer.toString(Bukkit.getPort())));
            params.add(new BasicNameValuePair("motd", Bukkit.getMotd()));
            httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

            //Execute and get the response.
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                InputStream instream = entity.getContent();
                try {

                } finally {
                    instream.close();
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static DropletBukkit getInstance() {
        return instance;
    }
}
