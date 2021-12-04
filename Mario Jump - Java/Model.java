// William Blair Farris
// 10/23/20
// A mario assignment to get us used to allowing control of mario and collission detection.

import java.util.ArrayList;
import java.util.Iterator;

class Model
{
	ArrayList<Sprite> sprites;
	Mario mario;

	Model()
	{
		sprites = new ArrayList<Sprite>();
		mario = new Mario(200, 50);
		sprites.add(mario);
	}

	public void update()
	{
		for(int i = 0; i < sprites.size(); i++)
			sprites.get(i).gravity();

		for(int i = 0; i < sprites.size(); i++){
			Sprite s = sprites.get(i);
			if(sprites.get(i).isTube()){
				if(mario.collision(s)){
					mario.tubeReject((Tube)sprites.get(i));
				}
			}
		}

		for(int i = 0; i < sprites.size(); i++)
			sprites.get(i).move();
	}

	void unmarshal(Json ob){
		sprites = new ArrayList<Sprite>();
		sprites.add(mario);
		Json tmpList = ob.get("tubes");
		for(int i = 0; i < tmpList.size(); i++)
			sprites.add(new Tube(tmpList.get(i), this));
		Json tmpList2 = ob.get("goombas");
		for(int i = 0; i < tmpList2.size(); i++)
			sprites.add(new Goomba(tmpList2.get(i), this));
	}
	
}

