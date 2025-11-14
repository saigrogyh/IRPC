package interviewproject.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class service {
    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee getEmployee(int id){
        return  employeeRepository.findById(id).orElse(null);
    }
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }
    public Employee updateEnployee(Integer id, Employee employee){
        if(!employeeRepository.existsById(id)){
            throw new IllegalArgumentException("Employee with ID " + id + " does not exist.");
        }
        return employeeRepository.save(employee);
    }
    public void deleteEmployee(Integer id){
        if(!employeeRepository.existsById(id)){
            throw new IllegalArgumentException("Employee with ID " + id + " does not exist.");
        }
        employeeRepository.deleteById(id);
    }
}
