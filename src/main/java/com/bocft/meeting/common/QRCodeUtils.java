package com.bocft.meeting.common;

import java.awt.image.BufferedImage;

import java.awt.Graphics2D;
import java.awt.Image;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Hashtable;
import java.util.UUID;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.springframework.stereotype.Component;

/**
 * 二维码工具类
 * @author Acui
 * @date 2022/7/19
 */
@Component
public class QRCodeUtils {
    /**
     * 字符集：UTF-8
     */
    public static final String UTF8_CHARSET = "UTF-8";
    /**
     * 字符集：UTF-16（包含中文建议使用UTF-8）
     */
    public static final String UTF16_CHARSET = "UTF-16";
    /**
     * 字符集：GB2312（包含中文建议使用UTF-8）
     */
    public static final String BG2313_CHARSET = "GB2312";
    /**
     * 字符集：GBK（包含中文建议使用UTF-8）
     */
    public static final String GBK_CHARSET = "GBK";
    /**
     * 字符集：ISO-8859-1（包含中文建议使用UTF-8）
     */
    public static final String ISO_8859_1_CHARSET = "ISO-8859-1";

    /**
     * 黑色不透明
     */
    public static final int BLACK_COLOR = 0xFF000000;
    /**
     * 白色不透明
     */
    public static final int WHITE_COLOR = 0xFFFFFFFF;
    /**
     * 蓝色不透明（支付宝蓝）
     */
    public static final int BLUE_COLOR = 0xFF1677FF;
    /**
     * 绿色不透明（微信绿）
     */
    public static final int GREEN_COLOR = 0xFF97E95F;

    /**
     * 大尺寸logo
     */
    public static final double LARGE_LOGO = 3.0;
    /**
     * 中等尺寸logo
     */
    public static final double MIDDLE_LOGO = 4.0;
    /**
     * 小尺寸logo
     */
    public static final double SMALL_LOGO = 5.0;
    /**
     * 微小尺寸logo
     */
    public static final double TINY_LOGO = 6.0;

    /**
     * 二维码保存至本地（外边距：1；背景是否透明：否；二维码颜色：黑色；背景颜色：白色；字符集：UTF-8；）
     *
     * @param imageFormat  图片格式（例：PNG；JPG...该参数会转化为文件名后缀）
     * @param fileName     文件名（不包含后缀！为空则通过UUID生成）
     * @param logoFilePath logo文件路径（为空则不添加logo）
     * @param logoSize     logo尺寸（占整个图片的比例分母，不存在logo传任何值不会产生影响）
     * @param savePath     保存目录路径（必填；路径不存在则自动创建）
     * @param content      二维码内容
     * @param size         图片尺寸（单位：像素）
     * @return 文件路径
     */
    public static String saveQRCodeToLocal(String imageFormat, String fileName, String logoFilePath, double logoSize,
                                           String savePath, String content, int size) throws Exception {
        return saveQRCodeToLocal(imageFormat, fileName, logoFilePath, logoSize, savePath, content, 1, size, false,
                BLACK_COLOR, WHITE_COLOR, UTF8_CHARSET);
    }

    /**
     * 二维码保存至本地（外边距：1；背景是否透明：否；二维码颜色：黑色；背景颜色：白色；）
     *
     * @param imageFormat  图片格式（例：PNG；JPG...该参数会转化为文件名后缀）
     * @param fileName     文件名（不包含后缀！为空则通过UUID生成）
     * @param logoFilePath logo文件路径（为空则不添加logo）
     * @param logoSize     logo尺寸（占整个图片的比例分母，不存在logo传任何值不会产生影响）
     * @param savePath     保存目录路径（必填；路径不存在则自动创建）
     * @param content      二维码内容
     * @param size         图片尺寸（单位：像素）
     * @param charset      字符集（包含中文建议使用UTF-8）
     * @return 文件路径
     */
    public static String saveQRCodeToLocal(String imageFormat, String fileName, String logoFilePath, double logoSize,
                                           String savePath, String content, int size, String charset) throws Exception {
        return saveQRCodeToLocal(imageFormat, fileName, logoFilePath, logoSize, savePath, content, 1, size, false,
                BLACK_COLOR, WHITE_COLOR, charset);
    }

    /**
     * 二维码保存至本地
     *
     * @param imageFormat     图片格式（例：PNG；JPG...该参数会转化为文件名后缀）
     * @param fileName        文件名（不包含后缀！为空则通过UUID生成）
     * @param logoFilePath    logo文件路径（为空则不添加logo）
     * @param logoSize        logo尺寸（占整个图片的比例分母，不存在logo传任何值不会产生影响）
     * @param savePath        保存目录路径（必填；路径不存在则自动创建）
     * @param content         二维码内容
     * @param margin          外边距（例：0；1；2；....）
     * @param size            图片尺寸（单位：像素）
     * @param transparent     是否透明（图片格式为"PNG"才可生效）
     * @param codeColor       二维码颜色值（ARGB颜色值，例：0xFF000000；前两位为透明度，后六位为颜色值；）
     * @param backgroundColor 背景颜色值（ARGB颜色值，例：0xFFFFFFFF；前两位为透明度，后六位为颜色值；）
     * @param charset         字符集（包含中文建议使用UTF-8）
     * @return 文件路径
     */
    public static String saveQRCodeToLocal(String imageFormat, String fileName, String logoFilePath, double logoSize,
                                           String savePath, String content, int margin, int size, boolean transparent, int codeColor, int backgroundColor,
                                           String charset) throws Exception {
        // 图片格式去空格转大写
        imageFormat = imageFormat.trim().toUpperCase();

        // 检查文件储存路径是否为空
        if (savePath == null || savePath.isEmpty())
            throw new FileNotFoundException("文件储存路径不得为空");

        // 检查图片格式
        if (!"PNG".equals(imageFormat))
            transparent = false;

        // 生成二维码
        BufferedImage qrCodeImage = generateQRCode(content, margin, size, transparent, codeColor, backgroundColor,
                UTF8_CHARSET);

        // 检查是否存在logo文件路径
        if (logoFilePath != null && !logoFilePath.isEmpty())
            // 二维码添加logo
            addLogo(qrCodeImage, getLogoImage(logoFilePath), logoSize);

        // 创建文件存放目录
        mkdirs(savePath);

        // 检查文件名是否存在
        if (fileName == null || fileName.isEmpty())
            // 生成文件名
            fileName = UUID.randomUUID().toString().replace("-", "").toLowerCase() + "." + imageFormat.toLowerCase();

        // 拼接文件路径
        String filePath = savePath + "/" + fileName + "." + imageFormat.toLowerCase();

        // 写出二维码
        ImageIO.write(qrCodeImage, imageFormat, new File(filePath));

        // 返回文件路径
        return filePath;
    }

    /**
     * 生成二维码（外边距：1；背景是否透明：否；二维码颜色：黑色；背景颜色：白色；字符集：UTF-8；）
     *
     * @param content 内容
     * @param size    图片尺寸
     * @return 二维码图片
     */
    public static BufferedImage generateQRCode(String content, int size) throws Exception {
        return generateQRCode(content, 1, size, false, BLACK_COLOR, WHITE_COLOR, UTF8_CHARSET);
    }

    /**
     * 生成二维码（外边距：1；背景是否透明：否；二维码颜色：黑色；背景颜色：白色；）
     *
     * @param content 内容
     * @param size    图片尺寸
     * @param charset 字符集（包含中文建议使用UTF-8）
     * @return 二维码图片
     */
    public static BufferedImage generateQRCode(String content, int size, String charset) throws Exception {
        return generateQRCode(content, 1, size, false, BLACK_COLOR, WHITE_COLOR, charset);
    }

    /**
     * 生成二维码
     *
     * @param content         内容
     * @param margin          外边距
     * @param size            图片尺寸
     * @param transparent     背景是否透明
     * @param codeColor       二维码颜色值（ARGB颜色值，例：0xFF000000；前两位为透明度，后六位为颜色值；）
     * @param backgroundColor 背景颜色值（ARGB颜色值，例：0xFFFFFFFF；前两位为透明度，后六位为颜色值；）
     * @param charset         字符集（包含中文建议使用UTF-8）
     * @return 二维码图片
     */
    public static BufferedImage generateQRCode(String content, int margin, int size, boolean transparent, int codeColor,
                                               int backgroundColor, String charset) throws Exception {
        // 二维码编码设置
        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H); // 设置误差修正
        hints.put(EncodeHintType.MARGIN, margin); // 设置二维码空白区域大小
        hints.put(EncodeHintType.CHARACTER_SET, charset); // 设置字符集

        // 位矩阵
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, size, size, hints);

        // 创建图片
        BufferedImage image;
        // 判断是否透明
        if (transparent)
            image = new BufferedImage(bitMatrix.getWidth(), bitMatrix.getHeight(), BufferedImage.TYPE_INT_ARGB);
        else
            image = new BufferedImage(bitMatrix.getWidth(), bitMatrix.getHeight(), BufferedImage.TYPE_INT_RGB);

        // 绘制位矩阵图
        paintBitMatrixImage(bitMatrix, image, transparent, codeColor, backgroundColor);

        // 返回二维码
        return image;
    }

    /**
     * 添加logo
     *
     * @param qrCodeImage 二维码图片
     * @param logoImage   logo图片
     * @param logoSize    logo尺寸（占整个图片的比例分母）
     * @return 带logo的二维码
     */
    public static void addLogo(BufferedImage qrCodeImage, BufferedImage logoImage, double logoSize) {
        // 计算logo缩放宽高
        int logoWidth = (int) (qrCodeImage.getWidth() / logoSize);
        int logoHeight = (int) (qrCodeImage.getHeight() / logoSize);

        // 缩放logo
        Image zoomImage = logoImage.getScaledInstance(logoWidth, logoHeight, Image.SCALE_SMOOTH);

        // 创建画笔
        Graphics2D g = qrCodeImage.createGraphics();
        // 将logo绘制到二维码上
        g.drawImage(zoomImage, (qrCodeImage.getWidth() - logoWidth) / 2, (qrCodeImage.getHeight() - logoHeight) / 2,
                logoWidth, logoHeight, null);
        // 释放画笔
        g.dispose();
    }

    /**
     * 解析二维码（字符集：UTF-8）
     *
     * @param filePath 文件路径
     * @return 解析后二维码内容
     */
    public static String parseQRCode(String filePath) throws Exception {
        return parseQRCode(filePath, UTF8_CHARSET);
    }

    /**
     * 解析二维码
     *
     * @param filePath 文件路径
     * @param charset  字符集（包含中文建议使用UTF-8）
     * @return 解析后二维码内容
     */
    public static String parseQRCode(String filePath, String charset) throws Exception {
        // 读取文件
        File qrCodeImageFile = new File(filePath);
        // 检查文件是否存在
        if (!qrCodeImageFile.exists())
            throw new FileNotFoundException();
        // 返回解析后二维码内容
        return parseQRCode(ImageIO.read(qrCodeImageFile), charset);
    }

    /**
     * 解析二维码（字符集：UTF-8）
     *
     * @param qrCodeImage 二维码图片
     * @return 解析后二维码内容
     */
    public static String parseQRCode(BufferedImage qrCodeImage) throws Exception {
        return parseQRCode(qrCodeImage, UTF8_CHARSET);
    }

    /**
     * 解析二维码
     *
     * @param qrCodeImage 二维码图片
     * @param charset     字符集（包含中文建议使用UTF-8）
     * @return 解析后二维码内容
     */
    public static String parseQRCode(BufferedImage qrCodeImage, String charset) throws Exception {
        // 解码设置
        Hashtable<DecodeHintType, Object> hints = new Hashtable<DecodeHintType, Object>();
        hints.put(DecodeHintType.CHARACTER_SET, charset); // 设定字符集

        // 图像解析为亮度源
        BufferedImageLuminanceSource luminanceSource = new BufferedImageLuminanceSource(qrCodeImage);
        // 亮度源解析为二进制位图
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(luminanceSource));

        // 二维码解码
        Result result = new MultiFormatReader().decode(bitmap, hints);

        // 解析后二维码内容
        return result.getText();
    }

    /**
     * 绘制位矩阵图
     *
     * @param bitMatrix       位矩阵
     * @param image           图片
     * @param transparent     是否透明（true：透明 / false：不透明）
     * @param codeColor       二维码颜色值（ARGB颜色值，例：0xFF000000；前两位为透明度，后六位为颜色值；）
     * @param backgroundColor 背景颜色值（ARGB颜色值，例：0xFFFFFFFF；前两位为透明度，后六位为颜色值；）
     */
    private static void paintBitMatrixImage(BitMatrix bitMatrix, BufferedImage image, boolean transparent, int codeColor,
                                            int backgroundColor) {
        // 检查是否透明
        if (transparent) {// 透明
            // 绘制位矩阵图
            for (int x = 0; x < bitMatrix.getWidth(); x++)
                for (int y = 0; y < bitMatrix.getHeight(); y++)
                    if (bitMatrix.get(x, y))
                        image.setRGB(x, y, codeColor);
        } else { // 不透明
            // 绘制位矩阵图
            for (int x = 0; x < bitMatrix.getWidth(); x++)
                for (int y = 0; y < bitMatrix.getHeight(); y++)
                    image.setRGB(x, y, bitMatrix.get(x, y) ? codeColor : backgroundColor);
        }
    }

    /**
     * 获取logo图片
     *
     * @param logoFilePath logo文件路径
     * @return logo图片
     */
    private static BufferedImage getLogoImage(String logoFilePath) throws Exception {
        // 读取logo文件
        File file = new File(logoFilePath);

        // 检查logo文件是否存在
        if (!file.exists())
            throw new FileNotFoundException();

        // 读取logo图片
        BufferedImage logoImage = ImageIO.read(file);

        // 返回logo图片
        return logoImage;
    }

    /**
     * 创建文件夹
     *
     * @param path 路径
     */
    private static void mkdirs(String path) {
        File file = new File(path);
        // 检查目录是否存在
        if (!file.exists() && !file.isDirectory())
            file.mkdirs(); // 不存在则创建目录
    }
}

