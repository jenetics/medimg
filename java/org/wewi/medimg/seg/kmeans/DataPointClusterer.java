/**
 * Created on 10.09.2002
 *
 */
package org.wewi.medimg.seg.kmeans;

import java.util.Collection;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 *
 */
public interface DataPointClusterer {
    public Collection[] cluster();
}
