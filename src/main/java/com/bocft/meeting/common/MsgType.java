package com.bocft.meeting.common;

public enum MsgType {
    //    预约回复模板（未通过/已通过）
    REPLY("1467996", "预约回复模板（未通过/已通过）", "会议室预约申请{1}，会议室名称：{2},预约时间：{3}，详情请前往个人中心查看，祝您工作愉快。"),
//    预约结果通知-多参数
    RE_MUL_PARM("1470256", "预约结果通知-多参数", "会议室预约申请{1}，会议室名称：{2},预约时间：{3}，{4}-{5}，祝您工作愉快。"),
//    验证码-通用
    VALID_CODE("1471039", "验证码-通用", "{1}为您的验证码，请于{2}分钟内填写，如非本人操作，请忽略本短信。"),
//    申请未通过-通知模板
    REJECT_INFORM("1471528","申请未通过-通知模板","尊敬的{1}您好，会议室预约申请{2}，会议室名称：{3},预约时间：{4}，{5}-{6}，请重新申请。"),
    //    群发-通知会议详情
    INFORM_CON_DETAILS("1471530", "群发-通知会议详情", "会议通知：尊敬的{1}您好，请于{2}{3}-{4}准时到{5}参加{6}会议，非本人请忽略此短信。"),
    //会议室删除后通知模板
    AFTER_ROOM_DEL("1478420", "会议室删除后通知模板", "抱歉，您所预定的会议室{1}场所已关闭，请谅解。如还需会议室，请到系统重新预约会议室。感谢配合！");


    private String tmpId;
    private String usage;
    private String content;



    MsgType(String tmpId, String message, String content) {
        this.tmpId = tmpId;
        this.usage = message;
        this.content = content;
    }

    public String getTmpId() {
        return tmpId;
    }

    public void setTmpId(String tmpId) {
        this.tmpId = tmpId;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
