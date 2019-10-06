package app.model.dao;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;

import app.model.entities.User;

public interface UserDao extends PagingAndSortingRepository<User, Long> {

	boolean existsByUserName(String userName);

	Optional<User> findByUserName(String userName);

}
