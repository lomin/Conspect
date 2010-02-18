package de.itagile.conspect;

import java.lang.reflect.Method;

public class ConspectExitCondition implements ExitCondition
{
  @Override
  public boolean isTrue(Method method, Object[] args, Object target, Exception exception)
  {
    return exception instanceof ConspectExceptionMarker;
  }
}