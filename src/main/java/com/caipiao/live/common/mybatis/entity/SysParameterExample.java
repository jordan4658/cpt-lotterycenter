package com.caipiao.live.common.mybatis.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SysParameterExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table sys_parameter
     *
     * @mbg.generated Tue Oct 26 17:00:28 ICT 2021
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table sys_parameter
     *
     * @mbg.generated Tue Oct 26 17:00:28 ICT 2021
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table sys_parameter
     *
     * @mbg.generated Tue Oct 26 17:00:28 ICT 2021
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_parameter
     *
     * @mbg.generated Tue Oct 26 17:00:28 ICT 2021
     */
    public SysParameterExample() {
        oredCriteria = new ArrayList<>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_parameter
     *
     * @mbg.generated Tue Oct 26 17:00:28 ICT 2021
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_parameter
     *
     * @mbg.generated Tue Oct 26 17:00:28 ICT 2021
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_parameter
     *
     * @mbg.generated Tue Oct 26 17:00:28 ICT 2021
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_parameter
     *
     * @mbg.generated Tue Oct 26 17:00:28 ICT 2021
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_parameter
     *
     * @mbg.generated Tue Oct 26 17:00:28 ICT 2021
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_parameter
     *
     * @mbg.generated Tue Oct 26 17:00:28 ICT 2021
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_parameter
     *
     * @mbg.generated Tue Oct 26 17:00:28 ICT 2021
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_parameter
     *
     * @mbg.generated Tue Oct 26 17:00:28 ICT 2021
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
     * This method corresponds to the database table sys_parameter
     *
     * @mbg.generated Tue Oct 26 17:00:28 ICT 2021
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_parameter
     *
     * @mbg.generated Tue Oct 26 17:00:28 ICT 2021
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table sys_parameter
     *
     * @mbg.generated Tue Oct 26 17:00:28 ICT 2021
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<>();
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

        public Criteria andParamIdIsNull() {
            addCriterion("param_id is null");
            return (Criteria) this;
        }

        public Criteria andParamIdIsNotNull() {
            addCriterion("param_id is not null");
            return (Criteria) this;
        }

        public Criteria andParamIdEqualTo(Long value) {
            addCriterion("param_id =", value, "paramId");
            return (Criteria) this;
        }

        public Criteria andParamIdNotEqualTo(Long value) {
            addCriterion("param_id <>", value, "paramId");
            return (Criteria) this;
        }

        public Criteria andParamIdGreaterThan(Long value) {
            addCriterion("param_id >", value, "paramId");
            return (Criteria) this;
        }

        public Criteria andParamIdGreaterThanOrEqualTo(Long value) {
            addCriterion("param_id >=", value, "paramId");
            return (Criteria) this;
        }

        public Criteria andParamIdLessThan(Long value) {
            addCriterion("param_id <", value, "paramId");
            return (Criteria) this;
        }

        public Criteria andParamIdLessThanOrEqualTo(Long value) {
            addCriterion("param_id <=", value, "paramId");
            return (Criteria) this;
        }

        public Criteria andParamIdIn(List<Long> values) {
            addCriterion("param_id in", values, "paramId");
            return (Criteria) this;
        }

        public Criteria andParamIdNotIn(List<Long> values) {
            addCriterion("param_id not in", values, "paramId");
            return (Criteria) this;
        }

        public Criteria andParamIdBetween(Long value1, Long value2) {
            addCriterion("param_id between", value1, value2, "paramId");
            return (Criteria) this;
        }

        public Criteria andParamIdNotBetween(Long value1, Long value2) {
            addCriterion("param_id not between", value1, value2, "paramId");
            return (Criteria) this;
        }

        public Criteria andParamCodeIsNull() {
            addCriterion("param_code is null");
            return (Criteria) this;
        }

        public Criteria andParamCodeIsNotNull() {
            addCriterion("param_code is not null");
            return (Criteria) this;
        }

        public Criteria andParamCodeEqualTo(String value) {
            addCriterion("param_code =", value, "paramCode");
            return (Criteria) this;
        }

        public Criteria andParamCodeNotEqualTo(String value) {
            addCriterion("param_code <>", value, "paramCode");
            return (Criteria) this;
        }

        public Criteria andParamCodeGreaterThan(String value) {
            addCriterion("param_code >", value, "paramCode");
            return (Criteria) this;
        }

        public Criteria andParamCodeGreaterThanOrEqualTo(String value) {
            addCriterion("param_code >=", value, "paramCode");
            return (Criteria) this;
        }

        public Criteria andParamCodeLessThan(String value) {
            addCriterion("param_code <", value, "paramCode");
            return (Criteria) this;
        }

        public Criteria andParamCodeLessThanOrEqualTo(String value) {
            addCriterion("param_code <=", value, "paramCode");
            return (Criteria) this;
        }

        public Criteria andParamCodeLike(String value) {
            addCriterion("param_code like", value, "paramCode");
            return (Criteria) this;
        }

        public Criteria andParamCodeNotLike(String value) {
            addCriterion("param_code not like", value, "paramCode");
            return (Criteria) this;
        }

        public Criteria andParamCodeIn(List<String> values) {
            addCriterion("param_code in", values, "paramCode");
            return (Criteria) this;
        }

        public Criteria andParamCodeNotIn(List<String> values) {
            addCriterion("param_code not in", values, "paramCode");
            return (Criteria) this;
        }

        public Criteria andParamCodeBetween(String value1, String value2) {
            addCriterion("param_code between", value1, value2, "paramCode");
            return (Criteria) this;
        }

        public Criteria andParamCodeNotBetween(String value1, String value2) {
            addCriterion("param_code not between", value1, value2, "paramCode");
            return (Criteria) this;
        }

        public Criteria andParamNameIsNull() {
            addCriterion("param_name is null");
            return (Criteria) this;
        }

        public Criteria andParamNameIsNotNull() {
            addCriterion("param_name is not null");
            return (Criteria) this;
        }

        public Criteria andParamNameEqualTo(String value) {
            addCriterion("param_name =", value, "paramName");
            return (Criteria) this;
        }

        public Criteria andParamNameNotEqualTo(String value) {
            addCriterion("param_name <>", value, "paramName");
            return (Criteria) this;
        }

        public Criteria andParamNameGreaterThan(String value) {
            addCriterion("param_name >", value, "paramName");
            return (Criteria) this;
        }

        public Criteria andParamNameGreaterThanOrEqualTo(String value) {
            addCriterion("param_name >=", value, "paramName");
            return (Criteria) this;
        }

        public Criteria andParamNameLessThan(String value) {
            addCriterion("param_name <", value, "paramName");
            return (Criteria) this;
        }

        public Criteria andParamNameLessThanOrEqualTo(String value) {
            addCriterion("param_name <=", value, "paramName");
            return (Criteria) this;
        }

        public Criteria andParamNameLike(String value) {
            addCriterion("param_name like", value, "paramName");
            return (Criteria) this;
        }

        public Criteria andParamNameNotLike(String value) {
            addCriterion("param_name not like", value, "paramName");
            return (Criteria) this;
        }

        public Criteria andParamNameIn(List<String> values) {
            addCriterion("param_name in", values, "paramName");
            return (Criteria) this;
        }

        public Criteria andParamNameNotIn(List<String> values) {
            addCriterion("param_name not in", values, "paramName");
            return (Criteria) this;
        }

        public Criteria andParamNameBetween(String value1, String value2) {
            addCriterion("param_name between", value1, value2, "paramName");
            return (Criteria) this;
        }

        public Criteria andParamNameNotBetween(String value1, String value2) {
            addCriterion("param_name not between", value1, value2, "paramName");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("remark is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("remark is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("remark =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("remark <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("remark >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("remark >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("remark <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("remark <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("remark like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("remark not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("remark in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("remark not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("remark between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("remark not between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andParamValueIsNull() {
            addCriterion("param_value is null");
            return (Criteria) this;
        }

        public Criteria andParamValueIsNotNull() {
            addCriterion("param_value is not null");
            return (Criteria) this;
        }

        public Criteria andParamValueEqualTo(String value) {
            addCriterion("param_value =", value, "paramValue");
            return (Criteria) this;
        }

        public Criteria andParamValueNotEqualTo(String value) {
            addCriterion("param_value <>", value, "paramValue");
            return (Criteria) this;
        }

        public Criteria andParamValueGreaterThan(String value) {
            addCriterion("param_value >", value, "paramValue");
            return (Criteria) this;
        }

        public Criteria andParamValueGreaterThanOrEqualTo(String value) {
            addCriterion("param_value >=", value, "paramValue");
            return (Criteria) this;
        }

        public Criteria andParamValueLessThan(String value) {
            addCriterion("param_value <", value, "paramValue");
            return (Criteria) this;
        }

        public Criteria andParamValueLessThanOrEqualTo(String value) {
            addCriterion("param_value <=", value, "paramValue");
            return (Criteria) this;
        }

        public Criteria andParamValueLike(String value) {
            addCriterion("param_value like", value, "paramValue");
            return (Criteria) this;
        }

        public Criteria andParamValueNotLike(String value) {
            addCriterion("param_value not like", value, "paramValue");
            return (Criteria) this;
        }

        public Criteria andParamValueIn(List<String> values) {
            addCriterion("param_value in", values, "paramValue");
            return (Criteria) this;
        }

        public Criteria andParamValueNotIn(List<String> values) {
            addCriterion("param_value not in", values, "paramValue");
            return (Criteria) this;
        }

        public Criteria andParamValueBetween(String value1, String value2) {
            addCriterion("param_value between", value1, value2, "paramValue");
            return (Criteria) this;
        }

        public Criteria andParamValueNotBetween(String value1, String value2) {
            addCriterion("param_value not between", value1, value2, "paramValue");
            return (Criteria) this;
        }

        public Criteria andSortByIsNull() {
            addCriterion("sort_by is null");
            return (Criteria) this;
        }

        public Criteria andSortByIsNotNull() {
            addCriterion("sort_by is not null");
            return (Criteria) this;
        }

        public Criteria andSortByEqualTo(Integer value) {
            addCriterion("sort_by =", value, "sortBy");
            return (Criteria) this;
        }

        public Criteria andSortByNotEqualTo(Integer value) {
            addCriterion("sort_by <>", value, "sortBy");
            return (Criteria) this;
        }

        public Criteria andSortByGreaterThan(Integer value) {
            addCriterion("sort_by >", value, "sortBy");
            return (Criteria) this;
        }

        public Criteria andSortByGreaterThanOrEqualTo(Integer value) {
            addCriterion("sort_by >=", value, "sortBy");
            return (Criteria) this;
        }

        public Criteria andSortByLessThan(Integer value) {
            addCriterion("sort_by <", value, "sortBy");
            return (Criteria) this;
        }

        public Criteria andSortByLessThanOrEqualTo(Integer value) {
            addCriterion("sort_by <=", value, "sortBy");
            return (Criteria) this;
        }

        public Criteria andSortByIn(List<Integer> values) {
            addCriterion("sort_by in", values, "sortBy");
            return (Criteria) this;
        }

        public Criteria andSortByNotIn(List<Integer> values) {
            addCriterion("sort_by not in", values, "sortBy");
            return (Criteria) this;
        }

        public Criteria andSortByBetween(Integer value1, Integer value2) {
            addCriterion("sort_by between", value1, value2, "sortBy");
            return (Criteria) this;
        }

        public Criteria andSortByNotBetween(Integer value1, Integer value2) {
            addCriterion("sort_by not between", value1, value2, "sortBy");
            return (Criteria) this;
        }

        public Criteria andParamStatusIsNull() {
            addCriterion("param_status is null");
            return (Criteria) this;
        }

        public Criteria andParamStatusIsNotNull() {
            addCriterion("param_status is not null");
            return (Criteria) this;
        }

        public Criteria andParamStatusEqualTo(Integer value) {
            addCriterion("param_status =", value, "paramStatus");
            return (Criteria) this;
        }

        public Criteria andParamStatusNotEqualTo(Integer value) {
            addCriterion("param_status <>", value, "paramStatus");
            return (Criteria) this;
        }

        public Criteria andParamStatusGreaterThan(Integer value) {
            addCriterion("param_status >", value, "paramStatus");
            return (Criteria) this;
        }

        public Criteria andParamStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("param_status >=", value, "paramStatus");
            return (Criteria) this;
        }

        public Criteria andParamStatusLessThan(Integer value) {
            addCriterion("param_status <", value, "paramStatus");
            return (Criteria) this;
        }

        public Criteria andParamStatusLessThanOrEqualTo(Integer value) {
            addCriterion("param_status <=", value, "paramStatus");
            return (Criteria) this;
        }

        public Criteria andParamStatusIn(List<Integer> values) {
            addCriterion("param_status in", values, "paramStatus");
            return (Criteria) this;
        }

        public Criteria andParamStatusNotIn(List<Integer> values) {
            addCriterion("param_status not in", values, "paramStatus");
            return (Criteria) this;
        }

        public Criteria andParamStatusBetween(Integer value1, Integer value2) {
            addCriterion("param_status between", value1, value2, "paramStatus");
            return (Criteria) this;
        }

        public Criteria andParamStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("param_status not between", value1, value2, "paramStatus");
            return (Criteria) this;
        }

        public Criteria andIsDeleteIsNull() {
            addCriterion("is_delete is null");
            return (Criteria) this;
        }

        public Criteria andIsDeleteIsNotNull() {
            addCriterion("is_delete is not null");
            return (Criteria) this;
        }

        public Criteria andIsDeleteEqualTo(Boolean value) {
            addCriterion("is_delete =", value, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteNotEqualTo(Boolean value) {
            addCriterion("is_delete <>", value, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteGreaterThan(Boolean value) {
            addCriterion("is_delete >", value, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_delete >=", value, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteLessThan(Boolean value) {
            addCriterion("is_delete <", value, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteLessThanOrEqualTo(Boolean value) {
            addCriterion("is_delete <=", value, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteIn(List<Boolean> values) {
            addCriterion("is_delete in", values, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteNotIn(List<Boolean> values) {
            addCriterion("is_delete not in", values, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteBetween(Boolean value1, Boolean value2) {
            addCriterion("is_delete between", value1, value2, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_delete not between", value1, value2, "isDelete");
            return (Criteria) this;
        }

        public Criteria andCreateUserIsNull() {
            addCriterion("create_user is null");
            return (Criteria) this;
        }

        public Criteria andCreateUserIsNotNull() {
            addCriterion("create_user is not null");
            return (Criteria) this;
        }

        public Criteria andCreateUserEqualTo(String value) {
            addCriterion("create_user =", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotEqualTo(String value) {
            addCriterion("create_user <>", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserGreaterThan(String value) {
            addCriterion("create_user >", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserGreaterThanOrEqualTo(String value) {
            addCriterion("create_user >=", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserLessThan(String value) {
            addCriterion("create_user <", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserLessThanOrEqualTo(String value) {
            addCriterion("create_user <=", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserLike(String value) {
            addCriterion("create_user like", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotLike(String value) {
            addCriterion("create_user not like", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserIn(List<String> values) {
            addCriterion("create_user in", values, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotIn(List<String> values) {
            addCriterion("create_user not in", values, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserBetween(String value1, String value2) {
            addCriterion("create_user between", value1, value2, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotBetween(String value1, String value2) {
            addCriterion("create_user not between", value1, value2, "createUser");
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

        public Criteria andUpdateUserIsNull() {
            addCriterion("update_user is null");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIsNotNull() {
            addCriterion("update_user is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateUserEqualTo(String value) {
            addCriterion("update_user =", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotEqualTo(String value) {
            addCriterion("update_user <>", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserGreaterThan(String value) {
            addCriterion("update_user >", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserGreaterThanOrEqualTo(String value) {
            addCriterion("update_user >=", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserLessThan(String value) {
            addCriterion("update_user <", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserLessThanOrEqualTo(String value) {
            addCriterion("update_user <=", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserLike(String value) {
            addCriterion("update_user like", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotLike(String value) {
            addCriterion("update_user not like", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIn(List<String> values) {
            addCriterion("update_user in", values, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotIn(List<String> values) {
            addCriterion("update_user not in", values, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserBetween(String value1, String value2) {
            addCriterion("update_user between", value1, value2, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotBetween(String value1, String value2) {
            addCriterion("update_user not between", value1, value2, "updateUser");
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

        public Criteria andMerchantCodeIsNull() {
            addCriterion("merchant_code is null");
            return (Criteria) this;
        }

        public Criteria andMerchantCodeIsNotNull() {
            addCriterion("merchant_code is not null");
            return (Criteria) this;
        }

        public Criteria andMerchantCodeEqualTo(String value) {
            addCriterion("merchant_code =", value, "merchantCode");
            return (Criteria) this;
        }

        public Criteria andMerchantCodeNotEqualTo(String value) {
            addCriterion("merchant_code <>", value, "merchantCode");
            return (Criteria) this;
        }

        public Criteria andMerchantCodeGreaterThan(String value) {
            addCriterion("merchant_code >", value, "merchantCode");
            return (Criteria) this;
        }

        public Criteria andMerchantCodeGreaterThanOrEqualTo(String value) {
            addCriterion("merchant_code >=", value, "merchantCode");
            return (Criteria) this;
        }

        public Criteria andMerchantCodeLessThan(String value) {
            addCriterion("merchant_code <", value, "merchantCode");
            return (Criteria) this;
        }

        public Criteria andMerchantCodeLessThanOrEqualTo(String value) {
            addCriterion("merchant_code <=", value, "merchantCode");
            return (Criteria) this;
        }

        public Criteria andMerchantCodeLike(String value) {
            addCriterion("merchant_code like", value, "merchantCode");
            return (Criteria) this;
        }

        public Criteria andMerchantCodeNotLike(String value) {
            addCriterion("merchant_code not like", value, "merchantCode");
            return (Criteria) this;
        }

        public Criteria andMerchantCodeIn(List<String> values) {
            addCriterion("merchant_code in", values, "merchantCode");
            return (Criteria) this;
        }

        public Criteria andMerchantCodeNotIn(List<String> values) {
            addCriterion("merchant_code not in", values, "merchantCode");
            return (Criteria) this;
        }

        public Criteria andMerchantCodeBetween(String value1, String value2) {
            addCriterion("merchant_code between", value1, value2, "merchantCode");
            return (Criteria) this;
        }

        public Criteria andMerchantCodeNotBetween(String value1, String value2) {
            addCriterion("merchant_code not between", value1, value2, "merchantCode");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table sys_parameter
     *
     * @mbg.generated do_not_delete_during_merge Tue Oct 26 17:00:28 ICT 2021
     */
    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table sys_parameter
     *
     * @mbg.generated Tue Oct 26 17:00:28 ICT 2021
     */
    public static class Criterion {
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