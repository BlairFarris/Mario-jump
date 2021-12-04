// William Blair Farris
// 10/23/20
// A mario assignment to get us used to allowing control of mario and collission detection.

import java.awt.Graphics;

abstract class Sprite{
	int x, y;
	int w, h;

	abstract void gravity();
	abstract void draw(Graphics g);
	abstract void move();

	boolean isTube()	{	return false;	}
	boolean isMario()	{	return false;	}
	boolean isGoomba()	{	return false;	}
	boolean isFireball()	{	return false;	}

	boolean collision(Sprite a){
		if(this.x + this.w < a.x)
			return false;
		if(this.x > a.x + a.w)
			return false;
		if(this.y + this.y < a.y)
			return false;
		if(this.y > a.y + a.h)
			return false;
		return true;
	}

	
}