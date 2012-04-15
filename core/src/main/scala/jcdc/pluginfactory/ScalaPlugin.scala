package jcdc.pluginfactory

import java.util.logging.Logger
import org.bukkit.event.Listener

object ScalaPlugin extends Pimps

abstract class ScalaPlugin extends org.bukkit.plugin.java.JavaPlugin with Pimps {
  val author: String
  val version: String

  val log = Logger.getLogger("Minecraft")

  def name = this.getDescription.getName
  def server = getServer

  // setup stuff
  override def onEnable(){ super.onEnable(); setupDatabase(); logInfo(name + " enabled!") }
  override def onDisable(){ super.onDisable(); logInfo(name + " disabled!") }
  def registerListener(listener:Listener): Unit = server.getPluginManager.registerEvents(listener, this)

  // db setup stuff.
  def dbClasses: List[Class[_]] = Nil
  override def getDatabaseClasses = new java.util.ArrayList[Class[_]](){ dbClasses.foreach(add) }
  def setupDatabase(){
    if(dbClasses.nonEmpty)
      try getDatabase.find(dbClasses.head).findRowCount
      catch{ case e: javax.persistence.PersistenceException =>
        logInfoAround("Installing database...", "installed"){ installDDL() }
      }
  }

  def yaml = {
    List(
      "name: " + this.getClass.getSimpleName,
      "main: " + this.getClass.getName,
      "author: " + author,
      "version: " + version,
      "database: " + (this.dbClasses.size > 0)
    ).mkString("\n")
  }

  // logging
  def logInfo(message:String) { log.info("["+name+"] - " + message) }
  def logInfoAround[T](beforeMessage:String, afterMessage:String)(f: => T): T = {
    logInfo(beforeMessage); val t = f; logInfo(afterMessage); t
  }
  def logError(e:Throwable){ log.log(java.util.logging.Level.SEVERE, "["+name+"] - " + e.getMessage, e) }
  def broadcast(message:String) = server.broadcastMessage("["+name+"] - " + message)
}