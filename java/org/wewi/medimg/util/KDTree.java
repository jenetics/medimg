/**
 * KDTree.java
 * 
 * Created on 12.03.2003, 11:04:25
 *
 */
package org.wewi.medimg.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Stack;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class KDTree {
    
    public static interface Point {
       public Comparable getOrdinate(int dimension);
   
       public int getDimensions();
    }
    
    private final class Node {
        private int discriminate;
        private int nsubtreenodes;
        private Point point;
        private Node left, right;
    
        public Node(Point point, int discriminate) {
            this.point = point;
            left  = null;
            right = null;
            this.discriminate = discriminate;
        
            nsubtreenodes = Integer.MIN_VALUE;
        }
    
        public int getNSubtreeNodes() {
            if (left == null && right == null) {
                return 1;
            }
            if (left == null && right != null) {
                return 1 + right.getNSubtreeNodes();
            }
            if (right == null && left != null) {
                return 1 + left.getNSubtreeNodes();
            }
            return left.getNSubtreeNodes() + right.getNSubtreeNodes();
        }
    
        public int getDiscriminate() {
            return discriminate;
        }
    
        public Point getPoint() {
            return point;
        }
    
        public Node getLeft() {
            return left;
        }
    
        public void setLeft(Node left) {
            this.left = left;
        }
    
        public Node getRight() {
            return right;
        }
    
        public void setRight(Node right) {
            this.right = right;
        }
    
        public void print(StringBuffer buffer) {
            buffer.append(point.toString());
            buffer.append(" ");
            buffer.append(discriminate);
        }
    
        public String toString() {
            StringBuffer buffer = new StringBuffer();
            buffer.append(point.toString());
            buffer.append(" ");
            buffer.append(discriminate);    
            return buffer.toString();
        }
    }    
    
    
    
    
    
    private Node root;

    public KDTree() { 
        root = null; 
    }
    
    /**
     * Inserting
     * 
     * @param point einzufügender Punkt
     */
    public void insert(Point point) { 
        if(root == null) {
            root = new Node(point, 0);
            return;
        } 
        
        int discriminate, dimensions;
        Node curNode, tmpNode;
        Comparable ordinate1, ordinate2;
        curNode = root;
        
        do {
            tmpNode = curNode;
            discriminate = tmpNode.getDiscriminate();
            ordinate1 = point.getOrdinate(discriminate);
            ordinate2 = tmpNode.getPoint().getOrdinate(discriminate);
            
            if(ordinate1.compareTo(ordinate2) > 0) {
                curNode = tmpNode.getRight();
            } else {
                curNode = tmpNode.getLeft();
            }
        } while(curNode != null);
        
        dimensions = point.getDimensions();
        
        if(++discriminate >= dimensions) {
            discriminate = 0;
        }
        
        if(ordinate1.compareTo(ordinate2) > 0) {
            tmpNode.setRight(new Node(point, discriminate));
        } else {
            tmpNode.setLeft(new Node(point, discriminate));
        }
        
    }
    
    
    /**
     * Determines if a point is contained within a given
     * k-dimensional bounding box.
     */
    private static final boolean isContained(Point point, Point lower, Point upper) {
        int dimensions;
        Comparable ordinate1, ordinate2, ordinate3;
        dimensions = point.getDimensions();
        
        for(int i = 0; i < dimensions; ++i) {
            ordinate1 = point.getOrdinate(i);
            ordinate2 = lower.getOrdinate(i);
            ordinate3 = upper.getOrdinate(i);
            
            if(ordinate1.compareTo(ordinate2) < 0 || ordinate1.compareTo(ordinate3) > 0) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Searches the tree for all points contained within a
     * given k-dimensional bounding box and stores them in a
     * Collection.
     */
    public void search(Collection results, Point lowerExtreme, Point upperExtreme) {
        Node tmpNode;
        Stack stack = new Stack();
        int discriminate;
        Comparable ordinate1, ordinate2;
        
        if(root == null) {
            return;
        }
        
        stack.push(root);
        
        while(!stack.empty()) {
            tmpNode =(Node)stack.pop();
            discriminate = tmpNode.getDiscriminate();
            ordinate1 = tmpNode.getPoint().getOrdinate(discriminate);
            ordinate2 = lowerExtreme.getOrdinate(discriminate);
            
            if(ordinate1.compareTo(ordinate2) > 0 && tmpNode.getLeft() != null) {
                stack.push(tmpNode.getLeft());
            }
            
            ordinate2 = upperExtreme.getOrdinate(discriminate);
            
            if(ordinate1.compareTo(ordinate2) < 0 && tmpNode.getRight() != null) {
                stack.push(tmpNode.getRight());
            }
            
            if(isContained(tmpNode.getPoint(), lowerExtreme, upperExtreme)) {
                results.add(tmpNode.getPoint());
            }
        }
    }
    
    public Collection search(Point lowerExtreme, Point upperExtreme) {
        ArrayList result = new ArrayList();
        
        search(result, lowerExtreme, upperExtreme);
        
        return result;
    }
}
















