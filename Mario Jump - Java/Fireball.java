// William Blair Farris
// 10/23/20
// A mario assignment to get us used to allowing control of mario and collission detection.

import java.awt.Graphics;
import java.awt.image.BufferedImage;

class Fireball extends Sprite{
	static BufferedImage image;
	Model model;
	double vert_velocity;

	Fireball(int fireX, int fireY, Model m){
		this.x = fireX;
		this.y = fireY;
		w = 25;
		h = 25;
		model = m;
		vert_velocity = 0;
		loadFireImage();
	}

	Fireball(Json ob, Model m){
		x = (int)ob.getLong("x");
		y = (int)ob.getLong("y");
		w = 50;
		h = 60;
		model = m;
		loadFireImage();
	}

	void loadFireImage(){
		if(image == null){
			image = View.loadImage("fireball.png");
		}
	}
	
	@Override
	boolean isFireball(){
		return true;
	}

	void gravity(){
		vert_velocity += 2.9;
		y += vert_velocity;
		if(y > 395 - h){
			y = 395 - h;
			vert_velocity = -16.8;
		}
	}

	void move(){
		x += 13;
	}

	void draw(Graphics g){
		g.drawImage(image, x - model.mario.x + 200, y, null);
	}

}