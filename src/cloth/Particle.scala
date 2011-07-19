package cloth

class Particle(
    var currentPosition: Position, 
    var previousPosition: Position, 
    val gridIndex: Coordinate, 
    val restLength: Float
    ) {

  val forces = 0.0f
  val stuck = false
  var neighbors = new Array[Coordinate](4)
    
  def getCurrentPos(): Position = {
    return currentPosition
  }
  
  def setCurrentPos(p: Position) = {
    currentPosition = p
  }
   
  def setPreviousPos(p: Position) = {
    previousPosition = p
  }

  def getPreviousPos(): Position = {
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
  
  def getForces(): Float = {
    return forces
  }

}