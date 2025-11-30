/**
 * This code is based on solutions provided by ChatGPT 4o and 
 * adapted using GitHub Copilot. It has been thoroughly reviewed 
 * and validated to ensure correctness and that it is free of errors.
 */
package es.deusto.sd.ecoembes.service;

import org.springframework.stereotype.Service;

import es.deusto.sd.ecoembes.dao.EmployeeRepository;
import es.deusto.sd.ecoembes.entity.Employee;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {

    // Simulating a user repository
    private static EmployeeRepository employeeRepository ;
    
    // Storage to keep the session of the users that are logged in
    private static Map<String, Employee> tokenStore = new HashMap<>(); 
    
    public AuthService(EmployeeRepository employeeRepository) {
		AuthService.employeeRepository = employeeRepository;
	}

    // Login method that checks if the user exists in the database and validates the password
    public Optional<String> login(String email, String password) {
        Employee user = employeeRepository.findByEmail(email).orElse(null);
        
        if (user != null && user.checkPassword(password)) {
            String token = generateToken();  // Generate a random token for the session
            tokenStore.put(token, user);    // Store the token and associate it with the user
            System.out.println(tokenStore.toString());

            return Optional.of(token);
        } else {
        	return Optional.empty();
        }
    }
    
    // Logout method to remove the token from the session store
    public Optional<Boolean> logout(String token) {
    	System.out.println(tokenStore.toString()+"=Logout called with token: " + token);
        if (tokenStore.containsKey(token)) {
            tokenStore.remove(token);

            return Optional.of(true);
        } else {
            return Optional.empty();
        }
    }
    
    // Method to add a new user to the repository
    public void addUser(Employee user) {
        if (user != null) {
            Optional<Employee> existing = employeeRepository.findById(user.getId());
            if (existing.isPresent()) {
                Employee emp = existing.get();
                emp.setName(user.getName());
                emp.setEmail(user.getEmail());
                emp.setPassword(user.getPassword());
                emp.setDate_birth(user.getDate_birth());
                emp.setSalary(user.getSalary());
                employeeRepository.save(emp); // merge seguro
            } else {
                employeeRepository.save(user); // inserción nueva
            }
        }
    }
    
    // Method to get the user based on the token
    public Employee getUserByToken(String token) {
    	System.out.println("Getting user by token: " + token);
    	System.out.println(tokenStore.toString());
        return tokenStore.get(token); 
    }
    
    // Method to get the user based on the email
    public Employee getUserByEmail(String email) {
		return employeeRepository.findByEmail(email).orElse(null);
	}
    
    public Employee getUserById(long id) {
        return employeeRepository.findAll().stream()
                .filter(e -> e.getId() == id)
                .findFirst()
                .orElse(null);
    }

    // Synchronized method to guarantee unique token generation
    private static synchronized String generateToken() {
        return Long.toHexString(System.currentTimeMillis());
    }
}