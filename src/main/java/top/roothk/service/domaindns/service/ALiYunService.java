package top.roothk.service.domaindns.service;

/**
 * 阿里云服务
 */
public interface ALiYunService {

    /**
     * 更新云解析
     * @param domain    主域名, 如 roothk.top 不能为空
     * @param rR        二级域名, 根域名为@ 不能为空
     * @param value     更新的指, 如 127.0.0.1, 默认127.0.0.1
     * @param type      解析类型, 如 A, 默认A
     * @param ttl       TTL有效时长, 默认600
     * @param priority  有限级 [1, 10] 默认1
     * @param line      解析线路, 默认default
     */
    void updateDomainRecord(String domain, String rR, String value, String type, Long ttl, Long priority, String line);

    /**
     * 获取指定的ID
     * @param domain
     * @param recordName
     * @return
     */
    String getDomainRecordId(String domain, String recordName);

}
