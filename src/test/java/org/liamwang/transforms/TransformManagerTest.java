package org.liamwang.transforms;

import org.liamwang.transforms.threed.Transform3D;

public class TransformManagerTest {

    public static void main(String[] args) {
        TransformManager tf = new TransformManager();

        tf.sendTransform(new Transform3DStamped("base_link2", "wow",
            new Transform3D(1, 1, 0)));

        tf.sendTransform(new Transform3DStamped("odom", "base_link2",
            new Transform3D(1, 1, 0)));

        tf.sendTransform(new Transform3DStamped("limelight", "vision_target",
            new Transform3D(1, 0, 0)));

        tf.sendTransform(new Transform3DStamped("odom", "base_link",
            new Transform3D(1, 0, 0)));

        tf.sendTransform(new Transform3DStamped("base_link", "limelight",
            new Transform3D(1, 0, 0)));

        System.out.println(tf.getTransform("vision_target", "wow"));
    }
}
