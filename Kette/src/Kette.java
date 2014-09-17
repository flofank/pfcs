//  -------------   JOGL SampleProgram  (Fadenkreuz) ------------
import javax.media.opengl.*;

import java.awt.*;
import java.awt.event.*;

import javax.media.opengl.awt.GLCanvas;

import com.jogamp.opengl.util.FPSAnimator;

public class Kette
       implements WindowListener, GLEventListener
{
	private GLCanvas canvas;
    double bottom, top, aspect;
    double near=-100, far=100;
    double left=-4, right=4;


     public Kette()                                             // Konstruktor
     {  Frame f = new Frame("MyFirst");
        f.setSize(800, 600);
        f.addWindowListener(this);
        canvas = new GLCanvas();                         // OpenGL-Window
        canvas.addGLEventListener(this);
        f.add(canvas);
        f.setVisible(true);
     }


     public static void main(String[] args)                     // main-Methode der Applikation
     {  new Kette();
     }


     //  ---------  OpenGL-Events  -----------------------

     public void init(GLAutoDrawable drawable)
     {  GL gl0 = drawable.getGL();                               // OpenGL-Objekt
        GL2 gl = gl0.getGL2();
        gl.glClearColor(0.0f, 0.0f, 1.0f, 1.0f);                // erasing color
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
     }


     public void display(GLAutoDrawable drawable)
     {  
    	GL gl0 = drawable.getGL();
        GL2 gl = gl0.getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT);                     // Bild loeschen
        gl.glColor3d(0.5,0.5,0.5);                                    // Zeichenfarbe
        zeichneKette(gl);
        gl.glColor3d(1, 1, 1);                                  // Zeichenfarbe
     }
     
     public void zeichneKette(GL2 gl) {
    	 double x = left;
    	 gl.glBegin(GL2.GL_LINE_LOOP);
    	 while (x < right) {
    		 double y = (Math.exp(x / aspect) + Math.exp(-x / aspect)) / 2;
    		 gl.glVertex2d(x, y);
    		 x += 0.01;
    	 }
    	 gl.glEnd();
     }

     public void reshape(GLAutoDrawable drawable,               // Window resized
                         int x, int y,
                         int width, int height)
     {  GL gl0 = drawable.getGL();
        GL2 gl = gl0.getGL2();
        gl.glViewport(0, 0, width, height);                     // Window
        aspect = (double)height/width;
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