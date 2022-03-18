package com.caipiao.task.server.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class AusPksSgUtil {
	
//	public static void main(String[] args) throws IOException {
//		new Runnable() {
//			@SuppressWarnings("resource")
//			@Override
//			public void run() {
//				String temp = "";
//				File file = new File("C:\\Users\\Msan\\Desktop\\data.txt");
//				FileOutputStream fos = null;
//				try {
//					fos = new FileOutputStream(file);
//				} catch (FileNotFoundException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//				while (true) {
//					String get = get("https://api-info-act.keno.com.au/v2/games/kds?jurisdiction=ACT", "UTF-8");
//					if(!get.equals(temp)) {
//						temp = get;
//						try {
//							get = get+"\n";
//							fos.write(get.getBytes());
//						} catch (FileNotFoundException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						} catch (IOException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						} 
//					}
//					try {
//						Thread.sleep(10000);
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
//			}
//		}.run();
//		
//	}
    /**
     * 请求接口获取json格式数据
     * @param url 彩种
     * @param charset 字符编码
     * @return 返回json结果
     */
    public static String get(String url, String charset) {
        String result = "";
        try {
        	 //创建httpclient对象
            CloseableHttpClient httpClient = HttpClients.createDefault();
            //代理对象
            HttpHost proxy = new HttpHost("47.74.70.72", 8888, "http");
            //配置对象
            RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
            //创建请求方法的实例， 并指定请求url
            HttpGet httpget=new HttpGet("https://api-info-act.keno.com.au/v2/games/kds?jurisdiction=ACT");
            //使用配置
            httpget.setConfig(config);
            CloseableHttpResponse response=httpClient.execute(httpget);
            HttpEntity entity=response.getEntity();
            result =EntityUtils.toString(entity, "utf-8");
            httpClient.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    

	/**
	 * 计算第一部分
	 * @return
	 */
	public static AusPksDto partOneMatch(AusPksDto dto){
		//获取开奖号码
		List<Integer> numbers = dto.getNumbers();
		//获取每一个开奖号码，截取尾数，判断是否重复，非重复添加至List数组
		if(null!=numbers&&numbers.size()>0) {
			for (Integer number : numbers) {
				String numberStr = String.valueOf(number);
				String index = "";
				if(numberStr.length() == 2) {
					index  = numberStr.substring(numberStr.length()-1, numberStr.length());
				}else if(numberStr.length()==1){
					index  = numberStr.toString();
				}
				int count = Integer.parseInt(index);
				if(!dto.getFinishedNumbers().contains(count)) {
					dto.getFinishedNumbers().add(count);
				}
			}
		}
		return setCycleParames(dto);
	}
	
	/**
	 * 计算第二部分
	 * @return
	 */
	public static AusPksDto partTwoMatch(AusPksDto dto){
		//获取开奖号码
		List<Integer> numbers = dto.getNumbers();
		//获取每一个开奖号码，个位十位相加后截取尾数，判断是否重复，非重复添加至List数组
		if(null!=numbers&&numbers.size()>0) {
			for (Integer number : numbers) {
				String numberStr = String.valueOf(number);
				if(numberStr.length()==2) {
					String beginIndex = numberStr.substring(0, numberStr.length()-1);
					String lastIndex = numberStr.substring(numberStr.length()-1,numberStr.length());
					int beginNumIndex = Integer.parseInt(beginIndex);
					int lastNumIndex = Integer.parseInt(lastIndex);
					int count = beginNumIndex+lastNumIndex;
					String countStr = String.valueOf(count);
					countStr = countStr.substring(countStr.length()-1, countStr.length());
					count = Integer.parseInt(countStr);
					if(!dto.getFinishedNumbers().contains(count)) {
						dto.getFinishedNumbers().add(count);
					}
				}
			}
		}
		return setCycleParames(dto);
	}
	
	/**
	 * 计算第三部分
	 * @return
	 */
	public static AusPksDto partThreeMatch(AusPksDto dto){
		//获取开奖号码
		List<Integer> numbers = dto.getNumbers();
		//拆分数组1-10，20-11为两个数组，两个数组索引位置相同的相加
		//截取尾数，判断是否重复，非重复添加至List数组
		List<Integer> beginNumbers = null;
		List<Integer> lastNumbers = null;
		if(numbers.size()==20) {
			beginNumbers = numbers.subList(0, 9);
			lastNumbers = numbers.subList(10, 19);
			//反序操作
			Collections.reverse(lastNumbers);
		}
		for (int i = 0; i < 10; i++) {
			int beginIndex = beginNumbers.get(i);
			int lastIndex = lastNumbers.get(i);
			int sum = beginIndex + lastIndex;
			//截取尾数
			String sumStr = String.valueOf(sum);
			String lastStr = sumStr.substring(sumStr.length()-1,sumStr.length());
			int count =Integer.parseInt(lastStr);
			if(!dto.getFinishedNumbers().contains(count)) {
				dto.getFinishedNumbers().add(count);
			}
		}
		return setCycleParames(dto);
	}
	
	/**
	 * 计算第四部分
	 * @return
	 */
	public static AusPksDto partFourMatch(AusPksDto dto){
		//获取开奖号码
		List<Integer> numbers = dto.getNumbers();
		//拆分数组索引单数，索引双数为两个数组，两个数组索引位置相同的相加
		//截取尾数，判断是否重复，非重复添加至List数组
		List<Integer> singleNumbers = new ArrayList<Integer>();
		List<Integer> doubleNumbers = new ArrayList<Integer>();
		if(numbers.size()==20) {
			singleNumbers.add(numbers.get(0));  doubleNumbers.add(numbers.get(1));
			singleNumbers.add(numbers.get(2));  doubleNumbers.add(numbers.get(3));
			singleNumbers.add(numbers.get(4));  doubleNumbers.add(numbers.get(5));
			singleNumbers.add(numbers.get(6));  doubleNumbers.add(numbers.get(7));
			singleNumbers.add(numbers.get(8));  doubleNumbers.add(numbers.get(9));
			singleNumbers.add(numbers.get(10));  doubleNumbers.add(numbers.get(11));
			singleNumbers.add(numbers.get(12));  doubleNumbers.add(numbers.get(13));
			singleNumbers.add(numbers.get(14));  doubleNumbers.add(numbers.get(15));
			singleNumbers.add(numbers.get(16));  doubleNumbers.add(numbers.get(17));
			singleNumbers.add(numbers.get(18));  doubleNumbers.add(numbers.get(19));
		}
		for (int i = 0; i < 10; i++) {
			int singleIndex = singleNumbers.get(i);
			int doubleIndex = doubleNumbers.get(i);
			int sum = singleIndex + doubleIndex;
			//截取尾数
			String sumStr = String.valueOf(sum);
			String lastStr = sumStr.substring(sumStr.length()-1,sumStr.length());
			int count =Integer.parseInt(lastStr);
			if(!dto.getFinishedNumbers().contains(count)) {
				dto.getFinishedNumbers().add(count);
			}
		}
		return setCycleParames(dto);
	}
	
	/**
	 * 计算第五部分
	 * @return
	 */
	public static  AusPksDto partFiveMatch(AusPksDto dto){
		//获取开奖号码
				List<Integer> numbers = dto.getNumbers();
				//拆分数组1-10，11-20为两个数组，两个数组索引位置相同的相加
				//截取尾数，判断是否重复，非重复添加至List数组
				List<Integer> beginNumbers = null;
				List<Integer> lastNumbers = null;
				if(numbers.size()==20) {
					beginNumbers = numbers.subList(0, 9);
					lastNumbers = numbers.subList(10, 19);
				}
				for (int i = 0; i < 10; i++) {
					int beginIndex = beginNumbers.get(i);
					int lastIndex = lastNumbers.get(i);
					int sum = beginIndex + lastIndex;
					//截取尾数
					String sumStr = String.valueOf(sum);
					String lastStr = sumStr.substring(sumStr.length()-1,sumStr.length());
					int count =Integer.parseInt(lastStr);
					if(!dto.getFinishedNumbers().contains(count)) {
						dto.getFinishedNumbers().add(count);
					}
				}
				return setCycleParames(dto);
	}
	
	private static  AusPksDto setCycleParames(AusPksDto dto) {
		//判断是否获取完整中奖号码，如果中奖号码全部取到，则把0转换成10
		if(dto.getFinishedNumbers().size()==10) {
			for (int i = 0; i < dto.getFinishedNumbers().size(); i++) {
				int num = dto.getFinishedNumbers().get(i);
				if(num==0) {
					//0代表10
					dto.getFinishedNumbers().set(i, 10);
					break;
				}
			}
			dto.setFinished(true);
		//判断是否获取完整中奖号码，如果中奖号码取到9个，则使用总数55减去已取到9个号码总和得到最后一位号码
		}else if(dto.getFinishedNumbers().size()==9){
			int allCount = 55; 
			int finishedCount = 0;
			for (int i = 0; i < dto.getFinishedNumbers().size(); i++) {
				int num = dto.getFinishedNumbers().get(i);
				finishedCount += (num==0?10:num);
				if(num==0) {
					//0代表10
					dto.getFinishedNumbers().set(i, 10);
				}
			}
			
			
			int lastCount = allCount - finishedCount;
			dto.getFinishedNumbers().add(lastCount);
			dto.setFinished(true);
		//取奖号码未超过8个,则未完成采集,进入下一个规则计算	
		}else {
			dto.setFinished(false);
		}
		return dto;
	}

    
}
