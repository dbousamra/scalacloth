package cloth

import scala.collection

class Coordinate(var x: Int, var y: Int) { }


class Cloth(
    val rows: Int, 
    val columns: Int, 
    var gravity: Float,
    val timestep: Float,
    val fixedParticles: List[Coordinate]
    ) {
  
  var grid = new Array[Array[Particle]](rows, columns)

  def createGrid() = {
    for (x <- 0.until(rows)) {
      for (y <- 0.until(columns)) {
        grid(x)(y) = new Particle(new Position(x, y), new Position(x, y), new Coordinate(x, y), 1.0f)
        grid(x)(y).neighbors = findNeighbors(grid(x)(y))
      }
    }
    fixedParticles.foreach(p => if (isOccupied(p)){grid(p.x)(p.y).stuck = true})
  }
  
  def findNeighbors(p: Particle): Array[Coordinate] = {
    var neighbors = new Array[Coordinate](4)
    
    //Hard coded in neighbor logic. Need to fix up
    neighbors.update(0, new Coordinate(p.gridIndex.x - 1, p.gridIndex.y))
    neighbors.update(1, new Coordinate(p.gridIndex.x, p.gridIndex.y - 1))
    neighbors.update(2, new Coordinate(p.gridIndex.x + 1, p.gridIndex.y))
    neighbors.update(3, new Coordinate(p.gridIndex.x, p.gridIndex.y + 1))

    return neighbors.filter(x => isOccupied(x) == true)
  }

  def isOccupied(coord: Coordinate): Boolean = {
    coord match {
      case _ if coord.x < 0.0 => false
      case _ if coord.x > rows - 1 => false
      case _ if coord.y < 0.0 => false
      case _ if coord.y > columns - 1 => false
      case _ => true
    }
  }
  
  def verletIntegration() = {	
    
    for (row <- grid;p <- row) {
        if (p.stuck) p.currentPos = p.previousPos
        
        val multiplyByTime = (p.forces * timestep * timestep, gravity * timestep * timestep)
        val minusPrevPos = (p.currentPos.x - p.previousPos.x, p.currentPos.y - p.previousPos.y)
        val together = (multiplyByTime._1 + minusPrevPos._1 , multiplyByTime._2  + minusPrevPos._2)
        p.previousPos = p.currentPos
        p.currentPos = new Position(p.currentPos.x + together._1, p.currentPos.y + together._2)
      
    }
  }
  
  def satisfyConstraints() {
    
    def calculateConstraint(constraint: Coordinate, p: Particle) {
      val c2 = grid(constraint.x)(constraint.y).currentPos
	  val c1 = p.currentPos
	  val delta = ((c2-c1).x, (c2-c1).y)
	  val deltaLength = math.sqrt(math.pow(((c2-c1).x), 2) + math.pow(((c2-c1).y),2))
	  val difference = (deltaLength - 1.0f) / deltaLength
	  val dtemp = (delta._1 * 0.5f * difference, delta._2 * 0.5f * difference)
	  p.currentPos = new Position(c1.x + dtemp._1.floatValue, c1.y + dtemp._2.floatValue)
      grid(constraint.x)(constraint.y).currentPos = (new Position(c2.x - dtemp._1.floatValue, c2.y - dtemp._2.floatValue))
    }
    
    for(row <- grid; p <- row) {
      if (p.stuck) p.currentPos = 	p.previousPos
      else {
        p.neighbors.map(n => calculateConstraint(n, p))
      }
    }
  }
  
}