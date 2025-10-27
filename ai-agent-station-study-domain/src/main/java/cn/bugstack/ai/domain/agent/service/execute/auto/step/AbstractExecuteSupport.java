package cn.bugstack.ai.domain.agent.service.execute.auto.step;

import cn.bugstack.ai.domain.agent.adapter.repository.IAgentRepository;
import cn.bugstack.ai.domain.agent.model.entity.ExecuteCommandEntity;
import cn.bugstack.ai.domain.agent.model.valobj.enums.AiAgentEnumVO;
import cn.bugstack.ai.domain.agent.service.execute.auto.step.factory.DefaultAutoAgentExecuteStrategyFactory;
import cn.bugstack.wrench.design.framework.tree.AbstractMultiThreadStrategyRouter;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.ApplicationContext;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * @Auther: yangjian
 * @Date: 2025-10-27 - 10 - 27 - 15:19
 * @Description: cn.bugstack.ai.domain.agent.service.execute.auto.step
 * @version: 1.0
 */
public abstract class AbstractExecuteSupport extends
        AbstractMultiThreadStrategyRouter<ExecuteCommandEntity, DefaultAutoAgentExecuteStrategyFactory.DynamicContext, String> {
    private final Logger log = LoggerFactory.getLogger(AbstractExecuteSupport.class);

    @Resource
    protected ApplicationContext applicationContext;

    @Resource
    protected IAgentRepository repository;

    public static final String CHAT_MEMORY_CONVERSATION_ID_KEY = "chat_memory_conversation_id";
    public static final String CHAT_MEMORY_RETRIEVE_SIZE_KEY = "chat_memory_response_size";

    @Override
    protected void multiThread(ExecuteCommandEntity requestParameter
            , DefaultAutoAgentExecuteStrategyFactory.DynamicContext dynamicContext) throws ExecutionException, InterruptedException, TimeoutException {

    }

    protected ChatClient getChatClientByClientId(String clientId) {
        return getBean(AiAgentEnumVO.AI_CLIENT.getBeanName(clientId));
    }

    protected <T> T getBean(String beanName) {
        return (T) applicationContext.getBean(beanName);
    }
}
