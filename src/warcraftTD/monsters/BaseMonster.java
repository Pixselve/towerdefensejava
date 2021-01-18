package warcraftTD.monsters;

import warcraftTD.libs.StdDraw;
import warcraftTD.utils.Animation;
import warcraftTD.utils.Position;

import java.awt.*;
import java.util.List;

/**
 * Base Monster class
 */
public abstract class BaseMonster extends Monster {
  /**
   * The walking animation of the monster
   */
  private Animation walkingAnimation;
  /**
   * The dying animation of the monster
   */
  private Animation dieAnimation;
  /**
   * The monster is flying or not
   */
  private final boolean isFlying;

  /**
   * Create a base monster
   *
   * @param path             The path the monster will follow
   * @param health           The monster base health
   * @param goldWhenDead     The amount of gold the monster will drop when killed
   * @param speed            The monster base speed
   * @param hitBoxRadius     Le rayon de la hit box du monstre
   * @param walkingAnimation The monster walking animation
   * @param dieAnimation     The monster dying animation
   */
  public BaseMonster(List<Position> path, int health, int goldWhenDead, double speed, double hitBoxRadius, Animation walkingAnimation, Animation dieAnimation) {
    super(health, goldWhenDead, speed, hitBoxRadius, path);
    this.walkingAnimation = walkingAnimation;
    this.dieAnimation = dieAnimation;
    this.dieAnimation.setCallback(() -> this.setReadyToBeRemoved(true));
    this.isFlying = false;
  }

  /**
   * Create a base monster
   *
   * @param path             The path the monster will follow
   * @param health           The monster base health
   * @param goldWhenDead     The amount of gold the monster will drop when killed
   * @param speed            The monster base speed
   * @param walkingAnimation The monster walking animation
   * @param dieAnimation     The monster dying animation
   * @param hitBoxRadius     Le rayon de la hit box du monstre
   * @param isFlying         If the monster if flying or not
   */
  public BaseMonster(List<Position> path, int health, int goldWhenDead, double speed, double hitBoxRadius, boolean isFlying, Animation walkingAnimation, Animation dieAnimation) {
    super(health, goldWhenDead, speed, hitBoxRadius, path);
    this.walkingAnimation = walkingAnimation;
    this.dieAnimation = dieAnimation;
    this.dieAnimation.setCallback(() -> this.setReadyToBeRemoved(true));
    this.isFlying = isFlying;
  }


  /**
   * Draw the monster
   *
   * @param deltaTime The game delta time
   */
  public void draw(double deltaTime) {
    if (this.isDead()) {
      this.dieAnimation.draw(deltaTime, this.getShiftX(), this.getShiftY());
    } else {
      double ratio = StdDraw.getPictureRatio(this.walkingAnimation.getCurrentFrame());
      //Position positionAnimation = new Position(this.getPosition().getX() + (this.getScaledHeight() * ratio) / 5, this.getPosition().getY() + this.getScaledHeight() / 3);
      Position positionAnimation = new Position(this.getPosition().getX(), this.getPosition().getY());

      if (this.isFlying) {
        StdDraw.setPenColor(Color.gray);
        StdDraw.filledEllipse(this.getPosition().getX(), this.getPosition().getY(), 0.15*this.getScaleWidth(), 0.05*this.getScaleHeight());
      }

      this.walkingAnimation.setPosition(positionAnimation);
      this.dieAnimation.setPosition(positionAnimation);
      this.walkingAnimation.draw(deltaTime, this.getShiftX(), this.getShiftY());

    }
  }

  /**
   * Check if the monster is flying
   *
   * @return True if the monster is flying
   */
  public boolean isFlying() {
    return this.isFlying;
  }


  public Animation getWalkingAnimation() {
    return this.walkingAnimation;
  }

  public Animation getDieAnimation() {
    return this.dieAnimation;
  }

  @Override
  public void resizeMonster(int nbSquareX, int nbSquareY) {
    super.resizeMonster(nbSquareX, nbSquareY);
    this.getDieAnimation().setScaledHeight(this.getScaleHeight());
    this.getWalkingAnimation().setScaledHeight(this.getScaleHeight());
  }
}
