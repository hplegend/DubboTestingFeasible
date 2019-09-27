package com.hplegend.dubbo.constant;

/**
 * @author hp.he
 * @date 2019/9/26 17:40
 */
public class Constants {

    //Registry Protocol
    public static final String REGISTRY_NONE = "none";
    public static final String REGISTRY_ZOOKEEPER = "zookeeper";
    public static final String REGISTRY_MULTICAST = "multicast";
    public static final String REGISTRY_REDIS = "redis";
    public static final String REGISTRY_SIMPLE = "simple";

    //RPC Protocol
    public static final String RPC_PROTOCOL_DUBBO = "dubbo";
    public static final String RPC_PROTOCOL_RMI = "rmi";
    public static final String RPC_PROTOCOL_HESSIAN = "hessian";
    public static final String RPC_PROTOCOL_HTTP = "http";
    public static final String RPC_PROTOCOL_WEBSERVICE = "webservice";
    public static final String RPC_PROTOCOL_THRIFT = "thrift";
    public static final String RPC_PROTOCOL_MEMCACHED = "memcached";
    public static final String RPC_PROTOCOL_REDIS = "redis";

    public static final String ASYNC = "async";
    public static final String SYMBOL = "://";

    public static final int INT_DEFAULT = 0;
    public static final double DOUBLE_DEFAULT = 0.0d;
    public static final boolean BOOLEAN_DEFAULT = false;
    public static final char CHAR_DEFAULT = '\u0000';
    public static final float FLOAT_DEFAULT = 0.0f;
    public static final byte BYTE_DEFAULT = 0;
    public static final long LONG_DEFAULT = 0l;
    public static final short SHORT_DEFAULT = 0;
    public static final int[] INT_ARRAY_DEFAULT = null;
    public static final double[] DOUBLE_ARRAY_DEFAULT = null;
    public static final boolean[] BOOLEAN_ARRAY_DEFAULT = null;
    public static final char[] CHAT_ARRAY_DEFAULT = null;
    public static final float[] FLOAT_ARRAY_DEFAULT = null;
    public static final byte[] BYTE_ARRAY_DEFAULT = null;
    public static final long[] LONG_ARRAY_DEFAULT = null;
    public static final short[] SHORT_ARRAY_DEFAULT = null;

    public static final String FIELD_DUBBO_REGISTRY_PROTOCOL = "FIELD_DUBBO_REGISTRY_PROTOCOL";
    public static final String FIELD_DUBBO_REGISTRY_GROUP = "FIELD_DUBBO_REGISTRY_GROUP";
    public static final String FIELD_DUBBO_RPC_PROTOCOL = "FIELD_DUBBO_RPC_PROTOCOL";
    public static final String FIELD_DUBBO_ADDRESS = "FIELD_DUBBO_ADDRESS";
    public static final String FIELD_DUBBO_TIMEOUT = "FIELD_DUBBO_TIMEOUT";
    public static final String FIELD_DUBBO_VERSION = "FIELD_DUBBO_VERSION";
    public static final String FIELD_DUBBO_RETRIES = "FIELD_DUBBO_RETRIES";
    public static final String FIELD_DUBBO_CLUSTER = "FIELD_DUBBO_CLUSTER";
    public static final String FIELD_DUBBO_GROUP = "FIELD_DUBBO_GROUP";
    public static final String FIELD_DUBBO_CONNECTIONS = "FIELD_DUBBO_CONNECTIONS";
    public static final String FIELD_DUBBO_LOADBALANCE = "FIELD_DUBBO_LOADBALANCE";
    public static final String FIELD_DUBBO_ASYNC = "FIELD_DUBBO_ASYNC";
    public static final String FIELD_DUBBO_INTERFACE = "FIELD_DUBBO_INTERFACE";
    public static final String FIELD_DUBBO_METHOD = "FIELD_DUBBO_METHOD";
    public static final String FIELD_DUBBO_METHOD_ARGS = "FIELD_DUBBO_METHOD_ARGS";
    public static final String FIELD_DUBBO_METHOD_ARGS_SIZE = "FIELD_DUBBO_METHOD_ARGS_SIZE";
    public static final String FIELD_DUBBO_ATTACHMENT_ARGS = "FIELD_DUBBO_ATTACHMENT_ARGS";
    public static final String FIELD_DUBBO_ATTACHMENT_ARGS_SIZE = "FIELD_DUBBO_ATTACHMENT_ARGS_SIZE";
    public static final String DEFAULT_TIMEOUT = "1000";
    public static final String DEFAULT_VERSION = "1.0";
    public static final String DEFAULT_RETRIES = "0";
    public static final String DEFAULT_CLUSTER = "failfast";
    public static final String DEFAULT_CONNECTIONS = "100";

    //冗余配置元件中的address、protocols、group,用于在sample gui获取配置元件中的默认值
    public static String DEFAULT_PANEL_ADDRESS = "";
    public static String DEFAULT_PANEL_PROTOCOLS = "";
    public static String DEFAULT_PANEL_GROUP = "";


}
