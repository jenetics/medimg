/**
 * Created on 22.10.2002 20:26:54
 *
 */
package org.wewi.medimg.image;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public final class ROI extends Dimension {

    public ROI(int sizeX, int sizeY, int sizeZ) {
        super(sizeX, sizeY, sizeZ);
    }

    /**
     * Constructor for ROI.
     * @param minX
     * @param maxX
     * @param minY
     * @param maxY
     * @param minZ
     * @param maxZ
     * @throws IllegalArgumentException
     */
    public ROI(int minX, int maxX, int minY, int maxY, int minZ, int maxZ) {
        super(minX, maxX, minY, maxY, minZ, maxZ);
    }

    public static ROI create(Dimension dim) {
        return new ROI(
            dim.getMinX(),
            dim.getMaxX(),
            dim.getMinY(),
            dim.getMaxY(),
            dim.getMinZ(),
            dim.getMaxZ());
    }

    public static ROI create(
        Dimension dim,
        int marginX,
        int marginY,
        int marginZ) {
        return new ROI(
            dim.getMinX() + marginX,
            dim.getMaxX() - marginX,
            dim.getMinY() + marginY,
            dim.getMaxY() - marginY,
            dim.getMinZ() + marginZ,
            dim.getMaxZ() - marginZ);
    }

    public static ROI create(Dimension dim, int margin) {
        return create(dim, margin, margin, margin);
    }

    /**
     * Creates a <code>ROI</code>-object with the center <code>(x, y, z)</code> and
     * the margins <code>marginX, marginY, marginZ</code>.
     *
     * @param x center x-coordinate.
     * @param y center y-coordinate.
     * @param z center z-coordinate.
     * @param marginX margin in x-direction.
     * @param marginY margin in y-direction.
     * @param marginZ margin in z-direction.
     *
     * @return <code>ROI</with> the given parameters.
     */
    public static ROI create(int x, int y, int z, int marginX, int marginY, int marginZ) {
        return new ROI(
            x - marginX,
            x + marginX,
            y - marginY,
            y + marginY,
            z - marginZ,
            z + marginZ);
    }

    public static ROI create(int x, int y, int z, int margin) {
        return create(x, y, z, margin, margin, margin);
    }

    /**
     * Joins <code>this</code> with <code>b</code>
     *
     */
    public ROI join(ROI b) {
        int minX = Math.min(getMinX(), b.getMinX());
        int maxX = Math.max(getMaxX(), b.getMaxX());
        int minY = Math.min(getMinY(), b.getMinY());
        int maxY = Math.max(getMaxY(), b.getMaxY());
        int minZ = Math.min(getMinZ(), b.getMinZ());
        int maxZ = Math.max(getMaxZ(), b.getMaxZ());

        return new ROI(minX, maxX, minY, maxY, minZ, maxZ);
    }

    /**
     * Intersects <code>this</code> with <code>b</code>
     *
     */
    public ROI intersect(ROI b) {
        int minX = Math.max(getMinX(), b.getMinX());
        int maxX = Math.min(getMaxX(), b.getMaxX());
        int minY = Math.max(getMinY(), b.getMinY());
        int maxY = Math.min(getMaxY(), b.getMaxY());
        int minZ = Math.max(getMinZ(), b.getMinZ());
        int maxZ = Math.min(getMaxZ(), b.getMaxZ());

        if (minX > maxX || minY > maxY || minZ > maxZ) {
            return new ROI(minX, minX, minY, minY, minZ, minZ);
        }

        return new ROI(minX, maxX, minY, maxY, minZ, maxZ);
    }

    public int size() {
        return getSizeX() * getSizeY() * getSizeZ();
    }

}
