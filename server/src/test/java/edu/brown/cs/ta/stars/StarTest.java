package edu.brown.cs.ta.stars;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class StarTest {
  @Test
  public void testGetters() {
    Star star = new Star(0, "Sagittarius A*", 2.22222222222, -22/39, 0);

    assertEquals(0, (int) star.getId());
    assertEquals("Sagittarius A*", star.getName());
    assertEquals(2.22222222222, star.getX(), 0.0);
    assertEquals(star.getY(), -22/39, 0.0);
    assertEquals(0, star.getZ(), 0.0);
  }

  @Test
  public void testEquals() {
    Star star1 = new Star(0, "Sagittarius A*", 2.22222222222, -22/39, 0);
    Star star2 = new Star(0, "Sagittarius A*", 2.22222222222, -22/39, 0);
    Star star3 = new Star(0, "Sagittarius A*", 2.2222222222, -22/39, 0);

    assertEquals(star1, star2);
    assertEquals(star1, star1);
    assertNotEquals(star1, star3);
  }
}
