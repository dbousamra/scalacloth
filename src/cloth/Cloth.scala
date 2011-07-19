package cloth

import scala.collection

class Cloth(val rows: Int, val columns: Int, var gravity: Float) {

  var grid = new Array[Array[Particle]](rows, columns)
  val timestep = 1.0f

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
        if (p.stuck) p.setCurrentPos(p.getPreviousPos)
        
        var multiplyByTime = Tuple2(p.getForces * timestep * timestep, gravity * timestep * timestep)
        var minusPrevPos = Tuple2(p.getCurrentPos.getX - p.getPreviousPos.getX, p.getCurrentPos.getY - p.getPreviousPos.getY)
        var together = Tuple2(multiplyByTime._1 + minusPrevPos._1 , multiplyByTime._2  + minusPrevPos._2)
        p.setPreviousPos(p.getCurrentPos)
        p.setCurrentPos(new Position(p.getCurrentPos.getX + together._1, p.getCurrentPos.getY + together._2))
      }
    }
  }

  def satisfyConstraints() = {
    for (row <- grid) {
      for (p <- row) {
        if (p.stuck) p.setCurrentPos(p.getPreviousPos)
        else {
          var neighbors = p.getNeighbors
          for (constraint <- neighbors) {
            val c2 = grid(constraint.getX)(constraint.getY).getCurrentPos
            val c1 = p.getCurrentPos
            val delta = Tuple2(c2.getX - c1.getX, c2.getY - c1.getY)
            val deltaLength = math.sqrt(math.pow((c2.getX - c1.getX), 2) + math.pow((c2.getY - c1.getY),2))
            val difference = (deltaLength - 1.0f) / deltaLength
            val dtemp = Tuple2(delta._1 * 0.5f * difference, delta._2 * 0.5f * difference)
            p.setCurrentPos(new Position(c1.getX + dtemp._1.floatValue, c1.getY + dtemp._2.floatValue))
            grid(constraint.getX)(constraint.getY).setCurrentPos(new Position(c2.getX - dtemp._1.floatValue, c2.getY - dtemp._2.floatValue))
//          }
          
          }
        }
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