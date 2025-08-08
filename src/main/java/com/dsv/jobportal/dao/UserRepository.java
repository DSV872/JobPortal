package com.dsv.jobportal.dao;

import com.dsv.jobportal.model.Role;
import com.dsv.jobportal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    User findByEmail(String email);

}
