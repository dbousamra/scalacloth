package cloth

import scala.collection

class Cloth(val rows: Int, val columns: Int, var gravity: Float) {

  var grid = new Array[Array[Particle]](rows, columns)
  val timestep = 0.5f

  def createGrid() = {
    for (x <- 0.until(rows)) {
      for (y <- 0.until(columns)) {
        grid(x)(y) = new Particle(new Position(x, y), new Position(x, y), new Coordinate(x, y), 1.0f)
        grid(x)(y).setNeighbors(findNeighbors(grid(x)(y)))
      }
    }
  }

  def findNeighbors(p: Particle): Array[Coordinate] = {
    var neighbors = new Array[Coordinate](4)
    
    //Hard coded in neighbor logic. Need to fix up
    neighbors.update(0, new Coordinate(p.getGridIndex.getX - 1, p.getGridIndex.getY))
    neighbors.update(1, new Coordinate(p.getGridIndex.getX, p.getGridIndex.getY - 1))
    neighbors.update(2, new Coordinate(p.getGridIndex.getX + 1, p.getGridIndex.getY))
    neighbors.update(3, new Coordinate(p.getGridIndex.getX, p.getGridIndex.getY + 1))

    return neighbors.filter(x => isOccupied(x) == true)
  }

  def isOccupied(coord: Coordinate): Boolean = {
    coord match {
      case _ if coord.getX < 0.0 => false
      case _ if coord.getX > rows - 1 => false
      case _ if coord.getY < 0.0 => false
      case _ if coord.getY > columns - 1 => false
      case _ => true
    }
  }
  
  def verletIntegration() = {	
    
    for (row <- grid) {
      for (p <- row) {
        //if (p.stuck) p.setCurrentPos(p.getPreviousPos)
        
        var multiplyByTime = Tuple2(p.getForces * timestep * timestep, gravity * timestep * timestep)
        var minusPrevPos = Tuple2(p.getCurrentPos.getX - p.getPreviousPos.getX, p.getCurrentPos.getY - p.getPreviousPos.getY)
        var together = Tuple2(multiplyByTime._1 + minusPrevPos._1 , multiplyByTime._2  + minusPrevPos._2)
        p.setPreviousPos(p.getCurrentPos)
        p.setCurrentPos(new Position(p.getCurrentPos.getX + together._1, p.getCurrentPos.getY + together._2))
      }
    }
  }

  def getRows(): Int = {
    return rows
  }

  def getColumns(): Int = {
    return columns
  }

  def getParticle(coord: Coordinate): Particle = {
    return grid(coord.getX())(coord.getY())
  }

}