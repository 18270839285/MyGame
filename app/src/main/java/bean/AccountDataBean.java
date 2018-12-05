package bean;

/**
 * Created by admin on 2017/12/25.
 */

public class AccountDataBean {

    /**
     * register_info : {"nickname":"18758587574","telephone":"18758587574","sex":"M","avatar_url":"http://ilivehome-image-bucket.oss-cn-qingdao.aliyuncs.com/avatar/2017-12-19/151367073957785799.jpg","register_role":"1","signature":"","invite_code":"","wxinfo_id":"0"}
     */
//        已沟通数
    int is_linkup_total;

    int collect_total;

    int send_position_total;

    int anchor_send_resume;
    int is_read;
    int msg_noread_total;

    int residue_num;

    int face_status;
    int license_status;
    String anchor_collect_total;
    //简历总数
    String resume_total;
    //公司信息是否完善 1 完善 0不完善
    int company_is_perfect;
    //是否vip 1是 0否
    int is_vip;
    //    vip到期时间
    String expire_time;
    //    公司简称
    String company_short_name;

    String bd;
    String licence_num;
    String licence_day_num;
    String vip_day_num;
    String recruit_num;
    String hf_bd;

    public String getBd() {
        return bd;
    }

    public void setBd(String bd) {
        this.bd = bd;
    }

    public String getLicence_num() {
        return licence_num;
    }

    public void setLicence_num(String licence_num) {
        this.licence_num = licence_num;
    }

    public String getLicence_day_num() {
        return licence_day_num;
    }

    public void setLicence_day_num(String licence_day_num) {
        this.licence_day_num = licence_day_num;
    }

    public String getVip_day_num() {
        return vip_day_num;
    }

    public void setVip_day_num(String vip_day_num) {
        this.vip_day_num = vip_day_num;
    }

    public String getRecruit_num() {
        return recruit_num;
    }

    public void setRecruit_num(String recruit_num) {
        this.recruit_num = recruit_num;
    }

    public String getHf_bd() {
        return hf_bd;
    }

    public void setHf_bd(String hf_bd) {
        this.hf_bd = hf_bd;
    }

    public int getCompany_is_perfect() {
        return company_is_perfect;
    }

    public void setCompany_is_perfect(int company_is_perfect) {
        this.company_is_perfect = company_is_perfect;
    }

    public int getIs_vip() {
        return is_vip;
    }

    public void setIs_vip(int is_vip) {
        this.is_vip = is_vip;
    }

    public String getExpire_time() {
        return expire_time;
    }

    public void setExpire_time(String expire_time) {
        this.expire_time = expire_time;
    }

    public String getCompany_short_name() {
        return company_short_name;
    }

    public void setCompany_short_name(String company_short_name) {
        this.company_short_name = company_short_name;
    }

    public String getResume_total() {
        return resume_total;
    }

    public void setResume_total(String resume_total) {
        this.resume_total = resume_total;
    }

    public String getAnchor_collect_total() {
        return anchor_collect_total;
    }

    public void setAnchor_collect_total(String anchor_collect_total) {
        this.anchor_collect_total = anchor_collect_total;
    }

    public int getFace_status() {
        return face_status;
    }

    public void setFace_status(int face_status) {
        this.face_status = face_status;
    }

    public int getLicense_status() {
        return license_status;
    }

    public void setLicense_status(int license_status) {
        this.license_status = license_status;
    }

    String img_url;

    public int getResidue_num() {
        return residue_num;
    }

    public void setResidue_num(int residue_num) {
        this.residue_num = residue_num;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public int getMsg_noread_total() {
        return msg_noread_total;
    }

    public void setMsg_noread_total(int msg_noread_total) {
        this.msg_noread_total = msg_noread_total;
    }

    public int getAnchor_send_resume() {
        return anchor_send_resume;
    }

    public void setAnchor_send_resume(int anchor_send_resume) {
        this.anchor_send_resume = anchor_send_resume;
    }

    public int getIs_read() {
        return is_read;
    }

    public void setIs_read(int is_read) {
        this.is_read = is_read;
    }

    private RegisterInfoBean register_info;

    public int getIs_linkup_total() {
        return is_linkup_total;
    }

    public void setIs_linkup_total(int is_linkup_total) {
        this.is_linkup_total = is_linkup_total;
    }

    public int getCollect_total() {
        return collect_total;
    }

    public void setCollect_total(int collect_total) {
        this.collect_total = collect_total;
    }

    public RegisterInfoBean getRegister_info() {
        return register_info;
    }

    public void setRegister_info(RegisterInfoBean register_info) {
        this.register_info = register_info;
    }

    public int getSend_position_total() {
        return send_position_total;
    }

    public void setSend_position_total(int send_position_total) {
        this.send_position_total = send_position_total;
    }

    public static class RegisterInfoBean {
        /**
         * nickname : 18758587574
         * telephone : 18758587574
         * sex : M
         * avatar_url : http://ilivehome-image-bucket.oss-cn-qingdao.aliyuncs.com/avatar/2017-12-19/151367073957785799.jpg
         * register_role : 1
         * signature :
         * invite_code :
         * wxinfo_id : 0
         */

        private String nickname;
        private String telephone;
        private String sex;
        private String avatar_url;
        private String register_role;
        private String signature;
        private String invite_code;
        private String wxinfo_id;
        private String member_time;
        private String resume_id;
        private String info_url;

        public String getResume_id() {
            return resume_id;
        }

        public void setResume_id(String resume_id) {
            this.resume_id = resume_id;
        }

        public String getInfo_url() {
            return info_url;
        }

        public void setInfo_url(String info_url) {
            this.info_url = info_url;
        }

        public String getMember_time() {
            return member_time;
        }

        public void setMember_time(String member_time) {
            this.member_time = member_time;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
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

        public String getAvatar_url() {
            return avatar_url;
        }

        public void setAvatar_url(String avatar_url) {
            this.avatar_url = avatar_url;
        }

        public String getRegister_role() {
            return register_role;
        }

        public void setRegister_role(String register_role) {
            this.register_role = register_role;
        }

        public String getSignature() {
            return signature;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }

        public String getInvite_code() {
            return invite_code;
        }

        public void setInvite_code(String invite_code) {
            this.invite_code = invite_code;
        }

        public String getWxinfo_id() {
            return wxinfo_id;
        }

        public void setWxinfo_id(String wxinfo_id) {
            this.wxinfo_id = wxinfo_id;
        }
    }
}
