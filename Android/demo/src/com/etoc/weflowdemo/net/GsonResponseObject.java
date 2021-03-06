package com.etoc.weflowdemo.net;



public class GsonResponseObject {
	public static final int MEDIA_TYPE_NEWS = 1;
	public static final int MEDIA_TYPE_MOVIE = 2;
	public static final int MEDIA_TYPE_MUSIC = 3;
	public static final int MEDIA_TYPE_EBOOK = 4;
	public static final int MEDIA_TYPE_JOKE = 5;
	public static final int MEDIA_TYPE_TRAVEL = 6;
	public static final int MEDIA_TYPE_TOPICS = 7;

	public static class sendSMSResponse extends commonResponse{
		public boolean isSucceed() {
			return "0000".equals(code) || "2009".equals(code);
		}
	}
	
	public static class commonResponse {
		public String code;// ":0;
		public String message;// 
		
		public boolean isSucceed() {
			return "0000".equals(code);
		}
		
		public boolean isRunningLow() {
			return "2012".equals(code);
		}
	}
	
	public static class loginResponse extends commonResponse {
		public UserInfo user;//
	}
	
	public static class orderLargessResponse extends commonResponse {
		public String blance;
		public String rate;
	}
	
	public static class UserInfo {
		public String userid;
		public String phone;
		public String blance;
		public String rate;
	}
	
	public static class getAccInfoResponse {
		public String status;// ":0;
		public String suitename; //套餐名
		public String innerflow; //套餐内剩余流量
		public String outerflow; //套餐外剩余流量
	}
	
	public static class getAdvInfoResponse {
		public String status;// ":0;
		public AdvInfo[] banners;       //轮播图对应广告
		public AdvInfo[] newadvs;       //最新广告
		public AdvInfo[] recommendadvs; //推荐广告
	}
	
	public static class AdvInfo {
		public String advid;      //广告id
//		public String advtype;    //广告类型
		public String coverurl;   //封面
		public String videourl;   //视频广告url
		public String title;      //标题
		public String time;       //更新时间
		public String content;    //广告内容
		public String instruction;//活动说明
		public String flowaward;  //奖励流量币额度
	}
	
	public static class lotteryResponse extends commonResponse {
		public String phone;
		public String blance;
	}
	
}
