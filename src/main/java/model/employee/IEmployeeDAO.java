package model.employee;

import java.util.List;

import org.springframework.stereotype.Component;

import model.exceptions.EmployeeException;
import model.project.Project;

@Component
public interface IEmployeeDAO {

	int registerUser(Employee emp) throws EmployeeException;

	int loginUser(Employee emp) throws EmployeeException;

	int removeUser(Employee emp) throws EmployeeException;

	int getEmployeeID(Employee emp) throws EmployeeException;

	int getUserCount() throws EmployeeException;

	int validEmail(String email) throws EmployeeException;
	
	List<Project> giveMyProjects(Employee emp) throws EmployeeException;

}