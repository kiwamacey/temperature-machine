package bad.robot.temperature.rrd

import bad.robot.temperature._
import org.specs2.mutable.Specification

import scala.xml.Elem

class ChartJsonTest extends Specification {

  val xml: Elem = <fetch_data>
    <request>
      <file>.temperature\temperature.rrd</file>
      <start>1487582455</start>
      <end>1487668855</end>
      <resolution>1</resolution>
      <cf>AVERAGE</cf>
    </request>
    <datasources>
      <name>bedroom-sensor-1</name>
      <name>bedroom-sensor-2</name>
      <name>lounge-sensor-1</name>
      <name>lounge-sensor-2</name>
    </datasources>
    <data>
      <row>
        <timestamp>1487582430</timestamp>
        <values>
          <v>NaN</v>
          <v>NaN</v>
          <v>NaN</v>
          <v>NaN</v>
        </values>
      </row>
      <row>
        <timestamp>1487582460</timestamp>
        <values>
          <v>+2.3310391215E01</v>
          <v>+2.9610391215E01</v>
          <v>+3.3282822188E00</v>
          <v>+4.6282822188E00</v>
        </values>
      </row>
      <row>
        <timestamp>1487582490</timestamp>
        <values>
          <v>+2.2793614109E01</v>
          <v>+2.9093614109E01</v>
          <v>+2.6427807382E00</v>
          <v>+3.9427807382E00</v>
        </values>
      </row>
    </data>
  </fetch_data>

  "Convert XML to object" >> {
    val actual = ChartJson.parse(xml)
    val expected = List(
      Series("bedroom-sensor-1", List(
        "1487582430" -> "NaN",
        "1487582460" -> "+2.3310391215E01",
        "1487582490" -> "+2.2793614109E01"
      )),
      Series("bedroom-sensor-2", List(
        "1487582430" -> "NaN",
        "1487582460" -> "+2.9610391215E01",
        "1487582490" -> "+2.9093614109E01"
      )),
      Series("lounge-sensor-1", List(
        "1487582430" -> "NaN",
        "1487582460" -> "+3.3282822188E00",
        "1487582490" -> "+2.6427807382E00"
      )),
      Series("lounge-sensor-2", List(
        "1487582430" -> "NaN",
        "1487582460" -> "+4.6282822188E00",
        "1487582490" -> "+3.9427807382E00"
      ))
    )
    actual must_=== expected
  }

  "Encode Json" >> {
    encode(ChartJson.parse(xml)).spaces2ps must_==
      """[
        |  {
        |    "label" : "bedroom-sensor-1",
        |    "data" : [
        |      {
        |        "x" : 1487582430000,
        |        "y" : "NaN"
        |      },
        |      {
        |        "x" : 1487582460000,
        |        "y" : "+2.3310391215E01"
        |      },
        |      {
        |        "x" : 1487582490000,
        |        "y" : "+2.2793614109E01"
        |      }
        |    ]
        |  },
        |  {
        |    "label" : "bedroom-sensor-2",
        |    "data" : [
        |      {
        |        "x" : 1487582430000,
        |        "y" : "NaN"
        |      },
        |      {
        |        "x" : 1487582460000,
        |        "y" : "+2.9610391215E01"
        |      },
        |      {
        |        "x" : 1487582490000,
        |        "y" : "+2.9093614109E01"
        |      }
        |    ]
        |  },
        |  {
        |    "label" : "lounge-sensor-1",
        |    "data" : [
        |      {
        |        "x" : 1487582430000,
        |        "y" : "NaN"
        |      },
        |      {
        |        "x" : 1487582460000,
        |        "y" : "+3.3282822188E00"
        |      },
        |      {
        |        "x" : 1487582490000,
        |        "y" : "+2.6427807382E00"
        |      }
        |    ]
        |  },
        |  {
        |    "label" : "lounge-sensor-2",
        |    "data" : [
        |      {
        |        "x" : 1487582430000,
        |        "y" : "NaN"
        |      },
        |      {
        |        "x" : 1487582460000,
        |        "y" : "+4.6282822188E00"
        |      },
        |      {
        |        "x" : 1487582490000,
        |        "y" : "+3.9427807382E00"
        |      }
        |    ]
        |  }
        |]""".stripMargin
  }
}
