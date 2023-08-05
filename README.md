# Online Learning System

The Online Learning System is an educational platform that offers courses and resources for learners to study from a distance. It provides flexibility in terms of time and location, allowing users to manage their own learning and choose courses that suit their learning goals. The system includes various features to cater to different types of users, such as guests, registered customers, marketing members, and administrators.

## Project Scope

### Major Features

1. Common Feature:
   - Shared features accessible to all users, including login, search, and account settings.

2. Public Feature:
   - Features available to guests, allowing them to browse the course catalog and register for an account.

3. Customer Feature:
   - Features available to registered customers, enabling them to view course materials and take quizzes.

4. Marketing Feature:
   - Features for marketing members to manage promotions and analyze customer data.

5. Course Content:
   - Features to manage course content, including creating and editing subjects, price packages, and lessons.

6. Testing Content:
   - Features to create and manage quizzes, including importing questions and generating quiz reports.

7. Sales Feature:
   - Features for managing registrations and tracking sales, including adding and editing registration details.

8. Admin Feature:
   - Features for system administrators to manage users, system settings, and site content.

### Limitations & Exclusions

- The system does not support live teaching or real-time communication between learners and instructors.
- Social learning and collaboration features among learners are not included.
- Offline courses or learning events management is not supported.
- Integration with external learning management systems or e-learning standards is not provided.
- Gamification or rewards for learning achievements are not part of the system.
- Mobile devices or native mobile applications are not supported.

## Software Design Document

### Overall Description

#### Assumptions

This system is designed based on the following assumptions:
- The end-user's environment is Windows 11.
- Spring Boot is used for the backend development.
- MySQL is used as the database management system.
- Google Chrome is recommended for accessing the application.

#### Design Constraints

- The system is designed with four websites at the client-side to serve different user roles (guest, student, staff, and admin).
- The end-user's environment must be Windows.
- The system utilizes MySQL as the database.
- The application supports the English language.
- Users must have a stable internet connection.

#### Technology Suggestion

- Database: MySQL
- Web Application: Tomcat 8.5.3
- Server: Local server

## Installation and Deployment

1. Clone the repository to your local machine.
2. Set up the required environment, including Windows 11, Spring Boot, MySQL, and Google Chrome.
3. Import the project into your preferred IDE.
4. Set up the database with MySQL, and configure the database connection in the application.
5. Build and run the application on Tomcat 8.5.3 server.

## Usage

1. Access the application through the web browser using the specified URLs for different user roles.
2. Register as a customer to access course materials and take quizzes.
3. Admins can log in to manage users, system settings, and site content.

## Contribution

Contributions to the project are welcome. If you find any issues or want to suggest improvements, please submit a pull request.

## License

This project is licensed under the [MIT License](LICENSE). Feel free to use, modify, and distribute the code. However, please attribute the original authors appropriately.
