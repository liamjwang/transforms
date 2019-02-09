package org.liamwang.transforms.utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * @author Jonathan Edelman
 */
public class ManualFastSearchTree<T> {

    private HashMap<T, Node<T>> nodes = new HashMap<>();
    private Node<T> root;

    public ManualFastSearchTree(T rootVal) {
        this.root = new Node<>(rootVal, null);
        nodes.put(rootVal, root);
    }

    public void add(T val, T parent) {
        Node<T> parentNode = nodes.get(parent);
        if (parentNode == null) {
            throw new NoSuchElementException("Parent " + parent + " not in tree.");
        }
        if (nodes.get(val) != null) {
            throw new IllegalStateException("Val " + val + " already in tree.");
        }
        Node<T> newNode = new Node<>(val, parentNode);
        parentNode.addChild(newNode);
        nodes.put(val, newNode);
    }

    public void setRoot(T val) {
        if (nodes.get(val) != null) {
            throw new IllegalStateException("Val " + val + " already in tree.");
        }
        Node<T> newNode = new Node<>(val, null);
        newNode.addChild(root);
        nodes.put(val, newNode);
        root.parent = newNode;
        root = newNode;
    }

    public T getRoot() {
        return root.val;
    }

    public T getParent(T val) {
        Node<T> tNode = nodes.get(val);
        if (tNode == null) {
            throw new NoSuchElementException("Val " + val + " not in tree.");
        }
        if (tNode.getParent() == null) {
            return null;
        }
        return tNode.getParent().val;
    }

    public Traversal shortestPath(T val1, T val2) {

        if (val1 == val2) {
            Traversal traversal = new Traversal();
            traversal.root = val1;
            return traversal;
        }

        if (nodes.get(val1) == null) {
            throw new NoSuchElementException("Val " + val1 + " not in tree.");
        }
        if (nodes.get(val2) == null) {
            throw new NoSuchElementException("Val " + val2 + " not in tree.");
        }

        LinkedList<T> val1ToRoot = new LinkedList<>();
        Node<T> current = nodes.get(val1);
        while (!current.val.equals(root.val)) {
            val1ToRoot.addLast(current.val);
            current = current.parent;
        }
        val1ToRoot.addLast(root.val);

        LinkedList<T> val2ToRoot = new LinkedList<>();
        current = nodes.get(val2);
        while (!current.val.equals(root.val)) {
            val2ToRoot.addLast(current.val);
            current = current.parent;
        }
        val2ToRoot.addLast(root.val);

        Traversal traversal = new Traversal();

        if (val1.equals(root.val)) {
            traversal.root = root.val;
            traversal.setDownList(val2ToRoot);
            return traversal;
        } else if (val2.equals(root.val)) {
            traversal.root = root.val;
            traversal.setUpList(val1ToRoot);
            return traversal;
        } else {
            Iterator<T> d1 = val1ToRoot.descendingIterator();
            Iterator<T> d2 = val2ToRoot.descendingIterator();

            T currentVal1, currentVal2;
            do {
                currentVal1 = d1.next();
                currentVal2 = d2.next();
            } while (currentVal1.equals(currentVal2));

            traversal.setRoot(nodes.get(currentVal1).parent.val);
            traversal.getUpList().addFirst(currentVal1);
            d1.forEachRemaining(traversal.getUpList()::addFirst);
            traversal.getDownList().addLast(currentVal2);
            d2.forEachRemaining(traversal.getDownList()::addLast);

            return traversal;
        }
    }

    public void mergeOther(ManualFastSearchTree<T> toTree, T fromFrame) {

    }


    private class Node<T> {

        private T val;
        private Node<T> parent;
        private HashSet<Node<T>> children = new HashSet<>();


        private Node(T val, Node<T> parent) {
            this.val = val;
            if (parent != null) {
                this.parent = parent;
            }
        }

        private void addChild(Node<T> child) {
            children.add(child);
        }

        private Node<T> getParent() {
            return parent;
        }

        private HashSet<Node<T>> getChildren() {
            return children;
        }
    }

    public class Traversal {

        T root;
        LinkedList<T> upList = new LinkedList<>();
        LinkedList<T> downList = new LinkedList<>();

        public T getRoot() {
            return root;
        }

        public void setRoot(T root) {
            this.root = root;
        }

        public LinkedList<T> getUpList() {
            return upList;
        }

        public LinkedList<T> getDownList() {
            return downList;
        }

        private void setUpList(LinkedList<T> upList) {
            this.upList = upList;
        }

        private void setDownList(LinkedList<T> downList) {
            this.downList = downList;
        }
    }

}