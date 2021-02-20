package com.jc.spring.order.config;

import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jincheng.zhang
 * ContextRefreshedEvent 会在spring启动后执行这个Event
 */
@Component
public class SentinelConfig implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // 流控规则list
        List<FlowRule> rules = new ArrayList<>();
        // 规则创建
        FlowRule rule = new FlowRule();
        // 设置资源
        rule.setResource("orderId");
        // 配置 Qps
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rule.setCount(1);
        // 规则来源
//        rule.setLimitApp("orderApp");
        rules.add(rule);
        FlowRuleManager.loadRules(rules);

        // 熔断
        List<DegradeRule> degradeRules = new ArrayList<>();
        DegradeRule degradeRule = new DegradeRule();
        degradeRule.setResource("orderId");
        // 降级策略 （失败指标）
        // DEGRADE_GRADE_RT 一秒内响应时间超过设置值的个数
        // DEGRADE_GRADE_EXCEPTION_RATIO 一秒钟内异常比例  （且每秒请求>=5）
        // DEGRADE_GRADE_EXCEPTION_COUNT 一分钟内异常个数
        degradeRule.setGrade(RuleConstant.DEGRADE_GRADE_RT);
        // RT-> ms  Ratio 0~1   count int
        degradeRule.setCount(10);
        // 熔断时间窗口（回到半打开）单位是秒
        degradeRule.setTimeWindow(10);
        degradeRules.add(degradeRule);
        DegradeRuleManager.loadRules(degradeRules);
    }

    public static class CommonBlockedHandler{

    }
}
