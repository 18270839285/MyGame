package api;

/**
 * Created ： LiLin
 * Date ： 2017/10/11
 * Function ：接口列表
 */
public interface API {
    String HEADER = "Content-Type";
    String HEADER_VALUE = "application/x-www-form-urlencoded";
    String BASE_URL_TEST = "http://192.168.0.57:8080/caifu/";
    /**
     * 正式地址
     */
//    String BASE_URL = "https://api.mhbk.monghoo.com/caifu/";
//    String BASE_URL_BORROW = "https://api.mhbk.monghoo.com/borrow/";
    /**
     * 测试地址
     */
    String BASE_URL = "http://test.tooohappy.xyz/api/";
//    String BASE_URL_BORROW = "http://klddev.monghoo.com:10004/borrow/";

    String LOGIN = "sign/login";//登录
    String REGISTER = "user/signUp";//注册
    String VERIFYCODE = "veriCodes/verifyCode";//验证验证码是否正确
    String OCRIDCARD = "face/ocrIdcard";//检测和识别二代身份证
    String SIGNOUT = "user/signOut";//登出
    String USERINFO = "userInfo/Info";//获取用户信息
    String GLOBALINFO = "lend/globalInfo";//获取借款总览信息
    String CREDITINFO = "usersInfo/creditInfo";//获取用户信用额度信息
    String AGREEMENTLIST = "agreement/list";//获取协议列表
    String AGREEMENTDETAIL = "agreement/detail";//获取协议详情
    String LEND = "orders/lend";//用户借款
    String AUTHINFO = "userInfo/authInfo";//获取用户授权信息列表
    String SHIELDNEXT = "shield/next";//获取用户授权信息列表
    String CHECKCARDNO = "user/auth/checkCardNo";//获取用户授权信息列表
    String ORDERSREFUND = "orders/refund";//用户还款
    String PUTUSERADDRESSBOOK = "userAddressBook/putUserAddressBook";//同步用户通讯录
    String CREDITCARDS = "usersInfo/creditCards";//获取用户信用卡列表
    String REGISTER_URL = "https://www.monghoo.com/newapp/reg/index.html";//注册协议
    String AUTH_MSG_URL = "https://www.monghoo.com/newapp/auth/index.html";//信息授权及使用协议
    String TRYAGAIN = "orders/tryAgain";//再试一次
    String ISEDIT = "activity/isEdit";//是否可编辑 0 可编辑,1 不可编辑

    String RESETLOGINPASSWORD = "user/resetLoginPassword";//重置密码
    String GETHOMECAROUSELLIST = "homeCarousel/getHomeCarouselList";//获取引导页
    String VERICODES = "veriCodes/send";//发送验证码
    String VERIFYIDCARD = "face/verifyIdcard";//人脸比对
    String GETSHIELD = "shield/getShield";//获取运营商请求数据
    String ZHIMAPARAMSPOST = "user/zhimaParamsPost";//芝麻信用回调参数上传

    String GETCREDITBANKCARDLIST = "creditBankCards/getCreditBankCardList";//获取银行卡列表
    String CB = "https://www.baidu.com";
    String SHUJUMOHE = "https://open.shujumohe.com/box/yys";//获取芝麻授权地址
    String GETZHIMAAUTHURL = "user/auth/getZhimaAuthUrl";//获取芝麻授权地址
    String GETORDERLIST = "ordersInfo/getOrderList";//订单列表
    String GETORDERDETAIL = "ordersInfo/getOrderDetail";//订单详情
    String APPMESSAGE = "appMessage/getAmsglist";//消息中心
    String BANKCARDS = "userInfo/bankCards";//银行列表
    String THECARDBANKLIST = "https://www.monghoo.com/mhbk/staticfiles/banklist-2017.json";//开卡银行列表
    String FORGETLOGINPASSWORD = "user/forgetLoginPassword";//忘记密码
    String QUERYUSERFACEMESSAGE = "userFaceMessage/query";//获取用户实名信息
    String SIGNUPPAYMMENT = "user/auth/signUpPayment";//开通快捷支付，并且绑定第一张借记卡
    String LIANLIANBINDCARD = "lianlianCallback/bindCard";//连连绑卡
    //    String CERTIFICATIONPAY = "http://centerdev.monghoo.com/center/authPayment/client/notify";//认证支付
    String CERTIFICATIONPAY = "http://center.mhbk.monghoo.com/center/authPayment/client/notify";//认证支付
    String CERTIFICATIONPAYSIGN = "orders/authPayment/sign";//认证支付添加签名
    String CERTIFICATIONPAYJUDGMENT = "insteadPayRecordController/hasInsteadPayRecord";//判断是否可以认证支付
    /**
     * Returns 参数返回列表
     */
    String HTTP_OK = "0";
    int NORECORD = 550;// 没有找到记录
    int DATABASEADDERROR = 600;// 数据库添加错误
    int SETPAYMENTFIRST = 10015;// 请先设定支付密码
    int RESETPAYMENT = 10033;//交易密码被锁定，请重置交易密码
    int ERRORCODE = 10034;// 交易密码错误
    int BANKCODEERROR = 10047;//银行卡号错误
    int NOTCODESENT = 10049;// 没有发送短信验证码
    int REPAYMENTFAILURE = 10048;// 还款失败
    int REPAYMENTFAILURE_REPEAT = 51005;// 不能多次还款
    int REPAYMENTFAILURE_EXCEEDED = 51013;// 已经超过最迟还款时间
}
