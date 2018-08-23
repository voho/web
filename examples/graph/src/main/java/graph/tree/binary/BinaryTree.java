package graph.tree.binary;

import java.util.ArrayList;
import java.util.List;

/**
 * Binary tree example.
 *
 * @author Vojtěch Hordějčuk
 */
public class BinaryTree<VALUE extends Comparable<? super VALUE>> {
    private static class Node<VALUE> {
        private VALUE innerValue;
        private Node<VALUE> left;
        private Node<VALUE> right;
    }

    private Node<VALUE> root;

    /**
     * Puts the value into the tree.
     *
     * @param value value to put
     */
    public void put(final VALUE value) {
        assert value != null;
        root = putRecursive(value, root);
    }

    /**
     * Removes the value from the tree.
     *
     * @param value value to be removed
     */
    public void remove(final VALUE value) {
        assert value != null;
        root = removeRecursive(value, root);
    }

    /**
     * Returns the minimum value.
     *
     * @return the minimum value or NULL if the tree is empty
     */
    public VALUE getMinimum() {
        final Node<VALUE> nodeWithMinimum = findLeftmost(root);
        return nodeWithMinimum != null ? nodeWithMinimum.innerValue : null;
    }

    /**
     * Returns the maximum value.
     *
     * @return the maximum value or NULL if the tree is empty
     */
    public VALUE getMaximum() {
        final Node<VALUE> nodeWithMaximum = findRightmost(root);
        return nodeWithMaximum != null ? nodeWithMaximum.innerValue : null;
    }

    private Node<VALUE> putRecursive(final VALUE value, final Node<VALUE> root) {
        if (root == null) {
            final Node<VALUE> newRoot = new Node<VALUE>();
            newRoot.innerValue = value;
            return newRoot;
        }

        final int comparison = value.compareTo(root.innerValue);

        if (comparison > 0) {
            // greater value = put in the right subtree
            root.right = putRecursive(value, root.right);
            return root;
        } else if (comparison < 0) {
            // lower value = put in the left subtree
            root.left = putRecursive(value, root.left);
            return root;
        } else {
            // same value = put here
            root.innerValue = value;
            return root;
        }
    }

    private Node<VALUE> removeRecursive(final VALUE value, final Node<VALUE> root) {
        if (root == null) {
            return null;
        }

        final int comparison = value.compareTo(root.innerValue);

        if (comparison > 0) {
            // greater value = remove from the right subtree
            root.right = removeRecursive(value, root.right);
            return root;
        } else if (comparison < 0) {
            // lower value = remove from the left subtree
            root.left = removeRecursive(value, root.left);
            return root;
        } else {
            // same value = remove this node

            if (root.left == null && root.right == null) {
                // removing leaf: no changes needed
                return null;
            } else if (root.left == null) {
                // removing node with right child only: replace by right child
                return root.right;
            } else if (root.right == null) {
                // removing node with left child only: replace by left child
                return root.left;
            } else {
                // removing node with two children:
                // 1) choose left or right subtree (does not matter - we choose right)
                // 1) find in-order successor in the right subtree
                // 2) copy value from successor to this node
                // 3) remove value from the right subtree to remove duplicate
                final Node<VALUE> inOrderSuccessor = findLeftmost(root.right);
                root.innerValue = inOrderSuccessor.innerValue;
                root.right = removeRecursive(root.innerValue, root.right);
                return root;
            }
        }
    }

    private Node<VALUE> findLeftmost(final Node<VALUE> root) {
        return root == null ? null : root.left == null ? root : findLeftmost(root.left);
    }

    private Node<VALUE> findRightmost(final Node<VALUE> root) {
        return root == null ? null : root.right == null ? root : findRightmost(root.right);
    }

    // TREE WALK-THROUGH
    // =================

    public List<VALUE> listValuesPreOrder() {
        final List<VALUE> accumulator = new ArrayList<VALUE>();
        collectValuesPreOrder(root, accumulator);
        return accumulator;
    }

    public List<VALUE> listValuesInOrder() {
        final List<VALUE> accumulator = new ArrayList<VALUE>();
        collectValuesInOrder(root, accumulator);
        return accumulator;
    }

    public List<VALUE> listValuesPostOrder() {
        final List<VALUE> accumulator = new ArrayList<VALUE>();
        collectValuesPostOrder(root, accumulator);
        return accumulator;
    }

    private void collectValuesPreOrder(final Node<VALUE> root, final List<VALUE> accumulator) {
        if (root != null) {
            accumulator.add(root.innerValue);
            collectValuesPreOrder(root.left, accumulator);
            collectValuesPreOrder(root.right, accumulator);
        }
    }

    private void collectValuesInOrder(final Node<VALUE> root, final List<VALUE> accumulator) {
        if (root != null) {
            collectValuesInOrder(root.left, accumulator);
            accumulator.add(root.innerValue);
            collectValuesInOrder(root.right, accumulator);
        }
    }

    private void collectValuesPostOrder(final Node<VALUE> root, final List<VALUE> accumulator) {
        if (root != null) {
            collectValuesPostOrder(root.left, accumulator);
            collectValuesPostOrder(root.right, accumulator);
            accumulator.add(root.innerValue);
        }
    }
}
