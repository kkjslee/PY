package com.inforstack.openstack.mail.template;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.inforstack.openstack.basic.BasicDaoImpl;
import com.inforstack.openstack.utils.CollectionUtil;

@Repository
public class MailTemplateDaoImpl extends BasicDaoImpl<MailTemplate> implements MailTemplateDao {

	@Override
	public List<MailTemplate> findTemplateByMailIdAndLanguage(Integer mailId,
			Integer languageId) {
		log.debug("Find all mail tempaltes by mail id : " + mailId + ", language : " + languageId);
		try {
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<MailTemplate> criteria = builder
					.createQuery(MailTemplate.class);
			Root<MailTemplate> root = criteria.from(MailTemplate.class);
			List<Predicate> predicates = new ArrayList<Predicate>();
			if(mailId != null){
				predicates.add(
						builder.equal(root.get("mail").get("id"), mailId)
				);
			}
			if(languageId != null){
				predicates.add(
						builder.equal(root.get("language"), languageId)
				);
			}
			
			if(predicates.size() > 0){
				criteria.where(
						builder.and(predicates.toArray(new Predicate[predicates.size()]))
				);
			}
			List<MailTemplate> instances = em.createQuery(criteria).getResultList();
			if(CollectionUtil.isNullOrEmpty(instances)){
				log.debug("No record found");
				return instances;
			}
			log.debug("get successful");
			return instances;
		} catch (RuntimeException re) {
			log.error(re.getMessage(), re);
			throw re;
		}
	}

}
