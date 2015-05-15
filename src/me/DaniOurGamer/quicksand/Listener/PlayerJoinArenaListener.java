package me.DaniOurGamer.quicksand.Listener;

import me.DaniOurGamer.quicksand.Arena;
import me.DaniOurGamer.quicksand.Events.PlayerJoinArenaEvent;
import me.DaniOurGamer.quicksand.Helper.Metadata;
import me.DaniOurGamer.quicksand.QuicksandPlugin;
import me.DaniOurGamer.quicksand.api.MatchApi;
import me.DaniOurGamer.quicksand.api.PlayerApi;
import me.DaniOurGamer.quicksand.api.SettingsApi;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerJoinArenaListener
  implements Listener
{
  private final QuicksandPlugin plugin;
  
  public PlayerJoinArenaListener(QuicksandPlugin plugin)
  {
    this.plugin = plugin;
  }
  
  @EventHandler
  public void onPlayerJoinArena(PlayerJoinArenaEvent event)
  {
    Player player = event.getPlayer();
    Arena arena = event.getArena();
    if ((arena != null) && 
      (!arena.getSettingsApi().isArenaReady()))
    {
      arena.announce(ChatColor.RED + "Arena " + arena.getTitle() + "hasn't been set up yet! You need a lobby, spawn, and end!", new Player[] { player });
      return;
    }
    if (player.getGameMode() == GameMode.CREATIVE)
    {
      arena.announce(ChatColor.RED + "You cannot join while in creative mode!", new Player[] { player });
      return;
    }
    if (arena.getMatchApi().isRunning())
    {
      arena.announce(ChatColor.RED + "A match is already running!", new Player[] { player });
      return;
    }
    if (arena.getMatchApi().isContestant(player))
    {
      arena.announce(ChatColor.RED + "You are already queued to play!", new Player[] { player });
      return;
    }
    if ((Metadata.isset(player, "arena")) && (this.plugin.getArena(Metadata.asString(player, "arena")).getMatchApi().isContestant(player)))
    {
      arena.announce(ChatColor.RED + "You are already queued in another arena!", new Player[] { player });
      return;
    }
    arena.getMatchApi().addContestant(player);
    arena.announce(player.getName() + " joined the game!");
    
    int numPlayers = arena.getMatchApi().countContestants();
    int minPlayers = arena.getSettingsApi().getMinPlayers();
    if (numPlayers < minPlayers)
    {
      arena.announce(ChatColor.YELLOW + String.valueOf(minPlayers - numPlayers) + " more player(s) until the match starts.");
      return;
    }
    if (numPlayers == minPlayers) {
      arena.startMatch();
    }
    arena.getPlayerApi().teleport(player, "spawn");
  }
}
