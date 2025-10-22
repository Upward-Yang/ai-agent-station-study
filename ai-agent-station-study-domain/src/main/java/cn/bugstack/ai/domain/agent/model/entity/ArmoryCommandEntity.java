package cn.bugstack.ai.domain.agent.model.entity;

import lombok.Data;

import java.util.List;

/**
 * 装配命令
 * @Auther: yangjian
 * @Date: 2025-10-22 - 10 - 22 - 11:07
 * @Description: cn.bugstack.ai.domain.agent.model.entity
 * @version: 1.0
 */
@Data
public class ArmoryCommandEntity {
    /**
     * 命令类型
     */
    private String commandType;

    /**
     * 命令索引（clientId、modelId、apiId...）
     */
    private List<String> commandIdList;
}
