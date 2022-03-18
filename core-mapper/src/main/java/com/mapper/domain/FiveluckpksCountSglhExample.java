package com.mapper.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FiveluckpksCountSglhExample implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table fiveluckpks_count_sglh
     *
     * @mbggenerated
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table fiveluckpks_count_sglh
     *
     * @mbggenerated
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table fiveluckpks_count_sglh
     *
     * @mbggenerated
     */
    protected List<Criteria> oredCriteria;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table fiveluckpks_count_sglh
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table fiveluckpks_count_sglh
     *
     * @mbggenerated
     */
    protected Integer offset;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table fiveluckpks_count_sglh
     *
     * @mbggenerated
     */
    protected Integer limit;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fiveluckpks_count_sglh
     *
     * @mbggenerated
     */
    public FiveluckpksCountSglhExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fiveluckpks_count_sglh
     *
     * @mbggenerated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fiveluckpks_count_sglh
     *
     * @mbggenerated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fiveluckpks_count_sglh
     *
     * @mbggenerated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fiveluckpks_count_sglh
     *
     * @mbggenerated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fiveluckpks_count_sglh
     *
     * @mbggenerated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fiveluckpks_count_sglh
     *
     * @mbggenerated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fiveluckpks_count_sglh
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
     * This method corresponds to the database table fiveluckpks_count_sglh
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
     * This method corresponds to the database table fiveluckpks_count_sglh
     *
     * @mbggenerated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fiveluckpks_count_sglh
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
     * This method corresponds to the database table fiveluckpks_count_sglh
     *
     * @mbggenerated
     */
    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fiveluckpks_count_sglh
     *
     * @mbggenerated
     */
    public Integer getOffset() {
        return this.offset;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fiveluckpks_count_sglh
     *
     * @mbggenerated
     */
    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fiveluckpks_count_sglh
     *
     * @mbggenerated
     */
    public Integer getLimit() {
        return this.limit;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fiveluckpks_count_sglh
     *
     * @mbggenerated
     */
    public FiveluckpksCountSglhExample bound(Integer offset, Integer limit) {
        this.offset = offset;
        this.limit = limit;
        return this;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table fiveluckpks_count_sglh
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

        public Criteria andDateIsNull() {
            addCriterion("`date` is null");
            return (Criteria) this;
        }

        public Criteria andDateIsNotNull() {
            addCriterion("`date` is not null");
            return (Criteria) this;
        }

        public Criteria andDateEqualTo(String value) {
            addCriterion("`date` =", value, "date");
            return (Criteria) this;
        }

        public Criteria andDateNotEqualTo(String value) {
            addCriterion("`date` <>", value, "date");
            return (Criteria) this;
        }

        public Criteria andDateGreaterThan(String value) {
            addCriterion("`date` >", value, "date");
            return (Criteria) this;
        }

        public Criteria andDateGreaterThanOrEqualTo(String value) {
            addCriterion("`date` >=", value, "date");
            return (Criteria) this;
        }

        public Criteria andDateLessThan(String value) {
            addCriterion("`date` <", value, "date");
            return (Criteria) this;
        }

        public Criteria andDateLessThanOrEqualTo(String value) {
            addCriterion("`date` <=", value, "date");
            return (Criteria) this;
        }

        public Criteria andDateLike(String value) {
            addCriterion("`date` like", value, "date");
            return (Criteria) this;
        }

        public Criteria andDateNotLike(String value) {
            addCriterion("`date` not like", value, "date");
            return (Criteria) this;
        }

        public Criteria andDateIn(List<String> values) {
            addCriterion("`date` in", values, "date");
            return (Criteria) this;
        }

        public Criteria andDateNotIn(List<String> values) {
            addCriterion("`date` not in", values, "date");
            return (Criteria) this;
        }

        public Criteria andDateBetween(String value1, String value2) {
            addCriterion("`date` between", value1, value2, "date");
            return (Criteria) this;
        }

        public Criteria andDateNotBetween(String value1, String value2) {
            addCriterion("`date` not between", value1, value2, "date");
            return (Criteria) this;
        }

        public Criteria andOnelIsNull() {
            addCriterion("onel is null");
            return (Criteria) this;
        }

        public Criteria andOnelIsNotNull() {
            addCriterion("onel is not null");
            return (Criteria) this;
        }

        public Criteria andOnelEqualTo(Integer value) {
            addCriterion("onel =", value, "onel");
            return (Criteria) this;
        }

        public Criteria andOnelNotEqualTo(Integer value) {
            addCriterion("onel <>", value, "onel");
            return (Criteria) this;
        }

        public Criteria andOnelGreaterThan(Integer value) {
            addCriterion("onel >", value, "onel");
            return (Criteria) this;
        }

        public Criteria andOnelGreaterThanOrEqualTo(Integer value) {
            addCriterion("onel >=", value, "onel");
            return (Criteria) this;
        }

        public Criteria andOnelLessThan(Integer value) {
            addCriterion("onel <", value, "onel");
            return (Criteria) this;
        }

        public Criteria andOnelLessThanOrEqualTo(Integer value) {
            addCriterion("onel <=", value, "onel");
            return (Criteria) this;
        }

        public Criteria andOnelIn(List<Integer> values) {
            addCriterion("onel in", values, "onel");
            return (Criteria) this;
        }

        public Criteria andOnelNotIn(List<Integer> values) {
            addCriterion("onel not in", values, "onel");
            return (Criteria) this;
        }

        public Criteria andOnelBetween(Integer value1, Integer value2) {
            addCriterion("onel between", value1, value2, "onel");
            return (Criteria) this;
        }

        public Criteria andOnelNotBetween(Integer value1, Integer value2) {
            addCriterion("onel not between", value1, value2, "onel");
            return (Criteria) this;
        }

        public Criteria andOnehIsNull() {
            addCriterion("oneh is null");
            return (Criteria) this;
        }

        public Criteria andOnehIsNotNull() {
            addCriterion("oneh is not null");
            return (Criteria) this;
        }

        public Criteria andOnehEqualTo(Integer value) {
            addCriterion("oneh =", value, "oneh");
            return (Criteria) this;
        }

        public Criteria andOnehNotEqualTo(Integer value) {
            addCriterion("oneh <>", value, "oneh");
            return (Criteria) this;
        }

        public Criteria andOnehGreaterThan(Integer value) {
            addCriterion("oneh >", value, "oneh");
            return (Criteria) this;
        }

        public Criteria andOnehGreaterThanOrEqualTo(Integer value) {
            addCriterion("oneh >=", value, "oneh");
            return (Criteria) this;
        }

        public Criteria andOnehLessThan(Integer value) {
            addCriterion("oneh <", value, "oneh");
            return (Criteria) this;
        }

        public Criteria andOnehLessThanOrEqualTo(Integer value) {
            addCriterion("oneh <=", value, "oneh");
            return (Criteria) this;
        }

        public Criteria andOnehIn(List<Integer> values) {
            addCriterion("oneh in", values, "oneh");
            return (Criteria) this;
        }

        public Criteria andOnehNotIn(List<Integer> values) {
            addCriterion("oneh not in", values, "oneh");
            return (Criteria) this;
        }

        public Criteria andOnehBetween(Integer value1, Integer value2) {
            addCriterion("oneh between", value1, value2, "oneh");
            return (Criteria) this;
        }

        public Criteria andOnehNotBetween(Integer value1, Integer value2) {
            addCriterion("oneh not between", value1, value2, "oneh");
            return (Criteria) this;
        }

        public Criteria andTwolIsNull() {
            addCriterion("twol is null");
            return (Criteria) this;
        }

        public Criteria andTwolIsNotNull() {
            addCriterion("twol is not null");
            return (Criteria) this;
        }

        public Criteria andTwolEqualTo(Integer value) {
            addCriterion("twol =", value, "twol");
            return (Criteria) this;
        }

        public Criteria andTwolNotEqualTo(Integer value) {
            addCriterion("twol <>", value, "twol");
            return (Criteria) this;
        }

        public Criteria andTwolGreaterThan(Integer value) {
            addCriterion("twol >", value, "twol");
            return (Criteria) this;
        }

        public Criteria andTwolGreaterThanOrEqualTo(Integer value) {
            addCriterion("twol >=", value, "twol");
            return (Criteria) this;
        }

        public Criteria andTwolLessThan(Integer value) {
            addCriterion("twol <", value, "twol");
            return (Criteria) this;
        }

        public Criteria andTwolLessThanOrEqualTo(Integer value) {
            addCriterion("twol <=", value, "twol");
            return (Criteria) this;
        }

        public Criteria andTwolIn(List<Integer> values) {
            addCriterion("twol in", values, "twol");
            return (Criteria) this;
        }

        public Criteria andTwolNotIn(List<Integer> values) {
            addCriterion("twol not in", values, "twol");
            return (Criteria) this;
        }

        public Criteria andTwolBetween(Integer value1, Integer value2) {
            addCriterion("twol between", value1, value2, "twol");
            return (Criteria) this;
        }

        public Criteria andTwolNotBetween(Integer value1, Integer value2) {
            addCriterion("twol not between", value1, value2, "twol");
            return (Criteria) this;
        }

        public Criteria andTwohIsNull() {
            addCriterion("twoh is null");
            return (Criteria) this;
        }

        public Criteria andTwohIsNotNull() {
            addCriterion("twoh is not null");
            return (Criteria) this;
        }

        public Criteria andTwohEqualTo(Integer value) {
            addCriterion("twoh =", value, "twoh");
            return (Criteria) this;
        }

        public Criteria andTwohNotEqualTo(Integer value) {
            addCriterion("twoh <>", value, "twoh");
            return (Criteria) this;
        }

        public Criteria andTwohGreaterThan(Integer value) {
            addCriterion("twoh >", value, "twoh");
            return (Criteria) this;
        }

        public Criteria andTwohGreaterThanOrEqualTo(Integer value) {
            addCriterion("twoh >=", value, "twoh");
            return (Criteria) this;
        }

        public Criteria andTwohLessThan(Integer value) {
            addCriterion("twoh <", value, "twoh");
            return (Criteria) this;
        }

        public Criteria andTwohLessThanOrEqualTo(Integer value) {
            addCriterion("twoh <=", value, "twoh");
            return (Criteria) this;
        }

        public Criteria andTwohIn(List<Integer> values) {
            addCriterion("twoh in", values, "twoh");
            return (Criteria) this;
        }

        public Criteria andTwohNotIn(List<Integer> values) {
            addCriterion("twoh not in", values, "twoh");
            return (Criteria) this;
        }

        public Criteria andTwohBetween(Integer value1, Integer value2) {
            addCriterion("twoh between", value1, value2, "twoh");
            return (Criteria) this;
        }

        public Criteria andTwohNotBetween(Integer value1, Integer value2) {
            addCriterion("twoh not between", value1, value2, "twoh");
            return (Criteria) this;
        }

        public Criteria andThreelIsNull() {
            addCriterion("threel is null");
            return (Criteria) this;
        }

        public Criteria andThreelIsNotNull() {
            addCriterion("threel is not null");
            return (Criteria) this;
        }

        public Criteria andThreelEqualTo(Integer value) {
            addCriterion("threel =", value, "threel");
            return (Criteria) this;
        }

        public Criteria andThreelNotEqualTo(Integer value) {
            addCriterion("threel <>", value, "threel");
            return (Criteria) this;
        }

        public Criteria andThreelGreaterThan(Integer value) {
            addCriterion("threel >", value, "threel");
            return (Criteria) this;
        }

        public Criteria andThreelGreaterThanOrEqualTo(Integer value) {
            addCriterion("threel >=", value, "threel");
            return (Criteria) this;
        }

        public Criteria andThreelLessThan(Integer value) {
            addCriterion("threel <", value, "threel");
            return (Criteria) this;
        }

        public Criteria andThreelLessThanOrEqualTo(Integer value) {
            addCriterion("threel <=", value, "threel");
            return (Criteria) this;
        }

        public Criteria andThreelIn(List<Integer> values) {
            addCriterion("threel in", values, "threel");
            return (Criteria) this;
        }

        public Criteria andThreelNotIn(List<Integer> values) {
            addCriterion("threel not in", values, "threel");
            return (Criteria) this;
        }

        public Criteria andThreelBetween(Integer value1, Integer value2) {
            addCriterion("threel between", value1, value2, "threel");
            return (Criteria) this;
        }

        public Criteria andThreelNotBetween(Integer value1, Integer value2) {
            addCriterion("threel not between", value1, value2, "threel");
            return (Criteria) this;
        }

        public Criteria andThreehIsNull() {
            addCriterion("threeh is null");
            return (Criteria) this;
        }

        public Criteria andThreehIsNotNull() {
            addCriterion("threeh is not null");
            return (Criteria) this;
        }

        public Criteria andThreehEqualTo(Integer value) {
            addCriterion("threeh =", value, "threeh");
            return (Criteria) this;
        }

        public Criteria andThreehNotEqualTo(Integer value) {
            addCriterion("threeh <>", value, "threeh");
            return (Criteria) this;
        }

        public Criteria andThreehGreaterThan(Integer value) {
            addCriterion("threeh >", value, "threeh");
            return (Criteria) this;
        }

        public Criteria andThreehGreaterThanOrEqualTo(Integer value) {
            addCriterion("threeh >=", value, "threeh");
            return (Criteria) this;
        }

        public Criteria andThreehLessThan(Integer value) {
            addCriterion("threeh <", value, "threeh");
            return (Criteria) this;
        }

        public Criteria andThreehLessThanOrEqualTo(Integer value) {
            addCriterion("threeh <=", value, "threeh");
            return (Criteria) this;
        }

        public Criteria andThreehIn(List<Integer> values) {
            addCriterion("threeh in", values, "threeh");
            return (Criteria) this;
        }

        public Criteria andThreehNotIn(List<Integer> values) {
            addCriterion("threeh not in", values, "threeh");
            return (Criteria) this;
        }

        public Criteria andThreehBetween(Integer value1, Integer value2) {
            addCriterion("threeh between", value1, value2, "threeh");
            return (Criteria) this;
        }

        public Criteria andThreehNotBetween(Integer value1, Integer value2) {
            addCriterion("threeh not between", value1, value2, "threeh");
            return (Criteria) this;
        }

        public Criteria andFourlIsNull() {
            addCriterion("fourl is null");
            return (Criteria) this;
        }

        public Criteria andFourlIsNotNull() {
            addCriterion("fourl is not null");
            return (Criteria) this;
        }

        public Criteria andFourlEqualTo(Integer value) {
            addCriterion("fourl =", value, "fourl");
            return (Criteria) this;
        }

        public Criteria andFourlNotEqualTo(Integer value) {
            addCriterion("fourl <>", value, "fourl");
            return (Criteria) this;
        }

        public Criteria andFourlGreaterThan(Integer value) {
            addCriterion("fourl >", value, "fourl");
            return (Criteria) this;
        }

        public Criteria andFourlGreaterThanOrEqualTo(Integer value) {
            addCriterion("fourl >=", value, "fourl");
            return (Criteria) this;
        }

        public Criteria andFourlLessThan(Integer value) {
            addCriterion("fourl <", value, "fourl");
            return (Criteria) this;
        }

        public Criteria andFourlLessThanOrEqualTo(Integer value) {
            addCriterion("fourl <=", value, "fourl");
            return (Criteria) this;
        }

        public Criteria andFourlIn(List<Integer> values) {
            addCriterion("fourl in", values, "fourl");
            return (Criteria) this;
        }

        public Criteria andFourlNotIn(List<Integer> values) {
            addCriterion("fourl not in", values, "fourl");
            return (Criteria) this;
        }

        public Criteria andFourlBetween(Integer value1, Integer value2) {
            addCriterion("fourl between", value1, value2, "fourl");
            return (Criteria) this;
        }

        public Criteria andFourlNotBetween(Integer value1, Integer value2) {
            addCriterion("fourl not between", value1, value2, "fourl");
            return (Criteria) this;
        }

        public Criteria andFourhIsNull() {
            addCriterion("fourh is null");
            return (Criteria) this;
        }

        public Criteria andFourhIsNotNull() {
            addCriterion("fourh is not null");
            return (Criteria) this;
        }

        public Criteria andFourhEqualTo(Integer value) {
            addCriterion("fourh =", value, "fourh");
            return (Criteria) this;
        }

        public Criteria andFourhNotEqualTo(Integer value) {
            addCriterion("fourh <>", value, "fourh");
            return (Criteria) this;
        }

        public Criteria andFourhGreaterThan(Integer value) {
            addCriterion("fourh >", value, "fourh");
            return (Criteria) this;
        }

        public Criteria andFourhGreaterThanOrEqualTo(Integer value) {
            addCriterion("fourh >=", value, "fourh");
            return (Criteria) this;
        }

        public Criteria andFourhLessThan(Integer value) {
            addCriterion("fourh <", value, "fourh");
            return (Criteria) this;
        }

        public Criteria andFourhLessThanOrEqualTo(Integer value) {
            addCriterion("fourh <=", value, "fourh");
            return (Criteria) this;
        }

        public Criteria andFourhIn(List<Integer> values) {
            addCriterion("fourh in", values, "fourh");
            return (Criteria) this;
        }

        public Criteria andFourhNotIn(List<Integer> values) {
            addCriterion("fourh not in", values, "fourh");
            return (Criteria) this;
        }

        public Criteria andFourhBetween(Integer value1, Integer value2) {
            addCriterion("fourh between", value1, value2, "fourh");
            return (Criteria) this;
        }

        public Criteria andFourhNotBetween(Integer value1, Integer value2) {
            addCriterion("fourh not between", value1, value2, "fourh");
            return (Criteria) this;
        }

        public Criteria andFivelIsNull() {
            addCriterion("fivel is null");
            return (Criteria) this;
        }

        public Criteria andFivelIsNotNull() {
            addCriterion("fivel is not null");
            return (Criteria) this;
        }

        public Criteria andFivelEqualTo(Integer value) {
            addCriterion("fivel =", value, "fivel");
            return (Criteria) this;
        }

        public Criteria andFivelNotEqualTo(Integer value) {
            addCriterion("fivel <>", value, "fivel");
            return (Criteria) this;
        }

        public Criteria andFivelGreaterThan(Integer value) {
            addCriterion("fivel >", value, "fivel");
            return (Criteria) this;
        }

        public Criteria andFivelGreaterThanOrEqualTo(Integer value) {
            addCriterion("fivel >=", value, "fivel");
            return (Criteria) this;
        }

        public Criteria andFivelLessThan(Integer value) {
            addCriterion("fivel <", value, "fivel");
            return (Criteria) this;
        }

        public Criteria andFivelLessThanOrEqualTo(Integer value) {
            addCriterion("fivel <=", value, "fivel");
            return (Criteria) this;
        }

        public Criteria andFivelIn(List<Integer> values) {
            addCriterion("fivel in", values, "fivel");
            return (Criteria) this;
        }

        public Criteria andFivelNotIn(List<Integer> values) {
            addCriterion("fivel not in", values, "fivel");
            return (Criteria) this;
        }

        public Criteria andFivelBetween(Integer value1, Integer value2) {
            addCriterion("fivel between", value1, value2, "fivel");
            return (Criteria) this;
        }

        public Criteria andFivelNotBetween(Integer value1, Integer value2) {
            addCriterion("fivel not between", value1, value2, "fivel");
            return (Criteria) this;
        }

        public Criteria andFivehIsNull() {
            addCriterion("fiveh is null");
            return (Criteria) this;
        }

        public Criteria andFivehIsNotNull() {
            addCriterion("fiveh is not null");
            return (Criteria) this;
        }

        public Criteria andFivehEqualTo(Integer value) {
            addCriterion("fiveh =", value, "fiveh");
            return (Criteria) this;
        }

        public Criteria andFivehNotEqualTo(Integer value) {
            addCriterion("fiveh <>", value, "fiveh");
            return (Criteria) this;
        }

        public Criteria andFivehGreaterThan(Integer value) {
            addCriterion("fiveh >", value, "fiveh");
            return (Criteria) this;
        }

        public Criteria andFivehGreaterThanOrEqualTo(Integer value) {
            addCriterion("fiveh >=", value, "fiveh");
            return (Criteria) this;
        }

        public Criteria andFivehLessThan(Integer value) {
            addCriterion("fiveh <", value, "fiveh");
            return (Criteria) this;
        }

        public Criteria andFivehLessThanOrEqualTo(Integer value) {
            addCriterion("fiveh <=", value, "fiveh");
            return (Criteria) this;
        }

        public Criteria andFivehIn(List<Integer> values) {
            addCriterion("fiveh in", values, "fiveh");
            return (Criteria) this;
        }

        public Criteria andFivehNotIn(List<Integer> values) {
            addCriterion("fiveh not in", values, "fiveh");
            return (Criteria) this;
        }

        public Criteria andFivehBetween(Integer value1, Integer value2) {
            addCriterion("fiveh between", value1, value2, "fiveh");
            return (Criteria) this;
        }

        public Criteria andFivehNotBetween(Integer value1, Integer value2) {
            addCriterion("fiveh not between", value1, value2, "fiveh");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table fiveluckpks_count_sglh
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
     * This class corresponds to the database table fiveluckpks_count_sglh
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