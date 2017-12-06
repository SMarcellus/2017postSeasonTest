package org.usfirst.frc.team2228.robot;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * The {@code Logger} class is a class that contains methods to writing a log text file for the robot.
 * Borrowed from Team 1089 but removing dependency on WPI drive station for simulation purposes
 * 
 * static methods allow any class that wants to add a line to the log to call log(string), etc
 */
class FormatterForFileHandler extends java.util.logging.Formatter {
    //
    // Create a DateFormat to format the logger timestamp.
    //
    private final DateFormat df = new SimpleDateFormat(
            "yyyy/MM/dd hh:mm:ss.SSS");

    public String format(LogRecord record) {
        StringBuilder builder = new StringBuilder(1000);
        // DateFormat objects are not thread-safe....
        synchronized (df) {
            builder.append(df.format(new Date(record.getMillis())))
                    .append(" ");
        }
        
        builder.append(record.getLevel()).append(" - ");
        builder.append(formatMessage(record));
        builder.append("\n");
        return builder.toString();
    }

    public String getHead(Handler h) {
        return super.getHead(h);
    }

    public String getTail(Handler h) {
        return super.getTail(h);
    }
}

public class DebugLogger {

	private static Logger log;
	private static FileHandler fh;
	private static boolean is_logging = false;
	private static FormatterForFileHandler formatter;
	
	private static final SimpleDateFormat format;
	
	static {
		//ISO8601 = new SimpleDateFormat("yyyyMMddHHmmssSSSXXX");
		format = new SimpleDateFormat("M-d_HHmmss");
	}
	
	/**
	 * <pre>
	 * public synchronized static void init(String location)
	 * </pre>
	 * Initializes the current {@code Logger} with the file at the specified location.
	 */
	public static synchronized void init(String filePath) {
		if (!is_logging) {
			try {
				log = Logger.getLogger("DebugLog");
				formatter = new FormatterForFileHandler();
				fh = new FileHandler(filePath
						  + format.format(Calendar.getInstance().getTime()) + ".log");
				/*
				fh.setFormatter(new Formatter() {
		            @Override
		            public String format(LogRecord record) {
		                SimpleDateFormat logTime = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
		                Calendar cal = new GregorianCalendar();
		                cal.setTimeInMillis(record.getMillis());
		                return record.getLevel()
		                        + logTime.format(cal.getTime())
		                        + " || "
		                        + record.getSourceClassName().substring(
		                                record.getSourceClassName().lastIndexOf(".")+1,
		                                record.getSourceClassName().length())
		                        + "."
		                        + record.getSourceMethodName()
		                        + "() : "
		                        + record.getMessage() + "\n";
		            }
		        });
		        */
				fh.setFormatter(formatter);
				/*
				SimpleFormatter formatter = new SimpleFormatter();
				fh.setFormatter(formatter);
				*/
				log.addHandler(fh);
           
				
/*				path = log.getParentFile();
				
				if (!path.exists()){
					path.mkdirs();
					System.out.println("path mkdirs");}
				
				if (!log.exists())
					log.createNewFile();
				
				out = new FileWriter(log.getAbsolutePath());
				writer = new PrintWriter(out);
				*/
				is_logging = true;
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * <pre>
	 * public static synchronized void log(Object... input)
	 * </pre>
	 * Logs the specified input into the log file, separating elements by tabs.
	 * Log entries are prefixed with the current system time.
	 * 
	 * @param input the text to put into the log.
	 */
	public static void log(Object... input){

		if (is_logging) {
			// Get the time outside of synchronized code so it captures most accurately the time when log() is invoked
			String time_prefix = "[" + format.format(Calendar.getInstance().getTimeInMillis()) + "]: ";
			
			synchronized(DebugLogger.class) {
				// Check is_logging again in case it changed since the lock was acquired
				if (is_logging) {
					try {
						String out = "";
						for (Object o : input)
							out += o.toString() + '\t';
						System.out.println(time_prefix + out);
						log.info(out);
					} catch (Exception e) { 
						e.printStackTrace(System.out);
					}
				}
			}
		}
	}
	
	/**
	 * <pre>
	 * public static synchronized void logWarning(String warn)
	 * </pre>
	 * Logs the specified warning into the log file, and timestamps it with the current time of the match.
	 * @param warning the warning to put into the log.
	 */
	public static void logWarning(String warning) {
		log("WARNING: " + warning);
	}
	
	/**
	 * <pre>
	 * public static synchronized void logError(String error)
	 * </pre>
	 * Logs the specified error into the log file, and timestamps it with the current time of the match.
	 * @param error the error to put into the log.
	 */
	public static void logError(String error) {
		log("ERROR: " + error);
	}
	
	/**
	 * <pre>
	 * public static synchronized void logTrace(Exception e)
	 * </pre>
	 * Logs the specified exception and trace into the log file, and timestamps it with the current time of the match.
	 * @param e the exception to trace into the log.
	 */
	public static void logTrace(Exception e) {
		StackTraceElement[] stack = e.getStackTrace();
		String msg = e.toString();
		for (StackTraceElement s : stack)
			msg += "			at " + s.toString();
		logError(msg);
	}
	
	/** 
	 * <pre>
	 * public static synchronized void close()
	 * </pre>
	 * Closes all the writers and releases the resources related to them.
	 */
	public static synchronized void close() {
		try {
			is_logging = false;
			if(log != null){
				if(fh != null) {
					fh.close();
					fh = null;
				}
				log = null;
			}
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
	}
}

