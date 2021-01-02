package warcraftTD.hud;

import warcraftTD.libs.StdDraw;
import warcraftTD.utils.Position;

public class Button extends ClickableElement {
  private final String sprite;
  private final String sprite_hover;
  private final String action;
  private final boolean clickable;

  public String getSprite() {
    return this.sprite;
  }

  public String getSprite_hover() {
    return this.sprite_hover;
  }

  public String getAction() {
    return this.action;
  }

  public boolean isClickable() {
    return this.clickable;
  }

  public Button(Position pos, double width, double height, String sprite, String sprite_hover, String action, Interface Parent) {
    super(pos, width, height, Parent);
    this.action = action;
    this.sprite = sprite;
    this.sprite_hover = sprite_hover;
    this.clickable = true;
  }

  @Override
  public void setPosition(Position position) {
    super.setPosition(position);
  }

  @Override
  public void update(double MouseX, double MouseY, double delta_time) {
    if (this.isVisible()) {
      if (this.getHitBox().isHit(MouseX, MouseY) && this.isEnabled()) {
        StdDraw.picture(this.getPosition().getX(), this.getPosition().getY(), this.sprite_hover, this.getWidth(), this.getHeight());
      } else {
        StdDraw.picture(this.getPosition().getX(), this.getPosition().getY(), this.sprite, this.getWidth(), this.getHeight());
        if (!this.isEnabled())
          StdDraw.picture(this.getPosition().getX(), this.getPosition().getY(), "images/disable_buttonshop.png", this.getWidth(), this.getHeight());
      }
    }
  }

  @Override
  public String onClick(double MouseX, double MouseY) {
    if(this.isVisible() && this.isEnabled() && this.clickable && this.getHitBox().isHit(MouseX, MouseY)){
      return this.action;
    } else {
      return "";
    }
  }
}