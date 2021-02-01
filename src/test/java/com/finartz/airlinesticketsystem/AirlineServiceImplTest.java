package com.finartz.airlinesticketsystem;

import com.finartz.airlinesticketsystem.domain.airline.Airline;
import com.finartz.airlinesticketsystem.model.airline.AirlineDto;
import com.finartz.airlinesticketsystem.repository.airline.AirlineRepository;
import com.finartz.airlinesticketsystem.service.airline.impl.AirlineServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class AirlineServiceImplTest {

    @InjectMocks
    private AirlineServiceImpl airlineService;

    @Mock
    private AirlineRepository airlineRepository;

    @Test
    public void testFindAll() {
        Airline airline = new Airline();
        airline.setCompanyAddress("İstanbul");
        airline.setStatus(true);
        airline.setContactName("Hasan Kartal");
        airline.setDigitCode(235);
        airline.setIataCode("TK");
        airline.setIcaoCode("THY");
        airline.setName("Turkish Airlines");

        Mockito.when(airlineRepository.findAll()).thenReturn(Collections.singletonList(airline));
        List<AirlineDto> airlineDtos = airlineService.findAll();

        Assertions.assertEquals(airlineDtos.size(), 1);
    }
}
