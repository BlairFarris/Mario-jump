// William Blair Farris
// 10/23/20
// A mario assignment to get us used to allowing control of mario and collission detection.

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

class Controller implements ActionListener, MouseListener, KeyListener
{
	View view;
	Model model;
	boolean keyLeft;
	boolean keyRight;
	boolean keyUp;
	boolean keyDown;
	boolean keySpace;
	boolean keyControl;
	boolean noHold;



	Controller(Model m)
	{
		model = m;
		noHold = true;
	}

	void setView(View v){
		view = v;
	}



	public void actionPerformed(ActionEvent e)
	{
	}

	
	

	public void mousePressed(MouseEvent e)
	{
	}
	
	public void mouseReleased(MouseEvent e) {
	}
	public void mouseEntered(MouseEvent e) {    }
	public void mouseExited(MouseEvent e) {    }
	public void mouseClicked(MouseEvent e) {    }

	public void keyPressed(KeyEvent e)
	{
		switch(e.getKeyCode())
		{
			case KeyEvent.VK_RIGHT: keyRight = true; break;
			case KeyEvent.VK_LEFT: keyLeft = true; break;
			case KeyEvent.VK_UP: keyUp = true; break;
			case KeyEvent.VK_DOWN: keyDown = true; break;
			case KeyEvent.VK_SPACE: keySpace = true; break;
			case KeyEvent.VK_CONTROL: keyControl = true; break;
		}
	}

	public void keyReleased(KeyEvent e)
	{
		switch(e.getKeyCode())
		{
			case KeyEvent.VK_RIGHT: keyRight = false; break;
			case KeyEvent.VK_LEFT: keyLeft = false; break;
			case KeyEvent.VK_UP: keyUp = false; break;
			case KeyEvent.VK_DOWN: keyDown = false; break;
			case KeyEvent.VK_SPACE: keySpace = false; break;
			case KeyEvent.VK_CONTROL: keyControl = false; break;
		}
		char c = e.getKeyChar();
	}

	public void keyTyped(KeyEvent e)
	{
	}

	void update()
	{
		model.mario.prevPos();
		if(keyRight){
			model.mario.x += 10;
			model.mario.update();
		}
		if(keyLeft){
			model.mario.x -= 10;
			model.mario.update();
		}
		if(keyUp || keySpace){
			model.mario.jump();
		}
		if(keyUp == false && keySpace == false && model.mario.vert_velocity != 0.0){
			model.mario.marioJumped = true;
		}
		if(keyControl){
			if(noHold)
				model.sprites.add(new Fireball(model.mario.x, model.mario.y + (model.mario.h / 2), model));
			noHold = false;
		}
		if(!keyControl){
			noHold = true;
		}
	}
}
