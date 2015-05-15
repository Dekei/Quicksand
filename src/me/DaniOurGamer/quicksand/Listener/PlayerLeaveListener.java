package me.DaniOurGamer.quicksand.Listener;

import me.DaniOurGamer.quicksand.Arena;
import me.DaniOurGamer.quicksand.Events.PlayerLeaveEvent;
import me.DaniOurGamer.quicksand.Helper.Metadata;
import me.DaniOurGamer.quicksand.QuicksandPlugin;
import me.DaniOurGamer.quicksand.api.ChatApi;
import me.DaniOurGamer.quicksand.api.MatchApi;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerLeaveListener
  implements Listener
{
  private final QuicksandPlugin plugin;
  
  public PlayerLeaveListener(QuicksandPlugin plugin)
  {
    this.plugin = plugin;
  }
  
  @EventHandler
  public void onPlayerLeave(PlayerLeaveEvent event)
  {
    Player player = event.getPlayer();
    if (!Metadata.isset(player, "arena"))
    {
      this.plugin.getChatApi().announce(ChatColor.RED + "You aren't playing!", new Player[] { player });
      return;
    }
    Arena arena = this.plugin.getArena(Metadata.asString(player, "arena"));
    if (!arena.getMatchApi().isContestant(player))
    {
      Metadata.remove(player, "arena");
      return;
    }
    arena.getMatchApi().removeContestant(player);
    if (arena.getMatchApi().countContestants() == 0) {
      arena.reset();
    }
    if (arena.getMatchApi().isRunning()) {
      arena.announce(ChatColor.GREEN + "Thanks for playing!", new Player[] { player });
    } else {
      arena.announce(ChatColor.GREEN + "Thanks for visiting!", new Player[] { player });
    }
  }
}
