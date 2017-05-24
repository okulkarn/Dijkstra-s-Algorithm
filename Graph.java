// Graph.java
// Graph code, modified from code by Mark A Weiss.
// Computes Unweighted shortest paths.

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.TreeMap;



// Used to signal violations of preconditions for
// various shortest path algorithms.
class GraphException extends RuntimeException
{
    public GraphException( String name )
    {
        super( name );
    }
}

// Represents a vertex in the graph.
class Vertex
{
    public String     name;   // Vertex name
    public List<Edge> adj;    // Adjacent edges
    public Vertex     prev;   // Previous vertex on shortest path
    public double     dist;   // Distance of path
    public int vertexstatus;
    public int visited;
    
    public Vertex( String nm, int vs,int vis )
      { name = nm; vertexstatus = vs; visited = vis; 
        adj = new LinkedList<Edge>( ); reset( ); }

    public void reset( )
      { dist = Graph.INFINITY; prev = null; }    

//This function is used     
    @Override
public String toString(){
    String s = this.name;
    return s.toString();
}
      
}

// Represents an vertex in the graph. Added to the adjacency list of vertex
class Edge{

public double weight;       //edge transmission time
public Vertex prev_node;    //previous vertex of type Vertex
public Vertex next_node;    //next vertex of type Vertex
public int edgestatus;

public Edge(double ed, Vertex pre, Vertex nex, int es){
    weight = ed; 
    prev_node = pre; 
    next_node = nex;         
    edgestatus = es;
}
@Override
public String toString(){
    Double d = this.weight;
    return d.toString();
}
}

// Graph class: evaluate shortest paths.
//
// CONSTRUCTION: with no parameters.
//
// ******************PUBLIC OPERATIONS**********************
// void addEdge( String v, String w )
//                              --> Add additional edge
// void printpath2( String w )   --> Print path after alg is run
// ******************ERRORS*********************************
// Some error checking is performed to make sure graph is ok,
// and to make sure graph satisfies properties needed by each
// algorithm.  Exceptions are thrown if errors are detected.

public class Graph
{
    public static final int INFINITY = Integer.MAX_VALUE;
    private Map<String,Vertex> vertexMap = new HashMap<String,Vertex>( );
    
    public Map getVertexMap()
    {
        return vertexMap;
    }
    
    /**
     * Add a new edge to the graph.
     */
    public void addEdge( String sourceName, String destName, double edgeinfo,int i )
    {
        Vertex v = getVertex( sourceName );
        Vertex w = getVertex( destName );
        int ee = 0;
        
        Edge e = new Edge(edgeinfo, v, w, 1);
        Edge e1 = new Edge(edgeinfo, w, v, 1 );
        
        //flag i checks if the graph is filled for the first time (i=0) or if the edge is added by the user (i=1)
        if(i==1)
            {    
                for(Edge ed : v.adj){
                    if (ed.next_node == w){
                        ee = 1;
                        ed.weight = edgeinfo;
                    }
                }
                
        //flag ee checks if the edge already exists between two vertices (ee = 1)        
                if(ee==0)
                    v.adj.add(e);                
            }
        
        if(i==0)
        {
        v.adj.add(e);
        w.adj.add(e1);
        }
    }

    /**
     * Driver routine to print total distance.
     * It calls recursive routine to print shortest path to
     * destNode after a shortest path algorithm has run.
     */
 
    /**
     * If vertexName is not present, add it to vertexMap.
     * In either case, return the Vertex.
     */
    private Vertex getVertex( String vertexName )
    {
        Vertex v = vertexMap.get( vertexName );
        if( v == null )
        {
            v = new Vertex( vertexName,1,0 );
            vertexMap.put( vertexName, v );
        }
        return v;
    }

/*deletes an edge*/    
    private void deleteedge(String source1,String dest1){

        Vertex v = getVertex( source1 );
        Vertex w = getVertex( dest1 );
        Edge e2 = null;
        Edge e3 = null;
        
        for (Edge e : v.adj){
            if(e.prev_node == v  && e.next_node == w){
                e2 = e;                
            }
        }
        
        v.adj.remove(e2);
        
    }

/* For printing the graph network*/    
   private void printpath2(){
        
        Map<String,Vertex> tempmap = new TreeMap<String,Vertex>(vertexMap);
         
         for (Map.Entry<String, Vertex> entry : tempmap.entrySet())
         {
             Vertex v = (Vertex)entry.getValue();
             List<Edge> edge_sort = v.adj;

             Collections.sort(edge_sort, new Comparator<Edge>(){
             public int compare(Edge e1, Edge e2) {
             return (e1.next_node.name).compareTo(e2.next_node.name);
            }
            });
             
             Iterator<Edge> itr = edge_sort.iterator();
             if (v.vertexstatus == 0)
                 System.out.println(""+entry.getKey().toString()+" DOWN");
             else
                 System.out.println(""+entry.getKey().toString());
             while (itr.hasNext())
             {
                Edge edg = (Edge)itr.next();
                System.out.print("  "+edg.next_node.name+" "+edg.weight );
                if(edg.edgestatus == 0)
                System.out.print(" DOWN");
                System.out.print("\n");                 
             }               
        }        
    }

/*updating the stautus up/down*/   
   private void edgestatusupdate(String source1,String dest1,int es){
       Vertex v = vertexMap.get(source1 );
       Vertex w = vertexMap.get(dest1 );
       for (Edge e : v.adj){
           if(e.prev_node == v && e.next_node == w){
               e.edgestatus = es;
           }
       }
   }
   
   private void vertexstatusupdate(String vertex,int vs){
       Vertex v = vertexMap.get(vertex);
       v.vertexstatus = vs;
   }

   private void checkreachability(){
       
       Map<String,Vertex> tempmap = new TreeMap<String,Vertex>(vertexMap);

       for (Map.Entry<String, Vertex> entry : tempmap.entrySet()){
           
            
           for (Map.Entry<String, Vertex> entry1 : tempmap.entrySet()){
           Vertex w = entry1.getValue();
           w.visited = 0;           
           }

           List<Vertex> reachlist = new ArrayList<Vertex>();
           Vertex w = entry.getValue();
           w.visited = 1;
           
           if(w.vertexstatus == 1)
             System.out.print(w.name+"\n");

           List r_sort = new ArrayList();           
           reachability(reachlist,r_sort,w,0);
           Collections.sort(r_sort);
           for (int i =0; i < r_sort.size();i++){
           System.out.println("  "+r_sort.get(i));
           }           
       }
   }
   
   private List reachability(List reachlist,List r_sort,Vertex w,int i)
   {       
       for(Edge e : w.adj){
           if (e.next_node.visited == 0 && e.edgestatus == 1 && e.next_node.vertexstatus == 1 && e.prev_node.vertexstatus == 1){
           reachlist.add(e.next_node);
           e.next_node.visited = 1;
       }
       }

       if(i < reachlist.size()){
           Vertex vname = (Vertex) reachlist.get(i);
           r_sort.add(vname.name);
           reachability(reachlist, r_sort,(Vertex) reachlist.get(i),i+1);
       }
       return r_sort;
   }
   
//Dijtras algorithm to find shortext path
   private void run_dijstra(String start, String destination){
            
      clearAll( );
      int i = 0;
      Vertex st = vertexMap.get( start );
      st.dist = 0;
      
      //test conditions
/*      Vertex st1 = vertexMap.get( "Belk" );
      Vertex st2 = vertexMap.get("Woodward");
      Vertex st3 = vertexMap.get("Health");
      Vertex st4 = vertexMap.get( "Education");
      Vertex st5 = vertexMap.get( "Grigg" );*/
      int n = vertexMap.size( );
      List<Vertex> priorityq = new ArrayList<Vertex>();
      if( start == null )
           throw new NoSuchElementException( "Start vertex not found" );
      
//     st.dist = 0;st2.dist = 25;st1.dist = 10;st3.dist = 3;st5.dist = 1;st4.dist = 6;
       
     for (Map.Entry<String, Vertex> entry : vertexMap.entrySet())
       {
          Vertex w = entry.getValue();
          priorityq.add(w);
       }
        
        buildminheap(priorityq,n);
       
        Map<String,Vertex> visited_list = new HashMap<String,Vertex>( );
        
        System.out.print("\n");
                        
        while(!priorityq.isEmpty())
        {
            Vertex u = extractmin(priorityq,n);
            if(n>0)
             n=n-1;
            visited_list.put( u.name, u );
            
            for(Edge v : u.adj){
                Vertex v1 = v.next_node;
                
                if ((v1.dist > u.dist + v.weight) && v.edgestatus == 1 && v1.vertexstatus == 1 && v1 != visited_list.get(v1)){
                    v1.dist = u.dist + v.weight;
                    v.prev_node = u;
                    v1.prev = u;
                    decrease_key(priorityq,priorityq.indexOf(v1),v1);
                }
                //System.out.println();
            } 

        }
        
        Vertex w = vertexMap.get(destination);
        
        printpathdijt(w.name);
   }
   
   private void decrease_key(List priorityq, int i2,Vertex v1){
       Vertex v2 = (Vertex) priorityq.get(i2);
       int j2 = 0;

       if(i2>0)
       {    
       j2 = (i2-1)/2;    
       Vertex p = (Vertex) priorityq.get(j2);
       
       Vertex temp = null;
       v2.dist = v1.dist;
       
       while(i2>0 && p.dist>v1.dist)
       {
            j2 = (i2-1)/2;
            p = (Vertex) priorityq.get(j2);
            priorityq.remove(j2);      
            priorityq.add(j2,v1);
            priorityq.remove(i2);
            priorityq.add(i2,p);
            i2 = (i2-1)/2;
       }
       }
   }
   
   /*sorts the heap*/
   private List minheapify(List q,int i,int n){
       int l = 2*i+1;
       int r = 2*i+2;
       
       Vertex index = (Vertex) q.get(i); 
       int smallest = i;
       
       if(l<n){
           Vertex left = (Vertex) q.get(l);
           if(left.dist<index.dist)
           smallest = l;
       }

       if(r<n){
           Vertex smallest1 = (Vertex) q.get(smallest);
           Vertex right = (Vertex) q.get(r);
           if(right.dist<smallest1.dist )
           smallest = r;
       }
                         
       if (smallest != i)
       {
        Vertex smallest1 = (Vertex) q.get(smallest);   
        q.remove(smallest);        
        q.add(smallest,index);
        q.remove(i);
        q.add(i,smallest1);
        minheapify(q,smallest,n);
       }
       return q;
   }
   
/*builds heap*/   
   private void buildminheap(List q,int n){
       List<Vertex> q1 = new ArrayList<Vertex>();
       for(int j = ((n/2)-1);j>=0; j--){
           q1 = minheapify(q,j,n);
       }
   }
   
/* Extracts min from the heap*/
   private Vertex extractmin(List pq,int n){
    Vertex min = (Vertex) pq.get(0);
    if(n>1)
    {
        Vertex pq1 = (Vertex) pq.get(n-1);       
        min = (Vertex) pq.remove(0);
        pq.add(0,pq1);
        pq.remove(n-1);
        n = n-1;
        minheapify(pq,0,n);
    }
    else
        pq.remove(0);
    return min;
   }
   
    /**
     * Recursive routine to print shortest path to dest
     * after running shortest path algorithm. The path
     * is known to exist.
     */
    public void printpathdijt( String destName )
    {
        Vertex w = vertexMap.get( destName );
        if( w == null )
            throw new NoSuchElementException( "Destination vertex not found" );
        else if( w.dist == INFINITY )
            System.out.println( destName + " is unreachable" );
        else
        {
            printpathdijt( w );
            System.out.print( " " );
            System.out.printf( "%.2f",w.dist);
            System.out.println( );
        }
    }

    private void printpathdijt(Vertex w)
    {
        if( w.prev != null )
        {
            printpathdijt( w.prev );
            System.out.print( "  " );
        }
        System.out.print( w.name );
    }
    /**
     * Initializes the vertex output info prior to running
     * any shortest path algorithm.
     */
    private void clearAll( )
    {
        for( Vertex v : vertexMap.values( ) )
            v.reset( );
    }


    /**
     * Process a request; return false if end of file.
     */
    public static boolean processRequest( Scanner in, Graph g )
    {
        try{
        System.out.print( "\nQueries:\n " );
        
        String choice ;
        String instruction = "";
        String source = "";
        String dest = "";
        double weight1 = 0; 
        choice = in.nextLine();

        StringTokenizer st1 = new StringTokenizer( choice );
        
        if(st1.countTokens( ) == 1 ){
            instruction = st1.nextToken();
        }
        
        if(st1.countTokens() == 2){
            instruction = st1.nextToken();
            source  = st1.nextToken( );
        }
        
        if(st1.countTokens() == 3){
            instruction = st1.nextToken();
            source  = st1.nextToken( );
            dest    = st1.nextToken( );
        }

        if(st1.countTokens() == 4){
            instruction = st1.nextToken();
            source  = st1.nextToken( );
            dest    = st1.nextToken( );
            weight1    = Double.parseDouble(st1.nextToken());
        }
        
        switch(instruction)
        {
            case "addedge":
            {
                
                g.addEdge( source, dest, weight1,1);
                
                break;
            }
            
            case "deleteedge":
            {
                g.deleteedge(source,dest);
                
                break;
            }

            case "edgedown":
            {
                g.edgestatusupdate(source,dest,0);
                
                break;
            }
            case "edgeup":
            {
                g.edgestatusupdate(source,dest,1);
                
                break;
            }
            case "vertexdown":
            {
                g.vertexstatusupdate(source,0);
                break;
            }
            case "vertexup":
            {
                g.vertexstatusupdate(source,1);
                break;
            }
           case "path":
            {
                g.run_dijstra(source,dest);
                break;
            }
          case "print":
            {
                g.printpath2();
                break;    
            }
           case "reachable":
            {
                g.checkreachability();
                break;
            }
           case "quit":
           {
               System.exit(0);
           }
           
           default :
                System.out.println("Invalid grade");                
        }

        }
        
        catch( NoSuchElementException e )
          { return false; }
        catch( GraphException e )
          { System.err.println( e ); }
        return true;
    }

    /**
     * A main routine that:
     * 1. Reads a file containing edges (supplied as a command-line parameter);
     * 2. Forms the graph;
     * 3. Repeatedly prompts for two vertices and
     *    runs the shortest path algorithm.
     * The data file is a sequence of lines of the format
     *    source destination 
     */
    @SuppressWarnings("empty-statement")
    public static void main( String [ ] args )
    {
        
        Graph g = new Graph( );

        try
        {
            //read a file from the command prompt
            FileReader fin = new FileReader("C:\\Users\\omkar\\Desktop\\univ_dist.txt");
            //FileReader fin = new FileReader(args[0]);
            Scanner graphFile1 = new Scanner( fin );

            // Read the edges and insert
            String line;
            int In;
            
            while( graphFile1.hasNextLine( ) )
            {
                line = graphFile1.nextLine( );
                StringTokenizer st = new StringTokenizer( line );
                
                try
                {
                    if( st.countTokens( ) != 3 )
                    {
                        System.err.println( "Skipping ill-formatted line " + line );
                        continue;
                    }
                    
                    String source  = st.nextToken( );
                    String dest    = st.nextToken( );
                    double edge    = Double.parseDouble(st.nextToken());
                    
                    g.addEdge( source, dest, edge,0 );
                    
                }
                catch( NumberFormatException e )
                  { System.err.println( "Skipping ill-formatted line " + line ); }
                 
                 
             }
         }
         catch( IOException e )
           { System.err.println( e ); }
         
        Scanner in = new Scanner( System.in );

        while( processRequest( in, g ) );        
    }

    private void printpath(String source) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}