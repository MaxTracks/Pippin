package pippin;

public class Memory {
	private int changedIndex = -1;
    public static final int DATA_SIZE = 512;
    private int[] data = new int[DATA_SIZE];
    public void setData(int index, int value) {
        data[index] = value;
        changedIndex = index;
    }
    public int getData(int index) {
        return data[index];
    }
    public int getChangedIndex(){
        return changedIndex;
    }
    int[] getData() {
        return data;
    }   
    public void clear(){
    	for(int dataPoint:data){
    		dataPoint = 0;
    	}
    	changedIndex= -1;
    }
}