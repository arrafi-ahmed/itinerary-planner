/**
 * plugins/index.js
 *
 * Automatically included in `./src/main.js`
 */

// Plugins
import {loadFonts} from "./webfontloader";
import vuetify from "./vuetify";
import router from "../router";
import vuex from "../store";
import {setupAxiosInterceptor} from "@/util";

export function registerPlugins(app) {
  loadFonts();

  router.beforeEach((to, from, next) => {
    const loggedIn = vuex.getters["customer/loggedIn"];
    if (to.matched.some((record) => record.meta.requiresAuth)) {
      if (!loggedIn) {
        router.replace({name: "drawer.login"}).catch(() => {
        });
      }
    } else {
      if (loggedIn) {
        router.replace({name: "drawer.home"}).catch(() => {
        });
      }
    }
    next();
  });

  setupAxiosInterceptor();

  app.use(vuetify).use(router).use(vuex);
}
