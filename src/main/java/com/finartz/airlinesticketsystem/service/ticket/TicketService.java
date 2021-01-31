package com.finartz.airlinesticketsystem.service.ticket;

import com.finartz.airlinesticketsystem.model.ticket.TicketDto;
import org.springframework.http.ResponseEntity;

public interface TicketService {

    //Ticket can be found with ticketNo(id)
    TicketDto findById(long ticketNo);
    ResponseEntity<Object> createTicket(TicketDto ticketDto);
    ResponseEntity<Object> cancelTicket(Long id);
}
