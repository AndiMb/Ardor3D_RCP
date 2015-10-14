/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.testwindows;

import com.ardor3d.framework.DisplaySettings;
import com.ardor3d.framework.FrameHandler;
import com.ardor3d.framework.jogl.JoglCanvasRenderer;
import com.ardor3d.framework.jogl.awt.JoglSwingCanvas;
import com.ardor3d.input.ControllerWrapper;
import com.ardor3d.input.PhysicalLayer;
import com.ardor3d.input.awt.AwtFocusWrapper;
import com.ardor3d.input.awt.AwtKeyboardWrapper;
import com.ardor3d.input.awt.AwtMouseManager;
import com.ardor3d.input.awt.AwtMouseWrapper;
import com.ardor3d.input.control.FirstPersonControl;
import com.ardor3d.input.logical.DummyControllerWrapper;
import com.ardor3d.input.logical.LogicalLayer;
import com.ardor3d.math.Vector3;
import com.ardor3d.renderer.Camera;
import com.ardor3d.util.Timer;
import java.awt.BorderLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JPanel;

/**
 *
 * @author Andreas
 */
public class ArdorPanel extends JPanel {

    private JoglSwingCanvas canvas;
    final Timer timer = new Timer();
    final FrameHandler frameWork = new FrameHandler(timer);
    final LogicalLayer logicalLayer = new LogicalLayer();
    private volatile boolean exit = false;

    public ArdorPanel(MyScene scene) {
        System.setProperty("ardor3d.useMultipleContexts", "true");
        setLayout(new BorderLayout());

        final JoglCanvasRenderer canvasRenderer = new JoglCanvasRenderer(scene);

        final DisplaySettings settings = new DisplaySettings(400, 300, 24, 0, 0, 16, 0, 0, false, false);
        canvas = new JoglSwingCanvas(settings, canvasRenderer);

        frameWork.addUpdater(scene);

        add(canvas);

        final AwtKeyboardWrapper keyboardWrapper = new AwtKeyboardWrapper(canvas);
        final AwtFocusWrapper focusWrapper = new AwtFocusWrapper(canvas);
        final AwtMouseManager mouseManager = new AwtMouseManager(canvas);
        final AwtMouseWrapper mouseWrapper = new AwtMouseWrapper(canvas, mouseManager);
        final ControllerWrapper controllerWrapper = new DummyControllerWrapper();

        final PhysicalLayer pl = new PhysicalLayer(keyboardWrapper, mouseWrapper, controllerWrapper, focusWrapper);

        logicalLayer.registerInput(canvas, pl);

        canvas.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                resizeCanvas(canvas);
            }

        });

        frameWork.addCanvas(canvas);

        FirstPersonControl control = FirstPersonControl.setupTriggers(logicalLayer, new Vector3(0, 1, 0), true);
         
        scene.init();
    }

    private static void resizeCanvas(JoglSwingCanvas canvas) {
        int w = canvas.getWidth();
        int h = canvas.getHeight();
        double r = (double) w / (double) h;

        Camera cam = canvas.getCanvasRenderer().getCamera();
        if (null != cam) {
            cam.resize(w, h);

            cam.setFrustumPerspective(cam.getFovY(), r, cam.getFrustumNear(),
                    cam.getFrustumFar());
        }
    }   

    public void panelOpened() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                frameWork.init();
                while (!exit) {
                    logicalLayer.checkTriggers(0);
                    frameWork.updateFrame();
                    Thread.yield();
                }
            }
        }).start();
    }

    public void panelClosed() {
    }

    protected void panelHidden() {
    }

    protected void panelShowing() {
    }
}
