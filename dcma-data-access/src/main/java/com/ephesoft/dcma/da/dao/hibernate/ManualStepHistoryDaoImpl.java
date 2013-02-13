/********************************************************************************* 
* Ephesoft is a Intelligent Document Capture and Mailroom Automation program 
* developed by Ephesoft, Inc. Copyright (C) 2010-2012 Ephesoft Inc. 
* 
* This program is free software; you can redistribute it and/or modify it under 
* the terms of the GNU Affero General Public License version 3 as published by the 
* Free Software Foundation with the addition of the following permission added 
* to Section 15 as permitted in Section 7(a): FOR ANY PART OF THE COVERED WORK 
* IN WHICH THE COPYRIGHT IS OWNED BY EPHESOFT, EPHESOFT DISCLAIMS THE WARRANTY 
* OF NON INFRINGEMENT OF THIRD PARTY RIGHTS. 
* 
* This program is distributed in the hope that it will be useful, but WITHOUT 
* ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS 
* FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more 
* details. 
* 
* You should have received a copy of the GNU Affero General Public License along with 
* this program; if not, see http://www.gnu.org/licenses or write to the Free 
* Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 
* 02110-1301 USA. 
* 
* You can contact Ephesoft, Inc. headquarters at 111 Academy Way, 
* Irvine, CA 92617, USA. or at email address info@ephesoft.com. 
* 
* The interactive user interfaces in modified source and object code versions 
* of this program must display Appropriate Legal Notices, as required under 
* Section 5 of the GNU Affero General Public License version 3. 
* 
* In accordance with Section 7(b) of the GNU Affero General Public License version 3, 
* these Appropriate Legal Notices must retain the display of the "Ephesoft" logo. 
* If the display of the logo is not reasonably feasible for 
* technical reasons, the Appropriate Legal Notices must display the words 
* "Powered by Ephesoft". 
********************************************************************************/ 

package com.ephesoft.dcma.da.dao.hibernate;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.ephesoft.dcma.core.dao.hibernate.HibernateDao;
import com.ephesoft.dcma.da.dao.ManualStepHistoryDao;
import com.ephesoft.dcma.da.domain.ManualStepHistoryInWorkflow;

/**
 * A Dao representing Manual_Step_History table in database.
 * 
 * @author Ephesoft
 * @version 1.0
 * @see com.ephesoft.dcma.da.dao.ManualStepHistoryDao
 */
@Repository
public class ManualStepHistoryDaoImpl extends HibernateDao<ManualStepHistoryInWorkflow> implements ManualStepHistoryDao {

	/**
	 * LOGGER to print the logging information.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ManualStepHistoryDaoImpl.class);

	/**
	 * An API to insert/update the ManualStepHistoryInWorkflow object.
	 * 
	 * @param ManualStepHistoryInWorkflow manualStepHistoryInWorkflow
	 */
	@Override
	public void updateManualStepHistory(ManualStepHistoryInWorkflow manualStepHistoryInWorkflow) {
		LOGGER.info("Inside UpdateManualStepHistory in ManualStepHistoryDaoImpl");
		ManualStepHistoryInWorkflow existingManualStepHistoryInWorkflow = getExistingManualStepHistory(manualStepHistoryInWorkflow
				.getBatchInstanceId(), manualStepHistoryInWorkflow.getBatchInstanceStatus(), manualStepHistoryInWorkflow.getUserName());
		long totalDuration = 0l;
		if (existingManualStepHistoryInWorkflow != null) {
			existingManualStepHistoryInWorkflow.setEndTime(manualStepHistoryInWorkflow.getEndTime());
			try {
				totalDuration = manualStepHistoryInWorkflow.getDuration();
				totalDuration = existingManualStepHistoryInWorkflow.getDuration() + manualStepHistoryInWorkflow.getDuration();
			} catch (NumberFormatException numberFormatException) {
				totalDuration = Long.MAX_VALUE;
				LOGGER.error("Total review/validate duration is too large to save.Only saving the maximum allowable time");
			}
			existingManualStepHistoryInWorkflow.setDuration(totalDuration);
			saveOrUpdate(existingManualStepHistoryInWorkflow);
		} else {
			create(manualStepHistoryInWorkflow);
		}

	}

	/**
	 * An API to get the ManualStepHistoryInWorkflow object from batchInstanceId And Status.
	 * 
	 * @param String batchInstanceId
	 * @param String batchInstanceStatus
	 * @param String userName
	 * @return ManualStepHistoryInWorkflow
	 */
	@Override
	public ManualStepHistoryInWorkflow getManualStepHistory(String batchInstanceId,String batchInstanceStatus, String userName) {
		LOGGER.info("Inside getManualStepHistory in ManualStepHistoryDaoImpl");
		DetachedCriteria criteria = criteria();
		criteria.add(Restrictions.eq("batchInstanceId", batchInstanceId));
		criteria.add(Restrictions.eq("batchInstanceStatus", batchInstanceStatus));
		criteria.add(Restrictions.eq("userName", userName));
		criteria.add(Restrictions.eq("endTime", new Date(0L)));
		ManualStepHistoryInWorkflow manualStepHistoryInWorkflow=null;
		List<ManualStepHistoryInWorkflow> resultList = find(criteria);
		if (resultList != null && resultList.size() > 0) {
			manualStepHistoryInWorkflow = resultList.get(0);
		}
		return manualStepHistoryInWorkflow;
	}
	/**
	 * An API to get the existing ManualStepHistoryInWorkflow object from batchInstanceId ,Status and username.
	 * 
	 * @param String batchInstanceId
	 * @param String batchInstanceStatus
	 * @param String userName
	 * @return ManualStepHistoryInWorkflow
	 */
	@Override
	public ManualStepHistoryInWorkflow getExistingManualStepHistory(String batchInstanceId, String batchInstanceStatus, String userName) {
		LOGGER.info("Inside getExistingManualStepHistory in ManualStepHistoryDaoImpl");
		DetachedCriteria criteria = criteria();
		criteria.add(Restrictions.eq("batchInstanceId", batchInstanceId));
		criteria.add(Restrictions.eq("batchInstanceStatus", batchInstanceStatus));
		criteria.add(Restrictions.eq("userName", userName));
		ManualStepHistoryInWorkflow manualStepHistoryInWorkflow=null;
		List<ManualStepHistoryInWorkflow> resultList = find(criteria);
		if (resultList != null && resultList.size() > 0) {
			manualStepHistoryInWorkflow = resultList.get(0);
		}
		return manualStepHistoryInWorkflow;

	}

}
