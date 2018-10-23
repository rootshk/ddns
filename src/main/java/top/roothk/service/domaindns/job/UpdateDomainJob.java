package top.roothk.service.domaindns.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import top.roothk.service.domaindns.service.ALiYunService;
import top.roothk.service.domaindns.util.NetWorkUtil;

@Component
public class UpdateDomainJob {

    @Autowired
    private ALiYunService aLiYunService;

    //本地IP地址
    private String clientIP;

    //免费版TTL时间为600s(10分钟), 所以程序每五分钟执行一次以保证性能和接口调用次数
    @Scheduled(cron = "0 0/5 * * * ?")
    public void updateDomain() {
        System.out.println("------------ 开始查看是否需要更新DNS -------------");

        //拿到当前的IP
        String newIP = NetWorkUtil.getOuterNetIp();

        if (null == newIP)
            return;

        //一样就跳过
        if (newIP.equals(clientIP))
            return;

        System.out.println("------------ 更新解析 -------------");
        //如果是空的,就直接传给阿里云再设置进去
        aLiYunService.updateDomainRecord("roothk.top", "ip", newIP, "A", 600L, 1L, "default");
        clientIP = newIP;
    }
}
