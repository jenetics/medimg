/**
 * Created on 11.09.2002
 *
 */
package org.wewi.medimg.image;

import java.util.EventListener;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public interface VoxelSelectorListener extends EventListener {
    public void voxelSelected(VoxelSelectorEvent event);
}
