package me.DaniOurGamer.quicksand.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import me.DaniOurGamer.quicksand.Arena;
import me.DaniOurGamer.quicksand.Helper.Metadata;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;

public class MatchApi
{
  private final HashMap<String, Boolean> contestants = new HashMap();
  private boolean sessionRunning;
  private final Arena arena;
  private long sessionStart;
  
  public MatchApi(Arena arena)
  {
    this.arena = arena;
  }
  
  public void addContestant(Player player)
  {
    if (isActiveContestant(player)) {
      return;
    }
    this.arena.getPlayerApi().storeInventory(player);
    this.arena.getPlayerApi().teleport(player, "lobby");
    this.contestants.put(player.getName(), Boolean.valueOf(true));
    
    Metadata.set(player, "arena", this.arena.getName());
  }
  
  public void changeSpectatorMode(Player contestant, boolean enabled)
  {
    this.contestants.put(contestant.getName(), Boolean.valueOf(!enabled));
    
    contestant.setAllowFlight(enabled);
    contestant.setFlying(enabled);
    if (enabled)
    {
      Player[] arrayOfPlayer;
      int j = (arrayOfPlayer = getContestants()).length;
      for (int i = 0; i < j; i++)
      {
        Player player = arrayOfPlayer[i];
        player.hidePlayer(contestant);
      }
    }
    else
    {
      for (Player player : Bukkit.getServer().getOnlinePlayers()) {
        player.showPlayer(contestant);
      }
    }
  }
  
  public int countActiveContestants()
  {
    if (!this.contestants.containsValue(Boolean.valueOf(true))) {
      return 0;
    }
    int counter = 0;
    for (Map.Entry<String, Boolean> contestant : this.contestants.entrySet()) {
      if (((Boolean)contestant.getValue()).booleanValue()) {
        counter++;
      }
    }
    return counter;
  }
  
  public int countContestants()
  {
    return this.contestants.size();
  }
  
  public Player[] getContestants()
  {
    List<Player> contestants = new ArrayList();
    for (Map.Entry<String, Boolean> contestant : this.contestants.entrySet())
    {
      Player player = Bukkit.getServer().getPlayer((String)contestant.getKey());
      if (player.isOnline()) {
        contestants.add(player);
      } else {
        this.contestants.remove(contestant.getKey());
      }
    }
    return (Player[])contestants.toArray(new Player[contestants.size()]);
  }
  
  public long getDuration()
  {
    return (System.currentTimeMillis() - this.sessionStart) / 1000L;
  }
  
  public String getFormattedDuration()
  {
    long seconds = getDuration();
    return String.format("%d:%02d:%02d", new Object[] { Long.valueOf(seconds / 3600L), Long.valueOf(seconds % 3600L / 60L), Long.valueOf(seconds % 60L) });
  }
  
  public boolean isActiveContestant(Player player)
  {
    return (isContestant(player)) && (((Boolean)this.contestants.get(player.getName())).booleanValue());
  }
  
  public boolean isContestant(Player player)
  {
    return this.contestants.containsKey(player.getName());
  }
  
  public boolean isRunning()
  {
    if (countContestants() == 0) {
      this.sessionRunning = false;
    }
    return this.sessionRunning;
  }
  
  public boolean isSpectator(Player player)
  {
    return (isContestant(player)) && (!((Boolean)this.contestants.get(player.getName())).booleanValue());
  }
  
  public void removeContestant(Player player)
  {
    if (!isContestant(player)) {
      return;
    }
    this.arena.getPlayerApi().restoreInventory(player);
    this.arena.getPlayerApi().teleport(player, "end");
    changeSpectatorMode(player, false);
    this.contestants.remove(player.getName());
    
    Metadata.remove(player, "arena");
  }
  
  public void reset()
  {
    Player[] arrayOfPlayer;
    int j = (arrayOfPlayer = getContestants()).length;
    for (int i = 0; i < j; i++)
    {
      Player player = arrayOfPlayer[i];
      removeContestant(player);
    }
    this.contestants.clear();
  }
  
  public void start()
  {
    this.sessionRunning = true;
    this.sessionStart = System.currentTimeMillis();
  }
}
