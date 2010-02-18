package de.itagile.conspect;

import java.util.ArrayList;
import java.util.List;

public class AbstractContractResult
{
  private final List<String> messages = new ArrayList<String>();

  public AbstractContractResult()
  {
    super();
  }

  public void addMessage(String message)
  {
    messages.add(message);
  }

  public List<String> getMessages()
  {
    return messages;
  }

  @Override
  public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((messages == null) ? 0 : messages.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj)
  {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    AbstractContractResult other = (AbstractContractResult) obj;
    if (messages == null)
    {
      if (other.messages != null)
        return false;
    }
    else if (!messages.equals(other.messages))
      return false;
    return true;
  }
}