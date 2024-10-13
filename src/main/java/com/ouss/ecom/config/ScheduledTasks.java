package com.ouss.ecom.config;

////    @Scheduled(cron = "0 0 * * * *")// This cron expression triggers the method every hour
//    @Scheduled(cron = "0 0/2 * * * *") // This cron expression triggers the method every 5 minutes

import com.ouss.ecom.dao.OrderRepo;
import com.ouss.ecom.dao.ReviewRepo;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {
    private final OrderRepo orderRepo;
    private final ReviewRepo reviewRepo;

    public ScheduledTasks(OrderRepo orderRepo, ReviewRepo reviewRepo) {
        this.orderRepo = orderRepo;
        this.reviewRepo = reviewRepo;
    }


    @Scheduled(cron = "0 0/20 * * * *")
    public void cleanupDatabase() {
        System.out.println("cleaniiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiing");
        orderRepo.deleteAll();
        reviewRepo.deleteAll();
    }
}