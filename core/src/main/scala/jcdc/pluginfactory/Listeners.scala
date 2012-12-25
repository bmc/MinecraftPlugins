package jcdc.pluginfactory

import org.bukkit.block.Block
import org.bukkit.entity.{Entity, Player}
import org.bukkit.event.{EventHandler => EH, Listener}
import org.bukkit.event.block.{BlockBreakEvent, BlockDamageEvent}
import org.bukkit.event.block.Action._
import org.bukkit.event.entity.{EntityDamageEvent, PlayerDeathEvent, EntityDamageByEntityEvent}
import org.bukkit.event.weather.WeatherChangeEvent
import org.bukkit.event.player.{PlayerInteractEvent, PlayerMoveEvent, PlayerChatEvent}

trait ListenerPlugin extends ScalaPlugin with Listeners {
  val listener:Listener
  override def onEnable(){ super.onEnable(); registerListener(listener) }
}

trait ListenersPlugin extends ScalaPlugin with Listeners {
  val listeners:List[Listener]
  override def onEnable(){ super.onEnable(); listeners.foreach(registerListener) }
}

object Listeners extends Listeners

trait Listeners extends EnrichmentClasses {
  abstract case class ListeningFor(listener:Listener) extends ListenerPlugin

  def OnPlayerMove(f: PlayerMoveEvent => Unit) = new Listener {
    @EH def on(e:PlayerMoveEvent): Unit = f(e)
  }
  def OnEntityDamageByEntity(f: EntityDamageByEntityEvent => Unit) = new Listener {
    @EH def on(e:EntityDamageByEntityEvent): Unit = f(e)
  }
  def OnPlayerDamageByEntity(f: (Player, EntityDamageByEntityEvent) => Unit) = new Listener {
    @EH def on(e:EntityDamageByEntityEvent): Unit = e.getEntity.whenPlayer(f(_, e))
  }
  def OnEntityDamageByPlayer(f: (Entity, Player, EntityDamageByEntityEvent) => Unit) = new Listener {
    @EH def on(e:EntityDamageByEntityEvent): Unit =
      if(e.getDamager.isInstanceOf[Player]) f(e.getEntity,e.getDamager.asInstanceOf[Player], e)
  }
  def OnPlayerDamage(f: (Player, EntityDamageEvent) => Unit) = new Listener {
    @EH def on(e:EntityDamageEvent): Unit   = e.getEntity.whenPlayer(f(_, e))
  }
  def OnPlayerDeath(f: (Player, PlayerDeathEvent) => Unit) = new Listener {
    @EH def on(e:PlayerDeathEvent): Unit    = f(e.getEntity, e)
  }
  def OnPlayerChat(f: (Player, PlayerChatEvent) => Unit) = new Listener {
    @EH def on(e:PlayerChatEvent): Unit     = f(e.getPlayer, e)
  }
  def OnBlockBreak(f: (Block, Player, BlockBreakEvent) => Unit) = new Listener {
    @EH def on(e:BlockBreakEvent): Unit     = f(e.getBlock, e.getPlayer, e)
  }
  def OnBlockDamage(f: (Block, BlockDamageEvent) => Unit) = new Listener {
    @EH def on(e:BlockDamageEvent): Unit    = f(e.getBlock, e)
  }
  def OnWeatherChange(f: WeatherChangeEvent => Unit) = new Listener {
    @EH def on(e:WeatherChangeEvent): Unit  = f(e)
  }
  def OnPlayerInteract(f: (Player, PlayerInteractEvent) => Unit) = new Listener {
    @EH def on(e:PlayerInteractEvent): Unit = f(e.getPlayer, e)
  }
  def OnRightClickBlock(f: (Player, PlayerInteractEvent) => Unit) = new Listener {
    @EH def on(e:PlayerInteractEvent): Unit = if (e.getAction == RIGHT_CLICK_BLOCK) f(e.getPlayer, e)
  }
  def OnLeftClickBlock(f: (Player, PlayerInteractEvent) => Unit) = new Listener {
    @EH def on(e:PlayerInteractEvent): Unit = if (e.getAction == LEFT_CLICK_BLOCK)  f(e.getPlayer, e)
  }
  def OnPlayerRightClickAir(f: (Player, PlayerInteractEvent) => Unit) = new Listener {
    @EH def on(e:PlayerInteractEvent): Unit = if (e.getAction == RIGHT_CLICK_AIR)   f(e.getPlayer, e)
  }
  def OnPlayerLeftClickAir(f: (Player, PlayerInteractEvent) => Unit) = new Listener {
    @EH def on(e:PlayerInteractEvent): Unit = if (e.getAction == LEFT_CLICK_AIR)    f(e.getPlayer, e)
  }
  def OnPlayerMove(f: (Player, PlayerMoveEvent) => Unit) = new Listener {
    @EH def on(e:PlayerMoveEvent): Unit     = f(e.getPlayer, e)
  }
}
