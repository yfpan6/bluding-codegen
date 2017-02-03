<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${mapperPackage}.${modelConfiguration.entityClassENName}Mapper">

    <resultMap type="${entityPackage}.${modelConfiguration.entityClassENName}" id="baseResultMap">
    <#list modelConfiguration.fields as field>
        <result property="${field.fieldName}" column="${modelConfiguration.alias}_${field.columnName}" jdbcType="${field.jdbcType}"/>
    </#list>
    </resultMap>

    <sql id="columnList" >
    <#list modelConfiguration.fields as field>
        ${modelConfiguration.alias}.${field.columnName} AS ${modelConfiguration.alias}_${field.columnName}<#if field_has_next>,</#if>
    </#list>
    </sql>

    <sql id="selectSql">
        SELECT
        <choose>
            <when test="columns != null and columns.size() > 0">
                <trim prefix="" suffixOverrides=",">
                    <foreach collection="columns" index="index" item="field" separator=",">
                        ${modelConfiguration.alias}.${"$"}{field} AS ${modelConfiguration.alias}_${"$"}{field}
                    </foreach>
                </trim>
            </when>
            <otherwise>
                <include refid="columnList"/>
            </otherwise>
        </choose>
        FROM ${modelConfiguration.tableName} ${modelConfiguration.alias}
    </sql>

    <sql id="orderSql">
        <if test="orders != null">
            order by
            <trim prefix="" suffixOverrides=",">
                <foreach collection="orders" index="index" item="order" separator=",">
                    ${modelConfiguration.alias}.${"$"}{order.field} ${"$"}{order.sort}
                </foreach>
            </trim>
        </if>
    </sql>

    <sql id="conditionSqlForUD">
        <if test="condition != null">
            <trim prefix="AND" prefixOverrides="AND">
            <#list modelConfiguration.fields as field>
                <if test="condition.${field.fieldName} != null">
                    AND ${field.columnName} = ${"#"}{condition.${"$"}{field.fieldName}
                </if>
            </#list>
            </trim>
        </if>
    </sql>

    <sql id="conditionSqlForWhere">
        <if test="condition != null">
            <trim prefix="AND" prefixOverrides="AND">
                <#list modelConfiguration.fields as field>
                <if test="condition.${field.fieldName} != null">
                    AND ${modelConfiguration.alias}.${field.columnName} = ${"#"}{condition.${field.fieldName}}
                </if>
                </#list>
            </trim>
        </if>
    </sql>

    <sql id="batchUpdateByIdsSql">
        UPDATE ${modelConfiguration.tableName}
        <trim prefix="SET" suffixOverrides=",">
        <#list modelConfiguration.fields as field>
            <#if field.columnName!="auto_inc_id">
            <if test="entity.${field.fieldName} != null">
            ${field.columnName} = ${"#"}{entity.${field.fieldName}},
            </if>
            </#if>
        </#list>
        </trim>
    </sql>

    <sql id="updateSql">
        UPDATE ${modelConfiguration.tableName}
        <trim prefix="SET" suffixOverrides=",">
        <#list modelConfiguration.fields as field>
            <#if field.columnName!="auto_inc_id">
            <if test="${field.fieldName} != null">
            ${field.columnName} = ${"#"}{${field.fieldName}},
            </if>
            </#if>
        </#list>
        </trim>
    </sql>

    <sql id="deleteSql">
        DELETE FROM ${modelConfiguration.tableName}
    </sql>

    <sql id="insertColumns">
        <trim prefix="" suffixOverrides=",">
        <#list modelConfiguration.fields as field>
            <#if field.columnName!="auto_inc_id">
            ${field.columnName},
            </#if>
        </#list>
        </trim>
    </sql>

    <sql id="insertValues">
        <trim prefix="" suffixOverrides=",">
        <#list modelConfiguration.fields as field>
            <#if field.columnName!="auto_inc_id">
            ${"#"}{${field.fieldName}},
            </#if>
        </#list>
        </trim>
    </sql>

    <sql id="batchInsertValues">
        <foreach collection="list" item="item" index="index" separator=",">
            (
            <trim prefix="" suffixOverrides=",">
            <#list modelConfiguration.fields as field>
                <#if field.columnName!="auto_inc_id">
                ${"#"}{${field.fieldName}},
                </#if>
            </#list>
            </trim>
            )
        </foreach>
    </sql>

    <insert id="insert" parameterType="${modelConfiguration.entityClassENName}">
        INSERT INTO ${modelConfiguration.tableName}(
            <include refid="insertColumns"/>
        ) VALUES (
            <include refid="insertValues"/>
        )
    </insert>

    <insert id="insertBatch" parameterType="CommonBatchParam">
        INSERT INTO ${modelConfiguration.tableName}(
            <include refid="insertColumns"/>
        )VALUES
            <include refid="batchInsertValues"/>
    </insert>

    <delete id="delete">
        <include refid="deleteSql"/>
        <where>
            <include refid="conditionSqlForUD"/>
        </where>
    </delete>

    <delete id="deleteByIds" parameterType="BatchDeleteByIdsParam">
        <include refid="deleteSql"/>
        <where>
            ${modelConfiguration.keyColumnName} IN
            <foreach collection="ids" open="(" close=")" item="item" separator=",">
                ${"$"}{item}
            </foreach>
            <include refid="conditionSqlForUD"/>
        </where>
    </delete>

    <update id="updateById" parameterType="${modelConfiguration.entityClassENName}">
        <include refid="updateSql"/>
        <where>
            ${modelConfiguration.keyColumnName}=${"#"}{${modelConfiguration.camelKeyColName}}
        </where>
    </update>

    <update id="updateByIds" parameterType="BatchUpdateByIdsParam">
        <include refid="batchUpdateByIdsSql"/>
        <where>
            ${modelConfiguration.keyColumnName} IN
            <foreach collection="ids" open="(" close=")" item="item" index="index" separator=",">
                ${"#"}{item}
            </foreach>
            <include refid="conditionSqlForUD"/>
        </where>
    </update>

    <select id="selectById" parameterType="IdQueryParam" resultMap="baseResultMap">
        <include refid="selectSql"/>
        <where>
            ${modelConfiguration.alias}.${modelConfiguration.keyColumnName} = ${"#"}{${modelConfiguration.camelKeyColName}}
            <include refid="conditionSqlForWhere"/>
        </where>
    </select>

    <select id="selectSingle" parameterType="QueryParam" resultMap="baseResultMap">
        <include refid="selectSql"/>
        <if test="condition != null">
            <where>
                <include refid="conditionSqlForWhere"/>
            </where>
        </if>
        LIMIT 1
    </select>

    <select id="selectAsList" parameterType="QueryParam" resultMap="baseResultMap">
        <include refid="selectSql"/>
        <if test="condition != null">
            <where>
                <include refid="conditionSqlForWhere"/>
            </where>
        </if>
        <include refid="orderSql"/>
        <if test="start != null">
            LIMIT ${"#"}{start}, ${"#"}{limit}
        </if>
    </select>

    <select id="selectAsListByIds" parameterType="IdsQueryParam" resultMap="baseResultMap">
        <include refid="selectSql"/>
        <where>
            ${modelConfiguration.alias}.${modelConfiguration.keyColumnName} IN
            <foreach collection="ids" open="(" close=")" item="item" index="index" separator=",">
                ${"#"}{item}
            </foreach>
            <include refid="conditionSqlForWhere"/>
        </where>
        <include refid="orderSql"/>
    </select>

    <select id="count" parameterType="QueryParam" resultType="Integer">
        SELECT
            COUNT(*) AS c
        FROM
            ${modelConfiguration.tableName} ${modelConfiguration.alias}
        <where>
            <include refid="conditionSqlForWhere"/>
        </where>
    </select>

    <select id="countUnique" parameterType="QueryParam" resultType="Integer">
        SELECT
            COUNT(*) AS c
        FROM
            ${modelConfiguration.tableName} ${modelConfiguration.alias}
        <where>
            <include refid="conditionSqlForWhere"/>
        </where>
    </select>

</mapper>