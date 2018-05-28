# ConsoleUserManagement
Simple user management application implemented using Spring Boot, Spring Data and Spring Security. The database used is h2 embedded for ease of deployment and testing.
Field validation is done using the validation annotations with regular expressions.
Password is held as char[] instead of String inside the PasswordHolder class to prevent leaks due to memory dumps and accidential prints of passwords.
Passwords are also encoded using the BCryptPasswordEncoder provided by Spring Security.
Password generation is done using apache commons RandomStringUtils and a simple algorithm that assures the password fits the validation requirements.
