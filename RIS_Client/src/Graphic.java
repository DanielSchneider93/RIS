import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.swing.JFrame;

import common.World;

@SuppressWarnings("serial")
public class Graphic extends JFrame implements KeyListener{
	private UpdateGraphic draw;
	public World world;
	
	public Graphic(World world) throws IOException{
		this.world = world;
        this.draw = new UpdateGraphic(world);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        setTitle("Daniel Schneider - RIS");          
        setResizable(false);
        add(draw);
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
	}

    public UpdateGraphic getUpdategraphic() {
		return draw;
	}

	public void keyPressed(KeyEvent e) {
    	/*if(e.getKeyCode()== KeyEvent.VK_RIGHT)
            //manipulate player with correct id in world object at client
        else if(e.getKeyCode()== KeyEvent.VK_LEFT)
            //do sth
        else if(e.getKeyCode()== KeyEvent.VK_DOWN)
            //
        else if(e.getKeyCode()== KeyEvent.VK_UP)
            //
             * */
    }

    public void keyReleased(KeyEvent e) {	
    }
    
    public void keyTyped(KeyEvent e) {
    }
}

