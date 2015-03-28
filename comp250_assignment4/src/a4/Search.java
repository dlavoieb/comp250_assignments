package a4;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Stack;



/* ACADEMIC INTEGRITY STATEMENT
 * 
 * Paste the block here.
 */



/* A Simple Search Engine exploring subnetwork of McGill University's webpages.
 * 	
 *	Complete the code provided as part of the assignment package. Fill in the \\TODO sections
 *  
 *  Do not change any of the function signatures. However, you can write additional helper functions 
 *  and test functions if you want.
 *  
 *  Do not define any new classes. Do not import any data structures. 
 *  
 *  Make sure your entire solution is in this file.
 *  
 *  We have simplified the task of exploring the network. Instead of doing the search online, we've
 *  saved the result of an hour of real-time graph traversal on the McGill network into two csv files.
 *  The first csv file "vertices.csv" contains the vertices (webpages) on the network and the second csv 
 *  file "edges.csv" contains the links between vertices. Note that the links are directed edges.
 *  
 *  An edge (v1,v2) is link from v1 to v2. It is NOT a link from v2 to v1.
 * 
 */

public class Search {

    private ArrayList<Vertex> graph;
    private ArrayList<Vertex> BFS_inspector;
    private ArrayList<Vertex> DFS_inspector;
    private Comparator<SearchResult> comparator = new WordOccurrenceComparator();
    private PriorityQueue<SearchResult> wordOccurrenceQueue;

    /**
     * You don't have to modify the constructor. It only initializes the graph
     * as an arraylist of Vertex objects
     */
    public Search(){
        graph = new ArrayList<Vertex>();
    }

    /**
     * Used to invoke the command line search interface. You only need to change
     * the 2 filepaths and toggle between "DFS" and "BFS" implementations.
     */
    public static void main(String[] args) {
        String pathToVertices = "vertices.csv";
        String pathToEdges = "edges.csv";

        Search mcgill_network = new Search();
        mcgill_network.loadGraph(pathToVertices, pathToEdges);

        Scanner scan = new Scanner(System.in);
        String keyword;

        do{
            System.out.println("\nEnter a keyword to search: ");
            keyword = scan.nextLine();

            if(keyword.compareToIgnoreCase("EXIT") != 0){
                mcgill_network.search(keyword, "BFS");		//You should be able to change between "BFS" and "DFS"
                mcgill_network.displaySearchResults();
            }

        } while(keyword.compareToIgnoreCase("EXIT") != 0);

        System.out.println("\n\nExiting Search...");
        scan.close();
    }

    /**
     * Do not change this method. You don't have to do anything here.
     * @return
     */
    public int getGraphSize(){
        return this.graph.size();
    }

    /**
     * This method will either call the BFS or DFS algorithms to explore your graph and search for the
     * keyword specified. You do not have to implement anything here. Do not change the code.
     * @param pKeyword
     * @param pType
     */
    public void search(String pKeyword, String pType){
        resetVertexVisits();
        wordOccurrenceQueue = new PriorityQueue<SearchResult>(1000, comparator);
        BFS_inspector = new ArrayList<Vertex>();
        DFS_inspector = new ArrayList<Vertex>();

        if(pType.compareToIgnoreCase("BFS") == 0){
            Iterative_BFS(pKeyword);
        }
        else{
            Iterative_DFS(pKeyword);
        }
    }

    /**
     * This method is called when a new search will be performed. It resets the visited attribute
     * of all vertices in your graph. You do not need to do anything here.
     */
    public void resetVertexVisits(){
        for(Vertex k : graph){
            k.resetVisited();
        }
    }

    /**
     * Do not change the code of this method. This is used for testing purposes. It follows the
     * your graph search traversal track to ensure a BFS implementation is performed.
     * @return
     */
    public String getBFSInspector(){
        String result = "";
        for(Vertex k : BFS_inspector){
            result = result + "," + k.getURL();
        }

        return result;
    }

    /**
     * Do not change the code of this method. This is used for testing purposes. It follows the
     * your graph search traversal track to ensure a DFS implementation is performed.
     * @return
     */
    public String getDFSInspector(){
        String result = "";
        for(Vertex k : DFS_inspector){
            result = result + "," + k.getURL();
        }
        return result;
    }

    /**
     * This method prints the search results in order of most occurrences. It utilizes
     * a priority queue (wordOccurrenceQueue). You do not need to change the code.
     * @return
     */
    public int displaySearchResults(){

        int count = 0;
        while(this.wordOccurrenceQueue.size() > 0){
            SearchResult r = this.wordOccurrenceQueue.remove();

            if(r.getOccurrence() > 0){
                System.out.println("Count: " + r.getOccurrence() + ", Page: " + r.getUrl());
                count++;
            }
        }

        if(count == 0) System.out.println("No results found for your search query");

        return count;

    }

    /**
     * This method returns the graph instance. You do not need to change the code.
     * @return
     */
    public ArrayList<Vertex> getGraph(){
        return this.graph;
    }

    /**
     * This method takes in the 2 file paths and creates your graph. Each Vertex must be
     * added to the graph arraylist. To implement an edge (v1, v2), add v2 to v1.neighbors list
     * by calling v1.addNeighbor(v2)
     * @param pVerticesFilePath
     * @param pEdgesFilePath
     */
    public void loadGraph(String pVerticesFilePath, String pEdgesFilePath){

        // **** LOADING VERTICES ***///

//TODO: Load the vertices from the pVerticesFilePath into this.graph. A Vertex needs a url and the words on the page. The 
//		first column of the vertices.csv file contains the urls. The other columns contain the words on the pages, one word per column.
//		Each row is 1 page.

        // **** END LOADING VERTICES ***///



        // **** LOADING EDGES ***///

//TODO: Load the edges from edges.csv. The file contains 2 columns. An edge is a link from column 1 to column 2.
//		Each row is an edge. Read the edges.csv file line by line. For every line, find the two Vertices that 
//		contain the urls in columns 1 and 2. Add an edge from Vertex v1 to Vertex v2 by calling v1.addNeighbor(v2); 

        // **** END LOADING EDGES ***///

    }




    /**
     * This method must implement the Iterative Breadth-First Search algorithm. Refer to the lecture
     * notes for the exact implementation. Fill in the //TODO lines
     * @param pKeyword
     */
    public void Iterative_BFS(String pKeyword){
        ArrayList<Vertex> BFSQ = new ArrayList<Vertex>();	//This is your breadth-first search queue.
        Vertex start = graph.get(0);						//We will always start with this vertex in a graph search

        start.setVisited();
        BFSQ.add(start);
        BFS_inspector.add(start);

//TODO: Complete the Code. Please add the line BFS_inspector.add(vertex); immediately after any insertion to your Queue BFSQ.add(vertex); This
//		is used for testing the validity of your code. See the above lines.


//TODO: When you explore a page, count the number of occurrences of the pKeyword on that page. You can use the String.contains() method to count.
//		Save your results into a SearchResult object "SearchResult r = new SearchResult(vertex.getURL(), occurrence);"
//		Also, add the SearchResult into this.wordOccurrenceQueue queue.


    }

    /**
     * This method must implement the Iterative Depth-First Search algorithm. Refer to the lecture
     * notes for the exact implementation. Fill in the //TODO lines
     * @param pKeyword
     */
    public void Iterative_DFS(String pKeyword){
        Stack<Vertex> DFSS = new Stack<Vertex>();	//This is your depth-first search stack.
        Vertex start = graph.get(0);				//We will always start with this vertex in a graph search

//TODO: Complete the code. Follow the same instructions that are outlined in the Iterative_BFS() method.		

    }


    /**
     * This simple class just keeps the information about a Vertex together.
     * You do not need to modify this class. You only need to understand how it works.
     */
    public class Vertex{
        private String aUrl;
        private boolean visited;
        private ArrayList<String> aWords;
        private ArrayList<Vertex> neighbors;

        public Vertex(String pUrl){
            this.aUrl = pUrl;
            this.visited = false;
            this.neighbors = new ArrayList<Vertex>();
            this.aWords = new ArrayList<String>();
        }

        public String getURL(){
            return this.aUrl;
        }

        public void setVisited(){
            this.visited = true;
        }

        public void resetVisited(){
            this.visited = false;
        }

        public boolean getVisited(){
            return this.visited;
        }

        public void addWord(String pWord){
            this.aWords.add(pWord);
        }

        public ArrayList<String> getWords(){
            return this.aWords;
        }

        public ArrayList<Vertex> getNeighbors(){
            return this.neighbors;
        }

        public void addNeighbor(Vertex pVertex){
            this.neighbors.add(pVertex);
        }

    }

    /**
     * This simple class just keeps the information about a Search Result. It stores
     * the occurrences of your keyword in a specific page in the graph. You do not need to modify this class.
     * You only need to understand how it works.
     */
    public class SearchResult{
        private String aUrl;
        private int aWordCount;

        public SearchResult(String pUrl, int pWordCount){
            this.aUrl = pUrl;
            this.aWordCount = pWordCount;
        }

        public int getOccurrence(){
            return this.aWordCount;
        }

        public String getUrl(){
            return this.aUrl;
        }
    }

    /**
     * This class enables us to use the PriorityQueue type. The PriorityQueue needs to know how to
     * prioritize its elements. This class instructs the PriorityQueue to compare the SearchResult
     * elements based on their word occurrence values.
     * You do not need to modify this class. You only need to understand how it works.
     */
    public class WordOccurrenceComparator implements Comparator<SearchResult>{
        @Override
        public int compare(SearchResult o1, SearchResult o2){
            int x = o1.getOccurrence();
            int y = o2.getOccurrence();

            if (x > y)
            {
                return -1;
            }
            if (x < y)
            {
                return 1;
            }
            return 0;
        }
    }
}
