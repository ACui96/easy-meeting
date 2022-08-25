package com.bocft.meeting.common;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.sms.v20210111.SmsClient;
import com.tencentcloudapi.sms.v20210111.models.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

/**
 * @author Acui
 * @date 2022年07月11日 16:57
 * 手机号相关工具类  - 发短信
 */
@Component
@Slf4j
public class PhoneUtil {
    public boolean phoneNumberIsValid(String phoneNumber){
        return Pattern.matches("^1[3-9]\\d{9}$", phoneNumber);
    }

    /**
     * 发送短信
     * @author Acui
     * @date 2022/7/11 17:07
     * @param phoneNumberSet1 手机号数组
     * @param templateParamSet 模板参数
     * @param templateId 模板id
     * @return
     */
    public SendSmsResponse sendMessage(String[] phoneNumberSet1, String[] templateParamSet, String templateId) {
        try{
            // 实例化一个认证对象，入参需要传入腾讯云账户secretId，secretKey,此处还需注意密钥对的保密
            // 密钥可前往https://console.cloud.tencent.com/cam/capi网站进行获取
            Credential cred = new Credential("yoursecretid", "yoursecretkey");
            // 实例化一个http选项，可选的，没有特殊需求可以跳过
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("sms.tencentcloudapi.com");
            // 实例化一个client选项，可选的，没有特殊需求可以跳过
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);
            // 实例化要请求产品的client对象,clientProfile是可选的
            SmsClient client = new SmsClient(cred, "ap-nanjing", clientProfile);
            // 实例化一个请求对象,每个接口都会对应一个request对象
            SendSmsRequest req = new SendSmsRequest();
//            String[] phoneNumberSet1 = {phone};
            req.setPhoneNumberSet(phoneNumberSet1);

            //replace smsSdkAppid with yours
            req.setSmsSdkAppId("SmsSdkAppId");
            // replace SignName with yours
            req.setSignName("SignName");

            req.setTemplateId(templateId);
            req.setTemplateParamSet(templateParamSet);

            // 返回的resp是一个SendSmsResponse的实例，与请求对象对应
            SendSmsResponse resp = client.SendSms(req);
            // 输出json格式的字符串回包
            System.out.println(SendSmsResponse.toJsonString(resp));
            return resp;
        } catch (TencentCloudSDKException e) {
            System.out.println(e.toString());
        }
        return null;
    }
}
