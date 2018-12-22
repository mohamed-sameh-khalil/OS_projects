package OS_projects;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
        memory.clear();
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
     * This is a helping function for LRU that removes last recently used element from memory
     * OR
     * the element with the smallest Refrence value that will helo in LFU
     */
    private int getLastRefrenced(){
        int min_index = -1;
        long min_value = Long.MAX_VALUE;
        for (int i = 0;i < memory.size();i++)
            if (min_value > memory.get(i).getLastReference()) {
                min_value = memory.get(i).getLastReference();
                min_index = i;
            }
        return min_index;
    }

    /**
     * gets the index of a PageID in the memory arrayList
     * @param pageID is the ID of the required page
     * @return index of the Page object in the arrayList
     */
    private int getIndex(int pageID){
        for (int i = 0;i < memory.size();i++)
            if(memory.get(i).getID() == pageID)
                return i;
        return -1;
    }

    /**
     * used to print the reference bit in each page
     */
    private void printMemoryRefrenceBit(){
        if(memory.size() == 0)
            return;
        System.out.println("        the memory Reference Bit are:");
        System.out.print("        ");
        for(Page p : memory){
            if (p.isReferenced())
                System.out.print(1 + " ");
            else
                System.out.print(0 + " ");
        }
        System.out.println();
    }

    /**
     * used to print the modified bits in each page
     */
    private void printMemoryModifiedBit(){
        if(memory.size() == 0)
            return;
        System.out.println("        the memory Modified bit are:");
        System.out.print("        ");
        for(Page p : memory){
            if (p.isModified())
                System.out.print(1 + " ");
            else
                System.out.print(0 + " ");
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
                   printVictimPage(memory.remove(0).getID());

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
                    printVictimPage(victim.getID());
                    memory.remove(victim);
                }
                insertPage(pageID);
            }
        }
        printFaultCount(faultCount, name);
    }

    /**
     * the implementation of LRU
     * The algorithm depends on a timer which in this case is called the counter value
     * every new page or already found page updates its refrence value
     * so that the victim to remove will be the one who carries the smallest value
     * do so using the removeLastRefrenced function
     */
    public void LRU(){
        String name = "Least Recently Used";
        printinit(name);
        int counter = 0;
        int faultCount = 0;
        for (int i = 0; i < referenceList.size(); i++) {
            printMemoryContents();//printing statement
            int pageID = referenceList.get(i);
            if(pageInMemory(pageID)) {
                printHit(pageID);//printing statement
                int index = getIndex(pageID);
                memory.get(index).setLastReference(counter);
                counter++;
            }

            else{
                //algorithm
                printFault(pageID);//printing statement
                faultCount++;

                if (fullMemory()) {
                    int index = getLastRefrenced();
                    Page page = new Page(pageID);
                    page.setLastReference(counter);
                    printVictimPage(memory.get(index).getID());
                    memory.set(index,page);
                }
                else {
                    Page page = new Page(pageID);
                    page.setLastReference(counter);
                    counter++;
                    memory.add(page);
                }
            }
        }
        printFaultCount(faultCount, name);
    }

    /**
     * the implementation of LFU
     * The algorithm depends on a counter in each page that calculates the frequency of
     * each time you reference a page exist in the memory
     * if no place remove the least used and add a new one with frequency equals 1
     */
    public void LFU() {
        String name = "Least Frequently Used";
        printinit(name);
        int faultCount = 0;
        for (int i = 0; i < referenceList.size(); i++) {
            printMemoryContents();//printing statement
            int pageID = referenceList.get(i);
            if(pageInMemory(pageID)) {
                printHit(pageID);//printing statement

                int index = getIndex(pageID);
                memory.get(index).setLastReference(memory.get(index).getLastReference()+1);
            }

            else{
                //algorithm
                printFault(pageID);//printing statement
                faultCount++;

                if (fullMemory()) {
                    int index = getLastRefrenced(); //with smallest frequency
                    Page page = new Page(pageID);
                    page.setLastReference(1);
                    printVictimPage(memory.get(index).getID());
                    memory.set(index,page);
                }
                else {
                    Page page = new Page(pageID);
                    page.setLastReference(1);
                    memory.add(page);
                }
            }
        }
        printFaultCount(faultCount, name);
    }

    /**
     * The Second chance algoritm
     * if memory full
     * using the clock analogy , The pointer variable is used to cycle through the frames in memory
     * as cycling checking if it is referenced if so make it unreferenced , if it it not referenced replace it
     * if found just reference it else create new one in empty frame
     */
    public void SC(){
        String name = "Second Chance";
        printinit(name);
        int faultCount = 0;
        int pointer = 0;
        for (int i = 0; i < referenceList.size(); i++) {
            printMemoryContents();//printing statement
            printMemoryRefrenceBit();//printing statement
            int pageID = referenceList.get(i);
            if(pageInMemory(pageID)) {
                printHit(pageID);//printing statement
                int index = getIndex(pageID);
                memory.get(index).referenced(true);
            }
            else{
                //algorithm
                printFault(pageID);//printing statement
                faultCount++;

                if (fullMemory()) {
                    Page page = new Page(pageID);
                    page.referenced(false);

                    while (memory.get(pointer).isReferenced()){
                        memory.get(pointer).referenced(false);
                        pointer = (pointer == memory.size()-1)? 0: pointer+1; //cycle again
                    }
                    printVictimPage(memory.get(pointer).getID());
                    //to be able to use FIFO
                    memory.set(pointer,page);
                    pointer = (pointer == memory.size()-1)? 0: pointer+1; //start from next
                }
                else {
                    Page page = new Page(pageID);
                    page.referenced(false);
                    memory.add(page);
                }
            }
        }
        printFaultCount(faultCount, name);
    }


    /**
     * Enhanced Second Chance algorithm
     * using the modified and refrence bits to choose a victim ... if the memory is full and there is a fault
     * check to see if reference bit is 0 if so see if the mod is 0 if so remove it ->>mod is 1 ...leave it for the second cycle
     *
     * if the required page was found and not modified -->using RAND to modify it & set reference bit to 1
     * if there is a space in memory just add
     *
     * adding a new element has always ref ,mod bits (0,0)
     */
    public void ESC(){
        String name = "Enhanced Second Chance";
        printinit(name);
        int faultCount = 0;
        int pointer = 0;
        for (int i = 0; i < referenceList.size(); i++) {
            printMemoryContents();//printing statement
            printMemoryRefrenceBit();//printing statement
            printMemoryModifiedBit();//printing statement
            int pageID = referenceList.get(i);
            if(pageInMemory(pageID)) {
                printHit(pageID);//printing statement
                Random rand = new Random();
                int index = getIndex(pageID);

                memory.get(index).referenced(true);
                if (!memory.get(index).isModified())
                    memory.get(index).modified(rand.nextBoolean());
            }
            else{
                //algorithm
                printFault(pageID);//printing statement
                faultCount++;

                if (fullMemory()) {
                    Page page = new Page(pageID);
                    page.referenced(false);
                    page.modified(false);

                    boolean first_cycle = true;
                    int full_cycle = pointer;
                    while (true){
                        if (memory.get(pointer).isReferenced()) {
                            memory.get(pointer).referenced(false);
                        }
                        else{
                            if (!memory.get(pointer).isModified())
                                break;
                            else if (memory.get(pointer).isModified() && !first_cycle)
                                break;
                        }
                        pointer = (pointer == memory.size() - 1) ? 0 : pointer + 1; //cycle again
                        if (full_cycle == pointer)
                            first_cycle = true;
                    }
                    printVictimPage(memory.get(pointer).getID());
                    //to be able to use FIFO
                    memory.set(pointer,page);
                    pointer = (pointer == memory.size()-1)? 0: pointer+1; //start from next frame
                }
                else {
                    Page page = new Page(pageID);
                    page.referenced(false);
                    page.modified(false);
                    memory.add(page);
                }
            }
        }
        printFaultCount(faultCount, name);
    }
}
