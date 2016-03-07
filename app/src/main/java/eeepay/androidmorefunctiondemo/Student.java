package eeepay.androidmorefunctiondemo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
/**
 * Created by xqf on 2015/11/19.
 */
public class Student implements Serializable {
    private String name;
    private  String old;
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    //---login-----
    private String openStatus;//0关闭1正常3审核中4审核失败
    private String rate;//0.49
    private String devices;//BBPOS_MSR,ITRON_MIC
    private String balance;
    private String settleDaysOption;
    private String realName;
    private String merchantNo;
    private String merchantName;
    private String defaultDevice;
    private String headImg;
    private String singleMaxAmount;
    private String singleMinAmount;
    private String dayMaxAmount;
    private String settleName;
    private String settleNo;
    private String settleBankName;
    private String loginKey;//免登陆判断，密码被改则这个key会变动

    //设备信息

    //
    private String billNo;// 订单号
    //----------add 20150823---------

    //-------余额查询 add 20150906
    private String transAmount;


    //
// -------余额查询------------
    private String amount;
    private String bankName;// 如：中国银行
    private String cardName;// 如：个人普卡
    private String cardNo;// 卡号
    private String cardType;// 卡类型 如：借记卡
    private String image;// 所属行logo
    private String responseCode;// 响应代号
    private String responseMsg;// 提示成功还是失败
    private String track;
    private String type;// 交易类型
    // ---------信用卡还款---------------
    private String creditAmount;// 还款金额
    private String transFee;// 手续费
    private String owner;// 开户者
    private String settleText;
    private String settleAmount;

    // -------交房租-------------

    private String houseAmount;
    // ----------支付宝充值----------
    private String accountName;
    private String accountNo;
    private String alipayAmount;
    // ----------余额查询历史信息-----------
    private String countRecord;
    private String currentPage;
    private String hasNextPage;
    private String pageSize;
    private String totalPage;
    // -----订单详情-----
    private String bizAccount;// 收款账户
    private String acqResponseMsg;// 收单机构返回状态
    private String bizAmount;// 收款金额
    private String bizFee;// 手续费
    private String bizOwner;// 收款人
    private String createTime;// 交易时间
    private String settleDate;// 处理时间
    private String transType;// 交易类型
    private String transStatus;// 交易状态


    private String shortUrl;
    private String referenceNo;
    private String terminalNo;
    private String voucherNo;
    private String batchNo;
    private String authNo;
    private String transDateTime;
    private String acqName;
    private String refNo;
    private String bizBank;
    private String settleBankNo;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOld() {
        return old;
    }

    public void setOld(String old) {
        this.old = old;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getAcqName() {
        return acqName;
    }

    public void setAcqName(String acqName) {
        this.acqName = acqName;
    }

    public String getAcqResponseMsg() {
        return acqResponseMsg;
    }

    public void setAcqResponseMsg(String acqResponseMsg) {
        this.acqResponseMsg = acqResponseMsg;
    }

    public String getAlipayAmount() {
        return alipayAmount;
    }

    public void setAlipayAmount(String alipayAmount) {
        this.alipayAmount = alipayAmount;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAuthNo() {
        return authNo;
    }

    public void setAuthNo(String authNo) {
        this.authNo = authNo;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getBizAccount() {
        return bizAccount;
    }

    public void setBizAccount(String bizAccount) {
        this.bizAccount = bizAccount;
    }

    public String getBizAmount() {
        return bizAmount;
    }

    public void setBizAmount(String bizAmount) {
        this.bizAmount = bizAmount;
    }

    public String getBizBank() {
        return bizBank;
    }

    public void setBizBank(String bizBank) {
        this.bizBank = bizBank;
    }

    public String getBizFee() {
        return bizFee;
    }

    public void setBizFee(String bizFee) {
        this.bizFee = bizFee;
    }

    public String getBizOwner() {
        return bizOwner;
    }

    public void setBizOwner(String bizOwner) {
        this.bizOwner = bizOwner;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }



    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCountRecord() {
        return countRecord;
    }

    public void setCountRecord(String countRecord) {
        this.countRecord = countRecord;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(String creditAmount) {
        this.creditAmount = creditAmount;
    }

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public String getDayMaxAmount() {
        return dayMaxAmount;
    }

    public void setDayMaxAmount(String dayMaxAmount) {
        this.dayMaxAmount = dayMaxAmount;
    }

    public String getDefaultDevice() {
        return defaultDevice;
    }

    public void setDefaultDevice(String defaultDevice) {
        this.defaultDevice = defaultDevice;
    }

    public String getDevices() {
        return devices;
    }

    public void setDevices(String devices) {
        this.devices = devices;
    }

    public String getHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(String hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getHouseAmount() {
        return houseAmount;
    }

    public void setHouseAmount(String houseAmount) {
        this.houseAmount = houseAmount;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLoginKey() {
        return loginKey;
    }

    public void setLoginKey(String loginKey) {
        this.loginKey = loginKey;
    }



    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo;
    }

    public String getOpenStatus() {
        return openStatus;
    }

    public void setOpenStatus(String openStatus) {
        this.openStatus = openStatus;
    }



    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }



    public String getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

    public String getRefNo() {
        return refNo;
    }

    public void setRefNo(String refNo) {
        this.refNo = refNo;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMsg() {
        return responseMsg;
    }

    public void setResponseMsg(String responseMsg) {
        this.responseMsg = responseMsg;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getSettleAmount() {
        return settleAmount;
    }

    public void setSettleAmount(String settleAmount) {
        this.settleAmount = settleAmount;
    }

    public String getSettleBankName() {
        return settleBankName;
    }

    public void setSettleBankName(String settleBankName) {
        this.settleBankName = settleBankName;
    }

    public String getSettleBankNo() {
        return settleBankNo;
    }

    public void setSettleBankNo(String settleBankNo) {
        this.settleBankNo = settleBankNo;
    }

    public String getSettleDate() {
        return settleDate;
    }

    public void setSettleDate(String settleDate) {
        this.settleDate = settleDate;
    }

    public String getSettleDaysOption() {
        return settleDaysOption;
    }

    public void setSettleDaysOption(String settleDaysOption) {
        this.settleDaysOption = settleDaysOption;
    }

    public String getSettleName() {
        return settleName;
    }

    public void setSettleName(String settleName) {
        this.settleName = settleName;
    }

    public String getSettleNo() {
        return settleNo;
    }

    public void setSettleNo(String settleNo) {
        this.settleNo = settleNo;
    }

    public String getSettleText() {
        return settleText;
    }

    public void setSettleText(String settleText) {
        this.settleText = settleText;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public String getSingleMaxAmount() {
        return singleMaxAmount;
    }

    public void setSingleMaxAmount(String singleMaxAmount) {
        this.singleMaxAmount = singleMaxAmount;
    }

    public String getSingleMinAmount() {
        return singleMinAmount;
    }

    public void setSingleMinAmount(String singleMinAmount) {
        this.singleMinAmount = singleMinAmount;
    }

    public String getTerminalNo() {
        return terminalNo;
    }

    public void setTerminalNo(String terminalNo) {
        this.terminalNo = terminalNo;
    }

    public String getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(String totalPage) {
        this.totalPage = totalPage;
    }

    public String getTrack() {
        return track;
    }

    public void setTrack(String track) {
        this.track = track;
    }

    public String getTransAmount() {
        return transAmount;
    }

    public void setTransAmount(String transAmount) {
        this.transAmount = transAmount;
    }

    public String getTransDateTime() {
        return transDateTime;
    }

    public void setTransDateTime(String transDateTime) {
        this.transDateTime = transDateTime;
    }

    public String getTransFee() {
        return transFee;
    }

    public void setTransFee(String transFee) {
        this.transFee = transFee;
    }

    public String getTransStatus() {
        return transStatus;
    }

    public void setTransStatus(String transStatus) {
        this.transStatus = transStatus;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVoucherNo() {
        return voucherNo;
    }

    public void setVoucherNo(String voucherNo) {
        this.voucherNo = voucherNo;
    }

//    /**
//     * 将java对象转换成json字符串
//     *
//     * @param bean
//     * @return
//     */
//    public static String beanToJson(Object bean) {
//
//        JSONArray array = JSONArray.fromObject(bean);
//
//        return json.toString();
//
//    }
}
