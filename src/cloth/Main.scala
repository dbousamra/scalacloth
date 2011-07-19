object Main {

  def main(args: Array[String]): Unit = {  }

  var x = new Cloth(10, 10, 0.005f)
  x.createGrid()
  //println(x.grid(0)(0).getNeighbors.foreach
//  x.grid(1)(1).getNeighbors.foreach {
//    arg => println(arg)
//  }
  for (i <- 0 until 100){
     x.verletIntegration
     println(x.grid(2)(4).getCurrentPos.getX, x.grid(2)(4).getCurrentPos.getY)
  }
}