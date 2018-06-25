package com.isarantidis.webmon.service.resultStore;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.isarantidis.webmon.service.common.CheckResult;

@Repository
interface CheckResultRepository extends JpaRepository<CheckResult, Long> {

}
