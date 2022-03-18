package com.caipiao.app.service;

import com.caipiao.core.library.model.ResultInfo;

import java.util.List;
import java.util.Map;

public interface LotterySgService {
    ResultInfo<Map<String, Object>> getNewSgInfoById(String id);

    ResultInfo<Map<String, Object>> getStatisticSgInfoById(String id, String date);

    ResultInfo<Map<String, Object>> getLsSgInfoByIdAndDay(String id, String date, Integer pageNum, Integer pageSize);

    ResultInfo<Map<String, Object>> getLsSgInfoByIdLately(String id, Integer pageNum, Integer pageSize);

    ResultInfo<Map<String, Object>> lishionelhcSg(String date, Integer pageNum, Integer pageSize);

    ResultInfo<Map<String, Object>> lishionelhcSgLately(Integer pageNum, Integer pageSize);

    ResultInfo<Map<String, Object>> lishijspksSg(String date, Integer pageNum, Integer pageSize);

    ResultInfo<Map<String, Object>> lishijspksSgLately(Integer pageNum, Integer pageSize);

    ResultInfo<Map<String, Object>> lishijssscSg(String date, Integer pageNum, Integer pageSize);

    ResultInfo<Map<String, Object>> lishijssscSgLately(Integer pageNum, Integer pageSize);

    ResultInfo<Map<String, Object>> lishitensscSg(String date, Integer pageNum, Integer pageSize);

    ResultInfo<Map<String, Object>> lishitensscSgLately(Integer pageNum, Integer pageSize);

    ResultInfo<Map<String, Object>> lishifivesscSg(String date, Integer pageNum, Integer pageSize);

    ResultInfo<Map<String, Object>> lishifivesscSgLately(Integer pageNum, Integer pageSize);

    ResultInfo<Map<String, Object>> lishifivelhcSg(String date, Integer pageNum, Integer pageSize);

    ResultInfo<Map<String, Object>> lishifivelhcSgLately(Integer pageNum, Integer pageSize);

    ResultInfo<Map<String, Object>> lishisslhcSg(String date, Integer pageNum, Integer pageSize);

    ResultInfo<Map<String, Object>> lishisslhcSgLately(Integer pageNum, Integer pageSize);

    ResultInfo<Map<String, Object>> lishifivepksSg(String date, Integer pageNum, Integer pageSize);

    ResultInfo<Map<String, Object>> lishifivepksSgLately(Integer pageNum, Integer pageSize);

    ResultInfo<Map<String, Object>> lishitenpksSg(String date, Integer pageNum, Integer pageSize);

    ResultInfo<Map<String, Object>> lishitenpksSgLately(Integer pageNum, Integer pageSize);

    ResultInfo<Map<String, Object>> getLsSgInfoByIdAndType(String id, String type, int num, String starteDate, String endDate, Integer pageNum, Integer pageSize);

    ResultInfo<Map<String, Object>> getLsSgInfoByDate(String issue, String date, Integer pageNum, Integer pageSize);

}
