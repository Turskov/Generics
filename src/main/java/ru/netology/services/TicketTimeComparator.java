package ru.netology.services;

import java.util.Comparator;

public class TicketTimeComparator implements Comparator<Ticket> {

    @Override
    public int compare(Ticket t1, Ticket t2) {
        return Integer.compare(t1.getTimeFrom(), t2.getTimeFrom());
    }
}
