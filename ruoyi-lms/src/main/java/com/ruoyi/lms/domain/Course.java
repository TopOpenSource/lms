package com.ruoyi.lms.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("lms_course")
public class Course extends BaseEntity {
    /**
     * 主键 ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @TableId
    private Long id;

    @TableField("grade")
    private String grade;

    /**
     * 班级
     */
    @TableField("clazz")
    private String clazz;

    /**
     * 课程
     */
    @TableField("course")
    private String course;

    /**
     * 教师
     */
    @TableField("teacher")
    private String teacher;

    /**
     * 学期
     */
    @TableField("term")
    private String term;

    /**
     * 周几
     */
    @TableField("week_day")
    private Integer weekDay;

    /**
     * 节次
     */
    @TableField("class_period")
    private Integer classPeriod;
}
