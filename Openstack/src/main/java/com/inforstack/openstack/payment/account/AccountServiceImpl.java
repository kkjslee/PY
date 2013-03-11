package com.inforstack.openstack.payment.account;

import java.math.BigDecimal;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.persistence.LockModeType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inforstack.openstack.exception.ApplicationRuntimeException;
import com.inforstack.openstack.instance.Instance;
import com.inforstack.openstack.instance.InstanceService;
import com.inforstack.openstack.log.Logger;
import com.inforstack.openstack.tenant.Tenant;
import com.inforstack.openstack.tenant.TenantService;
import com.inforstack.openstack.utils.Constants;
import com.inforstack.openstack.utils.DateUtil;
import com.inforstack.openstack.utils.NumberUtil;
import com.inforstack.openstack.utils.OpenstackUtil;
import com.inforstack.openstack.utils.StringUtil;

@Service("accountService")
@Transactional
public class AccountServiceImpl implements AccountService{
	
	private static final Logger log = new Logger(AccountServiceImpl.class);
	private static String cachedDate = null;
	private static int sequence = 0;
	
	@Autowired
	private AccountDao accountDao;
	@Autowired
	private InstanceService instanceService;
	@Autowired
	private TenantService tenantService;
	
	@PostConstruct
	public void postInit(){
		synchronized (AccountServiceImpl.class){
			if(cachedDate == null){
				Date date = new Date();
				Account account = accountDao.findLastestBySequenceDate("number", date);
				if(account != null){
					cachedDate = account.getNumber().substring(0, DateUtil.SEQ_DATE_LEN + 1);
					sequence = new Integer(account.getNumber().substring(DateUtil.SEQ_DATE_LEN + 1));
				}else{
					cachedDate = DateUtil.getSequenceDate(date);
					sequence = 0;
				}
			}
		}
	}
	
	private String genenrateAccountNumber(){
		synchronized (AccountServiceImpl.class) {
			int max = new Integer(StringUtil.leftPadding("", '9', DateUtil.SEQ_DATE_LEN));
			if(sequence == max){
				throw new ApplicationRuntimeException("Max account limit reached today");
			}
			
			String date = DateUtil.getSequenceDate(new Date());
			if(!date.equals(cachedDate)){
				cachedDate = date;
				sequence = 0;
			}
			sequence++;
			return date + NumberUtil.leftPaddingZero(sequence, 8);
		}
	}

	@Override
	public Account createAccount(int tenantId, Integer instanceId){
		Account account = new Account();
		account.setAmount(BigDecimal.ZERO);
		account.setBalance(BigDecimal.ZERO);
		if(instanceId != null){
			Instance instance =  instanceService.findInstanceById(instanceId);
			account.setInstance(instance);
		}
		account.setTenant(tenantService.findTenantById(tenantId));
		account.setUseOnly(BigDecimal.ZERO);
		account.setNumber(this.genenrateAccountNumber());
		account.setPoint(0);
		account.setStatus(Constants.ACCOUNT_STATUS_ACTIVE);
		account.setCreateTime(new Date());
		accountDao.persist(account);
		
		return account;
	}
	
	@Override
	public Account changeAccount(Account account, BigDecimal amount, boolean useOnly){
		if(amount.compareTo(BigDecimal.ZERO) >= 0){
			account.setAmount(account.getAmount().add(amount));
		}else{
			account.setPoint(amount.negate().intValue());
		}
		
		account.setBalance(account.getBalance().add(amount));
		if(useOnly){
			account.setUseOnly(account.getUseOnly().add(amount));
			if(account.getUseOnly().compareTo(BigDecimal.ZERO) < 0){
				account.setUseOnly(BigDecimal.ZERO);
			}
		}
		
		if(account.getBalance().compareTo(BigDecimal.ZERO) < 0){
			throw new ApplicationRuntimeException("payment is bigger than balance");
		}
		
		return account;
	}
	
	@Override
	public Account findAccountById(int accountId){
		return accountDao.findById(accountId);
	}
	
	@Override
	public Account findActiveAccount(Tenant tenant, Instance instance){
		return accountDao.findActiveAccount(
				tenant==null? null:tenant.getId(), 
				instance==null? null:instance.getId());
	}
	
	@Override
	public Account findActiveAccount(Integer tenantId, Integer instanceId){
		return accountDao.findActiveAccount(tenantId, instanceId);
	}

	@Override
	public Account findOrCreateActiveAccount(Tenant tenant, Instance instance) {
		Integer tenantId = tenant==null? null:tenant.getId();
		Integer instanceId = instance==null? null:instance.getId();
		
		Account account = this.findActiveAccount(tenantId, instanceId);
		
		if(account == null){
			account = this.createAccount(tenantId, instanceId);
		}
		
		return account;
	}
}
