package org.wewi.medimg.util;


final class KDNode {
    private int discriminate;
    private int nsubtreenodes;
    private KDTreePoint point;
    private KDNode left, right;
    
    public KDNode(KDTreePoint point, int discriminate) {
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
    
    public KDTreePoint getPoint() {
        return point;
    }
    
    public KDNode getLeft() {
        return left;
    }
    
    public void setLeft(KDNode left) {
        this.left = left;
    }
    
    public KDNode getRight() {
        return right;
    }
    
    public void setRight(KDNode right) {
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