package com.ruoyi.lms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.lms.domain.Course;
import com.ruoyi.lms.dto.CourseDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
public interface CourseMapper extends BaseMapper<Course> {

    @Select("select tt.*, t2.course_sum as courseSumStandard from  " +
            "(select t1.clazz,t1.course,count(*) as courseSum from lms_course t1 WHERE t1.grade=#{grade}  GROUP BY t1.clazz,t1.course) tt " +
            "left join lms_grade_course t2 on t2.grade=#{grade} and tt.course=t2.course")
    public List<CourseDto> selectCourseList(@Param("grade") String grade);
}
