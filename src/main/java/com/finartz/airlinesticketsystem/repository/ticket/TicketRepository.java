package com.finartz.airlinesticketsystem.repository.ticket;

import com.finartz.airlinesticketsystem.domain.ticket.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    @Query("SELECT count(u.id) FROM Ticket u WHERE u.status = true and u.transactionStatus = :transactionStatus" +
            " and u.flightTicket.id = :flightTicketId ")
    Long countByTransactionStatus(@Param("transactionStatus") String transactionStatus, @Param("flightTicketId") Long flightTicketId);
}
