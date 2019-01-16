import $ from "jquery"
import moment from "moment"
export default {
  computed: {
    mixBasePath() {
      return this.$store.state.basePath;
    },
    mixCurrentUser() {
      return this.$store.state.currentUser;
    },
  },
  methods: {
    // 登录
    mixLogin(inputAccount, inputPassword) {
      var account;
      var password;
      account = inputAccount;
      password = inputPassword;
      // console.log(username);
      return new Promise((resolve, reject) => {
        this.mixPost("api/login", {
          account: account,
          password: password,
        }).then(res => {
          //登录成功
          let data = res.data;
          let currentUser = {
            account: data.user.account,
            userName: data.user.userName,
            token: data.token,
            moreInfo: data.user,
          }
          this.$store.commit("login", currentUser);
          localStorage.setItem("account", account);
          localStorage.setItem("password", password);
          localStorage.setItem("currentUser", JSON.stringify(currentUser));
          resolve(res);
        }).catch(res => {
          this.$router.replace("/");
          reject(res);
        });
      })
    },
    mixLogout() {
      this.$store.commit("logout");
      this.mixPost("api/logout");
      this.$router.replace("/");
    },
    mixPost(api, data = {}, param = {}) {
      if (param.isShowLoading === undefined) {
        param.isShowLoading = false;
      }
      if (param.loadingMaxTime === undefined) {
        param.loadingMaxTime = 15000;
      }
      if (api != "api/login") {
        data.token = this.mixCurrentUser.token;
      }
      return new Promise((resolve, reject) => {
        let url = this.mixApi(api);
        let vue = this;
        if (param.isShowLoading) {
          this.$store.commit("setLoading", true);
          setTimeout(() => {
            this.$store.commit("setLoading", false);
            // this.mxLoading = false;
          }, param.loadingMaxTime)
        }
        $.ajax({
          url,
          type: "post",
          data,
          success(res) {
            if (param.isShowLoading) {
              vue.$store.commit("setLoading", false);
            }
            // this.mxLoading = false;
            if (res) {
              if (res.state == "errorToken") {
                if (res.message == "Expiry Token") {
                  vue.mixLogin();
                } else {
                  vue.$store.commit("logout");
                  vue.$router.replace("/")
                }
              }
              if (res.state != "success") {
                reject(res.state);
                vue.$alert(res.message, res.state, {
                  confirmButtonText: '确定'
                });
              }else{
                resolve(res);
              }
            }
          },
          error(err) {
            if (param.isShowLoading) {
              vue.$store.commit("setLoading", false);
            }
            vue.$message.error('无法连接服务器,请稍候重试');
            reject(err);
          }
        })
      });
    },
    mixApi(api) {
      let url = this.mixBasePath;
      url += api;
      return url;
    },
  }
}