package edu.brown.cs.ta.coordinates;

import edu.brown.cs.ta.node.TreeNode;
import edu.brown.cs.ta.stars.Star;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class KdTreeTest {

    List<Double> data = new ArrayList<>();
    List<Double> data2 = new ArrayList<>();
    List<Double> data3 = new ArrayList<>();
    List<Double> data4 = new ArrayList<>();
    List<Double> data5 = new ArrayList<>();
    List<Double> data6 = new ArrayList<>();
    List<Double> data7 = new ArrayList<>();
    List<Double> data8 = new ArrayList<>();
    List<Double> data9 = new ArrayList<>();
    List<Double> data10 = new ArrayList<>();
    List<Double> data11 = new ArrayList<>();

    Coordinate<Integer> c1;
    Coordinate<Integer> c2;
    Coordinate<Integer> c3;
    Coordinate<Integer> c4;
    Coordinate<Integer> c5;
    Coordinate<Integer> c6;
    Coordinate<Integer> c7;
    Coordinate<Integer> c8;
    Coordinate<Integer> c9;
    Coordinate<Integer> c10;
    Coordinate<Integer> c11;

    ArrayList<Coordinate<Integer>> allCoords = new ArrayList<>();

    @Before
    public void setUp() {
        data.add(0.0);
        data.add(10.0);
        c1 = new Star(1, "star1", data);

        data2.add(1.0);
        data2.add(9.0);
        c2 = new Star(2, "star2", data2);

        data3.add(2.0);
        data3.add(-1.0);
        c3 = new Star(3, "star3", data3);

        data4.add(3.0);
        data4.add(7.0);
        c4 = new Star(4, "star4", data4);

        data5.add(4.0);
        data5.add(-5.0);
        c5 = new Star(5, "star5", data5);

        data6.add(5.0);
        data6.add(5.0);
        c6 = new Star(6, "star6", data6);

        data7.add(6.0);
        data7.add(4.0);
        c7 = new Star(7, "star7", data7);

        data8.add(7.0);
        data8.add(-3.0);
        c8 = new Star(8, "star8", data8);

        data9.add(8.0);
        data9.add(2.0);
        c9 = new Star(9, "star9", data9);

        data10.add(9.0);
        data10.add(-1.0);
        c10 = new Star(10, "star10", data10);

        data11.add(10.0);
        data11.add(0.0);
        c11 = new Star(11, "star11", data11);

        allCoords.add(c1);
        allCoords.add(c2);
        allCoords.add(c3);
        allCoords.add(c4);
        allCoords.add(c5);
        allCoords.add(c6);
        allCoords.add(c7);
        allCoords.add(c8);
        allCoords.add(c9);
        allCoords.add(c10);
        allCoords.add(c11);
    }


    @Test
    public void testAddNode() {
        ArrayList<Star> stars = new ArrayList<>();
        List<Double> coors = new ArrayList<>(Arrays.asList(1.0, 2.0, 3.0));
        stars.add(new Star(1, "star1", coors));

        KdTree<Integer, Star> test = new KdTree<>(3, stars);

        //if current node is null
        Star s1 = new Star(1, "star1", coors);
        TreeNode<Integer, Star> testNode = test.addNode(0, null, s1);
        Star testStar = testNode.getValue();
        Assert.assertEquals(testStar, s1);

        //two nodes
        Star s2 = new Star(2, "stars2", coors);
        TreeNode<Integer, Star> testNotNull = test.addNode(0, testNode, s2);
        Star testStarNotNull = testNotNull.getRight().getValue();
        Assert.assertEquals(testStarNotNull, s2);

        //three nodes
        Star s3 = new Star(3, "stars3", new ArrayList<>(Arrays.asList(1.0, 0.0, 0.0)));
        TreeNode<Integer, Star> test3 = test.addNode(0, testNotNull, s3);
        Star testStar3 = test3.getRight().getLeft().getValue();
        Assert.assertEquals(testStar3, s3);
    }

    @Test
    public void testBuildTree() {
        // creating coordinates
        List<Double> data = new ArrayList<>();
        data.add(0.0);
        data.add(10.0);
        Coordinate<Integer> c1 = new Star(1, "s1", data);

        List<Double> data2 = new ArrayList<>();
        data2.add(1.0);
        data2.add(9.0);
        Coordinate<Integer> c2 = new Star(2, "s2", data2);

        List<Double> data3 = new ArrayList<>();
        data3.add(2.0);
        data3.add(-1.0);
        Coordinate<Integer> c3 = new Star(3, "s3", data3);

        List<Double> data4 = new ArrayList<>();
        data4.add(3.0);
        data4.add(7.0);
        Coordinate<Integer> c4 = new Star(4, "s4", data4);

        List<Double> data5 = new ArrayList<>();
        data5.add(4.0);
        data5.add(-5.0);
        Coordinate<Integer> c5 = new Star(5, "s5", data5);

        List<Double> data6 = new ArrayList<>();
        data6.add(5.0);
        data6.add(5.0);
        Coordinate<Integer> c6 = new Star(6, "s6", data6);

        List<Double> data7 = new ArrayList<>();
        data7.add(6.0);
        data7.add(4.0);
        Coordinate<Integer> c7 = new Star(7, "s7", data7);

        List<Double> data8 = new ArrayList<>();
        data8.add(7.0);
        data8.add(-3.0);
        Coordinate<Integer> c8 = new Star(8, "s8", data8);

        List<Double> data9 = new ArrayList<>();
        data9.add(8.0);
        data9.add(2.0);
        Coordinate<Integer> c9 = new Star(9, "s9", data9);

        List<Double> data10 = new ArrayList<>();
        data10.add(9.0);
        data10.add(-1.0);
        Coordinate<Integer> c10 = new Star(10, "s10", data10);

        List<Double> data11 = new ArrayList<>();
        data11.add(10.0);
        data11.add(0.0);
        Coordinate<Integer> c11 = new Star(11, "s11", data11);

        // creating list of coordinates
        ArrayList<Coordinate<Integer>> allCoords = new ArrayList<>();
        allCoords.add(c1);
        allCoords.add(c2);
        allCoords.add(c3);
        allCoords.add(c4);
        allCoords.add(c5);
        allCoords.add(c6);
        allCoords.add(c7);
        allCoords.add(c8);
        allCoords.add(c9);
        allCoords.add(c10);
        allCoords.add(c11);

        // loading tree
        KdTree<Integer, Coordinate<Integer>> tree = new KdTree<>(2, allCoords);
        tree.buildTree();
        TreeNode<Integer, Coordinate<Integer>> root = tree.getRoot();

        // checking tree
        Assert.assertEquals(root.getValue().getId(), 6, 0);

        Assert.assertEquals(root.getLeft().getValue().getId(), 3, 0);
        Assert.assertEquals(root.getRight().getValue().getId(), 9, 0);

        Assert.assertEquals(root.getLeft().getLeft().getValue().getId(), 5, 0);
        Assert.assertEquals(root.getLeft().getRight().getValue().getId(), 2, 0);

        Assert.assertEquals(root.getLeft().getRight().getLeft().getValue().getId(), 1, 0);
        Assert.assertEquals(root.getLeft().getRight().getRight().getValue().getId(), 4, 0);

        Assert.assertEquals(root.getRight().getLeft().getValue().getId(), 8, 0);
        Assert.assertEquals(root.getRight().getRight().getValue().getId(), 7, 0);

        Assert.assertEquals(root.getRight().getLeft().getRight().getValue().getId(), 11, 0);
        Assert.assertNull(root.getRight().getLeft().getLeft());

        Assert.assertNull(root.getRight().getRight().getLeft());
    }

    @Test
    public void testBuildTree_InconsistentDimensions() {
        List<Double> data12 = new ArrayList<>();
        data12.add(100.0);
        Coordinate<Integer> c12 = new Star(1, "star1", data12);
        allCoords.add(c12);

        KdTree<Integer, Coordinate<Integer>> tree = new KdTree<>(2, allCoords);

        assertThrows(IllegalStateException.class, tree::buildTree);
    }


    @Test
    public void testAddAll() {
        ArrayList<Star> stars = new ArrayList<>();
        List<Double> coors = new ArrayList<>(Arrays.asList(1.0, 2.0, 3.0));
        Star s1 = new Star(1, "star1", coors);
        stars.add(s1);

        //one node
        KdTree<Integer, Star> test = new KdTree<>(3, stars);
        TreeNode<Integer, Star> testRoot = test.addAll(0, null, new ArrayList<>(stars));
        Star testStar = testRoot.getValue();
        Assert.assertEquals(testStar, s1);

        //three nodes
        Star s2 = new Star(2, "star2",
            new ArrayList<>(Arrays.asList(3.0, 2.0, 3.0)));
        stars.add(s2);
        Star s3 = new Star(3, "star3",
            new ArrayList<>(Arrays.asList(0.0, 2.0, 3.0)));
        stars.add(s3);
        testRoot = test.addAll(0, null, new ArrayList<>(stars));
        Star testStarleft = testRoot.getLeft().getValue();
        Star testStarRight = testRoot.getRight().getValue();
        Assert.assertEquals(testStarleft, s3);
        Assert.assertEquals(testStarRight, s2);

        //four nodes
        Star s4 = new Star(4, "star4",
            new ArrayList<>(Arrays.asList(4.0, 4.0, 3.0)));
        stars.add(s4);
        testRoot = test.addAll(0, null, new ArrayList<>(stars));
        Star star3 = testRoot.getLeft().getRight().getValue();
        Star star4 = testRoot.getRight().getValue();
        Assert.assertEquals(star3, s3);
        Assert.assertEquals(star4, s4);
    }

    @Test
    public void testUpdateQueue() {
        List<Double> target = new ArrayList<>(Arrays.asList(0.0, 0.0, 0.0));
        Star s2 = new Star(2, "star2",
            new ArrayList<>(Arrays.asList(3.0, 2.0, 3.0)));
        Star s3 = new Star(3, "star3",
            new ArrayList<>(Arrays.asList(0.0, 2.0, 3.0)));
        ArrayList<Star> stars = new ArrayList<>();
        stars.add(s2);
        stars.add(s3);
        KdTree<Integer, Star> test = new KdTree<>(3, stars);
        TreeNode<Integer, Star> current = new TreeNode<>(s2, null, null);

        //already full
        Comparator<TreeNode<Integer, Star>> sortByDist = Comparator.comparing(TreeNode -> TreeNode.distanceTo(target));
        PriorityQueue<TreeNode<Integer, Star>> pq = new PriorityQueue<>(1, sortByDist);
        pq.offer(current);
        TreeNode<Integer, Star> newN = new TreeNode<>(s3, null, null);
        pq.poll();
        PriorityQueue<TreeNode<Integer, Star>> result = test.updateQueue(target, newN, pq, 1);
        Star popped = result.peek().getValue();
        Assert.assertEquals(popped, s3);

        //empty pq
        pq = new PriorityQueue<>(1, sortByDist);
        result = test.updateQueue(target, current, pq, 1);
        popped = result.peek().getValue();
        Assert.assertEquals(popped, s2);
    }

    @Test
    public void testGetRoot() {
        ArrayList<Star> stars = new ArrayList<>();
        stars.add(new Star(2, "star2",
            new ArrayList<>(Arrays.asList(0.0, 5.0, 3.0))));
        stars.add(new Star(3, "star3",
            new ArrayList<>(Arrays.asList(6.0, 2.0, 3.0))));
        stars.add(new Star(4, "star4",
            new ArrayList<>(Arrays.asList(4.0, 1.0, 6.0))));
        stars.add(new Star(5, "star5",
            new ArrayList<>(Arrays.asList(4.0, 6.0, 3.0))));
        stars.add(new Star(6, "star6",
            new ArrayList<>(Arrays.asList(6.0, 2.0, 2.0))));
        stars.add(new Star(7, "star7",
            new ArrayList<>(Arrays.asList(7.0, 0.0, 5.0))));
        Star s8 = new Star(8, "star2",
            new ArrayList<>(Arrays.asList(5.0, 4.0, 3.0)));
        stars.add(s8);

        KdTree<Integer, Star> test = new KdTree<>(3, stars);
        test.buildTree();
        Assert.assertEquals(test.getRoot().getValue(), s8);
    }

    @Test
    public void testGetNearestNeighborsResult_StarName() {
        // load tree
        KdTree<Integer, Coordinate<Integer>> tree = new KdTree<>(2, allCoords);
        tree.buildTree();

        int k = 5;
        Coordinate<Integer> targetPoint = c8;

        // map over nearest stars to get nearest indices
        List<Integer> kNearestNeighborIndices
            = tree.getNearestNeighborsResult(k, targetPoint, true)
            .stream().map(Coordinate::getId).collect(Collectors.toList());

        List<Integer> expectedIndices = new ArrayList<>();
        expectedIndices.add(10);
        expectedIndices.add(5);
        expectedIndices.add(11);
        expectedIndices.add(9);
        expectedIndices.add(3);

        Assert.assertEquals(kNearestNeighborIndices, expectedIndices);
    }

    @Test
    public void testGetNearestNeighborsResult_NoStarName() {
        // load tree
        KdTree<Integer, Coordinate<Integer>> tree = new KdTree<>(2, allCoords);
        tree.buildTree();

        int k = 5;
        Coordinate<Integer> targetPoint = c8;

        // map over nearest stars to get nearest indices
        List<Integer> kNearestNeighborsIndices
            = tree.getNearestNeighborsResult(k, targetPoint, false)
            .stream().map(Coordinate::getId).collect(Collectors.toList());

        List<Integer> expectedIndices = new ArrayList<>();
        expectedIndices.add(8);
        expectedIndices.add(10);
        expectedIndices.add(5);
        expectedIndices.add(11);
        expectedIndices.add(9);

        Assert.assertEquals(kNearestNeighborsIndices, expectedIndices);
    }

    @Test
    public void testGetRadiusSearchResult_StarName() {
        // load tree
        KdTree<Integer, Coordinate<Integer>> tree = new KdTree<>(2, allCoords);
        tree.buildTree();

        double r = 3.423;
        Coordinate<Integer> targetPoint = c8;


        // map over nearest stars to get nearest indices
        List<Integer> kNearestNeighborsIndices
            = tree.getRadiusSearchResult(r, targetPoint, true)
            .stream().map(Coordinate::getId).collect(Collectors.toList());


        List<Integer> expectedIndices = new ArrayList<>();
        expectedIndices.add(10);

        Assert.assertEquals(kNearestNeighborsIndices, expectedIndices);
    }

    @Test
    public void testGetRadiusSearchResult_NoStarName() {
        // load tree
        KdTree<Integer, Coordinate<Integer>> tree = new KdTree<>(2, allCoords);
        tree.buildTree();

        double r = 3.484;
        Coordinate<Integer> targetPoint = c8;

        // map over nearest stars to get nearest indices
        List<Integer> kNearestNeighborsIndices
            = tree.getRadiusSearchResult(r, targetPoint, false)
            .stream().map(Coordinate::getId).collect(Collectors.toList());


        List<Integer> expectedIndices = new ArrayList<>();
        expectedIndices.add(8);
        expectedIndices.add(10);

        Assert.assertEquals(kNearestNeighborsIndices, expectedIndices);
    }

    @Test
    public void testSearchNearestNeighbors_kGreaterThanSize() {
        // load tree
        KdTree<Integer, Coordinate<Integer>> tree = new KdTree<>(2, allCoords);
        tree.buildTree();

        TreeNode<Integer, Coordinate<Integer>> root = tree.getRoot();

        Coordinate<Integer> targetPoint = c8;

        // create comparators and priority queues
        Comparator<TreeNode<Integer, Coordinate<Integer>>> byReverseDistance
            = Comparator.comparing(t -> -1 * t.distanceTo(targetPoint.getCoordinates()));

        PriorityQueue<TreeNode<Integer, Coordinate<Integer>>> kNearestNeighborsReverse
            = new PriorityQueue<>(byReverseDistance);

        tree.searchNearestNeighbors(0, targetPoint.getCoordinates(), root,
            kNearestNeighborsReverse, 200);

        ArrayList<Integer> kNearestNeighborsIndices = new ArrayList<>();

        // convert resultant priority queue to list for comparison against known
        // correct list of indices
        while (!kNearestNeighborsReverse.isEmpty()) {
            TreeNode<Integer, Coordinate<Integer>> keyDist = kNearestNeighborsReverse.remove();
            kNearestNeighborsIndices.add(keyDist.getValue().getId());
        }
        Collections.reverse(kNearestNeighborsIndices);
        List<Integer> expectedIndices = new ArrayList<>();
        expectedIndices.add(8);
        expectedIndices.add(10);
        expectedIndices.add(5);
        expectedIndices.add(11);
        expectedIndices.add(9);
        expectedIndices.add(3);
        expectedIndices.add(7);
        expectedIndices.add(6);
        expectedIndices.add(4);
        expectedIndices.add(2);
        expectedIndices.add(1);

        Assert.assertEquals(kNearestNeighborsIndices, expectedIndices);
    }

    @Test
    public void testSearchNearestNeighbors_kLessThanSize() {
        KdTree<Integer, Coordinate<Integer>> tree = new KdTree<>(2, allCoords);
        tree.buildTree();

        TreeNode<Integer, Coordinate<Integer>> root = tree.getRoot();

        Coordinate<Integer> targetPoint = c8;

        // create comparators and priority queues
        Comparator<TreeNode<Integer, Coordinate<Integer>>> byReverseDistance
            = Comparator.comparing(t -> -1 * t.distanceTo(targetPoint.getCoordinates()));

        PriorityQueue<TreeNode<Integer, Coordinate<Integer>>> kNearestNeighborsReverse
            = new PriorityQueue<>(byReverseDistance);

        tree.searchNearestNeighbors(0, targetPoint.getCoordinates(), root,
            kNearestNeighborsReverse, 3);

        ArrayList<Integer> kNearestNeighborsIndices = new ArrayList<>();

        // convert resultant priority queue to list for comparison against known
        // correct list of indices
        while (!kNearestNeighborsReverse.isEmpty()) {
            TreeNode<Integer, Coordinate<Integer>> keyDist = kNearestNeighborsReverse.remove();
            kNearestNeighborsIndices.add(keyDist.getValue().getId());
        }

        List<Integer> expectedIndices = new ArrayList<>();
        expectedIndices.add(8);
        expectedIndices.add(10);
        expectedIndices.add(5);

        Collections.reverse(expectedIndices);
        Assert.assertEquals(kNearestNeighborsIndices, expectedIndices);
    }

    @Test
    public void testSearchNearestNeighbors_0K() {
        KdTree<Integer, Coordinate<Integer>> tree = new KdTree<>(2, allCoords);
        tree.buildTree();

        TreeNode<Integer, Coordinate<Integer>> root = tree.getRoot();

        Coordinate<Integer> targetPoint = c8;

        // create comparators and priority queues
        Comparator<TreeNode<Integer, Coordinate<Integer>>> byReverseDistance
            = Comparator.comparing(t -> -1 * t.distanceTo(targetPoint.getCoordinates()));

        PriorityQueue<TreeNode<Integer, Coordinate<Integer>>> kNearestNeighborsReverse
            = new PriorityQueue<>(byReverseDistance);

        tree.searchNearestNeighbors(0, targetPoint.getCoordinates(), root,
            kNearestNeighborsReverse, 0);

        ArrayList<Integer> kNearestNeighborsIndices = new ArrayList<>();

        // convert resultant priority queue to list for comparison against known
        // correct list of indices
        while (!kNearestNeighborsReverse.isEmpty()) {
            TreeNode<Integer, Coordinate<Integer>> keyDist = kNearestNeighborsReverse.remove();
            kNearestNeighborsIndices.add(keyDist.getValue().getId());
        }

        List<Integer> expectedIndices = new ArrayList<>();

        Assert.assertEquals(kNearestNeighborsIndices, expectedIndices);
    }

    @Test
    public void testSearchRadius_allStarsWithinR() {
        KdTree<Integer, Coordinate<Integer>> tree = new KdTree<>(2, allCoords);
        tree.buildTree();

        TreeNode<Integer, Coordinate<Integer>> root = tree.getRoot();

        double r = 32.39393939393939485945495849584958495;
        Coordinate<Integer> targetPoint = c8;

        // create comparators and priority queues
        Comparator<TreeNode<Integer, Coordinate<Integer>>> byReverseDistance
            = Comparator.comparing(t -> t.distanceTo(targetPoint.getCoordinates()));

        PriorityQueue<TreeNode<Integer, Coordinate<Integer>>> kNearestNeighborsQueue
            = new PriorityQueue<>(byReverseDistance);

        tree.searchRadius(0, targetPoint.getCoordinates(), root, r,
            kNearestNeighborsQueue);

        // convert resultant priority queue to list for comparison against known
        // correct list of indices
        List<Integer> kNearestNeighborsIndices = new ArrayList<>();
        while (!kNearestNeighborsQueue.isEmpty()) {
            TreeNode<Integer, Coordinate<Integer>> keyDist = kNearestNeighborsQueue.remove();
            kNearestNeighborsIndices.add(keyDist.getValue().getId());
        }

        List<Integer> expectedIndices = new ArrayList<>();
        expectedIndices.add(8);
        expectedIndices.add(10);
        expectedIndices.add(5);
        expectedIndices.add(11);
        expectedIndices.add(9);
        expectedIndices.add(3);
        expectedIndices.add(7);
        expectedIndices.add(6);
        expectedIndices.add(4);
        expectedIndices.add(2);
        expectedIndices.add(1);

        Assert.assertEquals(kNearestNeighborsIndices, expectedIndices);
    }

    @Test
    public void testSearchRadius_someStarsWithinR() {
        KdTree<Integer, Coordinate<Integer>> tree = new KdTree<>(2, allCoords);
        tree.buildTree();

        TreeNode<Integer, Coordinate<Integer>> root = tree.getRoot();

        double r = 4;
        Coordinate<Integer> targetPoint = c8;

        // create comparators and priority queues
        Comparator<TreeNode<Integer, Coordinate<Integer>>> byReverseDistance
            = Comparator.comparing(t -> t.distanceTo(targetPoint.getCoordinates()));

        PriorityQueue<TreeNode<Integer, Coordinate<Integer>>> kNearestNeighborsQueue
            = new PriorityQueue<>(byReverseDistance);

        tree.searchRadius(0, targetPoint.getCoordinates(), root, r,
            kNearestNeighborsQueue);

        // convert resultant priority queue to list for comparison against known
        // correct list of indices
        List<Integer> kNearestNeighborsIndices = new ArrayList<>();
        while (!kNearestNeighborsQueue.isEmpty()) {
            TreeNode<Integer, Coordinate<Integer>> keyDist = kNearestNeighborsQueue.remove();
            kNearestNeighborsIndices.add(keyDist.getValue().getId());
        }

        List<Integer> expectedIndices = new ArrayList<>();
        expectedIndices.add(8);
        expectedIndices.add(10);
        expectedIndices.add(5);

        Assert.assertEquals(kNearestNeighborsIndices, expectedIndices);
    }

    @Test
    public void testSearchRadius_0R() {
        KdTree<Integer, Coordinate<Integer>> tree = new KdTree<>(2, allCoords);
        tree.buildTree();

        TreeNode<Integer, Coordinate<Integer>> root = tree.getRoot();

        double r = 0;
        Coordinate<Integer> targetPoint = c8;

        // create comparators and priority queues
        Comparator<TreeNode<Integer, Coordinate<Integer>>> byReverseDistance
            = Comparator.comparing(t -> t.distanceTo(targetPoint.getCoordinates()));

        PriorityQueue<TreeNode<Integer, Coordinate<Integer>>> kNearestNeighborsQueue
            = new PriorityQueue<>(byReverseDistance);

        tree.searchRadius(0, targetPoint.getCoordinates(), root, r,
            kNearestNeighborsQueue);

        // convert resultant priority queue to list for comparison against known
        // correct list of indices
        List<Integer> kNearestNeighborsIndices = new ArrayList<>();
        while (!kNearestNeighborsQueue.isEmpty()) {
            TreeNode<Integer, Coordinate<Integer>> keyDist = kNearestNeighborsQueue.remove();
            kNearestNeighborsIndices.add(keyDist.getValue().getId());
        }

        List<Integer> expectedIndices = new ArrayList<>();
        expectedIndices.add(8);

        Assert.assertEquals(kNearestNeighborsIndices, expectedIndices);
    }

    @Test
    public void testToString() {
        // creating coordinates
        List<Double> data = new ArrayList<>();
        data.add(0.0);
        data.add(10.0);
        Coordinate<Integer> c1 = new Star(1, "s1", data);

        List<Double> data2 = new ArrayList<>();
        data2.add(1.0);
        data2.add(9.0);
        Coordinate<Integer> c2 = new Star(2, "s2", data2);

        List<Double> data3 = new ArrayList<>();
        data3.add(2.0);
        data3.add(-1.0);
        Coordinate<Integer> c3 = new Star(3, "s3", data3);

        List<Double> data4 = new ArrayList<>();
        data4.add(3.0);
        data4.add(7.0);
        Coordinate<Integer> c4 = new Star(4, "s4", data4);

        // creating list of coordinates
        List<Coordinate<Integer>> allCoords = new ArrayList<>();
        allCoords.add(c1);
        allCoords.add(c2);
        allCoords.add(c3);
        allCoords.add(c4);

        // loading tree
        KdTree<Integer, Coordinate<Integer>> tree = new KdTree<>(2, allCoords);
        tree.buildTree();

        assertEquals(tree.toString(), "KdTree{dimensions=2, tree=TreeNode{value=Coordinate{id=3, coordinates=[2.0, -1.0, ]}, left=TreeNode{value=Coordinate{id=2, coordinates=[1.0, 9.0, ]}, left=NULL, right=TreeNode{value=Coordinate{id=1, coordinates=[0.0, 10.0, ]}, left=NULL, right=NULL}}, right=TreeNode{value=Coordinate{id=4, coordinates=[3.0, 7.0, ]}, left=NULL, right=NULL}}}");
    }

    @Test
    public void testEquals() {
        // creating coordinates
        List<Double> data = new ArrayList<>();
        data.add(0.0);
        data.add(10.0);
        Coordinate<Integer> c1 = new Star(1, "s1", data);

        List<Double> data2 = new ArrayList<>();
        data2.add(1.0);
        data2.add(9.0);
        Coordinate<Integer> c2 = new Star(2, "s2", data2);

        List<Double> data3 = new ArrayList<>();
        data3.add(2.0);
        data3.add(-1.0);
        Coordinate<Integer> c3 = new Star(3, "s3", data3);

        List<Double> data4 = new ArrayList<>();
        data4.add(3.0);
        data4.add(7.0);
        Coordinate<Integer> c4 = new Star(4, "s4", data4);

        // creating list of coordinates
        List<Coordinate<Integer>> allCoords = new ArrayList<>();
        allCoords.add(c1);
        allCoords.add(c2);
        allCoords.add(c3);
        allCoords.add(c4);

        // loading tree
        KdTree<Integer, Coordinate<Integer>> tree1 = new KdTree<>(2, allCoords);
        KdTree<Integer, Coordinate<Integer>> tree2 = new KdTree<>(2, allCoords);
        KdTree<Integer, Coordinate<Integer>> tree3 = new KdTree<>(1, allCoords);

        assertEquals(tree1, tree2);
        assertEquals(tree1, tree1);
        assertNotEquals(tree1, tree3);
    }
}