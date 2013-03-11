package com.inforstack.openstack.mail.task.code;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.inforstack.openstack.basic.BasicDaoImpl;
import com.inforstack.openstack.mail.template.MailTemplate;
import com.inforstack.openstack.utils.CollectionUtil;
import com.inforstack.openstack.utils.StringUtil;

@Repository
public class TaskCodeDaoImpl extends BasicDaoImpl<TaskCode> implements TaskCodeDao {

	@Override
	public TaskCode findByCodeAndRandom(String code, String random) {
		log.debug("Find task code by code : " + code + ", raddom : " + StringUtil.convertToLogString(random));
		try {
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<TaskCode> criteria = builder
					.createQuery(TaskCode.class);
			Root<TaskCode> root = criteria.from(TaskCode.class);
			List<Predicate> predicates = new ArrayList<Predicate>();
			if(StringUtil.isNullOrEmpty(code) == false){
				predicates.add(
						builder.equal(root.get("code"), code)
				);
			}
			if(StringUtil.isNullOrEmpty(random) == false){
				predicates.add(
						builder.equal(root.get("random"), random)
				);
			}
			
			if(predicates.size() > 0){
				criteria.where(
						builder.and(predicates.toArray(new Predicate[predicates.size()]))
				);
			}
			List<TaskCode> instances = em.createQuery(criteria).getResultList();
			if(CollectionUtil.isNullOrEmpty(instances)){
				log.debug("No record found");
				return null;
			}
			log.debug("get successful");
			return instances.get(0);
		} catch (RuntimeException re) {
			log.error(re.getMessage(), re);
			throw re;
		}
	}

}
