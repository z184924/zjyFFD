<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${className}Mapper">
	
	
	<!-- 新增 -->
	<insert id="save" parameterType="PageData">
		insert into ${tableName}(
	<#list columnList as column>
		<#if column.isPK == "YES">
		<#elseif column?counter == columnListLength && primaryKeyColumn.dataType != 'String'>
            ${column.columnName}
		<#else>
            ${column.columnName},
		</#if>
	</#list>
    <#if primaryKeyColumn.dataType == 'String'>
			${primaryKeyColumn.columnName}
	</#if>
        ) values (
	<#list columnList as column>
		<#if column.isPK == "YES">
		<#elseif column?counter == columnListLength && primaryKeyColumn.dataType != 'String'>
			${r"#{"}${column.columnName}${r"}"}
		<#else>
			${r"#{"}${column.columnName}${r"}"},
		</#if>
	</#list>
    <#if primaryKeyColumn.dataType == 'String'>
			${r"#{"}${primaryKeyColumn.columnName}${r"}"}
	</#if>
        )
    </insert>


    <!-- 删除 -->
    <delete id="delete" parameterType="PageData">
		delete from ${tableName}
        where
			${primaryKeyColumn.columnName}= ${r"#{"}${primaryKeyColumn.columnName}${r"}"}
    </delete>


    <!-- 修改 -->
    <update id="edit" parameterType="PageData">
		update  ${tableName}
        set
	<#list columnList as column>
		<#if column.isEdit == "YES">
			${column.columnName} = ${r"#{"}${column.columnName}${r"}"},
		</#if>
	</#list>
			${primaryKeyColumn.columnName}= ${primaryKeyColumn.columnName}
        where
			${primaryKeyColumn.columnName}= ${r"#{"}${primaryKeyColumn.columnName}${r"}"}
    </update>


    <!-- 通过ID获取数据 -->
    <select id="findById" parameterType="PageData" resultType="PageData">
		select
	<#list columnList as column>
		<#if column.isPK == "NO">
			a.${column.columnName},
		</#if>
	</#list>
			a.${primaryKeyColumn.columnName}
        from
        	${tableName} a
        where
			a.${primaryKeyColumn.columnName}= ${r"#{"}${primaryKeyColumn.columnName}${r"}"}
    </select>

    <!-- 查询列表 -->
    <select id="listAll" parameterType="PageData" resultType="PageData">
		select
		<#list columnList as column>
			<#if column.isPK == "NO">
			a.${column.columnName},
			</#if>
		</#list>
			a.${primaryKeyColumn.columnName}
        from
			${tableName} a
        where
        	1=1
    </select>

    <!-- 批量删除 -->
    <delete id="deleteAll" parameterType="String">
        delete from ${tableName}
        where
			a.${primaryKeyColumn.columnName}
		in
        <foreach item="item" index="index" collection="array" open="(" separator="," close=")">
		${r"#{item}"}
        </foreach>
    </delete>

</mapper>