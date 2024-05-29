package cz.ticketsystem.model;

import lombok.Getter;
import lombok.Setter;

public class TicketWithOrder extends Ticket {

  @Getter
  @Setter
  private long order;

  protected TicketWithOrder(Ticket ticket, long order) {
    super(ticket);
    this.order = order;
  }
}
