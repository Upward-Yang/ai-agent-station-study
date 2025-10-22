package cn.bugstack.ai.domain.agent.service.armory;

import cn.bugstack.ai.domain.agent.model.entity.ArmoryCommandEntity;
import cn.bugstack.ai.domain.agent.service.armory.business.data.ILoadDataStrategy;
import cn.bugstack.ai.domain.agent.service.armory.factory.DefaultArmoryStrategyFactory;
import cn.bugstack.wrench.design.framework.tree.StrategyHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * 根节点，数据加载
 * @Auther: yangjian
 * @Date: 2025-10-22 - 10 - 22 - 11:24
 * @Description: cn.bugstack.ai.domain.agent.service.armory
 * @version: 1.0
 */
@Slf4j
@Service
public class RootNode extends AbstractArmorySupport{
    private final Map<String, ILoadDataStrategy> loadDataStrategyMap;

    public RootNode(Map<String, ILoadDataStrategy> loadDataStrategyMap) {
        this.loadDataStrategyMap = loadDataStrategyMap;
    }

    @Override
    protected void multiThread(ArmoryCommandEntity requestParameter
            , DefaultArmoryStrategyFactory.DynamicContext dynamicContext)
            throws ExecutionException, InterruptedException, TimeoutException {
        // 通过策略加载数据
        String commandType = requestParameter.getCommandType();
        ILoadDataStrategy loadDataStrategy = loadDataStrategyMap.get(commandType);
        loadDataStrategy.loadData(requestParameter, dynamicContext);
    }

    @Override
    protected String doApply(ArmoryCommandEntity armoryCommandEntity
            , DefaultArmoryStrategyFactory.DynamicContext dynamicContext)
            throws Exception {
        return router(armoryCommandEntity, dynamicContext);
    }

    @Override
    public StrategyHandler<ArmoryCommandEntity, DefaultArmoryStrategyFactory.DynamicContext, String> get(
            ArmoryCommandEntity armoryCommandEntity, DefaultArmoryStrategyFactory.DynamicContext dynamicContext)
            throws Exception {
        return defaultStrategyHandler;
    }
}
