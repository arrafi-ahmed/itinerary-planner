<template>
  <v-container class="fill-height">
    <v-row align="center" class="flex-grow-1" justify="center">
      <v-col cols="12">
        <h2 class="pb-3">Good {{ getGreetings() }} {{ userName }}!</h2>
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup>
import {useStore} from "vuex";

const store = useStore();

const userName = store.state.customer.username;

const getGreetings = () => {
  const currTime = new Date();
  if (currTime.getHours() > 6 && currTime.getHours() < 12) return "Morning";
  else if (currTime.getHours() > 12 && currTime.getHours() < 18)
    return "Afternoon";
  else return "Evening";
};
</script>

<style scoped></style>
