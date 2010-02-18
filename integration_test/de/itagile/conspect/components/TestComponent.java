package de.itagile.conspect.components;

public interface TestComponent
{
  Integer sqrt(Integer integer);

  Integer divide(Integer dividend, Integer divisor);

  boolean isIllegalState();
  
  void setIllegalState(boolean b);

  void throwIllegalStateException();
}