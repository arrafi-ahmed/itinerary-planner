import {createStore} from "vuex";
import * as customer from "./modules/customer";
import * as trip from "./modules/trip";

const store = createStore({
  modules: {
    customer,
    trip,
  },
  state: () => ({
    snackbars: [],
  }),
  mutations: {
    setSnackbar(state, payload) {
      state.snackbars.push(payload);
      console.log(state.snackbars.length)
    },
    removeSnackbar(state, payload) {
      state.snackbars.splice(payload, 1);
      console.log(state.snackbars.length, payload)
    },
  },
  actions: {
    setSnackbar({commit}, request) {
      if (request.show !== false) request.show = true;
      commit("setSnackbar", request);
    },
    removeSnackbar({commit}, request) {
      commit("removeSnackbar", request);
    },
  },
});

export default store;
