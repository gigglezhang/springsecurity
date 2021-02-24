package com.jc.spring.order.config;

import com.alibaba.csp.sentinel.adapter.spring.webflux.callback.WebFluxCallbackManager;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.UrlCleaner;
import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.nacos.NacosDataSource;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.nacos.api.PropertyKeyConst;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author jincheng.zhang
 * ContextRefreshedEvent 会在spring启动后执行这个Event
 */
@ConfigurationProperties(prefix = "sentinel.nacos")
@Component
@Getter
@Setter
public class SentinelConfig implements ApplicationListener<ContextRefreshedEvent> {
   /* @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // 流控规则list
        List<FlowRule> rules = new ArrayList<>();
        // 规则创建
        FlowRule rule = new FlowRule();
        // 设置资源
        rule.setResource("order");
        // 配置 Qps
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rule.setCount(20);
        // 规则来源
//        rule.setLimitApp("orderApp");
        rules.add(rule);
        FlowRuleManager.loadRules(rules);

        // 熔断
        List<DegradeRule> degradeRules = new ArrayList<>();
        DegradeRule degradeRule = new DegradeRule();
        degradeRule.setResource("order");


        // 降级策略 （失败指标）
        // DEGRADE_GRADE_RT 一秒内响应时间超过设置值的个数
        // DEGRADE_GRADE_EXCEPTION_RATIO 一秒钟内异常比例  （且每秒请求>=5）
        // DEGRADE_GRADE_EXCEPTION_COUNT 一分钟内异常个数
        degradeRule.setGrade(RuleConstant.DEGRADE_GRADE_RT);
        // RT-> ms  Ratio 0~1   count int
        degradeRule.setCount(10);
        // 设置时间区间，默认是1000
        degradeRule.setStatIntervalMs(1000);
        // 设置最小请求个数，默认为5
        degradeRule.setMinRequestAmount(5);
        // 慢调用比例(0,1)  此处不能为1不知道为哈
        degradeRule.setSlowRatioThreshold(0.5);
        // 熔断时间窗口（回到半打开）单位是秒
        degradeRule.setTimeWindow(10);
        degradeRules.add(degradeRule);
        DegradeRuleManager.loadRules(degradeRules);

    } */


    public String serverAddr;
    public String groupId;
    public String dataId;
    public String username;
    public String password;
    public String namespace;

    @Override
    public void onApplicationEvent(@NonNull ContextRefreshedEvent event) {
        Properties properties = new Properties();
        properties.setProperty(PropertyKeyConst.SERVER_ADDR, serverAddr);
        properties.setProperty(PropertyKeyConst.USERNAME, username);
        properties.setProperty(PropertyKeyConst.PASSWORD, password);
        if(namespace !=null){
            properties.setProperty(PropertyKeyConst.NAMESPACE, namespace);
        }
        

        ReadableDataSource<String, List<FlowRule>> flowRuleDataSource =
                new NacosDataSource<>(properties, groupId, dataId,
                        source-> JSON.parseObject(source, new TypeReference<List<FlowRule>>(){}));
        FlowRuleManager.register2Property(flowRuleDataSource.getProperty());
    }
}
