package cz.ticketsystem;

import cz.ticketsystem.model.TicketWithOrder;
import cz.ticketsystem.service.TicketQueueService;
import cz.ticketsystem.service.TicketService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
class TicketSystemApplicationTests {

  private static final int REPEAT_COUNT = 10;
  @Autowired
  private TicketService ticketService;
  @Test
  public void testCreateTicket() {
    ticketService.removeAll();
    TicketWithOrder ticket = ticketService.createNew();

    //Check if the ticket is created
    long firstId = ticketService.getFirst().orElseThrow().getId();
    long lastId = ticketService.removeLast().orElseThrow().getId();
    assert lastId == firstId;
    assert ticket.getId() == firstId;
    assert ticket.getOrder() == 0;
  }

  @Test
  public void testFirstTicket() {
    ticketService.removeAll();
    createTickets(REPEAT_COUNT);

    for (int i = 0; i < REPEAT_COUNT; i++) {
      Optional<TicketWithOrder> lowestTicket = ticketService.getFirstAndRemove();

      assert lowestTicket.isPresent();
      assert lowestTicket.get().getOrder() == 0;
    }
  }

  @Test
  public void testDeleteLastTicket() {
    ticketService.removeAll();
    createTickets(REPEAT_COUNT);

    Optional<TicketWithOrder> highestTicket = ticketService.removeLast();
    assert highestTicket.isPresent();
    assert highestTicket.get().getOrder() == REPEAT_COUNT - 1;
    assert ticketService.getAllTickets().size() == REPEAT_COUNT - 1;
  }

  @Test
  public void testGetAllTickets() {
    ticketService.removeAll();
    createTickets(REPEAT_COUNT);
    List<TicketWithOrder> allTickets = ticketService.getAllTickets();

    for (int i = 0; i < REPEAT_COUNT; i++) {
      assert allTickets.get(i).getOrder() == i;
    }
  }

  private void createTickets(int count) {
    for (int i = 0; i < count; i++) {
      ticketService.createNew();
    }
  }
}
