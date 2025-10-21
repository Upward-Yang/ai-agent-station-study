package cn.bugstack.ai.infrastructure.dao.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @Auther: yangjian
 * @Date: 2025-10-21 - 10 - 21 - 16:43
 * @Description: cn.bugstack.ai.infrastructure.dao.po
 * 智能体-客户端关联表
 * @version: 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AiAgentFlowConfig {
    /**
     * 主键ID
     */
    private Long id;

    /**
     * 智能体ID
     */
    private Long agentId;

    /**
     * 客户端ID
     */
    private Long clientId;

    /**
     * 序列号(执行顺序)
     */
    private Integer sequence;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
