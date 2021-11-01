package edu.brown.cs.ta.node;

import edu.brown.cs.ta.stars.Star;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotEquals;

public class TreeNodeTest {
  Star c1;
  Star c2;
  Star c3;
  Star c4;
  Star c5;
  TreeNode<Integer, Star> tn;
  TreeNode<Integer, Star> tn2;
  TreeNode<Integer, Star> tn3;



  @Before
  public void setUp() {
    // creating coordinates
    List<Double> data = new ArrayList<>();
    data.add(0.0);
    data.add(10.0);
    c1 = new Star(1, "s1", data);

    List<Double> data2 = new ArrayList<>();
    data2.add(1.0);
    data2.add(9.0);
    c2 = new Star(2, "s2", data2);

    List<Double> data3 = new ArrayList<>();
    data3.add(2.0);
    data3.add(-1.0);
    c3 = new Star(3, "s3", data3);

    List<Double> data4 = new ArrayList<>();
    data4.add(3.0);
    data4.add(7.0);
    c4 = new Star(4, "s4", data4);

    List<Double> data5 = new ArrayList<>();
    data5.add(4.0);
    data5.add(-5.0);
    c5 = new Star(5, "s5", data5);

    tn = new TreeNode<>(c1, null, null);
    tn2 = new TreeNode<>(c2, tn, null);
    tn3 = new TreeNode<>(c3, tn2, tn);
  }

  @Test
  public void testGetValue() {
    setUp();
    Assert.assertEquals(tn.getValue(), c1);
    Assert.assertEquals(tn2.getValue(), c2);
    Assert.assertEquals(tn3.getValue(), c3);
  }

  @Test
  public void testGetLeft() {
    Assert.assertNull(tn.getLeft());
    Assert.assertEquals(tn2.getLeft(), tn);
  }

  @Test
  public void testGetRight() {
    Assert.assertEquals(tn3.getRight(), tn);
    Assert.assertNull(tn2.getRight());
  }

  @Test
  public void testSetLeftChild() {
    tn.setLeftChild(tn2);
    Assert.assertEquals(tn.getLeft(), tn2);
  }

  @Test
  public void testSetRightChild() {
    tn.setRightChild(tn3);
    Assert.assertEquals(tn.getRight(), tn3);
  }

  @Test
  public void testDistanceTo() {
    Assert.assertEquals(tn.distanceTo(tn2.getValue().getCoordinates()), Math.sqrt(2), 0);
    Assert.assertEquals(tn.distanceTo(tn3.getValue().getCoordinates()), Math.sqrt(125), 0);
  }

  @Test
  public void testToString_RightAndLeftNull() {
    TreeNode<Integer, Star> left = new TreeNode<>(c1, null, null);
    TreeNode<Integer, Star> right = new TreeNode<>(c2, null, null);
    TreeNode<Integer, Star> n = new TreeNode<>(c3, left, right);

    Assert.assertEquals(n.toString(), "TreeNode{value=Coordinate{id=3, coordinates=[2.0, -1.0, ]}, left=TreeNode{value=Coordinate{id=1, coordinates=[0.0, 10.0, ]}, left=NULL, right=NULL}, right=TreeNode{value=Coordinate{id=2, coordinates=[1.0, 9.0, ]}, left=NULL, right=NULL}}");
  }

  @Test
  public void testToString_RightNull() {
    TreeNode<Integer, Star> rightRight = new TreeNode<>(c1, null, null);
    TreeNode<Integer, Star> left = new TreeNode<>(c2, null, null);
    TreeNode<Integer, Star> right = new TreeNode<>(c3, null, rightRight);
    TreeNode<Integer, Star> n = new TreeNode<>(c4, left, right);

    Assert.assertEquals(n.toString(), "TreeNode{value=Coordinate{id=4, coordinates=[3.0, 7.0, ]}, left=TreeNode{value=Coordinate{id=2, coordinates=[1.0, 9.0, ]}, left=NULL, right=NULL}, right=TreeNode{value=Coordinate{id=3, coordinates=[2.0, -1.0, ]}, left=NULL, right=TreeNode{value=Coordinate{id=1, coordinates=[0.0, 10.0, ]}, left=NULL, right=NULL}}}");
  }

  @Test
  public void testToString_LeftNull() {
    TreeNode<Integer, Star> leftLeft = new TreeNode<>(c1, null, null);
    TreeNode<Integer, Star> left = new TreeNode<>(c2, leftLeft, null);
    TreeNode<Integer, Star> right = new TreeNode<>(c3, null, null);
    TreeNode<Integer, Star> n = new TreeNode<>(c4, left, right);

    Assert.assertEquals(n.toString(), "TreeNode{value=Coordinate{id=4, coordinates=[3.0, 7.0, ]}, left=TreeNode{value=Coordinate{id=2, coordinates=[1.0, 9.0, ]}, left=TreeNode{value=Coordinate{id=1, coordinates=[0.0, 10.0, ]}, left=NULL, right=NULL}, right=NULL}, right=TreeNode{value=Coordinate{id=3, coordinates=[2.0, -1.0, ]}, left=NULL, right=NULL}}");
  }

  @Test
  public void testEquals() {
    TreeNode<Integer, Star> left = new TreeNode<>(c1, null, null);
    TreeNode<Integer, Star> right = new TreeNode<>(c2, null, null);
    TreeNode<Integer, Star> n1 = new TreeNode<>(c3, left, right);
    TreeNode<Integer, Star> n2 = new TreeNode<>(c3, left, right);
    TreeNode<Integer, Star> n3 = new TreeNode<>(c4, left, right);

    Assert.assertEquals(n1, n2);
    Assert.assertEquals(n1, n1);
    assertNotEquals(n1, n3);
  }
}