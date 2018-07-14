package com.coronaide.main;

import java.nio.file.Paths;

import javax.inject.Inject;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.coronaide.core.internal.service.ICoreConfiguration;
import com.coronaide.main.config.CoronaIdeMainConfiguration;

/**
 * Listens for the Spring ApplicationReadyEvent so it can finish configuring the application and for use
 * @author nickavv
 * @since 0.1.0
 */
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

    /** Logger reference to output information to the application log files */
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
	
    @Option(name = "--applicationDirectory", usage = "Directory the application is running from", required = true)
    private String applicationDirectory;

    @Option(name = "--workspaceDirectory", usage = "Working directory being used as a context", required = true)
    private String workspaceDirectory;
    
    private final ICoreConfiguration coreConfiguration;
    
    @Inject
    public ApplicationStartup(ICoreConfiguration coreConfiguration) {
    	this.coreConfiguration = coreConfiguration;
    }
     
	/**
	 * This event is executed as late as conceivably possible to indicate that the
	 * application is ready to service requests.
	 */
	@Override
	public void onApplicationEvent(final ApplicationReadyEvent event) {
		String[] args = event.getArgs();
		
		CmdLineParser parser = new CmdLineParser(this);

        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.err.println();
            System.err.println(e.getMessage());
            parser.printUsage(System.err);
            return;
        }

        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
                CoronaIdeMainConfiguration.class)) {
            coreConfiguration.setLocations(Paths.get(applicationDirectory), Paths.get(workspaceDirectory));

            logger.info("Running in workspace {} from {}", workspaceDirectory, applicationDirectory);
        }
	}
}
