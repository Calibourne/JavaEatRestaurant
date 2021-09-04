package Model;

/**
 * This is a class used to prevent unwanted multiple selection of identical items in checkListView
 * it serves like a shell to a record in a checkListView
 */
public class ListedRecord {
    private final Record record;

    public ListedRecord(Record record){
        this.record = record;
    }

    public Record getRecord() {
        return record;
    }

    @Override
    public String toString() {
        return record.toString();
    }
}