package org.qubership.profiler.agent;

import java.util.Scanner;
import java.util.logging.Level;

import static org.qubership.profiler.agent.PropertyFacadeBoot.getPropertyOrEnvVariable;

public class ServerNameResolverAgent {
    private static final ESCLogger logger = ESCLogger.getLogger(ServerNameResolverAgent.class.getName());

    public static final String PARAM_POD_NAME = "POD_NAME";
    public static final String DEFAULT_SERVER_NAME = "default";

    public static final String POD_NAME = parsePODName();;
    public static final String SERVER_NAME = System.getProperty("org.qubership.esc.serverName", System.getProperty("weblogic.Name", System.getProperty("jboss.server.name", POD_NAME)));;
    static {
        if (System.getProperty("org.qubership.esc.serverName") == null) {
            System.setProperty("org.qubership.esc.serverName", SERVER_NAME);
        }
    }

    public static String parsePODName() {
        String result = getPropertyOrEnvVariable(PARAM_POD_NAME);
        if (!StringUtils.isBlank(result)) {
            return result;
        }

        try (Scanner s = new Scanner(Runtime.getRuntime().exec("hostname").getInputStream()).useDelimiter("\\A");) {
            if(s.hasNext()) {
                return s.next().trim();
            }
        } catch (Throwable t) {
            logger.log(Level.WARNING, "Exception in getting hostname", t);
        }
        return DEFAULT_SERVER_NAME;
    }

}
