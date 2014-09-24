//  -------------   JOGL SampleProgram  (Fadenkreuz) ------------
import javax.media.opengl.*;

import java.awt.*;
import java.awt.event.*;

import javax.media.opengl.awt.GLCanvas;

import com.jogamp.opengl.util.FPSAnimator;

public class Kette extends MyStandardBoard {
	double scal = 0.5;
	double x = -1.5;
	double y;
	double ax;
	double ay;
	double vx;
	double vy;
	double dt = 0.001;

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
	}

	public void drawKette(GL2 gl) {
		double x = left - 10;
		gl.glColor3d(0, 1, 0);
		gl.glBegin(GL2.GL_LINE_LOOP);
		while (x < right + 10) {
			double y = (Math.exp(x*scal) + Math.exp(-x*scal)) / 2;
			gl.glVertex2d(x, y);
			x += 0.01;
		}
		gl.glEnd();
	}

}
