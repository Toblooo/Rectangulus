import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

/**
 * version 1.21
 * 
 * Project created by: Mr Ruth .. Niles North High School .. Skokie, IL ..
 * www.MrRuth.com
**/
public class EscapeFromRectangulus extends JPanel implements KeyListener,
    ActionListener {

  // DECLARE ALL INSTANCE VARIABLES HERE..

  private ArrayList<Rectangle> aliens = new ArrayList<Rectangle>();
  private Astronaut jan = new Astronaut();
  private Astronaut bob = new Astronaut();
  public static final Rectangle BORDER = new Rectangle(0, 0, 800, 400);
  private int frameCount;
  private int framCount2;
  private boolean BobT;
  private boolean JanT;
  
  // used for the score
  private String title = "Russell has cute hands.";
  private Timer timer;// handles animation
  private static Image offScreenBuffer;// needed for double buffering graphics
  private Graphics offScreenGraphics;// needed for double buffering graphics


  /**
   * main() is needed to initialize the window.<br>
   * THIS METHOD SHOULD NOT BE MODIFIED! .. <br>
   * you should write all necessary initialization code in initRound()
   */
  public static void main(String[] args) {
    JFrame window = new JFrame("Escape From Rectangulus");
    window.setBounds(0, 0, 1600, 860);// updated for Windows 10
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    window.setResizable(false);

    EscapeFromRectangulus game = new EscapeFromRectangulus();
    game.setBackground(Color.BLACK);
    window.getContentPane().add(game);
    window.setVisible(true);
    game.init();
    window.addKeyListener(game);
  }
 

  /**
   * init method needed to initialize non-static fields<br>
   * THIS METHOD SHOULD NOT BE MODIFIED! ..
   */
  public void init() {
    offScreenBuffer = createImage(getWidth(), getHeight());// should be 800x400
    offScreenGraphics = offScreenBuffer.getGraphics();
    timer = new Timer(30, this);
    // timer fires every 30 milliseconds.. invokes method actionPerformed()
    ((Graphics2D)offScreenGraphics).scale(2.0, 2.0);
    initRound();
    
  }

  /**
   * initializes all fields needed for each round of play (i.e. restart)
   */
  public void initRound() {
    frameCount = 0;
    framCount2 = 0;
    BobT = false;
    JanT = false;
    // YOUR CODE GOES HERE..
    // initialize instance variables here
    aliens = new ArrayList<Rectangle>();
    jan = new Astronaut();
    bob = new Astronaut();
    bob.changeSpawn(); 
  }

  /**
   * Called automatically after a repaint request<br>
   * THIS METHOD SHOULD NOT BE MODIFIED! ..
   */
  public void paint(Graphics g) {
    draw((Graphics2D) offScreenGraphics);
    
    g.drawImage(offScreenBuffer, 0, 0, this);
  }

  /**
   * renders all objects to Graphics g
   */
  public void draw(Graphics2D g) {
    super.paint(g);// refresh the background
    g.setColor(Color.WHITE);
    g.drawString(title, 100, 20);// draw the title towards the top
    g.drawRect(BORDER.x, BORDER.y, BORDER.width - 1, BORDER.height - 1);
    g.drawString("orange:  " + frameCount, 200, 100);// approximate middle
    g.drawString("cyan:  " + framCount2, 600, 100);
    
    if(JanT== false) {
    g.setColor(new Color(0.0f,1.0f,1.0f,1.0f));
    jan.draw(g);
    }
    if(JanT==true) {
    	g.setColor(new Color(0.0f,1.0f,1.0f,0.2f));
        jan.draw(g);	
    }
    if(BobT==false) {
    g.setColor(new Color(0.85f,0.3250f,0.098f,1.0f));
    bob.draw(g);
    }
    if(BobT==true) {
        g.setColor(new Color(0.85f,0.3250f,0.098f,0.2f));
        bob.draw(g);
        }
    g.setColor(Color.WHITE);
    // YOUR CODE GOES HERE..
    // render all game objects here
    
    for(int i =0; i<aliens.size(); i++) {
    	g.draw(aliens.get(i));
    }
  }

  /**
   * Called automatically when the timer fires<br>
   * this is where all the game fields will be updated
   */
  public void actionPerformed(ActionEvent e) {	  
  jan.move();
  jan.collision(bob);

  bob.move();
  bob.collision(jan);
    // Simple AI: Each frame there is a 5% chance of creating an alien Rectangle
    // with a random position and width. (note: its width is also its velocity)
  if (Math.random() < 0.02) {
       aliens.add(new Rectangle(BORDER.width, 360 - (int) (Math.random() *
      340), (int) (Math.random() * 15 + 1), 20));
    }
    for(Rectangle alien : aliens) {
    if(jan.getBody().intersects(alien)) {
    	JanT=true;
    	jan.dead();
 	
    }
    if(bob.getBody().intersects(alien)) {
    	BobT=true;
    	bob.dead();
 	
    }
    }
    if(BobT&&JanT) {
    	timer.stop();
    }

    // YOUR CODE GOES HERE..
    // update all alien Rectangles and stop the timer if any intersect with jan
    	for(int i =0; i < aliens.size();i++) {
    		aliens.get(i).x -= aliens.get(i).width;
    		if(aliens.get(i).x < 0) {
    			aliens.remove(i);
    			
    		}
    	}
    
    
   if(BobT == false) {
    frameCount++;
   }
   if(JanT == false) {
    framCount2++;
   }// used for the score
    repaint();// needed to refresh the animation
  }
 

  /**
   * handles any key pressed events and updates the player's direction by
   * setting their direction to either 1 or -1 for right or left respectively
   * and updates their jumping status by invoking jump()
   */
  public void keyPressed(KeyEvent e) {
    int keyCode = e.getKeyCode();
    if(JanT==false) {
    if (keyCode == KeyEvent.VK_LEFT) {
       jan.setDirection(-1);
    } else if (keyCode == KeyEvent.VK_RIGHT) {
      jan.setDirection(1);
    } else if (keyCode == KeyEvent.VK_UP) {
      jan.jump();
    }
    }
    if(BobT==false) {
    if (keyCode == KeyEvent.VK_A) {
	       bob.setDirection(-1);
	    } else if (keyCode == KeyEvent.VK_D) {
	      bob.setDirection(1);
	    } else if (keyCode == KeyEvent.VK_W) {
	      bob.jump();
	    }
    }
  }
 

  /**
   * handles any key released events and updates the player's direction by
   * setting their direction to 0 if they need to stop and restarts the game if
   * the Timer is not running and user types 'r'
   */
  public void keyReleased(KeyEvent e) {
    int keyCode = e.getKeyCode();
    if (keyCode == KeyEvent.VK_LEFT  && jan.getDirection() == -1 ) {
       jan.setDirection(0);
    } else if (keyCode == KeyEvent.VK_RIGHT && jan.getDirection() == 1 ) {
       jan.setDirection(0);
    }  if (keyCode == KeyEvent.VK_A  && bob.getDirection() == -1 ) {
	       bob.setDirection(0);
	    } else if (keyCode == KeyEvent.VK_D && bob.getDirection() == 1 ) {
	       bob.setDirection(0);
	    } else if (keyCode == KeyEvent.VK_R) {
      if (!timer.isRunning()) {
        timer.start();
        initRound();
      }
    }
  }


  /**
   * this method is needed for implementing interface KeyListener<br>
   * THIS METHOD SHOULD NOT BE MODIFIED! ..
   */
  public void keyTyped(KeyEvent e) {
  }

}// end class EscapeFromRectangulus
