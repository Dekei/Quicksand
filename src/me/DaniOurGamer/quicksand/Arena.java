package me.DaniOurGamer.quicksand;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import me.DaniOurGamer.quicksand.Listener.MovementTrackerTask;
import me.DaniOurGamer.quicksand.api.ChatApi;
import me.DaniOurGamer.quicksand.api.MatchApi;
import me.DaniOurGamer.quicksand.api.PlayerApi;
import me.DaniOurGamer.quicksand.api.SettingsApi;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

public class Arena
{
  private final PlayerApi playerApi;
  private final SettingsApi settingsApi;
  private final MatchApi matchApi;
  private final String name;
  private final QuicksandPlugin plugin;
  private final HashMap<Location, Material> floors = new HashMap();
  private final List<Material> floorMaterials;
  private BukkitTask tracker;
  private final List<String> runners = new ArrayList();
  
  Arena(QuicksandPlugin plugin, String name)
  {
    this.plugin = plugin;
    this.name = name;
    this.playerApi = new PlayerApi(this);
    this.matchApi = new MatchApi(this);
    this.settingsApi = new SettingsApi(plugin, this);
    this.floorMaterials = this.settingsApi.getMaterials();
  }
  
  public void addMovingPlayer(Player player)
  {
    this.runners.add(player.getName());
  }
  
  public void announce(String msg)
  {
    announce(msg, getMatchApi().getContestants());
  }
  
  public void announce(String msg, Player... players)
  {
    this.plugin.getChatApi().announce(getTitle() + msg, players);
  }
  
  public void announceTheWinner()
  {
    Player[] arrayOfPlayer;
    int j = (arrayOfPlayer = getMatchApi().getContestants()).length;
    for (int i = 0; i < j; i++)
    {
      Player player = arrayOfPlayer[i];
      if (getMatchApi().isActiveContestant(player)) {
        broadcast(ChatColor.GREEN + player.getName() + " WON the game!");
      }
    }
    broadcast("The match lasted " + getMatchApi().getFormattedDuration() + "!");
  }
  
  public void breakBlock(Block topBlock)
  {
    if (this.floorMaterials.contains(topBlock.getType()))
    {
      this.floors.put(topBlock.getLocation(), topBlock.getType());
      topBlock.setType(Material.AIR);
      topBlock.getRelative(BlockFace.DOWN).setType(Material.AIR);
    }
  }
  
  public void broadcast(String msg)
  {
    this.plugin.getChatApi().broadcast(getTitle() + msg);
  }
  
  public MatchApi getMatchApi()
  {
    return this.matchApi;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public PlayerApi getPlayerApi()
  {
    return this.playerApi;
  }
  
  public SettingsApi getSettingsApi()
  {
    return this.settingsApi;
  }
  
  public String getTitle()
  {
    return ChatColor.DARK_RED + ".:" + this.name + ":. " + ChatColor.RESET;
  }
  
  public boolean isReady()
  {
    return this.settingsApi.isArenaReady();
  }
  
  public void reset()
  {
    this.matchApi.reset();
    if (this.tracker != null) {
      this.tracker.cancel();
    }
    for (Map.Entry<Location, Material> field : this.floors.entrySet())
    {
      Block topBlock = ((Location)field.getKey()).getBlock();
      topBlock.setType((Material)field.getValue());
      topBlock.getRelative(BlockFace.DOWN).setType(Material.TNT);
    }
  }
  
  public void setup(String location, Player player, String permission, String message)
  {
    if (player.hasPermission(permission))
    {
      getSettingsApi().setLocation(location, player.getLocation());
      announce(message, new Player[] { player });
    }
    else
    {
      announce(ChatColor.RED + "You don't have permission!", new Player[] { player });
    }
  }
  
  public void shoveCampers()
  {
    Player[] arrayOfPlayer;
    int j = (arrayOfPlayer = getMatchApi().getContestants()).length;
    for (int i = 0; i < j; i++)
    {
      Player player = arrayOfPlayer[i];
      if ((getMatchApi().isActiveContestant(player)) && (!this.runners.contains(player.getName())))
      {
        getPlayerApi().shove(player);
        breakBlock(player.getLocation().getBlock().getRelative(BlockFace.DOWN));
      }
    }
    this.runners.clear();
  }
  
  public void startMatch()
  {
    if (getMatchApi().isRunning()) {
      return;
    }
    Player[] arrayOfPlayer;
    int j = (arrayOfPlayer = getMatchApi().getContestants()).length;
    for (int i = 0; i < j; i++)
    {
      Player contestant = arrayOfPlayer[i];
      getPlayerApi().teleport(contestant, "spawn");
    }
    int countdown = getSettingsApi().getCountdown();
    broadcast(ChatColor.YELLOW + "The match starts in " + ChatColor.RED + countdown + " seconds!");
    this.tracker = new MovementTrackerTask(this).runTaskTimer(this.plugin, 20 * (countdown + 1), 20L);
    new GameStartTask(this, countdown).runTaskTimer(this.plugin, 0L, 20L);
  }
}
