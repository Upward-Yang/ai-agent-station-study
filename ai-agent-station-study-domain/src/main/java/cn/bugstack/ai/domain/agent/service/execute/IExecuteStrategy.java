package cn.bugstack.ai.domain.agent.service.execute;

import cn.bugstack.ai.domain.agent.model.entity.ExecuteCommandEntity;

/**
 * 执行策略接口
 * @Auther: yangjian
 * @Date: 2025-10-27 - 10 - 27 - 15:15
 * @Description: cn.bugstack.ai.domain.agent.service.execute
 * @version: 1.0
 */
public interface IExecuteStrategy {
    void execute(ExecuteCommandEntity requestParameter) throws Exception;
}
