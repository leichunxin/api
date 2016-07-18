package com.oves.baseframework.common.server.protocol;

/**
 * Remoting模块中，服务器与客户端通过传递RemotingCommand来交互
 *
 * @author jin.qian
 * @version $Id: RemotingCommand.java, v 0.1 2016年1月14日 下午7:56:44 jin.qian Exp $
 */
public class RemotingCommand {
    public static String RemotingVersionKey = "jdb.remoting.version";
    private static volatile int ConfigVersion = -1;
    /**
     * 命令序列号
     */
    private int opaque = 0;
    /**
     * 协议版本号
     */
    private int version = 0;
    private RemotingCommandType type;
    /**
     * Body 部分
     */
    private transient byte[] body;

    public enum RemotingCommandType {
        REQUEST_COMMAND,
        RESPONSE_COMMAND;
    }

    public static RemotingCommand createRequestCommand() {
        RemotingCommand cmd = new RemotingCommand();
        setCmdVersion(cmd);
        cmd.setType(RemotingCommandType.REQUEST_COMMAND);
        return cmd;
    }

    /**
     * 只有通信层内部会调用，业务不会调用
     */
    public static RemotingCommand createResponseCommand(byte[] body) {
        RemotingCommand cmd = new RemotingCommand();
        setCmdVersion(cmd);
        cmd.setType(RemotingCommandType.RESPONSE_COMMAND);
        cmd.setBody(body);
        return cmd;
    }

    public static RemotingCommand createResponseCommand(int code, String remark) {
        RemotingCommand cmd = new RemotingCommand();
        String str = "error[" + code + "] " + remark;
        cmd.setBody(str.getBytes());
        cmd.setType(RemotingCommandType.RESPONSE_COMMAND);
        setCmdVersion(cmd);
        return cmd;
    }

    private static void setCmdVersion(RemotingCommand cmd) {
        if (ConfigVersion >= 0) {
            cmd.setVersion(ConfigVersion);
        } else {
            String v = System.getProperty(RemotingVersionKey);
            if (v != null) {
                int value = Integer.parseInt(v);
                cmd.setVersion(value);
                ConfigVersion = value;
            }
        }
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public RemotingCommandType getType() {
        return type;
    }

    public void setType(RemotingCommandType type) {
        this.type = type;
    }

    public int getOpaque() {
        return opaque;
    }

    public void setOpaque(int opaque) {
        this.opaque = opaque;
    }

    @Override
    public String toString() {
        return "RemotingCommand [version=" + version + ", opaque=" + opaque + "]";
    }
}
