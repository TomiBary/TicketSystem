package cz.ticketsystem.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Ticket{
  private long id;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime creationDateTime;

  public Ticket(long id) {
    this.id = id;
    this.creationDateTime = LocalDateTime.now();
  }

  protected Ticket(Ticket other) {
    this.id = other.id;
    this.creationDateTime = other.creationDateTime;
  }

  public TicketWithOrder toTicketWithOrder(long order) {
    return new TicketWithOrder(this, order);
  }

}


