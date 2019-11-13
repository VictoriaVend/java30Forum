package telran.java30.forum.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import telran.java30.forum.model.UserAccount;

public interface UserAccountRepository extends MongoRepository<UserAccount, String> {

}
