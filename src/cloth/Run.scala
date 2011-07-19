package cloth

import processing.core._

object Run {
 
   private var test:Screen = _
  
   def main(args: Array[String]): Unit = {
    test = new Screen
    val frame = new javax.swing.JFrame("Test")
    frame.getContentPane().add(test)
    test.init
 
    frame.pack
    frame.setVisible(true)
  }
 
}
 
class Screen extends PApplet {
 
  var cloth = new Cloth(20, 20, 0.005f)
  var angle:Int = 0;
  
  override def setup() = {
    cloth.createGrid()
    cloth.grid(0)(0).setStuck()
    cloth.grid(19)(0).setStuck()
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
    for (x <- 0.until(cloth.getRows)) {
      for (y <- 0.until(cloth.getColumns)) {
        var neighbors = cloth.grid(x)(y).getNeighbors
        for (n <- neighbors) {
          line(cloth.grid(x)(y).getCurrentPos.getX * 20 + 20, cloth.grid(x)(y).getCurrentPos.getY * 20 + 20,
              cloth.grid(n.getX)(n.getY).getCurrentPos.getX * 20 + 20, cloth.grid(n.getX)(n.getY).getCurrentPos.getY* 20 + 20);
        }
	  }
    }
    
    
    cloth.verletIntegration
    cloth.satisfyConstraints
  }
}