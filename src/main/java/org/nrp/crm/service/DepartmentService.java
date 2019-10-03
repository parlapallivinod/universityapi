package org.nrp.crm.service;

import org.nrp.crm.controller.DepartmentController;
import org.nrp.crm.exception.RecordNotFoundException;
import org.nrp.crm.entity.Department;
import org.nrp.crm.repository.DepartmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DepartmentService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentController.class);
    private static final RecordNotFoundException DEPARTMENT_NOT_FOUND_EXCEPTION = new RecordNotFoundException("Department not found");

    private DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Secured({"ROLE_ADMIN"})
    public Department addDepartment(Department department) {
        departmentRepository.save(department);
        LOGGER.info("Department added " + department);
        return department;
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public List<Department> getDepartments() {
        LOGGER.info("Getting All Departments");
        return departmentRepository.findAll();
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public Department getDepartmentById(Long departmentId) {
        Optional<Department> department = departmentRepository.findById(departmentId);
        if (!department.isPresent()) {
            LOGGER.info("Department not found: " + departmentId);
            throw DEPARTMENT_NOT_FOUND_EXCEPTION;
        }
        LOGGER.info("Department found: " + department.get());
        return department.get();
    }

    @Secured({"ROLE_ADMIN"})
    public Department updateDepartment(Long departmentId, Department department) {

        Optional<Department> object = departmentRepository.findById(departmentId);
        if (!object.isPresent()) {
            LOGGER.info("Department not found: " + departmentId);
            throw DEPARTMENT_NOT_FOUND_EXCEPTION;
        };

        department.setId(departmentId);
        departmentRepository.save(department);
        LOGGER.info("Deparment updated: " + department);
        return department;
    }

    @Secured({"ROLE_ADMIN"})
    public Department deleteDepartmentById(Long departmentId) {
        Optional<Department> department = departmentRepository.findById(departmentId);
        if (!department.isPresent()) {
            LOGGER.info("Department not found: " + departmentId);
            throw DEPARTMENT_NOT_FOUND_EXCEPTION;
        }

        departmentRepository.delete(department.get());
        LOGGER.info("Deparment deleted: " + department.get());
        return department.get();
    }

}
