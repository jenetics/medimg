/*
 * ComponentPrinter.java
 *
 * Created on 02. Februar 2002, 13:41
 */

package org.wewi.medimg.util;

import java.awt.Component;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public abstract class ComponentPrinter {
    protected Component component;

    public ComponentPrinter(Component component) {
        this.component = component;
    }

    public abstract void print(File file) throws IOException;
}
