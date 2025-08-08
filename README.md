
# 🧑‍💼 Job Portal Application (Spring Boot)

A full-featured job portal backend application built using **Spring Boot**, allowing users to register as either **Recruiters** or **Job Seekers**. Recruiters can post, update, and delete jobs; Job Seekers can view, apply, and manage their applications.

---

## 🚀 Features

### 👥 User Management
- Role-based authentication (Recruiter / Job Seeker)
- Secure login and registration (Spring Security)

### 🧑‍💼 Recruiter Functionalities
- Add, update, delete, and view job postings
- View applications for their jobs

### 👨‍💻 Job Seeker Functionalities
- View all job postings
- Apply to a job with resume and cover letter
- Update or delete applications
- View all applied jobs

---

## 🛠️ Tech Stack

| Layer             | Technologies                         |
|------------------|--------------------------------------|
| Language          | Java (JDK 17+)                      |
| Framework         | Spring Boot, Spring Web, Spring Security |
| Persistence       | Spring Data JPA, Hibernate          |
| Database          | MySQL                               |
| Build Tool        | Maven                               |
| API Testing       | Postman                             |

---

## 🏗️ Project Structure

```
com.dsv.jobportal
│
├── config              # Security and application config
├── controller          # REST API Controllers
├── dto                 # DTOs (if used)
├── exception           # Custom exceptions
├── model               # JPA Entities (User, Job, Application)
├── repository          # JPA Repositories
├── service             # Business logic layer
└── JobPortalApplication.java
```

---

## 🔐 Authentication & Authorization

- Role-based access via Spring Security
- `UserPrincipal` for accessing logged-in user details
- JWT or Session-based login supported (depending on setup)

---

## 🎯 API Endpoints (Sample)

| Method | Endpoint                        | Description                       | Role         |
|--------|----------------------------------|-----------------------------------|--------------|
| POST   | `/register`                      | Register a new user               | Public       |
| POST   | `/login`                         | Login and receive credentials     | Public       |
| POST   | `/jobs`                          | Post a new job                    | Recruiter    |
| PUT    | `/jobs/{id}`                     | Update job posting                | Recruiter    |
| DELETE | `/jobs/{id}`                     | Delete job                        | Recruiter    |
| GET    | `/jobs`                          | View all jobs                     | All          |
| POST   | `/applications`                 | Apply to a job                    | Job Seeker   |
| PUT    | `/applications/{id}`           | Update application                | Job Seeker   |
| DELETE | `/applications/{id}`           | Delete application                | Job Seeker   |
| GET    | `/applications/my-applications` | View applied jobs                 | Job Seeker   |

---

## ⚙️ Setup Instructions

1. **Clone the Repository**
   ```bash
   git clone https://github.com/yourusername/job-portal.git
   cd job-portal
   ```

2. **Configure the Database**
   Update `application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/job_portal
   spring.datasource.username=your_mysql_username
   spring.datasource.password=your_mysql_password
   spring.jpa.hibernate.ddl-auto=update
   ```

3. **Run the Application**
   ```bash
   mvn spring-boot:run
   ```

4. **Test APIs**
   Use **Postman** or similar tool to hit endpoints.

---

## 🧪 Sample User Roles

| Role       | Email                 | Password  |
|------------|-----------------------|-----------|
| Recruiter  | recruiter@example.com | password  |
| Job Seeker | jobseeker@example.com | password  |

*(Create users via `/register` API first.)*

---

## ✍️ Author

**Dasari Santhi Vardhan**  
📧 dasarisanthivardhan872@gmail.com  
🔗 [LinkedIn](https://www.linkedin.com/in/dasari-santhi-vardhan-b25515290/)
