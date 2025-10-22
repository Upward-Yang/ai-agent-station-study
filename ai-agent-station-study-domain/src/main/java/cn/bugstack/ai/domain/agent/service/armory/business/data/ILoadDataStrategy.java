package cn.bugstack.ai.domain.agent.service.armory.business.data;

import cn.bugstack.ai.domain.agent.model.entity.ArmoryCommandEntity;
import cn.bugstack.ai.domain.agent.service.armory.factory.DefaultArmoryStrategyFactory;

/**
 * 数据加载策略
 * @Auther: yangjian
 * @Date: 2025-10-22 - 10 - 22 - 11:26
 * @Description: cn.bugstack.ai.domain.agent.service.armory.business.data
 * @version: 1.0
 */
public interface ILoadDataStrategy {
    void loadData(ArmoryCommandEntity armoryCommandEntity
            , DefaultArmoryStrategyFactory.DynamicContext dynamicContext);
}
