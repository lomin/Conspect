package de.itagile.conspect;

import java.lang.reflect.Method;

public interface ExitCondition
{
  boolean isTrue(Method method, Object[] args, Object target, Exception exception);
}