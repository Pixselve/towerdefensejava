package warcraftTD.hud;

import warcraftTD.World;
import warcraftTD.towers.*;
import warcraftTD.utils.Position;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Interface {
  private final World world;
  private final List<Element> list__Elements;
  private final Button shop_btn;
  private final ProgressBar waveEnnemyBar;
  private final HorizontalGroupBox shopBox;
  private final Text fps_text;
  private final Text building_text;
  private final List<Element> garbage;
  private final boolean dev_mode = true;

  public World getWorld() {
    return this.world;
  }

  private final Text walletHUD;
  private final Text life_text;

  private final HorizontalGroupBox upgradeBox;

  private boolean building;
  private Tower upgradingTower;

  protected Element current_Disabled;

  private Image upgradeDamageIm;
  private Image upgradeRangeIm;
  private Image upgradeAttackSpeedIm;
  private Image upgradeSpecialIm;
  private Image upgradeSpecialIm_icon;
  private Text upgradeDamagePrice;
  private Text upgradeRangePrice;
  private Text upgradeAttackSpeedPrice;
  private Text upgradeSpecialPrice;

  public Interface(World parent) {
    this.world = parent;
    this.list__Elements = new ArrayList<Element>();
    this.garbage = new ArrayList<Element>();
    this.shop_btn = new Button(new Position(0.9, 0.1), 0.1, 0.13, "images/shop_button.png", "images/shop_button_hover.png", "Shopping", this);
    this.list__Elements.add(this.shop_btn);
    this.waveEnnemyBar = new ProgressBar(new Position(0.5, 0.94), 0.3, 0.1, "images/waveprogressbar.png", "images/bar_fill.png", this, 0.062);
    this.list__Elements.add(this.waveEnnemyBar);

    this.shopBox = new HorizontalGroupBox(new Position(0.5, 0.1), 0.75, 0.15, this, "images/background_hor_box.png");
    this.list__Elements.add(this.shopBox);
    Button closeshop_btn = new Button(new Position(0.97, 0.88), 0.06, 0.06, "images/close_button.png", "images/close_button_hover.png", "ClosingBox", this);
    this.shopBox.addHUDElement(closeshop_btn);
    TowerBuyButton turret_arrow = new TowerBuyButton(new Position(0.17, 0.7), 0.12, 0.15, "images/shop_arrowtower.png", "images/shop_arrowtower_hover.png", "turret_arrow", this, Arrow.class);
    this.shopBox.addHUDElement(turret_arrow);
    TowerBuyButton turret_bomb = new TowerBuyButton(new Position(0.37, 0.7), 0.12, 0.15, "images/shop_bombtower.png", "images/shop_bombtower_hover.png", "turret_bomb", this, Bomb.class);
    this.shopBox.addHUDElement(turret_bomb);
    TowerBuyButton turret_ice = new TowerBuyButton(new Position(0.57, 0.7), 0.12, 0.15, "images/shop_icetower.png", "images/shop_icetower_hover.png", "turret_ice", this, Ice.class);
    this.shopBox.addHUDElement(turret_ice);
    TowerBuyButton turret_poison = new TowerBuyButton(new Position(0.77, 0.7), 0.12, 0.15, "images/shop_poisontower.png", "images/shop_poisontower_hover.png", "turret_poison", this, Poison.class);
    this.shopBox.addHUDElement(turret_poison);

    this.upgradeBox = new HorizontalGroupBox(new Position(0.5, 0.13), 0.75, 0.25, this, "images/box_upgrade.png");
    this.list__Elements.add(this.upgradeBox);
    Button closeupgrade_btn = new Button(new Position(0.97, 0.85), 0.06, 0.06, "images/close_button.png", "images/close_button_hover.png", "ClosingUpgrade", this);
    this.upgradeBox.addHUDElement(closeupgrade_btn);
    this.upgradeDamageIm = new Image(new Position(0.2, 0.52), 0.1, 0.04, this, "images/level1.png");
    this.upgradeBox.addHUDElement(this.upgradeDamageIm);
    this.upgradeRangeIm = new Image(new Position(0.2, 0.25), 0.1, 0.04, this, "images/level1.png");
    this.upgradeBox.addHUDElement(this.upgradeRangeIm);
    this.upgradeAttackSpeedIm = new Image(new Position(0.67, 0.55), 0.1, 0.04, this, "images/level1.png");
    this.upgradeBox.addHUDElement(this.upgradeAttackSpeedIm);
    this.upgradeSpecialIm = new Image(new Position(0.67, 0.25), 0.1, 0.04, this, "images/level1.png");
    this.upgradeBox.addHUDElement(this.upgradeSpecialIm);
    Button upgradebtn = new Button(new Position(0.32, 0.52), 0.055, 0.055, "images/button_upgrade.png", "images/button_upgrade_hover.png", "UpgradeDamage", this);
    this.upgradeBox.addHUDElement(upgradebtn);
    upgradebtn = new Button(new Position(0.32, 0.25), 0.055, 0.055, "images/button_upgrade.png", "images/button_upgrade_hover.png", "UpgradeRange", this);
    this.upgradeBox.addHUDElement(upgradebtn);
    upgradebtn = new Button(new Position(0.79, 0.56), 0.055, 0.055, "images/button_upgrade.png", "images/button_upgrade_hover.png", "UpgradeAttackSpeed", this);
    this.upgradeBox.addHUDElement(upgradebtn);
    upgradebtn = new Button(new Position(0.79, 0.27), 0.055, 0.055, "images/button_upgrade.png", "images/button_upgrade_hover.png", "UpgradeSpecial", this);
    this.upgradeBox.addHUDElement(upgradebtn);
    this.upgradeDamagePrice = new Text(new Position(0.415, 0.52), 0.0, 0.0, new Font("Arial", Font.BOLD, 40), this, "100");
    this.upgradeBox.addHUDElement(this.upgradeDamagePrice);
    this.upgradeRangePrice = new Text(new Position(0.415, 0.25), 0.0, 0.0, new Font("Arial", Font.BOLD, 40), this, "100");
    this.upgradeBox.addHUDElement(this.upgradeRangePrice);
    this.upgradeBox.addHUDElement(upgradebtn);
    this.upgradeAttackSpeedPrice = new Text(new Position(0.875, 0.52), 0.0, 0.0, new Font("Arial", Font.BOLD, 40), this, "100");
    this.upgradeBox.addHUDElement(this.upgradeAttackSpeedPrice);
    this.upgradeSpecialPrice = new Text(new Position(0.875, 0.25), 0.0, 0.0, new Font("Arial", Font.BOLD, 40), this, "100");
    this.upgradeBox.addHUDElement(this.upgradeSpecialPrice);
    this.upgradeSpecialIm_icon = new Image(new Position(0.55, 0.25), 0.05, 0.05, this, "images/poison_upgrade.png");
    this.upgradeBox.addHUDElement(this.upgradeSpecialIm_icon);

    this.fps_text = new Text(new Position(0.08, 0.85), 0.0, 0.0, new Font("Arial", Font.BOLD, 40), this, "");
    this.list__Elements.add(this.fps_text);

    Image imageMoney = new Image(new Position(0.92, 0.95), 0.15, 0.08, this, "images/moneybox.png");
    this.list__Elements.add(imageMoney);
    this.walletHUD = new Text(new Position(0.94, 0.947), 0.0, 0.0, new Font("Arial", Font.BOLD, 40), this, "0");
    this.list__Elements.add(this.walletHUD);
    this.walletHUD.setColor(new Color(255, 246, 51));

    Image imageLife = new Image(new Position(0.08, 0.95), 0.15, 0.08, this, "images/lifebar.png");
    this.list__Elements.add(imageLife);
    this.life_text = new Text(new Position(0.1, 0.947), 0.0, 0.0, new Font("Arial", Font.BOLD, 40), this, "0");
    this.list__Elements.add(this.life_text);
    this.life_text.setColor(new Color(194, 4, 105));

    this.building_text = new Text(new Position(0.5, 0.07), 0.0, 0.0, new Font("Arial", Font.BOLD, 40), this, "Right click to cancel !");
    this.building_text.setVisible(false);
    this.list__Elements.add(this.building_text);

    this.upgradingTower = null;
    this.building = false;
  }

  public void UpdateInterface(double MouseX, double MouseY, double delta_time) {
    if (this.dev_mode) this.fps_text.setText("FPS : " + (int) (1 / delta_time));
    this.walletHUD.setText(this.world.getPlayer_wallet().getMoney() + "");
    this.life_text.setText(this.world.getLife() + "");

    Iterator<Element> i = this.list__Elements.iterator();
    Element el;
    while (i.hasNext()) {
      el = i.next();
      el.Update(MouseX, MouseY, delta_time);
    }
    i = this.garbage.iterator();
    while (i.hasNext()) {
      el = i.next();
      this.list__Elements.remove(el);
    }
  }

  public void makeAction(String action, Element from) {
    switch (action) {
      case "Shopping":
        this.shopBox.ShowBox(0.3, 0.0);
        this.shop_btn.setVisible(false);
        break;
      case "ClosingBox":
        this.shopBox.HideBox();
        this.shop_btn.visible = true;
        this.world.setNeedReleaseMouse(true);
        if (this.current_Disabled != null && this.current_Disabled != from) this.current_Disabled.enabled = true;
        break;
      case "ClosingUpgrade":
        this.upgradingTower = null;
        this.upgradeBox.HideBox();
        this.world.setNeedReleaseMouse(true);
        this.shop_btn.setVisible(true);
        break;
      case "UpgradeDamage":
        if (this.upgradingTower != null && this.upgradingTower.getDamage_u().getLevel() != this.upgradingTower.getDamage_u().getMax_level()) {
          if (this.world.getPlayer_wallet().pay(this.upgradingTower.getDamage_u().getLevel_price()[this.upgradingTower.getDamage_u().getLevel()])) {
            this.upgradingTower.upgradeDamage();
            this.world.setNeedReleaseMouse(true);
            this.upgradeDamageIm.setSprite("images/level" + this.upgradingTower.getDamage_u().getLevel() + ".png");
            if (this.upgradingTower.getDamage_u().getLevel() != this.upgradingTower.getDamage_u().getMax_level())
              this.upgradeDamagePrice.setText(this.upgradingTower.getDamage_u().getLevel_price()[this.upgradingTower.getDamage_u().getLevel()] + "");
            else this.upgradeDamagePrice.setText("---");
          }
        }
        break;
      case "UpgradeRange":
        if (this.upgradingTower != null && this.upgradingTower.getRange_u().getLevel() != this.upgradingTower.getRange_u().getMax_level()) {
          if (this.world.getPlayer_wallet().pay(this.upgradingTower.getRange_u().getLevel_price()[this.upgradingTower.getRange_u().getLevel()])) {
            this.upgradingTower.upgradeRange();
            this.world.setNeedReleaseMouse(true);
            this.upgradeRangeIm.setSprite("images/level" + this.upgradingTower.getRange_u().getLevel() + ".png");
            if (this.upgradingTower.getRange_u().getLevel() != this.upgradingTower.getRange_u().getMax_level())
              this.upgradeRangePrice.setText(this.upgradingTower.getRange_u().getLevel_price()[this.upgradingTower.getRange_u().getLevel()] + "");
            else this.upgradeRangePrice.setText("---");
          }
        }
        break;
      case "UpgradeAttackSpeed":
        if (this.upgradingTower != null && this.upgradingTower.getAttackspeed_u().getLevel() != this.upgradingTower.getAttackspeed_u().getMax_level()) {
          if (this.world.getPlayer_wallet().pay(this.upgradingTower.getAttackspeed_u().getLevel_price()[this.upgradingTower.getAttackspeed_u().getLevel()])) {
            this.upgradingTower.upgradeAttackSpeed();
            this.world.setNeedReleaseMouse(true);
            this.upgradeAttackSpeedIm.setSprite("images/level" + this.upgradingTower.getAttackspeed_u().getLevel() + ".png");
            if (this.upgradingTower.getAttackspeed_u().getLevel() != this.upgradingTower.getAttackspeed_u().getMax_level())
              this.upgradeAttackSpeedPrice.setText(this.upgradingTower.getAttackspeed_u().getLevel_price()[this.upgradingTower.getAttackspeed_u().getLevel()] + "");
            else this.upgradeAttackSpeedPrice.setText("---");
          }
        }
        break;
      case "UpgradeSpecial":
        if (this.upgradingTower != null && this.upgradingTower.getSpecial_u().getLevel() != this.upgradingTower.getSpecial_u().getMax_level()) {
          if (this.world.getPlayer_wallet().pay(this.upgradingTower.getSpecial_u().getLevel_price()[this.upgradingTower.getSpecial_u().getLevel()])) {
            this.upgradingTower.upgradeSpecial();
            this.world.setNeedReleaseMouse(true);
            this.upgradeSpecialIm.setSprite("images/level" + this.upgradingTower.getSpecial_u().getLevel() + ".png");
            if (this.upgradingTower.getSpecial_u().getLevel() != this.upgradingTower.getSpecial_u().getMax_level())
              this.upgradeSpecialPrice.setText(this.upgradingTower.getSpecial_u().getLevel_price()[this.upgradingTower.getSpecial_u().getLevel()] + "");
            else this.upgradeSpecialPrice.setText("---");
          }
        }
        break;
    }
  }

  public void startBuilding(Class towerClass) {
    this.building = true;
    this.shopBox.setVisible(false);
    this.building_text.setVisible(true);
    this.world.startBuilding(towerClass);
  }

  public void onClick(double MouseX, double MouseY, int mouseButton) {
    if (mouseButton == 3 && this.building) {
      this.building = false;
      this.shopBox.setVisible(true);
      this.building_text.setVisible(false);
      this.world.stopBuilding();
      return;
    }
    Element elc = null;
    String action = "";
    for (Element el : this.list__Elements) {
      action = el.onClick(MouseX, MouseY);
      if (!action.equals("")) {
        elc = el;
        break;
      }
    }
    if (!action.equals("")) this.makeAction(action, elc);
  }

  public void setWaveEnemyProgress(double ProgressPercent) {
    this.waveEnnemyBar.setProgressPercent(ProgressPercent);
  }

  public double getWaveEnemyProgress() {
    return this.waveEnnemyBar.getProgressPercent();
  }

  public void addNotifText(Position p, Font font, double deltay, String text, Color color) {
    NotifText notif = new NotifText(p, 0.0, 0.0, font, this, text, deltay, color);
    this.list__Elements.add(notif);
  }

  public void removeNotif(NotifText text) {
    this.garbage.add(text);
    //list_HUD_Elements.remove(text);
  }

  public void showUpgradeTowerBox(Tower tower) {
    if (this.upgradingTower != tower) {
      if (this.shopBox.isVisible()) this.shopBox.HideBox();
      this.upgradingTower = tower;
      this.world.setNeedReleaseMouse(true);
      this.upgradeBox.ShowBox(0.3, 0.0);
      this.shop_btn.setVisible(false);

      this.upgradeSpecialIm_icon.setSprite(tower.getSprite_HUD_special());

      this.upgradeDamageIm.setSprite("images/level" + this.upgradingTower.getDamage_u().getLevel() + ".png");
      if (tower.getDamage_u().getLevel() != tower.getDamage_u().getMax_level())
        this.upgradeDamagePrice.setText(tower.getDamage_u().getLevel_price()[tower.getDamage_u().getLevel()] + "");
      else this.upgradeDamagePrice.setText("---");

      this.upgradeRangeIm.setSprite("images/level" + this.upgradingTower.getRange_u().getLevel() + ".png");
      if (tower.getRange_u().getLevel() != tower.getRange_u().getMax_level())
        this.upgradeRangePrice.setText(tower.getRange_u().getLevel_price()[tower.getRange_u().getLevel()] + "");
      else this.upgradeRangePrice.setText("---");

      this.upgradeAttackSpeedIm.setSprite("images/level" + this.upgradingTower.getAttackspeed_u().getLevel() + ".png");
      if (tower.getAttackspeed_u().getLevel() != tower.getAttackspeed_u().getMax_level())
        this.upgradeAttackSpeedPrice.setText(tower.getAttackspeed_u().getLevel_price()[tower.getAttackspeed_u().getLevel()] + "");
      else this.upgradeAttackSpeedPrice.setText("---");

      this.upgradeSpecialIm.setSprite("images/level" + this.upgradingTower.getSpecial_u().getLevel() + ".png");
      if (tower.getSpecial_u().getLevel() != tower.getSpecial_u().getMax_level())
        this.upgradeSpecialPrice.setText(tower.getSpecial_u().getLevel_price()[tower.getSpecial_u().getLevel()] + "");
      else this.upgradeSpecialPrice.setText("---");
    }
  }

  public List<Element> getList__Elements() {
    return this.list__Elements;
  }

  public Button getShop_btn() {
    return this.shop_btn;
  }

  public ProgressBar getWaveEnnemyBar() {
    return this.waveEnnemyBar;
  }

  public HorizontalGroupBox getShopBox() {
    return this.shopBox;
  }

  public Text getFps_text() {
    return this.fps_text;
  }

  public Text getBuilding_text() {
    return this.building_text;
  }

  public List<Element> getGarbage() {
    return this.garbage;
  }

  public boolean isDev_mode() {
    return this.dev_mode;
  }

  public Text getWalletHUD() {
    return this.walletHUD;
  }

  public Text getLife_text() {
    return this.life_text;
  }

  public HorizontalGroupBox getUpgradeBox() {
    return this.upgradeBox;
  }

  public boolean isBuilding() {
    return this.building;
  }

  public void setBuilding(boolean building) {
    this.building = building;
  }

  public Tower getUpgradingTower() {
    return this.upgradingTower;
  }

  public void setUpgradingTower(Tower upgradingTower) {
    this.upgradingTower = upgradingTower;
  }

  public Element getCurrent_Disabled() {
    return this.current_Disabled;
  }

  public void setCurrent_Disabled(Element current_Disabled) {
    this.current_Disabled = current_Disabled;
  }

  public Image getUpgradeDamageIm() {
    return this.upgradeDamageIm;
  }

  public void setUpgradeDamageIm(Image upgradeDamageIm) {
    this.upgradeDamageIm = upgradeDamageIm;
  }

  public Image getUpgradeRangeIm() {
    return this.upgradeRangeIm;
  }

  public void setUpgradeRangeIm(Image upgradeRangeIm) {
    this.upgradeRangeIm = upgradeRangeIm;
  }

  public Image getUpgradeAttackSpeedIm() {
    return this.upgradeAttackSpeedIm;
  }

  public void setUpgradeAttackSpeedIm(Image upgradeAttackSpeedIm) {
    this.upgradeAttackSpeedIm = upgradeAttackSpeedIm;
  }

  public Image getUpgradeSpecialIm() {
    return this.upgradeSpecialIm;
  }

  public void setUpgradeSpecialIm(Image upgradeSpecialIm) {
    this.upgradeSpecialIm = upgradeSpecialIm;
  }

  public Image getUpgradeSpecialIm_icon() {
    return this.upgradeSpecialIm_icon;
  }

  public void setUpgradeSpecialIm_icon(Image upgradeSpecialIm_icon) {
    this.upgradeSpecialIm_icon = upgradeSpecialIm_icon;
  }

  public Text getUpgradeDamagePrice() {
    return this.upgradeDamagePrice;
  }

  public void setUpgradeDamagePrice(Text upgradeDamagePrice) {
    this.upgradeDamagePrice = upgradeDamagePrice;
  }

  public Text getUpgradeRangePrice() {
    return this.upgradeRangePrice;
  }

  public void setUpgradeRangePrice(Text upgradeRangePrice) {
    this.upgradeRangePrice = upgradeRangePrice;
  }

  public Text getUpgradeAttackSpeedPrice() {
    return this.upgradeAttackSpeedPrice;
  }

  public void setUpgradeAttackSpeedPrice(Text upgradeAttackSpeedPrice) {
    this.upgradeAttackSpeedPrice = upgradeAttackSpeedPrice;
  }

  public Text getUpgradeSpecialPrice() {
    return this.upgradeSpecialPrice;
  }

  public void setUpgradeSpecialPrice(Text upgradeSpecialPrice) {
    this.upgradeSpecialPrice = upgradeSpecialPrice;
  }
}
