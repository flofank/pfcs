//  -------------   JOGL SampleProgram  (Fadenkreuz) ------------
import javax.media.opengl.*;

import java.awt.*;
import java.awt.event.*;

import javax.media.opengl.awt.GLCanvas;

import com.jogamp.opengl.util.FPSAnimator;

public class MyFirst
       implements WindowListener, GLEventListener
{
	private GLCanvas canvas;
	private long startTime;
    double bottom, top;
    double near=-200, far=100;
    double left=-4, right=4;

     //  ------------------  Methoden  --------------------

     void zeichneAchsen(GL2 gl)                                  // Koordinatenachsen zeichnen
     {  gl.glBegin(GL2.GL_LINES);
        gl.glVertex2d(left,0);        // x-Achse
        gl.glVertex2d(right,0);
        gl.glVertex2d(0,bottom);        // y-Achse
        gl.glVertex2d(0,top);
        gl.glEnd();
     }


     void zeichneKreis(GL2 gl, double r, double x, double y)                         // Kreis um den Nullpunkt
     {  int nPkte = 40;                                          // Anzahl Punkte
        double dt = 2.0*Math.PI / nPkte;                         // Parameter-Schrittweite
        gl.glBegin(GL2.GL_POLYGON);
        for (int i=0; i < nPkte; i++)
          gl.glVertex2d(x+r*Math.cos(i*dt),                         // x = r*cos(i*dt)
                        y+r*Math.sin(i*dt));                        // y = r*sin(i*dt-phi)
        gl.glEnd();
     }


     public MyFirst()                                             // Konstruktor
     {  Frame f = new Frame("MyFirst");
        f.setSize(800, 600);
        f.addWindowListener(this);
        canvas = new GLCanvas();                         // OpenGL-Window
        canvas.addGLEventListener(this);
        f.add(canvas);
        f.setVisible(true);
     }


     public static void main(String[] args)                     // main-Methode der Applikation
     {  new MyFirst();
     }


     //  ---------  OpenGL-Events  -----------------------

     public void init(GLAutoDrawable drawable)
     {  GL gl0 = drawable.getGL();                               // OpenGL-Objekt
        GL2 gl = gl0.getGL2();
        gl.glClearColor(0.0f, 0.0f, 1.0f, 1.0f);                // erasing color
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
        startTime = System.currentTimeMillis();
        FPSAnimator anim = new FPSAnimator(canvas, 1000, true);
        anim.start();
     }


     public void display(GLAutoDrawable drawable)
     {  
    	GL gl0 = drawable.getGL();
        GL2 gl = gl0.getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT);                     // Bild loeschen
        gl.glColor3d(0.5,0.5,0.5);                                    // Zeichenfarbe
        zeichneAchsen(gl);
        gl.glColor3d(1, 1, 1);                                  // Zeichenfarbe
//        zeichneKreis(gl,0.4,0,0);
        gl.glColor3d(1, 1, 0);
        double roundtime = 5000;
        double t = ((System.currentTimeMillis() - startTime) % roundtime) / roundtime * 2 * Math.PI;
        double r = 2;
        double x = r * Math.cos(t);
        double y = r * Math.sin(t);
        zeichneKreis(gl,0.1,x,y);
//        try {
//            x = canvas.getMousePosition().getX() / canvas.getWidth() * (right-left) + left;
//            y = canvas.getMousePosition().getY() / canvas.getHeight() * (top-bottom) * -1 - bottom;
//        } catch (Exception e) {
//        	x = 0;
//            y = 0;
//        }
        r = 0.5;
        x = x + r * Math.cos(t * 4);
        y = y + r * Math.sin(t * 4);
        zeichneKreis(gl,0.05,x,y);
     }


     public void reshape(GLAutoDrawable drawable,               // Window resized
                         int x, int y,
                         int width, int height)
     {  GL gl0 = drawable.getGL();
        GL2 gl = gl0.getGL2();
        gl.glViewport(0, 0, width, height);                     // Window
        double aspect = (double)height/width;
//        double top = -4;
//        double bottom = 4;
        bottom=aspect*left;
        top=aspect*right;
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrtho(left,right,bottom,top,near,far);             // ViewingVolume
     }


     public void dispose(GLAutoDrawable drawable)
     { }


     //  ---------  Window-Events  --------------------

     public void windowClosing(WindowEvent e)
     {  System.exit(0);
     }
     public void windowActivated(WindowEvent e) {  }
     public void windowClosed(WindowEvent e) {  }
     public void windowDeactivated(WindowEvent e) {  }
     public void windowDeiconified(WindowEvent e) {  }
     public void windowIconified(WindowEvent e) {  }
     public void windowOpened(WindowEvent e) {  }

  }
