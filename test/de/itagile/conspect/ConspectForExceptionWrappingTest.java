package de.itagile.conspect;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class ConspectForExceptionWrappingTest
{
  private final ExitCondition exitCondition = mock(ExitCondition.class);
  private final Conspect conspect = new Conspect(null, exitCondition);
  private final Method method = null;
  private final Object[] args = new Object[]
  {};
  private final Object target = null;
  private final Exception exception = new RuntimeException();
  private final List<ExceptionWrapper> exceptionWrapper = new ArrayList<ExceptionWrapper>();
  private final ExceptionWrapper wrapper = mock(ExceptionWrapper.class);
  private final RuntimeException wrappedException = new RuntimeException("wrapped");
  private final ExceptionWrapper wrapper2 = mock(ExceptionWrapper.class);

  @Before
  public void setUp()
  {
    exceptionWrapper.add(wrapper);
    when(wrapper.isApplicable(target)).thenReturn(false);
  }

  @Test
  public void doesNothingIfExitConditionIsTrue() throws Exception
  {
    when(exitCondition.isTrue(method, args, target, exception)).thenReturn(true);

    conspect.afterThrowing(method, args, target, exception);
  }

  @Test
  public void doesNothingWhenNoExceptionWrapper() throws Exception
  {
    when(exitCondition.isTrue(method, args, target, exception)).thenReturn(false);

    conspect.afterThrowing(method, args, target, exception);
  }

  @Test
  public void doesNothingIfExceptionWrapperIsNotApplicable() throws Exception
  {
    when(exitCondition.isTrue(method, args, target, exception)).thenReturn(false);

    conspect.setExceptionWrapper(exceptionWrapper);

    conspect.afterThrowing(method, args, target, exception);
  }

  @Test
  public void WrapesException() throws Exception
  {
    when(exitCondition.isTrue(method, args, target, exception)).thenReturn(false);
    when(wrapper2.isApplicable(target)).thenReturn(true);
    when(wrapper2.wrap(exception)).thenReturn(wrappedException);

    exceptionWrapper.add(wrapper2);
    conspect.setExceptionWrapper(exceptionWrapper);

    try
    {
      conspect.afterThrowing(method, args, target, exception);
      fail();
    }
    catch (RuntimeException e)
    {
      assertSame(wrappedException, e);
    }
  }
}