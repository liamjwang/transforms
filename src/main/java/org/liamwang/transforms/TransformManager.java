package org.liamwang.transforms;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import org.liamwang.transforms.threed.Transform3D;
import org.liamwang.transforms.utils.ManualFastSearchTree;
import org.liamwang.transforms.utils.ManualFastSearchTree.Traversal;

/**
 * Coordinate frame based transform management
 *
 * Currently supports only one tree of transforms at a time. Each sent transform must be from a direct parent to child. Transform queries can be between any coordinate frames.
 */
public class TransformManager {

    private Map<String, CoordinateFrame> frameMap = new HashMap<>(); // Frame Name -> CoordinateFrame
    private Map<Integer, ManualFastSearchTree<CoordinateFrame>> frameTreeMap; // TreeID -> Tree
    private int treeIDInc = 0;

    /**
     * @param transform TransformStamped to be sent, must be directly from parent to child
     */
    public void sendTransform(Transform3DStamped transform) {
        /*
        if fromFrame does exist
            if toFrame does exist
                if fromFrame is direct parent of toFrame
                    update
                    return
                else - merge trees
                    assert toFrame is a root
                    merge trees
                    update
                    return
            else - toFrame doesn't exist
                create toFrame
                update
                return
        else - fromFrame doesn't exist
            if toFrame does exist
                assert toFrame is a root (doesn't have a parent)
                fromFrame is new root, child is toFrame
                update
                return
            else - toFrame doesn't exist
                Make a new searchtree with the new data
                update
                return
         */

        String fromId = transform.getFromId();
        String toId = transform.getToId();

        CoordinateFrame fromFrame = frameMap.get(fromId);
        CoordinateFrame toFrame = frameMap.get(toId);

        ManualFastSearchTree<CoordinateFrame> toTree = frameTreeMap.get(toFrame.getTreeID());
        ManualFastSearchTree<CoordinateFrame> fromTree = frameTreeMap.get(fromFrame.getTreeID());

        if (fromFrame != null) {
            if (toFrame != null) { // fromFrame and toFrame both exist
                if (fromTree.getParent(fromFrame).equals(toFrame)) {
                    // good to go
                } else {
                    if (!toTree.getRoot().equals(toFrame)) {
                        throw new IllegalArgumentException("Destination frame " + toFrame + " is not a direct child of " + fromFrame + " and already has a parent.");
                    }
                    fromTree.mergeOther(toTree, fromFrame);
                }
            } else { // fromFrame does exist, toFrame does not exist
                toFrame = new CoordinateFrame(toId, fromFrame.getTreeID());
                frameMap.put(toId, toFrame);
                fromTree.add(toFrame, fromFrame);
            }
        } else {
            if (toFrame != null) { // fromFrame does not exist, toFrame does
                ManualFastSearchTree<CoordinateFrame> toFrameTree = toTree;
                if (!toFrameTree.getRoot().equals(toFrame)) {
                    throw new IllegalArgumentException("Destination frame " + toFrame + " already has a parent.");
                }
                fromFrame = new CoordinateFrame(fromId, toFrame.getTreeID());
                toFrameTree.setRoot(fromFrame);
            } else { // fromFrame and toFrame each do not exist
                treeIDInc++;
                fromFrame = new CoordinateFrame(fromId, treeIDInc);
                toFrame = new CoordinateFrame(toId, treeIDInc);
                ManualFastSearchTree<CoordinateFrame> newTree = new ManualFastSearchTree<>(fromFrame);
                frameTreeMap.put(treeIDInc, newTree);
                newTree.add(fromFrame, toFrame);
            }
        }

        toFrame.setRelativeTransform(transform.getTransform3D());
    }

    public Transform3D getTransform(String sourceId, String destId) {
        CoordinateFrame sourceFrame = frameMap.get(sourceId);
        CoordinateFrame destFrame = frameMap.get(destId);
        if (sourceFrame == null) {
            throw new NoSuchElementException("Frame " + sourceId + " is not defined.");
        }
        if (destFrame == null) {
            throw new NoSuchElementException("Frame " + destId + " is not defined.");
        }
        if (sourceFrame.getTreeID() != destFrame.getTreeID()) {
            throw new IllegalArgumentException("No path from " + sourceId + " to " + destId + ".");
        }
        Traversal shortestPath = frameTreeMap.get(sourceFrame.getTreeID()).shortestPath(sourceFrame, destFrame);
        shortestPath.getDownList().forEach(frame -> {
            System.out.println(((CoordinateFrame) frame).getName());
        });
        shortestPath.getUpList().forEach(frame -> {
            System.out.println(((CoordinateFrame) frame).getName());
        });
        return null;
    }
}
