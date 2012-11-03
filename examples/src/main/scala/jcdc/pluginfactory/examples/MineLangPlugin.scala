package jcdc.pluginfactory.examples

import jcdc.pluginfactory.{Command, CommandsPlugin}
import jcdc.pluginfactory.ClojureInScala._
  import Reader._
  import AST._
  import Parser._
import jcdc.pluginfactory.MineLang._
import java.io.File

class MineLangPlugin extends CommandsPlugin {

  val houseDefs = new File("../minelang/house.mc")
  var defs: List[Def] = parseDefs(read(houseDefs))

  val commands = List(
    Command("import", "import some defs", args(existingFile){ case (_, codeFile) =>
      defs = defs ::: parseDefs(read(codeFile))
    }),
    Command("run", "run a program", args(slurp){ case (p, code) =>
      runProgram(Program(defs, parseExpr(read(code))), p)
    }),
    Command("reload-code", "run a program", noArgs{ p => defs = parseDefs(read(houseDefs))})
  )
}