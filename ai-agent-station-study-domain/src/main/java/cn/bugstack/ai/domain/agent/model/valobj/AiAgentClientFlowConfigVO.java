package cn.bugstack.ai.domain.agent.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Agent客户端流程配置
 * @Auther: yangjian
 * @Date: 2025-10-27 - 10 - 27 - 15:02
 * @Description: cn.bugstack.ai.domain.agent.model.valobj
 * @version: 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AiAgentClientFlowConfigVO {
    /**
     * 客户端ID
     */
    private String clientId;

    /**
     * 客户端名称
     */
    private String clientName;

    /**
     * 客户端枚举
     */
    private String clientType;

    /**
     * 序列号(执行顺序)
     */
    private Integer sequence;
}
