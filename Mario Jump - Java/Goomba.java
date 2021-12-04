// William Blair Farris
// 10/23/20
// A mario assignment to get us used to allowing control of mario and collission detection.

import java.awt.Graphics;
import java.awt.image.BufferedImage;

class Goomba extends Sprite{
	static BufferedImage image;
	Model model;
	double vert_velocity;
	boolean goombaTouchedRight;
	boolean burnGoomba;
	int framesOnFire;

	Goomba(int goombaX, int goombaY, Model m){
		this.x = goombaX;
		this.y = goombaY;
		w = 50;
		h = 60;
		model = m;
		goombaTouchedRight = false;
		burnGoomba = false;
		framesOnFire = 40;
		vert_velocity = 10.0;
		loadGoombaImage();
	}

	Goomba(Json ob, Model m){
		x = (int)ob.getLong("x");
		y = (int)ob.getLong("y");
		w = 50;
		h = 60;
		framesOnFire = 40;
		model = m;
		loadGoombaImage();
	}

	void loadGoombaImage(){
		if(image == null){
			image = View.loadImage("goomba.png");
		}
	}
	
	@Override
	boolean isGoomba(){
		return true;
	}

	void gravity(){
		vert_velocity += 2.9;
		y += vert_velocity;
		if(y > 395 - h){
			vert_velocity = 0.0;
			y = 395 - h;
		}
	}

	void move(){
		for(int i = 0; i < model.sprites.size(); i++){
			Sprite s = model.sprites.get(i);
			if(s.isFireball())
				if(collision(s)){
					burnGoomba = true;
					model.sprites.remove(i);
				}
		}
		if(!burnGoomba){
			for(int i = 0; i < model.sprites.size(); i++){
				Sprite s = model.sprites.get(i);
				if(s.isTube())
					if(collision(s))
						goombaTouchedRight = !goombaTouchedRight;
			}
			if(!goombaTouchedRight)
				x = x - 3;
			if(goombaTouchedRight)
				x = x + 3;
		}
		if(burnGoomba){
			image = View.loadImage("goomba_fire.png");
			for(int i = 0; i < model.sprites.size(); i++){
				Sprite s = model.sprites.get(i);
				if(s.isGoomba()){	
					
					if(framesOnFire > 0){
						framesOnFire = framesOnFire - 1;
					} else {
						model.sprites.remove(i);
					}
				}
			}
		}
		
		
	}

	void draw(Graphics g){
		g.drawImage(image, x - model.mario.x + 200, y, null);
	}

}