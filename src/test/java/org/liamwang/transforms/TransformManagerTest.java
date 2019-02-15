package org.liamwang.transforms;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.liamwang.transforms.threed.Transform3D;

public class TransformManagerTest {

    @Test
    public void repeatSendTransformTest() {
        TransformManager tf = new TransformManager();

        Transform3D a = new Transform3D(1, 0, Math.PI / 4);
        tf.sendTransform(new Transform3DStamped("odom", "base_link",
            a));
        Transform3D b = tf.getTransform("odom", "base_link");
        assertEquals(a, b);
        tf.sendTransform(new Transform3DStamped("odom", "base_link",
            a));
        Transform3D c = tf.getTransform("odom", "base_link");
        assertEquals(a, c);
        tf.sendTransform(new Transform3DStamped("odom", "base_link",
            a));
        Transform3D d = tf.getTransform("odom", "base_link");
        assertEquals(a, d);
    }

    @Test
    public void rootTransformSendTest() {
        TransformManager tf = new TransformManager();
        Transform3D b = new Transform3D(
            1, 0, 0, 0, 0, 0);
        tf.sendTransform(new Transform3DStamped("base_link", "limelight",
            b));
        Transform3D a = new Transform3D(5, 0, 0, 0, 0, 3);
        tf.sendTransform(new Transform3DStamped("odom", "base_link",
            a));
        Transform3D c = tf.getTransform("odom", "limelight");
        assertEquals(a.add(b), c);
    }
}
