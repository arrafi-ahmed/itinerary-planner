<template>
  <v-container class="fill-height">
    <v-row align="center" class="flex-grow-1" justify="center">
      <v-col class="border rounded px-10 py-5" sm="4">
        <h2 class="pb-3 text-center">Login</h2>
        <v-form ref="form" v-model="isFormValid" fast-fail @submit.prevent>
          <v-text-field
            v-model="username"
            :rules="usernameRules"
            label="Username"
            variant="underlined"
          ></v-text-field>

          <v-text-field
            v-model="password"
            :append-icon="showPass ? 'mdi-eye-off' : 'mdi-eye'"
            :rules="passwordRules"
            :type="showPass? 'text' : 'password'"
            label="Password"
            variant="underlined"
            @click:append="showPass = !showPass"
          ></v-text-field>

          <p class="text-center">
            No account?
            <router-link :to="{ name: 'drawer.register' }"
            >Register
            </router-link>
          </p>

          <v-btn class="d-block mt-2 mx-auto" type="submit" @click="handleLogin"
          >Submit
          </v-btn>
        </v-form>
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup>
import {ref} from "vue";
import {useStore} from "vuex";
import {useRouter} from "vue-router";

const store = useStore();
const router = useRouter();
const username = ref("");
const password = ref("");
const showPass = ref(false);
const form = ref(null);
const isFormValid = ref(true);
const usernameRules = [
  (value) => {
    if (value.length >= 3) return true;
    return "Username length must be 3 chars or longer";
  },
];
const passwordRules = [
  (value) => {
    if (value.length >= 6) return true;
    return "Password length must be 6 chars or long!";
  },
];
const handleLogin = async () => {
  await form.value.validate();
  if (!isFormValid.value) return;

  const request = {
    username: username.value,
    password: password.value,
  };
  store
    .dispatch("customer/login", request)
    .then((res) => {
      router.push({name: "drawer.home"});
      store.dispatch("setSnackbar", {
        message: "Login successful!",
        color: "success",
      });
    })
    .catch((err) => {
      store.dispatch("setSnackbar", {
        message: "Login failed!",
        color: "error",
      });
    });
};
</script>

<style scoped></style>
