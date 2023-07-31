<template>
  <v-container class="fill-height">
    <v-row align="center" class="flex-grow-1" justify="center">
      <v-col class="border rounded px-10 py-5" sm="4">
        <h2 class="pb-3 text-center">Register</h2>
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
            :type="showPass ? 'text' : 'password'"
            label="Password"
            variant="underlined"
            @click:append="showPass = !showPass"
          ></v-text-field>

          <v-text-field
            v-model="name"
            :rules="nameRules"
            label="Name"
            variant="underlined"
          ></v-text-field>

          <!--          <v-select-->
          <!--            v-model="currency"-->
          <!--            :items="['USD', 'BDT']"-->
          <!--            :rules="[(v) => !!v || 'Item is required']"-->
          <!--            label="Item"-->
          <!--            required-->
          <!--          ></v-select>-->

          <p class="text-center">
            Already have account?
            <router-link :to="{ name: 'drawer.login' }">Login</router-link>
          </p>

          <v-btn
            class="d-block mt-2 mx-auto"
            type="submit"
            @click="handleRegister"
          >Submit
          </v-btn>
        </v-form>
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup>
import {ref} from "vue";
import {useRouter} from "vue-router";
import CustomerService from "@/service/customer";
import {useStore} from "vuex";

const store = useStore();
const router = useRouter();
const username = ref("");
const password = ref("");
const name = ref("");
const currency = ref("");
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
const nameRules = [
  (value) => {
    if (value.length >= 3) return true;
    return "Name length must be 3 chars or long!";
  },
];
const handleRegister = async () => {
  await form.value.validate();
  if (!isFormValid.value) return;

  const request = {
    username: username.value,
    password: password.value,
    name: name.value,
    currency: currency.value,
  };
  CustomerService.register(request)
    .then((res) => {
      router.push({name: "drawer.login"});
      store.dispatch("setSnackbar", {
        message: "Registered successfully!",
        color: "success",
      });
    })
    .catch((err) => {
      store.dispatch("setSnackbar", {
        message: "Error registering!",
        color: "error",
      });
    });
};
</script>

<style scoped></style>
