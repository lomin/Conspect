package de.itagile.conspect;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class ConspectForComponentsTest
{
  private static final Object[] ARGS = new Object[]
  { "TEST_STRING" };
  private static final boolean NOT_APPLICABLE = false;
  private static final boolean APPLICABLE = true;
  private final IllegalArgumentException componentException = new IllegalArgumentException("test");
  private final ParameterContractHandler parameterContractHandler = mock(ParameterContractHandler.class);
  private final Conspect conspect = new Conspect(parameterContractHandler, null);
  private final String target = "target";
  private final List<ComponentContract> componentContracts = new ArrayList<ComponentContract>();
  private Method method1;

  @Before
  public void setUp() throws SecurityException, NoSuchMethodException
  {
    method1 = TestInterface.class.getMethod("testMethod1", String.class);
    conspect.setComponentContracts(componentContracts);
    conspect.setParameterContracts(new ArrayList<ParameterContract>());
  }

  @Test
  public void doesNothingWhenNoComponentContracts() throws Throwable
  {
    conspect.before(method1, ARGS, target);
  }

  @Test
  public void doesNothingWhenNoComponentContractsBroken() throws Throwable
  {
    componentContracts.add(new ContractNotBroken());
    conspect.before(method1, ARGS, target);
  }

  @Test
  public void doesNothingWhenNoComponentContractsBrokenButNotApplicable() throws Throwable
  {
    componentContracts.add(new ContractNotBroken());
    componentContracts.add(new ContractBroken(NOT_APPLICABLE));

    conspect.before(method1, ARGS, target);
  }

  @Test
  public void throwsComponentExceptionWhenComponentContractsBrokenAndApplicable() throws Throwable
  {
    componentContracts.add(new ContractNotBroken());
    componentContracts.add(new ContractBroken(APPLICABLE));

    try
    {
      conspect.before(method1, ARGS, target);
      fail();
    }
    catch (RuntimeException e)
    {
      assertSame(componentException, e);
    }
  }

  @Test
  public void callsCurryOnComponentContract() throws Throwable
  {
    RuntimeException beforeCurry = new RuntimeException("before");
    RuntimeException afterCurry = new RuntimeException("after");
    componentContracts.add(new ContractBrokenWithDifferentExceptionAfterCurry(beforeCurry, afterCurry));

    try
    {
      conspect.before(method1, ARGS, target);
      fail();
    }
    catch (RuntimeException e)
    {
      assertSame(afterCurry, e);
    }
  }

  private interface TestInterface
  {
    public String testMethod1(String string);
  }

  private class ContractNotBroken implements TestInterface, ComponentContract
  {

    @Override
    public String testMethod1(String string)
    {
      return null;
    }

    @Override
    public boolean isApplicable(Object target)
    {
      return false;
    }

    @Override
    public ComponentContract curry(Object target)
    {
      return null;
    }
  }

  private class ContractBroken implements TestInterface, ComponentContract
  {
    private final boolean applicable;

    public ContractBroken(boolean applicable)
    {
      this.applicable = applicable;
    }

    @Override
    public String testMethod1(String string)
    {
      throw componentException;
    }

    @Override
    public boolean isApplicable(Object target)
    {
      return applicable;
    }

    @Override
    public ComponentContract curry(Object target)
    {
      return this;
    }
  }

  private class ContractBrokenWithDifferentExceptionAfterCurry implements TestInterface, ComponentContract
  {
    private final RuntimeException beforeCurry;
    private final RuntimeException afterCurry;

    public ContractBrokenWithDifferentExceptionAfterCurry(RuntimeException beforeCurry, RuntimeException afterCurry)
    {
      this.beforeCurry = beforeCurry;
      this.afterCurry = afterCurry;
    }

    @Override
    public String testMethod1(String string)
    {
      throw beforeCurry;
    }

    @Override
    public ComponentContract curry(Object t)
    {
      assertEquals(target, t);
      return new ContractBrokenWithDifferentExceptionAfterCurry(afterCurry, null);
    }

    @Override
    public boolean isApplicable(Object target)
    {
      return true;
    }
  }
}