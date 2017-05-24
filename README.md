# Dijkstra-s-Algorithm


Omkar Kulkarni
okulkarn@uncc.edu
+1(980)3499973
Algorithms and Data structions
Project II
------------------------------------------------------------------------------------------------------------------------------------------------------------------
Project :- Shortest Paths in a Network
------------------------------------------------------------------------------------------------------------------------------------------------------------------
Software :
	- Java
IDE :
	- NetBeans IDE 8.1
------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
User Direction :

	- User can run the program in Windows cmd or Linux Terminal using following actions:


	- User has to pass the queries to avail the functionality of the program.
	- Examples of few queries are :
		print, path Belk Education, edgedown Health Education, path Health Education, vertexdown Belk, edgeup Health Education 
		reachable, vertexup Belk, deleteedge Duke Education, addedge Education Atkins 0.25
	- These queries perform the actions like printing graph, printing shortest path from source to destination, finding reachable graph, etc
	- quit query is used to quit the execution.

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	
Functioning Discription :

	- Dijtra's Algorithm is used in this project to find the shortest path from a start vertex to a destination vertex. 
	- The shortest path of any vertex to any vertex in a network can be found.
	- All the reachable verices are determined. Agorithm using BFS idea is used for that.
	- A vertex can be made down or again up by the user. Same is the case for the edge.
	- A down vertex indicates that it cannot be reached. So dijtra's or reachability algorithm doesn't work for that vertex.
	- A down edge indicates that it cannot be traversed.
 
-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

Proram Description:

	Class Vertex And Class Edge:

		- Class Vertex contains all the properties of object Vertex and same for edges.
		- Objects of class Vertex are stored in a Hashmap. The Hashmap is sorted in alphabetical order using treemap data structure.
		- The objects of Class Edge are stored in a adjacency list which is member of Class Vertex.
		- Class Vertex has the properties such as its name (string), its distance, adjacency list of its edges, visited, its status(up,down) etc.
		- Class Edge has properties such as its previous and next nodes (Class Vertex), its weight (double), its status etc.

	Class Graph :
		- This is the main class in which the program is executed. It is of type public.
		- All the functions are defined in this class.

	main :
		- The file which contains the network is read. It is provided by the user from the command prompt.
		- Then the addedge function is called which stores the graph in a data structure. The data structure used is Hashmap.
		- After adding the graph in the Hashmap queries are taken from the user and actions are performed.
		- The program ends when the user enters the query quit.

	Function addEdge:

		- In this function graph is added in the Hashmap. The hashmap is maintained as a <key,value> pair.
		- The key is of type string and the value is of class Vertex.
		- A brief discription of the Vertex class and Edge class is given above.
		- While adding the graph initially from the user file two edges are added between two vertices of same weight in opposite directions.
		- To add an edge afterwards taking it as a input from the user in a query only a unidirectional(single) edge is added.
		- The eddges are added to the adjacency list of the vertex.
		- New vertices and edges can added using this function.
		- If a edge already exists between two verices and is the user tries to add the edge between those vertices in the same direction only the weight of the same edge is updated. New wdge is not added.

		The user can avail different functionalities like delete edge, add edge, vertex up/down, edge up/down etc which are described below:

	Function run_dijtra:

		- The user has to give "path source destination" as a query.
		- This function is used to find the shortest between two vertices.
		- This function uses dijtras algorithm to do the same.
		- The priority queue is stored as a minheap. This reduces the time complexity.
		- The dijtra's algorithm takes v*loge + e*logv time.

	Function printdijtra:

		- The function is used to print the shortest path.
		- It is called recuresively.   

	Functions checkreachablity and reachabilty:

		- The user has to give "reachable" as a query.
		- The reachabilty list of all the vertices is deduced using this function.
		- If the vertex is down it is not reachable. If the vertex cannot be reached by travering through the edges it is unreachable. These conditions are taken care of.
		- This function is implemented uing the idea of Breadth First Search.
		- All the adjacent vertices of the vertices are deduced. Then the adjacent vertices of those vertices are deduced. If no vertex is reached in this way then it is unreachable.
		- Recursive call of the function reachability is performed.
		- The time complexity of the algorithm designed is v*O(v+e). It is v* since reachabke vertices of all the vertices are deduced.

	Function deleteedge:  

		- The user has to give "deleteedge source destination" as a query.
		- An edge is deleted using this function. source vertex aand destination vertex are passed in this function.
		- The edge is removed from the adjacency list using list.remove.

		Function edgestatusupdate:

		- The user has to give "edgeup/edgedown source destination" as a query.
		- Update edge status to up or down. It is implemented using a flag.
		-  A down edge cannot be traversed.

	Function vertexstatusupdate:

		- The user has to give "vertexup/vertexdown source destination" as a query.
		- Update vertex status to up or down. It is implemented using a flag.
		- A down vertex cannot be reached (accessed). 

	Function printpath2:

		- The graph is printed when the user gives "print" as a query.
		- The graph is printed in the aplphabetical order.
		- The data structure called treemap is used to sort the hashmap keys. For srting the values(adjacent nodes) collection.sort function is used.

-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

note : - The program fails if the right queries are not given.
	   - In this case the program doesnt end but gives an error message. 	
