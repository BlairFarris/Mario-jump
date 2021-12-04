// William Blair Farris
// 10/23/20
// A mario assignment to get us used to allowing control of mario and collission detection.

import java.awt.Graphics;
import java.awt.image.BufferedImage;

class Tube extends Sprite{
	static BufferedImage image;
	Model model;

	Tube(int pipeX, int pipeY, Model m){
		this.x = pipeX;
		this.y = pipeY;
		w = 55;
		h = 400;
		model = m;
		loadTubeImage();
	}

	Tube(Json ob, Model m){
		x = (int)ob.getLong("x");
		y = (int)ob.getLong("y");
		w = 55;
		h = 400;
		model = m;
		loadTubeImage();
	}

	void loadTubeImage(){
		if(image == null){
			image = View.loadImage("tube.png");
		}
	}
	
	@Override
	boolean isTube(){
		return true;
	}

	Json marshal(){
		Json ob = Json.newObject();
		ob.add("x", x);
		ob.add("y", y);
		return ob;
	}

	void gravity(){}
	void move(){}
	void draw(Graphics g){
		g.drawImage(image, x - model.mario.x + 200, y, null);
	}

}