import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.services.AviaSouls;
import ru.netology.services.Ticket;
import ru.netology.services.TicketTimeComparator;

import java.util.Arrays;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

public class AviaSoulsTest {

    private AviaSouls aviaSouls;
    private Ticket ticket1;
    private Ticket ticket2;
    private Ticket ticket3;

    private int compareTicketsByTimeFrom(Ticket t1, Ticket t2) {
        return Integer.compare(t1.getTimeFrom(), t2.getTimeFrom());
    }

    @BeforeEach
    public void setUp() {
        aviaSouls = new AviaSouls();
        ticket1 = new Ticket("Москва", "Екатеринбург", 500, 12, 15);
        ticket2 = new Ticket("Оренбург", "Сыктывкар", 300, 13, 14);
        ticket3 = new Ticket("Санкт-Петербург", "Красноярск", 400, 11, 13);
    }
    @Test
    public void testAddTicket() {
        aviaSouls.add(ticket1);
        aviaSouls.add(ticket2);
        aviaSouls.add(ticket3);

        Ticket[] allTickets = aviaSouls.findAll();
        assertEquals(3, allTickets.length);
        assertArrayEquals(new Ticket[]{ticket1, ticket2, ticket3}, allTickets);
    }
    @Test
    public void testSearchTickets() {
        aviaSouls.add(ticket1);
        aviaSouls.add(ticket2);
        aviaSouls.add(ticket3);

        Ticket[] searchedTickets = aviaSouls.search("Москва", "Екатеринбург");
        assertEquals(1, searchedTickets.length);
        assertEquals(ticket1, searchedTickets[0]);
    }
    @Test
    public void testSearchAndSortByPrice() {
        aviaSouls.add(ticket1);
        aviaSouls.add(ticket2);
        aviaSouls.add(ticket3);

        Comparator<Ticket> priceComparator = Comparator.comparingInt(Ticket::getPrice);
        Ticket[] sortedTickets = aviaSouls.searchAndSortBy("Москва", "Екатеринбург", priceComparator);

        assertEquals(1, sortedTickets.length);
        assertArrayEquals(new Ticket[]{ticket1}, sortedTickets);
    }


    @Test
    public void testSearchAndSortByTimeFrom() {

        aviaSouls.add(ticket1);
        aviaSouls.add(ticket2);
        aviaSouls.add(ticket3);

        Comparator<Ticket> timeFromComparator = Comparator.comparingInt(Ticket::getTimeFrom);

        Ticket[] tickets = {ticket1, ticket2, ticket3};
        Ticket[] expectedSorted = {ticket3, ticket1, ticket2};

        Comparator<Ticket> comparator = new TicketTimeComparator();
        Arrays.sort(tickets, comparator);
        assertArrayEquals(expectedSorted, tickets);
    }
    @Test
    public void testGetPrice() {

        Ticket ticket = new Ticket("Москва", "Екатеринбург", 500, 12, 15);
        assertEquals(500, ticket.getPrice());
    }
    @Test
    public void testGetTimeFrom() {
        Ticket ticket = new Ticket("Москва", "Екатеринбург", 500, 12, 15);
        assertEquals(12, ticket.getTimeFrom());
    }
    @Test
    public void testGetTimeTo() {
        Ticket ticket = new Ticket("Москва", "Екатеринбург", 500, 12, 15);
        assertEquals(15, ticket.getTimeTo());
    }
    @Test
    public void testSearch() {
        aviaSouls.add(ticket1);
        aviaSouls.add(ticket2);
        aviaSouls.add(ticket3);

        Ticket[] searchedTickets = aviaSouls.search("Оренбург", "Сыктывкар");

        assertEquals(1, searchedTickets.length);
        assertEquals(ticket2, searchedTickets[0]);
    }
    @Test
    public void testSearch_OneConditionTrue_OneConditionFalse() {
        aviaSouls.add(ticket1);
        aviaSouls.add(ticket2);
        aviaSouls.add(ticket3);

        Ticket[] searchedTickets = aviaSouls.search("Москва", "Сыктывкар");

        assertEquals(0, searchedTickets.length);
    }
    @Test
    public void testToString() {
        Ticket ticket = new Ticket("Москва", "Екатеринбург", 500, 12, 15);
        String expectedToString = "Ticket{" +
                "from='Москва', " +
                "to='Екатеринбург', " +
                "price=500, " +
                "timeFrom=12, " +
                "timeTo=15" +
                '}';
        assertEquals(expectedToString, ticket.toString());
    }
    @Test
    public void testCompareToEqual() {
        Ticket ticket1 = new Ticket("Москва", "Екатеринбург", 500, 12, 15);
        Ticket ticket2 = new Ticket("Оренбург", "Сыктывкар", 500, 13, 14);

        assertEquals(0, ticket1.compareTo(ticket2));
        assertEquals(0, ticket2.compareTo(ticket1));
    }
    @Test
    public void testCompareToLess() {
        Ticket ticket1 = new Ticket("Москва", "Екатеринбург", 500, 12, 15);
        Ticket ticket2 = new Ticket("Оренбург", "Сыктывкар", 600, 13, 14);

        assertTrue(ticket1.compareTo(ticket2) < 0);
        assertTrue(ticket2.compareTo(ticket1) > 0);
    }

    @Test
    public void testCompareToGreater() {
        Ticket ticket1 = new Ticket("Москва", "Екатеринбург", 600, 12, 15);
        Ticket ticket2 = new Ticket("Оренбург", "Сыктывкар", 500, 13, 14);

        assertTrue(ticket1.compareTo(ticket2) > 0);
        assertTrue(ticket2.compareTo(ticket1) < 0);
    }
    @Test
    public void testEqualsSameObject() {
        Ticket ticket1 = new Ticket("Москва", "Екатеринбург", 500, 12, 15);

        assertTrue(ticket1.equals(ticket1));
    }


    @Test
    public void testEqualsDifferentClass() {
        Ticket ticket1 = new Ticket("Москва", "Екатеринбург", 500, 12, 15);
        String otherObject = "Not a Ticket object";

        assertFalse(ticket1.equals(otherObject));
    }

    @Test
    public void testEqualsEqualTickets() {
        Ticket ticket1 = new Ticket("Москва", "Екатеринбург", 500, 12, 15);
        Ticket ticket2 = new Ticket("Москва", "Екатеринбург", 500, 12, 15);

        assertTrue(ticket1.equals(ticket2));
    }

    @Test
    public void testEqualsDifferentTickets() {
        Ticket ticket1 = new Ticket("Москва", "Екатеринбург", 500, 12, 15);
        Ticket ticket2 = new Ticket("Оренбург", "Сыктывкар", 600, 13, 14);

        assertFalse(ticket1.equals(ticket2));
    }
    @Test
    public void testHashCodeConsistency() {
        Ticket ticket1 = new Ticket("Москва", "Екатеринбург", 500, 12, 15);
        Ticket ticket2 = new Ticket("Москва", "Екатеринбург", 500, 12, 15);

        assertEquals(ticket1.hashCode(), ticket2.hashCode());
    }
    @Test
    public void testHashCodeDifferentTickets() {
        Ticket ticket1 = new Ticket("Москва", "Екатеринбург", 500, 12, 15);
        Ticket ticket2 = new Ticket("Оренбург", "Сыктывкар", 600, 13, 14);

        assertNotEquals(ticket1.hashCode(), ticket2.hashCode());
    }
    @Test
    public void testEqualsWithNull() {
        Ticket ticket1 = new Ticket("Москва", "Екатеринбург", 500, 12, 15);

        assertFalse(ticket1.equals(null));
    }
    @Test
    public void testEqualsWithDifferentClass() {
        Ticket ticket1 = new Ticket("Москва", "Екатеринбург", 500, 12, 15);

        assertFalse(ticket1.equals("not a Ticket"));
    }
    @Test
    public void testEqualsWithDifferentPriceTimeFromTimeTo() {
        Ticket ticket1 = new Ticket("Москва", "Екатеринбург", 500, 12, 15);
        Ticket ticket2 = new Ticket("Москва", "Екатеринбург", 600, 12, 15);

        assertFalse(ticket1.equals(ticket2));
    }
    @Test
    public void testEqualsWithNonTicketObject() {
        Ticket ticket = new Ticket("Москва", "Екатеринбург", 500, 12, 15);
        String nonTicketObject = "Не является биоетом";

        assertFalse(ticket.equals(nonTicketObject));
    }
}

