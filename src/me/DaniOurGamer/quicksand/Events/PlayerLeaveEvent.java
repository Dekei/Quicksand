package me.DaniOurGamer.quicksand.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public final class PlayerLeaveEvent
  extends PlayerEvent
{
  private static final HandlerList handlers = new HandlerList();
  
  public static HandlerList getHandlerList()
  {
    return handlers;
  }
  
  public PlayerLeaveEvent(Player player)
  {
    super(player);
  }
  
  @Override
  public HandlerList getHandlers()
  {
    return handlers;
  }
}
