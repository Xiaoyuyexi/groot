package com.web.utils.image;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Stroke;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.web.bo.PosOrder;


public class ImageUtil {
	
	/*public static void main(String[] args){
		PosOrder posOrder = new PosOrder();
		posOrder.setBankName("招商银行");
		posOrder.setCardNo("518710******8303");
		posOrder.setInvoicePrintName("兴隆大冶");
		posOrder.setOrderNo("20130610724808");
		posOrder.setOrderType("撤销");
		posOrder.setSignImage(new File("F:/qianming.jpg"));
		posOrder.setTrxAmt("-11.01");
		posOrder.setTrxTime("2013-06-10 13:43:04");
		posOrder.setMerchantNo("11111100000");
		posOrder.setTerminalNo("100000000");
		posOrder.setAuthorizationCode("12346");
		try {
			ImageUtil.createImage(posOrder);
		} catch (IOException e) {
			e.printStackTrace();
		}
//		ratoate("E:\\test\\Hydrangeas.JPG","E:\\test\\Hydrangeas_.JPG",-90);
	}*/
	
	
	public static void ratoate(String oldPath,String newPath,double degree){
		try {
			//ImageUtil.merge( new File("e:/2.jpg"),new File("e:/1.jpg"));
			BufferedImage oldImage = ImageIO.read(new FileInputStream(oldPath));
			int width=oldImage.getHeight(),height=oldImage.getWidth();
			if(degree%180==0){
				width=oldImage.getWidth();
				height=oldImage.getHeight();
			}
			BufferedImage newImage = new BufferedImage(width,height, oldImage.getType());
			Graphics2D graphics = (Graphics2D) newImage.getGraphics();
			graphics.rotate(Math.toRadians(degree), newImage.getWidth() / 2, newImage.getHeight() / 2);
			graphics.translate((newImage.getWidth() - oldImage.getWidth()) / 2, (newImage.getHeight() - oldImage.getHeight()) / 2);
			graphics.drawImage(oldImage, 0, 0, oldImage.getWidth(), oldImage.getHeight(), null);
			ImageIO.write(newImage, "JPG", new FileOutputStream(newPath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static File createImage(PosOrder posOrder) throws IOException{
		if(posOrder == null){
			return null;
		}
		posOrder = checkPosOrder(posOrder);
		
		String round = com.web.utils.lang.StringUtils.randomUUID();
		String fileName = "temp"+File.separator+"tradeSettle"+round+".jpg";
		//测试用
//		String fileName = "f:"+File.separator+"tradeSettle.jpg";
		
		File invoice = createInvoice(posOrder, fileName);
		
		File sign = posOrder.getSignImage();
		if(sign == null){
			return invoice;
		}
		return merge(invoice, sign);
	}
	
	private static PosOrder checkPosOrder(PosOrder posOrder){
		if(posOrder.getAuthorizationCode() == null){
			posOrder.setAuthorizationCode("");
		}
		if(posOrder.getTrxAmt() == null){
			posOrder.setTrxAmt("");
		}
		if(posOrder.getTrxTime() == null){
			posOrder.setTrxTime("");
		}
		if(posOrder.getInvoicePrintName() == null){
			posOrder.setInvoicePrintName("");
		}
		if(posOrder.getBankName() == null){
			posOrder.setBankName("");
		}
		if(posOrder.getCardNo() == null){
			posOrder.setCardNo("");
		}
		if(posOrder.getOrderType() == null){
			posOrder.setOrderType("");
		}
		if(posOrder.getOrderNo() == null){
			posOrder.setOrderNo("");
		}
		if(posOrder.getMerchantNo() == null){
			posOrder.setMerchantNo("");
		}
		if(posOrder.getTerminalNo() == null){
			posOrder.setTerminalNo("");
		}
		
		return posOrder;
	}
	
	
//	1．创建bufferedimage对象，该对象存在内存中，负责保存绘制的图像； 
//	2．创建graphics2d对象，该对象负责绘制所需的图像； 
//	3．当绘制完成后，调用com.sun.image.codec.jpeg包的jpeg编码器对其编码； 
//	4．最后将编码后的数据输出至httpresponse即可
	private static File createInvoice(PosOrder posOrder, String fileName) throws IOException{
//		String round = com.hkrtframework.utils.lang.StringUtils.randomUUID();
		File temp = new File(fileName);   
        int width = 318;   
        int height = 452;   
        int marginLeft = 29;
        int marginRight = 31;
        double x;
        Rectangle2D bounds;
        
        //Y轴坐标按照图片,由上到下排序
        int receipt_y = 26;
        int trxAmt_y = 80;
        int trxTime_y = 105;
        int dottedLine1_y = 116;
        int merchantNo_y = 135;
        int merchantName_y  = merchantNo_y   + 27;//162
        int terminalNo_y    = merchantName_y + 27;//189
        int bankName_y      = terminalNo_y   + 27;//216
        int cardNo_y        = bankName_y     + 27;//243
        int orderType_y     = cardNo_y       + 27;//270
        int orderNo_y       = orderType_y    + 27;//297
        int authCode_y      = orderNo_y      + 27;//324
        int dottedLine2_y   = authCode_y     + 11;
        int sign_y          = dottedLine2_y  + 21;
        int statement_y     = sign_y         + 88;
        
        String receipt = "交易收据";
        String trxAmt = "￥"+posOrder.getTrxAmt();//￥1.00
        String trxTime = posOrder.getTrxTime();//2013-03-03 15:45:45
        String merchantName_1 = "交易商户:";
        String merchantName_2 = posOrder.getInvoicePrintName();//"兴隆大冶";   
        String bankName_1 = "发卡行:";   
        String bankName_2 = posOrder.getBankName();//"招商银行";   
        String cardNo_1 = "卡号:";
        String cardNo_2 = posOrder.getCardNo();//"518710******8303";   
        String orderType_1 = "交易类型:";   
        String orderType_2 = posOrder.getOrderType();//"撤销";   
        String orderNo_1 = "交易编号:";   
        String orderNo_2 = posOrder.getOrderNo();//"1000000000";   
        String authCode_1 = "授权码:";   
        String authCode_2 = posOrder.getAuthorizationCode();//"123456";   
        String sign = "持卡人签名:";   
        String merchantNo_1 = "商户编号:";
        String merchantNo_2 = posOrder.getMerchantNo();
        String terminalNo_1 = "设备终端号:";
        String terminalNo_2 = posOrder.getTerminalNo();
        String statement = "本人确认以上交易，同意将其记入本人帐户";
        
        Font word20 = new Font("宋体",Font.PLAIN,18);
        Font word16 = new Font("宋体",Font.PLAIN,16);
        Font word12 = new Font("宋体",Font.PLAIN,12);
        Font num44 = new Font("verdana",Font.PLAIN,44);//verdana
        Font num17 = new Font("幼圆 常规",Font.PLAIN,17);
        Font num15 = new Font("幼圆 常规",Font.PLAIN,15);
        
        
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);   
        Graphics2D g2 = (Graphics2D)bi.getGraphics();   
        g2.setBackground(Color.WHITE);   
        g2.clearRect(0, 0, width, height); 
        FontRenderContext context = g2.getFontRenderContext();   
//        Stroke st = g2.getStroke();     
//        g2.setStroke(st);    
        
        //虚线
        g2.setColor(new Color(137,137,137));
        Stroke bs;     
        bs = new BasicStroke(1, BasicStroke.CAP_ROUND , BasicStroke.CAP_ROUND , 10, new float[]{2, 4}, 0);     
        g2.setStroke(bs);     
        g2.drawLine(0, dottedLine1_y, width, dottedLine1_y);     
        g2.drawLine(0, dottedLine2_y, width, dottedLine2_y);     
        
        
        g2.setColor(new Color(137,137,137));
        g2.fillRect(0, 0, width, 39);
        
        //交易收据
        g2.setColor(Color.WHITE);
        bounds = word20.getStringBounds(receipt, context);
        x = ((width - bounds.getWidth()) / 2);
        g2.setFont(word20);
        g2.drawString(receipt,(int)x,receipt_y);
        
        //交易金额
        g2.setPaint(new Color(68,99,144));
        bounds = num44.getStringBounds(trxAmt, context);
        x = ((width - bounds.getWidth()) / 2);
        g2.setFont(num44);
        g2.drawString(trxAmt,(int)x,trxAmt_y);
        
        //交易时间
        g2.setPaint(new Color(111,112,157));
        bounds = num15.getStringBounds(trxTime, context);
        x = ((width - bounds.getWidth()) / 2);
        g2.setFont(num15);
        g2.drawString(trxTime,(int)x,trxTime_y);
        
        //商户编号
        g2.setFont(word16);
        g2.drawString(merchantNo_1,marginLeft,merchantNo_y);
        
        bounds = num17.getStringBounds(merchantNo_2, context);
        x = (width - bounds.getWidth() - marginRight);
        g2.setFont(num17);
        g2.drawString(merchantNo_2,(int)x,merchantNo_y);
        
        
        //商户名称
        g2.setFont(word16);
        g2.drawString(merchantName_1,marginLeft,merchantName_y);
        
        bounds = word16.getStringBounds(merchantName_2, context);
        x = (width - bounds.getWidth() - marginRight);
        g2.setFont(word16);
        g2.drawString(merchantName_2,(int)x,merchantName_y);
        
        
        //终端号
        g2.setFont(word16);
        g2.drawString(terminalNo_1,marginLeft,terminalNo_y);
        
        bounds = word16.getStringBounds(terminalNo_2, context);
        x = (width - bounds.getWidth() - marginRight);
        g2.setFont(word16);
        g2.drawString(terminalNo_2,(int)x,terminalNo_y);
        
        
        //开户行
        g2.setFont(word16);
        g2.drawString(bankName_1,marginLeft,bankName_y);
        
        bounds = word16.getStringBounds(bankName_2, context);
        x = (width - bounds.getWidth() - marginRight);
        g2.setFont(word16);
        g2.drawString(bankName_2,(int)x,bankName_y);
        
        
        //卡号
        g2.setFont(word16);
        g2.drawString(cardNo_1,marginLeft,cardNo_y);
        
        bounds = num17.getStringBounds(cardNo_2, context);
        x = (width - bounds.getWidth() - marginRight);
        g2.setFont(num17);
        g2.drawString(cardNo_2,(int)x,cardNo_y);
        
        
        //交易类型
        g2.setFont(word16);
        g2.drawString(orderType_1,marginLeft,orderType_y);
        
        bounds = word16.getStringBounds(orderType_2, context);
        x = (width - bounds.getWidth() - marginRight);
        g2.setFont(word16);
        g2.drawString(orderType_2,(int)x,orderType_y);
        
        //交易编号
        g2.setFont(word16);
        g2.drawString(orderNo_1,marginLeft,orderNo_y);
        
        bounds = num17.getStringBounds(orderNo_2, context);
        x = (width - bounds.getWidth() - marginRight);
        g2.setFont(num17);
        g2.drawString(orderNo_2,(int)x,orderNo_y);
        
        //授权码
        g2.setFont(word16);
        g2.drawString(authCode_1,marginLeft,authCode_y);
        
        bounds = num17.getStringBounds(authCode_2, context);
        x = (width - bounds.getWidth() - marginRight);
        g2.setFont(num17);
        g2.drawString(authCode_2,(int)x,authCode_y);
        
        
        //签名
        g2.setFont(word12);
        g2.drawString(sign,marginLeft,sign_y);
        
        //声明
        g2.setFont(word12);
        g2.drawString(statement,marginLeft,statement_y);
        
        ImageIO.write(bi, "jpg", temp);   
      
        return temp;
    }   
	
	public static File merge (File invoice, File sign) throws IOException{
		if(invoice == null || sign == null){
			return null;
		}
		return merge(invoice,sign,80,360,invoice.getName());
	}
	
	public static File merge (File invoice, File sign,int x,int y,String fileName) throws IOException{
		BufferedImage bi1 = null;
		BufferedImage bi2 = null;
		bi1 = javax.imageio.ImageIO.read(invoice);
		bi2 = javax.imageio.ImageIO.read(sign);
		
		int width = bi2.getWidth(); 
		int height = bi2.getHeight();
		
		double scale = width/height;
		Double imageWidth = (int) 70 * scale;
		
		Graphics g = bi1.getGraphics();
		g.drawImage(bi2.getScaledInstance(imageWidth.intValue(), 70,  Image.SCALE_SMOOTH), x, y, null);

		File posOrder = new File("temp"+File.separator+fileName);
		//生产用，输出到文件流
		FileOutputStream out = new FileOutputStream(posOrder); 
		
		//测试用，输出到文件系统
//		FileOutputStream out = new FileOutputStream("f:/"+fileName);
		
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
		encoder.encode(bi1);
		out.close();
		
		return posOrder;
	}
}
