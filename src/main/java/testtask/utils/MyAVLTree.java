package testtask.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MyAVLTree {
    private class AVLNode {
        private int height;
        private String value;
        private AVLNode leftChild;
        private AVLNode rightChild;

        public AVLNode(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
    }

    private AVLNode root;
    private List<String> filteredValues = new ArrayList<>();

    public void insert(String value) {
        root = insert(root, value);
    }

    public String getRootValue() {
        return root.toString();
    }

    public void leftPreOrderTraversal(String mask) {
        searchTraversePreOrder(root.leftChild, mask);
    }

    public void rightPreOrderTraversal(String mask) {
        searchTraversePreOrder(root.rightChild, mask);
    }

    public List<String> getFilteredValues() {
        return filteredValues;
    }

    private void searchTraversePreOrder(AVLNode root, String mask) {
        if(root == null)
            return;

        if(root.value.startsWith(mask))
            filteredValues.add(root.value);

        searchTraversePreOrder(root.leftChild, mask);
        searchTraversePreOrder(root.rightChild, mask);
    }

    private AVLNode insert(AVLNode root, String value) {
        if (root == null)
            return new AVLNode(value);

        if (value.compareTo(root.value) < root.value.compareTo(value))
            root.leftChild = insert(root.leftChild, value);
        else
            root.rightChild = insert(root.rightChild, value);

        setHeight(root);

        return balance(root);
    }

    private AVLNode balance(AVLNode root) {
        if (isLeftHeavy(root)) {
            if (balanceFactor(root.leftChild) < 0)
                root.leftChild = rotateLeft(root.leftChild);
            return rotateRight(root);
        }
        else if (isRightHeavy(root)) {
            if (balanceFactor(root.rightChild) > 0)
                root.rightChild = rotateRight(root.rightChild);
            return rotateLeft(root);
        }
        return root;
    }

    private AVLNode rotateLeft(AVLNode root) {
        var newRoot = root.rightChild;

        root.rightChild = newRoot.leftChild;
        newRoot.leftChild = root;

        setHeight(root);
        setHeight(newRoot);

        return newRoot;
    }

    private AVLNode rotateRight(AVLNode root) {
        var newRoot = root.leftChild;

        root.leftChild = newRoot.rightChild;
        newRoot.rightChild = root;

        setHeight(root);
        setHeight(newRoot);

        return newRoot;
    }

    private void setHeight(AVLNode node) {
        node.height = Math.max(
                height(node.leftChild),
                height(node.rightChild)) + 1;
    }

    private boolean isLeftHeavy(AVLNode node) {
        return balanceFactor(node) > 1;
    }

    private boolean isRightHeavy(AVLNode node) {
        return balanceFactor(node) < -1;
    }

    private int balanceFactor(AVLNode node) {
        return (node == null) ? 0 : height(node.leftChild) - height(node.rightChild);
    }

    private int height(AVLNode node) {
        return (node == null) ? -1 : node.height;
    }
}
