package top.roothk.service.domaindns.service.Impl;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.alidns.model.v20150109.DescribeDomainRecordsRequest;
import com.aliyuncs.alidns.model.v20150109.DescribeDomainRecordsResponse;
import com.aliyuncs.alidns.model.v20150109.UpdateDomainRecordRequest;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.springframework.stereotype.Service;
import top.roothk.service.domaindns.service.ALiYunService;

import java.util.List;

@Service
public class ALiYunServiceImpl implements ALiYunService {

    private static IAcsClient client = null;
    static {
        String regionId = "cn-hangzhou"; //必填固定值，必须为“cn-hanghou”
        String accessKeyId = ""; // your accessKey
        String accessKeySecret = "";// your accessSecret
        IClientProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        // 若报Can not find endpoint to access异常，请添加以下此行代码
        // DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", "Alidns", "alidns.aliyuncs.com");
        client = new DefaultAcsClient(profile);
    }

    @Override
    public Boolean updateDomainRecord(String domain, String rR, String value, String type, Long ttl, Long priority, String line) {

        //要更新, 就要先查找
        String recordId = getDomainRecordId(domain, rR);
        if (null == recordId)
            return false;

        //设置解析值
        UpdateDomainRecordRequest updateDomainRecordRequest = new UpdateDomainRecordRequest();
        updateDomainRecordRequest.setRecordId(recordId);//域名ID
        updateDomainRecordRequest.setRR(rR);//解析的子域名
        updateDomainRecordRequest.setType(type);//解析类型
        updateDomainRecordRequest.setValue(value);//解析值
        updateDomainRecordRequest.setTTL(ttl);
        updateDomainRecordRequest.setPriority(priority);
        updateDomainRecordRequest.setLine(line);
//        UpdateDomainRecordResponse updateDomainRecordResponse;
        try {
//            updateDomainRecordResponse = client.getAcsResponse(updateDomainRecordRequest);
//            System.out.println(updateDomainRecordResponse.getRecordId());
            client.getAcsResponse(updateDomainRecordRequest);
            System.out.println("提示: ------------>> 更新解析成功");
            return true;
        } catch (ClientException e) {
//            e.printStackTrace();
            System.out.println("错误: ------------>> 更新错误, 可能该设置已存在");
        }
        return false;
    }

    public String getDomainRecordId(String domain, String recordName) {
        //获取域名的所有解析
        //获取列表
        DescribeDomainRecordsRequest describeDomainRecordsRequest = new DescribeDomainRecordsRequest();
        describeDomainRecordsRequest.setDomainName(domain);
        describeDomainRecordsRequest.setRRKeyWord("%" + recordName + "%");
        DescribeDomainRecordsResponse recordsResponse;
        try {
            recordsResponse = client.getAcsResponse(describeDomainRecordsRequest);
            List<DescribeDomainRecordsResponse.Record> list = recordsResponse.getDomainRecords();
            for (DescribeDomainRecordsResponse.Record record : list) {
                if (record.getRR().equals(recordName))
                    return record.getRecordId();
            }
        } catch (ClientException e) {
//            e.printStackTrace();
            System.out.println("错误: ------------>> 没有该域名");
        }
        return null;
    }
}
