package cn.bugstack.ai.domain.agent.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * AI 提示词&动态规划，值对象
 * @Auther: yangjian
 * @Date: 2025-10-22 - 10 - 22 - 11:18
 * @Description: cn.bugstack.ai.domain.agent.model.valobj
 * @version: 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AiClientSystemPromptVO {
    /**
     * 提示词ID
     */
    private String promptId;

    /**
     * 提示词名称
     */
    private String promptName;

    /**
     * 提示词内容
     */
    private String promptContent;

    /**
     * 描述
     */
    private String description;
}
