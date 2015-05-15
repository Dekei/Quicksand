package me.DaniOurGamer.quicksand.Listener;

import me.DaniOurGamer.quicksand.Arena;
import org.bukkit.scheduler.BukkitRunnable;

public class MovementTrackerTask
  extends BukkitRunnable
{
  private final Arena arena;
  
  public MovementTrackerTask(Arena arena)
  {
    this.arena = arena;
  }
  
  public void run()
  {
    this.arena.shoveCampers();
  }
}
