package de.itagile.conspect.components;

public class TestComponentDefault implements TestComponent
{
  public boolean illegalState;

  @Override
  public Integer sqrt(Integer integer)
  {
    return new Integer(0);
  }

  @Override
  public Integer divide(Integer object, Integer object2)
  {
    return new Integer(0);
  }

  public boolean isIllegalState()
  {
    return illegalState;
  }

  @Override
  public void setIllegalState(boolean b)
  {
    illegalState = b;
  }

  @Override
  public void throwIllegalStateException()
  {
    throw new IllegalStateException("Should be wrapped");
  }
}