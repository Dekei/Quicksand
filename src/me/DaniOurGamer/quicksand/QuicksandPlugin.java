package me.DaniOurGamer.quicksand;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import me.DaniOurGamer.quicksand.Helper.Metadata;
import me.DaniOurGamer.quicksand.Listener.BlockListener;
import me.DaniOurGamer.quicksand.Listener.CheatPrevention;
import me.DaniOurGamer.quicksand.Listener.LogoutListener;
import me.DaniOurGamer.quicksand.Listener.PlayerJoinArenaListener;
import me.DaniOurGamer.quicksand.Listener.PlayerLeaveListener;
import me.DaniOurGamer.quicksand.Listener.SignListener;
import me.DaniOurGamer.quicksand.api.ChatApi;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class QuicksandPlugin
  extends JavaPlugin
{
  private final Map<String, Arena> arenas = new HashMap();
  private ChatApi chatApi;
  
  public Arena getArena(String name)
  {
    if (!this.arenas.containsKey(name.toLowerCase()))
    {
      Arena arena = new Arena(this, name);
      this.arenas.put(name.toLowerCase(), arena);
    }
    return (Arena)this.arenas.get(name.toLowerCase());
  }
  
  public ChatApi getChatApi()
  {
    return this.chatApi;
  }
  
  public void onDisable()
  {
    for (Arena arena : this.arenas.values()) {
      arena.reset();
    }
  }
  
  public void onEnable()
  {
    long start = System.currentTimeMillis();
    saveDefaultConfig();
    
    PluginManager pm = getServer().getPluginManager();
    pm.registerEvents(new SignListener(this), this);
    pm.registerEvents(new PlayerJoinArenaListener(this), this);
    pm.registerEvents(new PlayerLeaveListener(this), this);
    pm.registerEvents(new BlockListener(this), this);
    pm.registerEvents(new CheatPrevention(), this);
    pm.registerEvents(new LogoutListener(this), this);
    
    getCommand("quicksand").setExecutor(new QuicksandCommandExecutor(this));
    getCommand("qs").setExecutor(new QuicksandCommandExecutor(this));
    this.chatApi = new ChatApi(this, ChatColor.YELLOW + "[Quicksand] " + ChatColor.RESET);
    
    Metadata.setPlugin(this);
    
    getLogger().info("by Firebreath15 loaded in " + (System.currentTimeMillis() - start) / 1000L + " seconds.");
  }
}
