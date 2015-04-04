package assignment1;

import java.util.Arrays;

import org.junit.Test;

import static org.junit.Assert.*;

public class Assignment1Test {

    @Test
    public void testIntersectionSize() throws Exception {
        String [] doc = new String [] {"a", "b", "b", "c", "c", "d", "e", "e"};
        String[] query = new String [] {"b", "e"};
        assertEquals(Assignment1.intersectionSize(doc,query), 2);

        query = new String [] {"b", "e", "f"};
        assertEquals(Assignment1.intersectionSize(doc,query), 2);
        assertEquals(Assignment1.intersectionSize(doc,new String[]{}), 0);
        assertEquals(Assignment1.intersectionSize(new String[]{},query), 0);
    }

    @Test
    public void testJaccardIndex() throws Exception {

    }

    @Test
    public void testUnionSize() throws Exception {
        String [] doc = new String [] {"a", "b", "b", "c", "c", "d", "e", "e"};
        String[] query = new String [] {"b", "e"};
        assertEquals(Assignment1.unionSize(doc,query), 5);

        query = new String [] {"b", "e", "f"};
        assertEquals(Assignment1.unionSize(doc,query), 6);
        assertEquals(Assignment1.unionSize(doc,new String[]{}), 5);
        assertEquals(Assignment1.unionSize(new String[]{},query), 3);

    }

    @Test
    public void testNumberOfHitsBin() throws Exception {
        String [] doc = new String [] {"a", "b", "b", "c", "c", "d", "e", "e"};
        String[] query = new String [] {"b", "e"};
        assertEquals(Assignment1.numberOfHits(doc, query), 4);

        query = new String [] {"b", "e", "f"};
        assertEquals(Assignment1.numberOfHits(doc, query), 4);
        assertEquals(Assignment1.numberOfHits(doc, new String[]{}), 0);
        assertEquals(Assignment1.numberOfHits(new String[]{}, query), 0);
    }

    @Test
    public void timeCompareHits() throws Exception{
        timeCompare(10);
        timeCompare(100);
        timeCompare(1000);
        timeCompare(10000);
        timeCompare(100000);
    }


    public void timeCompare (int size) throws Exception {
        String [] array= new String[size];
        for (int i = 0; i<size; i++){
            array[i] = String.valueOf(i);
        }

        String [] queryNum= new String[size];
        int number = 1;
        int index = 0;
        while (number<size){
            queryNum[index] = String.valueOf(number+=3);
            index++;
        }
        queryNum = Arrays.copyOfRange(queryNum, 0, index);

        long timeIn = System.currentTimeMillis();
        Assignment1.numberOfHits(array, queryNum);
        long timeOut = System.currentTimeMillis();
        System.out.println("Execution time for size: " + String.valueOf(size) +" bin search: " + String.valueOf(timeOut-timeIn));



    }
    @Test
    public void testRemoveStopWords() throws Exception {
        String [] doc = new String[]{"my", "life","is","an","empty","array"};
        String [] ans = new String[]{"life","empty","array"};
        assertArrayEquals(ans, Assignment1.removeStopWords(doc));
    }
}