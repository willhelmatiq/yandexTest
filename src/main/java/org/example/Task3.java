package org.example;

import java.util.*;

public class Task3 {

    public static void main(String[] args) {
        printResultTree();
    }

    static void printResultTree() {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int changesCount = scanner.nextInt();
        Map<Integer,MyNode> nodeMap = createTree(n);
        for (int i = 0; i < changesCount; i++) {
            nodeMap.get(scanner.nextInt()).transformTree();
        }

        MyNode root = findRoot(nodeMap.get(1));
        printTree(root);

    }

    static Map<Integer,MyNode>  createTree(int n) {
        Map<Integer,MyNode> nodeMap = new HashMap<>();
        for (int i = 1; i <= n; i++) {
            nodeMap.put(i ,new MyNode(i));
            if(i > 1) {
                MyNode parentNode = nodeMap.get(i/2);
                MyNode childNode = nodeMap.get(i);
                if (i%2 == 0) {
                    parentNode.setLeft(childNode);
                    childNode.setParent(parentNode);
                } else {
                    parentNode.setRight(childNode);
                    childNode.setParent(parentNode);
                }
            }
        }
        return nodeMap;
    }

    static MyNode findRoot(MyNode myNode){
        if(myNode.getParent() != null) {
            return findRoot(myNode.getParent());
        }
        return myNode;
    }
    static void printTree(MyNode root) {
        if (root.getLeft() != null) {
            printTree(root.getLeft());
        }
        System.out.print(root.id + " ");
        if (root.getRight() != null) {
            printTree(root.getRight());
        }
    }
}

class MyNode {
    MyNode parent;
    MyNode left;
    MyNode right;

    int id;

    public MyNode(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MyNode getParent() {
        return parent;
    }

    public void setParent(MyNode parent) {
        this.parent = parent;
    }

    public MyNode getLeft() {
        return left;
    }

    public void setLeft(MyNode left) {
        this.left = left;
    }

    public MyNode getRight() {
        return right;
    }

    public void setRight(MyNode right) {
        this.right = right;
    }

    public void transformTree() {
        MyNode currentNode = this;
        MyNode parentNode = currentNode.getParent();
        if(parentNode != null) {
            MyNode grandParentNode = parentNode.getParent();
            if (grandParentNode != null) {
                currentNode.setParent(grandParentNode);
                if (grandParentNode.getLeft() != null && grandParentNode.getLeft().equals(parentNode)) {
                    grandParentNode.setLeft(currentNode);
                } else {
                    grandParentNode.setRight(currentNode);
                }
            } else {
                currentNode.setParent(null);
            }
            if(currentNode.equals(parentNode.getLeft())) {
                parentNode.setLeft(currentNode.getLeft());
                parentNode.setParent(currentNode);
                if (currentNode.getLeft() != null) {
                    currentNode.getLeft().setParent(parentNode);
                }
                currentNode.setLeft(parentNode);
            } else {
                parentNode.setRight(currentNode.getRight());
                parentNode.setParent(currentNode);

                if (currentNode.getRight() != null) {
                    currentNode.getRight().setParent(parentNode);
                }
                currentNode.setRight(parentNode);
            }
        }
    }
}