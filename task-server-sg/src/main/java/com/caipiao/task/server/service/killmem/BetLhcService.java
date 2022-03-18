package com.caipiao.task.server.service.killmem;

import java.util.List;

public interface BetLhcService {

     int isWinByOnePlayOneOdds(String betNumber, String sgNumber, Integer playId, String dateStr, Integer lotteryId);
     List<Integer> isWinByOnePlayTwoOdds(String betNumber, String sgNumber, Integer playId, String dateStr, Integer lotteryId);
     String isWinByOnePlayManyOdds(String betNumber, String sgNumber, Integer playId, String dateStr, Integer lotteryId);

}
