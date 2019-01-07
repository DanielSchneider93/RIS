import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.swing.JFrame;

import common.GameObject;
import common.MapCache;
import common.World;

public class Graphic extends JFrame implements KeyListener{
	private UpdateGraphic draw;
	public World world;
	int playerID;
	GameObject player;
	int playerSpeed = 50;
	MapCache mc;
	
	public Graphic(World world, MapCache mc) throws IOException{
		this.world = world;
		this.mc = mc;
        this.draw = new UpdateGraphic(world);
        this.playerID = world.getPlayerID();
        this.player = world.findPlayer(playerID);
        
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        setTitle("Daniel Schneider - RIS");          
        setResizable(false);
        add(draw);
        setSize(1000, 1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
	}

    public UpdateGraphic getUpdategraphic() {
		return draw;
	}


	public void keyPressed(KeyEvent e) {
    	if(e.getKeyCode()== KeyEvent.VK_LEFT) {
    		
    		System.out.println("old player pos: " + player.getPosx() + " " + player.getPosy());
    		//this.player = world.findPlayer(playerID);
            player.setPosx(player.getPosx() - playerSpeed);
            draw.windowOffsetX += playerSpeed;
            player.setDirection(0);
    		world.triggerPosChange(player);
    		System.out.println("new player pos: " + player.getPosx() + " " + player.getPosy());
    	}
        else if(e.getKeyCode()== KeyEvent.VK_RIGHT)
        {
        	System.out.println("old player pos: " + player.getPosx() + " " + player.getPosy());
        	//this.player = world.findPlayer(playerID);
        	player.setPosx(player.getPosx() + playerSpeed);
        	draw.windowOffsetX -= playerSpeed;
        	player.setDirection(1);
    		world.triggerPosChange(player);
    		System.out.println("new player pos: " + player.getPosx() + " " + player.getPosy());
        }
        else if(e.getKeyCode()== KeyEvent.VK_DOWN)
        {
        	//this.player = world.findPlayer(playerID);
        	draw.windowOffsetY -= playerSpeed;
        	player.setPosy(player.getPosy() + playerSpeed);
    		world.triggerPosChange(player);
        }
        else if(e.getKeyCode()== KeyEvent.VK_UP)
        {
        	//this.player = world.findPlayer(playerID);
        	player.setPosy(player.getPosy() - playerSpeed);
        	draw.windowOffsetY += playerSpeed;
    		world.triggerPosChange(player);
        }
        else if(e.getKeyCode()== KeyEvent.VK_S)
        {
        	mc.updateManual();
        }
    }

    public void keyReleased(KeyEvent e) {	
    }
    
    public void keyTyped(KeyEvent e) {
    }
}

