package org.liamwang.transforms;

import org.liamwang.transforms.threed.Transform3D;

public class Transform3DStamped {

    private final String fromId;
    private final String toId;
    private final Transform3D transform3D;

    public Transform3DStamped(String fromId, String toId, Transform3D transform3D) {
        this.fromId = fromId;
        this.toId = toId;
        this.transform3D = transform3D;
    }

    public String getFromId() {
        return fromId;
    }

    public String getToId() {
        return toId;
    }

    public Transform3D getTransform3D() {
        return transform3D;
    }
}
