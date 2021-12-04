// William Blair Farris
// 9/10/2020
// A mario assignment to get us used to allowing control of mario and collission detection.

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;
import javax.swing.JButton;
import java.awt.Color;

class View extends JPanel
{
	JButton b1;

	static BufferedImage tube_image;
	Model model;
	//int scrollPos;

	View(Controller c, Model d)
	{
		model = d;
		c.setView(this);
		
		//scrollPos = 0;
		
	}

	static BufferedImage loadImage(String filename){
		BufferedImage im = null;
		try
		{
			im = ImageIO.read(new File(filename));
		} catch(Exception e) {
			e.printStackTrace(System.err);
			System.exit(1);
		}
		return im;
	}

	public void paintComponent(Graphics g){
		g.setColor(new Color(128, 255, 255));
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		g.setColor(Color.gray);
		g.drawLine(0, 395, 2000, 395);

		

		for(int i = 0; i < model.sprites.size(); i++)
		{
			model.sprites.get(i).draw(g);
		}
	}
}
