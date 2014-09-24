import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;

public class Wurf extends MyStandardBoard {
	double t = 0;
	double xB = -80;
	double yB = 0;
	double rB = 0.5;
	double vy = 40;
	double vx = 30;
	double ay = 0;
	double ax = 0;
	double dt = 0.001;
	double g = -9.81;

	public static void main(String[] args) {
		new Wurf(800, 1600, -100, 100, -5);
	}

	public Wurf(int wHeight, int wWidth, double minX, double maxX, double minY) {
		super(wHeight, wWidth, minX, maxX, minY);
	}

	public void display(GLAutoDrawable drawable) {
		super.display(drawable);
		GL2 gl = drawable.getGL().getGL2();
		//x
		xB += vx * dt;
		ax = - vx * vx * 0.02;
		vx = vx + ax * dt;
		//y
		yB += vy*dt;
		if (vy > 0) {
			ay = g - vy * vy * 0.02;
		} else {
			ay = g + vy * vy * 0.02;
		}
		vy += ay*dt; 
//		if (yB < rB) { // Ball ist am Boden
//			yB = rB;
//		}
		System.out.println(vx + " | " + vy);
		zeichneKreis(gl, rB, xB, yB);
	}

	void zeichneKreis(GL2 gl, double r, double x, double y) {
		gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
		int nPkte = 40;
		double dt = 2.0 * Math.PI / nPkte;
		gl.glBegin(GL2.GL_POLYGON);
		for (int i = 0; i < nPkte; i++)
			gl.glVertex2d(x + r * Math.cos(i * dt),
					y + r * Math.sin(i * dt)); 
		gl.glEnd();
	}

}
