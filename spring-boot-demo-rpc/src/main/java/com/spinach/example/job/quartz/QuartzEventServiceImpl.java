package com.spinach.example.job.quartz;

//import java.awt.Event;
import java.util.Date;

import org.json.JSONObject;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuartzEventServiceImpl implements QuartzEventService {
	private static final String JOB_GROUP = "event_job_group";
	private static final String TRIGGER_GROUP = "event_trigger_group";
	@Autowired
	private Scheduler scheduler;

	@Override
    public void addQuartz(Event event) throws SchedulerException {
        JSONObject eventData = JSONObject.parseObject(event.getEventData());
        Date triggerDate = eventData.getDate("date");
        JobDetail job = JobBuilder.newJob(EventJob.class)
        		.withIdentity(event.getId().toString(), JOB_GROUP)
        		.usingJobData(buildJobDateMap(event)).build();
        Trigger trigger = TriggerBuilder.newTrigger()
        		.withIdentity(event.getId().toString(), TRIGGER_GROUP)
        		.startAt(triggerDate).build();
        scheduler.scheduleJob(job, trigger);
    }