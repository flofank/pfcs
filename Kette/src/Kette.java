//  -------------   JOGL SampleProgram  (Fadenkreuz) ------------
import javax.media.opengl.*;

public class Kette extends MyStandardBoard {
	double scal = 1;
	double x = -1;
	double g = 9.81;
	double v = 0;
	long t = System.currentTimeMillis();
	
	public static void main(String[] args) {
		Kette k = new Kette(600, 800);
	}

	public Kette(int wHeight, int wWidth) {
		super(wHeight, wWidth, -2, 2, -0.1);
	}

	@Override
	public void display(GLAutoDrawable drawable) {
		super.display(drawable);
		GL gl0 = drawable.getGL();
		GL2 gl = gl0.getGL2();
		drawKette(gl);
		kugel(gl);
	}

	public void drawKette(GL2 gl) {
		double x = left - 10;
		gl.glColor3d(0, 1, 0);
		gl.glBegin(GL2.GL_LINE_LOOP);
		while (x < right + 10) {
			double y = (Math.exp(x * scal) + Math.exp(-x * scal)) / 2;
			gl.glVertex2d(x, y);
			x += 0.01;
		}
		gl.glEnd();
	}
	
	public void kugel(GL2 gl) {
		double dt = System.currentTimeMillis() - t;
		t += dt;
		dt = dt * 0.001; // convert to seconds
		double alpha = Math.atan((Math.exp(x / scal) - Math.exp(- x / scal)) / 2);
		x = x + v * Math.cos(alpha) * dt;
		v = v - g * Math.sin(alpha) * dt;
		double y = (Math.exp(x / scal) + Math.exp(-x / scal)) / 2;
		zeichneKreis(gl, 0.05, x, y);
	}

	void zeichneKreis(GL2 gl, double r, double x, double y) {
		int nPkte = 40;
		double dt = 2.0 * Math.PI / nPkte;
		gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
		gl.glBegin(GL2.GL_POLYGON);
		for (int i = 0; i < nPkte; i++)
			gl.glVertex2d(x + r * Math.cos(i * dt),
					y + r * Math.sin(i * dt));
		gl.glEnd();
	}
}
