package com.bocft.meeting.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Acui
 * @date 2022年07月15日 9:12
 * t_attendees 参会人员表对应实体类
 */

@TableName("t_attendees")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Attendees extends BaseEntity {
    @TableId(type = IdType.AUTO)
    private long aId;

    @TableField("a_uid")
    private long aUId;

    @TableField("a_cid")
    private long aCId;

    @TableField("a_msg_status")
    private long aMsgStatus;

    @TableField("a_ustatus")
    private int aUStatus;
}
