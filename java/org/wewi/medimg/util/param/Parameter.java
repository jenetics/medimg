/**
 * Created on 07.10.2002 18:28:06
 *
 */
package org.wewi.medimg.util.param;

import java.util.List;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public interface Parameter {
    public void setParameter(Parameter parameter);
    
    public void setParameter(List parameterList);
    
    public List getParameterList();    
    
    public String getName();
    
    public void setName(String name);
    
    public String getClassName();
    
    public String getValue();
    
    public void setValue(String value);
    
    public Object getInstance();
}
