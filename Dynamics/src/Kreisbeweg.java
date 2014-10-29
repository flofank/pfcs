import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;


public class Kreisbeweg extends MyStandardBoard {

	static class Kreisbahn extends Dynamics {
		double[] x; // Status

		public Kreisbahn(double x1start, double x2start) {
			x = new double[2];
			x[0] = x1start;
			x[1] = x2start;
		}
		
		@Override
		double[] f(double[] x) {
			double[] y = {-x[1], x[0]}; 
			return y;
		}
		
		public void move(double dt) {
			x = runge(x, dt);
		}
		
	}
	
	double r = 0.5;
	double dt = 0.01;
	Kreisbahn p = new Kreisbahn(4, 0);
	
	public static void main(String[] args) {
		Kreisbeweg kb = new Kreisbeweg();
	}
	
	public Kreisbeweg() {
		super(400, 400, -10, 10, -10, true);
	}
	
	public void display(GLAutoDrawable drawable) {
		super.display(drawable);
		GL2 gl = drawable.getGL().getGL2();
		gl.glColor3d(1, 1, 0);
		zeichneKreis(gl, r, p.x[0], p.x[1]);
		p.move(dt);
	}
	
	

}
