package cz.ticketsystem.controller;

import cz.ticketsystem.model.TicketWithOrder;
import cz.ticketsystem.service.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class TicketController {

  private final TicketService ticketService;

  public TicketController(TicketService service) {
    this.ticketService = service;
  }

  @GetMapping("/all")
  public List<TicketWithOrder> getAllTickets() {
    return ticketService.getAllTickets();
  }

  @GetMapping("/create")
  public TicketWithOrder createTicket() {
    return ticketService.createNew();
  }

  @GetMapping("/first")
  public ResponseEntity<TicketWithOrder> getFirstTicket() {
    Optional<TicketWithOrder> lowestTicket = ticketService.getFirst();
    return lowestTicket.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
  }

  // TODO: could be made as a delete method, but still... it is returning a TicketWithOrder
  @GetMapping("/deleteFirst")
  public ResponseEntity<TicketWithOrder> getFirstAndDelete() {
    Optional<TicketWithOrder> lowestTicket = ticketService.getFirstAndRemove();
    return lowestTicket.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
  }

  // TODO: could be made as a delete method, but still... it is returning a TicketWithOrder
  @GetMapping("/deleteLast")
  public ResponseEntity<TicketWithOrder> deleteLastTicket() {
    Optional<TicketWithOrder> highestTicket = ticketService.removeLast();
    return highestTicket.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
  }
}
