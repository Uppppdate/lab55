package Tickets;

import java.util.*;

public class TicketCollection {
    private Hashtable<Long, Ticket> hashtable;
    private Date initializationDate;
    private String collectionType;
    private String internalType;
    private int amountOfElements;
    private int lastIndexWorkedWith;
    private long lastIdWorkedWith;
    private long lastAnnualTurnoverWorkedWith;
    private TicketType lastTicketTypeWorkedWith;

    public TicketCollection(Hashtable<Long, Ticket> hashtable) {
        this.hashtable = hashtable;
        collectionType = hashtable.getClass().getSimpleName();
        internalType = "Tickets";
        amountOfElements = hashtable.size();
        initializationDate = new Date();
    }

    public Hashtable<Long, Ticket> getCollection() {
        return hashtable;
    }

    public void setHashtable(Hashtable<Long, Ticket> hashtable) {
        this.hashtable = hashtable;
        updateData();
    }

    public void updateData() {
        amountOfElements = hashtable.size();
        initializationDate = new Date();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("The collection\n");
        for(Map.Entry<Long, Ticket> collection: hashtable.entrySet()){
            stringBuilder.append(collection.getKey()).append(") ");
            stringBuilder.append(collection.getValue().toString());
        }
        return new String(stringBuilder);
    }

    public String toShowInfo(){
        return "Collection type: " + collectionType +
                "\nInternal type: " + internalType +
                "\nAmount of elements: " + hashtable.size();
    }

    public long getLastIdWorkedWith() {
        return lastIdWorkedWith;
    }

    public long getLastAnnualTurnoverWorkedWith() {
        return lastAnnualTurnoverWorkedWith;
    }

    public void setLastAnnualTurnoverWorkedWith(long lastAnnualTurnoverWorkedWith) {
        this.lastAnnualTurnoverWorkedWith = lastAnnualTurnoverWorkedWith;
    }

    public TicketType getLastOrganizationTypeWorkedWith() {
        return lastTicketTypeWorkedWith;
    }

    public void setLastOrganizationTypeWorkedWith(TicketType lastTicketTypeWorkedWith) {
        this.lastTicketTypeWorkedWith = lastTicketTypeWorkedWith;
    }

//    public int setLastIdWorkedWith(long lastIdWorkedWith) {
//        Hashtable<Long, Ticket> list1 = getCollection().stream().filter(o -> o.getId()==lastIdWorkedWith).toList();
//        if(list1.isEmpty()){
//            return -1;
//        }
//        this.lastIdWorkedWith = lastIdWorkedWith;
//        return 1;
//    }

    public int getLastIndexWorkedWith() {
        return lastIndexWorkedWith;
    }

    public int setLastIndexWorkedWith(int lastIndexWorkedWith) {
        if(getCollection().size()-1<lastIndexWorkedWith){
            return -1;
        }
        this.lastIndexWorkedWith = lastIndexWorkedWith;
        return 1;
    }
}
