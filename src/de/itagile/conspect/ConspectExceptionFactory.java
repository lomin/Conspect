package de.itagile.conspect;

import java.util.Iterator;
import java.util.List;

public class ConspectExceptionFactory implements ExceptionFactory
{
  @Override
  public RuntimeException create(List<String> messages)
  {
    StringBuffer buffer = new StringBuffer();
    int i = 1;
    for (Iterator<String> iterator = messages.iterator(); iterator.hasNext();)
    {
      String message = iterator.next();
      buffer.append(i++);
      buffer.append(".: ");
      buffer.append(message);
      if (iterator.hasNext())
      {
        buffer.append("\n");
      }
    }
    return new ConspectException(buffer.toString());
  }
}