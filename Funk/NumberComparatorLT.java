package Funk;

import java.util.*;

public class NumberComparatorLT extends NumberComparator {

  public NumberComparatorLT(Number value) {
    super(value);
  };

  public boolean compare (Object raw) {
    try {
      return get_value(raw) < comparator_value;
    } catch (NumberFormatException ex) {
      return false;
    }
  };

}