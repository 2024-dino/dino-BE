package khu.dino.common.scheduler;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class PushNotificationJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
            // 트리거된 푸시 알림 시간
        String pushDateTime = jobExecutionContext.getJobDetail().getJobDataMap().getString("pushDateTime");

        // 콘솔에 푸시 알림 출력
        System.out.println("푸쉬 알림이 발생할 시간 " + pushDateTime);
    }

}
