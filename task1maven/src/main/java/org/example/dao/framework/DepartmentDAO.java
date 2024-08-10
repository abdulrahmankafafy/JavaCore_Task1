package org.example.dao.framework;

import org.example.entity.Department;

import java.util.List;

public interface DepartmentDAO {

    List<Department> getAllDepartments();

    Department getDepartmentById(int departmentId);

    void addDepartment(Department department);

    void updateDepartment(Department department);

    void deleteDepartment(int departmentId);

}
