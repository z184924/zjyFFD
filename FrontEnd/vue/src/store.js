import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);
export default new Vuex.Store({
  state: {
    basePath:"http://127.0.0.1:8886",
    clientAndSecret:"Y2xpZW50OnNlY3JldA==",
    loading: false,
  },
  mutations: {
    login(info) {
      sessionStorage.setItem("sessionUser", JSON.stringify(info));
    },
    logout() {
      let info = {
        account: "",
        userName: "",
        moreInfo:{}
      };
      sessionStorage.setItem("sessionUser", JSON.stringify(info));
      sessionStorage.setItem("access_token","");
      localStorage.setItem("refresh_token","");
    },
    setLoading(state, loadingState) {
      state.loading = loadingState;
    },
  }
});
