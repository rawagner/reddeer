package org.jboss.reddeer.common.context;

import org.jboss.reddeer.common.logging.MessageType;
import org.jboss.reddeer.common.properties.RedDeerProperties;

/**
 * Recognized RedDeer test execution parameters
 * 
 * @author Jiri Peterka
 *
 */
public class ExecutionSetting {
	private static ExecutionSetting instance;
	private boolean debugEnabled = true;
	private boolean pauseFailedTest = false;
	private int logMessageFilter;
	private boolean filterSet = false;

	/**
	 * Provides ExecutionSetting instance
	 * 
	 * @return instance
	 */
	public static ExecutionSetting getInstance() {
		if (instance == null) {
			instance = new ExecutionSetting();
			instance.debugEnabled = RedDeerProperties.LOG_DEBUG.getBooleanSystemValue();
			instance.pauseFailedTest = RedDeerProperties.PAUSE_FAILED_TEST.getBooleanSystemValue();

			String logMessageFilterText = RedDeerProperties.LOG_MESSAGE_FILTER.getSystemValue();
			instance.parseLogMessageFilter(logMessageFilterText);

		}
		return instance;
	}

	private void parseLogMessageFilter(String logMessageTypeParam) {

		String[] parts = logMessageTypeParam.split("\\|");
		for (String p : parts) {
			if (p.equalsIgnoreCase("DUMP")) {
				addLogMessageType(MessageType.DUMP);
			} else if (p.equalsIgnoreCase("WARN")) {
				addLogMessageType(MessageType.WARN);
			} else if (p.equalsIgnoreCase("ERROR")) {
				addLogMessageType(MessageType.ERROR);
			} else if (p.equalsIgnoreCase("TRACE")) {
				addLogMessageType(MessageType.TRACE);
			} else if (p.equalsIgnoreCase("DEBUG")) {
				addLogMessageType(MessageType.DEBUG);
			} else if (p.equalsIgnoreCase("INFO")) {
				addLogMessageType(MessageType.INFO);
			} else if (p.equalsIgnoreCase("STEP")) {
				addLogMessageType(MessageType.STEP);				
			} else if (p.equalsIgnoreCase("ALL")) {
				setLogMessageFilter(MessageType.ALL);
				return;
			} else if (p.equalsIgnoreCase("NONE")) {
				setLogMessageFilter(MessageType.NONE);
				return;
			}
		}

		// ALL if not set
		if (filterSet == false) {
			setLogMessageFilter(MessageType.ALL);
		}

	}

	private void addLogMessageType(int type) {
		logMessageFilter |= type;
		filterSet = true;
	}

	private ExecutionSetting() {

	}

	/**
	 * isDebugEnabled getter
	 * 
	 * @return true if -DlogDebug=true (default), false otherwise
	 */
	public boolean isDebugEnabled() {
		return debugEnabled;
	}

	/**
	 * Turn debugging on or off
	 *
	 * @param enabled
	 */
	public void setDebugEnabled(boolean enabled) {
		debugEnabled = enabled;
	}

	/**
	 * pauseFailedTest getter
	 * 
	 * @return true if -DpauseFailingTest=true, false otherwise (default)
	 */
	public boolean isPauseFailedTest() {
		return pauseFailedTest;
	}

	/**
	 * Returns log message filter (value that determines what kind of log
	 * messages will be printed)
	 * 
	 * @return log message filter value set by -DlogMessageFilter or in the code
	 */
	public int getLogMessageFilter() {
		return logMessageFilter;
	}

	/**
	 * Sets log message filter (value that determines what kind of log messages
	 * will be printed). Usually you will use -DlogMessageFilter parameter
	 * instead of call this method
	 */
	public void setLogMessageFilter(int value) {
		logMessageFilter = value;
	}

}