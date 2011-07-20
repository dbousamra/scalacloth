package cloth

class Position(var x: Float, var y: Float) {
  
  def subtractPosition(position: Position): Position = {
    return new Position(x - position.x, y - position.y)
  }
  
  def addPosition(position: Position): Position = {
    return new Position(x + position.x, y + position.y)
  }
}