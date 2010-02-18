package de.itagile.conspect.components;

import de.itagile.conspect.ComponentContract;
import de.itagile.conspect.ConspectException;

public class TestComponentContract implements TestComponent, ComponentContract
{
  public static final String DIVISION_BY_ZERO = "Divisor must not be 0";
  public static final String ILLEGAL_STATE = "Component in illegal state";

  private TestComponent target = null;

  public TestComponentContract()
  {
  }

  public TestComponentContract(TestComponent target)
  {
    this.target = target;
  }

  @Override
  public Integer divide(Integer dividend, Integer divisor)
  {
    if (new Integer(0).equals(divisor))
    {
      throw new ConspectException(TestComponentContract.DIVISION_BY_ZERO);
    }

    if (target.isIllegalState())
    {
      throw new ConspectException(TestComponentContract.ILLEGAL_STATE);
    }

    return null;
  }

  @Override
  public ComponentContract curry(Object target)
  {
    return new TestComponentContract((TestComponent) target);
  }

  @Override
  public boolean isApplicable(Object target)
  {
    return target instanceof TestComponent;
  }

  @Override
  public Integer sqrt(Integer integer)
  {
    return null;
  }

  @Override
  public void setIllegalState(boolean b)
  {
  }

  @Override
  public boolean isIllegalState()
  {
    return false;
  }

  @Override
  public void throwIllegalStateException()
  {
  }
}