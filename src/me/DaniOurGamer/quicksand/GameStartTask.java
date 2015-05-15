package me.firebreath15.quicksand;

import me.firebreath15.quicksand.api.MatchApi;
import me.firebreath15.quicksand.api.SettingsApi;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

public class GameStartTask
  extends BukkitRunnable
{
  private final Arena arena;
  private int countdown;
  
  public GameStartTask(Arena arena, int countdown)
  {
    this.arena = arena;
    this.countdown = countdown;
  }
  
  public void run()
  {
    if (this.countdown > 0)
    {
      if ((this.countdown % 10 == 0) || (this.countdown < 10))
      {
        this.arena.getSettingsApi().getLocation("spawn").getWorld().playSound(this.arena.getSettingsApi().getLocation("spawn"), Sound.NOTE_PLING, 2.0F, 2.0F);
        this.arena.announce(String.valueOf(this.countdown));
      }
      this.countdown -= 1;
    }
    else
    {
      this.arena.getMatchApi().start();
      this.arena.broadcast("The match has started. " + ChatColor.GREEN + "Good luck!");
      cancel();
    }
  }
}
