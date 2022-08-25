package com.dqf.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dqf.reggie.common.R;
import com.dqf.reggie.entity.Employee;
import com.dqf.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;

/**
 * @author phil
 */
@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * 登录页
     * @param request
     * @param employee
     * @return
     */
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {
        /**
         * 将页面提交密码进行加密处理
         */
        String password =employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        /**
         * 根据页面提交的用户名进行查询
         */
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, employee.getUsername());
        Employee emp = employeeService.getOne(queryWrapper);

        if (emp == null) {
            return R.error("登录失败");
        }

        /**
         * 密码比对
         */
        if (!emp.getPassword().equals(password)) {
            return R.error("密码错误");

        }

        if (emp.getStatus() == 0) {
            return R.error("账号已经禁用");
        }

        /**
         * 登陆成功,将员工ID写入session并且返回
         */
        request.getSession().setAttribute("employee", emp.getId());
        return R.success(emp);
    }

    /**
     * 退出
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest req) {
        req.getSession().removeAttribute("employee");
        return R.success("退出成功");
    }

}
