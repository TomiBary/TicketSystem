package cz.moneta.ticketsystem.model;

import lombok.Data;
import java.util.Date;

@Data
public class Ticket{
  private long id;
  private Date creationDate;

  public Ticket(long id) {
    this.id = id;
    this.creationDate = new Date();
  }

  public TicketWithOrder toTicketWithOrder(long order) {
    return new TicketWithOrder(this, order);
  }

}


