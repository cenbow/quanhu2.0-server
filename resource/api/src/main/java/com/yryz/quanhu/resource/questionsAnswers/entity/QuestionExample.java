package com.yryz.quanhu.resource.questionsAnswers.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class QuestionExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public QuestionExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
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

        public Criteria andKidIsNull() {
            addCriterion("kid is null");
            return (Criteria) this;
        }

        public Criteria andKidIsNotNull() {
            addCriterion("kid is not null");
            return (Criteria) this;
        }

        public Criteria andKidEqualTo(Long value) {
            addCriterion("kid =", value, "kid");
            return (Criteria) this;
        }

        public Criteria andKidNotEqualTo(Long value) {
            addCriterion("kid <>", value, "kid");
            return (Criteria) this;
        }

        public Criteria andKidGreaterThan(Long value) {
            addCriterion("kid >", value, "kid");
            return (Criteria) this;
        }

        public Criteria andKidGreaterThanOrEqualTo(Long value) {
            addCriterion("kid >=", value, "kid");
            return (Criteria) this;
        }

        public Criteria andKidLessThan(Long value) {
            addCriterion("kid <", value, "kid");
            return (Criteria) this;
        }

        public Criteria andKidLessThanOrEqualTo(Long value) {
            addCriterion("kid <=", value, "kid");
            return (Criteria) this;
        }

        public Criteria andKidIn(List<Long> values) {
            addCriterion("kid in", values, "kid");
            return (Criteria) this;
        }

        public Criteria andKidNotIn(List<Long> values) {
            addCriterion("kid not in", values, "kid");
            return (Criteria) this;
        }

        public Criteria andKidBetween(Long value1, Long value2) {
            addCriterion("kid between", value1, value2, "kid");
            return (Criteria) this;
        }

        public Criteria andKidNotBetween(Long value1, Long value2) {
            addCriterion("kid not between", value1, value2, "kid");
            return (Criteria) this;
        }

        public Criteria andTitleIsNull() {
            addCriterion("title is null");
            return (Criteria) this;
        }

        public Criteria andTitleIsNotNull() {
            addCriterion("title is not null");
            return (Criteria) this;
        }

        public Criteria andTitleEqualTo(String value) {
            addCriterion("title =", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotEqualTo(String value) {
            addCriterion("title <>", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThan(String value) {
            addCriterion("title >", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThanOrEqualTo(String value) {
            addCriterion("title >=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThan(String value) {
            addCriterion("title <", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThanOrEqualTo(String value) {
            addCriterion("title <=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLike(String value) {
            addCriterion("title like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotLike(String value) {
            addCriterion("title not like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleIn(List<String> values) {
            addCriterion("title in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotIn(List<String> values) {
            addCriterion("title not in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleBetween(String value1, String value2) {
            addCriterion("title between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotBetween(String value1, String value2) {
            addCriterion("title not between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andContentIsNull() {
            addCriterion("content is null");
            return (Criteria) this;
        }

        public Criteria andContentIsNotNull() {
            addCriterion("content is not null");
            return (Criteria) this;
        }

        public Criteria andContentEqualTo(String value) {
            addCriterion("content =", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotEqualTo(String value) {
            addCriterion("content <>", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentGreaterThan(String value) {
            addCriterion("content >", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentGreaterThanOrEqualTo(String value) {
            addCriterion("content >=", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLessThan(String value) {
            addCriterion("content <", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLessThanOrEqualTo(String value) {
            addCriterion("content <=", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLike(String value) {
            addCriterion("content like", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotLike(String value) {
            addCriterion("content not like", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentIn(List<String> values) {
            addCriterion("content in", values, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotIn(List<String> values) {
            addCriterion("content not in", values, "content");
            return (Criteria) this;
        }

        public Criteria andContentBetween(String value1, String value2) {
            addCriterion("content between", value1, value2, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotBetween(String value1, String value2) {
            addCriterion("content not between", value1, value2, "content");
            return (Criteria) this;
        }

        public Criteria andTargetIdIsNull() {
            addCriterion("target_id is null");
            return (Criteria) this;
        }

        public Criteria andTargetIdIsNotNull() {
            addCriterion("target_id is not null");
            return (Criteria) this;
        }

        public Criteria andTargetIdEqualTo(String value) {
            addCriterion("target_id =", value, "targetId");
            return (Criteria) this;
        }

        public Criteria andTargetIdNotEqualTo(String value) {
            addCriterion("target_id <>", value, "targetId");
            return (Criteria) this;
        }

        public Criteria andTargetIdGreaterThan(String value) {
            addCriterion("target_id >", value, "targetId");
            return (Criteria) this;
        }

        public Criteria andTargetIdGreaterThanOrEqualTo(String value) {
            addCriterion("target_id >=", value, "targetId");
            return (Criteria) this;
        }

        public Criteria andTargetIdLessThan(String value) {
            addCriterion("target_id <", value, "targetId");
            return (Criteria) this;
        }

        public Criteria andTargetIdLessThanOrEqualTo(String value) {
            addCriterion("target_id <=", value, "targetId");
            return (Criteria) this;
        }

        public Criteria andTargetIdLike(String value) {
            addCriterion("target_id like", value, "targetId");
            return (Criteria) this;
        }

        public Criteria andTargetIdNotLike(String value) {
            addCriterion("target_id not like", value, "targetId");
            return (Criteria) this;
        }

        public Criteria andTargetIdIn(List<String> values) {
            addCriterion("target_id in", values, "targetId");
            return (Criteria) this;
        }

        public Criteria andTargetIdNotIn(List<String> values) {
            addCriterion("target_id not in", values, "targetId");
            return (Criteria) this;
        }

        public Criteria andTargetIdBetween(String value1, String value2) {
            addCriterion("target_id between", value1, value2, "targetId");
            return (Criteria) this;
        }

        public Criteria andTargetIdNotBetween(String value1, String value2) {
            addCriterion("target_id not between", value1, value2, "targetId");
            return (Criteria) this;
        }

        public Criteria andQuestionTypeIsNull() {
            addCriterion("question_type is null");
            return (Criteria) this;
        }

        public Criteria andQuestionTypeIsNotNull() {
            addCriterion("question_type is not null");
            return (Criteria) this;
        }

        public Criteria andQuestionTypeEqualTo(Byte value) {
            addCriterion("question_type =", value, "questionType");
            return (Criteria) this;
        }

        public Criteria andQuestionTypeNotEqualTo(Byte value) {
            addCriterion("question_type <>", value, "questionType");
            return (Criteria) this;
        }

        public Criteria andQuestionTypeGreaterThan(Byte value) {
            addCriterion("question_type >", value, "questionType");
            return (Criteria) this;
        }

        public Criteria andQuestionTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("question_type >=", value, "questionType");
            return (Criteria) this;
        }

        public Criteria andQuestionTypeLessThan(Byte value) {
            addCriterion("question_type <", value, "questionType");
            return (Criteria) this;
        }

        public Criteria andQuestionTypeLessThanOrEqualTo(Byte value) {
            addCriterion("question_type <=", value, "questionType");
            return (Criteria) this;
        }

        public Criteria andQuestionTypeIn(List<Byte> values) {
            addCriterion("question_type in", values, "questionType");
            return (Criteria) this;
        }

        public Criteria andQuestionTypeNotIn(List<Byte> values) {
            addCriterion("question_type not in", values, "questionType");
            return (Criteria) this;
        }

        public Criteria andQuestionTypeBetween(Byte value1, Byte value2) {
            addCriterion("question_type between", value1, value2, "questionType");
            return (Criteria) this;
        }

        public Criteria andQuestionTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("question_type not between", value1, value2, "questionType");
            return (Criteria) this;
        }

        public Criteria andShelveFlagIsNull() {
            addCriterion("shelve_flag is null");
            return (Criteria) this;
        }

        public Criteria andShelveFlagIsNotNull() {
            addCriterion("shelve_flag is not null");
            return (Criteria) this;
        }

        public Criteria andShelveFlagEqualTo(Byte value) {
            addCriterion("shelve_flag =", value, "shelveFlag");
            return (Criteria) this;
        }

        public Criteria andShelveFlagNotEqualTo(Byte value) {
            addCriterion("shelve_flag <>", value, "shelveFlag");
            return (Criteria) this;
        }

        public Criteria andShelveFlagGreaterThan(Byte value) {
            addCriterion("shelve_flag >", value, "shelveFlag");
            return (Criteria) this;
        }

        public Criteria andShelveFlagGreaterThanOrEqualTo(Byte value) {
            addCriterion("shelve_flag >=", value, "shelveFlag");
            return (Criteria) this;
        }

        public Criteria andShelveFlagLessThan(Byte value) {
            addCriterion("shelve_flag <", value, "shelveFlag");
            return (Criteria) this;
        }

        public Criteria andShelveFlagLessThanOrEqualTo(Byte value) {
            addCriterion("shelve_flag <=", value, "shelveFlag");
            return (Criteria) this;
        }

        public Criteria andShelveFlagIn(List<Byte> values) {
            addCriterion("shelve_flag in", values, "shelveFlag");
            return (Criteria) this;
        }

        public Criteria andShelveFlagNotIn(List<Byte> values) {
            addCriterion("shelve_flag not in", values, "shelveFlag");
            return (Criteria) this;
        }

        public Criteria andShelveFlagBetween(Byte value1, Byte value2) {
            addCriterion("shelve_flag between", value1, value2, "shelveFlag");
            return (Criteria) this;
        }

        public Criteria andShelveFlagNotBetween(Byte value1, Byte value2) {
            addCriterion("shelve_flag not between", value1, value2, "shelveFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagIsNull() {
            addCriterion("del_flag is null");
            return (Criteria) this;
        }

        public Criteria andDelFlagIsNotNull() {
            addCriterion("del_flag is not null");
            return (Criteria) this;
        }

        public Criteria andDelFlagEqualTo(Byte value) {
            addCriterion("del_flag =", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagNotEqualTo(Byte value) {
            addCriterion("del_flag <>", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagGreaterThan(Byte value) {
            addCriterion("del_flag >", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagGreaterThanOrEqualTo(Byte value) {
            addCriterion("del_flag >=", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagLessThan(Byte value) {
            addCriterion("del_flag <", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagLessThanOrEqualTo(Byte value) {
            addCriterion("del_flag <=", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagIn(List<Byte> values) {
            addCriterion("del_flag in", values, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagNotIn(List<Byte> values) {
            addCriterion("del_flag not in", values, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagBetween(Byte value1, Byte value2) {
            addCriterion("del_flag between", value1, value2, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagNotBetween(Byte value1, Byte value2) {
            addCriterion("del_flag not between", value1, value2, "delFlag");
            return (Criteria) this;
        }

        public Criteria andCreateDateIsNull() {
            addCriterion("create_date is null");
            return (Criteria) this;
        }

        public Criteria andCreateDateIsNotNull() {
            addCriterion("create_date is not null");
            return (Criteria) this;
        }

        public Criteria andCreateDateEqualTo(Date value) {
            addCriterion("create_date =", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotEqualTo(Date value) {
            addCriterion("create_date <>", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateGreaterThan(Date value) {
            addCriterion("create_date >", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateGreaterThanOrEqualTo(Date value) {
            addCriterion("create_date >=", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateLessThan(Date value) {
            addCriterion("create_date <", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateLessThanOrEqualTo(Date value) {
            addCriterion("create_date <=", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateIn(List<Date> values) {
            addCriterion("create_date in", values, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotIn(List<Date> values) {
            addCriterion("create_date not in", values, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateBetween(Date value1, Date value2) {
            addCriterion("create_date between", value1, value2, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotBetween(Date value1, Date value2) {
            addCriterion("create_date not between", value1, value2, "createDate");
            return (Criteria) this;
        }

        public Criteria andLastUpdateDateIsNull() {
            addCriterion("last_update_date is null");
            return (Criteria) this;
        }

        public Criteria andLastUpdateDateIsNotNull() {
            addCriterion("last_update_date is not null");
            return (Criteria) this;
        }

        public Criteria andLastUpdateDateEqualTo(Date value) {
            addCriterion("last_update_date =", value, "lastUpdateDate");
            return (Criteria) this;
        }

        public Criteria andLastUpdateDateNotEqualTo(Date value) {
            addCriterion("last_update_date <>", value, "lastUpdateDate");
            return (Criteria) this;
        }

        public Criteria andLastUpdateDateGreaterThan(Date value) {
            addCriterion("last_update_date >", value, "lastUpdateDate");
            return (Criteria) this;
        }

        public Criteria andLastUpdateDateGreaterThanOrEqualTo(Date value) {
            addCriterion("last_update_date >=", value, "lastUpdateDate");
            return (Criteria) this;
        }

        public Criteria andLastUpdateDateLessThan(Date value) {
            addCriterion("last_update_date <", value, "lastUpdateDate");
            return (Criteria) this;
        }

        public Criteria andLastUpdateDateLessThanOrEqualTo(Date value) {
            addCriterion("last_update_date <=", value, "lastUpdateDate");
            return (Criteria) this;
        }

        public Criteria andLastUpdateDateIn(List<Date> values) {
            addCriterion("last_update_date in", values, "lastUpdateDate");
            return (Criteria) this;
        }

        public Criteria andLastUpdateDateNotIn(List<Date> values) {
            addCriterion("last_update_date not in", values, "lastUpdateDate");
            return (Criteria) this;
        }

        public Criteria andLastUpdateDateBetween(Date value1, Date value2) {
            addCriterion("last_update_date between", value1, value2, "lastUpdateDate");
            return (Criteria) this;
        }

        public Criteria andLastUpdateDateNotBetween(Date value1, Date value2) {
            addCriterion("last_update_date not between", value1, value2, "lastUpdateDate");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdIsNull() {
            addCriterion("create_user_id is null");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdIsNotNull() {
            addCriterion("create_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdEqualTo(Long value) {
            addCriterion("create_user_id =", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdNotEqualTo(Long value) {
            addCriterion("create_user_id <>", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdGreaterThan(Long value) {
            addCriterion("create_user_id >", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdGreaterThanOrEqualTo(Long value) {
            addCriterion("create_user_id >=", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdLessThan(Long value) {
            addCriterion("create_user_id <", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdLessThanOrEqualTo(Long value) {
            addCriterion("create_user_id <=", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdIn(List<Long> values) {
            addCriterion("create_user_id in", values, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdNotIn(List<Long> values) {
            addCriterion("create_user_id not in", values, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdBetween(Long value1, Long value2) {
            addCriterion("create_user_id between", value1, value2, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdNotBetween(Long value1, Long value2) {
            addCriterion("create_user_id not between", value1, value2, "createUserId");
            return (Criteria) this;
        }

        public Criteria andLastUpdateUserIdIsNull() {
            addCriterion("last_update_user_id is null");
            return (Criteria) this;
        }

        public Criteria andLastUpdateUserIdIsNotNull() {
            addCriterion("last_update_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andLastUpdateUserIdEqualTo(Long value) {
            addCriterion("last_update_user_id =", value, "lastUpdateUserId");
            return (Criteria) this;
        }

        public Criteria andLastUpdateUserIdNotEqualTo(Long value) {
            addCriterion("last_update_user_id <>", value, "lastUpdateUserId");
            return (Criteria) this;
        }

        public Criteria andLastUpdateUserIdGreaterThan(Long value) {
            addCriterion("last_update_user_id >", value, "lastUpdateUserId");
            return (Criteria) this;
        }

        public Criteria andLastUpdateUserIdGreaterThanOrEqualTo(Long value) {
            addCriterion("last_update_user_id >=", value, "lastUpdateUserId");
            return (Criteria) this;
        }

        public Criteria andLastUpdateUserIdLessThan(Long value) {
            addCriterion("last_update_user_id <", value, "lastUpdateUserId");
            return (Criteria) this;
        }

        public Criteria andLastUpdateUserIdLessThanOrEqualTo(Long value) {
            addCriterion("last_update_user_id <=", value, "lastUpdateUserId");
            return (Criteria) this;
        }

        public Criteria andLastUpdateUserIdIn(List<Long> values) {
            addCriterion("last_update_user_id in", values, "lastUpdateUserId");
            return (Criteria) this;
        }

        public Criteria andLastUpdateUserIdNotIn(List<Long> values) {
            addCriterion("last_update_user_id not in", values, "lastUpdateUserId");
            return (Criteria) this;
        }

        public Criteria andLastUpdateUserIdBetween(Long value1, Long value2) {
            addCriterion("last_update_user_id between", value1, value2, "lastUpdateUserId");
            return (Criteria) this;
        }

        public Criteria andLastUpdateUserIdNotBetween(Long value1, Long value2) {
            addCriterion("last_update_user_id not between", value1, value2, "lastUpdateUserId");
            return (Criteria) this;
        }

        public Criteria andRevisionIsNull() {
            addCriterion("revision is null");
            return (Criteria) this;
        }

        public Criteria andRevisionIsNotNull() {
            addCriterion("revision is not null");
            return (Criteria) this;
        }

        public Criteria andRevisionEqualTo(Integer value) {
            addCriterion("revision =", value, "revision");
            return (Criteria) this;
        }

        public Criteria andRevisionNotEqualTo(Integer value) {
            addCriterion("revision <>", value, "revision");
            return (Criteria) this;
        }

        public Criteria andRevisionGreaterThan(Integer value) {
            addCriterion("revision >", value, "revision");
            return (Criteria) this;
        }

        public Criteria andRevisionGreaterThanOrEqualTo(Integer value) {
            addCriterion("revision >=", value, "revision");
            return (Criteria) this;
        }

        public Criteria andRevisionLessThan(Integer value) {
            addCriterion("revision <", value, "revision");
            return (Criteria) this;
        }

        public Criteria andRevisionLessThanOrEqualTo(Integer value) {
            addCriterion("revision <=", value, "revision");
            return (Criteria) this;
        }

        public Criteria andRevisionIn(List<Integer> values) {
            addCriterion("revision in", values, "revision");
            return (Criteria) this;
        }

        public Criteria andRevisionNotIn(List<Integer> values) {
            addCriterion("revision not in", values, "revision");
            return (Criteria) this;
        }

        public Criteria andRevisionBetween(Integer value1, Integer value2) {
            addCriterion("revision between", value1, value2, "revision");
            return (Criteria) this;
        }

        public Criteria andRevisionNotBetween(Integer value1, Integer value2) {
            addCriterion("revision not between", value1, value2, "revision");
            return (Criteria) this;
        }

        public Criteria andOperatorIdIsNull() {
            addCriterion("operator_id is null");
            return (Criteria) this;
        }

        public Criteria andOperatorIdIsNotNull() {
            addCriterion("operator_id is not null");
            return (Criteria) this;
        }

        public Criteria andOperatorIdEqualTo(String value) {
            addCriterion("operator_id =", value, "operatorId");
            return (Criteria) this;
        }

        public Criteria andOperatorIdNotEqualTo(String value) {
            addCriterion("operator_id <>", value, "operatorId");
            return (Criteria) this;
        }

        public Criteria andOperatorIdGreaterThan(String value) {
            addCriterion("operator_id >", value, "operatorId");
            return (Criteria) this;
        }

        public Criteria andOperatorIdGreaterThanOrEqualTo(String value) {
            addCriterion("operator_id >=", value, "operatorId");
            return (Criteria) this;
        }

        public Criteria andOperatorIdLessThan(String value) {
            addCriterion("operator_id <", value, "operatorId");
            return (Criteria) this;
        }

        public Criteria andOperatorIdLessThanOrEqualTo(String value) {
            addCriterion("operator_id <=", value, "operatorId");
            return (Criteria) this;
        }

        public Criteria andOperatorIdLike(String value) {
            addCriterion("operator_id like", value, "operatorId");
            return (Criteria) this;
        }

        public Criteria andOperatorIdNotLike(String value) {
            addCriterion("operator_id not like", value, "operatorId");
            return (Criteria) this;
        }

        public Criteria andOperatorIdIn(List<String> values) {
            addCriterion("operator_id in", values, "operatorId");
            return (Criteria) this;
        }

        public Criteria andOperatorIdNotIn(List<String> values) {
            addCriterion("operator_id not in", values, "operatorId");
            return (Criteria) this;
        }

        public Criteria andOperatorIdBetween(String value1, String value2) {
            addCriterion("operator_id between", value1, value2, "operatorId");
            return (Criteria) this;
        }

        public Criteria andOperatorIdNotBetween(String value1, String value2) {
            addCriterion("operator_id not between", value1, value2, "operatorId");
            return (Criteria) this;
        }

        public Criteria andCoterieIdIsNull() {
            addCriterion("coterie_id is null");
            return (Criteria) this;
        }

        public Criteria andCoterieIdIsNotNull() {
            addCriterion("coterie_id is not null");
            return (Criteria) this;
        }

        public Criteria andCoterieIdEqualTo(String value) {
            addCriterion("coterie_id =", value, "coterieId");
            return (Criteria) this;
        }

        public Criteria andCoterieIdNotEqualTo(String value) {
            addCriterion("coterie_id <>", value, "coterieId");
            return (Criteria) this;
        }

        public Criteria andCoterieIdGreaterThan(String value) {
            addCriterion("coterie_id >", value, "coterieId");
            return (Criteria) this;
        }

        public Criteria andCoterieIdGreaterThanOrEqualTo(String value) {
            addCriterion("coterie_id >=", value, "coterieId");
            return (Criteria) this;
        }

        public Criteria andCoterieIdLessThan(String value) {
            addCriterion("coterie_id <", value, "coterieId");
            return (Criteria) this;
        }

        public Criteria andCoterieIdLessThanOrEqualTo(String value) {
            addCriterion("coterie_id <=", value, "coterieId");
            return (Criteria) this;
        }

        public Criteria andCoterieIdLike(String value) {
            addCriterion("coterie_id like", value, "coterieId");
            return (Criteria) this;
        }

        public Criteria andCoterieIdNotLike(String value) {
            addCriterion("coterie_id not like", value, "coterieId");
            return (Criteria) this;
        }

        public Criteria andCoterieIdIn(List<String> values) {
            addCriterion("coterie_id in", values, "coterieId");
            return (Criteria) this;
        }

        public Criteria andCoterieIdNotIn(List<String> values) {
            addCriterion("coterie_id not in", values, "coterieId");
            return (Criteria) this;
        }

        public Criteria andCoterieIdBetween(String value1, String value2) {
            addCriterion("coterie_id between", value1, value2, "coterieId");
            return (Criteria) this;
        }

        public Criteria andCoterieIdNotBetween(String value1, String value2) {
            addCriterion("coterie_id not between", value1, value2, "coterieId");
            return (Criteria) this;
        }

        public Criteria andValidTimeIsNull() {
            addCriterion("valid_time is null");
            return (Criteria) this;
        }

        public Criteria andValidTimeIsNotNull() {
            addCriterion("valid_time is not null");
            return (Criteria) this;
        }

        public Criteria andValidTimeEqualTo(Integer value) {
            addCriterion("valid_time =", value, "validTime");
            return (Criteria) this;
        }

        public Criteria andValidTimeNotEqualTo(Integer value) {
            addCriterion("valid_time <>", value, "validTime");
            return (Criteria) this;
        }

        public Criteria andValidTimeGreaterThan(Integer value) {
            addCriterion("valid_time >", value, "validTime");
            return (Criteria) this;
        }

        public Criteria andValidTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("valid_time >=", value, "validTime");
            return (Criteria) this;
        }

        public Criteria andValidTimeLessThan(Integer value) {
            addCriterion("valid_time <", value, "validTime");
            return (Criteria) this;
        }

        public Criteria andValidTimeLessThanOrEqualTo(Integer value) {
            addCriterion("valid_time <=", value, "validTime");
            return (Criteria) this;
        }

        public Criteria andValidTimeIn(List<Integer> values) {
            addCriterion("valid_time in", values, "validTime");
            return (Criteria) this;
        }

        public Criteria andValidTimeNotIn(List<Integer> values) {
            addCriterion("valid_time not in", values, "validTime");
            return (Criteria) this;
        }

        public Criteria andValidTimeBetween(Integer value1, Integer value2) {
            addCriterion("valid_time between", value1, value2, "validTime");
            return (Criteria) this;
        }

        public Criteria andValidTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("valid_time not between", value1, value2, "validTime");
            return (Criteria) this;
        }

        public Criteria andChargeAmountIsNull() {
            addCriterion("charge_amount is null");
            return (Criteria) this;
        }

        public Criteria andChargeAmountIsNotNull() {
            addCriterion("charge_amount is not null");
            return (Criteria) this;
        }

        public Criteria andChargeAmountEqualTo(Long value) {
            addCriterion("charge_amount =", value, "chargeAmount");
            return (Criteria) this;
        }

        public Criteria andChargeAmountNotEqualTo(Long value) {
            addCriterion("charge_amount <>", value, "chargeAmount");
            return (Criteria) this;
        }

        public Criteria andChargeAmountGreaterThan(Long value) {
            addCriterion("charge_amount >", value, "chargeAmount");
            return (Criteria) this;
        }

        public Criteria andChargeAmountGreaterThanOrEqualTo(Long value) {
            addCriterion("charge_amount >=", value, "chargeAmount");
            return (Criteria) this;
        }

        public Criteria andChargeAmountLessThan(Long value) {
            addCriterion("charge_amount <", value, "chargeAmount");
            return (Criteria) this;
        }

        public Criteria andChargeAmountLessThanOrEqualTo(Long value) {
            addCriterion("charge_amount <=", value, "chargeAmount");
            return (Criteria) this;
        }

        public Criteria andChargeAmountIn(List<Long> values) {
            addCriterion("charge_amount in", values, "chargeAmount");
            return (Criteria) this;
        }

        public Criteria andChargeAmountNotIn(List<Long> values) {
            addCriterion("charge_amount not in", values, "chargeAmount");
            return (Criteria) this;
        }

        public Criteria andChargeAmountBetween(Long value1, Long value2) {
            addCriterion("charge_amount between", value1, value2, "chargeAmount");
            return (Criteria) this;
        }

        public Criteria andChargeAmountNotBetween(Long value1, Long value2) {
            addCriterion("charge_amount not between", value1, value2, "chargeAmount");
            return (Criteria) this;
        }

        public Criteria andIsOnlyShowMeIsNull() {
            addCriterion("is_only_show_me is null");
            return (Criteria) this;
        }

        public Criteria andIsOnlyShowMeIsNotNull() {
            addCriterion("is_only_show_me is not null");
            return (Criteria) this;
        }

        public Criteria andIsOnlyShowMeEqualTo(Byte value) {
            addCriterion("is_only_show_me =", value, "isOnlyShowMe");
            return (Criteria) this;
        }

        public Criteria andIsOnlyShowMeNotEqualTo(Byte value) {
            addCriterion("is_only_show_me <>", value, "isOnlyShowMe");
            return (Criteria) this;
        }

        public Criteria andIsOnlyShowMeGreaterThan(Byte value) {
            addCriterion("is_only_show_me >", value, "isOnlyShowMe");
            return (Criteria) this;
        }

        public Criteria andIsOnlyShowMeGreaterThanOrEqualTo(Byte value) {
            addCriterion("is_only_show_me >=", value, "isOnlyShowMe");
            return (Criteria) this;
        }

        public Criteria andIsOnlyShowMeLessThan(Byte value) {
            addCriterion("is_only_show_me <", value, "isOnlyShowMe");
            return (Criteria) this;
        }

        public Criteria andIsOnlyShowMeLessThanOrEqualTo(Byte value) {
            addCriterion("is_only_show_me <=", value, "isOnlyShowMe");
            return (Criteria) this;
        }

        public Criteria andIsOnlyShowMeIn(List<Byte> values) {
            addCriterion("is_only_show_me in", values, "isOnlyShowMe");
            return (Criteria) this;
        }

        public Criteria andIsOnlyShowMeNotIn(List<Byte> values) {
            addCriterion("is_only_show_me not in", values, "isOnlyShowMe");
            return (Criteria) this;
        }

        public Criteria andIsOnlyShowMeBetween(Byte value1, Byte value2) {
            addCriterion("is_only_show_me between", value1, value2, "isOnlyShowMe");
            return (Criteria) this;
        }

        public Criteria andIsOnlyShowMeNotBetween(Byte value1, Byte value2) {
            addCriterion("is_only_show_me not between", value1, value2, "isOnlyShowMe");
            return (Criteria) this;
        }

        public Criteria andIsAnonymityIsNull() {
            addCriterion("is_anonymity is null");
            return (Criteria) this;
        }

        public Criteria andIsAnonymityIsNotNull() {
            addCriterion("is_anonymity is not null");
            return (Criteria) this;
        }

        public Criteria andIsAnonymityEqualTo(Byte value) {
            addCriterion("is_anonymity =", value, "isAnonymity");
            return (Criteria) this;
        }

        public Criteria andIsAnonymityNotEqualTo(Byte value) {
            addCriterion("is_anonymity <>", value, "isAnonymity");
            return (Criteria) this;
        }

        public Criteria andIsAnonymityGreaterThan(Byte value) {
            addCriterion("is_anonymity >", value, "isAnonymity");
            return (Criteria) this;
        }

        public Criteria andIsAnonymityGreaterThanOrEqualTo(Byte value) {
            addCriterion("is_anonymity >=", value, "isAnonymity");
            return (Criteria) this;
        }

        public Criteria andIsAnonymityLessThan(Byte value) {
            addCriterion("is_anonymity <", value, "isAnonymity");
            return (Criteria) this;
        }

        public Criteria andIsAnonymityLessThanOrEqualTo(Byte value) {
            addCriterion("is_anonymity <=", value, "isAnonymity");
            return (Criteria) this;
        }

        public Criteria andIsAnonymityIn(List<Byte> values) {
            addCriterion("is_anonymity in", values, "isAnonymity");
            return (Criteria) this;
        }

        public Criteria andIsAnonymityNotIn(List<Byte> values) {
            addCriterion("is_anonymity not in", values, "isAnonymity");
            return (Criteria) this;
        }

        public Criteria andIsAnonymityBetween(Byte value1, Byte value2) {
            addCriterion("is_anonymity between", value1, value2, "isAnonymity");
            return (Criteria) this;
        }

        public Criteria andIsAnonymityNotBetween(Byte value1, Byte value2) {
            addCriterion("is_anonymity not between", value1, value2, "isAnonymity");
            return (Criteria) this;
        }

        public Criteria andIsValidIsNull() {
            addCriterion("is_valid is null");
            return (Criteria) this;
        }

        public Criteria andIsValidIsNotNull() {
            addCriterion("is_valid is not null");
            return (Criteria) this;
        }

        public Criteria andIsValidEqualTo(Byte value) {
            addCriterion("is_valid =", value, "isValid");
            return (Criteria) this;
        }

        public Criteria andIsValidNotEqualTo(Byte value) {
            addCriterion("is_valid <>", value, "isValid");
            return (Criteria) this;
        }

        public Criteria andIsValidGreaterThan(Byte value) {
            addCriterion("is_valid >", value, "isValid");
            return (Criteria) this;
        }

        public Criteria andIsValidGreaterThanOrEqualTo(Byte value) {
            addCriterion("is_valid >=", value, "isValid");
            return (Criteria) this;
        }

        public Criteria andIsValidLessThan(Byte value) {
            addCriterion("is_valid <", value, "isValid");
            return (Criteria) this;
        }

        public Criteria andIsValidLessThanOrEqualTo(Byte value) {
            addCriterion("is_valid <=", value, "isValid");
            return (Criteria) this;
        }

        public Criteria andIsValidIn(List<Byte> values) {
            addCriterion("is_valid in", values, "isValid");
            return (Criteria) this;
        }

        public Criteria andIsValidNotIn(List<Byte> values) {
            addCriterion("is_valid not in", values, "isValid");
            return (Criteria) this;
        }

        public Criteria andIsValidBetween(Byte value1, Byte value2) {
            addCriterion("is_valid between", value1, value2, "isValid");
            return (Criteria) this;
        }

        public Criteria andIsValidNotBetween(Byte value1, Byte value2) {
            addCriterion("is_valid not between", value1, value2, "isValid");
            return (Criteria) this;
        }

        public Criteria andCityCodeIsNull() {
            addCriterion("city_code is null");
            return (Criteria) this;
        }

        public Criteria andCityCodeIsNotNull() {
            addCriterion("city_code is not null");
            return (Criteria) this;
        }

        public Criteria andCityCodeEqualTo(String value) {
            addCriterion("city_code =", value, "cityCode");
            return (Criteria) this;
        }

        public Criteria andCityCodeNotEqualTo(String value) {
            addCriterion("city_code <>", value, "cityCode");
            return (Criteria) this;
        }

        public Criteria andCityCodeGreaterThan(String value) {
            addCriterion("city_code >", value, "cityCode");
            return (Criteria) this;
        }

        public Criteria andCityCodeGreaterThanOrEqualTo(String value) {
            addCriterion("city_code >=", value, "cityCode");
            return (Criteria) this;
        }

        public Criteria andCityCodeLessThan(String value) {
            addCriterion("city_code <", value, "cityCode");
            return (Criteria) this;
        }

        public Criteria andCityCodeLessThanOrEqualTo(String value) {
            addCriterion("city_code <=", value, "cityCode");
            return (Criteria) this;
        }

        public Criteria andCityCodeLike(String value) {
            addCriterion("city_code like", value, "cityCode");
            return (Criteria) this;
        }

        public Criteria andCityCodeNotLike(String value) {
            addCriterion("city_code not like", value, "cityCode");
            return (Criteria) this;
        }

        public Criteria andCityCodeIn(List<String> values) {
            addCriterion("city_code in", values, "cityCode");
            return (Criteria) this;
        }

        public Criteria andCityCodeNotIn(List<String> values) {
            addCriterion("city_code not in", values, "cityCode");
            return (Criteria) this;
        }

        public Criteria andCityCodeBetween(String value1, String value2) {
            addCriterion("city_code between", value1, value2, "cityCode");
            return (Criteria) this;
        }

        public Criteria andCityCodeNotBetween(String value1, String value2) {
            addCriterion("city_code not between", value1, value2, "cityCode");
            return (Criteria) this;
        }

        public Criteria andGpsIsNull() {
            addCriterion("gps is null");
            return (Criteria) this;
        }

        public Criteria andGpsIsNotNull() {
            addCriterion("gps is not null");
            return (Criteria) this;
        }

        public Criteria andGpsEqualTo(String value) {
            addCriterion("gps =", value, "gps");
            return (Criteria) this;
        }

        public Criteria andGpsNotEqualTo(String value) {
            addCriterion("gps <>", value, "gps");
            return (Criteria) this;
        }

        public Criteria andGpsGreaterThan(String value) {
            addCriterion("gps >", value, "gps");
            return (Criteria) this;
        }

        public Criteria andGpsGreaterThanOrEqualTo(String value) {
            addCriterion("gps >=", value, "gps");
            return (Criteria) this;
        }

        public Criteria andGpsLessThan(String value) {
            addCriterion("gps <", value, "gps");
            return (Criteria) this;
        }

        public Criteria andGpsLessThanOrEqualTo(String value) {
            addCriterion("gps <=", value, "gps");
            return (Criteria) this;
        }

        public Criteria andGpsLike(String value) {
            addCriterion("gps like", value, "gps");
            return (Criteria) this;
        }

        public Criteria andGpsNotLike(String value) {
            addCriterion("gps not like", value, "gps");
            return (Criteria) this;
        }

        public Criteria andGpsIn(List<String> values) {
            addCriterion("gps in", values, "gps");
            return (Criteria) this;
        }

        public Criteria andGpsNotIn(List<String> values) {
            addCriterion("gps not in", values, "gps");
            return (Criteria) this;
        }

        public Criteria andGpsBetween(String value1, String value2) {
            addCriterion("gps between", value1, value2, "gps");
            return (Criteria) this;
        }

        public Criteria andGpsNotBetween(String value1, String value2) {
            addCriterion("gps not between", value1, value2, "gps");
            return (Criteria) this;
        }

        public Criteria andOperateShelveDateIsNull() {
            addCriterion("operate_shelve_date is null");
            return (Criteria) this;
        }

        public Criteria andOperateShelveDateIsNotNull() {
            addCriterion("operate_shelve_date is not null");
            return (Criteria) this;
        }

        public Criteria andOperateShelveDateEqualTo(Date value) {
            addCriterion("operate_shelve_date =", value, "operateShelveDate");
            return (Criteria) this;
        }

        public Criteria andOperateShelveDateNotEqualTo(Date value) {
            addCriterion("operate_shelve_date <>", value, "operateShelveDate");
            return (Criteria) this;
        }

        public Criteria andOperateShelveDateGreaterThan(Date value) {
            addCriterion("operate_shelve_date >", value, "operateShelveDate");
            return (Criteria) this;
        }

        public Criteria andOperateShelveDateGreaterThanOrEqualTo(Date value) {
            addCriterion("operate_shelve_date >=", value, "operateShelveDate");
            return (Criteria) this;
        }

        public Criteria andOperateShelveDateLessThan(Date value) {
            addCriterion("operate_shelve_date <", value, "operateShelveDate");
            return (Criteria) this;
        }

        public Criteria andOperateShelveDateLessThanOrEqualTo(Date value) {
            addCriterion("operate_shelve_date <=", value, "operateShelveDate");
            return (Criteria) this;
        }

        public Criteria andOperateShelveDateIn(List<Date> values) {
            addCriterion("operate_shelve_date in", values, "operateShelveDate");
            return (Criteria) this;
        }

        public Criteria andOperateShelveDateNotIn(List<Date> values) {
            addCriterion("operate_shelve_date not in", values, "operateShelveDate");
            return (Criteria) this;
        }

        public Criteria andOperateShelveDateBetween(Date value1, Date value2) {
            addCriterion("operate_shelve_date between", value1, value2, "operateShelveDate");
            return (Criteria) this;
        }

        public Criteria andOperateShelveDateNotBetween(Date value1, Date value2) {
            addCriterion("operate_shelve_date not between", value1, value2, "operateShelveDate");
            return (Criteria) this;
        }

        public Criteria andOrderFlagIsNull() {
            addCriterion("order_flag is null");
            return (Criteria) this;
        }

        public Criteria andOrderFlagIsNotNull() {
            addCriterion("order_flag is not null");
            return (Criteria) this;
        }

        public Criteria andOrderFlagEqualTo(Byte value) {
            addCriterion("order_flag =", value, "orderFlag");
            return (Criteria) this;
        }

        public Criteria andOrderFlagNotEqualTo(Byte value) {
            addCriterion("order_flag <>", value, "orderFlag");
            return (Criteria) this;
        }

        public Criteria andOrderFlagGreaterThan(Byte value) {
            addCriterion("order_flag >", value, "orderFlag");
            return (Criteria) this;
        }

        public Criteria andOrderFlagGreaterThanOrEqualTo(Byte value) {
            addCriterion("order_flag >=", value, "orderFlag");
            return (Criteria) this;
        }

        public Criteria andOrderFlagLessThan(Byte value) {
            addCriterion("order_flag <", value, "orderFlag");
            return (Criteria) this;
        }

        public Criteria andOrderFlagLessThanOrEqualTo(Byte value) {
            addCriterion("order_flag <=", value, "orderFlag");
            return (Criteria) this;
        }

        public Criteria andOrderFlagIn(List<Byte> values) {
            addCriterion("order_flag in", values, "orderFlag");
            return (Criteria) this;
        }

        public Criteria andOrderFlagNotIn(List<Byte> values) {
            addCriterion("order_flag not in", values, "orderFlag");
            return (Criteria) this;
        }

        public Criteria andOrderFlagBetween(Byte value1, Byte value2) {
            addCriterion("order_flag between", value1, value2, "orderFlag");
            return (Criteria) this;
        }

        public Criteria andOrderFlagNotBetween(Byte value1, Byte value2) {
            addCriterion("order_flag not between", value1, value2, "orderFlag");
            return (Criteria) this;
        }

        public Criteria andOrderIdIsNull() {
            addCriterion("order_id is null");
            return (Criteria) this;
        }

        public Criteria andOrderIdIsNotNull() {
            addCriterion("order_id is not null");
            return (Criteria) this;
        }

        public Criteria andOrderIdEqualTo(String value) {
            addCriterion("order_id =", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotEqualTo(String value) {
            addCriterion("order_id <>", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdGreaterThan(String value) {
            addCriterion("order_id >", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdGreaterThanOrEqualTo(String value) {
            addCriterion("order_id >=", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLessThan(String value) {
            addCriterion("order_id <", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLessThanOrEqualTo(String value) {
            addCriterion("order_id <=", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLike(String value) {
            addCriterion("order_id like", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotLike(String value) {
            addCriterion("order_id not like", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdIn(List<String> values) {
            addCriterion("order_id in", values, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotIn(List<String> values) {
            addCriterion("order_id not in", values, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdBetween(String value1, String value2) {
            addCriterion("order_id between", value1, value2, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotBetween(String value1, String value2) {
            addCriterion("order_id not between", value1, value2, "orderId");
            return (Criteria) this;
        }

        public Criteria andRefundOrderIdIsNull() {
            addCriterion("refund_order_id is null");
            return (Criteria) this;
        }

        public Criteria andRefundOrderIdIsNotNull() {
            addCriterion("refund_order_id is not null");
            return (Criteria) this;
        }

        public Criteria andRefundOrderIdEqualTo(String value) {
            addCriterion("refund_order_id =", value, "refundOrderId");
            return (Criteria) this;
        }

        public Criteria andRefundOrderIdNotEqualTo(String value) {
            addCriterion("refund_order_id <>", value, "refundOrderId");
            return (Criteria) this;
        }

        public Criteria andRefundOrderIdGreaterThan(String value) {
            addCriterion("refund_order_id >", value, "refundOrderId");
            return (Criteria) this;
        }

        public Criteria andRefundOrderIdGreaterThanOrEqualTo(String value) {
            addCriterion("refund_order_id >=", value, "refundOrderId");
            return (Criteria) this;
        }

        public Criteria andRefundOrderIdLessThan(String value) {
            addCriterion("refund_order_id <", value, "refundOrderId");
            return (Criteria) this;
        }

        public Criteria andRefundOrderIdLessThanOrEqualTo(String value) {
            addCriterion("refund_order_id <=", value, "refundOrderId");
            return (Criteria) this;
        }

        public Criteria andRefundOrderIdLike(String value) {
            addCriterion("refund_order_id like", value, "refundOrderId");
            return (Criteria) this;
        }

        public Criteria andRefundOrderIdNotLike(String value) {
            addCriterion("refund_order_id not like", value, "refundOrderId");
            return (Criteria) this;
        }

        public Criteria andRefundOrderIdIn(List<String> values) {
            addCriterion("refund_order_id in", values, "refundOrderId");
            return (Criteria) this;
        }

        public Criteria andRefundOrderIdNotIn(List<String> values) {
            addCriterion("refund_order_id not in", values, "refundOrderId");
            return (Criteria) this;
        }

        public Criteria andRefundOrderIdBetween(String value1, String value2) {
            addCriterion("refund_order_id between", value1, value2, "refundOrderId");
            return (Criteria) this;
        }

        public Criteria andRefundOrderIdNotBetween(String value1, String value2) {
            addCriterion("refund_order_id not between", value1, value2, "refundOrderId");
            return (Criteria) this;
        }

        public Criteria andAnswerdFlagIsNull() {
            addCriterion("answerd_flag is null");
            return (Criteria) this;
        }

        public Criteria andAnswerdFlagIsNotNull() {
            addCriterion("answerd_flag is not null");
            return (Criteria) this;
        }

        public Criteria andAnswerdFlagEqualTo(Byte value) {
            addCriterion("answerd_flag =", value, "answerdFlag");
            return (Criteria) this;
        }

        public Criteria andAnswerdFlagNotEqualTo(Byte value) {
            addCriterion("answerd_flag <>", value, "answerdFlag");
            return (Criteria) this;
        }

        public Criteria andAnswerdFlagGreaterThan(Byte value) {
            addCriterion("answerd_flag >", value, "answerdFlag");
            return (Criteria) this;
        }

        public Criteria andAnswerdFlagGreaterThanOrEqualTo(Byte value) {
            addCriterion("answerd_flag >=", value, "answerdFlag");
            return (Criteria) this;
        }

        public Criteria andAnswerdFlagLessThan(Byte value) {
            addCriterion("answerd_flag <", value, "answerdFlag");
            return (Criteria) this;
        }

        public Criteria andAnswerdFlagLessThanOrEqualTo(Byte value) {
            addCriterion("answerd_flag <=", value, "answerdFlag");
            return (Criteria) this;
        }

        public Criteria andAnswerdFlagIn(List<Byte> values) {
            addCriterion("answerd_flag in", values, "answerdFlag");
            return (Criteria) this;
        }

        public Criteria andAnswerdFlagNotIn(List<Byte> values) {
            addCriterion("answerd_flag not in", values, "answerdFlag");
            return (Criteria) this;
        }

        public Criteria andAnswerdFlagBetween(Byte value1, Byte value2) {
            addCriterion("answerd_flag between", value1, value2, "answerdFlag");
            return (Criteria) this;
        }

        public Criteria andAnswerdFlagNotBetween(Byte value1, Byte value2) {
            addCriterion("answerd_flag not between", value1, value2, "answerdFlag");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

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