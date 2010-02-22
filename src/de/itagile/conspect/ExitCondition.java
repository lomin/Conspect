package de.itagile.conspect;

import java.lang.reflect.Method;

/**
 * If an exit condition is true, the exception will not be wrapped. If it is false, a matching wrapper will 
 * be searched and called to wrap the exception. 
 */
public interface ExitCondition
{
  boolean isTrue(Method method, Object[] args, Object target, Exception exception);
}