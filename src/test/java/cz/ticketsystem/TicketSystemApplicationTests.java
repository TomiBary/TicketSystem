package cz.moneta.ticketsystem;

import cz.moneta.ticketsystem.model.TicketWithOrder;
import cz.moneta.ticketsystem.service.TicketService;
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

    TicketWithOrder ticket_1 = ticketService.createNewTicket();

    //Check if the ticket is created
    long id = ticketService.getHighestTicketId();
    assert ticket_1.getId() == id;
    assert 1 == id;
    assert ticket_1.getOrder() == 0;
  }

  @Test
  public void testGetLowestTicket() {
    createTickets();

    for (int i = 0; i < REPEAT_COUNT; i++) {
      Optional<TicketWithOrder> lowestTicket = ticketService.getAndRemoveLowestTicket();

      assert lowestTicket.isPresent();
      assert lowestTicket.get().getOrder() == 0;
      assert lowestTicket.get().getId() == i + 1;
    }
  }

  @Test
  public void testDeleteLastTicket() {
    createTickets();

    Optional<TicketWithOrder> highestTicket = ticketService.removeHighestTicket();
    assert highestTicket.isPresent();
    assert highestTicket.get().getOrder() == REPEAT_COUNT - 1;
    assert highestTicket.get().getId() == REPEAT_COUNT;
    assert ticketService.getAllTicketsSorted().size() == REPEAT_COUNT - 1;
  }

  @Test
  public void testGetAllTicketsSorted() {
    createTickets();
    List<TicketWithOrder> allTickets = ticketService.getAllTicketsSorted();

    for (int i = 0; i < REPEAT_COUNT; i++) {
      assert allTickets.get(i).getOrder() == i;
      assert allTickets.get(i).getId() == i + 1;
    }
  }

  private void createTickets() {
    for (int i = 0; i < REPEAT_COUNT; i++) {
      ticketService.createNewTicket();
    }
  }
}
