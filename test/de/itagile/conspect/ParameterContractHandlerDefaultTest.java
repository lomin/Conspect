package de.itagile.conspect;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class ParameterContractHandlerDefaultTest
{
  private final ExceptionFactory exceptionFactory = mock(ExceptionFactory.class);
  private final ParameterContractHandler handler = new ConspectParameterContractHandler(exceptionFactory);
  private final List<ComponentContract> list = new ArrayList<ComponentContract>();
  private final String target = "target";
  private final ComponentContract componentContract = mock(ComponentContract.class);

  @Test
  public void doesNothingWhenWithoutResults() throws Exception
  {
    handler.handle(target);
  }

  @Test
  public void doesNothingWhenWithoutNegativeResults() throws Exception
  {
    handler.add(new ContractResultPositive());
    handler.handle(target);
  }

  @Test
  public void throwsComponentExceptionWhenWithNegativeResultsAndReferenceComponentContract() throws Exception
  {
    List<String> messages = new ArrayList<String>();
    messages.add("message1");
    messages.add("message2");
    messages.add("message3");
    messages.add("message4");

    ContractResult result1 = new ContractResultNegative();
    result1.addMessage("message1");
    result1.addMessage("message2");
    ContractResult result2 = new ContractResultNegative();
    result2.addMessage("message3");
    result2.addMessage("message4");
    handler.add(result1);
    handler.add(result2);

    IllegalArgumentException componentException = new IllegalArgumentException("test");
    when(exceptionFactory.create(messages)).thenReturn(componentException);

    list.add(componentContract);

    try
    {
      handler.handle(target);
      fail();
    }
    catch (IllegalArgumentException e)
    {
      assertSame(componentException, e);
    }
  }

  @Test
  public void doesResetAfterThrowing() throws Exception
  {
    handler.add(new ContractResultNegative("message"));

    when(exceptionFactory.create(anyListOf(String.class))).thenReturn(new RuntimeException());

    try
    {
      handler.handle(target);
      fail();
    }
    catch (RuntimeException e)
    {
      // expected
    }

    // does nothing when without results
    handler.handle(target);
  }
}