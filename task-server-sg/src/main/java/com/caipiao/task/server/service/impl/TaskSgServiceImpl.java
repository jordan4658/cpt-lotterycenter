package com.caipiao.task.server.service.impl;

import com.caipiao.core.library.constant.Constants;
import com.caipiao.core.library.enums.TaskStatusEnum;
import com.caipiao.task.server.service.TaskSgService;
import com.mapper.TaskSgMapper;
import com.mapper.domain.TaskSg;
import com.mapper.domain.TaskSgExample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class TaskSgServiceImpl implements TaskSgService {

    private static final Logger logger = LoggerFactory.getLogger(JssscTaskServiceImpl.class);

    @Resource
    private TaskSgMapper taskSgMapper;

    /**
     * 保存定时任务数据
     */
    @Override
    public void saveDragonPushTask(int taskType, String caiPiaoType, String playType, String dragonName, int dragonNum) {
        try {
            TaskSg taskSg = new TaskSg();
            taskSg.setTaskStatus(TaskStatusEnum.INIT.getKeyValue());
            taskSg.setTaskType(taskType);
            taskSg.setSendCount(Constants.DEFAULT_INTEGER);
            taskSg.setCreateTime(new Date());
            taskSg.setUpdateTime(new Date());
            taskSg.setCaipiaoType(caiPiaoType);
            taskSg.setPlayType(playType);
            taskSg.setDragonName(dragonName);
            taskSg.setDragonNum(dragonNum);
            taskSg.setDeleted(Constants.DEFAULT_INTEGER);
            taskSgMapper.insert(taskSg);
        } catch (Exception e) {
            logger.error("保存开奖库中定时任务失败：", e);
        }
    }

    /**
     * 查询待执行的定时任务
     */
    @Override
    public List<TaskSg> getInit(String caiPiaoType, Integer limitNum) {
        TaskSgExample taskExample = new TaskSgExample();
        TaskSgExample.Criteria taskCriteria = taskExample.createCriteria();
        taskCriteria.andCaipiaoTypeEqualTo(caiPiaoType);
        taskCriteria.andTaskStatusEqualTo(TaskStatusEnum.INIT.getKeyValue());
        taskCriteria.andDeletedEqualTo(Constants.DEFAULT_INTEGER);
        taskExample.setLimit(limitNum);
        List<TaskSg> taskList = taskSgMapper.selectByExample(taskExample);
        return taskList;
    }

    /**
     * 更新任务状态
     */
    @Override
    public void updateStatus(Integer id, Integer status) {
        TaskSg record = new TaskSg();
        record.setId(id);
        record.setTaskStatus(status);
        record.setSendCount(Constants.DEFAULT_ONE);
        taskSgMapper.updateByPrimaryKeySelective(record);
    }

}
