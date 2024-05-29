package cz.ticketsystem.service;

import cz.ticketsystem.model.Ticket;
import cz.ticketsystem.model.TicketWithOrder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TicketService {
  private final PriorityQueue<Ticket> ticketQueue = new PriorityQueue<Ticket>(Comparator.comparingLong(Ticket::getId));

  // Přidá lístek s ID o 1 větším než nejvyšší ID
  public TicketWithOrder createNewTicket() {
    //TODO neziskavat highestId, ale udrzovat si interne ID vytvorenych Ticketu, ktere inkrementuje
    long highestId = getHighestTicketId();
    Ticket newTicket = new Ticket(highestId + 1);
    ticketQueue.add(newTicket);
    return newTicket.toTicketWithOrder(ticketQueue.size() - 1);
  }

  // Vrátí ID lístku s největším ID
  public long getHighestTicketId() {
    return getHighestTicket().map(Ticket::getId).orElse(0L);
  }

  // Vrátí Optional lístku s největším ID
  public Optional<Ticket> getHighestTicket() {
    if(ticketQueue.isEmpty()) {
      return Optional.empty();
    }
    // Vytvoří se dočasnou PriorityQueue s obráceným pořadím
    PriorityQueue<Ticket> reversedTicketQueue = new PriorityQueue<>(Collections.reverseOrder(Comparator.comparingLong(Ticket::getId)));
    reversedTicketQueue.addAll(ticketQueue);
    // Vrátí lístek s největším ID z obrácené fronty
    Ticket ticket = reversedTicketQueue.peek();
    if (ticket == null) {
      throw new IllegalStateException("Ticket is null");
    }
    return Optional.of(ticket);
  }

  // Odstraní lístek s největším ID a vrátí ho
  public Optional<TicketWithOrder> removeHighestTicket() {
    if (ticketQueue.isEmpty())
      return Optional.empty();

    Optional<Ticket> highestTicket = getHighestTicket();
    highestTicket.ifPresent(ticketQueue::remove);
    return highestTicket.flatMap(ticket -> Optional.of(ticket.toTicketWithOrder(ticketQueue.size())));
  }

  // Vrátí lístek s nejmenším ID
  public Optional<TicketWithOrder> getLowestIdTicket() {
    return ticketQueue.isEmpty() ? Optional.empty() : Optional.of(ticketQueue.peek().toTicketWithOrder(0));
  }

  public Optional<TicketWithOrder> getAndRemoveLowestIdTicket() {
    return ticketQueue.isEmpty() ? Optional.empty() : Optional.of(ticketQueue.poll().toTicketWithOrder(0));
  }

  // Vrátí všechny lístky seřazené od nejmenšího ID
  public List<TicketWithOrder> getAllTicketsSorted() {
    if(ticketQueue.isEmpty()) {
      return Collections.emptyList();
    }
    List<TicketWithOrder> sortedTickets = new ArrayList<>();
    long i = 0;
    // !!! Kompletně vymaže z fronty všechny prvky !!! - použít LinkedList namísto PriorityQueue
    while (!ticketQueue.isEmpty()) {
      sortedTickets.add(ticketQueue.poll().toTicketWithOrder(i++));
    }
    ticketQueue.addAll(sortedTickets);
    return sortedTickets;
  }

  public void clearQueue() {
    ticketQueue.clear();
  }
}
