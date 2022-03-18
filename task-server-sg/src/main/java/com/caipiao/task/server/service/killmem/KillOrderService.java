package com.caipiao.task.server.service.killmem;

public interface KillOrderService {

    public String getLhcKillNumber(String issue, String lotteryid);
    public String getSscKillNumber(String issue, String lotteryid);
    public String getBjpksKillNumber(String issue, String lotteryid);
    public String getXyftKillNumber(String issue, String lotteryid);

    public String getAzksKillNumber(String issue,String lotteryid);

    public String getDzksKillNumber(String issue,String lotteryid);

    public String getDzxyftKillNumber(String issue,String lotteryid);

    public String getDzpceggKillNumber(String issue,String lotteryid);

    public String getXjplhcKillNumber(String issue,String lotteryid);
}
