import java.awt.*;

public class Astronaut {
	private Rectangle body;
	int xVelocity, yVelocity, direction;
	boolean g;
	public Astronaut() {
		body = new Rectangle(390, 360, 20, 40);
		g=true;
	}
	public void changeSpawn() {
		body.x = 40;
	}

	public void draw(Graphics2D g) {
			g.fill(body);
		
	}

	public int getDirection() {
		return direction;
	}

	public Rectangle getBody() {
		return body;
	}

	public void setDirection(int d) {
		 direction=d;
	}

	
	public void jump() {
		if (body.y >= 0) {
			yVelocity = 15;
			body.y -= 15;		
		}
	}
	public void dead() {
		g= false;
	}
	public void move() {
		if ((direction == 1) && (xVelocity < 8)) {
			xVelocity++;
		}
		if ((direction == -1) && (xVelocity > -8)) {
			xVelocity--;
		}
		if (direction == 0) {
			if (xVelocity < 0) {
				xVelocity++;
			} else if(xVelocity >0) {
				xVelocity--;
			}
		} 
		if (body.x<0) {
			body.x = 0;
		}
		if (body.x>780) {
			body.x = 780;
		}
		if (body.y>360) {
			body.y = 360;
		}
		body.x += xVelocity;
		if (body.y < 360 && g== true) {
			body.y -= yVelocity;
			yVelocity--;
		}
	
	}
	

	
	public void collision(Astronaut a) {
		if(body.intersects(a.getBody())) {
			xVelocity*=-1;
			body.x+=xVelocity;
			a.xVelocity*=-1;
			a.getBody().x += a.xVelocity;
		}
		if(body.intersects(a.getBody()) && (body.y > a.body.y) && (body.y > 40)) {
			yVelocity*=-1;
			body.y=a.body.y +40;
	}	
		if(body.intersects(a.getBody()) && (body.y < a.body.y)) {
			yVelocity*=-1;
			body.y+=yVelocity;
			
		}
		}
	}
	

