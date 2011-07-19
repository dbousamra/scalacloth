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
  
  def subtractPosition(position: Position): Position = {
    return new Position(X - position.getX, Y - position.getY)
  }
  
  def addPosition(position: Position): Position = {
    return new Position(X + position.getX, Y + position.getY)
  }
}