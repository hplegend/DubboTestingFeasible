package com.hplegend.dubbo.utils;

import com.google.common.reflect.TypeToken;
import com.hplegend.dubbo.common.MethodArgument;
import com.hplegend.dubbo.common.MethodArgumentAndValue;
import com.hplegend.dubbo.constant.Constants;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

/**
 * @author hp.he
 * @date 2019/9/26 20:01
 */
public class ClassUtils {

    private static final Logger log = LoggerFactory.getLogger(ClassUtils.class.getName());

    private static final String TYPE_NAME_PREFIX = "class ";

    public static String getClassName(Type type) {
        if (type == null) {
            return "";
        }
        String className = type.toString();
        if (className.startsWith(TYPE_NAME_PREFIX)) {
            className = className.substring(TYPE_NAME_PREFIX.length());
        }
        return className;
    }

    @SuppressWarnings("rawtypes")
    public static String[] getMethodParamType(String interfaceName,
                                              String methodName) {
        try {
            // 创建类
            Class<?> class1 = Class.forName(interfaceName);
            // 获取所有的公共的方法
            Method[] methods = class1.getMethods();
            for (Method method : methods) {
                if (method.getName().equals(methodName)) {
                    Class[] paramClassList = method.getParameterTypes();
                    String[] paramTypeList = new String[paramClassList.length];
                    int i = 0;
                    for (Class className : paramClassList) {
                        paramTypeList[i] = className.getName();
                        i++;
                    }
                    return paramTypeList;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    @SuppressWarnings("serial")
    public static void parseParameter(List<String> paramterTypeList,
                                      List<Object> parameterValuesList, MethodArgumentAndValue arg) {
        try {
            String className = arg.getMethodType();
            if ("int".equals(className)) {
                paramterTypeList.add(arg.getMethodType());
                parameterValuesList.add(StringUtils.isBlank(arg.getMethodValue()) ? Constants.INT_DEFAULT : Integer.parseInt(arg.getMethodValue()));
            } else if ("int[]".equals(className)) {
                paramterTypeList.add(arg.getMethodType());
                parameterValuesList.add(StringUtils.isBlank(arg.getMethodValue()) ? Constants.INT_ARRAY_DEFAULT : JsonUtils.formJson(arg.getMethodValue(), new TypeToken<int[]>() {
                }.getType()));
            } else if ("double".equals(className)) {
                paramterTypeList.add(arg.getMethodType());
                parameterValuesList.add(StringUtils.isBlank(arg.getMethodValue()) ? Constants.DOUBLE_DEFAULT : Double.parseDouble(arg.getMethodValue()));
            } else if ("double[]".equals(className)) {
                paramterTypeList.add(arg.getMethodType());
                parameterValuesList.add(StringUtils.isBlank(arg.getMethodValue()) ? Constants.DOUBLE_ARRAY_DEFAULT : JsonUtils.formJson(arg.getMethodValue(), new TypeToken<double[]>() {
                }.getType()));
            } else if ("short".equals(className)) {
                paramterTypeList.add(arg.getMethodType());
                parameterValuesList.add(StringUtils.isBlank(arg.getMethodValue()) ? Constants.SHORT_DEFAULT : Short.parseShort(arg.getMethodValue()));
            } else if ("short[]".equals(className)) {
                paramterTypeList.add(arg.getMethodType());
                parameterValuesList.add(StringUtils.isBlank(arg.getMethodValue()) ? Constants.SHORT_ARRAY_DEFAULT : JsonUtils.formJson(arg.getMethodValue(), new TypeToken<short[]>() {
                }.getType()));
            } else if ("float".equals(className)) {
                paramterTypeList.add(arg.getMethodType());
                parameterValuesList.add(StringUtils.isBlank(arg.getMethodValue()) ? Constants.FLOAT_DEFAULT : Float.parseFloat(arg.getMethodValue()));
            } else if ("float[]".equals(className)) {
                paramterTypeList.add(arg.getMethodType());
                parameterValuesList.add(StringUtils.isBlank(arg.getMethodValue()) ? Constants.FLOAT_ARRAY_DEFAULT : JsonUtils.formJson(arg.getMethodValue(), new TypeToken<float[]>() {
                }.getType()));
            } else if ("long".equals(className)) {
                paramterTypeList.add(arg.getMethodType());
                parameterValuesList.add(StringUtils.isBlank(arg.getMethodValue()) ? Constants.LONG_DEFAULT : Long.parseLong(arg.getMethodValue()));
            } else if ("long[]".equals(className)) {
                paramterTypeList.add(arg.getMethodType());
                parameterValuesList.add(StringUtils.isBlank(arg.getMethodValue()) ? Constants.LONG_ARRAY_DEFAULT : JsonUtils.formJson(arg.getMethodValue(), new TypeToken<long[]>() {
                }.getType()));
            } else if ("byte".equals(className)) {
                paramterTypeList.add(arg.getMethodType());
                parameterValuesList.add(StringUtils.isBlank(arg.getMethodValue()) ? Constants.BYTE_DEFAULT : Byte.parseByte(arg.getMethodValue()));
            } else if ("byte[]".equals(className)) {
                paramterTypeList.add(arg.getMethodType());
                parameterValuesList.add(StringUtils.isBlank(arg.getMethodValue()) ? Constants.BYTE_ARRAY_DEFAULT : JsonUtils.formJson(arg.getMethodValue(), new TypeToken<byte[]>() {
                }.getType()));
            } else if ("boolean".equals(className)) {
                paramterTypeList.add(arg.getMethodType());
                parameterValuesList.add(StringUtils.isBlank(arg.getMethodValue()) ? Constants.BOOLEAN_DEFAULT : Boolean.parseBoolean(arg.getMethodValue()));
            } else if ("boolean[]".equals(className)) {
                paramterTypeList.add(arg.getMethodType());
                parameterValuesList.add(StringUtils.isBlank(arg.getMethodValue()) ? Constants.BOOLEAN_ARRAY_DEFAULT : JsonUtils.formJson(arg.getMethodValue(), new TypeToken<boolean[]>() {
                }.getType()));
            } else if ("char".equals(className)) {
                paramterTypeList.add(arg.getMethodType());
                parameterValuesList.add(StringUtils.isBlank(arg.getMethodValue()) ? Constants.CHAR_DEFAULT : arg.getMethodValue().charAt(0));
            } else if ("char[]".equals(className)) {
                paramterTypeList.add(arg.getMethodType());
                parameterValuesList.add(StringUtils.isBlank(arg.getMethodValue()) ? Constants.CHAT_ARRAY_DEFAULT : JsonUtils.formJson(arg.getMethodValue(), new TypeToken<char[]>() {
                }.getType()));
            } else if ("java.lang.String".equals(className)
                    || "String".equals(className)
                    || "string".equals(className)) {
                paramterTypeList.add("java.lang.String");
                parameterValuesList.add(StringUtils.isBlank(arg.getMethodValue()) ? null : String.valueOf(arg.getMethodValue()));
            } else if ("java.lang.String[]".equals(className)
                    || "String[]".equals(className)
                    || "string[]".equals(className)) {
                paramterTypeList.add("java.lang.String[]");
                parameterValuesList.add(StringUtils.isBlank(arg.getMethodValue()) ? null : JsonUtils.formJson(arg.getMethodValue(), new TypeToken<String[]>() {
                }.getType()));
            } else if ("java.lang.Integer".equals(className)
                    || "Integer".equals(className)
                    || "integer".equals(className)) {
                paramterTypeList.add("java.lang.Integer");
                parameterValuesList.add(StringUtils.isBlank(arg.getMethodValue()) ? null : Integer.valueOf(arg.getMethodValue()));
            } else if ("java.lang.Integer[]".equals(className)
                    || "Integer[]".equals(className)
                    || "integer[]".equals(className)) {
                paramterTypeList.add("java.lang.Integer[]");
                parameterValuesList.add(StringUtils.isBlank(arg.getMethodValue()) ? null : JsonUtils.formJson(arg.getMethodValue(), new TypeToken<Integer[]>() {
                }.getType()));
            } else if ("java.lang.Double".equals(className)
                    || "Double".equals(className)) {
                paramterTypeList.add("java.lang.Double");
                parameterValuesList.add(StringUtils.isBlank(arg.getMethodValue()) ? null : Double.valueOf(arg.getMethodValue()));
            } else if ("java.lang.Double[]".equals(className)
                    || "Double[]".equals(className)) {
                paramterTypeList.add("java.lang.Double[]");
                parameterValuesList.add(StringUtils.isBlank(arg.getMethodValue()) ? null : JsonUtils.formJson(arg.getMethodValue(), new TypeToken<Double[]>() {
                }.getType()));
            } else if ("java.lang.Short".equals(className)
                    || "Short".equals(className)) {
                paramterTypeList.add("java.lang.Short");
                parameterValuesList.add(StringUtils.isBlank(arg.getMethodValue()) ? null : Short.valueOf(arg.getMethodValue()));
            } else if ("java.lang.Short[]".equals(className)
                    || "Short[]".equals(className)) {
                paramterTypeList.add("java.lang.Short[]");
                parameterValuesList.add(StringUtils.isBlank(arg.getMethodValue()) ? null : JsonUtils.formJson(arg.getMethodValue(), new TypeToken<Short[]>() {
                }.getType()));
            } else if ("java.lang.Long".equals(className)
                    || "Long".equals(className)) {
                paramterTypeList.add("java.lang.Long");
                parameterValuesList.add(StringUtils.isBlank(arg.getMethodValue()) ? null : Long.valueOf(arg.getMethodValue()));
            } else if ("java.lang.Long[]".equals(className)
                    || "Long[]".equals(className)) {
                paramterTypeList.add("java.lang.Long[]");
                parameterValuesList.add(StringUtils.isBlank(arg.getMethodValue()) ? null : JsonUtils.formJson(arg.getMethodValue(), new TypeToken<Long[]>() {
                }.getType()));
            } else if ("java.lang.Float".equals(className)
                    || "Float".equals(className)) {
                paramterTypeList.add("java.lang.Float");
                parameterValuesList.add(StringUtils.isBlank(arg.getMethodValue()) ? null : Float.valueOf(arg.getMethodValue()));
            } else if ("java.lang.Float[]".equals(className)
                    || "Float[]".equals(className)) {
                paramterTypeList.add("java.lang.Float[]");
                parameterValuesList.add(StringUtils.isBlank(arg.getMethodValue()) ? null : JsonUtils.formJson(arg.getMethodValue(), new TypeToken<Float[]>() {
                }.getType()));
            } else if ("java.lang.Byte".equals(className)
                    || "Byte".equals(className)) {
                paramterTypeList.add("java.lang.Byte");
                parameterValuesList.add(StringUtils.isBlank(arg.getMethodValue()) ? null : Byte.valueOf(arg.getMethodValue()));
            } else if ("java.lang.Byte[]".equals(className)
                    || "Byte[]".equals(className)) {
                paramterTypeList.add("java.lang.Byte[]");
                parameterValuesList.add(StringUtils.isBlank(arg.getMethodValue()) ? null : JsonUtils.formJson(arg.getMethodValue(), new TypeToken<Byte[]>() {
                }.getType()));
            } else if ("java.lang.Boolean".equals(className)
                    || "Boolean".equals(className)) {
                paramterTypeList.add("java.lang.Boolean");
                parameterValuesList.add(StringUtils.isBlank(arg.getMethodValue()) ? null : Boolean.valueOf(arg.getMethodValue()));
            } else if ("java.lang.Boolean[]".equals(className)
                    || "Boolean[]".equals(className)) {
                paramterTypeList.add("java.lang.Boolean[]");
                parameterValuesList.add(StringUtils.isBlank(arg.getMethodValue()) ? null : JsonUtils.formJson(arg.getMethodValue(), new TypeToken<Boolean[]>() {
                }.getType()));
            } else {
                // 对于数组类型
                if (className.endsWith("[]")) {
                    List<?> list = null;
                    if (!StringUtils.isBlank(arg.getMethodValue())) {
                        list = JsonUtils.formJson(arg.getMethodValue(), new TypeToken<List<?>>() {
                        }.getType());
                    }
                    paramterTypeList.add(arg.getMethodType());
                    parameterValuesList.add(list == null ? null : list.toArray());
                } else {
                    // 对于类类型, 类型和jsonString。系统里面负责解析。
                    try {
                        // List<Object>，会转换成List LinkedHashMap，对象全部被转换成了HashMap的key-value结构

                        // 子定义类加载器
                        // 支持 classPath 配置
                        Class<?> clazz = Class.forName(className);
                        paramterTypeList.add(arg.getMethodType());
                        parameterValuesList.add(StringUtils.isBlank(arg.getMethodValue()) ? null : JsonUtils.formJson(arg.getMethodValue(), clazz));
                    } catch (ClassNotFoundException e) {
                        // 不是jdk或者lib下的类，使用通用map格式反序列化值
                        paramterTypeList.add(arg.getMethodType());
                        Object obj = null;
                        if (!StringUtils.isBlank(arg.getMethodValue())) {
                            // 使用通用map格式反序列化值
                            obj = JsonUtils.formJson(arg.getMethodValue(), new TypeToken<HashMap<String, Object>>() {
                            }.getType());
                            if (obj == null) {
                                obj = JsonUtils.formJson(arg.getMethodValue(), String.class);
                            }
                        }
                        parameterValuesList.add(obj);
                    }
                }
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid parameter => [ParamType=" + arg.getMethodValue() + ",ParamValue=" + arg.getMethodValue() + "]", e);
        }
    }
}
