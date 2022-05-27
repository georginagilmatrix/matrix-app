package com.matrix.automation.runner;

import com.matrix.automation.component.ThreadLocalStepDefinitionMatch;
import cucumber.runtime.StepDefinitionMatch;
import gherkin.formatter.Reporter;
import gherkin.formatter.model.Match;
import gherkin.formatter.model.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomFormatter implements Reporter {
//public class CustomFormatter {
	protected static final Logger LOGGER = LoggerFactory.getLogger(CustomFormatter.class);

	@Override
	public void before(Match match, Result result) {
		LOGGER.debug("<<<<>>>>  CustomFormatter, before {}", result);
	}

	@Override
	public void result(Result result) {
		LOGGER.debug("<<<<>>>>  CustomFormatter, result {}", result.getStatus());
//		Map<String, Object> map = result.toMap();
//		for (Entry<String, Object> obj:  map.entrySet()) {
//			LOGGER.info("<<<<>>>>  CustomFormatter, result, obj.getKey() {}", obj.getKey());
//			LOGGER.info("<<<<>>>>  CustomFormatter, result, obj.getValue() {}", obj.getValue());
//		}
	}

	@Override
	public void after(Match match, Result result) {
		LOGGER.debug("<<<<>>>>  CustomFormatter, after {}", result);
//		Map<String, Object> map = result.toMap();
//		for (Entry<String, Object> obj:  map.entrySet()) {
//			LOGGER.info("<<<<>>>>  CustomFormatter, after, obj.getKey() {}", obj.getKey());
//			LOGGER.info("<<<<>>>>  CustomFormatter, after, obj.getValue() {}", obj.getValue());
//		}
//		try {
//			printFile.close();
//			writeFile.close();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	@Override
	public void match(Match match) {
		try {
			StepDefinitionMatch stepMatch = (StepDefinitionMatch)match;
			ThreadLocalStepDefinitionMatch.set(stepMatch);
			LOGGER.debug("<<<<>>>>  CustomFormatter, match {}", stepMatch.getStepName());
		} catch (Exception e) {
			LOGGER.error(String.valueOf(e));
		}
	}

	@Override
	public void embedding(String mimeType, byte[] data) {}

	@Override
	public void write(String text) {}

}
