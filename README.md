# README

## Maps 3+4

### Structural Organization

#### node

**TreeNode**: This class creates a Node object that contains any value and has references to two children, both which must be Nodes of the same type as the root Node.

#### coordinates

**Coordinate**: This interface makes a class an n-dimensional coordinate with each coordinate having its own unique ID.

**KdTree**: This class can be used to build a Balanced KdTree from a List of Coordinates using Nodes

**KeyDistance**: This class composes the Coordinate Class in the special case where there exists one coordinate and that coordinate has significance: distance to some arbitrary target. It provides a relation between any key and some real distance.

#### searchAlgorithms

**ListNaiveSearch**: This class allows running the naive neighbors and radius search algorithms to return appropriate indices according to the passed constraints.

#### csv

**Csv**: This class is called by the stars handler method in StarsCollection, and the mock handler method in MockCollection to load data from a CSV.

#### database

**DatabaseHandler**: This class is responsible for establishing connections to DB as long as they are valid.

#### dijkstraAStar

**DijkstraAStar**: This class is responsible for performing the A*/Dijkstra pathfind algorithm.

**GraphEdge**: This interface defines the requirements for a Graph Edge class to be compatible with our implementation of Dijkstra's.

**GraphNode**: This interface defines the requirements for a Graph Node class to be compatible with our implementation of Dijkstra's

**GraticuleNode**: This class represents a Node with lat and lon and implements GraphNode.

**GraticuleEdge**: This class represents a Way connecting GraticuleEdges and implements GraphEdge.

**HeuristicFuncs**: This class contains all methods that calculate heuristics for different pathfind algorithms.

**PathfinderPropertyBasedTesting**: This class performs Property Based Testing on pathfinding by comparing Dijkstra's output to A*.

**PathWeightHeuristic**: This class associates each path with its accumulated distance and last node's heuristic.

**ProxiedEdgeFetcher**: This class caches the neighboring edges for nodes while performing A* search.

#### main

**Main**: Starting point of program that activates the REPL.

**ErrorMessages**: final class that provides a common place to add and modify error messages across the program.

#### repl

**Repl**: This class manages the Repl, and is in charge of directing appropriate commands to their respective methods.

#### utils

**Utils**: Class containing any generic methods applicable beyond the Stars Assignment.

#### stars

**Star**: Class that represents all properties of every star (ID, name, etc).

**SearchAlgoPropertyBasedTesting**: Class that performs Model Based Testing to ensure that the result of the naive and kdTree commands/implementations match for randomly generated command inputs on 3D positioned stars.

#### checkin

**CheckinThread**: Thread runs continuously once started in main, querying for user checkin data from the checkin server.

**UserCheckin**: Used to store a UserCheckin datapoint consisting of a unique id, name, timestamp, latitude and longitude.

#### commandHandlers

#### gui

**StarGuiHandler**: This class contains all handlers of the stars GUI and communicates between the UI and the star search algorithms

**MapsGuiHandler**: This class contains all handlers of the maps GUI and communicates between the UI and the map algorithms

#### maps

**MapCommandHandler**: This class handles the map command.

**NearestCommandHandler**: This class handles the nearest command.

**WaysCommandHandlers**: This class handles the ways command.

**RouteCommandsHandlers**: This class handles both street/cross-street and lat lon route commands.

**MapDBResultSetHandler**: This class contains all methods that handle Result Sets and converts them to data types expected for the appropriate commands that need to query the DB.

**NeighborWaysSearchers**: This class contains the method using which edges from a node are found.

**NodeDistanceCalculators**: This class contains the method using which distances between nodes are calculated.

**DeleteCommandHandler**: This class handles the delete command created to assist in deleting all of a particular user's data from the SQL Checkin table.

**CheckinObjectPersistence** This class is used to store a reference to the currently running CheckinThread to allow access to this across the program.

#### stars

**StarsCommandHandler**: This class handles the stars command.

**NaiveCommandsHandler**: This class handles the naive_neighbors and naive_radius commands.

**KdTreeCommandsHandler**: This class handles the neighbors and radius commands.

**StarsResultParsers**: This class contains all methods required for parsing Lists of Coordinates to either the REPL or the GUI.

### Partner Maps Contributions

psekhsar provided the Dijkstra/A* implementation. We used this implementation since it was cleaner and the algorithm seemed more intuitive to us. We were able to integrate this within the framework of sdiwan2's overall code by changing certain function names and interfaces, as well as modifying the algorithm slightly to work with the heuristic functionality in sidwan2's code. Additionally, we added the common ErrorMessages class functionality from psekhsar's code which involved abstracting out error messages from the entire code and integrating all of them with a single, consistent place to add and modify these messages.

sdiwan2 provided the REPL structure. We found this REPL implementation with functional interfaces to be cleaner and more intuitive, and different components of the program seemed to fit together better. We also found it easier to use the remaining functionality of the codebase that provided the REPL. And so we used sdiwan2's code as the base and integrated psekhsar's Dijkstra implementation into it,  maintaining the whole command handler set-up from sdiwan2's code.

### Partner Maps Division of Labor

Please note that these indicate 'work done mostly by', and not 'work done only by'

**psekhsar**
1. Merging code bases and system tests
2. Integrating an error class
3. SQL for maps 4
4. Frontend caching ways
5. Checkin GUI

**sdiwan2**
1. Canvas display
2. Panning/zooming
3. Adding delete functionality
4. Route display
5. Backend GUI handler class

### Design Details

#### Frontend

We have maintained a distinction between the canvas bounds and the grid bounds. Canvas bounds represent the latitude and longitude boundaries of the top, left, bottom and right most edges of the canvas that may be displayed. On the other hand, gridbounds have a fixed hyperparametrized size of 0.008 lat/lon distance and serve as gridboxes for requesting ways from the backend or cache whenever necessary.

When attempting to get an intersection by clicking on the map, if the user attempts to click on a node that is untraversable or that lies at the intersection of a street with empty name, then an error will populate at the top of the page. We do not allow for clicking untraversable nodes because the user will never be able to find paths to that node. And, we do not allow clicking of a node at the intersection of a street with empty/no name because route names may not be unique, especially not the empty name, which could imply that getting a route with that coordinate could choose some other street with empty name somewhere different from where the user intends.

We have implemented the following functionality in the frontend in addition to minimum functionality:

1. Latitude/longitude inputs

Please note that we have hidden this div within the route.js file in our maps react app src folder because otherwise scrolling on the map would
have caused scrolling down the page. We encourage the TA to show the div. You may find appropriate instructions
at the bottom of the route.js file within the return block on how to show this div.

2. Coloring of road types

3. Legend of road types

This legend is not hardcoded, but instead maps over a dict that we have created within the route.js file. You will notice that some dicts in the
route.js file have some route names commented out. This is because any future developers may choose to add them to the dict and see their colors
populate appropriately if they choose to do so.

4. Start/end note highlighting

When the user clicks on the map to get an intersection point, they will see valid intersections highlighted either blue or green.   

5. Display of start/end node intersecting streets

When the user clicks on the map to get an intersection point, they will see the corresponding street intersections populate in the route input
boxes. We have implemented this by adding additional functionality in the backend that queries the loaded DB to find intersecting streets given
a node.

6. Loading state

When a map is loading or state is updating, a loader will populate that gives the user confidence that the app has not crashed :-) and has registered
their request.

7. Road name dynamic display

Different zoom levels will dynamically populate different road types depending on how long each road is in pixels. The road names are also populated in the direction of the road to minimize confusion about which name is for which street.

#### Backend

We have tried to make methods within our maps handler classes as generic as possible between the computations required by the two variants of the route command. We have done this using method overloading where methods with the same purpose, such as getPath, have different signatures in terms of paramters being accepted, but have the same return type.

In fact, we have also tried to make our A* implementation as generic as possible by allowing the user to pass separate distance, heuristic and neighborEdgeFinding functions. We do this by allowing the user to pass methods to the constructor. By having different distance functions, users can use distance metrics other than haversine. By having different heuristic functions, users can switch between A* and Dijkstra and other different implementations. By passing different neighborEdgeFind functions, users can work choose to work independent of SQL by instead having a graph, and users can pass methods that find neighboring edges without caching.

Additionally, one very interesting thing about our KdTree implementation is that neighbors actually calls naive_neighbors to handle tied distances. we find this quite fascinating. The PriorityQueue provided by the neighbors algorithm usually has all nodes that it traverses through because there is no guarantee that same distance stars will appear close to each other in the KdTree. For this reason, the neighbors algorithms essentially spits out a smaller, sorted list, which must undergo the same random handling for common distances as in the naive_neighbors algorithm!

One more interesting fact is that we have tried to separate out 'independent methods' into their own classes wherever possible. We have done this with HeuristicFuncs in dijkstraAStar package, and NodeDistanceCalculators and NeighborWaySearchers in maps commandHandlers. This way, it becomes straightforward to add variations to those functions, and pass them is as method parameters to whichever command handlers need to use them.

We also have integrated a common ErrorMessages class into our code. This is a final class that contains all the public static final strings that serve as our error messages. While a majority of these error strings are simply static final strings, we made use of a few public static methods so that we can pass important, error-related, data into our error messages. For example, if a database has a table with an invalid header name, we pass in the appropriate table's name into the error method which then returns an error message telling the user which table in the database was incorrectly formatted. This provides a common point for us to edit and modify error messages, and helps us standardise our error messages across the program.

### What is a valid DB

To avoid the cost of iterating through the entire database while also maintaining the functionality of maps, we've decided to consider only databases with valid cross products between their node and way table. That is, no empty ids and malformed values or types in the cross product table.

In the special case that a database has empty node ids but none of the ways references it or that the way table references nodes outside of the node table, both of these possibilities are considered valid. Our maps implementation will simply filter out
these ways and nodes.

### Runtime/space optimizations

For runtime optimization on drawing and redrawing ways and routes, we have used frontend caching by using a dictionary of bounding coordinates in lat and lon to an array of ways returned from backend querying. To enhance runtime optimizations for such a cache, we have tried to find a sweet spot for the bounding box size, and finally determined that each bounding box should have side length 0.008 latitude/longitude.

While testing the app, we also noticed that panning or zooming into unknown areas (into bounding boxes not in cache) can mean that hundreds of duplicate queries requesting ways from the same bounding box can be sent to the backend, causing a bottleneck and often making the loading of new areas of the map very slow. To fix this, we maintain a separate cache of bounding boxes that have already been requested, because we recognize that it may take some time before a request that is sent gives back appropriate ways. This way, requests to the backend have been heavily optimized to remove duplicate requests for the same bounding boxes, especially because our code triggers a refreshing of the canvas with every pixel movement caused by pan/zoom.

### How to Run System Tests

We have included all System Tests within this directory: _server/tests/student/

1. Git bash at the root directory of the project
2. Direct into the server directory (cd server)
3. Run each of the following commands for each of the Tests:

#### MapsCollection:

###### map:

./cs32-test tests/student/maps/map/errors/*.test

./cs32-test tests/student/maps/map/valid/*.test

###### nearest:

./cs32-test tests/student/maps/nearest/errors/*.test

./cs32-test tests/student/maps/nearest/valid/*.test

###### route:

./cs32-test tests/student/maps/route/byLocValid/*.test

./cs32-test tests/student/maps/route/byNameValid/*.test

./cs32-test tests/student/maps/route/errors/*.test

###### ways

./cs32-test tests/student/maps/ways/valid/*.test

./cs32-test tests/student/maps/ways/errors/*.test

#### Repl:

python cs32-test tests/student/stars/stars1/Repl/errors/*test

#### StarsCollection:

###### stars:

python cs32-test tests/student/stars/stars1/StarsCollection/stars/errors/*test

python cs32-test tests/student/stars/stars1/StarsCollection/stars/valid/*test

###### naive_neighbors:

python cs32-test tests/student/stars/stars1/StarsCollection/naive_neighbors/3_args/*test

python cs32-test tests/student/stars/stars1/StarsCollection/naive_neighbors/5_args/*test

python cs32-test tests/student/stars/stars1/StarsCollection/naive_neighbors/errors/*test

###### naive_radius:

python cs32-test tests/student/stars/stars1/StarsCollection/naive_radius/3_args/*test

python cs32-test tests/student/stars/stars1/StarsCollection/naive_radius/5_args/*test

python cs32-test tests/student/stars/stars1/StarsCollection/naive_radius/errors/*test

##### neighbors:

python cs32-test tests/student/stars/stars2/StarsCollection/neighbors/3_args/*test

python cs32-test tests/student/stars/stars2/StarsCollection/neighbors/5_args/*test

python cs32-test tests/student/stars/stars2/StarsCollection/neighbors/errors/*test

##### radius:

python cs32-test tests/student/stars/stars2/StarsCollection/radius/3_args/*test

python cs32-test tests/student/stars/stars2/StarsCollection/radius/5_args/*test

python cs32-test tests/student/stars/stars2/StarsCollection/radius/errors/*test

### How to Run and compile the Code

#### Without GUI:
1. Git bash at the root directory of the project
2. Direct into the server directory (cd server)
3. Type mvn package
4. Type ./run and hit enter
5. And... that's it :)

#### With GUI:
1. Direct into the server directory (cd server) and run:

    If you want to use python 2:

    python cs032_maps_location_tracking 8080 1000000 (python cs032_maps_location_tracking 8080 1000000 -s if you want to use the small map)

    If you want to use python 3:

    python3 cs032_maps_location_tracking_py3 8080 1000000 (python cs032_maps_location_tracking_py3 8080 1000000 -s if you want to use the small map)
2. In a new terminal, follow the instructions in the previous section titled Without GUI (in step 4 of Without GUI type ./run --gui instead)
3. In a new terminal, direct into the maps directory (cd maps) of the project and type npm start

### Notes on JUnit Tests

All tests may be found in the Test classes corresponding to each class. Please note that the headers of the test methods may include underscores, as permitted when we asked on Piazza. This is so that the Surefire report is easy to read and to allow for modularizing tests without making each block >100 lines each.

Whenever you run mvn package, you will also be re-running the tests. Since we have tests on the maps database using the local path: data/maps/maps.sqlite3, these will fail becasue the DB is gitignored. So, mvn package will fail if these paths are not changed. To make it easier to change these paths, we have included private variables called 'bigMapsPath' at the top of the JUnit testing files located in the commandHandlers, maps package that load from the maps database.

Note that the number of iterations for stars PBT is 100, and the number of iterations for A* PBT is 100. Also, at the moment the PBT is running only on the smallMaps.sqlite3 database. This is because our PBT runs Dijkstra against A* and so there is no guarantee as to how long mvn packaging would take were we loading from maps. To make PBT run on the maps database, you will need to replace the path to the maps DB within the PathfinderPropertyBasedTestingTest class (within the JUnit tests).

In the current state, mvn packaging should not take more than 6 minutes (It will still take a considerable amount of time because we have tests for the maps DB).

### How we tested the GUI

We ensured that the minimum functionality requirements were met, including route display, panning, zooming and so on. We ensured that it would be intuitive to interact with each minimum functionality requirement through the use of buttons or clicking on screen.

We also tested the GUI by ensuring that map loading from the terminal accurately reflected in the GUI state without needing to refresh the react app. We did this by alternately loading DBs from the frontend and terminal and seeing that the program could handle this.

Since the methods being used for the GUI were already being used by the terminal/ REPL and had already been tested in Maps 1 + 2, we retained confidence that our commands such as ways, route and nearest that were giving us results in the frontend were known good implementations of the appropriate algorithms or had correct SQL query statements.

We ensured that the type of data being recieved through the Axios requests was correct by console logging the data and ensuring the state of data was preserved. These console logs have been deleted from the react app for submission purposes and to keep the console as clean as possible.

### Known GUI Limitations

1. If the user loads a file through the REPL/terminal, whereas the change in DB is guaranteed to reflect and load a new map on the GUI, it may or may not change the DB state message on the GUI "Currently Loaded: blah". We believe this is because of some limitations with react use state.
2. DB files loaded from the GUI must be contained within the data/maps folder within the maps project. Other paths cannot be guaranteed to work. This stems from the security feature of Chromium browsers that a file input cannot read the path of the file.

### Browser compatibility

sdiwan2 tested for app compatibility on Chromium (Edge and Google) browsers and psekhsar tested compatibility on the Safari browser.

Please note that whereas the app is guaranteed to work on these (and likely other) browsers, the aspect ratio of a user's machine may change the location of different display elements (such as buttons and input fields) on the app screen.

### Direct References

The one thing that we copied exactly from the internet was the regular expression for splitting a String by spaces except between quotations.
We struggled on this for a while, and we were never satisfied with my naive implementations that tried to split over quotations and then over spaces, which had more loopholes than stars in the galaxy.

Link:
https://stackoverflow.com/questions/366202/regex-for-splitting-a-string-using-space-when-not-surrounded-by-single-or-double/366532

The other thing we took heavy inspiration for from the internet was finding a way to test System.out.println. We used a very similar structure to a discussion on Stackoverflow

Link:
https://stackoverflow.com/questions/1119385/junit-test-for-system-out-println/21216342#21216342

The third reference we took online was for creating my own Functional Interfaces.

Link:
https://stackoverflow.com/questions/27872387/can-a-java-lambda-have-more-than-1-parameter
