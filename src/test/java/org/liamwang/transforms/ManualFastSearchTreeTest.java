package org.liamwang.transforms;

import org.liamwang.transforms.utils.ManualFastSearchTree;
import org.liamwang.transforms.utils.ManualFastSearchTree.Traversal;

public class ManualFastSearchTreeTest {

    private static ManualFastSearchTree<String> tree = new ManualFastSearchTree<>("1");
    private static ManualFastSearchTree<String> tree2 = new ManualFastSearchTree<>("2");

    public static void main(String[] args) {

        letters(tree, "1", 1);
        letters(tree2, "2", 1);
        tree.addUnsafe(tree2, "1A");
//        tree.setRoot("_");

        Traversal traversal = tree.shortestPath("2A", "1Z");

        System.out.println(traversal.getUpList() + "\t" + traversal.getRoot() + "\t" + traversal.getDownList());
    }

    private static void letters(ManualFastSearchTree<String> tree, String base,
        int remainingDepth) {
        if (remainingDepth == 0) {
            return;
        }
        for (char alphabet = 'A'; alphabet <= 'Z'; alphabet++) {
            tree.add(base + alphabet, base);
            letters(tree, base + alphabet, remainingDepth - 1);
        }
    }

}