package OS_projects;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mohamed Sameh, Youssef Khaled, Nour E-din Osama
 */
/*
description:
you will implement the algorithms by writing methods with the same name as the alg. ex: void FIFO
the methods must use the printing methods written below, your functions should not have any printing otherwise for uniformity sake
you SHOULD NOT edit class Page variables or methods without making sure it is synced between other implementors
you SHOULD NOT edit class Memory's variables or private methods without making sure it is synced between other implementors
you may add new private functions in page or memory but do not forget to inform others
 */
public class Memory {
    private List<Page> memory;
    private int memorySize;
    private List<Integer> referenceList;

    public Memory(int memorySize, List<Integer> referenceList){
        memory = new ArrayList<>(memorySize);
        this.memorySize = memorySize;
        this.referenceList = new ArrayList<>(referenceList);
    }

    /**
     * this method searches for a page in memory
     * @param pageID the id of the page to be searched
     * @return true if it is found
     */
    private boolean pageInMemory(int pageID){
        for (Page p : memory)
            if(pageID == p.getID())
                return true;
        return false;
    }

    /**
     *
     * @return true if the memory is full
     */
    private boolean fullMemory(){
        return memory.size() >= memorySize;
    }

    /**
     *
     * @param pageID the id of the page to be inserted, the use of this method is optional
     * @return true if the memory is not full, the return value is not important if the check is already mad
     */
    private boolean insertPage(int pageID){
        if(fullMemory())
            return false;
        memory.add(new Page(pageID));
        return true;
    }

    /**
     * this function should be called at the beginning of the algorithm
     * @param algorithmName the name of the algorithm you are implementing
     */
    private void printinit(String algorithmName){
        System.out.println("*********" + algorithmName + "*********");
        System.out.println("Memory size: " + memorySize + " pages");
        System.out.println("Reference String: ");
        for (Integer i : referenceList){
            System.out.print(i + " ");
        }
        System.out.println();
    }

    /**
     * this function is called when a page faults
     * @param pageID the id of the faulted page
     */
    private void printFault(int pageID){
        System.out.println("The page no. " + pageID + " faulted");
    }

    /**
     * this function is called when a page is found in memory
     * @param pageID the id of the requested page
     */
    private void printHit(int pageID){
        System.out.println("The page no. " + pageID + " was found in memory");
    }

    /**
     * unlike other print methods THIS method is only called after the algorithm finishes
     * @param faultCount the number of faults at the end of the algortihm
     * @param algorithmName the name of the algorithm
     */
    private void printFaultCount(int faultCount, String algorithmName){
        System.out.println("There has been " + faultCount + " faults using " + algorithmName);
    }

    /**
     *
     * @param pageID the id-not the index-of the page to be replaced
     */
    private void printVictimPage(int pageID){
        System.out.println("Page no. " + pageID + " had to be removed for new page");
    }

    /**
     * prints the current memory contents
     */
    private void printMemoryContents(){
        if(memory.size() == 0){
            System.out.println("still empty memory");
            return;
        }
        System.out.println("the memory has the following pages:");
        for(Page p : memory){
            System.out.print(p.getID() + " ");
        }
        System.out.println();
    }

    /**
     * the implementation of FIFO
     * notice that this implementation relies on the arraylist to move object such that the index 0
     * is always the oldest element which is a slow implementation
     * an actual list would actually be much faster
     *
     */
    public void FIFO(){
        String name = "First in First Out";
        printinit(name);//printing statement
        int faultCount = 0;
        for (int i = 0; i < referenceList.size(); i++) {
            printMemoryContents();//printing statement
            int pageID = referenceList.get(i);
            if(pageInMemory(pageID))
                printHit(pageID);//printing statement

            else{
                printFault(pageID);//printing statement
                faultCount++;

                if(fullMemory())
                   memory.remove(0);

                insertPage(pageID);
            }
        }

        printFaultCount(faultCount, name);

    }

    public void Optimal(){
        String name = "Optimal";
        printinit(name);//printing statement
        int faultCount = 0, max = 0;
        Page victim = new Page(-1);
        for (int i = 0; i < referenceList.size(); i++) {
            printMemoryContents();//printing statement
            int pageID = referenceList.get(i);
            if(pageInMemory(pageID))
                printHit(pageID);//printing statement
            else{
                printFault(pageID);//printing statement
                faultCount++;

                if(fullMemory())
                {
                    for(Page p : memory)
                    {
                        for (int j = i+1; j < referenceList.size(); j++)
                        {
                            if((j-i) > max)
                                max++;
                            if(p.getID() == referenceList.get(j))
                            {
                                if((j-i) >= max)
                                    victim = p;
                                break;
                            }
                            if((j == referenceList.size()-1 && p.getID() != referenceList.get(j)))
                                victim = p;
                        }
                    }
                    memory.remove(victim);
                }
                insertPage(pageID);
            }
        }
        printFaultCount(faultCount, name);
    }
}
