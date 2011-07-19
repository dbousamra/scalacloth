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
 
  var cloth = new Cloth(10, 10, 0.005f)
  var angle:Int = 0;
  
  override def setup() = {
    cloth.createGrid()
    size(640, 360)
    background(102)
    smooth()
    noStroke()
    fill(0, 102)
  }
 
  override def draw() = {
    fill(255);
    for (x <- 0.until(cloth.getRows)) {
      for (y <- 0.until(cloth.getColumns)) {
        stroke(255)
        point(cloth.grid(x)(y).getCurrentPos.getX * 20 + 20, cloth.grid(x)(y).getCurrentPos.getY * 20 + 20)
	  }
    }
    cloth.verletIntegration
  }
}