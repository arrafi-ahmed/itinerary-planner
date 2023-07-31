<template>
  <v-app>
    <v-navigation-drawer v-model="drawer">
      <v-list>
        <v-list-item
          v-for="item in loggedInItems"
          v-if="loggedIn"
          :key="item.title"
          :title="item.title"
          :to="{ name: item.path }"
        ></v-list-item>
        <v-list-item
          v-for="item in loggedOutItems"
          v-else
          :key="item.title"
          :title="item.title"
          :to="{ name: item.path }"
        ></v-list-item>
      </v-list>
      <template v-slot:append>
        <div class="text-center pa-2 bg-grey-darken-4">
          Developed by
          <a
            class="text-white"
            href="https://www.linkedin.com/in/arrafi-ahmed"
            target="_blank"
          >Raf</a
          >
        </div>
      </template>
    </v-navigation-drawer>
    <v-app-bar border flat>
      <v-app-bar-nav-icon
        variant="text"
        @click="drawer = !drawer"
      ></v-app-bar-nav-icon>
      <v-app-bar-title>
        <logo/>
      </v-app-bar-title>
    </v-app-bar>
    <v-main>
      <router-view/>
    </v-main>
  </v-app>
</template>
<script setup>
import {computed, ref} from "vue";
import Logo from "@/components/Logo.vue";
import {useStore} from "vuex";

const store = useStore();
let drawer = ref(false);
const loggedIn = computed(() => store.getters["customer/loggedIn"]);
const loggedInItems = [
  {title: "Home", path: "drawer.home"},
  {title: "Add Trip", path: "drawer.tripAdd"},
  {title: "Trip List", path: "drawer.tripList"},
  {title: "Logout", path: "simple.logout"},
];
const loggedOutItems = [
  {title: "Register", path: "drawer.register"},
  {title: "Login", path: "drawer.login"},
];
</script>
