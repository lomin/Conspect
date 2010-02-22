package de.itagile.conspect;

/**
 * Implementations of this interface are called to check the contract of components for which they 
 * are applicable.
 */
public interface ComponentContract extends ConspectContract
{
  /**
   * This method can be used to curry the <tt>ComponentContract</tt> to check a component's state.
   * 
   * @param target the target component
   * @return a valid ComponentContract
   */
  ComponentContract curry(Object target);
}