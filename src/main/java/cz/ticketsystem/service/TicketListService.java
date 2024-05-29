package cz.ticketsystem.service;

import cz.ticketsystem.model.Ticket;
import cz.ticketsystem.model.TicketWithOrder;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@Primary
public class TicketListService implements TicketService{

  private final LinkedList<Ticket> tickets = new LinkedList<>();
  private int currentId = 0;

  @Override
  public TicketWithOrder createNew() {
    Ticket newTicket = new Ticket(++currentId);
    tickets.add(newTicket);
    return newTicket.toTicketWithOrder(tickets.size() - 1);
  }

  @Override
  public Optional<TicketWithOrder> getFirst() {
    if(tickets.isEmpty()) {
      return Optional.empty();
    }
    return Optional.of(tickets.getFirst().toTicketWithOrder(0));
  }

  @Override
  public Optional<TicketWithOrder> getFirstAndRemove() {
    if(tickets.isEmpty()) {
      return Optional.empty();
    }
    return Optional.of(tickets.removeFirst().toTicketWithOrder(0));
  }

  @Override
  public Optional<TicketWithOrder> removeLast() {
    if(tickets.isEmpty()) {
      return Optional.empty();
    }
    return Optional.of(tickets.removeLast().toTicketWithOrder(tickets.size()));
  }

  @Override
  public List<TicketWithOrder> getAllTickets() {
    if(tickets.isEmpty()) {
      return new LinkedList<>();
    }
    // map all tickets to TicketWithOrder with order set to index in list
    int index = 0;
    List<TicketWithOrder> ticketsWithOrder = new LinkedList<>();
    for(Ticket ticket : tickets) {
      ticketsWithOrder.add(ticket.toTicketWithOrder(index++));
    }
    return ticketsWithOrder;
    // Too much computation
    // return tickets.stream().map(ticket -> ticket.toTicketWithOrder(tickets.indexOf(ticket))).toList();
  }

  @Override
  public void removeAll() {
    tickets.clear();
  }
}
