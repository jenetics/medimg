/**
 * Created on 10.09.2002
 *
 */
package org.wewi.medimg.seg.kmeans;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 *
 */
public interface DataPointClusterer {
    public DataPointCollection[] cluster(DataPointCollection data);
}
