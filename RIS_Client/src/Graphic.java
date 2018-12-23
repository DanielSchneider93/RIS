import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.swing.JFrame;

import common.Player;
import common.World;

public class Graphic extends JFrame implements KeyListener{
	private UpdateGraphic draw;
	public World world;
	int playerID;
	Player player;
	
	public Graphic(World world) throws IOException{
		this.world = world;
        this.draw = new UpdateGraphic(world);
        this.playerID = world.getPlayerID();
        this.player = world.findPlayer(playerID);
        
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
    	if(e.getKeyCode()== KeyEvent.VK_RIGHT) {
    		
    		this.player = world.findPlayer(playerID);
            player.setPosx(player.getPosx() + 10);
            player.setDirection(1);
    		world.triggerPosChange(player);
    	}
        else if(e.getKeyCode()== KeyEvent.VK_LEFT)
        {
        	this.player = world.findPlayer(playerID);
        	player.setPosx(player.getPosx() - 10);
        	 player.setDirection(0);
    		world.triggerPosChange(player);
        }
        else if(e.getKeyCode()== KeyEvent.VK_DOWN)
        {
        	this.player = world.findPlayer(playerID);
        	player.setPosy(player.getPosy() + 10);
    		world.triggerPosChange(player);
        }
        else if(e.getKeyCode()== KeyEvent.VK_UP)
        {
        	this.player = world.findPlayer(playerID);
        	player.setPosy(player.getPosy() - 10);
    		world.triggerPosChange(player);
        }
    }

    public void keyReleased(KeyEvent e) {	
    }
    
    public void keyTyped(KeyEvent e) {
    }
}

