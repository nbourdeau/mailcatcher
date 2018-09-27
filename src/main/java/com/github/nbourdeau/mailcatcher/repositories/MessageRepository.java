package com.github.nbourdeau.mailcatcher.repositories;

import com.github.nbourdeau.mailcatcher.model.Message;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MessageRepository extends PagingAndSortingRepository<Message, Long> {

}
