package cloth

class Particle(
    var currentPos: Position, 
    var previousPos: Position, 
    val gridIndex: Coordinate, 
    val restLength: Float
    ) {

  val forces = 0.0f
  var stuck = false
  var neighbors = new Array[Coordinate](4)

}