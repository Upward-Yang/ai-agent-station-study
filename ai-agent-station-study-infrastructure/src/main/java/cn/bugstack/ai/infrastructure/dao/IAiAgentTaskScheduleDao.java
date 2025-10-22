package cn.bugstack.ai.infrastructure.dao;

import cn.bugstack.ai.infrastructure.dao.po.AiAgentTaskSchedule;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 智能体任务调度配置表 DAO
 * @Auther: yangjian
 * @Date: 2025-10-21 - 10 - 21 - 17:03
 * @Description: cn.bugstack.ai.infrastructure.dao
 * @version: 1.0
 */
@Mapper
public interface IAiAgentTaskScheduleDao {
    /**
     * 插入智能体任务调度配置
     * @param aiAgentTaskSchedule 智能体任务调度配置对象
     * @return 影响行数
     */
    int insert(AiAgentTaskSchedule aiAgentTaskSchedule);

    /**
     * 根据ID更新智能体任务调度配置
     * @param aiAgentTaskSchedule 智能体任务调度配置对象
     * @return 影响行数
     */
    int updateById(AiAgentTaskSchedule aiAgentTaskSchedule);

    /**
     * 根据ID删除智能体任务调度配置
     * @param id 主键ID
     * @return 影响行数
     */
    int deleteById(@Param("id") Long id);

    /**
     * 根据智能体ID删除任务调度配置
     * @param agentId 智能体ID
     * @return 影响行数
     */
    int deleteByAgentId(@Param("agentId") Long agentId);

    /**
     * 根据ID查询智能体任务调度配置
     * @param id 主键ID
     * @return 智能体任务调度配置对象
     */
    AiAgentTaskSchedule queryById(@Param("id") Long id);

    /**
     * 根据智能体ID查询任务调度配置列表
     * @param agentId 智能体ID
     * @return 智能体任务调度配置列表
     */
    List<AiAgentTaskSchedule> queryByAgentId(@Param("agentId") Long agentId);

    /**
     * 查询所有有效的任务调度配置
     * @return 智能体任务调度配置列表
     */
    List<AiAgentTaskSchedule> queryEnabledTasks();

    /**
     * 根据任务名称查询任务调度配置
     * @param taskName 任务名称
     * @return 智能体任务调度配置对象
     */
    AiAgentTaskSchedule queryByTaskName(@Param("taskName") String taskName);

    /**
     * 查询所有智能体任务调度配置
     * @return 智能体任务调度配置列表
     */
    List<AiAgentTaskSchedule> queryAll();
}
