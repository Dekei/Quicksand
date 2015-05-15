package me.DaniOurGamer.quicksand.api;

import me.DaniOurGamer.quicksand.QuicksandPlugin;
import org.bukkit.Server;
import org.bukkit.entity.Player;

public class ChatApi
{
  private final QuicksandPlugin plugin;
  private final String prefix;
  
  public ChatApi(QuicksandPlugin plugin, String prefix)
  {
    this.plugin = plugin;
    this.prefix = prefix;
  }
  
  public void announce(String msg, Player... players)
  {
    Player[] arrayOfPlayer;
    int j = (arrayOfPlayer = players).length;
    for (int i = 0; i < j; i++)
    {
      Player player = arrayOfPlayer[i];
      tell(player, msg);
    }
  }
  
  public void broadcast(String msg)
  {
    this.plugin.getServer().broadcastMessage(this.prefix + msg);
  }
  
  public void tell(Player player, String msg)
  {
    player.sendMessage(this.prefix + msg);
  }
}
