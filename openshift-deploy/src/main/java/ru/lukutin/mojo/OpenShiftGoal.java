package ru.lukutin.mojo;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import ru.lukutin.ssh.SshConnection;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by kerenby on 8/11/15.
 */

@Mojo(name = "odep")
public class OpenShiftGoal extends AbstractMojo {

    @Parameter(property = "application")
    private String application;

    @Parameter(property = "server")
    private String server;

    @Parameter(property = "useSshKey")
    private boolean useSshKey;

    @Parameter(property = "key")
    private String key;

    @Parameter(property = "user")
    private String user;

    @Parameter(property = "password")
    private String password;

    @Parameter(property = "host")
    private String host;

    @Parameter(property = "warfile")
    private String warfile;

    @Parameter(property = "path")
    private String path;

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public boolean isUseSshKey() {
        return useSshKey;
    }

    public void setUseSshKey(boolean useSshKey) {
        this.useSshKey = useSshKey;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getWarfile() {
        return warfile;
    }

    public void setWarfile(String warfile) {
        this.warfile = warfile;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {

        getLog().info("Application: " + getApplication().toString());
        getLog().info("Server: " + getServer().toString());
        getLog().info("Host: " + getHost().toString());
        getLog().info("User: " + getUser().toString());
        getLog().info("Key: " + getKey().toString());
        getLog().info("File: " + getWarfile().toString());
        getLog().info("Path: " + getPath().toString());

        String[] params = {getHost(), getUser(), getKey(), getWarfile(), getPath()};
        ExecutorService exec = null;
        try {
            exec = Executors.newFixedThreadPool(1);
            Future<String[]> f = exec.submit(new SshClient(params));

            System.out.print("\r[INFO] " + "Coping file.");
            while (!f.isDone()) {
                Thread.sleep(50);
                System.out.print(".");
            }
            System.out.print("\n");

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            exec.shutdown();
        }

    }

    private static class SshClient implements Callable<String[]> {

        private String user;
        private String host;
        private String key;
        private String warfile;
        private String path;

        public SshClient(String[] params) {
            this.host = params[0];
            this.user = params[1];
            this.key = params[2];
            this.warfile = params[3];
            this.path = params[4];
        }

        @Override
        public String[] call() throws Exception {

            SshConnection con = new SshConnection(host, user, key);
            con.connect();
            con.deployWarFile(warfile, path);
            con.close();

            return null;
        }
    }


}
