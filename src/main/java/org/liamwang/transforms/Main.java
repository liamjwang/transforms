package org.liamwang.transforms;

import org.liamwang.transforms.threed.Transform3D;

public class Main {

    public static void main(String[] args) {
        TransformManager tf = new TransformManager();

        tf.sendTransform(new Transform3DStamped("limelight", "vision_target",
            new Transform3D(1, 0, Math.PI / 2)));

        tf.sendTransform(new Transform3DStamped("odom", "base_link",
            new Transform3D(1, 1, Math.PI / 2)));

        tf.sendTransform(new Transform3DStamped("base_link", "limelight",
            new Transform3D(1, 1, Math.PI / 2)));

        tf.getTransform("odom", "vision_target");
    }
}
