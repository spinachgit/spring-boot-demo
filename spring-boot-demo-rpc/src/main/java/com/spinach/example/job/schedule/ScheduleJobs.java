package com.spinach.example.job.schedule;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.time.FastDateFormat;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduleJobs {
    public final static long SECOND = 1 * 1000;
    FastDateFormat fdf = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");


    @Scheduled(fixedDelay = SECOND * 2)
    public void fixedDelayJob() throws InterruptedException {
        TimeUnit.SECONDS.sleep(2);
        System.out.println("[FixedDelayJob Execute]"+fdf.format(new Date()));
    }
    
    @Scheduled(cron = "0/4 * * * * ?")
    public void cronJob() {
        System.out.println("[CronJob Execute]"+fdf.format(new Date()));
    }
}