package com.pizi.tools.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author 痞子
 * @since 2025-05-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_user_table")
public class TUserTable implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 用户编号
     */
    @TableId(value = "user_id", type = IdType.AUTO)
    @ApiModelProperty (value = "用户编号")
    private Integer userId;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    private String userName;

    /**
     * 用户状态 0存在，1,不存在
     */
    @ApiModelProperty(value = "用户状态 0存在，1,不存在")
    private Integer userStatus;

    /**
     * 0所有者；1管理；2普通用户
     */
    @ApiModelProperty(value = "0所有者；1管理；2普通用户")
    private Integer userGrade;

    /**
     * 修改时间YYYY-MM-DD HH:MM:SS
     */
    @ApiModelProperty(value = "修改时间YYYY-MM-DD HH:MM:SS")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 更改用户
     */
    @ApiModelProperty(hidden = true)
    private Integer updateUser;


}
