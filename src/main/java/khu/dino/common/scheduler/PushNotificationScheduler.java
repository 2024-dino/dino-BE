package khu.dino.common.scheduler;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Component
@Slf4j
public class PushNotificationScheduler {


    @Async
    public void schedulePushNotification(List<LocalDate> pushDates) throws SchedulerException {
        // 푸시 알림 스케줄링
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();

        for(LocalDate pushDate : pushDates) {
            LocalDateTime pushDateTime = pushDate.atTime(LocalTime.of(12, 0));
            Date triggerDate = Date.from(pushDateTime.atZone(ZoneId.systemDefault()).toInstant());

            // JobDetail 생성
            JobDetail job = JobBuilder.newJob(PushNotificationJob.class)
                    .withIdentity("job_" + pushDateTime, "test1") //여기에 사용자 ID_이벤트 ID로 구성
                    .usingJobData("pushDateTime", pushDateTime.toString())  // PushNotificationJob에서 사용할 데이터
                    .build();

            // Trigger 생성
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("trigger_" + pushDateTime, "test1")
                    .startAt(triggerDate)  // 실행 시간을 설정
//                    .startAt(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).plusSeconds(10).toInstant()))
//                    .withSchedule(SimpleScheduleBuilder.simpleSchedule()
//                            .withMisfireHandlingInstructionFireNow())  // 스케줄이 지났을 경우 즉시 실행
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule().withRepeatCount(0).withIntervalInSeconds(0).withMisfireHandlingInstructionFireNow())
                    .build();

            // 스케줄러에 작업과 트리거 등록
            scheduler.scheduleJob(job, trigger);
        }
    }


}
