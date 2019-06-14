package top.roothk.service.domaindns.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import top.roothk.service.domaindns.service.ALiYunService;
import top.roothk.service.domaindns.util.NetWorkUtil;

@Slf4j
@Component
public class UpdateDomainJob {

    @Value("${roothk.domain}")
    private String domain;
    @Value("${roothk.rr}")
    private String rr;
    @Value("${roothk.type}")
    private String type;
    @Value("${roothk.ttl}")
    private Long ttl;
    @Value("${roothk.priority}")
    private Long priority;
    @Value("${roothk.line}")
    private String line;

    private final ALiYunService aLiYunService;

    //本地IP地址
    private String clientIP;

    public UpdateDomainJob(ALiYunService aLiYunService) {
        this.aLiYunService = aLiYunService;
    }

    //免费版TTL时间为600s(10分钟), 所以程序每五分钟执行一次以保证性能和接口调用次数
    @Scheduled(cron = "0 0/5 * * * ?")
    public void updateDomain() {
        //拿到当前的IP
        String newIP = NetWorkUtil.getOuterNetIp();

        if (newIP.equals(""))
            return;

        //一样就跳过
        if (newIP.equals(clientIP))
            return;

        log.info("提示: ------------>> 需要更新解析 -------------");
        //如果是空的,就直接传给阿里云再设置进去
        Boolean isT = aLiYunService.updateDomainRecord(domain, rr, newIP, type, ttl, priority, line);
        if (isT)
            clientIP = newIP;
    }
}
