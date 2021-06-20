package com.dragon.system.quartz.utils;

import com.dragon.common.utils.SpringContextHolder;
import com.dragon.common.utils.ThrowableUtil;
import com.dragon.system.quartz.mapper.QuartzLogMapper;
import com.dragon.system.quartz.model.QuartzJobModel;
import com.dragon.system.quartz.model.QuartzLogModel;
import com.dragon.system.quartz.service.QuartzJobService;
import com.dragon.thread.config.ThreadPoolExecutorUtil;
import org.apache.commons.lang3.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

@Async
public class ExecutionJob extends QuartzJobBean {
    private static final Logger log = LoggerFactory.getLogger(ExecutionJob.class);

    private final static ThreadPoolExecutor EXECUTOR = ThreadPoolExecutorUtil.getThreadPool();

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        QuartzJobModel quartzJob = (QuartzJobModel) context.getMergedJobDataMap().get(QuartzJobModel.JOB_KEY);
        QuartzLogMapper quartzLogMapper = SpringContextHolder.getBean(QuartzLogMapper.class);
        QuartzJobService quartzJobService = SpringContextHolder.getBean(QuartzJobService.class);

        Date now = new Date();
        QuartzLogModel logModel = new QuartzLogModel();
        logModel.setJobName(quartzJob.getJobName());
        logModel.setBeanName(quartzJob.getBeanName());
        logModel.setMethodName(quartzJob.getMethodName());
        logModel.setParams(quartzJob.getParams());
        logModel.setCronExpression(quartzJob.getCronExpression());
        logModel.setCreateTime(new Timestamp(now.getTime()));

        long startTime = System.currentTimeMillis();
        try {
            log.info("任务开始执行，任务名称：{}", quartzJob.getJobName());
            ConcreteQuartzTaskRunnable task = new ConcreteQuartzTaskRunnable(quartzJob.getBeanName(), quartzJob.getMethodName(), quartzJob.getParams());
            Future<?> future = EXECUTOR.submit(task);
            future.get();
            long durationTime = System.currentTimeMillis() - startTime;
            // 执行耗时
            logModel.setTime(durationTime);
            // 任务状态
            logModel.setIsSuccess(true);
            log.info("任务执行完毕，任务名称：{}，执行时间：{}毫秒", quartzJob.getJobName(), durationTime);
        } catch (Exception e) {
            long durationTime = System.currentTimeMillis() - startTime;
            logModel.setTime(durationTime);
            // 任务状态
            logModel.setIsSuccess(false);
            // 异常详情
            logModel.setExceptionDetail(ThrowableUtil.getStackTrace(e));
            // 任务如果失败了则暂停
            if(Objects.nonNull(quartzJob.getPauseAfterFailure()) && quartzJob.getPauseAfterFailure()) {
                quartzJob.setIsPause(true);
                // 更新状态
                quartzJobService.updateIsPause(quartzJob);
            }
            if (StringUtils.isNotBlank(quartzJob.getEmail())) {
//                IEmailService emailService = SpringContextHolder.getBean(IEmailService.class);
//                // 邮箱报警
//                EmailVo emailVo = taskAlarm(quartzJob, ThrowableUtil.getStackTrace(e));
//                emailService.send(emailVo, emailService.find());
            }
            log.error("任务执行失败，任务名称：{}，执行时间：{}毫秒", quartzJob.getJobName(), durationTime, e);
        } finally {
            quartzLogMapper.insert(logModel);
        }
    }



//    @Override
//    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
//        QuartzJob quartzJob = (QuartzJob) context.getMergedJobDataMap().get(QuartzJob.JOB_KEY);
//        // 获取spring bean
//        QuartzLogRepository quartzLogRepository = SpringContextHolder.getBean(QuartzLogRepository.class);
//        RedisUtils redisUtils = SpringContextHolder.getBean(RedisUtils.class);
//        String uuid = quartzJob.getUuid();
//
//        QuartzLog quartzLog = new QuartzLog();
//        quartzLog.setJobName(quartzJob.getJobName());
//        quartzLog.setBeanName(quartzJob.getBeanName());
//        quartzLog.setMethodName(quartzJob.getMethodName());
//        quartzLog.setParams(quartzJob.getParams());
//        quartzLog.setCronExpression(quartzJob.getCronExpression());
//        long startTime = System.currentTimeMillis();
//        try {
//            log.info("任务开始执行，任务名称：{}", quartzJob.getJobName());
//            ConcreteQuartzTaskRunnable task = new ConcreteQuartzTaskRunnable(quartzJob.getBeanName(), quartzJob.getMethodName(), quartzJob.getParams());
//            Future<?> future = EXECUTOR.submit(task);
//            future.get();
//            long durationTime = System.currentTimeMillis() - startTime;
//            quartzLog.setTime(durationTime);
//            // 任务状态
//            quartzLog.setIsSuccess(true);
//            log.info("任务执行完毕，任务名称：{}，执行时间：{}毫秒", quartzJob.getJobName(), durationTime);
//            if(StringUtils.isNotBlank(uuid)) {
//                redisUtils.set(uuid, true);
//            }
//            // 判断是否存在子任务
//            if(StringUtils.isNotEmpty(quartzJob.getSubTask())) {
//                String[] tasks = quartzJob.getSubTask().split("[,，]");
////                // 执行子任务
////                quartzJobService.executionSubJob(tasks);
//            }
//        } catch (Exception e) {


//            if(Objects.nonNull(quartzJob.getEmail())) {
//                EmailConfigService emailConfigService = SpringContextHolder.getBean(EmailConfigService.class);
//                // 邮箱报警
//                EmailVo emailVo = taskAlarm(quartzJob, ThrowableUtil.getStackTrace(e));
//                emailConfigService.send(emailVo, emailConfigService.find());
//            }
//            if(StringUtils.isNotBlank(uuid)) {
//                redisUtils.set(uuid, false);
//            }
//
//        } finally {
//            quartzLogRepository.save(quartzLog);
//        }
//    }
//
//    private EmailVo taskAlarm(QuartzJob quartzJob, String msg) {
//        EmailVo emailVo = new EmailVo();
//        emailVo.setSubject("定时任务【"+ quartzJob.getJobName() +"】执行失败，请尽快处理！");
//        Map<String, Object> data = new HashMap<>(16);
//        data.put("task", quartzJob);
//        data.put("msg", msg);
//        TemplateEngine engine = TemplateUtil.createEngine(new TemplateConfig("template", TemplateConfig.ResourceMode.CLASSPATH));
//        Template template = engine.getTemplate("email/taskAlarm.ftl");
//        emailVo.setContent(template.render(data));
//        List<String> emails = Arrays.asList(quartzJob.getEmail().split("[,，]"));
//        emailVo.setTos(emails);
//        return emailVo;
//    }








}
