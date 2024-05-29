package cz.moneta.ticketsystem.controller;

import cz.moneta.ticketsystem.model.TicketWithOrder;
import cz.moneta.ticketsystem.service.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ticket")
public class TicketController {

  private final TicketService service;

  public TicketController(TicketService service) {
    this.service = service;
  }

  @GetMapping
  public List<TicketWithOrder> getAllTickets() {
    return service.getAllTicketsSorted();
  }

  @GetMapping("/create")
  public TicketWithOrder createTicket() {
    return service.createNewTicket();
  }

  @GetMapping("/current")
  public ResponseEntity<TicketWithOrder> getLowestTicket() {
    Optional<TicketWithOrder> lowestTicket = service.getLowestTicket();
    return lowestTicket.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
  }

  @GetMapping("/deleteLast")
  public ResponseEntity<TicketWithOrder> deleteLastTicket() {
    Optional<TicketWithOrder> highestTicket = service.removeHighestTicket();
    return highestTicket.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
  }
}
