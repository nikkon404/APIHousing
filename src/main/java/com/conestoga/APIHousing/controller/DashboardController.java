package com.conestoga.APIHousing.controller;

import com.conestoga.APIHousing.dtos.MaintenanceRequestDTO;
import com.conestoga.APIHousing.dtos.TransactionDTO;
import com.conestoga.APIHousing.interfaces.MaintenanceRequestRepository;
import com.conestoga.APIHousing.service.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.IsoFields;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DashboardController {
    UnitService unitService;
    ResidenceService residenceService;
    MaintenanceRequestService maintenanceRequestService;
    EventService eventService;
    TransactionService transactionService;

    AccountService accountService;
    private final MaintenanceRequestRepository maintenanceRequestRepository;

    public DashboardController(UnitService unitService, ResidenceService residenceService, MaintenanceRequestService maintenanceRequestService, EventService eventService,
                               TransactionService transactionService, AccountService accountService,
                               MaintenanceRequestRepository maintenanceRequestRepository) {
        this.unitService = unitService;
        this.residenceService = residenceService;
        this.maintenanceRequestService = maintenanceRequestService;
        this.eventService = eventService;
        this.transactionService = transactionService;
        this.accountService = accountService;
        this.maintenanceRequestRepository = maintenanceRequestRepository;
    }

    @GetMapping("/dashboard")
    public Map<String, Object> getDashboard() throws IOException {
//        Map<String, Object> map = new HashMap<>();
//        // Total users
//        map.put("Users", accountService.getAllAccounts().size());
//
//        // Total units
//        map.put("Units", unitService.getAllUnits().size());
//        map.put("Residences", residenceService.getAllResidences().size());
//
//        // Total events
//        map.put("Events", eventService.getAllEvents().size());
//
//        // Transactions
//        BigDecimal sumOfTransactions = BigDecimal.valueOf(0);
//        for (TransactionDTO transaction : transactionService.getAllTransactions()) {
//            sumOfTransactions = sumOfTransactions.add(transaction.getAmount());
//        }
//        map.put("Transactions", sumOfTransactions.intValue());
//
//        // Maintenance requests
//        int totalMaintenanceRequests = maintenanceRequestService.getAllMaintenanceRequests().size();
//        int solvedMaintenanceRequests = 0;
//
//        for (MaintenanceRequestDTO maintenanceRequestDTO : maintenanceRequestService.getAllMaintenanceRequests()) {
//            if (maintenanceRequestDTO.getRequestStatus() == 1) {
//                solvedMaintenanceRequests++;
//            }
//        }
//
//        map.put("Total Maintenance requests", totalMaintenanceRequests);
//        map.put("Solved Maintenance requests", solvedMaintenanceRequests);
//
//        double percentSolved = (double) solvedMaintenanceRequests / totalMaintenanceRequests * 100;
//        map.put("Percent Solved", percentSolved);
        Map<String, Object> map = new HashMap<>();
        // Total users
        map.put("Users", accountService.getAllAccounts().size());

        // Total units
        map.put("Units", unitService.getAllUnits().size());
        map.put("Residences", residenceService.getAllResidences().size());

        // Total events
        map.put("Events", eventService.getAllEvents().size());

        // Transactions
        BigDecimal sumOfTransactions = BigDecimal.valueOf(0);
        List<TransactionDTO> allTransactions = transactionService.getAllTransactions();
        for (TransactionDTO transaction : allTransactions) {
            sumOfTransactions = sumOfTransactions.add(transaction.getAmount());
        }
        map.put("Total revenue", sumOfTransactions.intValue());

        // Maintenance requests
        int totalMaintenanceRequests = maintenanceRequestService.getAllMaintenanceRequests().size();
        //int solvedMaintenanceRequests = maintenanceRequestService.getSolvedMaintenanceRequestsCount();
        map.put("Total Maintenance requests", totalMaintenanceRequests);
        //map.put("Solved Maintenance requests", solvedMaintenanceRequests);

        //Last 5 maintenance requests
        if(totalMaintenanceRequests >= 5 ){
            for(int i = 0; i < 5; i++){
                map.put("Maintenance request " + i, maintenanceRequestService.getAllMaintenanceRequests().get(i));
            }
        } else {
            for(int i = 0; i < totalMaintenanceRequests; i++){
                map.put("Maintenance request " + i, maintenanceRequestService.getAllMaintenanceRequests().get(i));
            }
        }


//        double percentSolved = (double) solvedMaintenanceRequests / totalMaintenanceRequests * 100;
//        map.put("Percent Solved", percentSolved);
//
//        // Percentage not solved
//        double percentNotSolved = 100 - percentSolved;
//        map.put("Percent Not Solved", percentNotSolved);

        // Percentage pending leases
//        int pendingLeases = accountService.getPendingLeasesCount();
//        double percentPendingLeases = (double) pendingLeases / accountService.getAllAccounts().size() * 100;
//        map.put("Percent Pending Leases", percentPendingLeases);
//
//         //Percentage approved/denied lease
//        int approvedLeases = accountService.getApprovedLeasesCount();
//        int deniedLeases = accountService.getDeniedLeasesCount();
//        double percentApprovedLeases = (double) approvedLeases / accountService.getAllAccounts().size() * 100;
//        double percentDeniedLeases = (double) deniedLeases / accountService.getAllAccounts().size() * 100;
//        map.put("Percent Approved Leases", percentApprovedLeases);
//        map.put("Percent Denied Leases", percentDeniedLeases);

        // Total amount of transactions per week for all 52 weeks
        allTransactions = transactionService.getAllTransactions();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");

        Map<Integer, BigDecimal> weeklyTransactions = allTransactions.stream()
                .collect(Collectors.groupingBy(transaction ->
                                LocalDateTime.parse(transaction.getCreatedAt().toString(), formatter).get(IsoFields.WEEK_OF_WEEK_BASED_YEAR),
                        Collectors.reducing(BigDecimal.ZERO, TransactionDTO::getAmount, BigDecimal::add)
                ));

// Assuming you want the total amount of transactions per week for all 52 weeks
        List<BigDecimal> totalAmountOfTransactionsPerWeek = new ArrayList<>();
        for (int weekNumber = 1; weekNumber <= 52; weekNumber++) {
            BigDecimal amount = weeklyTransactions.getOrDefault(weekNumber, BigDecimal.ZERO);
            totalAmountOfTransactionsPerWeek.add(amount);
        }
        map.put("Total Amount of Transactions Per Week", totalAmountOfTransactionsPerWeek.size());

        return map;
    }
}
