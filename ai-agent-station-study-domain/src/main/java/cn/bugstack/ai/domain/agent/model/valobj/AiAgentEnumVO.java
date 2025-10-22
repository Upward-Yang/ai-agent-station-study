package cn.bugstack.ai.domain.agent.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Auther: yangjian
 * @Date: 2025-10-22 - 10 - 22 - 11:13
 * @Description: cn.bugstack.ai.domain.agent.model.valobj
 * @version: 1.0
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum AiAgentEnumVO {
    AI_CLIENT("客户端", "client", "ai_client_", "aiClientLoadDataStrategy"),
    AI_CLIENT_MODEL("对话模型", "model", "ai_client_model_", "aiClientModelLoadDataStrategy"),

            ;

    /**
     * 名称
     */
    private String name;

    /**
     * code
     */
    private String code;

    /**
     * Bean 对象名称标签
     */
    private String beanNameTag;

    /**
     * 装配数据策略
     */
    private String loadDataStrategy;
}
