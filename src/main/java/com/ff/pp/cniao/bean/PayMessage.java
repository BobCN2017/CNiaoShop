package com.ff.pp.cniao.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by PP on 2017/4/18.
 */

public class PayMessage extends BaseMessage{

    /**
     * data : {"orderNum":"20170418223636000100","charge":{"id":"ch_rnHWn19q9OG8KSezPC9CqDW9","object":"charge","created":1492526196,"livemode":false,"paid":false,"refunded":false,"app":"app_0yvTSGSOOSO44u18","channel":"alipay","orderNo":"20170418223636000100","clientIp":"125.123.199.15","amount":13166,"amountSettle":13166,"currency":"cny","subject":"您正在菜鸟商城购物","body":"订单Body","timeExpire":1492612596,"refunds":{"object":"list","url":"/v1/charges/ch_rnHWn19q9OG8KSezPC9CqDW9/refunds","hasMore":false,"data":[]},"amountRefunded":0,"metadata":{},"credential":{"object":"credential","alipay":{"orderInfo":"_input_charset=\"utf-8\"&body=\"订单Body\"&it_b_pay=\"2017-04-19 22:36:36\"&notify_url=\"https%3A%2F%2Fnotify.pingxx.com%2Fnotify%2Fcharges%2Fch_rnHWn19q9OG8KSezPC9CqDW9\"&out_trade_no=\"20170418223636000100\"&partner=\"2008140609570431\"&payment_type=\"1\"&seller_id=\"2008140609570431\"&service=\"mobile.securitypay.pay\"&subject=\"您正在菜鸟商城购物\"&total_fee=\"131.66\"&sign=\"VG1iTGE5dTE0NHVMQ2Vmam45SHVYOUND\"&sign_type=\"RSA\""}},"extra":{}}}
     * status : 1
     * message : success
     */

    private OrderData data;

    public OrderData getData() {
        return data;
    }

    public void setData(OrderData data) {
        this.data = data;
    }

      public static class OrderData implements Serializable{
        /**
         * orderNum : 20170418223636000100
         * charge : {"id":"ch_rnHWn19q9OG8KSezPC9CqDW9","object":"charge","created":1492526196,"livemode":false,"paid":false,"refunded":false,"app":"app_0yvTSGSOOSO44u18","channel":"alipay","orderNo":"20170418223636000100","clientIp":"125.123.199.15","amount":13166,"amountSettle":13166,"currency":"cny","subject":"您正在菜鸟商城购物","body":"订单Body","timeExpire":1492612596,"refunds":{"object":"list","url":"/v1/charges/ch_rnHWn19q9OG8KSezPC9CqDW9/refunds","hasMore":false,"data":[]},"amountRefunded":0,"metadata":{},"credential":{"object":"credential","alipay":{"orderInfo":"_input_charset=\"utf-8\"&body=\"订单Body\"&it_b_pay=\"2017-04-19 22:36:36\"&notify_url=\"https%3A%2F%2Fnotify.pingxx.com%2Fnotify%2Fcharges%2Fch_rnHWn19q9OG8KSezPC9CqDW9\"&out_trade_no=\"20170418223636000100\"&partner=\"2008140609570431\"&payment_type=\"1\"&seller_id=\"2008140609570431\"&service=\"mobile.securitypay.pay\"&subject=\"您正在菜鸟商城购物\"&total_fee=\"131.66\"&sign=\"VG1iTGE5dTE0NHVMQ2Vmam45SHVYOUND\"&sign_type=\"RSA\""}},"extra":{}}
         */

        private String orderNum;
        private Charge charge;

        public String getOrderNum() {
            return orderNum;
        }

        public void setOrderNum(String orderNum) {
            this.orderNum = orderNum;
        }

        public Charge getCharge() {
            return charge;
        }

        public void setCharge(Charge charge) {
            this.charge = charge;
        }

        public static class Charge implements Serializable{
            /**
             * id : ch_rnHWn19q9OG8KSezPC9CqDW9
             * object : charge
             * created : 1492526196
             * livemode : false
             * paid : false
             * refunded : false
             * app : app_0yvTSGSOOSO44u18
             * channel : alipay
             * orderNo : 20170418223636000100
             * clientIp : 125.123.199.15
             * amount : 13166
             * amountSettle : 13166
             * currency : cny
             * subject : 您正在菜鸟商城购物
             * body : 订单Body
             * timeExpire : 1492612596
             * refunds : {"object":"list","url":"/v1/charges/ch_rnHWn19q9OG8KSezPC9CqDW9/refunds","hasMore":false,"data":[]}
             * amountRefunded : 0
             * metadata : {}
             * credential : {"object":"credential","alipay":{"orderInfo":"_input_charset=\"utf-8\"&body=\"订单Body\"&it_b_pay=\"2017-04-19 22:36:36\"&notify_url=\"https%3A%2F%2Fnotify.pingxx.com%2Fnotify%2Fcharges%2Fch_rnHWn19q9OG8KSezPC9CqDW9\"&out_trade_no=\"20170418223636000100\"&partner=\"2008140609570431\"&payment_type=\"1\"&seller_id=\"2008140609570431\"&service=\"mobile.securitypay.pay\"&subject=\"您正在菜鸟商城购物\"&total_fee=\"131.66\"&sign=\"VG1iTGE5dTE0NHVMQ2Vmam45SHVYOUND\"&sign_type=\"RSA\""}}
             * extra : {}
             */

            private String id;
            private String object;
            private int created;
            private boolean livemode;
            private boolean paid;
            private boolean refunded;
            private String app;
            private String channel;
            private String orderNo;
            private String clientIp;
            private int amount;
            private int amountSettle;
            private String currency;
            private String subject;
            private String body;
            private int timeExpire;
            private RefundsBean refunds;
            private int amountRefunded;
            private MetadataBean metadata;
            private CredentialBean credential;
            private ExtraBean extra;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getObject() {
                return object;
            }

            public void setObject(String object) {
                this.object = object;
            }

            public int getCreated() {
                return created;
            }

            public void setCreated(int created) {
                this.created = created;
            }

            public boolean isLivemode() {
                return livemode;
            }

            public void setLivemode(boolean livemode) {
                this.livemode = livemode;
            }

            public boolean isPaid() {
                return paid;
            }

            public void setPaid(boolean paid) {
                this.paid = paid;
            }

            public boolean isRefunded() {
                return refunded;
            }

            public void setRefunded(boolean refunded) {
                this.refunded = refunded;
            }

            public String getApp() {
                return app;
            }

            public void setApp(String app) {
                this.app = app;
            }

            public String getChannel() {
                return channel;
            }

            public void setChannel(String channel) {
                this.channel = channel;
            }

            public String getOrderNo() {
                return orderNo;
            }

            public void setOrderNo(String orderNo) {
                this.orderNo = orderNo;
            }

            public String getClientIp() {
                return clientIp;
            }

            public void setClientIp(String clientIp) {
                this.clientIp = clientIp;
            }

            public int getAmount() {
                return amount;
            }

            public void setAmount(int amount) {
                this.amount = amount;
            }

            public int getAmountSettle() {
                return amountSettle;
            }

            public void setAmountSettle(int amountSettle) {
                this.amountSettle = amountSettle;
            }

            public String getCurrency() {
                return currency;
            }

            public void setCurrency(String currency) {
                this.currency = currency;
            }

            public String getSubject() {
                return subject;
            }

            public void setSubject(String subject) {
                this.subject = subject;
            }

            public String getBody() {
                return body;
            }

            public void setBody(String body) {
                this.body = body;
            }

            public int getTimeExpire() {
                return timeExpire;
            }

            public void setTimeExpire(int timeExpire) {
                this.timeExpire = timeExpire;
            }

            public RefundsBean getRefunds() {
                return refunds;
            }

            public void setRefunds(RefundsBean refunds) {
                this.refunds = refunds;
            }

            public int getAmountRefunded() {
                return amountRefunded;
            }

            public void setAmountRefunded(int amountRefunded) {
                this.amountRefunded = amountRefunded;
            }

            public MetadataBean getMetadata() {
                return metadata;
            }

            public void setMetadata(MetadataBean metadata) {
                this.metadata = metadata;
            }

            public CredentialBean getCredential() {
                return credential;
            }

            public void setCredential(CredentialBean credential) {
                this.credential = credential;
            }

            public ExtraBean getExtra() {
                return extra;
            }

            public void setExtra(ExtraBean extra) {
                this.extra = extra;
            }

            public static class RefundsBean implements Serializable{
                /**
                 * object : list
                 * url : /v1/charges/ch_rnHWn19q9OG8KSezPC9CqDW9/refunds
                 * hasMore : false
                 * data : []
                 */

                private String object;
                private String url;
                private boolean hasMore;
                private List<?> data;

                public String getObject() {
                    return object;
                }

                public void setObject(String object) {
                    this.object = object;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public boolean isHasMore() {
                    return hasMore;
                }

                public void setHasMore(boolean hasMore) {
                    this.hasMore = hasMore;
                }

                public List<?> getData() {
                    return data;
                }

                public void setData(List<?> data) {
                    this.data = data;
                }
            }

            public static class MetadataBean implements Serializable{
            }

            public static class CredentialBean implements Serializable{
                /**
                 * object : credential
                 * alipay : {"orderInfo":"_input_charset=\"utf-8\"&body=\"订单Body\"&it_b_pay=\"2017-04-19 22:36:36\"&notify_url=\"https%3A%2F%2Fnotify.pingxx.com%2Fnotify%2Fcharges%2Fch_rnHWn19q9OG8KSezPC9CqDW9\"&out_trade_no=\"20170418223636000100\"&partner=\"2008140609570431\"&payment_type=\"1\"&seller_id=\"2008140609570431\"&service=\"mobile.securitypay.pay\"&subject=\"您正在菜鸟商城购物\"&total_fee=\"131.66\"&sign=\"VG1iTGE5dTE0NHVMQ2Vmam45SHVYOUND\"&sign_type=\"RSA\""}
                 */

                private String object;
                private AlipayBean alipay;

                public String getObject() {
                    return object;
                }

                public void setObject(String object) {
                    this.object = object;
                }

                public AlipayBean getAlipay() {
                    return alipay;
                }

                public void setAlipay(AlipayBean alipay) {
                    this.alipay = alipay;
                }

                public static class AlipayBean implements Serializable{
                    /**
                     * orderInfo : _input_charset="utf-8"&body="订单Body"&it_b_pay="2017-04-19 22:36:36"&notify_url="https%3A%2F%2Fnotify.pingxx.com%2Fnotify%2Fcharges%2Fch_rnHWn19q9OG8KSezPC9CqDW9"&out_trade_no="20170418223636000100"&partner="2008140609570431"&payment_type="1"&seller_id="2008140609570431"&service="mobile.securitypay.pay"&subject="您正在菜鸟商城购物"&total_fee="131.66"&sign="VG1iTGE5dTE0NHVMQ2Vmam45SHVYOUND"&sign_type="RSA"
                     */

                    private String orderInfo;

                    public String getOrderInfo() {
                        return orderInfo;
                    }

                    public void setOrderInfo(String orderInfo) {
                        this.orderInfo = orderInfo;
                    }
                }
            }

            public static class ExtraBean implements Serializable{
            }
        }
    }
}
