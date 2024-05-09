package Tickets;

import java.util.*;

public class TicketCollection {
    private Hashtable<Long, Ticket> hashtable;
    private Date initializationDate;
    private String collectionType;
    private String internalType;
    public static Long amountOfElements;
    private int lastIndexWorkedWith;
    private long lastIdWorkedWith;
    private long lastAnnualTurnoverWorkedWith;
    private TicketType lastTicketTypeWorkedWith;

    public TicketCollection(Hashtable<Long, Ticket> hashtable) {
        this.hashtable = hashtable;
        collectionType = hashtable.getClass().getSimpleName();
        internalType = "Tickets";
        amountOfElements = (long) hashtable.size();
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
        amountOfElements = (long)hashtable.size();
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

    public void updateNumeration(){
        Set<Map.Entry<Long, Ticket>> skibidi = hashtable.entrySet();
        List<Map.Entry<Long, Ticket>> list = skibidi.stream().toList();
        Hashtable<Long, Ticket> newHashTable = new Hashtable<>();
        for(long i = 0; i<list.size(); i++){
            Ticket ticket = list.get((int) i).getValue();
            ticket.setId(i+1);
            newHashTable.put(i+1, ticket);
        }
        Hashtable<Long, Ticket> table = new Hashtable<>();
        for(long i = 0; i<list.size(); i++){
            Ticket ticket = newHashTable.get(list.size()-i);
            ticket.setId(i+1);
            table.put(i + 1, ticket);
        }
        setHashtable(table);
    }
    public String toShowInfo(){
        return "Collection type: " + collectionType +
                "\nInternal type: " + internalType +
                "\nAmount of elements: " + hashtable.size();
    }
}
