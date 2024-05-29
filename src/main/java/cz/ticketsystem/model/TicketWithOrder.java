package cz.moneta.ticketsystem.model;

import lombok.Getter;
import lombok.Setter;

public class TicketWithOrder extends Ticket {

  @Getter
  @Setter
  private long order;

  protected TicketWithOrder(Ticket ticket, long order) {
    super(ticket.getId());
    this.order = order;
  }
}
