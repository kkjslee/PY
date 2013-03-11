package com.inforstack.openstack.billing.invoice;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.inforstack.openstack.basic.BasicDaoImpl;
import com.inforstack.openstack.controller.model.PaginationModel;
import com.inforstack.openstack.log.Logger;
import com.inforstack.openstack.utils.CollectionUtil;

@Repository
public class InvoiceDaoImpl extends BasicDaoImpl<Invoice> implements InvoiceDao {

	private static final Logger log = new Logger(InvoiceDaoImpl.class);
	
	@Override
	public PaginationModel<Invoice> findByTime(int pageIndex, int pageSize,
			Integer tenantId, Date from, Date to) {
		log.debug("Find invoices from : " + from + ", to : " + to);
		try {
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<Invoice> criteria = builder
					.createQuery(Invoice.class);
			Root<Invoice> root = criteria.from(Invoice.class);
			List<Predicate> predicates = new ArrayList<Predicate>();
			if(tenantId != null){
				predicates.add(
						builder.equal(root.get("tenant").get("id"), tenantId)
				);
			}
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
			if(!predicates.isEmpty()){
				criteria.where(
					builder.and(predicates.toArray(new Predicate[predicates.size()]))
				);
			}
			
			return this.pagination(pageIndex, pageSize, criteria);
		} catch (RuntimeException re) {
			log.error(re.getMessage(), re);
			throw re;
		}
	}

	@Override
	public List<Invoice> findInvoices(Integer status, Integer orderId) {
		log.debug("Find invoices by order id : " + orderId + ", status : " + status);
		try {
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<Invoice> criteria = builder
					.createQuery(Invoice.class);
			Root<Invoice> root = criteria.from(Invoice.class);
			List<Predicate> predicates = new ArrayList<Predicate>();
			if(status != null){
				predicates.add(
						builder.equal(root.get("status"), status)
				);
			}
			if(orderId != null){
				predicates.add(
						builder.equal(root.get("order").get("id"), orderId)
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
				return new ArrayList<Invoice>();
			}
			
			log.debug("Find successfully");
			return invoices;
		} catch (RuntimeException re) {
			log.error(re.getMessage(), re);
			throw re;
		}
	}
}
