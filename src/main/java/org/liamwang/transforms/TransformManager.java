package org.liamwang.transforms;

import java.util.Map;
import org.liamwang.transforms.threed.Transform3D;
import org.liamwang.transforms.utils.ManualFastSearchTree;

/**
 * Coordinate frame based transform management
 *
 * Currently supports only one tree of transforms at a time. Each sent transform must be from a direct parent to child. Transform queries can be between any coordinate frames.
 */
public class TransformManager {

    private Map<String, CoordinateFrame> frameMap;
    private ManualFastSearchTree<CoordinateFrame> frameTree; // TODO: This is actually a graph

    /**
     * @param transform TransformStamped to be sent, must be directly from parent to child
     */
    public void sendTransform(Transform3DStamped transform) {
        String fromId = transform.getFromId();
        String toId = transform.getToId();

        CoordinateFrame fromFrame;
        if (!frameMap.containsKey(fromId)) { // If fromFrame does NOT already exist
            if (!frameMap.get(toId).equals(frameTree.getRoot())) { // If the transform is NOT a transform to the previous transform root // TODO: Support forest of transform trees
                throw new IllegalArgumentException(
                    "Source frame " + fromId + " is not defined and destination frame " + toId + " is not the previous frame root " + frameTree.getRoot().getName() + ".");
            }
            fromFrame = new CoordinateFrame(fromId);
            frameMap.put(fromId, fromFrame);
            frameTree.setRoot(fromFrame);
        } else {
            fromFrame = frameMap.get(fromId);
        }

        CoordinateFrame toFrame;
        if (!frameMap.containsKey(toId)) { // If toFrame is new
            toFrame = new CoordinateFrame(toId);
            frameMap.put(toId, toFrame);
            frameTree.add(toFrame, fromFrame);
        } else {
            toFrame = frameMap.get(toId);
        }
        // assert parent toFrame's parent is fromFrame
        if (!frameTree.getParent(toFrame).equals(fromFrame)) { // TODO: Add legal method to move parent
            throw new IllegalArgumentException("Source frame does not directly parent destination frame");
        }

        toFrame.setRelativeTransform(transform.getTransform3D());
    }

    public Transform3D getTransform(String sourceId, String destId) {
        return null;
    }
}
