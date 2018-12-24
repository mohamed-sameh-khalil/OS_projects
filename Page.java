package OS_projects;

public class Page {
    private int ID;
    private boolean rBit = true;//reference bit
    private boolean mBit = false;//modification bit
    private long lastReference;//represents time for LRU and count for LFU

    Page(int ID){
        this.ID = ID;
    }
    public boolean isReferenced() {
        return rBit;
    }

    public void referenced(boolean r_bit) {
        this.rBit = r_bit;
    }

    public boolean isModified() {
        return mBit;
    }

    public void modified(boolean m_bit) {
        this.mBit = m_bit;
    }

    public long getLastReference() {
        return lastReference;
    }

    public void setLastReference(long currentTime) {
        this.lastReference = currentTime;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
