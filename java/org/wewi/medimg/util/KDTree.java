/*
 * KDTree.java
 *
 * Created on 6. Februar 2002, 09:18
 */

package org.wewi.medimg.util;

import java.util.Collection;
import java.util.Stack;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public class KDTree {
    protected KDNode root;
    
    public KDTree() { 
        root = null; 
    }
    
    /**
     * Einfügen eines Punktes unter beibehaltung der räumlichen Ordnung
     * 
     * @param point einzufügender Punkt
     */
    public void insert(KDTreePoint point) { 
        if(root == null) {
            root = new KDNode(point, 0);
        } else {
            int discriminate, dimensions;
            KDNode curNode, tmpNode;
            Comparable ordinate1, ordinate2;
            curNode = root;
            
            do {
                tmpNode = curNode;
                discriminate = tmpNode.getDiscriminate();
                ordinate1 = point.getComparableOrdinate(discriminate);
                ordinate2 = tmpNode.getPoint().getComparableOrdinate(discriminate);
                if(ordinate1.compareTo(ordinate2) > 0)
                    curNode = tmpNode.getRight();
                else
                    curNode = tmpNode.getLeft();
            } while(curNode != null);
            
            dimensions = point.getNDim();
            if(++discriminate >= dimensions) {
                discriminate = 0;
            }
            if(ordinate1.compareTo(ordinate2) > 0) {
                tmpNode.setRight(new KDNode(point, discriminate));
            } else {
                tmpNode.setLeft(new KDNode(point, discriminate));
            }
        }
    }
    
    
    /**
     * Determines if a point is contained within a given
     * k-dimensional bounding box.
     */
    static final boolean isContained(KDTreePoint point, KDTreePoint lower, KDTreePoint upper) {
        int dimensions;
        Comparable ordinate1, ordinate2, ordinate3;
        dimensions = point.getNDim();
        for(int i = 0; i < dimensions; ++i) {
            ordinate1 = point.getComparableOrdinate(i);
            ordinate2 = lower.getComparableOrdinate(i);
            ordinate3 = upper.getComparableOrdinate(i);
            if(ordinate1.compareTo(ordinate2) < 0 ||
            ordinate1.compareTo(ordinate3) > 0)
                return false;
        }
        return true;
    }
    /**
     * Searches the tree for all points contained within a
     * given k-dimensional bounding box and stores them in a
     * Collection.
     */
    public void rangeSearch(Collection results, KDTreePoint lowerExtreme, KDTreePoint upperExtreme) {
        KDNode tmpNode;
        Stack stack = new Stack();
        int discriminate;
        Comparable ordinate1, ordinate2;
        if(root == null)
            return;
        stack.push(root);
        while(!stack.empty()) {
            tmpNode =(KDNode)stack.pop();
            discriminate = tmpNode.getDiscriminate();
            ordinate1 = tmpNode.getPoint().getComparableOrdinate(discriminate);
            ordinate2 = lowerExtreme.getComparableOrdinate(discriminate);
            if(ordinate1.compareTo(ordinate2) > 0 && tmpNode.getLeft() != null) {
                stack.push(tmpNode.getLeft());
            }
            ordinate2 = upperExtreme.getComparableOrdinate(discriminate);
            if(ordinate1.compareTo(ordinate2) < 0 && tmpNode.getRight() != null) {
                stack.push(tmpNode.getRight());
            }
            if(isContained(tmpNode.getPoint(), lowerExtreme, upperExtreme)) {
                results.add(tmpNode.getPoint());
            }
        }
    }
}
