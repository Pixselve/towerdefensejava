package warcraftTD;

public class Position implements Comparable<Position>{
  double x;
  double y;

  /**
   * Classe qui permet d'avoir la position sur l'axe des x et des y des monstres et des tours
   *
   * @param x
   * @param y
   */
  public Position(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public Position(Position p) {
    this.x = p.x;
    this.y = p.y;
  }

  public boolean equals(Position p) {
    return this.x == p.x && this.y == p.y;
  }

  /**
   * Mesure la distance euclidienne entre 2 positions.
   *
   * @param p
   * @return
   */
  public double dist(Position p) {
    return Math.sqrt(Math.pow(this.x - p.x, 2) + Math.pow(this.y - p.y, 2));
  }

  /**
   * Retourne la position du point sur l'axe des x et des y
   */
  public String toString() {
    return "(" + this.x + "," + this.y + ")";
  }

  @Override
  public boolean equals(Object o) {
    if(o == this) return true;
    if(!(o instanceof Position)) return false;
    Position p = (Position)o;
    return (p.x == this.x && p.y == this.y);
  }

  @Override
  public int compareTo(Position p) {
    if(this.x<p.x) return -1;
    else if(this.x>p.x) return 1;
    else {
      if(this.y<p.y) return -1;
      else if(this.y>p.y) return 1;
      else {
        return 0;
      }
    }
  }
}
