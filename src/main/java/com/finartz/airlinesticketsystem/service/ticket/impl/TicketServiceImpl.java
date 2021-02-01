package com.finartz.airlinesticketsystem.service.ticket.impl;

import com.finartz.airlinesticketsystem.domain.flight.Flight;
import com.finartz.airlinesticketsystem.domain.ticket.Ticket;
import com.finartz.airlinesticketsystem.exception.DataIntegrityViolationDbException;
import com.finartz.airlinesticketsystem.exception.flight.FlightNotFoundException;
import com.finartz.airlinesticketsystem.exception.ticket.TicketNotFoundException;
import com.finartz.airlinesticketsystem.model.airport.AirportDto;
import com.finartz.airlinesticketsystem.model.flight.FlightDto;
import com.finartz.airlinesticketsystem.model.route.RouteDto;
import com.finartz.airlinesticketsystem.model.ticket.TicketDto;
import com.finartz.airlinesticketsystem.repository.ticket.TicketRepository;
import com.finartz.airlinesticketsystem.service.flight.impl.FlightServiceImpl;
import com.finartz.airlinesticketsystem.service.ticket.TicketService;
import com.finartz.airlinesticketsystem.util.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.DateTimeException;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final FlightServiceImpl flightService;

    @Override
    public TicketDto findById(long ticketNo) throws TicketNotFoundException {
        Optional<Ticket> ticket = ticketRepository.findById(ticketNo);
        if (!ticket.isPresent()) {
            throw new TicketNotFoundException("Ticket-" + ticketNo);
        }

        TicketDto ticketDto = new TicketDto();
        ticketDto.setId(ticket.get().getId());

        FlightDto flightDto = new FlightDto();
        flightDto.setId(ticket.get().getFlightTicket().getId());

        RouteDto routeDto = new RouteDto();
        routeDto.setId(ticket.get().getFlightTicket().getRouteFlight().getId());

        AirportDto airportRouteFrom = new AirportDto();
        airportRouteFrom.setId(ticket.get().getFlightTicket().getRouteFlight().getAirportRouteFrom().getId());
        airportRouteFrom.setAirportCode(ticket.get().getFlightTicket().getRouteFlight().getAirportRouteFrom().getAirportCode());
        airportRouteFrom.setAirportName(ticket.get().getFlightTicket().getRouteFlight().getAirportRouteFrom().getAirportName());
        airportRouteFrom.setCity(ticket.get().getFlightTicket().getRouteFlight().getAirportRouteFrom().getCity());
        airportRouteFrom.setCountry(ticket.get().getFlightTicket().getRouteFlight().getAirportRouteFrom().getCountry());
        airportRouteFrom.setCountryAreaCode(ticket.get().getFlightTicket().getRouteFlight().getAirportRouteFrom().getCountryAreaCode());
        airportRouteFrom.setCountryCode(ticket.get().getFlightTicket().getRouteFlight().getAirportRouteFrom().getCountryCode());
        airportRouteFrom.setStatus(ticket.get().getFlightTicket().getRouteFlight().getAirportRouteFrom().getStatus());
        routeDto.setAirportRouteFrom(airportRouteFrom);

        AirportDto airportRouteTo = new AirportDto();
        airportRouteTo.setId(ticket.get().getFlightTicket().getRouteFlight().getAirportRouteTo().getId());
        airportRouteTo.setAirportCode(ticket.get().getFlightTicket().getRouteFlight().getAirportRouteTo().getAirportCode());
        airportRouteTo.setAirportName(ticket.get().getFlightTicket().getRouteFlight().getAirportRouteTo().getAirportName());
        airportRouteTo.setCity(ticket.get().getFlightTicket().getRouteFlight().getAirportRouteTo().getCity());
        airportRouteTo.setCountry(ticket.get().getFlightTicket().getRouteFlight().getAirportRouteTo().getCountry());
        airportRouteTo.setCountryAreaCode(ticket.get().getFlightTicket().getRouteFlight().getAirportRouteTo().getCountryAreaCode());
        airportRouteTo.setCountryCode(ticket.get().getFlightTicket().getRouteFlight().getAirportRouteTo().getCountryCode());
        airportRouteTo.setStatus(ticket.get().getFlightTicket().getRouteFlight().getAirportRouteTo().getStatus());
        routeDto.setAirportRouteTo(airportRouteTo);
        routeDto.setStatus(ticket.get().getFlightTicket().getRouteFlight().getStatus());
        flightDto.setRouteDto(routeDto);

        flightDto.setFlightDate(ticket.get().getFlightTicket().getFlightDate());
        flightDto.setFlightFee(ticket.get().getFlightTicket().getFlightFee());
        flightDto.setPassengerCount(ticket.get().getFlightTicket().getPassengerCount());
        flightDto.setFlightFeePresent(ticket.get().getFlightTicket().getFlightFeePresent());
        flightDto.setPassengerCountPresent(ticket.get().getFlightTicket().getPassengerCountPresent());
        flightDto.setStatus(ticket.get().getFlightTicket().getStatus());
        ticketDto.setFlightDto(flightDto);

        ticketDto.setCustomerName(ticket.get().getCustomerName());
        ticketDto.setCardNo(ticket.get().getCardNo());
        ticketDto.setStatus(ticket.get().getStatus());
        ticketDto.setTransactionStatus(ticket.get().getTransactionStatus());
        ticketDto.setFlightFee(ticket.get().getFlightFee());

        return ticketDto;
    }

    @Override
    public ResponseEntity<Object> createTicket(TicketDto ticketDto) {
        Ticket ticket = new Ticket();

        String card = ticketDto.getCardNo().replaceAll("[^0-9]", "");
        card = Utils.maskCardNumber(card, Utils.mask);
        ticket.setCardNo(card);

        FlightDto flightDto;
        try {
            flightDto = flightService.findById(ticketDto.getFlightDto().getId());
        } catch (FlightNotFoundException ex) {
            throw new FlightNotFoundException("Flight id-" + ticketDto.getFlightDto().getId());
        }
        Flight flight = new Flight();
        flight.setId(flightDto.getId());
        ticket.setFlightTicket(flight);

        Date flightDate = flightDto.getFlightDate();
        Calendar today = Calendar.getInstance();
        Date now = today.getTime();
        if(flightDate.before(now)) {
            throw new DateTimeException("It is not allowed to buy ticket");
        }

        Long countOfTransactionStatus = ticketRepository.countByTransactionStatus("N",flightDto.getId());
        if (flightDto.getPassengerCountPresent() <= countOfTransactionStatus) {
            throw new DataIntegrityViolationDbException("The flight is full.");
        }
        ticket.setFlightFee(flightDto.getFlightFeePresent());
        ticket.setTransactionStatus("N");
        ticket.setStatus(true);
        ticket.setCustomerName(ticketDto.getCustomerName());

        Ticket savedTicket;
        try{
            savedTicket = ticketRepository.save(ticket);
        } catch (DataIntegrityViolationException ex) {
            throw new DataIntegrityViolationDbException("Could not create ticket in db");
        }

        URI location =
                ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                        .buildAndExpand(savedTicket.getId())
                        .toUri();

        return ResponseEntity.created(location).build();
    }

    public ResponseEntity<Object> cancelTicket(@RequestParam(required = false) Long id) {
        Optional<Ticket> ticketOptional = ticketRepository.findById(id);

        if (!ticketOptional.isPresent())
            return ResponseEntity.notFound().build();

        Date flightDate = ticketOptional.get().getFlightTicket().getFlightDate();
        Calendar today = Calendar.getInstance();
        Date now = today.getTime();
        if(flightDate.before(now)) {
            throw new DateTimeException("It is not allowed to buy ticket");
        }

        Ticket ticket = ticketOptional.get();
        ticket.setTransactionStatus("C");
        ticketRepository.save(ticket);

        return ResponseEntity.noContent().build();
    }

}
