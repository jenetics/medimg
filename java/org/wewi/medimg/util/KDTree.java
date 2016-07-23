/* 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.    See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */

/**
 * KDTree.java
 * 
 * Created on 12.03.2003, 11:04:25
 *
 */
package org.wewi.medimg.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
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
    
    public static final class DoublePoint implements Point, Immutable {
        
        private static final class ComparableDouble implements Comparable {
            private double value = 0;
            public ComparableDouble setValue(double v) {
                value = v;
                return this;
            }
            public int compareTo(Object o) {
                ComparableDouble d = (ComparableDouble)o;
                if (value < d.value) {
                    return -1;
                } else if (value > d.value) {
                    return 1;
                } else {
                    return 0;
                }
            }
        }
        
        private double[] point;
        private ComparableDouble cpoint;
        
        public DoublePoint(double[] p) {
            point = new double[p.length];
            System.arraycopy(p, 0, point, 0, p.length);
            cpoint = new ComparableDouble();
        }
        
        /**
         * @see org.wewi.medimg.util.KDTree.Point#getOrdinate(int)
         */
        public Comparable getOrdinate(int dimension) {
            return cpoint.setValue(point[dimension]);
        }
        /**
         * @see org.wewi.medimg.util.KDTree.Point#getDimensions()
         */
        public int getDimensions() {
            return point.length;
        }
        
        public void getValue(double[] p) {
            System.arraycopy(point, 0, p, 0, point.length);
        }
        
        public boolean equals(Object obj) {
            if (!(obj instanceof DoublePoint)) {
                return false;
            }
            
            DoublePoint p = (DoublePoint)obj;
            for (int i = 0, n = point.length; i < n; i++){
                if (Double.doubleToLongBits(p.point[i]) != Double.doubleToLongBits(point[i])) {
                    return false;
                }
            }
            
            return true;
        }
        
        public String toString() {
            StringBuffer buffer = new StringBuffer();
            buffer.append("(");
            for (int i = 0, n = point.length; i < n; i++) {
                buffer.append(point[i]);
                if (i < n-1) {
                    buffer.append(" ; ");
                }
            }
            buffer.append(")");
            
            return buffer.toString();
        }
    }
    
    
    public static final class IntPoint implements Point, Immutable {
        
        private static final class ComparableInt implements Comparable {
            private int value = 0;
            public ComparableInt setValue(int v) {
                value = v;
                return this;
            }
            public int compareTo(Object o) {
                ComparableInt d = (ComparableInt)o;
                return value - d.value;
            }
        }
        
        private int[] point;
        private ComparableInt cpoint;
        
        public IntPoint(int[] p) {
            point = new int[p.length];
            System.arraycopy(p, 0, point, 0, p.length);
            cpoint = new ComparableInt();
        }
        
        /**
         * @see org.wewi.medimg.util.KDTree.Point#getOrdinate(int)
         */
        public Comparable getOrdinate(int dimension) {
            return cpoint.setValue(point[dimension]);
        }
        /**
         * @see org.wewi.medimg.util.KDTree.Point#getDimensions()
         */
        public int getDimensions() {
            return point.length;
        }
        
        public void getValue(int[] p) {
            System.arraycopy(point, 0, p, 0, point.length);
        }
        
        public boolean equals(Object obj) {
            if (!(obj instanceof IntPoint)) {
                return false;
            }
            
            IntPoint p = (IntPoint)obj;
            for (int i = 0, n = point.length; i < n; i++){
                if (p.point[i] != point[i]) {
                    return false;
                }
            }
            
            return true;
        }        
        
        public String toString() {
            StringBuffer buffer = new StringBuffer();
            buffer.append("(");
            for (int i = 0, n = point.length; i < n; i++) {
                buffer.append(point[i]);
                if (i < n-1) {
                    buffer.append(" ; ");
                }
            }
            buffer.append(")");
            
            return buffer.toString();
        }
    }    
    
    private final static class Node {
        private int discriminate;
        private Point point;
        private Node left, right;
    
        public Node(Point point, int discriminate) {
            this.point = point;
            left  = null;
            right = null;
            this.discriminate = discriminate;
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
            return 1 + left.getNSubtreeNodes() + right.getNSubtreeNodes();
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
    
    private class NodeIterator implements Iterator {
        private Stack stack = new Stack();
        
        public NodeIterator(Node root) {
            if (root != null) {
                stack.push(root);
            }
        }
        
        public boolean hasNext() {
            return !stack.isEmpty();
        }
        
        public Object next() {
            Node node = (Node)stack.pop();
            
            if(node.getLeft() != null) {
                stack.push(node.getLeft());
            }
            if(node.getRight() != null) {
                stack.push(node.getRight());
            }
            
            return node.getPoint();
        }
        
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
    
    /**************************************************************************/
    private Node root;

    public KDTree() { 
        root = null; 
    }
    
    /**
     * Determines if a point is contained within a given
     * k-dimensional bounding box.
     */
    private static final boolean contains(Point point, Point lower, Point upper) {
        Comparable ordinate1, ordinate2, ordinate3;
        int dimensions = point.getDimensions();
        
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
     * Inserting a new Point.
     * 
     * @param point to be insert.
     */
    public boolean add(Point point) { 
        if(root == null) {
            root = new Node(point, 0);
            return true;
        } 
        
        int discriminate, dimensions;
        Node currentNode;
        Node tempNode;
        Comparable ordinate1, ordinate2;
        currentNode = root;
        
        do {
            tempNode = currentNode;
            discriminate = tempNode.getDiscriminate();
            ordinate1 = point.getOrdinate(discriminate);
            ordinate2 = tempNode.getPoint().getOrdinate(discriminate);
            
            if(ordinate1.compareTo(ordinate2) > 0) {
                currentNode = tempNode.getRight();
            } else {
                currentNode = tempNode.getLeft();
            }
        } while(currentNode != null);
        
        dimensions = point.getDimensions();
        
        if(++discriminate >= dimensions) {
            discriminate = 0;
        }
        
        if(ordinate1.compareTo(ordinate2) > 0) {
            tempNode.setRight(new Node(point, discriminate));
        } else {
            tempNode.setLeft(new Node(point, discriminate));
        }
        
        return true;
    }
    
    private void add(Node node) {
        Node n = null;
        for (Iterator it = new NodeIterator(node); it.hasNext();) {
            n = (Node)it.next();
            add(n.getPoint());
        }
    }
    

    /**
     * Removes the first occurence of the specified point from the kd-tree, 
     * if it exists.
     * Returns <code>true</code> if the point could be removed and
     * <code>false</code> if the tree doesn't contain the point
     * 
     * @param point point to be removed.
     * @return boolean <code>true</code> if the point could be removed
     *                 successfully, <code>false</code> otherwise.
     */
    public boolean remove(Point point) {
        Node node = root;
        Node parent = null;
        int dim; 
        while (true) {
            if (node == null) {
                return false;
            }
            if (node.getPoint().equals(point)) {
                Node right = node.getRight();
                Node left = node.getLeft();
                
                if (parent.getRight() == node) {
                    parent.setRight(null);
                } else if (parent.getLeft() == node) {
                    parent.setLeft(null);
                }
                
                add(right);
                add(left);
                
                return true;
            }
            
            parent = node;
            dim = node.getDiscriminate();
            if (node.getPoint().getOrdinate(dim).compareTo(point.getOrdinate(dim)) > 0) {
                node = node.getRight();   
            } else {
                node = node.getLeft();
            }
        }
    }
    
    /**
     * Tests, if the specified point is part of the kd-tree.
     * 
     * @param point Specific point to be test.
     * @return boolean <code>true</code> if this kd-tree contains the
     *                  specified point, <code>false</code> otherwise.
     */
    public boolean contains(Point point) {
        Node node = root;
        int dim = node.getDiscriminate();
        while (true) {
            if (node == null) {
                return false;
            }
            if (node.getPoint().equals(point)) {
                return true;
            }
            
            if (node.getPoint().getOrdinate(dim).compareTo(point.getOrdinate(dim)) > 0) {
                node = node.getRight();   
            } else {
                node = node.getLeft();
            }
        }
    }
    
    /**
     * Returns <code>true</code> if the range (k-dimensional bounding box), 
     * specified by the <code>lower</code>- and <code>upper</code> bound, 
     * contains any point.
     * 
     * @param lower lower bound of the k-dimensional bounding box.
     * @param upper upper bound of the k-dimensional bounding box.
     * @return boolean <code>true</code> if the range contains any point.
     */
    public boolean contains(Point lower, Point upper) {
        return false;
    }
    
    /**
     * Searches the tree for all points contained within a given k-dimensional
     * bounding box and stores them in a collection.
     * 
     * @param lower lower bound of the k-dimensional bounding box.
     * @param upper upper bound of the k-dimensional bounding box.
     * @param results collection with the points found in the bouning box.
     */
    public void getPoints(Point lower, Point upper, Collection results) {
        Node node;
        Stack stack = new Stack();
        int discriminate;
        Comparable ordinate1, ordinate2;
        
        if(root == null) {
            return;
        }
        
        stack.push(root);
        
        while(!stack.empty()) {
            node =(Node)stack.pop();
            discriminate = node.getDiscriminate();
            ordinate1 = node.getPoint().getOrdinate(discriminate);
            ordinate2 = lower.getOrdinate(discriminate);
            
            if(ordinate1.compareTo(ordinate2) > 0 && node.getLeft() != null) {
                stack.push(node.getLeft());
            }
            
            ordinate2 = upper.getOrdinate(discriminate);
            
            if(ordinate1.compareTo(ordinate2) < 0 && node.getRight() != null) {
                stack.push(node.getRight());
            }
            
            if(contains(node.getPoint(), lower, upper)) {
                results.add(node.getPoint());
            }
        }

    }
    
    /**
     * Searches the tree for all points contained within a given k-dimensional
     * bounding box and returns them in a collection.
     * 
     * @param lower lower bound of the k-dimensional bounding box.
     * @param upper upper bound of the k-dimensional bounding box.
     * @return Collection collection with the points found in the bouning box.
     */
    public Collection getPoints(Point lower, Point upper) {
        ArrayList result = new ArrayList();
        getPoints(lower, upper, result);
        return result;
    }
    
    public Iterator rangeIterator(Point lower, Point upper) {
        return getPoints(lower, upper).iterator();
    }
    
    public void clear() {
        root = null;
    }

    public int size() {
        if (root == null) {
            return 0;
        }
        return root.getNSubtreeNodes();
    }
    
    public boolean isEmpty() {
        return (root == null);
    }
  
    public Iterator iterator() {
        return new NodeIterator(root);
    }
    
    public Object[] toArray() {
        Point[] result = new Point[size()];
        
        int count = 0;
        for (Iterator it = iterator(); it.hasNext();) {
            result[count++] = (Point)it.next();
        }
        
        return result;
    }
    
    public Object[] toArray(Object[] a) {
        if (a == null) {   
          return toArray();
        }
        
        int count = 0;
        for (Iterator it = iterator(); it.hasNext();) {
            a[count++] = (Point)it.next();
        }        
        
        return a;
    }
    
    public String toString() {
        if (root != null) {
            return root.toString();
        }
        
        return getClass().getName() + ": Empty tree.";
    }
}
















