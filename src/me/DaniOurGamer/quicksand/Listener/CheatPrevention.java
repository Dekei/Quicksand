package me.DaniOurGamer.quicksand.Listener;

import me.DaniOurGamer.quicksand.Helper.Metadata;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CheatPrevention
  implements Listener
{
  @EventHandler
  public void onBlockBreak(BlockBreakEvent event)
  {
    if (Metadata.isset(event.getPlayer(), "arena")) {
      event.setCancelled(true);
    }
  }
  
  @EventHandler
  public void onBlockPlace(BlockPlaceEvent event)
  {
    if (Metadata.isset(event.getPlayer(), "arena")) {
      event.setCancelled(true);
    }
  }
  
  @EventHandler
  public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event)
  {
    Player player = event.getPlayer();
    if ((Metadata.isset(player, "arena")) && (!event.getMessage().contains("qs")))
    {
      event.setCancelled(true);
      player.sendMessage(ChatColor.GREEN + "To do that you must first leave " + ChatColor.RED + "QUICKSAND");
    }
  }
  
  @EventHandler
  public void onPlayerPvp(EntityDamageEvent event)
  {
    if (((event.getEntity() instanceof Player)) && (Metadata.isset(event.getEntity(), "arena"))) {
      event.setCancelled(true);
    }
  }
}
