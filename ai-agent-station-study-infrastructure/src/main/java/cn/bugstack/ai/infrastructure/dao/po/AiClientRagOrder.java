package cn.bugstack.ai.infrastructure.dao.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 知识库配置表
 * @Auther: yangjian
 * @Date: 2025-10-21 - 10 - 21 - 16:57
 * @Description: cn.bugstack.ai.infrastructure.dao.po
 * @version: 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AiClientRagOrder {
    /**
     * 主键ID
     */
    private Long id;

    /**
     * 知识库ID
     */
    private String ragId;

    /**
     * 知识库名称
     */
    private String ragName;

    /**
     * 知识标签
     */
    private String knowledgeTag;

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
