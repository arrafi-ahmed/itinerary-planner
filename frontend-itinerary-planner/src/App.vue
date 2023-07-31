<template>
  <drawer-layout v-if="layout === 'drawer'"></drawer-layout>
  <simple-layout v-if="layout === 'simple'"></simple-layout>

  <v-snackbar
    v-for="(snackbar, index) in snackbars.filter((s) => s.show)"
    :key="index"
    v-model="snackbar.show"
    :color="snackbar.color"
    :style="`bottom: ${index * 50}px`"
    :timeout="2000"
  >
    {{ snackbar.message }}
    <template v-slot:actions>
      <v-btn color="pink" variant="text" @click="closeSnackbar(index)">
        Close
      </v-btn>
    </template>
  </v-snackbar>
</template>

<script setup>
import DrawerLayout from "@/layouts/Drawer";
import SimpleLayout from "@/layouts/Simple";
import {computed} from "vue";
import {useRoute} from "vue-router";
import {useStore} from "vuex";

const store = useStore();
const route = useRoute();
const layout = computed(() => (route.name || "").split(".")[0]);
const snackbars = computed(() => store.state.snackbars);

const closeSnackbar = (index) => {
  store.dispatch("removeSnackbar", index);
};
</script>
<style>
.right-0 {
  right: 0;
}

.right-40 {
  right: 40px;
}

.right-120 {
  right: 120px;
}

@media print {
  html,
  body {
    height: 99%;
  }
}
</style>
