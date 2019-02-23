package com.chau.abear.domain;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

public interface UserRepository extends Repository<User, Long> {

    Page<User> findAll(Pageable pageable);

    User findByNickNameAndEmailAllIgnoringCase(String nickName, String email);
}
