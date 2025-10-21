package cn.bugstack.ai.infrastructure.dao.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @Auther: yangjian
 * @Date: 2025-10-21 - 10 - 21 - 16:42
 * @Description: cn.bugstack.ai.infrastructure.dao.po
 * AI智能体配置表
 * @version: 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AiAgent {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 智能体ID
     */
    private String agentId;

    /**
     * 智能体名称
     */
    private String agentName;

    /**
     * 描述
     */
    private String description;

    /**
     * 渠道类型(agent，chat_stream)
     */
    private String channel;

    /**
     * 状态(0:禁用,1:启用)
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
