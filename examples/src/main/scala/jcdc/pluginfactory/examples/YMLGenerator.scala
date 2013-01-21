package jcdc.pluginfactory.examples

import jcdc.pluginfactory.ScalaPlugin

// A helper object I use to auto generate all my plugin.yml files.
object YMLGenerator {

  val plugins = List[ScalaPlugin](
    new Arena,
    new GetOffMyLawn,
    new LightningArrows,
    new MultiPlayerCommands,
    new BanArrows,
    new BlockChanger,
    new BlockChangerGold,
    new CurseBan,
    new CursePreventer,
    new Farmer,
    new God,
    new NoRain,
    //new NPCTest,
    new PermissionsTest,
    new PluginCommander,
    new Thor,
    new TreeDelogger,
    new ZombieApocalypse,
    new WarpPlugin,
    new WorldEdit,
    new YellowBrickRoad)

  def main(args: Array[String]): Unit =
    plugins.foreach(_.writeYML("Josh Cough", "0.3.0"))
}
