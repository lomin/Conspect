package de.itagile.conspect;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import de.itagile.conspect.components.TestComponent;
import de.itagile.conspect.components.TestComponentContract;
import de.itagile.conspect.components.TestParameterContract;

public class ConspectIntegrationTest
{
  private final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("conspect.xml");
  private final TestComponent testComponent = (TestComponent) context.getBean("testComponent");

  @Test
  public void forOneBrokenParameterContract() throws Exception
  {
    try
    {
      testComponent.sqrt(null);
      fail();
    }
    catch (ConspectException e)
    {
      assertTrue(e.getMessage().contains(TestParameterContract.PARAMETER_MUST_NOT_BE_NULL));
    }
  }

  @Test
  public void forTwoBrokenParameterContract() throws Exception
  {
    try
    {
      testComponent.divide(null, null);
      fail();
    }
    catch (ConspectException e)
    {
      assertTrue(e.getMessage().contains(
          TestParameterContract.PARAMETER_MUST_NOT_BE_NULL + "\n2.: "
              + TestParameterContract.PARAMETER_MUST_NOT_BE_NULL));
    }
  }

  @Test
  public void forBrokenComponentContract() throws Exception
  {
    try
    {
      testComponent.divide(new Integer(5), new Integer(0));
      fail();
    }
    catch (ConspectException e)
    {
      assertTrue(e.getMessage().contains(TestComponentContract.DIVISION_BY_ZERO));
    }
  }

  @Test
  public void forBrokenComponentContractLikeIlegalState() throws Exception
  {
    testComponent.divide(new Integer(5), new Integer(1));
    testComponent.setIllegalState(true);
    try
    {
      testComponent.divide(new Integer(5), new Integer(1));
      fail();
    }
    catch (ConspectException e)
    {
      assertTrue(e.getMessage().contains(TestComponentContract.ILLEGAL_STATE));
    }
  }

  @Test
  public void forExceptionWrapping() throws Exception
  {
    try
    {
      testComponent.throwIllegalStateException();
      fail();
    }
    catch (ConspectException e)
    {
      // Excepted
    }
  }
}