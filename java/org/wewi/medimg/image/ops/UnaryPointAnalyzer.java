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
 * Created on 13.11.2002 19:57:15
 *
 */
package org.wewi.medimg.image.ops;

import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.ROI;
import org.wewi.medimg.image.VoxelIterator;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public final class UnaryPointAnalyzer {
    private Image image;
    private UnaryOperator operator;

    /**
     * Constructor for UnaryPointAnalyzer.
     */
    public UnaryPointAnalyzer(Image image, UnaryOperator operator) {
        super();
        this.image = image;
        this.operator = operator;
    }
    
    public void analyze() {
        for (VoxelIterator it = image.getVoxelIterator(); it.hasNext();) {
            operator.process(it.next());       
        }    
    }
    
    public void analyze(ROI roi) {
        ImageLoop loop = new ImageLoop(image, new UnaryOperatorTaskAdapter(operator));
        loop.loop(roi);
    }
    
    public Image getImage() {
        return image;    
    }
    
    public UnaryOperator getOperator() {
        return operator;    
    }

}
