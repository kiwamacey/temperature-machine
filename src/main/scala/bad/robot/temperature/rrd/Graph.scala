package bad.robot.temperature.rrd

import java.awt.Color
import java.awt.Color._

import bad.robot.temperature.rrd.RrdUpdate._
import org.rrd4j.ConsolFun._
import org.rrd4j.graph.{RrdGraph, RrdGraphDef}

import scala.concurrent.duration.Duration


object Graph {

  val path = RrdFile.path / "temperature.png"

  def create(from: Seconds, to: Seconds) = {
    val graph = new RrdGraphDef()
    graph.setWidth(800)
    graph.setHeight(500)
    graph.setFilename(path)
    graph.setStartTime(from)
    graph.setEndTime(to)
    graph.setTitle("Temperature")
    graph.setVerticalLabel("°C")

    graph.datasource(name, RrdFile.file, name, AVERAGE)
    graph.line(name, blue)
    graph.setImageFormat("png")

    graph.gprint(name, MIN, "min = %.2f%s °C")
    graph.gprint(name, MAX, "max = %.2f%s °C")

    graph.hrule(0, new Color(204, 255, 255), "freezing")
    graph.hspan(16, 20, transparent(green), "Optimal")

    val file = new RrdGraph(graph)
    println(file.getRrdGraphInfo.getFilename)
    println(file.getRrdGraphInfo.dump())
  }

  def transparent(color: Color): Color = {
    val rgb = color.getRGB
    val red = (rgb >> 16) & 0xFF
    val green = (rgb >> 8) & 0xFF
    val blue = rgb & 0xFF
    new Color(red, green, blue, 0x33)
  }
}

object GenerateGraph extends App {

  val period = Duration(5, "minutes")

  val start = now() - period.toSeconds
//  Graph.create(start, start + period.toSeconds)

  val s = Seconds(1451420961)
  Graph.create(s, s + Duration(12, "hours").toSeconds)

}