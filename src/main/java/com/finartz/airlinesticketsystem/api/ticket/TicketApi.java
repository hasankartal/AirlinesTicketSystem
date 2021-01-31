package com.finartz.airlinesticketsystem.api.ticket;

import com.finartz.airlinesticketsystem.exception.DataIntegrityViolationDbException;
import com.finartz.airlinesticketsystem.exception.ticket.TicketNotFoundException;
import com.finartz.airlinesticketsystem.model.ticket.TicketDto;
import com.finartz.airlinesticketsystem.service.ticket.impl.TicketServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
public class TicketApi {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TicketServiceImpl service;

    @GetMapping("/tickets/{ticketNo}")
    public TicketDto retrieveTicket(@PathVariable int ticketNo) {
        logger.info("retrieveTicket with id param service started");
        TicketDto ticket;
        try {
            ticket = service.findById(ticketNo);
            return ticket;
        } catch (TicketNotFoundException ex) {
            logger.error("Error in retrieveTicket service : {" + ex + " }");
            throw new TicketNotFoundException("TicketNo-" + ticketNo);
        }
    }

    @PostMapping("/ticket")
    public ResponseEntity<Object> createTicket(@Valid @RequestBody TicketDto ticketDto) {
        logger.info("createTicket service started");

        ResponseEntity<Object> savedTicket;
        try {
            savedTicket = service.createTicket(ticketDto);
            return savedTicket;
        } catch (DataIntegrityViolationDbException ex) {
            logger.error("Error in createTicket service Could not create ticket: {" + ex + " }");
            throw new DataIntegrityViolationDbException("Could not create ticket");
        }
    }

    @PutMapping("/cancelTicket/{id}")
    public ResponseEntity<Object> cancelTicket(@PathVariable Long id) {
        logger.info("cancelTicket service started");

        return service.cancelTicket(id);
    }
}
