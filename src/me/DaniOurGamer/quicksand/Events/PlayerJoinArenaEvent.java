package me.DaniOurGamer.quicksand.Events;

import me.DaniOurGamer.quicksand.Arena;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public final class PlayerJoinArenaEvent
  extends PlayerEvent
{
  private static final HandlerList handlers = new HandlerList();
  private final Arena arena;
  
  public static HandlerList getHandlerList()
  {
    return handlers;
  }
  
  public PlayerJoinArenaEvent(Player player, Arena arena)
  {
    super(player);
    this.arena = arena;
  }
  
  public Arena getArena()
  {
    return this.arena;
  }
  
  public HandlerList getHandlers()
  {
    return handlers;
  }
}
