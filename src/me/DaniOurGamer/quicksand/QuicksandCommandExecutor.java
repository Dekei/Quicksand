package me.DaniOurGamer.quicksand;

import me.DaniOurGamer.quicksand.Events.PlayerJoinArenaEvent;
import me.DaniOurGamer.quicksand.Events.PlayerLeaveEvent;
import me.DaniOurGamer.quicksand.api.ChatApi;
import me.DaniOurGamer.quicksand.api.SettingsApi;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;

public class QuicksandCommandExecutor
  implements CommandExecutor
{
  private final QuicksandPlugin plugin;
  
  public QuicksandCommandExecutor(QuicksandPlugin plugin)
  {
    this.plugin = plugin;
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
  {
    if ((!cmd.getName().equalsIgnoreCase("quicksand")) && (!cmd.getName().equalsIgnoreCase("qs"))) {
      return false;
    }
    if (!(sender instanceof Player)) {
      return false;
    }
    Player player = (Player)sender;
    Arena arena = args.length > 1 ? this.plugin.getArena(args[1]) : null;
    String str1;
    switch ((str1 = args.length > 0 ? args[0].toLowerCase() : "help").hashCode())
    {
    case -905782983: 
      if (str1.equals("setend")) {}
      break;
    case -804429082: 
      if (str1.equals("configure")) {}
      break;
    case 3267882: 
      if (str1.equals("join")) {}
      break;
    case 102846135: 
      if (str1.equals("leave")) {}
      break;
    case 1427410100: 
      if (str1.equals("setlobby")) {
        break;
      }
      break;
    case 1433904217: 
      if (!str1.equals("setspawn"))
      {
        break label966;
        if (arena == null)
        {
          this.plugin.getChatApi().announce(ChatColor.RED + "You must enter the name of the arena!", new Player[] { player });
          break label1339;
        }
        arena.setup("lobby", player, "quicksand.setlobby", "You set QUICKSAND's lobby! Players will teleport here before the game starts.");
        break label1339;
      }
      else if (arena == null)
      {
        this.plugin.getChatApi().announce(ChatColor.RED + "You must enter the name of the arena!", new Player[] { player });
      }
      else
      {
        arena.setup("spawn", player, "quicksand.setspawn", "You set QUICKSAND's spawnpoint! Players will teleport here when the game starts!");
        break label1339;
        if (arena == null)
        {
          this.plugin.getChatApi().announce(ChatColor.RED + "You must enter the name of the arena!", new Player[] { player });
        }
        else
        {
          arena.setup("end", player, "quicksand.setend", "You set QUICKSAND's end point! Players will teleport here when the game is over.");
          break label1339;
          if (!player.hasPermission("quicksand.config"))
          {
            this.plugin.getChatApi().announce(ChatColor.RED + "You don't have permission!", new Player[] { player });
            return true;
          }
          if (arena == null)
          {
            this.plugin.getChatApi().announce(ChatColor.RED + "You must enter the name of the arena!", new Player[] { player });
            return true;
          }
          if (args.length < 4)
          {
            this.plugin.getChatApi().announce(ChatColor.RED + "You must enter a settings and its new value!", new Player[] { player });
            return true;
          }
          String str2;
          switch ((str2 = args[2].toLowerCase()).hashCode())
          {
          case -493567566: 
            if (str2.equals("players")) {
              break;
            }
          case 1352226353: 
            if ((goto 754) && (str2.equals("countdown")))
            {
              arena.getSettingsApi().setCountdown(Integer.parseInt(args[3]));
              this.plugin.getChatApi().announce("Countdown is set to " + arena.getSettingsApi().getCountdown(), new Player[] { player });
              break label1339;
              arena.getSettingsApi().setMinPlayers(Integer.parseInt(args[3]));
              this.plugin.getChatApi().announce("minPlayers is set to " + arena.getSettingsApi().getMinPlayers(), new Player[] { player });
            }
            break;
          }
          this.plugin.getChatApi().announce(ChatColor.RED + "There is no setting called \"" + args[3] + "\"!", new Player[] { player });
          return true;
          if (player.hasPermission("quicksand.join"))
          {
            this.plugin.getServer().getPluginManager().callEvent(new PlayerJoinArenaEvent(player, arena));
          }
          else if (arena == null)
          {
            this.plugin.getChatApi().announce(ChatColor.RED + "You must enter the name of the arena!", new Player[] { player });
          }
          else
          {
            arena.announce(ChatColor.RED + "You don't have permission!", new Player[] { player });
            break label1339;
            this.plugin.getServer().getPluginManager().callEvent(new PlayerLeaveEvent(player));
          }
        }
      }
      break;
    }
    label966:
    player.sendMessage(ChatColor.GOLD + "==========[ " + ChatColor.YELLOW + "QUICKSAND " + ChatColor.GOLD + "]==========");
    player.sendMessage(ChatColor.RED + "/quicksand setSpawn <arena> " + ChatColor.RESET + "- sets the spawnpoint for the game.");
    player.sendMessage(ChatColor.RED + "/quicksand setLobby <arena> " + ChatColor.RESET + "- sets the waiting room for the game.");
    player.sendMessage(ChatColor.RED + "/quicksand setEnd <arena> " + ChatColor.RESET + "- sets the place where players go at the end of the game.");
    player.sendMessage(ChatColor.RED + "/quicksand configure <arena> countdown <seconds>" + ChatColor.RESET + "- sets the duration of the countdown.");
    player.sendMessage(ChatColor.RED + "/quicksand configure <arena> players <number>" + ChatColor.RESET + "- sets the minimum amount of players required to start the game.");
    player.sendMessage(ChatColor.RED + "/quicksand join <arena> " + ChatColor.RESET + "- join QUICKSAND!");
    player.sendMessage(ChatColor.RED + "/quicksand leave " + ChatColor.RESET + "- leave QUICKSAND!");
    player.sendMessage(ChatColor.GOLD + "==========[ " + ChatColor.YELLOW + "QUICKSAND " + ChatColor.GOLD + "]==========");
    label1339:
    return true;
  }
}
