package de.itagile.conspect;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class ConspectExceptionFactoryTest
{
  private final ConspectExceptionFactory factory = new ConspectExceptionFactory();
  private final List<String> messages = new ArrayList<String>();

  @Test
  public void createRuntimeExceptionsWithMessages() throws Exception
  {
    messages.add("message1");
    messages.add("message2");
    RuntimeException exception = factory.create(messages);
    assertEquals("1.: message1\n2.: message2", exception.getMessage());
  }
}