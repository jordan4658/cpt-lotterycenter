package com.caipiao.task.server.service;

import java.util.List;

import com.mapper.domain.TaskSg;

public interface TaskSgService {
	
	public void saveDragonPushTask(int taskType, String caiPiaoType, String playType, String dragonName, int dragonNum);
	
	public List<TaskSg> getInit(String caiPiaoType, Integer limitNum);
	
	public void updateStatus(Integer id, Integer status);

}
