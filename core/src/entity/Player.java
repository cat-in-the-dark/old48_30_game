package entity;

public class Player {
	private DirectionX dirX;
	private DirectionY dirY;

	// player physical model here

	public DirectionX getDirX() {
		return dirX;
	}

	public DirectionY getDirY() {
		return dirY;
	}

	public void moveLeft() {
		dirX = DirectionX.LEFT;
	}

	public void moveRight() {
		dirX = DirectionX.RIGHT;

	}

	public void jump() {
		System.out.println("jump");
	}

	public void crosshairUp() {
		dirY = DirectionY.CROSSHAIR_UP;
	}

	public void crosshairDown() {
		dirY = DirectionY.CROSSHAIR_DOWN;

	}

	public void crosshairMiddle() {
		dirY = DirectionY.CROSSHAIR_MIDDLE;
	}

}
