package com.liamwang.transforms;

import org.liamwang.transforms.utils.ManualFastSearchTree;
import org.liamwang.transforms.utils.ManualFastSearchTree.Traversal;

public class Test {

    private static ManualFastSearchTree<String> tree = new ManualFastSearchTree<>(".");

    public static void main(String[] args) {

        letters(".", 5);
        tree.setRoot("_");

        Traversal traversal = tree.shortestPath(".LMAO", ".SUCC");

        System.out.println(traversal.getUpList() + "\t" + traversal.getRoot() + "\t" + traversal.getDownList());
    }

    private static void letters(String base, int remainingDepth) {
        if (remainingDepth == 0) {
            return;
        }
        for (char alphabet = 'A'; alphabet <= 'Z'; alphabet++) {
            tree.add(base + alphabet, base);
            letters(base + alphabet, remainingDepth - 1);
        }
    }

}