package warcraftTD.world;

import warcraftTD.libs.StdDraw;
import warcraftTD.particules.CircleParticule;
import warcraftTD.particules.RandomParticuleGenerator;

import java.awt.*;

/**
 * Entité de type fleur
 */
public class Flower extends Entity {
  /**
   * Chemin vers le fichier image de la fleur
   */
  private final String path;

  /**
   * Création d'une nouvelle fleur
   *
   * @param type Type de la fleur
   * @param tile Tuile parent
   */
  public Flower(FlowerType type, Tile tile) {
    super(EntityBuildable.BUILDABLE);
    switch (type) {
      case BLUE:
        this.getEntityParticules().addGenerator(new RandomParticuleGenerator(tile.getPosition(), 0.5, tile.getHeight() * 0.40, new CircleParticule(1, 0.01, 0.05, new Color(73, 109, 227))));
        this.path = "images/tiles/flowers/blue.png";
        break;
      case RED:
        this.getEntityParticules().addGenerator(new RandomParticuleGenerator(tile.getPosition(), 0.5, tile.getHeight() * 0.40, new CircleParticule(1, 0.01, 0.05, new Color(176, 84, 105))));

        this.path = "images/tiles/flowers/red.png";
        break;
      case WHITE:
        this.getEntityParticules().addGenerator(new RandomParticuleGenerator(tile.getPosition(), 0.5, tile.getHeight() * 0.40, new CircleParticule(1, 0.01, 0.05, new Color(216, 208, 224))));
        this.path = "images/tiles/flowers/white.png";
        break;
      case YELLOW:
        this.getEntityParticules().addGenerator(new RandomParticuleGenerator(tile.getPosition(), 0.5, tile.getHeight() * 0.40, new CircleParticule(1, 0.01, 0.05, new Color(242, 178, 43))));
        this.path = "images/tiles/flowers/yellow.png";
        break;
      default:
        this.path = "images/tiles/flowers/bush.png";
        break;

    }
  }

  /**
   * Update and draw the flower
   *
   * @param deltaTime The game delta time
   * @param tile      The tile the flower is attached to
   */
  public void update(double deltaTime, Tile tile) {
    StdDraw.pictureHeight(tile.getPosition().getX(), tile.getPosition().getY(), this.path, tile.getHeight() * 0.40);
  }

}
