package com.inforstack.openstack.billing.invoice;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.inforstack.openstack.basic.BasicDaoImpl;
import com.inforstack.openstack.log.Logger;
import com.inforstack.openstack.utils.CollectionUtil;

@Repository
public class InvoiceDaoImpl extends BasicDaoImpl<Invoice> implements InvoiceDao {

	private static final Logger log = new Logger(InvoiceDaoImpl.class);
	
	@Override
	public List<Invoice> findByTime(Date from, Date to) {
		log.debug("Find invoices from : " + from + ", to : " + to);
		try {
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<Invoice> criteria = builder
					.createQuery(Invoice.class);
			Root<Invoice> root = criteria.from(Invoice.class);
			List<Predicate> predicates = new ArrayList<Predicate>();
			if(from != null){
				predicates.add(
						builder.greaterThanOrEqualTo(root.<Date>get("createTime"), from)
				);
			}
			if(to != null){
				predicates.add(
						builder.lessThanOrEqualTo(root.<Date>get("createTime"), to)
				);
			}
			if(predicates.isEmpty()){
				criteria.select(root);
			}else{
				criteria.select(root).where(
					builder.and(predicates.toArray(new Predicate[predicates.size()]))
				);
			}
			
			List<Invoice> invoices = em.createQuery(criteria).getResultList();
			if(CollectionUtil.isNullOrEmpty(invoices)){
				log.debug("No record found");
				return invoices;
			}
			
			log.debug("Find successfully");
			return invoices;
		} catch (RuntimeException re) {
			log.error(re.getMessage(), re);
			throw re;
		}
	}
}
