package com.fujin.bootdemo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

// 实体类
@Data
public class User {
    private Integer id;

    private String username;

    private Byte gender;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate birthday;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime loginTime;

    private String loginIp;

    private Byte type;

    private String phone;

    private String avatar;

    private String wxOpenid;

    private String sessionKey;

    private Byte status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private String createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private String updateTime;
}
