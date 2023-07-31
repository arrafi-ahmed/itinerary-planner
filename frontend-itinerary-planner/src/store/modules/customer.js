import CustomerService from "@/service/customer";

export const namespaced = true;

export const state = {
  token: localStorage.getItem("token") || null,
  username: localStorage.getItem("username") || null,
  id: localStorage.getItem("id") || null,
};

export const mutations = {
  setToken(state, payload) {
    localStorage.setItem("token", payload);
    state.token = payload;
  },
  removeToken(state) {
    state.token = null;
    localStorage.removeItem("token");
  },
  setUsername(state, payload) {
    localStorage.setItem("username", payload);
    state.username = payload;
  },
  removeUsername(state) {
    state.username = null;
    localStorage.removeItem("username");
  },
  setId(state, payload) {
    localStorage.setItem("id", payload);
    state.id = payload;
  },
  removeId(state) {
    state.id = null;
    localStorage.removeItem("id");
  },
};

export const actions = {
  login({commit}, request) {
    return new Promise((resolve, reject) => {
      CustomerService.login(request)
        .then((response) => {
          if (response.data) {
            const token = response.data.token;
            const username = response.data.username;
            const id = response.data.id;
            if (token) {
              commit("setToken", token);
              commit("setUsername", username);
              commit("setId", id);
            }
          }
          resolve(response);
        })
        .catch((err) => {
          reject(err);
        });
    });
  },
  logout({commit}) {
    return new Promise((resolve, reject) => {
      commit("removeUsername");
      commit("removeToken");
      commit("removeId");
    });
  },
};

export const getters = {
  loggedIn(state) {
    return !!state.token;
  },
  getId(state) {
    return state.id;
  },
  getToken(state) {
    return state.token;
  },
};
