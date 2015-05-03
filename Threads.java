
package threadsandcollections;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alexandr Poryvaev PPV 23-01
 */ 


public class ThreadsAndCollections {
    final static int countThreads = 3;
    
    private static class CallBack implements Callable<Long> {
        private final Runnable task;

        public CallBack(Runnable task) {
            this.task = task;
        }
        
        @Override
        public Long call() throws Exception {
            long startTime = System.currentTimeMillis();
            task.run();
            return System.currentTimeMillis() - startTime;
        }
        
    }
    
    private static class Result{
        public long testDrive(Runnable task, 
                int executionCount, int threadPoolSize) throws InterruptedException{
            ExecutorService executor = Executors.newFixedThreadPool(threadPoolSize);
            List<Future<Long>> execTimes = new LinkedList<>();
            for (int i = 0; i < executionCount; i++) {
                execTimes.add(executor.submit(new CallBack(task)));
            }

            long totalTime = 0;

            for (Future<Long> future : execTimes) {
                try {
                    long time = future.get();
                    totalTime += time;
                    
                } catch (ExecutionException ex) {
                    throw new RuntimeException(ex);
                }
            }
            
            return totalTime/executionCount;
        }
    }
    
    public static ArrayList fillCollection(int size){
        ArrayList <Integer> arrList = new ArrayList <>();
        
        for(int i=0; i<size; i++){
            arrList.add(i);
        }
        return arrList;
    }
    
    public static class OverList implements Runnable {
        List <Integer> list;
        
        public OverList (List <Integer> someList){
            list = someList;
        }
        
        @Override
        public void run() {
            for(int i=0; i< list.size()/2; i++){
                try {
                    Thread.sleep(1);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ThreadsAndCollections.class.getName()).log(Level.SEVERE, null, ex);
                  }
            }
            System.out.println("Finished!");
        }
    }
    
    public static void universalTest (List <Integer> list , String name , int countThr) throws InterruptedException{
        OverList runntableList = new OverList (list);
        Result result = new Result ();
        long res = result.testDrive(runntableList, countThr, 11);
        System.out.println("Average time "+name+"= " +res);
    }
    
    public static class OverSet implements Runnable {
        Set <Integer> set;
        
        public OverSet (Set <Integer> someSet){
            set = someSet;
        }
        
        @Override
        public void run() {
            for(int i=0; i< set.size()/2; i++){
                try {
                    Thread.sleep(1);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ThreadsAndCollections.class.getName()).log(Level.SEVERE, null, ex);
                  }
            }
            System.out.println("Finished!");
        }
    }
    
    public static void universalTest (Set <Integer> set , String name , int countThr) throws InterruptedException{
        OverSet runntableSet = new OverSet (set);
        Result result = new Result ();
        long res = result.testDrive(runntableSet, countThr, 11);
        System.out.println("Average time "+name+"= " +res);
    }
    
    public static void main(String[] args) throws InterruptedException {
        ArrayList <Integer> arrL1 = new ArrayList <>();
        ArrayList <Integer> arrL2 = new ArrayList <>();
        ArrayList <Integer> arrL3 = new ArrayList <>();
        
        arrL1 = fillCollection(100);
        arrL2 = fillCollection(1000);
        arrL3 = fillCollection(10000);
        
        LinkedList  <Integer> linL1 = new LinkedList<>(arrL1);
        LinkedList  <Integer> linL2 = new LinkedList<>(arrL2);
        LinkedList  <Integer> linL3 = new LinkedList<>(arrL3);
        
        HashSet <Integer> hashS1 = new HashSet<>(arrL1);
        HashSet <Integer> hashS2 = new HashSet<>(arrL2);
        HashSet <Integer> hashS3 = new HashSet<>(arrL3);
        
        LinkedHashSet <Integer> lhashS1 = new LinkedHashSet<>(arrL1);
        LinkedHashSet <Integer> lhashS2 = new LinkedHashSet<>(arrL2);
        LinkedHashSet <Integer> lhashS3 = new LinkedHashSet<>(arrL3);
        
        TreeSet <Integer> thashS1 = new TreeSet<>(arrL1);
        TreeSet <Integer> thashS2 = new TreeSet<>(arrL2);
        TreeSet <Integer> thashS3 = new TreeSet<>(arrL3);
        
        universalTest(arrL1, "ArrayList with 100 elements", countThreads);
        universalTest(linL1, "LinkedList with 100 elements", countThreads);
        universalTest(hashS1, "HashSet with 100 elements", countThreads);
        universalTest(lhashS1, "LinkedHashSet with 100 elements", countThreads);
        universalTest(thashS1, "TreeSet with 100 elements", countThreads);
        
        System.out.println("---------------------------------------------" );
        
        universalTest(arrL2, "ArrayList with 1000 elements", countThreads);
        universalTest(linL2, "LinkedList with 1000 elements", countThreads);
        universalTest(hashS2, "HashSet with 1000 elements", countThreads);
        universalTest(lhashS2, "LinkedHashSet with 1000 elements", countThreads);
        universalTest(thashS2, "TreeSet with 1000 elements", countThreads);
        
        System.out.println("---------------------------------------------" );
        
        universalTest(arrL3, "ArrayList with 10000 elements", countThreads);  
        universalTest(linL3, "LinkedList with 10000 elements", countThreads);
        universalTest(hashS3, "HashSet with 10000 elements", countThreads);
        universalTest(lhashS3, "LinkedHashSet with 10000 elements", countThreads);
        universalTest(thashS3, "TreeSet with 10000 elements", countThreads);
    }
    
}
