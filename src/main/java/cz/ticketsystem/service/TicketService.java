package cz.ticketsystem.service;

import cz.ticketsystem.model.TicketWithOrder;

import java.util.List;
import java.util.Optional;

public interface TicketService {

  TicketWithOrder createNew();

  Optional<TicketWithOrder> getFirst();

  Optional<TicketWithOrder> getFirstAndRemove();

  Optional<TicketWithOrder> removeLast();

  List<TicketWithOrder> getAllTickets();

  void removeAll();
}
