package de.itagile.conspect;

public interface ConspectContract
{
  /**
   * @param object either the target or the parameter of a component call
   * @return <tt>true</tt> if the contract can be applied to the target or the argument
   */
  boolean isApplicable(Object object);
}