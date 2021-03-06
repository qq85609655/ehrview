/*
 * 
 * 版权：版权所有 Bsoft 保留所有权力。
 */
package com.bsoft.ehr.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bsoft.ehr.auth.LogonValidation;
import com.bsoft.ehr.auth.VisitKey;
import com.bsoft.ehr.controller.support.ControllerResponse;
import com.bsoft.ehr.extend.dic.DocumentFormat;
import com.bsoft.mpi.service.ServiceCode;
import com.bsoft.xds.exception.DocumentEntryException;
import com.bsoft.xds.support.instance.pregnant.PregnantDeathReportDocumentEntryRetrieveService;

/**
 * 孕妇死亡报告
 * 
 */
@Controller
@LogonValidation
public class PregnantDeathReportController extends AbstractTemplateViewController {
	private static final Logger logger = LoggerFactory
			.getLogger(PregnantDeathReportController.class);

	@Autowired
	private PregnantDeathReportDocumentEntryRetrieveService pregnantDeathReportService;

	@RequestMapping("/pregnantDeath/getPregnantDeath")
	@ResponseBody
	public ControllerResponse getPregnantDeath(VisitKey visitKey) {
		ControllerResponse res = new ControllerResponse();
		try {
			Map<String, Object> l = pregnantDeathReportService
					.getMap(visitKey.getMpiId());
			res.setBody(l);
		} catch (DocumentEntryException e) {
			logger.error("Cannot get pregnantDeath record.", e);
			res.setStatus(ServiceCode.CODE_DATABASE_ERROR, "获取孕妇死亡报告失败。");
		}
		return res;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bsoft.ehr.controller.AbstractTemplateViewController#getHtmlDocument
	 * (java.lang.String)
	 */
	@RequestMapping("/pregnantDeath/getHtmlDocument")
	@ResponseBody
	@Override
	public ControllerResponse getHtmlDocument(String dcId) {
		return super.getHtmlDocument(dcId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bsoft.ehr.controller.AbstractTemplateViewController#getDocument(java
	 * .lang.String)
	 */
	@Override
	protected Object getDocument(String dcId) throws DocumentEntryException {
		return pregnantDeathReportService.getDocumentByRecordId(dcId,
				DocumentFormat.HTML);
	}
}
