package com.mapper.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TaskSgExample implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table task_sg
     *
     * @mbggenerated
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table task_sg
     *
     * @mbggenerated
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table task_sg
     *
     * @mbggenerated
     */
    protected List<Criteria> oredCriteria;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table task_sg
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table task_sg
     *
     * @mbggenerated
     */
    protected Integer offset;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table task_sg
     *
     * @mbggenerated
     */
    protected Integer limit;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_sg
     *
     * @mbggenerated
     */
    public TaskSgExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_sg
     *
     * @mbggenerated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_sg
     *
     * @mbggenerated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_sg
     *
     * @mbggenerated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_sg
     *
     * @mbggenerated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_sg
     *
     * @mbggenerated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_sg
     *
     * @mbggenerated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_sg
     *
     * @mbggenerated
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_sg
     *
     * @mbggenerated
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_sg
     *
     * @mbggenerated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_sg
     *
     * @mbggenerated
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
        offset = null;
        limit = null;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_sg
     *
     * @mbggenerated
     */
    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_sg
     *
     * @mbggenerated
     */
    public Integer getOffset() {
        return this.offset;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_sg
     *
     * @mbggenerated
     */
    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_sg
     *
     * @mbggenerated
     */
    public Integer getLimit() {
        return this.limit;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_sg
     *
     * @mbggenerated
     */
    public TaskSgExample bound(Integer offset, Integer limit) {
        this.offset = offset;
        this.limit = limit;
        return this;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table task_sg
     *
     * @mbggenerated
     */
    protected abstract static class GeneratedCriteria implements Serializable {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andTaskStatusIsNull() {
            addCriterion("task_status is null");
            return (Criteria) this;
        }

        public Criteria andTaskStatusIsNotNull() {
            addCriterion("task_status is not null");
            return (Criteria) this;
        }

        public Criteria andTaskStatusEqualTo(Integer value) {
            addCriterion("task_status =", value, "taskStatus");
            return (Criteria) this;
        }

        public Criteria andTaskStatusNotEqualTo(Integer value) {
            addCriterion("task_status <>", value, "taskStatus");
            return (Criteria) this;
        }

        public Criteria andTaskStatusGreaterThan(Integer value) {
            addCriterion("task_status >", value, "taskStatus");
            return (Criteria) this;
        }

        public Criteria andTaskStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("task_status >=", value, "taskStatus");
            return (Criteria) this;
        }

        public Criteria andTaskStatusLessThan(Integer value) {
            addCriterion("task_status <", value, "taskStatus");
            return (Criteria) this;
        }

        public Criteria andTaskStatusLessThanOrEqualTo(Integer value) {
            addCriterion("task_status <=", value, "taskStatus");
            return (Criteria) this;
        }

        public Criteria andTaskStatusIn(List<Integer> values) {
            addCriterion("task_status in", values, "taskStatus");
            return (Criteria) this;
        }

        public Criteria andTaskStatusNotIn(List<Integer> values) {
            addCriterion("task_status not in", values, "taskStatus");
            return (Criteria) this;
        }

        public Criteria andTaskStatusBetween(Integer value1, Integer value2) {
            addCriterion("task_status between", value1, value2, "taskStatus");
            return (Criteria) this;
        }

        public Criteria andTaskStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("task_status not between", value1, value2, "taskStatus");
            return (Criteria) this;
        }

        public Criteria andTaskTypeIsNull() {
            addCriterion("task_type is null");
            return (Criteria) this;
        }

        public Criteria andTaskTypeIsNotNull() {
            addCriterion("task_type is not null");
            return (Criteria) this;
        }

        public Criteria andTaskTypeEqualTo(Integer value) {
            addCriterion("task_type =", value, "taskType");
            return (Criteria) this;
        }

        public Criteria andTaskTypeNotEqualTo(Integer value) {
            addCriterion("task_type <>", value, "taskType");
            return (Criteria) this;
        }

        public Criteria andTaskTypeGreaterThan(Integer value) {
            addCriterion("task_type >", value, "taskType");
            return (Criteria) this;
        }

        public Criteria andTaskTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("task_type >=", value, "taskType");
            return (Criteria) this;
        }

        public Criteria andTaskTypeLessThan(Integer value) {
            addCriterion("task_type <", value, "taskType");
            return (Criteria) this;
        }

        public Criteria andTaskTypeLessThanOrEqualTo(Integer value) {
            addCriterion("task_type <=", value, "taskType");
            return (Criteria) this;
        }

        public Criteria andTaskTypeIn(List<Integer> values) {
            addCriterion("task_type in", values, "taskType");
            return (Criteria) this;
        }

        public Criteria andTaskTypeNotIn(List<Integer> values) {
            addCriterion("task_type not in", values, "taskType");
            return (Criteria) this;
        }

        public Criteria andTaskTypeBetween(Integer value1, Integer value2) {
            addCriterion("task_type between", value1, value2, "taskType");
            return (Criteria) this;
        }

        public Criteria andTaskTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("task_type not between", value1, value2, "taskType");
            return (Criteria) this;
        }

        public Criteria andSendCountIsNull() {
            addCriterion("send_count is null");
            return (Criteria) this;
        }

        public Criteria andSendCountIsNotNull() {
            addCriterion("send_count is not null");
            return (Criteria) this;
        }

        public Criteria andSendCountEqualTo(Integer value) {
            addCriterion("send_count =", value, "sendCount");
            return (Criteria) this;
        }

        public Criteria andSendCountNotEqualTo(Integer value) {
            addCriterion("send_count <>", value, "sendCount");
            return (Criteria) this;
        }

        public Criteria andSendCountGreaterThan(Integer value) {
            addCriterion("send_count >", value, "sendCount");
            return (Criteria) this;
        }

        public Criteria andSendCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("send_count >=", value, "sendCount");
            return (Criteria) this;
        }

        public Criteria andSendCountLessThan(Integer value) {
            addCriterion("send_count <", value, "sendCount");
            return (Criteria) this;
        }

        public Criteria andSendCountLessThanOrEqualTo(Integer value) {
            addCriterion("send_count <=", value, "sendCount");
            return (Criteria) this;
        }

        public Criteria andSendCountIn(List<Integer> values) {
            addCriterion("send_count in", values, "sendCount");
            return (Criteria) this;
        }

        public Criteria andSendCountNotIn(List<Integer> values) {
            addCriterion("send_count not in", values, "sendCount");
            return (Criteria) this;
        }

        public Criteria andSendCountBetween(Integer value1, Integer value2) {
            addCriterion("send_count between", value1, value2, "sendCount");
            return (Criteria) this;
        }

        public Criteria andSendCountNotBetween(Integer value1, Integer value2) {
            addCriterion("send_count not between", value1, value2, "sendCount");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andCaipiaoTypeIsNull() {
            addCriterion("caipiao_type is null");
            return (Criteria) this;
        }

        public Criteria andCaipiaoTypeIsNotNull() {
            addCriterion("caipiao_type is not null");
            return (Criteria) this;
        }

        public Criteria andCaipiaoTypeEqualTo(String value) {
            addCriterion("caipiao_type =", value, "caipiaoType");
            return (Criteria) this;
        }

        public Criteria andCaipiaoTypeNotEqualTo(String value) {
            addCriterion("caipiao_type <>", value, "caipiaoType");
            return (Criteria) this;
        }

        public Criteria andCaipiaoTypeGreaterThan(String value) {
            addCriterion("caipiao_type >", value, "caipiaoType");
            return (Criteria) this;
        }

        public Criteria andCaipiaoTypeGreaterThanOrEqualTo(String value) {
            addCriterion("caipiao_type >=", value, "caipiaoType");
            return (Criteria) this;
        }

        public Criteria andCaipiaoTypeLessThan(String value) {
            addCriterion("caipiao_type <", value, "caipiaoType");
            return (Criteria) this;
        }

        public Criteria andCaipiaoTypeLessThanOrEqualTo(String value) {
            addCriterion("caipiao_type <=", value, "caipiaoType");
            return (Criteria) this;
        }

        public Criteria andCaipiaoTypeLike(String value) {
            addCriterion("caipiao_type like", value, "caipiaoType");
            return (Criteria) this;
        }

        public Criteria andCaipiaoTypeNotLike(String value) {
            addCriterion("caipiao_type not like", value, "caipiaoType");
            return (Criteria) this;
        }

        public Criteria andCaipiaoTypeIn(List<String> values) {
            addCriterion("caipiao_type in", values, "caipiaoType");
            return (Criteria) this;
        }

        public Criteria andCaipiaoTypeNotIn(List<String> values) {
            addCriterion("caipiao_type not in", values, "caipiaoType");
            return (Criteria) this;
        }

        public Criteria andCaipiaoTypeBetween(String value1, String value2) {
            addCriterion("caipiao_type between", value1, value2, "caipiaoType");
            return (Criteria) this;
        }

        public Criteria andCaipiaoTypeNotBetween(String value1, String value2) {
            addCriterion("caipiao_type not between", value1, value2, "caipiaoType");
            return (Criteria) this;
        }

        public Criteria andPlayTypeIsNull() {
            addCriterion("play_type is null");
            return (Criteria) this;
        }

        public Criteria andPlayTypeIsNotNull() {
            addCriterion("play_type is not null");
            return (Criteria) this;
        }

        public Criteria andPlayTypeEqualTo(String value) {
            addCriterion("play_type =", value, "playType");
            return (Criteria) this;
        }

        public Criteria andPlayTypeNotEqualTo(String value) {
            addCriterion("play_type <>", value, "playType");
            return (Criteria) this;
        }

        public Criteria andPlayTypeGreaterThan(String value) {
            addCriterion("play_type >", value, "playType");
            return (Criteria) this;
        }

        public Criteria andPlayTypeGreaterThanOrEqualTo(String value) {
            addCriterion("play_type >=", value, "playType");
            return (Criteria) this;
        }

        public Criteria andPlayTypeLessThan(String value) {
            addCriterion("play_type <", value, "playType");
            return (Criteria) this;
        }

        public Criteria andPlayTypeLessThanOrEqualTo(String value) {
            addCriterion("play_type <=", value, "playType");
            return (Criteria) this;
        }

        public Criteria andPlayTypeLike(String value) {
            addCriterion("play_type like", value, "playType");
            return (Criteria) this;
        }

        public Criteria andPlayTypeNotLike(String value) {
            addCriterion("play_type not like", value, "playType");
            return (Criteria) this;
        }

        public Criteria andPlayTypeIn(List<String> values) {
            addCriterion("play_type in", values, "playType");
            return (Criteria) this;
        }

        public Criteria andPlayTypeNotIn(List<String> values) {
            addCriterion("play_type not in", values, "playType");
            return (Criteria) this;
        }

        public Criteria andPlayTypeBetween(String value1, String value2) {
            addCriterion("play_type between", value1, value2, "playType");
            return (Criteria) this;
        }

        public Criteria andPlayTypeNotBetween(String value1, String value2) {
            addCriterion("play_type not between", value1, value2, "playType");
            return (Criteria) this;
        }

        public Criteria andDragonNumIsNull() {
            addCriterion("dragon_num is null");
            return (Criteria) this;
        }

        public Criteria andDragonNumIsNotNull() {
            addCriterion("dragon_num is not null");
            return (Criteria) this;
        }

        public Criteria andDragonNumEqualTo(Integer value) {
            addCriterion("dragon_num =", value, "dragonNum");
            return (Criteria) this;
        }

        public Criteria andDragonNumNotEqualTo(Integer value) {
            addCriterion("dragon_num <>", value, "dragonNum");
            return (Criteria) this;
        }

        public Criteria andDragonNumGreaterThan(Integer value) {
            addCriterion("dragon_num >", value, "dragonNum");
            return (Criteria) this;
        }

        public Criteria andDragonNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("dragon_num >=", value, "dragonNum");
            return (Criteria) this;
        }

        public Criteria andDragonNumLessThan(Integer value) {
            addCriterion("dragon_num <", value, "dragonNum");
            return (Criteria) this;
        }

        public Criteria andDragonNumLessThanOrEqualTo(Integer value) {
            addCriterion("dragon_num <=", value, "dragonNum");
            return (Criteria) this;
        }

        public Criteria andDragonNumIn(List<Integer> values) {
            addCriterion("dragon_num in", values, "dragonNum");
            return (Criteria) this;
        }

        public Criteria andDragonNumNotIn(List<Integer> values) {
            addCriterion("dragon_num not in", values, "dragonNum");
            return (Criteria) this;
        }

        public Criteria andDragonNumBetween(Integer value1, Integer value2) {
            addCriterion("dragon_num between", value1, value2, "dragonNum");
            return (Criteria) this;
        }

        public Criteria andDragonNumNotBetween(Integer value1, Integer value2) {
            addCriterion("dragon_num not between", value1, value2, "dragonNum");
            return (Criteria) this;
        }

        public Criteria andDragonNameIsNull() {
            addCriterion("dragon_name is null");
            return (Criteria) this;
        }

        public Criteria andDragonNameIsNotNull() {
            addCriterion("dragon_name is not null");
            return (Criteria) this;
        }

        public Criteria andDragonNameEqualTo(String value) {
            addCriterion("dragon_name =", value, "dragonName");
            return (Criteria) this;
        }

        public Criteria andDragonNameNotEqualTo(String value) {
            addCriterion("dragon_name <>", value, "dragonName");
            return (Criteria) this;
        }

        public Criteria andDragonNameGreaterThan(String value) {
            addCriterion("dragon_name >", value, "dragonName");
            return (Criteria) this;
        }

        public Criteria andDragonNameGreaterThanOrEqualTo(String value) {
            addCriterion("dragon_name >=", value, "dragonName");
            return (Criteria) this;
        }

        public Criteria andDragonNameLessThan(String value) {
            addCriterion("dragon_name <", value, "dragonName");
            return (Criteria) this;
        }

        public Criteria andDragonNameLessThanOrEqualTo(String value) {
            addCriterion("dragon_name <=", value, "dragonName");
            return (Criteria) this;
        }

        public Criteria andDragonNameLike(String value) {
            addCriterion("dragon_name like", value, "dragonName");
            return (Criteria) this;
        }

        public Criteria andDragonNameNotLike(String value) {
            addCriterion("dragon_name not like", value, "dragonName");
            return (Criteria) this;
        }

        public Criteria andDragonNameIn(List<String> values) {
            addCriterion("dragon_name in", values, "dragonName");
            return (Criteria) this;
        }

        public Criteria andDragonNameNotIn(List<String> values) {
            addCriterion("dragon_name not in", values, "dragonName");
            return (Criteria) this;
        }

        public Criteria andDragonNameBetween(String value1, String value2) {
            addCriterion("dragon_name between", value1, value2, "dragonName");
            return (Criteria) this;
        }

        public Criteria andDragonNameNotBetween(String value1, String value2) {
            addCriterion("dragon_name not between", value1, value2, "dragonName");
            return (Criteria) this;
        }

        public Criteria andDeletedIsNull() {
            addCriterion("deleted is null");
            return (Criteria) this;
        }

        public Criteria andDeletedIsNotNull() {
            addCriterion("deleted is not null");
            return (Criteria) this;
        }

        public Criteria andDeletedEqualTo(Integer value) {
            addCriterion("deleted =", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedNotEqualTo(Integer value) {
            addCriterion("deleted <>", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedGreaterThan(Integer value) {
            addCriterion("deleted >", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedGreaterThanOrEqualTo(Integer value) {
            addCriterion("deleted >=", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedLessThan(Integer value) {
            addCriterion("deleted <", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedLessThanOrEqualTo(Integer value) {
            addCriterion("deleted <=", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedIn(List<Integer> values) {
            addCriterion("deleted in", values, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedNotIn(List<Integer> values) {
            addCriterion("deleted not in", values, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedBetween(Integer value1, Integer value2) {
            addCriterion("deleted between", value1, value2, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedNotBetween(Integer value1, Integer value2) {
            addCriterion("deleted not between", value1, value2, "deleted");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table task_sg
     *
     * @mbggenerated do_not_delete_during_merge
     */
    public static class Criteria extends GeneratedCriteria implements Serializable {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table task_sg
     *
     * @mbggenerated
     */
    public static class Criterion implements Serializable {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}