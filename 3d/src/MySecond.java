//  -------------    JOGL SampleProgram  (Pyramide) ------------
//
//     Darstellung einer Pyramide mit Kamera- und Objekt-System
//
import javax.media.opengl.*;

import java.awt.*;
import java.awt.event.*;

import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.glu.GLU;

import com.jogamp.opengl.util.*;
import com.jogamp.opengl.util.gl2.*;

public class MySecond implements WindowListener, GLEventListener {
	GLCanvas canvas; // OpenGl-Canvas
	GLU glu = new GLU();
	double left = -10, right = 10; // Koordinatenbereich
	double bottom, top; // werden in reshape gesetzt
	double near = 0, far = 100; // Cliping Bereich
	double elev = 14; // Elevation Kamera-System
	double azim = 40; // Azimut Kamera-System
	double dist = 4; // Abstand Kamera von O (ohne Bedeutung
						// bei Orthogonalprjektion)
	double phi = 0;

	// ------------------ Methoden --------------------

	void zeichneAchsen(GL2 gl, double a) // Koordinatenachsen zeichnen
	{
		gl.glColor3d(1, 1, 1);
		gl.glBegin(gl.GL_LINES);
		gl.glVertex3d(0, 0, 0);
		gl.glVertex3d(a, 0, 0);
		gl.glVertex3d(0, 0, 0);
		gl.glVertex3d(0, a, 0);
		gl.glVertex3d(0, 0, 0);
		gl.glVertex3d(0, 0, a);
		gl.glEnd();
	}

	void zeichnePyramide(GL2 gl, double a, double h) // Pyramide zeichnen
	{
		gl.glBegin(gl.GL_POLYGON); // Boden
		gl.glVertex3d(a, 0, a);
		gl.glVertex3d(a, 0, -a);
		gl.glVertex3d(-a, 0, -a);
		gl.glVertex3d(-a, 0, a);
		gl.glEnd();
		gl.glColor3d(1, 0, 0);
		gl.glBegin(gl.GL_POLYGON); // Seitenflaechen
		gl.glVertex3d(a, 0, a);
		gl.glVertex3d(a, 0, -a);
		gl.glVertex3d(0, h, 0);
		gl.glEnd();
		gl.glColor3d(1, 1, 0);
		gl.glBegin(gl.GL_POLYGON);
		gl.glVertex3d(a, 0, -a);
		gl.glVertex3d(-a, 0, -a);
		gl.glVertex3d(0, h, 0);
		gl.glEnd();
		gl.glColor3d(0, 1, 0);
		gl.glBegin(gl.GL_POLYGON);
		gl.glVertex3d(-a, 0, -a);
		gl.glVertex3d(-a, 0, a);
		gl.glVertex3d(0, h, 0);
		gl.glEnd();
		gl.glColor3d(0, 0, 1);
		gl.glBegin(gl.GL_POLYGON);
		gl.glVertex3d(-a, 0, a);
		gl.glVertex3d(a, 0, a);
		gl.glVertex3d(0, h, 0);
		gl.glEnd();
	}

	public MySecond() // Konstruktor
	{
		Frame f = new Frame("MySecond");
		canvas = new GLCanvas(); // OpenGL-Window
		f.setSize(800, 600);
		f.setBackground(Color.gray);
		f.addWindowListener(this);
		canvas.addGLEventListener(this);
		f.add(canvas);
		f.setVisible(true);
	}

	public static void main(String[] args) // main-Methode der Applikation
	{
		new MySecond();
	}

	// --------- OpenGL-Events -----------------------

	public void init(GLAutoDrawable drawable) {
		GL gl0 = drawable.getGL(); // OpenGL-Objekt
		GL2 gl = gl0.getGL2();
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f); // erasing color
		gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
		FPSAnimator anim = new FPSAnimator(canvas, 200, true);
        anim.start();
        gl.glEnable(GL2.GL_DEPTH_TEST);
	}

	void setCamSys(double r, double azim, double elev) {
		double phi = Math.toRadians(azim);
		double theta = Math.toRadians(elev);
		double r0 = r * Math.cos(theta);
		double x = r0 * Math.sin(phi); 
		double y = r * Math.sin(theta);
		double z = r0 * Math.cos(phi);
		glu.gluLookAt(x, y, z, 0, 0, 0, 0, 1, 0);
	}
	
	public void display(GLAutoDrawable drawable) {
		GL gl0 = drawable.getGL();
		GL2 gl = gl0.getGL2();
		gl.glClear(gl.GL_COLOR_BUFFER_BIT | gl.GL_DEPTH_BUFFER_BIT);
		gl.glColor3d(0, 1, 1); // Zeichenfarbe
		gl.glPolygonMode(gl.GL_FRONT_AND_BACK, gl.GL_FILL); // Polygon
															// Zeichen-Modus
		gl.glColor3d(1, 0, 0); // Zeichenfarbe

		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();
		setCamSys(dist,azim,elev);
		
		zeichneAchsen(gl, 6);
		gl.glRotated(phi, 0, 1, 0);
		gl.glTranslated(2, 0, 0);
//		gl.glRotated(phi, 0, -1, 0);
		zeichnePyramide(gl, 1.5, 1.5);
		phi += 1;
	}

	public void reshape(GLAutoDrawable drawable, // Window resized
			int x, int y, int width, int height) {
		GL gl0 = drawable.getGL();
		GL2 gl = gl0.getGL2();
		gl.glViewport(0, 0, width, height);
		double aspect = (float) height / width; // aspect-ratio
		bottom = aspect * left;
		top = aspect * right;
		gl.glMatrixMode(gl.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glOrtho(left, right, bottom, top, near, far); // Viewing-Volume (im
															// Raum)
	}

	public void dispose(GLAutoDrawable drawable) {
	}

	// --------- Window-Events --------------------

	public void windowClosing(WindowEvent e) {
		System.exit(0);
	}

	public void windowActivated(WindowEvent e) {
	}

	public void windowClosed(WindowEvent e) {
	}

	public void windowDeactivated(WindowEvent e) {
	}

	public void windowDeiconified(WindowEvent e) {
	}

	public void windowIconified(WindowEvent e) {
	}

	public void windowOpened(WindowEvent e) {
	}

}