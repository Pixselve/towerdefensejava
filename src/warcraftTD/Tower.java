package warcraftTD;

abstract public class Tower {
    protected String sprite;
    protected String sprite_hover;
    protected Position position;
    protected double width;
    protected double height;

    public Tower(Position p, double width, double height){
        this.position = p;
        this.width = width;
        this.height = height;
    }

    public void Update(double delta_time, boolean hovered){
        if(hovered) StdDraw.picture(this.position.x, this.position.y, this.sprite_hover, this.width, this.height);
        else StdDraw.picture(this.position.x, this.position.y, this.sprite, this.width, this.height);
    }
}