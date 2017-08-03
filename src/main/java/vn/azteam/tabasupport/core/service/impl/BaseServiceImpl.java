package vn.azteam.tabasupport.core.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import vn.azteam.tabasupport.provider.ApplicationContextProvider;

/**
 * package vn.azteam.tabasupport.core.service.impl
 * created 12/26/2016.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @since 1.0.0
 */
public abstract class BaseServiceImpl {
	protected final Logger logger = LogManager.getLogger(getClass());
	protected DataSourceTransactionManager transactionManager;
	protected TransactionStatus status;

	protected void createTransaction() {
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		transactionManager = ApplicationContextProvider.getApplicationContext().getBean(DataSourceTransactionManager.class);
		status = transactionManager.getTransaction(def);
	}
}
