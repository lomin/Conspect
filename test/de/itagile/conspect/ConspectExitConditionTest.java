package de.itagile.conspect;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ConspectExitConditionTest
{
  private final ConspectExitCondition exitCondition = new ConspectExitCondition();

  @Test
  public void isFalseWhenExceptionIsNotMarked() throws Exception
  {
    assertFalse(exitCondition.isTrue(null, null, null, new RuntimeException("not marked")));
  }

  @Test
  public void isTrueWhenExceptionIsMarked() throws Exception
  {
    assertTrue(exitCondition.isTrue(null, null, null, new TestException()));
  }

  private class TestException extends RuntimeException implements ConspectExceptionMarker
  {
    private static final long serialVersionUID = 1L;
  }
}
