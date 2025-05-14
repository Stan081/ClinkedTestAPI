package com.clinked.article_api.repositories;

import com.clinked.article_api.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
