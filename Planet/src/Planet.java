import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;

public class Planet extends MyStandardBoard {
	List<DoublePoint> path = new ArrayList<>();
	static final double G = 9.81E-6;
	long t = System.currentTimeMillis();
	double rE = 6.371;
	double xE = 0;
	double yE = 0;
	double gM = G*rE*rE;
	double xS = 40;
	double yS = 20;
	double vxS = 0;
	double vyS = Math.sqrt(gM / xS);
	
	public static void main(String[] args) {
		new Planet(800, 800, -100, 100, -100);
	}

	public Planet(int wHeight, int wWidth, double minX, double maxX, double minY) {
		super(wHeight, wWidth, minX, maxX, minY, false);
	}

	public void display(GLAutoDrawable drawable) {
		super.display(drawable);
		GL2 gl = drawable.getGL().getGL2();
		long dt = System.currentTimeMillis() - t;
		t = System.currentTimeMillis();
		// Sonne zeichnen
		zeichneKreis(gl, rE, xE, yE);
		
		// Planet bewegen
		double r = Math.sqrt(xS * xS + yS * yS);
		double ax = - gM / Math.pow(r, 3) * xS;
		double ay = - gM / Math.pow(r, 3) * yS;
		System.out.println(ax);
		xS += vxS * dt;
		yS += vyS * dt;
		vxS += ax * dt;
		vyS += ay * dt;
		path.add(new DoublePoint(xS, yS));
		// Planet zeichnen
		zeichneKreis(gl, 1, xS, yS);
		// Pfad zeichnen
		zeichnePfad(gl);
		
	}

	void zeichneKreis(GL2 gl, double r, double x, double y) {
		gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
		gl.glColor3d(1,1,1);
		int nPkte = 40;
		double dt = 2.0 * Math.PI / nPkte;
		gl.glBegin(GL2.GL_POLYGON);
		for (int i = 0; i < nPkte; i++)
			gl.glVertex2d(x + r * Math.cos(i * dt),
					y + r * Math.sin(i * dt)); 
		gl.glEnd();
	}
	
	void zeichnePfad(GL2 gl) {
		gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_LINE);
		gl.glColor3d(0.2, 0.2, 0.2);
		gl.glBegin(GL2.GL_POLYGON);
		for (DoublePoint p : path) {
			gl.glVertex2d(p.x, p.y);
		}
		gl.glEnd();
		
//		gl.glColor3d(0.2, 0.2, 0.2);
//		DoublePoint pO = path.get(0);
//		for (DoublePoint p : path) {
//			gl.glBegin(GL2.GL_LINE);
//			gl.glVertex2d(pO.x, pO.y);
//			gl.glVertex2d(p.x, p.y);
//			gl.glEnd();
//			pO = p;
//		}
	}

}
