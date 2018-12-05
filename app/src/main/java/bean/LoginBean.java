package bean;

/**
 * Description:
 * Data：2018/10/10-14:55
 * Author: lin
 */
public class LoginBean {
    private UserBean user;
    private OrdersBean orders;

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public OrdersBean getOrders() {
        return orders;
    }

    public void setOrders(OrdersBean orders) {
        this.orders = orders;
    }

    public static class UserBean {
        /**
         * id : 8
         * nickname : 17600203815
         * avatar_id : 0
         * telephone : 17600203815
         * sex : M
         * token : WRyWfIUCr9ZB6I5oaCdDw67f9wKXweDZ6ZBxiRS6kKojO7xW
         * auth_time : 0
         * info_id : 0
         * photo_id : 0
         * avatar_url :
         */
        private int id;
        private String nickname;
        private int avatar_id;
        private String telephone;
        private String sex;
        private String token;
        private String auth_time;
        private int info_id;
        private int photo_id;
        private String avatar_url;
        private String realname;
        private String id_card;
        private String idcard_face_id;
        private String handheld_id;
        private String idcard_back_id;
        private String face_verify_id;
        private String re_id_card;
        private String re_realname;
        private int yys_status;
        private int kind;
        private int step;
        private String bank_card;
        private String bank_id;

        public String getBank_id() {
            return bank_id;
        }

        public void setBank_id(String bank_id) {
            this.bank_id = bank_id;
        }

        public String getBank_card() {
            return bank_card;
        }

        public void setBank_card(String bank_card) {
            this.bank_card = bank_card;
        }

        public int getStep() {
            return step;
        }

        public void setStep(int step) {
            this.step = step;
        }

        private int auth_status;//实人绑卡 0未认证/1认证通过
        private int baseinfo_status;//基础信息认证 99未认证/0未审核(审核中)/1审核通过/2审核失败/3拉入黑名单
        private int handheld_status;//手持身份证 0未认证/1认证通过/2认证失败/3审核中
        private int face_status;//	人脸认证 0未提交/1审核通过/2审核不通过
        private int bank_status;//银行卡 0未提交/1失败2成功
        private int farther_status;//补充资料 0未提交/1通过/2失败/3未审核(审核中)

        public int getKind() {
            return kind;
        }

        public void setKind(int kind) {
            this.kind = kind;
        }

        public String getRe_id_card() {
            return re_id_card;
        }

        public void setRe_id_card(String re_id_card) {
            this.re_id_card = re_id_card;
        }

        public String getRe_realname() {
            return re_realname;
        }

        public void setRe_realname(String re_realname) {
            this.re_realname = re_realname;
        }

        public String getId_card() {
            return id_card;
        }

        public void setId_card(String id_card) {
            this.id_card = id_card;
        }

        public int getAuth_status() {
            return auth_status;
        }

        public void setAuth_status(int auth_status) {
            this.auth_status = auth_status;
        }

        public int getBaseinfo_status() {
            return baseinfo_status;
        }

        public void setBaseinfo_status(int baseinfo_status) {
            this.baseinfo_status = baseinfo_status;
        }

        public int getHandheld_status() {
            return handheld_status;
        }

        public void setHandheld_status(int handheld_status) {
            this.handheld_status = handheld_status;
        }

        public int getFace_status() {
            return face_status;
        }

        public void setFace_status(int face_status) {
            this.face_status = face_status;
        }

        public int getBank_status() {
            return bank_status;
        }

        public void setBank_status(int bank_status) {
            this.bank_status = bank_status;
        }

        public int getFarther_status() {
            return farther_status;
        }

        public void setFarther_status(int farther_status) {
            this.farther_status = farther_status;
        }

        public String getIdcard_face_id() {
            return idcard_face_id;
        }

        public void setIdcard_face_id(String idcard_face_id) {
            this.idcard_face_id = idcard_face_id;
        }

        public String getHandheld_id() {
            return handheld_id;
        }

        public void setHandheld_id(String handheld_id) {
            this.handheld_id = handheld_id;
        }

        public String getIdcard_back_id() {
            return idcard_back_id;
        }

        public void setIdcard_back_id(String idcard_back_id) {
            this.idcard_back_id = idcard_back_id;
        }

        public String getFace_verify_id() {
            return face_verify_id;
        }

        public void setFace_verify_id(String face_verify_id) {
            this.face_verify_id = face_verify_id;
        }

        public int getYys_status() {
            return yys_status;
        }

        public void setYys_status(int yys_status) {
            this.yys_status = yys_status;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public int getAvatar_id() {
            return avatar_id;
        }

        public void setAvatar_id(int avatar_id) {
            this.avatar_id = avatar_id;
        }

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getAuth_time() {
            return auth_time;
        }

        public void setAuth_time(String auth_time) {
            this.auth_time = auth_time;
        }

        public int getInfo_id() {
            return info_id;
        }

        public void setInfo_id(int info_id) {
            this.info_id = info_id;
        }

        public int getPhoto_id() {
            return photo_id;
        }

        public void setPhoto_id(int photo_id) {
            this.photo_id = photo_id;
        }

        public String getAvatar_url() {
            return avatar_url;
        }

        public void setAvatar_url(String avatar_url) {
            this.avatar_url = avatar_url;
        }
    }

    public static class OrdersBean {
        /**
         * pay : 0
         * ship : 0
         * receive : 0
         */

        private int pay;
        private int ship;
        private int receive;

        public int getPay() {
            return pay;
        }

        public void setPay(int pay) {
            this.pay = pay;
        }

        public int getShip() {
            return ship;
        }

        public void setShip(int ship) {
            this.ship = ship;
        }

        public int getReceive() {
            return receive;
        }

        public void setReceive(int receive) {
            this.receive = receive;
        }
    }
}
