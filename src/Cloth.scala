import scala.collection

class Position(var X: Float, var Y: Float) {

  def getX(): Float = {
    return X
  }

  def getY(): Float = {
    return Y
  }

  def setX(newCoordinate: Int) = {
    X = newCoordinate
  }

  def setY(newCoordinate: Int) = {
    Y = newCoordinate
  }
}

class Coordinate(var X: Int, var Y: Int) {

  def getX(): Int = {
    return X
  }

  def getY(): Int = {
    return Y
  }

}

class Particle(
    var currentPosition: Position, 
    var previousPosition: Position, 
    val gridIndex: Coordinate, 
    val restLength: Float
    ) {

  val forces = 0
  val stuck = false
  var neighbors = new Array[Coordinate](4)
    
  def getCurrentPos(): Position = {
    return currentPosition
  }

  def getPreviousPosition(): Position = {
    return previousPosition
  }
  
  def getGridIndex(): Coordinate = {
    return gridIndex
  }

  def getNeighbors(): Array[Coordinate] = {
    return neighbors
  }
  
  def setNeighbors(neigh: Array[Coordinate]) = {
    neighbors = neigh
  }
}

class Cloth(val rows: Int, val columns: Int, var gravity: Float) {

  var grid = new Array[Array[Particle]](rows, columns)

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