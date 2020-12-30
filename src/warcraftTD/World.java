package warcraftTD;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.List;

public class World {
  // l'ensemble des monstres, pour gerer (notamment) l'affichage
  List<Monster> monsters = new ArrayList<Monster>();
  private int totalMonsterAmount;

  // l'ensemble des cases du chemin
  List<Position> paths = new ArrayList<Position>();

  // L'interface du jeu
  Interface HUD;

  // le porte monnaie du joueur
  Wallet player_wallet;

  Hashtable<Class, Integer> price_tower;

  TreeMap<Position, Tower> list_tower;

  boolean needReleaseMouse = false;

  // le nom de la tour en construction
  Class building_class = null;

  // représente le temps pour chaque tick en s
  double delta_time;

  // Position par laquelle les monstres vont venir
  Position spawn;

  // Information sur la taille du plateau de jeu
  int width;
  int height;
  int nbSquareX;
  int nbSquareY;
  double squareWidth;
  double squareHeight;

  // Nombre de points de vie du joueur
  int life = 20;

  // Commande sur laquelle le joueur appuie (sur le clavier)
  char key;

  // Condition pour terminer la partie
  boolean end = false;

  boolean isMonsterActing = false;

  /**
   * Initialisation du monde en fonction de la largeur, la hauteur et le nombre de cases données
   *
   * @param width
   * @param height
   * @param nbSquareX
   * @param nbSquareY
   * @param startSquareX
   * @param startSquareY
   */
  public World(int width, int height, int nbSquareX, int nbSquareY, int startSquareX, int startSquareY) {
    this.width = width;
    this.height = height;
    this.nbSquareX = nbSquareX;
    this.nbSquareY = nbSquareY;
    this.squareWidth = (double) 1 / nbSquareX;
    this.squareHeight = (double) 1 / nbSquareY;
    this.spawn = new Position(startSquareX * this.squareWidth + this.squareWidth / 2, startSquareY * this.squareHeight + this.squareHeight / 2);
    StdDraw.setCanvasSize(width, height);
    StdDraw.enableDoubleBuffering();
    this.delta_time = 0.0;

    this.player_wallet = new Wallet(this);
    this.player_wallet.addMoney(9999);
    this.HUD = new Interface(this);

    this.list_tower = new TreeMap<>();

    price_tower = new Hashtable<>();
    price_tower.put(ArrowTower.class, 50);
    price_tower.put(BombTower.class, 60);
    price_tower.put(IceTower.class, 70);
    price_tower.put(PoisonTower.class, 80);

    // Chemin temporaire
    this.paths.add(new Position(1, 10));
    this.paths.add(new Position(1, 9));
    this.paths.add(new Position(1, 8));
    this.paths.add(new Position(1, 7));
    this.paths.add(new Position(1, 6));
    this.paths.add(new Position(1, 5));
    this.paths.add(new Position(1, 4));
    this.paths.add(new Position(1, 3));
    this.paths.add(new Position(1, 2));
    this.paths.add(new Position(2, 2));
    this.paths.add(new Position(3, 2));
    this.paths.add(new Position(4, 2));
    this.paths.add(new Position(4, 3));
    this.paths.add(new Position(4, 4));
    this.paths.add(new Position(4, 5));
    this.paths.add(new Position(4, 6));
    this.paths.add(new Position(4, 7));
    this.paths.add(new Position(4, 8));
    this.paths.add(new Position(4, 9));
    this.paths.add(new Position(5, 9));
    this.paths.add(new Position(6, 9));
    this.paths.add(new Position(7, 9));
    this.paths.add(new Position(8, 9));
    this.paths.add(new Position(8, 8));
    this.paths.add(new Position(8, 7));
    this.paths.add(new Position(8, 6));
    this.paths.add(new Position(8, 5));
    this.paths.add(new Position(8, 4));
    this.paths.add(new Position(8, 3));
    this.paths.add(new Position(8, 2));
    this.paths.add(new Position(8, 1));
    this.paths.add(new Position(8, 0));
  }

  /**
   * Définit le décors du plateau de jeu.
   */
  public void drawBackground() {
    //StdDraw.setPenColor(StdDraw.LIGHT_GREEN);
    StdDraw.picture(0.5, 0.5, "images/fondtest_complet.jpg", 1.0, 1.0);

//    for (int i = 0; i < nbSquareX; i++) {
//      for (int j = 0; j < nbSquareY; j++) {
//        StdDraw.filledRectangle(i * squareWidth + squareWidth / 2, j * squareHeight + squareHeight / 2, squareWidth , squareHeight);
//        StdDraw.picture(i * squareWidth + squareWidth / 2, j * squareHeight + squareHeight / 2, "images/grass.jpg", squareWidth, squareHeight);
//      }
//    }


  }

  /**
   * Initialise le chemin sur la position du point de départ des monstres. Cette fonction permet d'afficher une route qui sera différente du décors.
   */
  public void drawPath() {


//		 Iterator<Position> i = paths.iterator();
//		 Position p;
//		 while (i.hasNext()) {
//		 	p = i.next();
//			 StdDraw.setPenColor(StdDraw.YELLOW);
//			 double coorX = p.x / nbSquareX + (squareWidth/2);
//			 double coorY = p.y / nbSquareY + (squareHeight/2);
//			 //StdDraw.filledRectangle(coorX, coorY, squareWidth / 2, squareHeight / 2);
//			 StdDraw.picture(coorX, coorY, "images/sand.jpg", squareWidth, squareHeight);
//		 }

  }

  /**
   * Affiche certaines informations sur l'écran telles que les points de vie du joueur ou son or
   */
  public void drawInfos() {
    this.HUD.UpdateInterface(StdDraw.mouseX(), StdDraw.mouseY(), this.delta_time);
  }

  /**
   * Fonction qui récupère le positionnement de la souris et permet d'afficher une image de tour en temps réél
   * lorsque le joueur appuie sur une des touches permettant la construction d'une tour.
   */
  public void drawMouse() {
    double normalizedX = (int) (StdDraw.mouseX() / this.squareWidth) * this.squareWidth + this.squareWidth / 2;
    double normalizedY = (int) (StdDraw.mouseY() / this.squareHeight) * this.squareHeight + this.squareHeight / 2;
    if (building_class != null) {
      Position mousep = new Position((int) ((normalizedX * nbSquareX)), (int) ((normalizedY * nbSquareY)));
      if (paths.contains(mousep) || list_tower.containsKey(mousep)) {
        StdDraw.picture(normalizedX, normalizedY, "images/Select_tile_unavailable.png", squareWidth, squareHeight);
      } else {
        StdDraw.picture(normalizedX, normalizedY, "images/Select_tile.png", squareWidth, squareHeight);
      }
    }
  }

  double secondCounter = 0.0;

  /**
   * Pour chaque monstre de la liste de monstres de la vague, utilise la fonction update() qui appelle les fonctions run() et draw() de Monster.
   * Modifie la position du monstre au cours du temps à l'aide du paramètre nextP.
   */
  public void updateMonsters() {
    Iterator<Monster> i = this.monsters.iterator();
    Monster m;
    while (i.hasNext()) {
      m = i.next();
      m.update(this.delta_time);

      if (m.hasFinishedPath() && this.secondCounter >= 1.0) {
        this.life -= 1;
      }
      if (m.isDead()) {
        i.remove();
        this.HUD.setWaveEnemyProgress(((100 * this.monsters.size()) / (double) this.totalMonsterAmount));
      }
    }

    if (this.monsters.size() == 0) {
      this.isMonsterActing = false;
      new java.util.Timer().schedule(
          new java.util.TimerTask() {
            @Override
            public void run() {
              initWave(totalMonsterAmount + 1);
            }
          },
          5000
      );
    }

  }


  public void initWave(int monsterAmount) {
    this.HUD.setWaveEnemyProgress(100);
    this.totalMonsterAmount = monsterAmount;
    this.monsters = new ArrayList<>();
    for (int i = 0; i < monsterAmount; i++) {
      Position startingPosition = new Position(this.paths.get(0).x * this.squareWidth + this.squareWidth / 2, this.paths.get(0).y * this.squareHeight + this.squareHeight / 2 + 0.04 * i);
      this.monsters.add(new BaseMonster(startingPosition, this.paths, this.nbSquareX, this.nbSquareY, (double) 1 / this.nbSquareX, (double) 1 / this.nbSquareY, 20));
    }
    this.isMonsterActing = true;
  }

  public void updateTowers() {
    double normalizedX = (int) (StdDraw.mouseX() / this.squareWidth) * this.squareWidth + this.squareWidth / 2;
    double normalizedY = (int) (StdDraw.mouseY() / this.squareHeight) * this.squareHeight + this.squareHeight / 2;
    Position mousep = new Position((int) ((normalizedX * nbSquareX)), (int) ((normalizedY * nbSquareY)));
    Tower towerUnderMouse = list_tower.get(mousep);
    for (Map.Entry<Position, Tower> entry : list_tower.entrySet()) {
      boolean hover = false;
      Tower value = entry.getValue();
      if (building_class == null) hover = value == towerUnderMouse;
      value.Update(this.delta_time, hover);
    }
  }

  /**
   * Met à jour toutes les informations du plateau de jeu ainsi que les déplacements des monstres et les attaques des tours.
   *
   * @return les points de vie restants du joueur
   */
  public int update() {
    this.drawBackground();
    this.drawPath();
    if (this.isMonsterActing) {
      this.updateMonsters();
    }

    this.updateTowers();
    this.drawMouse();
    this.drawInfos();

    return this.life;
  }

  /**
   * Récupère la touche appuyée par l'utilisateur et affiche les informations pour la touche séléctionnée
   *
   * @param key la touche utilisée par le joueur
   */
  public void keyPress(char key) {
    key = Character.toLowerCase(key);
    this.key = key;
    switch (key) {
      case 'a':
        System.out.println("Arrow Tower selected (50g).");
        break;
      case 'b':
        System.out.println("Bomb Tower selected (60g).");
        break;
      case 'e':
        System.out.println("Evolution selected (40g).");
        break;
      case 's':
        System.out.println("Starting game!");
      case 'q':
        System.out.println("Exiting.");
    }
  }

  /**
   * Vérifie lorsque l'utilisateur clique sur sa souris qu'il peut:
   * - Ajouter une tour à la position indiquée par la souris.
   * - Améliorer une tour existante.
   * Puis l'ajouter à la liste des tours
   *
   * @param x
   * @param y
   */
  public void mouseClick(double x, double y, int mouseButton) {
    double normalizedX = (int) (x / this.squareWidth) * this.squareWidth + this.squareWidth / 2;
    double normalizedY = (int) (y / this.squareHeight) * this.squareHeight + this.squareHeight / 2;
    Position p = new Position(normalizedX, normalizedY);
    Position mousep = new Position((int) ((normalizedX * nbSquareX)), (int) ((normalizedY * nbSquareY)));

    this.HUD.onClick(x, y, mouseButton);

    if (building_class != null && !this.needReleaseMouse) {
      if (!(paths.contains(mousep) || list_tower.containsKey(mousep))) {
        int price = this.price_tower.get(building_class);
        if (this.player_wallet.pay(price)) {
          try {
            Constructor cons = building_class.getConstructor(Position.class, double.class, double.class, World.class);
            Tower t = (Tower) cons.newInstance(new Position(normalizedX, normalizedY), this.squareWidth, this.squareHeight, this);
            list_tower.put(new Position((int) ((normalizedX * nbSquareX)), (int) ((normalizedY * nbSquareY))), t);
          } catch (NoSuchMethodException e) {
            e.printStackTrace();
          } catch (IllegalAccessException e) {
            e.printStackTrace();
          } catch (InstantiationException e) {
            e.printStackTrace();
          } catch (InvocationTargetException e) {
            e.printStackTrace();
          }
        }
      }
    } else if (!this.needReleaseMouse) {
      Tower towerUnderMouse = list_tower.get(mousep);
      if (towerUnderMouse != null) {
        this.HUD.showUpgradeTowerBox(towerUnderMouse);
      }
    }
  }

  /**
   * Comme son nom l'indique, cette fonction permet d'afficher dans le terminal les différentes possibilités
   * offertes au joueur pour intéragir avec le clavier
   */
  public void printCommands() {
    System.out.println("Press A to select Arrow Tower (cost 50g).");
    System.out.println("Press B to select Cannon Tower (cost 60g).");
    System.out.println("Press E to update a tower (cost 40g).");
    System.out.println("Click on the grass to build it.");
    System.out.println("Press S to start.");
  }

  public void startBuilding(Class c) {
    this.building_class = c;
    this.needReleaseMouse = true;
  }

  public void stopBuilding() {
    this.building_class = null;
    this.needReleaseMouse = true;
  }

  /**
   * Récupère la touche entrée au clavier ainsi que la position de la souris et met à jour le plateau en fonction de ces interractions
   */
  public void run() {
    this.printCommands();
    while (!this.end) {
      long time_nano = System.nanoTime();

      StdDraw.clear();
			/*if (StdDraw.hasNextKeyTyped()) {
				keyPress(StdDraw.nextKeyTyped());
			}*/

      if (StdDraw.isMousePressed()) {
        if (!this.needReleaseMouse) {
          this.mouseClick(StdDraw.mouseX(), StdDraw.mouseY(), StdDraw.mouseButtonPressed());
        }
      } else if (this.needReleaseMouse) {
        this.needReleaseMouse = false;
      }

      this.update();
      StdDraw.show();
      //StdDraw.pause(20);

      int ms = (int) (System.nanoTime() - time_nano) / 1000000;
      int fps = 1000 / ms;
      this.delta_time = 1.0 / fps;

      if (this.secondCounter >= 1.0) {
        this.secondCounter = 0.0;
      } else {
        this.secondCounter += this.delta_time;
      }

    }
  }
}
