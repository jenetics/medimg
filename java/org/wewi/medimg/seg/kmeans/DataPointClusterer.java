/**
 * Created on 10.09.2002
 *
 */
package org.wewi.medimg.seg.kmeans;

/**
 * @author Franz Wilhelmst�tter
 * @version 0.1
 *
 */
public interface DataPointClusterer {
    public DataPointCollection[] cluster(DataPointCollection data);
}
