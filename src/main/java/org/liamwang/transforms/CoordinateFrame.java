package org.liamwang.transforms;

import org.liamwang.transforms.threed.Transform3D;

public class CoordinateFrame {

    private final String name;
    private Transform3D relativeTransform3D;
    private int treeID;

    public CoordinateFrame(String name, int treeID) {
        this.name = name;
        this.treeID = treeID;
    }

    public void setRelativeTransform(Transform3D relativeTransform3D) {
        this.relativeTransform3D = relativeTransform3D;
    }

    public Transform3D getRelativeTransform3D() {
        return relativeTransform3D;
    }

    public String getName() {
        return name;
    }

    public int getTreeID() {
        return treeID;
    }

    public void setTreeID(int treeID) {
        this.treeID = treeID;
    }
}
