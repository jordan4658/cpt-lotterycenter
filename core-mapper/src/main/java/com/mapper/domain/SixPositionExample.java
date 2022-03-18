package com.mapper.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SixPositionExample implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table six_position
     *
     * @mbggenerated
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table six_position
     *
     * @mbggenerated
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table six_position
     *
     * @mbggenerated
     */
    protected List<Criteria> oredCriteria;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table six_position
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table six_position
     *
     * @mbggenerated
     */
    protected Integer offset;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table six_position
     *
     * @mbggenerated
     */
    protected Integer limit;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table six_position
     *
     * @mbggenerated
     */
    public SixPositionExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table six_position
     *
     * @mbggenerated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table six_position
     *
     * @mbggenerated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table six_position
     *
     * @mbggenerated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table six_position
     *
     * @mbggenerated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table six_position
     *
     * @mbggenerated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table six_position
     *
     * @mbggenerated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table six_position
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
     * This method corresponds to the database table six_position
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
     * This method corresponds to the database table six_position
     *
     * @mbggenerated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table six_position
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
     * This method corresponds to the database table six_position
     *
     * @mbggenerated
     */
    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table six_position
     *
     * @mbggenerated
     */
    public Integer getOffset() {
        return this.offset;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table six_position
     *
     * @mbggenerated
     */
    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table six_position
     *
     * @mbggenerated
     */
    public Integer getLimit() {
        return this.limit;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table six_position
     *
     * @mbggenerated
     */
    public SixPositionExample bound(Integer offset, Integer limit) {
        this.offset = offset;
        this.limit = limit;
        return this;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table six_position
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

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andOpenTimeIsNull() {
            addCriterion("open_time is null");
            return (Criteria) this;
        }

        public Criteria andOpenTimeIsNotNull() {
            addCriterion("open_time is not null");
            return (Criteria) this;
        }

        public Criteria andOpenTimeEqualTo(String value) {
            addCriterion("open_time =", value, "openTime");
            return (Criteria) this;
        }

        public Criteria andOpenTimeNotEqualTo(String value) {
            addCriterion("open_time <>", value, "openTime");
            return (Criteria) this;
        }

        public Criteria andOpenTimeGreaterThan(String value) {
            addCriterion("open_time >", value, "openTime");
            return (Criteria) this;
        }

        public Criteria andOpenTimeGreaterThanOrEqualTo(String value) {
            addCriterion("open_time >=", value, "openTime");
            return (Criteria) this;
        }

        public Criteria andOpenTimeLessThan(String value) {
            addCriterion("open_time <", value, "openTime");
            return (Criteria) this;
        }

        public Criteria andOpenTimeLessThanOrEqualTo(String value) {
            addCriterion("open_time <=", value, "openTime");
            return (Criteria) this;
        }

        public Criteria andOpenTimeLike(String value) {
            addCriterion("open_time like", value, "openTime");
            return (Criteria) this;
        }

        public Criteria andOpenTimeNotLike(String value) {
            addCriterion("open_time not like", value, "openTime");
            return (Criteria) this;
        }

        public Criteria andOpenTimeIn(List<String> values) {
            addCriterion("open_time in", values, "openTime");
            return (Criteria) this;
        }

        public Criteria andOpenTimeNotIn(List<String> values) {
            addCriterion("open_time not in", values, "openTime");
            return (Criteria) this;
        }

        public Criteria andOpenTimeBetween(String value1, String value2) {
            addCriterion("open_time between", value1, value2, "openTime");
            return (Criteria) this;
        }

        public Criteria andOpenTimeNotBetween(String value1, String value2) {
            addCriterion("open_time not between", value1, value2, "openTime");
            return (Criteria) this;
        }

        public Criteria andAutoStartTimeIsNull() {
            addCriterion("auto_start_time is null");
            return (Criteria) this;
        }

        public Criteria andAutoStartTimeIsNotNull() {
            addCriterion("auto_start_time is not null");
            return (Criteria) this;
        }

        public Criteria andAutoStartTimeEqualTo(String value) {
            addCriterion("auto_start_time =", value, "autoStartTime");
            return (Criteria) this;
        }

        public Criteria andAutoStartTimeNotEqualTo(String value) {
            addCriterion("auto_start_time <>", value, "autoStartTime");
            return (Criteria) this;
        }

        public Criteria andAutoStartTimeGreaterThan(String value) {
            addCriterion("auto_start_time >", value, "autoStartTime");
            return (Criteria) this;
        }

        public Criteria andAutoStartTimeGreaterThanOrEqualTo(String value) {
            addCriterion("auto_start_time >=", value, "autoStartTime");
            return (Criteria) this;
        }

        public Criteria andAutoStartTimeLessThan(String value) {
            addCriterion("auto_start_time <", value, "autoStartTime");
            return (Criteria) this;
        }

        public Criteria andAutoStartTimeLessThanOrEqualTo(String value) {
            addCriterion("auto_start_time <=", value, "autoStartTime");
            return (Criteria) this;
        }

        public Criteria andAutoStartTimeLike(String value) {
            addCriterion("auto_start_time like", value, "autoStartTime");
            return (Criteria) this;
        }

        public Criteria andAutoStartTimeNotLike(String value) {
            addCriterion("auto_start_time not like", value, "autoStartTime");
            return (Criteria) this;
        }

        public Criteria andAutoStartTimeIn(List<String> values) {
            addCriterion("auto_start_time in", values, "autoStartTime");
            return (Criteria) this;
        }

        public Criteria andAutoStartTimeNotIn(List<String> values) {
            addCriterion("auto_start_time not in", values, "autoStartTime");
            return (Criteria) this;
        }

        public Criteria andAutoStartTimeBetween(String value1, String value2) {
            addCriterion("auto_start_time between", value1, value2, "autoStartTime");
            return (Criteria) this;
        }

        public Criteria andAutoStartTimeNotBetween(String value1, String value2) {
            addCriterion("auto_start_time not between", value1, value2, "autoStartTime");
            return (Criteria) this;
        }

        public Criteria andAutoEndTimeIsNull() {
            addCriterion("auto_end_time is null");
            return (Criteria) this;
        }

        public Criteria andAutoEndTimeIsNotNull() {
            addCriterion("auto_end_time is not null");
            return (Criteria) this;
        }

        public Criteria andAutoEndTimeEqualTo(String value) {
            addCriterion("auto_end_time =", value, "autoEndTime");
            return (Criteria) this;
        }

        public Criteria andAutoEndTimeNotEqualTo(String value) {
            addCriterion("auto_end_time <>", value, "autoEndTime");
            return (Criteria) this;
        }

        public Criteria andAutoEndTimeGreaterThan(String value) {
            addCriterion("auto_end_time >", value, "autoEndTime");
            return (Criteria) this;
        }

        public Criteria andAutoEndTimeGreaterThanOrEqualTo(String value) {
            addCriterion("auto_end_time >=", value, "autoEndTime");
            return (Criteria) this;
        }

        public Criteria andAutoEndTimeLessThan(String value) {
            addCriterion("auto_end_time <", value, "autoEndTime");
            return (Criteria) this;
        }

        public Criteria andAutoEndTimeLessThanOrEqualTo(String value) {
            addCriterion("auto_end_time <=", value, "autoEndTime");
            return (Criteria) this;
        }

        public Criteria andAutoEndTimeLike(String value) {
            addCriterion("auto_end_time like", value, "autoEndTime");
            return (Criteria) this;
        }

        public Criteria andAutoEndTimeNotLike(String value) {
            addCriterion("auto_end_time not like", value, "autoEndTime");
            return (Criteria) this;
        }

        public Criteria andAutoEndTimeIn(List<String> values) {
            addCriterion("auto_end_time in", values, "autoEndTime");
            return (Criteria) this;
        }

        public Criteria andAutoEndTimeNotIn(List<String> values) {
            addCriterion("auto_end_time not in", values, "autoEndTime");
            return (Criteria) this;
        }

        public Criteria andAutoEndTimeBetween(String value1, String value2) {
            addCriterion("auto_end_time between", value1, value2, "autoEndTime");
            return (Criteria) this;
        }

        public Criteria andAutoEndTimeNotBetween(String value1, String value2) {
            addCriterion("auto_end_time not between", value1, value2, "autoEndTime");
            return (Criteria) this;
        }

        public Criteria andIsAutoIsNull() {
            addCriterion("is_auto is null");
            return (Criteria) this;
        }

        public Criteria andIsAutoIsNotNull() {
            addCriterion("is_auto is not null");
            return (Criteria) this;
        }

        public Criteria andIsAutoEqualTo(Integer value) {
            addCriterion("is_auto =", value, "isAuto");
            return (Criteria) this;
        }

        public Criteria andIsAutoNotEqualTo(Integer value) {
            addCriterion("is_auto <>", value, "isAuto");
            return (Criteria) this;
        }

        public Criteria andIsAutoGreaterThan(Integer value) {
            addCriterion("is_auto >", value, "isAuto");
            return (Criteria) this;
        }

        public Criteria andIsAutoGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_auto >=", value, "isAuto");
            return (Criteria) this;
        }

        public Criteria andIsAutoLessThan(Integer value) {
            addCriterion("is_auto <", value, "isAuto");
            return (Criteria) this;
        }

        public Criteria andIsAutoLessThanOrEqualTo(Integer value) {
            addCriterion("is_auto <=", value, "isAuto");
            return (Criteria) this;
        }

        public Criteria andIsAutoIn(List<Integer> values) {
            addCriterion("is_auto in", values, "isAuto");
            return (Criteria) this;
        }

        public Criteria andIsAutoNotIn(List<Integer> values) {
            addCriterion("is_auto not in", values, "isAuto");
            return (Criteria) this;
        }

        public Criteria andIsAutoBetween(Integer value1, Integer value2) {
            addCriterion("is_auto between", value1, value2, "isAuto");
            return (Criteria) this;
        }

        public Criteria andIsAutoNotBetween(Integer value1, Integer value2) {
            addCriterion("is_auto not between", value1, value2, "isAuto");
            return (Criteria) this;
        }

        public Criteria andQihaoIsNull() {
            addCriterion("qihao is null");
            return (Criteria) this;
        }

        public Criteria andQihaoIsNotNull() {
            addCriterion("qihao is not null");
            return (Criteria) this;
        }

        public Criteria andQihaoEqualTo(String value) {
            addCriterion("qihao =", value, "qihao");
            return (Criteria) this;
        }

        public Criteria andQihaoNotEqualTo(String value) {
            addCriterion("qihao <>", value, "qihao");
            return (Criteria) this;
        }

        public Criteria andQihaoGreaterThan(String value) {
            addCriterion("qihao >", value, "qihao");
            return (Criteria) this;
        }

        public Criteria andQihaoGreaterThanOrEqualTo(String value) {
            addCriterion("qihao >=", value, "qihao");
            return (Criteria) this;
        }

        public Criteria andQihaoLessThan(String value) {
            addCriterion("qihao <", value, "qihao");
            return (Criteria) this;
        }

        public Criteria andQihaoLessThanOrEqualTo(String value) {
            addCriterion("qihao <=", value, "qihao");
            return (Criteria) this;
        }

        public Criteria andQihaoLike(String value) {
            addCriterion("qihao like", value, "qihao");
            return (Criteria) this;
        }

        public Criteria andQihaoNotLike(String value) {
            addCriterion("qihao not like", value, "qihao");
            return (Criteria) this;
        }

        public Criteria andQihaoIn(List<String> values) {
            addCriterion("qihao in", values, "qihao");
            return (Criteria) this;
        }

        public Criteria andQihaoNotIn(List<String> values) {
            addCriterion("qihao not in", values, "qihao");
            return (Criteria) this;
        }

        public Criteria andQihaoBetween(String value1, String value2) {
            addCriterion("qihao between", value1, value2, "qihao");
            return (Criteria) this;
        }

        public Criteria andQihaoNotBetween(String value1, String value2) {
            addCriterion("qihao not between", value1, value2, "qihao");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table six_position
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
     * This class corresponds to the database table six_position
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