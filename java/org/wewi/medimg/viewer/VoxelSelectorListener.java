/**
 * Created on 11.09.2002
 *
 */
package org.wewi.medimg.viewer;

import java.util.EventListener;

import org.wewi.medimg.viewer.*;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public interface VoxelSelectorListener extends EventListener {
    public void voxelSelected(VoxelSelectorEvent event);
}
