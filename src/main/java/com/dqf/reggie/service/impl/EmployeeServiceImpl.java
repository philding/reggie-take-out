package com.dqf.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dqf.reggie.entity.Employee;
import com.dqf.reggie.mapper.EmployeeMapper;
import com.dqf.reggie.service.EmployeeService;
import org.springframework.stereotype.Service;


@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService{


}
