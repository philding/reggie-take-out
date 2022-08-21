package com.dqf.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dqf.reggie.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author phil
 */
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {

}
