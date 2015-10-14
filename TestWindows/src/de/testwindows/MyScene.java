/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.testwindows;

import com.ardor3d.intersection.PickResults;
import com.ardor3d.light.PointLight;
import com.ardor3d.math.ColorRGBA;
import com.ardor3d.math.Ray3;
import com.ardor3d.math.Vector3;
import com.ardor3d.renderer.ContextManager;
import com.ardor3d.renderer.Renderer;
import com.ardor3d.renderer.state.LightState;
import com.ardor3d.renderer.state.ZBufferState;
import com.ardor3d.scenegraph.Mesh;
import com.ardor3d.scenegraph.Node;
import com.ardor3d.scenegraph.shape.Box;
import com.ardor3d.util.ContextGarbageCollector;
import com.ardor3d.util.GameTaskQueue;
import com.ardor3d.util.GameTaskQueueManager;
import com.ardor3d.util.ReadOnlyTimer;
import java.util.Random;

/**
 *
 * @author Andreas
 */
public class MyScene implements UpdaterScene {

    private final Node root;
    private boolean inited;
    private Mesh box;
    private static final int light_loc = 4;

    public MyScene() {
        this.root = new Node("root");
    }

    public Node getRoot() {
        return root;
    }

    public Mesh getBox() {
        return box;
    }
    @Override
    public boolean renderUnto(Renderer renderer) {
        // Execute renderQueue item
        GameTaskQueueManager.getManager(ContextManager.getCurrentContext()).getQueue(GameTaskQueue.RENDER)
                .execute(renderer);
        ContextGarbageCollector.doRuntimeCleanup(renderer);

        renderer.draw(root);
        return true;
    }

    @Override
    public PickResults doPick(Ray3 ray3) {
        return null;
    }

    @Override
    public void init() {
        if (inited) {
            return;
        }
        box = new Box("The cube", new Vector3(-1, -1, -1), new Vector3(1, 1, 1));

        final ZBufferState buf = new ZBufferState();
        buf.setEnabled(true);
        buf.setFunction(ZBufferState.TestFunction.LessThanOrEqualTo);
        root.setRenderState(buf);

        final PointLight light = new PointLight();

        final Random random = new Random();

        final float r = random.nextFloat();
        final float g = random.nextFloat();
        final float b = random.nextFloat();
        final float a = random.nextFloat();

        light.setDiffuse(new ColorRGBA(r, g, b, a));
        light.setAmbient(new ColorRGBA(0.5f, 0.5f, 0.5f, 1.0f));
        light.setLocation(new Vector3(light_loc, light_loc, light_loc));
        light.setEnabled(true);

        /**
         * Attach the light to a lightState and the lightState to rootNode.
         */
        final LightState lightState = new LightState();
        lightState.setEnabled(true);
        lightState.attach(light);
        root.setRenderState(lightState);

        root.attachChild(box);

        inited = true;
    }

    @Override
    public void update(ReadOnlyTimer timer) {
        double tpf = timer.getTimePerFrame();
        root.updateGeometricState(tpf, true);
    }

}
