package cloth

case class Position(x: Float, y: Float) {
    def -(that:Position) = Position(this.x - that.x, this.y - that.y)
    def +(that:Position) = Position(this.x + that.x, this.y + that.y)
}