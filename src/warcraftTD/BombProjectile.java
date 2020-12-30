package warcraftTD;

public class BombProjectile extends Projectile{
    double rangeExplosion;

    public BombProjectile(Position initialPosition, Vector direction, World world, int damage, double rangeExplosion) {
        super(initialPosition, direction, world, damage);
        this.sprite = "images/bomb.png";
        this.rotating = false;
        this.hitrange = 0.02;
        this.width = 0.02;
        this.height = 0.02;
        this.speed = 0.4;
        this.rangeExplosion = rangeExplosion;
    }

    @Override
    public boolean onCollideMonster(Monster m) {
        m.takeDamage(this.damage, this.world);
        for (Monster mo:this.world.monsters) {
            if(mo!=m && mo.p.dist(this.position)<rangeExplosion) mo.takeDamage(damage/2, this.world);
        }
        return false;
    }
}