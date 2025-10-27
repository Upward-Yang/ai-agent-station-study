package cn.bugstack.ai.test.dao;

import cn.bugstack.ai.infrastructure.dao.IAiAgentFlowConfigDao;
import cn.bugstack.ai.infrastructure.dao.po.AiAgentFlowConfig;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Auther: yangjian
 * @Date: 2025-10-21 - 10 - 21 - 22:05
 * @Description: cn.bugstack.ai.test.dao
 * @version: 1.0
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class AiAgentFlowConfigDaoTest {
    @Resource
    private IAiAgentFlowConfigDao aiAgentFlowConfigDao;

    @Test
    public void test_insert() {
        AiAgentFlowConfig aiAgentFlowConfig = AiAgentFlowConfig.builder()
                .agentId("1")
                .clientId("3001")
                .clientName("通用的")
                .clientType("DEFAULT")
                .sequence(1)
                .createTime(LocalDateTime.now())
                .build();

        int result = aiAgentFlowConfigDao.insert(aiAgentFlowConfig);
        log.info("插入结果: {}, 生成ID: {}", result, aiAgentFlowConfig.getId());
    }

    @Test
    public void test_updateById() {
        AiAgentFlowConfig aiAgentFlowConfig = AiAgentFlowConfig.builder()
                .id(1L)
                .agentId("1")
                .clientId("3002")
                .clientName("通用的")
                .clientType("DEFAULT")
                .sequence(2)
                .build();

        int result = aiAgentFlowConfigDao.updateById(aiAgentFlowConfig);
        log.info("更新结果: {}", result);
    }

    @Test
    public void test_queryById() {
        AiAgentFlowConfig aiAgentFlowConfig = aiAgentFlowConfigDao.queryById(1L);
        log.info("根据ID查询结果: {}", aiAgentFlowConfig);
    }

    @Test
    public void test_queryByAgentId() {
        List<AiAgentFlowConfig> aiAgentFlowConfigs = aiAgentFlowConfigDao.queryByAgentId("1");
        log.info("根据智能体ID查询结果数量: {}", aiAgentFlowConfigs.size());
        aiAgentFlowConfigs.forEach(config -> log.info("智能体关联配置: {}", config));
    }

    @Test
    public void test_queryByClientId() {
        List<AiAgentFlowConfig> aiAgentFlowConfigs = aiAgentFlowConfigDao.queryByClientId("3001");
        log.info("根据客户端ID查询结果数量: {}", aiAgentFlowConfigs.size());
        aiAgentFlowConfigs.forEach(config -> log.info("客户端关联配置: {}", config));
    }

    @Test
    public void test_queryByAgentIdAndClientId() {
        AiAgentFlowConfig aiAgentFlowConfig = aiAgentFlowConfigDao.queryByAgentIdAndClientId("1", "3000");
        log.info("根据智能体ID和客户端ID查询结果: {}", aiAgentFlowConfig);
    }

    @Test
    public void test_queryAll() {
        List<AiAgentFlowConfig> aiAgentFlowConfigs = aiAgentFlowConfigDao.queryAll();
        log.info("查询所有关联配置数量: {}", aiAgentFlowConfigs.size());
        aiAgentFlowConfigs.forEach(config -> log.info("关联配置: {}", config));
    }

    @Test
    public void test_deleteById() {
        int result = aiAgentFlowConfigDao.deleteById(1L);
        log.info("根据ID删除结果: {}", result);
    }

    @Test
    public void test_deleteByAgentId() {
        int result = aiAgentFlowConfigDao.deleteByAgentId("1");
        log.info("根据智能体ID删除结果: {}", result);
    }
}
