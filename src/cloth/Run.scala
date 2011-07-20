package cloth

import processing.core._
import java.awt.Dimension

object Run {
 
   private var test:Screen = _
  
   def main(args: Array[String]): Unit = {
    test = new Screen
    val frame = new javax.swing.JFrame("scalacloth")
    frame.setPreferredSize(new Dimension(640, 720))
    frame.getContentPane().add(test)
    test.init
    frame.pack
    frame.setVisible(true)
  }
 
}
 
class Screen extends PApplet {
 
  var cloth = new Cloth(
      rows = 20, 
      columns = 20, 
      gravity = 0.001f,
      timestep = 0.8f,
      fixedParticles = List(
          new Coordinate(x = 19, y = 0)
          )
      )
  
  override def setup() = {
    cloth.createGrid()
    size(640, 720)
    background(255)
    smooth()
    noStroke()
    fill(0, 102)
  }
 
  override def draw() = {
    background(255)
    fill(255);
    stroke(0)
    
    def drawLines(particle: Particle) = {
      particle.neighbors.foreach {
        n =>
          line(particle.currentPos.x * 20 + 20, particle.currentPos.y * 20 + 20,
          cloth.grid(n.x)(n.y).currentPos.x * 20 + 20, cloth.grid(n.x)(n.y).currentPos.y* 20 + 20);
        }
    }
    
    cloth.grid.map(x => x.foreach(y => drawLines(y))) 
    cloth.verletIntegration
    cloth.satisfyConstraints
  }
}