
package org.wewi.medimg.viewer.image;

/**
 * Span is an interval on the integers.  In C-style arrays,
 * "min" is included; "max" is not included.
 * 
 */
final class Span {
    private int min = Integer.MAX_VALUE;
    private int max = Integer.MIN_VALUE;

    public void addPoint(int d) {
        if (getMin() > d) {
            setMin(d);
        }
        if (getMax() < d) {
            setMax(d);
        }
    }

    public void addSpan(Span s) {
        if (getMin() > s.getMin()) { 
            setMin(s.getMin());
        }
        if (getMax() < s.getMax()) { 
            setMax(s.getMax());
        }
    }

    public int length() {
        return getMax() - getMin();
    }

	public void setMin(int min) {
		this.min = min;
	}

	public int getMin() {
		return min;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public int getMax() {
		return max;
	}
}
