# data_structures
Custom implementation of various data structures to facilitate learning. If a data structure is present in the Java
Collections library (e.g. List) then my implementations follows the same API when there is overlap though not all functionality
will be supported with intial versions. For example, my implementation of a List will have the same method names for adding, removing,
indexing, etc. as the Java List iterface but will not nessecarily implement the Java interface (for example the java Streams
api is currently not supported in any of the data structures). Basically I want to implement a basic version of each data structure in order to learn how they work. It should also be noted that while I try to write code as efficiently as possible, the main priority is for the data structures to be functionally correct and not neccesarily the fastest / best version of such a data structure. Just use the Java collections libary if that is what you want as many man-hours have been spent optimizing that library.

This code is tested via Continuous Integration using Travis-CI.
The builds can be viewed publicly going [here](https://travis-ci.org/lock14/data_structures)
