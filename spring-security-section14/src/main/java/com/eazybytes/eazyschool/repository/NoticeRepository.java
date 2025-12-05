package com.eazybytes.eazyschool.repository;

import java.util.List;

import com.eazybytes.eazyschool.model.Notice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeRepository extends CrudRepository<Notice, Long> {

	@Query("from Notice n where current_date between n.noticBegDt and n.noticEndDt")
	List<Notice> findAllActiveNotices();

}