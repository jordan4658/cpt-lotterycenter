package com.caipiao.live.common.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class VerifyCodeUtil {

    /**
     * 很多网站使用滑块验证码提高网站安全性，为了做到真正的验证，必须要走后台服务器。
     *
     * 下面是java实现滑块验证的核心步骤：
     *
     * 1、从服务器随机取一张图片，并对图片上的随机x,y坐标和宽高一块区域抠图；
     *
     * 2、根据步骤一的坐标和宽高，使用二维数组保存原图上抠图区域的像素点坐标；
     *
     * 3、根据步骤二的坐标点，对原图的抠图区域的颜色进行处理。
     *
     * 完成以上步骤之后得到三张图（原图，扣下来的方块图，带有抠图区域阴影的原图），将这三张图和抠图区域的y坐标传到前台，前端在移动方块验证时，将移动后的x坐标传递到后台与原来的x坐标作比较，如果在阈值内则验证通过。
     *
     * 请求验证的步骤：前台向后台发起请求，后台随机一张图片做处理将处理完的三张图片的base64，抠图y坐标和token返回给前台。
     *
     * 前台滑动图片将x坐标和token作为参数请求后台验证，服务器根据token取出x坐标与参数的x进行比较。
     */

    /**
     * 这时候就要想这个轮廓形状怎么生成了。有坐标系、有矩形、有圆形，没错，用到数学的图形函数。典型用到一个圆的函数方程和矩形的边线的函数，类似：
     * (x-a)²+(y-b)²=r²中，有三个参数a、b、r，即圆心坐标为(a，b)，半径r。这些将抠图放在上文描述的坐标系上很容易就图算出来具体的值。
     *
     * @return
     */
    /**
     * @Createdate: 2019年1月24日上午10:52:42 @Title: getBlockData @Description: 生成小图轮廓 /
     * 获取扣图区域坐标 @author mzl @return int[][] @throws
     */
    private static int[][] getBlockData() {
        int[][] data = new int[targetLength][targetWidth];
        double x2 = targetLength - circleR;
        // 随机生成圆的位置
        double h1 = circleR + Math.random() * (targetWidth - 3 * circleR - r1);
        double po = circleR * circleR;
        double xbegin = targetLength - circleR - r1;
        double ybegin = targetWidth - circleR - r1;
        for (int i = 0; i < targetLength; i++) {// 1280
            for (int j = 0; j < targetWidth; j++) {// 720
                double d3 = Math.pow(i - x2, 2) + Math.pow(j - h1, 2);
                double d2 = Math.pow(j - 2, 2) + Math.pow(i - h1, 2);
                if ((j <= ybegin && d2 <= po) || (i >= xbegin && d3 >= po)) {
                    data[i][j] = 0;
                } else {
                    data[i][j] = 1;
                }
            }
        }
        return data;
    }

    /**
     *
     * @param targetLength          原图的长度
     * @param targetWidth  原图的宽度
     * @param x            裁剪区域的x坐标
     * @param y            裁剪区域的y坐标
     * @param length        抠图的长度
     * @param width        抠图的宽度
     * @return
     */
	/* private int [][] getCutAreaData(int targetLength,int targetWidth,int x,int y ,int length,int width){
	    int[][] data = new int[targetLength][targetWidth];
	    for (int i=0;i<targetLength;i++){//1280
	        for(int j=0;j<targetWidth;j++){//720
	            if(i<x+length&&i>=x&&j<y+width&&j>y){
	                data[i][j]=1;
	            }else {
	                data[i][j]=0;
	            }
	        }
	    }
	    return data;
	}*/

    /**
     * @Createdate: 2019年1月24日上午10:51:30 @Title: cutByTemplate @Description:
     * 生成小图片、给大图片添加阴影 @author mzl @param oriImage @param targetImage @param
     * templateImage @param x @param y void @throws
     */
    private static void cutByTemplate(BufferedImage oriImage, BufferedImage targetImage, int[][] templateImage, int x,
                                      int y) {
        for (int i = 0; i < targetLength; i++) {
            for (int j = 0; j < targetWidth; j++) {
                int rgb = templateImage[i][j];
                // 原图中对应位置变色处理
                int rgb_ori = oriImage.getRGB(x + i, y + j);
                if (rgb == 1) {
                    // 抠图上复制对应颜色值
                    targetImage.setRGB(i, j, rgb_ori);
                    // 原图对应位置颜色变化
                    oriImage.setRGB(x + i, y + j, rgb_ori & 0x363636);
                } else {
                    // 这里把背景设为透明
                    targetImage.setRGB(i, j, rgb_ori & 0x00ffffff);
                }
            }
        }

        // REDIS 设置tooken
    }

    /**
     * @Createdate: 2019年1月24日上午11:49:42 @Title: createImage @Description:
     * 获取大图，小图Base64码 @author mzl @param url @return Map<String,String> @throws
     */
    @SuppressWarnings("finally")
    public static Map<String, String> createOnLineImage(String url2, int L, int W) {
        Map<String, String> resultMap = new HashMap<String, String>();
        try {
            URL url = new URL(url2);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5 * 1000);
            InputStream inStream = conn.getInputStream();

            // BufferedImage bufferedImage = ImageIO.read(new FileInputStream(url));
            BufferedImage bufferedImage = ImageIO.read(inStream);
            BufferedImage target = new BufferedImage(targetLength, targetWidth, BufferedImage.TYPE_4BYTE_ABGR);
            cutByTemplate(bufferedImage, target, getBlockData(), L, W);
            // 扣下来的方块图，带有抠图区域阴影的
            resultMap.put("b", imageToBase64(bufferedImage));// 大图
            resultMap.put("s", imageToBase64(target));// 小图
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return resultMap;
        }
    }

    /**
     * @Createdate: 2019年1月24日上午11:49:42 @Title: createImage @Description:
     * 获取大图，小图Base64码 @author mzl @param url @return Map<String,String> @throws
     */
    @SuppressWarnings("finally")
    public static Map<String, String> createImage(String url, int L, int W) {
        Map<String, String> resultMap = new HashMap<String, String>();
        try {
            BufferedImage bufferedImage = ImageIO.read(new FileInputStream(url));
            BufferedImage target = new BufferedImage(targetLength, targetWidth, BufferedImage.TYPE_4BYTE_ABGR);
            cutByTemplate(bufferedImage, target, getBlockData(), L, W);
            // 扣下来的方块图，带有抠图区域阴影的
            resultMap.put("b", imageToBase64(bufferedImage));// 大图
            resultMap.put("s", imageToBase64(target));// 小图
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return resultMap;
        }
    }


    /**
     * @Createdate: 2019年1月24日上午11:14:19 @Title: getImageStr @Description:
     * 图片转BASE64 @author mzl @param image @return @throws IOException String @throws
     */
    public static String getImageBASE64(BufferedImage image) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(image, "png", out);
        byte[] b = out.toByteArray();// 转成byte数组
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(b);// 生成base64编码
    }

    public static String imageToBase64(BufferedImage image) throws Exception {
        byte[] imagedata = null;
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        ImageIO.write(image, "png", bao);
        imagedata = bao.toByteArray();
        BASE64Encoder encoder = new BASE64Encoder();
        String BASE64IMAGE = encoder.encodeBuffer(imagedata).trim();
        BASE64IMAGE = BASE64IMAGE.replaceAll("\n", "").replaceAll("\r", "");// 删除 \r\n
        return BASE64IMAGE;
    }

    public static int targetLength = 55;// 小图长
    public static int targetWidth = 45;// 小图宽
    public static int circleR = 6;// 半径
    public static int r1 = 3;// 距离点

    public static int ORI_WIDTH = 300; // 源文件宽度
    public static int ORI_HEIGHT = 120; // 源文件高度
    public static int X; // 抠图坐标x
    public static int Y; // 抠图坐标y
    public static int WIDTH = 55; // 模板图宽度
    public static int HEIGHT = 45; // 模板图高度
    public static float xPercent; // X位置移动百分比
    public static float yPercent; // Y位置移动百分比

    /**
     * 随机生成抠图坐标
     */
    public static void generateCutoutCoordinates() {
        Random random = new Random();
        int widthDifference = ORI_WIDTH - WIDTH;
        int heightDifference = ORI_HEIGHT - HEIGHT;

        if (widthDifference <= 0) {
            X = 5;

        } else {
            X = random.nextInt(ORI_WIDTH - WIDTH) + 5;
        }

        if (heightDifference <= 0) {
            Y = 5;
        } else {
            Y = random.nextInt(ORI_HEIGHT - HEIGHT) + 5;
        }
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(2);

        xPercent = Float.parseFloat(numberFormat.format((float) X / (float) ORI_WIDTH));
        yPercent = Float.parseFloat(numberFormat.format((float) Y / (float) ORI_HEIGHT));
    }

    public static void main(String[] args) {
        generateCutoutCoordinates();
        System.out.println(xPercent);
        System.out.println(yPercent);
        System.out.println(X);
        System.out.println(Y);
        String url = "C:" + File.separator + "YIHUI" + File.separator + "tuzi.png";
        //String url = "https://prolivephoto.s3-ap-northeast-1.amazonaws.com/ad/photo_morentouxiang.jpg";
        Map<String, String> resultMap = createImage(url, X, Y);
        System.out.println(resultMap);
    }

    public static int getTargetLength() {
        return targetLength;
    }

    public static void setTargetLength(int targetLength) {
        VerifyCodeUtil.targetLength = targetLength;
    }

    public static int getTargetWidth() {
        return targetWidth;
    }

    public static void setTargetWidth(int targetWidth) {
        VerifyCodeUtil.targetWidth = targetWidth;
    }

    public static int getCircleR() {
        return circleR;
    }

    public static void setCircleR(int circleR) {
        VerifyCodeUtil.circleR = circleR;
    }

    public static int getR1() {
        return r1;
    }

    public static void setR1(int r1) {
        VerifyCodeUtil.r1 = r1;
    }

    public static int getORI_WIDTH() {
        return ORI_WIDTH;
    }

    public static void setORI_WIDTH(int oRI_WIDTH) {
        ORI_WIDTH = oRI_WIDTH;
    }

    public static int getORI_HEIGHT() {
        return ORI_HEIGHT;
    }

    public static void setORI_HEIGHT(int oRI_HEIGHT) {
        ORI_HEIGHT = oRI_HEIGHT;
    }

    public static int getX() {
        return X;
    }

    public static void setX(int x) {
        X = x;
    }

    public static int getY() {
        return Y;
    }

    public static void setY(int y) {
        Y = y;
    }

    public static int getWIDTH() {
        return WIDTH;
    }

    public static void setWIDTH(int wIDTH) {
        WIDTH = wIDTH;
    }

    public static int getHEIGHT() {
        return HEIGHT;
    }

    public static void setHEIGHT(int hEIGHT) {
        HEIGHT = hEIGHT;
    }

    public static float getxPercent() {
        return xPercent;
    }

    public static void setxPercent(float xPercent) {
        VerifyCodeUtil.xPercent = xPercent;
    }

    public static float getyPercent() {
        return yPercent;
    }

    public static void setyPercent(float yPercent) {
        VerifyCodeUtil.yPercent = yPercent;
    }


    private BufferedImage base64StringToImage(String base64String) {
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] bytes1 = decoder.decodeBuffer(base64String);
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes1);
            return ImageIO.read(bais);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
