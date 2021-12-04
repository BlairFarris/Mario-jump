// William Blair Farris
// 10/23/20
// A mario assignment to get us used to allowing control of mario and collission detection.

import java.awt.Graphics;
import java.awt.image.BufferedImage;

class Mario extends Sprite{
	int px, py;
	static BufferedImage [] mario_images;
	int marioImageNum;
	double vert_velocity;
	boolean marioJumped;

	public Mario(int x, int y){
		this.x = x;
		this.y = y;
		w = 60;
		h = 95;
		marioImageNum = 0;
		vert_velocity = 12.0;
		marioJumped = false;

		mario_images = new BufferedImage[5];

		for(int i = 0; i <= 4; i++){
			mario_images[i] = View.loadImage("mario" + (i + 1) + ".png");
		}
	}

	void prevPos(){
		px = x;
		py = y;
	}

	void tubeReject(Tube t){
		if(x + w >= t.x && px + w <= t.x)//Double check with equal signs
			x = t.x - w;
		if(x <= t.x + t.w && px >= t.x + t.w)
			x = t.x + t.w;
		if(y + h >= t.y && py + h <= t.y){
			marioJumped = false;
			vert_velocity = 0.0;
			y = t.y - h;
		}
		if(y >= t.y + t.h && py <= t.y + t.h)
			y = t.y + t.h;
	}

	void update(){
		marioImageNum++;
		if(marioImageNum > 4)
			marioImageNum = 0;
	}

	void draw(Graphics g){
		g.drawImage(mario_images[marioImageNum], 200, y, null);
	}

	void gravity(){
		vert_velocity += 2.9;
		y += vert_velocity;
		if(y > 300){
			vert_velocity = 0.0;
			y = 300;
		}
		if(y < 0){
			y = 0;
		}
		if(vert_velocity == 0.0){
			marioJumped = false;
		}
	}

	void jump(){
		if(marioJumped == false){
			vert_velocity -= 7.5;
		}
		if(vert_velocity < -28){
			marioJumped = true;
		}
	}
	
	@Override
	boolean isMario(){
		return true;
	}

	void move(){}
}