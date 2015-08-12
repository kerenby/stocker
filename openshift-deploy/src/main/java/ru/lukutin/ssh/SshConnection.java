package ru.lukutin.ssh;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by kerenby on 8/11/15.
 */
public class SshConnection {

    private String host;
    private String user;
    private String key;

    private Session session = null;
    private ChannelSftp sftp = null;

    public SshConnection() {

    }

    public SshConnection(String host, String user, String key) {
        this.host = host;
        this.user = user;
        this.key = key;
    }

    public boolean connect() {

        try {
            JSch jsch = new JSch();
            jsch.addIdentity(key);
            session = jsch.getSession(user, host);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();

        } catch (Exception e) {
            System.err.println(e);
        }

        return true;
    }

    public void deployWarFile(String warFile, String path) {
        try {
            sftp = (ChannelSftp) session.openChannel("sftp");
            sftp.connect();

            System.out.println(sftp.pwd());

            sftp.put(warFile, path);
            sftp.exit();

        } catch (Exception e) {
            System.err.print(e);
        } finally {
            if (sftp != null) {
                sftp.disconnect();
            }
        }
    }

    public void close() {
        try {
            if (session != null) {
                session.disconnect();
                System.out.print("Session was closed");
            }
        }
        catch (Exception e) {
            System.err.println(e);
        }
    }


}
