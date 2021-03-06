package xyz.hellothomas.tinyurl.common.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UrlMappingExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public UrlMappingExample() {
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

        public Criteria andPartitionTagIsNull() {
            addCriterion("partition_tag is null");
            return (Criteria) this;
        }

        public Criteria andPartitionTagIsNotNull() {
            addCriterion("partition_tag is not null");
            return (Criteria) this;
        }

        public Criteria andPartitionTagEqualTo(Integer value) {
            addCriterion("partition_tag =", value, "partitionTag");
            return (Criteria) this;
        }

        public Criteria andPartitionTagNotEqualTo(Integer value) {
            addCriterion("partition_tag <>", value, "partitionTag");
            return (Criteria) this;
        }

        public Criteria andPartitionTagGreaterThan(Integer value) {
            addCriterion("partition_tag >", value, "partitionTag");
            return (Criteria) this;
        }

        public Criteria andPartitionTagGreaterThanOrEqualTo(Integer value) {
            addCriterion("partition_tag >=", value, "partitionTag");
            return (Criteria) this;
        }

        public Criteria andPartitionTagLessThan(Integer value) {
            addCriterion("partition_tag <", value, "partitionTag");
            return (Criteria) this;
        }

        public Criteria andPartitionTagLessThanOrEqualTo(Integer value) {
            addCriterion("partition_tag <=", value, "partitionTag");
            return (Criteria) this;
        }

        public Criteria andPartitionTagIn(List<Integer> values) {
            addCriterion("partition_tag in", values, "partitionTag");
            return (Criteria) this;
        }

        public Criteria andPartitionTagNotIn(List<Integer> values) {
            addCriterion("partition_tag not in", values, "partitionTag");
            return (Criteria) this;
        }

        public Criteria andPartitionTagBetween(Integer value1, Integer value2) {
            addCriterion("partition_tag between", value1, value2, "partitionTag");
            return (Criteria) this;
        }

        public Criteria andPartitionTagNotBetween(Integer value1, Integer value2) {
            addCriterion("partition_tag not between", value1, value2, "partitionTag");
            return (Criteria) this;
        }

        public Criteria andOriginUrlIsNull() {
            addCriterion("origin_url is null");
            return (Criteria) this;
        }

        public Criteria andOriginUrlIsNotNull() {
            addCriterion("origin_url is not null");
            return (Criteria) this;
        }

        public Criteria andOriginUrlEqualTo(String value) {
            addCriterion("origin_url =", value, "originUrl");
            return (Criteria) this;
        }

        public Criteria andOriginUrlNotEqualTo(String value) {
            addCriterion("origin_url <>", value, "originUrl");
            return (Criteria) this;
        }

        public Criteria andOriginUrlGreaterThan(String value) {
            addCriterion("origin_url >", value, "originUrl");
            return (Criteria) this;
        }

        public Criteria andOriginUrlGreaterThanOrEqualTo(String value) {
            addCriterion("origin_url >=", value, "originUrl");
            return (Criteria) this;
        }

        public Criteria andOriginUrlLessThan(String value) {
            addCriterion("origin_url <", value, "originUrl");
            return (Criteria) this;
        }

        public Criteria andOriginUrlLessThanOrEqualTo(String value) {
            addCriterion("origin_url <=", value, "originUrl");
            return (Criteria) this;
        }

        public Criteria andOriginUrlLike(String value) {
            addCriterion("origin_url like", value, "originUrl");
            return (Criteria) this;
        }

        public Criteria andOriginUrlNotLike(String value) {
            addCriterion("origin_url not like", value, "originUrl");
            return (Criteria) this;
        }

        public Criteria andOriginUrlIn(List<String> values) {
            addCriterion("origin_url in", values, "originUrl");
            return (Criteria) this;
        }

        public Criteria andOriginUrlNotIn(List<String> values) {
            addCriterion("origin_url not in", values, "originUrl");
            return (Criteria) this;
        }

        public Criteria andOriginUrlBetween(String value1, String value2) {
            addCriterion("origin_url between", value1, value2, "originUrl");
            return (Criteria) this;
        }

        public Criteria andOriginUrlNotBetween(String value1, String value2) {
            addCriterion("origin_url not between", value1, value2, "originUrl");
            return (Criteria) this;
        }

        public Criteria andOriginUrlMd5IsNull() {
            addCriterion("origin_url_md5 is null");
            return (Criteria) this;
        }

        public Criteria andOriginUrlMd5IsNotNull() {
            addCriterion("origin_url_md5 is not null");
            return (Criteria) this;
        }

        public Criteria andOriginUrlMd5EqualTo(String value) {
            addCriterion("origin_url_md5 =", value, "originUrlMd5");
            return (Criteria) this;
        }

        public Criteria andOriginUrlMd5NotEqualTo(String value) {
            addCriterion("origin_url_md5 <>", value, "originUrlMd5");
            return (Criteria) this;
        }

        public Criteria andOriginUrlMd5GreaterThan(String value) {
            addCriterion("origin_url_md5 >", value, "originUrlMd5");
            return (Criteria) this;
        }

        public Criteria andOriginUrlMd5GreaterThanOrEqualTo(String value) {
            addCriterion("origin_url_md5 >=", value, "originUrlMd5");
            return (Criteria) this;
        }

        public Criteria andOriginUrlMd5LessThan(String value) {
            addCriterion("origin_url_md5 <", value, "originUrlMd5");
            return (Criteria) this;
        }

        public Criteria andOriginUrlMd5LessThanOrEqualTo(String value) {
            addCriterion("origin_url_md5 <=", value, "originUrlMd5");
            return (Criteria) this;
        }

        public Criteria andOriginUrlMd5Like(String value) {
            addCriterion("origin_url_md5 like", value, "originUrlMd5");
            return (Criteria) this;
        }

        public Criteria andOriginUrlMd5NotLike(String value) {
            addCriterion("origin_url_md5 not like", value, "originUrlMd5");
            return (Criteria) this;
        }

        public Criteria andOriginUrlMd5In(List<String> values) {
            addCriterion("origin_url_md5 in", values, "originUrlMd5");
            return (Criteria) this;
        }

        public Criteria andOriginUrlMd5NotIn(List<String> values) {
            addCriterion("origin_url_md5 not in", values, "originUrlMd5");
            return (Criteria) this;
        }

        public Criteria andOriginUrlMd5Between(String value1, String value2) {
            addCriterion("origin_url_md5 between", value1, value2, "originUrlMd5");
            return (Criteria) this;
        }

        public Criteria andOriginUrlMd5NotBetween(String value1, String value2) {
            addCriterion("origin_url_md5 not between", value1, value2, "originUrlMd5");
            return (Criteria) this;
        }

        public Criteria andOriginUrlTypeIsNull() {
            addCriterion("origin_url_type is null");
            return (Criteria) this;
        }

        public Criteria andOriginUrlTypeIsNotNull() {
            addCriterion("origin_url_type is not null");
            return (Criteria) this;
        }

        public Criteria andOriginUrlTypeEqualTo(Integer value) {
            addCriterion("origin_url_type =", value, "originUrlType");
            return (Criteria) this;
        }

        public Criteria andOriginUrlTypeNotEqualTo(Integer value) {
            addCriterion("origin_url_type <>", value, "originUrlType");
            return (Criteria) this;
        }

        public Criteria andOriginUrlTypeGreaterThan(Integer value) {
            addCriterion("origin_url_type >", value, "originUrlType");
            return (Criteria) this;
        }

        public Criteria andOriginUrlTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("origin_url_type >=", value, "originUrlType");
            return (Criteria) this;
        }

        public Criteria andOriginUrlTypeLessThan(Integer value) {
            addCriterion("origin_url_type <", value, "originUrlType");
            return (Criteria) this;
        }

        public Criteria andOriginUrlTypeLessThanOrEqualTo(Integer value) {
            addCriterion("origin_url_type <=", value, "originUrlType");
            return (Criteria) this;
        }

        public Criteria andOriginUrlTypeIn(List<Integer> values) {
            addCriterion("origin_url_type in", values, "originUrlType");
            return (Criteria) this;
        }

        public Criteria andOriginUrlTypeNotIn(List<Integer> values) {
            addCriterion("origin_url_type not in", values, "originUrlType");
            return (Criteria) this;
        }

        public Criteria andOriginUrlTypeBetween(Integer value1, Integer value2) {
            addCriterion("origin_url_type between", value1, value2, "originUrlType");
            return (Criteria) this;
        }

        public Criteria andOriginUrlTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("origin_url_type not between", value1, value2, "originUrlType");
            return (Criteria) this;
        }

        public Criteria andTinyUrlIsNull() {
            addCriterion("tiny_url is null");
            return (Criteria) this;
        }

        public Criteria andTinyUrlIsNotNull() {
            addCriterion("tiny_url is not null");
            return (Criteria) this;
        }

        public Criteria andTinyUrlEqualTo(String value) {
            addCriterion("tiny_url =", value, "tinyUrl");
            return (Criteria) this;
        }

        public Criteria andTinyUrlNotEqualTo(String value) {
            addCriterion("tiny_url <>", value, "tinyUrl");
            return (Criteria) this;
        }

        public Criteria andTinyUrlGreaterThan(String value) {
            addCriterion("tiny_url >", value, "tinyUrl");
            return (Criteria) this;
        }

        public Criteria andTinyUrlGreaterThanOrEqualTo(String value) {
            addCriterion("tiny_url >=", value, "tinyUrl");
            return (Criteria) this;
        }

        public Criteria andTinyUrlLessThan(String value) {
            addCriterion("tiny_url <", value, "tinyUrl");
            return (Criteria) this;
        }

        public Criteria andTinyUrlLessThanOrEqualTo(String value) {
            addCriterion("tiny_url <=", value, "tinyUrl");
            return (Criteria) this;
        }

        public Criteria andTinyUrlLike(String value) {
            addCriterion("tiny_url like", value, "tinyUrl");
            return (Criteria) this;
        }

        public Criteria andTinyUrlNotLike(String value) {
            addCriterion("tiny_url not like", value, "tinyUrl");
            return (Criteria) this;
        }

        public Criteria andTinyUrlIn(List<String> values) {
            addCriterion("tiny_url in", values, "tinyUrl");
            return (Criteria) this;
        }

        public Criteria andTinyUrlNotIn(List<String> values) {
            addCriterion("tiny_url not in", values, "tinyUrl");
            return (Criteria) this;
        }

        public Criteria andTinyUrlBetween(String value1, String value2) {
            addCriterion("tiny_url between", value1, value2, "tinyUrl");
            return (Criteria) this;
        }

        public Criteria andTinyUrlNotBetween(String value1, String value2) {
            addCriterion("tiny_url not between", value1, value2, "tinyUrl");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNull() {
            addCriterion("user_id is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("user_id is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(String value) {
            addCriterion("user_id =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(String value) {
            addCriterion("user_id <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(String value) {
            addCriterion("user_id >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(String value) {
            addCriterion("user_id >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(String value) {
            addCriterion("user_id <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(String value) {
            addCriterion("user_id <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLike(String value) {
            addCriterion("user_id like", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotLike(String value) {
            addCriterion("user_id not like", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<String> values) {
            addCriterion("user_id in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<String> values) {
            addCriterion("user_id not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(String value1, String value2) {
            addCriterion("user_id between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(String value1, String value2) {
            addCriterion("user_id not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUrlTypeIsNull() {
            addCriterion("url_type is null");
            return (Criteria) this;
        }

        public Criteria andUrlTypeIsNotNull() {
            addCriterion("url_type is not null");
            return (Criteria) this;
        }

        public Criteria andUrlTypeEqualTo(Integer value) {
            addCriterion("url_type =", value, "urlType");
            return (Criteria) this;
        }

        public Criteria andUrlTypeNotEqualTo(Integer value) {
            addCriterion("url_type <>", value, "urlType");
            return (Criteria) this;
        }

        public Criteria andUrlTypeGreaterThan(Integer value) {
            addCriterion("url_type >", value, "urlType");
            return (Criteria) this;
        }

        public Criteria andUrlTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("url_type >=", value, "urlType");
            return (Criteria) this;
        }

        public Criteria andUrlTypeLessThan(Integer value) {
            addCriterion("url_type <", value, "urlType");
            return (Criteria) this;
        }

        public Criteria andUrlTypeLessThanOrEqualTo(Integer value) {
            addCriterion("url_type <=", value, "urlType");
            return (Criteria) this;
        }

        public Criteria andUrlTypeIn(List<Integer> values) {
            addCriterion("url_type in", values, "urlType");
            return (Criteria) this;
        }

        public Criteria andUrlTypeNotIn(List<Integer> values) {
            addCriterion("url_type not in", values, "urlType");
            return (Criteria) this;
        }

        public Criteria andUrlTypeBetween(Integer value1, Integer value2) {
            addCriterion("url_type between", value1, value2, "urlType");
            return (Criteria) this;
        }

        public Criteria andUrlTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("url_type not between", value1, value2, "urlType");
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

        public Criteria andCreateTimeEqualTo(LocalDateTime value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(LocalDateTime value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(LocalDateTime value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(LocalDateTime value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<LocalDateTime> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<LocalDateTime> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(LocalDateTime value1, LocalDateTime value2) {
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

        public Criteria andUpdateTimeEqualTo(LocalDateTime value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(LocalDateTime value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(LocalDateTime value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(LocalDateTime value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<LocalDateTime> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<LocalDateTime> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andExpireTimeIsNull() {
            addCriterion("expire_time is null");
            return (Criteria) this;
        }

        public Criteria andExpireTimeIsNotNull() {
            addCriterion("expire_time is not null");
            return (Criteria) this;
        }

        public Criteria andExpireTimeEqualTo(LocalDateTime value) {
            addCriterion("expire_time =", value, "expireTime");
            return (Criteria) this;
        }

        public Criteria andExpireTimeNotEqualTo(LocalDateTime value) {
            addCriterion("expire_time <>", value, "expireTime");
            return (Criteria) this;
        }

        public Criteria andExpireTimeGreaterThan(LocalDateTime value) {
            addCriterion("expire_time >", value, "expireTime");
            return (Criteria) this;
        }

        public Criteria andExpireTimeGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("expire_time >=", value, "expireTime");
            return (Criteria) this;
        }

        public Criteria andExpireTimeLessThan(LocalDateTime value) {
            addCriterion("expire_time <", value, "expireTime");
            return (Criteria) this;
        }

        public Criteria andExpireTimeLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("expire_time <=", value, "expireTime");
            return (Criteria) this;
        }

        public Criteria andExpireTimeIn(List<LocalDateTime> values) {
            addCriterion("expire_time in", values, "expireTime");
            return (Criteria) this;
        }

        public Criteria andExpireTimeNotIn(List<LocalDateTime> values) {
            addCriterion("expire_time not in", values, "expireTime");
            return (Criteria) this;
        }

        public Criteria andExpireTimeBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("expire_time between", value1, value2, "expireTime");
            return (Criteria) this;
        }

        public Criteria andExpireTimeNotBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("expire_time not between", value1, value2, "expireTime");
            return (Criteria) this;
        }
    }

    /**
     */
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
