package com.itstyle.quartz.mapper;
import com.itstyle.quartz.entity.QuartzEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author xuzhen
* @description 针对表【qrtz_job_details】的数据库操作Mapper
* @createDate 2023-05-05 13:48:17
* @Entity generator.domain.QrtzJobDetails
*/
@Mapper
public interface QrtzJobDetailsMapper {


    List<QuartzEntity> queryListModel(@Param("jobName") String jobName);

    Long queryCount();

}
