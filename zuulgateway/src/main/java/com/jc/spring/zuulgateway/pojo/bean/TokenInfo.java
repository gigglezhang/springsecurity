package com.jc.spring.zuulgateway.pojo.bean;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author jincheng.zhang
 */
@Data
public class TokenInfo {
    private boolean active;

    private String client_id;
    private String user_name;
    private List<String> aud;
    private List<String> scope;
    private Date exp;
    private List<String> authorities;

}
