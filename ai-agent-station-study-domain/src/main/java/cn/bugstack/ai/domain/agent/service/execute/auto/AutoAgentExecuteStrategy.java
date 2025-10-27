package cn.bugstack.ai.domain.agent.service.execute.auto;

import cn.bugstack.ai.domain.agent.model.entity.ExecuteCommandEntity;
import cn.bugstack.ai.domain.agent.service.execute.IExecuteStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 自动执行策略
 * @Auther: yangjian
 * @Date: 2025-10-27 - 10 - 27 - 15:17
 * @Description: cn.bugstack.ai.domain.agent.service.execute.auto
 * @version: 1.0
 */
@Slf4j
@Service
public class AutoAgentExecuteStrategy implements IExecuteStrategy {
    @Override
    public void execute(ExecuteCommandEntity requestParameter) throws Exception {

    }
}
