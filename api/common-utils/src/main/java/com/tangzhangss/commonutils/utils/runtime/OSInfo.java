package com.tangzhangss.commonutils.utils.runtime;

public class OSInfo {
    private static final String OS = System.getProperty("os.name").toLowerCase();

    private static OSInfo _instance = new OSInfo();

    private EPlatform platform;

    private static String LINE_BREAK="\n";//linux换行符

    private OSInfo(){}


    public static String getLineBreak(){
        if(isWindows()){
            LINE_BREAK="\r\n";
        }else if(isMacOS()){
            LINE_BREAK="\r";
        }
        return LINE_BREAK;
    }

    public static boolean isLinux(){
        return OS.indexOf("linux")>=0;
    }

    public static boolean isMacOS(){
        return OS.indexOf("mac")>=0&&OS.indexOf("os")>0&&OS.indexOf("x")<0;
    }

    public static boolean isMacOSX(){
        return OS.indexOf("mac")>=0&&OS.indexOf("os")>0&&OS.indexOf("x")>0;
    }

    public static boolean isWindows(){
        return OS.indexOf("windows")>=0;
    }

    public static boolean isOS2(){
        return OS.indexOf("os/2")>=0;
    }

    public static boolean isSolaris(){
        return OS.indexOf("solaris")>=0;
    }

    public static boolean isSunOS(){
        return OS.indexOf("sunos")>=0;
    }

    public static boolean isMPEiX(){
        return OS.indexOf("mpe/ix")>=0;
    }

    public static boolean isHPUX(){
        return OS.indexOf("hp-ux")>=0;
    }

    public static boolean isAix(){
        return OS.indexOf("aix")>=0;
    }

    public static boolean isOS390(){
        return OS.indexOf("os/390")>=0;
    }

    public static boolean isFreeBSD(){
        return OS.indexOf("freebsd")>=0;
    }

    public static boolean isIrix(){
        return OS.indexOf("irix")>=0;
    }

    public static boolean isDigitalUnix(){
        return OS.indexOf("digital")>=0&&OS.indexOf("unix")>0;
    }

    public static boolean isNetWare(){
        return OS.indexOf("netware")>=0;
    }

    public static boolean isOSF1(){
        return OS.indexOf("osf1")>=0;
    }

    public static boolean isOpenVMS(){
        return OS.indexOf("openvms")>=0;
    }

    /**
     * 获取操作系统名字
     * @return 操作系统名
     */
    public static EPlatform getOSname(){
        if(isWindows()){
            _instance.platform = EPlatform.Windows;
        }else if (isLinux()) {
            _instance.platform = EPlatform.Linux;
        }else if (isFreeBSD()) {
            _instance.platform = EPlatform.FreeBSD;
        }else if (isHPUX()) {
            _instance.platform = EPlatform.HP_UX;
        }else if (isIrix()) {
            _instance.platform = EPlatform.Irix;
        }else if (isDigitalUnix()) {
            _instance.platform = EPlatform.Digital_Unix;
        }else if (isMacOS()) {
            _instance.platform = EPlatform.Mac_OS;
        }else if (isMacOSX()) {
            _instance.platform = EPlatform.Mac_OS_X;
        }else if (isMPEiX()) {
            _instance.platform = EPlatform.MPEiX;
        }else if (isNetWare()) {
            _instance.platform = EPlatform.NetWare_411;
        }else if (isOpenVMS()) {
            _instance.platform = EPlatform.OpenVMS;
        }else if (isOS2()) {
            _instance.platform = EPlatform.OS2;
        }else if (isOS390()) {
            _instance.platform = EPlatform.OS390;
        }else if (isOSF1()) {
            _instance.platform = EPlatform.OSF1;
        }else if (isSolaris()) {
            _instance.platform = EPlatform.Solaris;
        }else if (isSunOS()) {
            _instance.platform = EPlatform.SunOS;
        }else if (isAix()) {
            _instance.platform = EPlatform.AIX;
        }else{
            _instance.platform = EPlatform.Others;
        }
        return _instance.platform;
    }
    /**
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(OSInfo.getOSname());
    }

}
