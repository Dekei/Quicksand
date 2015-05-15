package me.DaniOurGamer.quicksand.Listener;

import me.DaniOurGamer.quicksand.Arena;
import me.DaniOurGamer.quicksand.Events.PlayerJoinArenaEvent;
import me.DaniOurGamer.quicksand.QuicksandPlugin;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.PluginManager;

public class SignListener
  implements Listener
{
  private final QuicksandPlugin plugin;
  
  public SignListener(QuicksandPlugin plugin)
  {
    this.plugin = plugin;
  }
  
  @EventHandler
  public void onSignClick(PlayerInteractEvent event)
  {
    if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
      return;
    }
    Material material = event.getClickedBlock().getType();
    if ((material != Material.SIGN_POST) && (material != Material.WALL_SIGN) && (material != Material.SIGN)) {
      return;
    }
    Sign sign = (Sign)event.getClickedBlock().getState();
    if ((!"[quicksand]".equalsIgnoreCase(sign.getLine(0))) || ("".equals(sign.getLine(1)))) {
      return;
    }
    this.plugin.getServer().getPluginManager().callEvent(new PlayerJoinArenaEvent(event.getPlayer(), this.plugin.getArena(sign.getLine(1))));
  }
  
  @EventHandler
  public void onSignCreate(SignChangeEvent event)
  {
    if (!"[quicksand]".equalsIgnoreCase(event.getLine(0))) {
      return;
    }
    Player player = event.getPlayer();
    if ("".equals(event.getLine(1)))
    {
      player.sendMessage(ChatColor.RED + "The name of the arena is missing!");
      event.setCancelled(true);
      return;
    }
    if (!this.plugin.getArena(event.getLine(1)).isReady())
    {
      player.sendMessage(ChatColor.RED + "Arena \"" + event.getLine(1) + "\" is not ready to play!");
      event.setCancelled(true);
      return;
    }
    if (player.hasPermission("quicksand.sign"))
    {
      event.setLine(2, "");
      event.setLine(3, ChatColor.DARK_BLUE + "Click to play!");
      player.sendMessage(ChatColor.GREEN + "Sign created! Right-click it to play!");
    }
  }
}
