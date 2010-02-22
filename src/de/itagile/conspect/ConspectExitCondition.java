package de.itagile.conspect;

import java.lang.reflect.Method;

/**
 * This exit condition is true, if the exception is marked by the {@link ConspectExceptionMarker}.
 */
public class ConspectExitCondition implements ExitCondition
{
  @Override
  public boolean isTrue(Method method, Object[] args, Object target, Exception exception)
  {
    return exception instanceof ConspectExceptionMarker;
  }
}