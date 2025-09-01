package com.ruoyi.lms.convert;

import com.ruoyi.lms.domain.Course;
import com.ruoyi.lms.dto.CourseDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CourseConvert {
    CourseConvert INSTANCE = Mappers.getMapper(CourseConvert.class);


     CourseDto convert2Dto(Course bean);

     List<CourseDto> convert2ListDto(List<Course> bean);

    Course convert2Entity(CourseDto bean);

     List<Course> convert2Entity(List<CourseDto> bean);
}
