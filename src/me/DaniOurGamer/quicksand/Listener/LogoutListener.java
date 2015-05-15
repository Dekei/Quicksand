package me.DaniOurGamer.quicksand.Listener;

import me.DaniOurGamer.quicksand.Events.PlayerLeaveEvent;
import me.DaniOurGamer.quicksand.QuicksandPlugin;
import org.bukkit.Server;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.PluginManager;

public class LogoutListener
  implements Listener
{
  private final QuicksandPlugin plugin;
  
  public LogoutListener(QuicksandPlugin plugin)
  {
    this.plugin = plugin;
  }
  
  @EventHandler
  public void onPlayerKick(PlayerKickEvent e)
  {
    this.plugin.getServer().getPluginManager().callEvent(new PlayerLeaveEvent(e.getPlayer()));
  }
  
  @EventHandler
  public void onPlayerQuit(PlayerQuitEvent e)
  {
    this.plugin.getServer().getPluginManager().callEvent(new PlayerLeaveEvent(e.getPlayer()));
  }
}
